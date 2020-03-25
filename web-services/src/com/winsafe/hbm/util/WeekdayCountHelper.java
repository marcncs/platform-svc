package com.winsafe.hbm.util;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;

import java.util.LinkedList;

import java.util.List;

/**
 * 
 * 可用于工作日计算<br>
 * 
 * 
 * 支持计算仅除周末的工作日，及特殊日期的工作日.<br>
 * 制定的特殊假日集合，特殊的周末工作日集合.<br>
 * 比如春节为阴历假日，休息后可能存在的调休制度.
 * 
 * </p>
 * 
 * <br>
 * 
 * @Time 2014-5-14 10:14:04
 * 
 * @author Andy.liu
 * 
 * @version 1.0
 */

public class WeekdayCountHelper {

	/** 控制是否显示console */

	private static boolean needConsoleInfo = false;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 * needConsoleInfo
	 * 
	 * 
	 * 
	 * @return the needConsoleInfo
	 * 
	 * @since Ver 1.0
	 */

	public static boolean isNeedConsoleInfo() {

		return needConsoleInfo;

	}

	/**
	 * 
	 * needConsoleInfo
	 * 
	 * 
	 * 
	 * @param needConsoleInfo
	 *            the needConsoleInfo to set
	 * 
	 * @since Ver 1.0
	 */

	public static void setNeedConsoleInfo(boolean needConsoleInfo) {

		WeekdayCountHelper.needConsoleInfo = needConsoleInfo;

	}

	/**
	 * 
	 * 判断工作日是否大于n，如果是则返回true
	 * 
	 * 
	 * 
	 * @param revDate
	 * 
	 * @param retnDate
	 * 
	 * @return 返回判断工作日是否大于n的结果
	 * 
	 * @since Ver 1.0
	 */

	public static boolean isValid(Date revDate, Date retnDate, int n)
			throws Exception {

		if (getWeekDays(revDate, retnDate, null, null) > n) {

			return true;

		} else {

			return false;

		}

	}

	/**
	 * 
	 * 判断工作日是否大于n
	 * 
	 * 
	 * 
	 * @param revDate
	 * 
	 * @param retnDate
	 * 
	 * @param specialHolidayList
	 * 
	 * @param specialNonHolidayList
	 * 
	 * @return 判断工作日是否大于n
	 * 
	 * @since Ver 1.0
	 */

	public static boolean isValid(Date revDate, Date retnDate, int n,

	List<Date> specialHolidayList, List<Date> specialNonHolidayList)
			throws Exception {

		if (getWeekDays(revDate, retnDate, specialHolidayList,

		specialNonHolidayList) > n) {

			return true;

		} else {

			return false;

		}

	}

	/**
	 * 
	 * 判断工作日是否大于n
	 * 
	 * 
	 * 
	 * @param startDate
	 * 
	 * @param endDate
	 * 
	 * @param specialHolidayList
	 * 
	 * @param specialNonHolidayList
	 * 
	 * @throws ParseException
	 * 
	 * @return boolean
	 * 
	 * @since Ver 1.0
	 */

	public static boolean isValid(String startDate, String endDate, int n,

	List<Date> specialHolidayList, List<Date> specialNonHolidayList)

	throws Exception {

		if (isNeedConsoleInfo()) {

			System.out.println("\nFrom " + startDate + " to " + endDate);

		}

		return isValid(format.parse(startDate), format.parse(endDate), n,

		specialHolidayList, specialNonHolidayList);

	}

	/**
	 * 
	 * 根据日期判断是否为周末，只考虑周六，周日
	 * 
	 * 
	 * 
	 * @param date
	 * 
	 * @return 返回是否为周末
	 * 
	 * @since Ver 1.0
	 */

	public static boolean isWeekend(Date date) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		int week = calendar.get(Calendar.DAY_OF_WEEK);

		if (Calendar.SUNDAY == week || Calendar.SATURDAY == week) {

			return true;

		} else {

			return false;

		}

	}

	/**
	 * 
	 * 周末要上班的天数
	 * 
	 * 
	 * 
	 * @param startDate
	 * 
	 * @param endDate
	 * 
	 * @param specialHolidayList
	 * 
	 * @return int
	 * 
	 * @exception
	 * 
	 * @since Ver 1.0
	 */

	public static int getSpecialNonHolidays(Date startDate, Date endDate,

	List<Date> specialNonHolidayList) throws Exception {

		if (specialNonHolidayList == null) {

			return 0;

		}

		Calendar start = Calendar.getInstance();

		start.setTime(startDate);

		Calendar end = Calendar.getInstance();

		end.setTime(endDate);

		Calendar compareDate = Calendar.getInstance();

		int days = 0;

		while (start.compareTo(end) <= 0) {

			for (Date date : specialNonHolidayList) {

				compareDate.setTime(date);

				int day = compareDate.get(Calendar.DAY_OF_WEEK);

				if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {

					if (start.compareTo(compareDate) == 0) {

						days++;

						continue;

					}

				} else {

					// do nothing, 过滤掉输入的周末日期

				}

			}

			start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);

		}

		if (isNeedConsoleInfo()) {

			System.out.print("周末要上班的工作日:" + days + "\t");

		}

		return days;

	}

	/**
	 * 
	 * 特殊的工作日，主要由农历节日引起
	 * 
	 * 
	 * 
	 * @param startDate
	 * 
	 * @param endDate
	 * 
	 * @param specialHolidayList
	 * 
	 * @return int
	 * 
	 * @exception
	 * 
	 * @since Ver 1.0
	 */

	public static int getSpecialHolidays(Date startDate, Date endDate,

	List<Date> specialHolidayList) throws Exception {

		if (specialHolidayList == null) {

			return 0;

		}

		Calendar start = Calendar.getInstance();

		start.setTime(startDate);

		Calendar end = Calendar.getInstance();

		end.setTime(endDate);

		Calendar compareDate = Calendar.getInstance();

		int days = 0;

		while (start.compareTo(end) <= 0) {

			for (Date date : specialHolidayList) {

				compareDate.setTime(date);

				int day = compareDate.get(Calendar.DAY_OF_WEEK);

				if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {

					// do nothing, 过滤掉输入的非周末日期

				} else {

					if (start.compareTo(compareDate) == 0) {

						days++;

						continue;

					}

				}

			}

			start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);

		}

		if (isNeedConsoleInfo()) {

			System.out.print("正常工作日的休息日:" + days + "\t");

		}

		return days;

	}

	/**
	 * 
	 * 循环遍历日期，获取工作日天数
	 * 
	 * 
	 * 
	 * @param startDate
	 *            起始日期
	 * 
	 * @param endDate
	 *            结束日期
	 * 
	 * @return 返回工作日天数
	 * 
	 * @since Ver 1.0
	 */

	public static int getNormalWeekdays(Date startDate, Date endDate)
			throws Exception {

		Calendar start = Calendar.getInstance();

		start.setTime(startDate);

		Calendar end = Calendar.getInstance();

		end.setTime(endDate);

		int days = 0;

		while (start.compareTo(end) <= 0) {

			int day = start.get(Calendar.DAY_OF_WEEK);

			start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);

			if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {

				continue;

			} else {

				days++;

			}

		}

		if (isNeedConsoleInfo()) {

			System.out.print("正常工作日:" + days + "\t");

		}

		return days;

	}

	/**
	 * 
	 * 计算获得实际的工作天数<br>
	 * 
	 * 工作天数=计算公式正常的工作天数 - 特殊节假日 + 特殊的工作日
	 * 
	 * 
	 * 
	 * @param startDate
	 * 
	 * @param endDate
	 * 
	 * @param specialHolidayList
	 * 
	 * @param specialNonHolidayList
	 * 
	 * @return 返回实际的工作天数
	 */

	public static int getWeekDays(Date startDate, Date endDate,

	List<Date> specialHolidayList, List<Date> specialNonHolidayList)
			throws Exception {

		int days = getNormalWeekdays(startDate, endDate)

		- getSpecialHolidays(startDate, endDate, specialHolidayList)

		+ getSpecialNonHolidays(startDate, endDate, specialNonHolidayList);

		if (isNeedConsoleInfo()) {

			System.out.print("\n实际的工作天数:" + days + "\t");

		}

		return days;

	}

	/**
	 * 
	 * 计算获得实际的工作天数<br>
	 * 
	 * 工作天数=计算公式正常的工作天数 - 特殊节假日 + 特殊的工作日,<br>
	 * 
	 * 有特殊日期
	 * 
	 * @param startDate
	 * 
	 * @param endDate
	 * 
	 * @param specialHolidayList
	 * 
	 * @param specialNonHolidayList
	 * 
	 * @return 返回实际的工作天数
	 */

	public static int getWeekDays(String startDate, String endDate,
	List<Date> specialHolidayList, List<Date> specialNonHolidayList)
			throws Exception {
		if(StringUtil.isEmpty(startDate)||StringUtil.isEmpty(endDate)){
			return 0;
		}
		return getWeekDays(format.parse(startDate), format.parse(endDate),
				specialHolidayList, specialNonHolidayList);

	}

	/**
	 * 
	 * 计算获得实际的工作天数<br>
	 * 
	 * 工作天数=计算公式正常的工作天数 - 特殊节假日 + 特殊的工作日<br>
	 * 
	 * 无特殊日期
	 * 
	 * @param startDate
	 * 
	 * @param endDate
	 * 
	 * @return 返回实际的工作天数
	 */

	public static int getWeekDays(String startDate, String endDate)
			throws Exception {
		if(StringUtil.isEmpty(startDate)||StringUtil.isEmpty(endDate)){
			return 0;
		}
		return getWeekDays(format.parse(startDate), format.parse(endDate),
				null, null);

	}

	/**
	 * 封装日期集合
	 * 
	 * @param date
	 * @param list
	 * @throws Exception
	 */
	public static void add(String date, List<Date> list) throws Exception {
		if(StringUtil.isEmpty(date)){
			return;
		}
		Date date0 = format.parse(date);

		list.add(date0);

	}

	/**
	 * 将String类型的时间集合转换成java.util.Date类型集合
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<Date> parseListDate(List<String> list) throws Exception {

		if (list == null || list.size() == 0) {

			return null;

		}

		List<Date> dateList = new ArrayList<Date>();

		Date date = null;

		for (String dateStr : list) {

			date = format.parse(dateStr);

			dateList.add(date);

		}

		return dateList;

	}

}
