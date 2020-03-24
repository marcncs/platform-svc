<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.*,java.text.*"%>
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
<html:errors/>
<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
              <td width="772"> 客户分类详情</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%" height="20" align="right">分类名称：</td>
          <td width="25%">${cs.sortname}</td>         
	  </tr>
	  </table>
	</fieldset>

	<fieldset align="center"> <legend>
      <table width="80" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>许可用户</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="title-top">
    <td width="11%" >用户编号</td>
    <td width="89%">用户名称</td>
  </tr>
  <logic:iterate id="r" name="rvls" >
  <tr class="table-back">
          <td height="20">${r.userid}</td>
    <td>${r.useridname}</td>
  </tr>
  </logic:iterate>
</table>
</fieldset>

		
		</td>
      </tr>
    </table></td>
  </tr>
</table>

</body>
</html>
