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
      <div align="center" class="print_title_style">零售单据汇总</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="10%"  align="right">制单机构：</td>
            <td width="25%">${oname}</td>
            <td width="10%" align="right">配送机构：</td>
            <td width="25%">${oname2}    </td>   
            <td  align="right">制单日期：</td>
            <td>${BeginDate}-${EndDate}</td>           
          </tr>
          <tr>            
            <td align="right">会员名称：</td>
            <td>${CName}</td>
            <td width="10%" align="right">单据编号：</td>
            <td width="20%">${ID} </td>
            <td align="right">&nbsp;</td>
            <td class="SeparatePage">&nbsp;</td>
          </tr>
          <tr>
            <td width="10%"  align="right">打印机构：</td>
            <td width="25%">${porganname}</td>
            <td width="10%" align="right">打印人：</td>
            <td width="25%">${pusername}    </td>   
            <td  align="right">打印时间：</td>
            <td>${ptime}</td>           
          </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">
			<td width="11%">单据编号</td>
			<td width="14%">制单时间 </td>
			<td width="9%">会员编号 </td>
			<td width="11%">会员名称 </td>
			<td width="11%">会员手机 </td>
			<td width="16%">制单机构 </td>
			<td width="15%">配送机构 </td>
			<td width="13%">金额 </td>
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
     
          <td align="right">￥<windrp:format p="###,##0.00" value='${totalcount}' />
         </td>
        </tr>
		
      </table>
      </div>
</body>
</html>
