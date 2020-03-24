package com.winsafe.drp.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter; 
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.winsafe.hbm.util.StringUtil;

public class ResponseUtil2 {
	private static Logger logger = Logger.getLogger(ResponseUtil2.class);
	
	public static void write(HttpServletResponse response,String value,String charset) throws Exception{
		BufferedOutputStream bos = null;
		try {
			if(StringUtil.isEmpty(value)){
				return;
			}
			byte[] bytes=value.getBytes(charset);
			response.setHeader("content-length", String.valueOf(bytes.length));
			bos = new BufferedOutputStream(response.getOutputStream());
			bos.write(bytes);
			bos.flush();
		}finally {
			if ( bos != null ){
				try {
					bos.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static void write(HttpServletResponse response,String value) throws Exception{
		write(response,value,"utf-8");
	}
	
	public static void writeReturnMsg(HttpServletResponse response,String returnCode,String returnMsg) throws Exception{
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(),"utf8"),true);
			
			writer.println("returnCode="+returnCode);
			writer.println("returnMsg="+returnMsg);
		}finally {
			if ( writer != null ){
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	
	public static void writeReturnMsg(ServletResponse response,String returnCode,String returnMsg) throws Exception{
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(),"utf8"),true);
			
			writer.println("returnCode="+returnCode);
			writer.println("returnMsg="+returnMsg);
		}finally {
			if ( writer != null ){
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
		}
	}
	public static void writeJsonMsg(HttpServletResponse response,String returnCode,String returnMsg,Object returnData) throws Exception{
		writeJsonMsg(response, returnCode, returnMsg, returnData, null, null, null, false);
	}
	/**
	 * 
	 * Create Time 2014-10-20 上午11:32:05 
	 * @param response
	 * @param returnCode
	 * @param returnMsg
	 * @param returnData
	 * @throws Exception
	 */
	public static void writeJsonMsg(HttpServletResponse response,String returnCode,String returnMsg,Object returnData
			,Integer userid,String model,String logMessage,boolean isLog) throws Exception{
			JSONObject json = getJsonMsg(returnCode, returnMsg, returnData);
			//是否记录日志
			if(isLog){
				DBUserLog.addUserLog(userid, model,logMessage,json.toString().replace("\"", ""));
			} 
//			logger.debug("interface return:" + json.toString());
			write(response,json.toString(),"utf-8");
	}
	
	public static JSONObject getJsonMsg(String returnCode,String returnMsg,Object returnData){
		JSONObject json = new JSONObject();		
		if(returnData == null){ 
			returnCode = Constants.CODE_NO_DATA;
			returnMsg = Constants.CODE_NO_DATA_MSG;
			returnData = "";
		}else if(returnData instanceof  Map){
			Map map = (Map) returnData;
			if(map.size() <= 0){
				returnCode = Constants.CODE_NO_DATA;
				returnMsg = Constants.CODE_NO_DATA_MSG;
				returnData = "";
			}
			if(returnCode == Constants.CODE_SUCCESS && map.size() > 0){
				returnMsg = Constants.CODE_SUCCESS_MSG_SIZE.replace("{count}", map.size()+"");
			}
			
		}else if(returnData instanceof Collection){
			Collection collection = (Collection) returnData;
			if(collection.size() <= 0){
				returnCode = Constants.CODE_NO_DATA;
				returnMsg = Constants.CODE_NO_DATA_MSG;
				returnData = "";
			}
			if(returnCode == Constants.CODE_SUCCESS && collection.size() > 0){
				returnMsg = Constants.CODE_SUCCESS_MSG_SIZE.replace("{count}", collection.size()+"");
			}
		}else if(returnData instanceof Object[] ){
			Object[] objs = (Object[]) returnData;
			if(objs.length <= 0){
				returnCode = Constants.CODE_NO_DATA;
				returnMsg = Constants.CODE_NO_DATA_MSG;
				returnData = "";
			}
			if(returnCode == Constants.CODE_SUCCESS && objs.length > 0){
				returnMsg = Constants.CODE_SUCCESS_MSG_SIZE.replace("{count}", objs.length+"");
			}
		}
		json.put("code", returnCode);
		json.put("msg", returnMsg);
		json.put("data", returnData);
		return json;
	}
	
	
	public static void writeAppUpdateJsonMsg(HttpServletResponse response,String returnCode,String returnMsg, String downloadUrl) throws Exception{
		JSONObject json = new JSONObject();
		json.put("returnCode", returnCode);
		json.put("returnMsg", returnMsg);
		if(downloadUrl != null) {
			json.put("downloadUrl", downloadUrl);
		}
//		logger.debug("interface return:" + json.toString());
		write(response,json.toString(),"utf-8");
	}
	
	public static void writeJsonMsg(HttpServletResponse response,String returnCode,String returnMsg) throws Exception{
		writeJsonMsg(response, returnCode, returnMsg, null, null, null, false);
	}
	
	/**
	 * 
	 * Create Time 2014-10-20 上午11:32:05 
	 * @param response
	 * @param returnCode
	 * @param returnMsg
	 * @throws Exception
	 */
	public static void writeJsonMsg(HttpServletResponse response,String returnCode,String returnMsg
			,Integer userid,String model,String logMessage,boolean isLog) throws Exception{
		JSONObject json = getJsonMsg(returnCode, returnMsg);
		//是否记录日志
		if(isLog){
			DBUserLog.addUserLog(userid, model,logMessage,json.toString().replace("\"", ""));
		}
//		logger.debug("interface return:" + json.toString());
		write(response,json.toString(),"utf-8");
	}
	
	public static JSONObject getJsonMsg(String returnCode,String returnMsg){
		JSONObject json = new JSONObject();		
		json.put("returnCode", returnCode);
		json.put("returnMsg", returnMsg);
		return json;
	}
}
