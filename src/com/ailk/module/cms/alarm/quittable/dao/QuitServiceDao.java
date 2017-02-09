
package com.ailk.module.cms.alarm.quittable.dao;

import java.util.List;
import java.util.Map;

/**
 * @author gaoqc(69151) Tel:18652058796
 * @version 1.0
 * @since 2012-9-24 下午7:32:54
 * @category com.ailk.module.cms.alarm.quittable.dao
 * @copyright 南京联创科技 网管科技部
 */
public interface QuitServiceDao
{

	/**
	 * 取得已经存在的数据记录数
	 * 
	 * @return
	 */
	int getExistData();

	/**
	 * 向统计表中插入统计数据
	 * 
	 * @param param
	 */
	void insertQuitTblInfo(List<Map> param);

	/**
	 * 更新统计表中的数据
	 * 
	 * @param param
	 */
	void updateQuitTblInfo(List<Map> param);

	/**
	 * 取得13个地市列表
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getCity();

	/**
	 * 获得对应时间内的每个地市的GSM基站宏站退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getBtsHzTfsCount(long startTime, long endTime);

	/**
	 * 获得每个地市的所有的GSM基站宏站数(网元)
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getBtsHzCount();

	/**
	 * 获得对应时间内的每个地市的GSM基站微蜂窝退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getBtsWfwTfsCount(long startTime, long endTime);

	/**
	 * 获得每个地市的所有的GSM基站微蜂窝数(网元数)
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getBtsWfwCount();

	/**
	 * 获得对应时间内的每个地市的GSM小区退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getCellTfsCount(long startTime, long endTime);

	/**
	 * 获得每个地市的所有的GSM小区数(网元数)
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getCellCount();

	/**
	 * 获得对应时间内的每个地市的GSM VIP基站退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getVipBtsTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的GSM小区 载频退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getCellZpTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的GSM 基站人工闭塞退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getBtsRgbsTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的GSM 小区人工闭塞退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getCellRgbsTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的TD基站宏站退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getNodebHzTfsCount(long startTime, long endTime);

	/**
	 * 获得每个地市的TD基站宏站数(网元数)
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getNodebHzCount();

	/**
	 * 获得对应时间内的每个地市的TD基站微蜂窝退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getNodebWfwTfsCount(long startTime, long endTime);

	/**
	 * 获得每个地市的TD基站微蜂窝数(网元数)
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getNodebWfwCount();

	/**
	 * 获得对应时间内的每个地市的TD小区退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getUcellTfsCount(long startTime, long endTime);

	/**
	 * 获得每个地市的TD小区数(网元数)
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getUcellCount();

	/**
	 * 获得对应时间内的每个地市的TD VIP基站退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getVipNodebTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的TD小区载频退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getUcellZpTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的TD基站人工闭塞退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getNodebRgbsTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的TD小区人工闭塞退服数(告警数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getUcellRgbsTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的停电退服数(网元数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getPowerFailureTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的电池组总电压过低退服数(网元数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getVoltageLowTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的一次下电退服数(网元数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getOnceDownPowerTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的水浸退服数(网元数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getWaterSoakTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的烟感退服数(网元数)
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getSmogTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的温度过高退服数(网元数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getTemperatureHighTfsCount(long startTime, long endTime);

	/**
	 * 获得对应时间内的每个地市的温度超高退服数(网元数)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getTemperatureExtraHighTfsCount(long startTime, long endTime);
}
