<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ page import="com.winsafe.hbm.util.Internation"%>
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
<script language="javascript">
	function SelectCustomer(){
		var os = document.validateCustomer.objectsort.value;
		if(os==0){
		showModalDialog("toSelectUsersAction.do",null,"dialogWidth:16cm;dialogHeight:10.5cm;center:yes;status:no;scrolling:no;");
		document.validateCustomer.payeeid.value=getCookie("uid");
		document.validateCustomer.pname.value=getCookie("uname");
		}
	if(os==1){
		showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:10.5cm;center:yes;status:no;scrolling:no;");
		document.validateCustomer.payeeid.value=getCookie("cid");
		document.validateCustomer.pname.value=getCookie("cname");
		}
	if(os==2){
		showModalDialog("toSelectProvideAction.do",null,"dialogWidth:16cm;dialogHeight:10.5cm;center:yes;status:no;scrolling:no;");
		document.validateCustomer.payeeid.value=getCookie("pid");
		document.validateCustomer.pname.value=getCookie("pname");
		}

	}

function Clear(){
	document.validateCustomer.payeeid.value="";
	document.validateCustomer.pname.value="";
}

function Check(){
	var cid = document.validateCustomer.cid.value;
	
	if(cid==""){
		alert("付款方不能为空！");
		return false;
	}
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
        <td width="772"> 修改应收款</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="validateCustomer" method="post" action="updPayableAction.do" onSubmit="return Check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right"><input name="id" type="hidden" id="id" value="${paf.id}">
应收款金额：</td>
          <td width="22%"><input name="payablesum" type="text" id="payablesum" value="${paf.payablesum}"></td>
          <td width="11%" align="right">单据号：</td>
          <td width="21%"><input name="billno" type="text" id="billno" value="${paf.billno}" size="35"></td>
	      <td width="11%" align="right">描述：</td>
	      <td width="23%"><input name="payabledescribe" type="text" id="payabledescribe" value="${paf.payabledescribe}" size="35"></td>
	  </tr>
	  <tr>
	  	<td width="12%"  align="right"> 结算方式：</td>
          <td width="22%">${paf.paymodename}</td>
          <td width="11%" align="right">&nbsp;</td>
          <td width="21%">&nbsp;</td>
          <td width="11%" align="right">&nbsp;</td>
	      <td width="23%">&nbsp;</td>
	  </tr>
	  </table>
	</fieldset>
	
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center">
          <td><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
              <input type="reset" name="cancel" onClick="javascript:window.close();" value="取消"></td>

        </tr>
      
     
    </table></form></td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
