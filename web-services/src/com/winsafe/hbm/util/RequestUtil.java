package com.winsafe.hbm.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.hbm.util.RequestTool;

/*
 * Created on 2005-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RequestUtil {

	private HttpServletRequest request;
	
	public RequestUtil(HttpServletRequest request){
		this.request = request;
	}
	
	public String getString(String key){
		return RequestTool.getString(request, key);
	}
	
	public int getInt(String key){
		return RequestTool.getInt(request, key);
	}
	
	public double getDouble(String key){
		return RequestTool.getDouble(request, key);
	}
	
	public long getLong(String key){
		return RequestTool.getLong(request, key);
	}
	
	public Date getDate(String key){
		return RequestTool.getDate(request, key);
	}
	
	public Date getDateTime(String key){
		return RequestTool.getDateTime(request, key);
	}
	
	public String[] getStrings(String key){
		return RequestTool.getStrings(request, key);
	}
	
	public int[] getInts(String key){
		return RequestTool.getInts(request, key);
	}
	
	public double[] getDoubles(String key){
		return RequestTool.getDoubles(request, key);
	}
	
	public long[] getLongs(String key){
		return RequestTool.getLongs(request, key);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "";
		Integer.valueOf(str).intValue();

	}

}
