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
function Audit(pbid){
			window.open("../purchase/auditPurchaseBillAction.do?PBID="+pbid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(pbid){
			window.open("../purchase/cancelAuditPurchaseBillAction.do?PBID="+pbid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function Ratify(pbid){
			window.open("../purchase/ratifyPurchaseBillAction.do?PBID="+pbid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelRatify(pbid){
			window.open("../purchase/cancelRatifyPurchaseBillAction.do?PBID="+pbid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function PurchaseOrder(pbid){
			window.open("../purchase/printPurchaseOrderAction.do?ID="+pbid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="1003"> 采购订单详情 </td>
          <td width="230" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:PurchaseOrder('${pbf.id}');">打印</a></td>
       <!--       <td width="60" align="center"><c:choose>
                  <c:when test=""><a href="javascript:Audit('');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('')">取消复核</a></c:otherwise>
              </c:choose></td>
              <td width="60" align="center"><c:choose>
                  <c:when test=""><a href="javascript:Ratify('')">批准</a></c:when>
                  <c:otherwise><a href="javascript:CancelRatify('')">取消批准</a></c:otherwise>
              </c:choose></td>  -->
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
	  	<td width="9%"  align="right">供应商：</td>
          <td width="21%">${pbf.providename}</td>
          <td width="13%" align="right">联系人：</td>
          <td width="23%">${pbf.plinkman}</td>
	      <td width="11%" align="right">联系电话：</td>
	      <td width="23%">${pbf.tel}</td>
	  </tr>
	  <tr>
	    <td  align="right">收货地址：</td>
	    <td >${pbf.receiveaddr}</td>
		<td width="11%" align="right">预计收货日期：</td>
	      <td width="23%">${pbf.receivedate}</td>
	    <td align="right">批次：</td>
	    <td>${pbf.batch}</td>
	    </tr>
	  <tr>
	    <td  align="right">采购部门：</td>
	    <td>${pbf.purchasedeptname}</td>
	    <td align="right">采购人员：</td>
	    <td>${pbf.purchaseidname}</td>
	    <td align="right">付款方式：</td>
	    <td>${pbf.paymentmodename}</td>
	    </tr>
	  <tr>
	    <td  align="right">总金额：</td>
	    <td>${pbf.totalsum}</td>
	    <td align="right">备注：</td>
	    <td>${pbf.remark}</td>
		 <td align="right">采购询价编号：</td>
	    <td>${pbf.ppid}</td>
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
	  	<td width="9%"  align="right">制单人：</td>
          <td width="21%">${pbf.makeidname}</td>
          <td width="13%" align="right">制单日期：</td>
          <td width="23%">${pbf.makedate}</td>
	      <td width="11%" align="right">审阅状况：</td>
	      <td width="23%">${pbf.approvestatusname}</td>
	  </tr>
	  <tr>
	    <td  align="right">审阅日期：</td>
	    <td>${pbf.approvedate}</td>
	    <td align="right">是否结案：</td>
	    <td>${pbf.isendcasename}</td>
	    <td align="right">结案日期：</td>
	    <td>${pbf.endcasedate}</td>
	    </tr>
	  <tr>
	  	<td width="9%"  align="right">结案人：</td>
          <td width="21%">${pbf.endcaseidname}</td>
          <td width="13%" align="right">是否作废：</td>
          <td width="23%">${pbf.isblankoutname}</td>
	      <td width="11%" align="right">作废日期：</td>
	      <td width="23%">${pbf.blankoutdate}</td>
	  </tr>
	   <tr>
	  	<td width="9%"  align="right">作废人：</td>
          <td width="21%">${pbf.blankoutidname}</td>
          <td width="13%" align="right">&nbsp;</td>
          <td width="23%">&nbsp;</td>
	      <td width="11%" align="right">&nbsp;</td>
	      <td width="23%">&nbsp;</td>
	  </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="100" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>采购订单产品列表</td>
        </tr>
      </table>
	  </legend>
<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="13%">产品编号</td> 
          <td width="25%" >产品名称</td>
          <td width="30%">规格</td>
          <td width="7%">单位</td>
          <td width="7%">单价</td>
          <td width="6%">数量</td>
		   <td width="6%">入库数量</td>
          <td width="12%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.unitname}</td>
          <td>${p.unitprice}</td>
          <td>${p.quantity}</td>
		  <td>${p.incomequantity}</td>
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
    <td width="12%" >是否审阅</td>
    <td width="13%">审阅者</td>
    <td width="9%">动作</td>
    <td width="66%">审阅内容</td>
  </tr>
  <logic:iterate id="r" name="rvls" >
  <tr class="table-back">
          <td >${r.approvename}</td>
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
