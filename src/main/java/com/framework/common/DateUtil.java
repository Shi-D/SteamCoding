package com.framework.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static Calendar calendar = Calendar.getInstance();
	private static SimpleDateFormat format;
	/**
	 * 日期格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String FORMATTER_DATETIME = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式：yyyy-MM-dd
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

	/** 日期格式：cron时间表达式 ss mm HH dd MM ? yyyy */
	public static final String FORMATTER_CRON = "ss mm HH dd MM ? yyyy";

	public static final String[] WEEK = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };

	/**
	 * 根据毫秒数生成时间对象
	 * 
	 * @param millisecond
	 *            毫秒数
	 * @return
	 */
	public static Date createDate(long millisecond) {
		calendar.setTimeInMillis(millisecond);

		return calendar.getTime();
	}

	/**
	 * 获取UTC时间
	 * 
	 * @param date
	 * @return
	 */
	public static long UTC(Date date) {
		// 1、取得本地时间：
		calendar.setTime(date);
		// 2、取得时间偏移量：
		final int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		final int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		return calendar.getTimeInMillis();
	}

	/**
	 * 获取当天零点日期时间
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getCurrentZeroDate() throws ParseException {
		return getZeroDate(new Date());
	}

	/**
	 * 根据传入的日期时间获取零点的日期时间
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getZeroDate(Date date) throws ParseException {
		return formatDate(date, FORMATTER_DATE);
	}

	/**
	 * 获取当前指定格式格式化的时间
	 * 
	 * @param formatter
	 * @return
	 * @throws ParseException
	 */
	public static String getCurrentStringDate(String formatter) throws ParseException {
		return formatDateToString(new Date(), formatter);
	}

	/**
	 * 获取当前指定格式的时间
	 * 
	 * @param formatter
	 * @return
	 * @throws ParseException
	 */
	public static Date getCurrentDate(String formatter) throws ParseException {
		return formatDate(new Date(), formatter);
	}

	/**
	 * 根据传入的日期对象得到该月的第一天的日期对象
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDateOfMonth(Date date) {
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 根据传入的日期对象得到该月的最后一天的日期对象
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDateOfMonth(Date date) {
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 取得当月天数
	 * 
	 * @return
	 */
	public static int getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取得指定月份最大天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonthLastDay(Date date) {
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 得到指定月的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthLastDay(int year, int month) {
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
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
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	public static Date setTime(Date date, int hour, int minute, int second) {
		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 根据传入的日期时间对象，获取小时(24小时制)
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		return get(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 根据传入的日期时间对象，获取分钟
	 * 
	 * @param date
	 *            日期时间对象
	 * @return
	 */
	public static int getMinute(Date date) {
		return get(date, Calendar.MINUTE);
	}

	/**
	 * 根据传入的日期时间对象，获取秒
	 * 
	 * @param date
	 *            日期时间对象
	 * @return
	 */
	public static int getSecond(Date date) {
		return get(date, Calendar.SECOND);
	}

	/**
	 * 根据传入的日期时间对象，获取星期(整型数字)<br/>
	 * 1：星期天<br/>
	 * 2：星期一<br/>
	 * 3：星期二<br/>
	 * 4：星期三<br/>
	 * 5：星期四<br/>
	 * 6：星期五<br/>
	 * 7：星期六
	 * 
	 * @param date
	 *            日期时间对象
	 * @return
	 */
	public static int getWeek(Date date) {
		return get(date, Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据传入的日期时间对象，获取星期(英文字符串)<br/>
	 * "SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"
	 * 
	 * @param date
	 *            日期时间对象
	 * @return
	 */
	public static String getWeekToString(Date date) {
		int week = get(date, Calendar.DAY_OF_WEEK);

		return WEEK[week - 1];
	}

	public static int get(Date date, int field) {
		calendar.setTime(date);
		return calendar.get(field);
	}

	/**
	 * 获取两个时间相差的天数
	 * 
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public static int dateDiffWithDay(Date startdate, Date enddate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(startdate);
		c2.setTime(enddate);

		c1.set(Calendar.MILLISECOND, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.HOUR_OF_DAY, 0);

		c2.set(Calendar.MILLISECOND, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.HOUR_OF_DAY, 0);

		long time1 = c1.getTimeInMillis();
		long time2 = c2.getTimeInMillis();

		long day = Math.abs((time1 - time2) / (1000 * 60 * 60 * 24));

		return Long.valueOf(day).intValue();
	}

	/**
	 * 获取两个时间相差的秒数
	 * 
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public static int dateDiffWithSecond(Date startdate, Date enddate) {
		long time1 = startdate.getTime() / 1000;
		long time2 = enddate.getTime() / 1000;

		return Long.valueOf(time1 - time2).intValue();
	}

	/**
	 * 获取两个时间相差的毫秒数
	 * 
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public static long dateDiffWithMillisecond(Date startdate, Date enddate) {
		long time1 = startdate.getTime();
		long time2 = enddate.getTime();

		return Long.valueOf(time1 - time2).longValue();
	}

	/**
	 * 根据传入日期时间对象，获取下一天(第二天)日期时间对象
	 * 
	 * @param date
	 * @return
	 */
	public static Date nextDay(Date date) {
		return addDay(date, 1);
	}

	/**
	 * 根获取当前时间的下一天(第二天)日期时间对象
	 * 
	 * @param date
	 * @return
	 */
	public static Date nextDay() {
		return addDay(new Date(), 1);
	}

	public static Date nextDay(Date date, String formatter) throws ParseException {
		return formatDate(nextDay(date), formatter);
	}

	public static Date nextDay(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return nextDay(format.parse(date));
	}

	public static String nextDayToString(Date date, String formatter) throws ParseException {
		return formatDateToString(nextDay(date), formatter);
	}

	public static String nextDayToString(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return formatDateToString(nextDay(format.parse(date)), formatter);
	}

	/* nextSeriesDay module */
	public static Date nextSeriesDay(Date date, Integer days) {
		calendar.setTime(date);

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);

		return calendar.getTime();
	}

	public static Date nextSeriesDay(Date date, String formatter, Integer days) throws ParseException {

		return formatDate(nextSeriesDay(date, days), formatter);
	}

	public static Date nextSeriesDay(String date, String formatter, Integer days) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return nextSeriesDay(format.parse(date), days);
	}

	public static String nextSeriesDayToString(Date date, String formatter, Integer days) throws ParseException {
		return formatDateToString(nextSeriesDay(date, days), formatter);
	}

	public static String nextSeriesDayToString(String date, String formatter, Integer days) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return formatDateToString(nextSeriesDay(format.parse(date), days), formatter);
	}

	/**
	 * 昨天：获取指定时间的前一天
	 * 
	 * @param date
	 *            指定时间
	 * @return
	 */
	public static Date lastDay(Date date) {
		return addDay(date, -1);
	}

	/**
	 * 昨天：获取当前时间的前一天
	 * 
	 * @return
	 */
	public static Date lastDay() {
		return addDay(new Date(), -1);
	}

	public static Date lastDay(Date date, String formatter) throws ParseException {
		return formatDate(lastDay(date), formatter);
	}

	public static Date lastDay(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return lastDay(format.parse(date));
	}

	public static String lastDayToString(Date date, String formatter) throws ParseException {
		return formatDateToString(lastDay(date), formatter);
	}

	public static String lastDayToString(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return formatDateToString(lastDay(format.parse(date)), formatter);
	}

	/* lastSeriesDay module */
	public static Date lastSeriesDay(Date date, Integer days) {
		calendar.setTime(date);

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - days);

		return calendar.getTime();
	}

	public static Date lastSeriesDay(Date date, String formatter, Integer days) throws ParseException {

		return formatDate(lastSeriesDay(date, days), formatter);
	}

	public static Date lastSeriesDay(String date, String formatter, Integer days) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return lastSeriesDay(format.parse(date), days);
	}

	public static String lastSeriesDayToString(Date date, String formatter, Integer days) throws ParseException {
		return formatDateToString(lastSeriesDay(date, days), formatter);
	}

	public static String lastSeriesDayToString(String date, String formatter, Integer days) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return formatDateToString(lastSeriesDay(format.parse(date), days), formatter);
	}

	/* nextMonth module */
	public static Date nextMonth(Date date) {
		calendar.setTime(date);

		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);

		return calendar.getTime();
	}

	public static Date nextMonth(Date date, String formatter) throws ParseException {
		return formatDate(nextMonth(date), formatter);
	}

	public static Date nextMonth(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return nextMonth(format.parse(date));
	}

	public static String nextMonthToString(Date date, String formatter) throws ParseException {
		return formatDateToString(nextMonth(date), formatter);
	}

	public static String nextMonthToString(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return formatDateToString(nextMonth(format.parse(date)), formatter);
	}

	/* nextSeriesMonth module */
	public static Date nextSeriesMonth(Date date, Integer months) {
		calendar.setTime(date);

		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + months);

		return calendar.getTime();
	}

	public static Date nextSeriesMonth(Date date, String formatter, Integer months) throws ParseException {
		return formatDate(nextSeriesMonth(date, months), formatter);
	}

	public static Date nextSeriesMonth(String date, String formatter, Integer months) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return nextSeriesMonth(format.parse(date), months);
	}

	public static String nextSeriesMonthToString(Date date, String formatter, Integer months) throws ParseException {
		return formatDateToString(nextSeriesMonth(date, months), formatter);
	}

	public static String nextSeriesMonthToString(String date, String formatter, Integer months) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return formatDateToString(nextSeriesMonth(format.parse(date), months), formatter);
	}

	/* lastMonth module */
	public static Date lastMonth(Date date) {
		calendar.setTime(date);

		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);

		return calendar.getTime();
	}

	public static Date lastMonth(Date date, String formatter) throws ParseException {
		return formatDate(lastMonth(date), formatter);
	}

	public static Date lastMonth(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return lastMonth(format.parse(date));
	}

	public static String lastMonthToString(Date date, String formatter) throws ParseException {
		return formatDateToString(lastMonth(date), formatter);
	}

	public static String lastMonthToString(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return formatDateToString(lastMonth(format.parse(date)), formatter);
	}

	/* lastSeriesMonth module */
	public static Date lastSeriesMonth(Date date, Integer months) {
		calendar.setTime(date);

		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - months);

		return calendar.getTime();
	}

	public static Date lastSeriesMonth(Date date, String formatter, Integer months) throws ParseException {
		return formatDate(lastSeriesMonth(date, months), formatter);
	}

	public static Date lastSeriesMonth(String date, String formatter, Integer months) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return lastSeriesMonth(format.parse(date), months);
	}

	public static String lastSeriesMonthToString(Date date, String formatter, Integer months) throws ParseException {
		return formatDateToString(lastSeriesMonth(date, months), formatter);
	}

	public static String lastSeriesMonthToString(String date, String formatter, Integer months) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return formatDateToString(lastSeriesMonth(format.parse(date), months), formatter);
	}

	/* nextYear module */
	public static Date nextYear(Date date) {
		calendar.setTime(date);

		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);

		return calendar.getTime();
	}

	public static Date nextYear(Date date, String formatter) throws ParseException {
		return formatDate(nextYear(date), formatter);
	}

	public static Date nextYear(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return nextYear(format.parse(date));
	}

	public static String nextYearToString(Date date, String formatter) throws ParseException {
		return formatDateToString(nextYear(date), formatter);
	}

	public static String nextYearToString(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return formatDateToString(nextYear(format.parse(date)), formatter);
	}

	/* nextSeriesYear module */
	public static Date nextSeriesYear(Date date, Integer years) {
		calendar.setTime(date);

		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + years);

		return calendar.getTime();
	}

	public static Date nextSeriesYear(Date date, String formatter, Integer years) throws ParseException {
		return formatDate(nextSeriesYear(date, years), formatter);
	}

	public static Date nextSeriesYear(String date, String formatter, Integer years) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return nextSeriesYear(format.parse(date), years);
	}

	public static String nextSeriesYearToString(Date date, String formatter, Integer years) throws ParseException {
		return formatDateToString(nextSeriesYear(date, years), formatter);
	}

	public static String nextSeriesYearToString(String date, String formatter, Integer years) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return formatDateToString(nextSeriesYear(format.parse(date), years), formatter);
	}

	/* lastYear module */
	public static Date lastYear(Date date) {
		calendar.setTime(date);

		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);

		return calendar.getTime();
	}

	public static Date lastYear(Date date, String formatter) throws ParseException {
		return formatDate(lastYear(date), formatter);
	}

	public static Date lastYear(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return lastYear(format.parse(date));
	}

	public static String lastYearToString(Date date, String formatter) throws ParseException {
		return formatDateToString(lastYear(date), formatter);
	}

	public static String lastYearToString(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return formatDateToString(lastYear(format.parse(date)), formatter);
	}

	/* lastSeriesYear module */
	public static Date lastSeriesYear(Date date, Integer years) {
		calendar.setTime(date);

		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - years);

		return calendar.getTime();
	}

	public static Date lastSeriesYear(Date date, String formatter, Integer years) throws ParseException {
		return formatDate(lastSeriesYear(date, years), formatter);
	}

	public static Date lastSeriesYear(String date, String formatter, Integer years) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return lastSeriesYear(format.parse(date), years);
	}

	public static String lastSeriesYearToString(Date date, String formatter, Integer years) throws ParseException {
		return formatDateToString(lastSeriesYear(date, years), formatter);
	}

	public static String lastSeriesYearToString(String date, String formatter, Integer years) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return formatDateToString(lastSeriesYear(format.parse(date), years), formatter);
	}

	/**
	 * 格式化成cron时间表达式
	 * 
	 * @param date
	 *            时间对象
	 * @return
	 * @throws ParseException
	 */
	public static String formatCron(Date date) throws ParseException {
		return formatDateToString(date, FORMATTER_CRON);
	}

	/* formatDate module */
	public static Date formatDate(Date date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return format.parse(format.format(date));
	}

	public static Date formatDate(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return format.parse(date);
	}

	public static String formatDateToString(Date date, String formatter) {
		format = new SimpleDateFormat(formatter);

		return format.format(date);
	}

	public static String formatDateToString(String date, String formatter) throws ParseException {
		format = new SimpleDateFormat(formatter);

		return format.format(format.parse(date));
	}

	public static String getDay(String date, String split) {
		return (date.split(split))[2];
	}

	/* monthSpace module */
	public static int monthSpace(String date1, String date2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar before = Calendar.getInstance();
		Calendar after = Calendar.getInstance();
		before.setTime(sdf.parse(date1));
		after.setTime(sdf.parse(date2));
		int result = Math.abs(after.get(Calendar.MONTH) - before.get(Calendar.MONTH));
		result += Math.abs(after.get(Calendar.YEAR) - before.get(Calendar.YEAR)) * 12;

		return result + 1;
	}

	public static float dayOfMonth(String date, String pattern) {
		float dayOfMonth = 0;
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(formatDate(date, pattern));
			dayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayOfMonth;
	}

}
