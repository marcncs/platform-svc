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
<%
	request.setCharacterEncoding("utf-8");	
	String filename = request.getParameter("filename");	
	response.reset(); 
	response.setContentType("application/octet-stream; charset=utf-8");
	response.setContentType("unknown");
	String name = filename.substring(filename.lastIndexOf("/")+1,filename.length());
	response.setHeader("Content-Disposition","attachment; filename="+URLEncoder.encode(name,"ISO-8859-1")); 
	try
	{
		String sfile = new String(filename.getBytes("ISO-8859-1"), "utf-8");
		String download_file="/"; 
	
		download_file = download_file + sfile; 
		String dd;
		if(sfile.indexOf("WSPrint/Record/")!=-1){
			dd = sfile;
		}else if(download_file.lastIndexOf("/")!=0){
			String dir=  download_file.substring(0,download_file.lastIndexOf("/"));  
			String dirName=application.getRealPath(dir); 
			 dd=dirName+sfile.substring(download_file.lastIndexOf("/")-1); 
		}else{
			//处理生产记录的下载
			dd = request.getRealPath("/") + "WSPrint/Record/" + sfile;
			//dd="D:/WSPrint/Record/"+sfile; 
		}
		

		File fff=new File(dd); 		
		OutputStream os = response.getOutputStream();
		FileInputStream fis = new FileInputStream(fff);
		
		
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
		System.out.println("downloadfile error:" + e.toString());
	}
	
	
%>
