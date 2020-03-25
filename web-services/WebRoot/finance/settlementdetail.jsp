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
function Audit(sid){
			window.open("../finance/auditSettlementAction.do?SID="+sid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(sid){
			window.open("../finance/cancelAuditSettlementAction.do?SID="+sid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function Ratify(sid){
			window.open("../finance/ratifySettlementAction.do?SID="+sid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelRatify(sid){
			window.open("../finance/cancelRatifySettlementAction.do?SID="+sid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="976"> 结算单详情 </td>
          <td width="257" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><c:choose>
                  <c:when test="${sf.isaudit==0}"><a href="javascript:Audit('${sf.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${sf.id}')">取消复核</a></c:otherwise>
              </c:choose></td>
              <td width="60" align="center"><c:choose>
                  <c:when test="${sf.isratify==0}"><a href="javascript:Ratify('${sf.id}')">批准</a></c:when>
                  <c:otherwise>已经批准<!--<a href="javascript:CancelRatify('${sf.id}')">取消批准</a>--></c:otherwise>
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
	  	<td width="9%"  align="right">供应商：</td>
          <td width="24%">${sf.providename}</td>
          <td width="10%" align="right">结算总金额：</td>
          <td width="24%">${sf.settlementsum}</td>
	      <td width="8%" align="right">备注：</td>
	      <td width="25%">${sf.remark}</td>
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
	  	<td width="9%"  align="right">结算人：</td>
          <td width="24%">${sf.settlementidname}</td>
          <td width="10%" align="right">结算日期：</td>
          <td width="24%">${sf.settlementdate}</td>
	      <td width="8%" align="right">是否复核：</td>
	      <td width="25%">${sf.isauditname}</td>
	  </tr>
	  <tr>
	    <td  align="right">复核人：</td>
	    <td>${sf.auditidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${sf.auditdate}</td>
	    <td align="right">是否批准：</td>
	    <td>${sf.isratifyname}</td>
	    </tr>
	  <tr>
	    <td  align="right">批准人：</td>
	    <td>${sf.ratifyidname}</td>
	    <td align="right">批准日期：</td>
	    <td>${sf.ratifydate}</td>
	    <td align="right">是否提交：</td>
	    <td>${sf.isrefername}</td>
	    </tr>
	  <tr>
	    <td  align="right">是否审阅：</td>
	    <td>${sf.approvestatusname}</td>
	    <td align="right">审阅日期：</td>
	    <td>${sf.approvedate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="90" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>结算单子项列表</td>
        </tr>
      </table>
	  </legend>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="14%">发票号</td> 
          <td width="14%">产品编号</td>
          <td width="31%" >产品名称</td>
          <td width="17%">结算数量</td>
          <td width="9%">结算单价</td>
          <td width="15%">结算金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back">
          <td>${p.invoiceid}</td> 
          <td>${p.productid}</td>
          <td >${p.productidname}</td>
          <td>${p.settlementquantity}</td>
          <td>${p.settlementprice}</td>
          <td>${p.settlementsum}</td>
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
          <td width="8%" >是否审阅</td>
          <td width="9%">审阅者</td>
          <td width="12%">审阅动作</td>
          <td width="71%">审阅内容</td>
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
