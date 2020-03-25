<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
			<div align="center" class="print_title_style">
				配送单
			</div>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<form name="search" method="post" action="listEquipAction.do" onSubmit="return oncheck();">
						<tr >
							<td align="right">
								所属机构：</td>
							<td>${oname}</td>
							<td align="right">
								所属部门：</td>
							<td>${deptname}</td>
							<td align="right">
								所属用户：</td>
							<td>${uname}</td>
							<td></td>
						</tr>
						<tr >
							<td width="9%" align="right">
								对象类型：</td>
							<td width="20%"><windrp:getname key="ObjectSort"  p="f" value="${ObjectSort}"/></td>
							<td align="right">选择对象：</td>
							<td>${cname}</td>
							<td align="right">配送日期：</td>
							<td>${BeginDate}-${EndDate}</td>
						</tr>
						<tr >
							<td width="9%" align="right">关键字：</td>
							<td width="20%">${KeyWord}</td>
							<td align="right">&nbsp;</td>
							<td>&nbsp;</td>
							<td align="right">&nbsp;</td>
							<td>&nbsp;</td>

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
					<td width="11%">
						对象类型
					</td>
					<td width="19%">
						对象名称
					</td>
					<td width="11%">
						联系人
					</td>
					<td width="12%">
						配送日期
					</td>
					<td width="9%">
						件数
					</td>
					<td width="9%">
						发运方式
					</td>
					<td width="16%">
						司机
					</td>
				</tr>
				<logic:iterate id="s" name="also">
					<tr class="table-back-colorbar">
						<td>
							${s.id}
						</td>
						<td>
							<windrp:getname key='ObjectSort' value='${s.objectsort}' p='f' />
						</td>
						<td>
							${s.cname}
						</td>
						<td>
							${s.clinkman}
						</td>
						<td>
							<windrp:dateformat value='${s.equipdate}' p='yyyy-MM-dd' />
						</td>
						<td>
							<windrp:format value='${s.piece}' />
						</td>
						<td>
							<windrp:getname key='TransportMode' value='${s.transportmode}'
								p='d' />
						</td>
						<td>
							<windrp:getname key='users' value='${s.motorman}' p='d' />
						</td>

					</tr>
				</logic:iterate>
			</table>
		</div>
	</body>
</html>
