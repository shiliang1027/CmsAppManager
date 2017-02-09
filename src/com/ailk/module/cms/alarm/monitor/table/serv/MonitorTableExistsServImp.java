
package com.ailk.module.cms.alarm.monitor.table.serv;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.ailk.module.cms.alarm.monitor.table.dao.MonitorTableExistsDAO;
import com.linkage.system.utils.corba.CorbaMsgSupport;
import com.linkage.system.utils.corba.NodeUtil;

/**
 * @author yuanj(65478) Tel:18652040107
 * @version 1.0
 * @since 2012-7-4 下午1:00:30
 * @category com.ailk.module.cms.alarm.monitor.table.serv
 * @copyright 亚信联创 网管产品部
 */
public class MonitorTableExistsServImp implements MonitorTableExistsServ
{

	@Override
	public void afterPropertiesSet() throws Exception
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		String SRE = DateFormatUtils.format(calendar.getTime(), "yyyy_MM");
		for (String tn : new String[] { TA_HA_PRE + SRE, TA_HR_PRE + SRE, TA_HC_PRE + SRE })
		{
			if (!mteDao.queryExistsTable(tn))
			{
				log.fatal("表[" + tn + "]还未创建，发送给用户" + smsReceivers + "！！");
				sendMsg(tn);
			}
		}
	}

	@Override
	public void destroy() throws Exception
	{
	}

	private void sendMsg(String tn)
	{
		for (Object n : smsReceivers)
		{
			Map data = NodeUtil.createElement();
			Map nodeAttr = new HashMap();
			nodeAttr.put("shortMsg", "表[" + tn + "]还未创建！！");
			Map node = NodeUtil.createElement();
			NodeUtil.addAttr(node, nodeAttr);
			NodeUtil.addSubNode(data, node);
			nodeAttr.put("phoneNumber", n);
			smsci.sendAndRecvMsg(smsTopic, data);
		}
	}

	public void setMteDao(MonitorTableExistsDAO mteDao)
	{
		this.mteDao = mteDao;
	}

	public void setSmsci(CorbaMsgSupport smsci)
	{
		this.smsci = smsci;
	}

	public void setSmsReceivers(List smsReceivers)
	{
		this.smsReceivers = smsReceivers;
	}

	public void setSmsTopic(String smsTopic)
	{
		this.smsTopic = smsTopic;
	}

	private Logger log = Logger.getLogger(MonitorTableExistsServImp.class);
	private MonitorTableExistsDAO mteDao;
	private CorbaMsgSupport smsci;
	private List smsReceivers;
	private String smsTopic;
	private String TA_HA_PRE = "ta_historyalarm_".toUpperCase();
	private String TA_HR_PRE = "ta_hisalarm_relation_".toUpperCase();
	private String TA_HC_PRE = "ta_hisalarm_cust_".toUpperCase();
}
