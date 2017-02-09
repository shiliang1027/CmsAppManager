
package com.ailk.module.cms.alarm.quittable.startup;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gaoqc(69151) Tel:18652058796
 * @version 1.0
 * @since 2012-9-26 下午2:19:30
 * @category com.ailk.module.cms.alarm.quittable.startup
 * @copyright 南京联创科技 网管科技部
 */
public class QuitServiceStatBootstartup
{

	/**
	 * 日志记录
	 */
	private static final Logger LOG = Logger.getLogger(QuitServiceStatBootstartup.class);

	public static void main(String[] args)
	{
		try
		{
			new ClassPathXmlApplicationContext(
					"classpath:com/ailk/module/cms/alarm/quittable/applicationContext.xml")
					.registerShutdownHook();
			LOG.info("start print.......");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}
