<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>

	<script type="text/javascript" src="../js/pub_Calendar.js"></script>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
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
								<table width="100%" border="0" cellpadding="0"
									cellspacing="0" class="title-back">
									<tr>
										<td width="10">
											<img src="../images/CN/spc.gif" width="10" height="8">
										</td>
										<td width="772">
											赠品详情
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
											<td align="right">
												客户类型：
											</td>
											<td>
												<windrp:getname key="ObjSort" p="f" value="${lf.objsort}"/>
											</td>
											<td height="20" align="right">
												客户名称：
											</td>
											<td>
												${lf.cname}
											</td>
											<td align="right">&nbsp;
												
											</td>
											<td>&nbsp;
												
											</td>
										</tr>
										<tr>
											<td width="9%" align="right">
												 赠品名称：
											</td>
											<td width="25%">
												${lf.largessname }
											</td>
											<td width="9%" height="20" align="right">
												赠品费用：
											</td>
											<td width="25%">
												<windrp:format value="${lf.lfee}"/>
											</td>
											<td width="9%" align="right">
												赠送日期：
											</td>
											<td width="23%">
												
												<windrp:dateformat value='${lf.ldate}' p='yyyy-MM-dd' />
											</td>
											
										</tr>

										<tr>
											<td height="20" align="right">
												赠品描述：
											</td>
											<td colspan="5">
												${lf.largessdescribe}
											</td>
										</tr>
										<tr>
											<td height="20" align="right">
												备注：
											</td>
											<td colspan="5">
												${lf.memo}
											</td>
										</tr>
									</table>
								</fieldset>


								<fieldset align="center">
									<legend>
										<table width="50" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td>
													其它信息
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
										<tr>
											<td width="9%" height="20" align="right">
												制单机构：
											</td>
											<td width="25%">
												<windrp:getname key='organ' value='${lf.makeorganid}' p='d' />
											</td>
											<td width="9%" align="right">
												制单部门：
											</td>
											<td width="23%">
												<windrp:getname key='dept' value='${lf.makedeptid}' p='d' />
											</td>
											<td width="9%" align="right">&nbsp;
												
											</td>
											<td width="25%">&nbsp;
												
											</td>
										</tr>
										<tr>
											<td width="9%" height="20" align="right">
												制单人：
											</td>
											<td width="25%">
												<windrp:getname key='users' value='${lf.makeid}' p='d' />
											</td>
											<td width="9%" align="right">
												制单日期：
											</td>
											<td width="23%">
												<windrp:dateformat value='${lf.makedate}' p='yyyy-MM-dd' />
											</td>
											<td width="9%" align="right">&nbsp;
												
											</td>
											<td width="25%">&nbsp;
												
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
