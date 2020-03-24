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
      <div id="abc" >
      <div align="center" class="print_title_style">零售换货按会员汇总</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">
          <td>会员名称编号</td>
          <td>名称</td>
          <td>手机</td>
          <td>次数</td>
        </tr>
		<c:set var="totalcount" value="0"/>
		<c:forEach items="${list}" var="d">
        <tr class="table-back-colorbar">
          <td align="center" width="25%">${d.id}</td>
          <td align="center" width="25%">${d.name}</td>
          <td align="center" width="25%">${d.tel}</td>
          <td align="right" width="25%"><windrp:format p="###,##0.00" value='${d.count}' /></td>                
        </tr>
        <c:set var="totalcount" value="${totalcount+d.count}"></c:set>
		</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="12%">合计：</td>        
          <td align="right" ><windrp:format p="###,##0.00" value='${totalcount}' />
         </td>
        </tr>
      </table>
      </div>
</body>
</html>
