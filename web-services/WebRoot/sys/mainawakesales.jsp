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
        <td width="772"> 销售管理 </td>
      </tr>
    </table>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="4%" height="20">&nbsp;</td>
            <td width="96%">待复核销售订单(<a href="../sales/listSaleOrderAction.do?IsAudit=0"><span <c:if test="${saleorderaudit>0}">class="text-red"</c:if>>${saleorderaudit}</span></a>)</td>
          </tr>
      </table></td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
        <td width="772"> 账务管理 </td>
      </tr>
    </table>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <!--  <tr>
            <td width="4%" height="20">&nbsp;</td>
            <td width="96%">待复核应收款(<a href="../finance/listReceivableAllAction.do?IsAudit=0"><span <c:if test="${receivableaudit>0}">class="text-red"</c:if>>${receivableaudit}</span></a>)</td>
          </tr>  -->
          <tr>
            <td height="20">&nbsp;</td>
            <td>待复核收款(<a href="../finance/listIncomeLogAction.do?IsAudit=0"><span <c:if test="${incomelogaudit>0}">class="text-red"</c:if>>${incomelogaudit}</span></a>)</td>
          </tr>
        <!--  <tr>
            <td height="20">&nbsp;</td>
            <td>待复核应付款(<a href="../finance/listPayableAllAction.do?IsAudit=0"><span <c:if test="${payableaudit>0}">class="text-red"</c:if>>${payableaudit}</span></a>)</td>
          </tr> -->
          <tr>
            <td height="20">&nbsp;</td>
            <td>待复核付款(<a href="../finance/listPaymentLogAction.do?IsAudit=0"><span <c:if test="${paymentlogaudit>0}">class="text-red"</c:if>>${paymentlogaudit}</span></a>)</td>
          </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
