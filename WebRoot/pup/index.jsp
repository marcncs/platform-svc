<%@page import="com.winsafe.hbm.util.TextUtil"%>
<%@page import="com.winsafe.drp.dao.PopularProduct"%>
<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
boolean isLanguageEn = false;

String product_name = "品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名";
String product_component = "产品描述";
String product_certification = "证件信息";
String product_manufacturer = "出品企业";
String product_introduce = "产品介绍";

if (isLanguageEn) {
	product_name = "product";
	product_component = "component";
	product_certification = "register";
	product_manufacturer = "enterprise";
	product_introduce = "introduce";
}
%>

<%
	PopularProduct product = (PopularProduct) request.getAttribute("product_info");
	Manufacturer manufacturer = (Manufacturer) request.getAttribute("manufacturer_info");
	Integer productId = Integer.valueOf(product.getId());
	Integer manufacturerId = manufacturer.getId();
	String certifyClass = ""; 
	/* if (manufacturer.getCertifyStatus().equals(ECertifyStatus.PASSED)) {
		certifyClass = " icon-co-approved";
		if (manufacturer.getLevel().equals(EManufacturerLevel.CHARGING)) {
			certifyClass = " icon-co-paid";
		}
	} */
%>

<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
	<!-- 引入通用的css、js等 -->
	<jsp:include page="/WEB-INF/page/elabel/include/meta.jsp" flush="true" />

	<link rel="stylesheet" href="/res/am-swipe/css/amazeui.css">
	
	<title>云单页</title>
	
<style type="text/css">
		#product_content_container img{
			width: 100%;
		} 
		
		.center {text-align:center !important; display: block; width:100%; padding:0 35px 0 10px;}
		
		figure {
			display: inline-block;
		}
		
		.product-info {width: 83px;}
</style> 

</head>
<body>

	<div class="all-elements">
		<div class="snap-drawers">
		</div>

		<!-- 页面头部 -->
		<jsp:include page="/WEB-INF/page/elabel/include/header.jsp" flush="true" />
		
		<div class="BackTop"><i class="fa fa-angle-up"></i></div>
		
		<!-- Page Content-->
		<div class="snap-content">
			<div class="content">
				
				<div class="container">
					<div class="staff-item">
					<div class='no-detail'>
						<span><br>
						<%=isLanguageEn ? "If you need the TDS, please contact our Technical Inquiry Hotline <a href='tel:4008207278'>400-820-7278</a>" : "如需所查询该产品技术文件，敬请联系技术热线<a href='tel:4008236582'>400-823-6582</a>" %>
						
						</span>
					</div>
					</div>
					
					<div class="decoration"></div>

				</div>
				
				<!-- Page Footer-->
				<div class="footer container">
				<ul class="parallel">
					<li id="product_views" class="grey small-font"></li>
					<!-- <li><a class="small-font" href="http://m.n369.com/elabel/1">了解并创建你的云单页</a></li> -->
				</ul>
				</div>
				
			</div> <!-- end of .content -->
			
		</div> <!-- end of Page Content -->
		
		<!-- 页面底部 -->
		<%-- <jsp:include page="/elabel/include/footer.jsp" flush="true" /> --%>

	</div>
	
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1">
	  <div class="am-modal-dialog">
	    <img src="/api/product/<%=product.getId()%>/qrcode?size=300&is_img=true" />
	  </div>
	</div>

	<div class="tips_favorite" id="tips_favorite">
	    <img src="/res/tinybar/images/icons/tips_favorite.png" />
	</div>
  <div class="tips_favorite" id="tips_share">
	    <img src="/res/tinybar/images/icons/tips_share.png" />
	</div>
	<div class="tips_favorite_bg"></div>
	
	<div class="am-modal am-modal-alert" tabindex="-1" id="model_product_label_tip">
	  <div class="am-modal-dialog">
	    <div class="am-modal-bd">
	      	<%=isLanguageEn ? "label not uploaded!" : "厂家未上传标签！"%>
	    </div>
	    <div class="am-modal-footer">
	      <span class="am-modal-btn"><%=isLanguageEn ? "confirm" : "确定"%></span>
	    </div>
	  </div>
	</div>
	
	<script src="/WEB-INF/page/elabel/js/my-animate.js"></script>
	<%-- <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script> --%>
	<script src="/res/custom/weixin-jssdk.js"></script>
	<script src="/WEB-INF/page/elabel/js/bottom-banner.js"></script>
	
	<script>
		(function ($) {
			$(document).ready(function() {
				initWeixinConfig(); //调用初始化微信JSSDK的配置信息
				//填写微信分享自定义内容
				<%
				String shareContent = TextUtil.disableBreakLineTag(TextUtil.filterHtmlElement(product.getContent()));
				if (shareContent == null || shareContent.length() <= 0) {
					shareContent = manufacturer.getFullName() + "出品的" + product.getName() + "给你带来惊喜";
				} else {
					shareContent = StringUtils.substring(StringUtils.replace(shareContent, " ", ""), 0, 50);
				}
				%>
				wx.ready(function () {
					var shareData = {
				    title: '<%=product.getName()%>的云单页：<%=product.getSlogan()%>',
				    desc: '<%=shareContent.trim()%>',
				    //link: 'http://www.baidu.com/',
				    imgUrl: '<%=product.getPicUrl()%>'
				  };
				  wx.onMenuShareAppMessage(shareData);
				  wx.onMenuShareTimeline(shareData);
				});
				
				set_html_title('<%=manufacturer.getFullName()%>');
				set_custom_style(<%=manufacturerId%>); //根据不同厂家 应用不同的样式
				set_elabel_title('<%=product.getName()%>');
				hide_elabel_back();
				var options = {productId:'<%=productId%>', manufacturerId:'<%=manufacturerId%>'};
				set_menu_href(options);
				
				cookieProductId('<%=productId%>');
				
				$("#product_content_container").find('img').each(function(i, n) {
					$(n).wrap('<a href="' + $(n).attr("src") + '" class=""></a>');
					$(n).attr('alt', '');
				});
				
				$("#btn_qrcode").click(function() {
					$("#doc-modal-1").modal({width: 300, height: 320});
				});
				
				$("#show_favorite").click(function() {
					$(".tips_favorite_bg").fadeIn(50);
					$("#tips_favorite").fadeIn(200);
				});
				
				$("#show_weixin").click(function() {
					$(".tips_favorite_bg").fadeIn(50);
					$("#tips_share").fadeIn(200);
				});
				
				$("#show_friend").click(function() {
					$(".tips_favorite_bg").fadeIn(50);
					$("#tips_share").fadeIn(200);
				});
				
				$(".tips_favorite_bg").click(function() {
					$("#tips_share").fadeOut(100);
					$("#tips_favorite").fadeOut(100);
					$(".tips_favorite_bg").fadeOut(50);
				});
				
				//新浪微博分享按钮初始化
			  $('#a_share_weibo').click(function() {
				  <%
				  String weiboShareContent = product.getName() + "的云单页：" + product.getSlogan();
				  %>
					var title = '<%=weiboShareContent%>';
					var href = "http://v.t.sina.com.cn/share/share.php?title=" + title + "&url=" + window.location.href;
					$('#a_share_weibo').attr("href", href);
			  });
				
				//点击二维码退出
				$("#doc-modal-1").click(function() {
					$("#doc-modal-1").modal('close');
				});
				//没有标签是点击事件-提示
				$("#btn_no_product_label").click(function() {
					$("#model_product_label_tip").modal();
				});
				
			
				
				if (!browser.version.reviewElabel) {
					//如果不是审核版本，才出现广告
					//getAdBannerByProductId('<%=productId%>');
				}
				
			}); // --- end of jquery ready
			
		}(jQuery));
	</script>
	
	<script src="/res/am-swipe/js/amazeui.min.js"></script>
	
</body>
</html>

