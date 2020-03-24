<%@ page contentType="text/html; charset=utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>修改用户组</title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/validator.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script language="javascript">
function SelectRole(){ 
	var c=showModalDialog("../common/selectRoleAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( c == undefined ){
		return;
	}	
	document.updUserGroup.roleId.value=c.id;
	document.updUserGroup.roleName.value=c.rolename;
}
function checkValue(form){
	if(!Validator.Validate(form,2)) { 
		return false;
	}
	showloading();
	return true;
}
</script>
</head>

<body>
 <form id="updUserGroup" name="updUserGroup" method="post" action="updUserGroupAction.do" onSubmit="return checkValue(this)">
	<input type="hidden" name="groupId" value="${groupId}">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 修改用户组</td>
      </tr>
    </table>
    <fieldset align="center"> <legend>
      <table >
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
        <table width="100%" border="0" cellpadding="0" cellspacing="1">
          
            <tr >
              <td  align="right">用户组名：</td>
              <td><input type="text" name="groupName" size="20" dataType="Require" msg="必须录入用户组名!" maxlength="50" value="${currentGroup.groupName}"/><span class="style1">*</span></td>
              <td align="right">描述：</td>
              <td><input name="description" type="text" value="无" size="20" maxlength="100" value="${currentGroup.description}"/></td>
            </tr>
            <tr >
              <td  align="right">角色：</td>
              <td>
              <input type="hidden" id="roleId" name="roleId" value="${currentGroup.roleId}">
              <input name="roleName" type="text" dataType="Require" msg="必须选择角色!" id="roleName" value="${currentGroup.roleName}" maxlength="128" readonly><a href="javascript:SelectRole();">
              <img src="../images/CN/find.gif" width="17" height="18" border="0" align="absmiddle"> </a>
              <span class="style1">*</span></td>
              <td align="right"></td>
              <td></td>
            </tr>
      </table>
      </fieldset>
		
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td><div align="center">
                  <input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                  <input type="button" name="cancel" onClick="javascript:window.close()" value="取消">
                </div></td>
              </tr>
        </table>
      </td>
  </tr>
</table>
          </form>
</body>
</html>
