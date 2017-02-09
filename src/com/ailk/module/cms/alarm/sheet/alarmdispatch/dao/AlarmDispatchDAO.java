
package com.ailk.module.cms.alarm.sheet.alarmdispatch.dao;

import java.util.List;
import java.util.Map;

public interface AlarmDispatchDAO
{

	/**
	 * ��ȡ���ڵĸ澯�������б�
	 * 
	 * @param param
	 *            �澯�������������
	 * @return
	 */
	List<Map> getExistDispatchTabList(Map param);

	/**
	 * �������ɵ��澯��
	 * 
	 * @param param
	 *            �澯�������������
	 * @return
	 */
	int createDispatchTabList(Map param);

	/**
	 * ͳ���ɵ���������
	 * 
	 * @param param
	 * @return
	 */
	void saveDispatchList(Map param);
	
	/**
	 * �����ɵ���ʱ����
	 * 
	 * @param param
	 * @return
	 */
	void updateDispatchList(Map param);
}