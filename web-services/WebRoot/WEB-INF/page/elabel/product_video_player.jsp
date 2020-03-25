<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>


<!DOCTYPE HTML>
<head>
	<!-- 引入通用的css、js等 -->
	<%@ include file="include/meta.jsp"%>
	
	<link rel="stylesheet" href="<%=path%>/res/am-swipe/css/amazeui.css">

<title>云单页</title>

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
						data-am-widget="gallery" data-am-gallery="{ pureview: true }">
					<div class="">
					  <h4 id="video_title"></h4>
					  <div class="video_views">
							<span class="v_date"></span>
							<span class="v_readrecord"></span>
					  </div>
					  <video id="video_url" class="responsive-video" width="100%" controls="" preload="none"></video>
					</div>
					<div class="video-others">
<!-- 						<div class="video_others_title">相关视频</div> -->
						<div class="video_others_title"></div>
						<div class="content_list3">
							
						</div>
					</div>
				</div>
				
				<!-- <dl id="share_tips_weixin" class="content share" style="display:none">
					<dt><i class="sharetitleleft"></i>分享收藏<i class="sharetitleright"></i></dt>
					<dd class="weibo"><a id="a_share_weibo" href="">微博</a></dd>
					<dd class="weixin" id="show_weixin"><a>微信</a></dd>
					<dd class="friend" id="show_friend"><a>朋友圈</a></dd>
					<dd class="favorite" id="show_favorite"><a>收藏</a></dd>
				</dl> -->
				
				<div class="footer">
					<ul class="parallel">
						<li id="manufacturer_news_views" class="grey small-font"></li>
<!-- 						<li><a id="a_redirect2home" class="small-font" href="#">返回图文讲解</a></li> -->
						<li><a id="a_redirect2home" class="small-font" href="#"></a></li>
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
<%-- 	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script> --%>
	<script type="text/javascript" src="<%=path%>/res/custom/weixin-jssdk.js"></script>
	<script type="text/javascript" src="<%=path%>/res/custom/api-product.js"></script>
	
	<script src="<%=path%>/js/elabel/resource_views.js"></script>
	
	<script>
		(function ($) {
			$().ready(function() {
				set_elabel_title('视频展示');
				//hide_menu();
				//initWeixinConfig(); //调用初始化微信JSSDK的配置信息
				<%
				String productId = (String)request.getSession().getAttribute("ELABEL_PRODUCT_ID");
				%>
				var options = {productId:'<%=productId%>', manufacturerId:'1'};
				set_menu_href(options);
				
				$.ajax({url: "<%=path%>/qr/api/product/videos/${video_id}", dataType: "json", async: true, type: "GET",
					success:function(data){
						console.log(data);
						var video = data.returnData;
						if (video) {
							var shareContent = "";
							/* var product = getProductById(video.product_id);
							if (product) {
								shareContent = "来自 " + product.name + "的视频";
							} */
							//填写微信分享自定义内容
							/* wx.ready(function () {
								var shareData = {
							    title: video.title,
							    desc: shareContent,
							    //link: 'http://demo.open.weixin.qq.com/jssdk/',
							    imgUrl: video.video_img_url
							  };
							  wx.onMenuShareAppMessage(shareData);
							  wx.onMenuShareTimeline(shareData);
							}); */
							
							//分享收藏按钮点击事件
							/* $("#show_favorite").click(function() {
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
							}); */
							//新浪微博分享按钮初始化
						 /*  $('#a_share_weibo').click(function() {
								var weiboShareContent = video.title;
								var href = "http://v.t.sina.com.cn/share/share.php?title=" + weiboShareContent + "&url=" + window.location.href;
								$('#a_share_weibo').attr("href", href);
						  }); */
							
							//显示返回按钮
							show_elabel_back('/elabel/' + video.product_id + '/videos');
							//返回图文讲解链接
							jQuery("#a_redirect2home").attr("href", "/elabel/" + video.product_id);
							
							set_html_title(video.title, 1);
							set_custom_style(1); //根据不同厂家 应用不同的样式
							
							//显示视频信息
							set_html_title(video.title);
							$("#video_title").html(video.title);
							$("#video_url").attr("src", video.url);
							$("#video_url").attr("poster", video.imgurl);
							$(".v_date").html(new Date(video.createtime).format("yyyy-MM-dd"));
							$(".v_readrecord").html("播 " + video.viewcount);
							
							/* viewResource(1, video.id, 'product_video');
							getViews(1, video.id, 'product_video', function(result) {
								var data = result.data;
								if (data) {
									var views = data[0].views;
									if (views < 10000) {
										$(".v_readrecord").html("播 " + views);
									} else {
										$(".v_readrecord").html("播 10000+");
									}
								} else {
									$(".v_readrecord").html("阅读 0");
								}
							}); */
							
							//加载相关视频
							showRelatedVideos(video.product_id);
						} else {
							console.log(1122);
						}
						
					},
				});
			});
			
			/** 获取产品详细信息 */
			function getProductById(productId) {
				var product = null;
				$.ajax({url: "<%=path%>/qr/api/product/" + productId, type: "GET", dataType: "json", async: false, data: null,
					success: function(result) {
						if (result.returnData) {
							product = result.returnData;
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						var result = jQuery.parseJSON(XMLHttpRequest.responseText);
						if (result) {
							throw {status: XMLHttpRequest.status, code: result.code, msg: result.msg};
						} else {
							throw {status: XMLHttpRequest.status};
						}
					}
				});
				
				return product;
			}
			
			function showRelatedVideos(productId) {
				var url = '<%=path%>/qr/api/product/' + productId + '/videos?current_page=1&page_size=5';
				$.ajax({url: url, dataType: "json", async: true, type: "GET", 
					success: function(result) {
						var videos = result.returnData.list;
						if (videos) {
							var items = [];
							var relatedVideoIds = [];
							items.push('<ul>');
							for (var i=0; i<videos.length; i++) {
								//去掉当前视频
								if (videos[i].id === ${video_id}) continue;
								
								relatedVideoIds.push(videos[i].id);
								var videoImgUrl = "<%=path%>/elabel/images/placeholder_video2.png";
								if (videos[i].imgurl) {
									videoImgUrl = videos[i].imgurl;
								}
								items.push('<li><a href="<%=path%>/qr/elabel/videos/' + videos[i].id + '">');
									items.push('<div class="videp_others_pic"><img height="60" src="' + videoImgUrl + '"></div>');
									items.push('<div class="videp_others_playview">');
										items.push('<h5>' + videos[i].title + '</h5>');
										items.push('<div class="videp_others_playrecord">');
											items.push('<span class="date">' + (new Date(videos[i].createtime).format("yyyy-MM-dd")) + '</span>');
											items.push('<span id="related_video_views_' + videos[i].id + '" class="readrecord">'+videos[i].viewcount+' 播放</span>');
										items.push('</div>');
									items.push('</div>');
								items.push('</a></li>');
							}
							items.push('</ul>');
							
							
							$(".content_list3").html(items.join(''));
							
							getViews(1, relatedVideoIds.join(","), 'product_video', function(result) {
								var data = result.data;
								if (data) {
									for (var i=0; i<data.length; i++) {
										var resourceViews = data[i];
										if (resourceViews.views < 100000) {
											$("#related_video_views_" + resourceViews.resource_id).html(resourceViews.views + " 播放");
										} else {
											$("#related_video_views_" + resourceViews.resource_id).html("100000+ 播放");
										}
									}
								}
							});
							
						}					
					}
				});
			}
			
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
