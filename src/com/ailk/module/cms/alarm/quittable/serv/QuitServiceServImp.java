
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
 * @since 2012-9-25 上午11:07:20
 * @category com.ailk.module.cms.alarm.quittable.serv
 * @copyright 南京联创科技 网管科技部
 */
public class QuitServiceServImp implements BaseSupportServ
{

	/**
	 * 日志记录
	 */
	private static final Logger LOG = Logger.getLogger(QuitServiceServImp.class);
	/**
	 * 基站退服表格统计Dao
	 */
	private QuitServiceDao quitServiceDao;
	/**
	 * kpi英文名与int下标的映射关系
	 */
	private static Map<Integer, String> kpiMap = QuitServiceUtil.kpiMap;
	
	public void afterPropertiesSet() throws Exception
	{
		LOG.info("spring afterPropertiesSet");
		LOG.info("开始统计数据....");
		long endTime = System.currentTimeMillis() / 1000;
		this.saveQuitTblInfo(endTime - 86400, endTime);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	public void saveQuitTblInfo(long startTime, long endTime)
	{
		LOG.info("统计基站退服信息startTime=" + startTime + ",endTime=" + endTime);
		// 地市列表
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
		allCityKpiInfo.put("全省", szxKpiInfo);
		// 统计全省数据
		List<String> cityNames = new ArrayList<String>();
		for (Map city : allCityList)
		{
			String cityName = String.valueOf(city.get("CITY_NAME"));
			cityNames.add(cityName);
		}
		LOG.info("打印cityNameList2:" + cityNames);
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
		allCityMap.put("00", "全省");
		Set<String> cityIdSet = allCityMap.keySet();
		List<Map> dbList = new ArrayList<Map>();
		Map temp = null;
		// 计算退服率
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
			// GSM 宏站退服数
			double gsmHzCount = cityKpiInfo.get(kpiMap.get(QuitServiceUtil.GSM_HZ_COUNT));
			// GSM 微蜂窝退服数
			double gsmWfwCount = cityKpiInfo.get(kpiMap
					.get(QuitServiceUtil.GSM_WFW_COUNT));
			// GSM 小区退服数
			double gsmXqCount = cityKpiInfo.get(kpiMap.get(QuitServiceUtil.GSM_XQ_COUNT));
			// TD 宏站退服数
			double tdHzCount = cityKpiInfo.get(kpiMap.get(QuitServiceUtil.TD_HZ_COUNT));
			// TD 微蜂窝退服数
			double tdWfwCount = cityKpiInfo.get(kpiMap.get(QuitServiceUtil.TD_WFW_COUNT));
			// TD 小区退服数
			double tdXqCount = cityKpiInfo.get(kpiMap.get(QuitServiceUtil.TD_XQ_COUNT));
			// 在返回结果中放入GSM 宏站退服率
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
			// 在返回结果中放入GSM 微蜂窝退服率
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
			// 在返回结果中放入GSM 小区退服率
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
			// 在返回结果中放入TD 宏站退服率
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
			// 在返回结果中放入TD 微蜂窝退服率
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
			// 在返回结果中放入TD 小区退服率
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
		LOG.info("----------统计基站退服入库----------");
		// 判断表中是否存在数据
		int existDataCount = quitServiceDao.getExistData();
		LOG.info("告警数量=======>" + existDataCount);
		// 当数据不存在时
		if (existDataCount > 0)
		{
			quitServiceDao.updateQuitTblInfo(dbList);
		}
		else
		{
			quitServiceDao.insertQuitTblInfo(dbList);
		}
		LOG.info("----------统计基站退服完成----------");
	}

	/**
	 * 获得13个地市和全省的所有指标数据
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param allCityMap
	 *            所有地市ID-Name映射关系
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, Map<String, Integer>> getAllCityKpiInfo(long startTime,
			long endTime, Map<String, String> allCityMap)
	{
		LOG.info("取得用历史数据统计--------------" + startTime + "|" + endTime);
		// 所有地市名List
		List<String> allCityNames = new ArrayList<String>();
		for (String cityid : allCityMap.keySet())
		{
			allCityNames.add(allCityMap.get(cityid));
		}
		// 外层key:地市名|内层key:指标英文名
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
			// GSM 基站宏站退服数据
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_HZ_TFS), cityname, btsHzTfs,
					cityTfs);
			// GSM 基站宏站数
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_HZ_COUNT), cityname, btshz,
					cityTfs);
			// GSM 基站蜂窝退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_WFW_TFS), cityname, btsWfwTfs,
					cityTfs);
			// GSM 基站微蜂窝数
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_WFW_COUNT), cityname, btsWfw,
					cityTfs);
			// GSM 小区退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_XQ_TFS), cityname, cellTfs,
					cityTfs);
			// GSM 小区数
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_XQ_COUNT), cityname, cell,
					cityTfs);
			// GSM VIP基站退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_VIP_TFS), cityname, vipBtsTfs,
					cityTfs);
			// GSM 载频小区退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_ZB_TFS), cityname, cellZpTfs,
					cityTfs);
			// GSM 小区人工退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_XQRGBS_S), cityname,
					cellRgbsTfs, cityTfs);
			// GSM 基站人工退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.GSM_JZRGBS_S), cityname, btsRgbsTfs,
					cityTfs);
			// TD 宏站退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_HZ_TFS), cityname, nodebHzTfs,
					cityTfs);
			// TD 宏站数
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_HZ_COUNT), cityname, nodebHz,
					cityTfs);
			// TD 微蜂窝退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_WFW_TFS), cityname, nodebWfwTfs,
					cityTfs);
			// TD 微蜂窝数
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_WFW_COUNT), cityname, nodebWfw,
					cityTfs);
			// TD 小区退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_XQ_TFS), cityname, ucellTfs,
					cityTfs);
			// TD 小区数
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_XQ_COUNT), cityname, ucell,
					cityTfs);
			// TD VIP基站退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_VIP_TFS), cityname, vipNodebTfs,
					cityTfs);
			// TD 载频小区退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_ZB_TFS), cityname, ucellZpTfs,
					cityTfs);
			// TD 小区人工退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_XQRGBS_S), cityname,
					ucellRgbsTfs, cityTfs);
			// TD 基站人工退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.TD_JZRGBS_S), cityname,
					nodebRgbsTfs, cityTfs);
			// 停电退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.POWER_FAILURE), cityname,
					powerFailureTfs, cityTfs);
			// 低压退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.VOLTAGE_LOW), cityname,
					voltageLowTfs, cityTfs);
			// 一次下电退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.ONCE_DOWN_POWER), cityname,
					onceDownPowerTfs, cityTfs);
			// 水浸退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.WATER_SOAK), cityname, waterSoakTfs,
					cityTfs);
			// 烟雾退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.SMOG), cityname, smogTfs, cityTfs);
			// 温度过高退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.TEMPERATURE_HIGH), cityname,
					temperatureHighTfs, cityTfs);
			// 温度超高退服数
			this.dealStat(kpiMap.get(QuitServiceUtil.TEMPERATURE_EXTRA_HIGH), cityname,
					temperatureExtraHighTfs, cityTfs);
		}
		LOG.info("打印allTfsInfo:" + allTfsInfo);
		return allTfsInfo;
	}

	/**
	 * 封装所有地市所有指标的查询数据
	 * 
	 * @param kpiType
	 *            指标名对应的int值
	 * @param allCityNames
	 *            所有地市名List
	 * @param statList
	 *            封装结果
	 * @param allTfsInfo
	 *            所有的退服数据
	 * @param cityTfs
	 *            地市的退服数据
	 */
	@SuppressWarnings( { "rawtypes" })
	private void dealStat(String kpiType, String cityname, List<Map> statList,
			Map<String, Integer> cityTfs)
	{
		// Map
		if (null == statList)
		{
			// 当数据库未查出退服数据,设置所有地市的对应数:0
			// 取得地市的指标统计数
			cityTfs.put(kpiType, 0);
		}
		else
		{
			// 判断在查询结果中是否包含该地市数据
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
		LOG.info("取得地市列表:11");
		List<Map> cityList = quitServiceDao.getCity();
		LOG.info("取得地市列表:11--" + cityList);
		List<Map> returnInfo = new ArrayList<Map>();
		for (Map city : cityList)
		{
			Map temp = new HashMap();
			temp.put("cityId", String.valueOf(city.get("CITY_ID")));
			temp.put("label", String.valueOf(city.get("CITY_NAME")));
			returnInfo.add(temp);
		}
		LOG.info("打印:getcitys:" + returnInfo);
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
