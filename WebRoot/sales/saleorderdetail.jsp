<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
		<script language="javascript">
	function Audit(soid) {
		showloading();
		popWin2("../sales/auditSaleOrderAction.do?SOID=" + soid);
	}

	function CancelAudit(soid) {
		showloading();
		popWin2("../sales/cancelAuditSaleOrderAction.do?SOID=" + soid);	}

	function Endcase(soid) {
		popWin2("../sales/endcaseSaleOrderAction.do?id=" + soid);	}

	function CancelEndcase(soid) {
		PopWin2("../sales/cancelEndcaseSaleOrderAction.do?id=" + soid);	}

	function Audittwo(soid) {
		popWin2("../sales/audittwoSaleOrderAction.do?SOID=" + soid);	}

	function CancelAudittwo(soid) {
		popWin2("../sales/cancelAudittwoSaleOrderAction.do?SOID="+ soid);	}

	function SaleOrder(soid) {
		popWin("../sales/saleOrderAction.do?ID=" + soid,900,600);	}

	function blankout(soid) {
		if (window.confirm("您确认要作废该记录吗？如果作废将不能恢复!")) {
			popWin2("../sales/blankoutSaleOrderAction.do?id=" + soid);		}
	}
	function prints(id){
			popWin("../sales/printSaleOrderAction.do?ID="+id,900,600);
	}
</script>

	</head>
	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td width="958">
								零售单详情
							</td>
							<td width="275" align="right">
								<table width="120" border="0" cellpadding="0" cellspacing="0">
									<tr>
										 <td width="60" align="center"><a href="javascript:prints('${sof.id}');">打印</a></td>
										<td width="100" align="center">
											<c:choose>
												<c:when test="${sof.isaudit==0}">
													<a href="javascript:Audit('${sof.id}');">复核</a>
												</c:when>
												<c:otherwise>
													<a href="javascript:CancelAudit('${sof.id}')">取消复核</a>
												</c:otherwise>
											</c:choose>
										</td>
										
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<fieldset align="center">
						<legend>
							<table width="70" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										订货人信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
							<tr>
								<td width="9%" align="right">
									会员编号：
									<input name="cid" type="hidden" id="cid" value="${sof.cid}">
								</td>
								<td width="28%">
									${sof.cid}
								</td>
								<td width="11%" align="right">
									会员名称：								</td>
								<td width="21%">
									${sof.cname}								</td>
								<td width="11%" align="right">
									订货人：								</td>
								<td width="20%">
									${sof.decideman}								</td>
							</tr>
							<tr>
								<td align="right">
									会员手机：
								</td>
								<td>
									${sof.cmobile}
								</td>
								<td align="right">
									会员电话：
								</td>
								<td>
									${sof.decidemantel}
								</td>
								<td align="right">&nbsp;
									
								</td>
								<td>&nbsp;
									
								</td>
							</tr>
						</table>
					</fieldset>


					<fieldset align="center">
						<legend>
							<table width="60" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										收货人信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
							<tr>
								<td width="9%" align="right">
									收货人：
								</td>
								<td width="28%">
									${sof.receiveman}
								</td>
								<td width="11%" align="right">
									收货人手机：								</td>
								<td width="21%">
									${sof.receivemobile}								</td>
								<td width="11%" align="right">
									收货人电话：								</td>
								<td width="20%">
									${sof.receivetel}								</td>
							</tr>
							<tr>
								<td align="right">
									收货地址：
								</td>
								<td colspan="5">
									${sof.transportaddr}
								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0" class="table-detail">
								<tr>
									<td>
										其它信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
							<tr>
								<td width="9%" align="right">
									交货日期：								</td>
								<td width="28%">
									${sof.consignmentdate}								</td>
								<td width="11%" align="right">
									发运方式：								</td>
								<td width="18%">
									<windrp:getname key='TransportMode' value='${sof.transportmode}'
										p='d' />							  </td>
								<td width="14%" align="right">
									付款方式：								</td>
								<td width="20%">
								<windrp:getname key='paymentmode' value='${sof.paymentmode}' p='d'/>							  </td>
							</tr>
							<tr>
								<td align="right">
									开票信息：								</td>
								<td>
									<c:if test="${sof.invmsg>0}">
										<font color="red">
									</c:if>
									${invmsgname}								</td>
								<td align="right">
									发票抬头：								</td>
								<td>
									${sof.tickettitle}								</td>
								<td align="right">
									客户方单据编号：								</td>
								<td>
									${sof.customerbillid}								</td>
							</tr>
							<tr>
								<td align="right">
									送货机构：								</td>
								<td>
									<windrp:getname key='organ' value='${sof.equiporganid}' p='d' />								</td>
								<td align="right">
									制单机构：								</td>
								<td>
									<windrp:getname key='organ' value='${sof.makeorganid}' p='d' />								</td>
								<td align="right">制单人：</td>
								<td><windrp:getname key='users' value='${sof.makeid}' p='d' /></td>
							</tr>
							<tr>
								<td align="right">制单日期：</td>
								<td><windrp:dateformat value='${sof.makedate}' /></td>
								<td align="right">客户来源：</td>
								<td><windrp:getname key='Source' value='${sof.source}' p='d' /></td>
								<td align="right">
									总金额：								</td>
								<td>
									${sof.totalsum}								</td>
							</tr>
							<tr>
								<td align="right">
									备注：								</td>
								<td colspan="5">
									${sof.remark}								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										状态信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
							<tr>
								<td width="9%" align="right">是否复核：</td>
								<td width="28%"><windrp:getname key='YesOrNo' value='${sof.isaudit}' p='f' /></td>
								<td width="11%" align="right">复核人：</td>
								<td width="22%"><windrp:getname key='users' value='${sof.auditid}' p='d' /></td>
								<td width="10%" align="right">
									复核日期：								</td>
								<td width="20%">
									${sof.auditdate}								</td>
							</tr>
							<tr>
								<td align="right">是否结案：</td>
								<td><windrp:getname key='YesOrNo' value='${sof.isendcase}' p='f' /></td>
								<td align="right">结案人：</td>
								<td><windrp:getname key='users' value='${sof.endcaseid}' p='d' /></td>
								<td align="right">
									结案日期：								</td>
								<td>
									${sof.endcasedate}								</td>
							</tr>
							<tr>
								<td align="right">是否作废：</td>
								<td><windrp:getname key='YesOrNo' value='${sof.isblankout}' p='f' /></td>
								<td align="right">作废人：</td>
								<td><windrp:getname key='users' value='${sof.blankoutid}' p='d' /></td>
								<td align="right">作废日期：</td>
								<td>${sof.blankoutdate}</td>
							</tr>
							<tr>
								<td align="right">作废原因：</td>
								<td colspan="5">${sof.blankoutreason} </td>
							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="100" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										零售单产品列表
									</td>
								</tr>
							</table>
						</legend>
						<table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr align="center" class="title-top">
								<td width="10%">
									产品编号								</td>
								<td width="15%">
									产品名称
								</td>
								<td width="14%">
									规格								</td>
								<td width="4%">
									单位								</td>
								<td width="10%">
									出货仓库								</td>
								<!--  <td width="8%">批次</td>-->
								<td width="6%">
									单价								</td>
								<td width="10%">
									税后单价								</td>
								<td width="5%">
									数量								</td>
								<td width="7%">
									检货数量								</td>
								<td width="6%">
									折扣率								</td>
								<td width="6%">
									税率
								</td>
								<td width="7%">
									金额
								</td>
							</tr>
							<logic:iterate id="p" name="als">
								<tr class="table-back-colorbar">
									<td>
										${p.productid}
									</td>
									<td>
										${p.productname}
									</td>
									<td>
										${p.specmode}
									</td>
									<td>
										<windrp:getname key='CountUnit' value='${p.unitid}' p='d' />
									</td>
									<td>
										<windrp:getname key='warehouse' value='${p.warehouseid}' p='d' />
									</td>
									<!-- <td></td>-->
									<td align="right">
										<windrp:format value='${p.unitprice}' p="###,##0.00" />
									</td>
									<td align="right">
										<c:if test="${p.unitprice!=p.taxunitprice}">
											<font color="red">
										</c:if>
										<windrp:format value='${p.taxunitprice}' p="###,##0.00"/>
									</td>
									<td align="center">
										<windrp:format value='${p.quantity}' />
									</td>
									<td align="center">
										<windrp:format value='${p.takequantity}' />
									</td>
									<td align="right">
										<windrp:format value='${p.discount}' />%
									</td>
									<td align="right">
										<windrp:format value='${p.taxrate}' />%
									</td>
									<td align="right">
										<windrp:format value='${p.subsum}' p="###,##0.00"/>
									</td>
								</tr>
							</logic:iterate>
						</table>
					</fieldset>

				</td>
			</tr>
		</table>
	</body>
</html>
