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
      <div align="center" class="print_title_style">库存成本</div>
       <table width="100%" border="0" cellpadding="0" cellspacing="0">
           <tr>
            <td align="right">仓库：</td>
            <td ><windrp:getname key='warehouse' value='${WarehouseID}' p='d'/></td>
            <td align="right">产品类别：</td>
            <td>${psname}</td>
            <td align="right">产品：</td>
            <td>${ProductName}</td>
          </tr>
           <tr >
            <td  align="right">批次：</td>
            <td>
            ${Batch}</td>
            <td align="right">关键字：</td>
            <td>
            ${KeyWord}</td>
            <td align="right">&nbsp;</td>
            <td >&nbsp;</td>
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
			<td width="10%">仓库名</td>
			<td width="10%">批次</td>
			<td width="10%">产品编号</td>
            <td width="15%" >产品名称</td>
            <td width="15%">规格</td>   
            <td width="5%">单位</td>  
            <td width="11%">实际库存</td>     
            <td width="12%">成本单价</td>             
            <td width="12%">成本合计</td>    
          </tr>
          <logic:iterate id="p" name="alp" > 
          <tr class="table-back-colorbar" > 
			<td >${p.warehourseidname}</td>
			<td>${p.batch}</td>
			<td>${p.productid}</td>
            <td >${p.psproductname}</td>
            <td>${p.psspecmode}</td>    
            <td><windrp:getname key='CountUnit' value='${p.countunit}' p='d'/></td>
            <td><windrp:format value='${p.allstockpile}'/></td>        
            <td align="right"><windrp:format value='${p.cost}'/></td>    
            <td align="right"><windrp:format value='${p.totalcost}'/></td>        
          </tr>
          </logic:iterate> 
      </table>
      <table width="100%" cellspacing="1"  class="totalsumTable">
        <tr class="back-gray-light">
          <td width="10%"  align="right">本页合计：</td>
          <td align="right" width="66%"><windrp:format value="${totalstockpile}" /></td>
          <td align="right" width="12%">￥<windrp:format p="###,##0.00" value="${totalcost}" /></td>
          <td align="right" width="12%">￥<windrp:format p="###,##0.00" value="${totalsum}" /></td>
        </tr>
		<tr class="back-gray-light">
          <td  align="right">总合计：</td>
          <td align="right" ><windrp:format value="${allstockpile}" /></td>
          <td align="right" >￥<windrp:format p="###,##0.00" value="${allcost}" /></td>
          <td align="right" >￥<windrp:format p="###,##0.00" value="${allsum}" /></td>
        </tr>
      </table>
      </div>	
</body>
</html>
