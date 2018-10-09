package com.xn.hk.common.utils.string;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xn.hk.common.constant.Result;

/**
 * 
 * @ClassName: JsonUtil
 * @Package: com.xn.hk.common.utils.string
 * @Description: 使用fastjson操作json的工具类 1.对象转json字符串 2.字符串转化为对象 3.字符串转化为ArrayList对象
 *               4.字符串转化为ArrayList的HashMap对象 5.HashMap对象转对象
 * @Author: wanlei
 * @Date: 2018年9月5日 上午11:38:01
 */
public class JsonUtil {
	private static final SerializeConfig config;

	static {
		config = new SerializeConfig();
		config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
		config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
	}

	private static final SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
	};

	/**
	 * 将Object对象转换为json字符串，空值处理
	 * 
	 * @param object
	 *            对象
	 * @return json字符串
	 */
	public static String object2Json(Object object) {
		return JSON.toJSONString(object, config, features);
	}

	/**
	 * 将Object对象转换为json字符串,没有空值处理
	 * 
	 * @param object
	 *            对象
	 * @return json字符串
	 */
	public static String object2JsonNoFeatures(Object object) {
		return JSON.toJSONString(object, config);
	}

	/**
	 * 将json字符串转换成object对象
	 * 
	 * @param json
	 *            json字符串
	 * @return object对象
	 */
	public static Object jsonToObject(String json) {
		return JSON.parse(json);
	}

	/**
	 * 将json字符串转换成object对象
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            类的字节码
	 * @return object对象
	 */
	public static <T> T jsonToBean(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}

	/**
	 * 将json字符串转换成object对象数组
	 * 
	 * @param json
	 *            json字符串
	 * @return object对象数组
	 */
	public static <T> Object[] jsonToArray(String json) {
		return jsonToArray(json, null);
	}

	/**
	 * 将json字符串转换成object对象数组
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            类的字节码
	 * @return object对象数组
	 */
	public static <T> Object[] jsonToArray(String json, Class<T> clazz) {
		return JSON.parseArray(json, clazz).toArray();
	}

	/**
	 * 将json字符串转换成object对象list数组
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            类的字节码
	 * @return object对象数组
	 */
	public static <T> List<T> jsonToList(String json, Class<T> clazz) {
		return JSON.parseArray(json, clazz);
	}

	/**
	 * 将json字符串转化为map集合
	 * 
	 * @param s
	 *            json字符串
	 * @return map集合
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> jsonToMap(String json) {
		Map<K, V> m = (Map<K, V>) JSONObject.parseObject(json);
		return m;
	}

	/**
	 * 转换JSON字符串为对象
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            类的字节码
	 * @return 类对象
	 */
	public static Object jsonToObject(String json, Class<?> clazz) {
		return JSONObject.parseObject(json, clazz);
	}

	/**
	 * 将map集合转化为json字符串
	 * 
	 * @param map
	 *            map集合
	 * @return json字符串
	 */
	public static <K, V> String mapToJson(Map<K, V> map) {
		String json = JSONObject.toJSONString(map);
		return json;
	}
	public static void main(String[] args) {
		Result result = new Result();
		result.setCode(0);
		result.setMsg("新密码与旧密码一致!");
		result.setData(null);
	}
}