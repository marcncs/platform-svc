<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ page import="com.winsafe.hbm.util.StringUtil" %>
<%
String appName = request.getParameter("appName");
String appNameCn = "";
if("RI4BKDBKR".equals(appName)) {
	appNameCn = "拜耳客户信息系统";
} else {
	appName = "RTCIMPCNEW";
	appNameCn = "拜验证";
}

String prefixPath = request.getScheme() + "://" + request.getServerName() + ":" +request.getServerPort() + request.getContextPath();
String basePath = prefixPath + "/common/downloadAppFile.jsp?appName=" + appName;

String basePathIOS = "downloadAppNotify.jsp";

if("RI4BKDBKR".equals(appName)) {
	basePathIOS = prefixPath + "/common/downloadAppFile.jsp?appType=IOS&appUrl=此处是拜耳客户信息系统IOS地址,若目前还没有地址,删掉这一行";
} else {
	basePathIOS = prefixPath + "/common/downloadAppFile.jsp?appType=IOS&appUrl=此处是拜验证IOS地址,若目前还没有地址,删掉这一行";
}

%>
<html>
  <head>
    <title></title>
    
	<meta charset="utf-8">
	<meta name="viewport"
			content="width=device-width, initial-scale=1, maximum-scale=1">
	<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
	<link href="../css/app.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
		function downloadAndroid(){
			window.location.href="<%=basePath%>";	
		}
		function downloadIOS(){
			window.location.href="<%=basePathIOS%>";
		}
	</script>
  </head>
  
  <body>
    	<br>
	<br>
	<div align="center">
		欢迎下载<%=appNameCn%>
    </div>
	
	<br/>

    <p><btn1 onclick="downloadAndroid()">安卓手机(Android版)</btn1></p>&nbsp;<p><btn1 onclick="downloadIOS()">&nbsp;&nbsp;&nbsp;苹果手机(IOS版)&nbsp;&nbsp;&nbsp;</btn1></p>
  </body>
</html>
