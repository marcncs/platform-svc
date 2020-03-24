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
		popWin("../aftersale/auditPurchaseWithdrawAction.do?id="+ piid,500,250);
	}

	function CancelAudit(piid) {
		popWin("../aftersale/cancelAuditPurchaseWithdrawAction.do?id="+ piid,500,250);
	}

	function Endcase(piid) {
		popWin("../aftersale/endcasePurchaseWithdrawAction.do?id="+ piid,500,250);
	}

	function CancelEndcase(piid) {
		popWin("../aftersale/cancelEndcasePurchaseWithdrawAction.do?id="
								+ piid,500,250);
	}

	function print(piid) {
		popWin("../aftersale/purchaseWithdrawAction.do?ID=" + piid,900,600);
	}
	function toaddidcode(pidid, batch, piid) {
		popWin("../aftersale/toAddWithdrawIdcodeAction.do?pidid="+ pidid + "&batch=" + batch + "&piid=" + piid,500,250);
	}

	function blankout(wid) {
		if (window.confirm("您确认要作废该记录吗？如果作废将永远不能恢复!")) {
			popWin("../aftersale/blankoutWithdrawAction.do?id=" + wid,500,250);
		}
	}
</script>

	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td width="925">
								采购退货详情
							</td>
							<td width="120" align="right">
								<table width="120" border="0" cellpadding="0" cellspacing="0">
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
									供应商名称：
								</td>
								<td width="26%">
									${sof.pname}
								</td>
								<td width="8%" align="right">
									联系人：
								</td>
								<td width="23%">
									${sof.plinkman}
								</td>
								<td width="10%" align="right">
									联系电话：
								</td>
								<td width="22%">
									${sof.tel}
								</td>
							</tr>
							<tr>
								<td align="right">
									出货仓库：
								</td>
								<td>
								<windrp:getname key='warehouse' value='${sof.warehouseid}' p='d'/>
								</td>
								<td align="right">
									总金额：
								</td>
								<td>
									${sof.totalsum}
								</td>
								<td align="right"></td>
								<td></td>
							</tr>
							<tr>
								<td align="right">
									退货原因：
								</td>
								<td colspan="5">
									${sof.withdrawcause}
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
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
							<tr>
								<td width="11%" align="right">
									制单机构：
								</td>
								<td width="26%">
									<windrp:getname key='organ' value='${sof.makeorganid}' p='d' />
								</td>
								<td width="8%" align="right">
									制单人：
								</td>
								<td width="23%">
									<windrp:getname key='users' value='${sof.makeid}' p='d' />
								</td>
								<td width="10%" align="right">
									制单日期：
								</td>
								<td width="22%">
									${sof.makedate}
								</td>
							</tr>
							<tr>
								<td align="right">
									是否复核：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${sof.isaudit}' p='f' />
								</td>
								<td align="right">
									复核人：
								</td>
								<td>
									<windrp:getname key='users' value='${sof.auditid}' p='d' />
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
									是否结案：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${sof.isendcase}' p='f' />
								</td>
								<td align="right">
									结案人：
								</td>
								<td>
									
									<windrp:getname key='users' value='${sof.endcaseid}' p='d' />
								</td>
								<td align="right">
									结案日期：
								</td>
								<td>
									${sof.endcasedate}
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
										退货物品清单列表
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
								<!--<td width="4%">序号</td>-->
								<td width="8%">
									单价
								</td>
								<td width="8%">
									数量
								</td>
								<td width="8%">
									检货数量
								</td>
								<td width="12%">
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
									<td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/>
									</td>
									<!--     <td><a href="javascript:toaddidcode(${p.id},'${p.batch}','${sof.id}')"><img src="../images/CN/record.gif" width="19"  border="0" title="录入序号"></a></td>-->
									<td align="right">
										<windrp:format value="${p.unitprice}" p="###,##0.00"/>
									</td>
									<td align="right">
										<windrp:format value="${p.quantity}" p="###,##0.00"/>
									</td>
									<td align="right">
										<windrp:format value="${p.takequantity}" p="###,##0.00"/>
									</td>
									<td align="right">
										<windrp:format value="${p.subsum}" p="###,##0.00"/>
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
