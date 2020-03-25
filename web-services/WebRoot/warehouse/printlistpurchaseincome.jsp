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
		<div align="center" class="print_title_style">采购入库</div>
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
            <td  align="right">供应商：</td>
            <td>${providename}</td>
            <td align="right">仓库：</td>
            <td>${wname}</td>
            <td align="right">是否复核：</td>
            <td ><windrp:getname key='YesOrNo' value='${IsAudit}' p='f'/></td>
          </tr>          
          <tr>
            <td align="right">是否记帐：</td>
            <td ><windrp:getname key='YesOrNo' value='${IsTally}' p='f'/></td>
            <td align="right">制单日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">关键字：</td>
            <td>
            ${KeyWord}</td>
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
            <td width="12%"  >编号</td>
            <td width="8%" >仓库</td>
            <td width="12%" > 采购订单编号</td>
            <td width="16%" > 供应商</td>
            <td width="9%" >入库日期</td>
            <td width="8%" >是否复核</td>
			<td width="8%" >是否记帐</td>
			<td width="14%" >制单机构</td>
            <td width="13%" >制单人</td>
          </tr>
          <logic:iterate id="pi" name="alpi" > 
          <tr class="table-back-colorbar"> 
            <td >${pi.id}</td>
            <td><windrp:getname key='warehouse' value='${pi.warehouseid}' p='d'/></td>
            <td>${pi.poid}</td>
            <td>${pi.providename}</td>
            <td><windrp:dateformat value='${pi.incomedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='YesOrNo' value='${pi.isaudit}' p='f'/></td>
			<td><windrp:getname key='YesOrNo' value='${pi.istally}' p='f'/></td>
			<td><windrp:getname key='organ' value='${pi.makeorganid}' p='d'/></td>
            <td><windrp:getname key='users' value='${pi.makeid}' p='d'/></td>
          </tr>
          </logic:iterate> 
      </table>
      </div>
</body>
</html>
