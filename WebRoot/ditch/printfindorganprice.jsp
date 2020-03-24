<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script language="javascript" src="../js/sorttable.js"></script>
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
      <div align="center" class="print_title_style">查价</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td  align="right">产品类别：</td>
          <td><windrp:getname key='organ' value='${MakeOrganID}' p='d'/>
            ${psname}</td>
          <td  align="right">价格范围：</td>
          <td><windrp:getname key='dept' value='${MakeDeptID}' p='d'/>
            ${BeginPrice}-${EndPrice}</td>
          <td  align="right">机构：</td>
          <td><windrp:getname key='users' value='${MakeID}' p='d'/>
            ${oname}</td>
        </tr>
        <tr>
          <td  align="right">关键字：</td>
          <td><windrp:getname key='organ' value='${POrganID}' p='d'/>
            ${KeyWord}</td>
          <td  align="right">&nbsp;</td>
          <td>&nbsp;</td>
          <td  align="right">&nbsp;</td>
          <td>&nbsp;</td>
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
      <td>产品编号</td>
      <td width="24%">产品名称</td>
    	<td>机构</td>      
      <td>单位</td>
      <td>规格</td>
      <td>价格</td>
      </tr>
      <logic:iterate id="p" name="list" >
        <tr class="table-back-colorbar" >
        <td >${p.productid}</td>
          <td>${p.productname}</td>
        	<td><windrp:getname key="organ" p="d" value="${p.organid }"/>   </td>          
          <td><windrp:getname key="CountUnit" value="${p.unitid}" p="d"/></td>
          <td>${p.specmode}</td>
		  <td align="right"><fmt:formatNumber value="${p.unitprice}" pattern="0.00"/></td>
          </tr>
      </logic:iterate>
	</table>
	</div>
</body>
</html>
