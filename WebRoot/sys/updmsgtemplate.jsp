<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function ChkValue(){
		var mobileno = document.updateform.templatename;
		var msgcontent = document.updateform.templatecontent;
		
		if(mobileno.value.trim()==""){
		alert("模板名称不能为空！");
		return false;
		}
	
		if(msgcontent.value.trim()==""){
			alert("模板内容不能为空！");
			return false;
		}
		if ( !Validator.Validate(document.updateform,2) ){
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
          <td width="772"> 修改短信模版</td>
        </tr>
      </table>
	  <form name="updateform" method="post" action="updMsgTemplateAction.do" onSubmit="return ChkValue()">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%" height="20" align="right"><input name="id" type="hidden" id="id" value="${mt.id}">
模版名称：</td>
          <td width="21%"><input name="templatename" type="text" id="templatename" value="${mt.templatename}" maxlength="200">
            <font color="#FF0000">*</font></td>
          <td width="13%" align="right">模版类型：</td>
          <td width="23%"><windrp:getname key='TemplateType' value='${mt.templatetype}' p='f'/></td>
          <td width="9%" align="right">是否启用：</td>
	      <td width="25%"><windrp:select key="YesOrNo" name="isuse" p="n|f" value="${mt.isuse}"/></td>
	  </tr>
	  <tr>
	  	<td width="9%" height="20" align="right">模版内容：</td>
          <td colspan="5"><p>
            <textarea name="templatecontent" cols="120" dataType="Limit" max="256"  msg="内容在256个字之内" require="false">${mt.templatecontent}</textarea><br><span class="td-blankout">(内容中的英文字母请不要修改，它将会替换成相应的信息，内容长度不能超过256字符。)</span>
          </td>
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
