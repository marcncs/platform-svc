<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<%@ page import="com.winsafe.drp.dao.AppBaseResource" %>
<%@ page import="com.winsafe.drp.dao.BaseResource" %>
<%
String type = request.getParameter("type");
AppBaseResource appBr = new AppBaseResource();
BaseResource pImage = appBr.getBaseResourceValue("productPicture", 1);
String imageName = "";
if(pImage!=null){
	imageName = pImage.getTagsubvalue();
}

%>
<html>
  <head>
    <title></title>
    
	<meta charset="utf-8">
	<meta name="viewport"
			content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1">
	<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
	<script language="JavaScript">
		
	</script>
  </head>
  
  <body>
  		<div align="center"><img style="max-width: 100%;overflow: hidden;" src="../images/CN/<%=imageName%>"></div>
  		<p>
  		<c:if test='<%=type != null && type.equals("1") %>'>
  		*以上为目前可用拜验证扫描验证的拜耳品牌列表<br/><br/>
  		适用于拜耳作物科学（中国）有限公司2013年11月8日以后生产分装的产品，产品验证功能涵盖含有二维码信息的瓶装，铝箔袋和针剂彩盒标签。
  		</c:if>
  		<c:if test='<%=type != null && type.equals("2") %>'>
  		*以上为目前可用拜追踪扫描验证的拜耳品牌列表<br/><br/>
  		适用于拜耳作物科学（中国）有限公司2013年11月8日以后由杭州工厂生产分装的产品，产品追踪涵盖二维码箱标，二维码瓶装，铝箔袋和针剂彩盒标签以及2015年一季度后含有暗码的产品。
  		</c:if>
  		</p>
  </body>
</html>
