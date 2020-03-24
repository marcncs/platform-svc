<%@ page contentType="text/html; charset=utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<script type="text/javascript">


function check(){
	if(document.addRoleForm.deptname.value.trim()==""){
		alert("部门名称不能为空!");
		addRoleForm.deptname.focus();
		return false;
	}
	showloading();
	return true;
}


</script>
<title>修改部门</title>
</head>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 修改部门</td>
      </tr>
    </table>
	<form name="addRoleForm" method="post" action="updDeptAction.do" onSubmit="return check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">部门名：</td>
          <td width="21%"><input name="ID" type="hidden" id="ID" value="${d.id}">
          <input type="hidden" name="hiddendept">
          <input name="deptname" type="text" id="deptname" value="${d.deptname}" size="20" onClick="selectDUW(this,'hiddendept','','dn')" maxlength="32"/>
          <span class="STYLE1">*</span></td>
          <td width="13%" align="right">&nbsp;</td>
          <td width="23%">&nbsp;</td>
	      <td width="9%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
	  </tr>
	  </table>
      <br><br><br><br><br>
	</fieldset>
	
        <table width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr align="center">
              <td><input type="submit" name="Submit" value="提交">&nbsp;&nbsp;
              <input type="button" name="Submit2" value="取消" onClick="window.close()"></td>
            </tr>
          
      </table></form>
	  </td>
  </tr>
</table>
</body>
</html>
