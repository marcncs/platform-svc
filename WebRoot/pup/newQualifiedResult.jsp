<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../../common/tag.jsp"%>
<%
	boolean isLanguageEn_header = false;

%>
<link href="../css/trace/yun.css" rel="stylesheet" type="text/css">

	<div class="header">
		<div class="top">
			<ul>
				<li class="col-1"><a id="header_elabel_back" href="javascript:history.go(-1);" class="icon-hidden back"><img src="../images/trace/back.png"/></a></li>
				<li class="col-2 main-logo" id="header_elabel_title"></li> 
			</ul>
		</div>
       	<!--  热门产品、产品热线、图文讲解、视频讲解、用户反馈和成功故事 -->
		<div class="eight-menu">
			<div id="menu-main">
	    		<%-- <a id="menu_antifake" href="#"><img src="../images/trace/icon-02.png" class="fangwei"/><span><%=isLanguageEn_header ? "Anti-Fake<br>Checking" : "防伪追踪"%></span></a> --%>
				<%-- <a id="menu_manufacturer_news" href="#"><img src="../images/trace/icon-08.png" class="qiye"/><span><%=isLanguageEn_header ? "Experience<br>Loctite" : "拜耳新闻"%></span></a> --%>
				<a id="menu_product_list" href="#"><img src="../images/trace/icon-03.png" class="remen"/><span><%=isLanguageEn_header ? "Products" : "热门产品"%></span></a>
				<a id="menu_customer_tel" href=""><img src="../images/trace/icon-01.png" class="kefu"/><span><%=isLanguageEn_header ? "Contact<br>Us" : "产品热线"%></span></a>
				<a id="menu_product_content" href="#"><img src="../images/trace/icon-04.png" class="tuwen"/><span><%=isLanguageEn_header ? "Technical<br>Data Sheet" : "图文讲解"%></span></a>
			</div>
			 
			<div id="menu_product">
				
				<a id="menu_product_videos" href="#"><img src="../images/trace/icon-07.png" class="shipin"/><span><%=isLanguageEn_header ? "Limitless<br>Bonding" : "视频讲解"%></span></a>
				<a id="menu_product_cases" href="#"><img src="../images/trace/icon-06.png" class="gedi"/><span><%=isLanguageEn_header ? "Examples" : "用户反馈"%></span></a>
				<a id="menu_product_feedbacks" href="http://m.loctite-repairs.cn/zh.html?utm_source=online&utm_medium=wechat"><img src="../images/trace/icon-05.png" class="yonghu"/><span><%=isLanguageEn_header ? "Application<br>Process" : "成功故事"%></span></a>
			</div>
    </div>
	</div>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>	
<script type="text/javascript">
	function set_menu_href(options) {
		if (!options) return;
		
		var href_product_list = "/elabel/manufacturer/" + options.manufacturerId + "/products"; //产品列表
		var href_news = "/elabel/manufacturer/" + options.manufacturerId + "/news"; //企业新闻
		var href_tel = "/elabel/manufacturer/" + options.manufacturerId + "/contacts"; //联系电话
		if (options.productId) {
			var href_antifake = "/elabel/af?wz_id=" + options.productId + "&random_test=" + Math.random(); //防伪追踪
			var href_content = "/elabel/" + options.productId; //图文详情
			var href_videos = "/elabel/" + options.productId + "/videos"; //视频讲解
			var href_cases = "/elabel/" + options.productId + "/cases"; //各地经验
			var href_feedbacks = "/elabel/" + options.productId + "/feedbacks"; //用户反馈
		} else {
			var href_antifake = href_product_list;
			var href_content = href_product_list;
			var href_videos = href_product_list;
			var href_cases = href_product_list;
			var href_feedbacks = href_product_list;
		}
		
		jQuery("#menu_antifake").attr("href", href_antifake);
		jQuery("#menu_manufacturer_news").attr("href", href_news);
		jQuery("#menu_product_list").attr("href", href_product_list);
		jQuery("#menu_customer_tel").attr("href", href_tel);
		jQuery("#menu_product_content").attr("href", href_content);
		jQuery("#menu_product_videos").attr("href", href_videos);
		jQuery("#menu_product_cases").attr("href", href_cases);
		//jQuery("#menu_product_feedbacks").attr("href", href_feedbacks);
	}
	
	function set_html_title(title, manufacturerId) {
		if (!title) {
			if (manufacturerId) {
				jQuery.ajax({url: "/api/manufacturers/" + manufacturerId, dataType: "json", async: true, type: "GET",
					success:function(result) {
						if (result) {
							document.title = result.data.full_name;
						} else {
							document.title = "<%=isLanguageEn_header ? "Elabel" : "云单页"%>";
						}
					}
				});
			} else {
				document.title = "<%=isLanguageEn_header ? "Elabel" : "云单页"%>";
			}
		} else {
			document.title = title;
		}
	}
	
	//根据不同语言调用不同样式
	function set_custom_style() {
		var cssHref;
		<%
		if (isLanguageEn_header) {
		%>
			cssHref = "/elabel/css/style_en.css";
		<%
		} else {
		%>
			cssHref = "/elabel/css/style.css";
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
		/* if (browser.version.microMessenger) {
			//在微信中打开
			jQuery("#share_tips_weixin").show();
		} */
		
		//隐藏51la自动生成的图片
		jQuery("a").each(function() {
			//$(this).attr("href", "javascript:void(0);")
			//console.log(jQuery(this).attr("href"));
			var link_href = jQuery(this).attr("href");
			if (link_href && link_href.indexOf("51.la") > -1) {
				jQuery(this).hide();
			}
		});
		
	});
</script>