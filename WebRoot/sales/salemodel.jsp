<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>NPS</title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 零售管理视图 </td>
      </tr>
    </table>
      <table width="100%" height="373" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="42" height="175">&nbsp;</td>
          <td width="830"><table height="126" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td valign="top"><img src="../images/model/demandpricesaleorder.jpg" width="56" height="189" border="0" usemap="#Map"></td>
              <td valign="top"><img src="../images/model/saleordertoshipment.jpg" width="79" height="189"></td>
              <td valign="top"><img src="../images/model/saleshipment.jpg" width="62" height="189" border="0" usemap="#Map2"></td>
              <td valign="top"><img src="../images/model/shipmenttoinvoice.jpg" width="92" height="189"></td>
              <td valign="top"><img src="../images/model/saleinvoice.jpg" width="56" height="189" border="0" usemap="#Map3"></td>
              <td valign="top">&nbsp;</td>
              <td valign="top">&nbsp;</td>
            </tr>
          </table></td>
          <td width="371">&nbsp;</td>
        </tr>
        <tr>
          <td height="79">&nbsp;</td>
          <td><table width="432" height="137" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="137">&nbsp;</td>
              <td width="157" height="31">&nbsp;</td>
              <td width="138">&nbsp;</td>
            </tr>
            <tr>
              <td><img src="../images/model/samplebill.jpg" width="56" height="80" border="0" usemap="#Map5"></td>
              <td><img src="../images/model/salewithdraw.jpg" width="62" height="80" border="0" usemap="#Map6"></td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td >&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
          </table>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="100">&nbsp;</td>
          <td><table width="62%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="11" height="16" background="../images/model/bottom_01.gif"></td>
              <td width="479" background="../images/model/bottom_02.gif"></td>
              <td width="23" height="16" background="../images/model/bottom_03.gif"></td>
            </tr>
            <tr>
              <td background="../images/model/bottom_04.gif">&nbsp;</td>
              <td><table width="100%" height="52" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="26%"><img src="../images/model/waitdeal.jpg" width="56" height="60" border="0" usemap="#Map8"></td>
                  <td width="33%"><img src="../images/model/customer.jpg" width="62" height="60" border="0" usemap="#Map9"></td>
                  <td width="34%"><img src="../images/model/product.jpg" width="56" height="60" border="0" usemap="#Map10"></td>
                  <td width="7%">&nbsp;</td>
                </tr>
              </table></td>
              <td background="../images/model/bottom_06.gif">&nbsp;</td>
            </tr>
            <tr>
              <td width="11" height="17px" background="../images/model/bottom_07.gif"></td>
              <td background="../images/model/bottom_08.gif"></td>
              <td width="23" height="17px" background="../images/model/bottom_09.gif"></td>
            </tr>
          </table></td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>

<map name="Map">
  <area shape="rect" coords="2,9,50,75" href="../sales/listSaleOrderAction.do"><area shape="rect" coords="3,111,52,184" href="../sales/listDemandPriceAction.do">
</map>
<map name="Map2"><area shape="rect" coords="3,2,60,77" href="../warehouse/listShipmentBillAction.do">
<area shape="rect" coords="5,116,60,185" href="../purchase/listPurchasePlanAction.do">
</map>
<map name="Map3"><area shape="rect" coords="3,4,53,76" href="../sales/listSaleInvoiceAction.do">
<area shape="rect" coords="4,116,53,186" href="../finance/listReceivableObjectAction.do">
</map>
<map name="Map5"><area shape="rect" coords="3,2,53,77" href="../sales/listSampleBillAction.do">
</map>
<map name="Map6"><area shape="rect" coords="6,3,59,76" href="../sales/listWithdrawAction.do">
</map>
<map name="Map8"><area shape="rect" coords="3,5,52,57" href="../sales/waitApproveAction.do">
</map>
<map name="Map9"><area shape="rect" coords="8,4,58,56" href="../sales/listCustomerAction.do">
</map>
<map name="Map10"><area shape="rect" coords="7,4,53,57" href="../sys/listProductStructAction.do">
</map></body>
</html>
