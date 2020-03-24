<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>

	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
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
											相关合同详细信息
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
									<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
										<tr>
											<td width="9%" align="right">
												客户：
											</td>
											<td width="21%">
												${pt.cname}
											</td>
											<td width="13%" align="right">
												业务员：
											</td>
											<td width="23%">
												<windrp:getname key='users' value='${pt.userid}' p='d' />
											</td>
											<td width="9%" align="right">
												合同编号：
											</td>
											<td width="25%">
												${pt.pactcode}
											</td>
										</tr>
										<tr>
											<td align="right">
												合同类型：
											</td>
											<td>
												<windrp:getname key="PactType" value="${pt.pacttype}" p="d" />
											</td>
											<td align="right">
												客户方代表：
											</td>
											<td>
												${pt.cdeputy}
											</td>
											<td align="right">
												签定日期：
											</td>
											<td>
											<windrp:dateformat value='${pt.signdate}' p='yyyy-MM-dd' />
											</td>
										</tr>
										<tr>
											<td align="right">
												供方：
											</td>
											<td>
												${pt.provide}
											</td>
											<td align="right">
												供方代表：
											</td>
											<td>
												${pt.pdeputy}
											</td>
											<td align="right">
												签定地址：
											</td>
											<td>
												${pt.signaddr}
											</td>
										</tr>
										<tr>
											<td align="right">
												合同范围：
											</td>
											<td colspan="5">
												${pt.pactscopy}
											</td>
											</tr>
											<tr>
											<td align="right">
												附件：
											</td>
											
											<td colspan="5">
												<img src="../images/CN/beizheng.gif" border="0">
												<a href="../common/downloadfile.jsp?filename=${pt.affix}">${pt.affix}</a>
											</td>
										</tr>
									</table>
								</fieldset>
								<fieldset align="center">
									<legend>
										<table width="50" border="0" cellpadding="0" cellspacing="0"
											class="table-detail">
											<tr>
												<td>
													状态信息
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
										<tr>
											<td align="right" width="9%">
												制单机构：
											</td>
											<td width="21%">
												<windrp:getname key='organ' value='${pt.makeorganid}' p='d' />
											</td>
											<td align="right" width="13%">
												制单部门：
											</td>
											<td>
												<windrp:getname key='dept' value='${pt.makedeptid}' p='d' />
											</td>
										</tr>
										<tr>
											<td align="right" width="100">
												制单人：
											</td>
											<td width="200">
												<windrp:getname key='users' value='${pt.makeid}' p='d' />
											</td>
											<td align="right">
												制单时间：
											</td>
											<td>
												<windrp:dateformat value='${pt.makedate}' p='yyyy-MM-dd' />
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
