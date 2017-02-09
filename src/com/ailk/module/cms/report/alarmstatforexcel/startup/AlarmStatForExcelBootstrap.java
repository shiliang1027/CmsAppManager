package com.ailk.module.cms.report.alarmstatforexcel.startup;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 该java的描述信息
 * 
 * @author shiliang Tel:18661205639
 * @version 1.0
 * @since 2014-5-27 下午4:46:29
 * @category com.ailk.module.cms.report.alarmstatforexcel.startup
 * @copyright
 */
public class AlarmStatForExcelBootstrap {

	private static final Logger LOG = Logger
			.getLogger(AlarmStatForExcelBootstrap.class);

	public static void main(String[] args) {
		try {
			new ClassPathXmlApplicationContext(
					"classpath:com/ailk/module/cms/report/alarmstatforexcel/applicationContext.xml")
					.registerShutdownHook();
			LOG.info("sprint started...");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
