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
          <td width="772"> 修改编号规则</td>
        </tr>
      </table>
	  <form name="updateform" method="post" action="../sys/updMakeConfAction.do" onSubmit="return Validator.Validate(this,2)">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%" height="20" align="right">表名：</td>
          <td width="21%"><input name="tablename" type="text" id="tablename" value="${mc.tablename}" size="10" readonly></td>
          <td width="10%" align="right">表中文名：</td>
          <td width="21%"><input name="chname" type="text" id="chname" value="${mc.chname}" readonly></td>
          <td width="14%" align="right">是否自动编号：</td>
	      <td width="24%"><windrp:select key="YesOrNo" name="runmode" p="n|f" value="${mc.runmode}"/></td>
	  </tr>
	  <tr>
	    <td height="20" align="right">前缀：</td>
	    <td><input name="profix" type="text" id="profix" value="${mc.profix}" size="10" maxlength="2"></td>
	    <td align="right">长度：</td>
	    <td><input name="extent" type="text" id="extent" value="${mc.extent}" size="10" dataType="Integer" msg="长度必须录入数值类型!" maxlength="7"><span class="style1">*</span></td>
	    <td align="right">当前值：</td>
	    <td><input name="currentvalue" type="text" id="currentvalue" value="${mc.currentvalue}" size="10" dataType="Integer" msg="当前值必须录入数值类型!" maxlength="10"><span class="style1">*</span></td>
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
