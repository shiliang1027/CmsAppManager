package com.ailk.module.cms.report.alarmstatforexcel.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * 该java的描述信息
 * @author shiliang Tel:18661205639
 * @version 1.0
 * @since 2014-5-27 下午4:53:50
 * @category com.ailk.module.cms.report.alarmstatforexcel.dao
 * @copyright 
 */
public class AlarmStatForExcelDAOImp extends JPABaseDAO implements
		AlarmStatForExcelDAO {
	private final String sqlPre = AlarmStatForExcelDAOImp.class.getName();
	private static final Logger LOG = Logger.getLogger(AlarmStatForExcelDAOImp.class);
	@Override
	public void delTmp() {
		// TODO Auto-generated method stub
		int result = getSqlSessionTemplate().delete(sqlPre + ".delTmp");
		LOG.info("[删除]"+result);
	}

	@Override
	public void statSubTotal() {
		// TODO Auto-generated method stub
		Map param = new HashMap();
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		param.put("stattime", cal.getTimeInMillis()/1000L);
		LOG.info("[小计]"+param);
		int result=0;
		for(int i=1;i<=5;i++){
			result = getSqlSessionTemplate().insert(sqlPre + ".subtotal"+i, param);
			LOG.info("[小计]"+result);
		}
	}

	@Override
	public void statSheet() {
		Map param = new HashMap();
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		param.put("stattime", cal.getTimeInMillis()/1000L);
		LOG.info("[工单]"+param);
		int result=0;
		for(int i=1;i<=5;i++){
			result = getSqlSessionTemplate().insert(sqlPre + ".sheet"+i, param);
			LOG.info("[工单]"+result);
		}
	}

	@Override
	public void statProject() {
		// TODO Auto-generated method stub
		Map param = new HashMap();
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		param.put("stattime", cal.getTimeInMillis()/1000L);
		LOG.info("[工程]"+param);
		int result=0;
		for(int i=1;i<=5;i++){
			result = getSqlSessionTemplate().insert(sqlPre + ".project"+i, param);
			LOG.info("[工程]"+result);
		}
	}

	@Override
	public void statException() {
		// TODO Auto-generated method stub
		Map param = new HashMap();
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		param.put("stattime", cal.getTimeInMillis()/1000L);
		LOG.info("[异常]"+param);
		int result=0;
		for(int i=1;i<=5;i++){
			result = getSqlSessionTemplate().insert(sqlPre + ".excep"+i, param);
			LOG.info("[异常]"+result);
		}
	}

	@Override
	public void updateAll() {
		// TODO Auto-generated method stub
		Map param = new HashMap();
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		param.put("stattime", cal.getTimeInMillis()/1000L);
		LOG.info("[更新]"+param);
		int result=0;
		for(int i=1;i<=25;i++){
			result = getSqlSessionTemplate().update(sqlPre + ".update"+i, param);
			LOG.info("[更新]"+result);
		}
	}

}
