<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
        <SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
	function CheckedObj(obj){
		
		 for(i=1; i<obj.parentNode.childNodes.length; i++)
		 {
			   obj.parentNode.childNodes[i].className="table-back-colorbar";
		 }
	 }
	function Audit(piid){
		popWin("../purchase/auditPurchaseInquireAction.do?PIID="+piid,500,250);
		
	}
	
	function CancelAudit(piid){
		popWin("../purchase/cancelAuditPurchaseInquireAction.do?PIID="+piid,500,250);
	}
</script>
		<title>WINDRP-分销系统</title>
	</head>

	<body>


		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="15">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td>
								采购询价记录详情
							</td>
							<td align="right" style="padding-right: 10px;">
							<table width="180" border="0" cellpadding="0" cellspacing="0">
									<tr>
									<td align="right" style="padding-right: 15px;">
									<c:choose>
										<c:when test="${pif.isaudit==0}">
											<a href="javascript:Audit('${pif.id}');">复核</a>
										</c:when>
										<c:otherwise>
											<a href="javascript:CancelAudit('${pif.id}')">取消复核</a>
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
								<td width="10%" align="right">
									询价标题：
								</td>
								<td width="20%">
									${pif.inquiretitle}
								</td>
								<td width="10%" align="right">
									供应商：
								</td>
								<td width="20%">
									${pif.providename}
								</td>
								<td width="10%" align="right">
									联系人：
								</td>
								<td >
									${pif.plinkman}
								</td>
							</tr>
							<tr>
								<td align="right">
									采购计划编号：
								</td>
								<td>
									${pif.ppid}
								</td>
								<td align="right">
									有效天数：
								</td>
								<td>
									${pif.validdate}
								</td>
								<td align="right">
									备注：
								</td>
								<td>
									${pbf.remark}
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
								<td width="10%" align="right">
									制单人：
								</td>
								<td width="20%">
									<windrp:getname key='users' value='${pif.makeid}' p='d' />
								</td>
								<td width="10%" align="right">
									制单日期：
								</td>
								<td width="20%">
									
									<windrp:dateformat value="${pif.makedate}" p="yyyy-MM-dd"/>
								</td>
								<td width="10%" align="right">
									是否复核：
								</td>
								<td >
									<windrp:getname key='YesOrNo' value='${pif.isaudit}' p='f' />
								</td>
							</tr>
							<tr>
								<td align="right">
									复核人：
								</td>
								<td>
									<windrp:getname key='users' value='${pif.auditid}' p='d' />
								</td>
								<td align="right">
									复核日期：
								</td>
								<td>
									
									<windrp:dateformat value="${pif.auditdate}" p="yyyy-MM-dd"/>
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
							<table width="100" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										采购询价产品列表
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
							<tr align="center" class="title-top">
								<td width="">
									产品编号
								</td>
								<td width="">
									产品名称
								</td>
								<td width="">
									规格
								</td>
								<td width="">
									单位
								</td>
								<td width="">
									单价
								</td>
								<td width="">
									数量
								</td>
								<td width="">
									金额
								</td>
							</tr>
							<logic:iterate id="p" name="als">
								<tr class="table-back-colorbar" onclick=CheckedObj(this);>
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
										${p.unitname}
									</td>
									<td>
										<fmt:formatNumber value='${p.unitprice}' pattern='0.00' />
									</td>
									<td>
										${p.quantity}
									</td>
									<td>
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
