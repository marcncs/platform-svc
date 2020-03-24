<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/sorttable.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js">
	
</SCRIPT>
		<SCRIPT language="javascript" src="../js/showCQ.js"></SCRIPT>
		<title>WINDRP-分销系统</title>
		<script language="javascript">
	function Audit(osid) {
		if (confirm("是否将库存信息 按本次盘库的数据为准? 未盘点到的产品库存将被清空,按无库存处理!")) {
			popWin2("../warehouse/auditBarcodeInventoryAction.do?ID=" + osid);
		}
	}

	function CancelAudit(osid) {
		showloading();
		popWin2("../warehouse/cancelAuditBarcodeInventoryAction.do?ID=" + osid);
	}

	function Endcase(osid) {
		showloading();
		popWin2("../machin/endcaseOtherShipmentBillAction.do?id=" + osid);
	}

	function CancelEndcase(osid) {
		showloading();
		popWin2("../machin/cancelEndcaseOtherShipmentBillAction.do?id=" + osid);
	}

	function Ratify(osid) {
		window
				.open(
						"../machin/ratifyOtherShipmentBillAction.do?OSID="
								+ osid,
						"newwindow",
						"height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}

	function CancelRatify(osid) {
		window
				.open(
						"../machin/cancelRatifyOtherShipmentBillAction.do?OSID="
								+ osid,
						"newwindow",
						"height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}

	function OtherShipmentBill(ssid) {
		window
				.open(
						"../machin/otherShipmentBillAction.do?ID=" + ssid,
						"newwindow",
						"height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}

	function BlankOut(osid) {
		if (window.confirm("您确认要作废所选的记录吗？如果作废将永远不能恢复!")) {
			window
					.open(
							"../machin/blankOutOtherShipmentBillAction.do?id="
									+ osid,
							"newwindow",
							"height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
	}

	function PrintOtherShipmentBill(id) {
		popWin4("../machin/printOtherShipmentBillAction.do?ID=" + id, 900, 600,
				"print");
	}
	function toaddbit(billid, bdid, wid, isaudit) {
		popWin("../machin/toAddOtherShipmentBillIdcodeiAction.do?billid="
				+ billid + "&bdid=" + bdid + "&wid=" + wid + "&isaudit="
				+ isaudit, 1000, 600);
	}
	function toaddidcode(billid, bdid, wid, isaudit, batch) {
		popWin("../warehouse/toAddBarcodeAction.do?billid=" + billid + "&bdid="
				+ bdid + "&wid=" + wid + "&isaudit=" + isaudit + "&batch="
				+ batch, 1000, 650);
	}
	function changeValue() {
		if (!Validator.Validate(document.listform, 2)) {
			return false;
		}
		var different = 0.00;
		var stockpile = document.listform.stockpile;
		var realstock = document.listform.realstock;
		var resultstock = document.listform.resultstock;
		if (stockpile.length) {
			for ( var m = 0; m < stockpile.length; m++) {
				resultstock[m].value = realstock[m].value - stockpile[m].value;
			}
		} else {
			resultstock.value = realstock.value - stockpile.value;
		}
	}
</script>
	</head>

	<body>
		<SCRIPT language=javascript>
	
</SCRIPT>
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
							<td width="967">
								条码盘点详情
							</td>
							<td width="266" align="right">
								<table width="120" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="60" align="center">
											<input type="button" name="printothershipmentbill"
												id="printothershipmentbill" value="打印"
												onclick="javascript:PrintOtherShipmentBill('${osbf.id}');" />
											&nbsp;
											<%--<a href="javascript:PrintOtherShipmentBill('${osbf.id}');">打印</a>--%>
										</td>
										<ws:hasAuth operationName="/warehouse/auditBarcodeInventoryAction.do">
											<td width="60" align="center">
												<c:choose>
													<c:when test="${osbf.isapprove==1&&osbf.isaudit==0}">
														<input type="button" name="audit" id="audit" value="单据确认"
															onclick="javascript:Audit('${osbf.id}');" />
														<%--<a href="javascript:Audit('${osbf.id}');">单据确认</a>--%>
													</c:when>
													<c:otherwise>
														<%--<input type="button" name="cancelaudit" id="cancelaudit" value="取消单据确认" onclick="javascript:CancelAudit('${osbf.id}');"/>--%>
														<%--<a href="javascript:CancelAudit('${osbf.id}')">取消单据确认</a>--%>
													</c:otherwise>
												</c:choose>
											</td>
										</ws:hasAuth>
										<!--<td width="60" align="center"><c:choose>
                  <c:when test="${osbf.isendcase==0}"><a href="javascript:Endcase('${osbf.id}');">结案</a></c:when>
                  <c:otherwise> <a href="javascript:CancelEndcase('${osbf.id}')">取消结案</a></c:otherwise>
              </c:choose></td>-->

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
									盘点仓库：
								</td>
								<td width="20%">
									<windrp:getname key='warehouse' value='${osbf.warehouseid}' p='d' />
								</td>
								<%--<td width="9%" align="right">
									盘点日期：
								</td>
								<td width="10%">
									${osbf.requiredate}
								</td>
								<td width="12%" align="right">
									备注：
								</td>
								<td width="30%">
									${osbf.remark}
								</td>
							
								<td width="9%" align="right">
									备注：
								</td>
								<td width="10%">
									${osbf.remark}
								</td>
								--%>
								<td width="14%" align="right">
								</td>
								<td width="10%">
								</td>
								<td width="12%" align="right"></td>
								<td width="30%" align="right"></td>
							</tr>
							<tr>
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
								<td width="10%" align="right">
									制单机构：
								</td>
								<td width="25%">
									<windrp:getname key='organ' value='${osbf.makeorganid}' p='d' />
								</td>
								<td width="8%" align="right">
									制单人：
								</td>
								<td width="23%">
									<windrp:getname key='users' value='${osbf.makeid}' p='d' />
								</td>
								<td width="9%" align="right">
									制单日期：
								</td>
								<td width="25%">
									${osbf.makedate}
								</td>
							</tr>
							<tr>
								<td align="right">
									是否单据确认：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${osbf.isaudit}' p='f' />
								</td>
								<td align="right">
									单据确认人：
								</td>
								<td>
									<windrp:getname key='users' value='${osbf.auditid}' p='d' />
								</td>
								<td align="right">
									单据确认日期：
								</td>
								<td>
									${osbf.auditdate}
								</td>
							</tr>
							<tr>
								<td align="right">
									审核不通过原因：
								</td>
								<td colspan="5">
									${osbf.remark}
								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="110" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										条码盘点单产品列表
									</td>
								</tr>
							</table>
						</legend>
						<form id="transfor" method="post" action="" name="transfor">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">
								<tr align="center" class="title-top">
									<td>
										物料号
									</td>
									<td>
										产品名称
									</td>
									<td>
										规格
									</td>
									<td>
										单位
									</td>
									<td>
										批号
									</td>
									<td>
										序号
									</td>
									<td>
										货物数量
									</td>
									<td>
										盘点数量
									</td>
									<td>
										数量差异
									</td>
								</tr>
								<logic:iterate id="p" name="als">
									<tr class="table-back-colorbar">
										<td>
											${p.nccode}
										</td>
										<td>
											${p.psproductname}
										</td>
										<td>
											${p.specmode}
										</td>
										<td>
											<windrp:getname key='CountUnit' value='${p.countunit}' p='d' />
										</td>
										<td>
											${p.batch}
										</td>
										<td>
											<span
												onmouseover="ShowBCQ(this,'${p.productid}','${osbf.id}','${p.batch}');"><a
												href="javascript:toaddidcode('${osbf.id}','${p.productid}','${osbf.warehouseid}','${osbf.isaudit}','${p.batch}')"><img
														src="../images/CN/code.gif" border="0" title="录入条码">
											</a> </span>
										</td>
										<td>
											<windrp:format value='${p.allstockpile}' />
										</td>
										<td>
											${p.stockpile}
										</td>
										<td>
											${p.squantity}
										</td>
									</tr>
								</logic:iterate>
							</table>
						</form>
					</fieldset>
				</td>
			</tr>
		</table>
	</body>
</html>
