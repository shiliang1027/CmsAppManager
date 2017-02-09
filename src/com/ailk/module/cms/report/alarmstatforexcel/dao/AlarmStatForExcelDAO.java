package com.ailk.module.cms.report.alarmstatforexcel.dao;
/**
 * 该java的描述信息
 * @author shiliang Tel:18661205639
 * @version 1.0
 * @since 2014-5-27 下午4:53:17
 * @category com.ailk.module.cms.report.alarmstatforexcel.dao
 * @copyright 
 */
public interface AlarmStatForExcelDAO {

	public abstract void delTmp();

	public abstract void statSubTotal();

	public abstract void statSheet();

	public abstract void statProject();

	public abstract void statException();

	public abstract void updateAll();

}
