
package com.ailk.module.cms.alarm.alarmassociate.dao;

import java.util.List;
import java.util.Map;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * @author bilei5 (69064)
 * @version 1.0
 * @since 2012-7-4
 * @category com.ailk.module.cms.alarm.alarmassociate.dao.mapper.oracle
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 */
public class AlarmAssociateDAOImp extends JPABaseDAO implements AlarmAssociateDAO
{

	/**
	 * sqlǰ׺
	 */
	private final String sqlPre = AlarmAssociateDAOImp.class.getName();

	public List<Map> getSubAlarmNum(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getSubAlarmNum", param);
	}

	public List<Map> getOriginalAlarmNum(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getOriginalAlarmNum", param);
	}

	public List<Map> getSheetAlarmNum(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getSheetAlarmNum", param);
	}

	public List<Map> getDeriveAlarmNum(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getDeriveAlarmNum", param);
	}

	public List<Map> getRelationAlarmNum(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getRelationAlarmNum", param);
	}
	
	public List<Map> getRelationInnerAlarmNum(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getRelationInnerAlarmNum", param);
	}

	public List<Map> getTotalAlarmNum(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getTotalAlarmNum", param);
	}

	public void saveRelationAnalysisStat(List<Map> param)
	{
		getSqlSessionTemplate().batchInsert(sqlPre + ".saveRelationAnalysisStat", param);
	}

	public List<Map> getExistAssociateTabList(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getExistAssociateTabList",
				param);
	}

	public int createAssociateTabList(Map param)
	{
		return getSqlSessionTemplate().update(sqlPre + ".createAssociateTabList", param);
	}
}