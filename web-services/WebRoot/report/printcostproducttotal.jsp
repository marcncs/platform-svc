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
      <div align="center" class="print_title_style">营业成本按产品汇总</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="13%" align="right">制单机构：</td>
            <td width="21%"><windrp:getname key='organ' value='${MakeOrganID}' p='d'/></td>
            <td width="10%"  align="right">配送机构：</td>
            <td width="23%"><windrp:getname key='organ' value='${EquipOrganID}' p='d'/></td>
  			<td width="9%" align="right">日期：</td>
            <td width="24%">${BeginDate}-${EndDate}</td>     
           </tr>
		    <tr>
		      <td  align="right">产品：</td>
		      <td>${ProductID}</td>
		      <td  align="right">产品名称： </td>
		      <td>${ProductName}</td>
		      <td  align="right">单据类型：</td>
		      <td><windrp:getname key='BSort' value='${BSort}' p='f'/></td>
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
        <tr align="center" class="title-top-lock">
          <td width="14%">制单机构</td>
		<td width="11%">产品编号</td>
		<td width="16%">产品名称</td>
		<td width="11%">规格</td>
		<td width="5%">单位</td>
		<td width="8%">数量</td>
		<td width="12%">金额小计</td>
		<td width="11%">成本小计</td>
		<td width="12%">毛利小计</td>
        </tr>
		<c:set var="totalqt" value="0"/>
		<c:set var="totalsum" value="0"/>
        <c:set var="totalcb" value="0"/>
		<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar">
        <td><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>
        <td>${p.productid }</td>
		<td>${p.productname }</td>
		<td>${p.specmode }</td>
		<td><windrp:getname key="CountUnit" p="d" value="${p.unitid }"/></td>
		<td align="right"><windrp:format p="###,##0.00" value="${p.quantity}" /></td>
		<td align="right">￥<windrp:format p="###,##0.00" value='${p.subsum}' /></td>
		<td align="right">￥<windrp:format p="###,##0.00" value='${p.cost}' /></td>
		<td align="right">￥<windrp:format p="###,##0.00" value='${(p.subsum)-(p.cost)}' /></td>
        </tr>
        <c:set var="totalqt" value="${totalqt+p.quantity}"/>
		<c:set var="totalsum" value="${totalsum+p.subsum}"/>
        <c:set var="totalcb" value="${totalcb+p.cost}"/>
		</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="12%" >合计：</td>
          <td width="53%"  align="right" ><windrp:format p="###,##0.00" value='${totalqt}' /></td>
          <td width="12%" align="right">￥<windrp:format p="###,##0.00" value='${totalsum}' /></td>
          <td width="11%" align="right">￥<windrp:format p="###,##0.00" value='${totalcb}' /></td>  
		  <td width="12%" align="right">￥<windrp:format p="###,##0.00" value='${totalsum-totalcb}' /></td>
        </tr>
      </table>
      </div>
</body>
</html>
