<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/showCQ.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/jquery.js">
</SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
		<script language="javascript">
	function Receive(smid) {
		showloading();
		window
				.open(
						"../warehouse/completeStockMoveReceiveAction.do?SMID="
								+ smid,
						"newwindow",
						"height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}

	function CancelReceive(smid) {
		window
				.open(
						"../warehouse/cancelCompleteStockMoveReceiveAction.do?SMID="
								+ smid,
						"newwindow",
						"height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}

	function toaddidcode(billid, bdid, wid, isaudit) {
		popWin("../warehouse/toAddStockMoveIdcodeiiAction.do?billid=" + billid
				+ "&bdid=" + bdid + "&wid=" + wid + "&isaudit=" + isaudit,
				1000, 650);
	}
	function toaddbit(billid, bdid, wid, isaudit) {
		popWin("../warehouse/toAddStockMoveIdcodeiAction.do?billid=" + billid
				+ "&bdid=" + bdid + "&wid=" + wid + "&isaudit=" + isaudit,
				1000, 600);
	}
	function prints(id) {
		popWin("../warehouse/printStockMoveReceiveAction.do?ID=" + id, 900, 600);
	}

	//验证条码数量与订单数量是否一致
	function toQuantityCheck(id) {
		$.post("../warehouse/ajaxQuantityCompleteStockMoveAction.do?id=" + id,
				function(result) {
					var data = eval('(' + result + ')');
					if (data.state == "1") {
						var con = confirm("条码数量与转仓数量不符，是否签收？");
						if (con == true) {
							Receive(data.id);
						}
						if (con == false) {
							return;
						}
					} else {
						var con = confirm("是否签收？");
						if (con == true) {
							Receive(data.id);
						}
						if (con == false) {
							return;
						}
					}
				});
	}
</script>
	</head>

	<body>


		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
						class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td>
								转仓单详情
							</td>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<ws:hasAuth operationName="/warehouse/printStockMoveReceiveAction.do">
											<td width="60" align="center">
											<input type="button" name="prints" id="prints" value="打印"
													onclick="javascript:prints('${smf.id}');" />
												<%--<a href="javascript:prints('${smf.id}');">打印</a>--%>
											</td>
										</ws:hasAuth>
										<ws:hasAuth
											operationName="/warehouse/completeStockMoveReceiveAction.do">
											<td width="80" align="center">
												<c:choose>
													<c:when test="${smf.iscomplete==0}">
													<input type="button" name="toquantitycheck" id="toquantitycheck" value="签收"
													onclick="javascript:toQuantityCheck('${smf.id}');" />
														<!--<a href="javascript:toQuantityCheck('${smf.id}')">签收</a>-->
													</c:when>
													<%--<c:otherwise><a href="javascript:CancelReceive('${smf.id}')">取消签收</a></c:otherwise>--%>
												</c:choose>
											</td>
										</ws:hasAuth>
									</tr>
								</table>
							</td>
						</tr>
					</table>


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
									转仓日期：
								</td>
								<td width="25%">
									<windrp:dateformat value='${smf.movedate}' p='yyyy-MM-dd' />
								</td>
								<td width="9%" align="right">
									转出机构：
								</td>
								<td width="23%">
									<windrp:getname key='organ' value='${smf.outorganid}' p='d' />
								</td>
								<td width="9%" align="right">
									转出仓库：
								</td>
								<td width="25%">
									<windrp:getname key='warehouse' value='${smf.outwarehouseid}' p='d' />
								</td>
							</tr>
							<tr>
								<td align="right">
									转入机构：
								</td>
								<td>
									<windrp:getname key='organ' value='${smf.inorganid}' p='d' />
								</td>
								<td align="right">
									转入仓库：
								</td>
								<td>
									<windrp:getname key='warehouse' value='${smf.inwarehouseid}' p='d' />
								</td>
								<td align="right">
									
								</td>
								<td>
								</td>
							</tr>
							<tr>
								<td align="right">
									转仓原因：
								</td>
								<td colspan="5">
									${smf.movecause}
								</td>
							</tr>
							<tr>
								<td align="right">
									备注：
								</td>
								<td colspan="5">
									${smf.remark}
								</td>
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
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td width="9%" align="right">
									制单人：
								</td>
								<td width="25%">
									<windrp:getname key='users' value='${smf.makeid}' p='d' />
								</td>
								<td width="9%" align="right">
									制单日期：
								</td>
								<td width="23%">
									<windrp:dateformat value='${smf.makedate}' p='yyyy-MM-dd' />
								</td>
								<td width="9%" align="right">
									是否检货：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${smf.takestatus}' p='f' />
								</td>

							</tr>
							<tr>
								<td width="9%" align="right">
									是否复核：
								</td>
								<td width="25%">
									<windrp:getname key='YesOrNo' value='${smf.isaudit}' p='f' />
								</td>
								<td align="right">
									复核人：
								</td>
								<td>
									<windrp:getname key='users' value='${smf.auditid}' p='d' />
								</td>
								<td align="right">
									复核日期：
								</td>
								<td>
									${smf.auditdate}
								</td>

							</tr>
							<tr>
								<td align="right">
									是否发货：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${smf.isshipment}' p='f' />
								</td>
								<td align="right">
									发货人：
								</td>
								<td>
									<windrp:getname key='users' value='${smf.shipmentid}' p='d' />
								</td>
								<td align="right">
									发货日期：
								</td>
								<td>
									${smf.shipmentdate}
								</td>

							</tr>
							<tr>
								<td align="right">
									是否签收：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${smf.iscomplete}' p='f' />
								</td>
								<td align="right">
									签收人：
								</td>
								<td>
									<windrp:getname key='users' value='${smf.receiveid}' p='d' />
								</td>
								<td align="right">
									签收日期：
								</td>
								<td>
									${smf.receivedate}
								</td>

							</tr>
<%--							
							<tr>
								<td align="right">
									是否作废：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${smf.isblankout}' p='f' />
								</td>
								<td align="right">
									作废人：
								</td>
								<td>
									<windrp:getname key='users' value='${smf.blankoutid}' p='d' />
								</td>
								<td align="right">
									作废日期：
								</td>
								<td>
									${smf.blankoutdate}
								</td>
							</tr>
							<tr>
								<td align="right">
									作废原因：
								</td>
								<td colspan="5">
									${smf.blankoutreason}
								</td>
							</tr>
							--%>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="90" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										转仓单产品列表
									</td>
								</tr>
							</table>
						</legend>

						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							<tr align="center" class="title-top">
								<td width="12%">
									产品编号
								</td>
								<td width="12%">
									物料号
								</td>
								<td width="26%">
									产品名称
								</td>
								<td width="10%">
									规格
								</td>
								<td width="5%">
									单位
								</td>
								<td width="5%">
									序号
								</td>
								<td width="7%">
									计划数量
								</td>
								<td width="8%">
									转仓数量
								</td>
								<td width="8%">
									签收数量
								</td>
							</tr>
							<logic:iterate id="p" name="als">
								<tr class="table-back-colorbar">
									<td>
										${p.productid}
									</td>
									<td>
										${p.nccode}
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
									<td><span onmouseover="ShowCQ(this,'${p.productid}','${smid}','2');"><windrp:idcodebit value="${p.productid}" idcodeclick="toaddidcode('${smf.id}','${p.id}','${smf.inwarehouseid}','${smf.iscomplete}')" bitclick="toaddbit('${smf.id}','${p.id}','${smf.inwarehouseid}','${smf.iscomplete}')"/></span></td>
									<td>
										<windrp:format value="${p.quantity}" p="###,##0.00" />
									</td>
									<td>
										<windrp:format value="${p.takequantity}" p="###,##0.00" />
									</td>
									<td>
										<c:if test="${smf.iscomplete==1}">
											<windrp:format value="${p.takequantity}" p="###,##0.00" />
										</c:if>
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
