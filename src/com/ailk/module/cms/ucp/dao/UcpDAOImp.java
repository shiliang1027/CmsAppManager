
package com.ailk.module.cms.ucp.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * Ucp���ݿ�ӿ�ʵ����
 * 
 * @author mengqiang(65453)
 * @version 1.0
 * @since 2013-10-21 ����5:03:51
 * @category com.ailk.module.cms.ucp.dao
 * @copyright �Ͼ������Ƽ� ���ܿƼ���
 */
public class UcpDAOImp extends JPABaseDAO implements UcpDAO
{

	/**
	 * LOG��־
	 */
	private final static Logger LOG = Logger.getLogger(UcpDAOImp.class);
	/**
	 * Ŀ¼
	 */
	private final static String pre = UcpDAOImp.class.getName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ailk.module.cms.ucp.dao.UcpDAO#getDataSetList(java.util.Map)
	 */
	public List<Map> getDataSetList(Map params)
	{
		LOG.info("��ȡ���ݼ��б�:" + params);
		return getSqlSessionTemplate().selectList(pre + ".getDataSetList", params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ailk.module.cms.ucp.dao.UcpDAO#savePerfConfigCheck(java.util.List)
	 */
	public void savePerfConfigCheck(List<Map> checkList)
	{
		LOG.info("��������������֤:" + checkList.size());
		getSqlSessionTemplate().batchInsert(pre + ".savePerfConfigCheck", checkList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ailk.module.cms.ucp.dao.UcpDAO#savePerfDataCheck(java.util.List)
	 */
	public void savePerfDataCheck(List<Map> dataList)
	{
		LOG.info("��������������֤:" + dataList.size());
		getSqlSessionTemplate().batchInsert(pre + ".savePerfDataCheck", dataList);
	}
}