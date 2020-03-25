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
<script type="text/javascript" src="../js/function.js"></script>
<script language="javascript" src="../js/jquery-1.4.2.min.js"></script>
<SCRIPT language="javascript" src="../js/passwordcheck.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

</head>
<script language="javascript">
$(function(){
	if('${msg}'!="") {
		alert('${msg}'); 
	}
});  
	function ChkValue(){
		var password = addform.password.value;
		var agapassword = addform.agapassword.value;
		if(password.trim().length<=0){
			alert("新密码不能为空！");
			return false;
		}
		if(password.trim().length<5){
			alert("您的密码过于简单，请输入6位以上数字或字母组合的密码!");
			return false;
		}

		if(password!=agapassword){
			alert("两次输入密码不符！");
			return false;
		}

		if(!checkPwd($('#password').val(),$("#userName").val())) {
			return false;
		}
		
		return true;
	}
</script>
<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> ${menusTrace }</td>
        </tr>
      </table>
	  <form name="addform" method="post" action="../users/updSelfPwdAction.do" onSubmit="return ChkValue();" >
	  <input type="hidden" name="isForce" value="${isForce}">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">用户名：	  	  </td>
        <td width="9%"><input id="userName" name="userName" type="text" value="${userName}" readonly></td>
	  </tr>
	  <tr> 
	  	<td width="9%"  align="right">旧密码：	  	  </td>
        <td width="9%"><input id="oldpassword" name="oldpassword" type="password"></td>
	  </tr>
	  <tr>
	    <td  align="right">新密码：	      </td>
	    <td><input id="password" name="password" type="password"></td>
	  </tr>
	  <tr>
	    <td  align="right">重复新密码：	      </td>
	    <td><input name="agapassword" type="password"></td>
	  </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center"> 
            <td width="54%" > <input type="submit" name="Submit" value="确定"> &nbsp;&nbsp;
            <!-- 
			<input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();"></td>
			 -->
          </tr>
        
      </table>
	  </form>
    </td>
  </tr>
</table>
</body>
</html>
