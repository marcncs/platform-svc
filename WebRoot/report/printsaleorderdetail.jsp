<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/pss.css" rel="stylesheet" type="text/css">
</head>
<style media=print> 
.Noprint{display:none;} 
</style> 
<body style="overflow: auto;">
<center class="Noprint" > 
<div class="printstyle">
<img src="images/print.gif" onClick="javascript:window.print();"></img>
</div><hr align="center" width="100%" size="1" noshade> 
</center> 
      <div id="abc" >
      <div align="center" class="print_title_style">零售明细单</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="10%"  align="right">制单机构：</td>
            <td width="25%">${oname}</td>
            <td width="10%" align="right">配送机构：</td>
            <td width="25%">${oname2}    </td>   
            <td  align="right">制单日期：</td>
            <td>${BeginDate}-${EndDate}</td>           
          </tr>
          <tr>            
            <td align="right">会员名称：</td>
            <td>${CName}</td>
            <td width="10%" align="right">产品：</td>
            <td width="20%">${ProductName} </td>
            <td align="right">&nbsp;</td>
            <td class="SeparatePage">&nbsp;</td>
          </tr>
          <tr>
            <td width="10%"  align="right">打印机构：</td>
            <td width="25%">${porganname}</td>
            <td width="10%" align="right">打印人：</td>
            <td width="25%">${pusername}    </td>   
            <td  align="right">打印时间：</td>
            <td>${ptime}</td>           
          </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">   
          <td>会员编号</td>        
          <td>会员名称</td>
		  <td>单据号</td>
		  <td>制单时间</td>
		  <td>制单机构</td>
		  <td>配送机构</td>
		  <td >产品名称</td>
          <td>规格</td>
          <td width="5%">单位</td>
          <td width="7%">单价</td>
          <td width="7%">数量</td>
          <td width="7%">金额</td>
        </tr>
        <c:set var="totalcount" value="0"/>
		<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar">      
          <td >${p.cid}</td>
		  <td >${p.cname}</td>
		  <td >${p.id}</td>
		  <td ><windrp:dateformat value="${p.makedate}" p="yyyy-MM-dd hh:mm:ss"/></td>
		  <td ><windrp:getname key="organ" p="d" value="${p.makeorganid}"/></td>
		  <td ><windrp:getname key="organ" p="d" value="${p.equiporganid}"/></td>
          <td >${p.productname}</td>
          <td >${p.specmode}</td>
          <td ><windrp:getname key="CountUnit" p="d" value="${p.unitid}"/></td>
          <td align="right">${p.unitprice}</td>
          <td align="right"><windrp:format p="###,##0.00" value="${p.quantity}" /></td>
          <td align="right">${p.subsum}</td>
        </tr>
        <c:set var="totalcount" value="${totalcount+p.subsum}"></c:set>
	</c:forEach> 
	</table>
	<table width="100%" cellspacing="1" class="totalsumTable">
        <tr  class="back-gray-light">
          <td align="right" width="12%">合计：</td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${totalcount}' /></td>
        </tr>
      </table>
      </div>
</body>
</html>
