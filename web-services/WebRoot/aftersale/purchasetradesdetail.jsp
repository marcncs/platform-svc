<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
        <SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
		<script language="javascript">
	function Audit(piid) {
		showloading();
		popWin2("../aftersale/auditPurchaseTradesAction.do?id=" + piid);
	}

	function CancelAudit(piid) {
		showloading();
		popWin2("../aftersale/cancelAuditPurchaseTradesAction.do?id=" + piid);
	}

	function Endcase(piid) {
		showloading();
		popWin2("../aftersale/receivePurchaseTradesAction.do?id=" + piid);
	}

	function CancelEndcase(piid) {
		showloading();
		popWin2("../aftersale/cancelReceivePurchaseTradesAction.do?id=" + piid);
	}

	function print(piid) {
		popWin("../aftersale/purchaseTradesAction.do?ID=" + piid,900,600);
	}
	function toaddidcode(pidid, batch, piid) {
		popWin2("../aftersale/toAddPurchaseTradesIdcodeAction.do?pidid="
				+ pidid + "&batch=" + batch + "&piid=" + piid);
	}

	function blankout(wid) {
		if (window.confirm("您确认要作废该记录吗？如果作废将永远不能恢复!")) {
			popWin2("../aftersale/blankoutSaleRepairAction.do?id=" + wid);
		}
	}
	function toaddidcode(billid,bdid,wid,isaudit){
			popWin("../aftersale/toAddPurchaseTradesIdcodeiiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,650);
	}
	function toaddbit(billid,bdid,wid,isaudit){
			popWin("../aftersale/toAddPurchaseTradesIdcodeiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,600);
	}
</script>

	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td width="925">
								采购换货详情
							</td>
							<td align="right">
								<table width="180" border="0" cellpadding="0" cellspacing="0">
									<tr>

										<td width="60" align="center">
											<a href="javascript:print('${sof.id}');">打印</a>
										</td>
										<td width="60" align="center">
											<c:choose>
												<c:when test="${sof.isaudit==0}">
													<a href="javascript:Audit('${sof.id}');">复核</a>
												</c:when>
												<c:otherwise>
													<a href="javascript:CancelAudit('${sof.id}')">取消复核</a>
												</c:otherwise>
											</c:choose>
                                            </td>
                                            <td width="60" align="center">
											<c:choose>
												<c:when test="${sof.isreceive==0}">
													<a href="javascript:Endcase('${sof.id}');">回收</a>
												</c:when>
												<c:otherwise>
													<a href="javascript:CancelEndcase('${sof.id}')">取消回收</a>
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
						<td width="11%" align="right">
							供应商：
						</td>
						<td width="27%">
							${sof.providename}
						</td>
						<td width="10%" align="right">
							联系人：
						</td>
						<td width="20%">
							${sof.plinkman}
						</td>
						<td width="12%" align="right">
							联系电话：
						</td>
						<td width="20%">
							${sof.tel}
						</td>
					</tr>
					<tr>
						<td align="right">
							入货仓库：
						</td>
						<td>
							<windrp:getname key='warehouse' value='${sof.warehouseinid}' p='d'/>
						</td>
						<td align="right">
							出货仓库：
						</td>
						<td>
							<windrp:getname key='warehouse' value='${sof.warehouseoutid}' p='d'/>
						</td>
						<td align="right">
							预计取货日期：
						</td>
						<td><windrp:dateformat value='${sof.tradesdate}' p='yyyy-MM-dd'/></td>
					</tr>
					<tr>
						<td align="right">备注：</td>
						<td colspan="5">${sof.remark}					    </td>
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
						<td width="11%" align="right">制单机构：</td>
						<td width="26%"><windrp:getname key='organ' value='${sof.makeorganid}' p='d'/></td>
						<td width="9%" align="right">制单人：</td>
						<td width="21%"><windrp:getname key='users' value='${sof.makeid}' p='d'/></td>
						<td width="11%" align="right">制单日期：
							
						</td>
						<td width="22%">${sof.makedate} 
							
						</td>
					</tr>
					<tr>
						<td align="right">
							是否复核：
						</td>
						<td>
							<windrp:getname key="YesOrNo" value="${sof.isaudit}" p="f"/>
						</td>
						<td align="right">
							复核人：
						</td>
						<td>
							<windrp:getname key='users' value='${sof.auditid}' p='d'/> 
						</td>
						<td align="right">
							复核日期：
						</td>
						<td>
							${sof.auditdate}
						</td>
					</tr>
					<tr>
						<td align="right">
							是否回收：
						</td>
						<td>
							
							<windrp:getname key="YesOrNo" value="${sof.isreceive}" p="f"/>
						</td>
						<td align="right">
							回收人：
						</td>
						<td>
							
							<windrp:getname key='users' value='${sof.receiveid}' p='d'/> 
						</td>
						<td align="right">
							回收日期：
						</td>
						<td>
							${sof.receivedate}
						</td>
					</tr>
                    <tr>
								<td align="right">
									是否作废：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${sof.isblankout}' p='f' />
								</td>
								<td align="right">
									作废人：
								</td>
								<td>
									
									<windrp:getname key='users' value='${sof.blankoutid}' p='d' />
								</td>
								<td align="right">
									作废日期：
								</td>
								<td>
									${sof.blankoutdate}
								</td>
							</tr>
							<tr>
								<td align="right">
									作废原因：
								</td>
								<td colspan="5">
									${sof.blankoutreason}
								</td>
							</tr>
				</table>
			</fieldset>

			<fieldset align="center">
				<legend>
					<table width="110" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								换货物品清单列表
							</td>
						</tr>
					</table>
				</legend>
				<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
					<tr align="center" class="title-top">
						<td width="9%">
							产品编号
						</td>
						<td width="20%">
							产品名称
						</td>
						<td width="14%">
							规格
						</td>
						<td width="9%">
							单位
						</td>
						<td width="5%">
							序号
						</td>
						<td width="7%">
							单价
						</td>
						<td width="8%">
							数量
						</td>
						<td width="9%">
							检货数量
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
								
								<windrp:getname key="CountUnit" p="d" value="${p.unitid}"/>
							</td>
							<td><windrp:idcodebit value="${p.productid}" idcodeclick="toaddidcode('${sof.id}','${p.id}','${sof.warehouseinid}','${sof.isreceive}')" bitclick="toaddbit('${sof.id}','${p.id}','${sof.warehouseinid}','${sof.isreceive}')"/></td>
							<td>
								${p.unitprice}
							</td>
							<td>
								${p.quantity}
							</td>
							<td>
								${p.takequantity}
							</td>
							<td>
								${p.subsum}
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
