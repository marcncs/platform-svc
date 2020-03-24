<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
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
		 代销申请
		</c:if>
		<c:if test="${ISAUDIT=='yes'}">
		 代销申请审核
		</c:if>  
   </div>
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
	    <td  align="right">需求日期：</td>
	    <td>${BeginDate}-${EndDate}</td>
	    <td  align="right">是否复核：</td>
	    <td><windrp:getname key='YesOrNo' value='${IsAudit}' p='f'/></td>
	    <td  align="right">是否批准：</td>
	    <td><windrp:getname key='YesOrNo' value='${IsRatify}' p='f'/></td>
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
			<td>编号</td>
			<td>需求日期</td>
			<td>调出机构</td>
			<td>调入机构</td>
			<td>调入仓库</td>
			<td>是否复核</td>
			<td>是否批准</td>
			<td>是否作废</td>
			<td>制单人</td>
		</tr>
		<logic:iterate id="a" name="list">
			
				<tr class="table-back-colorbar" >
				<td class="${a.isblankout==1?'td-blankout':''}">
					${a.id}
				</td>
				<td class="${a.isblankout==1?'td-blankout':''}">
					<windrp:dateformat value="${a.movedate}" p="yyyy-MM-dd"/>
				</td>
				<td class="${a.isblankout==1?'td-blankout':''}">
					<windrp:getname key='organ' value='${a.outorganid}' p='d' />
				</td>
				
				<td class="${a.isblankout==1?'td-blankout':''}">
					<windrp:getname key='organ' value='${a.inorganid}' p='d' />
				</td>
				<td class="${a.isblankout==1?'td-blankout':''}">
					<windrp:getname key='warehouse' value='${a.inwarehouseid}' p='d'/>
				</td>
				<td class="${a.isblankout==1?'td-blankout':''}">
					<windrp:getname key='YesOrNo' value='${a.isaudit}' p='f' />
				</td>
				<td class="${a.isblankout==1?'td-blankout':''}">
					<windrp:getname key='YesOrNo' value='${a.isratify}' p='f' />
				</td>
				<td class="${a.isblankout==1?'td-blankout':''}">
					<windrp:getname key='YesOrNo' value='${a.isblankout}' p='f' />
				</td>
				<td class="${a.isblankout==1?'td-blankout':''}">
					<windrp:getname key='users' value='${a.makeid}' p='d' />
				</td>
			</tr>
		</logic:iterate>
		</table>
		
	</div>
	</body>
</html>
