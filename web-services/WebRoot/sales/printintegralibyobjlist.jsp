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
			<div align="center"
				class="print_title_style">
				积分收入
			</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr >
            <td  align="right">对象类型：</td>
            <td><windrp:getname key="ObjectSort" p="f" value="${OSort}"/> </td>
            <td align="right">选择对象：</td>
            <td>${name }</td>
            <td align="right"></td>
            <td></td>
          </tr>
          <tr > 
            <td align="right">制单日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">单据号：</td>
            <td >${KeyWord }</td>
            <td  align="right"></td>
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
          <tr align="center" class="title-top"> 
		    <td width="10%">编号</td>
            <td width="15%">单据号</td>
            <td width="13%">积分类别</td>
            <td width="14%">应收</td>
            <td width="14%">已收</td>
            <td width="18%">制单日期</td>
            <td width="16%">配送机构</td>
          </tr>
          <logic:iterate id="s" name="iils" > 
          <tr class="table-back-colorbar"> 
            <td>${s.id}</td>
            <td><a href="javascript:ToBill('${s.billno}');">${s.billno}</a></td>
            <td><windrp:getname key='ISort' value='${s.isort}' p='d'/></td>
            <td>${s.rincome}</td>
            <td>${s.aincome}</td>
            <td><windrp:dateformat value='${s.makedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='organ' value='${s.equiporganid}' p='d'/></td>
            </tr>
          </logic:iterate> 
      </table>
    </div>
</body>
</html>
