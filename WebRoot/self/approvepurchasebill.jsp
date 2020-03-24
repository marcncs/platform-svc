<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="772"> 采购单详情 </td>
  </tr>
</table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr class="table-back"> 
          <td width="17%"  align="right">供应商：</td>
          <td colspan="3">${pbf.pname}</td>
        </tr>
        <tr class="table-back"> 
          <td  align="right">采购单日期：</td>
          <td width="33%">${pbf.createdate}</td>
          <td width="16%" align="right">预计收货日期：</td>
          <td width="34%">${pbf.receivedate}</td>
        </tr>
        <tr class="table-back"> 
          <td  align="right">是否提交：</td>
          <td>${pbf.refer}</td>
          <td align="right">审阅状况：</td>
          <td>${pbf.isapprove}</td>
        </tr>
        <tr class="table-back"> 
          <td  align="right">负责人：</td>
          <td>${pbf.seeto}</td>
          <td align="right">总金额：</td>
          <td>${pbf.totalsum}</td>
        </tr>
        <tr class="table-back"> 
          <td  align="right">备注：</td>
          <td colspan="3">${pbf.remark}</td>
        </tr>
      </table>
<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="772"> 采购单产品列表</td>
  </tr>
</table>
<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top"> 
          <td width="57%" >产品</td>
          <td width="13%">单位</td>
          <td width="13%">单价</td>
          <td width="5%">数量</td>
          <td width="12%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr align="center" class="table-back"> 
          <td >${p.productname}</td>
          <td>${p.unitname}</td>
          <td>${p.unitprice}</td>
          <td>${p.quantity}</td>
          <td>${p.subsum}</td>
        </tr>
        </logic:iterate> 
      </table>
<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="772"> 审阅信息</td>
  </tr>
</table>
<form name="addform" method="post" action="approvePurchaseBillAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back"> 
            <td  align="right"><input name="billid" type="hidden" id="billid" value="${billid}">
            审阅状态：</td>
            <td>${approvestatus}</td>
          </tr>
          <tr class="table-back"> 
            <td width="13%" align="right" valign="top"> 审阅内容：</td>
            <td width="87%"><textarea name="approvecontent" cols="80" rows="10" id="approvecontent"></textarea></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right" valign="top">&nbsp;</td>
            <td><input type="submit" name="Submit2" value="确定"> &nbsp;&nbsp; 
              <input type="button" name="Submit2" value="取消" onClick="javascript:history.back();"></td>
          </tr>
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
