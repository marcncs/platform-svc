<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@page import="java.util.*,java.text.*"%>
<%@ include file="../common/tag.jsp"%>
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
											<img src="../images/CN/spc.gif" width="10" height="8">
										</td>
										<td width="772">
											仓库详情
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
											<td width="9%" height="20" align="right">
												仓库名称：
											</td>
											<td width="25%">
												${wf.warehousename}
											</td>
											<%--<td width="9%" align="right">所属部门：</td>
          <td width="23%"><windrp:getname key='dept' value='${wf.dept}' p='d'/></td>
	      --%>
	      
											<td width="9%" align="right">
												负责人：
											</td>
											<td width="25%">
												${wf.username}
											</td>
											<td align="right">
												是否自动签收：
											</td>
											<td>
												<windrp:getname key="YesOrNo" value='${wf.isautoreceive}' p="f" />
											</td>
										</tr>
										<tr>
											<td height="20" align="right">
												联系电话：
											</td>
											<td>
												${wf.warehousetel}
											</td>
											<td align="right">
												仓库属性：
											</td>
											<td>
												<windrp:getname key='WarehouseProperty'
													value='${wf.warehouseproperty}' p='f' />
											</td>
											<td align="right">
												是否可用：
											</td>
											<td>
												<windrp:getname key='UseSign' value='${wf.useflag}' p='f' />
											</td>
										</tr>
										<tr>
											<td height="20" align="right">
												区域：
											</td>
											<td colspan="3">
												<windrp:getname key='countryarea' value='${wf.province}' p='d' />
												<windrp:getname key='countryarea' value='${wf.city}' p='d' />
												<windrp:getname key='countryarea' value='${wf.areas}' p='d' />
											</td>
											
										</tr>
										<tr>
											<td colspan="4"></td>
											<td align="right">
												是否负库存：
											</td>
											<td>
												<windrp:getname key="YesOrNo" value='${wf.isMinusStock}' p="f" />
											</td>
										</tr>
										<tr>
											<td height="20" align="right">
												仓库库存下限值：
											</td>
											<td>
												${wf.belowNumber}
											</td>
											<td height="20" align="right">
												仓库库存上限值：
											</td>
											<td colspan="3">
												${wf.highNumber}
											</td>
										</tr>
										<tr>
											<tr>
												<td height="20" align="right">
													仓库地址：
												</td>
												<td colspan="5">
													${wf.warehouseaddr}
												</td>
											</tr>
											<tr>
												<td height="20" align="right">
													备注：
												</td>
												<td colspan="5">
													${wf.remark}
												</td>
											</tr>
									</table>
								</fieldset>

								<fieldset align="center">
									<legend>
										<table width="80" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td>
													许可仓库用户
												</td>
											</tr>
										</table>
									</legend>
									<table class="sortable" width="100%" border="0" cellpadding="0"
										cellspacing="1">
										<tr align="center" class="title-top">
											<td width="11%">
												用户编号
											</td>
											<td width="89%">
												用户名称
											</td>
										</tr>
										<logic:iterate id="r" name="rvls">
											<tr class="table-back-colorbar">
												<td height="20">
													${r.userid}
												</td>
												<td>
													<windrp:getname key='users' value='${r.userid}' p='d' />
												</td>
											</tr>
										</logic:iterate>
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
