package com.ailk.module.cms.typhoon.startup;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 该java的描述信息
 * @author shiliang Tel:18661205639
 * @version 1.0
 * @since 2014-6-16 下午3:56:46
 * @category com.ailk.module.cms.typhoon.startup
 * @copyright 
 */
public class TyphoonBootstrap {
	private static final Logger log = Logger.getLogger(TyphoonBootstrap.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new ClassPathXmlApplicationContext(
					"classpath:com/ailk/module/cms/typhoon/applicationContext.xml")
					.registerShutdownHook();
			log.info("台风数据统计 started...");
		} catch (Exception e) {
			log.info("台风数据统计 Exception...");
			log.info(e);
			e.printStackTrace();
			System.exit(1);
		}
	}

}
