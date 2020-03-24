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
          <td width="772"> 待审阅信息</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td width="13%" valign="top"> 
<table width="100%" height="275"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="11%" >&nbsp;</td>
          <td width="89%"  class="CC"><a href="waitSaleLogAction.do" target="waitapprove">销售单(${salelog})</a></td>
        </tr>
        <tr>
          <td >&nbsp;</td>
          <td  class="CC"><a href="waitSampleBillAction.do" target="waitapprove">样品(${samplebill})</a></td>
        </tr>
        <tr>
          <td >&nbsp;</td>
          <td  class="CC"><a href="waitApproveShipmentBillAction.do" target="waitapprove">出库单(${shipmentbill})</a></td>
        </tr>
        <tr> 
          <td >&nbsp;</td>
          <td  class="CC"><a href="waitApproveIncomeLogAction.do" target="waitapprove">收款单(${incomelog})</a></td>
        </tr>
        <!--<tr> 
          <td >&nbsp;</td>
          <td  class="CC"><a href="waitPurchaseApplyAction.do" target="waitapprove">采购申请(${purchaseapply})</a></td>
        </tr>-->
        <tr> 
          <td >&nbsp;</td>
          <td  class="CC"><a href="waitPurchaseBillAction.do" target="waitapprove">采购单(${purchasebill})</a></td>
        </tr>
        <tr>
          <td >&nbsp;</td>
          <td  class="CC"><a href="waitApproveProductIncomeAction.do" target="waitapprove">入库单(${productincome})</a></td>
        </tr>
        <tr> 
          <td >&nbsp;</td>
          <td  class="CC"><a href="waitApprovePaymentLogAction.do" target="waitapprove">付款单(${payment})</a></td>
        </tr>
        <tr> 
          <td >&nbsp;</td>
          <td  class="CC"><a href="waitApproveOutlayAction.do" target="waitapprove">费用单(${outlay})</a></td>
        </tr>
		<tr> 
          <td >&nbsp;</td>
          <td  class="CC"><a href="waitApproveChangeBillAction.do" target="waitapprove">换货单(${changebill})</a></td>
        </tr>
		<tr> 
          <td >&nbsp;</td>
          <td  class="CC"><a href="waitApproveWithdrawAction.do" target="waitapprove">退货单(${withdraw})</a></td>
        </tr>
        <tr> 
          <td >&nbsp;</td>
          <td  class="CC"><a href="waitApproveWorkReportAction.do" target="waitapprove">工作报告(${workreport})</a></td>
        </tr>
      </table></td>
    <td width="87%">
      <IFRAME id="phonebook" 
      style="WIDTH: 100%; HEIGHT: 100%" 
      name="waitapprove" src="../self/waitApproveWorkReportAction.do" frameBorder=0 
      scrolling=auto></IFRAME></td>
  </tr>
</table>
</body>
</html>
