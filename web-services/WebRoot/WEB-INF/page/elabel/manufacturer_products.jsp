<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
boolean isLanguageEn = false;
%>

<%
	Integer manufacturerId = (Integer) request.getAttribute("manufacturer_id");
%>

<!DOCTYPE HTML>
<head>
<%@ include file="include/meta.jsp"%>
<!-- 引入通用的css、js等 -->
<%-- <jsp:include page="include/meta.jsp" flush="true" /> --%>

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
		<div id="content" class="snap-content">
			<div class="content">
				<div id="manufacturer_products_container"></div>
			</div>
		</div>
		
		<!-- 页面底部 -->
		<jsp:include page="include/footer.jsp" flush="true" />
	</div>
	
	<script type="text/javascript" src="<%=path%>/res/datetime.js"></script>
	<script type="text/javascript" src="<%=path%>/js/elabel/back-top.js"></script>
<%-- 	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script> --%>
	<script type="text/javascript" src="<%=path%>/res/custom/weixin-jssdk.js"></script>
	<script type="text/javascript" src="<%=path%>/js/elabel/my-animate.js"></script>
	
	<script>
		var pageNo = 1;
		var html = [];
		var totalPage = 0;
		
		(function ($) {
			
			function refreshData() {
				$.ajax({url: "<%=path%>/qr/api/manufacturer/<%=manufacturerId%>/products?page_size=15&current_page="+pageNo, dataType: "json", async: false, type: "GET",
					success:function(data){
						var dataList = data.returnData.list;
						totalPage = data.returnData.totalPage;
						if (dataList) {
							var wxShareImgUrl;
							//输出产品信息内容
							for (var i=0; i<dataList.length; i++) {
								var product = dataList[i];
								html.push('<div class="portfolio-two-item full-bottom">');
								html.push('<a href="<%=path%>/qr/elabel/' + product.id + '" class="portfolio-two-image">');
								<%-- html.push('<img src="'+ product.pic_url + '<%=GlobalConstant.IMG_PREVIEW_SIZE_200_200 %>" class="responsive-image">'); --%>
								html.push('<img src="'+ product.pic_url + '" class="responsive-image">');
								html.push('<div class="portfolio-two-text biaoti">');
								html.push('<span class="title">' + product.name + '</span>');
								html.push('</div>');
								html.push('</a>');
								html.push("</div>");
								if (i == 0) {
									wxShareImgUrl = product.pic_url;
								}
							}
							$("#manufacturer_products_container").html(html.join(''));

							//填写微信分享自定义内容
							/* wx.ready(function () {
								var shareData = {
							    title: '拜耳',
							    desc: '点击进入拜耳云单页产品手册',
							    //link: 'http://demo.open.weixin.qq.com/jssdk/',
							    imgUrl: wxShareImgUrl
							  };
							  wx.onMenuShareAppMessage(shareData);
							  wx.onMenuShareTimeline(shareData);
							}); */
						} else {
							$("#manufacturer_products_container").html("<span>无热门产品</span>");
						}
					},
				});
			}
			
			$().ready(function() {
				set_html_title('拜耳热门产品');
				set_custom_style("1"); //根据不同厂家 应用不同的样式
				set_elabel_title("热门产品");
				show_elabel_back();
				var options = {productId:getLastProductId(), manufacturerId:'1'};
				set_menu_href(options);
				//hide_menu();
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
