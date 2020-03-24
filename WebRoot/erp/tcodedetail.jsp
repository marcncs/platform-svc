<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>${menu }详情</title>
<script src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/showCQ.js"> </SCRIPT>
<script language="javascript">
</script>
	</head>
	<body style="overflow: auto;">
		<form id="excput" name="excput" method="post"
							action="../warehouse/excPutTakeBillAction.do">
		    <input type="hidden" name="billno" value="${sof.billno}">
		</form>
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
							<td width="925">
								${menu }详情
							</td>
							<td width="308" align="right">
								<table width="300" border="0" cellpadding="0" cellspacing="0">
									<c:if test="${type == '2'}">
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
									</c:if>
									<c:if test="${type != '2'}">
										<tr>
											<ws:hasAuth operationName="/warehouse/auditTakeTicketAction.do">
												<td width="100" align="right">
													<c:choose>
														<c:when test="${sof.isaudit==0}">
														<input type="button" name="toaudit" id="toaudit" value="${flag eq 'PW' ? '退货完成' : '出库确认'}"
															onclick="javascript:toAudit('${sof.id}','${sof.billno}');" />
															<%--<a href="javascript:toAudit('${sof.id}','${sof.billno}');">出库确认</a>--%>
														</c:when>
														
													</c:choose>
											
												</td>
											</ws:hasAuth>
											<ws:hasAuth operationName="/warehouse/excPutTakeBillAction.do">
											    <td width="80">
													<a href="javascript:excput();"><img
														src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
														align="absmiddle">&nbsp;导出条码</a>
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
								<td width="11%" align="right">
									出货仓库：
								</td>
								<td width="27%">
									<windrp:getname key='warehouse' value='${sof.warehouseid}' p='d' />
								</td>
								<td width="8%" align="right">
									企业内部单号：
								</td>
								<td width="22%">
									${sof.nccode}
								</td>
								<td width="10%" align="right">
									出库机构：
								</td>
								<td width="22%">
									${sof.oname}
								</td>
							</tr>
							<tr>
								<td width="11%" align="right">
									收货人：
								</td>
								<td width="27%">
									${sof.rlinkman}
								</td>
								<td width="8%" align="right">
									联系电话：
								</td>
								<td width="22%">
									${sof.tel}
								</td>
								<td width="10%" align="right">
									收货仓库：
								</td>
								<td width="22%">
									<windrp:getname key='warehouse' value='${sof.inwarehouseid}' p='d' />
								</td>
							</tr>
							<tr>
								<%--<td align="right">
									检货人：
								</td>
								<td>
									<windrp:getname key='users' value='${sof.takeid}' p='d' />
								</td>
								--%><td align="right">
									打印次数：
								</td>
								<td>
									${sof.printtimes}
								</td>
								<td align="right">
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align="right">
									备注：
								</td>
								<td colspan="3">
									${sof.remark}
								</td>
								<td align="right">
									是否查看:
								</td>
								<td>
									<windrp:getname key="YesOrNo" p="f" value="${IsRead}" />
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
									制单人：
								</td>
								<td width="26%">
									<windrp:getname key='users' value='${sof.makeid}' p='d' />
								</td>
								<td width="8%" align="right"></td>
								<td width="22%"></td>
								<td width="11%" align="right">
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
							<c:choose>
									<c:when test="${sof.bsort == 1}">
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
								<td>
									${sof.blankoutreason}
								</td>
							</tr>
									</c:when>
								</c:choose>
<%--							
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
							--%>
							<%--
	   <tr>

	    <td  align="right">是否验货：</td>
	    <td><windrp:getname key='checkedStatus' value='${sof.isChecked}' p='f'/></td>
	    <td align="right">验货人：</td>
	    <td><windrp:getname key='users' value='${sof.checkedId}' p='d'/></td>
	    <td align="right">验货日期：</td>
	    <td>${sof.checkedDate}</td>
	    </tr>
	     --%>
						</table>
					</fieldset>
					<%--
	<c:if test="${sof.bsort != 7}">
		<fieldset align="center"> 
		<legend>
    		采集器信息
	 	 </legend>
		  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
		  <tr>
		  	<td width="11%"  align="right">采集器编号：</td>
		  	
	          <td width="26%">
					<c:if test="${sof.isPicked != null && sof.isPicked != 0 }">
						<input type="text" name="scannerNo" id="scannerNo" value="${sof.scannerNo}" readonly="readonly"/>
						
					</c:if>
					
					<c:if test="${sof.isPicked == null || sof.isPicked == 0 }">
					<select name="scannerNo" id="scannerNo" onchange="setScannerNo();">
					<option value="">
						-选择-
					</option>
					<logic:iterate id="c" name="scannerUsers">
					<option value="${c.scanner}" ${c.scanner == sof.scannerNo ?"selected":""} >${c.scanner}</option>
					</logic:iterate>
					</select>
					</c:if>
					
			  </td>
	          <td width="8%" align="right"></td>
	          <td width="22%"></td>
		      <td width="11%" align="right"></td>
		      <td width="22%"></td>
		  </tr>
		  </table>
		</fieldset>
	</c:if>
	--%>

					<fieldset align="center">
						<legend>
							<table width="110" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										检货物品清单列表
									</td>
								</tr>
							</table>
						</legend>
						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							<tr align="center" class="title-top">
								<td width="7%">
									产品编号
								</td>
								<td width="10%">
									物料号
								</td>
								<td width="20%">
									产品名称
								</td>
								<td width="10%">
									规格
								</td>
								<td width="4%">
									序号
								</td>
								<td width="8%">
									计划数量
								</td>
								<td width="8%">
									确认数量
								</td>
								<td width="5%">
									单位
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
									<td><span onmouseover="ShowTCQ(this,'${p.productid}','${sof.id}');"><a href="javascript:toaddidcode('${sof.id}','${p.id}','${sof.warehouseid}','${sof.isaudit}')"><img src="../images/CN/code.gif"  border="0" title="录入条码"></span></a></td>
										<td>
											<windrp:format value="${p.quantity}" p="###,##0.00" />
										</td>
										<td>
											<windrp:format value="${p.realQuantity}" p="###,##0.00" />
										</td>
										<td>
											<windrp:getname key='CountUnit' value='${p.unitid}' p='d' />
										</td>
								</tr>
							</logic:iterate>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
		<%
			session.setAttribute("sof", request.getAttribute("sof"));
		%>
	</body>
</html>
