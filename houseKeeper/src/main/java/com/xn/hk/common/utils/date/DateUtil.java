package com.xn.hk.common.utils.date;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: DateFormatUtil
 * @Package: com.xn.hk.common.utils
 * @Description: 日期格式处理、转换工具类
 * @Author: wanlei
 * @Date: 2018年8月22日 上午9:12:16
 */
public final class DateUtil {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	// 定义日期格式
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATE_SECOND = "yyMMddHHmmssSSS";
	public static final String PATTERN_SIMPLE_DATE = "yyyyMMdd";
	private static final String[] DAY_OF_WEEK = new String[] { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY",
			"FRIDAY", "SATURDAY" };

	/**
	 * 把当前时间格式化为yyyy-MM-dd格式
	 * 
	 * @return 格式化后的日期时间字符串
	 */
	public static String formatDate() {
		return formatDate(new Date());
	}

	/**
	 * 把日期时间格式化为yyyy-MM-dd格式
	 * 
	 * @return 格式化后的日期时间字符串
	 */
	public static String formatDate(Date date) {
		if (date != null) {
			SimpleDateFormat dateStyle = new SimpleDateFormat(PATTERN_DATE);
			return dateStyle.format(date);
		}
		return "";
	}

	/**
	 * 当前系统时间生成唯一标识的字符串
	 * 
	 * @param date
	 *            java.util.Date
	 * @return 格式化后的日期时间字符串
	 */
	public static String genCurrentSecond() {
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE_SECOND);
		return (sdf.format(new Date(System.currentTimeMillis())) + Math.abs(new Random().nextInt(64)));
	}

	/**
	 * 把当前时间格式化为yyyy-MM-dd HH:mm:ss格式
	 * 
	 * @return 格式化后的日期时间字符串
	 */
	public static String formatDateTime() {
		return formatDateTime(new Date());
	}

	/**
	 * 把日期时间格式化为yyyy-MM-dd HH:mm:ss格式
	 * 
	 * @param date
	 *            java.util.Date
	 * @return 格式化后的日期时间字符串
	 */
	public static String formatDateTime(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE_TIME, Locale.CHINESE);
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * 把当前时间格式化为指定格式
	 * 
	 * @param dt
	 *            java.util.Date
	 * @return 格式化后的日期时间字符串
	 */
	public static String formatDateTime(String pattern) {
		return formatDateTime(new Date(), pattern);
	}

	/**
	 * 把日期时间格式化为指定格式
	 * 
	 * @param dt
	 *            java.util.Date
	 * @return 格式化后的日期时间字符串
	 */
	public static String formatDateTime(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINESE);
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * 将指定的字符串转换成日期
	 * 
	 * @return 返回日期格式yyyy-MM-dd HH:mm:ss
	 */
	public static Date parseStrToDate(String dateStr) {
		return parseStrToDate(dateStr, PATTERN_DATE_TIME);
	}

	/**
	 * 按照不同的格式模板将指定的字符串转换成日期。
	 * 
	 * @param date:
	 *            待转换的日期符串
	 * @param pattern:
	 *            字符串的格式模板,例如:yyyy-MM-dd hh:mm:ss
	 * @return 与字符串dateStr对应的date对象
	 */
	public static Date parseStrToDate(String datestr, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(datestr);
		} catch (ParseException e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	/**
	 * 获得当前系统的时间戳
	 * 
	 * @return 从1970-1-1到现在的毫秒数
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 在给定的日期点上加入指定的天数
	 * 
	 * @param date
	 *            给定的日期点
	 * @param days
	 *            天数，正数为向后；负数为向前
	 * @return 返回改变后的时间点
	 */
	public static Date addDate(Date date, int days) {
		if (date == null)
			return date;
		Locale loc = Locale.getDefault();
		GregorianCalendar cal = new GregorianCalendar(loc);
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		cal.set(year, month, day + days);
		return cal.getTime();
	}

	/**
	 * 在当前的日期点上加入指定的天数
	 * 
	 * @param days
	 *            天数，正数为向后；负数为向前
	 * @return 返回改变后的时间
	 */
	public static Date addDate(int days) {
		Locale loc = Locale.getDefault();
		GregorianCalendar cal = new GregorianCalendar(loc);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		cal.set(year, month, day + days);
		return cal.getTime();
	}

	/**
	 * 获得两个时间点之间相差的天数
	 * 
	 * @param date1
	 *            开始时间点
	 * @param date2
	 *            结束时间点
	 * @return 具体的天数
	 */
	public static int getDays(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;
		return (int) ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 获得给定时间到当前时间的天数
	 * 
	 * @param dateStr
	 *            给定时间
	 * @return 具体的天数
	 */
	public static int getDays(String dateStr) {
		if (StringUtil.isEmpty(dateStr))
			return 0;
		Date date = null;
		date = DateUtil.parseStrToDate(dateStr, DateUtil.PATTERN_DATE_TIME);
		return DateUtil.getDays(date, new Date());
	}

	/**
	 * 获得两个时间点之间相差的秒数
	 * 
	 * @param date1
	 *            开始时间点
	 * @param date2
	 *            结束时间点
	 * @return 具体的天数
	 */
	public static int getValideTimeSeconds(Date date1, Date date2) {
		int gtime = 0;
		if (date1 == null || date2 == null)
			return gtime;
		gtime = (int) ((date2.getTime() - date1.getTime()) / (1000));
		return gtime;
	}

	/**
	 * 获得两个时间点之间相差的秒数
	 * 
	 * @param date1
	 *            开始时间点
	 * @param date2
	 *            结束时间点
	 * @return 具体的天数
	 */
	public static int getValideTimeSeconds(Date date1, String date2) {
		int nextUpdateTime = 0;
		Date date = DateUtil.parseStrToDate(date2, DateUtil.PATTERN_DATE_TIME);
		nextUpdateTime = DateUtil.getValideTimeSeconds(date1, date);
		return nextUpdateTime;
	}

	/**
	 * 返回较小的时间
	 * 
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return 返回较小的时间
	 */
	public static Date getMinDate(Date date1, String date2) {
		Date date = DateUtil.parseStrToDate(date2, DateUtil.PATTERN_DATE_TIME);
		if (date.getTime() > System.currentTimeMillis()) {
			if (date1 == null) {
				date1 = date;
			} else if (date.getTime() < date1.getTime()) {
				date1 = date;
			}
		}
		return date1;
	}

	/**
	 * 返回每月最后一天的日期,格式为yyyy-MM-dd
	 * 
	 * @param month
	 *            月份
	 * @return 返回每月最后一天的日期
	 */
	public static String getLastDayOfMonth(String month) {
		Calendar cal = Calendar.getInstance();
		String day = month + "-01";
		cal.setTime(DateUtil.parseStrToDate(day));
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return DateUtil.formatDate(cal.getTime());
	}

	/**
	 * 返回每月第一天的日期,格式为yyyy-MM-dd
	 * 
	 * @param month
	 *            月份
	 * @return 返回每月第一天的日期
	 */
	public static String getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return DateUtil.formatDate(cal.getTime());
	}

	/**
	 * 返回当前时间月份第一天的日期,格式为yyyy-MM-dd
	 * 
	 * @return 返回当前时间月份第一天的日期
	 */
	public static String getFirstDayOfMonth() {
		return DateUtil.getFirstDayOfMonth(new Date());
	}

	/**
	 * 返回每月第一天的日期,格式为yyyy-MM-dd
	 * 
	 * @return 返回每月第一天的日期
	 */
	public static String getFirstDayOfLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		return DateUtil.getFirstDayOfMonth(cal.getTime());
	}

	/**
	 * 获取昨天的日期,格式为yyyy-MM-dd
	 * 
	 * @return 获取昨天的日期
	 */
	public static String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return DateUtil.formatDate(cal.getTime());
	}

	/**
	 * 返回当天是周几
	 * 
	 * @return SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
	 */
	public static String getDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		return DateUtil.DAY_OF_WEEK[dayOfWeek - 1];
	}

	/**
	 * 返回当前时间是该月的几号
	 * 
	 * @return 1-31号的数字
	 */
	public static int getDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 将当前时间转换为格式为yyyyMMdd的日期数字返回
	 * 
	 * @return
	 */
	public static int getNumberDay() {
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_SIMPLE_DATE);
		return Integer.parseInt(sdf.format(new Date()));
	}

	/**
	 * 返回两个日期相差的月数
	 * 
	 * @param sDay
	 *            起始日期，格式为yyyy-MM-dd
	 * @param eDay
	 *            结束日期，格式为yyyy-MM-dd
	 * @return 两个日期相差的月数，结果为整数
	 */
	public static int getMonthsBetweenTowDays(String sDay, String eDay) {
		Calendar sCal = Calendar.getInstance();
		sCal.setTime(DateUtil.parseStrToDate(sDay));
		Calendar eCal = Calendar.getInstance();
		eCal.setTime(DateUtil.parseStrToDate(eDay));

		return (eCal.get(Calendar.YEAR) - sCal.get(Calendar.YEAR)) * 12
				+ (eCal.get(Calendar.MONTH) - sCal.get(Calendar.MONTH));
	}

	/**
	 * 将特定格式字符串时间格式化为另一种特定格式
	 * 
	 * @param dateTime
	 *            时间
	 * @param srcPattern
	 *            原时间格式
	 * @param destPattern
	 *            新时间格式
	 * @return
	 */
	public static String format(String dateTime, String srcPattern, String destPattern) {
		Date date = DateUtil.parseStrToDate(dateTime, srcPattern);
		return DateUtil.formatDateTime(date, destPattern);
	}

	/**
	 * 从字符日期中得到月份
	 * 
	 * @param dateString
	 *            格式为:yyyy-MM-dd
	 * @return
	 */
	public static String getMonth(String dateString) {
		return dateString.substring(4, 7).replace("-", "");
	}

	/**
	 * 取得当前时间戳（精确到秒）
	 *
	 * @return nowTimeStamp
	 */
	public static long getNowTimeStamp() {
		return getCurrentTimeMillis() / 1000;
	}

	/**
	 * 把日期时间格式化为yyyy-MM-dd HH:mm:ss并进行url编码
	 * 
	 * @param date
	 *            java.util.Date
	 * @return 格式化后的日期时间字符串
	 */
	public static String formatDateTimeToUrl(Date date) {
		return formatDateTimeToUrl(date, PATTERN_DATE_TIME);
	}

	/**
	 * 把日期时间格式化为指定格式格式并进行url编码
	 * 
	 * @param date
	 *            java.util.Date
	 * @param pattern
	 *            格式化字符串
	 * @return 格式化后的日期时间字符串
	 */
	public static String formatDateTimeToUrl(Date date, String pattern) {
		String newDate = formatDateTime(new Date(), pattern);
		try {
			newDate = URLEncoder.encode(newDate, Constant.UTF8);
		} catch (UnsupportedEncodingException e) {
			logger.info(e.getMessage());
		}
		return newDate;
	}

	public static void main(String[] args) {
		System.out.println(genCurrentSecond());
	}
}
