
package com.ailk.module.cms.ucp.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * Ucp数据库接口实现类
 * 
 * @author mengqiang(65453)
 * @version 1.0
 * @since 2013-10-21 下午5:03:51
 * @category com.ailk.module.cms.ucp.dao
 * @copyright 南京联创科技 网管科技部
 */
public class UcpDAOImp extends JPABaseDAO implements UcpDAO
{

	/**
	 * LOG日志
	 */
	private final static Logger LOG = Logger.getLogger(UcpDAOImp.class);
	/**
	 * 目录
	 */
	private final static String pre = UcpDAOImp.class.getName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ailk.module.cms.ucp.dao.UcpDAO#getDataSetList(java.util.Map)
	 */
	public List<Map> getDataSetList(Map params)
	{
		LOG.info("获取数据集列表:" + params);
		return getSqlSessionTemplate().selectList(pre + ".getDataSetList", params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ailk.module.cms.ucp.dao.UcpDAO#savePerfConfigCheck(java.util.List)
	 */
	public void savePerfConfigCheck(List<Map> checkList)
	{
		LOG.info("保存性能配置验证:" + checkList.size());
		getSqlSessionTemplate().batchInsert(pre + ".savePerfConfigCheck", checkList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ailk.module.cms.ucp.dao.UcpDAO#savePerfDataCheck(java.util.List)
	 */
	public void savePerfDataCheck(List<Map> dataList)
	{
		LOG.info("保存性能数据验证:" + dataList.size());
		getSqlSessionTemplate().batchInsert(pre + ".savePerfDataCheck", dataList);
	}
}