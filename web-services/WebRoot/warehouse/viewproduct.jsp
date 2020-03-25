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
<script language="javascript">
	function Audit(rid){
			window.open("../finance/auditPayableAction.do?POID="+rid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(rid){
			window.open("../finance/cancelAuditPayableAction.do?POID="+rid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="1004"> 产品详情 </td>
    <td width="229" align="right">&nbsp;</td>
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
	  	<td width="13%"  align="right">类别：</td>
          <td width="20%">${paf.psid}</td>
          <td width="9%" align="right">品牌：</td>
          <td width="21%">${paf.brandname}</td>
          <td width="8%" align="right">&nbsp;</td>
          <td width="29%">&nbsp;</td>
	  </tr>
	   <tr>
	  	<td width="13%"  align="right">产品名称：</td>
          <td width="20%">${paf.productname}</td>
          <td width="9%" align="right">规格：</td>
          <td width="21%">${paf.specmode}</td>
          <td width="8%" align="right">单位：</td>
          <td width="29%">${paf.countunitname}</td>
	  </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>价格信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="13%"  align="right">标准采购价：</td>
          <td width="19%">${paf.standardpurchase}</td>
          <td width="11%" align="right">标准销售价：</td>
          <td width="23%">${paf.standardsale}</td>
	      <td width="12%" align="right">最低销售价：</td>
	      <td width="22%">${paf.leastsale}</td>
	  </tr>
	  <tr>
	  	<td width="13%"  align="right">A类终端价：</td>
          <td width="19%">${paf.pricei}</td>
          <td width="11%" align="right">B类终端价：</td>
          <td width="23%">${paf.priceii}</td>
	      <td width="12%" align="right">C类终端价：</td>
	      <td width="22%">${paf.priceiii}</td>
	  </tr>
	  <tr>
	  	<td width="13%"  align="right">二级批发商价：</td>
          <td width="19%">${paf.pricewholesale}</td>
          <td width="11%" align="right">4S价：</td>
          <td width="23%">${paf.priceivs}</td>
	      <td width="12%" align="right">加盟价：</td>
	      <td width="22%">${paf.priceuni}</td>
	  </tr>
	 
	  </table>
	</fieldset>
	
	
	
</td>
  </tr>
  <tr><td align="center"><input type="button" name="关闭" value="关闭" onClick="window.close();"></td></tr>
</table>
</body>
</html>
