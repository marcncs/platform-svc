<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>WINDRP-分销系统</title>

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>

</head>
<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="wb" name="wb" width="0"></OBJECT>
<body>
<table width="906" height="440" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="896" valign="top" background="images/sendgoods.gif"><table width="100%" height="123" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="11%" height="54">&nbsp;</td>
        <td width="20%">&nbsp;</td>
        <td width="9%">&nbsp;</td>
        <td width="24%">&nbsp;</td>
        <td width="11%">&nbsp;</td>
        <td width="25%"><table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="25%">&nbsp;</td>
            <td width="28%">&nbsp;</td>
            <td width="15%"><a href="#"><img src="../images/CN/empty.gif" width="31" height="18" border="0" onClick="wb.execwb(6,1);" title="打印"></a></td>
            <td width="32%"><a href="#"><img src="../images/CN/empty.gif" width="27" height="19" border="0" onClick="wb.execwb(7,1);" title="打印预览"></a></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td >&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>${sb.id}</td>
      </tr>
      <tr>
        <td >&nbsp;</td>
        <td>${receivename}</td>
        <td>&nbsp;</td>
        <td>${sb.linkman}</td>
        <td>&nbsp;</td>
        <td>${sb.invoicecode}</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>${sb.receiveaddr}</td>
        <td>&nbsp;</td>
        <td>${sb.tel}</td>
        <td>&nbsp;</td>
        <td>${sb.shipmentdate}</td>
      </tr>
    </table>
	<font style="height:170; width:800;overflow-x:no; overflow-y:no;">
      <table width="800" height="51" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="10%"  align="center">&nbsp;</td>
          <td width="26%">&nbsp;</td>
          <td width="1%">&nbsp;</td>
          <td width="11%">&nbsp;</td>
          <td width="10%">&nbsp;</td>
          <td width="12%">&nbsp;</td>
          <td width="22%">&nbsp;</td>
          <td width="8%">&nbsp;</td>
        </tr>
		<logic:iterate id="a" name="als" >
        <tr>
          <td  align="center">${a.id}</td>
          <td>${a.productname}</td>
          <td>&nbsp;</td>
          <td>${a.unitname}</td>
          <td>${a.quantity}</td>
          <td>${a.unitprice}</td>
          <td>${a.subsum}</td>
          <td>&nbsp;</td>
        </tr>
		</logic:iterate>
      </table>
	  </font>
	<table width="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9%">&nbsp;</td>
        <td width="47%">${sb.totalsumcapital}</td>
        <td width="7%">&nbsp;</td>
        <td width="37%">${sb.totalsum}</td>
      </tr>
    </table>    </td>
  </tr>
</table>
</body>
</html>
