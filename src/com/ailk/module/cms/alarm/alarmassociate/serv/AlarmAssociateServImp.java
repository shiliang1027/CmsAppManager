
package com.ailk.module.cms.alarm.alarmassociate.serv;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ailk.module.cms.alarm.alarmassociate.dao.AlarmAssociateDAO;
import com.ailk.module.cms.alarm.alarmassociate.util.AssociateUtil;
import com.ailk.module.cms.system.basesupport.BaseSupportServ;
import com.linkage.system.utils.DateTimeUtil;
import com.linkage.system.utils.database.DBUtil;

/**
 * �澯����servʵ����
 * 
 * @author mengqiang (65453)
 * @version 1.0
 * @since 2012-6-29
 * @category com.ailk.module.cms.alarm.alarmsheet.serv
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 */
public class AlarmAssociateServImp implements BaseSupportServ
{

	/**
	 * log4j��־��¼��
	 */
	private static final Logger LOG = Logger.getLogger(AlarmAssociateServImp.class);
	/**
	 * �澯����DAO
	 */
	private AlarmAssociateDAO alarmAssociateDao = null;
	/**
	 * �������ݿ�ʼʱ��
	 */
	private String addStartTime = null;
	/**
	 * �������ݿ�ʼʱ��
	 */
	private String addEndTime = null;
	/**
	 * �Ƿ񲹳����� <li>0:���ò��� <li>1:��������(ͬ�±������ÿ�ʼʱ��ͽ���ʱ��)
	 */
	private int isAddData = 0;
	/**
	 * ��ͳ�ƣ�ͳ����ǰ�������
	 */
	private int ds = -1;

	public void afterPropertiesSet() throws Exception
	{
		LOG.info("spring afterPropertiesSet");
		if (isAddData == 1)
		{
			// ��ʼʱ��
			DateTimeUtil dtu = new DateTimeUtil(addStartTime);
			long startTime = dtu.getLongTime();
			// ����ʱ��
			dtu = new DateTimeUtil(addEndTime);
			long endTime = dtu.getLongTime();
			for (long time = startTime; time <= endTime; time += 86400)
			{
				LOG.info("�����䡿ͳ�ƽ�������:" + time);
				startStatData(time, (time + 86399));
			}
		}
		else
		{
			LOG.info("��������ͳ����������");
			long startTime = getStartTime();
			long endTime = getEndTime();
			startStatData(startTime, endTime);
		}
	}

	// ͳ������
	public void startStatData(long startTime, long endTime)
	{
		HashMap param = new HashMap();
		param.put("startTime", String.valueOf(startTime));
		param.put("endTime", String.valueOf(endTime));
		param.put("alarmHis", getHisAlarmTabName(startTime));
		param.put("relationHis", getHisRelationTabName(startTime));
		
		// �õ�6����רҵIDΪkey ͳ����Ϊvalue��Map
		LOG.info("��ʼͳ�����ι����еĴθ澯��+++++++++++++++");
		Map<String, String> subAlarmMap = listToMap(alarmAssociateDao.getSubAlarmNum(param));
		LOG.info("��ʼͳ�����������е�ԭʼ�澯��+++++++++++++++");
		Map<String, String> originalAlarmMap = listToMap(alarmAssociateDao.getOriginalAlarmNum(param));
		LOG.info("��ʼͳ�ƹ���׷���漰�ĸ澯��+++++++++++++++");
		Map<String, String> sheetAlarmMap = listToMap(alarmAssociateDao.getSheetAlarmNum(param));
		LOG.info("��ʼͳ�����������е������澯��+++++++++++++++");
		Map<String, String> deriveAlarmMap = listToMap(alarmAssociateDao.getDeriveAlarmNum(param));
		LOG.info("��ʼͳ�ƹ��������漰�澯��+++++++++++++++");
		Map<String, String> relationAlarmMap = listToMap(alarmAssociateDao.getRelationAlarmNum(param));
		LOG.info("��ʼͳ��1~3���澯ԭʼ����������������澯��+++++++++++++++");
		Map<String, String> totalAlarmMap = listToMap(alarmAssociateDao.getTotalAlarmNum(param));
		LOG.info("��ʼͳ�ƹ���רҵ�ڵĸ澯��+++++++++++++++");
		Map<String, String> innerAlarmMap = listToMap(alarmAssociateDao.getRelationInnerAlarmNum(param));
		// ����list��Map��ֵ���������ݿ�
		String tabName = getRelationAnalysisTabName(startTime);
		Iterator<String> itr = AssociateUtil.specMap.keySet().iterator();
		List<Map> dataList = new ArrayList<Map>();
		while (itr.hasNext())
		{
			LOG.info("��ʼ��������+++++++++++++++");
			String specId = itr.next();
			String specName = AssociateUtil.specMap.get(specId);
			Map map = new HashMap();
			map.put("specialty", specId);
			map.put("name", specName);
			
			map.put("subAlarmNum", subAlarmMap.get(specId) == null ? "0" : subAlarmMap.get(specId));
			map.put("originalAlarmNum", originalAlarmMap.get(specId) == null ? "0" : originalAlarmMap.get(specId));
			map.put("sheetAlarmNum", sheetAlarmMap.get(specId) == null ? "0" : sheetAlarmMap.get(specId));
			map.put("deriveAlarmNum", deriveAlarmMap.get(specId) == null ? "0" : deriveAlarmMap.get(specId));
			map.put("relationAlarmNum", sumRelationTotal(relationAlarmMap.get(specId), innerAlarmMap.get(specId)));
			map.put("totalAlarmNum", totalAlarmMap.get(specId) == null ? "0" : totalAlarmMap.get(specId));
			
			map.put("overallCompEffic", getOverallCompEffic(map));
			map.put("overallCompRatio", getOverallCompRatio(map));
			map.put("gatherTime", String.valueOf(startTime));
			map.put("relationanalysis", tabName);
			dataList.add(map);
		}
		// ��ʼ�������
		if (!dataList.isEmpty())
		{
			LOG.info("��ʼ�������+++++++++++++++");
			alarmAssociateDao.saveRelationAnalysisStat(dataList);
			dataList.clear();
		}
	}
	
	// �����ͳ���ܺ�
	private String sumRelationTotal(String relNum, String innerNum)
	{
		int total = 0;
		if(relNum != null)
		{
			total += Integer.parseInt(relNum);
		}
		if(innerNum != null)
		{
			total += Integer.parseInt(innerNum);
		}
		return String.valueOf(total);
	}
	
	private String getOverallCompEffic(Map<String, String> map)
	{
		// ������������ѹ��Ч�� =
		// �����ι����еĴθ澯�������������е�ԭʼ�澯��+����׷���漰�ĸ澯��-���������е������澯����/�����������漰�澯��+����׷���漰�ĸ澯����
		float overallCompEffic = 1l;
		if(Float.valueOf(map.get("relationAlarmNum")) + Float.valueOf(map
				.get("sheetAlarmNum")) != 0) {
			overallCompEffic = (Float.valueOf(map.get("subAlarmNum"))
					+ Float.valueOf(map.get("originalAlarmNum"))
					+ Float.valueOf(map.get("sheetAlarmNum")) - Float.valueOf(map
					.get("deriveAlarmNum")))
					/ (Float.valueOf(map.get("relationAlarmNum")) + Float.valueOf(map
							.get("sheetAlarmNum")));
		}
		return String.valueOf(overallCompEffic);
	}

	private String getOverallCompRatio(Map<String, String> map)
	{
		// �澯����ѹ���ȣ������� =
		// �����ι����еĴθ澯�������������е�ԭʼ�澯��+����׷���漰�ĸ澯��-���������е������澯����/1~3���澯ԭʼ����������������澯��
		float overallCompRatio = 1l;
		if(!"0".equals(map.get("totalAlarmNum")))  {
			overallCompRatio = (Float.valueOf(map.get("subAlarmNum"))
					+ Float.valueOf(map.get("originalAlarmNum"))
					+ Float.valueOf(map.get("sheetAlarmNum")) - Float.valueOf(map
					.get("deriveAlarmNum")))
					/ Float.valueOf(map.get("totalAlarmNum"));
		}
		return String.valueOf(overallCompRatio);
	}

	private Map<String, String> listToMap(List<Map> list)
	{
		Map<String, String> resultMap = new HashMap<String, String>();
		int total = 0;// 2G��TD�ϲ��������
		String specId = null;
		for (Map map : list)
		{
			specId = String.valueOf(map.get("specialty"));
			if ("5".equals(specId) || "6".equals(specId))
			{
				total += objToInt(map.get("sl"));
			}
			else
			{
				resultMap.put(specId, String.valueOf(map.get("sl")));
			}
		}
		// �����������
		resultMap.put("56", String.valueOf(total));
		return resultMap;
	}

	// ����ת��Ϊ����
	private int objToInt(Object obj)
	{
		return obj == null ? 0 : Integer.parseInt(String.valueOf(obj));
	}
	
	private String getHisAlarmTabName(long startTime)
	{
		List<String> tabList = DBUtil.createTableNames(startTime * 1000L,
				startTime * 1000L, Calendar.MONTH, 1, "ta_historyalarm_", "yyyy_MM");
		return tabList.get(0);
	}

	private String getHisRelationTabName(long startTime)
	{
		List<String> tabList = DBUtil.createTableNames(startTime * 1000L,
				startTime * 1000L, Calendar.MONTH, 1, "ta_hisalarm_relation_", "yyyy_MM");
		return tabList.get(0);
	}

	private String getRelationAnalysisTabName(long startTime)
	{
		List<String> tabList = DBUtil.createTableNames(startTime * 1000L,
				startTime * 1000L, Calendar.YEAR, 1, "tt_relation_analysis_stat_",
				"yyyy");
		Map param = new HashMap();
		param.put("alarmHis", tabList.get(0));
		LOG.info("����ʼ��ѯ����ѯ�澯����ͳ����ʷ�����:" + param);
		List<Map> hisTabList = alarmAssociateDao.getExistAssociateTabList(param);
		LOG.info("��������ѯ����ѯ�澯����ͳ����ʷ������:" + hisTabList);
		// �������ͳ����ʷ������ֱ�Ӵ���
		if (hisTabList.isEmpty())
		{
			LOG.info("����ʼ�����������澯����ͳ����ʷ�����:" + param);
			int ctRes = alarmAssociateDao.createAssociateTabList(param);
			LOG.info("�����������������澯����ͳ����ʷ������:" + (ctRes == 0 ? "�ɹ�" : "ʧ��"));
		}
		return tabList.get(0);
	}

	// ��ȡ��ʼʱ��
	private long getStartTime()
	{
		DateTimeUtil dtu = new DateTimeUtil();
		dtu.getNextDate(1 + ds);
		dtu = new DateTimeUtil(dtu.getDate());
		return dtu.getLongTime() - 86400;
	}

	// ��ȡ����ʱ��
	private long getEndTime()
	{
		DateTimeUtil dtu = new DateTimeUtil();
		dtu.getNextDate(1 + ds);
		dtu = new DateTimeUtil(dtu.getDate());
		return dtu.getLongTime() - 1;
	}

	public void destroy() throws Exception
	{
		LOG.info("spring destroy");
	}

	public void setAlarmAssociateDao(AlarmAssociateDAO alarmAssociateDao)
	{
		this.alarmAssociateDao = alarmAssociateDao;
	}

	public void setAddStartTime(String addStartTime)
	{
		this.addStartTime = addStartTime;
	}

	public void setAddEndTime(String addEndTime)
	{
		this.addEndTime = addEndTime;
	}

	public void setIsAddData(int isAddData)
	{
		this.isAddData = isAddData;
	}
}