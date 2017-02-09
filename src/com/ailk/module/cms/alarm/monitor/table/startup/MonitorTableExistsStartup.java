
package com.ailk.module.cms.alarm.monitor.table.startup;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yuanj(65478) Tel:18652040107
 * @version 1.0
 * @since 2012-7-4 下午1:00:55
 * @category com.ailk.module.cms.alarm.monitor.table.startup
 * @copyright 亚信联创 网管产品部
 */
public class MonitorTableExistsStartup
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			new ClassPathXmlApplicationContext(new String[] {
					"classpath:conf/applicationContext_public.xml",
					"classpath:com/ailk/module/cms/alarm/monitor/table/applicationContext.xml" })
					.registerShutdownHook();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}
