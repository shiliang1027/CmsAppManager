
package com.ailk.module.cms.alarm.indentify.dao;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author yuanj(65478) Tel:18652040107
 * @version 1.0
 * @since 2012-7-3 下午8:56:19
 * @category com.ailk.module.cms.alarm.indentify.dao
 * @copyright 亚信联创 网管产品部
 */
public interface AlarmStatDAO
{

	/**
	 * 创建表
	 * 
	 * @param m
	 */
	void createTable(Map<String, Object> map) throws SQLException;

	/**
	 * 判断表是否存在
	 * 
	 * @param tn
	 * @return
	 */
	boolean judgeTableExists(Map<String, Object> map);

	/**
	 * 清理表数据
	 * 
	 * @param tn
	 * @throws SQLException
	 */
	void truncateTable(Map<String, Object> map) throws SQLException;

	/**
	 * 查询统计数据
	 * 
	 * @param m
	 */
	void statAlarm(Map<String, Object> m);
}
