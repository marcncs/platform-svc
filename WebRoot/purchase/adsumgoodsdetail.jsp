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
			window.open("../purchase/auditAdsumGoodsAction.do?AGID="+pbid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(pbid){
			window.open("../purchase/cancelAuditAdsumGoodsAction.do?AGID="+pbid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="1003"> 到货通知详情 </td>
          <td width="120" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><c:choose>
                  <c:when test="${pbf.isaudit==0}"><a href="javascript:Audit('${pbf.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${pbf.id}')">取消复核</a></c:otherwise>
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
          <td width="23%">${pbf.pidname}</td>
          <td width="11%" align="right">联系人：</td>
          <td width="22%">${pbf.plinkman}</td>
	      <td width="13%" align="right">供应商送货单号：</td>
	      <td width="22%">${pbf.obtaincode}</td>
	  </tr>
	  <tr>
	    <td  align="right">收货日期：</td>
	    <td>${pbf.receivedate}</td>
	    <td align="right">采购部门：</td>
	    <td>${pbf.purchasedeptname}</td>
	    <td align="right">采购人员：</td>
	    <td>${pbf.purchaseidname}</td>
	    </tr>
	  <tr>
	    <td  align="right">相关采购单：</td>
	    <td>${pbf.poid}</td>
	    <td align="right">总金额：</td>
	    <td>${pbf.totalsum}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${pbf.remark}</td>
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
          <td width="23%">${pbf.makeidname}</td>
          <td width="11%" align="right">制单日期：</td>
          <td width="23%">${pbf.makedate}</td>
	      <td width="12%" align="right">是否复核：</td>
	      <td width="22%">${pbf.isauditname}</td>
	  </tr>
	  <tr>
	    <td  align="right">复核人：</td>
	    <td>${pbf.auditidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${pbf.auditdate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="100" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>到货通知产品列表</td>
        </tr>
      </table>
	  </legend>
<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="12%">产品编号</td> 
          <td width="24%" >产品名称</td>
          <td width="26%">规格</td>
          <td width="13%">单位</td>
          <td width="7%">单价</td>
          <td width="6%">数量</td>
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
