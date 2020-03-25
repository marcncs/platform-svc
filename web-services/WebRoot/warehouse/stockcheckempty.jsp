<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="application/vnd.ms-excel;charset=UTF-8" %>  
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<html>
<head>

<meta Content-Disposition="attachment; filename=stockempty.xls">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>WINDRP-分销系统</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
}
-->
</style>
</head>

<body>
<table width="100%" height="49" border="1" cellpadding="0" cellspacing="0" bordercolordark="#FFFFFF" bordercolorlight="#000000">
  <tr>
    <td width="10%"  align="center" class="back-gray">产品编号</td>
    <td width="26%" align="center" class="back-gray">产品名称</td>
    <td width="13%" align="center" class="back-gray">规格</td>
    <td width="10%" align="center" class="back-gray">单位编号</td>
    <td width="12%" align="center" class="back-gray">单位</td>
    <td width="15%" align="center" class="back-gray">账面数量</td>
    <td width="14%" align="center" class="back-gray">盘点数量</td>
  </tr>
  <logic:iterate id="p" name="alp" > 
  <tr>
    <td>${p.productid}</td>
    <td>${p.psproductname}</td>
    <td>${p.psspecmode}</td>
    <td>${p.countunit}</td>
    <td>${p.unitname}</td>
    <td>${p.stockpile}</td>
    <td>&nbsp;</td>
  </tr>
  </logic:iterate> 
</table>
</body>
</html>
