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
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="37" colspan="2"><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 待审阅提交信息</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td width="13%" valign="top"> 
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="11%" >&nbsp;</td>
          <td width="89%"  class="CC"><a href="../sales/waitSaleOrderAction.do" target="waitapprove">零售单(${saleorder})</a></td>
        </tr>
        <tr> 
          <td >&nbsp;</td>
          <td  class="CC"><a href="../sales/waitWithdrawAction.do" target="waitapprove">退货单(${withdraw})</a></td>
        </tr>
		<tr> 
          <td >&nbsp;</td>
          <td  class="CC"><a href="../sales/waitOutlayAction.do" target="waitapprove">费用单(${outlay})</a></td>
        </tr>
      </table></td>
    <td width="87%">
      <IFRAME id="phonebook" 
      style="WIDTH: 100%; HEIGHT: 100%" 
      name="waitapprove" src="../sys/remind.htm" frameBorder=0 
      scrolling=auto></IFRAME></td>
  </tr>
</table>
</body>
</html>
