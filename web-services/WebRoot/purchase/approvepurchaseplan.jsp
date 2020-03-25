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
    <td width="772"> 采购计划单详情 </td>
  </tr>
</table>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right">采购类型：</td>
          <td width="25%">${ppf.purchasesortname}</td>
          <td width="8%" align="right">计划日期：</td>
          <td width="23%">${ppf.plandate}</td>
	      <td width="9%" align="right">计划部门：</td>
	      <td width="25%">${ppf.plandeptname}</td>
	  </tr>
	  <tr>
	    <td  align="right">计划人：</td>
	    <td>${ppf.planidname}</td>
	    <td align="right">备注：</td>
	    <td colspan="3">${ppf.remark}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>状态信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right">是否复核：</td>
          <td width="25%">${ppf.isauditname}</td>
          <td width="8%" align="right">复核人：</td>
          <td width="23%">${ppf.auditidname}</td>
	      <td width="9%" align="right">复核日期：</td>
	      <td width="25%">${ppf.auditdate}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否批准：</td>
	    <td>${ppf.isratifyname}</td>
	    <td align="right">批准人：</td>
	    <td>${ppf.ratifyidname}</td>
	    <td align="right">批准日期：</td>
	    <td>${ppf.ratifydate}</td>
	    </tr>
	  <tr>
	    <td  align="right">是否提交：</td>
	    <td>${ppf.isrefername}</td>
	    <td align="right">是否审阅：</td>
	    <td>${ppf.approvestatusname}</td>
	    <td align="right">审阅日期：</td>
	    <td>${ppf.approvedate}</td>
	    </tr>
	  <tr>
	    <td  align="right">是否转采购单：</td>
	    <td>${ppf.iscompletename}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>采购计划单产品列表</td>
        </tr>
      </table>
	  </legend>
<table width="100%"  border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="title-top">
    <td width="12%">产品编号</td>
    <td width="17%" >产品名称</td>
    <td width="17%">规格</td>
    <td width="7%">单位</td>
    <td width="6%">单价</td>
    <td width="6%">数量</td>
    <td width="10%">需求日期</td>
    <td width="9%">建议定购日期</td>
    <td width="16%">说明</td>
  </tr>
  <logic:iterate id="p" name="als" >
    <tr class="table-back">
      <td>${p.productid}</td>
      <td >${p.productname}</td>
      <td>${p.specmode}</td>
      <td>${p.unitname}</td>
      <td>${p.unitprice}</td>
      <td>${p.quantity}</td>
      <td>${p.requiredate}</td>
      <td>${p.advicedate}</td>
      <td>${p.requireexplain}</td>
    </tr>
  </logic:iterate>
</table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>审阅信息</td>
        </tr>
      </table>
	  </legend>
  <form name="addform" method="post" action="approvePurchasePlanAction.do">
<table width="100%"  border="0" cellpadding="0" cellspacing="1">

    <tr class="table-back">
      <td  align="right"><input name="ppid" type="hidden" id="ppid" value="${ppid}">
        审阅状态：</td>
      <td>${approvestatus}</td>
    </tr>
    <tr class="table-back">
      <td  align="right"><input name="actid" type="hidden" id="actid" value="${actid}">
        动作：</td>
      <td>${stractid}</td>
    </tr>
    <tr class="table-back">
      <td width="13%"  align="right" valign="top">审阅内容：</td>
      <td width="87%"><textarea name="approvecontent" cols="80" rows="10" id="approvecontent"></textarea></td>
    </tr>
    <tr class="table-back">
      <td  align="right" valign="top">&nbsp;</td>
      <td><input type="submit" name="Submit2" value="确定">
&nbsp;&nbsp;
        <input type="button" name="Submit2" value="取消" onClick="javascript:history.back();"></td>
    </tr>

</table>
  </form>
</fieldset>

</td>
  </tr>
</table>
</body>
</html>
