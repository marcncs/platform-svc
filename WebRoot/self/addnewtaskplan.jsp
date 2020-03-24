<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772"> 添加新任务与计划</td>
  </tr>
</table>
 <form name="search" method="post" action="addNewTaskPlanAction.do">
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
 
    <tr class="table-back"> 
      <td width="8%"  align="right">标题：</td>
      <td width="32%"><input name="TPTitle" type="text" id="TPTitle" maxlength="250"></td>
	  <td align="right">状态：</td>
      <td>${status}</td>      
    </tr>
    <tr class="table-back"> 
	  <td width="11%" align="right">开始日期：</td>
      <td width="49%"><input type="text" name="BeginDate" size="10" onFocus="javascript:selectDate(this)"></td>
      <td  align="right">结束日期：</td>
      <td><input type="text" name="EndDate" size="10" onFocus="javascript:selectDate(this)"></td>
    </tr>
    <tr class="table-back"> 
      <td  align="right">内容：</td>
      <td colspan="3"><textarea name="TPContent" cols="100" rows="5"></textarea></td>
    </tr>
    <tr class="table-back"> 
      <td  align="right">类别：</td>
      <td>${sort}</td>
      <td align="right">&nbsp;</td>
      <td>&nbsp; </td>
    </tr>
    <tr class="table-back"> 
      <td  align="right">&nbsp;</td>
      <td><input type="submit" name="Submit" value="确定"> 
              <input type="button" name="cancel" value="取消" onClick="javascript:history.back();"></td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  
</table>
</form>
</td>
  </tr>
</table>
</body>
</html>
