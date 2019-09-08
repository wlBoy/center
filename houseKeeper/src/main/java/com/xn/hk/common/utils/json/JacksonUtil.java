package com.xn.hk.common.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @ClassName: FastjsonUtil
 * @Package: com.xn.hk.common.utils.string
 * @Description: 使用jackson操作json的工具类 1.对象转json字符串 2.字符串转化为对象 3.字符串转化为ArrayList对象
 *               4.字符串转化为ArrayList的HashMap对象 5.HashMap对象转对象
 * @Author: wanlei
 * @Date: 2018年9月5日 上午11:38:01
 */
public class JacksonUtil<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtil.class);
	private static ObjectMapper objectMapper;
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private String timeFormat;

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
		objectMapper.setDateFormat(new SimpleDateFormat(timeFormat));
	}

	public JacksonUtil() {
		objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));
	}

	public JacksonUtil(String timeFormat) {
		objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.setDateFormat(new SimpleDateFormat(timeFormat));
	}

	/**
	 * 对象转json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSon(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			LOGGER.error("对象转json字符串", e);
		}
		return "";
	}

	/**
	 * 字符串转化为对象
	 * 
	 * @param v
	 * @param json
	 * @return
	 */
	public T getObjectFromStr(Class<T> v, String json) {
		try {
			return objectMapper.readValue(json.getBytes(), objectMapper.constructType(v));
		} catch (IOException e) {
			LOGGER.error("字符串转化为对象异常", e);
		}
		return null;
	}

	/**
	 * HashMap对象转对象
	 * 
	 * @param v
	 * @param map
	 * @return
	 */
	public T getObjectFromMap(Class<T> v, HashMap<String, Object> map) {
		return objectMapper.convertValue(map, objectMapper.getTypeFactory().constructType(v));
	}

	/**
	 * 字符串转化为ArrayList对象
	 * 
	 * @param v
	 * @param json
	 * @return
	 */
	public List<T> getArrayListObjectFromStr(Class<T> v, String json) {
		try {
			return objectMapper.readValue(json.getBytes(),
					objectMapper.getTypeFactory().constructParametricType(ArrayList.class, v));
		} catch (IOException e) {
			LOGGER.error("字符串转化为ArrayList对象异常", e);
		}
		return null;
	}

	/**
	 * 字符串转化为ArrayList的HashMap对象
	 * 
	 * @param json
	 * @return
	 */
	public List<T> getArrayListMapFromStr(String json) {
		try {
			return objectMapper.readValue(json.getBytes(),
					objectMapper.getTypeFactory().constructParametricType(ArrayList.class, HashMap.class));
		} catch (IOException e) {
			LOGGER.error("字符串转化为ArrayList的HashMap对象异常", e);
		}
		return null;
	}

}
