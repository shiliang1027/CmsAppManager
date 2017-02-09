
package com.ailk.module.cms.alarm.indentify.serv;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.module.cms.alarm.indentify.dao.AlarmStatDAO;
import com.ailk.module.cms.alarm.indentify.util.Util;

/**
 * @author yuanj(65478) Tel:18652040107
 * @version 1.0
 * @since 2012-7-3 下午9:54:57
 * @category com.ailk.module.cms.alarm.indentify.serv
 * @copyright 亚信联创 网管产品部
 */
public class AlarmStatServImp implements AlarmStatServ
{

	@Override
	public void onceStat()
	{
		try
		{
			long repairStS = DateUtils.parseDate(REPAIR_ST + " 00:00:00", TPS).getTime() / 1000l;
			long repairEtS = DateUtils.parseDate(REPAIR_ET + " 00:00:00", TPS).getTime() / 1000l;
			for (int i = 0, size = (int) ((repairEtS - repairStS) / DAY) + 1; i < size; i++)
			{
				long startTime = repairStS + DAY * i;
				long endTime = startTime + DAY;
				String timeStr = DateFormatUtils.format(startTime * 1000l, "yyyy-MM-dd");
				LOG.info("Repair Data Begin[" + timeStr + "]!!!");
				String tab = getTableName(tabPre, startTime * 1000l, TP1);
				// 封装Mybatis参数
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("st", startTime);
				map.put("et", endTime);
				map.put("stat_tab", tab);
				map.put("his_tab", getTableName(hisTabPre, startTime * 1000l, TP2));
				map.put("whereConds", getWhereConds());
				asd.createTable(map);
				asd.statAlarm(map);
				LOG.info("Repair Data End[" + timeStr + "]!!!");
			}
		}
		catch (ParseException e)
		{
			LOG.error("Parse Date Exception：", e);
		}
		catch (SQLException e)
		{
			LOG.error("DataBase Operation Exception：", e);
		}
		finally
		{
			// 正常退出
			System.exit(0);
		}
	}

	@Override
	public void cycleStat()
	{
		try
		{
			long t = System.currentTimeMillis() - DAY * 1000l;
			String tab = getTableName(tabPre, t, TP1);
			String timeStr = DateFormatUtils.format(t, "yyyy-MM-dd");
			long startTime = DateUtils.parseDate(timeStr + " 00:00:00", TPS).getTime() / 1000l;
			// 封装Mybatis参数
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("st", startTime);
			map.put("et", startTime + DAY);
			map.put("stat_tab", tab);
			map.put("his_tab", getTableName(hisTabPre, startTime * 1000l, TP2));
			map.put("whereConds", getWhereConds());
			asd.createTable(map);
			LOG.info("Stat Data Begin[" + timeStr + "]!!!");
			asd.statAlarm(map);
			LOG.info("Stat Data End[" + timeStr + "]!!!");
		}
		catch (ParseException e)
		{
			LOG.error("Parse Date Exception：", e);
		}
		catch (SQLException e)
		{
			LOG.error("DataBase Operation Exception：", e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		if (isRepair())
		{
			onceStat();
		}
		else
		{
			cycleStat();
		}
	}

	@Override
	public void destroy() throws Exception
	{
	}

	private String getTableName(String tabPre, long t, String tp)
	{
		return tabPre.toUpperCase() + DateFormatUtils.format(t, tp);
	}

	static private boolean isRepair()
	{
		return !Util.isn(REPAIR_ST) && !Util.isn(REPAIR_ET);
	}

	static public void main(String[] args)
	{
		try
		{
			if (args != null && args.length == 2)
			{
				REPAIR_ST = args[0];
				REPAIR_ET = args[1];
				Pattern p = Pattern.compile(REGEX_TIME);
				if (!p.matcher(REPAIR_ST).matches() || !p.matcher(REPAIR_ET).matches())
				{
					throw new IllegalArgumentException("Time FormatPattern Illegal repairSt："
							+ REPAIR_ST + "，repairEt：" + REPAIR_ET);
				}
			}
			new ClassPathXmlApplicationContext(new String[] {
					"classpath:conf/applicationContext_public.xml",
					"classpath:com/ailk/module/cms/alarm/indentify/applicationContext.xml" })
					.registerShutdownHook();
			if (isRepair())
			{
				LOG.info("Repair Data BeginTime：" + REPAIR_ST + ",EndTime：" + REPAIR_ET);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void setAsd(AlarmStatDAO asd)
	{
		this.asd = asd;
	}

	public void setWhereConds(String whereConds)
	{
		this.whereConds = whereConds;
	}

	public String getWhereConds()
	{
		return whereConds;
	}

	static private String REPAIR_ST;
	static private String REPAIR_ET;
	static private Logger LOG = Logger.getLogger(AlarmStatServImp.class);
	static private String REGEX_TIME = "20[0-9]{2}-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])";
	static private String[] TPS = { "yyyy-MM-dd HH:mm:ss" };
	static private String TP1 = "yyyy_MM_dd";
	static private String TP2 = "yyyy_MM";
	static private long DAY = 24 * 3600;
	private AlarmStatDAO asd;
	private String whereConds;
	private String tabPre = "ta_alarmpro_";
	private String hisTabPre = "ta_historyalarm_";
}
