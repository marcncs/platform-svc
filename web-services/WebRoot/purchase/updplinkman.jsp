<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
 
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script type="text/javascript" src="../js/validator.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>
function ChkValue(){
	if ( !Validator.Validate(document.validateProvideLinkman,2) ){
		return false;
	}
	showloading();
	return true;
	
}
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改联系人</td>
        </tr>
      </table>
	  <form name="validateProvideLinkman" method="post" action="updPlinkmanAction.do" onSubmit="return ChkValue();">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right"><input name="id" type="hidden" value="${pl.id}">
	  	  <input name="pid" type="hidden" value="${pl.pid}">
	  	  姓名：</td>
          <td width="20%"><input name="name" type="text" id="name" value="${pl.name}" dataType="Require" msg="必须录入联系人姓名!">
            <font color="#FF0000">*</font> </td>
          <td width="13%" align="right">性别：</td>
          <td width="21%">${sexselect}</td>
	      <td  align="right">MSN： </td>
	      <td><input name="msn" type="text" id="msn" value="${pl.msn}"></td>
	  </tr>
	  <tr>
	    <td  align="right">生日：</td>
	    <td><input name="birthday" type="text" id="birthday" onFocus="selectDate(this);" value="${birthday}" readonly="readonly"></td>
	    <td align="right">部门：</td>
	    <td><input name="department" type="text" id="department" value="${pl.department}"></td>
	    <td align="right">职务：</td>
	    <td><input name="duty" type="text" id="duty" value="${pl.duty}"></td>
	    </tr>
	  <tr>
	    <td  align="right">办公电话：</td>
	    <td><input name="officetel" type="text" id="officetel" value="${pl.officetel}" dataType="PhoneOrMobile" msg="必须录入正确的办公电话!" require="false">
          </td>
	    <td align="right">是否主联系人：</td>
	    <td>${ismainselect}</td>
	    <td align="right">手机：</td>
	    <td><input name="mobile" type="text" id="mobile"  maxlength="11" value="${pl.mobile}" require="true" dataType="Mobile" msg="必须录入正确的手机!"><font color="#FF0000">*</font></td>
	    </tr>
	  <tr>
	    <td  align="right">家庭电话：</td>
	    <td><input name="hometel" type="text" id="hometel" value="${pl.hometel}" require="false" dataType="Phone" msg="必须录入正确的家庭电话!"></td>
	    <td align="right">QQ：</td>
	    <td><input name="qq" type="text" id="qq" value="${pl.qq}" require="false" dataType="QQ" msg="必须录入正确的QQ!"></td>
	    <td align="right">Email：</td>
	    <td><input name="email" type="text" id="email" value="${pl.email}" require="false" dataType="Email" msg="必须录入正确的Email!"></td>
	    </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr> 
            <td> <div align="center">
              <input type="submit" name="Submit" value="确定">
              &nbsp;&nbsp; 
              <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();">
            </div></td>
          </tr>
        
      </table>
	  </form>
    </td>
  </tr>
</table>
</body>
</html>
