package com.ailk.module.cms.scene.usercountstat.serv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import com.ailk.module.cms.scene.usercountstat.dao.SceneUserCountStatDAO;
import com.ailk.module.cms.system.basesupport.BaseSupportServ;
import com.linkage.system.utils.corba.CorbaMsgSupport;

/**
 * 场景/区域业务层接口
 * 
 * @author loukp(No:69139 tel:15951882231)
 * @version 1.0.0
 * @category com.ailk.module.cms.scene.usercountstat.serv
 * @since 2012-08-12
 * @copyright 亚信联创 网管开发部
 */
public class SceneUserCountStatServImp implements BaseSupportServ {

	/**
	 * log4j日志记录器
	 */
	private static final Logger LOG = Logger
			.getLogger(SceneUserCountStatServImp.class);

	/**
	 * 用户数统计DAO
	 */
	private SceneUserCountStatDAO sceneUserCountStatDao = null;

	/**
	 * corba 接口
	 */
	private String corbaUrl = "";

	/**
	 * pm Corba
	 */
	private CorbaMsgSupport pmCorba = null;

	public void afterPropertiesSet() throws Exception {
		LOG.info("spring afterPropertiesSet  begin.  BeginTime:"
				+ DateFormatUtils.format(System.currentTimeMillis(),
						"yyyy-MM-dd HH:mm:ss"));
		// 获取需要统计的用户数
		List<Map> trainGroupList = sceneUserCountStatDao.getTrainGroupList();
		LOG.info("获取铁路场景基站分组数据 : " + trainGroupList);

		if (null == trainGroupList) {
			return;
		}

		// 调用corba接口
		String groupId = "";
		String moId = "";
		String moTypeId = "";
		String perfTypeId = "";

		List<Map> nodeAttrsList = new ArrayList();
		Map nodeAttrsMap = null;
		Map nodeAttrs = null;
		// List<Map(groupId = moList)>
		Map<String, List> groupIdMoIdMap = new HashMap<String, List>();
		List moIdList = null;
		
		for (Map trainGroup : trainGroupList) {
			groupId = trainGroup.get("GROUP_ID") + "";
			moId = trainGroup.get("MO_ID") + "";
			moTypeId = trainGroup.get("MO_TYPE_ID") + "";
			perfTypeId = trainGroup.get("PERF_TYPE_ID") + "";
			
			// 按组分网元
			if(groupIdMoIdMap.get(groupId) == null)
			{
				moIdList = new ArrayList();
				moIdList.add(moId);
				groupIdMoIdMap.put(groupId, moIdList);
			}
			else
			{
				groupIdMoIdMap.get(groupId).add(moId);
			}
			
			// 组装corba参数
			nodeAttrsMap = new HashMap();
			nodeAttrsMap.put("mo_id", moId);
			nodeAttrsMap.put("mo_type_id", moTypeId);
			nodeAttrsMap.put("kpi_id", perfTypeId);

			nodeAttrs = new HashMap();
			nodeAttrs.put("nodeAttrs", nodeAttrsMap);
			
			nodeAttrsList.add(nodeAttrs);
		}

		Map corbaParams = new HashMap();
		corbaParams.put("subNodes", nodeAttrsList);
		Map subNodes = null;
		List<Map> subNodesList = null;
		try {
			LOG.info("调用corba 主题:" + corbaUrl);
			LOG.info("调用corba 参数:" + corbaParams);
			subNodes = pmCorba.sendAndRecvMsg(corbaUrl, corbaParams);
			subNodesList = (List<Map>) subNodes.get("subNodes");
			LOG.info("调用corba 返回结果:" + subNodesList);
		} catch (Exception e) {
			LOG.error("afterPropertiesSet() 调用corba接口异常", e);
		}

		/*
		 * 场景网元实时数据corba接口定义 主题：/Pm/abims/realdata/scenemodata
		 * 传入参数：mo_id,mo_type_id,kpi_id 传出参数:
		 * mo_id,mo_type_id,kpi_id,value,gather_time,warn_level,color
		 */
		// 按组求和 
		if (subNodesList == null || subNodesList.size() == 0) {
			return ;
		}
		
		
		
		Map naMap = null;
		Map paramMap = null;
		String corbaMoId = "";
		String corbaMoTypeId = "";
		String corbaKpiId = "";
		String corbaValue = "";
		List<String> moList = null;
		double userCount = 0l;
		Map<String,String> groupIdValue = new HashMap<String,String>();
		// 循环Map
		List<Map> subSubNodes = null;
		for(Map.Entry<String, List> entry : groupIdMoIdMap.entrySet())    
		{    
			moList = entry.getValue();
			userCount = 0l;
			for (String strMoId : moList) {
				for(Map subNode : subNodesList)
				{   //subNodes
					subSubNodes = (List<Map>)subNode.get("subNodes");
					for(Map map : subSubNodes)
					{   //kpi_id=1110, color=, value=0.00, gather_time=, warn_level=5
						naMap = (HashMap) map.get("nodeAttrs");
						paramMap = (HashMap)subNode.get("nodeAttrs");
						corbaMoId = paramMap.get("mo_id") + "";
						corbaValue = naMap.get("value") + "";
						if(corbaMoId.equals(strMoId))
						{
							if(null != corbaValue && !"".equals(corbaValue))
							{
								userCount += Double.valueOf(corbaValue);
							}
						}
					}
				}
			}
			groupIdValue.put(entry.getKey()+"", String.valueOf(userCount));	
		}   
		
		// 查出所要更新的groupId
		StringBuffer sbGroupId = new StringBuffer("");
		for(Map.Entry<String, String> entry : groupIdValue.entrySet())    
		{    
			sbGroupId.append("'").append(entry.getKey()+"").append("'");
			sbGroupId.append(",");
		}
		
		List<Map> trainGroupListByGroupId = sceneUserCountStatDao
				.getTrainGroupListByGroupId(sbGroupId.substring(0, sbGroupId
						.length() - 1));

		// 更新数据
		List paramsList = new ArrayList();
		Map paramsMap = null;
		String currentTime = "";
		double value = 0l;
		double threshold = 0l;
		int oper = 0;
		boolean isShow = true;
		for(Map.Entry<String, String> entry : groupIdValue.entrySet())    
		{    
			paramsMap = new HashMap();
			paramsMap.put("groupId", entry.getKey()+"");
			paramsMap.put("value", entry.getValue()+"");
			currentTime = String.valueOf(System.currentTimeMillis());
			paramsMap.put("updateTime", currentTime.substring(0, currentTime.length() - 3));
			
			value = Double.parseDouble(entry.getValue()+"");
			
			for(Map map : trainGroupListByGroupId) 
			{
				// 1:<   2:<=  3:=  4:>  5:>=
				oper = Integer.parseInt((map.get("OPER")+"")==null || "".equals(map.get("OPER") + "")  ? "0" : map.get("OPER") + "");
				threshold = Double.parseDouble(map.get("THRESHOLD")==null || "".equals(map.get("THRESHOLD") + "") ? "0" : map.get("THRESHOLD") + "");
				if((entry.getKey()+"").equals(map.get("GROUP_ID")+""))
				{
					switch (oper) {
					case 1:
						isShow = (value < threshold);
						break;

					case 2:
						isShow = (value <= threshold);
						break;

					case 3:
						isShow = (value == threshold);
						break;

					case 4:
						isShow = (value > threshold);
						break;

					case 5:
						isShow = (value >= threshold);
						break;
					}
				}
			}

			paramsMap.put("isShow", isShow ? "1" : "0");
			paramsList.add(paramsMap);
		}
		
		sceneUserCountStatDao.updateUserCount(paramsList);
		
		LOG.info("spring afterPropertiesSet  end.  EndTime:" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
	}

	// THRESHOLD
	
	
	@Override
	public void destroy() throws Exception {
		LOG.info("Spring destroy");
	}

	public SceneUserCountStatDAO getSceneUserCountStatDao() {
		return sceneUserCountStatDao;
	}

	public void setSceneUserCountStatDao(
			SceneUserCountStatDAO sceneUserCountStatDao) {
		this.sceneUserCountStatDao = sceneUserCountStatDao;
	}

	public String getCorbaUrl() {
		return corbaUrl;
	}

	public void setCorbaUrl(String corbaUrl) {
		this.corbaUrl = corbaUrl;
	}

	public CorbaMsgSupport getPmCorba() {
		return pmCorba;
	}

	public void setPmCorba(CorbaMsgSupport pmCorba) {
		this.pmCorba = pmCorba;
	}
}