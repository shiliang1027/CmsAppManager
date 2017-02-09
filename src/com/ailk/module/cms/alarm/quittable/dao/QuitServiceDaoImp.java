
package com.ailk.module.cms.alarm.quittable.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ailk.module.cms.alarm.quittable.utils.QuitServiceUtil;
import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * @author gaoqc(69151) Tel:18652058796
 * @version 1.0
 * @since 2012-9-25 上午10:43:17
 * @category com.ailk.module.cms.alarm.quittable.dao
 * @copyright 南京联创科技 网管科技部
 */
public class QuitServiceDaoImp extends JPABaseDAO implements QuitServiceDao
{

	/**
	 * 日志记录
	 */
	private static final Logger LOG = Logger.getLogger(QuitServiceDaoImp.class);
	/**
	 * 命名空间
	 */
	private static final String SQL_PREFIX = QuitServiceDaoImp.class.getName() + ".";

	@Override
	public int getExistData()
	{
		LOG.info("查询基站退服统计表中已有记录数");
		return (Integer) getSqlSessionTemplate().selectOne(SQL_PREFIX + "getExistData");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void insertQuitTblInfo(List<Map> param)
	{
		LOG.info("开始向统计表中插入数据-insertQuitTblInfo" + param);
		getSqlSessionTemplate().batchInsert(SQL_PREFIX + "insertQuitTblInfo", param);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updateQuitTblInfo(List<Map> param)
	{
		LOG.info("开始向统计表中更新数据-updateQuitTblInfo" + param);
		getSqlSessionTemplate().batchUpdate(SQL_PREFIX + "updateQuitTblInfo", param);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getCity()
	{
		LOG.info("查询地市信息getCity:");
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getCity");
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getBtsHzTfsCount(long startTime, long endTime)
	{
		LOG.info("查询GSM基站宏站退服数:getBtsHzTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("hz", QuitServiceUtil.HZ);
		LOG.info("打印getBtsHzTfsCount" + paramsMap);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getBtsHzTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getBtsHzCount()
	{
		LOG.info("查询GSM基站宏站数:getBtsHzCount");
		Map paramsMap = new HashMap();
		paramsMap.put("hz", QuitServiceUtil.HZ);
		return getSqlSessionTemplate()
				.selectList(SQL_PREFIX + "getBtsHzCount", paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getBtsWfwTfsCount(long startTime, long endTime)
	{
		LOG.info("查询GSM基站微蜂窝退服数:getBtsWfwTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("wfw", QuitServiceUtil.WFW);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getBtsWfwTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getBtsWfwCount()
	{
		LOG.info("查询GSM基站微蜂窝数:getBtsWfwCount");
		Map paramsMap = new HashMap();
		paramsMap.put("wfw", QuitServiceUtil.WFW);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getBtsWfwCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getCellTfsCount(long startTime, long endTime)
	{
		LOG.info("查询GSM小区退服数:getCellTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getCellTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getCellCount()
	{
		LOG.info("查询GSM小区数:getCellCount");
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getCellCount");
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getVipBtsTfsCount(long startTime, long endTime)
	{
		LOG.info("查询GSM VIP基站退服数:getVipBtsTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getVipBtsTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getCellZpTfsCount(long startTime, long endTime)
	{
		LOG.info("查询GSM 小区载频退服数:getCellZpTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getCellZpTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getBtsRgbsTfsCount(long startTime, long endTime)
	{
		LOG.info("查询GSM 基站人工闭塞退服数:getBtsRgbsTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("jzexception", QuitServiceUtil.JZEXCEPTION);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getBtsRgbsTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getCellRgbsTfsCount(long startTime, long endTime)
	{
		LOG.info("查询GSM 小区人工闭塞退服数:getCellRgbsTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("xqexception", QuitServiceUtil.XQEXCEPTION);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getCellRgbsTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getNodebHzTfsCount(long startTime, long endTime)
	{
		LOG.info("查询TD基站小区退服数:getNodebHzTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("hz", QuitServiceUtil.HZ);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getNodebHzTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getNodebHzCount()
	{
		LOG.info("查询TD基站宏站退服数:getNodebHzCount");
		Map paramsMap = new HashMap();
		paramsMap.put("hz", QuitServiceUtil.HZ);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getNodebHzCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getNodebWfwTfsCount(long startTime, long endTime)
	{
		LOG.info("查询TD基站微蜂窝退服数:getNodebWfwTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("wfw", QuitServiceUtil.WFW);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getNodebWfwTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getNodebWfwCount()
	{
		LOG.info("查询TD基站微蜂窝数:getNodebWfwCount");
		Map paramsMap = new HashMap();
		paramsMap.put("wfw", QuitServiceUtil.WFW);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getNodebWfwCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getUcellTfsCount(long startTime, long endTime)
	{
		LOG.info("查询TD小区退服数:getUcellTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getUcellTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getUcellCount()
	{
		LOG.info("查询TD小区数:getUcellCount");
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getUcellCount");
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getVipNodebTfsCount(long startTime, long endTime)
	{
		LOG.info("查询TD VIP基站退服数:getVipNodebTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getVipNodebTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getUcellZpTfsCount(long startTime, long endTime)
	{
		LOG.info("查询TD 小区载频退服数:getUcellZpTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getUcellZpTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getNodebRgbsTfsCount(long startTime, long endTime)
	{
		LOG.info("查询TD 基站人工闭塞退服数:getNodebRgbsTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("jzexception", QuitServiceUtil.JZEXCEPTION);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getNodebRgbsTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getUcellRgbsTfsCount(long startTime, long endTime)
	{
		LOG.info("查询TD 小区人工闭塞退服数:getUcellRgbsTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("xqexception", QuitServiceUtil.XQEXCEPTION);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getUcellRgbsTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getPowerFailureTfsCount(long startTime, long endTime)
	{
		LOG.info("查询停电退服数:getPowerFailureTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("ringrulename", QuitServiceUtil.RINGRULENAME);
		paramsMap.put("power_failure", QuitServiceUtil.POWER_FAILURE_CONDITION);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getPowerFailureTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getVoltageLowTfsCount(long startTime, long endTime)
	{
		LOG.info("查询低压退服数:getVoltageLowTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("ringrulename", QuitServiceUtil.RINGRULENAME);
		paramsMap.put("voltage_low", QuitServiceUtil.VOLTAGE_LOW_CONDITION);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getVoltageLowTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getOnceDownPowerTfsCount(long startTime, long endTime)
	{
		LOG.info("查询一次下电退服数:getOnceDownPowerTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("ringrulename", QuitServiceUtil.RINGRULENAME);
		paramsMap.put("once_down_power", QuitServiceUtil.ONCE_DOWN_POWER_CONDITION);
		return getSqlSessionTemplate().selectList(
				SQL_PREFIX + "getOnceDownPowerTfsCount", paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getWaterSoakTfsCount(long startTime, long endTime)
	{
		LOG.info("查询水浸退服数:getWaterSoakTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("ringrulename", QuitServiceUtil.RINGRULENAME);
		paramsMap.put("water_soak", QuitServiceUtil.WATER_SOAK_CONDITION);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getWaterSoakTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getSmogTfsCount(long startTime, long endTime)
	{
		LOG.info("查询烟雾退服数:getSmogTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("ringrulename", QuitServiceUtil.RINGRULENAME);
		paramsMap.put("smog", QuitServiceUtil.SMOG_CONDITION);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getSmogTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getTemperatureHighTfsCount(long startTime, long endTime)
	{
		LOG.info("查询温度过高退服数:getTemperatureHighTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("ringrulename", QuitServiceUtil.RINGRULENAME);
		paramsMap.put("temperature_high", QuitServiceUtil.TEMPERATURE_HIGH_CONDITION);
		return getSqlSessionTemplate().selectList(
				SQL_PREFIX + "getTemperatureHighTfsCount", paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getTemperatureExtraHighTfsCount(long startTime, long endTime)
	{
		LOG.info("查询温度过高退服数:getTemperatureExtraHighTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("ringrulename", QuitServiceUtil.RINGRULENAME);
		paramsMap.put("temperature_extra_high",
				QuitServiceUtil.TEMPERATURE_EXTRA_HIGH_CONDITION);
		return getSqlSessionTemplate().selectList(
				SQL_PREFIX + "getTemperatureExtraHighTfsCount", paramsMap);
	}
}
