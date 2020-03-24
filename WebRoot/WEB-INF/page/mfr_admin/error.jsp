<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String err_msg = (String) request.getAttribute("error_msg");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>云单页厂家管理平台</title>

<link href="/res/bracket/css/style.default.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="/res/bracket/js/html5shiv.js"></script>
<script src="/res/bracket/js/respond.min.js"></script>
<![endif]-->
</head>

<body class="notfound">
	<section>
	  <div class="notfoundpanel">
	    <h2>出错了！</h2>
	    <h3><%=err_msg == null ? "很抱歉，你的访问有点问题！" : err_msg%></h3>
	  </div><!-- notfoundpanel -->
	  
	</section>

</body>
</html>

