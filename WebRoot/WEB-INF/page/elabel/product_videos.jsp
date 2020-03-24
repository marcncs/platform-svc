<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
boolean isLanguageEn = false;
%> 

<!DOCTYPE HTML>
<head>
<!-- 引入通用的css、js等 -->
<%@ include file="include/meta.jsp"%>

<title>云单页</title>


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
				<div id="product_videos" class="content_list3"></div>
			</div>
		</div>
		
		<!-- 页面底部 -->
		<jsp:include page="include/footer.jsp" flush="true" />
	</div>
	
	<script type="text/javascript" src="<%=path%>/res/datetime.js"></script>
	<script type="text/javascript" src="<%=path%>/js/elabel/my-animate.js"></script>
<%-- 	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script> --%>
<%-- 	<script type="text/javascript" src="<%=path%>/res/custom/weixin-jssdk.js"></script> --%>
<%-- 	<script src="<%=path%>/js/elabel/resource_views.js"></script> --%>
	
	<script>
		var pageNo = 1;
		var videoIds = [];
		var htmlVideos = [];
		var totalPage = 0;
		
		(function ($) {
			function refreshData() {
				$.ajax({url: "<%=path%>/qr/api/product/${product_info.id}/videos?page_size=15&current_page="+pageNo, dataType: "json", async: false, type: "GET",
					success:function(data){
						var videos = data.returnData.list;
						totalPage = data.returnData.totalPage;
						if (videos && videos.length > 0) {
							var shareDesc = "";
							htmlVideos.push('<ul>');
							for (var i=0; i<videos.length; i++) {
								var video = videos[i];
								videoIds.push(video.id);
								var videoTagId = "video_" + video.id;
								
								htmlVideos.push('<li><a href="<%=path%>/qr/elabel/videos/' + video.id + '">');
								var imgUrl = null;
								if (video.imgurl) {
									imgUrl = video.imgurl;
								} else {
									imgUrl = "/elabel/images/placeholder_video2.png";
								}
								htmlVideos.push('<div class="videp_others_pic"><img height="60" src="' + imgUrl + '" alt=""></div>');
								htmlVideos.push('<div class="videp_others_playview">');
									htmlVideos.push('<h5>' + video.title + '</h5>');
									htmlVideos.push('<div class="videp_others_playrecord">');
										htmlVideos.push('<span class="date">' + (new Date(video.createtime).format("yyyy-MM-dd")) + '</span>');
										htmlVideos.push('<span id="product_videos_views_' + video.id + '" class="readrecord">'+video.viewcount+'播放</span>');
									htmlVideos.push('</div>');
								htmlVideos.push('</div>');
								htmlVideos.push('</a></li>');
								
								shareDesc = shareDesc + video.title + "；"; //微信分享内容
							}
							htmlVideos.push('</ul>');
							$("#product_videos").html(htmlVideos.join(''));
						} else {
							$("#product_videos").html('<p class="no-detail"><i class="fa fa-exclamation-circle red"></i>' + "&nbsp;" + "更多活动，敬请期待"+'</p>');
						}
					},
				});
			}
			
			
			$().ready(function() {
				set_html_title('拜耳视频展示');
				set_custom_style('1'); //根据不同厂家 应用不同的样式
				//set_elabel_title('${product_info.name}');
				set_elabel_title('视频展示');
				hide_elabel_back();
				var options = {productId:'${product_info.id}', manufacturerId:'1'};
				set_menu_href(options);
				//initWeixinConfig(); //调用初始化微信JSSDK的配置信息
				refreshData();
				//页面滚到底部异步加载下一页数据
			    $(window).scroll(function () {
			        //已经滚动到上面的页面高度
			        var scrollTop = parseFloat($(this).scrollTop()),
			        //页面高度
			            scrollHeight = $(document).height(),
			        //浏览器窗口高度
			            windowHeight = parseFloat($(this).height()),
			            totalHeight = scrollTop + windowHeight;
			        //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
			        if (scrollTop + windowHeight >= scrollHeight - 0.7) {
			            pageNo++;
			            if(pageNo > totalPage) {
			            	return false;
			            }
			            refreshData();
			        }
			    });
			});
		}(jQuery));
	</script>

</body>
