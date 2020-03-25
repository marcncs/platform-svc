<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<html:errors />
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<table width="100%" height="40" border="0" cellpadding="0"
									cellspacing="0" class="title-back">
									<tr>
										<td width="10">
											<img src="../images/CN/spc.gif" width="10" height="1">
										</td>
										<td width="772">
											联系人详情
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>

								<fieldset align="center">
									<legend>
										<table width="50" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td>
													基本信息
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1"
										class="table-detail">
										<tr>
											<td width="9%" align="right">
												联系人姓名：
											</td>
											<td width="23%">
												${ld.name}
											</td>
											<td align="right">
												手机：
											</td>
											<td>
												${ld.mobile}
											</td>
											<%--<td width="10%" align="right">
												联系人性别：
											</td>
											<td width="22%">
												<windrp:getname key='Sex' value='${ld.sex}' p='f' />
											</td>
											--%><!--<td width="11%" align="right">身份证号码：</td>
	      <td width="25%">${ld.idcard}</td>
	  -->
											<%--<td align="right">
												部门：
											</td>
											<td>
												${ld.department}
											</td>

										--%>
											<%--<td align="right">
												主联系人：
											</td>
											<td>
												<windrp:getname key='YesOrNo' value='${ld.ismain}' p='f' />
											</td>
										--%></tr>
										<tr>

											<!--<td align="right">
												地址：
											</td>
											<td colspan="3">
												${ld.addr}
											-->
											</td>

										</tr>
									</table>
								</fieldset>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
