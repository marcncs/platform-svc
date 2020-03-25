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
      <div align="center" class="print_title_style">渠道代销按单据汇总</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
      
          <tr>
            <td align="right">制单机构：</td>
            <td>${MakeOrganIDName}</td>
            <td align="right">申请机构：</td>
            <td>${SupplyOrganIDName}</td>
            <td align="right">调入机构：</td>
            <td>${InOrganIDName}</td>
          </tr>
          <tr>
            <td align="right">订购日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">单据编号：</td>
            <td>${ID}</td>
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
			<td width="11%">单据编号</td>
			<td width="12%">订购时间 </td>
			<td width="12%">调入机构 </td>
			<td width="17%">制单机构 </td>
			<td width="15%">制单时间 </td>
			<td width="10%">申请机构</td>
			<td width="10%">订购金额 </td>
			<td width="13%">销售金额</td>
        </tr>
		<c:set var="totalcount" value="0"/>
        <c:set var="totals" value="0"/>
		<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar">
          <td >${p.id}</td>
          <td ><windrp:dateformat value="${p.movedate}" p="yyyy-MM-dd hh:mm:ss"/></td>
          <td ><windrp:getname key="organ" p="d" value="${p.inorganid}"/></td>
          <td ><windrp:getname key="organ" p="d" value="${p.makeorganid}"/></td>
          <td ><windrp:dateformat  value="${p.makedate}" p="yyyy-MM-dd hh:mm:ss"/></td>
          <td align="right" ><windrp:getname key="organ" p="d" value="${p.supplyorganid}"/></td>
          <td align="right" >￥<windrp:format p="###,##0.00" value='${p.totalsum}' /></td>
          <td align="right" >￥<windrp:format p="###,##0.00" value='${p.stotalsum}' /></td>                
        </tr>
        <c:set var="totalcount" value="${totalcount+p.totalsum}"></c:set>
        <c:set var="totals" value="${totals+p.stotalsum}"></c:set>
		</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="11%"> 合计：</td>
          <td  align="right">￥<windrp:format p="###,##0.00" value='${totalcount}' /></td>
          <td width="13%" align="right">￥<windrp:format p="###,##0.00" value='${totals}' /></td>
        </tr>
      </table>
      </div>

</body>
</html>
