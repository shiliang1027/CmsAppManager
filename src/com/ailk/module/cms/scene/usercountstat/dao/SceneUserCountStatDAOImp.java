
package com.ailk.module.cms.scene.usercountstat.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * ����/�������ݲ�ӿ�
 * 
 * @author loukp(No:69139 tel:15951882231)
 * @version 1.0.0
 * @category com.ailk.module.cms.scene.usercountstat.dao
 * @since 2012-08-12
 * @copyright �������� ���ܿ�����
 */
public class SceneUserCountStatDAOImp extends JPABaseDAO implements SceneUserCountStatDAO
{

	/**
	 * sqlǰ׺
	 */
	private final String sqlPre = SceneUserCountStatDAOImp.class.getName();
	
	/**
	 * ��ȡ��·������վ��������
	 * 
	 * @return List<Map> ��·������վ��������
	 */
	public List<Map> getTrainGroupList()
	{
		Map param = new HashMap();
		return getSqlSessionTemplate().selectList(sqlPre + ".getTrainGroupList", param);
	}
	
	/**
	 * ������Id��ȡ��·������վ����
	 * 
	 * @param groupId String ��Id������ö��ŷֿ�
	 * @return List<Map> ��·������վ����
	 */
	public List<Map> getTrainGroupListByGroupId(String groupId)
	{
		Map param = new HashMap();
		param.put("groupId", groupId);
		return getSqlSessionTemplate().selectList(sqlPre + ".getTrainGroupListByGroupId", param);
	}
	
	/**
	 * ��ȡ��·�������û���
	 * 
	 * @return void 
	 */
	public void updateUserCount(List<Map> list)
	{
		getSqlSessionTemplate().batchUpdate(sqlPre + ".updateUserCount", list);
	}
}