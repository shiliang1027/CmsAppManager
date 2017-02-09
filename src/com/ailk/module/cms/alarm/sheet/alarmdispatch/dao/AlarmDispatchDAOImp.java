
package com.ailk.module.cms.alarm.sheet.alarmdispatch.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

public class AlarmDispatchDAOImp extends JPABaseDAO implements AlarmDispatchDAO
{

	private final static Logger log = Logger.getLogger(AlarmDispatchDAOImp.class);
	private final static String alias = AlarmDispatchDAOImp.class.getName() + ".";

	public List<Map> getExistDispatchTabList(Map param)
	{
		return getSqlSessionTemplate().selectList(alias + "getExistDispatchTabList",
				param);
	}

	public int createDispatchTabList(Map param)
	{
		return getSqlSessionTemplate().update(alias + "createDispatchTabList", param);
	}

	@Override
	public void saveDispatchList(Map param)
	{
		log.info("dispatchList=======================hhhhhhhhhhhhhhhhhhhhhh");
		getSqlSessionTemplate().selectList(alias + "saveDispatchList", param);
	}

	@Override
	public void updateDispatchList(Map param)
	{
		getSqlSessionTemplate().selectList(alias + "updateDispatchList", param);
	}
}