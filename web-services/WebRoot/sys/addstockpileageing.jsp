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
          <td width="772"> 库龄参数设置</td>
        </tr>
      </table>
	  <form name="updateform" method="post" action="../sys/addStockPileAgeingAction.do" onSubmit="return Validator.Validate(this,2)">
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
			开始值：
		</td>
          <td width="21%"><input name="tagMinValue" type="text" id="tagMinValue" dataType="Require" msg="必须输入开始值">
            <font color="#FF0000">*</font></td>
            
            <td width="9%" height="20" align="right">
			结束值：
		</td>
          <td width="21%"><input name="tagMaxValue" type="text" id="tagMaxValue" dataType="Require" msg="必须输入结束值">
            <font color="#FF0000">*</font></td>
            
          <td width="6%" align="right">颜色：</td>
          <td ><input type="text" name="tagColor" id="tagColor" dataType="Require" msg="必须输入颜色" /> <font color="#FF0000">格式如：#FFFFFF</font></td>
          
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
