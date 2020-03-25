<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>

	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

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
											详情
										</td>
										<td width="276" align="right">
											<table width="60" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="60" align="center"></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<form name="validateCustomer" method="post"
									action="../sales/addMemberAction.do">

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
												<td width="13%" align="right">
													编号：
												</td>
												<td width="23%">
													${of.id}
												</td>
												<td width="12%" align="right">
													企业内部编码：
												</td>
												<td width="19%">
													${of.oecode}
												</td>
												<td width="10%" align="right">
													名称：
												</td>
												<td width="23%">
													${of.organname}
												</td>
											</tr>
											<tr>
												<td align="right">
													电话：
												</td>
												<td>
													${of.otel}
												</td>
												<td align="right">
													手机：
												</td>
												<td>
													${of.omobile}
												</td>
												<td align="right">
													传真：
												</td>
												<td>
													${of.ofax}
												</td>
											</tr>
											<tr>
												<td align="right">
													EMail：
												</td>
												<td>
													${of.oemail}
												</td>
												<td align="right">
													上级机构：
												</td>
												<td>
													<windrp:getname key='organ' value='${of.parentid}' p='d' />
												</td>
												<td rowspan="2" align="right" valign="top">
													Logo：
												</td>
												<td rowspan="2">
													<c:if test="${not empty of.logo}">
														<img src="../${logo }" width="123" height="55"></img>
													</c:if>
												</td>
												<%--<td align="right">
													价格级别：
												</td>
												<td>
													${ratename}
												</td>
												<td align="right">
													付款帐期：
												</td>
												<td>
													${of.prompt}/天
												</td>
											--%>
											</tr>
											<%--
											<tr>

												<td align="right">
													付款条件：
												</td>
												<td>
													${of.paycondition}
												</td>
											</tr>
											--%>
											<tr>

												<!-- 
	    <td  align="right">级别：</td>
	    <td>${of.rank}</td>
	     -->
												<td align="right">
													区域：
												</td>
												<td colspan="3">
													${province}-${city}-${areas}
												</td>

											</tr>
											<tr>
												<td align="right">
													地址：
												</td>
												<td colspan="5">
													${of.oaddr}
												</td>
											</tr>
											<tr>
												<td align="right">
													机构类型：
												</td>
												<td>
													<windrp:getname key='OrganType' value='${of.organType}' p='f' />
												</td>
												<c:if test="${of.organType == 1}">
													<td align="right">
														类别：
													</td>
													<td>
														<windrp:getname key='PlantType' value='${of.organModel}' p='f' />
													</td>
												</c:if>
												<c:if test="${of.organType == 2}">
													<td align="right">
														类别：
													</td>
													<td>
														<windrp:getname key='DealerType' value='${of.organModel}' p='f' />
													</td>
												</c:if>
											</tr>
										</table>
									</fieldset>
									<%--<fieldset align="center">
										<legend>
											<table width="50" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														状态信息
													</td>
												</tr>
											</table>
										</legend>
										<table width="100%" border="0" cellpadding="0" cellspacing="1"
											class="table-detail">
											<tr>
												<td width="10%" align="right">
													是否撤消：
												</td>
												<td width="20%">
													<windrp:getname key='YesOrNo' value='${of.isrepeal}' p='f' />
												</td>
												<td width="10%" align="right">
													撤消人：
												</td>
												<td width="25%">
													<windrp:getname key='users' value='${of.repealid}' p='d' />
												</td>
												<td width="15%" align="right">
													撤消日期：
												</td>
												<td width="20%">
													${of.repealdate}
												</td>
											</tr>
										</table>
									</fieldset>


								--%></form>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>
