<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<script type="text/javascript">
function ChkValue(){
        var doc = document.validateCorrelationDocument.doc;
		var documentname = document.validateCorrelationDocument.documentname;
		if(documentname.value==null||documentname.value==""||documentname.value.trim()==""){
			alert("文档名不能为空!");
			return false;
		}
		if(doc.value==null||doc.value==""){
			alert("请选择上传的文件!");
			return false;
		}
		
		showloading();
		return true;
	}
</script>
<head>
<title>上传文档</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>
<html:errors/>
<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
              <td width="772"> 上传新文档</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<form name="validateCorrelationDocument" method="post" enctype="multipart/form-data" 
		action="addDocumentAction.do" onsubmit="return ChkValue();" >
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">文档名：</td>
          <td><input name="documentname" type="text" >
          <span class="STYLE1">*</span></td>
          <td align="right">选择文件：</td>
          <td ><input name="doc" type="file" size="50"></td>
	  </tr>
	  <tr>
	  	<td align="right">描述：</td>
	  	 <td colspan="3"><input name="description" type="text" id="description" size="80" maxlength="128"></td>
	  </tr>
	  </table>
	</fieldset>
		
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
            
              <tr>
                <td width="32%"><div align="center">
                  <input type="submit" name="Submit" value="确定">
                  &nbsp;&nbsp;
                  <input type="reset" name="cancel" onClick="javascript:window.close();" value="取消">
                </div></td>
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
