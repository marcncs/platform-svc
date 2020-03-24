<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	function Audit(ilid){
			window.open("../finance/auditIncomeLogAction.do?ILID="+ilid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
	
	function CancelAudit(ilid){
			window.open("../finance/cancelAuditIncomeLogAction.do?ILID="+ilid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
	
	function Receive(ilid){
			window.open("../finance/receiveIncomeLogAction.do?ILID="+ilid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
	
	function CancelReceive(ilid){
			window.open("../finance/cancelReceiveIncomeLogAction.do?ILID="+ilid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="1011"> 收款记录详情</td>
          <td width="222" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="60" align="center"><c:choose>
          <c:when test="${ilf.isaudit==0}"><a href="javascript:Audit('${ilf.id}');">复核</a></c:when>
          <c:otherwise> <a href="javascript:CancelAudit('${ilf.id}')">取消复核</a></c:otherwise>
        </c:choose></td>
        <td width="60" align="center"><c:choose>
          <c:when test="${ilf.isreceive==0}"><a href="javascript:Receive('${ilf.id}')">收款</a></c:when>
          <c:otherwise><a href="javascript:CancelReceive('${ilf.id}')">取消收款</a></c:otherwise>
        </c:choose></td>        
      </tr>
    </table></td>
        </tr>
      </table>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">付款人：</td>
          <td width="21%">${ilf.drawee}</td>
          <td width="13%" align="right">本次收款金额：</td>
          <td width="23%"><windrp:format value='${ilf.incomesum}'/></td>
	      <td width="11%" align="right">收款去向：</td>
	      <td width="23%">${ilf.fundattachname}</td>
	  </tr>
	  <tr>
	    <td  align="right">票据号：</td>
	    <td>${ilf.billnum}</td>
	    <td align="right">结算方式：</td>
	    <td>${ilf.paymentmodename}</td>
	    <td align="right">已核销金额：</td>
	    <td><windrp:format value='${ilf.alreadyspend}'/></td>
	  </tr>
		 <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${ilf.remark}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>状态信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	    <td  align="right">制单机构：</td>
	    <td><windrp:getname key='organ' value='${ilf.makeorganid}' p='d'/></td>
	    <td align="right">制单部门：</td>
	    <td><windrp:getname key='dept' value='${ilf.makedeptid}' p='d'/></td>
	    <td align="right">制单人：</td>
	    <td>${ilf.makeidname}</td>
	    </tr>
	  <tr>
	  	<td width="9%"  align="right">制单日期：</td>
          <td width="21%">${ilf.makedate}</td>
          <td width="13%" align="right">是否复核：</td>
          <td width="23%">${ilf.isauditname}</td>
	      <td width="9%" align="right">复核人：</td>
	      <td width="25%">${ilf.auditidname}</td>
	  </tr>
	  <tr>
	  	<td width="9%"  align="right">复核日期：</td>
          <td width="21%">${ilf.auditdate}</td>
          <td width="13%" align="right">是否收款：</td>
          <td width="23%">${ilf.isreceivename}</td>
	      <td width="9%" align="right">收款人：</td>
	      <td width="25%">${ilf.receiveidname}</td>
	  </tr>
	   <tr>
	  	<td width="9%"  align="right">收款日期：</td>
          <td width="21%">${ilf.receivedate}</td>
          <td width="13%" align="right">&nbsp;</td>
          <td width="23%">&nbsp;</td>
	      <td width="9%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
	  </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="100" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>收款记录列表</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable"> 
        <tr align="center" class="title-top">
          <td width="16%">结算单号</td> 
          <td width="32%" >结算方式</td>
          <td width="25%">相关单据号</td>
          <td width="13%">应收款金额</td>
          <td width="14%">本次收款金额</td>         
        </tr>
        <logic:iterate id="p" name="rvls" > 
        <tr class="table-back-colorbar">
          <td>${p.rid}</td> 
          <td >${p.paymentmodename}</td>
          <td>${p.billno}</td>
          <td><windrp:format value='${p.receivablesum}'/></td>          
          <td><windrp:format value='${p.thisreceivablesum}'/></td>         
        </tr>
        </logic:iterate> 
      </table>
</fieldset>
      </td>
  </tr>
</table>
</body>
</html>
