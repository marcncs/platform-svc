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
      <div align="center" class="print_title_style">支出明细</div>
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
            <td>单据编号</td>
            <td>收款方编号</td>
            <td>收款方</td>
            <td>所属机构</td>
            <td>制单日期</td>
            <td>结算方式</td>
            <td >摘要</td>
            <td width="9%">应付款结算金额</td>
            <td width="12%">付款金额</td>
          </tr>
          <c:set var="payout" value="0"/>
          <c:set var="pay" value="0"/>
          <logic:iterate id="r" name="rls" >
            <tr class="table-back-colorbar">
              <td  align="left">${r.id}</td>
              <td align="left">${r.poid}</td>
              <td align="left">${r.poidname}</td>
              <td align="left"><windrp:getname key='organ' value='${r.makeorganid}' p='d'/></td>
              <td align="left"><windrp:dateformat value='${r.makedate}' p='yyyy-MM-dd hh:mm:ss'/></td>
              <td align="left">${r.paymodename}</td>
              <td align="left">${r.memo}</td>
              <td align="right">￥<windrp:format p="###,##0.00" value='${r.payablesum}'/></td>
              <td align="right">￥<windrp:format p="###,##0.00" value='${r.paysum}'/></td>
            </tr>
            <c:set var="payout" value="${payout+r.payablesum}"/>
            <c:set var="pay" value="${pay+r.paysum}"/>
          </logic:iterate>
		  </table>
		<table width="100%" cellspacing="1" class="totalsumTable">
          <tr class="back-gray-light">
            <td align="right" width="12%">合计：</td>
            <td align="right">￥<windrp:format p="###,##0.00" value='${payout}'/></td>
            <td align="right" width="12%">￥<windrp:format p="###,##0.00" value='${pay}'/></td>
          </tr>
      </table>
      </div>
</body>
</html>
