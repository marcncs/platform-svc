<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
      <div align="center" class="print_title_style">收款汇总</div>
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
            <td width="8%" align="right">结算方式：</td>
            <td width="25%"><windrp:getname key="PaymentMode" p="d" value="${PaymentMode}"/></td>
            <td width="8%" align="right">收款去向：</td>
            <td width="19%">${FundAttach}</td>
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
            <td width="9%" >编号</td>
            <td width="10%">付款方编号</td>
            <td width="11%">付收方</td>
            <td width="11%">结算方式</td>
            <td width="11%">收款去向</td>
            <td width="17%">所属机构</td>
            <td width="14%">制单日期</td>
            <td width="17%">已收款金额</td>
          </tr>
          <logic:iterate id="s" name="str" >
            <tr class="table-back-colorbar">
              <td>${s.id}</td>
              <td>${s.roid}</td>
              <td>${s.drawee}</td>
              <td>${s.paymentmodename}</td>
              <td>${s.fundattachname}</td>
              <td><windrp:getname key='organ' value='${s.makeorganid}' p='d'/></td>
              <td><windrp:dateformat value='${s.makedate}' p='yyyy-MM-dd'/></td>
              <td align="right">￥<windrp:format p="###,##0.00" value='${s.incomesum}'/></td>
            </tr>
          </logic:iterate>
	    </table>
		<table width="100%" cellspacing="1" class="totalsumTable">
          <tr class="back-gray-light">
            <td  align="right" width="12%">合计：</td>
            <td  align="right">${allsum}</td>
          </tr>
      </table>
      </div>
</body>
</html>
