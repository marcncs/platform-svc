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
.Noprint {
	display: none;
}
</style>
	</head>

<body style="overflow: auto;">
<center class="Noprint">
<div class="printstyle">
<img src="../images/print.gif" onClick="javascript:window.print();"></img>
</div>
<hr align="center" width="100%" size="1" noshade>
</center>
		<div id="abc">
			<div align="center" class="print_title_style">销售换货列表</div>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr > 
            <td width="11%"  align="right">会员名称：</td>
            <td width="26%">${cname}</td>
            <td width="8%" align="right">是否复核：</td>
            <td width="16%"><windrp:getname key="YesOrNo" p="f" value="${IsAudit}" /></td>
            <td width="10%" align="right">制单日期：</td>
            <td width="25%">${BeginDate}-${EndDate}</td>
           
          </tr>
          <tr>
            <td  align="right">是否作废：</td>
            <td><windrp:getname key="YesOrNo" p="f" value="${IsBlankOut}"/></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
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
            <td width="13%"  >编号</td>
            <td width="14%" >客户名称</td>
            <td width="9%" >联系人</td>
            <td width="10%" >换货入货仓库</td>           
            <td width="14%" >制单机构</td>
            <td width="9%" >制单人</td>
            <td width="10%" >制单日期</td>
            <td width="7%" >是否复核</td>
			<td width="7%" >是否作废</td>
			<td width="7%" >是否签收</td>
          </tr>
          <logic:iterate id="s" name="also" > 
          <tr class="table-back-colorbar" > 
            <td >${s.id}</td>
            <td>${s.cname} </td>
            <td>${s.clinkman}</td>
            <td><windrp:getname key='warehouse' value='${s.warehouseinid}' p='d'/></td>            
            <td><windrp:getname key='organ' value='${s.makeorganid}' p='d'/></td>
            <td><windrp:getname key='users' value='${s.makeid}' p='d'/></td>
            <td><windrp:dateformat value='${s.makedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
			<td><windrp:getname key='YesOrNo' value='${s.isblankout}' p='f'/></td>
			<td><windrp:getname key='YesOrNo' value='${s.isendcase}' p='f'/></td>
            </tr>
          </logic:iterate>
      </table>
	</div>

</body>
</html>
