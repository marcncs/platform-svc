<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
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
		<div align="center" class="print_title_style">盘盈列表</div>
         <table width="100%" border="0" cellpadding="0" cellspacing="0">
           <tr>
            <td align="right">仓库：</td>
            <td ><windrp:getname key='warehouse' value='${WarehouseID}' p='d'/></td>
            <td align="right">入库日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">是否复核：</td>
            <td><windrp:getname key='YesOrNo' value='${IsAudit}' p='f'/></td>
          </tr>
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
          <tr >
            <td  align="right">关键字：</td>
            <td>${KeyWord}</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td >&nbsp;</td>
          </tr>
		   <tr>
          <td width="13%"  align="right">打印机构：</td>
          <td width="21%">${porganname}</td>
           <td width="10%"  align="right">打印人：</td>
          <td width="23%">${pusername}</td>
           <td width="15%"  align="right">打印时间：</td>
          <td width="18%">${ptime}</td>
        </tr>
      </table>
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="15%"  >编号</td>
            <td width="16%">相关单据</td>
            <td width="13%" >入货仓库</td>
            <td width="25%" >制单机构</td>
            <td width="11%" >制单人</td>
            <td width="12%" >制单日期</td>
            <td width="8%" >是否复核</td>
          </tr>
          <logic:iterate id="pi" name="alpi" > 
          <tr class="table-back-colorbar" > 
            <td >${pi.id}</td>
            <td>${pi.billno}</td>
            <td><windrp:getname key='warehouse' value='${pi.warehouseid}' p='d'/></td>
            <td><windrp:getname key='organ' value='${pi.makeorganid}' p='d'/></td>
            <td><windrp:getname key='users' value='${pi.makeid}' p='d'/> </td>
            <td><windrp:dateformat value='${pi.makedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='YesOrNo' value='${pi.isaudit}' p='f'/></td>
            </tr>
          </logic:iterate> 
      </table>     
      <br>
      </div>
</body>
</html>
