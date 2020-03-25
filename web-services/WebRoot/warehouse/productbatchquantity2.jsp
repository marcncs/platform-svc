 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head> 
		<title>WINDRP-选择批次</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script src="../js/function.js"></script>
	</head>
	<body>
	<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td width="925">
								查看检货状态
							</td>
							<td width="308" align="right">
								<table width="120" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="120" align="center">
											<a href="#" onclick="window.close();">关闭</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<td>
									<input type="hidden" name="productid" value="${productid}" />
									<table width="100%" border="0"
											cellpadding="0" cellspacing="1">
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr >
												<td>&nbsp;&nbsp;产品名称：</td>
												<td align="left" colspan="3">
													&nbsp;&nbsp;${productName}
												</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
											</table>
									<fieldset align="center">
										<legend>
											<table width="50" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														检货状态
													</td>
												</tr>
											</table>
										</legend>
										
										<table width="100%" border="0"
											cellpadding="0" cellspacing="1">
											<tr class="title-top">
												<td width="100" align="center">
													产品ID
												</td>
												<td width="100" align="center">
													批次
												</td>
												<td width="150" align="center">
													原分配数量(箱数)
												</td>
												<td width="150" align="center">
													原分配数量(散数)
												</td>
												<td width="150" align="center">
													实际出库数量(箱数)
												</td>
												<td width="150" align="center">
													实际出库数量(散数)
												</td>
												<td width="150" align="center">
													关联单据号
												</td>
											</tr>
											<c:forEach var="ttdbb" items="${ttdbbs}">
												<tr class="table-back-colorbar">
													<td align="center">
														${productid}
													</td>
													<td align="center">
														${ttdbb.batch}
													</td>
													<td align="center">
														${ttdbb.boxnum}
													</td>
													<td align="center">
														${ttdbb.scatternum}
													</td>
													<td align="center">
														${ttdbb.realboxnum}
													</td>
													<td align="center">
														${ttdbb.realscatternum}
													</td>
													<td align="center">
														${ttdbb.ttid}
													</td>
												</tr>
											</c:forEach>
										</table>
									</fieldset>
								<br />
								<br />
							</td>
						</tr>
					</table>
					</td>
					</tr>
					</table>
					</div>
	</body>
</html>