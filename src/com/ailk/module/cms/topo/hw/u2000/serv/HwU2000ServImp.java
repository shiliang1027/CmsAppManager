
package com.ailk.module.cms.topo.hw.u2000.serv;

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
import com.ailk.module.cms.topo.hw.u2000.dao.HwU2000DAOImp;

/**
 * ��ΪU2000��������Serv
 * 
 * @author mengqiang(65453)
 * @version 1.0
 * @since 2014-3-6 ����9:41:00
 * @category com.ailk.module.cms.topo.hw.u2000.serv
 * @copyright �Ͼ������Ƽ� ���ܿƼ���
 */
public class HwU2000ServImp implements BaseSupportServ
{

	/**
	 * log4j��־��¼��
	 */
	private static final Logger LOG = Logger.getLogger(HwU2000ServImp.class);
	/**
	 * �豸����Map
	 */
	private Map<String, String> moTypeMap = new HashMap<String, String>();
	/**
	 * ��Ԫ��ϢMap
	 */
	private Map<String, String> moMap = new HashMap<String, String>();
	/**
	 * ��Ԫ��ϢMap
	 */
	private Map<String, String> moNameMap = new HashMap<String, String>();
	/**
	 * �ڵ���ϢMap
	 */
	private Map<String, Map> nodeMap = new HashMap<String, Map>();
	/**
	 * ��ΪU2000��������DAO
	 */
	private HwU2000DAOImp hwU2000DAOImp = null;
	/**
	 * �ļ�·��
	 */
	private String filePath = null;
	/**
	 * ��ʼId
	 */
	private int startId = 10000;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		initMoType("����");
		File file = new File(filePath);
		for (File sfile : file.listFiles())
		{
			LOG.info(sfile.getName());
			if (sfile.getName().contains("getAllManagedElements"))
			{
				LOG.info("parserSubnetworkViewInfo");
				parserAllManagedElements(sfile);
			}
			else if (sfile.getName().contains("getTopoSubnetworkViewInfo"))
			{
				LOG.info("parserSubnetworkViewInfo");
				parserSubnetworkViewInfo(sfile);
			}
			else if (sfile.getName().contains("getAllTopologicalLinks"))
			{
				LOG.info("parserAllTopologicalLinks");
				parserAllTopologicalLinks(sfile);
			}
		}
	}

	// ��ʼ����Ԫ����
	public void initMoType(String vendor)
	{
		Map param = new HashMap();
		param.put("vendor_id", vendor);
		List<Map> moList = hwU2000DAOImp.getMoModel(param);
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

	// ��ȡ��Ԫ����
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

	// ��ȡ��Ԫ����
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

	// ����������Ϣ
	private void parserAllManagedElements(File file)
	{
		String emsInfo = getEmsInfo(file.getName());
		String emsId = emsInfo.split(",")[0];
		String cityId = emsInfo.split(",")[1];
		SAXReader saxReader = new SAXReader();
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
				node.put("mo_name_alias", objToStr(et.valueOf("userLabel")));
				node.put("city_id", cityId);
				node.put("dev_model", et.valueOf("productName"));
				node.put("dev_type", getMoTypeNameByModel(et.valueOf("productName"), "SDH"));
				node.put("omc_mo_name", emsName);
				node.put("ems_id", emsId);
				node.put("vendor_id", "��Ϊ");
				node.put("longitude", "0");
				node.put("latitude", "0");
				nodeMap.put(emsName, node);
				startId++;
			}
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}

	// ����������Ϣ
	private void parserSubnetworkViewInfo(File file)
	{
		Map<String, Map> subSysMap = new HashMap<String, Map>();
		String emsInfo = getEmsInfo(file.getName());
		String emsId = emsInfo.split(",")[0];
		String cityId = emsInfo.split(",")[1];
		SAXReader saxReader = new SAXReader();
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
				node.put("sub_system_name", et.valueOf("nativeEMSName"));
				node.put("x", et.valueOf("xPos"));
				node.put("y", et.valueOf("yPos"));
				node.put("ems_id", emsId);
				node.put("city_id", cityId);
				node.put("ems_name", emsName);
				node.put("sub_system_pid", "");
				node.put("parent", et.valueOf("parent"));
				if (emsName != null && emsName.contains("TopoSubnetwork"))
				{
					node.put("sub_system_id", "4.segment." + emsId + getMoId(startId));
					node.put("type", "1");
				}
				else
				{
					node.put("sub_system_id", "4.tran." + emsId + getMoId(startId));
					node.put("type", "0");
				}
				subSysMap.put(emsName, node);
				startId++;
			}
			dealSubnetworkViewInfo(subSysMap, emsId);
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}

	// ����������ͼ����
	public void dealSubnetworkViewInfo(Map<String, Map> subSysMap, String emsId)
	{
		List<Map> moTranList = new ArrayList<Map>();
		List<Map> subList = new ArrayList<Map>();
		Map parentNode = null;
		String emsName = null;
		Map moTranMap = null;
		String type = null;
		String pid = "";
		Map moInfoMap = null;
		for (Map node : subSysMap.values())
		{
			parentNode = subSysMap.get(objToStr(node.get("parent")));
			if (parentNode != null)
			{
				pid = objToStr(parentNode.get("sub_system_id"));
				node.put("sub_system_pid", pid);
			}
			type = objToStr(node.get("type"));
			// ����
			if ("1".equals(type))
			{
				subList.add(node);
			}
			else
			{
				// �豸��Ϣ
				emsName = objToStr(node.get("ems_name"));
				moInfoMap = nodeMap.get(emsName);
				if (moInfoMap == null)
				{
					moInfoMap = new HashMap();
					moInfoMap.put("mo_id", node.get("sub_system_id"));
					moInfoMap.put("mo_name", node.get("sub_system_name"));
					moInfoMap.put("mo_type_id", "39");
					moInfoMap.put("mo_name_alias", objToStr(node.get("userLabel")));
					moInfoMap.put("city_id", node.get("city_id"));
					moInfoMap.put("dev_model", "");
					moInfoMap.put("omc_mo_name", emsName);
					moInfoMap.put("ems_id", node.get("ems_id"));
					moInfoMap.put("vendor_id", "��Ϊ");
					nodeMap.put(emsName, moInfoMap);
				}
				moInfoMap.put("longitude", node.get("x"));
				moInfoMap.put("latitude", node.get("y"));
				// ��Ԫϵͳ��Ӧ��ϵ
				moTranMap = new HashMap();
				moTranMap.put("ems_name", emsName);
				moTranMap.put("sub_system_id", getSubSystemId(pid, moInfoMap.get("ems_id")));
				moTranMap.put("mo_id", moInfoMap.get("mo_id"));
				moTranMap.put("mo_name", moInfoMap.get("mo_name"));
				moTranMap.put("ems_id", moInfoMap.get("ems_id"));
				moTranList.add(moTranMap);
			}
		}
		contrastMoInfo(emsId, emsName);
		deleteTopoViewInfo(emsId);
		dealWithMoTranInfo(moTranList);
		hwU2000DAOImp.batchSaveSystemInfo(subList);
		hwU2000DAOImp.batchSaveTranSystemInfo(moTranList);
	}

	// ��ȡ��ϵͳId
	private String getSubSystemId(String pid, Object emsId)
	{
		if (pid == null || "".equals(pid))
		{
			return "1/transport/sdh/ems/" + emsId;
		}
		return pid;
	}
	
	// ������ԪId
	public void dealWithMoTranInfo(List<Map> moTranList)
	{
		String emsName = null;
		String moName = null;
		for (Map map : moTranList)
		{
			emsName = objToStr(map.get("ems_name"));
			moName = objToStr(map.get("mo_name"));
			if (moMap.containsKey(emsName))
			{
				map.put("mo_id", moMap.get(emsName));
				continue;
			}
			if (moNameMap.containsKey(moName))
			{
				map.put("mo_id", moNameMap.get(moName));
			}
		}
	}

	// �Ա���Ԫ��Ϣ
	public void contrastMoInfo(String emsId, String en)
	{
		Map param = new HashMap();
		param.put("ems_id", emsId);
		param.put("ems_name", en.split(";")[0]);
		List<Map> moList = hwU2000DAOImp.getMoInfo(param);
		for (Map map : moList)
		{
			moMap.put(objToStr(map.get("omc_mo_name")), objToStr(map.get("mo_id")));
			moNameMap.put(objToStr(map.get("mo_name")), objToStr(map.get("mo_id")));
		}
		// ��ʼ�Ա�
		List<Map> saveMoList = new ArrayList<Map>();
		List<Map> updateMoList = new ArrayList<Map>();
		String emsName = null;
		String moName = null;
		for (Map map : nodeMap.values())
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
		// ��Ԫ��ʼ���
		if (!saveMoList.isEmpty())
		{
			LOG.info("�������:" + saveMoList.size());
			hwU2000DAOImp.batchSaveMoInfo(saveMoList);
		}
		// ��Ԫ��ʼ����
		if (!updateMoList.isEmpty())
		{
			LOG.info("��������:" + updateMoList.size());
			hwU2000DAOImp.batchUpdateMoInfo(updateMoList);
		}
	}

	// ɾ����������
	public void deleteTopoViewInfo(String emsId)
	{
		Map param = new HashMap();
		param.put("ems_id", emsId);
		hwU2000DAOImp.deleteSystemInfo(param);
		hwU2000DAOImp.deleteMoSystemInfo(param);
	}

	// ɾ��������·����
	public void deleteTopoViewLinkInfo(String emsId)
	{
		Map param = new HashMap();
		param.put("ems_id", emsId);
		hwU2000DAOImp.deleteSystemLinkInfo(param);
	}

	// ��ȡEmsId
	private String getEmsInfo(String fileName)
	{
		String emsName = fileName.split(";")[0];
		Map param = new HashMap();
		param.put("emsName", fileName.split(";")[0]);
		List<Map> emsList = hwU2000DAOImp.getEmsInfo(param);
		if (emsList.isEmpty())
		{
			return emsName + ",-1";
		}
		Map cityMap = emsList.get(0);
		return cityMap.get("ems_id") + "," + cityMap.get("city_id");
	}

	// ������·
	public void parserAllTopologicalLinks(File file)
	{
		List<Map> linkList = new ArrayList<Map>();
		String emsInfo = getEmsInfo(file.getName());
		String emsId = emsInfo.split(",")[0];
		SAXReader saxReader = new SAXReader();
		try
		{
			int i = 10000;
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
						LOG.info("��·����Ϊ��:" + aPortName + "," + zPortName);
						continue;
					}
				}
				linkMap = new HashMap();
				linkMap.put("ems_id", emsId);
				linkMap.put("topo_id", "4.link." + i);
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
				i++;
			}
			deleteTopoViewLinkInfo(emsId);
			hwU2000DAOImp.bacthSaveViewLinkInfo(linkList);
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}

	// ��ȡems����
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

	// ��ȡems����
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

	@Override
	public void destroy() throws Exception
	{
	}

	// ��ȡ����ID���
	public long getMoId(int i)
	{
		return System.currentTimeMillis() / 1000 + i;
	}

	// ����ת��Ϊ�ַ���
	public String objToStr(Object obj)
	{
		return obj == null ? "" : String.valueOf(obj).trim();
	}

	public void setHwU2000DAOImp(HwU2000DAOImp hwU2000DAOImp)
	{
		this.hwU2000DAOImp = hwU2000DAOImp;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
}