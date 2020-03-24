<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/pss.css" rel="stylesheet" type="text/css">
<style media=print> 
.Noprint{display:none;} 
</style> 
</head>

<body style="overflow: auto;">
<center class="Noprint" > 
<div class="printstyle">
<img src="../images/print.gif" onClick="javascript:window.print();"></img>
</div><hr align="center" width="100%" size="1" noshade> 
</center> 
    <div id="abc">
    <div align="center" class="print_title_style">零售发票</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 <tr>
	    <td  align="right">发票类型：</td>
	    <td><windrp:getname key='InvoiceType' value='${InvoiceType}' p='f'/></td>
	     <td  align="right">是否复核：</td>
	    <td><windrp:getname key='YesOrNo' value='${IsAudit}' p='f'/></td>
	     <td  align="right">制单机构：</td>
	    <td>${oname}</td>
      </tr>
	  <tr>
	   
	    <td  align="right">制单人：</td>
	    <td>${uname}</td>
	     <td  align="right">制单日期：</td>
	    <td>${BeginDate}-${EndDate}</td>
	    <td  align="right">关键字：</td>
	    <td>${KeWord}</td>
      </tr>
	  <tr>
	    <td width="13%"  align="right">打印机构：</td>
	    <td width="21%">${porganname}</td>
	    <td width="10%"  align="right">打印人：</td>
	    <td width="23%">${pusername}</td>
	    <td width="9%"  align="right">打印时间：</td>
	    <td width="24%">${ptime}</td>
      </tr>
	  </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
          <tr align="center" class="title-top"> 
            <td width="9%"  >编号</td>
            <td width="8%" >发票编号</td>
            <td width="20%" >会员名称</td>
            <td width="11%" >发票类型</td>
            <td width="17%" >制票日期</td>
            <td width="15%" >开票日期</td>
            <td width="8%" >总金额</td>
            <td width="12%" >是否复核</td>
          </tr>
          <logic:iterate id="s" name="alsi" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,${s.id});"> 
            <td >${s.id}</td>
            <td>${s.invoicecode}</td>
            <td>${s.cname}</td>
            <td><windrp:getname key='InvoiceType' value='${s.invoicetype}' p='f'/></td>
            <td><windrp:dateformat value='${s.makeinvoicedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:dateformat value='${s.invoicedate}' p='yyyy-MM-dd'/></td>
            <td align="right"><windrp:format value="${s.invoicesum}" p="###,##0.0"/></td>
            <td><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
          </tr>
          </logic:iterate>
      </table>
 </div>
</body>
</html>
