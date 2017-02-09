
package com.ailk.module.cms.alarm.alarmassociate.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 工具类
 * 
 * @author mengqiang (65453)
 * @version 1.0
 * @since 2012-7-18
 * @category com.ailk.module.cms.alarm.alarmassociate.util
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 */
public class AssociateUtil
{

	/**
	 * 专业ID和专业专业名称对应关系
	 */
	public static Map<String, String> specMap = new HashMap<String, String>();
	static
	{
		specMap.put("1", "核心网");
		specMap.put("2", "数据网");
		specMap.put("3", "传输网");
		specMap.put("4", "动环网");
		specMap.put("56", "无线网");
	}
}