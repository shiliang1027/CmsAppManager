
package com.ailk.module.cms.scene.usercountstat.startup;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ʵʱͳ�Ƴ���/�����û�����
 * 
 * @author loukp(No:69139 tel:15951882231)
 * @version 1.0.0
 * @category com.ailk.module.cms.scene.usercountstat.startup
 * @since 2012-08-12
 * @copyright �������� ���ܿ�����
 */
public class SceneUserCountStatBootstrap
{
	private static final Logger LOG = Logger.getLogger(SceneUserCountStatBootstrap.class);

	public static void main(String[] args)
	{
		try
		{
			new ClassPathXmlApplicationContext(
					"classpath:com/ailk/module/cms/scene/usercountstat/applicationContext_scene_userCount.xml")
					.registerShutdownHook();
			LOG.info("spring started...");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}
