package com.winsafe.hbm.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import com.winsafe.common.util.StringUtil;
import com.winsafe.hbm.util.cache.SysOSCache;

public final class DateUtil {

 
    public static final int SECOND  = 1000;

  
    public static final int MINUTE  = SECOND * 60;

 
    public static final int HOUR    = MINUTE * 60;

  
    public static final int DAY     = HOUR * 24;

  
    public static final int WEEK    = DAY * 7;

 
    public static final int YEAR    = DAY * 365; // or 366 ???

  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
  private static SimpleDateFormat weekFormat = new SimpleDateFormat("yyyy年M月d日");
  private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
  private static final String[] WEEKS = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
  /**
   * private constructor
   */
  private DateUtil() {
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
	
  public static String formatDate(Date date) {
	  if ( date == null ){
		  return null;
	  }
      return dateFormat.format(date);
  }
  
  public static Date formatStrDate(String dateStr)
  {
	  Date date = null;
		try
		{
			if (dateStr != null)
			{
				if(dateStr.length() == 8)
				{
					String year = dateStr.substring(0,4);
					String month = dateStr.substring(4,6);
					String day = dateStr.substring(6,8);
					dateStr = year + "-" + month + "-" + day;
				}
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				date = dateFormat.parse(dateStr);
			}
		}
		catch(ParseException parse)
		{
			parse.printStackTrace();
		}
		return date;
  }
  
  public static String formatDate(Date date, String format) {
	  if ( date == null ){
		  return null;
	  }
	  SimpleDateFormat dateFormat = new SimpleDateFormat(format);
      return dateFormat.format(date);
  }
  
  public static String formatDate(Date date, String format,String defaultString) {
	  if ( date == null ){
		  return defaultString;
	  }
	  SimpleDateFormat dateFormat = new SimpleDateFormat(format);
      return dateFormat.format(date);
  }


  public static String formatDate(Timestamp date) {
      Date date2 = new Date(date.getTime());
      return dateFormat.format(date2);
  }

  public static Date StringToDate(String strDate){
    DateFormat tempformat = new SimpleDateFormat("yyyy-MM-dd");
    Date date=new Date();
    try{
      if (strDate == null || strDate.equals("null") || strDate.equals("")) {
        //strDate = "0000-00-00 00:00:00";
    	  return null;
      }
      date = tempformat.parse(strDate);
    }catch(Exception e){
      e.printStackTrace();
    }
    return date;
  }
  
  public static Date StringToDate(String strDate,String dateFormat){
	    DateFormat tempformat = new SimpleDateFormat(dateFormat);
	    Date date=new Date();
	    try{
	      if (strDate == null || strDate.equals("null") || strDate.equals("")) {
	    	  return null;
	      }
	      date = tempformat.parse(strDate);
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	    return date;
	  }
  
  public static String StringToStr(String strDate){
	    DateFormat tempformat = new SimpleDateFormat("yyMMdd");
	    Date date=new Date();
	    try{
	      if (strDate == null || strDate.equals("null") || strDate.equals("")) {
	    	  return "";
	      }
	      date = tempformat.parse(strDate);
	      return formatDate(date);
	    }catch(Exception e){
	    	return "";
	    }
	  }
  
  
  public static Date StringToTime(String strTime){
  DateFormat tempformat = new SimpleDateFormat("HH:mm:ss");
  Date date=new Date();
  try{
    if (strTime == null || strTime.equals("null")) {
      strTime = "0000-00-00 00:00:00";
    }
    date = tempformat.parse(strTime);
  }catch(Exception e){
    e.printStackTrace();
  }
  return date;
}



  public static Date StringToDatetime(String strDatetime){
    DateFormat tempformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date=new Date();
    try{
      if(strDatetime==null||strDatetime.equals("null")){
        strDatetime = "0000-00-00 00:00:00";
      }
        date = tempformat.parse(strDatetime);
    }catch(Exception e){
      e.printStackTrace();
    }
    return date;
  }


  public static String formatTime(Date date) {
	  if ( date == null ){
		  return null;
	  }
		  
      return timeFormat.format(date);
  }



  public static String formatDateTime(Date date) {
	  if ( date == null ){
		  return null;
	  }
      return datetimeFormat.format(date);
  }


  public static String formatDateTimeWeek(Date date) {
	  if ( date == null ){
		  return null;
	  }
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
    
    

    public static String getCurrentYear() {
        return yearFormat.format(new Date(System.currentTimeMillis()));
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
        birth_month = birthday.get(Calendar.MONTH)+1;
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


    public static String timestampToString(Timestamp timestamp, boolean displayTime) {
        if (timestamp == null) {
            return "";
        }
        if (displayTime) {
            return timestamp.toString().substring(0, 16);
        }
        return timestamp.toString().substring(0, 10);
    }


    public static String timestampToStringSecond(Timestamp timestamp, boolean displayTime) {
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


    public static Date calculatedays(Date date,int amount){
            GregorianCalendar g = new GregorianCalendar();
            g.setGregorianChange(date);
            g.add(GregorianCalendar.DATE,amount);
            Date d = g.getTime();
            return d;
    }
    public static Date calculatemonths(Date date,int amount){
        GregorianCalendar g = new GregorianCalendar();
        g.setGregorianChange(date);
        g.add(GregorianCalendar.MONTH,amount);
        Date d = g.getTime();
        return d;
    }
    
    public static int getMonthsDay(int year,int month){
    	int day;   
    	  switch(month)   {   
    	    case   2:   
    	         if(year%100!=0){  
    	            day =(year%400==0)?28:29;
    	         }else{   
    	            day =(year%4)==0?28:29;
    	          }
    	          break;   
    	   case   1:   
    	   case   3:   
    	   case   5:   
    	   case   7:   
    	   case   8:   
    	   case   10:   
    	   case   12: day   =   31;break;   
    	   default: day   =   30; break;   
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
      }
      catch (Exception e) {
        return null;
      }
      return currentTime;
    }

    public static String getCurrentDateTimeString() {
      String currentTime = "";
      try {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
            "yyyyMMddHHmmss");
        Calendar currentDate = Calendar.getInstance();
        currentTime = format.format(currentDate.getTime());
      }
      catch (Exception e) {
        return null;
      }
      return currentTime;
    }


    public static String getCurrentDateString() {
      String currentTime = "";
      try {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Calendar currentDate = Calendar.getInstance();
        currentTime = format.format(currentDate.getTime());
      }
      catch (Exception e) {
        return null;
      }

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
    
    public static int getTimeDifference(String firstDate,String endDate){
    	int d=0;
    	firstDate=firstDate.replaceAll("-", "/");
    	endDate=endDate.replaceAll("-", "/");

    	d = Integer.parseInt(String.valueOf(Math.abs(Date.parse(firstDate)-Date.parse(endDate))/1000/60));
    	
    	return d;
    }
    
    public static int getDayDifference(String firstDate,String endDate){
    	int d=0;
    	firstDate=firstDate.replaceAll("-", "/");
    	endDate=endDate.replaceAll("-", "/");

    	d = Integer.parseInt(String.valueOf((Date.parse(firstDate)-Date.parse(endDate))/1000/60/60/24));
    	
    	return d;
    }
    
    public static String formatDateByAdd(String date, int day) throws ParseException{
    	Date d = null;  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		d = format.parse(date);  
		long afterTime = (d.getTime()/1000)+60*60*24*day; 
		d.setTime(afterTime*1000); 
		return format.format(d);
	 }
    
    public static long formatDateByDiffByTwoStringDate(String date1, String date2) throws ParseException{
    	java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");    
		java.util.Date beginDate= format.parse(date1);    
		java.util.Date endDate= format.parse(date2);    
		return (beginDate.getTime()-endDate.getTime())/(24*60*60*1000);    
	 }
    
	
	public static String formatDateTimeWeek2(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		int weeknum = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return formatDate(date,"MM月dd日") + "\r\n" + WEEKS[weeknum];
	}
	
	
	public static String getBeforeDay(String dateStr){
		Date date = formatStrDate(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return format.format(cal.getTime());
	}
	
	public static String getAfterDay(String dateStr){
		Date date = formatStrDate(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return format.format(cal.getTime());
	}
	
	public static Date setHHmmss(Date date,int HH,int mm,int ss)
	{
		Date newDate = null;
		if (date != null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			newDate = calendar.getTime();
		}
		return newDate;
	}
	public static Date setHHmmssSSS(Date date,int HH,int mm,int ss,int sss)
	{
		Date newDate = null;
		if (date != null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, HH);
			calendar.set(Calendar.MINUTE, mm);
			calendar.set(Calendar.SECOND, ss);
			calendar.set(Calendar.MILLISECOND, sss);
			newDate = calendar.getTime();
		}
		return newDate;
	}
	
	public static String getCurrentDateYMD() {
	      String currentTime = "";
	      try {
	        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
	            "yyyyMMdd");
	        Calendar currentDate = Calendar.getInstance();
	        currentTime = format.format(currentDate.getTime());
	      }
	      catch (Exception e) {
	        return null;
	      }
	      return currentTime;
	    }
	
	public static String getYestDay(){
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.roll(Calendar.DAY_OF_YEAR, -1);
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
			return format.format(calendar.getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static int mulDate2Date(String minYMD, String maxYMD){
		
		Date date01 = DateUtil.formatStrDate(minYMD);
		Date date02 = DateUtil.StringToDate(maxYMD);
		if(date01 == null){
			return 0;
		}
		long Ldate1 = date01.getTime();
		long Ldate2 = date02.getTime();
		
		long day = (Ldate2 - Ldate1) / (1000 * 60 * 60 * 24);
		return (int)day + 1;
		
	}
	
	/**
	 * 获取当前月份的最后一天
	 * @return
	 * @throws Exception
	 */
	public static String getLastDayOfMonth(){
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(new Date());
			int lastDay = calendar.getActualMaximum(Calendar.DATE);
			calendar.set(Calendar.DAY_OF_MONTH, lastDay);
			return format.format(calendar.getTime())+" 23:59:59";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
		
		
		
	}
	
	
	/**
	 * 获取当前月份的第一天
	 * @return
	 * @throws Exception
	 */
	public static String getFirstDayOfMonthYMDHMS(){
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(new Date());
			int lastDay = calendar.getActualMinimum(Calendar.DATE);
			calendar.set(Calendar.DAY_OF_MONTH, lastDay);
			return format.format(calendar.getTime())+" 00:00:00";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 获取当前月份的第一天
	 * @return
	 * @throws Exception
	 */
	public static String getFirstDayOfMonthYMD(){
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(new Date());
			int lastDay = calendar.getActualMinimum(Calendar.DATE);
			calendar.set(Calendar.DAY_OF_MONTH, lastDay);
			return format.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取指定时间的前N个月
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getBeforeByMonth(String date, int month) {
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(format.parse(date));
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-month);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			return format.format(calendar.getTime())+" 00:00:00";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取指定时间的上一个月第一天
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getBeforeMonthFirstDay(String date) throws Exception{
		String beforeMonthFistDay = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(date));
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DATE));
		beforeMonthFistDay = sdf.format(calendar.getTime());
		return beforeMonthFistDay;
	}
	
	/**
	 * 获取指定时间的上一个月最后一天
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getBeforeMonthLastDay(String date) throws Exception{
		String beforeMonthFistDay = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(date));
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
		beforeMonthFistDay = sdf.format(calendar.getTime());
		return beforeMonthFistDay;
	}
	
	/**
	 * 获取指定时间的当月第一天
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getMonthFirstDayOfStringDate(String date) throws Exception{
		String firstDay = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(sdf.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DATE));
		firstDay = sdf.format(calendar.getTime());
		return firstDay;
	}
	
	/**
	 * 获取指定时间的当月最后一天
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getMonthLastDayOfStringDate(String date) throws Exception{
		String firstDay = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(sdf.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
		firstDay = sdf.format(calendar.getTime());
		return firstDay;
	}
	
	/**
	 * 计算两个日期之间的天数
	 * 
	 */
	public static int calculatedays(String beginDate,String endDate) {
		
		Date date01 = DateUtil.formatStrDate(beginDate);
		Date date02 = DateUtil.formatStrDate(endDate);
		if(date01 == null){
			return 0;
		}
		long Ldate1 = date01.getTime();
		long Ldate2 = date02.getTime();
		
		long day = (Ldate2 - Ldate1) / (1000 * 60 * 60 * 24);
		return (int)day;
	}
	
	/**
	 * 验证字符串是否为有效的日期格式
	 * Create Time 2014-10-20 上午11:23:26
	 * @param date
	 * @author Ryan.xi
	 */
	public static boolean isValidDate(String date, String partten) { 
	      boolean convertSuccess=true; 
	      if(StringUtil.isEmpty(date)) {
	    	  return false;
	      }
	      SimpleDateFormat format = new SimpleDateFormat(partten); 
	       try { 
	          format.setLenient(false); 
	          format.parse(date); 
	       } catch (Exception e) { 
	           convertSuccess=false; 
	       } 
	       return convertSuccess; 
	}
}
