
package com.ailk.module.cms.alarm.indentify.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.log4j.Logger;

import com.linkage.system.jdbc.jpa.SqlSessionTemplate;

/**
 * @author yuanj(65478) Tel:18652040107
 * @version 1.0
 * @since 2012-7-3 下午8:56:43
 * @category com.ailk.module.cms.alarm.indentify.dao
 * @copyright 亚信联创 网管产品部
 */
public class AlarmStatDAOImp implements AlarmStatDAO
{

	@Override
	public void createTable(Map<String, Object> map) throws SQLException
	{
		if (!judgeTableExists(map))
		{
			log.info("table[" + map.get("stat_tab") + "]not exists,need create!!!");
			getSstmpt().update(getNameSpace() + "createTable", map);
		}
		else
		{
			log.info("table[" + map.get("stat_tab") + "]already exists,need clear!!!");
			truncateTable(map);
		}
	}

	@Override
	public boolean judgeTableExists(Map<String, Object> map)
	{
		return 0 < Integer.class.cast(getSstmpt().selectOne(getNameSpace() + "judgeTableExists",
				map));
	}

	@Override
	public void truncateTable(Map<String, Object> map) throws SQLException
	{
		if (!pattern.matcher(String.valueOf(map.get("stat_tab"))).matches())
		{
			log.warn("table is needed clear[" + map.get("stat_tab") + "]not conform rule!!!");
			return;
		}
		getSstmpt().update(getNameSpace() + "truncateTable", map);
	}

	@Override
	public void statAlarm(final Map<String, Object> m)
	{
		if (log.isDebugEnabled())
		{
			log.debug("stat params：" + m);
		}
		final List<Map> alarms = new ArrayList<Map>(500);
		final long st = Long.parseLong(String.valueOf(m.get("st")));
		final String stat_tab = String.valueOf(m.get("stat_tab"));
		getSstmpt().select(getNameSpace() + "statAlarm", m, new ResultHandler()
		{

			@Override
			public void handleResult(ResultContext context)
			{
				try
				{
					Map map = Map.class.cast(context.getResultObject());
					parseResultObject(map);
					map.put("gather_time", st);
					long ct = Long.parseLong(String.valueOf(map.get("canceltime")));
					long et = Long.parseLong(String.valueOf(map.get("eventtime")));
					map.put("cancelinterval", ct - et > 0 ? ct - et : 0);
					map.put("stat_tab", stat_tab);
					alarms.add(map);
				}
				catch (Exception e)
				{
					log.error("column parse Err：", e);
				}
				if (0 == alarms.size() % BATCH)
				{
					try
					{
						batchUpdate(alarms);
					}
					catch (SQLException e)
					{
						log.error("DataBase Operation Err：", e);
					}
					alarms.clear();
				}
			}
		});
		if (!alarms.isEmpty())
		{
			try
			{
				batchUpdate(alarms);
			}
			catch (SQLException e)
			{
				log.error("DataBase Operation Err：", e);
			}
			alarms.clear();
		}
	}

	private void batchUpdate(final List<Map> alarms) throws SQLException
	{
		getSstmpt().batchUpdate(getNameSpace() + "batchUpdate", alarms);
	}

	private void parseResultObject(Map map)
	{
		for (String col : INSERT_COLS)
		{
			if (!map.containsKey(col))
			{
				map.put(col, "");
			}
		}
	}

	public void setSstmpt(SqlSessionTemplate sstmpt)
	{
		this.sstmpt = sstmpt;
	}

	public SqlSessionTemplate getSstmpt()
	{
		return sstmpt;
	}

	public String getNameSpace()
	{
		return nameSpace;
	}

	private String nameSpace = AlarmStatDAOImp.class.getName() + ".";
	private SqlSessionTemplate sstmpt;
	private Pattern pattern = Pattern.compile("ta_alarmpro_".toUpperCase()
			+ "[0-9]{4}_[0-9]{2}_[0-9]{2}");
	private Logger log = Logger.getLogger(AlarmStatDAOImp.class);
	private int BATCH = 500;
	private String[] INSERT_COLS = "alarmuniqueid,nename,equipmentclass,alarmtext,daltime,eventtime,alarmregion,vendor,alarmseverity,alarmstatus,alarmtitle,canceltime,vendorseverity,locatenestatus,destsignal,destnename,sheetsendstatus"
			.split(",");
}
