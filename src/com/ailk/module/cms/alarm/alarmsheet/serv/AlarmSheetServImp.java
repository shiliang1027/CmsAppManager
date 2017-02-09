
package com.ailk.module.cms.alarm.alarmsheet.serv;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ailk.module.cms.alarm.alarmsheet.dao.AlarmSheetDAO;
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
public class AlarmSheetServImp implements BaseSupportServ
{

	/**
	 * log4j日志记录器
	 */
	private static final Logger LOG = Logger.getLogger(AlarmSheetServImp.class);
	/**
	 * 告警工单DAO
	 */
	private AlarmSheetDAO alarmSheetDao = null;
	/**
	 * 补充数据开始时间
	 */
	private String addStartTime = null;
	/**
	 * 补充数据开始时间
	 */
	private String addEndTime = null;
	/**
	 * 是否补充数据
	 * <li>0:不用补充
	 * <li>1:补充数据(同事必须配置开始时间和结束时间)
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

	// 开始统计数据
	private void startStatData(long startTime, long endTime)
	{
		String sheetTab = createSheetHisTab(startTime);
		String alarmTab = getHisAlarmTabName(startTime);
		// 1.统计工单数据参数
		Map param = new HashMap();
		param.put("sheetHis", sheetTab);
		param.put("alarmHis", alarmTab);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		// 2.统计工单数据
		LOG.info("【开始统计】统计工单统计历史表参数:" + param);
		int stRes = alarmSheetDao.statAlarmAutoSendSheetData(param);
		LOG.info("【结束统计】统计工单统计历史表数据:" + (stRes > 0 ? "成功" : "失败"));
	}

	// 获取告警历史表
	private String getHisAlarmTabName(long startTime)
	{
		List<String> tabList = DBUtil.createTableNames(startTime * 1000L,
				startTime * 1000L, Calendar.MONTH, 1, "ta_historyalarm_", "yyyy_MM");
		return tabList.get(0);
	}

	// 创建告警工单统计历史数据
	private String createSheetHisTab(long startTime)
	{
		List<String> tabList = DBUtil.createTableNames(startTime * 1000L,
				startTime * 1000L, Calendar.MONTH, 1, "tt_sheetstat_history_", "yyyy_MM");
		String alarmHisTab = tabList.get(0);
		// 查询工单统计历史表参数
		Map param = new HashMap();
		param.put("sheetHis", alarmHisTab);
		LOG.info("【开始查询】查询工单统计历史表参数:" + param);
		List<Map> hisTabList = alarmSheetDao.getExistSheetTabList(param);
		LOG.info("【结束查询】查询工单统计历史表数据:" + hisTabList);
		// 如果工单统计历史表不存在直接创建
		if (hisTabList.isEmpty())
		{
			LOG.info("【开始创建】创建工单统计历史表参数:" + param);
			int ctRes = alarmSheetDao.createSheetTabList(param);
			LOG.info("【结束创建】创建工单统计历史表数据:" + (ctRes == 0 ? "成功" : "失败"));
		}
		return alarmHisTab;
	}

	// 获取开始时间
	public long getStartTime()
	{
		DateTimeUtil dtu = new DateTimeUtil();
		dtu.getNextDate(1 + ds);
		dtu = new DateTimeUtil(dtu.getDate());
		return dtu.getLongTime() - 86400;
	}

	// 获取结束时间
	public long getEndTime()
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

	public void setAlarmSheetDao(AlarmSheetDAO alarmSheetDao)
	{
		this.alarmSheetDao = alarmSheetDao;
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