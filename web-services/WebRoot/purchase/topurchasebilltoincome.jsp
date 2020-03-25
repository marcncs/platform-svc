<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert('本页拒绝使用右键!');}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择入货仓库 </td>
        </tr>
      </table>
      <form name="affirmform" method="post" action="../purchase/affirmPurchaseBillToIncomeAction.do">
      <table width="100%" height="69" border="0" cellpadding="0" cellspacing="0">
	  
        <tr>
            <td width="23%"  align="right"><input name="PBID" type="hidden" id="PBID" value="${pbid}">
            请选择仓库：</td>
            <td width="33%"><select name="warehouseid">
              <logic:iterate id="w" name="alw" >
                <option value="${w.id}">${w.warehousename}</option>
              </logic:iterate>
            </select></td>
            <td width="7%" align="right">&nbsp;</td>
            <td width="37%">&nbsp;</td>
        </tr>
        <tr>
          <td  align="right">批次：</td>
          <td><input name="batch" type="text" id="batch" value="${curdate}" ${isbatch}></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td align="right">&nbsp;</td>
          <td><input type="submit" name="Submit" value="确定">
              <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
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
