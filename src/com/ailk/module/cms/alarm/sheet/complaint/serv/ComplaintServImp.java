
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
 * 投诉表 同步，237同步到193，同步相应字段
 * 
 * @author wangsh(69070)
 * @version 1.0
 * @since 2013-4-22 下午03:00:56
 * @category com.ailk.module.cms.alarm.sheet.complaint.serv
 * @copyright 南京联创科技 网管科技部
 */
public class ComplaintServImp implements BaseSupportServ
{

	/**
	 * log4j日志记录器
	 */
	private static final Logger LOG = Logger.getLogger(ComplaintServImp.class);
	private ComplaintDAO complaintDAO;
	/**
	 * 补充数据开始时间
	 */
	private String addStartTime = null;
	/**
	 * 补充数据开始时间
	 */
	private String addEndTime = null;
	/**
	 * 是否补充数据
	 * <li>0:不用补充
	 * <li>1:补充数据(同事必须配置开始时间和结束时间)
	 */
	private int isAddData = 0;
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		LOG.info("spring afterPropertiesSet");
		// 补数据
		if (isAddData == 1)
		{
			DateTimeUtil dtu = new DateTimeUtil(addStartTime);
			// 开始时间
			long startTime = dtu.getLongTime();
			dtu = new DateTimeUtil(addEndTime);
			// 结束时间
			long endTime = dtu.getLongTime();
			// 补数据
			for (long time = startTime; time <= endTime; time += 86400)
			{
				LOG.info("【补充】:" + time);
				getComplaintData(time * 1000L, isAddData);
			}
		}
		else
		{
			getComplaintData(0, isAddData);
		}
	}
	
	/**
	 * 投诉表数据
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
			LOG.info("【开始补投诉表数据】");
			nextDt = new DateTimeUtil(startTime);
		}
		if (0 == type)
		{
			LOG.info("【正常统计投诉表数据】");
			nextDt = new DateTimeUtil();
		}
		// 统计前一天00:00到今天00:00的数据
		String beginTime = getDateFrontNday(nextDt.getDateTime(), -1)+ " 00:00:00";
		String endTime = getDateFrontNday(nextDt.getDateTime(), -1)+ " 23:59:59";
		long t1 = new DateTimeUtil(beginTime).getLongTime();
		long t2 = new DateTimeUtil(endTime).getLongTime();
		LOG.info("【开始时间】" + beginTime);
		LOG.info("【结束时间】" + endTime);
		Map param = new HashMap();
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		param.put("t1", t1);
		param.put("t2", t2);
		//如表之前先去掉重复的数据
		complaintDAO.deleteComplaintData(param);
		List<Map> cityList =complaintDAO.queryCityList();
		Map<String, String> cityMap = new HashMap<String, String>();
		List<Map> complaintList = complaintDAO.queryComplaintData(param);
		// 从237数据库t_zhjk_tousu取出对应数据
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
		LOG.info("【同步的数据】" + complaintList.get(0));
		complaintDAO.insertComplain(complaintList);
	}
	
	/**
	 * 获取前N天的日期
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            天数
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
