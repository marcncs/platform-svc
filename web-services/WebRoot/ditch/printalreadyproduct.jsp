<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>		 
<html>
<head>

<title>销售产品</title>
<link href="../css/pss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
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
      <div align="center" class="print_title_style">销售产品表</div>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="13%"  align="right">打印机构：</td>
            <td width="21%">${porganname}</td>
            <td width="10%"  align="right">打印人：</td>
            <td width="23%">${pusername}</td>
            <td width="9%"  align="right">打印时间：</td>
            <td width="24%">${ptime}</td>
          </tr>
        </table>
        <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr align="center" class="title-top-lock">
              <td width="20%" >产品编号</td>
              <td width="26%">产品名称</td>
              <td width="26%">规格</td>
              <td width="26%">单位</td>
            </tr>
              <logic:iterate id="d" name="opls" >
                <tr align="center" class="table-back-colorbar" >
                  <td>${d.productid}</td>
                  <td>${d.productname}</td>
                  <td>${d.specmode}</td>
                  <td><windrp:getname key='CountUnit' value='${d.countunit}' p='d'/></td>
                </tr>
              </logic:iterate>
      </table>
		</div>
</body>
</html>

