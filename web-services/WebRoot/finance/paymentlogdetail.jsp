<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	function Audit(plid){
			showloading();
			popWin2("../finance/auditPaymentLogAction.do?PLID="+plid);
	}
	
	function CancelAudit(plid){
			showloading();
			popWin2("../finance/cancelAuditPaymentLogAction.do?PLID="+plid);
	}
	
	function Pay(plid){
			showloading();
			popWin2("../finance/payPaymentLogAction.do?PLID="+plid);
	}
	
	function CancelPay(plid){
			showloading();
			popWin2("../finance/cancelPayPaymentLogAction.do?PLID="+plid);
	}
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="1009"> 付款记录详情</td>
          <td width="224" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="60" align="center"><c:choose>
          <c:when test="${plf.isaudit==0}"><a href="javascript:Audit('${plf.id}');">复核</a></c:when>
          <c:otherwise> <a href="javascript:CancelAudit('${plf.id}')">取消复核</a></c:otherwise>
        </c:choose></td>
        <td width="60" align="center"><c:choose>
          <c:when test="${plf.ispay==0}"><a href="javascript:Pay('${plf.id}')">付款</a></c:when>
          <c:otherwise><a href="javascript:CancelPay('${plf.id}')">取消付款</a></c:otherwise>
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
	  	<td width="10%"  align="right">领款人：</td>
          <td width="25%">${plf.payee}</td>
          <td width="8%" align="right">结算方式：</td>
          <td width="22%">${plf.paymodename}</td>
	      <td width="8%" align="right">款项来源：</td>
	      <td width="27%">${plf.fundsrcname}</td>
	  </tr>
	  <tr>
	    <td  align="right">付款金额：</td>
	    <td>${plf.paysum}</td>
	    <td align="right">票据号：</td>
	    <td>${plf.billnum}</td>
	    <td align="right">凭证号：</td>
	    <td>${plf.voucher}</td>
	    </tr>
		 <tr>
	    <td  align="right">已核销金额：</td>
	    <td>${plf.alreadyspend}</td>
	    <td align="right">付款用途：</td>
	    <td colspan="3">${plf.paypurpose}</td>
	    </tr>
		 <tr>
		   <td  align="right">备注：</td>
		   <td colspan="5">${plf.remark}</td>
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
	    <td><windrp:getname key='organ' value='${plf.makeorganid}' p='d'/></td>
	    <td align="right">制单部门：</td>
	    <td><windrp:getname key='dept' value='${plf.makedeptid}' p='d'/></td>
	    <td align="right">制单人：</td>
	    <td><windrp:getname key='users' value='${plf.makeid}' p='d'/></td>
	    </tr>
	  <tr>
	  	<td width="9%"  align="right">制单日期：</td>
          <td width="26%">${plf.makedate}</td>
          <td width="13%" align="right">是否复核：</td>
          <td width="23%"><windrp:getname key='YesOrNo' value='${plf.isaudit}' p='f'/></td>
	      <td width="9%" align="right">复核人：</td>
	      <td width="27%"><windrp:getname key='users' value='${plf.auditid}' p='d'/></td>
	  </tr>
	  <tr>
	  	<td width="9%"  align="right">复核日期：</td>
          <td width="26%">${plf.auditdate}</td>
          <td width="13%" align="right">是否付款：</td>
          <td width="23%"><windrp:getname key='YesOrNo' value='${plf.ispay}' p='f'/></td>
	      <td width="9%" align="right">付款人：</td>
	      <td width="27%"><windrp:getname key='users' value='${plf.payid}' p='d'/></td>
	  </tr>
	   <tr>
	  	<td width="9%"  align="right">付款日期：</td>
          <td width="26%">${plf.paydate}</td>
          <td width="13%" align="right">&nbsp;</td>
          <td width="23%"></td>
	      <td width="9%" align="right">&nbsp;</td>
	      <td width="27%">&nbsp;</td>
	  </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="100" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>付款记录列表</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td width="13%">结算单号</td> 
          <td width="29%" >结算方式</td>
          <td width="27%">相关单据号</td>
          <td width="14%">应付款金额</td>
          <td width="17%">本次付款金额</td>         
        </tr>
        <logic:iterate id="p" name="rvls" > 
        <tr class="table-back-colorbar">
          <td>${p.id}</td> 
          <td >${p.paymodename}</td>
          <td>${p.billno}</td>
          <td>${p.payablesum}</td>          
          <td>${p.thispayablesum}</td>         
        </tr>
        </logic:iterate> 
      </table>
</fieldset>
	
      </td>
  </tr>
</table>
</body>
</html>
