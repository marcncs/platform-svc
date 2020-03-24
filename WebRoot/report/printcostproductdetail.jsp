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
      <div align="center" class="print_title_style">营业成本明细</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="13%" align="right">制单机构：</td>
          <td width="21%"><windrp:getname key='organ' value='${MakeOrganID}' p='d'/></td>
          <td width="10%"  align="right">产品：</td>
          <td width="23%">${ProductName}</td>
          <td width="9%" align="right">日期：</td>
          <td width="24%">${BeginDate}-${EndDate}</td>
        </tr>
        <tr>
          <td  align="right">单据类型：</td>
          <td><windrp:getname key='BSort' value='${BSort}' p='f'/></td>
          <td  align="right">&nbsp;</td>
          <td>&nbsp;</td>
          <td  align="right">&nbsp;</td>
          <td></td>
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
          <td width="5%">对象编号</td>
          <td width="10%">对象名称</td>
          <td width="10%">单据号</td>
          <td width="7%">制单日期</td>
          <td width="11%">制单机构</td>
          <td width="10%">产品名称</td>
          <td width="7%">规格</td>
          <td width="7%">批次</td>
          <td width="3%">单位</td>
          <td width="4%">数量</td>
          <td width="6%">单价</td>
          <td width="7%">金额小计</td>
          <td width="7%">成本小计</td>
          <td width="6%">毛利小计</td>
        </tr>
        <c:set var="slcount" value="0"/>
        <c:set var="jecount" value="0"/>
        <c:set var="cbcount" value="0"/>
        <c:set var="mlcount" value="0"/>
        <c:forEach items="${list}" var="p">
          <tr class="table-back-colorbar">
            <td >${p.oid}</td>
            <td >${p.oname}</td>
            <td >${p.id}</td>
            <td ><windrp:dateformat value='${p.makedate}' p='yyyy-MM-dd'/></td>
            <td ><windrp:getname key="organ" p="d" value="${p.makeorganid}"/></td>
            <td >${p.productname}</td>
            <td >${p.specmode}</td>
            <td >${p.batch}</td>
            <td ><windrp:getname key="CountUnit" p="d" value="${p.unitid}"/></td>
            <td align="right"><windrp:format p="###,##0.00" value='${p.quantity}' /></td>
            <td align="right">￥
              <windrp:format p="###,##0.00" value='${p.unitprice}' /></td>
            <td align="right">￥
              <windrp:format p="###,##0.00" value='${p.unitprice*p.quantity}' /></td>
            <td align="right">￥
              <windrp:format p="###,##0.00" value='${p.cost*p.quantity}' /></td>
            <td align="right">￥
              <windrp:format p="###,##0.00" value='${(p.unitprice*p.quantity)-(p.cost*p.quantity)}' /></td>
          </tr>
          <c:set var="slcount" value="${slcount+p.quantity}"/>
          <c:set var="jecount" value="${jecount+(p.unitprice*p.quantity)}"/>
          <c:set var="cbcount" value="${cbcount+(p.cost*p.quantity)}"/>
          <c:set var="mlcount" value="${jecount-cbcount}"/>
        </c:forEach>
      </table>
      <table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td width="12%" height="15" align="right" >合计：</td>
          <td width="62%"  align="right" ><windrp:format p="###,##0.00" value='${slcount}' /></td>
          <td width="13%" align="right">￥<windrp:format p="###,##0.00" value='${jecount}' /></td>
          <td width="7%" align="right">￥<windrp:format p="###,##0.00" value='${cbcount}' /></td>  
		  <td width="6%" align="right">￥<windrp:format p="###,##0.00" value='${mlcount}' /></td>
        </tr>
      </table>
      </div>
</body>
</html>
