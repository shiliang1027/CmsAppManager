
package com.ailk.module.cms.ucp.serv;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ailk.module.cms.system.basesupport.BaseSupportServ;
import com.ailk.module.cms.ucp.dao.UcpDAO;
import com.linkage.system.utils.DateTimeUtil;

/**
 * UCPҵ�������
 * 
 * @author mengqiang(65453)
 * @version 1.0
 * @since 2013-10-21 ����5:02:22
 * @category com.ailk.module.cms.ucp.serv
 * @copyright �Ͼ������Ƽ� ���ܿƼ���
 */
public class UcpServImp implements BaseSupportServ
{

	/**
	 * log4j��־��¼��
	 */
	private static final Logger LOG = Logger.getLogger(UcpServImp.class);
	/**
	 * UCP���ݿ�ӿ�
	 */
	private UcpDAO ucpDao = null;
	/**
	 * �ļ�·��
	 */
	private String path = null;

	public void afterPropertiesSet() throws Exception
	{
		List<String> fileList = getNewPerfFiles();
		LOG.info("�����ļ��б�" + fileList);
		initPerfConfigCheck(fileList);
		initPerfDataCheck(fileList);
	}

	// ������������֤
	private void initPerfConfigCheck(List<String> fileList)
	{
		Map params = new HashMap();
		String[] nameArray = null;
		List<Map> configList = null;
		for (String name : fileList)
		{
			try
			{
				nameArray = name.split("_");
				params.put("dataset_id", nameArray[0]);
				configList = ucpDao.getDataSetList(params);
				savePerfConfigCheck(configList, nameArray, name);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// ��������������֤
	private void savePerfConfigCheck(List<Map> configList, String[] nameArray, String fileName)
	{
		List<Map> checkList = new ArrayList<Map>();
		FileInputStream fis = null;
		DataInputStream dis = null;
		try
		{
			// ��ȡ�ļ�
			Map<String, String> checkMap = new HashMap<String, String>();
			fis = new FileInputStream(new File(path + File.separator + fileName));
			dis = new DataInputStream(fis);
			String str1 = null;
			while ((str1 = dis.readLine()) != null)
			{
				String[] txtTitle = str1.split("\t");
				for (String name : txtTitle)
				{
					checkMap.put(name, name);
				}
				break;
			}
			// ��֤������
			for (Map cm : configList)
			{
				String name = String.valueOf(cm.get("name"));
				if (!checkMap.containsKey(name))
				{
					Map paramMap = new HashMap();
					paramMap.put("dataset_id", nameArray[0]);
					paramMap.put("task_id", nameArray[1]);
					paramMap.put("subtask_no", nameArray[2]);
					paramMap.put("start_time", getTime(nameArray[3]));
					paramMap.put("end_time", getTime(nameArray[4]));
					paramMap.put("data_name", name);
					checkList.add(paramMap);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(fis != null)
			{
				try
				{
					fis.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				fis = null;
			}
			if(dis != null)
			{
				try
				{
					dis.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				dis = null;
			}
		}
		if (checkList.isEmpty())
		{
			Map paramMap = new HashMap();
			paramMap.put("dataset_id", nameArray[0]);
			paramMap.put("task_id", nameArray[1]);
			paramMap.put("subtask_no", nameArray[2]);
			paramMap.put("start_time", getTime(nameArray[3]));
			paramMap.put("end_time", getTime(nameArray[4]));
			paramMap.put("data_name", "-1");
			checkList.add(paramMap);
		}
		ucpDao.savePerfConfigCheck(checkList);
	}
	
	// ��ȡʱ��
	private long getTime(String date)
	{
		DateTimeUtil dtu = new DateTimeUtil(date, "yyyyMMddHHmmss");
		return dtu.getLongTime();
	}
	
	// ����������֤
	private void initPerfDataCheck(List<String> fileList)
	{
		String[] nameArray = null;
		for (String name : fileList)
		{
			try
			{
				nameArray = name.split("_");
				savePerfDataCheck(nameArray, name);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// ��������������֤
	private void savePerfDataCheck(String[] nameArray, String fileName)
	{
		List<Map> dataList = new ArrayList<Map>();
		FileInputStream fis = null;
		DataInputStream dis = null;
		try
		{
			// ��ȡ�ļ�
			Map<String, String> checkMap = new HashMap<String, String>();
			fis = new FileInputStream(new File(path + File.separator + fileName));
			dis = new DataInputStream(fis);
			Map map = null;
			String[] txtTitle = null;
			String[] content = null;
			Object value = null;
			String str1 = null;
			while ((str1 = dis.readLine()) != null)
			{
				if (txtTitle == null)
				{
					txtTitle = str1.split("\t");
				}
				else
				{
					map = new HashMap();
					content = str1.split("\t");
					int len = txtTitle.length;
					for (int i = 0; i < len; i++)
					{
						map.put(txtTitle[i], content[i]);
					}
					if (map.containsKey("ERAB.NbrHoInc")
							&& map.containsKey("ERAB.NbrHoInc._Qci")
							&& map.containsKey("ERAB.NbrAttEstab")
							&& map.containsKey("ERAB.NbrAttEstab._Qci")
							&& map.containsKey("ERAB.NbrReqRelMmeInitNoHo")
							&& map.containsKey("ERAB.NbrReqRelMmeInitNoHo._Qci")
							&& map.containsKey("ERAB.NbrReqRelEnbByHo")
							&& map.containsKey("ERAB.NbrReqRelEnbByHo._Qci"))
					{
						map.put("dataset_id", nameArray[0]);
						map.put("task_id", nameArray[1]);
						map.put("subtask_no", nameArray[2]);
						map.put("start_time", getTime(nameArray[3]));
						map.put("end_time", getTime(nameArray[4]));
						value = map.remove("ERAB.NbrHoInc");
						map.put("erab_nbrhoinc", value);
						value = map.remove("ERAB.NbrHoInc._Qci");
						map.put("erab_nbrhoinc_qci", value);
						value = map.remove("ERAB.NbrAttEstab");
						map.put("erab_nbrattestab", value);
						value = map.remove("ERAB.NbrAttEstab._Qci");
						map.put("erab_nbrattestab_qci", value);
						value = map.remove("ERAB.NbrReqRelMmeInitNoHo");
						map.put("erab_nbrreqrelmmeinitnoho", value);
						value = map.remove("ERAB.NbrReqRelMmeInitNoHo._Qci");
						map.put("erab_nbrreqrelmmeinitnoho_qci", value);
						value = map.remove("ERAB.NbrReqRelEnbByHo");
						map.put("erab_nbrreqrelenbbyho", value);
						value = map.remove("ERAB.NbrReqRelEnbByHo._Qci");
						map.put("erab_nbrreqrelenbbyho_qci", value);
						dataList.add(map);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(fis != null)
			{
				try
				{
					fis.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				fis = null;
			}
			if(dis != null)
			{
				try
				{
					dis.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				dis = null;
			}
		}
		ucpDao.savePerfDataCheck(dataList);
	}
		
	// ��ȡ�µ������ļ�
	private List<String> getNewPerfFiles()
	{
		List<String> list = new ArrayList<String>();
		long endTime = System.currentTimeMillis();
		long startTime = endTime - 300000;
		long curTime = 0;
		File file = new File(path);
		String[] fileArray = file.list();
		for (String fileName : fileArray)
		{
			file = new File(path + File.separator + fileName);
			curTime = file.lastModified();
			if (curTime >= startTime && curTime <= endTime)
			{
				list.add(fileName);
			}
		}
		return list;
	}

	public void destroy() throws Exception
	{
	}

	public void setUcpDao(UcpDAO ucpDao)
	{
		this.ucpDao = ucpDao;
	}

	public void setPath(String path)
	{
		this.path = path;
	}
}