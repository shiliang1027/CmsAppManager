
package com.ailk.module.cms.alarm.quittable.dao;

import java.util.List;
import java.util.Map;

/**
 * @author gaoqc(69151) Tel:18652058796
 * @version 1.0
 * @since 2012-9-24 ����7:32:54
 * @category com.ailk.module.cms.alarm.quittable.dao
 * @copyright �Ͼ������Ƽ� ���ܿƼ���
 */
public interface QuitServiceDao
{

	/**
	 * ȡ���Ѿ����ڵ����ݼ�¼��
	 * 
	 * @return
	 */
	int getExistData();

	/**
	 * ��ͳ�Ʊ��в���ͳ������
	 * 
	 * @param param
	 */
	void insertQuitTblInfo(List<Map> param);

	/**
	 * ����ͳ�Ʊ��е�����
	 * 
	 * @param param
	 */
	void updateQuitTblInfo(List<Map> param);

	/**
	 * ȡ��13�������б�
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getCity();

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�GSM��վ��վ�˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getBtsHzTfsCount(long startTime, long endTime);

	/**
	 * ���ÿ�����е����е�GSM��վ��վ��(��Ԫ)
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getBtsHzCount();

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�GSM��վ΢�����˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getBtsWfwTfsCount(long startTime, long endTime);

	/**
	 * ���ÿ�����е����е�GSM��վ΢������(��Ԫ��)
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getBtsWfwCount();

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�GSMС���˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getCellTfsCount(long startTime, long endTime);

	/**
	 * ���ÿ�����е����е�GSMС����(��Ԫ��)
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getCellCount();

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�GSM VIP��վ�˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getVipBtsTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�GSMС�� ��Ƶ�˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getCellZpTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�GSM ��վ�˹������˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getBtsRgbsTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�GSM С���˹������˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getCellRgbsTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�TD��վ��վ�˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getNodebHzTfsCount(long startTime, long endTime);

	/**
	 * ���ÿ�����е�TD��վ��վ��(��Ԫ��)
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getNodebHzCount();

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�TD��վ΢�����˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getNodebWfwTfsCount(long startTime, long endTime);

	/**
	 * ���ÿ�����е�TD��վ΢������(��Ԫ��)
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getNodebWfwCount();

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�TDС���˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getUcellTfsCount(long startTime, long endTime);

	/**
	 * ���ÿ�����е�TDС����(��Ԫ��)
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getUcellCount();

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�TD VIP��վ�˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getVipNodebTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�TDС����Ƶ�˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getUcellZpTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�TD��վ�˹������˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getNodebRgbsTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�TDС���˹������˷���(�澯��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getUcellRgbsTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�ͣ���˷���(��Ԫ��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getPowerFailureTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����еĵ�����ܵ�ѹ�����˷���(��Ԫ��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getVoltageLowTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�һ���µ��˷���(��Ԫ��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getOnceDownPowerTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е�ˮ���˷���(��Ԫ��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getWaterSoakTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е��̸��˷���(��Ԫ��)
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getSmogTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е��¶ȹ����˷���(��Ԫ��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getTemperatureHighTfsCount(long startTime, long endTime);

	/**
	 * ��ö�Ӧʱ���ڵ�ÿ�����е��¶ȳ����˷���(��Ԫ��)
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getTemperatureExtraHighTfsCount(long startTime, long endTime);
}
