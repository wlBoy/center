package com.kl.ar.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kl.ar.common.constant.Constant;

/**
 * 
 * @package:com.kl.ar.common.util
 * @Description:  日期格式处理、转换工具类
 * @author: wanlei
 * @date: 2019年12月21日下午2:57:26
 */
public final class DateUtil {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	// 定义日期格式
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String SHORT_PATTERN_DATE_TIME = "yyyyMMddHHmmss";
	public static final String PATTERN_DATE_SECOND = "yyMMddHHmmssSSS";
	public static final String PATTERN_SIMPLE_DATE = "yyyyMMdd";
	private static final String[] DAY_OF_WEEK = new String[] { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY",
			"FRIDAY", "SATURDAY" };
	// 初始化日历实例
	private static final Calendar gregorianCalendar = new GregorianCalendar();

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
	public static Date parseStrToDateTime(String dateStr) {
		return parseStrToDate(dateStr, PATTERN_DATE_TIME);
	}

	/**
	 * 将指定的字符串转换成日期
	 * 
	 * @return 返回日期格式yyyy-MM-dd
	 */
	public static Date parseStrToDate(String dateStr) {
		return parseStrToDate(dateStr, PATTERN_DATE);
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
	 * 获取指定几个月前的时间，返回格式为:yyyy-MM-dd
	 * 
	 * @param month
	 *            月份数
	 * @return 获取指定几个月前的时间
	 */
	public static String getBeforeMonth(int month) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -month);
		return formatDate(c.getTime());
	}

	/**
	 * 获取指定时间段的每一天，返回格式为:yyyy-MM-dd
	 * 
	 * @param 开始日期
	 * @param 结算日期
	 * @return 日期列表,返回格式为yyyy-MM-dd的时间字符串
	 */
	public static List<String> getEveryDay(String startDateStr, String endDateStr) {
		List<String> dates = new ArrayList<String>();
		if (StringUtil.isEmpty(startDateStr) || StringUtil.isEmpty(endDateStr)) {
			return dates;
		}
		// 格式化日期(yy-MM-dd)
		Date startDate = parseStrToDate(startDateStr);
		Date endDate = parseStrToDate(endDateStr);
		gregorianCalendar.setTime(startDate);
		dates.add(formatDate(gregorianCalendar.getTime()));
		while (gregorianCalendar.getTime().compareTo(endDate) < 0) {
			// 加1天
			gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
			dates.add(formatDate(gregorianCalendar.getTime()));
		}
		return dates;
	}

	/**
	 * 从字符日期中得到月份
	 * 
	 * @param dateString
	 *            格式为:yyyy-MM-dd
	 * @return 返回月份
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

	/**
	 * 得到最近天数的map集合,key为描述,value为yyyy-MM-dd格式的时间字符串
	 * 
	 * @return 最近天数的map集合
	 */
	public static Map<String, Object> getRecentDayMap() {
		Map<String, Object> logTimeMap = new TreeMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DATE, -1);
		logTimeMap.put("最近一天", formatDate(cal.getTime()));
		cal.add(Calendar.DATE, -6);
		logTimeMap.put("最近一周", formatDate(cal.getTime()));
		cal.add(Calendar.DATE, -8);
		logTimeMap.put("最近半月", formatDate(cal.getTime()));
		cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		logTimeMap.put("最近1月", formatDate(cal.getTime()));
		return logTimeMap;
	}

	/**
	 * 得到最近月份的map集合,key为描述,value为yyyy-MM-dd格式的时间字符串
	 * 
	 * @return 最近月份的map集合
	 */
	public static Map<String, Object> getRecentMonthMap() {
		Map<String, Object> logTimeMap = new TreeMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH, -1);
		logTimeMap.put("最近1月", formatDate(cal.getTime()));
		cal.add(Calendar.MONTH, -2);
		logTimeMap.put("最近3月", formatDate(cal.getTime()));
		cal.add(Calendar.MONTH, -3);
		logTimeMap.put("最近6月", formatDate(cal.getTime()));
		cal.add(Calendar.MONTH, -6);
		logTimeMap.put("最近1年", formatDate(cal.getTime()));
		return logTimeMap;
	}

	/**
	 * 获取日期前一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayBefore(Date date) {
		gregorianCalendar.setTime(date);
		int day = gregorianCalendar.get(Calendar.DATE);
		gregorianCalendar.set(Calendar.DATE, day - 1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取日期后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayAfter(Date date) {
		gregorianCalendar.setTime(date);
		int day = gregorianCalendar.get(Calendar.DATE);
		gregorianCalendar.set(Calendar.DATE, day + 1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static int getNowYear() {
		Calendar d = Calendar.getInstance();
		return d.get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份
	 * 
	 * @return
	 */
	public static int getNowMonth() {
		Calendar d = Calendar.getInstance();
		return d.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前日
	 * 
	 * @return
	 */
	public static int getNowDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取当前时
	 * 
	 * @return
	 */
	public static int getNowHour() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR);
	}

	/**
	 * 获取哪一年共有多少周
	 * 
	 * @param year
	 * @return
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		return getWeekNumOfYear(c.getTime());
	}

	/**
	 * 取得某天是一年中的多少周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekNumOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取当月天数
	 * 
	 * @return
	 */
	public static int getNowMonthDay() {
		Calendar d = Calendar.getInstance();
		return d.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 得到一年中的所有月份的数字集合
	 * 
	 * @return 得到一年中的所有月份的数字集合
	 */
	public static List<Integer> getSumMonthList() {
		List<Integer> yearList = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			yearList.add(i);
		}
		return yearList;
	}

	/**
	 * 得到一月中的所有日的数字集合
	 * 
	 * @return 得到一月中的所有日的数字集合
	 */
	public static List<Integer> getSumDayList() {
		List<Integer> yearList = new ArrayList<Integer>();
		for (int i = 1; i <= 31; i++) {
			yearList.add(i);
		}
		return yearList;
	}

	/**
	 * 得到一天中的所有日的数字集合
	 * 
	 * @return 得到一天中的所有小时的数字集合
	 */
	public static List<Integer> getSumHourList() {
		List<Integer> hourList = new ArrayList<Integer>();
		for (int i = 0; i < 24; i++) {
			hourList.add(i);
		}
		return hourList;
	}

	/**
	 * 获取当前月的第一天
	 * 
	 * @return date
	 */
	public static Date getFirstDayOfMonth() {
		return getFirstDayOfMonth(new Date());
	}

	/**
	 * 获取指定月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		gregorianCalendar.setTime(date);
		gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取当前月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfMonth() {
		return getLastDayOfMonth(new Date());
	}

	/**
	 * 获取指定月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		gregorianCalendar.setTime(date);
		gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
		gregorianCalendar.add(Calendar.MONTH, 1);
		gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 增加时间，calendarField解释:1则代表的是对年份操作， 2是对月份操作， 3是对星期操作， 5是对日期操作， 11是对小时操作，
	 * 12是对分钟操作， 13是对秒操作， 14是对毫秒操作
	 * 
	 * @param date
	 *            时间
	 * @param calendarField
	 *            日历字段
	 * @param amount
	 *            增加数
	 * @return 增加之后的时间
	 */
	private static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(calendarField, amount);
			return c.getTime();
		}
	}

	/**
	 * 增加年
	 * 
	 * @param date
	 *            时间
	 * @param amount
	 *            增加数
	 * @return 增加之后的时间
	 */
	public static Date addYears(Date date, int amount) {
		return add(date, 1, amount);
	}

	/**
	 * 增加月
	 * 
	 * @param date
	 *            时间
	 * @param amount
	 *            增加数
	 * @return 增加之后的时间
	 */
	public static Date addMonths(Date date, int amount) {
		return add(date, 2, amount);
	}

	/**
	 * 增加周
	 * 
	 * @param date
	 *            时间
	 * @param amount
	 *            增加数
	 * @return 增加之后的时间
	 */
	public static Date addWeeks(Date date, int amount) {
		return add(date, 3, amount);
	}

	/**
	 * 增加天
	 * 
	 * @param date
	 *            时间
	 * @param amount
	 *            增加数
	 * @return 增加之后的时间
	 */
	public static Date addDays(Date date, int amount) {
		return add(date, 5, amount);
	}

	/**
	 * 增加时
	 * 
	 * @param date
	 *            时间
	 * @param amount
	 *            增加数
	 * @return 增加之后的时间
	 */
	public static Date addHours(Date date, int amount) {
		return add(date, 11, amount);
	}

	/**
	 * 增加分
	 * 
	 * @param date
	 *            时间
	 * @param amount
	 *            增加数
	 * @return 增加之后的时间
	 */
	public static Date addMinutes(Date date, int amount) {
		return add(date, 12, amount);
	}

	/**
	 * 增加秒
	 * 
	 * @param date
	 *            时间
	 * @param amount
	 *            增加数
	 * @return 增加之后的时间
	 */
	public static Date addSeconds(Date date, int amount) {
		return add(date, 13, amount);
	}

	/**
	 * 增加毫秒
	 * 
	 * @param date
	 *            时间
	 * @param amount
	 *            增加数
	 * @return 增加之后的时间
	 */
	public static Date addMilliseconds(Date date, int amount) {
		return add(date, 14, amount);
	}

	public static void main(String[] args) {
		System.out.println(genCurrentSecond());
		Map<String, Object> timeMap = getRecentDayMap();
		for (String key : timeMap.keySet()) {
			System.out.println(key + "-" + timeMap.get(key));
		}
		List<Integer> list = getSumHourList();
		for (Integer integer : list) {
			System.out.println(integer);
		}
		List<String> everyDay = getEveryDay("2018-11-1", "2018-11-20");
		for (String string : everyDay) {
			System.out.println(string);
		}
		System.out.println(getNowMonthDay());
		System.out.println(formatDate(getDayBefore(new Date())));
		System.out.println(formatDate(getDayAfter(new Date())));
		System.out.println(getWeekNumOfYear(new Date()));
		System.out.println(getMaxWeekNumOfYear(2018));
	}
}
