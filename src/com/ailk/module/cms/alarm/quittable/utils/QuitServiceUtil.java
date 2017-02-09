
package com.ailk.module.cms.alarm.quittable.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gaoqc(69151) Tel:18652058796
 * @version 1.0
 * @since 2012-9-7 ����10:40:26
 * @category com.linkage.module.cms.alarm.quittableinfo.utils
 * @copyright �Ͼ������Ƽ� ���ܿƼ���
 */
public class QuitServiceUtil
{

	public static final String QUITSERVICE_TABLE = "tt_quittablestat";
	/**
	 * �����澯ָ��
	 */
	public static final String RINGALARM = "power_failure,voltage_low,once_down_power,water_soak,smog,temperature_high,temperature_extra_high";
	/**
	 * �Ƿ�ʡ����
	 */
	public static final String IS_SZX = "ȫʡ";
	/**
	 * 
	 */
	public static final String WFW = "΢����";
	/**
	 * 
	 */
	public static final String HZ = "��վ";
	/**
	 * С���쳣�澯
	 */
	public static final String XQEXCEPTION = "С���쳣�˷��澯";
	/**
	 * ��վ�쳣�˷��澯
	 */
	public static final String JZEXCEPTION = "��վ�쳣�˷��澯";
	/**
	 * �����������»�վ�˷�����
	 */
	public static final String RINGRULENAME = "[���ι���]�����澯���»�վ�˷�";
	/**
	 * ͣ���ж�����
	 */
	public static final String POWER_FAILURE_CONDITION = "'��վͣ��澯','�ڵ�ͣ��澯','�е�����','�е���ϸ澯','�е�ͣ��澯','�е����뿪��״̬'";
	/**
	 * ��ѹ�ж�����
	 */
	public static final String VOLTAGE_LOW_CONDITION = "'������ܵ�ѹ���͸澯','�����ѹ���͸澯'";
	/**
	 * һ���µ��ж�����
	 */
	public static final String ONCE_DOWN_POWER_CONDITION = "'һ����ѹ����Ͽ��澯','һ���µ�澯״̬'";
	/**
	 * ˮ���ж�����
	 */
	public static final String WATER_SOAK_CONDITION = "'ˮ���澯','ˮ��'";
	/**
	 * �����ж�����
	 */
	public static final String SMOG_CONDITION = "'����澯','����'";
	/**
	 * �¶ȹ����ж�����
	 */
	public static final String TEMPERATURE_HIGH_CONDITION = "'�¶ȹ��߸澯','�¶�'";
	/**
	 * �¶ȳ����ж�����
	 */
	public static final String TEMPERATURE_EXTRA_HIGH_CONDITION = "�¶ȳ��߸澯";
	/**
	 * GSM��վ�˷���
	 */
	public static final Integer GSM_HZ_TFS = 0;
	
	/**
	 * GSM��վ��
	 */
	public static final Integer GSM_HZ_COUNT = 1;
	
	/**
	 * GSM��վ�˷���
	 */
	public static final Integer GSM_HZ_TFV = 2;
	/**
	 * GSM�����˷���
	 */
	public static final Integer GSM_WFW_TFS = 3;
	/**
	 * GSM������
	 */
	public static final Integer GSM_WFW_COUNT = 4;
	/**
	 * GSM�����˷���
	 */
	public static final Integer GSM_WFW_TFV = 5;
	/**
	 * GSMС���˷���
	 */
	public static final Integer GSM_XQ_TFS = 6;
	
	/**
	 * GSMС����
	 */
	public static final Integer GSM_XQ_COUNT = 7;
	/**
	 * GSMС���˷���
	 */
	public static final Integer GSM_XQ_TFV = 8;
	/**
	 * GSM VIP��վ�˷���
	 */
	public static final Integer GSM_VIP_TFS = 9;
	/**
	 * GSMС����Ƶ�˷���
	 */
	public static final Integer GSM_ZB_TFS = 10;
	/**
	 * GSM��վ�˹��˷���
	 */
	public static final Integer GSM_JZRGBS_S = 11;
	/**
	 * GSMС���˹��˷���
	 */
	public static final Integer GSM_XQRGBS_S = 12;
	/**
	 * TD��վ�˷���
	 */
	public static final Integer TD_HZ_TFS = 13;
	/**
	 * TD��վ��
	 */
	public static final Integer TD_HZ_COUNT = 14;
	/**
	 * TD��վ�˷���
	 */
	public static final Integer TD_HZ_TFV = 15;
	/**
	 * TD�����˷���
	 */
	public static final Integer TD_WFW_TFS = 16;
	/**
	 * TD������
	 */
	public static final Integer TD_WFW_COUNT = 17;
	/**
	 * TD�����˷���
	 */
	public static final Integer TD_WFW_TFV = 18;
	/**
	 * TDС���˷���
	 */
	public static final Integer TD_XQ_TFS = 19;
	/**
	 * TDС����
	 */
	public static final Integer TD_XQ_COUNT = 20;
	/**
	 * TDС���˷���
	 */
	public static final Integer TD_XQ_TFV = 21;
	/**
	 * TD VIP��վ�˷���
	 */
	public static final Integer TD_VIP_TFS = 22;
	/**
	 * TDС����Ƶ�˷���
	 */
	public static final Integer TD_ZB_TFS = 23;
	/**
	 * TD��վ�˹��˷���
	 */
	public static final Integer TD_JZRGBS_S = 24;
	/**
	 * TD�˹��˷���
	 */
	public static final Integer TD_XQRGBS_S = 25;
	/**
	 * ͣ��
	 */
	public static final Integer POWER_FAILURE = 26;
	/**
	 * ��ѹ
	 */
	public static final Integer VOLTAGE_LOW = 27;
	/**
	 * һ���µ�
	 */
	public static final Integer ONCE_DOWN_POWER = 28;
	/**
	 * ˮ��
	 */
	public static final Integer WATER_SOAK = 29;
	/**
	 * ����
	 */
	public static final Integer SMOG = 30;
	/**
	 * �¶ȹ���
	 */
	public static final Integer TEMPERATURE_HIGH = 31;
	/**
	 * �¶ȳ���
	 */
	public static final Integer TEMPERATURE_EXTRA_HIGH = 32;
	
	/**
	 * kpiָ��ӳ���ϵ
	 */
	public static Map<Integer,String> kpiMap = new HashMap<Integer,String>();
	
	/**
	 * kpiָ������ӳ���ϵ
	 */
	public static Map<String,String> kpiTypeMap = new HashMap<String,String>();
	
	static
	{
		/**
		 * kpiָ��ӳ���ϵ
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
		  * kpiָ������ӳ���ϵ
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
