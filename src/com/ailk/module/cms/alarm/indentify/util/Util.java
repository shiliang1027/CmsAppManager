
package com.ailk.module.cms.alarm.indentify.util;

/**
 * @author yuanj(65478) Tel:18652040107
 * @version 1.0
 * @since 2012-7-3 下午9:16:09
 * @category com.ailk.module.cms.alarm.indentify.util
 * @copyright 亚信联创 网管产品部
 */
public final class Util
{

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	static public boolean isn(String s)
	{
		return null == s || "".equals(s.trim()) || "null".equalsIgnoreCase(s.trim());
	}
}
