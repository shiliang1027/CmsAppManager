
package com.ailk.module.cms.scene.usercountstat.dao;

import java.util.List;
import java.util.Map;

/**
 * 场景/区域数据层接口
 * 
 * @author loukp(No:69139 tel:15951882231)
 * @version 1.0.0
 * @category com.ailk.module.cms.scene.usercountstat.dao
 * @since 2012-08-12
 * @copyright 亚信联创 网管开发部
 */
public interface SceneUserCountStatDAO
{

	/**
	 * 获取铁路场景基站分组数据
	 * 
	 * @return List<Map> 铁路场景基站分组数据
	 */
	List<Map> getTrainGroupList();
	
	/**
	 * 根据组Id获取铁路场景基站数据
	 * 
	 * @param groupId String 组Id，多个用逗号分开
	 * @return List<Map> 铁路场景基站数据
	 */
	List<Map> getTrainGroupListByGroupId(String groupId);
	
	/**
	 * 获取铁路场景组用户数
	 * 
	 * @return void 
	 */
	public void updateUserCount(List<Map> list);
}