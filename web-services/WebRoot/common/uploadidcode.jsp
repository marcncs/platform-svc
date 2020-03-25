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
	if ( !Validator.Validate(document.refer,2) ){
		return false;
	}
	showloading();
	return true;
}
</script>
</head>
<html:errors/>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
              <td width="772">上传采集数据文件</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<form name="refer" method="post" enctype="multipart/form-data" action="../common/uploadIdcodeAction.do" onSubmit="return formcheck()">
		<input type="hidden" name="billsort" value="${billsort}">
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right"></td>
          <td width="21%">选择TXT文件：
            <input name="idcodefile" type="file" id="idcodefile" size="40" dataType="Filter" accept="txt" msg="只能上传txt格式的文件!"></td>
          <td width="13%" align="right">&nbsp;</td>
          <td width="23%">&nbsp;</td>
          </tr>
	  </table>
	</fieldset>
		
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr align="center">
                <td ><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                    <input type="button" name="cancel" onClick="window.close()" value="取消"></td>
                </tr>
        </table> 
		</form>
		</td>
      </tr>
    </table></td>
  </tr>
</table>

</body>
</html>
