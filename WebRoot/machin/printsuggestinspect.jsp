<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
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
      <div align="center" class="print_title_style">拣货建议单</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
           <tr >
            <td  align="right">入库日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td >&nbsp;</td>
          </tr>
          <tr>
            <td align="right">是否排除：</td>
            <td ><windrp:getname key='IsRemove' value='${isRemove}' p='f'/></td>
            <td align="right">是否合并：</td>
            <td><windrp:getname key='IsMerge' value='${isMerge}' p='f'/></td>
            <td align="right">是否出库：</td>
            <td><windrp:getname key='IsOut' value='${isOut}' p='f'/></td>
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
           <td width="9%"  align="right">打印时间：</td>
          <td width="24%">${ptime}</td>
        </tr>
      </table>
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="13%"  >编号</td>
            <td width="9%" >单据类型</td>
            <td width="9%" >单据名称</td>
            <td width="10%" >单据编号</td>            
			<td width="10%" >来往仓库</td>
            <td width="12%" >仓库名称</td>
            <td width="7%" >客户编号</td>
            <td width="9%" >制单日期</td>
            <td width="9%" >是否过账</td>
          </tr>
	
          <logic:iterate id="s" name="also" > 
          <tr class="table-back-colorbar" > 
            <td class="" >${s.id}</td>
            <td class=""><windrp:getname key='typeId' value='${s.typeId}' p='f'/></td>
            <td class="">${s.typeName}</td>
            <td class="">${s.siid}</td>           
			<td class="">${s.disWareHouseName }</td>
            <td class="">${s.souWareHouseName }</td>
            <td class="">${s.customerCode }</td>
            <td class=""><windrp:dateformat value='${s.makeDate}' p='yyyy-MM-dd'/></td>
			<td class="">${s.isPost }</td>
          </tr>
          </logic:iterate>	
      </table>
	 
	</div>
	
</body>
</html>
