package com.ailk.module.cms.typhoon.dao;

import java.util.Map;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * 该java的描述信息
 * @author shiliang Tel:18661205639
 * @version 1.0
 * @since 2014-6-16 下午3:55:56
 * @category com.ailk.module.cms.typhoon.dao
 * @copyright 
 */
public class TyphoonDAOImp extends JPABaseDAO implements TyphoonDAO {
	private final String sqlPre = TyphoonDAOImp.class.getName();
	@Override
	public void saveTyphoonData(Map typhoonDataMap) {
		getSqlSessionTemplate().insert(sqlPre+".saveTyphoonData",typhoonDataMap);
	}
	@Override
	public void deleteTyphoonData() {
		getSqlSessionTemplate().delete(sqlPre+".deleteTyphoonData");
	}

}
