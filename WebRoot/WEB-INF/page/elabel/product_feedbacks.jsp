<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
boolean isLanguageEn = false;

%>

<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
<!-- 引入通用的css、js等 -->
<%@ include file="include/meta.jsp"%>

<link href="<%=path%>/res/bracket/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=path%>/res/star-rating.min.css" media="all" rel="stylesheet" type="text/css"/>

<link rel="stylesheet" href="<%=path%>/res/am-swipe/css/amazeui.css">

<title>云单页</title>

</head>
<body>

	<div class="all-elements">
		<div class="snap-drawers">
		</div>

		<!-- 页面头部 -->
		<jsp:include page="include/header.jsp" flush="true" />
		
		<div class="BackTop"><i class="fa fa-angle-up"></i></div>
		<div class="fixed_bottom_button"><a href="javascript:void(0);" class="button button-green full-button">发表评论</a></div>

		<!-- Page Content-->
		<div class="snap-content">

			<div class="content">
				
				<div id="product_feedbacks_container"></div>
				
			</div>
			
			<!-- Page Footer-->
		</div>
	</div>
	
	<div class="am-modal am-modal-alert" tabindex="-1" id="model_evaluated_tip">
	  <div class="am-modal-dialog">
	    <div class="am-modal-bd">
	      	您已评论！
	    </div>
	    <div class="am-modal-footer">
	      <span class="am-modal-btn">确定</span>
	    </div>
	  </div>
	</div>
	
	<script type="text/javascript" src="<%=path%>/res/datetime.js"></script>
	<script type="text/javascript" src="<%=path%>/js/elabel/my-animate.js"></script>
	<script src="<%=path%>/res/star-rating.min.js" type="text/javascript"></script>
	
	<script>
		(function ($) {
			//生成某一个评价的html元素
			function createFeedbackItem(feedback) {
				if (!feedback) return "";
				var html = [];
				html.push('<div class="one-half-responsive">');
				if (feedback.individual) {
					html.push('<strong class="designation">' + feedback.individual.real_name + '&nbsp;&nbsp;' + '<span class="grey small-font">' + (new Date(feedback.createtime).format("yyyy-MM-dd hh:mm")) + '</span>');
				} else {
					html.push('<strong class="designation">游客&nbsp;&nbsp;' + '<span class="grey small-font">' + (new Date(feedback.createtime).format("yyyy-MM-dd hh:mm")) + '</span>');
				}
// 				html.push('<div style="float:right;"><input value="' + feedback.starscore + '" type="number" class="rating" data-size="xs" readonly></div>');
				html.push('</strong>');
				html.push('<p>' + feedback.content + '</p>');
				html.push('<div class="clear"></div>');
				if (feedback.picurl) {
					html.push('<div data-am-widget="gallery" data-am-gallery="{pureview: true}" class="pic-thumbnail">');
					html.push('<a href="' + feedback.picurl + '"><img class="figure-thumbnail" src="' + feedback.picurl + '" style="background-image:url(' + feedback.picurl + ')"></img></a>');
					html.push('</div>');
					html.push('<div class="thumb-clear"></div>');
				}
				if (feedback.isreplied == 1) {
					html.push('<div class="feedback-reply">');
					html.push('<p style="margin:0;">厂家回复：' + feedback.reply + '</p>');
					html.push('<p class="adress-time">' + (new Date(feedback.replytime).format("yyyy-MM-dd hh:mm")) + '</p>');
					html.push('</div>');
				}
				html.push("</div>");
				html.push('<div class="decoration"></div>');
				
				return html.join("");
			}
			
			function showFeedbacks() {
				var html = [];
				var ownFeedback = null;
				//获取用户自己对该产品的评价
				if (getAccessToken()) {
					$.ajax({url: "<%=path%>/qr/api/product/${product_info.id}/feedbacks/own", dataType: "json", async: false, type: "GET",
						headers: {"token": getAccessToken() }, success:function(result) {
							ownFeedback = result.data;
							html.push(createFeedbackItem(ownFeedback));
						}
					});
				}
				//获取该产品的所有评价
				$.ajax({url: "<%=path%>/qr/api/product/${product_info.id}/feedbacks", dataType: "json", async: false, type: "GET",
				//$.ajax({url: "<%=path%>/qr/api/product/feedbacks", dataType: "json", async: false, type: "GET",
					success:function(result){
						if (result.returnData && result.returnData.list) {
							var dataList = result.returnData.list;
							for (var i=0; i<dataList.length; i++) {
								var feedback = dataList[i];
								if (!ownFeedback || feedback.id !== ownFeedback.id) {
									html.push(createFeedbackItem(feedback));
								}
							}
						}
					},
				}); //ajax结束
				
				if (html && html.length > 0) {
					$("#product_feedbacks_container").html(html.join(''));
					//星星分数显示
					$('.rating').rating('refresh', {
				    min: 0,
				    max: 5,
				    step: 1,
				    showClear: false,
				    showCaption: false
				  });
				} else {
					$("#product_feedbacks_container").html('<p class="no-detail"><i class="fa fa-exclamation-circle red"></i>' + "&nbsp;" + "暂无评论"+'</p>');
				}
			}
			
			$().ready(function() {
				set_html_title('拜耳用户交流');
				set_custom_style('1'); //根据不同厂家 应用不同的样式
				set_elabel_title('${product_info.name}');
				hide_elabel_back();
				var options = {productId:'${product_info.id}', manufacturerId:'1'};
				set_menu_href(options);
				
				showFeedbacks();
				
				$('.fixed_bottom_button').click(function() {
					//判断用户是否登录
					if (getAccessToken()) {
						//判断是否评价过
						ajaxJson("<%=path%>/qr/api/product/${product_info.id}/feedbacks/evaluated", "GET", null, false, 
							function(result){
								//请求成功
								if (result.data) {
									//评价过，则提示
									$("#model_evaluated_tip").modal();
								} else {
									//没有评价，则可以进入评价页面
									window.location.href = "<%=path%>/qr/elabel/${product_info.id}/feedbacks/evaluation";
								}
							}, 
							function(result) {
								//请求错误
								if (result.code == 'WZ130001') {
									window.location.href = "<%=path%>/qr/elabel/signin.jsp";
								}
							}
						);
					} else {
						//未登录则直接进行匿名评论
						window.location.href = "<%=path%>/qr/elabel/${product_info.id}/feedbacks/evaluation";
						//window.location.href = "/elabel/signin.jsp";
					}
				});
									
			});
		}(jQuery));
	</script>
	
	<script src="<%=path%>/res/am-swipe/js/amazeui.min.js"></script>
	
</body>
</html>
