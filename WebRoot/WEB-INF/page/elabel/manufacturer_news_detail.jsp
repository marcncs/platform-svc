<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<head>
	<!-- 引入通用的css、js等 -->
	<%@ include file="include/meta.jsp"%>
	
	<link rel="stylesheet" href="<%=path%>/res/am-swipe/css/amazeui.css">

<title>云单页</title>

	<style type="text/css">
		.content img{
			width: 100%;
		}
	</style>
	
</head>
<body>

	<div class="all-elements">

		<!-- 页面头部 -->
		<jsp:include page="include/header.jsp" flush="true" />
		
		<div class="BackTop"><i class="fa fa-angle-up"></i></div>

		<!-- Page Content-->
		<div id="content" class="snap-content">

			<div class="content">
				
				<div id="manufacturer_news_detail_container" class="container no-bottom margin-table" 
						data-am-widget="gallery" data-am-gallery="{ pureview: true }"></div>
				
				<!-- <dl id="share_tips_weixin" class="content share" style="display:none">
					<dt><i class="sharetitleleft"></i>分享收藏<i class="sharetitleright"></i></dt>
					<dd class="weibo"><a id="a_share_weibo" href="">微博</a></dd>
					<dd class="weixin" id="show_weixin"><a>微信</a></dd>
					<dd class="friend" id="show_friend"><a>朋友圈</a></dd>
					<dd class="favorite" id="show_favorite"><a>收藏</a></dd>
				</dl> -->
				
				<div class="decoration container"></div>
			
				<div class="footer">
					<ul class="parallel">
						<li id="manufacturer_news_views" class="grey small-font"></li>
<!-- 						<li><a id="a_redirect2products" class="small-font" href="#">查看热门产品</a></li> -->
						<li><a id="a_redirect2products" class="small-font" href="#"></a></li>
					</ul>
				</div>
			
			</div>
			
		</div>
		
		<!-- 页面底部 -->
		<jsp:include page="include/footer.jsp" flush="true" />
	</div>
	
	<!-- 分享收藏提示tip的div块 -->
	<div class="tips_favorite" id="tips_favorite">
	    <img src="<%=path%>/res/tinybar/images/icons/tips_favorite.png" />
	</div>
    <div class="tips_favorite" id="tips_share">
	    <img src="<%=path%>/res/tinybar/images/icons/tips_share.png" />
	</div>
	<div class="tips_favorite_bg"></div>
	
	<script type="text/javascript" src="<%=path%>/res/datetime.js"></script>
	<script type="text/javascript" src="<%=path%>/js/elabel/back-top.js"></script>
<%-- 	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script> --%>
	<script type="text/javascript" src="<%=path%>/res/custom/weixin-jssdk.js"></script>
	<script type="text/javascript" src="<%=path%>/js/elabel/my-animate.js"></script>
	<script src="<%=path%>/res/am-swipe/js/amazeui.min.js"></script>
	<script src="<%=path%>/js/elabel/resource_views.js"></script>
	
	<script>
		(function ($) {
			$().ready(function() {
				set_elabel_title('成功故事');
				//hide_menu();
				var options = {productId:getLastProductId(), manufacturerId:'1'};
				set_menu_href(options);
				//initWeixinConfig(); //调用初始化微信JSSDK的配置信息
				
				$.ajax({url: "<%=path%>/qr/api/manufacturer/news/${news_id}", dataType: "json", async: true, type: "GET",
					success:function(data){
						var news = data.returnData;
						if (news) {
							//填写微信分享自定义内容
							/* wx.ready(function () {
								var shareData = {
							    title: news.title,
							    desc: news.summary,
							    //link: 'http://demo.open.weixin.qq.com/jssdk/',
							    imgUrl: news.pic_url
							  };
							  wx.onMenuShareAppMessage(shareData);
							  wx.onMenuShareTimeline(shareData);
							});
							
							//分享收藏按钮点击事件
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
								var weiboShareContent = news.title;
								var href = "http://v.t.sina.com.cn/share/share.php?title=" + weiboShareContent + "&url=" + window.location.href;
								$('#a_share_weibo').attr("href", href);
						  });
							 */
							//显示返回按钮
							show_elabel_back('<%=path%>qr/elabel/manufacturer/' + news.manufacturer_id + '/news');
							//返回热门产品链接
							jQuery("#a_redirect2products").attr("href", "/elabel/manufacturer/" + news.manufacturer_id + "/products");
							set_html_title(news.title, news.manufacturer_id);
							set_custom_style(news.manufacturer_id); //根据不同厂家 应用不同的样式
							
							var html = [];
							html.push('<div class="one-half-responsive last-column">');
							html.push('<h3 class="center-if-mobile no-bottom">' + news.title + '</h3>');
							html.push('<span class="time-areas">' + (new Date(news.publish_time).format("yyyy-MM-dd")) + '</span>');
							/* if (news.is_show_pic) {
								html.push('<img class="post" src="' + news.pic_url + '">');
							} */
							html.push('<div class="center-if-mobile no-bottom">' + news.content + '</div>');
							html.push("</div>");
							$("#manufacturer_news_views").html("阅读 " + news.viewcount);
							
							/* viewResource(news.manufacturer_id, news.id, 'manufacturer_news');
							getViews(news.manufacturer_id, news.id, 'manufacturer_news', function(result) {
								var data = result.data;
								if (data) {
									var views = data[0].views;
									if (views < 10000) {
										$("#manufacturer_news_views").html("阅读 " + views);
									} else {
										$("#manufacturer_news_views").html("阅读 10000+");
									}
								} else {
									$("#manufacturer_news_views").html("阅读 0");
								}
							}); */
							
							$("#manufacturer_news_detail_container").html(html.join(''));
						} else {
							$("#manufacturer_news_detail_container").html("<span>新闻获取错误</span>");
						}
						
						$("#manufacturer_news_detail_container").find('img').each(function(i, n) {
							//$(n).wrap('<a href="' + $(n).attr("src") + '" class=""></a>');
							$(n).attr('alt', '');
						});
					},
				});
			});
		}(jQuery));
	</script>
	
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?84f2ccbc987ef138709ef25bd96daf46";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
</body>
