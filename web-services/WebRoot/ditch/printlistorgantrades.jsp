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
       <c:if test="${isshow=='no'}">
		渠道换货
		</c:if>
		<c:if test="${isshow=='yes'}">
		渠道换货审核
		</c:if> </div>
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
	      <td  align="right">供货机构：</td>
	      <td><windrp:getname key='organ' value='${POrganID}' p='d'/></td>
	      <td  align="right">是否复核：</td>
	      <td><windrp:getname key='YesOrNo' value='${IsAudit}' p='f'/></td>
	      <td  align="right"> 是否签收：</td>
	      <td><windrp:getname key='YesOrNo' value='${IsReceive}' p='f'/></td>
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
			<td width="12%">编号</td>
			<td width="12%">供货机构</td>
			<td width="10%">联系人</td>
			<td width="12%">制单机构</td>
			<td width="8%">制单人</td>
			<td width="10%">制单日期</td>
			<td width="7%">是否复核</td>
			<td width="7%">是否批准</td>
			<td width="7%">是否确认</td>
			<td width="7%">是否签收</td>
		</tr>
		<logic:iterate id="o" name="list">
				<tr class="table-back-colorbar" >
				<td class="${o.isblankout==1?'td-blankout':''}">
					${o.id}
				</td>
				<td class="${o.isblankout==1?'td-blankout':''}">
					<windrp:getname key='organ' value='${o.porganid}' p='d' />
				</td>
				<td class="${o.isblankout==1?'td-blankout':''}">
					${o.plinkman }
				</td>									
				<td class="${o.isblankout==1?'td-blankout':''}">
					<windrp:getname key='organ' value='${o.makeorganid}' p='d' />
				</td>
				<td class="${o.isblankout==1?'td-blankout':''}">
					<windrp:getname key='users' value='${o.makeid}' p='d' />
				</td>
				<td class="${o.isblankout==1?'td-blankout':''}">
					<windrp:dateformat value="${o.makedate}" p="yyyy-MM-dd"/>
				</td>
				<td class="${o.isblankout==1?'td-blankout':''}">
					<windrp:getname key='YesOrNo' value='${o.isaudit}' p='f' />
				</td>
				<td class="${o.isblankout==1?'td-blankout':''}">
					<windrp:getname key='YesOrNo' value='${o.isratify}' p='f' />
				</td>
				<td class="${o.isblankout==1?'td-blankout':''}">
					<windrp:getname key='YesOrNo' value='${o.isaffirm}' p='f' />
				</td>
				<td class="${o.isblankout==1?'td-blankout':''}">
					<windrp:getname key='YesOrNo' value='${o.isreceive}' p='f' />
				</td>
			</tr>
		</logic:iterate>
	</table>
						
	</body>
</html>
