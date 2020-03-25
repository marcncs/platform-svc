<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">


function check(){
	if(document.addRoleForm.deptname.value==""){
		alert("部门名称不能为空!");
		addRoleForm.deptname.focus();
		return false;
	}
	return true;
}


</script>
<title>添加角色</title>
</head>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 修改入库扫描设置</td>
      </tr>
    </table>
	<form name="addRoleForm" method="post" action="updOrganScanAction.do" >
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">表单名称：</td>
          <td width="21%"><input name="ID" type="hidden" id="ID" value="${os.id}">${sc.bname}</td>
          <td width="13%" align="right">是否扫描：</td>
          <td width="23%"><windrp:select key="YesOrNo" name="isscan" p="n|f" value="${os.isscan}"/></td>
	      <td width="9%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
	  </tr>
	  </table>
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
