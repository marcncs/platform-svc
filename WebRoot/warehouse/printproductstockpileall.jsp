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
      <div align="center" class="print_title_style">库存统计</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
           <tr>
            <td align="right">机构：</td>
            <td >${oname}</td>
            <td align="right">仓库：</td>
            <td>${wname}</td>
            <td align="right">产品类别：</td>
            <td>${psname}</td>
          </tr>
           <tr >
            <td  align="right">产品：</td>
            <td>
            ${ProductName}</td>
            <td align="right">批次：</td>
            <td>
            ${Batch}</td>
            <td align="right">关键字：</td>
            <td >${KeyWord}</td>
          </tr>
		   <tr>
          <td width="13%"  align="right">打印机构：</td>
          <td width="21%">${porganname}</td>
           <td width="10%"  align="right">打印人：</td>
          <td width="23%">${pusername}</td>
           <td width="15%"  align="right">打印时间：</td>
          <td width="18%">${ptime}</td>
        </tr>
      </table>
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
			<td width="12%">仓库名</td>
			<td width="8%">批次</td>
			<td width="13%">产品编号</td>
			<td width="13%">产品内部编号</td>
            <td width="15%" >产品名称</td>
            <td width="13%">规格</td>      
            <td width="5%">单位</td>            
            <td width="10%"> 可用库存量</td>
            <td width="5%">预定出库量</td>
            <td width="9%">实际库存</td>           
          </tr>
          <logic:iterate id="p" name="alp" > 
          <tr class="table-back-colorbar" > 
			<td >${p.warehourseidname}</td>
			<td>${p.batch}</td>
			<td>${p.productid}</td>
			<td>${p.nccode}</td>
            <td >${p.psproductname}</td>
            <td>${p.psspecmode}</td>   
           <td><windrp:getname key='CountUnit' value='${p.countunit}' p='d'/></td>        
            <td><windrp:format value='${p.stockpile}'/></td>
			<td><windrp:format value='${p.prepareout}'/></td> 
			<td><windrp:format value='${p.allstockpile}'/></td>             
          </tr>
          </logic:iterate> 
      </table>
      </div>	
</body>
</html>
