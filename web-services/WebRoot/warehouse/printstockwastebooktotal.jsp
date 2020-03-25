<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
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
      <div align="center" class="print_title_style">库存报表</div>
       <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="13%"  align="right">机构：</td>
          <td width="21%">${oname}</td>
           <td width="10%"  align="right">仓库：</td>
          <td width="23%"><windrp:getname key='warehouse' value='${WarehouseID}' p='d'/></td>
           <td width="9%"  align="right">批次：</td>
          <td width="24%">${KeyWord}</td>
        </tr>
        <tr>
          <td width="13%"  align="right">产品：</td>
          <td width="21%">${ProductName}</td>
           <td width="10%"  align="right">日期：</td>
          <td width="23%">${BeginDate}-${EndDate}</td>
           <td width="9%"  align="right">&nbsp;</td>
          <td width="24%">&nbsp;</td>
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
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
<tr align="center" class="title-top"> 
			 <td width="8%">仓库编号</td> 
            <td width="12%" >仓库名称</td>
            <td width="8%" >产品编号</td>
            <td width="8%" >内部编码</td>
			<td width="14%" >产品名称</td>
            <td width="13%" >规格</td>
            <td width="10%" >批次</td>
			 <td width="5%">单位</td>   
            <td width="9%" >入库数量</td>
            <td width="9%" >出库数量</td>
			<td width="9%" >结存数量</td>
      </tr>
             <logic:iterate id="s" name="als" > 
          <tr class="table-back-colorbar"  >
            <td >${s.warehouseid}</td> 
            <td >${s.warehourseidname}</td>
            <td >${s.productid}</td>
            <td >${s.barcode}</td>
			<td >${s.psproductname}</td>
            <td >${s.psspecmode}</td>
            <td >${s.batch}</td>
			<td ><windrp:getname key="CountUnit" value="${s.countunit}" p="d"/></td>
            <td ><windrp:format value='${s.prepareout}'/></td>
            <td ><windrp:format value='${s.stockpile}'/></td>
			 <td ><windrp:format value='${s.allstockpile}'/></td>
		    </tr>
		    <c:set var="incount" value="${incount+s.prepareout}"/>
			<c:set var="outcount" value="${outcount+s.stockpile}"/>
			<c:set var="count" value="${count+s.allstockpile}"/>
			</logic:iterate>   
      </table>
      <table width="100%" cellspacing="1"  class="totalsumTable">
        <tr class="back-gray-light">
          <td width="8%"  align="right">合计：</td>
          <td align="right" width="76%"><windrp:format value="${incount}" /></td>
          <td align="right" width="9%"><windrp:format value="${outcount}" /></td>
          <td align="right" width="9%"><windrp:format value="${count}" /></td>
        </tr>		
      </table>
      </div>	
</body>
</html>
