
package com.ailk.module.cms.alarm.alarmsheet.dao;

import java.util.List;
import java.util.Map;

/**
 * 告警工单DAO
 * 
 * @author mengqiang (65453)
 * @version 1.0
 * @since 2012-6-29
 * @category com.ailk.module.cms.alarm.alarmsheet.dao
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 */
public interface AlarmSheetDAO
{

	/**
	 * 获取存在的工单表列表
	 * 
	 * @param param
	 *            工单表表名参数
	 * @return
	 */
	List<Map> getExistSheetTabList(Map param);

	/**
	 * 创建工单表
	 * 
	 * @param param
	 *            工单表表名参数
	 * @return
	 */
	int createSheetTabList(Map param);

	/**
	 * 统计告警自动派单数据
	 * 
	 * @param param
	 *            参数
	 * @return
	 */
	int statAlarmAutoSendSheetData(Map param);
}