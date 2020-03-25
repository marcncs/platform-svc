<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript" src="../js/function.js"> </script>
  
<script language="javascript">
	var bb = setTimeout("winClose(1);",300);

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
</head>

<body >
 
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr> 
    <td> 
      <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> <bean:message key="sys.operator.dealresult"/></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="center"><bean:message bundle="purchase" key="${result}" /></td>
        </tr>
        <tr> 
          <td align="center"><input type="submit" name="Submit" value="<bean:message key='sys.close'/>"> 
          </td>
        </tr>
      </table>
      </td>
  </tr>
</table>
</body>
</html>
