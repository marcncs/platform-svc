<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
</head>

<body>
<form name="init" action="initsysAction.do" method="post">
<table width="75%" height="166" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="36%">&nbsp;</td>
    <td width="31%" align="center">&nbsp;</td>
    <td width="33%">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td align="center">欢迎进入Super Sales 初始化介面，请点击下面按钮对系统进行初始化。如系统已初始化,请退出此介面。</td>
    <td>&nbsp;</td>
  </tr>
  
  <tr>
  
    <td>&nbsp;</td>
	
    <td align="center"><input name="init" type="submit" id="init" value="初始化">&nbsp;&nbsp;
      <input name="exit" type="button" id="exit" value="退出"></td>
    <td>&nbsp;</td>
	
  </tr>
  
</table>
</form>
</body>
</html>
