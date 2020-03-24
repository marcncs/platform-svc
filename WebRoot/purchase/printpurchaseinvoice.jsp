<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
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
				采购发票
			</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr >
					<td align="right">供应商：</td>
					<td>${ProvideName}</td>
					<td align="right">发票编号：</td>
					<td>${InvoiceCode}</td>
					<td align="right">发票类型：</td>
					<td>
						<windrp:getname key="InvoiceType" value="${InvoiceType}" p="f" />
					</td>
				</tr>
				<tr >
					<td align="right">制单机构：</td>
					<td>${oname}</td>
					<td align="right">制单部门：</td>
					<td>${deptname}</td>
					<td align="right">制单人：</td>
					<td>${uname}</td>
				</tr>
				<tr >
					<td align="right">
						是否复核：
					</td>
					<td>
						<windrp:getname key="YesOrNo" value="${IsAudit}" p="f" />
					</td>
					<td align="right">
						开票日期：
					</td>
					<td>${BeginDate}-${EndDate}</td>
					<td align="right">&nbsp;
					</td>
					<td class="SeparatePage">
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
					<td>
						编号
					</td>
					<td>
						发票编号
					</td>
					<td>
						供应商
					</td>
					<td>
						发票类型
					</td>
					<td>
						制票日期
					</td>
					<td>
						开票日期
					</td>
					<td width="70">
						是否复核
					</td>
					<td>
						制单人
					</td>
					<td>
						制单机构
					</td>

				</tr>
				<logic:iterate id="pl" name="alpl">
					<tr class="table-back-colorbar"
						onClick="CheckedObj(this,${pl.id});">
						<td>
							${pl.id}
						</td>
						<td>
							${pl.invoicecode}
						</td>
						<td>
							${pl.provideidname}
						</td>
						<td>

							<windrp:getname key='InvoiceType' value='${pl.invoicetype}' p='f' />
						</td>
						<td>
							<windrp:dateformat value="${pl.makeinvoicedate}" p="yyyy-MM-dd" />

						</td>
						<td>
							<windrp:dateformat value="${pl.invoicedate}" p="yyyy-MM-dd" />

						</td>
						<td>
							<windrp:getname key='YesOrNo' value='${pl.isaudit}' p='f' />
						</td>
						<td>
							<windrp:getname key='users' value='${pl.makeid}' p='d' />
						</td>
						<td>
							<windrp:getname key='organ' value='${pl.makeorganid}' p='d' />
						</td>

					</tr>
				</logic:iterate>
			</table>
		</div>
	</body>
</html>
