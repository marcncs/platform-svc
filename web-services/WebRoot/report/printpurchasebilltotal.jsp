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
      <div align="center" class="print_title_style">采购订单按单据汇总</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right">制单机构：</td>
            <td ><windrp:getname key='organ' value='${MakeOrganID}' p='d'/></td>
            <td align="right">采购部门：</td>
            <td><windrp:getname key='dept' value='${PurchaseDept}' p='d'/> </td>
            <td align="right">日期：</td>
            <td>${BeginDate}-${EndDate}</td>
          </tr>
          <tr >
            <td  align="right">单据编号：</td>
            <td>${ID}</td>
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
        <tr class="title-top-lock">
          <td  width="21%" align="center" >单据编号</td>
          <td width="35%" align="center" >制单时间</td>
          <td width="22%" align="center" >金额</td>
        </tr>
		<c:set var="totalcount" value="0"/>
		<c:forEach items="${list}" var="d">
        <tr class="table-back-colorbar">
          <td  >${d.pid}</td>
          <td >${d.makedate}</td>
          <td align="right">￥<windrp:format p="###,##0.00" value="${d.totalsum}" /></td>          
        </tr>
        <c:set var="totalcount" value="${totalcount+d.totalsum}"/>
		</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td  align="right" width="12%">合计：</td>
          <td align="right" >￥<windrp:format p="###,##0.00" value="${totalcount}" /></td>
        </tr>
      </table>
	 </div>
</body>
</html>
