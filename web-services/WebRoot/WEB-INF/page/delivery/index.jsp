<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;"
	name="viewport" />
<title>单据确认</title>
<style>
html, body {
	width: 100%;
	height: 100%;
	margin: 0;
}
</style>
</head>
<body style="background: #f4f4f4; overflow-y: auto;">
	<c:choose>
		<c:when test="${isExpire}">
			<div style="background: #1c97d0; padding: 10px 10px;">
				<div style="color: #ffffff;text-align: center;">
					无相关信息
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div style="background: #1c97d0; padding: 10px 10px;">
				<div style="color: #ffffff;">
					<div>
						<div
							style="display: inline; width: 20%; float: left; height: 25px; line-height: 25px;">
							单据号</div>
						<div
							style="display: inline; width: 80%; height: 25px; line-height: 25px;">
							${data.sapNo }</div>
					</div>
					<div>
						<div
							style="display: inline; width: 20%; float: left; height: 25px; line-height: 25px;">
							收货客户</div>
						<div
							style="display: inline; width: 80%; height: 25px; line-height: 25px;">
							${data.customer }</div>
					</div>
				</div>
			</div>
			<div style="padding: 10px 10px;">
				<div>
					<div
						style="display: inline; width: 20%; height: 25px; line-height: 25px; color: #494949; font-weight: bold;">
						单据详情</div>
				</div>
			</div>
			<div style="padding: 0 0 40px;">
				<div style="background: #ffffff; padding: 0 10px">
					<logic:iterate id="c" name="details">
						<div style="border-bottom: solid thin #e8e8e8; padding: 5px 0;">
							<div>
								<div
									style="display: inline; width: 20%; float: left; height: 25px; line-height: 25px; color: #888888;">
									产品代码</div>
								<div
									style="display: inline; width: 80%; height: 25px; line-height: 25px; color: #494949;">
									${c.productid}</div>
							</div>
							<div>
								<div
									style="display: inline; width: 20%; float: left; height: 25px; line-height: 25px; color: #888888;">
									产品名称</div>
								<div
									style="display: inline; width: 80%; height: 25px; line-height: 25px;">
									${c.productname}</div>
							</div>
							<div>
								<div
									style="display: inline; width: 20%; float: left; height: 25px; line-height: 25px; color: #888888;">
									产品规格</div>
								<div
									style="display: inline; width: 80%; height: 25px; line-height: 25px;">
									${c.specmode}</div>
							</div>
							<div>
								<div
									style="display: inline; width: 20%; float: left; height: 25px; line-height: 25px; color: #888888;">
									产品批号</div>
								<div
									style="display: inline; width: 80%; height: 25px; line-height: 25px; color: #494949;">
									${c.batch}</div>
							</div>
							<div>
								<div
									style="display: inline; width: 20%; float: left; height: 25px; line-height: 25px; color: #888888;">
									产品数量</div>
								<div
									style="display: inline; width: 80%; height: 25px; line-height: 25px; color: #494949;">
									${c.count}件</div>
							</div>
							<%-- <div>
						<div
							style="display: inline; width: 20%; float: left; height: 25px; line-height: 25px; color: #888888;">
							计量单位</div>
						<div
							style="display: inline; width: 80%; height: 25px; line-height: 25px; color: #494949;">
							${c.quantity}</div>
					</div> --%>
						</div>
					</logic:iterate>
				</div>
			</div>
			<!-- <div
				style="position: fixed; color: #ffffff; font-size: 18px; bottom: 0; left: 0; width: 100%; height: 40px; line-height: 40px; text-align: center; background: #89b744;">
				确定</div> -->
		</c:otherwise>
	</c:choose>
</body>
</html>
