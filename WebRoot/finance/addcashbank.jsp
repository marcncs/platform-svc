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
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script language="javascript">
function Check(){
	var cbname = document.validateCustomer.cbname.value.trim();
	
	if(cbname==""){
		alert("现金银行名称不能为空！");
		return false;
	}
	showloading();
	return true;
}
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 添加现金银行</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	
	<form name="validateCustomer" method="post" action="addCashBankAction.do" onSubmit="return Check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="17%"  align="right">现金银行名称：</td>
          <td width="35%"><input name="cbname" type="text" id="cbname" size="35" maxlength="32"></td>
          <td width="11%" align="right">当前金额：</td>
          <td width="37%"><input name="totalsum" type="text" id="totalsum" value="0" onKeyPress="KeyPress(this)" maxlength="10"></td>
	      </tr>
	  </table>
	</fieldset>
	
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
       <tr>
          <td><div align="center">
            <input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
            <input type="reset" name="cancel" onClick="javascript:window.close()" value="取消">
          </div></td>
        </tr>
      
     
    </table>
	</form>
	</td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
