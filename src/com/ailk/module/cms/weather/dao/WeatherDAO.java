package com.ailk.module.cms.weather.dao;

import java.util.List;
import java.util.Map;

/**
 * 该java的描述信息
 * @author shiliang Tel:18661205639
 * @version 1.0
 * @since 2014-6-15 下午2:09:51
 * @category com.ailk.module.cms.weather.dao
 * @copyright 
 */
public interface WeatherDAO {

	public abstract List<Map> findCitys();

	public abstract void insertWeather(Map param);

	public abstract void deleteWeather();

	public abstract void updateWeather(Map param);

}
