
package com.ailk.module.cms.scene.usercountstat.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * 场景/区域数据层接口
 * 
 * @author loukp(No:69139 tel:15951882231)
 * @version 1.0.0
 * @category com.ailk.module.cms.scene.usercountstat.dao
 * @since 2012-08-12
 * @copyright 亚信联创 网管开发部
 */
public class SceneUserCountStatDAOImp extends JPABaseDAO implements SceneUserCountStatDAO
{

	/**
	 * sql前缀
	 */
	private final String sqlPre = SceneUserCountStatDAOImp.class.getName();
	
	/**
	 * 获取铁路场景基站分组数据
	 * 
	 * @return List<Map> 铁路场景基站分组数据
	 */
	public List<Map> getTrainGroupList()
	{
		Map param = new HashMap();
		return getSqlSessionTemplate().selectList(sqlPre + ".getTrainGroupList", param);
	}
	
	/**
	 * 根据组Id获取铁路场景基站数据
	 * 
	 * @param groupId String 组Id，多个用逗号分开
	 * @return List<Map> 铁路场景基站数据
	 */
	public List<Map> getTrainGroupListByGroupId(String groupId)
	{
		Map param = new HashMap();
		param.put("groupId", groupId);
		return getSqlSessionTemplate().selectList(sqlPre + ".getTrainGroupListByGroupId", param);
	}
	
	/**
	 * 获取铁路场景组用户数
	 * 
	 * @return void 
	 */
	public void updateUserCount(List<Map> list)
	{
		getSqlSessionTemplate().batchUpdate(sqlPre + ".updateUserCount", list);
	}
}