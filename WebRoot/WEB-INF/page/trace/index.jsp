<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
String path = "/".equalsIgnoreCase(request.getContextPath())?"":request.getContextPath();
%>
<!DOCTYPE html> 
<html class="indexHtml">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1" />
		<meta name="viewport"
			content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/trace/style.css" />
		<script language="javascript" src="<%=path%>/js/jquery-1.11.1.min.js"></script>
		<title>查询</title>
		<script type="text/javascript">
	$(document).ready(function() {
		$("#btn").click(function() {
			var code = $("#code").val();
			if (code == "") {
				alert("请输入要查询的验证码!");
				return;
			}
			document.query.action = "<%=path%>/qr/"+code; 
			document.query.submit();
		});
	});
</script>
	</head>
	<body class="bodySearch">
		<div class="logoIcon">

		</div>
		<p class="tx-c logoTxt">
			拜耳杭州工厂追溯
		</p>
		<br/>
		<form id="query" name="query" method="get">
		</form>
		<div class="searchBtn">
			<div id="codeAndUrl" class="tx-c">
				<p>追溯网站：<a href="#" onclick="viewDetail();">${traceUrl }</a></p>
			</div>
			<input type="text" id="code" name="code" placeholder="请输入验证码" />
			<button type="button" id="btn">
				查询
			</button>
		</div>
		<%--<br>
		<div style="text-align: center;">
			<a href="#">如何查询？</a>
		</div>
	--%></body>
</html>
