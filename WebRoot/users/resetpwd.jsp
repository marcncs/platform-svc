<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>	
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/passwordcheck.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript"> 

	function check(){
		var password = document.getElementById("password");
		var agapassword = document.getElementById("agapassword");
		
		if(password.value.trim()==""){
			alert("新密码不能为空!");
			password.focus();
			return false;
		}
		if(agapassword.value.trim() != password.value.trim()){
			alert("重复密码与新密码不一致!");
			agapassword.select();
			return false;
		} 
		if(checkPassword(password.value) == false){
			alert("您设置的密码过于简单,建议使用8位以上的字母与数字组合作为密码!");
			agapassword.select();
			return false;
		}
		if(!checkPwd(password.value,'${username}')) {
			return false;
		} 
		showloading();
		return true;
	}
	
	function checkPassword(psd){
		var ls = -1;
		if (psd.match(/[a-z]/ig)){
			ls++;
		}
		if (psd.match(/[0-9]/ig)){
			ls++;
		}
	 	if (psd.match(/(.[^a-z0-9])/ig)){
			ls++;
		}
		if (psd.length < 6 && ls > 0){
			ls--;
		}
		
		if(ls > 0){
			return true;
		}else{
			return false;
		}
	}
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 重设密码 </td>
        </tr>
      </table>
	  <form name="addform" method="post" action="../users/resetPwdAction.do" onSubmit="return check();">
	  <input type="hidden" name="username" value="${username}">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right"><input name="uid" type="hidden" id="uid" value="${uid}">
			用户名：</td>
          <td width="20%">${username}</td>
          <td width="11%" align="right">新密码：</td>
          <td width="23%"><input name="password" type="password" id="password" maxlength="10"></td>
	      <td width="13%" align="right">重复新密码：</td>
	      <td width="25%"><input name="agapassword" type="password" id="agapassword"></td>
	  </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center"> 
            <td width="33%"> <input type="submit" name="Submit" value="确定">&nbsp;&nbsp; 
            <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();"></td>

          </tr>
        
      </table>
	  </form>
    </td>
  </tr>
</table>
</body>
</html>
