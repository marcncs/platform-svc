<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 联系人详情</td>
        </tr>
      </table>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">姓名：</td>
          <td width="21%">${pl.name}</td>
          <td width="13%" align="right">性别：</td>
          <td width="20%">${sex}</td>
	      <td width="12%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">生日：</td>
	    <td>${pl.birthday}</td>
	    <td align="right">部门：</td>
	    <td>${pl.department}</td>
	    <td align="right">职务：</td>
	    <td>${pl.duty}</td>
	    </tr>
	  <tr>
	    <td  align="right">办公电话：</td>
	    <td>${pl.officetel}</td>
	    <td align="right">传真：</td>
	    <td>${pl.fax}</td>
	    <td align="right">手机：</td>
	    <td>${pl.mobile}</td>
	    </tr>
	  <tr>
	    <td  align="right">家庭电话：</td>
	    <td>${pl.hometel}</td>
	    <td align="right">QQ：</td>
	    <td>${pl.qq}</td>
	    <td align="right">Email：</td>
	    <td>${pl.email}</td>
	    </tr>
	  <tr>
	    <td  align="right">MSN： </td>
	    <td>${pl.msn}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">是否主联系人：</td>
	    <td>${ismain}</td>
	    </tr>
	  </table>
	</fieldset>
	  
    </td>
  </tr>
</table>
</body>
</html>
