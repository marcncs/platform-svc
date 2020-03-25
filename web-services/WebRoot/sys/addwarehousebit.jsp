<%@ page contentType="text/html; charset=utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>添加仓位</title>
<script language="javascript" src="../js/prototype.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript">
var organid_hasdouble=false;
function checkWbId(){
	var wbid = $F('wbid');
	organid_hasdouble = false;
	var url = '../sys/ajaxWarehouseBitAction.do';
	var pars = 'wbid=' + wbid+"&wid=${sjwid}";
   	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showorgan}
				);
}

function showorgan(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.wb;
	if ( lk == undefined ){
	}else{	
		organid_hasdouble=true;	
		alert($F("wbid")+"此仓位编号已经存在，请重新录入！");		
	}
}

function check(){
	var wbid= document.addRoleForm.wbid.value;
	if(wbid==""){
		alert("仓位编号不能为空!");
		addRoleForm.wbid.focus();
		return false;
	}
	if(wbid.length != parseInt("${wblength}") ){
		alert("仓位编号必须为${wblength}位!");
		addRoleForm.wbid.focus();
		return false;
	}
	if ( escape(wbid).indexOf("%u")!=-1 ){
		alert("仓位编号不能为中文!");
		addRoleForm.wbid.focus();
		return false;
	}
	if ( organid_hasdouble ){
		alert($F("wbid")+"此仓位编号已经存在，请重新录入！");
		return false;
	}
	return true;
}


</script>
</head>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 添加仓位</td>
      </tr>
    </table>
	<form name="addRoleForm" method="post" action="../sys/addWarehouseBitAction.do" onSubmit="return check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">仓位编号：</td>
          <td width="21%"><input name="wbid" type="text" id="wbid" size="20" maxlength="${wblength}" onBlur="checkWbId()"/>
          <span class="style1">*</span>
          </td>
          <td width="13%" align="right">&nbsp;</td>
          <td width="23%">&nbsp;</td>
	      <td width="9%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
	  </tr>
	  </table>
	</fieldset>
	
        <table width="100%" border="0" cellpadding="0" cellspacing="1">

            <tr align="center">
              <td><input type="submit" name="Submit" value="提交">&nbsp;&nbsp;
              <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
            </tr>
      </table>
	 </td>
  </tr>
</table>
</body>
</html>
