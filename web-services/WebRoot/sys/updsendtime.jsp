<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/validator.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改送货时间</td>
        </tr>
      </table>
	  <form name="updateform" method="post" action="../sys/updSendTimeAction.do" onSubmit="return Validator.Validate(this,2)">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%" height="20" align="right">编号：</td>
          <td width="21%"><input name="id" type="text" id="id" value="${wf.id}" size="10" readonly></td>
          <td width="13%" align="right">分类
	  	：</td>
          <td width="23%"><input name="irid" type="text" id="irid" value="${wf.irid}" readonly></td>
          <td width="9%" align="right">开始时间：</td>
	      <td width="25%"><input name="stime" type="text" id="stime" value="${wf.stime}" size="10" 
	      dataType="Integer" msg="开始必须录入数值类型!" maxlength="10" onKeyPress="KeyPress(this)">分钟<span class="style1">*</span></td>
	  </tr>
	  <tr>
	    <td height="20" align="right">结束时间：</td>
	    <td><input name="etime" type="text" id="etime" value="${wf.etime}" size="10" 
	    dataType="Integer" msg="结束时间必须录入数值类型!" maxlength="10" onKeyPress="KeyPress(this)">分钟<span class="style1">*</span></td>
	    <td align="right">积分比例：</td>
	    <td><input name="integralrate" type="text" id="integralrate" value="${wf.integralrate}" size="10"
	    dataType="Double" msg="积分比例必须录入数值类型!" maxlength="8"><span class="style1">*</span></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
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
