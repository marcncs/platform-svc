<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增联系人</td>
        </tr>
      </table>
	  <form name="validateProvideLinkman" method="post" action="../purchase/addProvideLinkmanAction.do" >
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right"><input name="pid" type="hidden" value="${pid}">
	  	  姓名：</td>
          <td width="21%"><input name="name" type="text" id="name">
            <font color="#FF0000">*</font> </td>
          <td width="11%" align="right">性别：</td>
          <td width="22%">${sexselect}</td>
	      <td width="11%" align="right">婚姻状况：</td>
	      <td width="26%">${wedlockselect}</td>
	  </tr>
	  <tr>
	    <td  align="right">生日：</td>
	    <td><input name="birthday" type="text" id="birthday" onFocus="selectDate(this);"></td>
	    <td align="right">部门：</td>
	    <td><input name="department" type="text" id="department"></td>
	    <td align="right">职务： </td>
	    <td><input name="duty" type="text" id="duty"></td>
	    </tr>
	  <tr>
	    <td  align="right">办公电话：</td>
	    <td><input name="officetel" type="text" id="officetel"></td>
	    <td align="right">传真：</td>
	    <td><input name="fax" type="text" id="fax"></td>
	    <td align="right">手机：</td>
	    <td><input name="mobile" type="text" id="mobile"  maxlength="11"></td>
	    </tr>
	  <tr>
	    <td  align="right">家庭电话：</td>
	    <td><input name="hometel" type="text" id="hometel"></td>
	    <td align="right">QQ：</td>
	    <td><input name="qq" type="text" id="qq"></td>
	    <td align="right">Email：</td>
	    <td><input name="email" type="text" id="email" size="35"></td>
	    </tr>
	  <tr>
	    <td  align="right">MSN：</td>
	    <td><input name="msn" type="text" id="msn" size="35"></td>
	    <td align="right">地址：</td>
	    <td><input name="addr" type="text" id="addr" size="35"></td>
	    <td align="right">是否主联系人：</td>
	    <td>${ismainselect}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5"><input name="memo" type="text" id="memo" size="80"></td>
	    </tr>
	  </table>
	  </fieldset>
	
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr> 
            <td width="29%" align="center"> <input type="submit" name="Submit" value="确定">&nbsp;&nbsp; <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:history.back();"></td>
          </tr>
      </table>
	  </form>
    </td>
  </tr>
</table>
</body>
</html>
