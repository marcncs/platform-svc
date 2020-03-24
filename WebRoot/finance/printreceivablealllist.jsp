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
      <div align="center" class="print_title_style">应收款结算</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		 <tr> 
            <td width="16%"  align="right">制单日期：</td>
            <td width="21%">${BeginDate}- ${EndDate}</td>
            <td width="8%" align="right">对象类型：</td>
            <td width="19%"><windrp:getname key="ObjectSort"  p="f" value="${ObjectSort}"/></td>
            <td width="8%" align="right">选择对象：</td>
            <td >${cname}</td>
          </tr>
          <tr>
            <td  align="right">应收金额不等于零：</td>
            <td><windrp:getname key="YesOrNo" p="f" value="${GreatZero}"/></td>
            <td align="right">所属机构：</td>
            <td>${oname}</td>
            <td align="right">关键字：</td>
            <td width="16%">${KeyWord}</td>
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
            <td width="10%" >编号</td>
            <td width="14%">应收款对象名称</td>
            <td width="11%">结算金额</td>
            <td width="9%">应收款金额</td>
            <td width="11%">所属机构</td>
            <td width="7%">制单日期</td>
            <td width="7%">到期日</td>
            <td width="8%">超龄(天)</td>
            <td width="10%">单据号</td>
            <td width="18%">描述</td>
          </tr>
          <logic:iterate id="s" name="alpl" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');"> 
            <td >${s.id}</td>
            <td>${s.receivableobjectname}</td>
            <td align="right"><windrp:format value='${s.settlementsum}'/></td>
            <td align="right"><windrp:format value='${s.receivablesum}'/></td>
            <td><windrp:getname key='organ' value='${s.makeorganid}' p='d'/></td>
            <td>${s.makedate}</td>
            <td><windrp:dateformat value='${s.awakedate}' p='yyyy-MM-dd'/></td>
            <td>${s.overage}</td>
            <td>${s.billno}</td>
            <td>${s.receivabledescribe}</td>
          </tr>
          </logic:iterate> 
      </table>
	  </div>

</body>
</html>
