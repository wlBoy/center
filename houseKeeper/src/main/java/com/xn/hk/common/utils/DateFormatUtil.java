package com.xn.hk.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * 日期格式处理、转换工具类
 * 
 * @author WuQiaoYun
 */
public final class DateFormatUtil {
	/**
	 * 长日期型
	 */
	public static final int LONG = DateFormat.LONG;

	/**
	 * 中日期型
	 */
	public static final int MEDIUM = DateFormat.MEDIUM;

	/**
	 * 短日期型
	 */
	public static final int SHORT = DateFormat.SHORT;

	public static final String PATTERN_YYMM = "yyMM";
	public static final String PATTERN_yyyyMM = "yyyyMM";
	public static final String PATTERN_MONTH = "yyyy-MM";
	public static final String PATTERN_SHORT_DAY = "MM-dd";
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_TIME = "HH:mm:ss";
	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_yyMMddHHmmss = "yyMMddHHmmss";
	public static final String PATTERN_yyyyMMddHH = "yyyyMMddHH";
	public static final String PATTERN_yyyyMMdd = "yyyyMMdd";

	private static final String[] DAY_OF_WEEK = new String[] { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };

	public static final String MONDAY = "MONDAY";
	public static final String THURSDAY = "THURSDAY";
	public static final String SATURDAY = "SATURDAY";

	/**
	 * 按照给定的格式style将指定的日期值转换成字符串。
	 * 
	 * @param date:
	 *            待转换的日期
	 * @param style:
	 *            指定转化类型,style参数取静态常量LONG、MEDIUM和SHORT的值
	 * @param loc：字符定义对象
	 * @return 格式化后的日期字符串
	 * @throws IllegalArgumentException:
	 *             style模板不符合格式时报异常
	 */
	public static String formatDate(Date date, int style, Locale loc) {
		String newDate = "";
		if (loc == null) {
			loc = Locale.getDefault();
		}
		if (date != null) {
			DateFormat df = DateFormat.getDateInstance(style, loc);
			newDate = df.format(date);
		}
		return newDate;
	}

	/**
	 * 按照给定的格式模板将指定的日期值转换成字符串。
	 * 
	 * @param date:
	 *            待转换的日期
	 * @param pattern:
	 *            指定转化格式字符串,例如：yyyy-MM-dd
	 * @param loc:
	 *            字符定义对象
	 * @return 格式化后的日期字符串
	 * @throws IllegalArgumentException:
	 *             pattern模板不符合格式时报异常
	 */
	public static String formatDate(Date date, String pattern, Locale loc) {
		String newDate = "";
		if (loc == null) {
			loc = Locale.getDefault();
		}
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, loc);
			newDate = sdf.format(date);
		}
		return newDate;
	}

	/**
	 * 按照不同的日期格式和时间格式，将指定的日期时间值转换成字符串。
	 * 
	 * @param date:
	 *            待转换的日期
	 * @param dateStyle:
	 *            指定的日期类型,style参数取静态常量LONG、MEDIUM和SHORT的值
	 * @param timeStyle：指定的时间类型,style参数取静态常量LONG、MEDIUM和SHORT的值
	 * @param loc：字符定义对象
	 * @return 格式化后的日期时间字符串
	 * @throws IllegalArgumentOfException:
	 *             日期时间模板违反规定时
	 */
	public static String formatDateTime(Date date, int dateStyle, int timeStyle, Locale loc) {
		String newDate = "";
		if (loc == null) {
			loc = Locale.getDefault();
		}
		if (date != null) {
			DateFormat df = DateFormat.getDateTimeInstance(dateStyle, timeStyle, loc);
			newDate = df.format(date);
		}
		return newDate;
	}

	/**
	 * 按照不同的格式模板将指定的日期时间值转换成字符串。
	 * 
	 * @param date:
	 *            待转换的日期
	 * @param dateStr:
	 *            指定日期转化格式字符串模板,例如:yyyy-MM-dd
	 * @param timeStr:
	 *            指定时间转化格式字符串模板,例如:hh:mm:ss
	 * @param loc：字符定义对象
	 * @return 格式化后的日期时间字符串
	 * @throws IllegalArgumentException:
	 *             pattern模板不符合格式时报异常
	 */
	public static String formatDateTime(Date date, String dateStr, String timeStr, Locale loc) {
		String newDate = "";
		if (loc == null) {
			loc = Locale.getDefault();
		}
		if (date != null) {
			String pattern = (dateStr == null ? "" : dateStr) + " " + (timeStr == null ? "" : timeStr);
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, loc);
			newDate = sdf.format(date);
		}
		return newDate;
	}

	public static String formatDate() {
		SimpleDateFormat dateStyle = new SimpleDateFormat(DateFormatUtil.PATTERN_DATE);
		return dateStyle.format(new Date());
	}

	public static String formatDate(Date date) {
		SimpleDateFormat dateStyle = new SimpleDateFormat(DateFormatUtil.PATTERN_DATE);
		return dateStyle.format(date);
	}

	/**
	 * 把日期时间格式化为yyyy-MM-dd HH:mm:ss格式
	 * 
	 * @param dt
	 *            java.util.Date
	 * @return 格式化后的日期时间字符串
	 */
	public static String formatDateTime(Date dt) {
		String newDate = "";
		if (dt != null) {
			Locale locale = Locale.CHINESE;
			SimpleDateFormat dateStyle = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
			newDate = dateStyle.format(dt);
		}
		return newDate;
	}

	/**
	 * 把日期时间格式化为指定格式
	 * 
	 * @param dt
	 *            java.util.Date
	 * @return 格式化后的日期时间字符串
	 */
	public static String formatDateTime(String pattern) {
		return DateFormatUtil.formatDateTime(new Date(), pattern);
	}

	/**
	 * 把日期时间格式化为指定格式
	 * 
	 * @param dt
	 *            java.util.Date
	 * @return 格式化后的日期时间字符串
	 */
	public static String formatDateTime(Date date, String pattern) {
		String newDate = "";
		Locale locale = Locale.CHINESE;
		SimpleDateFormat dateStyle = new SimpleDateFormat(pattern, locale);
		newDate = dateStyle.format(date);
		return newDate;
	}

	/**
	 * 把当前日期时间格式化为yyyy-MM-dd HH:mm:ss格式
	 * 
	 * @param dt
	 *            java.util.Date
	 * @return 格式化后的日期时间字符串
	 */
	public static String formatDateTime() {
		String newDate = "";
		Locale locale = Locale.CHINESE;
		SimpleDateFormat dateStyle = new SimpleDateFormat(DateFormatUtil.PATTERN_DATE_TIME, locale);
		newDate = dateStyle.format(new Date());
		return newDate;
	}

	/**
	 * 将指定的字符串转换成日期
	 * 
	 * @param dateStr:
	 *            待转换的日期符串,以yyyy-MM-dd模板进行转换
	 * @return 返回标准的日期格式yyyy-MM-dd,与字符串dateStr对应的date对象
	 * @throws ParseStringException
	 */
	public static Date parseStrToDate(String dateStr) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			return sf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 按照不同的格式模板将指定的字符串转换成日期。
	 * 
	 * @param date:
	 *            待转换的日期符串
	 * @param pattern:
	 *            字符串的格式模板,例如:yyyy-MM-dd hh:mm:ss
	 * @return 与字符串dateStr对应的date对象
	 * @throws ParseStringException
	 */
	public static Date parseStrToDate(String date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得当前日期，时间位置为00:00:00
	 * 
	 * @return Date实例
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
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
	public static int getDays(java.util.Date date1, java.util.Date date2) {
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
		if (dateStr == null || dateStr.equals(""))
			return 0;
		Date date = null;
		date = DateFormatUtil.parseStrToDate(dateStr, DateFormatUtil.PATTERN_DATE_TIME);
		return DateFormatUtil.getDays(date, new Date());
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
	 * 获取该时间字符串的Gmt时间差的秒数
	 * 
	 * @param dateStr
	 *            格式为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static int getGmtSecond(String dateStr) {
		int gmtSecond = 0;
		Date date = DateFormatUtil.parseStrToDate(dateStr, DateFormatUtil.PATTERN_DATE_TIME);
		gmtSecond = (int) (date.getTime() / 1000);
		return gmtSecond;
	}

	/**
	 * 获取该时间字符串的Gmt时间差的秒数
	 * 
	 * @param dateStr
	 *            格式为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static int getGmtSecond(Date date) {
		int gmtSecond = 0;
		if (date != null)
			gmtSecond = (int) (date.getTime() / 1000);
		return gmtSecond;
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
		Date date = DateFormatUtil.parseStrToDate(date2, DateFormatUtil.PATTERN_DATE_TIME);
		nextUpdateTime = DateFormatUtil.getValideTimeSeconds(date1, date);
		return nextUpdateTime;
	}

	/**
	 * 返回较小的时间
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date getMinDate(Date date1, String date2) {
		Date date = DateFormatUtil.parseStrToDate(date2, DateFormatUtil.PATTERN_DATE_TIME);
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
	 * @return
	 */
	public static String getLastDayOfMonth(String month) {
		Calendar cal = Calendar.getInstance();
		String day = month + "-01";
		cal.setTime(DateFormatUtil.parseStrToDate(day));
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return DateFormatUtil.formatDate(cal.getTime());
	}

	/**
	 * 返回每月最后一天的日期,格式为yyyy-MM-dd
	 * 
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return DateFormatUtil.formatDate(cal.getTime());
	}

	/**
	 * 返回每月第一天的日期,格式为yyyy-MM-dd
	 * 
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return DateFormatUtil.formatDate(cal.getTime());
	}

	/**
	 * 返回每月第一天的日期,格式为yyyy-MM-dd
	 * 
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth() {
		return DateFormatUtil.getFirstDayOfMonth(new Date());
	}

	/**
	 * 返回每月第一天的日期,格式为yyyy-MM-dd
	 * 
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		return DateFormatUtil.getFirstDayOfMonth(cal.getTime());
	}

	/**
	 * 获取昨天的日期,格式为yyyy-MM-dd
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return DateFormatUtil.formatDate(cal.getTime());
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
		return DateFormatUtil.DAY_OF_WEEK[dayOfWeek - 1];
	}

	public static int getDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMonthsBetweenTowDays(String sDay, String eDay) {
		Calendar sCal = Calendar.getInstance();
		sCal.setTime(DateFormatUtil.parseStrToDate(sDay));
		Calendar eCal = Calendar.getInstance();
		eCal.setTime(DateFormatUtil.parseStrToDate(eDay));

		return (eCal.get(Calendar.YEAR) - sCal.get(Calendar.YEAR)) * 12 + (eCal.get(Calendar.MONTH) - sCal.get(Calendar.MONTH));
	}

	/**
	 * 
	 * @param day yyyy-MM-dd
	 * @param pattern
	 * @return
	 */
	public static String format(String day, String pattern) {
		Date date = DateFormatUtil.parseStrToDate(day);
		return DateFormatUtil.formatDateTime(date, pattern);
	}

	/**
	 * 将特定格式字符串格式化为另一种特定格式
	 * 
	 * @param dateTime
	 * @param srcPattern
	 * @param destPattern
	 * @return
	 */
	public static String format(String dateTime, String srcPattern, String destPattern) {
		Date date = DateFormatUtil.parseStrToDate(dateTime, srcPattern);
		return DateFormatUtil.formatDateTime(date, destPattern);
	}

	/**
	 * 从字符串中得到月份
	 * 
	 * @param dateString
	 *            2012-04-02 20:32:13
	 * @return
	 */
	public static String getMonth(String dateString) {
		return dateString.substring(2, 7).replace("-", "");
	}
	/**
     * 取得当前时间戳（精确到秒）
     *
     * @return nowTimeStamp
     */
    public static String getNowTimeStamp() {
        long time = System.currentTimeMillis();
        String nowTimeStamp = String.valueOf(time / 1000);
        return nowTimeStamp ;
    }
    /**
     * 取得当前时间戳（精确到毫秒）
     *
     * @return nowTimeStampStr
     */
    public static String getNowTimeStampStr() {
        long time = System.currentTimeMillis();
        String nowTimeStampStr = String.valueOf(time);
        return nowTimeStampStr ;
    }
    /**
     * 时间串获取(格式:yyyyMMddHHmmss)
     * @param args
     */
    public static String getNowTimeStr(){
    	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	Calendar calendar = Calendar.getInstance();
    	String dateName = df.format(calendar.getTime());
    	return dateName;
    }
    /**
	 * 把当前日期时间格式化为yyyy-MM-dd HH:mm:ss格式并进行url编码
	 * 
	 * @param dt
	 *            java.util.Date
	 * @return 格式化后的日期时间字符串
     * @throws UnsupportedEncodingException 
	 */
	public static String formatDateTimeToUrl() {
		String newDate = "";
		Locale locale = Locale.CHINESE;
		SimpleDateFormat dateStyle = new SimpleDateFormat(DateFormatUtil.PATTERN_DATE_TIME, locale);
		newDate = dateStyle.format(new Date());
		try {
			newDate = URLEncoder.encode(newDate, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}
    public static void main(String[] args) {		
			System.out.println(formatDateTimeToUrl());
	}
}
