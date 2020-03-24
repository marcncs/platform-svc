<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/validate.js"> </SCRIPT>
<script type="text/javascript" src="../js/function.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function check(){
	var gradename = document.getElementById("gradename");
	var integralrate = document.getElementById("integralrate");
		if(gradename.value.trim()==""){
			alert("会员级别名不能为空!");
			return false;
		}
		if(integralrate.value.trim()==""){
			alert("积分比例不能为空!");
			return false;
		}
		if ( !IsNumber(integralrate.value) ){
			alert("积分比例必须是数字类型!");
			integralrate.select();
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
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增会员级别</td>
        </tr>
      </table>
	  <form name="updateform" method="post" action="../sys/addMemberGradeAction.do" onSubmit="return check();">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%" height="20" align="right"><input name="id" type="hidden" id="id" value="${wf.id}">
会员级别名：</td>
          <td width="20%"><input name="gradename" type="text" id="gradename" maxlength="30">
            <font color="#FF0000">*</font></td>
          <td width="13%" align="right">享受价格：</td>
          <td width="21%"><windrp:select key="PricePolicy" name="policyid" p="n|d"/></td>
          <td width="9%" align="right">积分比例：</td>
	      <td width="25%"><input name="integralrate" type="text" id="integralrate" maxlength="8" onKeyPress="KeyPress(this)"></td>
	  </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center"> 
            <td width="28%" height="20"><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
            <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();"></td>
          </tr>
        
	  </table></form>
    </td>
  </tr>
</table>
</body>
</html>
