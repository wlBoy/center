package com.xn.hk.common.utils.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
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
	 * get请求，参数拼接在地址上
	 * 
	 * @param url
	 *            请求地址加参数
	 * @return 响应
	 */
	public String get(String url) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(get);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = entityToString(entity);
			}
			return result;
		} catch (IOException e) {
			logger.error(e.getMessage());
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
		return null;
	}

	/**
	 * get请求，参数放在map里
	 * 
	 * @param url
	 *            请求地址
	 * @param map
	 *            参数map
	 * @return 响应
	 */
	public String getMap(String url, Map<String, String> map) {
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
			return result;
		} catch (URISyntaxException e) {
			logger.error(e.getMessage());
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
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

		return null;
	}

	/**
	 * 发送post请求，参数用map接收
	 * 
	 * @param url
	 *            地址
	 * @param map
	 *            参数
	 * @return 返回值
	 */
	public String postMap(String url, Map<String, String> map) {
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
			return result;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
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
		return null;
	}

	/**
	 * post请求，参数为json字符串
	 * 
	 * @param url
	 *            请求地址
	 * @param jsonString
	 *            json字符串
	 * @return 响应
	 */
	public String postJson(String url, String jsonString) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		CloseableHttpResponse response = null;
		try {
			post.setEntity(new ByteArrayEntity(jsonString.getBytes(UTF8)));
			response = httpClient.execute(post);
			if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				result = entityToString(entity);
			}
			return result;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
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
		return null;
	}

	private String entityToString(HttpEntity entity) throws IOException {
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

	/**
	 * 发送get请求,返回JSONObject对象
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public static JSONObject httpGet(String url) {
		// get请求返回结果
		JSONObject jsonResult = null;
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				String strResult = EntityUtils.toString(response.getEntity());
				/** 把json字符串转换成json对象 **/
				jsonResult = JSONObject.parseObject(strResult);
				url = URLDecoder.decode(url, UTF8);
			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		}
		return jsonResult;
	}

	/**
	 * 发送post请求,返回JSONObject对象
	 * 
	 * @param url
	 *            路径
	 * @param jsonParam
	 *            json字符串参数
	 * @return
	 */
	public static JSONObject httpPost(String url, JSONObject jsonParam) {
		return httpPost(url, jsonParam, false);
	}

	/**
	 * 发送post请求,返回JSONObject对象或null
	 * 
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            json字符串参数
	 * @param noNeedResponse
	 *            不需要返回结果，true则返回null，false返回JSONObject对象
	 * @return
	 */
	public static JSONObject httpPost(String url, JSONObject jsonParam, boolean noNeedResponse) {
		// post请求返回结果
		CloseableHttpClient client = HttpClients.createDefault();
		JSONObject jsonResult = null;
		HttpPost method = new HttpPost(url);
		try {
			if (null != jsonParam) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(jsonParam.toString(), UTF8);
				entity.setContentEncoding(UTF8);
				entity.setContentType("application/json");
				method.setEntity(entity);
			}
			HttpResponse result = client.execute(method);
			url = URLDecoder.decode(url, UTF8);
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					/** 读取服务器返回过来的json字符串数据 **/
					str = EntityUtils.toString(result.getEntity());
					if (noNeedResponse) {
						return null;
					}
					/** 把json字符串转换成json对象 **/
					jsonResult = JSONObject.parseObject(str);
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		}
		return jsonResult;
	}
}
