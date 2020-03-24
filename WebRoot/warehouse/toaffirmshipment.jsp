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
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 确认发货</td>
        </tr>
      </table>
      <form name="affirmform" method="post" action="affirmShipmentAction.do">
      <table width="100%" height="57" border="0" cellpadding="0" cellspacing="0">
	  
        <tr>
            <td width="31%" align="right">请选择仓库：</td>
            <td width="37%"> 
              <select name="warehouseid">
			  <logic:iterate id="w" name="alw" > 
                <option value="${w.id}">${w.warehousename}</option>
			  </logic:iterate>
              </select></td>
            <td width="32%"> 
              <input name="shipmentid" type="hidden" id="shipmentid" value="${shipmentid}"></td>
        </tr>
        <tr>
          <td align="right">&nbsp;</td>
          <td><input type="submit" name="Submit" value="确定">
              <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          <td>&nbsp;</td>
        </tr>
		
      </table> 
      </form>
      </td>
  </tr>
</table>
</body>
</html>
