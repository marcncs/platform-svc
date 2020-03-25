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
<title>WINDRP-分销系统</title>
<script language="javascript">
function Audit(smid){
			window.open("../warehouse/auditAlterMoveApplyAction.do?AAID="+smid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(smid){
			window.open("../warehouse/cancelAuditAlterMoveApplyAction.do?AAID="+smid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function Ratify(smid){
			window.open("../warehouse/toRatifyAlterMoveApplyAction.do?AAID="+smid,"newwindow","height=650,width=1000,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelRatify(smid){
			window.open("../warehouse/cancelRatifyAlterMoveApplyAction.do?AAID="+smid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="869"> 批准机构机构间转仓申请单详情 </td>
          <td width="364" align="right"><table  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td style="float: right; padding-right: 20px;"><c:choose>
                  <c:when test="${smf.isratify==0}"><a href="javascript:Ratify('${smf.id}');">批准</a></c:when>
                  <c:otherwise><a href="javascript:CancelRatify('${smf.id}')">取消批准</a></c:otherwise>
              </c:choose> &nbsp;&nbsp;<c:choose><c:when test="${smf.isblankout==0}">作废</c:when><c:otherwise>
<font color="#FF0000">已作废</font></c:otherwise>
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
	  	<td width="9%"  align="right">机构间转仓日期：</td>
          <td width="25%">${smf.movedate}</td>
          <td width="9%" align="right">调出机构：</td>
          <td width="23%">${smf.outorganidname}</td>
	      <td width="9%" align="right">调入(制单)机构：</td>
	      <td width="25%">${smf.makeorganidname}</td>
	  </tr>
	  <tr>
	    <td  align="right">付款方式：</td>
	    <td>${smf.paymentmodename}</td>
	    <td align="right">开票信息：</td>
	    <td>${smf.invmsgname}</td>
	    <td align="right">发票抬头：</td>
	    <td>${smf.tickettitle}</td>
	  </tr>
	  <tr>
        <td  align="right">发运方式：</td>
	    <td>${smf.transportmodename}</td>
	    <td align="right">总金额：</td>
	    <td>${smf.totalsum}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">收货地址：</td>
	    <td>${smf.transportaddr}</td>
	    <td align="right">&nbsp;</td>
	    <td colspan="3">&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">机构间转仓原因：</td>
	    <td colspan="5">${smf.movecause}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${smf.remark}</td>
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
          <td width="25%">${smf.makeidname}</td>
          <td width="9%" align="right">制单日期：</td>
          <td width="23%">${smf.makedate}</td>
	      <td width="9%" align="right">是否复核：</td>
	      <td width="25%">${smf.isauditname}</td>
	  </tr>
	  <tr>
	    <td  align="right">复核人：</td>
	    <td>${smf.auditidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${smf.auditdate}</td>
	    <td align="right">是否批准：</td>
	    <td>${smf.isratifyname}</td>
	    </tr>
	  <tr>
	    <td  align="right">批准人：</td>
	    <td>${smf.ratifyidname}</td>
	    <td align="right">批准日期：</td>
	    <td>${smf.ratifydate}</td>
	    <td align="right">是否转机构间转仓：</td>
	    <td>${smf.istransname}</td>
	    </tr>
	  <tr>
	    <td  align="right">是否作废：</td>
	    <td>${smf.isblankoutname}</td>
	    <td align="right">作废人：</td>
	    <td>${smf.blankoutidname}</td>
	    <td  align="right">作废日期：</td>
	    <td>${smf.blankoutdate}</td>
	    </tr>
	  <tr>
	    <td  align="right">作废原因：</td>
	    <td  colspan="5">${smf.blankoutreason}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="122" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="122">机构间转仓申请单产品列表</td>
        </tr>
      </table>
	  </legend>

      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="10%">产品编号</td>
          <td width="24%" >产品名称</td>
          <td width="21%">规格</td>
          <td width="7%">单位</td>
          <td width="6%">单价</td>
          <td width="6%">数量</td>
          <td width="10%">批准数量</td>
          <td width="8%">已转数量</td>
          <td width="8%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" >
          <tr class="table-back">
            <td>${p.productid}</td>
            <td >${p.productname}</td>
            <td>${p.specmode}</td>
            <td>${p.unitidname}</td>
            <td>${p.unitprice}</td>
            <td>${p.quantity}</td>
            <td>${p.canquantity}</td>
            <td>${p.alreadyquantity}</td>
            <td>${p.subsum}</td>
          </tr>
        </logic:iterate>
      </table>
	  </fieldset>
	  

	  
      
</td>
  </tr>
</table>
</body>
</html>
