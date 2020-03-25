<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>


<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../js/validator.js"></script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改经销商级别</td>
        </tr>
      </table>
	  <form name="updateform" method="post" action="../sys/updOrganGradeAction.do" onSubmit="return Validator.Validate(this,2)">
	  <input type="hidden" name="id" value="${wf.id}">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%" height="20" align="right">
级别名称：</td>
          <td width="21%"><input name="gradename" type="text" id="gradename" dataType="Require" msg="必须录入级别名称名称" value="${wf.gradename}">
            <font color="#FF0000">*</font></td>
          <td width="13%" align="right">享受价格：</td>
          <td width="23%"><windrp:select key="OrganPricePolicy" name="policyid" value="${wf.policyid}" p="n|d"/></td>
          <!--<td width="9%" align="right">积分比例：</td>
	      <td width="25%"><input name="integralrate" type="text" id="integralrate" value="0"></td>-->
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
