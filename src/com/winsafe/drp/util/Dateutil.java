package com.winsafe.drp.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期方法类 /*
 * <p>
 * Title: EntityManager.java
 * </p>
 * <p>
 * Description: 提供对hibernate基础类进行处理的常用方法：读取、修改、查询、新增等。
 * </p>
 * <p>
 * Copyright: D3space (c) 2004
 * </p>
 * <p>
 * Company: www.winsafe.cn
 * </p>
 * 
 * @author jelli
 */
public final class Dateutil {

	/**
	 * 秒数
	 */
	public static final int SECOND = 1000;

	/**
	 * 分数
	 */
	public static final int MINUTE = SECOND * 60;

	/**
	 * 时数
	 */
	public static final int HOUR = MINUTE * 60;

	/**
	 * 天数
	 */
	public static final int DAY = HOUR * 24;

	/**
	 * 周数
	 */
	public static final int WEEK = DAY * 7;

	/**
	 * 年数
	 */
	public static final int YEAR = DAY * 365; // or 366 ???

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat timeFormat = new SimpleDateFormat(
			"HH:mm:ss");
	private static SimpleDateFormat weekFormat = new SimpleDateFormat(
			"yyyy年M月d日");
	private static final String[] WEEKS = { "星期日", "星期一", "星期二", "星期三", "星期四",
			"星期五", "星期六" };

	/**
	 * private constructor
	 */
	private Dateutil() {
	}

	public static String formatDate(Date date) {
		if (null == date) {
			return null;
		}
		return dateFormat.format(date);
	}
	
	public static String formatDate(Date date,String dateFormat) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}

	public static String formatDate(Timestamp date) {
		Date date2 = new Date(date.getTime());
		return dateFormat.format(date2);
	}

	public static Date StringToDate(String strDate) {
		DateFormat tempformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			if (strDate == null || strDate.equals("null") || strDate.equals("")) {
				// strDate = "0000-00-00 00:00:00";
				return null;
			}
			date = tempformat.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date StringToTime(String strTime) {
		DateFormat tempformat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		try {
			if (strTime == null || strTime.equals("null")) {
				strTime = "0000-00-00 00:00:00";
			}
			date = tempformat.parse(strTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date StringToDatetime(String strDatetime) {
		DateFormat tempformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			if (strDatetime == null || strDatetime.equals("null")) {
				strDatetime = "0000-00-00 00:00:00";
			}
			date = tempformat.parse(strDatetime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date formatDatetime(String strDatetime) {
		DateFormat tempformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			if (strDatetime == null || strDatetime.equals("null")) {
				return null;
			}
			date = tempformat.parse(strDatetime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String formatTime(Date date) {
		return timeFormat.format(date);
	}

	public static String formatDateTime(Date date) {
		if (null == date) {
			return null;
		}
		return datetimeFormat.format(date);
	}

	public static String formatDateTimeWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		int weeknum = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return weekFormat.format(date) + WEEKS[weeknum];
	}

	public static int DateTimeToWeekNum(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		int weeknum = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return weeknum;
	}

	public static byte getMemberAge(Timestamp memberBirthday) {
		Calendar birthday = Calendar.getInstance();
		birthday.setTimeInMillis(memberBirthday.getTime());
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
		birthday.add(Calendar.YEAR, age);
		if (today.before(birthday)) {
			age--;
		}
		return (byte) age;
	}

	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	public static int getBirthYear(Timestamp birth) {
		int birth_year = 0;
		Calendar birthday = Calendar.getInstance();
		birthday.setTimeInMillis(birth.getTime());
		birth_year = birthday.get(Calendar.YEAR);
		return birth_year;
	}

	public static int getBirthMonth(Timestamp birth) {
		int birth_month = 0;
		Calendar birthday = Calendar.getInstance();
		birthday.setTimeInMillis(birth.getTime());
		birth_month = birthday.get(Calendar.MONTH) + 1;
		return birth_month;
	}

	public static int getBirthDay(Timestamp birth) {
		int birth_day = 0;
		Calendar birthday = Calendar.getInstance();
		birthday.setTimeInMillis(birth.getTime());
		birth_day = birthday.get(Calendar.DATE);
		return birth_day;
	}

	public static String getPicPath() {
		StringBuffer picpath = new StringBuffer(50);
		Calendar date = Calendar.getInstance();
		picpath.append("/");
		picpath.append(date.get(Calendar.DAY_OF_MONTH) % 8);
		picpath.append("/");
		picpath.append(date.get(Calendar.MINUTE));
		picpath.append("/");
		picpath.append(date.get(Calendar.SECOND));
		picpath.append("/");
		return picpath.toString();
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String timestampToString(Timestamp timestamp,
			boolean displayTime) {
		if (timestamp == null) {
			return "";
		}
		if (displayTime) {
			return timestamp.toString().substring(0, 16);
		}
		return timestamp.toString().substring(0, 10);
	}

	public static String timestampToStringSecond(Timestamp timestamp,
			boolean displayTime) {
		if (timestamp == null) {
			return "";
		}
		if (displayTime) {
			return timestamp.toString().substring(0, 19);
		} else {
			return timestamp.toString().substring(0, 10);
		}
	}

	public static String timestampToString(Timestamp timestamp) {
		return timestampToString(timestamp, false);
	}

	public static Date calculatedays(Date date, int amount) {
		GregorianCalendar g = new GregorianCalendar();
		g.setGregorianChange(date);
		g.add(GregorianCalendar.DATE, amount);
		Date d = g.getTime();
		return d;
	}

	public static Date calculatemonths(Date date, int amount) {
		GregorianCalendar g = new GregorianCalendar();
		g.setGregorianChange(date);
		g.add(GregorianCalendar.MONTH, amount);
		Date d = g.getTime();
		return d;
	}

	public static int getMonthsDay(int year, int month) {
		int day;
		switch (month) {
		case 2:
			if (year % 100 != 0) {
				day = (year % 400 == 0) ? 28 : 29;
			} else {
				day = (year % 4) == 0 ? 28 : 29;
			}
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	public static String getCurrentDateTime() {
		String currentTime = "";
		try {
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Calendar currentDate = Calendar.getInstance();
			currentTime = format.format(currentDate.getTime());
		} catch (Exception e) {
			return null;
		}
		return currentTime;
	}
	
	public static String getCurrentDateTimeByOrcl() {
		String currentTime = "";
		try {
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			Calendar currentDate = Calendar.getInstance();
			currentTime = format.format(currentDate.getTime());
		} catch (Exception e) {
			return null;
		}
		return currentTime;
	}
	
	/**
	 * 获取前一天的日期字符串
	 * @param format
	 * @return
	 */
	public static String getPreDayStr(String format)
	{
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DATE, -1);
		String preDayStr = new SimpleDateFormat(format).format(currentDate.getTime());
		return preDayStr;
	}

	public static String getCurrentDateTimeString() {
		String currentTime = "";
		try {
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
					"yyyyMMddHHmmss");
			Calendar currentDate = Calendar.getInstance();
			currentTime = format.format(currentDate.getTime());
		} catch (Exception e) {
			return null;
		}
		return currentTime;
	}

	public static String getCurrentDateString() {
		String currentTime = "";
		try {
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			Calendar currentDate = Calendar.getInstance();
			currentTime = format.format(currentDate.getTime());
		} catch (Exception e) {
			return null;
		}
		// System.out.println("fdfdfsfdsfds"+currentTime);
		return currentTime;
	}

	public static String getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.getActualMinimum(Calendar.DAY_OF_MONTH));

		return dateFormat.format(calendar.getTime());
	}

	public static String getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 用于转换字符串至日期时判断有没有异常
	 * @param strDatetime
	 * @return
	 * @throws Exception
	 */
	public static Date StringToDatetimeCheck(String strDatetime)
			throws Exception {
		//RichieYu-----2010-07-26
		//DateFormat tempformat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date(strDatetime);

		
		/*if (strDatetime == null || strDatetime.equals("null")) {
			strDatetime = "0000/00/00";

			date = tempformat.parse(strDatetime);

		}*/
		return date;

	}
	
	public static String getPreThreeMonDayStr(String format)
	{
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DATE, -90);
		String preDayStr = new SimpleDateFormat(format).format(currentDate.getTime());
		return preDayStr;
	}
	
	public static String getCurrenttimeStamp(){
		Date date = new Date();
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
	}
	
	public static int getDiffDays(Date fromDate, Date toDate)
	{
		return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
	}
	/**
	 * 在指定的日期上增加天数
	 * @Time 2011-8-9 下午01:23:44 create
	 * @param dayAmount 天数
	 * @param date 指定日期
	 * @return 返回增加天数后的日期
	 * @author dufazuo
	 */
	public static Date addDay2Date(int dayAmount, Date date)
	{
		Date newDate = null;
		if (date != null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, dayAmount);
			newDate = calendar.getTime();
		}
		return newDate;
	}
	
	public static Date addMonth2Date(int monthAmount, Date date)
	{
		Date newDate = null;
		if (date != null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, monthAmount);
			newDate = calendar.getTime();
		}
		return newDate;
	}
	public static Date addYear2Date(int yearAmount, Date date)
	{
		Date newDate = null;
		if (date != null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, yearAmount);
			newDate = calendar.getTime();
		}
		return newDate;
	}
	

	public static Date getDateYM(String dateYMStr)
	{
		Date date = null;
		
		try {
					if (dateYMStr != null)
					{
						String separator = dateYMStr.indexOf('/') > 0 ? "/" : "-";
						DateFormat dateFormat = new SimpleDateFormat("yyyy" + separator + "MM");
					    date = dateFormat.parse(dateYMStr);
					}
	} catch (ParseException e) {
					e.printStackTrace();
				}
		return date;
	}
	

	/**
	 * 在指定的日期上增加秒数
	 * @Time 2011-8-9 下午01:23:44 create
	 * @param secondAmount 秒数
	 * @param date 指定日期
	 * @return 返回增加秒数后的日期
	 * @author dufazuo
	 */
	public static Date addSecond2Date(int secondAmount, Date date)
	{
		Date newDate = null;
		if (date != null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.SECOND, secondAmount);
			newDate = calendar.getTime();
		}
		return newDate;
	}
	
	public static int getYear(Date date){
		int year = 0;
		Calendar birthday = Calendar.getInstance();
		birthday.setTimeInMillis(date.getTime());
		year = birthday.get(Calendar.YEAR);
		return year;
	}
	
	public static int getMonth(Date date){
		int month = 0;
		Calendar birthday = Calendar.getInstance();
		birthday.setTimeInMillis(date.getTime());
		month = birthday.get(Calendar.MONTH);
		return month;
	}
	
	public static int getDay(Date date){
		int month = 0;
		Calendar birthday = Calendar.getInstance();
		birthday.setTimeInMillis(date.getTime());
		month = birthday.get(Calendar.DAY_OF_MONTH);
		return month;
	}
}
