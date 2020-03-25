<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%
/**
 * <P> Title: download					    	</P>
 * <P> Description:   					 	    </P>
 * <P> Copyright: Copyright (c) 2005/06/15          </P>
 * <P> Company: Honour Tech Co.                     </P>
 * @author jelli
 * @version 0.1 Original Design from design document.
 */ 
%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.winsafe.app.dao.AppUpdateDao" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.winsafe.hbm.entity.HibernateUtil" %>
<%@ page import="com.winsafe.aws.util.S3Util" %>
<%@ page import="com.winsafe.drp.util.WfLogger" %>
<%
	Logger logger = Logger.getLogger(this.getClass());
	AppUpdateDao appUpdateDao = new AppUpdateDao();
	request.setCharacterEncoding("utf-8");	
	String filename = request.getParameter("filename");	
	String appid = request.getParameter("appid");	
	response.reset(); 
	response.setContentType("application/octet-stream; charset=utf-8");
	String name = filename.substring(filename.lastIndexOf("/")+1,filename.length());
	String path = filename.replace("/"+name, "");
	response.setHeader("Content-Disposition","attachment; filename="+URLEncoder.encode(name,"ISO-8859-1").replaceAll("[+]","%20")); 
	File file = null;
	try
	{
		if(appid != null && !"".equals(appid.trim())) {
			appUpdateDao.updDownloadCountById(appid);
			HibernateUtil.commitTransaction();
		}
		String userAgent = request.getHeader("User-Agent");
		if(userAgent.toLowerCase().indexOf("android") > 0){
			response.setContentType("application/vnd.android.package-archive");
		}
		else if(userAgent.toLowerCase().indexOf("iphone") > 0){
			response.setContentType("application/vnd.iphone");
		}
		
		file = File.createTempFile(UUID.randomUUID().toString(), ".xml");
		
		S3Util.getFileFromS3(new FileOutputStream(file), path, name);
		response.addHeader("Content-Length", Long.toString(file.length()));
		
		OutputStream os = response.getOutputStream();
		FileInputStream fis = new FileInputStream(file);
		
		byte [] b = new byte[1024];
		int i = 0;
		
		while ( (i = fis.read(b)) > 0 )
		{
			os.write(b, 0, i);
		}
		fis.close();
		os.flush();
		os.close();
		response.flushBuffer();
		out.clear();
		out = pageContext.pushBody();  
	}
	catch ( Exception e )
	{		
		WfLogger.error("", e);
	}finally{
		if(file!=null) {
			file.deleteOnExit();
		}
		response.flushBuffer();
		out.clear();
		out = pageContext.pushBody(); 
	}
	
	
%>
