
package com.ailk.module.cms.alarm.indentify.util;

/**
 * @author yuanj(65478) Tel:18652040107
 * @version 1.0
 * @since 2012-7-3 ����9:16:09
 * @category com.ailk.module.cms.alarm.indentify.util
 * @copyright �������� ���ܲ�Ʒ��
 */
public final class Util
{

	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * 
	 * @param s
	 * @return
	 */
	static public boolean isn(String s)
	{
		return null == s || "".equals(s.trim()) || "null".equalsIgnoreCase(s.trim());
	}
}
