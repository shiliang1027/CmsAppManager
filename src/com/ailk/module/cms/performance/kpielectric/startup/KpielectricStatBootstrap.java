package com.ailk.module.cms.performance.kpielectric.startup;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wangsh(69070) Tel:15051899778
 * @version 1.0
 * @since 2012-8-9 上午11:53:37
 * @category com.ailk.module.cms.performance.kpielectric.startup
 * @copyright 南京联创科技 网管科技部
 */
public class KpielectricStatBootstrap {

	/**
	 * log4j日志记录器
	 */
	private static final Logger LOG = Logger
			.getLogger(KpielectricStatBootstrap.class);

	public static void main(String[] args) {
		try {
			new ClassPathXmlApplicationContext(
					"classpath:com/ailk/module/cms/performance/kpielectric/applicationContext.xml")
					.registerShutdownHook();
			LOG.info("sprint started...");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
