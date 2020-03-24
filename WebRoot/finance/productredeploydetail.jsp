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
function Audit(smid){
			window.open("../finance/auditProductRedeployAction.do?SMID="+smid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(smid){
			window.open("../finance/cancelAuditProductRedeployAction.do?SMID="+smid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function Shipment(smid){
			window.open("../warehouse/completeStockMoveShipmentAction.do?SMID="+smid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelShipment(smid){
			window.open("../warehouse/cancelCompleteStockMoveShipmentAction.do?SMID="+smid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	function Receive(smid){
			window.open("../warehouse/completeStockMoveReceiveAction.do?SMID="+smid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelReceive(smid){
			window.open("../warehouse/cancelCompleteStockMoveReceiveAction.do?SMID="+smid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="869"> 产品调价单详情 </td>
          <td width="364" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><c:choose>
                  <c:when test="${prf.isaudit==0}"><a href="javascript:Audit('${prf.id}');">复核</a></c:when>
                  
              </c:choose></td>
              </tr>
          </table></td>
  </tr>
</table>

	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="92%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	    <td width="8%"  align="right">调价人：</td>
	    <td width="18%">${prf.redeployidname}</td>
	  	<td width="9%" align="right">调价部门：</td>
	  	<td width="18%">${prf.redeploydeptname}</td>
	  	<td width="10%" align="right">调拨原因：</td>
	      <td width="37%">${prf.redeploymemo}</td>
	  </tr>
	  
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>状态信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">制单人：</td>
          <td width="25%">${prf.makeidname}</td>
          <td width="9%" align="right">制单日期：</td>
          <td width="23%">${prf.makedatename}</td>
	      <td width="9%" align="right">是否复核：</td>
	      <td width="25%">${prf.isauditname}</td>
	  </tr>
	  <tr>
	    <td  align="right">复核人：</td>
	    <td>${prf.auditidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${prf.auditdatename}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="90" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>调拨单产品列表</td>
        </tr>
      </table>
	  </legend>

      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="7%">产品编号</td>
          <td width="10%" >产品名称</td>
		   <td width="10%" >规格</td>
		   <td width="3%" >单位</td>
          <td width="7%">标准采购价</td>
          <td width="7%">标准销售价</td>
          <td width="7%">一级价</td>
		  <td width="7%">二级价</td>
          <td width="7%">三级价</td>
          <td width="9%">二级批发商价</td>
          <td width="5%">4S价</td>
		  <td width="7%">加盟价</td>
          <td width="7%">最低销售价</td>
          <td width="10%">产品成本</td>
        </tr>
        <logic:iterate id="p" name="prdls" >
          <tr class="table-back">
            <td>${p.id}</td>
            <td >${p.productidname}</td>
			<td>${p.specmode}</td>
			<td>${p.countunitname}</td>
            <td>${p.standardpurchase}</td>
            <td>${p.standardsale}</td>
            <td>${p.pricei}</td>
            <td>${p.priceii}</td>
            <td>${p.priceiii}</td>
            <td>${p.pricewholesale}</td>
			<td>${p.priceivs}</td>
			<td>${p.priceuni}</td>
			<td>${p.leastsale}</td>
			<td>${p.cost}</td>
          </tr>
        </logic:iterate>
      </table>
	  </fieldset>
	  

	  
      
</td>
  </tr>
</table>
</body>
</html>
