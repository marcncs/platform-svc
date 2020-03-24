<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>领用出库单详情</title>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script language="javascript">
	function Audit(osid){
			popWin2("../warehouse/auditDrawShipmentBillAction.do?OSID="+osid);
	}
	
	function CancelAudit(osid){
			popWin2("../warehouse/cancelAuditDrawShipmentBillAction.do?OSID="+osid);
	}
	
	function print(ssid){
			popWin("../warehouse/printDrawShipmentBillAction.do?ID="+ssid,900,600);
	}
	
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="967"> 领用出库单详情 </td>
          <td width="266" align="right"><table width="240"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:print('${osbf.id}');">打印</a></td>
              <td width="60" align="center"><c:choose>
                  <c:when test="${osbf.isaudit==0}"><a href="javascript:Audit('${osbf.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${osbf.id}')">取消复核</a></c:otherwise>
              </c:choose></td>
			  <td width="60" align="center"><c:choose>
                  <c:when test="${osbf.isendcase==0}">结案</c:when>
                  <c:otherwise>取消结案</c:otherwise>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">出货仓库：</td>
          <td width="25%">${osbf.warehouseidname}</td>
          <td width="9%" align="right">领用日期：</td>
          <td width="23%">${osbf.drawdate}</td>
	      <td width="9%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${osbf.remark}</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">制单机构：</td>
          <td width="25%">${osbf.makeorganidname}</td>
          <td width="9%" align="right">制单人：</td>
          <td width="23%">${osbf.makeidname}</td>
	      <td width="9%" align="right">制单日期：</td>
	      <td width="25%">${osbf.makedate}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否复核：</td>
	    <td>${osbf.isauditname}</td>
	    <td align="right">复核人：</td>
	    <td>${osbf.auditidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${osbf.auditdate}</td>
	    </tr>
		<tr>
	    <td  align="right">是否结案：</td>
	    <td>${osbf.isendcasename}</td>
	    <td align="right">结案人：</td>
	    <td>${osbf.endcaseidname}</td>
	    <td align="right">结案日期：</td>
	    <td>${osbf.endcasedate}</td>
	    </tr>
	  	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>领用出库单产品列表</td>
        </tr>
      </table>
	  </legend>

	<table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="16%">产品编号</td> 
          <td width="24%" >产品名称</td>
          <td width="22%">规格</td>
          <td width="8%">单位</td>
         <!-- <td width="6%">单价</td>-->
          <td width="6%">数量</td>
		   <td width="6%">检货数量</td>
          <!--<td width="12%">金额</td>-->
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.unitidname}</td>
         <!-- <td>${p.unitprice}</td>-->
          <td>${p.quantity}</td>
		  <td>${p.takequantity}</td>
         <!-- <td>${p.subsum}</td>-->
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>
	  

</td>
  </tr>
</table>
</body>
</html>
