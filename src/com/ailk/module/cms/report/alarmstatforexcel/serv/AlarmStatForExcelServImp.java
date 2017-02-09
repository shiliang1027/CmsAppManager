package com.ailk.module.cms.report.alarmstatforexcel.serv;

import com.ailk.module.cms.report.alarmstatforexcel.dao.AlarmStatForExcelDAO;
import com.ailk.module.cms.system.basesupport.BaseSupportServ;

/**
 * 该java的描述信息
 * @author shiliang Tel:18661205639
 * @version 1.0
 * @since 2014-5-27 下午4:48:28
 * @category com.ailk.module.cms.report.alarmstatforexcel.serv
 * @copyright 
 */
public class AlarmStatForExcelServImp implements BaseSupportServ{

	private AlarmStatForExcelDAO dao;

	public AlarmStatForExcelDAO getDao() {
		return dao;
	}

	public void setDao(AlarmStatForExcelDAO dao) {
		this.dao = dao;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		dao.delTmp();
		dao.statSubTotal();
		dao.statSheet();
		dao.statProject();
		dao.statException();
		dao.updateAll();
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
