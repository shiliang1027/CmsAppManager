
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
 * 告警工单serv实现类
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
	 * log4j日志记录器
	 */
	private static final Logger LOG = Logger.getLogger(AlarmAssociateServImp.class);
	/**
	 * 告警工单DAO
	 */
	private AlarmAssociateDAO alarmAssociateDao = null;
	/**
	 * 补充数据开始时间
	 */
	private String addStartTime = null;
	/**
	 * 补充数据开始时间
	 */
	private String addEndTime = null;
	/**
	 * 是否补充数据 <li>0:不用补充 <li>1:补充数据(同事必须配置开始时间和结束时间)
	 */
	private int isAddData = 0;
	/**
	 * 天统计，统计向前多少天的
	 */
	private int ds = -1;

	public void afterPropertiesSet() throws Exception
	{
		LOG.info("spring afterPropertiesSet");
		if (isAddData == 1)
		{
			// 开始时间
			DateTimeUtil dtu = new DateTimeUtil(addStartTime);
			long startTime = dtu.getLongTime();
			// 结束时间
			dtu = new DateTimeUtil(addEndTime);
			long endTime = dtu.getLongTime();
			for (long time = startTime; time <= endTime; time += 86400)
			{
				LOG.info("【补充】统计今天数据:" + time);
				startStatData(time, (time + 86399));
			}
		}
		else
		{
			LOG.info("【正常】统计昨天数据");
			long startTime = getStartTime();
			long endTime = getEndTime();
			startStatData(startTime, endTime);
		}
	}

	// 统计数据
	public void startStatData(long startTime, long endTime)
	{
		HashMap param = new HashMap();
		param.put("startTime", String.valueOf(startTime));
		param.put("endTime", String.valueOf(endTime));
		param.put("alarmHis", getHisAlarmTabName(startTime));
		param.put("relationHis", getHisRelationTabName(startTime));
		
		// 得到6个以专业ID为key 统计数为value的Map
		LOG.info("开始统计主次关联中的次告警数+++++++++++++++");
		Map<String, String> subAlarmMap = listToMap(alarmAssociateDao.getSubAlarmNum(param));
		LOG.info("开始统计衍生关联中的原始告警数+++++++++++++++");
		Map<String, String> originalAlarmMap = listToMap(alarmAssociateDao.getOriginalAlarmNum(param));
		LOG.info("开始统计工单追加涉及的告警数+++++++++++++++");
		Map<String, String> sheetAlarmMap = listToMap(alarmAssociateDao.getSheetAlarmNum(param));
		LOG.info("开始统计衍生关联中的衍生告警数+++++++++++++++");
		Map<String, String> deriveAlarmMap = listToMap(alarmAssociateDao.getDeriveAlarmNum(param));
		LOG.info("开始统计关联分析涉及告警数+++++++++++++++");
		Map<String, String> relationAlarmMap = listToMap(alarmAssociateDao.getRelationAlarmNum(param));
		LOG.info("开始统计1~3级告警原始入库总数（含衍生告警）+++++++++++++++");
		Map<String, String> totalAlarmMap = listToMap(alarmAssociateDao.getTotalAlarmNum(param));
		LOG.info("开始统计关联专业内的告警数+++++++++++++++");
		Map<String, String> innerAlarmMap = listToMap(alarmAssociateDao.getRelationInnerAlarmNum(param));
		// 遍历list给Map赋值并存入数据库
		String tabName = getRelationAnalysisTabName(startTime);
		Iterator<String> itr = AssociateUtil.specMap.keySet().iterator();
		List<Map> dataList = new ArrayList<Map>();
		while (itr.hasNext())
		{
			LOG.info("开始处理数据+++++++++++++++");
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
		// 开始批量入库
		if (!dataList.isEmpty())
		{
			LOG.info("开始数据入库+++++++++++++++");
			alarmAssociateDao.saveRelationAnalysisStat(dataList);
			dataList.clear();
		}
	}
	
	// 求关联统计总和
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
		// 关联分析整体压缩效率 =
		// （主次关联中的次告警数＋衍生关联中的原始告警数+工单追加涉及的告警数-衍生关联中的衍生告警数）/（关联分析涉及告警数+工单追加涉及的告警数）
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
		// 告警整体压缩比（总量） =
		// （主次关联中的次告警数＋衍生关联中的原始告警数+工单追加涉及的告警数-衍生关联中的衍生告警数）/1~3级告警原始入库总数（含衍生告警）
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
		int total = 0;// 2G和TD合并后的总数
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
		// 添加无线数据
		resultMap.put("56", String.valueOf(total));
		return resultMap;
	}

	// 对象转换为整形
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
		LOG.info("【开始查询】查询告警关联统计历史表参数:" + param);
		List<Map> hisTabList = alarmAssociateDao.getExistAssociateTabList(param);
		LOG.info("【结束查询】查询告警关联统计历史表数据:" + hisTabList);
		// 如果工单统计历史表不存在直接创建
		if (hisTabList.isEmpty())
		{
			LOG.info("【开始创建】创建告警关联统计历史表参数:" + param);
			int ctRes = alarmAssociateDao.createAssociateTabList(param);
			LOG.info("【结束创建】创建告警关联统计历史表数据:" + (ctRes == 0 ? "成功" : "失败"));
		}
		return tabList.get(0);
	}

	// 获取开始时间
	private long getStartTime()
	{
		DateTimeUtil dtu = new DateTimeUtil();
		dtu.getNextDate(1 + ds);
		dtu = new DateTimeUtil(dtu.getDate());
		return dtu.getLongTime() - 86400;
	}

	// 获取结束时间
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