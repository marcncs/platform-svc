<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>

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
        <td width="772"> 新增应付款</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="validateCustomer" method="post" action="addPayableAction.do" onSubmit="return Check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="11%"  align="right">应付款金额：</td>
          <td width="24%"><input name="payablesum" type="text" id="payablesum" value="0"></td>
          <td width="10%" align="right">单据号：</td>
          <td width="22%"><input name="billno" type="text" id="billno" size="15"></td>
	      <td width="10%" align="right">应付款描述：</td>
	      <td width="23%"><input name="payabledescribe" type="text" id="payabledescribe" size="35" maxlength="64"></td>
	  </tr>
	   <tr>
	  	<td width="11%"  align="right">结算方式：</td>
          <td width="24%">${paymode}</td>
          <td width="10%" align="right">&nbsp;</td>
          <td width="22%">&nbsp;</td>
          <td width="10%" align="right">&nbsp;</td>
	      <td width="23%">&nbsp;</td>
	   </tr>
	  </table>
	</fieldset>
	
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center">
          <td><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
              <input type="reset" name="cancel" onClick="javascript:window.close()" value="取消"></td>
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
