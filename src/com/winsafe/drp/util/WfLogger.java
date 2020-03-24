package com.winsafe.drp.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest; 

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class WfLogger {

		private static Logger logger = Logger.getLogger(WfLogger.class);
		public static void  debug(Object message){
			logger.debug(message);
			//write("message",null);
		}
		public static void  info(Object message){
			logger.info(message);
		}
		public static void  fatal(Object message){
			logger.fatal(message);
		}
		public static void  error(Object message){
			logger.error(message);
			//logger.
		}
		public static void  error(Object message, Exception e){
			StringWriter sw = new StringWriter();  
			PrintWriter pw = new PrintWriter(sw);  
			e.printStackTrace(pw);  
			String msg=sw.toString();
			logger.error(msg);
			if(e.getCause()!=null) {
				StringWriter sw2 = new StringWriter();  
				PrintWriter pw2 = new PrintWriter(sw);  
				e.getCause().printStackTrace(pw2);  
				String msg2=sw2.toString();
				logger.error(msg2);
			}
			//logger.
		}
		
		public static void write(String ip,String hostname,String stackinfo,String msg)
		{
			//log4j 如果配置sql 插入数据库，可以从MDC中取出
			//log4j.appender.db.sql=INSERT INTO LOG4J_MSG (LOGINID,PRIORITY,LOGDATE,CLASS,METHOD,MSG) VALUES('%X{userId}','%p','%d{yyyy-MM-dd HH:mm:ss}','%C','%M','%m')
			MDC.put("IPAddress", ip);
			MDC.put("HostName", hostname);
			MDC.put("StackInfo",stackinfo);
			logger.error(msg+"\n"+stackinfo);
		}
		
		public static void write(HttpServletRequest sq,String stackinfo,String msg)
		{
			String ip = sq.getRemoteAddr();
			String hostname = sq.getRemoteHost();
			write(ip,hostname,stackinfo,msg);
		}
		
		public static void write(String msg,Exception e){
		//	HttpServletRequest httpServletRequest=ServletActionContext.getRequest();((ServletRequestAttributes)
			ServletRequestAttributes requestA =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request=requestA.getRequest();
			String ip=request.getRemoteAddr();
			try {
				InetAddress inetAddress = InetAddress.getByName(ip);
				String hostname=inetAddress.getHostName();
				write(ip, hostname,getStackInfo(e), msg);
			} catch (UnknownHostException ex) {
				ex.printStackTrace();
			}
		}
		
		public static void write(Exception e){
			
			try {
				ServletRequestAttributes requestA =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				HttpServletRequest request=requestA.getRequest();
				String ip=request.getRemoteAddr();
				InetAddress	inetAddress = InetAddress.getByName(ip);
				String hostname=inetAddress.getHostName();
				//write(ip,hostname,getStackInfo(e),e.toString());
				write(ip,hostname,getAllStackInfo(e),e.toString());
			} catch (UnknownHostException ex) {
				ex.printStackTrace();
			}
			
		}
		/**
		 * 简单写日志，不去hostname，ip等信息
		 * @param e
		 */
         public static void singleWrite(Exception e){
			
			ServletRequestAttributes requestA =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request=requestA.getRequest();
			logger.error(e.toString()+"\n"+getAllStackInfo(e));
            // write(,e.toString());
			
		}
		public  static String getStackInfo(Exception e)
		{
			StackTraceElement[] se =  e.getStackTrace();
			StringBuilder buffer = new StringBuilder();
			buffer.append(se[0].getClassName());
			buffer.append(":");
			buffer.append(se[0].getFileName());
			buffer.append(":");
			buffer.append(se[0].getMethodName());
			buffer.append(":");
			buffer.append(se[0].getLineNumber());
			String info = buffer.toString();
			return info;
		}
		public  static String getAllStackInfo(Exception e)
		{
			StackTraceElement[] se =  e.getStackTrace();
			StringBuffer buffer = new StringBuffer();
			for(int i=0;i<se.length;i++){
				buffer.append(se[i].toString());
				buffer.append("\n");
			}
			String info = buffer.toString();
			return info;
		}
}
