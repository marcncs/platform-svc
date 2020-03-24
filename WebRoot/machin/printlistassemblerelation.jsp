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
      <div align="center" class="print_title_style">组装关系</div>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td width="10%"  align="right">制单机构：</td>
            <td width="18%">${oname}</td>
            <td width="10%" align="right">制单部门：</td>
            <td width="25%">${deptname}</td>
            <td width="9%" align="right">制单人：</td>
            <td width="28%">${uname}</td>
          </tr>
          <tr >
            <td  align="right">是否复核：</td>
            <td><windrp:getname key="YesOrNo"  p="f" value="${IsAudit}"/></td>
            <td align="right">制单日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">关键字：</td>
            <td>${KeyWord}</td>
         
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
            <td width="10%" >序号</td>
            <td width="13%" >组装产品编码</td>
            <td width="23%" >组装产品名称</td>
            <td width="14%" >规格</td>
            <td width="10%" >单位</td>
            <td width="8%" >数量</td>
            <td width="9%" >是否复核</td>
          </tr>
	<logic:iterate id="s" name="alsb" > 
          <tr class="table-back-colorbar" > 
            <td >${s.id}</td>
            <td >${s.arproductid}</td>
            <td >${s.arproductname}</td>
            <td >${s.arspecmode}</td>
            <td ><windrp:getname key='CountUnit' value='${s.arunitid}' p='d'/></td>
            <td >${s.arquantity}</td>
            <td ><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
            </tr>
		  </logic:iterate>
      </table>
	  </div>
</body>
</html>
