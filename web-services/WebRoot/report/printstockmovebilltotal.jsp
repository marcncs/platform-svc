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
	
      <div id="abc" >
      <div align="center" class="print_title_style">转仓按单据汇总</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="13%" align="right">转出机构：</td>
          <td width="21%"><windrp:getname key='organ' value='${MakeOrganID}' p='d'/></td>
          <td width="10%"  align="right">转出仓库：</td>
          <td width="23%"><windrp:getname key='warehouse' value='${OutWarehouseID}' p='d'/></td>
          <td width="9%" align="right">制单日期：</td>
          <td width="24%"> ${BeginDate}-${EndDate}</td>
        </tr>
        <tr>
          <td  align="right">转入机构：</td>
          <td><windrp:getname key='organ' value='${InOrganID}' p='d'/></td>
          <td  align="right">转入仓库：</td>
          <td><windrp:getname key='warehouse' value='${InWarehouseID}' p='d'/></td>
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
			<td width="12%">单据编号</td>
			<td width="16%">转仓日期 </td>
			<td width="12%">转入机构 </td>
			<td width="22%">转出机构 </td>
			<td width="22%">制单时间 </td>
			<td width="12%">数量 </td>
        </tr>
		<c:set var="totalcount" value="0"/>
		<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar">
          <td>${p.id}</td>
          <td><windrp:dateformat value="${p.movedate}" p="yyyy-MM-dd"/></td>
          <td><windrp:getname key="organ" p="d" value="${p.inorganid}"/></td>
          <td><windrp:getname key="organ" p="d" value="${p.makeorganid}"/></td>
          <td><windrp:dateformat  value="${p.makedate}" p="yyyy-MM-dd hh:mm:ss"/></td>
          <td align="right"><windrp:format p="###,##0.00" value="${p.quantity}" /></td>                
        </tr>
        <c:set var="totalcount" value="${totalcount+p.quantity}"></c:set>
		</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="12%">合计：</td>
          <td align="right"><windrp:format p="###,##0.00" value="${totalcount}" />
         </td>
        </tr>
      </table>
      </div>
</body>
</html>
