
package com.ailk.module.cms.alarm.alarmsheet.dao;

import java.util.List;
import java.util.Map;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * 告警工单DAO实现类
 * 
 * @author mengqiang (65453)
 * @version 1.0
 * @since 2012-6-29
 * @category com.ailk.module.cms.alarm.alarmsheet.dao
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 */
public class AlarmSheetDAOImp extends JPABaseDAO implements AlarmSheetDAO
{

	/**
	 * sql前缀
	 */
	private final String sqlPre = AlarmSheetDAOImp.class.getName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ailk.module.cms.alarm.alarmsheet.dao.AlarmSheetDAO#getExistSheetTabList(java.util.Map)
	 */
	public List<Map> getExistSheetTabList(Map param)
	{
		return getSqlSessionTemplate()
				.selectList(sqlPre + ".getExistSheetTabList", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ailk.module.cms.alarm.alarmsheet.dao.AlarmSheetDAO#createSheetTabList(java.util.Map)
	 */
	public int createSheetTabList(Map param)
	{
		return getSqlSessionTemplate().update(sqlPre + ".createSheetTabList", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ailk.module.cms.alarm.alarmsheet.dao.AlarmSheetDAO#statAlarmAutoSendSheetData(java.util.Map)
	 */
	public int statAlarmAutoSendSheetData(Map param)
	{
		return getSqlSessionTemplate().update(sqlPre + ".statAlarmAutoSendSheetData",
				param);
	}
}