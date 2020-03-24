<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
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
        <td width="772"> 仓库管理</td>
      </tr>
    </table>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="4%" height="20">&nbsp;</td>
            <td width="96%">待批准其它出库单(<a href="../warehouse/listOtherShipmentBillAction.do"><span class="text-red">${othershipmentratify}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待批准其它入库单(<a href="../warehouse/listOtherIncomeAction.do"><span class="text-red">${otherincomeratify}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>调拨待发货(<a href="../warehouse/listStockMoveAction.do"><span class="text-red">${stockmoveshipment}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>调拨待签收(<a href="../warehouse/listStockMoveAction.do"><span class="text-red">${stockmovecomplete}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待批准盘点单(<a href="../warehouse/listStockCheckAction.do"><span class="text-red">${checkratify}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待审阅材料出库(<a href="../warehouse/waitApproveAction.do"><span class="text-red">${stuffshipment}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待审阅其它出库(<a href="../warehouse/waitApproveAction.do"><span class="text-red">${othershipment}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待审阅采购入库(<a href="../warehouse/waitApproveAction.do"><span class="text-red">${purchaseincome}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待审阅产成品入库(<a href="../warehouse/waitApproveAction.do"><span class="text-red">${productincome}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待审阅其它入库(<a href="../warehouse/waitApproveAction.do"><span class="text-red">${otherincome}</span></a>)</td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td>待审阅调拨(<a href="../warehouse/waitApproveAction.do"><span class="text-red">${stockmove}</span></a>)</td>
          </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
