
package com.ailk.module.cms.alarm.monitor.table.dao;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * @author yuanj(65478) Tel:18652040107
 * @version 1.0
 * @since 2012-7-4 下午12:59:23
 * @category com.ailk.module.cms.alarm.monitor.table.dao
 * @copyright 亚信联创 网管产品部
 */
public class MonitorTableExistsDAOImp extends JPABaseDAO implements MonitorTableExistsDAO
{

	@Override
	public boolean queryExistsTable(String tn)
	{
		return 0 < Integer.class.cast(getSqlSessionTemplate().selectOne(
				nameSpace + "queryExistsTable", tn));
	}

	private String nameSpace = MonitorTableExistsDAOImp.class.getName() + ".";
}
