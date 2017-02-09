package com.ailk.module.cms.typhoon.serv;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ailk.module.cms.system.basesupport.BaseSupportServ;
import com.ailk.module.cms.typhoon.dao.TyphoonDAO;

/**
 * 该java的描述信息
 * @author shiliang Tel:18661205639
 * @version 1.0
 * @since 2014-6-16 下午3:56:24
 * @category com.ailk.module.cms.typhoon.serv
 * @copyright 
 */
public class TyphoonServImp implements BaseSupportServ {
	private static final Logger log = Logger.getLogger(TyphoonServImp.class);
	private TyphoonDAO dao;
	private String typhoonListUrl;
	private String typhoonDataUrl;
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		log.info("台风数据处理");
		String typhoonList = TyphoonService.getRequestResult(typhoonListUrl);
		log.info("台风列表:"+typhoonList);
		SAXReader reader = new SAXReader();
		Document doc = null;
		Document datadoc = null;
		doc = reader.read(new ByteArrayInputStream(typhoonList.getBytes()));
		Element root = doc.getRootElement();
		List<Element> tfProps = root.elements("tfProps");
		dao.deleteTyphoonData();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		for(Element tfProp:tfProps){
			log.info("台风列表---------------");
			String code = tfProp.attributeValue("code");
			String title = tfProp.attributeValue("title");
			String data = tfProp.attributeValue("data");
			String city = tfProp.attributeValue("city");
			String incPoint = tfProp.attributeValue("incPoint");
			Map typhoonMap = new HashMap();
			typhoonMap.put("code", code);
			typhoonMap.put("title", title);
//			log.info(code);
//			log.info(title);
//			log.info(data);
//			log.info(city);
//			log.info(incPoint);
			String typhoonData = TyphoonService.getRequestResult(typhoonDataUrl.replace("{dataFile}", data));
			log.info("台风数据:"+typhoonData);
			datadoc = reader.read(new ByteArrayInputStream(typhoonData.getBytes()));
			Element dataroot = datadoc.getRootElement();
			List<Element> datatfProps = dataroot.elements("tfProps");
			for(Element datatfProp:datatfProps){
				log.info("台风数据---------------");
				String y = datatfProp.attributeValue("y");
				String m = datatfProp.attributeValue("m");
				String d = datatfProp.attributeValue("d");
				String h = datatfProp.attributeValue("h");
				String t = datatfProp.attributeValue("t");
				String jd = datatfProp.attributeValue("jd");
				String wd = datatfProp.attributeValue("wd");
				String qy = datatfProp.attributeValue("qy");
				String fs = datatfProp.attributeValue("fs");
				String b7 = datatfProp.attributeValue("en7");
				String b10 = datatfProp.attributeValue("en10");
				String fx = datatfProp.attributeValue("fx");
				String sd = datatfProp.attributeValue("sd");
				String fl = datatfProp.attributeValue("fl");
				Map typhoonDataMap = new HashMap();
				typhoonDataMap.put("code", code);
				typhoonDataMap.put("title", title);
				typhoonDataMap.put("daltime", df.parse(y+m+d+h+t+"00").getTime()/1000L);
				typhoonDataMap.put("windspeed", fs);
				typhoonDataMap.put("longitude", jd);
				typhoonDataMap.put("latitude", wd);
				typhoonDataMap.put("pneumatic", qy);
				typhoonDataMap.put("wind7Radius", b7);
				typhoonDataMap.put("wind10Radius", b10);
				log.info(typhoonDataMap);
				try{
					dao.saveTyphoonData(typhoonDataMap);
				}catch(Exception e){
					log.error(e);
				}
//				log.info(m);
//				log.info(d);
//				log.info(h);
//				log.info(t);
//				log.info(jd);
//				log.info(wd);
//				log.info(qy);
//				log.info(fs);
//				log.info(b7);
//				log.info(b10);
//				log.info(fx);
//				log.info(sd);
//				log.info(fl);
			}
		}
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public TyphoonDAO getDao() {
		return dao;
	}

	public void setDao(TyphoonDAO dao) {
		this.dao = dao;
	}

	public String getTyphoonListUrl() {
		return typhoonListUrl;
	}

	public void setTyphoonListUrl(String typhoonListUrl) {
		this.typhoonListUrl = typhoonListUrl;
	}

	public String getTyphoonDataUrl() {
		return typhoonDataUrl;
	}

	public void setTyphoonDataUrl(String typhoonDataUrl) {
		this.typhoonDataUrl = typhoonDataUrl;
	}

	
}
