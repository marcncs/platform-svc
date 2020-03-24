<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="1034"> 应收款详情 </td>
    <td width="199" align="right">&nbsp;</td>
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
	  	<td width="12%"  align="right">付款方名称：</td>
          <td width="19%">${payer}</td>
          <td width="10%" align="right">日期：</td>
          <td width="21%">${BeginDate} - ${EndDate} </td>
          <td width="8%" align="right">&nbsp;</td>
          <td width="30%"></td>
	  </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>现款</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right">期初应收金额：</td>
          <td width="19%">${detail.cashprevious}</td>
          <td width="12%" align="right">本期应收金额：</td>
          <td width="23%">${detail.cashcurrent}</td>	      
	  </tr>
	    <tr>
	  	<td width="12%"  align="right">本期已收金额：</td>
          <td width="19%">${detail.cashcurrentalready}</td>
          <td width="12%" align="right">期未应收金额：</td>
          <td width="23%">${detail.cashend}</td>	      
	  </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>代款</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right">期初应收金额：</td>
          <td width="19%">${detail.proxyprevious}</td>
          <td width="12%" align="right">本期应收金额：</td>
          <td width="23%">${detail.proxycurrent}</td>	      
	  </tr>
	    <tr>
	  	<td width="12%"  align="right">本期已收金额：</td>
          <td width="19%">${detail.proxycurrentalready}</td>
          <td width="12%" align="right">期未应收金额：</td>
          <td width="23%">${detail.proxyend}</td>	      
	  </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>预收</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right">期初应收金额：</td>
          <td width="19%">${detail.budgetprevious}</td>
          <td width="12%" align="right">本期应收金额：</td>
          <td width="23%">${detail.budgetcurrent}</td>	      
	  </tr>
	    <tr>
	  	<td width="12%"  align="right">本期已收金额：</td>
          <td width="19%">${detail.budgetcurrentalready}</td>
          <td width="12%" align="right">期未应收金额：</td>
          <td width="23%">${detail.budgetend}</td>	      
	  </tr>
	  </table>
	</fieldset>
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>月结</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right">期初应收金额：</td>
          <td width="19%">${detail.monthprevious}</td>
          <td width="12%" align="right">本期应收金额：</td>
          <td width="23%">${detail.monthcurrent}</td>	      
	  </tr>
	    <tr>
	  	<td width="12%"  align="right">本期已收金额：</td>
          <td width="19%">${detail.monthcurrentalready}</td>
          <td width="12%" align="right">期未应收金额：</td>
          <td width="23%">${detail.monthend}</td>	      
	  </tr>
	  </table>
	</fieldset>
	
</td>
  </tr>
</table>
</body>
</html>
