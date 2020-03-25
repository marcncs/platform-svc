<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/pss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
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
      <div align="center" class="print_title_style">库存台账</div>
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
            <td  align="right">日期：</td>
            <td>
            ${BeginDate}-${EndDate}</td>
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
          <tr align="center" class="title-top-lock">
            <td width="8%">日期</td> 
            <td width="10%" >产品</td>
            <td width="7%" >批次</td>
            <td width="10%" >仓库</td>
			<td width="6%" >仓位</td>
            <td width="12%" >单据号</td>
            <td width="12%" >摘要</td>
            <td width="5%" >单位</td>
            <td width="6%" >期初数量</td>
            <td width="6%" >收入数量</td>
			<td width="6%" >发出数量</td>
			<td width="6%" >结存数量</td>
		  </tr>
		  <c:set var="incount" value="0"/>
		  <c:set var="outcount" value="0"/>
		    <c:set var="count" value="0"/>
          <logic:iterate id="s" name="als" > 
          <tr class="table-back-colorbar" >
            <td ><windrp:dateformat value="${s.recorddate}" p="yyyy-MM-dd"/></td> 
            <td >${s.productidname}</td>
            <td >${s.batch}</td>
            <td ><windrp:getname key='warehouse' value='${s.warehouseid}' p='d'/></td>
			<td >${s.warehousebit}</td>
            <td >${s.billcode}</td>
            <td >${s.memo}</td>
            <td><windrp:getname key='CountUnit' value='${s.unitid}' p='d'/></td>
            <td ><windrp:format value='${s.cyclefirstquantity}'/></td>
            <td ><windrp:format value='${s.cycleinquantity}'/></td>
			 <td ><windrp:format value='${s.cycleoutquantity}'/></td>
			 <td ><windrp:format value='${s.cyclebalancequantity}'/></td>
		    </tr>
			<c:set var="incount" value="${incount+s.cycleinquantity}"/>
			<c:set var="outcount" value="${outcount+s.cycleoutquantity}"/>
			<c:set var="count" value="${count+s.cyclebalancequantity}"/>
			</logic:iterate> 
			</table>
      <table width="100%" cellspacing="1"  class="totalsumTable">
          <tr class="back-gray-light" >
            <td align="right" width="10%" >合计：</td>
           	<td></td>
            <td width="6%">&nbsp;<windrp:format value='${incount}'/></td>
            <td width="6%">&nbsp;<windrp:format value='${outcount}'/></td>
            <td width="6%">&nbsp;<windrp:format value='${count}'/></td>
			
          </tr>          
      </table>
	   </div> 

</body>
</html>
