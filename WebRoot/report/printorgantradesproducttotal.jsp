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
      <div align="center" class="print_title_style">渠道换货按产品汇总</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right">换货机构：</td>
            <td >${oname}</td>
            <td align="right">产品名称：</td>
            <td>${ProductName}</td>
            <td align="right">制单日期：</td>
            <td>${BeginDate}-${EndDate}</td>
          </tr>
          <tr >
            <td  align="right">供货机构：</td>
            <td>${oname2}</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td class="SeparatePage">&nbsp;</td>
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
		  <td width="10%">换货机构编号</td>
          <td width="20%">换货机构</td>
          <td width="10%">产品编号</td>
		  <td width="20%">产品名称</td>
          <td width="25%">规格</td>
          <td width="5%">单位</td>
          <td width="10%">数量</td>
        </tr>
         <c:set var="totalqt" value="0"/>
	<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar">         
		  <td>${p.makeorganid}</td> 
          <td><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>       
		  <td>${p.productid}</td>
          <td>${p.productname}</td>
          <td>${p.specmode}</td>
          <td><windrp:getname key="CountUnit" p="d" value="${p.unitid}"/></td>
          <td align="right"><windrp:format p="###,##0.00" value="${p.quantity}" /></td>
        </tr>
        <c:set var="totalqt" value="${totalqt+p.quantity}"></c:set>
	</c:forEach> 
		</table>
	<table width="100%" cellspacing="1" class="totalsumTable">
        <tr  align="center" class="back-gray-light">
          <td align="right" width="10%">合计：</td>
          <td align="right"><windrp:format p="###,##0.00" value='${totalqt}' /></td>
        </tr>
      </table>
      
      </div>
   
</body>
</html>
