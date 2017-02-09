
package com.ailk.module.cms.performance.kpielectric.dao;

import java.util.List;
import java.util.Map;

/**
 * @author wangsh(69070) Tel:15051899778
 * @version 1.0
 * @since 2012-8-9 上午09:25:42
 * @category com.ailk.module.cms.performance.kpielectric.dao
 * @copyright 南京联创科技 网管科技部
 */
public interface KpielectricDAO
{

	/**
	 * 获取存在的告警关联表列表
	 * 
	 * @param param
	 *            告警关联表表名参数
	 * @return
	 */
	List<Map> getKpielectricTabList(Map param);

	/**
	 * 创建电力信息卡表electric_card_info
	 * 
	 * @param param
	 * @return
	 */
	int createKpielectricTab(Map param);

	/**
	 * 创建TCP连接成功率统计ZC_TCP_CON_STAT_yyyy_mm_dd
	 * 
	 * @param param
	 * @return
	 */
	int createTcpTabList(Map param);
	/**
	 * 创建不在线终端详情那张表
	 * @param param
	 * @return
	 */
	int createDetailTabList(Map param);

	/**
	 * 查询东信那边的数据
	 * 
	 * @param param
	 * @return
	 */
	int queryTcpList(Map param);
	/**
	 * 用来详情的统计表
	 * @param param
	 * @return
	 */
	int queryunlineList(Map param);

	int queryKpielectricList(Map param);


	/**
	 * 创建tp_m2m_kpibuilding_yyyy_MM 
	 * @param param
	 * @return
	 */
	int createBuildingTabList(Map param);
	
	int queryBuildingList(Map param);
}
