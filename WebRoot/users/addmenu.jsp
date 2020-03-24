<%@ page contentType="text/html; charset=utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>添加菜单</title>
</head>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 添加菜单</td>
      </tr>
    </table>
    <form name="addMenuForm" method="post" action="addMenuAction.do" onSubmit="return validateValidateProvide(this);">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back">
            <td  align="right">模块名：</td>
            <td>${stateselect}</td>
            <td align="right"> 菜单名 ：</td>
            <td><input type="text" name="menuname" size="20" /></td>
          </tr>
          <tr class="table-back">
            <td  align="right"> URL ：</td>
            <td><input type="text" name="url" size="20" /></td>
            <td align="right">描述：</td>
            <td><input type="text" name="describes" size="20" /></td>
          </tr>
          <tr class="table-back">
            <td  align="right">&nbsp;</td>
            <td><input type="submit" name="Submit" value="提交">
            <input type="reset" name="Submit2" value="重置"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        
        <html:javascript formName="validateProvide"/>
      </table>
      
      </form></td>
  </tr>
</table>
</body>
</html>
