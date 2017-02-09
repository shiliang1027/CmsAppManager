
package com.ailk.module.cms.alarm.quittable.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gaoqc(69151) Tel:18652058796
 * @version 1.0
 * @since 2012-9-7 上午10:40:26
 * @category com.linkage.module.cms.alarm.quittableinfo.utils
 * @copyright 南京联创科技 网管科技部
 */
public class QuitServiceUtil
{

	public static final String QUITSERVICE_TABLE = "tt_quittablestat";
	/**
	 * 动环告警指标
	 */
	public static final String RINGALARM = "power_failure,voltage_low,once_down_power,water_soak,smog,temperature_high,temperature_extra_high";
	/**
	 * 是否省中心
	 */
	public static final String IS_SZX = "全省";
	/**
	 * 
	 */
	public static final String WFW = "微蜂窝";
	/**
	 * 
	 */
	public static final String HZ = "宏站";
	/**
	 * 小区异常告警
	 */
	public static final String XQEXCEPTION = "小区异常退服告警";
	/**
	 * 基站异常退服告警
	 */
	public static final String JZEXCEPTION = "基站异常退服告警";
	/**
	 * 动环关联导致基站退服规则
	 */
	public static final String RINGRULENAME = "[主次关联]动环告警导致基站退服";
	/**
	 * 停电判断条件
	 */
	public static final String POWER_FAILURE_CONDITION = "'基站停电告警','节点停电告警','市电有无','市电故障告警','市电停电告警','市电输入开关状态'";
	/**
	 * 低压判断条件
	 */
	public static final String VOLTAGE_LOW_CONDITION = "'电池组总电压过低告警','输出电压过低告警'";
	/**
	 * 一次下电判断条件
	 */
	public static final String ONCE_DOWN_POWER_CONDITION = "'一级低压脱离断开告警','一次下电告警状态'";
	/**
	 * 水浸判断条件
	 */
	public static final String WATER_SOAK_CONDITION = "'水浸告警','水浸'";
	/**
	 * 烟雾判断条件
	 */
	public static final String SMOG_CONDITION = "'烟雾告警','烟雾'";
	/**
	 * 温度过高判断条件
	 */
	public static final String TEMPERATURE_HIGH_CONDITION = "'温度过高告警','温度'";
	/**
	 * 温度超高判断条件
	 */
	public static final String TEMPERATURE_EXTRA_HIGH_CONDITION = "温度超高告警";
	/**
	 * GSM宏站退服数
	 */
	public static final Integer GSM_HZ_TFS = 0;
	
	/**
	 * GSM宏站数
	 */
	public static final Integer GSM_HZ_COUNT = 1;
	
	/**
	 * GSM宏站退服率
	 */
	public static final Integer GSM_HZ_TFV = 2;
	/**
	 * GSM蜂窝退服数
	 */
	public static final Integer GSM_WFW_TFS = 3;
	/**
	 * GSM蜂窝数
	 */
	public static final Integer GSM_WFW_COUNT = 4;
	/**
	 * GSM蜂窝退服率
	 */
	public static final Integer GSM_WFW_TFV = 5;
	/**
	 * GSM小区退服数
	 */
	public static final Integer GSM_XQ_TFS = 6;
	
	/**
	 * GSM小区数
	 */
	public static final Integer GSM_XQ_COUNT = 7;
	/**
	 * GSM小区退服率
	 */
	public static final Integer GSM_XQ_TFV = 8;
	/**
	 * GSM VIP基站退服数
	 */
	public static final Integer GSM_VIP_TFS = 9;
	/**
	 * GSM小区载频退服数
	 */
	public static final Integer GSM_ZB_TFS = 10;
	/**
	 * GSM基站人工退服数
	 */
	public static final Integer GSM_JZRGBS_S = 11;
	/**
	 * GSM小区人工退服数
	 */
	public static final Integer GSM_XQRGBS_S = 12;
	/**
	 * TD宏站退服数
	 */
	public static final Integer TD_HZ_TFS = 13;
	/**
	 * TD宏站数
	 */
	public static final Integer TD_HZ_COUNT = 14;
	/**
	 * TD宏站退服率
	 */
	public static final Integer TD_HZ_TFV = 15;
	/**
	 * TD蜂窝退服数
	 */
	public static final Integer TD_WFW_TFS = 16;
	/**
	 * TD蜂窝数
	 */
	public static final Integer TD_WFW_COUNT = 17;
	/**
	 * TD蜂窝退服率
	 */
	public static final Integer TD_WFW_TFV = 18;
	/**
	 * TD小区退服数
	 */
	public static final Integer TD_XQ_TFS = 19;
	/**
	 * TD小区数
	 */
	public static final Integer TD_XQ_COUNT = 20;
	/**
	 * TD小区退服率
	 */
	public static final Integer TD_XQ_TFV = 21;
	/**
	 * TD VIP基站退服数
	 */
	public static final Integer TD_VIP_TFS = 22;
	/**
	 * TD小区载频退服数
	 */
	public static final Integer TD_ZB_TFS = 23;
	/**
	 * TD基站人工退服数
	 */
	public static final Integer TD_JZRGBS_S = 24;
	/**
	 * TD人工退服数
	 */
	public static final Integer TD_XQRGBS_S = 25;
	/**
	 * 停电
	 */
	public static final Integer POWER_FAILURE = 26;
	/**
	 * 低压
	 */
	public static final Integer VOLTAGE_LOW = 27;
	/**
	 * 一次下电
	 */
	public static final Integer ONCE_DOWN_POWER = 28;
	/**
	 * 水浸
	 */
	public static final Integer WATER_SOAK = 29;
	/**
	 * 烟雾
	 */
	public static final Integer SMOG = 30;
	/**
	 * 温度过高
	 */
	public static final Integer TEMPERATURE_HIGH = 31;
	/**
	 * 温度超高
	 */
	public static final Integer TEMPERATURE_EXTRA_HIGH = 32;
	
	/**
	 * kpi指标映射关系
	 */
	public static Map<Integer,String> kpiMap = new HashMap<Integer,String>();
	
	/**
	 * kpi指标类型映射关系
	 */
	public static Map<String,String> kpiTypeMap = new HashMap<String,String>();
	
	static
	{
		/**
		 * kpi指标映射关系
		 */
		 kpiMap.put(GSM_HZ_TFS,"gsm_hz_tfs");
		 kpiMap.put(GSM_HZ_COUNT,"gsm_hz_count");
		 kpiMap.put(GSM_HZ_TFV,"gsm_hz_tfv");
		 kpiMap.put(GSM_WFW_TFS,"gsm_fw_tfs");
		 kpiMap.put(GSM_WFW_COUNT,"gsm_fw_count");
		 kpiMap.put(GSM_WFW_TFV,"gsm_fw_tfv");
		 kpiMap.put(GSM_XQ_TFS,"gsm_xq_tfs");
		 kpiMap.put(GSM_XQ_COUNT,"gsm_xq_count");
		 kpiMap.put(GSM_XQ_TFV,"gsm_xq_tfv");
		 kpiMap.put(GSM_VIP_TFS,"gsm_vip_tfs");
		 kpiMap.put(GSM_ZB_TFS,"gsm_zb_tfs");
		 kpiMap.put(GSM_XQRGBS_S,"gsm_xqrgbs_s");
		 kpiMap.put(GSM_JZRGBS_S,"gsm_jzrgbs_s");
		 kpiMap.put(TD_HZ_TFS,"td_hz_tfs");
		 kpiMap.put(TD_HZ_COUNT,"td_hz_count");
		 kpiMap.put(TD_HZ_TFV,"td_hz_tfv");
		 kpiMap.put(TD_WFW_TFS,"td_fw_tfs");
		 kpiMap.put(TD_WFW_COUNT,"td_fw_count");
		 kpiMap.put(TD_WFW_TFV,"td_fw_tfv");
		 kpiMap.put(TD_XQ_TFS,"td_xq_tfs");
		 kpiMap.put(TD_XQ_COUNT,"td_xq_count");
		 kpiMap.put(TD_XQ_TFV,"td_xq_tfv");
		 kpiMap.put(TD_VIP_TFS,"td_vip_tfs");
		 kpiMap.put(TD_ZB_TFS,"td_zb_tfs");
		 kpiMap.put(TD_XQRGBS_S,"td_xqrgbs_s");
		 kpiMap.put(TD_JZRGBS_S,"td_jzrgbs_s");
		 kpiMap.put(POWER_FAILURE,"power_failure");
		 kpiMap.put(VOLTAGE_LOW,"voltage_low");
		 kpiMap.put(ONCE_DOWN_POWER,"once_down_power");
		 kpiMap.put(WATER_SOAK,"water_soak");
		 kpiMap.put(SMOG,"smog");
		 kpiMap.put(TEMPERATURE_HIGH,"temperature_high");
		 kpiMap.put(TEMPERATURE_EXTRA_HIGH,"temperature_extra_high");
		 
		 /**
		  * kpi指标类型映射关系
		  */
		 kpiTypeMap.put("gsm_hz_tfs", "1");
		 kpiTypeMap.put("gsm_hz_tfv", "1");
		 kpiTypeMap.put("gsm_fw_tfs", "1");
		 kpiTypeMap.put("gsm_fw_tfv", "1");
		 kpiTypeMap.put("gsm_xq_tfs", "1");
		 kpiTypeMap.put("gsm_xq_tfv", "1");
		 kpiTypeMap.put("gsm_vip_tfs", "1");
		 kpiTypeMap.put("gsm_zb_tfs", "1");
		 kpiTypeMap.put("gsm_jzrgbs_s", "1");
		 kpiTypeMap.put("gsm_xqrgbs_s", "1");
		 kpiTypeMap.put("td_hz_tfs", "2");
		 kpiTypeMap.put("td_hz_tfv", "2");
		 kpiTypeMap.put("td_fw_tfs", "2");
		 kpiTypeMap.put("td_fw_tfv", "2");
		 kpiTypeMap.put("td_xq_tfs", "2");
		 kpiTypeMap.put("td_xq_tfv", "2");
		 kpiTypeMap.put("td_vip_tfs", "2");
		 kpiTypeMap.put("td_zb_tfs", "2");
		 kpiTypeMap.put("td_jzrgbs_s", "2");
		 kpiTypeMap.put("td_xqrgbs_s", "2");
		 kpiTypeMap.put("power_failure", "3");
		 kpiTypeMap.put("voltage_low", "3");
		 kpiTypeMap.put("once_down_power", "3");
		 kpiTypeMap.put("water_soak", "3");
		 kpiTypeMap.put("smog", "3");
		 kpiTypeMap.put("temperature_high", "3");
		 kpiTypeMap.put("temperature_extra_high", "3");
	}
}
