<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script language=javascript>
var organid_hasdouble=false;
function checkOrganId(){ 
	var oid = $F('lcode');
	organid_hasdouble = false;
	var url = '../sys/ajaxICodeAction.do';
	var pars = 'lcode=' + oid;
   	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showorgan}
				);
}

function showorgan(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.icode;
	if ( lk == undefined ){
	}else{	
		organid_hasdouble=true;	
		alert($F("lcode")+"此物流码前缀已经存在，请重新录入！");		
	}
}

function CheckInput(){
	var lcode =document.getElementById("lcode").value.trim();
	if(lcode==null||lcode==""){
		alert("物流码前缀不能为空!");
		return false;
	}
	//if ( lcode.length!=parseInt('${iclength}') ){
	//	alert("物流码前缀必须${iclength}位，请重新录入！");		
	//	return false;
	//}
	if ( lcode.length!=4){
		alert("物流码前缀必须4位，请重新录入！");		
		return false;
	}
	if ( organid_hasdouble ){
		alert($F("lcode")+"此物流码前缀已经存在，请重新录入！");		
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
          <td width="772"> 新增物流码前缀</td>
        </tr>
      </table>
	  <form name="referform" method="post" action="../sys/addICodeAction.do" onSubmit="return CheckInput();">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	   <tr>
	  	<td width="9%" height="20" align="right">产品名称：</td>
          <td width="21%"><input type="hidden" name="pid" value="${p.id}">${p.productname}</td>
          <td width="13%" align="right">物流码前缀：</td>
          <td width="23%"><input name="lcode" type="text" id="lcode" size="8" maxlength="8" onBlur="checkOrganId()">
            <span class="text-red3">*</span></td>
          <td width="13%" align="right">&nbsp;</td>
	      <td width="21%">&nbsp;</td>
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
