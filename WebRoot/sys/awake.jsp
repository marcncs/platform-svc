<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<script language="javascript">
	function commit()
	{
		//alert("alert");
		//document.form1.action="affrieAwakeAction.do";
		document.form1.submit();
		//setTimeout("window.close()",50);
		setInterval("window.close()",10);
	}
</script>
<title><bean:message key="sys.awake.systemawake"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body onBlur="this.focus()" onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td> <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> <bean:message key="sys.awake.systemawake"/></td>
        </tr>
      </table>
       <form name="form1" method="post" action="../self/affrieAwakeAction.do">
      <table width="100%" height="99" border="0" cellpadding="2" cellspacing="5">
       
          <tr> 
            <td width="80%"> <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-back">
				<tr> 
                  <td><input name="ID" type="hidden" id="ID" value="${ca.id}"></td>
                </tr>

				<tr>  <td align="center"><textarea style="width:450px; height:100px; border:0px; overflow:auto; background-color:#E4F7D8" onFocus="this.blur()">${ca.awakecontent}</textarea></td></tr>
                <tr> 
                  <td>&nbsp;</td>
                </tr>				
              </table>
              <input type="button" name="Submit" onClick="commit();" value="<bean:message key='sys.affirm'/>" ></td>
          </tr>
       
      </table>
 </form>
</td>
  </tr>
</table>
</body>
</html>
