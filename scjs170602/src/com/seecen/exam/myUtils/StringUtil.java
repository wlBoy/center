package com.seecen.exam.myUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 字符串工具类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月23日
 */
public class StringUtil {
	/**
	 * 判断一个字符串不为空
	 * 
	 * @param str
	 *            字符串
	 * @return 不为空返回true,为空返回false
	 */
	public static boolean isNotEmpty(String str) {
		if (null != str && !"".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个字符串为空
	 * 
	 * @param str
	 *            字符串
	 * @return 为空返回true,不为空返回false
	 */
	public static boolean isEmpty(String str) {
		if (!isNotEmpty(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 将日期转为字符串
	 * 
	 * @param date
	 *            要转换的日期
	 * @return 返回该日期格式化后的对应字符串
	 */
	public static String dateToString(Date date) {
		// 24小时制
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 12小时制
		// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sf.format(date);
	}

	/**
	 * 将日期转为字符串
	 * 
	 * @param date
	 *            要转换的日期
	 * @return 返回该日期格式化后的对应字符串
	 */
	public static String simpleDateToString(Date date) {
		// 124小时制
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// 12小时制
		// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sf.format(date);
	}

	/**
	 * 将字符串转为日期
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 返回该字符串格式化后的日期,抛出异常时返回null
	 */
	public static Date stringToDate(String str) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字符串转为日期
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 返回该字符串格式化后的日期,抛出异常时返回null
	 */
	public static Date stringToSimpleDate(String str) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字符串转换为整数int
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 字符串为空返回0，否则返回该字符串对应的整数
	 */
	public static int convertToInt(String str) {
		if (isNotEmpty(str)) {
			return Integer.parseInt(str);
		} else {
			return 0;
		}
	}

	/**
	 * 将字符串转换为浮点double
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 字符串为空返回0.0，否则返回该字符串对应的浮点double
	 */
	public static Double convertToDouble(String str) {
		if (isNotEmpty(str)) {
			return Double.parseDouble(str);
		} else {
			return 0.0;
		}
	}

	/**
	 * 将字符串数组转换为整数int数组
	 * 
	 * @param strs
	 *            要转换的字符串数组
	 * @return 字符串数组为空返回null，否则返回该字符串对应的整数数组
	 */
	public static int[] convertToIntArray(String[] strs) {
		if (strs == null || strs.length == 0) {
			return null;
		}
		int[] nums = new int[strs.length];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = Integer.parseInt(strs[i]);
		}
		return nums;
	}

	/**
	 * 将字符串数组转换为list<Integer>数组
	 * 
	 * @param strs
	 *            要转换的字符串数组
	 * @return 字符串数组为空返回null，否则返回该字符串对应的list<Integer>数组
	 */
	public static List<Integer> convertToIntegerList(String[] strs) {
		if (strs == null || strs.length == 0) {
			return null;
		}
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < strs.length; i++) {
			list.add(Integer.parseInt(strs[i]));
		}
		return list;
	}
	/**
	 * 将字符串数组转换为String
	 * @param strs  字符串数组
	 * @return 返回转换之后的字符串
	 */
	public static String convertToString(String[] strs) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i]);
		}
		return sb.toString();
	}
}
