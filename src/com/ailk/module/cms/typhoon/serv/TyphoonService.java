package com.ailk.module.cms.typhoon.serv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * 该java的描述信息
 * 
 * @author shiliang Tel:18661205639
 * @version 1.0
 * @since 2014-6-17 上午10:11:37
 * @category com.ailk.module.cms.typhoon.serv
 * @copyright
 */
public class TyphoonService {
	private static final Logger log = Logger.getLogger(TyphoonService.class);

	public static String getRequestResult(String requestUrl) {
		log.info("请求地址：" + requestUrl);
		String result = new String();
		// 查询并解析结果
		try {
			// 查询并获取返回结果
			result = httpRequest(requestUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.replace("<?xml version=\"1.0\"?>", "<?xml version=\"1.0\" encoding=\"gbk\" ?>");
	}

	public static String httpRequest(String requestUrl) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();
			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString().replace("utf-8", "gbk");
	}

}
