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
<title></title>
<html:base/>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}" onUnload="history.back()">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td> 
<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> <bean:message key="sys.lockrecord.flowerror"/></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr> 
          <td align="center">${result}</td>
        </tr>
        <tr>
          <td align="center"> 
<input type="submit" name="Submit" value="<bean:message key='sys.return'/>" onClick="javascript:history.back();">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
