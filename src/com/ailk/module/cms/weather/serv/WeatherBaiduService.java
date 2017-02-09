package com.ailk.module.cms.weather.serv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class WeatherBaiduService {
	private static final Logger log = Logger.getLogger(WeatherBaiduService.class);
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

	/**
	 * 
	 * utf编码
	 * 
	 * 
	 * 
	 * @param source
	 * 
	 * @return
	 */

	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getWeather(String url,String source,String output) {
		String json = new String();
		// 组装查询地址
		String requestUrl = url.replace("{keyWord}", urlEncodeUTF8(source)).replace("{output}", output);
		log.info("天气查询地址："+requestUrl);
		// 查询并解析结果
		try {
			// 查询并获取返回结果
			json = httpRequest(requestUrl);
			// 利用正则表达式去除json数据中形如转义字符
			/*
			 * Pattern p = Pattern.compile("\\\\/");
			 * 
			 * Matcher m = p.matcher(json);
			 * 
			 * StringBuffer buf = new StringBuffer();
			 * 
			 * while(m.find()){
			 * 
			 * m.appendReplacement(buf, "\\/");
			 * 
			 * }
			 * 
			 * m.appendTail(buf);
			 * 
			 * json=buf.toString();
			 */
			// json=unicodeToUtf8(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	// 将json数据中的unicode编码转换为UTF-8编码格式，以便正常显示中文

	public static String unicodeToUtf8(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
							"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	// 去除json数据中的空格，换行等符号；

	public static String replaceBlank(String str) {
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			str = m.replaceAll("");
		}
		return str;
	}
}
