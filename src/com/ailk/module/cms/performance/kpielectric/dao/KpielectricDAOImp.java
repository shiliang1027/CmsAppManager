package com.ailk.module.cms.performance.kpielectric.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.linkage.system.jdbc.jpa.JPABaseDAO;
import com.linkage.system.jdbc.jpa.SqlSessionTemplate;
import com.linkage.system.utils.StringUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;

/**
 * @author wangsh(69070) Tel:15051899778
 * @version 1.0
 * @since 2012-8-9 上午09:25:26
 * @category com.ailk.module.cms.performance.kpielectric.dao
 * @copyright 南京联创科技 网管科技部
 */
public class KpielectricDAOImp extends JPABaseDAO implements KpielectricDAO {

	private final static Logger log = Logger.getLogger(KpielectricDAOImp.class);
	private final static String alias = KpielectricDAOImp.class.getName() + ".";
	// 东信数据源oracle
	private SqlSessionTemplate inspurStmpt;
	// Sybase 10.39.254.228
	private SqlSessionTemplate zcssStmpt;
	// informix 10.40.5.134
	private SqlSessionTemplate ailkStmpt;
	private DataSource inspurDs;
	private JdbcTemplate inspurJt;

	public void setInspurDs(DataSource inspurDs) {
		this.inspurDs = inspurDs;
		inspurJt = new JdbcTemplate(inspurDs);
	}

	public List<Map> getKpielectricTabList(Map param) {
		return getSqlSessionTemplate().selectList(
				alias + "getExistDispatchTabList", param);
	}

	public int createKpielectricTab(Map param) {
		return getSqlSessionTemplate().update(alias + "createKpielectricTab",
				param);
	}
	
	@Override
	public int createDetailTabList(Map param) {
		
		return getSqlSessionTemplate().update(alias + "createDetailTabList",
				param);
	}

	// 从东信数据库取表electric_card_info(oracle)
	public int queryKpielectricList(Map param) {

		final List<Map> list = new ArrayList<Map>(500);
		final Cnt cnt = new Cnt(0);
		log.info("---------------------:" + param + ","
				+ inspurStmpt.getConnection());
		inspurStmpt.select(alias + "queryKpielectricList", param,
				new ResultHandler() {

					@Override
					public void handleResult(ResultContext rc) {
						list.add(Map.class.cast(rc.getResultObject()));
						cnt.add();
						if (list.size() % 500 == 0) {
							getSqlSessionTemplate().batchUpdate(
									alias + "saveKpielectricList", list);
							list.clear();
						}
					}
				});
		if (!list.isEmpty()) {
			getSqlSessionTemplate().batchUpdate(alias + "saveKpielectricList",
					list);
			list.clear();
		}
		return cnt.getCnt();

		// String sql =
		// "select count(1) from EASTCOM.ELECTRIC_CARD_INFO where to_date(updatetime,'yyyy-mm-dd hh24:mi:ss')>to_date('"
		// + param.get("startTime") + "','yyyy-mm-dd hh24:mi:ss')";
		// log.info("sql--->" + sql);
		// log.info("--->" + inspurJt.queryForInt(sql));
		// return 0;
	}

	// 从东信数据库取表zc_tcp_con_stat_yyyy_mm_dd(sybase)
	public int createTcpTabList(Map param) {
		return getSqlSessionTemplate()
				.update(alias + "createTcpTabList", param);
	}

	public int queryunlineList(final Map param) {
		final List<Map> list = new ArrayList<Map>(500);
		final Cnt cnt = new Cnt(0);
		zcssStmpt.select(alias + "queryunlineList", param, new ResultHandler() {

			@Override
			public void handleResult(ResultContext rc) {

				Map map = Map.class.cast(rc.getResultObject());
				map.put("detailTab", param.get("detailTab"));
				// 不在线终端数，总数减去在线终端数
				Date now = new Date();
				DateFormat d8 = DateFormat.getDateTimeInstance(
						DateFormat.MEDIUM, DateFormat.MEDIUM); // 显示日期，时间（精确到分）
				String str8 = d8.format(now);

				long daltime = 0;
				try {
					daltime = DateUtils.parseDate(str8,
							new String[] { "yyyy-MM-dd HH:mm:ss" }).getTime() / 1000;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				map.put("gather_time", daltime);
				map.put("city_name", param.get("city"));
				list.add(map);
				cnt.add();
				if (list.size() % 500 == 0) {
					getSqlSessionTemplate()
					.batchUpdate(alias + "saveunlineList", list);
					list.clear();
				}
				

			}
		});
		if (!list.isEmpty()) {
			getSqlSessionTemplate().batchUpdate(alias + "saveunlineList",
					list);
			list.clear();
		}

		return cnt.getCnt();
	}

	public int queryTcpList(final Map param) {
		final List<Map> list = new ArrayList<Map>(500);
		final Cnt cnt = new Cnt(0);

		zcssStmpt.select(alias + "queryTcpList", param, new ResultHandler() {

			@Override
			public void handleResult(ResultContext rc) {

				Map map = Map.class.cast(rc.getResultObject());
				map.put("sheetHis", param.get("sheetHis"));
				// 不在线终端数，总数减去在线终端数
				long kpi4 = Long.parseLong(String.valueOf(map.get("kpi5")))
						- Long.parseLong(String.valueOf(map.get("kpi3")));
				long kpi5 = Long.parseLong(String.valueOf(map.get("kpi5")));
				long kpi3 = Long.parseLong(String.valueOf(map.get("kpi3")));
				String rate = StringUtils.formatString((kpi3 * 100F) / kpi5, 2)
						+ "%";
				//
				long up_success = Long.parseLong(String.valueOf(map
						.get("up_success")));
				long up_fail = Long.parseLong(String.valueOf(map.get("up_fail")));
				long down_success = Long.parseLong(String.valueOf(map
						.get("down_success")));
				long down_fail = Long.parseLong(String.valueOf(map
						.get("down_fail")));
				String rate1 = StringUtils.formatString((up_success * 100F)
						/ (up_success + up_fail), 2)
						+ "%";
				String rate2 = StringUtils.formatString((down_success * 100F)
						/ (down_success + down_fail), 2)
						+ "%";
				Date now = new Date();
				DateFormat d8 = DateFormat.getDateTimeInstance(
						DateFormat.MEDIUM, DateFormat.MEDIUM); // 显示日期，时间（精确到分）
				String str8 = d8.format(now);

				long daltime = 0;
				try {
					daltime = DateUtils.parseDate(str8,
							new String[] { "yyyy-MM-dd HH:mm:ss" }).getTime() / 1000;
				} catch (ParseException e) {
					e.printStackTrace();
				}

				map.put("kpi4", kpi4);
				map.put("kpi6", rate);
				map.put("kpi1", rate1);
				map.put("kpi2", rate2);
				map.put("gather_time", daltime);
				map.put("city_name", param.get("city"));
				list.add(map);
				cnt.add();
				getSqlSessionTemplate()
						.batchUpdate(alias + "saveTcpList", list);

			}
		});

		return cnt.getCnt();
	}

	// informix
	public int queryBuildingList(Map param) {
		final List<Map> list = new ArrayList<Map>(500);
		final Cnt cnt = new Cnt(0);
		ailkStmpt.select(alias + "queryBuildingList", param,
				new ResultHandler() {

					@Override
					public void handleResult(ResultContext rc) {
						Map map = Map.class.cast(rc.getResultObject());
						Map apnmap = getApnNameMap();
						String apn = String.valueOf(map.get("apn"));
						String city_name = String.valueOf(apnmap.get(apn));
						map.put("city_name", city_name);
						list.add(map);
						cnt.add();
						if (list.size() % 500 == 0) {
							getSqlSessionTemplate().batchUpdate(
									alias + "saveBuildingList", list);
							list.clear();
						}
					}
				});
		if (!list.isEmpty()) {
			getSqlSessionTemplate().batchUpdate(alias + "saveBuildingList",
					list);
			list.clear();
		}
		return cnt.getCnt();
	}

	private Map getApnNameMap() {
		Map<String, String> apnmap = new HashMap();
		apnmap.put("eic186lygdl.js", "连云港");
		apnmap.put("eic186xzdl.js", "徐州");
		apnmap.put("eic186ycdl.js", "盐城");
		apnmap.put("eic186hadl.js", "淮安");
		apnmap.put("eic186sqdl.js", "宿迁");
		apnmap.put("eic186tzdl.js", "泰州");
		apnmap.put("eic186yzdl.js", "扬州");
		apnmap.put("eic186szdl.js", "苏州");
		apnmap.put("eic186wxdl.js", "无锡");
		apnmap.put("eic186ntdl.js", "南通");
		apnmap.put("eic186czdl.js", "常州");
		apnmap.put("eic186zjdl.js", "镇江");
		apnmap.put("eic186njdl.js", "南京");
		return apnmap;
	}

	/**
	 * 计数器
	 * 
	 * @author Administrator(工号) Tel:
	 * @version 1.0
	 * @since 2012-8-23 下午02:14:06
	 * @category com.ailk.module.cms.performance.kpielectric.dao<br>
	 * @copyright 南京联创科技 网管科技部
	 */
	class Cnt {

		private int cnt = 0;

		public Cnt(int cnt) {
			this.cnt = cnt;
		}

		public void add() {
			this.cnt++;
		}

		public int getCnt() {
			return cnt;
		}
	}

	public void setInspurStmpt(SqlSessionTemplate inspurStmpt) {
		this.inspurStmpt = inspurStmpt;
	}

	public SqlSessionTemplate getAilkStmpt() {
		return ailkStmpt;
	}

	public void setAilkStmpt(SqlSessionTemplate ailkStmpt) {
		this.ailkStmpt = ailkStmpt;
	}

	public void setZcssStmpt(SqlSessionTemplate zcssStmpt) {
		this.zcssStmpt = zcssStmpt;
	}

	@Override
	public int createBuildingTabList(Map param) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
