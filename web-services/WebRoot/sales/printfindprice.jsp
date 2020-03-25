<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<link href="../css/pss.css" rel="stylesheet" type="text/css">
		<style media=print>
			.Noprint {
				display: none;
			}
		</style>
	</head>
	<body style="overflow: auto;">
		<center class="Noprint">
			<div class="printstyle">
				<img src="../images/print.gif" onClick="javascript:window.print();"></img>
			</div>
			<hr align="center" width="100%" size="1" noshade>
		</center>
		<div id="abc">
			<div align="center" class="print_title_style">
				零售查价
			</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="13%" align="right">
						产品类别：
					</td>
					<td width="20%">
						${productstruts}
					</td>
					<td width="12%" align="right">
						价格范围：
					</td>
					<td width="20%">
						${BeginPrice}-${EndPrice}
					</td>
					<td width="11%" align="right">
						送货机构：
					</td>
					<td width="21%">
						${oname}
					</td>
				</tr>
				<tr>
					<td align="right">
						关键字：
					</td>
					<td>
						${KeyWord}
					</td>
					<td align="right">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td align="right">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="13%" align="right">
						打印机构：
					</td>
					<td width="21%">
						${porganname}
					</td>
					<td width="10%" align="right">
						打印人：
					</td>
					<td width="23%">
						${pusername}
					</td>
					<td width="9%" align="right">
						打印时间：
					</td>
					<td width="24%">
						${ptime}
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="1"
				class="sortable">
				<tr align="center" class="title-top">
					<td width="12%">
						产品编号
					</td>
					<td>
						产品名称
					</td>
					<td>
						规格
					</td>
					<td width="4%">
						单位
					</td>
					<td width="14%">
						非会员价
					</td>
					<td width="14%">
						会员价
					</td>
				</tr>
				<logic:iterate id="p" name="sls">
					<tr class="table-back-colorbar"
						onClick="CheckedObj(this,'${p.id}');">
						<td>
							${p.id}
						</td>
						<td>
							${p.productname}
						</td>
						<td>
							${p.specmode}
						</td>
						<td>
							${p.countunitname}
						</td>
						<td align="right">
							<fmt:formatNumber value="${p.pricei}" pattern="0.00" />
						</td>
						<td align="right">
							<fmt:formatNumber value="${p.priceii}" pattern="0.00" />
						</td>
					</tr>
				</logic:iterate>
			</table>
		</div>
	</body>
</html>
