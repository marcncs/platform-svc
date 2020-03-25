<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	function Audit(plid){
			window.open("../finance/auditPaymentLogAction.do?PLID="+plid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(plid){
			window.open("../finance/cancelAuditPaymentLogAction.do?PLID="+plid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="1009"> 付款记录详情</td>
        
        </tr>
      </table>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">领款人：</td>
          <td width="26%">${plf.payee}</td>
          <td width="8%" align="right">付款用途：</td>
          <td width="22%">${plf.paypurpose}</td>
	      <td width="8%" align="right">款项来源：</td>
	      <td width="27%">${plf.fundsrcname}</td>
	  </tr>
	   <tr>
	    <td  align="right">付款金额：</td>
	    <td>${plf.paysum}</td>
	    <td align="right">长短款：</td>
	    <td>${plf.accidentsum}</td>
	    <td align="right">手续费：</td>
	    <td>${plf.poundage}</td>
	    </tr>
	  <tr>
	    <td  align="right">票据号：</td>
	    <td>${plf.billnum}</td>
	    <td align="right">开单时间：</td>
	    <td>${plf.billdate}</td>
	    <td align="right">备注：</td>
	    <td>${plf.remark}</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">制单人：</td>
          <td width="26%">${plf.makeidname}</td>
          <td width="8%" align="right">制单日期：</td>
          <td width="22%">${plf.makedate}</td>
	      <td width="8%" align="right">&nbsp;</td>
	      <td width="27%">&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">是否复核：</td>
	    <td>${plf.isauditname}</td>
	    <td align="right">复核人：</td>
	    <td>${plf.auditidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${plf.auditdate}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>付款清单列表</td>
        </tr>
      </table>
	  </legend>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="8%">产品编号</td> 
          <td width="19%" >产品名称</td>
          <td width="13%">规格</td>
          <td width="8%">批次</td>
          <td width="7%">单据号</td>          
		  <td width="10%">单位</td>
          <td width="8%">数量</td>
		  <td width="8%">货款</td>
		  <td width="6%">税额</td>		 
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.batch}</td>
          <td>${p.billno}</td>
		  <td>${p.unitidname}</td>
		  <td>${p.quantity}</td>
          <td>${p.goodsfund}</td>
		  <td>${p.scot}</td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>
	
	 <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>审核信息</td>
        </tr>
      </table>
	  </legend>
 <form name="addform" method="post" action="approvePaymentLogAction.do">
      <table width="100%" height="200"  border="0" cellpadding="0" cellspacing="1">
       
			<input name="logid" type="hidden" id="logid" value="${logid}">
          <tr class="table-back"> 
            <td  align="right">
            审核状态：</td>
            <td>${approvestatus}</td>
          </tr>
          <tr class="table-back">
            <td  align="right">
            动作：</td>
            <td>${stractid}</td>
          </tr>
          <tr class="table-back"> 
            <td width="13%"  align="right" valign="top"> 审核内容：</td>
            <td width="87%"><textarea name="approvecontent" cols="80" rows="10" id="approvecontent"></textarea></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right" valign="top">&nbsp;</td>
            <td><input type="submit" name="Submit2" value="确定"> &nbsp;&nbsp; 
              <input type="button" name="Submit2" value="取消" onClick="javascript:history.back();"></td>
          </tr>
       
      </table>
       </form>
      </fieldset>
      </td>
  </tr>
</table>
</body>
</html>
