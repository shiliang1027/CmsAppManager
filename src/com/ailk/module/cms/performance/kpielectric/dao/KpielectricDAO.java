
package com.ailk.module.cms.performance.kpielectric.dao;

import java.util.List;
import java.util.Map;

/**
 * @author wangsh(69070) Tel:15051899778
 * @version 1.0
 * @since 2012-8-9 ����09:25:42
 * @category com.ailk.module.cms.performance.kpielectric.dao
 * @copyright �Ͼ������Ƽ� ���ܿƼ���
 */
public interface KpielectricDAO
{

	/**
	 * ��ȡ���ڵĸ澯�������б�
	 * 
	 * @param param
	 *            �澯�������������
	 * @return
	 */
	List<Map> getKpielectricTabList(Map param);

	/**
	 * ����������Ϣ����electric_card_info
	 * 
	 * @param param
	 * @return
	 */
	int createKpielectricTab(Map param);

	/**
	 * ����TCP���ӳɹ���ͳ��ZC_TCP_CON_STAT_yyyy_mm_dd
	 * 
	 * @param param
	 * @return
	 */
	int createTcpTabList(Map param);
	/**
	 * �����������ն��������ű�
	 * @param param
	 * @return
	 */
	int createDetailTabList(Map param);

	/**
	 * ��ѯ�����Ǳߵ�����
	 * 
	 * @param param
	 * @return
	 */
	int queryTcpList(Map param);
	/**
	 * ���������ͳ�Ʊ�
	 * @param param
	 * @return
	 */
	int queryunlineList(Map param);

	int queryKpielectricList(Map param);


	/**
	 * ����tp_m2m_kpibuilding_yyyy_MM 
	 * @param param
	 * @return
	 */
	int createBuildingTabList(Map param);
	
	int queryBuildingList(Map param);
}
