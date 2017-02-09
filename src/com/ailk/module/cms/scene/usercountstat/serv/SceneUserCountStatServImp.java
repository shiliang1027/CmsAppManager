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
 * ����/����ҵ���ӿ�
 * 
 * @author loukp(No:69139 tel:15951882231)
 * @version 1.0.0
 * @category com.ailk.module.cms.scene.usercountstat.serv
 * @since 2012-08-12
 * @copyright �������� ���ܿ�����
 */
public class SceneUserCountStatServImp implements BaseSupportServ {

	/**
	 * log4j��־��¼��
	 */
	private static final Logger LOG = Logger
			.getLogger(SceneUserCountStatServImp.class);

	/**
	 * �û���ͳ��DAO
	 */
	private SceneUserCountStatDAO sceneUserCountStatDao = null;

	/**
	 * corba �ӿ�
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
		// ��ȡ��Ҫͳ�Ƶ��û���
		List<Map> trainGroupList = sceneUserCountStatDao.getTrainGroupList();
		LOG.info("��ȡ��·������վ�������� : " + trainGroupList);

		if (null == trainGroupList) {
			return;
		}

		// ����corba�ӿ�
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
			
			// �������Ԫ
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
			
			// ��װcorba����
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
			LOG.info("����corba ����:" + corbaUrl);
			LOG.info("����corba ����:" + corbaParams);
			subNodes = pmCorba.sendAndRecvMsg(corbaUrl, corbaParams);
			subNodesList = (List<Map>) subNodes.get("subNodes");
			LOG.info("����corba ���ؽ��:" + subNodesList);
		} catch (Exception e) {
			LOG.error("afterPropertiesSet() ����corba�ӿ��쳣", e);
		}

		/*
		 * ������Ԫʵʱ����corba�ӿڶ��� ���⣺/Pm/abims/realdata/scenemodata
		 * ���������mo_id,mo_type_id,kpi_id ��������:
		 * mo_id,mo_type_id,kpi_id,value,gather_time,warn_level,color
		 */
		// ������� 
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
		// ѭ��Map
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
		
		// �����Ҫ���µ�groupId
		StringBuffer sbGroupId = new StringBuffer("");
		for(Map.Entry<String, String> entry : groupIdValue.entrySet())    
		{    
			sbGroupId.append("'").append(entry.getKey()+"").append("'");
			sbGroupId.append(",");
		}
		
		List<Map> trainGroupListByGroupId = sceneUserCountStatDao
				.getTrainGroupListByGroupId(sbGroupId.substring(0, sbGroupId
						.length() - 1));

		// ��������
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