
package com.ailk.module.cms.ucp.dao;

import java.util.List;
import java.util.Map;

/**
 * Ucp数据库接口
 * 
 * @author mengqiang(65453)
 * @version 1.0
 * @since 2013-10-21 下午5:03:43
 * @category com.ailk.module.cms.ucp.dao
 * @copyright 南京联创科技 网管科技部
 */
public interface UcpDAO
{

	/**
	 * 获取数据项列表
	 * 
	 * @param params
	 *            参数
	 * @return
	 */
	List<Map> getDataSetList(Map params);

	/**
	 * 保存性能验证数据
	 * 
	 * @param checkList
	 *            验证列表
	 */
	void savePerfConfigCheck(List<Map> checkList);

	/**
	 * 保存性能数据验证
	 * 
	 * @param dataList
	 *            数据列表
	 */
	void savePerfDataCheck(List<Map> dataList);
}