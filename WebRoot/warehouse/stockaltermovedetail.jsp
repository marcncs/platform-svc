<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/showSQ.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
		<script language="javascript">

	function CheckedObj(obj){
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 obj.className="event";
	}
	function Audit(smid){
			popWin2("../warehouse/auditStockAlterMoveAction.do?SMID="+smid);
	}
	function MoveAndAudit(smid){
			popWin2("../warehouse/moveAndAuditStockAlterMoveAction.do?SMID="+smid);
	}
	function CancelAudit(smid){
			popWin2("../warehouse/cancelAuditStockAlterMoveAction.do?SMID="+smid);
	}
	function Prints(id){
			popWin("printStockAlterMoveAction.do?ID="+id,1000,650);
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
								${menu }详情
							</td>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<c:if test="${type == '2'}">
						          		<tr><td>&nbsp;</td></tr>
						          	</c:if>
						          	
						          	<c:if test="${type != '2'}">
						          		<tr>
											<%--
											<td width="60" align="center">
												<a href="javascript:Prints('${smf.id}');">打印</a>
											</td>
											--%><ws:hasAuth operationName="/warehouse/auditStockAlterMoveAction.do">
											<td style="float: right; padding-right: 20px;">
												<c:choose>
													<c:when test="${smf.isaudit==0}">
														<input type="button" name="audit" id="audit" value="单据确认" onclick="javascript:Audit('${smf.id}');"/>
														<!--<a href="javascript:Audit('${smf.id}');">单据确认</a>
														&nbsp;
														--><!-- <a href="javascript:MoveAndAudit('${smf.id}');">移库并单据确认</a> -->
													</c:when>
													<c:when test="${smf.isshipment==0}">
														<input type="button" name="cancelaudit" id="cancelaudit" value="取消单据确认" onclick="javascript:CancelAudit('${smf.id}');"/>
														<!--<a href="javascript:CancelAudit('${smf.id}')">取消单据确认</a>
													-->
													</c:when>
												</c:choose>
											</td>
											</ws:hasAuth>
										</tr>
						          	</c:if>
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
								<td width="12%" align="right">
									单据日期：
								</td>
								<td width="23%">
									<windrp:dateformat value="${smf.movedate}" p="yyyy-MM-dd" />
								</td>
								<td width="10%" align="right">
									调出机构：
								</td>
								<td width="23%">
									<windrp:getname key='organ' value='${smf.outOrganId}' p='d' />
								</td>
								<td width="10%" align="right">
									调出仓库：
								</td>
								<td width="22%">
									<windrp:getname key='warehouse' value='${smf.outwarehouseid}'
										p='d' />
								</td>
							</tr>
							<tr>
							<td align="right">
									 企业内部单号：
								</td>
								<td>
									${smf.nccode }
								</td>
								<td align="right">
									调入机构：
								</td>
								<td>
									<windrp:getname key='organ' value='${smf.receiveorganid}' p='d' />
								</td>
								<td align="right">
									调入仓库：
								</td>
								<td>
									<windrp:getname key='warehouse' value='${smf.inwarehouseid}'
										p='d' />
								</td>
								
							</tr>
							<tr>
								<td align="right">
									联系人：
								</td>
								<td>
									${smf.olinkman }
								</td>
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
							</tr>
							
							<tr>
								<td align="right">
									发货原因：
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
								<td width="12%" align="right">
									制单人：
								</td>
								<td width="23%">
									<windrp:getname key='users' value='${smf.makeid}' p='d' />
								</td>
								<td width="10%" align="right">
									制单日期：
								</td>
								<td width="23%">
									${smf.makedate}
								</td>
								<td width="10%" align="right">
									是否检货：
								</td>
								<td width="22%">
									<windrp:getname key='YesOrNo' value='${smf.takestatus}' p='f' />
								</td>

							</tr>
							<tr>
								<td width="11%" align="right">
									是否单据确认：
								</td>
								<td width="23%">
									<windrp:getname key='YesOrNo' value='${smf.isaudit}' p='f' />
								</td>
								<td align="right">
									单据确认人：
								</td>
								<td>
									<windrp:getname key='users' value='${smf.auditid}' p='d' />
								</td>
								<td align="right">
									单据确认日期：
								</td>
								<td>
									<windrp:dateformat value="${smf.auditdate}" p="yyyy-MM-dd" />
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
									<windrp:dateformat value="${smf.shipmentdate}" p="yyyy-MM-dd" />
								</td>

							</tr>
							<%--
							<tr>
								<td align="right">
									是否完成配送：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${smf.istally}' p='f' />
								</td>
								<td align="right">
									完成配送人：
								</td>
								<td>
									<windrp:getname key='users' value='${smf.tallyid}' p='d' />
								</td>
								<td align="right">
									完成配送时间：
								</td>
								<td>
									${smf.tallydate}
								</td>

							</tr>
							 --%>
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
									<windrp:dateformat value="${smf.receivedate}" p="yyyy-MM-dd" />
								</td>
							</tr>
							
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="90" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										调拨单产品列表
									</td>
								</tr>
							</table>
						</legend>

						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							<tr align="center" class="title-top">
								<td width="6%">
									产品编号
								</td>
								<td width="6%">
									物料号
								</td>
								<td width="10%">
									产品名称
								</td>
								<td width="8%">
									规格
								</td>
								
								<td width="2%">
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
								<tr class="table-back-colorbar" onClick="CheckedObj(this);">
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
