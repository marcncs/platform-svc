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
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">设定提醒日期</td>
        </tr>
      </table>
       <form name="affirmform" method="post" action="../finance/setLoanAwakeAction.do">
      <table width="100%" height="57" border="0" cellpadding="0" cellspacing="0">
	 
        <tr>
            <td width="31%" align="right"><input name="id" type="hidden" id="id" value="${id}">
            请选择日期：</td>
            <td width="37%"><input name="promisedate" type="text" id="promisedate" onFocus="javascript:selectDate(this)"></td>
            <td width="32%">&nbsp;</td>
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
