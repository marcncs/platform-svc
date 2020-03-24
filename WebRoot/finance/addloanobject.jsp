<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function Check(){
	var uid = document.validateCustomer.uid.value;
	
	if(uid==""){
		alert("借款人不能为空！");
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
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 添加借款对象</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	
	<form name="validateCustomer" method="post" action="addLoanObjectAction.do" onSubmit="return Check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="20%"  align="right">借款人：</td>
          <td width="32%">
            <input type="hidden" name="uid" id="uid">
            <input type="text" name="uname" id="uname" onClick="selectDUW(this,'uid','','ou')" value="请选择" readonly>
            </td>
          <td width="11%" align="right">&nbsp;</td>
          <td width="37%">&nbsp;</td>
	      </tr>
	  </table>
	</fieldset>
	
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
       <tr>
          <td><div align="center">
            <input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
            <input type="reset" name="cancel" onClick="javascript:window.close()" value="取消">
          </div></td>
        </tr>
      
     
    </table>
	</form>
	</td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
