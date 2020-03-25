<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
boolean isLanguageEn_header = false;
String path = "/".equalsIgnoreCase(request.getContextPath())?"":request.getContextPath();
String imgPath = path + "/images";
%>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
<!-- 引入通用的css、js等 -->
<jsp:include page="include/meta.jsp" flush="true" />

<style type="text/css">
	#product_content_container img{
		width: 100%;
	}
	#product_quality {
		color: red;
	}
	.textareas .word-wrap {
	word-break: break-all;
	}
	.security-page {
		font-family: '宋体', sans-serif;
	}
        .btnDiv {
            height: 100%;
            text-align: center;
            margin: 1.6rem 0;
            padding: 0 30px;
            display: flex;
            display: -ms-flex;
            display: -o-flex;
            display: -moz-flex;
            -webkit-justify-content: space-around;
            -moz-justify-content: space-around;
            -ms-justify-content: space-around;
            justify-content: space-around;
        }

        .btnDiv .div {
            width: 100px;
            height: 100px;
        }

        .btnDiv .div .img01 {
            width: 45px;
            height: 45px;
            margin: 0 27.5px 5px 27.5px;
        }

        .btnDiv .div .p {
            text-align: center;
            font-size: .75rem;
            color: #000000;
            margin: 1px 0;
        }
        [v-cloak]{
        	display:none;
        }
</style>

<title>云单页</title>
</head>
<body>

	<div id="app" class="all-elements" v-cloak>
		<div class="snap-drawers">
		</div>

		<!-- 页面头部 -->
		<jsp:include page="include/header.jsp"/>
		
		<div class="BackTop"><i class="fa fa-angle-up"></i></div>

		<!-- Page Content-->
		<div class="snap-content">

			<div class="content">
				
				<div>
					<div class="staff-item">
					<c:if test="${isQualified==true}">
						<div class="textareas security-page-1">
							<ul>
								<li><span class="security-info">追溯网址:</span> <span id="security_code" class="word-wrap">${traceUrl }</span></li>
								<!-- 产品物料编号是86298473 -->
								<c:if test="${result.regcertCodeFixed ne '999999'}">
								<li><span class="security-info">单元识别码:</span></li>
								<li><span class="security-info">${code }</span></li>
									<c:if test="${result.regCertUser.toLowerCase() ne 'na'}">
									<li><span class="security-info">登记证持有人名称:</span></li>
									<li><span class="security-info">${result.regCertUser }</span></li>
									</c:if>
								</c:if>
								<li><span class="security-info">产品名称:</span><span id="security_code" class="word-wrap">${result.productName }</span></li>
								<!-- 产品物料编号是86298473 -->
								<c:if test="${result.regcertCodeFixed ne '999999' && result.regcertCodeFixed.toLowerCase() ne 'na'}">
								<li><span class="security-info">农药名称:</span><span id="security_code" class="word-wrap">${result.standardName }</span></li>
								</c:if>
<%-- 								<li><span class="security-info">产品规格:</span><span id="security_code" class="word-wrap">${result.specMode }</span></li> --%>
								<li><span class="security-info">生产日期:</span><span id="security_code" class="word-wrap">${result.productionDate }</span></li>
								<c:if test="${result.inspectionInstitution.toLowerCase() ne 'na'}">
								<li><span class="security-info">生产企业:</span><span id="security_code" class="word-wrap">${result.inspectionInstitution }</span></li>
								</c:if>
								<li><span class="security-info">生产批次:</span><span id="security_code" class="word-wrap">${result.batch }</span></li>
								<li><span class="security-info">质量检验:</span><span id="security_code" class="word-wrap" style="color: red;">合格</span></li>
<%-- 								<li><span class="security-info">查询次数:</span><span id="security_code" class="word-wrap">${result.queryCount }</span></li> --%>
<%-- 								<li><span class="security-info">首次查询:</span><span id="security_code" class="word-wrap">${result.firstQuery }</span></li> --%>
								<li v-show="display"><span class="security-info">验证结果:</span><span id="security_code" class="word-wrap">您查询的产品为拜耳公司系列产品</span></li>
								<li v-show="display"><span class="security-info">产品流向:</span><span id="security_code" class="word-wrap">{{flow}}</span></li>
							</ul>
						</div>
					</c:if>
					<c:if test="${isQualified==false}">
						<div class='no-detail'><i class='fa fa-exclamation-circle red'></i>暂未获取相关产品信息，请重新扫描或联系卖家</span></div>
					</c:if>
					</div>
					
					<div class="decoration"></div>
				</div>
				<!-- Page Footer-->

			</div>
			
		</div>
		<!-- 关注悦农堂 -->
		<div id="attentionDiv" align="center" v-show="showAttention">
			<div style="width:80%;padding-top:10px;">
				<a href="https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=Mzg5OTEwMjAwNA==&scene=124&#wechat_redirect"><img
					src="<%=imgPath%>/trace/attention.png" class="attention" style="margin:0 auto;width: 45px;heigth:45px" /><span style="font-size:23px;"><%=isLanguageEn_header ? "Attention<br>Official Account" : "关注注册拜耳【悦农堂】即可查询真伪及货物流向信息"%></span></a>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="<%=path%>/js/elabel/my-animate.js"></script>
	<script src="<%=path%>/res/custom/weixin-jssdk.js"></script>
	<script src="<%=path%>/js/elabel/vue.min.js"></script>

	<script>
		var vueApp = new Vue({
	        el: document.getElementById('app'),
	        data: {
	        	loading: {},
	            display: false,
	            showAttention: true,
	            flow: ''
	        },
	        created: function () {
	            
	        },
	        mounted: function () {      //初始化    相当于jquery ready()
	            var _this = this;      //调用_this
	            set_custom_style(); //根据不同厂家 应用不同的样式
				set_elabel_title('拜耳云单页');
				hide_elabel_back();
				var options = {productId:'${result.productId}', manufacturerId:'000001'};
				set_menu_href(options);
				
				cookieProductId('${result.productId}');
				var ua = navigator.userAgent.toLowerCase();
			    if(ua.match(/MicroMessenger/i)=="micromessenger") {
			        //ios的ua中无miniProgram，但都有MicroMessenger（表示是微信浏览器）
			        wx.miniProgram.getEnv((res)=>{
			           //在小程序环境中
			           if (res.miniprogram) {
			        	   _this.display=true;
			        	   _this.flow='${result.flow}';
			        	   _this.showAttention=false;
			           }
			        })
			    }
	        },
	        methods: {
	            
	        }
	    });
	</script>
</body>
</html>

