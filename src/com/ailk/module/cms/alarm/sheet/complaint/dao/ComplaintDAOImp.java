
package com.ailk.module.cms.alarm.sheet.complaint.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.linkage.system.jdbc.jpa.JPABaseDAO;
import com.linkage.system.jdbc.jpa.SqlSessionTemplate;

/**
 * @author mengqiang (65453)
 * @version 1.0
 * @since 2013-4-22
 * @category com.ailk.module.cms.alarm.sheet.complaint.dao
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 */
public class ComplaintDAOImp extends JPABaseDAO implements ComplaintDAO
{

	private final static Logger log = Logger.getLogger(ComplaintDAOImp.class);
	private final static String alias = ComplaintDAOImp.class.getName() + ".";
	// 237数据库
	private SqlSessionTemplate tousuStmpt;
	// 本地193或110
	private SqlSessionTemplate eomsStmpt;

	public SqlSessionTemplate getEomsStmpt()
	{
		return eomsStmpt;
	}

	public void setEomsStmpt(SqlSessionTemplate eomsStmpt)
	{
		this.eomsStmpt = eomsStmpt;
	}

	public SqlSessionTemplate getTousuStmpt()
	{
		return tousuStmpt;
	}

	public void setTousuStmpt(SqlSessionTemplate tousuStmpt)
	{
		this.tousuStmpt = tousuStmpt;
	}

	@Override
	public List<Map> queryComplaintData(Map params)
	{
		return tousuStmpt.selectList(alias + "queryComplaintData", params);
	}

	@Override
	public void insertComplain(List<Map> paramList)
	{
		eomsStmpt.batchInsert(alias + "insertComplain", paramList);
	}

	@Override
	public void deleteComplaintData(Map params)
	{
		// 删除本地的
		eomsStmpt.delete(alias + "deleteComplaintData", params);
	}

	@Override
	public List<Map> queryCityList()
	{
		return eomsStmpt.selectList(alias + "queryCityList");
	}
}
