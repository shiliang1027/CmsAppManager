
package com.ailk.module.cms.alarm.quittable.serv;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ailk.module.cms.alarm.quittable.dao.QuitServiceDao;
import com.ailk.module.cms.alarm.quittable.utils.QuitServiceUtil;
import com.ailk.module.cms.system.basesupport.BaseSupportServ;

/**
 * @author gaoqc(69151) Tel:18652058796
 * @version 1.0
 * @since 2012-9-25 ����11:07:20
 * @category com.ailk.module.cms.alarm.quittable.serv
 * @copyright �Ͼ������Ƽ� ���ܿƼ���
 */
public class QuitServiceServImp implements BaseSupportServ
{

	/**
	 * ��־��¼
	 */
	private static final Logger LOG = Logger.getLogger(QuitServiceServImp.class);
	/**
	 * ��վ�˷����ͳ��Dao
	 */
	private QuitServiceDao quitServiceDao;
	/**
	 * kpiӢ������int�±��ӳ���ϵ
	 */
	private static Map<Integer, String> kpiMap = QuitServiceUtil.kpiMap;
	
	public void afterPropertiesSet() throws Exception
	{
		LOG.info("spring afterPropertiesSet");
		LOG.info("��ʼͳ������....");
		long endTime = System.currentTimeMillis() / 1000;
		this.saveQuitTblInfo(endTime - 86400, endTime);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	public void saveQuitTblInfo(long startTime, long endTime)
	{
		LOG.info("ͳ�ƻ�վ�˷���ϢstartTime=" + startTime + ",endTime=" + endTime);
		// �����б�
		Map<String, String> allCityMap = new HashMap<String, String>();
		List<Map> allCityList = quitServiceDao.getCity();
		for (Map map : allCityList)
		{
			allCityMap.put(String.valueOf(map.get("CITY_ID")), String.valueOf(map
					.get("CITY_NAME")));
		}
		Map<String, Map<String, Integer>> allCityKpiInfo = this.getAllCityKpiInfo(
				startTime, endTime, allCityMap);
		Map<String, Integer> szxKpiInfo = new HashMap<String, Integer>();
		allCityKpiInfo.put("ȫʡ", szxKpiInfo);
		// ͳ��ȫʡ����
		List<String> cityNames = new ArrayList<String>();
		for (Map city : allCityList)
		{
			String cityName = String.valueOf(city.get("CITY_NAME"));
			cityNames.add(cityName);
		}
		LOG.info("��ӡcityNameList2:" + cityNames);
		for (Integer kpiIndex : kpiMap.keySet())
		{
			int szxCount = 0;
			String kpiName = kpiMap.get(kpiIndex);
			if (kpiName.endsWith("tfv"))
			{
				continue;
			}
			else
			{
				for (String cityname : cityNames)
				{
					szxCount += allCityKpiInfo.get(cityname).get(kpiName);
				}
				szxKpiInfo.put(kpiName, szxCount);
			}
		}
		allCityMap.put("00", "ȫʡ");
		Set<String> cityIdSet = allCityMap.keySet();
		List<Map> dbList = new ArrayList<Map>();
		Map temp = null;
		// �����˷���
		for (String cityid : cityIdSet)
		{
			String cityname = allCityMap.get(cityid);
			Map<String, Integer> cityKpiInfo = allCityKpiInfo.get(cityname);
			DecimalFormat df = new DecimalFormat("#.##");
			for (Integer index : kpiMap.keySet())
			{
				String kpiname = kpiMap.get(index);
				if (kpiname.endsWith("_tfs") || kpiname.endsWith("_s")
						|| QuitServiceUtil.RINGALARM.indexOf(kpiname) > -1)
				{
					temp = new HashMap();
					temp.put("cityId", cityid);
					temp.put("cityName", cityname);
					temp.put("updateLasttime", endTime);
					temp.put("columnEn", kpiname);
					temp.put("columnUnit", "");
					temp.put("perfValue", cityKpiInfo.get(kpiname));
					temp.put("columnType", QuitServiceUtil.kpiTypeMap.get(kpiname));
					dbList.add(temp);
				}
			}
			// GSM ��վ�˷���
			double gsmHzCount = cityKpiInfo.get(kpiMap.get(QuitServiceUtil.GSM_HZ_COUNT));
			// GSM ΢�����˷���
			double gsmWfwCount = cityKpiInfo.get(kpiMap
					.get(QuitServiceUtil.GSM_WFW_COUNT));
			// GSM С���˷���
			double gsmXqCount = cityKpiInfo.get(kpiMap.get(QuitServiceUtil.GSM_XQ_COUNT));
			// TD ��վ�˷���
			double tdHzCount = cityKpiInfo.get(kpiMap.get(QuitServiceUtil.TD_HZ_COUNT));
			// TD ΢�����˷���
			double tdWfwCount = cityKpiInfo.get(kpiMap.get(QuitServiceUtil.TD_WFW_COUNT));
			// TD С���˷���
			double tdXqCount = cityKpiInfo.get(kpiMap.get(QuitServiceUtil.TD_XQ_COUNT));
			// �ڷ��ؽ���з���GSM ��վ�˷���
			temp = new HashMap();
			temp.put("cityId", cityid);
			temp.put("cityName", cityname);
			temp.put("columnUnit", "%");
			temp.put("updateLasttime", endTime);
			temp.put("columnEn", kpiMap.get(QuitServiceUtil.GSM_HZ_TFV));
			temp.put("columnType", QuitServiceUtil.kpiTypeMap.get(kpiMap.get(QuitServiceUtil.GSM_HZ_TFV)));
			temp.put("perfValue", 0 == gsmHzCount ? 0 : df.format(cityKpiInfo.get(kpiMap
					.get(QuitServiceUtil.GSM_HZ_TFS))
					* 100 / gsmHzCount));
			dbList.add(temp);
			// �ڷ��ؽ���з���GSM ΢�����˷���
			temp = new HashMap();
			temp.put("cityId", cityid);
			temp.put("cityName", cityname);
			temp.put("columnUnit", "%");
			temp.put("updateLasttime", endTime);
			temp.put("columnEn", kpiMap.get(QuitServiceUtil.GSM_WFW_TFV));
			temp.put("columnType", QuitServiceUtil.kpiTypeMap.get(kpiMap.get(QuitServiceUtil.GSM_WFW_TFV)));
			temp.put("perfValue", 0 == gsmWfwCount ? 0 : df.format(cityKpiInfo
					.get(kpiMap.get(QuitServiceUtil.GSM_WFW_TFS))
					* 100 / gsmWfwCount));
			dbList.add(temp);
			// �ڷ��ؽ���з���GSM С���˷���
			temp = new HashMap();
			temp.put("cityId", cityid);
			temp.put("cityName", cityname);
			temp.put("columnUnit", "%");
			temp.put("updateLasttime", endTime);
			temp.put("columnEn", kpiMap.get(QuitServiceUtil.GSM_XQ_TFV));
			temp.put("columnType", QuitServiceUtil.kpiTypeMap.get(kpiMap.get(QuitServiceUtil.GSM_XQ_TFV)));
			temp.put("perfValue", 0 == gsmXqCount ? 0 : df.format(cityKpiInfo.get(kpiMap
					.get(QuitServiceUtil.GSM_XQ_TFS))
					* 100 / gsmXqCount));
			dbList.add(temp);
			// �ڷ��ؽ���з���TD ��վ�˷���
			temp = new HashMap();
			temp.put("cityId", cityid);
			temp.put("cityName", cityname);
			temp.put("columnUnit", "%");
			temp.put("updateLasttime", endTime);
			temp.put("columnEn", kpiMap.get(QuitServiceUtil.TD_HZ_TFV));
			temp.put("columnType", QuitServiceUtil.kpiTypeMap.get(kpiMap.get(QuitServiceUtil.TD_HZ_TFV)));
			temp.put("perfValue", 0 == tdHzCount ? 0 : df.format(cityKpiInfo.get(kpiMap
					.get(QuitServiceUtil.TD_HZ_TFS))
					* 100 / tdHzCount));
			dbList.add(temp);
			// �ڷ��ؽ���з���TD ΢�����˷���
			temp = new HashMap();
			temp.put("cityId", cityid);
			temp.put("cityName", cityname);
			temp.put("columnUnit", "%");
			temp.put("updateLasttime", endTime);
			temp.put("columnEn", kpiMap.get(QuitServiceUtil.TD_WFW_TFV));
			temp.put("columnType", QuitServiceUtil.kpiTypeMap.get(kpiMap.get(QuitServiceUtil.TD_WFW_TFV)));
			temp.put("perfValue", 0 == tdWfwCount ? 0 : df.format(cityKpiInfo.get(kpiMap
					.get(QuitServiceUtil.TD_WFW_TFS))
					* 100 / tdWfwCount));
			dbList.add(temp);
			// �ڷ��ؽ���з���TD С���˷���
			temp = new HashMap();
			temp.put("cityId", cityid);
			temp.put("cityName", cityname);
			temp.put("columnUnit", "%");
			temp.put("updateLasttime", endTime);
			temp.put("columnEn", kpiMap.get(QuitServiceUtil.TD_XQ_TFV));
			temp.put("columnType", QuitServiceUtil.kpiTypeMap.get(kpiMap.get(QuitServiceUtil.TD_XQ_TFV)));
			temp.put("perfValue", 0 == tdXqCount ? 0 : df.format(cityKpiInfo.get(kpiMap
					.get(QuitServiceUtil.TD_XQ_TFS))
					* 100 / tdXqCount));
			dbList.add(temp);
		}
		LOG.info("----------ͳ�ƻ�վ�˷����----------");
		// �жϱ����Ƿ��������
		int existDataCount = quitServiceDao.getExistData();
		LOG.info("�澯����=======>" + existDataCount);
		// �����ݲ�����ʱ
		if (existDataCount > 0)
		{
			quitServiceDao.updateQuitTblInfo(dbList);
		}
		else
		{
			quitServiceDao.insertQuitTblInfo(dbList);
		}
		LOG.info("----------ͳ�ƻ�վ�˷����----------");
	}

	/**
	 * ���13�����к�ȫʡ������ָ������
	 * 
	 * @param startTime
	 *            ��ʼʱ��
	 * @param endTime
	 *            ����ʱ��
	 * @param allCityMap
	 *            ���е���ID-Nameӳ���ϵ
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, Map<String, Integer>> getAllCityKpiInfo(long startTime,
			long endTime, Map<String, String> allCityMap)
	{
		LOG.info("ȡ������ʷ����ͳ��--------------" + startTime + "|" + endTime);
		// ���е�����List
		List<String> allCityNames = new ArrayList<String>();
		for (String cityid : allCityMap.keySet())
		{
			allCityNames.add(allCityMap.get(cityid));
		}
		// ���key:������|�ڲ�key:ָ��Ӣ����
		Map<String, Map<String, Integer>> allTfsInfo = new HashMap<String, Map<String, Integer>>();
		Map<String, Integer> cityTfs = null;
		for (String name : allCityNames)
		{
			cityTfs = allTfsInfo.get(name);
			if (null == cityTfs)
			{
				cityTfs = new HashMap<String, Integer>();
				allTfsInfo.put(name, cityTfs);
			}
		}
		List<Map> btsHzTfs = quitServiceDao.getBtsHzTfsCount(startTime, endTime);
		List<Map> btshz = quitServiceDao.getBtsHzCount();
		List<Map> btsWfwTfs = quitServiceDao.getBtsWfwTfsCount(startTime, endTime);
		List<Map> btsWfw = quitServiceDao.getBtsWfwCount();
		List<Map> cellTfs = quitServiceDao.getCellTfsCount(startTime, endTime);
		List<Map> cell = quitServiceDao.getCellCount();
		List<Map> vipBtsTfs = quitServiceDao.getVipBtsTfsCount(startTime, endTime);
		List<Map> cellZpTfs = quitServiceDao.getCellZpTfsCount(startTime, endTime);
		List<Map> cellRgbsTfs = quitServiceDao.getCellRgbsTfsCount(startTime, endTime);
		List<Map> btsRgbsTfs = quitServiceDao.getBtsRgbsTfsCount(startTime, endTime);
		List<Map> nodebHzTfs = quitServiceDao.getNodebHzTfsCount(startTime, endTime);
		List<Map> nodebHz = quitServiceDao.getNodebHzCount();
		List<Map> nodebWfwTfs = quitServiceDao.getNodebWfwTfsCount(startTime, endTime);
		List<Map> nodebWfw = quitServiceDao.getNodebWfwCount();
		List<Map> ucellTfs = quitServiceDao.getUcellTfsCount(startTime, endTime);
		List<Map> ucell = quitServiceDao.getUcellCount();
		List<Map> vipNodebTfs = quitServiceDao.getVipNodebTfsCount(startTime, endTime);
		List<Map> ucellZpTfs = quitServiceDao.getUcellZpTfsCount(startTime, endTime);
		List<Map> ucellRgbsTfs = quitServiceDao.getUcellRgbsTfsCount(startTime, endTime);
		List<Map> nodebRgbsTfs = quitServiceDao.getNodebRgbsTfsCount(startTime, endTime);
		List<Map> powerFailureTfs = quitServiceDao.getPowerFailureTfsCount(startTime,
				endTime);
		List<Map> voltageLowTfs = quitServiceDao
				.getVoltageLowTfsCount(startTime, endTime);
		List<Map> onceDownPowerTfs = quitServiceDao.getOnceDownPowerTfsCount(startTime,
				endTime);
		List<Map> waterSoakTfs = quitServiceDao.getWaterSoakTfsCount(startTime, endTime);
		List<Map> smogTfs = quitServiceDao.getSmogTfsCount(startTime, endTime);
		List<Map> temperatureHighTfs = quitServiceDao.getTemperatureHighTfsCount(
				startTime, endTime);
		List<Map> temperatureExtraHighTfs = quitServiceDao
				.getTemperatureExtraHighTfsCount(startTime, endTime);
		for (String cityname : allTfsInfo.keySet())
		{
			cityTfs = allTfsInfo.get(cityname);
			// GSM ��վ��վ�˷�����
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_HZ_TFS), cityname, btsHzTfs,
					cityTfs);
			// GSM ��վ��վ��
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_HZ_COUNT), cityname, btshz,
					cityTfs);
			// GSM ��վ�����˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_WFW_TFS), cityname, btsWfwTfs,
					cityTfs);
			// GSM ��վ΢������
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_WFW_COUNT), cityname, btsWfw,
					cityTfs);
			// GSM С���˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_XQ_TFS), cityname, cellTfs,
					cityTfs);
			// GSM С����
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_XQ_COUNT), cityname, cell,
					cityTfs);
			// GSM VIP��վ�˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_VIP_TFS), cityname, vipBtsTfs,
					cityTfs);
			// GSM ��ƵС���˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_ZB_TFS), cityname, cellZpTfs,
					cityTfs);
			// GSM С���˹��˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_XQRGBS_S), cityname,
					cellRgbsTfs, cityTfs);
			// GSM ��վ�˹��˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_JZRGBS_S), cityname, btsRgbsTfs,
					cityTfs);
			// TD ��վ�˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_HZ_TFS), cityname, nodebHzTfs,
					cityTfs);
			// TD ��վ��
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_HZ_COUNT), cityname, nodebHz,
					cityTfs);
			// TD ΢�����˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_WFW_TFS), cityname, nodebWfwTfs,
					cityTfs);
			// TD ΢������
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_WFW_COUNT), cityname, nodebWfw,
					cityTfs);
			// TD С���˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_XQ_TFS), cityname, ucellTfs,
					cityTfs);
			// TD С����
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_XQ_COUNT), cityname, ucell,
					cityTfs);
			// TD VIP��վ�˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_VIP_TFS), cityname, vipNodebTfs,
					cityTfs);
			// TD ��ƵС���˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_ZB_TFS), cityname, ucellZpTfs,
					cityTfs);
			// TD С���˹��˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_XQRGBS_S), cityname,
					ucellRgbsTfs, cityTfs);
			// TD ��վ�˹��˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_JZRGBS_S), cityname,
					nodebRgbsTfs, cityTfs);
			// ͣ���˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.POWER_FAILURE), cityname,
					powerFailureTfs, cityTfs);
			// ��ѹ�˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.VOLTAGE_LOW), cityname,
					voltageLowTfs, cityTfs);
			// һ���µ��˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.ONCE_DOWN_POWER), cityname,
					onceDownPowerTfs, cityTfs);
			// ˮ���˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.WATER_SOAK), cityname, waterSoakTfs,
					cityTfs);
			// �����˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.SMOG), cityname, smogTfs, cityTfs);
			// �¶ȹ����˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.TEMPERATURE_HIGH), cityname,
					temperatureHighTfs, cityTfs);
			// �¶ȳ����˷���
			this.dealStat(kpiMap.get(QuitServiceUtil.TEMPERATURE_EXTRA_HIGH), cityname,
					temperatureExtraHighTfs, cityTfs);
		}
		LOG.info("��ӡallTfsInfo:" + allTfsInfo);
		return allTfsInfo;
	}

	/**
	 * ��װ���е�������ָ��Ĳ�ѯ����
	 * 
	 * @param kpiType
	 *            ָ������Ӧ��intֵ
	 * @param allCityNames
	 *            ���е�����List
	 * @param statList
	 *            ��װ���
	 * @param allTfsInfo
	 *            ���е��˷�����
	 * @param cityTfs
	 *            ���е��˷�����
	 */
	@SuppressWarnings( { "rawtypes" })
	private void dealStat(String kpiType, String cityname, List<Map> statList,
			Map<String, Integer> cityTfs)
	{
		// Map
		if (null == statList)
		{
			// �����ݿ�δ����˷�����,�������е��еĶ�Ӧ��:0
			// ȡ�õ��е�ָ��ͳ����
			cityTfs.put(kpiType, 0);
		}
		else
		{
			// �ж��ڲ�ѯ������Ƿ�����õ�������
			boolean isGeted = false;
			for (Map map : statList)
			{
				String city = String.valueOf(map.get("CITY_NAME"));
				if (city.equals(cityname))
				{
					cityTfs.put(kpiType, Integer
							.valueOf(String.valueOf(map.get("COUNT"))));
					isGeted = true;
					break;
				}
			}
			if (!isGeted)
			{
				cityTfs.put(kpiType, 0);
			}
		}
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	public List<Map> getcitys(Map user)
	{
		LOG.info("ȡ�õ����б�:11");
		List<Map> cityList = quitServiceDao.getCity();
		LOG.info("ȡ�õ����б�:11--" + cityList);
		List<Map> returnInfo = new ArrayList<Map>();
		for (Map city : cityList)
		{
			Map temp = new HashMap();
			temp.put("cityId", String.valueOf(city.get("CITY_ID")));
			temp.put("label", String.valueOf(city.get("CITY_NAME")));
			returnInfo.add(temp);
		}
		LOG.info("��ӡ:getcitys:" + returnInfo);
		return returnInfo;
	}

	public QuitServiceDao getQuitServiceDao()
	{
		return quitServiceDao;
	}

	public void setQuitServiceDao(QuitServiceDao quitServiceDao)
	{
		this.quitServiceDao = quitServiceDao;
	}

	@Override
	public void destroy() throws Exception
	{
		// TODO Auto-generated method stub
	}
}
