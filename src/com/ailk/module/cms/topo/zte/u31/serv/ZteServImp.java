
package com.ailk.module.cms.topo.zte.u31.serv;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ailk.module.cms.system.basesupport.BaseSupportServ;
import com.ailk.module.cms.topo.zte.u31.dao.ZteDAOImp;

/**
 * 华为U2000拓扑生产Serv
 * 
 * @author mengqiang(65453)
 * @version 1.0
 * @since 2014-3-6 上午9:41:00
 * @category com.ailk.module.cms.topo.hw.u2000.serv
 * @copyright 南京联创科技 网管科技部
 */
public class ZteServImp implements BaseSupportServ
{

	/**
	 * log4j日志记录器
	 */
	private static final Logger LOG = Logger.getLogger(ZteServImp.class);
	/**
	 * 设备类型Map
	 */
	private Map<String,String> moTypeMap = new HashMap<String, String>();
	/**
	 * 节点信息Map
	 */
	private Map<String, Map> nodeMap = new HashMap<String, Map>();
	/**
	 * 节点信息Map
	 */
	private Map<String, String> moMap = new HashMap<String, String>();
	/**
	 * 节点信息Map
	 */
	private Map<String, String> moNameMap = new HashMap<String, String>();
	/**
	 * 中兴U31拓扑生产DAO
	 */
	private ZteDAOImp zteDAOImp = null;
	/**
	 * 文件路径
	 */
	private String filePath = null;
	/**
	 * ems名称
	 */
	private String emsNameExt = null;
	/**
	 * 开始Id
	 */
	private int startId = 10000;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		initMoType("华为");
		LOG.info("1");
		File file = new File(filePath + File.separator + "SDTA-ZX-U31-2-ZTEOTN;1getAllManagedElements.txt");
		parserAllManagedElements(file);
		LOG.info("2");
		file = new File(filePath + File.separator + "SDTA-ZX-U31-2-ZTEOTN;2getTopoSubnetworkViewInfo.txt");
		parserSubnetworkViewInfo(file);
		LOG.info("3");
		file = new File(filePath + File.separator + "SDTA-ZX-U31-2-ZTEOTN;3getTopLevelTopologicalLink.txt");
		parserAllTopologicalLinks(file);
		LOG.info("4");
		file = new File(filePath + File.separator + "SDTA-ZX-U31-2-ZTEOTN;4getAllTopologicalLinks.txt");
		parserAllTopologicalLinks(file);
	}

	// 获取子系统Id
	private String getSubSystemId(String pid, Object emsId)
	{
		if (pid == null || "".equals(pid))
		{
			return "1/transport/sdh/ems/" + emsId;
		}
		return pid;
	}
		
	// 初始化网元类型
	public void initMoType(String vendor)
	{
		Map param = new HashMap();
		param.put("vendor_id", vendor);
		List<Map> moList = zteDAOImp.getMoModel(param);
		String moTypeName = null;
		String moTypeId = null;
		String devModel = null;
		for (Map map : moList)
		{
			devModel = objToStr(map.get("dev_model")).toLowerCase();
			moTypeId = objToStr(map.get("mo_type_id")).toLowerCase();
			moTypeName = objToStr(map.get("mo_type_name")).toLowerCase();
			moTypeMap.put(devModel, moTypeId + "|-|" + moTypeName);
		}
	}
	
	// 获取网元类型
	public String getMoTypeIdByModel(String model, String defType)
	{
		if (model == null || "".equals(model))
		{
			return defType;
		}
		String value = moTypeMap.get(model.toLowerCase());
		if (value != null)
		{
			return value.split("|-|")[0];
		}
		return defType;
	}

	// 获取网元类型
	public String getMoTypeNameByModel(String model, String defType)
	{
		if (model == null || "".equals(model))
		{
			return defType;
		}
		String value = moTypeMap.get(model.toLowerCase());
		if (value != null)
		{
			return value.split("|-|")[1];
		}
		return defType;
	}
	
	// 解析链路
	public void parserAllTopologicalLinks(File file)
	{
		List<Map> linkList = new ArrayList<Map>();
		String emsInfo = getEmsInfo(file.getName());
		String emsId = emsInfo.split(",")[0];
		SAXReader saxReader = new SAXReader();
		try
		{
			Map linkMap = null;
			Document doc = saxReader.read(file);
			Element rootElt = doc.getRootElement();
			List<Element> eleList = rootElt.elements();
			String aMoId = null;
			String zMoId = null;
			String amoName = null;
			String zmoName = null;
			String aPortName = null;
			String zPortName = null;
			for (Element et : eleList)
			{
				aPortName = et.valueOf("aEndTP");
				zPortName = et.valueOf("zEndTP");
				amoName = getEmsName(aPortName);
				zmoName = getEmsName(zPortName);
				aMoId = moMap.get(amoName);
				zMoId = moMap.get(zmoName);
				if (aMoId == null || zMoId == null)
				{
					aMoId = String.valueOf(nodeMap.get(amoName).get("mo_id"));
					zMoId = String.valueOf(nodeMap.get(zmoName).get("mo_id"));
					if (aMoId == null || zMoId == null)
					{
						LOG.info("链路两端为空:" + aPortName + "," + zPortName);
						continue;
					}
				}
				linkMap = new HashMap();
				linkMap.put("ems_id", emsId);
				linkMap.put("topo_id", "4.link." + emsId + getMoId(startId));
				linkMap.put("direction", et.valueOf("direction"));
				linkMap.put("amo_id", aMoId);
				linkMap.put("zmo_id", zMoId);
				linkMap.put("amo_name", amoName);
				linkMap.put("zmo_name", zmoName);
				linkMap.put("aport_name", aPortName);
				linkMap.put("zport_name", zPortName);
				linkMap.put("aport_id", getPortName(aPortName));
				linkMap.put("zport_id", getPortName(zPortName));
				linkList.add(linkMap);
				startId++;
			}
			deleteTopoViewLinkInfo(emsId);
			if(!linkList.isEmpty())
			{
				LOG.info("处理链路信息:" + linkList.size());
			}
			zteDAOImp.bacthSaveViewLinkInfo(linkList);
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}

	// 解析所有网元信息
	public void parserAllManagedElements(File file)
	{
		String emsInfo = getEmsInfo(file.getName());
		String emsId = emsInfo.split(",")[0];
		String cityId = emsInfo.split(",")[1];
		System.out.println(emsNameExt);
		SAXReader saxReader = new SAXReader();
		List<Map> nodeList = new ArrayList<Map>();
		try
		{
			Map node = null;
			String emsName = null;
			Document doc = saxReader.read(file);
			Element rootElt = doc.getRootElement();
			List<Element> eleList = rootElt.elements();
			for (Element et : eleList)
			{
				emsName = et.valueOf("name");
				node = new HashMap();
				node.put("mo_id", "4.tran." + emsId + getMoId(startId));
				node.put("mo_type_id", getMoTypeIdByModel(et.valueOf("productName"), "39"));
				node.put("mo_name", et.valueOf("nativeEMSName"));
				node.put("mo_name_alias", et.valueOf("userLabel"));
				node.put("city_id", cityId);
				node.put("dev_model", getMoTypeNameByModel(et.valueOf("productName"), "SDH"));
				node.put("omc_mo_name", emsName);
				node.put("ems_id", emsId);
				node.put("vendor_id", "中兴");
				Element addEle = et.element("additionalInfos");
				if (addEle != null)
				{
					String coordinate = addEle.valueOf("Coordinate");
					String[] pos = coordinate.split(",");
					if (pos.length == 2)
					{
						try
						{
							node.put("longitude", (Float.parseFloat(pos[0]) * 5));
							node.put("latitude", (Float.parseFloat(pos[1]) * 5));
						}
						catch (Exception e)
						{
							LOG.info("转换坐标出错:" + e.getMessage());
							node.put("longitude", pos[0]);
							node.put("latitude", pos[1]);
						}
					}
				}
				nodeMap.put(emsName, node);
				nodeList.add(node);
				startId++;
			}
			contrastMoInfo(nodeList, emsId);
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}

	// 对比网元信息
	public void contrastMoInfo(List<Map> nodeList, String emsId)
	{
		Map param = new HashMap();
		param.put("ems_id", emsId);
		param.put("ems_name", emsNameExt);
		List<Map> moList = zteDAOImp.getMoInfo(param);
		for (Map map : moList)
		{
			moMap.put(objToStr(map.get("omc_mo_name")), objToStr(map.get("mo_id")));
			moNameMap.put(objToStr(map.get("mo_name")), objToStr(map.get("mo_id")));
		}
		// 开始对比
		List<Map> saveMoList = new ArrayList<Map>();
		List<Map> updateMoList = new ArrayList<Map>();
		String emsName = null;
		String moName = null;
		for (Map map : nodeList)
		{
			emsName = objToStr(map.get("omc_mo_name"));
			moName = objToStr(map.get("mo_name"));
			if (moMap.containsKey(emsName))
			{
				map.put("mo_id", moMap.get(emsName));
				updateMoList.add(map);
			}
			else if (moNameMap.containsKey(moName))
			{
				map.put("mo_id", moNameMap.get(moName));
				updateMoList.add(map);
			}
			else
			{
				moMap.put(emsName, objToStr(map.get("mo_id")));
				moNameMap.put(moName, objToStr(map.get("mo_id")));
				saveMoList.add(map);
			}
		}
		// 网元开始入库
		if (!saveMoList.isEmpty())
		{
			LOG.info("新增网元:" + saveMoList.size());
			zteDAOImp.batchSaveMoInfo(saveMoList);
		}
		// 网元开始更新
		if (!updateMoList.isEmpty())
		{
			LOG.info("更新网元:" + updateMoList.size());
			zteDAOImp.batchUpdateMoInfo(updateMoList);
		}
	}

	// 解析子网信息
	private void parserSubnetworkViewInfo(File file)
	{
		String emsInfo = getEmsInfo(file.getName());
		String emsId = emsInfo.split(",")[0];
		String cityId = emsInfo.split(",")[1];
		SAXReader saxReader = new SAXReader();
		try
		{
			Map node = null;
			String subSysId = null;
			String emsName = null;
			Document doc = saxReader.read(file);
			Element rootElt = doc.getRootElement();
			List<Element> eleList = rootElt.elements();
			List<Map> moTranList = new ArrayList<Map>();
			Map<String, Map> segMap = new HashMap<String, Map>();
			for (Element et : eleList)
			{
				emsName = et.valueOf("name");
				node = new HashMap();
				node.put("sub_system_name", et.valueOf("nativeEMSName"));
				subSysId = "4.segment." + emsId + getMoId(startId);
				node.put("sub_system_id", subSysId);
				node.put("ems_id", emsId);
				node.put("ems_name", emsName);
				node.put("sub_system_pid", "");
				String coordinate = et.valueOf("coordinate");
				String[] pos = coordinate.split(",");
				if (pos.length == 2)
				{
					node.put("x", pos[0]);
					node.put("y", pos[1]);
				}
				// 网元与子网之间的关系
				Element memberName = et.element("includingMemberNameList");
				List<Element> memberNameList = memberName.elements();
				for (Element mnl : memberNameList)
				{
					String name = mnl.valueOf("name");
					if (name != null && name.contains("ManagedElementGroup"))
					{
						List<String> childList = (List<String>) node.get("child_system");
						if (childList == null)
						{
							childList = new ArrayList<String>();
							node.put("child_system", childList);
						}
						childList.add(name);
					}
					else
					{
						if (moMap.containsKey(name))
						{
							Map moTranMap = new HashMap();
							moTranMap.put("ems_id", emsId);
							moTranMap.put("sub_system_id", getSubSystemId(subSysId,emsId));
							moTranMap.put("mo_id", moMap.get(name));
							moTranList.add(moTranMap);
						}
						else if (nodeMap.containsKey(name))
						{
							Map devMap = nodeMap.get(name);
							String moName = String.valueOf(devMap.get("mo_name"));
							if (moNameMap.containsKey(moName))
							{
								Map moTranMap = new HashMap();
								moTranMap.put("ems_id", emsId);
								moTranMap.put("sub_system_id", getSubSystemId(subSysId,emsId));
								moTranMap.put("mo_id", moNameMap.get(moName));
								moTranList.add(moTranMap);
							}
						}
					}
				}
				moMap.put(emsName, subSysId);
				segMap.put(emsName, node);
				startId++;
			}
			// 处理子网视图
			deleteTopoViewInfo(emsId);
			dealSubSystemViewInfo(segMap);
			if (!moTranList.isEmpty())
			{
				LOG.info("处理子网视图:" + moTranList);
				zteDAOImp.batchSaveTranSystemInfo(moTranList);
			}
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}

	// 处理子网视图
	private void dealSubSystemViewInfo(Map<String, Map> segMap)
	{
		List<Map> subSystemList = new ArrayList<Map>();
		for (Map seg : segMap.values())
		{
			if (seg.containsKey("child_system"))
			{
				List<String> childList = (List<String>) seg.get("child_system");
				for (String childName : childList)
				{
					Map childMap = segMap.get(childName);
					if (childMap != null)
					{
						childMap.put("sub_system_pid", seg.get("sub_system_id"));
					}
				}
			}
			subSystemList.add(seg);
		}
		if (!subSystemList.isEmpty())
		{
			zteDAOImp.batchSaveSystemInfo(subSystemList);
		}
	}

	// 获取数据ID编号
	public long getMoId(int i)
	{
		return System.currentTimeMillis() / 1000 + i;
	}

	// 删除拓扑数据
	public void deleteTopoViewInfo(String emsId)
	{
		Map param = new HashMap();
		param.put("ems_id", emsId);
		zteDAOImp.deleteSystemInfo(param);
		zteDAOImp.deleteMoSystemInfo(param);
	}

	// 删除拓扑链路数据
	public void deleteTopoViewLinkInfo(String emsId)
	{
		Map param = new HashMap();
		param.put("ems_id", emsId);
		zteDAOImp.deleteSystemLinkInfo(param);
	}

	// 获取EmsId
	private String getEmsInfo(String fileName)
	{
		String emsName = fileName.split(";")[0];
		emsNameExt = emsName;
		Map param = new HashMap();
		param.put("emsName", emsName);
		List<Map> emsList = zteDAOImp.getEmsInfo(param);
		if (emsList.isEmpty())
		{
			return emsName + ",-1";
		}
		Map cityMap = emsList.get(0);
		return cityMap.get("ems_id") + "," + cityMap.get("city_id");
	}

	// 获取ems名称
	private String getEmsName(String emsName)
	{
		if (emsName == null)
		{
			return null;
		}
		String[] array = emsName.split(";");
		if (array.length < 2)
		{
			return emsName;
		}
		return array[0] + ";" + array[1];
	}

	// 获取ems名称
	private String getPortName(String emsName)
	{
		if (emsName == null)
		{
			return null;
		}
		String[] array = emsName.split(";");
		if (array.length < 3)
		{
			return emsName;
		}
		return array[2];
	}

	// 对象转换为字符串
	public String objToStr(Object obj)
	{
		return obj == null ? "" : String.valueOf(obj).trim();
	}

	@Override
	public void destroy() throws Exception
	{
	}

	public void setZteDAOImp(ZteDAOImp zteDAOImp)
	{
		this.zteDAOImp = zteDAOImp;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
}