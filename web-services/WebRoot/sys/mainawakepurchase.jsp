<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>WINDRP-分销系统</title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
        <td width="772"> 产品采购 </td>
      </tr>
    </table>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="4%" height="20">&nbsp;</td>
            <td width="96%">待批准采购计划(<a href="../purchase/listPurchasePlanAction.do"><span class="text-red">${purchaseplanratify}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待批准采购单(<a href="../purchase/listPurchaseBillAllAction.do"><span class="text-red">${purchasebillratify}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待审阅采购计划(<a href="../purchase/waitApproveAction.do"><span class="text-red">${purchaseplan}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待审阅采购单(<a href="../purchase/waitApproveAction.do"><span class="text-red">${purchasebill}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待审阅到货通知(<a href="../purchase/waitApproveAction.do"><span class="text-red">${purchasewithdraw}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待审阅采购退货(<a href="../purchase/waitApproveAction.do"><span class="text-red">${adsumgoods}</span></a>)</td>
          </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
