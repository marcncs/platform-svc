<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = "/".equalsIgnoreCase(request.getContextPath())?"":request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1"/>
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/trace/style_new.css"/>
        <script language="javascript" src="<%=path%>/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
		<title>查询结果</title>
		<script type="text/javascript"> 
			$(document).ready(function() {
				$("#btn").click(toIndex);
				$("#btn_faq").click(toFaq);
				$("#btn_return").click(closePage);
			});
			function toIndex() {
				window.location.href="<%=path%>/qr";
			}
			function toFaq() {
				window.location.href="<%=path%>/qr/faq";
			}
			function closePage() {
				var ua = navigator.userAgent.toLowerCase();
				if(ua.match(/MicroMessenger/i)=="micromessenger") {
					WeixinJSBridge.call('closeWindow');
				} else if(ua.indexOf("alipay")!=-1) {
					AlipayJSBridge.call('closeWebview');
				} else {
					alert("请关闭当前页面，然后重新扫描");
				}
			}
			wx.miniProgram.postMessage({});
		</script>
	</head>
	<body>
		<div class="logo tx-c">
			<img src="<%=path%>/images/trace/logo.png" alt="" />
		</div>
		<div class="tx-c result">
			<p>扫描结果</p>
			<span></span>
		</div>
		<div class="contentBox">
				<p class="error tx-c">暂未获取相关产品信息，请重新扫描或联系卖家</p>
				<button type="button" id="btn_return">重新扫描</button>
				<button type="button" id="btn">手工输入信息查询</button>
				<button type="button" id="btn_faq">查询使用说明</button>
	     </div>
	     <p class="company tx-c">拜耳作物科学（中国）有限公司</p>
	</body>
</html>