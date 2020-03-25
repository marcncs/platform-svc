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
      <div align="center" class="print_title_style">资金台帐</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		 <tr>
            <td align="right">现金银行：</td>
            <td>${cbname}</td>
            <td align="right">记录日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">&nbsp;</td>
            <td >
              
            </td>
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
          <tr align="center" class="title-top"> 
            <td width="11%" >编号</td>
            <td width="13%">现金银行</td>
            <td width="13%">单据号</td>
            <td width="16%">摘要</td>
            <td width="9%">期初金额</td>
            <td width="8%">本期收入</td>
			<td width="9%">本期支出</td>
			<td width="11%">本期结存金额</td>
			<td width="10%">记录日期</td>
          </tr>
          <logic:iterate id="s" name="arls" > 
          <tr class="table-back-colorbar"  onClick="CheckedObj(this,'${s.id}');"> 
            <td >${s.id}</td>
            <td>${s.cbidname}</td>
            <td>${s.billno}</td>
            <td>${s.memo}</td>
			<td align="right"><windrp:format value='${s.cyclefirstsum}'  p="###,##0.00"/></td>     
			<td align="right"><windrp:format value='${s.cycleinsum}'  p="###,##0.00"/></td>
			<td align="right"><windrp:format value='${s.cycleoutsum}'  p="###,##0.00"/></td>
			<td align="right"><windrp:format value='${s.cyclebalancesum}'  p="###,##0.00"/></td>
			<td>${s.recorddate}</td>
          </tr>
          </logic:iterate> 
      </table>
	  </div>
</body>
</html>
