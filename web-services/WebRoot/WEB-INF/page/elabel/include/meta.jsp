<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%
	String path = "/".equalsIgnoreCase(request.getContextPath())?"":request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+path;
	String cssPath = path + "/css";
	String scriptPath = path + "/js";
	String imgPath = path + "/images";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0 minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link rel="apple-touch-icon-precomposed" sizes="114x114" href="<%=path%>/res/tinybar/images/splash/splash-icon.png">
<link rel="apple-touch-icon-precomposed" sizes="180x180" href="<%=path%>/res/tinybar/images/splash/splash-icon-big.png">
<link rel="apple-touch-startup-image" href="<%=path%>/res/tinybar/images/splash/splash-screen.png" media="screen and (max-device-width: 320px)" />
<link rel="apple-touch-startup-image" href="<%=path%>/res/tinybar/images/splash/splash-screen@2x.png" media="(max-device-width: 480px) and (-webkit-min-device-pixel-ratio: 2)" />
<link rel="apple-touch-startup-image" href="<%=path%>/res/tinybar/images/splash/splash-screen-six.png" media="(device-width: 375px)">
<link rel="apple-touch-startup-image" href="<%=path%>/res/tinybar/images/splash/splash-screen-six-plus.png" media="(device-width: 414px)">
<link rel="apple-touch-startup-image" sizes="640x1096" href="<%=path%>/res/tinybar/images/splash/splash-screen@3x.png" />
<link rel="apple-touch-startup-image" sizes="1024x748" href="<%=path%>/res/tinybar/images/splash/splash-screen-ipad-landscape"
	media="screen and (min-device-width : 481px) and (max-device-width : 1024px) and (orientation : landscape)" />
<link rel="apple-touch-startup-image" sizes="768x1004" href="<%=path%>/res/tinybar/images/splash/splash-screen-ipad-portrait.png"
	media="screen and (min-device-width : 481px) and (max-device-width : 1024px) and (orientation : portrait)" />
<link rel="apple-touch-startup-image" sizes="1536x2008" href="<%=path%>/res/tinybar/images/splash/splash-screen-ipad-portrait-retina.png"
	media="(device-width: 768px)	and (orientation: portrait)	and (-webkit-device-pixel-ratio: 2)" />
<link rel="apple-touch-startup-image" sizes="1496x2048" href="<%=path%>/res/tinybar/images/splash/splash-screen-ipad-landscape-retina.png"
	media="(device-width: 768px)	and (orientation: landscape)	and (-webkit-device-pixel-ratio: 2)" />

<link href="<%=path%>/res/tinybar/styles/style.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/res/tinybar/styles/framework.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/res/tinybar/styles/owl.theme.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/res/tinybar/styles/swipebox.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/res/tinybar/styles/font-awesome.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/res/tinybar/styles/animate.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/res/tinybar/styles/n369.css?v=1" rel="stylesheet" type="text/css">
<link id="custom_css_link" href="" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path%>/res/tinybar/scripts/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/res/tinybar/scripts/jqueryui.js"></script>
<script type="text/javascript" src="<%=path%>/res/tinybar/scripts/framework.plugins.js"></script>
<script type="text/javascript" src="<%=path%>/res/tinybar/scripts/custom.js"></script>

<script src="<%=path%>/res/custom/api-functions.js"></script>
<script src="<%=path%>/res/jquery.cookies.2.2.0.min.js"></script>
<script src="<%=path%>/js/elabel/login-info-cookie.js"></script>


<script type="text/javascript">
	//通过UserAgent判断PC浏览器还是手机浏览器
	var browser = {
		version : function() {
			var ua = navigator.userAgent;
			return { //移动终端浏览器版本信息
				trident : ua.indexOf('Trident') > -1, //IE内核
				presto : ua.indexOf('Presto') > -1, //opera内核
				webKit : ua.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
				gecko : ua.indexOf('Gecko') > -1 && ua.indexOf('KHTML') == -1, //火狐内核
				mobile : !!ua.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
				ios : !!ua.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
				android : ua.indexOf('Android') > -1 || ua.indexOf('Linux') > -1, //android终端或uc浏览器
				iPhone : ua.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
				iPad : ua.indexOf('iPad') > -1, //是否iPad
				webApp : ua.indexOf('Safari') == -1, //是否web应用程序
				microMessenger : ua.indexOf('MicroMessenger') > -1, //是否微信浏览器
				elabel : ua.indexOf('_ELabel/') > -1, //是否云单页app
				iosElabel : ua.indexOf('iOS_ELabel') > -1, //是否云单页ios版app
				androidElabel : ua.indexOf('Android_ELabel') > -1, //是否云单页Android版app
				reviewElabel : true //是否云单页审核版本app，该app版本需要屏蔽广告等额外操作
			};
		}(),
		language : (navigator.browserLanguage || navigator.language).toLowerCase()
	}
</script>