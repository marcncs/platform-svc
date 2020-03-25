<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../../../../../common/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1.0"/>
    <title>拜耳云单页</title>
    <link rel="stylesheet" href="../../css/trace/main.css">
    <script src="../../js/trace/main.js"></script>
    <script language="javascript" src="../../js/jquery-1.11.1.min.js"></script>
    <style type="text/css">
.contentBox{
	width:94%;
	margin:1rem auto;
	box-sizing:border-box;
	font-size:0.9rem;	
	padding:0.1rem 0 0.625rem 0;
	border-top:1px solid #333;
	border-bottom:1px solid #333;
	position:relative;
}
.contentBox button{
	display:block;
	width:90%;
	margin:0.3rem auto 0;
	height:1rem;
	background:#2196DC;
	color:#fff;
	border:none;
}
    </style>
    <script type="text/javascript">
			$(document).ready(function() {
				$("#btn").click(toIndex);
				$("#btn_faq").click(toFaq);
				$("#btn_return").click(closePage);
			});
			function toIndex() {
				window.location.href="../qr";
			}
			function toFaq() {
				window.location.href="../qr/faq";
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
		</script>
</head>
<body class="body">
<div class="containB">
    <div class="containB0">
        <div class="containB1" style="margin-left: 0.1rem;">
            <a href="">
                <img src="../../images/trace/1.png" alt="" class="BImg">
                <div class="divB">热门产品</div>
            </a>
        </div>
        <div class="containB1" style="margin-left: 1rem;">
            <a href="">
                <img src="../../images/trace/2.png" alt="" class="BImg">
                <div class="divB">产品热线</div>
            </a>
        </div>
        <div class="containB1" style="margin-left: 1rem;">
            <a href="">
                <img src="../../images/trace/3.png" alt="" class="BImg">
                <div class="divB">图文讲解</div>
            </a>
        </div>

    </div>
    <div class="containB0">
        <div class="containB1" style="margin-left: 0.1rem;">
            <a href="">
                <img src="../../images/trace/4.png" alt="" class="BImg">
                <div class="divB">视频讲解</div>
            </a>
        </div>
        <div class="containB1" style="margin-left: 1rem;">
            <a href="">
                <img src="../../images/trace/5.png" alt="" class="BImg">
                <div class="divB">用户反馈</div>
            </a>
        </div>
        <div class="containB1" style="margin-left: 1rem;">
            <a href="">
                <img src="../../images/trace/6.png" alt="" class="BImg">
                <div class="divB">成功故事</div>
            </a>
        </div>
    </div>
</div>
<div class="main">
    <ul class="ul">
    <c:if test="${isQualified==true}">
    	<li><p class="p1"><strong>追溯网址:</strong></p>
            <p class="p2">${traceUrl }</p>
        </li>
        <li><p class="p1"><strong>单元识别码:</strong></p>
        </li>
        <li>
            <p class="p3">${code }</p>
        </li>
        <li><p class="p1"><strong>登记证持有人名称:</strong></p>
        </li>
        <li>
            <p class="p3">${result.regCertUser }</p>
        </li>
        <li><p class="p1"><strong>产品名称:</strong></p>
            <p class="p2">${result.productName }</p>
        </li>
        <li><p class="p1"><strong>农药名称:</strong></p>
            <p class="p2">${result.standardName }</p>
        </li>
        <li><p class="p1"><strong>产品规格:</strong></p>
            <p class="p2">${result.specMode }</p>
        </li>
        <li><p class="p1"><strong>生产日期:</strong></p>
            <p class="p2">${result.productionDate }</p>
        </li>
        <li><p class="p1"><strong>生产企业:</strong></p>
            <p class="p2">${result.inspectionInstitution }</p>
        </li>
        <li><p class="p1"><strong>生产批次:</strong></p>
            <p class="p2">${result.batch }</p>
        </li>
        <li><p class="p1"><strong>质量检验:</strong></p>
            <p class="p2" style="color: red;">合格</p>
        </li>
        <li><p class="p1"><strong>查询次数:</strong></p>
            <p class="p2">${result.queryCount }</p>
        </li>
        <li><p class="p1"><strong>首次查询:</strong></p>
            <p class="p2">${result.firstQuery }</p>
        </li>
        <li><p class="p1"><strong>验证结果:</strong></p>
            <p class="p2">您查询的产品为拜耳公司系列产品</p>
        </li>
        <li><p class="p1"><strong>产品流向:</strong></p>
            <p class="p2">新星种子有限责任公司</p>
        </li>
    </c:if>
    <c:if test="${isQualified==false}">
    	<li>
            <p class="p3">暂未获取相关产品信息，请重新扫描或联系卖家</p>
            <div class="contentBox">
				<button type="button" id="btn_return">重新扫描</button>
				<button type="button" id="btn">手工输入信息查询</button>
				<button type="button" id="btn_faq">查询使用说明</button>
	     	</div>
        </li>
    </c:if>
    </ul>
</div>
</body>
</html>


