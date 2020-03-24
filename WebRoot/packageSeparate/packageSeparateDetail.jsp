<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/function.js">
	
</SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js">
	
</SCRIPT>
		<title>WINDRP-分销系统</title>
		<script language="javascript">
	function Audit(oiid) {
		showloading();
		popWin2("../warehouse/auditPackSeparateAction.do?ID=" + oiid);
	}

	function CancelAudit(oiid) {
		showloading();
		popWin2("../machin/cancelAuditOtherIncomeAction.do?ID=" + oiid);
	}

	function OtherIncome(oiid) {
		window
				.open(
						"../machin/otherIncomeAction.do?ID=" + oiid,
						"newwindow",
						"height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	function toaddidcode(pidid, batch, piid) {
		window
				.open(
						"../machin/toAddOtherIncomeIdcodeAction.do?pidid="
								+ pidid + "&batch=" + batch + "&piid=" + piid,
						"newwindow",
						"height=400,width=600,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function toaddidcode(billid, bdid, wid, isaudit) {
		popWin("../machin/toAddOtherIncomeIdcodeiiAction.do?billid=" + billid
				+ "&bdid=" + bdid + "&wid=" + wid + "&isaudit=" + isaudit,
				1000, 650);
	}
	function toaddbit(billid, bdid, wid, isaudit) {
		popWin("../machin/toAddOtherIncomeIdcodeiAction.do?billid=" + billid
				+ "&bdid=" + bdid + "&wid=" + wid + "&isaudit=" + isaudit,
				1000, 600);
	}
	function PrintOtherIncome(id) {
		popWin4("../machin/printOtherIncomeAction.do?ID=" + id, 900, 600,
				"print");
	}
	function checkPrice(oiid) {
		var unitprice = document.all("unitprice");
		var productname = document.all("productname");
		var iszero = false;
		var pname = "";
		if (unitprice.length) {
			for (i = 0; i < unitprice.length; i++) {
				if (parseFloat(unitprice[i].value) == 0) {
					iszero = true;
					pname = productname[i].value;
					break;
				}
			}
		} else {
			if (parseFloat(unitprice.value) == 0) {
				iszero = true;
				pname = productname.value;
			}
		}
		if (iszero) {
			if (window.confirm(pname + " 单价为0，您确认要单据确认吗？")) {
				Audit(oiid);
			}
		} else {
			Audit(oiid);
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
							<td width="982">
								${menu }详情
							</td>
							<td width="251" align="right">
								<table width="120" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<%--
										<td width="60" align="center">
											<input type="button" name="printotherincome" id="printotherincome"
												value="打印" onclick="javascript:PrintOtherIncome('${oif.id}');" />
											&nbsp;
											<a href="javascript:PrintOtherIncome('${oif.id}');">打印</a>
										</td>
										--%>
										<ws:hasAuth operationName="/warehouse/auditPackSeparateAction.do">
										<td width="60" align="center">
											<c:choose>
												<c:when test="${oif.isAudit==0}">
													<input type="button" name="audit" id="audit" value="单据确认"
														onclick="javascript:Audit('${oif.id}');" />
													<%--<a href="javascript:Audit('${oif.id}');">单据确认</a>--%>
												</c:when>
												<c:otherwise>
													<%--<input type="button" name="cancelaudit" id="cancelaudit" value="取消单据确认" onclick="javascript:CancelAudit('${oif.id}');"/>
													--%>
													<%--<a href="javascript:CancelAudit('${oif.id}')">取消单据确认</a>--%>
												</c:otherwise>
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
									入库仓库：
								</td>
								<td width="26%">
									<windrp:getname key='warehouse' value='${oif.warehouseId}' p='d' />
								</td>
								<td width="9%" align="right">
									分包日期：
								</td>
								<td width="22%">
									<windrp:dateformat value="${oif.billDate}" p="yyyy-MM-dd" />
								</td>
								<%--<td width="9%" align="right">
									相关单据：
								</td>
								<td width="25%">
									${oif.billno}
								</td>
							--%>
								<td width="9%" align="right">
								</td>
								<td width="25%">
								</td>
							</tr>
							<tr>
								<td align="right">
									备注：
								</td>
								<td colspan="5">
									${oif.remark}
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
									制单机构：
								</td>
								<td width="26%">
									<windrp:getname key='organ' value='${oif.makeOrganId}' p='d' />
								</td>
								<td width="9%" align="right">
									制单人：
								</td>
								<td width="22%">
									<windrp:getname key='users' value='${oif.makeId}' p='d' />
								</td>
								<td width="9%" align="right">
									制单日期：
								</td>
								<td width="25%">
									${oif.makeDate}
								</td>
							</tr>
							<tr>
								<td align="right">
									是否单据确认：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${oif.isAudit}' p='f' />
								</td>
								<td align="right">
									单据确认人：
								</td>
								<td>
									<windrp:getname key='users' value='${oif.auditId}' p='d' />
								</td>
								<td align="right">
									单据确认日期：
								</td>
								<td>
									${oif.auditDate}
								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="110" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										${menu }产品列表
									</td>
								</tr>
							</table>
						</legend>
						<form name="listform" method="post">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">

								<tr align="center" class="title-top">
									<td>
										产品
									</td>
									<td>
										分包前物料号
									</td>
									<td>
										分包前规格
									</td>
									<td>
										分包前批次
									</td>
									<td>
										分包后物料号
									</td>
									<td>
										分包后规格
									</td>
									<td>
										分包后批次
									</td>
									<td>
										分包前数量
									</td>
									<td>
										分包后数量
									</td>
									<td>
										损耗
									</td>
								</tr>
								<logic:iterate id="p" name="als">
									<tr class="table-back-colorbar">
										<td>
											${p.productName }
										</td>
										<td>
											${p.outMcode }
										</td>
										<td>
											${p.outSpecMode}
										</td>
										<td>
											${p.outBatch}
										</td>
										<td>
											${p.inMcode }
										</td>
										<td>
											${p.inSpecMode}
										</td>
										<td>
											${p.inBatch}
										</td>
										<td>
											<windrp:format value="${p.outQuantity}" p="###,##0.000" />
											<windrp:getname key='CountUnit' value='${p.outUnitId}' p='d' />
										</td>
										<td>
											<windrp:format value="${p.inQuantity}" p="###,##0.0" />
											<windrp:getname key='CountUnit' value='${p.inUnitId}' p='d' />
										</td>
										<%--<td><input type="hidden" name="unitprice" value="${p.unitprice}">${p.unitprice}</td>
										<td>
											<windrp:format value="${p.quantity}" p="###,##0.0" />
										</td>
										<td align="right">
										<windrp:format value="${p.cUnitQuantity}" p="###,##0.0" />
										${p.countUnitName}
										</td>
										--%>
										<td>
											<windrp:format value="${p.wastage}" p="###,##0.000" />
											<windrp:getname key='CountUnit' value='${p.outUnitId}' p='d' />
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
