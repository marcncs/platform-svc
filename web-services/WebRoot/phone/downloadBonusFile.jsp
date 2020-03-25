<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
  <head> 
    <title></title>
    
	<meta charset="utf-8">
	<meta name="viewport"
			content="width=device-width, initial-scale=1, maximum-scale=1">
	<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
	<link href="../css/app.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
		function downloadBonusPlan(){
			window.location.href="../common/downloadfile.jsp?filename=/templates/<windrp:encode key='积分计划协议.doc' />";	
		}
		function downloadBonusDetail(){
			window.location.href="../common/downloadfile.jsp?filename=/templates/<windrp:encode key='积分目标导入模板.xls' />";
		}
	</script>
  </head>
  
  <body>
    	<br>
	<br>
	<div align="center">
		目标设置模板
    </div>
	
	<br/>

    <p><btn1 onclick="downloadBonusPlan()">&nbsp;&nbsp;&nbsp;积分计划协议&nbsp;&nbsp;&nbsp;</btn1></p>&nbsp;<p><btn1 onclick="downloadBonusDetail()">积分目标导入模板</btn1></p>
  </body>
</html>
