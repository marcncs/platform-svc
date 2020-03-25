<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
</head>
<script language="javascript">
	function Audit(siid){
			window.open("../sales/auditSaleInvoiceAction.do?SIID="+siid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(siid){
			window.open("../sales/cancelAuditSaleInvoiceAction.do?SIID="+siid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}

</script>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="957"> 零售发票详情 </td>
          <td width="276" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><c:choose>
                <c:when test="${sif.isaudit==0}"><a href="javascript:Audit('${sif.id}');">复核</a></c:when>
                <c:otherwise> <a href="javascript:CancelAudit('${sif.id}')">取消复核</a></c:otherwise>
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
	  	<td width="9%"  align="right">客户名称：</td>
          <td width="26%">${sif.cname}</td>
          <td width="9%" align="right">发票编号：</td>
          <td width="20%">${sif.invoicecode}</td>
	      <td width="11%" align="right">发票类型：</td>
	      <td width="25%"><windrp:getname key='InvoiceType' value='${sif.invoicetype}' p='f'/></td>
	  </tr>
	  <tr>
	    <td  align="right">发票金额：</td>
	    <td>${sif.invoicesum}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${sif.memo}</td>
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
	    <td  align="right">制单机构：</td>
	    <td><windrp:getname key='organ' value='${sif.makeorganid}' p='d'/></td>
	    <td align="right">制单部门：</td>
	    <td><windrp:getname key='dept' value='${sif.makedeptid}' p='d'/></td>
	    <td align="right">制单人：</td>
	    <td><windrp:getname key='users' value='${sif.makeid}' p='d'/></td>
	    </tr>
	  <tr>
	  	<td width="9%"  align="right">制单日期：</td>
          <td width="26%">${sif.makedate}</td>
          <td width="9%" align="right">制票日期：</td>
          <td width="20%">${sif.makeinvoicedate}</td>
	      <td width="11%" align="right">开票日期：</td>
	      <td width="25%">${sif.invoicedate}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否复核：</td>
	    <td><windrp:getname key='YesOrNo' value='${sif.isaudit}' p='f'/></td>
	    <td align="right">复核人：</td>
	    <td><windrp:getname key='users' value='${sif.auditid}' p='d'/></td>
	    <td align="right">复核日期：</td>
	    <td>${sif.auditdate}</td>
	    </tr>
	  </table>
</fieldset>
	  
	  <fieldset align="center"> <legend>
      <table width="80" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="80">零售发票明细</td>
        </tr>
      </table>
	  </legend>



	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td width="9%">零售单号</td> 
          <td width="24%" >金额</td>
          <td width="34%">制单日期</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.soid}</td> 
          <td align="right"><windrp:format value="${p.subsum}" p="###,##0.00"/></td>
          <td>${p.makedate}</td>
          </tr>
        </logic:iterate> 
      </table>
	  </fieldset>

</td>
  </tr>
</table>
</body>
</html>
