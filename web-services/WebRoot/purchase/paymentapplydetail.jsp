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
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 付款申请详情</td>
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
	  	<td width="9%"  align="right">供应商：</td>
          <td width="21%">${pbf.pname}</td>
          <td width="13%" align="right">联系人：</td>
          <td width="20%">${pbf.plinkman}</td>
	      <td width="12%" align="right">联系电话：</td>
	      <td width="25%">${pbf.tel}</td>
	  </tr>
	  <tr>
	    <td  align="right">采购人：</td>
	    <td>${pbf.purchaseidname}</td>
	    <td align="right">结算方式：</td>
	    <td>${pbf.paymentmodename}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
		<tr>
	    <td  align="right">户名：</td>
	    <td>${pbf.doorname}</td>
	    <td align="right">开户行：</td>
	    <td>${pbf.bankname}</td>
	    <td align="right">帐号：</td>
	    <td>${pbf.bankaccount}</td>
	    </tr>
	  <tr>
	    <td  align="right">采购部门：</td>
	    <td>${pbf.purchasedeptname}</td>
	    <td align="right">相关单据号：</td>
	    <td>${pbf.billno}</td>
	    <td align="right">金额：</td>
	    <td>${pbf.totalsum}</td>
	    </tr>
	  <tr>
	    <td  align="right">制单人：</td>
	    <td>${pbf.makeidname}</td>
	    <td align="right">制单日期：</td>
	    <td>${pbf.makedate}</td>
	    <td align="right">是否结案：</td>
	    <td>${pbf.isendcasename}</td>
	    </tr>
	  <tr>
	    <td  align="right">结案人：</td>
	    <td>${pbf.endcaseidname}</td>
	    <td align="right">结案日期：</td>
	    <td>${pbf.endcasedate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	  
    </td>
  </tr>
</table>
</body>
</html>
