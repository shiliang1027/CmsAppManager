
package com.ailk.module.cms.alarm.sheet.complaint.serv;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ailk.module.cms.alarm.sheet.complaint.dao.ComplaintDAO;
import com.ailk.module.cms.system.basesupport.BaseSupportServ;
import com.linkage.system.utils.DateTimeUtil;

/**
 * Ͷ�߱� ͬ����237ͬ����193��ͬ����Ӧ�ֶ�
 * 
 * @author wangsh(69070)
 * @version 1.0
 * @since 2013-4-22 ����03:00:56
 * @category com.ailk.module.cms.alarm.sheet.complaint.serv
 * @copyright �Ͼ������Ƽ� ���ܿƼ���
 */
public class ComplaintServImp implements BaseSupportServ
{

	/**
	 * log4j��־��¼��
	 */
	private static final Logger LOG = Logger.getLogger(ComplaintServImp.class);
	private ComplaintDAO complaintDAO;
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
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		LOG.info("spring afterPropertiesSet");
		// ������
		if (isAddData == 1)
		{
			DateTimeUtil dtu = new DateTimeUtil(addStartTime);
			// ��ʼʱ��
			long startTime = dtu.getLongTime();
			dtu = new DateTimeUtil(addEndTime);
			// ����ʱ��
			long endTime = dtu.getLongTime();
			// ������
			for (long time = startTime; time <= endTime; time += 86400)
			{
				LOG.info("�����䡿:" + time);
				getComplaintData(time * 1000L, isAddData);
			}
		}
		else
		{
			getComplaintData(0, isAddData);
		}
	}
	
	/**
	 * Ͷ�߱�����
	 * 
	 * @param startTime
	 * @param type
	 */
	public void getComplaintData(long startTime, int type)
	{
		long t = System.currentTimeMillis();
		DateTimeUtil nextDt = null;
		if (1 == type)
		{
			LOG.info("����ʼ��Ͷ�߱����ݡ�");
			nextDt = new DateTimeUtil(startTime);
		}
		if (0 == type)
		{
			LOG.info("������ͳ��Ͷ�߱����ݡ�");
			nextDt = new DateTimeUtil();
		}
		// ͳ��ǰһ��00:00������00:00������
		String beginTime = getDateFrontNday(nextDt.getDateTime(), -1)+ " 00:00:00";
		String endTime = getDateFrontNday(nextDt.getDateTime(), -1)+ " 23:59:59";
		long t1 = new DateTimeUtil(beginTime).getLongTime();
		long t2 = new DateTimeUtil(endTime).getLongTime();
		LOG.info("����ʼʱ�䡿" + beginTime);
		LOG.info("������ʱ�䡿" + endTime);
		Map param = new HashMap();
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		param.put("t1", t1);
		param.put("t2", t2);
		//���֮ǰ��ȥ���ظ�������
		complaintDAO.deleteComplaintData(param);
		List<Map> cityList =complaintDAO.queryCityList();
		Map<String, String> cityMap = new HashMap<String, String>();
		List<Map> complaintList = complaintDAO.queryComplaintData(param);
		// ��237���ݿ�t_zhjk_tousuȡ����Ӧ����
		for (Map map : cityList)
		{
			cityMap.put(String.valueOf(map.get("city_name")), String.valueOf(map
					.get("city_id")));
		}
		for(Map map:complaintList)
		{
			long sendtime = new DateTimeUtil(String.valueOf(map.get("sendtime"))).getLongTime();
			map.put("sendtime", sendtime);
			long sheettime = new DateTimeUtil(String.valueOf(map.get("sheettime"))).getLongTime();
			map.put("sheettime", sheettime);
			map.put("createtime", System.currentTimeMillis() / 1000L);
			//map.put("failureoccurcity", cityMap.get(String.valueOf(map.get("failureoccurcity"))));
		}
		LOG.info("��ͬ�������ݡ�" + complaintList.get(0));
		complaintDAO.insertComplain(complaintList);
	}
	
	/**
	 * ��ȡǰN�������
	 * 
	 * @param date
	 *            ����
	 * @param n
	 *            ����
	 * @return String YYYY_MM_DD
	 */
	private String getDateFrontNday(Date date, int n)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return (new DateTimeUtil(cal.getTime())).getDate();
	}

	public ComplaintDAO getComplaintDAO()
	{
		return complaintDAO;
	}

	public void setComplaintDAO(ComplaintDAO complaintDAO)
	{
		this.complaintDAO = complaintDAO;
	}

	public String getAddStartTime()
	{
		return addStartTime;
	}

	public void setAddStartTime(String addStartTime)
	{
		this.addStartTime = addStartTime;
	}

	public String getAddEndTime()
	{
		return addEndTime;
	}

	public void setAddEndTime(String addEndTime)
	{
		this.addEndTime = addEndTime;
	}

	public int getIsAddData()
	{
		return isAddData;
	}

	public void setIsAddData(int isAddData)
	{
		this.isAddData = isAddData;
	}

	@Override
	public void destroy() throws Exception
	{
		LOG.info("spring destroy");
	}
}
