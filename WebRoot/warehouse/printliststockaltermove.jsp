<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
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
		<div align="center" class="print_title_style">渠道订购</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td  align="right">制单机构：</td>
		    <td><windrp:getname key='organ' value='${MakeOrganID}' p='d'/></td>
		    <td  align="right">制单部门：</td>
		    <td><windrp:getname key='dept' value='${MakeDeptID}' p='d'/></td>
		    <td  align="right">制单人：</td>
		    <td><windrp:getname key='users' value='${MakeID}' p='d'/></td>
	      </tr>
		  <tr>
		    <td  align="right">制单日期：</td>
		    <td>${BeginDate}-${EndDate}</td>
		    <td  align="right">是否复核：</td>
		    <td><windrp:getname key='YesOrNo' value='${IsAudit}' p='f'/></td>
		    <td  align="right">是否发货：</td>
		    <td><windrp:getname key='YesOrNo' value='${IsShipment}' p='f'/></td>
	      </tr>
		  <tr>
		    <td  align="right">是否签收：</td>
		    <td><windrp:getname key='YesOrNo' value='${IsComplete}' p='f'/></td>
		    <td  align="right">是否作废：</td>
		    <td><windrp:getname key='YesOrNo' value='${IsBlankOut}' p='f'/></td>
		    <td  align="right">调入机构：</td>
		    <td><windrp:getname key='organ' value='${ReceiveOrganID}' p='d'/></td>
	      </tr>
		  <tr>
		    <td  align="right">关键字：</td>
		    <td>&nbsp;</td>
		    <td  align="right">&nbsp;</td>
		    <td>&nbsp;</td>
		    <td  align="right">&nbsp;</td>
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
		<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable"> 
	  <tr align="center" class="title-top">
				<td width="12%">
					编号
				</td>
				<td width="8%">
					调拨日期
				</td>
				<td width="17%">
					制单机构
				</td>
				<td width="8%">
					制单人
				</td>
				<td width="10%">
					调出仓库
				</td>
				<td width="17%">
					调入机构
				</td>
				<td width="7%">
					是否复核
				</td>
				<td width="7%">
					是否发货
				</td>
				<td width="7%">
					是否签收
				</td>
				<td width="7%">
					是否作废
				</td>
				
			</tr>
			<logic:iterate id="sa" name="als">
				<tr class="table-back-colorbar" >
					<td class="${sa.isblankout==1?'td-blankout':''}">
						${sa.id}
					</td>
					<td class="${sa.isblankout==1?'td-blankout':''}">
						<windrp:dateformat value='${sa.movedate}' p='yyyy-MM-dd'/>
					</td>
					<td class="${sa.isblankout==1?'td-blankout':''}">
						${sa.makeorganidname}
					</td>
					<td class="${sa.isblankout==1?'td-blankout':''}">
						<windrp:getname key='users' value='${sa.makeid}' p='d'/> 
					</td>
					<td class="${sa.isblankout==1?'td-blankout':''}">
						
						<windrp:getname key='warehouse' value='${sa.outwarehouseid}' p='d'/>
					</td>
					<td class="${sa.isblankout==1?'td-blankout':''}">
						${sa.receiveorganidname}
					</td>
					<td class="${sa.isblankout==1?'td-blankout':''}">
						<windrp:getname key='YesOrNo' value='${sa.isaudit}' p='f'/>
					</td>
					<td class="${sa.isblankout==1?'td-blankout':''}">
						<windrp:getname key='YesOrNo' value='${sa.isshipment}' p='f'/>
					</td>
					<td class="${sa.isblankout==1?'td-blankout':''}">
						<windrp:getname key='YesOrNo' value='${sa.iscomplete}' p='f'/>
					</td>
					<td class="${sa.isblankout==1?'td-blankout':''}">
						<windrp:getname key='YesOrNo' value='${sa.isblankout}' p='f'/>
					</td>
				</tr>
			</logic:iterate>
		</table>
		</div>
	</body>
</html>
