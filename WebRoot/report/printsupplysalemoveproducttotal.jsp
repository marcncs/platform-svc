<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
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
      <div align="center" class="print_title_style">渠道代销按产品汇总</div>
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
            <td align="right"></td>
            <td></td>
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
          <td width="9%">制单机构</td>
		<td width="9%">产品编号</td>
		<td width="15%">产品名称</td>
		<td width="15%">规格</td>
		<td width="7%">单位</td>
		<td width="9%">数量</td>
		<td width="14%">订购金额小计</td>
		<td width="13%">销售金额小计</td>
        </tr>
		<c:set var="totalqt" value="0"/>
		<c:set var="totalpsum" value="0"/>
		<c:set var="totalssum" value="0"/>
		<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar">
        <td><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>
        <td>${p.productid }</td>
		<td>${p.productname }</td>
		<td>${p.specmode }</td>
		<td><windrp:getname key="CountUnit" p="d" value="${p.unitid }"/></td>
		<td align="right"><windrp:format p="###,##0.00" value="${p.quantity}" /></td>
		<td align="right">￥<windrp:format p="###,##0.00" value='${p.psubsum}' /></td>
		<td align="right">￥<windrp:format p="###,##0.00" value='${p.ssubsum}' /></td>
        </tr>
        <c:set var="totalqt" value="${totalqt+p.quantity}"/>
		<c:set var="totalpsum" value="${totalpsum+p.psubsum}"/>
        <c:set var="totalssum" value="${totalssum+p.ssubsum}"/>
		</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="10%"> 本页合计：</td>
          <td width="61%" align="right" ><windrp:format p="###,##0.00" value='${totalqt}' /></td>
          <td width="15%" align="right">￥<windrp:format p="###,##0.00" value='${totalpsum}' /></td>  
		  <td width="14%" align="right">￥<windrp:format p="###,##0.00" value='${totalssum}' /></td>      
        </tr>
      </table>
      </div>
</body>
</html>
