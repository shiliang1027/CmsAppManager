
package com.ailk.module.cms.scene.usercountstat.dao;

import java.util.List;
import java.util.Map;

/**
 * ����/�������ݲ�ӿ�
 * 
 * @author loukp(No:69139 tel:15951882231)
 * @version 1.0.0
 * @category com.ailk.module.cms.scene.usercountstat.dao
 * @since 2012-08-12
 * @copyright �������� ���ܿ�����
 */
public interface SceneUserCountStatDAO
{

	/**
	 * ��ȡ��·������վ��������
	 * 
	 * @return List<Map> ��·������վ��������
	 */
	List<Map> getTrainGroupList();
	
	/**
	 * ������Id��ȡ��·������վ����
	 * 
	 * @param groupId String ��Id������ö��ŷֿ�
	 * @return List<Map> ��·������վ����
	 */
	List<Map> getTrainGroupListByGroupId(String groupId);
	
	/**
	 * ��ȡ��·�������û���
	 * 
	 * @return void 
	 */
	public void updateUserCount(List<Map> list);
}