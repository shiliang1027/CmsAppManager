
package com.ailk.module.cms.alarm.sheet.alarmdispatch.startup;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wangsh5 (69070)
 * @version 1.0
 * @since 2012-7-26
 * @category com.ailk.module.cms.alarm.sheet.alarmdispatch.startup
 */
public class AlarmDispatchStatBootstrap
{

	/**
	 * log4jÈÕÖ¾¼ÇÂ¼Æ÷
	 */
	private static final Logger LOG = Logger.getLogger(AlarmDispatchStatBootstrap.class);

	public static void main(String[] args)
	{
		try
		{
			new ClassPathXmlApplicationContext(
					"classpath:com/ailk/module/cms/alarm/sheet/alarmdispatch/applicationContext.xml")
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
