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
		.Noprint{display:none;} 
		</style> 
		</head>
		
		<body style="overflow: auto;">
		<center class="Noprint" > 
		<div class="printstyle">
		<img src="../images/print.gif" onClick="javascript:window.print();"></img>
		</div>
		<hr align="center" width="100%" size="1" noshade>
		</center> 
		<div id="abc">
		<div align="center" class="print_title_style">送货清单</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="10%" align="right">
								对象类型：							</td>
							<td width="27%"><windrp:getname key="ObjectSort"  p="f" value="${ObjectSort}"/></td>
							<td width="10%" align="right">
								选择对象：</td>
							<td width="23%">${cname}</td>
							<td width="10%" align="right">需求日期：</td>
							<td width="20%">${BeginDate}
							-
							  ${EndDate}</td>
										<td></td>
						</tr>
						<tr >
							<td width="10%" align="right">是否转配送：</td>
							<td width="27%"><windrp:getname key="YesOrNo" p="f" value="${IsTrans}"/></td>
							<td width="10%" align="right">
								是否完成配送：							</td>
							<td width="23%">
								<windrp:getname key="YesOrNo"  p="f" value="${IsAudit}" />							</td>
							<td width="10%" align="right">
								是否作废：							</td>
							<td width="20%">
								<windrp:getname key="YesOrNo" p="f" value="${IsBlankOut}" />	</td>
								
						</tr>
						<tr >
							<td align="right">
								制单机构：</td>
							<td>${oname}</td>
							<td align="right">
								制单部门：</td>
							<td>${deptname}</td>
							<td align="right">
								制单用户：</td>
							<td>${uname}</td>
							
						</tr>
						<tr >
							<td align="right">关键字：</td>
							<td>${KeyWord}</td>
							<td align="right">&nbsp;</td>
							<td>&nbsp;</td>
							<td align="right">&nbsp;</td>
						
						
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
				<td width="16%">
					编号
				</td>
				<td width="15%">
					对象名称
				</td>
				<td width="11%">
					收货人
				</td>
				<td width="12%">
					联系电话
				</td>
				<td width="8%">
					发运方式
				</td>
				<td width="10%">
					需求日期
				</td>
				<td width="8%">
					是否转配送
				</td>
				<td width="9%">
					是否完成配送
				</td>
				<td width="9%">
					是否作废
				</td>
			</tr>
			<logic:iterate id="s" name="alsb">
				<tr class="table-back-colorbar">
					<td class="${s.isblankout==1?'text2-red':''}">
						${s.id}
					</td>
					<td class="${s.isblankout==1?'text2-red':''}">
						<input type="hidden" name="cid" id="cid" value="${s.cid}">
						${s.cname}
					</td>
					<td class="${s.isblankout==1?'text2-red':''}">
						${s.linkman}
					</td>
					<td class="${s.isblankout==1?'text2-red':''}">
						${s.tel}
					</td>
					<td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='TransportMode' value='${s.transportmode}' p='d'/>
					</td>
					<td class="${s.isblankout==1?'text2-red':''}"><windrp:dateformat value='${s.requiredate}' p='yyyy-MM-dd'/></td>
					<td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='YesOrNo' value='${s.istrans}' p='f'/></td>
					<td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
					<td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='YesOrNo' value='${s.isblankout}' p='f'/></td>
				</tr>
			</logic:iterate>
		</table>
		</div>

	</body>
</html>
