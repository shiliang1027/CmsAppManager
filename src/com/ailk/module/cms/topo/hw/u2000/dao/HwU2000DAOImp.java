
package com.ailk.module.cms.topo.hw.u2000.dao;

import java.util.List;
import java.util.Map;

import com.linkage.system.jdbc.jpa.JPABaseDAO;

/**
 * ��ΪU2000��������DAO
 * 
 * @author mengqiang(65453)
 * @version 1.0
 * @since 2014-3-6 ����9:36:38
 * @category com.ailk.module.cms.topo.hw.u2000.dao
 * @copyright �Ͼ������Ƽ� ���ܿƼ���
 */
public class HwU2000DAOImp extends JPABaseDAO
{

	/**
	 * sqlǰ׺
	 */
	private final String sqlPre = HwU2000DAOImp.class.getName();

	/**
	 * ��ȡEMS��Ϣ
	 * 
	 * @param params
	 *            ����
	 * @return
	 */
	public List<Map> getEmsInfo(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getEmsInfo", param);
	}

	/**
	 * ��ȡ�豸��Ϣ
	 * 
	 * @param params
	 *            ����
	 * @return
	 */
	public List<Map> getMoInfo(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getMoInfo", param);
	}

	/**
	 * ��ȡ�豸�ͺŸ��豸���͵Ķ�Ӧ��ϵ��
	 * 
	 * @param param
	 * @return
	 */
	public List<Map> getMoModel(Map param)
	{
		return getSqlSessionTemplate().selectList(sqlPre + ".getMoModel", param);
	}
	
	/**
	 * ������������
	 * 
	 * @param param
	 *            ����
	 */
	public void batchSaveSystemInfo(List<Map> param)
	{
		try
		{
			getSqlSessionTemplate().batchInsert(sqlPre + ".batchSaveSystemInfo", param);
		}
		catch (Exception e)
		{
			System.out.println("�������±���:" + e.getMessage());
			for (Map map : param)
			{
				getSqlSessionTemplate().insert(sqlPre + ".batchSaveSystemInfo", map);
			}
		}
	}

	/**
	 * ������������
	 * 
	 * @param param
	 *            ����
	 */
	public void batchSaveMoInfo(List<Map> param)
	{
		try
		{
			getSqlSessionTemplate().batchInsert(sqlPre + ".batchSaveMoInfo", param);
		}
		catch (Exception e)
		{
			System.out.println("�������±���:" + e.getMessage());
			for (Map map : param)
			{
				getSqlSessionTemplate().insert(sqlPre + ".batchSaveMoInfo", map);
			}
		}
	}

	/**
	 * ������������
	 * 
	 * @param param
	 *            ����
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
			System.out.println("�������±���:" + e.getMessage());
			for (Map map : param)
			{
				getSqlSessionTemplate().insert(sqlPre + ".batchSaveTranSystemInfo", map);
			}
		}
	}

	/**
	 * ������������
	 * 
	 * @param param
	 *            ����
	 */
	public void bacthSaveViewLinkInfo(List<Map> param)
	{
		try
		{
			getSqlSessionTemplate().batchInsert(sqlPre + ".bacthSaveViewLinkInfo", param);
		}
		catch (Exception e)
		{
			System.out.println("�������±���:" + e.getMessage());
			for (Map map : param)
			{
				getSqlSessionTemplate().insert(sqlPre + ".bacthSaveViewLinkInfo", map);
			}
		}
	}

	/**
	 * ������������
	 * 
	 * @param param
	 *            ����
	 */
	public void batchUpdateMoInfo(List<Map> param)
	{
		try
		{
			getSqlSessionTemplate().batchUpdate(sqlPre + ".batchUpdateMoInfo", param);
		}
		catch (Exception e)
		{
			System.out.println("�������±���:" + e.getMessage());
			for (Map map : param)
			{
				getSqlSessionTemplate().update(sqlPre + ".batchUpdateMoInfo", map);
			}
		}
	}

	/**
	 * ɾ��ָ��ems��Ԫϵͳ��Ϣ
	 * 
	 * @param param
	 *            ����
	 */
	public void deleteMoSystemInfo(Map param)
	{
		getSqlSessionTemplate().delete(sqlPre + ".deleteMoSystemInfo", param);
	}

	/**
	 * ɾ��ָ��emsϵͳ��Ϣ
	 * 
	 * @param param
	 *            ����
	 */
	public void deleteSystemInfo(Map param)
	{
		getSqlSessionTemplate().delete(sqlPre + ".deleteSystemInfo", param);
	}

	/**
	 * ɾ��ָ��ems��·��Ϣ
	 * 
	 * @param param
	 *            ����
	 */
	public void deleteSystemLinkInfo(Map param)
	{
		getSqlSessionTemplate().delete(sqlPre + ".deleteSystemLinkInfo", param);
	}
}