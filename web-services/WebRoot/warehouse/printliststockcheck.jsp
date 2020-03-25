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
		<div align="center" class="print_title_style">库存盘点</div>
         <table width="100%" border="0" cellpadding="0" cellspacing="0">
           <tr>
            <td align="right">盘点仓库：</td>
            <td ><windrp:getname key='warehouse' value='${WarehouseID}' p='d'/></td>
            <td align="right">是否复核：</td>
            <td><windrp:getname key='YesOrNo' value='${IsAudit}' p='f'/></td>
            <td align="right">是否生成入库出库：</td>
            <td><windrp:getname key='YesOrNo' value='${IsCreate}' p='f'/></td>
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
            <td  align="right">盘点日期：</td>
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
           <td width="15%"  align="right">打印时间：</td>
          <td width="18%">${ptime}</td>
        </tr>
      </table>
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="12%" >编号</td>
            <td width="13%" >盘点仓库</td>
            <td width="10%" >仓位</td>
            <td width="8%" >盘点日期</td>            
            <td width="21%" >制单机构</td>
            <td width="13%" >制单人</td>
            <td width="8%" >是否复核</td>
			<td width="12%" >是否生成入库出库</td>
            
          </tr>
          <logic:iterate id="sa" name="als" > 
          <tr class="table-back-colorbar" > 
            <td>${sa.id}</td>
            <td><windrp:getname key='warehouse' value='${sa.warehouseid}' p='d'/></td>
            <td>${sa.warehousebit}</td>
            <td><windrp:dateformat value='${sa.makedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='organ' value='${sa.makeorganid}' p='d'/></td>
            <td><windrp:getname key='users' value='${sa.makeid}' p='d'/></td>
            <td><windrp:getname key='YesOrNo' value='${sa.isaudit}' p='f'/></td>
			<td><windrp:getname key='YesOrNo' value='${sa.iscreate}' p='f'/></td>
            
          </tr>
          </logic:iterate> 
      </table>
      </div>
</body>
</html>
