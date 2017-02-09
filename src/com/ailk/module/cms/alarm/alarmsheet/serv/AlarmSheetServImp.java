
package com.ailk.module.cms.alarm.alarmsheet.serv;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ailk.module.cms.alarm.alarmsheet.dao.AlarmSheetDAO;
import com.ailk.module.cms.system.basesupport.BaseSupportServ;
import com.linkage.system.utils.DateTimeUtil;
import com.linkage.system.utils.database.DBUtil;

/**
 * �澯����servʵ����
 * 
 * @author mengqiang (65453)
 * @version 1.0
 * @since 2012-6-29
 * @category com.ailk.module.cms.alarm.alarmsheet.serv
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 */
public class AlarmSheetServImp implements BaseSupportServ
{

	/**
	 * log4j��־��¼��
	 */
	private static final Logger LOG = Logger.getLogger(AlarmSheetServImp.class);
	/**
	 * �澯����DAO
	 */
	private AlarmSheetDAO alarmSheetDao = null;
	/**
	 * �������ݿ�ʼʱ��
	 */
	private String addStartTime = null;
	/**
	 * �������ݿ�ʼʱ��
	 */
	private String addEndTime = null;
	/**
	 * �Ƿ񲹳�����
	 * <li>0:���ò���
	 * <li>1:��������(ͬ�±������ÿ�ʼʱ��ͽ���ʱ��)
	 */
	private int isAddData = 0;
	/**
	 * ��ͳ�ƣ�ͳ����ǰ�������
	 */
	private int ds = -1;

	public void afterPropertiesSet() throws Exception
	{
		LOG.info("spring afterPropertiesSet");
		if (isAddData == 1)
		{
			// ��ʼʱ��
			DateTimeUtil dtu = new DateTimeUtil(addStartTime);
			long startTime = dtu.getLongTime();
			// ����ʱ��
			dtu = new DateTimeUtil(addEndTime);
			long endTime = dtu.getLongTime();
			for (long time = startTime; time <= endTime; time += 86400)
			{
				LOG.info("�����䡿ͳ�ƽ�������:" + time);
				startStatData(time, (time + 86399));
			}
		}
		else
		{
			LOG.info("��������ͳ����������");
			long startTime = getStartTime();
			long endTime = getEndTime();
			startStatData(startTime, endTime);
		}
	}

	// ��ʼͳ������
	private void startStatData(long startTime, long endTime)
	{
		String sheetTab = createSheetHisTab(startTime);
		String alarmTab = getHisAlarmTabName(startTime);
		// 1.ͳ�ƹ������ݲ���
		Map param = new HashMap();
		param.put("sheetHis", sheetTab);
		param.put("alarmHis", alarmTab);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		// 2.ͳ�ƹ�������
		LOG.info("����ʼͳ�ơ�ͳ�ƹ���ͳ����ʷ�����:" + param);
		int stRes = alarmSheetDao.statAlarmAutoSendSheetData(param);
		LOG.info("������ͳ�ơ�ͳ�ƹ���ͳ����ʷ������:" + (stRes > 0 ? "�ɹ�" : "ʧ��"));
	}

	// ��ȡ�澯��ʷ��
	private String getHisAlarmTabName(long startTime)
	{
		List<String> tabList = DBUtil.createTableNames(startTime * 1000L,
				startTime * 1000L, Calendar.MONTH, 1, "ta_historyalarm_", "yyyy_MM");
		return tabList.get(0);
	}

	// �����澯����ͳ����ʷ����
	private String createSheetHisTab(long startTime)
	{
		List<String> tabList = DBUtil.createTableNames(startTime * 1000L,
				startTime * 1000L, Calendar.MONTH, 1, "tt_sheetstat_history_", "yyyy_MM");
		String alarmHisTab = tabList.get(0);
		// ��ѯ����ͳ����ʷ�����
		Map param = new HashMap();
		param.put("sheetHis", alarmHisTab);
		LOG.info("����ʼ��ѯ����ѯ����ͳ����ʷ�����:" + param);
		List<Map> hisTabList = alarmSheetDao.getExistSheetTabList(param);
		LOG.info("��������ѯ����ѯ����ͳ����ʷ������:" + hisTabList);
		// �������ͳ����ʷ������ֱ�Ӵ���
		if (hisTabList.isEmpty())
		{
			LOG.info("����ʼ��������������ͳ����ʷ�����:" + param);
			int ctRes = alarmSheetDao.createSheetTabList(param);
			LOG.info("��������������������ͳ����ʷ������:" + (ctRes == 0 ? "�ɹ�" : "ʧ��"));
		}
		return alarmHisTab;
	}

	// ��ȡ��ʼʱ��
	public long getStartTime()
	{
		DateTimeUtil dtu = new DateTimeUtil();
		dtu.getNextDate(1 + ds);
		dtu = new DateTimeUtil(dtu.getDate());
		return dtu.getLongTime() - 86400;
	}

	// ��ȡ����ʱ��
	public long getEndTime()
	{
		DateTimeUtil dtu = new DateTimeUtil();
		dtu.getNextDate(1 + ds);
		dtu = new DateTimeUtil(dtu.getDate());
		return dtu.getLongTime() - 1;
	}

	public void destroy() throws Exception
	{
		LOG.info("spring destroy");
	}

	public void setAlarmSheetDao(AlarmSheetDAO alarmSheetDao)
	{
		this.alarmSheetDao = alarmSheetDao;
	}

	public void setAddStartTime(String addStartTime)
	{
		this.addStartTime = addStartTime;
	}

	public void setAddEndTime(String addEndTime)
	{
		this.addEndTime = addEndTime;
	}

	public void setIsAddData(int isAddData)
	{
		this.isAddData = isAddData;
	}
}