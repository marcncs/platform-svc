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
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
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
      <div align="center" class="print_title_style">产品互转明细</div>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
         <tr> 
            <td align="right">制单机构：</td>
            <td>${oname}</td>
            <td align="right">制单人：</td>
            <td>${uname}</td>
            <td align="right">制单日期：</td>
            <td>${BeginDate}-${EndDate}</td>		 
          </tr>
          <tr >
            <td  align="right">转出仓库：</td>
            <td>${wname}
            </td>
            <td align="right">转入仓库：</td>
            <td>${wname2}</td>
            <td align="right">产品：</td>
            <td>${ProductName}</td>
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
        <tr class="title-top-lock">         
          <td width="13%"  align="center" >转出仓库</td>
		  <td width="12%"  align="center" >转入仓库</td>
		  <td width="11%" align="center" >单据号</td>
		  <td width="11%"  align="center" >产品编号</td>
		  <td width="19%" align="center" >产品名称</td>
          <td width="13%" align="center" >规格</td>
          <td width="7%" align="center" >单位</td>
          <td width="10%" align="center" >数量</td>
        </tr>
        
	<c:forEach items="${list}" var="d">
        <tr class="table-back-colorbar">           
		  <td  >${d.oname}</td>
		  <td  >${d.soname}</td>
		  <td >${d.billid}</td>
		  <td  >${d.productid}</td>
          <td >${d.productname}</td>
          <td >${d.specmode}</td>
          <td >${d.unitidname}</td>
          <td align="right"><windrp:format p="###,##0.00" value="${d.quantity}" /></td>
          </tr>
	</c:forEach> 
	</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="12%">合计：</td>
          <td align="right"><windrp:format p="###,##0.00" value="${totalsum}"/></td>
        </tr>
      </table>
	  </div>
</body>
</html>
