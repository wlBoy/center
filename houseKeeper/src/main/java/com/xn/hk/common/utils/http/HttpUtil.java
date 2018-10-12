package com.xn.hk.common.utils.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @Title: HttpUtil
 * @Package: com.xn.hk.common.utils.http
 * @Description: 发送http请求的工具类
 * @Author: wanlei
 * @Date: 2018年1月12日 下午12:20:03
 */
public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private static final String UTF8 = "UTF-8";

	/**
	 * 发送get请求，参数拼接在地址上key=value&key=value
	 * 
	 * @param url
	 *            请求地址加参数
	 * @return 响应字符串
	 */
	public static Object httpGet(String url) {
		return httpGet(url, false);
	}

	/**
	 * 发送get请求，参数拼接在地址上key=value&key=value
	 * 
	 * @param url
	 *            请求地址加参数
	 * @param needResponseJsonObject
	 *            是否需要响应jsonObject对象，为true则返回jsonObject对象，false返回字符串
	 * @return 响应jsonObject对象或响应字符串
	 */
	public static Object httpGet(String url, boolean needResponseJsonObject) {
		Object result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(get);
			if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String resultStr = entityToString(response.getEntity());
				if (needResponseJsonObject) {
					// 把字符串转换成json对象
					result = JSONObject.parseObject(resultStr);
				} else {
					result = resultStr;
				}
			}
		} catch (IOException e) {
			logger.error("get请求{}失败，失败原因为:", url, e);
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param map
	 *            参数map
	 * @return 响应
	 */
	public static String httpGetMap(String url, Map<String, String> map) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			builder.setParameters(pairs);
			HttpGet get = new HttpGet(builder.build());
			response = httpClient.execute(get);
			if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				result = entityToString(entity);
			}
		} catch (URISyntaxException e) {
			logger.error(e.getMessage());
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error("get请求{}失败，失败原因为:", url, e);
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 *            地址
	 * @param map
	 *            参数map集合
	 * @return 响应字符串
	 */
	public static String httpPostMap(String url, Map<String, String> map) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		CloseableHttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs, UTF8));
			response = httpClient.execute(post);
			if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				result = entityToString(entity);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error("post请求{}失败，失败原因为:", url, e);
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}

		}
		return result;
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param json
	 *            json字符串
	 * @return 响应字符串
	 */
	public static Object httpPost(String url, String json) {
		return httpPost(url, json, false);
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param json
	 *            json字符串
	 * @param needResponseJsonObject
	 *            是否需要响应jsonObject对象，为true则返回jsonObject对象，false返回字符串
	 * @return 响应jsonObject对象或响应字符串
	 */
	public static Object httpPost(String url, String json, boolean needResponseJsonObject) {
		Object result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		CloseableHttpResponse response = null;
		try {
			post.setEntity(new ByteArrayEntity(json.getBytes(UTF8)));
			response = httpClient.execute(post);
			if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String resultStr = entityToString(response.getEntity());
				if (needResponseJsonObject) {
					// 把字符串转换成json对象
					result = JSONObject.parseObject(resultStr);
				} else {
					result = resultStr;
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error("post请求{}失败，失败原因为:", url, e);
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 将实体转换为字符串(重写EntityUtils.toString方法)
	 * 
	 * @param entity
	 *            实体
	 * @return 字符串
	 * @throws IOException
	 */
	private static String entityToString(HttpEntity entity) throws IOException {
		String result = null;
		if (entity != null) {
			long lenth = entity.getContentLength();
			if (lenth != -1 && lenth < 2048) {
				result = EntityUtils.toString(entity, UTF8);
			} else {
				InputStreamReader reader1 = new InputStreamReader(entity.getContent(), UTF8);
				CharArrayBuffer buffer = new CharArrayBuffer(2048);
				char[] tmp = new char[1024];
				int l;
				while ((l = reader1.read(tmp)) != -1) {
					buffer.append(tmp, 0, l);
				}
				result = buffer.toString();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		String getUrl = "http://127.0.0.1:8030/houseKeeper/system/rest/test.do?userId=1&userName=张三";
		String postUrl = "http://127.0.0.1:8030/houseKeeper/system/rest/test.do";
		System.out.println("*******测试get请求***********");
		Object resultObj = httpGet(getUrl, true);
		if (resultObj != null) {
			if (resultObj instanceof String) {
				System.out.println("返回的字符串为:" + resultObj.toString());
			} else {
				JSONObject jsonObj = (JSONObject) resultObj;
				System.out.println("返回的字符串为:" + jsonObj.toJSONString());
			}
		}
		Map<String, String> datamap = new HashMap<String, String>();
		datamap.put("userId", "45");
		datamap.put("userName", "测试");
		System.out.println("返回的字符串为:" + httpGetMap(postUrl, datamap));
		System.out.println("*******测试post请求***********");
		String json = "{\"userId\":789,\"userName\":\"李柳\"}";
		Object resultObject = httpPost(postUrl, json, true);
		if (resultObject != null) {
			if (resultObject instanceof String) {
				System.out.println("返回的字符串为:" + resultObject.toString());
			} else {
				JSONObject jsonObj = (JSONObject) resultObject;
				System.out.println("返回的字符串为:" + jsonObj.toJSONString());
			}
		}
		System.out.println("返回的字符串为:" + httpPostMap(postUrl, datamap));

	}
}
