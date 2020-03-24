<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/sorttable.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
	</head>

	<body>
		<script language="javascript">
	var checkid = 0;
	function CheckedObj(obj) {

		for (i = 0; i < obj.parentNode.childNodes.length; i++) {
			obj.parentNode.childNodes[i].className = "table-back-colorbar";
		}

		obj.className = "event";
	}

	function Audit(id) {
		popWin("../ditch/auditOrganWithdrawAction.do?ID=" + id, 500, 250);
	}

	function CancelAudit(id) {
		popWin("../ditch/cancelAuditOrganWithdrawAction.do?ID=" + id, 500, 250);
	}
	function Ratify(id) {
		popWin("../ditch/toRatifyOrganWithdrawAction.do?ID=" + id, 1000, 700);
	}
	function CancelRatify(id) {
		popWin("../ditch/cancelRatifyOrganWithdrawAction.do?ID=" + id, 500, 250);
	}

	function Affirm(id) {
		popWin("../ditch/affirmOrganWithdrawAction.do?ID=" + id, 500, 250);
	}
	function CancelAffirm(id) {
		popWin("../ditch/cancelAffirmOrganWithdrawAction.do?ID=" + id, 500, 250);
	}

	function Receive(id) {
		popWin("../ditch/receiveOrganWithdrawAction.do?ID=" + id, 500, 250);
	}
	function CancelReceive(id) {
		popWin("../ditch/cancelReceiveOrganWithdrawAction.do?ID=" + id, 500,
				250);
	}
	function BlankOut(id) {
		if (window.confirm("您确认要作废编号为：" + id + " 的 订购申请吗？如果作废后将不能恢复!"))
			popWin("../ditch/blankOutOrganWithdrawAction.do?ID=" + id, 500, 250);
	}
	function CancelBlankOut(id) {
		popWin("../ditch/cancelBlankOutOrganWithdrawAction.do?ID=" + id, 500,
				250);
	}
	function prints(id) {
		popWin("../ditch/printOrganWithdrawAction.do?ID=" + id, 900, 600);
	}
</script>

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
								渠道退货详情
							</td>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td style="float: right; padding-right: 20px;">
											<ws:hasAuth operationName="/ditch/auditOrganWithdrawAction.do">
												<c:choose>
													<c:when test="${ama.isaudit==0}">
														<input type="button" name="audit" id="audit" value="单据确认"
															onclick="javascript:Audit('${ama.id}');" />
													</c:when>
													<c:when test="${ama.iscomplete == 0}">
														<input type="button" name="audit" id="audit" value="取消单据确认"
															onclick="javascript:CancelAudit('${ama.id}');" />
													</c:when>
												</c:choose>
											</ws:hasAuth>
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
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td width="11%" align="right">
									编号：
								</td>
								<td width="22%">
									${ama.id}
								</td>
								<td width="10%" align="right">
									退货机构：
								</td>
								<td width="23%">
									<windrp:getname key="organ" p="d" value="${ama.porganid}" />
								</td>
								<td width="9%" align="right">
									机构联系人：
								</td>
								<td width="25%">
									${ama.plinkman }
								</td>
							</tr>
							<tr>
								<td align="right">
									退货仓库：
								</td>
								<td>
									<windrp:getname key='warehouse' value='${ama.warehouseid}' p='d' />
								</td>
								<td align="right">
									总金额：
								</td>
								<td>
									${ama.totalsum }
								</td>
								<td align="right">
									联系电话：
								</td>
								<td>
									${ama.tel }
								</td>
							</tr>
							<tr>
								<td align="right">
									入货仓库：
								</td>
								<td colspan="5">
									<windrp:getname key='warehouse' value='${ama.inwarehouseid}' p='d' />
								</td>
							</tr>
							<tr>
								<td align="right">
									退货原因：
								</td>
								<td colspan="5">
									<windrp:getname key="ReturnReason" p="f" value="${ama.remark}" />
								</td>
							</tr>
							<tr>
								<td align="right">
									备注：
								</td>
								<td colspan="5">
									${ama.withdrawcause}
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
								<td width="11%" align="right">
									制单机构：
								</td>
								<td width="22%">
									<windrp:getname key='organ' value='${ama.makeorganid}' p='d' />
								</td>
								<td width="10%" align="right">
									制单部门：
								</td>
								<td>
									<windrp:getname key='dept' value='${ama.makedeptid}' p='d' />
								</td>
								<td align="right">
									制单人：
								</td>
								<td>
									<windrp:getname key='users' value='${ama.makeid}' p='d' />
								</td>
							</tr>
							<tr>
								<td align="right">
									制单日期：
								</td>
								<td>
									<windrp:dateformat value='${ama.makedate}' p='yyyy-MM-dd' />
								</td>
								<%--<td align="right">
									检货状态：
								</td>
								<td>
									<windrp:getname key="TakeStatus" p="f" value="${ama.takestatus}" />
								</td>
								--%>
								<td align="right">
									是否检货：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${ama.takestatus}' p='f' />
								</td>
								<td align="right">
									打印次数：
								</td>
								<td>
									${ama.printtimes}
								</td>
							</tr>
							<tr>
								<td width="11%" align="right">
									是否单据确认：
								</td>
								<td width="22%">
									<windrp:getname key='YesOrNo' value='${ama.isaudit}' p='f' />
								</td>
								<td width="10%" align="right">
									单据确认人：
								</td>
								<td width="23%">
									<windrp:getname key='users' value='${ama.auditid}' p='d' />
								</td>
								<td width="9%" align="right">
									单据确认日期：
								</td>
								<td width="25%">
									<windrp:dateformat value='${ama.auditdate}' p='yyyy-MM-dd  HH:mm:ss' />
								</td>
							</tr>
							<%--  
	  <tr>
	    <td  align="right">是否批准：</td>
	    <td><windrp:getname key='YesOrNo' value='${ama.isratify}' p='f'/></td>
	    <td align="right">批准人：</td>
	    <td><windrp:getname key='users' value='${ama.ratifyid}' p='d'/></td>
	    <td align="right">批准日期：</td>
	    <td><windrp:dateformat value='${ama.ratifydate}' p='yyyy-MM-dd  HH:mm:ss'/>
	    </td>
	    </tr>
	    
	     <tr>
	    <td  align="right">是否确认：</td>
	    <td><windrp:getname key='YesOrNo' value='${ama.isaffirm}' p='f'/></td>
	    <td align="right">确认人：</td>
	    <td><windrp:getname key='users' value='${ama.affirmid}' p='d'/></td>
	    <td align="right">确认日期：</td>
	    <td><windrp:dateformat value='${ama.affirmdate}' p='yyyy-MM-dd  HH:mm:ss'/>
	    </td>
	    </tr>
	    --%>
							<tr>
								<td align="right">
									是否签收：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${ama.iscomplete}' p='f' />
								</td>
								<td align="right">
									签收人：
								</td>
								<td>
									<windrp:getname key='users' value='${ama.receiveid}' p='d' />
								</td>
								<td align="right">
									签收日期：
								</td>
								<td>
									<windrp:dateformat value='${ama.receivedate}' p='yyyy-MM-dd  HH:mm:ss' />
								</td>
							</tr>
<%--						
							<tr>
								<td align="right">
									是否作废：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${ama.isblankout}' p='f' />
								</td>
								<td align="right">
									作废人：
								</td>
								<td>
									<windrp:getname key='users' value='${ama.blankoutid}' p='d' />
								</td>
								<td align="right">
									作废日期：
								</td>
								<td>
									<windrp:dateformat value='${ama.blankoutdate}' p='yyyy-MM-dd  HH:mm:ss' />
								</td>
							</tr>

							<tr>
								<td align="right">
									作废原因：
								</td>
								<td colspan="5">
									${ama.blankoutreason }
								</td>
							</tr>
						--%>
						</table>
			</fieldset>

			<fieldset align="center">
				<legend>
					<table width="110" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								渠道退货产品列表
							</td>
						</tr>
					</table>
				</legend>
				<table width="100%" border="0" cellpadding="0" cellspacing="1"
					class="sortable">
					<tr align="center" class="title-top">
						<td width="5%">
							产品编号
						</td>
						<td width="8%">
							物料号
						</td>
						<td width="10%">
							产品名称
						</td>
						<td width="10%">
							规格
						</td>
						<td width="6%">
							单位
						</td>
						<td width="6%">
							数量
						</td>
						<td width="6%">
									计量
						</td>
						
					</tr>
					<logic:iterate id="p" name="list">
						<tr class="table-back-colorbar" onClick=
	CheckedObj(this);;
>
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
							<td align="right">
								<windrp:format value="${p.quantity}" p="###,##0.0" />
							</td>
							<td align="right">
										<windrp:format value="${p.cUnitQuantity}" p="###,##0.0" />
										<windrp:getname key='CountUnit' value='${p.countunit}' p='d' />
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
