
package com.ailk.module.cms.alarm.alarmsheet.startup;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 告警工单启动程序入口
 * 
 * @author mengqiang (65453)
 * @version 1.0
 * @since 2012-6-29
 * @category com.ailk.module.cms.alarm.alarmsheet.startup
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 */
public class AlarmSheetStatBootstrap
{

	/**
	 * log4j日志记录器
	 */
	private static final Logger LOG = Logger.getLogger(AlarmSheetStatBootstrap.class);

	public static void main(String[] args)
	{
		try
		{
			new ClassPathXmlApplicationContext(
					"classpath:com/ailk/module/cms/alarm/alarmsheet/applicationContext.xml")
					.registerShutdownHook();
			LOG.info("sprint started...");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}