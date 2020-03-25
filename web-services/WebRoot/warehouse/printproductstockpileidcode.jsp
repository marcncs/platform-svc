<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>打印库存条码</title>
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
      <div align="center" class="print_title_style">库存条码</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
           <tr>
            <td align="right">仓库：</td>
            <td ><windrp:getname key='warehouse' value='${WarehouseID}' p='d'/></td>
            <td align="right">仓位：</td>
            <td>${bitname}</td>
            <td align="right">产品：</td>
            <td>${ProductName}</td>
          </tr>
           <tr >
            <td  align="right">关键字：</td>
            <td>
            ${KeyWord}</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
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
			<td width="10%">仓位</td>
            <td width="26%">条码</td>
			<td width="9%">批号</td>
			<td width="10%">生产日期</td>
			<%--<td width="10%">物流码起始号</td>
            <td width="9%" >物流码结束号</td>--%>
            <td width="10%">包装数量</td>  
			<td width="3%">单位</td>  
			<td width="10%">可用包装数量</td>            
          </tr>
          <logic:iterate id="p" name="list" > 
          <tr align="center" class="table-back-colorbar" > 
			<td >${p.warehousebit}</td>
            <td >${p.idcode}</td>
			<td >${p.batch}</td>
			<td>${p.producedate}</td>
			<%--<td>${p.startno}</td>
            <td >${p.endno}</td>--%>
            <td><windrp:format value="${p.packquantity}"/></td>   
			<td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
			<td><windrp:format value="${p.quantity}"/></td>         
          </tr>
          </logic:iterate> 
      </table>
 
</body>
</html>
