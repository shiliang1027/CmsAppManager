
package com.ailk.module.cms.alarm.monitor.table.dao;

/**
 * @author yuanj(65478) Tel:18652040107
 * @version 1.0
 * @since 2012-7-4 下午12:59:03
 * @category com.ailk.module.cms.alarm.monitor.table.dao
 * @copyright 亚信联创 网管产品部
 */
public interface MonitorTableExistsDAO
{

	/**
	 * 查询指定表是否存在
	 * 
	 * @param tn
	 * @return
	 */
	boolean queryExistsTable(String tn);
}
