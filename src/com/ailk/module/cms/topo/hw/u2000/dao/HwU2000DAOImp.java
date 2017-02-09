
package com.ailk.module.cms.topo.hw.u2000.dao;

import java.util.List;
import java.util.Map;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * 华为U2000拓扑生产DAO
 * 
 * @author mengqiang(65453)
 * @version 1.0
 * @since 2014-3-6 上午9:36:38
 * @category com.ailk.module.cms.topo.hw.u2000.dao
 * @copyright 南京联创科技 网管科技部
 */
public class HwU2000DAOImp extends JPABaseDAO
{

	/**
	 * sql前缀
	 */
	private final String sqlPre = HwU2000DAOImp.class.getName();

	/**
	 * 获取EMS信息
	 * 
	 * @param params
	 *            参数
	 * @return
	 */
	public List<Map> getEmsInfo(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getEmsInfo", param);
	}

	/**
	 * 获取设备信息
	 * 
	 * @param params
	 *            参数
	 * @return
	 */
	public List<Map> getMoInfo(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getMoInfo", param);
	}

	/**
	 * 获取设备型号跟设备类型的对应关系表
	 * 
	 * @param param
	 * @return
	 */
	public List<Map> getMoModel(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getMoModel", param);
	}
	
	/**
	 * 批量插入数据
	 * 
	 * @param param
	 *            参数
	 */
	public void batchSaveSystemInfo(List<Map> param)
	{
		try
		{
			getSqlSessionTemplate().batchInsert(sqlPre + ".batchSaveSystemInfo", param);
		}
		catch (Exception e)
		{
			System.out.println("批量更新报错:" + e.getMessage());
			for (Map map : param)
			{
				getSqlSessionTemplate().insert(sqlPre + ".batchSaveSystemInfo", map);
			}
		}
	}

	/**
	 * 批量插入数据
	 * 
	 * @param param
	 *            参数
	 */
	public void batchSaveMoInfo(List<Map> param)
	{
		try
		{
			getSqlSessionTemplate().batchInsert(sqlPre + ".batchSaveMoInfo", param);
		}
		catch (Exception e)
		{
			System.out.println("批量更新报错:" + e.getMessage());
			for (Map map : param)
			{
				getSqlSessionTemplate().insert(sqlPre + ".batchSaveMoInfo", map);
			}
		}
	}

	/**
	 * 批量插入数据
	 * 
	 * @param param
	 *            参数
	 */
	public void batchSaveTranSystemInfo(List<Map> param)
	{
		try
		{
			getSqlSessionTemplate().batchInsert(sqlPre + ".batchSaveTranSystemInfo",
					param);
		}
		catch (Exception e)
		{
			System.out.println("批量更新报错:" + e.getMessage());
			for (Map map : param)
			{
				getSqlSessionTemplate().insert(sqlPre + ".batchSaveTranSystemInfo", map);
			}
		}
	}

	/**
	 * 批量插入数据
	 * 
	 * @param param
	 *            参数
	 */
	public void bacthSaveViewLinkInfo(List<Map> param)
	{
		try
		{
			getSqlSessionTemplate().batchInsert(sqlPre + ".bacthSaveViewLinkInfo", param);
		}
		catch (Exception e)
		{
			System.out.println("批量更新报错:" + e.getMessage());
			for (Map map : param)
			{
				getSqlSessionTemplate().insert(sqlPre + ".bacthSaveViewLinkInfo", map);
			}
		}
	}

	/**
	 * 批量更新数据
	 * 
	 * @param param
	 *            参数
	 */
	public void batchUpdateMoInfo(List<Map> param)
	{
		try
		{
			getSqlSessionTemplate().batchUpdate(sqlPre + ".batchUpdateMoInfo", param);
		}
		catch (Exception e)
		{
			System.out.println("批量更新报错:" + e.getMessage());
			for (Map map : param)
			{
				getSqlSessionTemplate().update(sqlPre + ".batchUpdateMoInfo", map);
			}
		}
	}

	/**
	 * 删除指定ems网元系统信息
	 * 
	 * @param param
	 *            参数
	 */
	public void deleteMoSystemInfo(Map param)
	{
		getSqlSessionTemplate().delete(sqlPre + ".deleteMoSystemInfo", param);
	}

	/**
	 * 删除指定ems系统信息
	 * 
	 * @param param
	 *            参数
	 */
	public void deleteSystemInfo(Map param)
	{
		getSqlSessionTemplate().delete(sqlPre + ".deleteSystemInfo", param);
	}

	/**
	 * 删除指定ems链路信息
	 * 
	 * @param param
	 *            参数
	 */
	public void deleteSystemLinkInfo(Map param)
	{
		getSqlSessionTemplate().delete(sqlPre + ".deleteSystemLinkInfo", param);
	}
}