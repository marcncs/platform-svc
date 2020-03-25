<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title>零售机会详情</title>
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
											<img src="../images/CN/spc.gif" width="10" height="8">
										</td>
										<td >
											销售机会详情
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset align="center">
									<legend>
										<table width="50" border="0" cellpadding="0" cellspacing="0"
											class="table-detail">
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
												机会主题：
											</td>
											<td width="23%">
												${hf.haptitle}
											</td>
											<td width="11%" align="right">
												预计签单日期：
											</td>
											<td width="21%">
												<windrp:dateformat value='${hf.intenddate}' p='yyyy-MM-dd' />
											</td>
											<td width="11%" align="right">机会来源：</td>
											<td width="25%"><windrp:getname key="HapSrc" value="${hf.hapsrc}" p="d" /></td>
										</tr>

										<tr>
											<td width="9%" align="right">
												机会种类：
											</td>
											<td width="23%">
												<windrp:getname key='HapContent' value='${hf.hapcontent}'
													p='d' />
											</td>
											<td width="11%" align="right">
												机会性质：
											</td>
											<td width="21%">
												<windrp:getname key='HapKind' value='${hf.hapkind}' p='d' />
											</td>
											<td width="11%" align="right">
												机会状态：
											</td>
											<td width="25%">
												<windrp:getname key='HapStatus' value='${hf.hapstatus}' p='d' />
											</td>
										</tr>
										<tr>
											<td align="right">
												预计金额：
											</td>
											<td>
												<windrp:format value='${hf.intend}' p="###,##0.00"/>

											</td>
											<td align="right">
												下次约定日期：
											</td>
											<td>
												<windrp:dateformat value='${hf.hapbegin}' p='yyyy-MM-dd'/>
											</td>
											<td align="right">
												机会消失日期：
											</td>
											<td>
											<windrp:dateformat value='${hf.hapend}' p='yyyy-MM-dd'/>
											</td>
										</tr>
										<tr>
											<td align="right">
												对象类型：
											</td>
											<td>
												<windrp:getname key='ObjSort' value='${hf.objsort}' p='f' />
											</td>
											<td align="right">
												对象名称：
											</td>
											<td colspan="3">
												${hf.cname}
											</td>
										</tr>
										<tr>
											<td align="right">
												备注：
											</td>
											<td colspan="5">
												${hf.memo}
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
											<td align="right" width="113">
												制单机构：
											</td>
											<td width="290">
												<windrp:getname key='organ' value='${hf.makeorganid}' p='d' />
											</td>
											<td align="right" width="138">
												制单部门：
											</td>
											<td width="278">
												<windrp:getname key='dept' value='${hf.makedeptid}' p='d' />
											</td>
											<td width="127" align="right">制单人： </td>
										  <td width="315"><windrp:getname key='users' value='${hf.makeid}' p='d' /></td>
										</tr>
										<tr>
											<td align="right" width="113">制单日期：</td>
											<td width="290">
												<windrp:dateformat value='${hf.makedate}' p='yyyy-MM-dd' />
											</td>
											<td align="right">
												
											</td>
											<td>
												
											</td>
										  <td>&nbsp;</td>
										  <td>&nbsp;</td>
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
