<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
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
				供应商列表
			</div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right">制单机构：</td>
            <td >${oname}</td>
            <td align="right">制单部门：</td>
            <td>${deptname}</td>
            <td align="right">制单人：</td>
            <td>${uname}</td>
          </tr>
          <tr >
            <td  align="right">供应商类型：</td>
            <td><windrp:getname key='Genre' value='${Genre}' p='d'/></td>
            <td align="right">ABC分类：</td>
            <td><windrp:getname key='AbcSort' value='${AbcSort}' p='f'/></td>
            <td align="right">关键字：</td>
            <td >${KeyWord}</td>
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
			<table width="100%" border="0" cellpadding="0" cellspacing="1"
				class="sortable">
				<tr align="center" class="title-top">
					<td width="13%">
						编号
					</td>
					<td width="27%">
						供应商名
					</td>
					<td width="12%">
						电话
					</td>
					<td width="14%">
						供应商类型
					</td>
					<td width="8%">
						ABC分类
					</td>
					<td width="13%">
						供应商行业
					</td>
					<td width="13%">
						制单机构
					</td>
				</tr>
				<logic:iterate id="p" name="alls">
					<tr class="table-back-colorbar"
						>
						<td>
							${p.pid}
						</td>
						<td>
							${p.pname}
						</td>
						<td>
							${p.tel}
						</td>
						<td>
							<windrp:getname key="Genre" value="${p.genre}" p="d" />
						</td>
						<td>
							<windrp:getname key="AbcSort" value="${p.abcsort}" p="f" />
						</td>
						<td>
							<windrp:getname key="Vocation" value="${p.vocation}" p="d" />
						</td>
						<td>
							<windrp:getname key='organ' value='${p.makeorganid}' p='d' />
						</td>
					</tr>
				</logic:iterate>
			</table>
		</div>
	</body>
</html>
