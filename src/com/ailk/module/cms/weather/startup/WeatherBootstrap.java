package com.ailk.module.cms.weather.startup;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 该java的描述信息
 * 
 * @author shiliang Tel:18661205639
 * @version 1.0
 * @since 2014-6-15 下午2:07:14
 * @category com.ailk.module.cms.weather.startup
 * @copyright
 */
public class WeatherBootstrap {
	private static final Logger log = Logger.getLogger(WeatherBootstrap.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new ClassPathXmlApplicationContext(
					"classpath:com/ailk/module/cms/weather/applicationContext.xml")
					.registerShutdownHook();
			log.info("weather started...");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
