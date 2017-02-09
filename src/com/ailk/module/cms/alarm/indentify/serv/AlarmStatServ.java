
package com.ailk.module.cms.alarm.indentify.serv;

import com.ailk.module.cms.system.basesupport.BaseSupportServ;

/**
 * @author yuanj(65478) Tel:18652040107
 * @version 1.0
 * @since 2012-7-3 下午9:54:40
 * @category com.ailk.module.cms.alarm.indentify.serv
 * @copyright 亚信联创 网管产品部
 */
public interface AlarmStatServ extends BaseSupportServ
{

	void onceStat();

	void cycleStat();
}
