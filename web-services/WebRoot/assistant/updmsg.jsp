<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>

<head>
<title>发送短信</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectCustomer(){
	window.open("../sales/selectMultiSaleOrderCustomerAction.do",null,"height=600,width=850,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");
	
	}
	
	function ChkValue(){
		var mobileno = document.msg.mobileno;
		var msgcontent = document.msg.msgcontent;
		var phonefile = document.msg.phonefile;
		var seltel=document.msg.seltel;
		var seltelvalue=1;
		for(i=0; i<seltel.length; i++){
			if (seltel[i].checked){
				seltelvalue=seltel[i].value;
			}
		}
		if ( seltelvalue== 1 ){
			if(mobileno.value.trim()==""){
			alert("手机号码不能为空！");
			return false;
			}
		}
		if ( seltelvalue== 2 ){
			if(phonefile.value.trim()==""){
			alert("手机号码不能为空！");
			return false;
			}
		}
		if(msgcontent.value.trim()==""){
		alert("短信内容不能为空！");
		return false;
		}
		if ( !Validator.Validate(document.msg,2) ){
			return false;
		}
		showloading();	
		return true;
	}
	function addItemValue(obj){
		var mobileno = document.msg.mobileno.value;
		if ( mobileno == "" ){
			document.msg.mobileno.value = obj.mobile;
		}else{
			document.msg.mobileno.value = mobileno +"," +obj.mobile;
		}
	}
	function selectFile(obj){
		if ( obj.value == 1 ){
			document.getElementById("handid").style.display="";
			document.getElementById("fileid").style.display="none";
		}else if ( obj.value==2){
			document.getElementById("handid").style.display="none";
			document.getElementById("fileid").style.display="";
		}
	}
</script>
</head>
<html:errors/>
<body style="overflow:auto">
<form method="post" name="msg" enctype="multipart/form-data"  action="updMsgAction.do" onSubmit="return ChkValue();">
<input type="hidden" name="ID" value="${msg.id}">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
              <td width="772"> 修改短信</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>发送信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="13%"  align="right">手机号码：</td>
        <td width="87%"><input type="radio" value="1" name="seltel" onClick="selectFile(this)" checked>手动录入<input type="radio" name="seltel" onClick="selectFile(this)"  value="2">文件导入</td>          
	  </tr>
	   <tr>
	  	<td width="13%"  align="right">&nbsp;</td>
        <td width="87%">
		<div id="handid"><input type="text" name="mobileno" size="60" value="${msg.mobileno}" maxlength="1000">(多个手机号码请用逗号','隔开)</div>
		<div id="fileid" style="display:none"><input name="phonefile" type="file" id="phonefile" dataType="Filter" accept="txt" msg="只能上传txt格式的文件!" require="false">(只能导入txt文件，内容格式：一行一个手机号码)</div></td>          
	  </tr>
	  <tr>
	  	<td width="13%" align="right">短信内容：</td>
        <td><textarea name="msgcontent" cols="100" id="msgcontent" dataType="Limit" max="256"  msg="内容在256个字之内" require="false">${msg.msgcontent}</textarea><br><span class="td-blankout">(内容长度不能超过256字符)</span></td>
	  </tr>
	  <tr>
	  	<td width="13%" align="right">是否立刻发送：</td>
        <td><input type="radio" value="1" name="quicksend"  checked>否&nbsp;&nbsp;
		<input type="radio" name="quicksend" value="2">是</td>
	  </tr>
	  <tr>
        <td colspan="2" align="center"><input type="submit" value="提交" >&nbsp;&nbsp;<input type="button" value="取消" onClick="window.close();"></td>
	  </tr>
	  </table>
	</fieldset>
		
		</td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>
