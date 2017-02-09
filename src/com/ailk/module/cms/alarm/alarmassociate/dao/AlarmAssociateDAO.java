
package com.ailk.module.cms.alarm.alarmassociate.dao;

import java.util.List;
import java.util.Map;

/**
 * @author bilei5 (69064)
 * @version 1.0
 * @since 2012-7-4
 * @category com.ailk.module.cms.alarm.alarmassociate.dao.mapper.oracle
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 */
public interface AlarmAssociateDAO
{

	/**
	 * 获取主次关联中的次告警数
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getSubAlarmNum(Map param);

	/**
	 * 衍生关联中的原始告警数
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getOriginalAlarmNum(Map param);

	/**
	 * 工单追加涉及的告警数
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getSheetAlarmNum(Map param);

	/**
	 * 衍生关联中的衍生告警数
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getDeriveAlarmNum(Map param);

	/**
	 * 关联分析涉及告警数
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getRelationAlarmNum(Map param);
	
	/**
	 * 关联专业内的告警数
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getRelationInnerAlarmNum(Map param);

	/**
	 * 1~3级告警原始入库总数（含衍生告警）
	 * 
	 * @param param
	 * @return
	 */
	List<Map> getTotalAlarmNum(Map param);
	
	/**
	 * 保存关联指标统计信息
	 * 
	 * @param param
	 */
	void saveRelationAnalysisStat(List<Map> param);

	/**
	 * 获取存在的告警关联表列表
	 * 
	 * @param param
	 *            告警关联表表名参数
	 * @return
	 */
	List<Map> getExistAssociateTabList(Map param);

	/**
	 * 创建告警关联表
	 * 
	 * @param param
	 *            告警关联表表名参数
	 * @return
	 */
	int createAssociateTabList(Map param);
}