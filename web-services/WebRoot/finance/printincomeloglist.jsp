<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<link href="../css/pss.css" rel="stylesheet" type="text/css">
<style media=print> 
.Noprint{display:none;} 
</style> 
</head>

<body style="overflow: auto;">
<center class="Noprint" > 
<div class="printstyle">
<img src="../images/print.gif" onClick="javascript:window.print();"></img>
</div>
<hr align="center" width="100%" size="1" noshade> 
</center> 
      <div id="abc">
      <div align="center" class="print_title_style">收款</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr>
            <td align="right">对象类型：</td>
            <td><windrp:getname key="ObjectSort"  p="f" value="${ObjectSort}"/></td>
            <td align="right">选择对象：</td>
            <td>${cname}</td>
            <td align="right">是否复核：</td>
            <td><windrp:getname key="YesOrNo"  p="f" value="${IsAudit}"/></td>
          </tr>
          <tr>
            <td  align="right">是否收款：</td>
            <td><windrp:getname key="YesOrNo"  p="f" value="${IsReceive}"/></td>
            <td align="right">制单机构：</td>
            <td>${oname}</td>
            <td align="right">制单部门：</td>
            <td>${deptname}</td>
          </tr>
		   <tr>
            <td  align="right">制单人：</td>
            <td>${uname}</td>
            <td align="right">制单日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">关键字：</td>
            <td>${KeyWord}</td>
		   </tr>
		  <tr>
		    <td width="13%" align="right">打印机构：</td>
		    <td width="21%">${porganname}</td>
		    <td width="10%" align="right">打印人：</td>
		    <td width="23%">${pusername}</td>
		    <td width="9%" align="right">打印时间：</td>
		    <td width="24%">${ptime}</td>
	      </tr>
   </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
		  <tr class="title-top"> 
            <td width="10%"  align="center">编号 </td>
            <td width="29%"  align="center">付款人</td>
            <td width="10%"  align="center">结算方式</td>
            <td width="10%"  align="center">收款金额</td>           
            <td width="13%"  align="center">收款去向</td>
            <td width="10%"  align="center">制单机构</td>
			<td width="14%"  align="center">制单日期</td>
          </tr>
          <logic:iterate id="p" name="arls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}');"> 
            <td >${p.id}</td>
            <td >${p.drawee}</td>
            <td ><windrp:getname key='paymentmode' value='${p.paymentmode}' p='d'/>
</td>
            <td align="right"><windrp:format value='${p.incomesum}' p="###,##0.00"/></td>            
            <td >${p.fundattachname}</td>
            <td ><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>
			<td >${p.makedate}</td>
          </tr>
          </logic:iterate> 
      </table>
      
	  </div>
</body>
</html>
