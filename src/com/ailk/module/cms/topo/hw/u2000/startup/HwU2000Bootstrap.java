
package com.ailk.module.cms.topo.hw.u2000.startup;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * U2000��������
 * 
 * @author mengqiang(65453)
 * @version 1.0
 * @since 2013-10-21 ����4:57:38
 * @category com.ailk.module.cms.ucp.startup
 * @copyright �Ͼ������Ƽ� ���ܿƼ���
 */
public class HwU2000Bootstrap
{

	/**
	 * log4j��־��¼��
	 */
	private static final Logger LOG = Logger.getLogger(HwU2000Bootstrap.class);

	public static void main(String[] args)
	{
		try
		{
			new ClassPathXmlApplicationContext(
					"classpath:com/ailk/module/cms/topo/hw/u2000/applicationContext.xml")
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