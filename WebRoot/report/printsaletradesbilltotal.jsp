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
      <div align="center" class="print_title_style">零售退货单据汇总</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">
			<td width="18%">单据编号</td>
			<td width="22%">制单时间 </td>
			<td width="10%">会员编号 </td>
			<td width="12%">会员名称 </td>
			<td width="12%">会员手机 </td>
			<td width="20%">制单机构 </td>
			<td width="20%">配送机构 </td>
			<td width="10%">金额 </td>
        </tr>
		<c:set var="totalcount" value="0"/>
		<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar">
          <td >${p.id}</td>
          <td ><windrp:dateformat value="${p.makedate}" p="yyyy-MM-dd hh:mm:ss"/></td> 
          <td >${p.cid}</td>
          <td >${p.cname}</td>
          <td >${p.cmobile}</td>
          <td ><windrp:getname key="organ" p="d" value="${p.makeorganid}"/></td>
          <td ><windrp:getname key="organ" p="d" value="${p.equiporganid}"/></td>
          <td align="right" >￥<windrp:format p="###,##0.00" value='${p.totalsum}' /></td>                
        </tr>
        <c:set var="totalcount" value="${totalcount+p.totalsum}"></c:set>
		</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="12%">合计：</td>
   
          <td align="right" >￥<windrp:format p="###,##0.00" value='${totalcount}' />
         </td>
        </tr>
		
      </table>
      </div>
</body>
</html>
