<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<!DOCTYPE HTML>
<head>
<%@ include file="include/meta.jsp"%>
<title>云单页</title>
<style type="text/css">
	.box {
    margin-top: 5px;
    font-size: 12px;
    width: 100%;
    height: auto;
    -webkit-background-size: 100%;
    background-size: 100%;
    background-repeat: no-repeat;
     }
	.box p{
    width: 95%;
    font-size: 15px;
    text-align: left;
    margin: 3px auto;
    color: #092b47;
        }
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
		
			<div class="container">
					<div class="box">
          	<img src="<%=path%>/images/elabel/banner.png" alt="" style="width: inherit;height: auto;margin-bottom:10px;">
              <p>${OnlineShopUrl}</p>
          </div>
			</div>
            
		</div>
		
		<!-- 页面底部 -->
		<jsp:include page="include/footer.jsp" flush="true" />
	</div>
	
	<script type="text/javascript" src="<%=path%>/res/datetime.js"></script>
	<script type="text/javascript" src="<%=path%>/js/elabel/my-animate.js"></script>
<%-- 	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script> --%>
	<script type="text/javascript" src="<%=path%>/res/custom/weixin-jssdk.js"></script>
	<script src="<%=path%>/js/elabel/resource_views.js"></script>
	
	<script>
		(function ($) {
			$().ready(function() {
				set_html_title('拜耳');
				set_elabel_title('产品热线');
				set_custom_style('1'); //根据不同厂家 应用不同的样式
				show_elabel_back(); //显示返回按钮
				var options = {productId:getLastProductId(), manufacturerId:'1'};
				set_menu_href(options);
			});
		}(jQuery));
		
	</script>

</body>
