<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
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
			<div align="center" class="print_title_style">零售单</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr >
								<td width="9%" align="right">
									会员名称：
								</td>
								<td width="24%">${cname}</td>
								<td width="8%" align="right">
									是否复核：
								</td>
								<td width="24%">
									<windrp:getname key="YesOrNo" p="f" value="${IsAudit}" />
								</td>
								<td width="10%" align="right">
									是否作废：
								</td>
								<td width="25%">
									<windrp:getname key="YesOrNo"  p="f" value="${IsBlankOut}"/>
								</td>
							</tr>
							<tr >
								<td align="right">
									是否结案：
								</td>
								<td>
									<windrp:getname key="YesOrNo"  p="f" value="${IsEndcase}" />
								</td>
								<td align="right">
									开票信息：
								</td>
								<td>
									<windrp:getname key="InvoiceType"p="f" value="${InvMsg}"/>
								</td>
								<td align="right">
									配送机构：
								</td>
							  <td>${eoname}</td>
							</tr>
							<tr >
								<td align="right">
									制单机构：
								</td>
								<td>${oname}</td>
								<td align="right">
									制单部门：
								</td>
								<td>${dname}
								</td>
								<td align="right">
									制单人：
								</td>
								<td>${uname}
								</td>
							</tr>
							<tr>
								<td align="right">
									制单日期：
								</td>
								<td>${BeginDate}-${EndDate}</td>
								<td align="right">
									关键字：
								</td>
								<td>${KeyWord}
								</td>
								<td align="right">&nbsp;
									
								</td>
								<td class="SeparatePage">&nbsp;</td>
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
			<table class="sortable" width="100%" border="0" cellpadding="0"
				cellspacing="1">
				<tr align="center" class="title-top">
					<td width="13%">
						编号
					</td>
					<td width="13%">
						会员名称
					</td>
					<td width="14%">
						制单机构
					</td>
					<td width="9%">
						制单人
					</td>
					<td width="15%">
						制单日期
					</td>
					<td width="8%">
						总金额
					</td>
					<td width="7%">
						是否复核
					</td>
					<td width="7%">
						是否检货
					</td>
					<td width="7%">
						是否配送
					</td>
					<td width="7%">
						是否作废
					</td>

				</tr>
				<logic:iterate id="s" name="also">
					<tr class="table-back-colorbar">
						<td class="${s.isblankout==1?'text2-red':''}">
							${s.id}
						</td>
						<td class="${s.isblankout==1?'text2-red':''}">
							${s.cname}
							<a href="../sales/listMemberAction.do?CID=${s.cid}"><img
									src="../images/CN/go.gif" width="10" height="10" border="0">
							</a>
						</td>
						<td class="${s.isblankout==1?'text2-red':''}">
							<windrp:getname key='organ' value='${s.makeorganid}' p='d' />
						</td>
						<td class="${s.isblankout==1?'text2-red':''}" align="center">
							<windrp:getname key='users' value='${s.makeid}' p='d' />
						</td>
						<td class="${s.isblankout==1?'text2-red':''}">
							<windrp:dateformat value='${s.makedate}' />
						</td>
						<td class="${s.isblankout==1?'text2-red':''}" align="right">
							<windrp:format value="${s.totalsum}" />
						</td>
						<td class="${s.isblankout==1?'text2-red':''}" align="center">
							<windrp:getname key='YesOrNo' value='${s.isaudit}' p='f' />
						</td>
						<td class="${s.isblankout==1?'text2-red':''}" align="center">
							<windrp:getname key='YesOrNo' value='${s.takestatus}' p='f' />
						</td>
						<td class="${s.isblankout==1?'text2-red':''}" align="center">
							<windrp:getname key='YesOrNo' value='${s.isendcase}' p='f' />
						</td>
						<td class="${s.isblankout==1?'text2-red':''}" align="center">
							<windrp:getname key='YesOrNo' value='${s.isblankout}' p='f' />
						</td>

					</tr>
				</logic:iterate>
			</table>
		</div>
	</body>

</html>
