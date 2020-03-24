<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/sorttable.js"></script>
		<SCRIPT language="javascript" src="../js/prototype.js">
	
</SCRIPT>
		<SCRIPT language="javascript" src="../js/showSQ.js">
	
</SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
		<script language="javascript">
	function CheckedObj(obj) {

		for (i = 0; i < obj.parentNode.childNodes.length; i++) {
			obj.parentNode.childNodes[i].className = "table-back-colorbar";
		}

		obj.className = "event";
	}
	function Audit(smid) {
		popWin2("../warehouse/auditMoveApplyAction.do?AAID=" + smid);
	}

	function CancelAudit(smid) {
		popWin2("../warehouse/cancelAuditMoveApplyAction.do?AAID=" + smid);
	}

	function prints(id) {
		popWin("../warehouse/printMoveApplyAction.do?ID=" + id, 900, 600);
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
							<td>
								机构转仓申请单详情
							</td>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="60" align="center">
											<input type="button" name="prints" id="prints" value="打印"
												onclick="javascript:prints('${smf.id}');" />
											<%--<a href="javascript:prints('${smf.id}');">打印</a>--%>

										</td>
										<td style="float: right; padding-right: 20px;">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

					<fieldset align="center">
						<legend>
							<table border="0" cellpadding="0" cellspacing="0"
								class="table-detail">
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
									机构转仓日期：
								</td>
								<td width="25%">
									<windrp:dateformat value='${smf.movedate}' p='yyyy-MM-dd' />
								</td>
								<td width="9%" align="right">
									调出(制单)机构：
								</td>
								<td width="21%">
									<windrp:getname key='organ' value='${smf.outorganid}' p='d' />
								</td>
								<td width="13%" align="right">
									调出仓库：
								</td>
								<td width="23%">
									<windrp:getname key='warehouse' value='${smf.outwarehouseid}'
										p='d' />
								</td>
							</tr>
							<tr>
								<td width="13%" align="right">
									调入机构：
								</td>
								<td width="23%">
									<windrp:getname key='organ' value='${smf.inorganid}' p='d' />
								</td>
								<td align="right">
									调入仓库：
								</td>
								<td>
									<windrp:getname key='warehouse' value='${smf.inwarehouseid}'
										p='d' />
								</td>
								<td align="right">
									联系人：
								</td>
								<td>
									${smf.olinkman}
								</td>
							</tr>
							<tr>

								<td align="right">
									联系电话：
								</td>
								<td>
									${smf.otel }
								</td>
								<td align="right">
									收货地址：
								</td>
								<td>
									${smf.transportaddr}
								</td>
								<td align="right">
									类型：
								</td>
								<td>
									<windrp:getname key="MoveType" p="f" value="${smf.moveType}"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									机构转仓原因：
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
							<tr>
								<td align="right">
									拒绝原因：
								</td>
								<td colspan="5">
									${smf.blankoutreason}
								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0"
								class="table-detail">
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
								<td width="20%">
									<windrp:getname key='users' value='${smf.makeid}' p='d' />
								</td>
								<td width="9%" align="right">
									制单日期：
								</td>
								<td width="20%">
									<windrp:dateformat value='${smf.makedate}' p='yyyy-MM-dd' />
								</td>
								<td width="9%" align="right">当前审批人：</td>
								<td width="33%">${approvers}</td>
							</tr> 
							<tr>
								<td align="right">
									是否批准：
								</td>
								<td>
									<windrp:getname key='ConfirmStatus' value='${smf.isratify}' p='f' />
								</td>
								<td align="right">
									批准人：
								</td>
								<td>
									<windrp:getname key='users' value='${smf.ratifyid}' p='d' />
								</td>
								<td align="right">
									批准日期：
								</td>
								<td>
									<windrp:dateformat value='${smf.ratifydate}' p='yyyy-MM-dd' />
								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="150" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="150">
										机构转仓申请单产品列表
									</td>
								</tr>
							</table>
						</legend>

						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top">
								<td width="11%">
									产品编号
								</td>
								<td width="25%">
									产品名称
								</td>
								<td width="8%">
									规格
								</td>

								<td width="6%">
									相关
								</td>
								<td width="2%">
									单位
								</td>
								<td width="6%">
									数量
								</td>
								<td width="6%">
									计量
								</td>
							</tr>
							<logic:iterate id="p" name="als">
								<tr class="table-back-colorbar" onClick=
	CheckedObj(this);;
>
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
										<a href="#" onMouseOver="ShowSQ(this,'${p.productid}');"><img
												src="../images/CN/stock.gif" width="16" border="0"> </a>
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
