<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
boolean isLanguageEn_header = false;
String path = "/".equalsIgnoreCase(request.getContextPath())?"":request.getContextPath();
String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+path;
String cssPath = path + "/css";
String scriptPath = path + "/js";
String imgPath = path + "/images";
%>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
	<div class="header">
		<div class="top">
			<ul>
				<li class="col-1"><a id="header_elabel_back" href="javascript:history.go(-1);" class="icon-hidden back"><img src="<%=path%>/res/tinybar/images/icons/back.png"/></a></li>
				<li class="col-2 main-logo" id="header_elabel_title"></li> 
			</ul>
		</div>
        
		<div class="eight-menu">
			<div id="menu-main">
			<a id="menu_product_content" href="#"><img
				src="<%=imgPath%>/trace/3.png" class="tuwen" /><span><%=isLanguageEn_header ? "Technical<br>Data Sheet" : "产品信息"%></span></a>
			<a id="menu_product_list" href="#"><img
				src="<%=imgPath%>/trace/1.png" class="remen" /><span><%=isLanguageEn_header ? "Products" : "热门产品"%></span></a>
			<a id="menu_product_videos" href="#"><img
				src="<%=imgPath%>/trace/4.png" class="shipin" /><span><%=isLanguageEn_header ? "Limitless<br>Bonding" : "视频展示"%></span></a>
		</div>

		<div id="menu_product">
			<a id="menu_news" href="#"><img
				src="<%=imgPath%>/trace/6.png" class="fangwei" /><span><%=isLanguageEn_header ? "Anti-Fake<br>Checking" : "成功故事"%></span></a>
			<a id="menu_product_feedbacks" href="#"><img
				src="<%=imgPath%>/trace/5.png" class="yonghu" /><span><%=isLanguageEn_header ? "Application<br>Process" : "用户交流"%></span></a>
			<a id="menu_customer_tel" href="#"><img
				src="<%=imgPath%>/trace/7.png" class="kefu" /><span><%=isLanguageEn_header ? "Contact<br>Us" : "网上商城"%></span></a>
		</div>
    </div>

	</div>
	
<script type="text/javascript">
	function set_menu_href(options) {
		if (!options) return;
		
		var href_product_list = "<%=path%>/qr/elabel/manufacturer/" + options.manufacturerId + "/products"; //产品列表
		var href_news = "<%=path%>/qr/elabel/manufacturer/" + options.manufacturerId + "/news"; //企业新闻
		var href_tel = "<%=path%>/qr/elabel/manufacturer/contacts"; //联系电话
		var href_menu_news = "<%=path%>/qr/elabel/manufacturer/" + options.manufacturerId + "/news"; //成功故事
		
		if (options.productId) {
			var href_antifake = "<%=path%>/qr/elabel/af?wz_id=" + options.productId + "&random_test=" + Math.random(); //防伪追踪
			var href_content = "<%=path%>/qr/elabel/" + options.productId; //图文详情
			var href_videos = "<%=path%>/qr/elabel/" + options.productId + "/videos"; //视频讲解
			var href_cases = "<%=path%>/qr/elabel/" + options.productId + "/cases"; //各地经验
			var href_feedbacks = "<%=path%>/qr/elabel/" + options.productId + "/feedbacks"; //用户反馈
			<%
			//如果是产品列表页面，则用户交流进入到特殊的显示全部评论的页面
			if (request.getRequestURI().contains("page/elabel/manufacturer_products.jsp")) {
			%>
				href_feedbacks = "<%=path%>/qr/elabel/allfeedbacks"; //用户反馈
			<%
			}
			%>
		} else {
			var href_antifake = href_product_list;
			var href_content = href_product_list;
			var href_videos = href_product_list;
			var href_cases = href_product_list;
			var href_feedbacks = "<%=path%>/qr/elabel/allfeedbacks";
		}
		
		jQuery("#menu_news").attr("href", href_menu_news);
		jQuery("#menu_manufacturer_news").attr("href", href_news);
		jQuery("#menu_product_list").attr("href", href_product_list);
		jQuery("#menu_customer_tel").attr("href", href_tel);
		jQuery("#menu_product_content").attr("href", href_content);
		jQuery("#menu_product_videos").attr("href", href_videos);
		jQuery("#menu_product_cases").attr("href", href_cases);
		jQuery("#menu_product_feedbacks").attr("href", href_feedbacks);
	}
	
	function set_html_title(title, manufacturerId) {
		document.title = title;
	}
	
	//根据不同语言调用不同样式
	function set_custom_style() {
		var cssHref;
		<%
		if (isLanguageEn_header) {
		%>
			cssHref = "<%=path%>/css/elabel/style_en.css";
		<%
		} else {
		%>
			cssHref = "<%=path%>/css/elabel/style.css";
		<%
		}
		%>
		//加载自定义CSS文件
		jQuery("#custom_css_link").attr({
			rel: "stylesheet",
			type: "text/css",
			href: cssHref
		});
	}
	
	function set_elabel_title(elabel_title) {
		jQuery("#header_elabel_title").html(elabel_title);
	}

	function show_elabel_back(url) {
		if (url && history.length <= 1) {
			jQuery("#header_elabel_back").attr("href", url);
		}
		jQuery("#header_elabel_back").removeClass("icon-hidden");
	}
	
	function hide_elabel_back() {
		jQuery("#header_elabel_back").addClass("icon-hidden");
	}

	function hide_menu() {
		jQuery(".eight-menu").hide();
		jQuery(".header").css("height", "40px");
		jQuery(".snap-content").css("margin-top", "40px");
	}
	
	jQuery().ready(function() {
		//显示微信分享提示
		if (browser.version.microMessenger) {
			//在微信中打开
			jQuery("#share_tips_weixin").show();
		}
		
		//隐藏51la自动生成的图片
		jQuery("a").each(function() {
			//$(this).attr("href", "javascript:void(0);")
			//console.log(jQuery(this).attr("href"));
			var link_href = jQuery(this).attr("href");
			if (link_href && link_href.indexOf("51.la") > -1) {
				jQuery(this).hide();
			}
		});
		
	}); // end of qjuery ready...
	
	function getFlowInfo() {
		var result = {};
		jQuery.ajax({
			type : "POST",
			url : "<%=path%>/qr/getflow/1",
			data : '',
			dataType: "json",
			async: false,
			success : function(d) {
				//console.log(d);
				result = d.returnData;
			}
		});
		return result;
	}
	
	var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        //ios的ua中无miniProgram，但都有MicroMessenger（表示是微信浏览器）
        wx.miniProgram.getEnv((res)=>{
           //在小程序环境中
           if (res.miniprogram) {
        	   wx.miniProgram.postMessage(getFlowInfo());
           }
        })
    }
</script>