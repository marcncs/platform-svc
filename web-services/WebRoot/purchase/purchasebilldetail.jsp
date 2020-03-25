<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
         <SCRIPT language="javascript" src="../js/showhp.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
		<script language="javascript">
		function Audit(pbid){
				popWin("../purchase/auditPurchaseBillAction.do?PBID="+pbid,500,250);
		}
		
		function CancelAudit(pbid){
				popWin("../purchase/cancelAuditPurchaseBillAction.do?PBID="+pbid,500,250);
		}
		
		function Ratify(pbid){
				popWin("../purchase/ratifyPurchaseBillAction.do?PBID="+pbid,500,250);
		}
		
		function CancelRatify(pbid){
				popWin("../purchase/cancelRatifyPurchaseBillAction.do?PBID="+pbid,500,250);
		}
		
		function PurchaseBill(pbid){
				popWin("../purchase/purchaseBillAction.do?ID="+pbid,900,600);
		}
		
	
	
</script>

	</head>
	<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
		<SCRIPT language=javascript>
function click() {if (event.button==2) {alert('本页拒绝使用右键!');}}document.onmousedown=click
</SCRIPT>

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
							<td width="1003">
								采购订单详情
							</td>
							<td width="230" align="right">
								<table width="180" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="60" align="center">
											<a href="javascript:PurchaseBill('${pbf.id}');">打印</a>
										</td>
										<td width="60" align="center">
											<c:choose>
												<c:when test="${pbf.isaudit==0}">
													<a href="javascript:Audit('${pbf.id}');">复核</a>
												</c:when>
												<c:otherwise>
													<a href="javascript:CancelAudit('${pbf.id}')">取消复核</a>
												</c:otherwise>
											</c:choose>
										</td>
										<td width="60" align="center">
											<c:choose>
												<c:when test="${pbf.isratify==0}">
													<a href="javascript:Ratify('${pbf.id}')">批准</a>
												</c:when>
												<c:otherwise>
													<a href="javascript:CancelRatify('${pbf.id}')">取消批准</a>
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
							<table width="50" border="0" cellpadding="0" cellspacing="0" class="table-detail">
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
						<input name="pid" type="hidden" id="pid" value="${pbf.pid}">
						供应商：
					</td>
					<td width="26%">
						${pbf.pname}					</td>
					<td width="10%" align="right">
						联系人：
					</td>
					<td width="21%">
						${pbf.plinkman}
					</td>
					<td width="9%" align="right">
						联系电话：
					</td>
					<td width="23%">
						${pbf.tel}
					</td>
				</tr>
				<tr>
					<td align="right">
						采购部门：
					</td>
					<td>
						<windrp:getname key='dept' value='${pbf.purchasedept}' p='d'/>
					</td>
					<td align="right">
						采购人员：
					</td>
					<td>
					<windrp:getname key='users' value='${pbf.purchaseid}' p='d'/>
					</td>
					<td align="right">
						结算方式：
					</td>
					<td>
						<windrp:getname key='PayMode' value='${pbf.paymode}' p='f'/>
					</td>
				</tr>
				<tr>
					<td align="right">
						预计收货日期：
					</td>
					<td>
						<windrp:dateformat value='${pbf.receivedate}' p='yyyy-MM-dd'/>
					</td>
					<td align="right">
						总金额：
					</td>
					<td>
						${pbf.totalsum}
					</td>
					<td align="right">
						发票信息：
					</td>
					<td>
						${pbf.invmsgname}
					</td>
				</tr>
				<tr>
					<td align="right">
						收货地址：
					</td>
					<td colspan="2">
						${pbf.receiveaddr}
					</td>
					<td colspan="3">&nbsp;
						
					</td>
				</tr>
				<tr>
					<td align="right">
						备注：
					</td>
					<td colspan="2">
						${pbf.remark}
					</td>
					<td colspan="3">&nbsp;
						
					</td>
				</tr>
			</table>
			</fieldset>

			<fieldset align="center">
				<legend>
					<table width="50" border="0" cellpadding="0" cellspacing="0" class="table-detail">
						<tr>
							<td>
								状态信息
							</td>
						</tr>
					</table>
				</legend>
				<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
					<tr>
						<td width="9%" align="right">
							制单人：
						</td>
						<td width="21%">
						<windrp:getname key='users' value='${pbf.makeid}' p='d'/>
						</td>
						<td width="13%" align="right">
							制单日期：
						</td>
						<td width="23%">
							<windrp:dateformat value='${pbf.makedate}' p='yyyy-MM-dd'/>
						</td>
						<td width="11%" align="right">
							是否复核：
						</td>
						<td width="23%">
						<windrp:getname key='YesOrNo' value='${pbf.isaudit}' p='f'/>
							
						</td>
					</tr>
					<tr>
						<td align="right">
							复核人：
						</td>
						<td>
						<windrp:getname key='users' value='${pbf.auditid}' p='d'/>
							
						</td>
						<td align="right">
							复核日期：
						</td>
						<td>
						<windrp:dateformat value='${pbf.auditdate}' p='yyyy-MM-dd'/>
							
						</td>
						<td align="right">
							是否批准：
						</td>
						<td>
						<windrp:getname key='YesOrNo' value='${pbf.isratify}' p='f'/>
							
						</td>
					</tr>
					<tr>
						<td align="right">
							批准人：
						</td>
						<td>
						<windrp:getname key='users' value='${pbf.ratifyid}' p='d'/>
							
						</td>
						<td align="right">
							批准日期：
						</td>
						<td>
						<windrp:dateformat value='${pbf.ratifydate}' p='yyyy-MM-dd'/>
							
						</td>
						<td align="right">
							是否转到货：
						</td>
						<td>
						<windrp:getname key='YesOrNo' value='${pbf.istransferadsum}' p='f'/>
							
						</td>
					</tr>
				</table>
			</fieldset>

			<fieldset align="center">
				<legend>
					<table width="90" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								采购单产品列表
							</td>
						</tr>
					</table>
				</legend>
				<table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr align="center" class="title-top">
						<td width="11%">
							产品编号
						</td>
						<td width="19%">
							产品名称
						</td>
						<td width="20%">
							规格
						</td>
						<td width="4%">
							单位
						</td>
						<td width="12%">需求日期</td>
						<td width="3%">
							相关
						</td>
						<td width="6%">
							单价
						</td>
						<td width="7%">
							数量
						</td>
                        <td width="9%">
							入库数量
						</td>
						<td width="9%">
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
							<td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
							<td><windrp:dateformat value='${p.requiredate}' p='yyyy-MM-dd'/></td>
							<td>
								<a href="#" onMouseOver="showhp(this,'${pbf.pid}','${p.productid}');"
													onMouseOut="hiddenhp();"><img src="../images/CN/cheng.gif"
										width="16" border="0">
								</a>
							</td>
							<td align="right">
								<fmt:formatNumber value='${p.unitprice}' pattern='0.00' />
							</td>
							<td align="right">
								<fmt:formatNumber value='${p.quantity}' pattern='0.00' />
							</td>
                            <td align="right">
								<fmt:formatNumber value='${p.incomequantity}' pattern='0.00' />
							</td>
							<td align="right">
								<fmt:formatNumber value='${p.subsum}' pattern='0.00' />
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
