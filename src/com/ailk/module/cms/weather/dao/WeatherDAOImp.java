package com.ailk.module.cms.weather.dao;

import java.util.List;
import java.util.Map;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * 该java的描述信息
 * @author shiliang Tel:18661205639
 * @version 1.0
 * @since 2014-6-15 下午2:10:22
 * @category com.ailk.module.cms.weather.dao
 * @copyright 
 */
public class WeatherDAOImp extends JPABaseDAO implements WeatherDAO {
	private final String sqlPre = WeatherDAOImp.class.getName();
	@Override
	public List<Map> findCitys() {
		return getSqlSessionTemplate().selectList(sqlPre+".findCitys");
	}
	@Override
	public void insertWeather(Map param) {
		getSqlSessionTemplate().insert(sqlPre+".insertWeather",param);
	}
	@Override
	public void deleteWeather() {
		getSqlSessionTemplate().delete(sqlPre+".deleteWeather");
	}
	@Override
	public void updateWeather(Map param) {
		// TODO Auto-generated method stub
		getSqlSessionTemplate().update(sqlPre+".updateWeather", param);
	}

}
