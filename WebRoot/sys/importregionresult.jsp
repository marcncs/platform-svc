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
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<script language="javascript">
	function winClose(isRefrash) 
	{ 
	window.returnValue=isRefrash; 
	if (window.opener.document.forms[0]){
		window.opener.document.forms[0].submit();
		//window.opener.location.reload();
		//window.opener.location.src=window.opener.location.src;
	}
	window.close(); 
	} 
</script>
<html:base/>
</head>
<body style="overflow: scroll;" onkeydown="if(event.keyCode==122){event.keyCode=0;return false}" onUnload="winClose(1)">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td> 
<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">处理结果</td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
          <td align="center" width="20%" >&nbsp; </td>
          <td align="left" >${result}</td>
        </tr>
        <tr>
          <td align="center" colspan="2"> 
            <input type="submit" name="Submit" value="<bean:message key='sys.close'/>" onClick="winClose(1);">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
