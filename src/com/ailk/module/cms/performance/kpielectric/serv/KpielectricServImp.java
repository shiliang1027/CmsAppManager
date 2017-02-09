package com.ailk.module.cms.performance.kpielectric.serv;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.ailk.module.cms.performance.kpielectric.dao.KpielectricDAO;
import com.ailk.module.cms.system.basesupport.BaseSupportServ;
import com.linkage.system.utils.DateTimeUtil;
import com.linkage.system.utils.database.DBUtil;
import com.sybase.jdbc2.tds.SybTimestamp;

/**
 * @author wangsh(69070) Tel:15051899778
 * @version 1.0
 * @since 2012-8-9 ����09:22:19
 * @category com.ailk.module.cms.performance.kpielectric.serv
 * @copyright �Ͼ�j���Ƽ� ��ܿƼ���
 */
public class KpielectricServImp implements BaseSupportServ {

	/**
	 * log4j��־��¼��
	 */
	private static final Logger LOG = Logger
			.getLogger(KpielectricServImp.class);
	/**
	 * �澯����DAO
	 */
	private KpielectricDAO kpielectricDAO = null;
	/**
	 * ������ݿ�ʼʱ��
	 */
	private String addStartTime = null;
	/**
	 * ������ݿ�ʼʱ��
	 */
	private String addEndTime = null;
	/**
	 * �Ƿ񲹳���� <li>0:���ò��� <li>1:�������(ͬ�±������ÿ�ʼʱ��ͽ���ʱ��)
	 */
	private int isAddData = 0;
	/**
	 * ��ͳ�ƣ�ͳ����ǰ�������
	 */
	private int ds = -1;
	/**
	 * ���ͳ�Ƴ��� flag=1ͬ��electric_card_info flag=2ͬ��ZC_TCP_CON_STAT_yyyy_mm_dd
	 * flag=3ͬ��ָ��ƽ̨p_ggsn_apn_zhibiao
	 */
	private int flag;

	public void afterPropertiesSet() throws Exception {
		LOG.info("spring afterPropertiesSet");
		if (isAddData == 1) {
			// ��ʼʱ��
			DateTimeUtil dtu = new DateTimeUtil(addStartTime);
			long startTime = dtu.getLongTime();
			// ����ʱ��
			dtu = new DateTimeUtil(addEndTime);
			long endTime = dtu.getLongTime();
			for (long time = startTime; time <= endTime; time += 86400) {
				LOG.info("�����䡿ͳ�ƽ������:" + time);
				startStatData(time);
			}
		} else {
			LOG.info("����ͳ���������");
			long startTime = getStartTime();
			long endTime = getEndTime();
			if (flag == 1) {
				// startStatData(startTime);
				startTcpData(startTime, endTime);
			}
			// if (flag == 2)
			// {
			// startTcpData(startTime, endTime);
			// }
			if (flag == 3) {
				startBuildingData(startTime, endTime);
			}
		}
	}

	/**
	 * ͬ�����ŵ�f��Ϣ����electric_card_info
	 * 
	 * @param startTime
	 */
	private void startStatData(long startTime) {
		String sheetTab = createSheetHisTab();
		// 1.ͳ�ƹ�����ݲ���
		Map param = new HashMap();
		param.put("sheetHis", sheetTab);
		param.put("startTime",
				new DateTimeUtil(startTime * 1000L).getLongDate());
		LOG.info("time============"
				+ new DateTimeUtil(startTime * 1000L).getLongDate());
		// �Ӷ�����ݿ�ȡ��electric_card_info����Ӧ�ֶ�
		LOG.info("ͬ����electric_card_info��¼��"
				+ kpielectricDAO.queryKpielectricList(param) + "��.");
	}

	/**
	 * ͬ������TCPl�ӳɹ���ͳ��ZC_TCP_CON_STATyymmdd
	 * 
	 * @param startTime
	 * @param endTime
	 */
	private void startTcpData(long startTime, long endTime)
			throws ParseException {
		Date now = new Date();
		DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.MEDIUM); // ��ʾ���ڣ�ʱ�䣨��ȷ���֣�
		String str8 = d8.format(now);
		long daltime = 1346378400;
		try {
			daltime = DateUtils.parseDate(str8,
					new String[] { "yyyy-MM-dd HH:mm:ss" }).getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Timestamp ts1 = new Timestamp(daltime * 1000);
		Timestamp ts2 = new Timestamp(daltime * 1000 - 3600 * 1000);
		String sheetTab = createSheetHisTab2(daltime);
		String detailTab = createDetailTab(daltime);
		List<String> tabList = DBUtil.createTableNames(daltime * 1000L,
				daltime * 1000L, Calendar.DAY_OF_MONTH, 1,
				"ZC_TCP_CON_STAT01".toUpperCase(), "MMdd");
		String oldtab = tabList.get(0);
		Map param = new HashMap();
		//tp_terminallinkrate_YYYY_MM
		param.put("sheetHis", sheetTab);
		//tp_terminalunlinedetail_YYYYMM
		param.put("detailTab", detailTab);
		//ZC_TCP_CON_STAT (YY%2+1)mmdd
		param.put("oldtab", oldtab);
		param.put("startTime", ts2);
		param.put("endTime", ts1);
		final List<String> cityinfolist = getCityinfoList();
		for (String cityinfo : cityinfolist) {
			final String[] info = cityinfo.split(",");

			param.put("start_ip", parse(info[1]));
			param.put("end_ip", parse(info[2]));
			param.put("city", info[0]);
			LOG.info("ͬ����ZC_TCP_CON_STATyymmdd��¼��"
					+ kpielectricDAO.queryTcpList(param) + "��.");
			LOG.info("ͬ����tp_terunlinedetail_YYYYMM��¼��"
					+ kpielectricDAO.queryunlineList(param) + "��.");
		}
	}

	/**
	 * ipת����
	 * 
	 * @param ip
	 * @return
	 */
	public static long parse(String ip) {
		String[] ip2 = ip.split("\\.");
		int ip3 = Integer.parseInt(ip2[0]);
		int ip4 = Integer.parseInt(ip2[1]);
		int ip5 = Integer.parseInt(ip2[2]);
		int ip6 = Integer.parseInt(ip2[3]);
		String mp = deal(ip3) + deal(ip4) + deal(ip5) + deal(ip6);
		long haha = Long.parseLong(mp, 16);
		return haha;
	}

	public static String deal(int op) {
		String pp = "";
		if (Integer.toHexString(op).length() > 1) {
			pp = Integer.toHexString(op);
		} else {
			pp = "0".concat(Integer.toHexString(op));
		}
		return pp;
	}

	public List<String> getCityinfoList() {
		List<String> result = new ArrayList<String>();
		result.add("������,10.0.0.1,10.3.163.64");
		result.add("������,10.3.163.65,10.7.36.32");
		result.add("l�Ƹ���,10.7.36.33,10.10.90.0");
		result.add("�Ͼ���,10.10.90.1,10.16.7.32");
		result.add("��ͨ��,10.16.7.33,10.22.74.64");
		result.add("������,10.22.74.65,10.29.183.208");
		result.add("��Ǩ��,10.29.183.209,10.32.237.176");
		result.add("̩����,10.32.237.177,10.37.76.112");
		result.add("������,10.37.76.113,10.42.63.160");
		result.add("������,10.42.63.161,10.48.129.48");
		result.add("�γ���,10.48.129.49,10.54.121.80");
		result.add("������,10.54.121.81,10.58.67.160");
		result.add("����,10.58.67.161,10.61.9.0");

		return result;

	}

	/**
	 * ͬ��ָ��ƽ̨ͳ����ݱ�
	 * 
	 * @param startTime
	 * @param endTime
	 */
	private void startBuildingData(long startTime, long endTime) {
		String sheetTab = createSheetHisTab3(startTime);
		Map param = new HashMap();
		param.put("sheetHis", sheetTab);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		LOG.info("===startTime===================" + startTime);
		LOG.info("===sheetTab===================" + sheetTab);
		List<Map> list = new ArrayList<Map>();
		LOG.info("ͬ����ZC_TCP_CON_STATyymmdd��¼��"
				+ kpielectricDAO.queryBuildingList(param) + "��.");
	}

	private String createSheetHisTab() {
		String alarmHisTab = "electric_card_info";
		Map param = new HashMap();
		param.put("sheetHis", alarmHisTab);
		List<Map> hisTabList = kpielectricDAO.getKpielectricTabList(param);
		if (hisTabList.isEmpty()) {
			int ctRes = kpielectricDAO.createKpielectricTab(param);
			LOG.info("������f��Ϣ����ɹ�:" + (ctRes == 0 ? "�ɹ�" : "ʧ��"));
		}
		return alarmHisTab;
	}

	private String createSheetHisTab2(long daltime) {
		List<String> tabList = DBUtil.createTableNames(daltime * 1000L,
				daltime * 1000L, Calendar.MONTH, 1,
				"tp_terminallinkrate_", "yyyy_MM");
		String alarmHisTab = tabList.get(0);
		Map param = new HashMap();
		param.put("sheetHis", alarmHisTab);
		LOG.info("param:" + param);
		List<Map> hisTabList = kpielectricDAO.getKpielectricTabList(param);
		LOG.info("hisTabList:" + hisTabList);
		if (hisTabList.isEmpty()) {
			int ctRes = kpielectricDAO.createTcpTabList(param);
			LOG.info("����������������ͳ����ʷ�����:" + (ctRes == 0 ? "�ɹ�" : "ʧ��"));
		}
		return alarmHisTab;
	}
	
	private String createDetailTab(long daltime) {
		List<String> tabList = DBUtil.createTableNames(daltime * 1000L,
				daltime * 1000L, Calendar.MONTH, 1,
				"tp_terunlinedetail_", "yyyy_MM");
		String alarmHisTab = tabList.get(0);
		LOG.info("==========����============"+alarmHisTab);
		Map param = new HashMap();
		param.put("sheetHis", alarmHisTab);
		LOG.info("param:" + param);
		List<Map> hisTabList = kpielectricDAO.getKpielectricTabList(param);
		LOG.info("hisTabList:" + hisTabList);
		if (hisTabList.isEmpty()) {
			int ctRes = kpielectricDAO.createDetailTabList(param);
			LOG.info("����������������ͳ����ʷ�����:" + (ctRes == 0 ? "�ɹ�" : "ʧ��"));
		}
		return alarmHisTab;
	}

	private String createSheetHisTab3(long startTime) {
		List<String> tabList = DBUtil.createTableNames(startTime * 1000L,
				startTime * 1000L, Calendar.MONTH, 1, "tp_m2m_kpibuilding_",
				"yyyy_MM");
		String alarmHisTab = tabList.get(0);
		Map param = new HashMap();
		param.put("sheetHis", alarmHisTab);
		List<Map> hisTabList = kpielectricDAO.getKpielectricTabList(param);
		if (hisTabList.isEmpty()) {
			int ctRes = kpielectricDAO.createBuildingTabList(param);
			LOG.info("����������������ͳ����ʷ�����:" + (ctRes == 0 ? "�ɹ�" : "ʧ��"));
		}
		return alarmHisTab;
	}

	/**
	 * ÿ���00:00:00��ʱ ��ǰһ������ͬ�������ǿ���
	 */
	// ��ȡ��ʼʱ��,ǰһ��� ʱ��
	public long getStartTime() {
		DateTimeUtil dtu = new DateTimeUtil();
		dtu.getNextDate(1 + ds);
		dtu = new DateTimeUtil(dtu.getDate());
		return dtu.getLongTime() - 86400;
	}

	// ��ȡ����ʱ�䣬��ǰʱ���ȥ1
	public long getEndTime() {
		DateTimeUtil dtu = new DateTimeUtil();
		dtu.getNextDate(1 + ds);
		dtu = new DateTimeUtil(dtu.getDate());
		return dtu.getLongTime() - 1;
	}

	public void destroy() throws Exception {
		LOG.info("spring destroy");
	}

	public void setKpielectricDAO(KpielectricDAO kpielectricDAO) {
		this.kpielectricDAO = kpielectricDAO;
	}

	public void setAddStartTime(String addStartTime) {
		this.addStartTime = addStartTime;
	}

	public void setAddEndTime(String addEndTime) {
		this.addEndTime = addEndTime;
	}

	public void setIsAddData(int isAddData) {
		this.isAddData = isAddData;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
