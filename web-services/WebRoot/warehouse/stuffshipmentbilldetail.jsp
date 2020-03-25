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
	function Audit(ssid){
			window.open("../warehouse/auditStuffShipmentBillAction.do?SSID="+ssid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(ssid){
			window.open("../warehouse/cancelAuditStuffShipmentBillAction.do?SSID="+ssid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function StuffShipmentBill(ssid){
			window.open("../warehouse/stuffShipmentBillAction.do?ID="+ssid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="1015"> 材料出库单详情 </td>
          <td width="218" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:StuffShipmentBill('${ssbf.id}');">打印</a></td>
              <td width="60" align="center"><c:choose>
                  <c:when test="${ssbf.isaudit==0}"><a href="javascript:Audit('${ssbf.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${ssbf.id}')">取消复核</a></c:otherwise>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">出货仓库：</td>
          <td width="25%">${ssbf.warehouseidname}</td>
          <td width="9%" align="right">出库类别：</td>
          <td width="23%">${ssbf.shipmentsortname}</td>
	      <td width="9%" align="right">出库部门：</td>
	      <td width="25%">${ssbf.shipmentdeptname}</td>
	  </tr>
	  <tr>
	    <td  align="right">需求日期：</td>
	    <td>${ssbf.requiredate}</td>
	    <td align="right">备注：</td>
	    <td>${ssbf.remark} </td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
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
          <td width="25%">${ssbf.makeidname}</td>
          <td width="9%" align="right">制单日期：</td>
          <td width="23%">${ssbf.makedate}</td>
	      <td width="9%" align="right">是否复核：</td>
	      <td width="25%">${ssbf.isauditname}</td>
	  </tr>
	  <tr>
	    <td  align="right">复核人：</td>
	    <td>${ssbf.auditidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${ssbf.auditdate}</td>
	    <td align="right">是否提交：</td>
	    <td>${ssbf.isrefername}</td>
	    </tr>
	  <tr>
	    <td  align="right">是否审阅：</td>
	    <td>${ssbf.approvestatusname}</td>
	    <td align="right">审阅日期：</td>
	    <td>${ssbf.approvedate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>材料出库单产品列表</td>
        </tr>
      </table>
	  </legend>

<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="13%">产品编号</td> 
          <td width="21%" >产品名称</td>
          <td width="23%">规格</td>
          <td width="8%">单位</td>
          <td width="11%">批次</td>
          <td width="6%">单价</td>
          <td width="6%">数量</td>
          <td width="12%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back">
          <td >${p.productid}</td> 
          <td  >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.unitidname}</td>
          <td>${p.batch}</td>
          <td>${p.unitprice}</td>
          <td>${p.quantity}</td>
          <td>${p.subsum}</td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>审阅信息</td>
        </tr>
      </table>
	  </legend>
<table width="100%"  border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="title-top">
    <td width="9%" >是否审阅</td>
    <td width="8%">审阅者</td>
    <td width="13%">动作</td>
    <td width="70%">审阅内容</td>
  </tr>
  <logic:iterate id="r" name="rvls" >
  <tr class="table-back">
    <td  >${r.approvename}</td>
    <td>${r.approveidname}</td>
    <td>${r.actidname}</td>
    <td>${r.approvecontent}</td>
  </tr>
  </logic:iterate>
</table>
</fieldset>

</td>
  </tr>
</table>
</body>
</html>
