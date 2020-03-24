<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="com.winsafe.hbm.util.Internation"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>
<html:errors/>
<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 专卖店活动详情</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
     <form name="addform" method="post" action="updRegieAction.do" >
    <table width="100%"  border="0" cellpadding="0" cellspacing="1">
     
        <tr class="table-back">
          <td width="11%"  align="right">活动类型：</td>
          <td width="38%">${rpf.ploytypename}</td>
          <td width="13%" align="right">活动日期：</td>
          <td width="38%">${rpf.ploydate}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">记录人员：</td>
          <td>${rpf.makeidname} </td>
          <td align="right">记录日期：</td>
          <td>${rpf.makedate}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">活动内容：</td>
          <td colspan="3">${rpf.ploycontent}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">&nbsp;</td>
          <td>
              <input type="reset" name="cancel" onClick="javascript:history.back()" value="返回"></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
     
     
    </table> </form></td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
