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
      <div align="center" class="print_title_style">收入明细</div>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		 <tr>
            <td width="11%"  align="right">对象类型：</td>
            <td width="20%"><windrp:getname key="ObjectSort" p="f" value="${objectsort}"/></td>
            <td width="8%" align="right">付款方：</td>
            <td width="25%">${payername}</td>
            <td width="8%" align="right">所属机构：</td>
            <td width="19%">${oname}</td>
          </tr>
          <tr >
            <td width="11%"  align="right">制单日期：</td>
            <td width="20%">${BeginDate}-${EndDate}</td>
            <td width="8%" align="right">&nbsp;</td>
            <td width="25%"></td>
            <td width="8%" align="right">&nbsp;</td>
            <td width="19%">&nbsp;</td>
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
          <td width="10%">单据编号</td>
          <td width="12%">付款方编号</td>
          <td width="16%">付款方</td>
          <td width="11%">所属机构</td>
          <td width="9%">制单日期</td>
          <td width="4%">结算方式</td>
          <td width="16%">摘要</td>
          <td width="11%">应收款结算金额</td>
          <td width="11%">收款金额</td>
        </tr>
        <logic:iterate id="r" name="rls" >
          <tr class="table-back-colorbar">
            <td align="left">${r.id}</td>
            <td align="left">${r.roid}</td>
            <td align="left">${r.roidname}</td>
            <td align="left"><windrp:getname key='organ' value='${r.makeorganid}' p='d'/></td>
            <td align="left"><windrp:dateformat value='${r.makedate}' p='yyyy-MM-dd'/></td>
            <td align="left"><windrp:getname key='paymentmode' value='${r.paymentmode}' p='d'/></td>
            <td align="left">${r.memo}</td>
            <td align="right">￥${r.receivablesum}</td>
            <td align="right">￥${r.incomesum}</td>
          </tr>
        </logic:iterate>
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="12%">合计：</td>
          <td align="right">${allreceivablesum}</td>
          <td align="right" width="11%">${allincomesum}</td>
        </tr>
      </table>
      </div>
</body>
</html>
