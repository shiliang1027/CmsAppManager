
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
 * @since 2012-9-25 ����10:43:17
 * @category com.ailk.module.cms.alarm.quittable.dao
 * @copyright �Ͼ������Ƽ� ���ܿƼ���
 */
public class QuitServiceDaoImp extends JPABaseDAO implements QuitServiceDao
{

	/**
	 * ��־��¼
	 */
	private static final Logger LOG = Logger.getLogger(QuitServiceDaoImp.class);
	/**
	 * �����ռ�
	 */
	private static final String SQL_PREFIX = QuitServiceDaoImp.class.getName() + ".";

	@Override
	public int getExistData()
	{
		LOG.info("��ѯ��վ�˷�ͳ�Ʊ������м�¼��");
		return (Integer) getSqlSessionTemplate().selectOne(SQL_PREFIX + "getExistData");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void insertQuitTblInfo(List<Map> param)
	{
		LOG.info("��ʼ��ͳ�Ʊ��в�������-insertQuitTblInfo" + param);
		getSqlSessionTemplate().batchInsert(SQL_PREFIX + "insertQuitTblInfo", param);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updateQuitTblInfo(List<Map> param)
	{
		LOG.info("��ʼ��ͳ�Ʊ��и�������-updateQuitTblInfo" + param);
		getSqlSessionTemplate().batchUpdate(SQL_PREFIX + "updateQuitTblInfo", param);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getCity()
	{
		LOG.info("��ѯ������ϢgetCity:");
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getCity");
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getBtsHzTfsCount(long startTime, long endTime)
	{
		LOG.info("��ѯGSM��վ��վ�˷���:getBtsHzTfsCount" + startTime + "|" + endTime);
		Map paramsMap = new HashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		paramsMap.put("hz", QuitServiceUtil.HZ);
		LOG.info("��ӡgetBtsHzTfsCount" + paramsMap);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getBtsHzTfsCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getBtsHzCount()
	{
		LOG.info("��ѯGSM��վ��վ��:getBtsHzCount");
		Map paramsMap = new HashMap();
		paramsMap.put("hz", QuitServiceUtil.HZ);
		return getSqlSessionTemplate()
				.selectList(SQL_PREFIX + "getBtsHzCount", paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getBtsWfwTfsCount(long startTime, long endTime)
	{
		LOG.info("��ѯGSM��վ΢�����˷���:getBtsWfwTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯGSM��վ΢������:getBtsWfwCount");
		Map paramsMap = new HashMap();
		paramsMap.put("wfw", QuitServiceUtil.WFW);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getBtsWfwCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getCellTfsCount(long startTime, long endTime)
	{
		LOG.info("��ѯGSMС���˷���:getCellTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯGSMС����:getCellCount");
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getCellCount");
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getVipBtsTfsCount(long startTime, long endTime)
	{
		LOG.info("��ѯGSM VIP��վ�˷���:getVipBtsTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯGSM С����Ƶ�˷���:getCellZpTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯGSM ��վ�˹������˷���:getBtsRgbsTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯGSM С���˹������˷���:getCellRgbsTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯTD��վС���˷���:getNodebHzTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯTD��վ��վ�˷���:getNodebHzCount");
		Map paramsMap = new HashMap();
		paramsMap.put("hz", QuitServiceUtil.HZ);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getNodebHzCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getNodebWfwTfsCount(long startTime, long endTime)
	{
		LOG.info("��ѯTD��վ΢�����˷���:getNodebWfwTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯTD��վ΢������:getNodebWfwCount");
		Map paramsMap = new HashMap();
		paramsMap.put("wfw", QuitServiceUtil.WFW);
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getNodebWfwCount",
				paramsMap);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getUcellTfsCount(long startTime, long endTime)
	{
		LOG.info("��ѯTDС���˷���:getUcellTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯTDС����:getUcellCount");
		return getSqlSessionTemplate().selectList(SQL_PREFIX + "getUcellCount");
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public List<Map> getVipNodebTfsCount(long startTime, long endTime)
	{
		LOG.info("��ѯTD VIP��վ�˷���:getVipNodebTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯTD С����Ƶ�˷���:getUcellZpTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯTD ��վ�˹������˷���:getNodebRgbsTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯTD С���˹������˷���:getUcellRgbsTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯͣ���˷���:getPowerFailureTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯ��ѹ�˷���:getVoltageLowTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯһ���µ��˷���:getOnceDownPowerTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯˮ���˷���:getWaterSoakTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯ�����˷���:getSmogTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯ�¶ȹ����˷���:getTemperatureHighTfsCount" + startTime + "|" + endTime);
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
		LOG.info("��ѯ�¶ȹ����˷���:getTemperatureExtraHighTfsCount" + startTime + "|" + endTime);
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
