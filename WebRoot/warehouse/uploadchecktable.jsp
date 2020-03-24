<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<script language="JavaScript">
function formcheck(){
	if ( !Validator.Validate(document.validateProvide,2) ){
		return false;
	}
	showloading();
	return true;
}
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 上传盘点单 </td>
        </tr>
      </table>
              <form action="../warehouse/importCheckTableAction.do" method="post" enctype="multipart/form-data" name="validateProvide" onSubmit="return formcheck()">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr> 
            <td >
			
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">选择excel文件：</td>
          <td width="21%"><input type="file" name="doc" dataType="Filter" accept="xls" msg="只能上传xls格式的文件!">
            <input type="submit" name="Submit" value="上传"></td>
          </tr>
	  <tr>
	    <td  align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
			
			</td>
          </tr>
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
