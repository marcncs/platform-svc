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
	/* Product product = (Product) request.getAttribute("product_info");
	Manufacturer manufacturer = (Manufacturer) request.getAttribute("manufacturer_info");
	Integer productId = product.getId();
	Integer manufacturerId = manufacturer.getId();
	String certifyClass = ""; 
	if (manufacturer.getCertifyStatus().equals(ECertifyStatus.PASSED)) {
		certifyClass = " icon-co-approved";
		if (manufacturer.getLevel().equals(EManufacturerLevel.CHARGING)) {
			certifyClass = " icon-co-paid";
		}
	} */
	String certifyClass = " icon-co-paid"; 
%>

<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
	<!-- 引入通用的css、js等 -->
	<%@ include file="include/meta.jsp"%>

	<link rel="stylesheet" href="<%=path%>/res/am-swipe/css/amazeui.css">
	
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
		<jsp:include page="include/header.jsp" flush="true" />
		
		<div class="BackTop"><i class="fa fa-angle-up"></i></div>
		
		<!-- Page Content-->
		<div class="snap-content">
			<div class="content">
				
				<div class="container">
					<c:choose>
					<c:when test="${product_info == null}">
						<div class="staff-item">
						<div class='no-detail'>
							<span><br>
							如需所查询该产品技术文件，敬请联系技术热线<a href='tel:4008236582'>400-823-6582</a>
							</span>
						</div>
						</div>
					</c:when>
					<c:otherwise>
					<div class="staff-item">
						<h1 id="product_slogan">${product_info.slogan}</h1>
						<img id="product_pic_url" src="${product_info.pic_url}" alt="img">
						<div class="textareas product_intro">
							<ul>
								<li><span class="product-info">品<a class="word-hidden">哈哈</a>名:</span><span id="product_name">${product_info.name}</span></li>
								<li><span class="product-info">产品描述:</span><span id="product_component">${product_info.component}</span></li>
								<li><span class="product-info">证件信息:</span><span id="product_certification">${product_info.certification}</span></li>
								<li><span class="product-info">产品规格:</span><span id="product_sku">${product_info.sku}</span></li>
<!-- 								<li><span class="product-info">出品企业:</span><span id="product_manufacturer">杜邦植物保护<i class="E_icon icon-co-paid"></i></span></li> -->
								<!-- <li class="center" style="height:24px;">
								
									<figure data-am-widget="figure" class="am am-figure am-figure-default am-no-layout am-figure-zoomable" data-am-figure="{pureview:'true'}">
									  <img style="display:none" src="" data-rel="http://cdn.n369.com/Fo0Po5aMxVE71mjhO7HeVHPGluk3?imageView2/2/w/2880" alt="杜邦增威赢绿的电子标签" data-am-pureviewed="1">
									  <figcaption>
											<a>标签详情 &gt;</a>
									  </figcaption>    
									</figure>
								
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<figure>
						        <figcaption>
						            <a id="btn_qrcode">二维码 &gt;</a>
						        </figcaption>
						    	</figure>
								</li> -->
							</ul>
						</div>
					</div>
					<div id="product_content_container" class="container am-no-layout" data-am-widget="gallery" data-am-gallery="{ pureview: true }">
						${product_info.content}
					</div>
					<div class="content contact">
					<a href="tel:4008236582"><div class="contacttel">
						<h6>产品热线:4008236582</h6>
					</div></a>
				</div>
					</c:otherwise>
					</c:choose>
					
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
		<jsp:include page="include/footer.jsp" flush="true" />

	</div>
	
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1">
	  <div class="am-modal-dialog">
<%-- 	    <img src="/api/product/<%=product.getId()%>/qrcode?size=300&is_img=true" /> --%>
	  </div>
	</div>

	<div class="tips_favorite" id="tips_favorite">
	    <img src="<%=path%>/res/tinybar/images/icons/tips_favorite.png" />
	</div>
  <div class="tips_favorite" id="tips_share">
	    <img src="<%=path%>/res/tinybar/images/icons/tips_share.png" />
	</div>
	<div class="tips_favorite_bg"></div>
	
	<div class="am-modal am-modal-alert" tabindex="-1" id="model_product_label_tip">
	  <div class="am-modal-dialog">
	    <div class="am-modal-bd">
	      	厂家未上传标签！
	    </div>
	    <div class="am-modal-footer">
	      <span class="am-modal-btn">确定</span>
	    </div>
	  </div>
	</div>
	
	<script src="<%=path%>/js/elabel/my-animate.js"></script>
<%-- 	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script> --%>
<%-- 	<script src="<%=path%>/res/custom/weixin-jssdk.js"></script> --%>
	<script src="<%=path%>/js/elabel/bottom-banner.js"></script>
	
	<script>
		(function ($) {
			$(document).ready(function() {
				//initWeixinConfig(); //调用初始化微信JSSDK的配置信息
				//填写微信分享自定义内容
				<%-- <%
				String shareContent = TextUtil.disableBreakLineTag(TextUtil.filterHtmlElement(product.getContent()));
				if (shareContent == null || shareContent.length() <= 0) {
					shareContent = manufacturer.getFullName() + "出品的" + product.getName() + "给你带来惊喜";
				} else {
					shareContent = StringUtils.substring(StringUtils.replace(shareContent, " ", ""), 0, 50);
				}
				%> --%>
				/* wx.ready(function () {
					var shareData = {
				    title: '${product_info.name}的云单页：${product_info.slogan}',
				    desc: '${product_info.content}',
				    //link: 'http://www.baidu.com/',
				    imgUrl: '${product_info.picurl}'
				  };
				  wx.onMenuShareAppMessage(shareData);
				  wx.onMenuShareTimeline(shareData);
				}); */
				
				set_html_title('拜耳${product_info.name}云单页');
				set_custom_style('1'); //根据不同厂家 应用不同的样式
				set_elabel_title('${product_info.name}');
				hide_elabel_back();
				var options = {productId:'${product_info.id}', manufacturerId:'1'};
				set_menu_href(options);
				
				cookieProductId('${product_info.id}');
				
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
				  <%-- <%
				  String weiboShareContent = product.getName() + "的云单页：" + product.getSlogan();
				  %> --%>
					var title = '${product_info.name}的云单页：${product_info.slogan}';
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
					//getAdBannerByProductId('${product_info.id}');
				}
				
			}); // --- end of jquery ready
			
		}(jQuery));
	</script>
	
	<script src="<%=path%>/res/am-swipe/js/amazeui.min.js"></script>
	
</body>
</html>

