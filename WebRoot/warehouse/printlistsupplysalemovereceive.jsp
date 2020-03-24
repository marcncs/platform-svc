<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
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
		<div align="center" class="print_title_style">代销签收</div>
         <table width="100%" border="0" cellpadding="0" cellspacing="0">
           <tr >
            <td  align="right">制单机构：</td>
            <td>
            ${oname}</td>
            <td align="right">制单部门：</td>
            <td>
            ${deptname}</td>
            <td align="right">制单人：</td>
            <td >${uname}</td>
          </tr>
          <tr>
            <td align="right">调入机构：</td>
            <td >${oname1}</td>
            <td align="right">申请机构：</td>
            <td>${oname2}</td>
            <td align="right">是否签收：</td>
            <td><windrp:getname key='YesOrNo' value='${IsComplete}' p='f'/></td>
          </tr>
          <tr >
            <td  align="right">订购需求日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">关键字：</td>
            <td>${KeyWord}</td>
            <td align="right">&nbsp;</td>
            <td >&nbsp;</td>
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
				<td>订购需求日期</td>
				<td>申请机构</td>
				<td>调入机构</td>
				<td>调入仓库</td>
				<td>调出仓库</td>
				<td>制单机构</td>
				<td>制单人</td>
				<td width="7%">是否签收</td>
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
						<windrp:getname key='organ' value='${a.supplyorganid}' p='d' />
					</td>
					<td class="${a.isblankout==1?'td-blankout':''}">
						<windrp:getname key='organ' value='${a.inorganid}' p='d' />
					</td>
					<td class="${a.isblankout==1?'td-blankout':''}">
						<windrp:getname key='warehouse' value='${a.inwarehouseid}' p='d'/>
					</td>
					<td class="${a.isblankout==1?'td-blankout':''}">
						<windrp:getname key='warehouse' value='${a.outwarehouseid}' p='d'/>
					</td>
					<td class="${a.isblankout==1?'td-blankout':''}">
						<windrp:getname key='organ' value='${a.makeorganid}' p='d' />
					</td>
					<td class="${a.isblankout==1?'td-blankout':''}">
						<windrp:getname key='users' value='${a.makeid}' p='d' />
					</td>
					<td class="${a.isblankout==1?'td-blankout':''}">
						<windrp:getname key='YesOrNo' value='${a.iscomplete}' p='f' />
					</td>
				</tr>
			</logic:iterate>
		</table>
	</div>
	</body>
</html>
