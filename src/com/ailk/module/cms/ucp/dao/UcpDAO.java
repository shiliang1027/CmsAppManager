
package com.ailk.module.cms.ucp.dao;

import java.util.List;
import java.util.Map;

/**
 * Ucp���ݿ�ӿ�
 * 
 * @author mengqiang(65453)
 * @version 1.0
 * @since 2013-10-21 ����5:03:43
 * @category com.ailk.module.cms.ucp.dao
 * @copyright �Ͼ������Ƽ� ���ܿƼ���
 */
public interface UcpDAO
{

	/**
	 * ��ȡ�������б�
	 * 
	 * @param params
	 *            ����
	 * @return
	 */
	List<Map> getDataSetList(Map params);

	/**
	 * ����������֤����
	 * 
	 * @param checkList
	 *            ��֤�б�
	 */
	void savePerfConfigCheck(List<Map> checkList);

	/**
	 * ��������������֤
	 * 
	 * @param dataList
	 *            �����б�
	 */
	void savePerfDataCheck(List<Map> dataList);
}