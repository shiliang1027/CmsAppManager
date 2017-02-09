
package com.ailk.module.cms.alarm.indentify.dao;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author yuanj(65478) Tel:18652040107
 * @version 1.0
 * @since 2012-7-3 ����8:56:19
 * @category com.ailk.module.cms.alarm.indentify.dao
 * @copyright �������� ���ܲ�Ʒ��
 */
public interface AlarmStatDAO
{

	/**
	 * ������
	 * 
	 * @param m
	 */
	void createTable(Map<String, Object> map) throws SQLException;

	/**
	 * �жϱ��Ƿ����
	 * 
	 * @param tn
	 * @return
	 */
	boolean judgeTableExists(Map<String, Object> map);

	/**
	 * ���������
	 * 
	 * @param tn
	 * @throws SQLException
	 */
	void truncateTable(Map<String, Object> map) throws SQLException;

	/**
	 * ��ѯͳ������
	 * 
	 * @param m
	 */
	void statAlarm(Map<String, Object> m);
}
