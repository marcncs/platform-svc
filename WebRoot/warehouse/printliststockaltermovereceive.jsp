<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
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
		<div align="center" class="print_title_style">订购入库</div>
         <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr >
            <td  align="right">制单机构：</td>
            <td>${oname}</td>
            <td align="right">制单部门：</td>
            <td>${deptname}</td>
            <td align="right">制单人：</td>
            <td >${uname}</td>
          </tr>
           <tr >
            <td  align="right">调入机构：</td>
            <td>${rname}</td>
            <td align="right">是否签收：</td>
            <td><windrp:getname key='YesOrNo' value='${IsComplete}' p='f'/></td>
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
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="14%" >编号</td>
            <td width="13%" >订购日期</td>
            <td width="19%" >制单机构</td>
            <td width="11%" >制单人</td>
            <td width="11%" >调出仓库</td>
            <td width="23%" >调入机构</td>
            <td width="9%" >是否签收</td>			
          </tr>
          <logic:iterate id="sa" name="als" > 
          <tr class="table-back-colorbar" > 
            <td >${sa.id}</td>
            <td><windrp:dateformat value='${sa.movedate}' p='yyyy-MM-dd'/></td>
            <td>${sa.makeorganidname}</td>
            <td><windrp:getname key='users' value='${sa.makeid}' p='d'/> </td>
            <td><windrp:getname key='warehouse' value='${sa.outwarehouseid}' p='d'/></td>
            <td>${sa.receiveorganidname}</td>
            <td><windrp:getname key='YesOrNo' value='${sa.iscomplete}' p='f'/></td> 			
          </tr>
          </logic:iterate> 
      </table>
     </div>
</body>
</html>
