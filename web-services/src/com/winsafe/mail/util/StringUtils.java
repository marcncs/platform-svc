package com.winsafe.mail.util;


import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * @date Dec 8, 2006
 * @update Dec 8, 2006
 * @author <a href="maozg@dne.com.cn">maozhigang</a>
 */
public class StringUtils {

	private static String PROPERTIES_NAME = "database.properties";

	public static List array2List(String[] arr) {
		if (arr == null)
			return null;
		if (arr.length == 0) {
			return new ArrayList();
		}
		List list = new ArrayList();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return list;
	}
    
	public static String toString(Object string){
		if(string!=null)
			return string.toString();
	    else
	    	return "";
	}
	
	/**
	 * @param string
	 * @return
	 */
	public static String convertToString(Object[] string) {
		StringBuffer sta = new StringBuffer("('");
		for (int i = 0; i < string.length; i++) {
			sta.append(string[i].toString().trim()).append("'");
			if (i < string.length - 1)
				sta.append(",'");
			else if (i == string.length - 1)
				sta.append(")");
		}
		return sta.toString();
	}

	public static String convertToString2(Object[] string) {
		StringBuffer sta = new StringBuffer("(");
		for (int i = 0; i < string.length; i++) {
			sta.append(string[i].toString().trim());
			if (i < string.length - 1)
				sta.append(",");
			else if (i == string.length - 1)
				sta.append(")");
		}
		return sta.toString();
	}
	/**
	 * @param string
	 * @return
	 */
	public static String convertToString3(Object[] string) {
		StringBuffer sta = new StringBuffer("'");
		for (int i = 0; i < string.length; i++) {
			sta.append(string[i].toString().trim()).append("'");
			if (i < string.length - 1)
				sta.append(",'");
		}
		return sta.toString();
	}
	public static String convertToString3(String[] temp) {
		StringBuffer sb = new StringBuffer();
		String str = "";
		for (int i = 0; i < temp.length; i++) {
			sb.append(temp[i] + ",");
		}
		if (!"".equals(sb.toString())) {
			str= sb.toString().substring(0, sb.toString().length()-1);
		}
		return str;
	}

	/**
	 * @param string
	 * @return
	 */
	public static String[] toStringArray(String string) {
		String[] stringarray = { "" };
		if (string != null) {
			java.util.StringTokenizer st = new java.util.StringTokenizer(
					string, ",");
			if (st.countTokens() > 0) {
				stringarray = new String[st.countTokens()];
				int index = 0;
				while (st.hasMoreTokens()) {
					stringarray[index++] = st.nextToken();
				}
			}
		}
		return stringarray;
	}

	public static Date formatDate(String dateStr) {
		Date date = null;
		try {
			java.text.DateFormat formatter = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			dateStr = dateStr.trim();
			date = (java.util.Date) formatter.parse(dateStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// e.printStackTrace(System.out);
			return null;
		}
		return date;
	}
	
	public static Date formatDateUsePattern(String dateStr,String pattern) throws ParseException {
		Date date = null;
			java.text.DateFormat formatter = new java.text.SimpleDateFormat(pattern);
			dateStr = dateStr.trim();
			date = (java.util.Date) formatter.parse(dateStr);
		return date;
	}

	/**
	 * @param name
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isRealEmpty(String str) {
		return str == null || str.length() == 0 || str.trim().equals("");
	}

	// caiyu add
	/*
	 * change date to string as "yyyy-mm-dd"
	 */
	public static String DateToYMD(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = formatter.format(date);
		return dateStr;
	}
	public static String DateToY(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String dateStr = formatter.format(date);
		return dateStr;
	}
	
	/**
	 * John add
	 * change java.sql.Timestamp to string as "yyyy-mm-dd"
	 */
	public static String DateToYMD(Timestamp stamp) {
		if (stamp == null) {
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = formatter.format(stamp); //java.util.Date, while the timestamp extends it.
		return dateStr;
	}

	/*
	 * change date to string as "yyyy-mm-dd HH:mm"
	 */
	public static String DateToYMDHM(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateStr = formatter.format(date);
		return dateStr;

	}

	/*
	 * change date to string as "yyyy-mm-dd HH:mm"
	 */
	public static String DateToYMDHMS(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = formatter.format(date);
		return dateStr;

	}

	/*
	 * change date to string as "yyyy/mm"
	 */
	public static String DateToYM(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM");
		String dateStr = formatter.format(date);
		return dateStr;
	}

	/*
	 * change date to only has date and set the hour,minute,second as 0"
	 */
	public static Date toYMDDate(Date date) {
		if (date == null) {
			return null;
		}

		String strYmd = DateToYMD(date);
		return formatDate(strYmd);
	}

	/*
	 * union date and time for example: date = 2006-10-15, time = 12:30, will
	 * return 2006-10-15 12:30
	 * 
	 * @param dateStr date @param timeStr time
	 */
	public static Date UnitTime(Date dateStr, String timeStr) {
		String dateStrTemp = StringUtils.DateToYMD(dateStr);
		return UnitTime(dateStrTemp, timeStr);
	}

	/*
	 * union date and time for example: date = 2006-10-15, time = 12:30, will
	 * return 2006-10-15 12:30
	 * 
	 * @param dateStr date @param timeStr time
	 */
	public static Date UnitTime(String dateStr, String timeStr) {

		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}

		java.text.DateFormat formatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		dateStr = dateStr + " " + timeStr + ":00";

		dateStr = dateStr.trim();
		Date date = null;
		try {
			date = (java.util.Date) formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	
	public static Date UnitTime(String dateStr) {

		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}

		java.text.DateFormat formatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		dateStr = dateStr + " 00:00:00";

		dateStr = dateStr.trim();
		Date date = null;
		try {
			date = (java.util.Date) formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String formatMoney(BigDecimal money) {
		money.setScale(2);
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumFractionDigits(2);
		try {
			String result = formatter.format(money);
			return result;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return money.toString();
		}

	}

	/**
	 * add by zhangzhi
	 * 
	 * @param money
	 * @return
	 */
	public static String formatMoney(double money) {
		DecimalFormat formatter = new DecimalFormat("###,###.00");
		String result = formatter.format(money);
		if (".00".equals(result)) {
			result = "";
		}
		return result;
	}

	/**
	 * add by zhangzhi
	 * 
	 * @param money
	 * @return
	 */
	public static String formatMoney(String money) {
		if (StringUtils.isEmpty(money)) {
			return "";
		}
		Double value = Double.valueOf(money);
		DecimalFormat formatter = new DecimalFormat("###,###.00");
		String result = formatter.format(value);
		if (".00".equals(result)) {
			result = "";
		}
		return result;
	}

	/**
	 * add by zhangzhi
	 * 
	 * @param money
	 * @return
	 */
	public static String formatMoney(float money) {
		DecimalFormat formatter = new DecimalFormat("###,###.00");
		String result = formatter.format(money);
		if (".00".equals(result)) {
			result = "";
		}
		return result;
	}

	/**
	 * add by zhangzhi
	 * 
	 * @param money
	 * @return
	 */
	public static String formatMoney(Float money) {
		if (money == null) {
			return null;
		}
		DecimalFormat formatter = new DecimalFormat("###,###.00");
		String result = formatter.format(money);
		if (".00".equals(result)) {
			result = "";
		}
		return result;
	}

	/**
	 * add by zhangzhi
	 * 
	 * @param money
	 * @return
	 */
	public static String formatMoney(Double money) {
		if (money == null) {
			return null;
		}
		DecimalFormat formatter = new DecimalFormat("###,###.00");
		String result = formatter.format(money);
		if (".00".equals(result)) {
			result = "";
		}
		return result;
	}
	
	public static String formatMumber(Double money) {
		if (money == null) {
			return null;
		}
		DecimalFormat formatter = new DecimalFormat("######.00");
		String result = formatter.format(money);
		if (".00".equals(result)) {
			result = "";
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(formatMumber(new Double(1110.0)));
	}
	
	public static String formatMumber(BigDecimal number, int fraction) {
		number.setScale(fraction);
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMaximumFractionDigits(fraction);
		try {
			String result = formatter.format(number);
			return result;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return number.toString();
		}
	}

	/*
	 * add days base date for example: date = 2006-10-15, dayNum = 3, will
	 * return 2006-10-18
	 * 
	 * @param dateStr base date @param dayNum add days
	 */
	public static String addDay(String dateStr, int dayNum) {
		java.text.DateFormat formatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");

		dateStr = dateStr.trim();

		String toDateStr = "";
		try {
			if (dateStr != null && !"".equals(dateStr)) {
				Date date = (java.util.Date) formatter.parse(dateStr);
				Calendar nextDay = formatter.getCalendar();
				nextDay.add(Calendar.DAY_OF_MONTH, dayNum);
				toDateStr = DateToYMDHM(nextDay.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toDateStr;
	}

	/*
	 * add days base date for example: date = 2006-10-15, dayNum = 3, will
	 * return 2006-10-18
	 * 
	 * @param dateStr base date @param dayNum add days
	 */
	public static String addDayToYMD(String dateStr, int dayNum) {
		java.text.DateFormat formatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");

		dateStr = dateStr.trim();

		String toDateStr = "";
		try {
			if (dateStr != null && !"".equals(dateStr)) {
				Date date = (java.util.Date) formatter.parse(dateStr);
				Calendar nextDay = formatter.getCalendar();
				nextDay.add(Calendar.DAY_OF_MONTH, dayNum);
				toDateStr = DateToYMD(nextDay.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toDateStr;
	}

	/*
	 * get time according to date for example: date = 2006-10-15 12:30, will
	 * return 12:30
	 * 
	 * @param date date include time
	 */
	public static String DateToHM(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String dateStr = formatter.format(date);
		return dateStr;
	}

	/*
	 * get time according to date for example: date = 2006-10-15 12:30, will
	 * return 12:30
	 * 
	 * @param date date include time
	 */
	public static Date toHMSDate(String strTime) {
		try {
			if (strTime == null || "".equals(strTime)) {
				return null;
			} else {
				strTime = strTime + ":00";
			}

			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			Date dateStr = formatter.parse(strTime);
			return dateStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * get start day and end day for one month according to date for example:
	 * date = 2006-10-15, will return 2006-10-01 and 2006-10-31 in list
	 * 
	 * @param date date in some month
	 */
	public static List getMonthPeriod(Date date) {

		List listPeriod = new ArrayList();
		if (date == null) {
			return listPeriod;
		}

		java.text.DateFormat formatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");

		// get current calendar
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);

		rightNow.set(Calendar.MONTH, rightNow.get(Calendar.MONTH));
		rightNow.set(Calendar.DAY_OF_MONTH, 1);
		listPeriod.add(formatter.format(rightNow.getTime()));

		rightNow.set(Calendar.DAY_OF_MONTH, rightNow
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		listPeriod.add(formatter.format(rightNow.getTime()));

		return listPeriod;

	}

	/*
	 * get start day and end day for one quarter according to date for example:
	 * date = 2006-10-15, will return 2006-10-01 and 2006-12-31 in list
	 * 
	 * @param date date in some quarter
	 */
	public static List getQuarterPeriod(Date date) {
		List listPeriod = new ArrayList();
		if (date == null) {
			return listPeriod;
		}

		java.text.DateFormat formatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");

		// get current calendar
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);

		// month start from 0
		int iQuarter = rightNow.get(Calendar.MONTH) / 3;

		rightNow.set(Calendar.MONTH, iQuarter * 3);
		rightNow.set(Calendar.DAY_OF_MONTH, 1);
		listPeriod.add(formatter.format(rightNow.getTime()));

		rightNow.set(Calendar.MONTH, (iQuarter + 1) * 3 - 1);
		rightNow.set(Calendar.DAY_OF_MONTH, 1);
		rightNow.set(Calendar.DAY_OF_MONTH, rightNow
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		listPeriod.add(formatter.format(rightNow.getTime()));

		return listPeriod;
	}

	/*
	 * compare fromdate and todate fromdate<=todate return true fromdate>todate
	 * return false
	 */
	public static boolean dayEquals(Date fromDate, Date toDate) {
		if ((DateToYMD(fromDate)).compareTo(DateToYMD(toDate)) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * compare fromdate and todate fromdate<=todate return true fromdate>todate
	 * return false
	 */
	public static boolean dayLessEquals(Date fromDate, Date toDate) {
		if ((DateToYMD(fromDate)).compareTo(DateToYMD(toDate)) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * compare fromdate and todate fromdate>=todate return true fromdate<todate
	 * return false
	 */
	public static boolean dayGreaterEquals(Date fromDate, Date toDate) {
		if ((DateToYMD(fromDate)).compareTo(DateToYMD(toDate)) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * compare fromdate and todate fromdate<=todate return true fromdate>todate
	 * return false
	 */
	public static boolean timeLessEquals(Date fromDate, Date toDate) {
		if ((DateToYMDHMS(fromDate)).compareTo(DateToYMDHMS(toDate)) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * compare fromdate and todate fromdate>=todate return true fromdate<todate
	 * return false
	 */
	public static boolean timeGreaterEquals(Date fromDate, Date toDate) {
		if ((DateToYMDHMS(fromDate)).compareTo(DateToYMDHMS(toDate)) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * get Millisecond string
	 */
	public static String formatDate16(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	/*
	 * get upload file path
	 */
	public static String initUpdPath(String realPath) {

		String updPath = "/";

		try {
			if (!realPath.endsWith(System.getProperty("file.separator"))) {
				realPath = realPath + System.getProperty("file.separator");
			}
			String path = realPath + "/WEB-INF/" + PROPERTIES_NAME;

			java.util.Properties pro = new java.util.Properties();
			//
			FileInputStream in = new java.io.FileInputStream(path);
			pro.load(in);

			updPath = pro.getProperty("db.UPDFILE.path");

			if (!updPath.endsWith(System.getProperty("file.separator"))) {
				updPath = updPath + System.getProperty("file.separator");
			}
			in.close();
			pro.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return updPath;
	}

 

	public static boolean isNull(Object arg) {
		if (arg == null) {
			return true;
		}

		if (arg instanceof String) {
			if ("".equals((String) arg)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isDateFormatter(String dateArg) {
		return isDateFormatter(dateArg, "0");
		// try {
		// java.text.DateFormat formatter = new java.text.SimpleDateFormat(
		// "yyyy-MM-dd");
		// Date date = (java.util.Date) formatter.parse(dateArg);
		// String newTime = formatter.format(date);
		//
		// return newTime.equals(dateArg);
		//
		// } catch (Exception e) {
		// return false;
		// }
	}

	// added by wfc on 2008-07-28
	public static boolean isDateFormatter(String dateArg, String format) {
		try {
			String ff = "yyyy-MM-dd";
			if (format.equals("1")) {
				ff = "yyyy-MM-dd HH:mm";
			}
			DateFormat formatter = new SimpleDateFormat(ff);
			Date date = (Date) formatter.parse(dateArg);
			String newTime = formatter.format(date);
			return newTime.equals(dateArg);

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isFloatFormatter(String charge) {

		try {
			Float.parseFloat(charge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// likai add
	public static boolean isIntegerFormatter(String charge) {
		try {
			Integer.parseInt(charge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// likai end
	 

	public static String htmltotext(String htmlstr) {
		if (htmlstr == null) {
			return "";
		}
		htmlstr = htmlstr.replaceAll("<B>", "");
		htmlstr = htmlstr.replaceAll("<b>", "");
		htmlstr = htmlstr.replaceAll("</B>", "");
		htmlstr = htmlstr.replaceAll("</b>", "");
		htmlstr = htmlstr.replaceAll("<BR>", "\r\n");
		htmlstr = htmlstr.replaceAll("<Br>", "\r\n");
		htmlstr = htmlstr.replaceAll("<br>", "\r\n");
		htmlstr = htmlstr.replaceAll("<bR>", "\r\n");
		htmlstr = htmlstr.replaceAll("<i>", "");
		htmlstr = htmlstr.replaceAll("</i>", "");
		htmlstr = htmlstr.replaceAll("<I>", "");
		htmlstr = htmlstr.replaceAll("</I>", "");
		return htmlstr;
	}

	/**
	 * 对<,>进行转义
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeSymbol(String str) {
		if (str != null) {
			str = str.replaceAll("<", "&lt");
			str = str.replaceAll(">", "&gt");
		}
		return str;
	}

	public static String texttohtml(String text) {
		text = text.replaceAll(" ", "&nbsp;");
		text = text.replaceAll("\n", "<br>");
		// text=text.replaceAll("<", "&lt;");
		// text=text.replaceAll(">", "&gt;");

		return text;
	}

	public static String formatNumber(String sValue, int iDecimalDigits) {
		if (sValue.indexOf(".") > -1) {
			String sNew = "";

			String integral = sValue.substring(0, sValue.indexOf("."));
			String decimal = sValue.substring(sValue.indexOf(".") + 1, sValue
					.length());

			if (decimal == null) {
				sNew = integral + "." + multiChar("0", iDecimalDigits);
			} else if (decimal.length() <= iDecimalDigits) {
				sNew = integral + "." + decimal
						+ multiChar("0", iDecimalDigits - decimal.length());
			} else if (decimal.length() > iDecimalDigits) {
				sNew = integral + "." + decimal.substring(0, iDecimalDigits);
			}
			return sNew;
		} else {
			if (sValue.equals(""))
				return "0." + multiChar("0", iDecimalDigits);
			else
				return sValue + "." + multiChar("0", iDecimalDigits);
		}
	}

	public static String multiChar(String c, int n) {
		String s = "";
		for (int i = 0; i < n; i++) {
			s = s + c;
		}
		return s;
	}

	public static Date stringToYMDDate(String string) {
		if (isRealEmpty(string)) {
			return null;
		}
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formater.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * change date to string as "MM/YY"
	 */
	public static String DateStrToMY(String dateStr) {
		if (dateStr == null || dateStr.trim().equals("")) {
			return "";
		}
		Date date = formatDate(dateStr);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
		String dateStrTemp = formatter.format(date);
		return dateStrTemp;
	}

	/*
	 * change date to string as "yyyy.mm.dd"
	 */
	public static String DateStrToYMDForReport(String dateStr) {
		if (dateStr == null || dateStr.trim().equals("")) {
			return "";
		}
		Date date = formatDate(dateStr);
		return DateToYMDForReport(date);
	}

	/*
	 * change date to string as "yyyy.mm.dd"
	 */
	public static String DateToYMDForReport(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		String dateStr = formatter.format(date);
		return dateStr;
	}
	
	
	/**
	 * @param dateStr "1/7/03"
	 * @return 
	 */
	public static Date formatExcelDate(String dateStr) {
		Date date = null;
		try {
			
			/*java.text.DateFormat formatter = new java.text.SimpleDateFormat("MM/dd/yy");
			dateStr = dateStr.trim();
			
			date = (java.util.Date) formatter.parse(dateStr);*/
			date=  new Date(dateStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return formatDate(dateStr);
		}
		return date;
	}
	/**
	 * 把数组转为逗号分隔的字符串
	 * @param string
	 * @return
	 */
	public static String convertToString4(Object[] string) {
		StringBuffer sta = new StringBuffer();
		for (int i = 0; i < string.length; i++) {
			sta.append(string[i].toString().trim());
			if (i < string.length - 1)
				sta.append(",");
			else if (i == string.length - 1)
				sta.append("");
		}
		return sta.toString();
	}
	//把逗号分隔的字符串转为数组
	public static Object[] convertToArray(String str){
		String[] string=str.split(",");
		return string;
	}
	
	 
	/**
	 * add by zhangzhi
	 * 
	 * @param money
	 * @return
	 */
	public static String formatMoney2(double money) {
		DecimalFormat formatter = new DecimalFormat("###,###");
		String result = formatter.format(money);
		if (".000".equals(result)) {
			result = "";
		}
		return result;
	}
	public static String formatMoney2(Double money) {
		if (money == null) {
			return null;
		}
		DecimalFormat formatter = new DecimalFormat("###,###");
		String result = formatter.format(money);
		if (".000".equals(result)) {
			result = "";
		}
		return result;
	}
	
	public static String ConvertNBitString(Integer dropDownValue,int bit) {
		String DropDownValue = dropDownValue.toString();
		int length = DropDownValue.length();
		if (length < bit) {
			while (length < bit) {
				DropDownValue = "0" + DropDownValue;
				length = DropDownValue.length();
			}
		}
		return DropDownValue;
	}
 
	

}