
package com.ailk.module.cms.alarm.sheet.complaint.dao;

import java.util.List;
import java.util.Map;

/**
 * @author mengqiang (65453)
 * @version 1.0
 * @since 2013-4-22
 * @category com.ailk.module.cms.alarm.sheet.complaint.dao
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 */
public interface ComplaintDAO
{

	List<Map> queryComplaintData(Map params);

	void insertComplain(List<Map> paramList);

	/**
	 * ɾ��֮ǰ��ȥ���ظ�����
	 * 
	 * @param params
	 */
	void deleteComplaintData(Map params);
	
	List<Map> queryCityList();
}
