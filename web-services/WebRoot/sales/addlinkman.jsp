<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<script language="javascript">

function formChk(){
	
	if ( !Validator.Validate(document.validateLinkman,2) ){
		return false;
	}
	
	showloading();
	return true;
 }

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>
<html:errors/>
<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
              <td width="772"> 添加联系人</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		
		<form name="validateLinkman" method="post" action="addLinkManAction.do" onSubmit="return formChk();">
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right"><input name="cid" type="hidden" id="cid" value="${cid}" >
	  	  会员名称：</td>
          <td width="24%"><input name="cname" type="text" id="cname" value="${cname}"  readonly>
          <td width="10%" align="right">联系人姓名：</td>
          <td width="21%"><input name="name" type="text" maxlength="30" dataType="Require" msg="联系人姓名不能为空!">
            <span class="STYLE1">*</span></td>
	      <td width="10%" align="right">联系人性别：</td>
	      <td width="23%">${sexselect}</td>
	  </tr>
	  <tr>
	    <td  align="right">身份证号码：</td>
	    <td><input name="idcard" type="text" ></td>
	    <td align="right">联系人生日：</td>
	    <td><input type="text" name="birthday" onFocus="javascript:selectDate(this)"></td>
	    <td align="right">部门：</td>
	    <td><input name="department" type="text" ></td>
	    </tr>
	  <tr>
	    <td  align="right">职务：</td>
	    <td><input type="text" name="duty" ></td>
	    <td align="right">主联系人：</td>
	    <td>${ismanselect}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>联系方式</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right">办公电话：</td>
          <td width="24%"><input name="officetel" type="text" ></td>
          <td width="10%" align="right">手机：</td>
          <td width="21%"><input type="text" name="mobile" maxlength="20" dataType="Mobile" msg="手机格式不正确!" require="true">
            <span class="STYLE1">*</span></td>
	      <td width="10%" align="right">家庭电话：</td>
	      <td width="23%"><input name="hometel" type="text"></td>
	  </tr>
	  <tr>
	    <td  align="right">Emai：</td>
	    <td><input type="text" name="email" ></td>
	    <td align="right">QQ：</td>
	    <td><input name="qq" type="text" id="qq" ></td>
	    <td align="right">MSN：</td>
	    <td><input type="text" name="msn"></td>
	    </tr>
	  <tr>
	    <td  align="right">送货地址：</td>
	    <td colspan="5"><input name="addr" type="text" size="40" ></td>
	    </tr>
	  </table>
</fieldset>
		
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td><div align="center">
                  <input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
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
