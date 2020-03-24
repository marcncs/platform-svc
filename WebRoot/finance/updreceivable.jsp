<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script language="javascript">

function Check(){
	var receivablesum = document.validateCustomer.receivablesum.value;
	
	if(receivablesum==""){
		alert("应付款金额不能为空！");
		return false;
	}
	
	if ( !Validator.Validate(document.validateCustomer,2) ){
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
        <td width="772"> 修改应收款结算单</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="validateCustomer" method="post" action="updReceivableAction.do" onSubmit="return Check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="15%"  align="right"><input name="id" type="hidden" id="id" value="${rf.id}">
应收款结算金额：</td>
          <td width="22%"><input name="receivablesum" type="text" id="receivablesum" value="${rf.receivablesum}" dataType="Double" msg="金额只能是数字!" require="false"></td>
          <td width="9%" align="right">单据号：</td>
          <td width="23%"><input name="billno" type="text" id="billno" value="${rf.billno}" size="15"></td>
          <td width="8%" align="right">描述：</td>
	      <td width="23%"><input name="receivabledescribe" type="text" id="receivabledescribe" value="${rf.receivabledescribe}" size="35" maxlength="128"></td>
	  </tr>
	   <tr>
	  	<td width="15%"  align="right">
结算方式：</td>
          <td width="22%">${rf.paymentmodename}</td>
          <td width="9%" align="right">&nbsp;</td>
          <td width="23%">&nbsp;</td>
          <td width="8%" align="right">&nbsp;</td>
	      <td width="23%">&nbsp;</td>
	   </tr>
	  </table>
	</fieldset>
	
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">

        <tr>

          <td><div align="center">
            <input type="submit" name="Submit" value="确定">
            &nbsp;&nbsp;
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
