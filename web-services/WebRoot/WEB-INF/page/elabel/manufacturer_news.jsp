<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
boolean isLanguageEn = false;

%>

<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
<!-- 引入通用的css、js等 -->
<%@ include file="include/meta.jsp"%>

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
				<div id="manufacturer_news_container" class="container"></div>
			</div>
		</div>
		
		<!-- 页面底部 -->
		<jsp:include page="include/footer.jsp" flush="true" />
	</div>
	<script type="text/javascript" src="<%=path%>/js/elabel/my-animate.js"></script>
	<script type="text/javascript" src="<%=path%>/res/datetime.js"></script>
	<script type="text/javascript" src="<%=path%>/js/elabel/back-top.js"></script>
	
<%-- 	<script src="<%=path%>/js/elabel/resource_views.js"></script> --%>
	
	<script>
		(function ($) {
			$().ready(function() {
				set_html_title('拜耳成功故事');
				set_custom_style(); //根据不同厂家 应用不同的样式
				set_elabel_title("成功故事");
				var productId = getLastProductId();
				if (productId) {
					//如果cookies中有产品信息，则返回到产品页
					show_elabel_back("<%=path%>/qr/elabel/" + productId);
				} else {
					//如果没有则返回热门产品
					show_elabel_back('<%=path%>/qr/elabel/manufacturer/1/products');
				}
				var options = {productId:getLastProductId(), manufacturerId:'1'};
				set_menu_href(options);
				//hide_menu();
				
				$.ajax({url: "<%=path%>/qr/api/manufacturer/1/news?page_size=99", dataType: "json", async: false, type: "GET",
					success:function(data){
						var dataList = data.returnData.list;
						if (dataList) {
							var html = [];
							var newsIds = [];
							for (var i=0; i<dataList.length; i++) {
								var manufacturerNews = dataList[i];
								newsIds.push(manufacturerNews.id);
								
								html.push('<a href="<%=path%>/qr/elabel/manufacturer/news/' + manufacturerNews.id + '">');
								html.push('<div class="one-half-responsive">');
								html.push('<div class="pic-thumbnail">');
								html.push('<figure class="figure-thumbnail" style="background-image:url(' + manufacturerNews.pic_url + ')"></figure>');
								html.push('</div>');
								html.push('<div class="wordbox">');
								html.push('<strong class="designation">' + manufacturerNews.title + '</strong>');
								html.push('<div class="location-pviews">');
								html.push('<span class="adress-time">' + (new Date(manufacturerNews.publish_time).format("yyyy-MM-dd")) + '</span>');
								html.push('<span id="manufacturer_news_views_' + manufacturerNews.id + '" class="pviews">'+manufacturerNews.viewcount+'阅</span>');
								html.push('</div>');
								html.push('<p class="summary">' + manufacturerNews.summary + '</p>');
								html.push('</div>');
								html.push('<div class="thumb-clear"></div>');
								html.push("</div></a>");
								html.push('<div class="decoration hide-if-responsive"></div>');
							}
							$("#manufacturer_news_container").html(html.join(''));
							
							/* getViews(1, newsIds.join(","), 'manufacturer_news', function(result) {
								var data = result.data;
								if (data) {
									for (var i=0; i<data.length; i++) {
										var news = data[i];
										if (news.views < 10000) {
											$("#manufacturer_news_views_" + news.resource_id).html(news.views + " 阅");
										} else {
											$("#manufacturer_news_views_" + news.resource_id).html("10000+阅");
										}
									}
								}
							}); */
							
						} else {
							$("#manufacturer_news_container").html('<p class="no-detail"><i class="fa fa-exclamation-circle red"></i>' + "&nbsp;" + "<%=isLanguageEn ? "no news" : "厂家未上传新闻"%>"+'</p>');
						}
					},
				});
			});
		}(jQuery));
	</script>

</body>
</html>
