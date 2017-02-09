package com.ailk.module.cms.weather.serv;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ailk.module.cms.system.basesupport.BaseSupportServ;
import com.ailk.module.cms.weather.dao.WeatherDAO;

/**
 * 该java的描述信息
 * 
 * @author shiliang Tel:18661205639
 * @version 1.0
 * @since 2014-6-15 下午2:12:45
 * @category com.ailk.module.cms.weather.serv
 * @copyright
 */
public class WeatherServImp implements BaseSupportServ {
	private static final Logger log = Logger.getLogger(WeatherServImp.class);
	private WeatherDAO dao;
	private String weatherQueryUrl;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		List<Map> citys = dao.findCitys();
		log.info(citys);
		SAXReader reader = new SAXReader();
		Document doc = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		dao.deleteWeather();
		for (Map m : citys) {
			String weather = WeatherBaiduService.getWeather(weatherQueryUrl,String.valueOf(m.get("city_name")), "xml");
			try {
				doc = reader.read(new ByteArrayInputStream(weather.getBytes()));
				Element root = doc.getRootElement();
				String date = root.elementText("date").trim();
				Element results = root.element("results");
				String currentCity = results.elementText("currentCity");
				Element weather_data = results.element("weather_data");
				List<Element> dates = weather_data.elements("date");
				List<Element> dayPictureUrls = weather_data.elements("dayPictureUrl");
				List<Element> nightPictureUrls = weather_data.elements("nightPictureUrl");
				List<Element> weathers = weather_data.elements("weather");
				List<Element> winds = weather_data.elements("wind");
				List<Element> temperatures = weather_data.elements("temperature");
				log.info(date);
				log.info(currentCity);
				long currentDate = df.parse(date).getTime()/1000L;
				for(int i=0;i<dates.size();i++){
					log.info(dates.get(i).getText());
					log.info(weathers.get(i).getText());
					log.info(winds.get(i).getText());
					log.info(temperatures.get(i).getText());
					String dayPic = dayPictureUrls.get(i).getText();
					String nightPic = nightPictureUrls.get(i).getText();
					log.info(dayPic);
					log.info(nightPic);
					dayPic = dayPic.substring(dayPic.lastIndexOf("/")+1);
					nightPic = nightPic.substring(nightPic.lastIndexOf("/")+1);
					Map param = new HashMap();
					param.put("city_id", m.get("city_id"));
					param.put("weather_date", currentDate);
					param.put("weather_date_detail", dates.get(i).getText());
					param.put("daypicture", dayPic);
					param.put("nightpicture", nightPic);
					param.put("weathers", weathers.get(i).getText());
					param.put("winds", winds.get(i).getText());
					param.put("temperatures", temperatures.get(i).getText());
					param.put("sque", i);
					currentDate+=24*3600;
					dao.updateWeather(param);
				}
			} catch (DocumentException e) {
				log.error(e);
			}
		}
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub

	}

	public WeatherDAO getDao() {
		return dao;
	}

	public void setDao(WeatherDAO dao) {
		this.dao = dao;
	}

	public String getWeatherQueryUrl() {
		return weatherQueryUrl;
	}

	public void setWeatherQueryUrl(String weatherQueryUrl) {
		this.weatherQueryUrl = weatherQueryUrl;
	}
}
