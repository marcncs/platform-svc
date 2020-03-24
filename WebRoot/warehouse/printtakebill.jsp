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
      <div align="center" class="print_title_style">检货清单</div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
           <tr >
            <td  align="right">对象类型：</td>
            <td><windrp:getname key='ObjectSort' value='${ObjectSort}' p='f'/></td>
            <td align="right">对象名称：</td>
            <td>${OName}</td>
            <td align="right">是否关闭：</td>
            <td ><windrp:getname key='YesOrNo' value='${IsAudit}' p='f'/></td>
          </tr>
           <tr >
            <td  align="right">制单机构：</td>
            <td>${oname}</td>
            <td align="right">制单部门：</td>
            <td>${deptname}</td>
            <td align="right">制单人：</td>
            <td >${uname}</td>
          </tr>
          <tr>
            <td align="right">是否作废：</td>
            <td ><windrp:getname key='YesOrNo' value='${IsBlankOut}' p='f'/></td>
            <td align="right">制单日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">单据类型：</td>
            <td><windrp:getname key='BSort' value='${BSort}' p='f'/></td>
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
            <td width="9%" >对象名称</td>
            <td width="9%" >收货人</td>
            <td width="10%" >电话</td>            
			<td width="10%" >制单机构</td>
            <td width="12%" >配送机构</td>
            <td width="7%" >制单人</td>
            <td width="9%" >收货仓库</td>
            <td width="9%" >送货日期</td>
			<td width="6%" >是否关闭</td>
            <td width="6%" >是否作废</td>
          </tr>
	
          <logic:iterate id="s" name="also" > 
          <tr class="table-back-colorbar" > 
            <td class="${s.isblankout==1?'text2-red':''}" >${s.id}</td>
            <td class="${s.isblankout==1?'text2-red':''}">${s.oname}</td>
            <td class="${s.isblankout==1?'text2-red':''}">${s.rlinkman}</td>
            <td class="${s.isblankout==1?'text2-red':''}">${s.tel}</td>           
			<td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='organ' value='${s.makeorganid}' p='d'/></td>
            <td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='organ' value='${s.equiporganid}' p='d'/></td>
            <td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='users' value='${s.makeid}' p='d'/></td>
            <td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='warehouse' value='${s.inwarehouseid}' p='d'/></td>
            <td class="${s.isblankout==1?'text2-red':''}"><windrp:dateformat value='${s.senddate}' p='yyyy-MM-dd'/></td>
			 <td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
            <td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='YesOrNo' value='${s.isblankout}' p='f'/></td>
          </tr>
          </logic:iterate>	
      </table>
	 
	</div>
	
</body>
</html>
