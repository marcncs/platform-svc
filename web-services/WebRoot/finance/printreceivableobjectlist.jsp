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
</div><hr align="center" width="100%" size="1" noshade> 
</center> 
      <div id="abc">
      <div align="center" class="print_title_style">收款管理</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		 <tr> 
            <td align="right">结算期单：</td>
            <td>${BeginDate} - ${EndDate}</td>
            <td align="right">对象类型：</td>
            <td><windrp:getname key="ObjectSort" p="f" value="${ObjectSort}"/>            </td>
            <td align="right">选择对象：</td>
            <td>${cname}</td>
          </tr>
          <tr>
            <td align="right">所属机构：</td>
            <td>${oname}</td>
            <td align="right">关键字：</td>
            <td>${KeyWord}</td>
            <td align="right">&nbsp;</td>
            <td width="18%">&nbsp;</td>
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
          <tr align="center" class="title-top"> 
            <td width="8%" >编号</td>
            <td width="9%">对象类型</td>
			<td width="18%">付款方名称</td>
			<td width="11%">期初应收金额</td>
            <td width="8%">本期应收金额</td>
            <td width="8%">本期已收金额 </td>
			<td width="8%">期末应收金额 </td>
            <td width="8%">提醒日期</td>
            <td width="10%">所属机构</td>
          </tr>
          <logic:iterate id="s" name="alpl" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.oid}','${s.objectsortname}','${s.makeorganid}');"> 
            <td >${s.oid}</td>
            <td>${s.objectsortname}</td>
			<td>${s.payer}</td>
			<td align="right"><fmt:formatNumber value="${s.previoussum}" pattern="0.00"/></td>
            <td align="right"><fmt:formatNumber value="${s.currentsum}" pattern="0.00"/></td>
			 <td align="right"><fmt:formatNumber value="${s.currentalreadysum}" pattern="0.00"/></td>
            <td align="right"><fmt:formatNumber value="${s.waitreceivablesum}" pattern="0.00"/></td>
            <td>${s.promisedate}</td>
            <td>${s.makeorganidname}</td>
            </tr>
          </logic:iterate> 
      </table>
	  </div>

</body>
</html>
