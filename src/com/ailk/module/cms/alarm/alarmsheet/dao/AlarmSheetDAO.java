
package com.ailk.module.cms.alarm.alarmsheet.dao;

import java.util.List;
import java.util.Map;

/**
 * �澯����DAO
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
	 * ��ȡ���ڵĹ������б�
	 * 
	 * @param param
	 *            �������������
	 * @return
	 */
	List<Map> getExistSheetTabList(Map param);

	/**
	 * ����������
	 * 
	 * @param param
	 *            �������������
	 * @return
	 */
	int createSheetTabList(Map param);

	/**
	 * ͳ�Ƹ澯�Զ��ɵ�����
	 * 
	 * @param param
	 *            ����
	 * @return
	 */
	int statAlarmAutoSendSheetData(Map param);
}