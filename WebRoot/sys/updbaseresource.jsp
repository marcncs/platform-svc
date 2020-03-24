<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
	function CheckInput(){
		var tagsubvalue =referform.tagsubvalue.value;
		if(tagsubvalue==null||tagsubvalue==""){
			alert("值不能为空!");
			return false;
		}
		return true;
	}
</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改资源</td>
        </tr>
      </table>
	  <form name="referform" method="post" action="../sys/updBaseResourceAction.do" onSubmit="return CheckInput();">
	  <input type="hidden" name="id" value="${br.id}">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%" height="20" align="right">资源名：</td>
          <td width="21%"><input name="tagname" type="text" id="tagname" value="${br.tagname}" readonly></td>
          <td width="13%" align="right">键：</td>
          <td width="23%"><input name="tagsubkey" type="text" id="tagsubkey" value="${br.tagsubkey}" readonly></td>
	      <td width="9%" align="right">值：</td>
	      <td width="25%"><input name="tagsubvalue" type="text" id="tagsubvalue" value="${br.tagsubvalue}"><span class="style1">*</span></td>
	  </tr>
	   <tr>
	  	<td width="9%" height="20" align="right">是否可用：</td>
          <td width="21%"><windrp:select key="YesOrNo" name="isuse" p="n|f" value="${br.isuse}"/></td>
          <td width="13%" align="right">&nbsp;</td>
          <td width="23%">&nbsp;</td>
          <td width="9%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
	  </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center"> 
            <td width="28%" > <input type="submit" name="Submit" value="确定">&nbsp;&nbsp; <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();"></td>
          </tr>
        
		 </table>
		 </form>
    </td>
  </tr>
</table>
</body>
</html>
