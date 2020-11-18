package com.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date工具类
 */
public class DateUtil {
	/**
	 * 日期格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String FORMATTER_DATETIME = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式：yyyy-MM-dd HH:mm:ss,SSS
	 */
	public static final String FORMATTER_DATETIME_MILLISECOND = "yyyy-MM-dd HH:mm:ss,SSS";
	/**
	 * /** 日期格式：yyyy-MM-dd
	 */
	public static final String FORMATTER_DATE = "yyyy-MM-dd";
	/**
	 * 日期格式：yyyy-MM-dd HH
	 */
	public static final String FORMATTER_DATETIME_HOUR = "yyyy-MM-dd HH";
	/**
	 * 日期格式：HH:mm:ss
	 */
	public static final String FORMATTER_TIME = "HH:mm:ss";
	/**
	 * 日期格式：cron时间表达式 ss mm HH dd MM ? yyyy
	 */
	public static final String FORMATTER_CRON = "ss mm HH dd MM ? yyyy";

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat();

	/**
	 * 获取当前日期零点的日历实例
	 * 
	 * @return
	 */
	public static Calendar getCurrentZeroCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	/**
	 * 获取指定日期零点的日历实例
	 * 
	 * @return
	 */
	public static Calendar getZeroCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	/**
	 * 获取指定日期零点的日期对象
	 * 
	 * @return
	 */
	public static Date getZeroDate(Date date) {
		return getZeroCalendar(date).getTime();
	}

	// 获取时间年份，如果为4位长度年份则直接返回，如果为2位短年份则变成4位年份返回
	private static int getYear(int year) {
		int _year = 0;
		if (year > 0 && year < 99) {
			Calendar calendar = Calendar.getInstance();
			int y = calendar.get(Calendar.YEAR);
			_year = y - (y % 100) + year;
		} else {
			_year = year;
		}
		return _year;
	}

	/**
	 * 获取指定年月的Date对象
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return Date对象
	 */
	public static Date getDate(int year, int month) {
		int _year = getYear(year);
		return getDate(_year, month, 0, 0, 0, 0, 0);
	}

	/**
	 * 获取指定年月日的Date对象
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return Date对象
	 */
	public static Date getDate(int year, int month, int day) {
		int _year = getYear(year);
		return getDate(_year, month, day, 0, 0, 0, 0);
	}

	/**
	 * 获取指定年月日时的Date对象
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            时
	 * @return Date对象
	 */
	public static Date getDate(int year, int month, int day, int hour) {
		int _year = getYear(year);
		return getDate(_year, month, day, hour, 0, 0, 0);
	}

	/**
	 * 获取指定年月日时分的Date对象
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @return Date对象
	 */
	public static Date getDate(int year, int month, int day, int hour, int minute) {
		int _year = getYear(year);
		return getDate(_year, month, day, hour, minute, 0, 0);
	}

	/**
	 * 获取指定年月日时分的Date对象
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @return Date对象
	 */
	public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
		int _year = getYear(year);
		return getDate(_year, month, day, hour, minute, second, 0);
	}

	/**
	 * 获取指定年月日时分秒毫秒的Date对象
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @param millisecond
	 *            毫秒
	 * @return Date对象
	 */
	public static Date getDate(int year, int month, int day, int hour, int minute, int second, int millisecond) {
		return getCalendar(year, month, day, hour, minute, second, millisecond).getTime();
	}

	/**
	 * 获取指定年月日时分秒毫秒的Calendar对象
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @param millisecond
	 *            毫秒
	 * @return Calendar对象
	 */
	public static Calendar getCalendar(int year, int month, int day, int hour, int minute, int second, int millisecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		return calendar;
	}

	/**
	 * 根据指定日期为基日期，设置指定小时和分钟，默认秒和毫秒为0的新日期对象
	 * 
	 * @param date
	 *            基日期
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 * @return
	 */
	public static Date getDate(Date date, int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 修改Calendar对象
	 * 
	 * @param calendar
	 *            Calendar对象
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @param millisecond
	 *            毫秒
	 */
	public static void setCalendar(Calendar calendar, int year, int month, int day, int hour, int minute, int second, int millisecond) {
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
	}

	/**
	 * 获取4位数年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getFullYear(Date date) {
		return get(date, Calendar.YEAR);
	}

	/**
	 * 获取两位数年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getShortYear(Date date) {
		int year = getFullYear(date);
		year = year % 100;
		return year;
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		return get(date, Calendar.MONTH);
	}

	/**
	 * 获取一个月中的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		return get(date, Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取一年中的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		return get(date, Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取12小时制的小时
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		return get(date, Calendar.HOUR);
	}

	/**
	 * 获取24小时制的小时
	 * 
	 * @param date
	 * @return
	 */
	public static int getHourOfDay(Date date) {
		return get(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取分钟
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		return get(date, Calendar.MINUTE);
	}
	
	/**
	 * 获取秒
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		return get(date, Calendar.SECOND);
	}
	
	public static int get(Date date, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field);
	}

	/**
	 * 根据日历的规则，对指定的时间对象根据毫秒日历字段添加或减去指定的时间量。
	 * 
	 * @param date
	 *            指定的时间对象
	 * @param amount
	 *            为字段添加的日期或时间量
	 * @return
	 */
	public static Date addMilltsecond(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	/**
	 * 根据日历的规则，对指定的时间对象根据秒钟日历字段添加或减去指定的时间量。
	 * 
	 * @param date
	 *            指定的时间对象
	 * @param amount
	 *            为字段添加的日期或时间量
	 * @return
	 */
	public static Date addSecond(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * 根据日历的规则，对指定的时间对象根据分钟日历字段添加或减去指定的时间量。
	 * 
	 * @param date
	 *            指定的时间对象
	 * @param amount
	 *            为字段添加的日期或时间量
	 * @return
	 */
	public static Date addMinute(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 根据日历的规则，对指定的时间对象根据小时日历字段(24小时制)添加或减去指定的时间量。
	 * 
	 * @param date
	 *            指定的时间对象
	 * @param amount
	 *            为字段添加的日期或时间量
	 * @return
	 */
	public static Date addHour(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}

	/**
	 * 根据日历的规则，对指定的时间对象根据天日历字段添加或减去指定的时间量。
	 * 
	 * @param date
	 *            指定的时间对象
	 * @param amount
	 *            为字段添加的日期或时间量
	 * @return
	 */
	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DAY_OF_YEAR, amount);
	}

	/**
	 * 根据日历的规则，对指定的时间对象根据月日历字段添加或减去指定的时间量。
	 * 
	 * @param date
	 *            指定的时间对象
	 * @param amount
	 *            为字段添加的日期或时间量
	 * @return
	 */
	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * 根据日历的规则，对指定的时间对象根据年日历字段添加或减去指定的时间量。
	 * 
	 * @param date
	 *            指定的时间对象
	 * @param amount
	 *            为字段添加的日期或时间量
	 * @return
	 */
	public static Date addYear(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	/**
	 * 根据日历的规则，对指定的时间对象根据给定的日历字段添加或减去指定的时间量。<br/>
	 * 例如，要从当前时间对象减去 5 天，可以通过调用以下方法做到这一点：<br/>
	 * add(new Date(), Calendar.DAY_OF_MONTH, -5);
	 * 
	 * @param date
	 *            指定的时间对象
	 * @param field
	 *            日历字段
	 * @param amount
	 *            为字段添加的日期或时间量
	 * @return 返回重新计算后的时间对象
	 */
	public static Date add(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * 将时间格式化成字符串类型，默认格式规则：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String Format2String(Date date) {
		return Format2String(date, FORMATTER_DATETIME);
	}

	/**
	 * 根据格式规则将时间格式化成字符串类型
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String Format2String(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		dateFormat.applyPattern(pattern);
		return dateFormat.format(date);
	}
	
	/**
	 * 将时间戳格式化成字符串类型，默认格式规则：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param times
	 * @return
	 * @author LEVEL
	 * @date 2016年10月18日 下午3:18:41
	 */
	public static String Format2String(long times) {
		if (times <= 0) {
			return null;
		}
		return Format2String(new Date(times));
	}
	
	/**
	 * 将时间戳格式化成字符串类型
	 * 
	 * @param times
	 * @param pattern
	 * @return
	 * @author LEVEL
	 * @date 2016年10月18日 下午3:20:03
	 */
	public static String Format2String(long times, String pattern) {
		if (times <= 0) {
			return null;
		}
		return Format2String(new Date(times), pattern);
	}
	
	 /**
     * 计算2个时间 相差多少分钟
     * @param startDate
     * @param endDate
     * @return
     */
	public static long getMinutesDiff(Date startDate, Date endDate) {
		Calendar dateOne=Calendar.getInstance(), dateTwo=Calendar.getInstance();
		dateOne.setTime(startDate);
		dateTwo.setTime(endDate);
		long timeOne=dateOne.getTimeInMillis();
		long timeTwo=dateTwo.getTimeInMillis();
		long minute=(timeOne-timeTwo)/(1000*60);//转化minute
		//System.out.println("相隔"+minute+"分钟");
		return Math.abs(minute);
	}
}
