package com.winsafe.hbm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Created on 2005-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StringFilters {
	
	public static String filter(String str){
		if ( str == null ){
			return "";
		}
		if ( str.equals("") ){
			return "";
		}
		
		String regEx = "[~^&……【】”“\"]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();      
	}
	
	public static String CommaToDot(String str){
		String r="";
		r = str.replaceAll(",", "、");
		return r;
	}
	
	public static String filterSql(String value){
		if (StringUtil.isEmpty(value) ){
			return "";
		}
		value=value.replaceAll("'", "''");
		value=value.replaceAll("=", "＝");
		value=value.replaceAll(">", "＞");
		value=value.replaceAll("<", "＜");
		value=value.replaceAll("\\?", "？");
		value=value.replaceAll("\\%", "％");
		value=value.replaceAll("\\*", "＊");
		value=value.replaceAll("\\(", "（");
		value=value.replaceAll("\\)", "）");
		value=value.replaceAll(";", "；");
		value=value.replaceAll("0x", "0 x");
		value=value.replaceAll("0X", "0 x");
		return value;      
	}
	
	public static String filterHtml(String value){
		if (StringUtil.isEmpty(value) ){
			return "";
		}
		value=value.replaceAll("'", "");
		value=value.replaceAll("\"", "");
		value=value.replaceAll(">", "");
		value=value.replaceAll("<", "");
		return value;      
	}
}
