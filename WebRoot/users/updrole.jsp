<%@ page contentType="text/html; charset=utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>修改角色</title>
</head>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/validator.js"></script>
<body>
 <form name="addRoleForm" method="post" action="updRoleAction.do" onSubmit="return Validator.Validate(this,2)">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 修改角色</td>
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
              <td  align="right"><input type="hidden" name="id" value="${currentRole.id}" >角色名：</td>
              <td ><input type="text" name="rolename" size="20" dataType="Require" msg="必须录入角色名!" value="${currentRole.rolename}" maxlength="50"/><span class="style1">*</span></td>
              <td  align="right">描述：</td>
              <td ><input type="text" name="describes" size="20" value="${currentRole.describes}"  maxlength="100"/></td>
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
