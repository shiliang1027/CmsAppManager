package com.ailk.module.cms.alarm.sheet.complaint.startup;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author wangsh(69070)
 * @version 1.0
 * @since 2013-1-24 上午11:04:23
 * @category com.ailk.module.cms.alarm.roamin.startup
 * @copyright 南京联创科技 网管科技部
 *
 */
public class ComplaintStatBootstartup
{
	private static final Logger LOG = Logger.getLogger(ComplaintStatBootstartup.class);

	public static void main(String[] args)
	{
		try
		{
			new ClassPathXmlApplicationContext(
					"classpath:com/ailk/module/cms/alarm/sheet/complaint/applicationContext.xml")
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
