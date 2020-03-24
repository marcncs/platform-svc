<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/funtcion.js"></script>
<title>WINDRP-分销系统</title>
<link href="../css/pss.css" rel="stylesheet" type="text/css">
		<style media=print>
.Noprint {
	display: none;
}
</style>
	</head>

	<body style="overflow: auto;">
		<center class="Noprint">
			<div class="printstyle">
				<img src="../images/print.gif" onClick="javascript:window.print();"></img>
			</div>
			<hr align="center" width="100%" size="1" noshade>
		</center>
		<div id="abc">
			<div align="center" class="print_title_style">
				出车情况详情
			</div>
	<table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="12%">对象名称</td>
		  <td width="13%">联系人</td> 
		  <td width="11%">联系电话</td> 
		  <td width="15%">司机</td> 	 
		  <td width="26%">送货地址</td> 
          <td width="13%" >配送日期</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.cname}</td> 
		  <td>${p.clinkman}</td>
		  <td>${p.tel}</td>
		  <td>${p.motormanname}</td>
		  <td>${p.addr}</td>
          <td >${p.equipdatename}</td>
        </tr>
        </logic:iterate> 
      </table>
</div>
</body>
</html>
