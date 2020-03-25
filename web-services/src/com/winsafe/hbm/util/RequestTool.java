package com.winsafe.hbm.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.hbm.util.DateUtil;


public class RequestTool {
	

	public static String getString(HttpServletRequest request, String key){
		String pv = request.getParameter(key);
		if ( pv == null ){
			return "";
		}
		//return StringFilters.filter(pv);
		return pv;
	}
	
	public static int getInt(HttpServletRequest request, String key){
		String pv = getString(request, key);		
		if ( pv.equals("") ){
			return 1;
		}
		int iv = 1 ;
		try{
			iv = Integer.parseInt(pv);
		}catch ( NumberFormatException e ){
			iv = 1;
		}
		return iv;
	}
	
	public static double getDouble(HttpServletRequest request, String key){
		String pv = getString(request, key);	
		double iv = 0.00;
		if ( pv.equals("") ){
			return iv;
		}
		try{
			iv = Double.parseDouble(pv);
		}catch ( NumberFormatException e ){
			iv = 0.00;
		}
		return iv;
	}
	
	public static long getLong(HttpServletRequest request, String key){
		String pv = getString(request, key);	
		long iv = 1l;
		if ( pv.equals("") ){
			return iv;
		}
		try{
			iv = Long.parseLong(pv);
		}catch ( NumberFormatException e ){
			iv = 1l;
		}
		return iv;
	}
	
	public static Date getDate(HttpServletRequest request, String key){
		String pv = getString(request, key);	
		if ( pv.equals("") ){
			return null;
		}
		Date date;
		try{
			date = DateUtil.StringToDate(pv);
		}catch ( Exception e ){
			return null;
		}
		return date;
	}
	
	public static Date getDateTime(HttpServletRequest request, String key){
		String pv = getString(request, key);	
		if ( pv.equals("") ){
			return null;
		}
		Date date;
		try{
			date = DateUtil.StringToDatetime(pv);
		}catch ( Exception e ){
			return null;
		}
		return date;
	}
	
	

	public static String[] getStrings(HttpServletRequest request, String key){
		String[] pv = request.getParameterValues(key);
//		if ( null != pv ){
//			String[] filterpv = new String[pv.length];
//			for (int i=0; i<pv.length; i++){
//				filterpv[i] = StringFilters.filter(pv[i]);
//			}
//			return filterpv;
//		}
		return pv;
	}
	
	public static int[] getInts(HttpServletRequest request, String key){
		String[] strs = getStrings(request, key);	
		if ( strs == null ){
			return null;
		}
		int[] array = new int[strs.length];
		for ( int i=0; i<strs.length; i++ ){
			try{
				array[i] = Integer.parseInt(strs[i]);
			}catch ( Exception e ){
				array[i] = 0;
			}
		}
		return array;
	}
	
	public static double[] getDoubles(HttpServletRequest request, String key){
		String[] strs = getStrings(request, key);	
		if ( strs == null ){
			return null;
		}
		double[] array = new double[strs.length];
		for ( int i=0; i<strs.length; i++ ){
			try{
				array[i] = Double.parseDouble(strs[i]);
			}catch ( Exception e ){
				array[i] = 0;
			}
		}
		return array;
	}
	
	public static long[] getLongs(HttpServletRequest request, String key){
		String[] strs = getStrings(request, key);	
		if ( strs == null ){
			return null;
		}
		long[] array = new long[strs.length];
		for ( int i=0; i<strs.length; i++ ){
			try{
				array[i] = Long.parseLong(strs[i]);
			}catch ( Exception e ){
				array[i] = 0;
			}
		}
		return array;
	}
	
	public static Date[] getDates(HttpServletRequest request, String key){
		String[] strs = getStrings(request, key);	
		if ( strs == null ){
			return null;
		}
		Date[] array = new Date[strs.length];
		for ( int i=0; i<strs.length; i++ ){
			try{
				array[i] = DateUtil.StringToDate(strs[i]);
			}catch ( Exception e ){
				array[i] = null;
			}
		}
		return array;
	}


}
