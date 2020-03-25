<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/pss.css" rel="stylesheet" type="text/css">

<style media=print> 
.Noprint{display:none;} 
</style> 
</head>

<body style="overflow: auto;">
<center class="Noprint" > 
<div class="printstyle">
<img src="images/print.gif" onClick="javascript:window.print();"></img>
</div><hr align="center" width="100%" size="1" noshade> 
</center> 
      <div id="abc">
      <div align="center" class="print_title_style">渠道代销明细</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
      
          <tr>
            <td align="right">制单机构：</td>
            <td>${oname}</td>
            <td align="right">申请机构：</td>
            <td>${SupplyOrganIDName}</td>
            <td align="right">调入机构：</td>
            <td>${InOrganIDName}</td>
          </tr>
          <tr>
            <td align="right">订购日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">产品：</td>
            <td>${ProductName}</td>
            <td align="right"></td>
            <td></td>
          </tr>
          <tr>
          <td align="right">打印机构：</td>
          <td>${porganname}</td>
           <td  align="right">打印人：</td>
          <td>${pusername}</td>
           <td align="right">打印时间：</td>
          <td>${ptime}</td>
        </tr>
       
      </table>
     
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">    
          <td width="9%">调入机构</td>
		  <td width="9%">制单机构</td>
		  <td width="7%">制单时间</td>
		  <td width="9%">申请机构</td>     
		  <td width="7%">单据号</td>
		  <td width="7%">产品编号</td>
		  <td width="9%">产品名称</td>
          <td width="8%">规格</td>
          <td width="4%">单位</td>
          <td width="5%">订购单价</td>
          <td width="4%">销售单价</td>
          <td width="4%">数量</td>
          <td width="6%">订购金额</td>
          <td width="6%">销售金额</td>
        </tr>
        <c:set var="totalqt" value="0"/>
        <c:set var="totalpsum" value="0"/>
        <c:set var="totalssum" value="0"/>
	<c:forEach items="${alsod}" var="d">
        <tr class="table-back-colorbar">           
		  <td  ><windrp:getname key='organ' value='${d.rname}' p='d'/></td>
		  <td  ><windrp:getname key='organ' value='${d.oname}' p='d'/></td>
		  <td><windrp:dateformat value="${d.makedate}" p="yyyy-MM-dd"/></td>
		  <td ><windrp:getname key='organ' value='${d.sname}' p='d'/></td>
		  <td ><a href="javascript:ToBill('${d.billid}');">${d.billid}</a></td>
		  <td  >${d.productid}</td>
          <td >${d.productname}</td>
          <td >${d.specmode}</td>
          <td >${d.unitidname}</td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${d.punitprice}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${d.sunitprice}' /></td>
          <td align="right"><windrp:format p="###,##0.00" value='${d.quantity}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${d.psubsum}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${d.ssubsum}' /></td>
        </tr>
        <c:set var="totalqt" value="${totalqt+d.quantity}"></c:set>
        <c:set var="totalpsum" value="${totalpsum+d.psubsum}"></c:set>
        <c:set var="totalssum" value="${totalssum+d.ssubsum}"></c:set>
	</c:forEach> 
	
	</table>
	<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="10%">合计：</td>
          <td  align="right"><windrp:format p="###,##0.00" value='${totalqt}' /></td>
          <td width="6%" align="right">￥<windrp:format p="###,##0.00" value='${totalpsum}' /></td>
          <td width="6%" align="right">￥<windrp:format p="###,##0.00" value='${totalssum}' /></td>
        </tr>
  
      </table>
      </div>
</body>
</html>
