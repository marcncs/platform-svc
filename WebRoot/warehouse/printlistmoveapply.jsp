<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>机构间转仓申请</title>
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
		</div><hr align="center" width="100%" size="1" noshade> 
		</center> 
		<div id="abc">
		<div align="center" class="print_title_style">
		<c:if test="${ISAUDIT=='no'}">
		机构间转仓申请
		</c:if>
		<c:if test="${ISAUDIT=='yes'}">
		机构间转仓申请审核
		</c:if>
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td  align="right">调出机构：</td>
		    <td><windrp:getname key='organ' value='${OutOrganID}' p='d'/></td>
		    <td  align="right">调入机构：</td>
		    <td><windrp:getname key='dept' value='${InOrganID}' p='d'/></td>
		    <td  align="right">制单人：</td>
		    <td><windrp:getname key='users' value='${MakeID}' p='d'/></td>
	      </tr>
		  <tr>
		    <td  align="right">是否批准：</td>
		    <td><windrp:getname key='YesOrNo' value='${IsRatify}' p='f'/></td>
		    <td  align="right">关键字：</td>
		    <td>${KeyWord}</td>
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
							<td width="12%">
								编号
							</td>
							<td width="10%">
								机构间转仓需求日期
							</td>
							<td width="17%">
								调出机构
							</td>
							<td width="15%">
								调入机构
							</td>
							<td width="10%">
								制单人
							</td>
<!--							<td width="7%">-->
<!--								是否复核-->
<!--							</td>-->
							<td width="7%">
								是否批准
							</td>
						</tr>
			</tr>
			<logic:iterate id="sa" name="als">
				<tr class="table-back-colorbar">
					<td class="${sa.isblankout==1?'td-blankout':''}">
									${sa.id}
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									
									<windrp:dateformat value="${sa.movedate}" p="yyyy-MM-dd"/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									
									<windrp:getname key="organ" p="d" value="${sa.outorganid}"/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
								<windrp:getname key="organ" p="d" value="${sa.inorganid}"/>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:getname key="users" p="d" value="${sa.makeid}"/>
								</td>
<!--								<td class="${sa.isblankout==1?'td-blankout':''}">-->
<!--									<windrp:getname key="YesOrNo" p="f" value="${sa.isaudit}"/>-->
<!--								</td>-->
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:getname key="YesOrNo" p="f" value="${sa.isratify}"/>
								</td>
				</tr>
			</logic:iterate>
		</table>
		</div>
	</body>
</html>
