<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=utf-8" %>
<%@ page import="com.winsafe.app.dao.AppUpdateDao" %>
<%@ page import="com.winsafe.app.pojo.AppUpdate" %>
<%@ page import="com.winsafe.hbm.util.StringUtil" %>
<%
String path = "";
String appType = request.getParameter("appType");
if(appType!=null&&"IOS".equals(appType)) {
	path = request.getParameter("appUrl");
} else {
	AppUpdateDao appUpdateDao = new AppUpdateDao();
	AppUpdate appUpdate = appUpdateDao.getLatestAppUpdate(request.getParameter("appName"));
	path = "downloadSapfile.jsp?filename=" + appUpdate.getFilePath() + "&appid=" + appUpdate.getId();
}
%>
<html>
  <head>
    <title></title>
    
	<meta charset="utf-8">
	<meta name="viewport"
			content="width=device-width, initial-scale=1, maximum-scale=1">
	<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
	<script language="JavaScript">
		function isWeixin(){
			var ua = navigator.userAgent.toLowerCase();
			if(ua.match(/MicroMessenger/i)=="micromessenger") {
 				return true;
			} else {
				return false;
			}
		}
		window.onload = function() {
			if(isWeixin()) {
 				$("#msg").show();
			}
			else {
				window.location.href="<%=path%>";
			}
		}
	</script>

  </head>
  
  <body>
  	<c:choose>
  		<c:when test='<%="IOS".equals(appType)%>'>
  			 <p style="font-weight: bold;font-size: 20;width:100%; display: none;" id="msg">亲，你使用的是微信扫描，无法直接下载。
    		 <br/>请点击屏幕右上角的<img width="32" height="24" src="../images/weixin3.png">图标
   			 <br/>选择在Safari中打开，即可下载此APP
   			 <br/><img width="300" height="197" src="../images/weixin4.png">
   			 </p>
  		</c:when>
  		<c:otherwise>
  			 <p style="font-weight: bold;font-size: 20;width:100%; display: none;" id="msg">亲，你使用的是微信扫描，无法直接下载。
    		 <br/>请点击屏幕右上角的<img width="32" height="34" src="../images/weixin1.png">图标
   			 <br/>选择在浏览器中打开，即可下载此APP
   			 <br/><img width="167" height="308" src="../images/weixin2.png">
   			 </p>
  		</c:otherwise>
  	</c:choose>
   
  </body>
</html>
