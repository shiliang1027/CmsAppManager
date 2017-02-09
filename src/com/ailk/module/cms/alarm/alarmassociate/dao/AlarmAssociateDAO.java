
package com.ailk.module.cms.alarm.alarmassociate.dao;

import java.util.List;
import java.util.Map;

/**
 * @author bilei5 (69064)
 * @version 1.0
 * @since 2012-7-4
 * @category com.ailk.module.cms.alarm.alarmassociate.dao.mapper.oracle
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 */
public interface AlarmAssociateDAO
{

	/**
	 * ��ȡ���ι����еĴθ澯��
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getSubAlarmNum(Map param);

	/**
	 * ���������е�ԭʼ�澯��
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getOriginalAlarmNum(Map param);

	/**
	 * ����׷���漰�ĸ澯��
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getSheetAlarmNum(Map param);

	/**
	 * ���������е������澯��
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getDeriveAlarmNum(Map param);

	/**
	 * ���������漰�澯��
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getRelationAlarmNum(Map param);
	
	/**
	 * ����רҵ�ڵĸ澯��
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getRelationInnerAlarmNum(Map param);

	/**
	 * 1~3���澯ԭʼ����������������澯��
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getTotalAlarmNum(Map param);
	
	/**
	 * �������ָ��ͳ����Ϣ
	 * 
	 * @param param
	 */
	void saveRelationAnalysisStat(List<Map> param);

	/**
	 * ��ȡ���ڵĸ澯�������б�
	 * 
	 * @param param
	 *            �澯�������������
	 * @return
	 */
	List<Map> getExistAssociateTabList(Map param);

	/**
	 * �����澯������
	 * 
	 * @param param
	 *            �澯�������������
	 * @return
	 */
	int createAssociateTabList(Map param);
}