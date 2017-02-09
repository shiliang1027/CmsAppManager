
package com.ailk.module.cms.alarm.sheet.alarmdispatch.dao;

import java.util.List;
import java.util.Map;

public interface AlarmDispatchDAO
{

	/**
	 * 获取存在的告警关联表列表
	 * 
	 * @param param
	 *            告警关联表表名参数
	 * @return
	 */
	List<Map> getExistDispatchTabList(Map param);

	/**
	 * 创建已派单告警表
	 * 
	 * @param param
	 *            告警关联表表名参数
	 * @return
	 */
	int createDispatchTabList(Map param);

	/**
	 * 统计派单数据数据
	 * 
	 * @param param
	 * @return
	 */
	void saveDispatchList(Map param);
	
	/**
	 * 更新派单及时数据
	 * 
	 * @param param
	 * @return
	 */
	void updateDispatchList(Map param);
}