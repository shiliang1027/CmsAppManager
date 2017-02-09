
package com.ailk.module.cms.alarm.alarmassociate.startup;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author bilei5 (69064)
 * @version 1.0
 * @since 2012-7-4
 * @category com.ailk.module.cms.alarm.alarmassociate.startup
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 */
public class AlarmAssociateStatBootstrap
{

	/**
	 * log4jÈÕÖ¾¼ÇÂ¼Æ÷
	 */
	private static final Logger LOG = Logger.getLogger(AlarmAssociateStatBootstrap.class);

	public static void main(String[] args)
	{
		try
		{
			new ClassPathXmlApplicationContext(
					"classpath:com/ailk/module/cms/alarm/alarmassociate/applicationContext.xml")
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
