package com.winsafe.drp.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class AjaxUtil { 
	
	/** 判断是否为Ajax请求  
     * @author qing
     * @param request 
     * @return 是true, 否false  
     */  
    public static boolean isAjaxRequest(HttpServletRequest request)  
    {  
        String header = request.getHeader("X-Requested-With");   
        if (header != null && "XMLHttpRequest".equals(header))   
            return true;   
        else   
            return false;    
    }
	/** Ajax请求  提示信息 
	 *  message格式将会影响前台是否能正常打印，弹出
     * @author qing
     * @param text  
     * @return void 
     */ 
	public static void alert(HttpServletResponse response,String text){
		
		try{
			//HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			JSONObject json = new JSONObject();  
	        json.put("legal", false);  
	        json.put("message", text);
	        out.write(json.toString());
			//必须要\", 否则js不会弹出
		 // out.write("{\"legal\":false,\"message\":\""+text+"\"}");
		  out.flush();
		}catch(Exception ex){
				ex.printStackTrace();
		}
	}
	/**
	 * Ajax操作成功提示信息
	 */
	public static void success(HttpServletResponse response,String text) {
		
		try{
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println("{\"legal\":true,\"message\":\""+text+"\"}");
		out.flush();
		}catch(Exception ex){
			ex.printStackTrace();
	}
	}
	public static void ajaxReturn(JSONObject jsonObject ,HttpServletResponse response) throws Exception{

				
		//HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		try{
			PrintWriter out=response.getWriter();
			out.println(jsonObject);
		}catch(Exception ex){
			throw(ex);
		}
	}
	/**
	 * 信息过期
	 */
	public static void ajaxTimeout(HttpServletResponse response) throws Exception{
		try{
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			response.setHeader("sessionstatus", "timeout");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void formTimeout(HttpServletResponse response) throws Exception{
		try{
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
