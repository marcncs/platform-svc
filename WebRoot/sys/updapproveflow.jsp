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
<script type="text/javascript" src="../js/function.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
	function CheckInput(){
		var flowname =referform.flowname.value;
		if(flowname.trim()==null){
			alert("流程名不能为空!");
			return false;
		}
		return true;
	}
</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改流程</td>
        </tr>
      </table>
	  <form name="referform" method="post" action="../sys/updApproveFlowAction.do" onSubmit="return CheckInput();">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%" height="20" align="right"><input name="id" type="hidden" id="id" value="${aff.id}">
流程名：</td>
          <td width="21%"><input name="flowname" type="text" id="flowname" value="${aff.flowname}"></td>
          <td width="13%" align="right">备注：</td>
          <td width="23%"><input name="memo" type="text" id="memo" value="${aff.memo}"></td>
	      <td width="9%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
	  </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center"> 
            <td width="28%"> <input type="submit" name="Submit" value="确定">&nbsp;&nbsp; <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:history.back();"></td>

          </tr>
        
	  </table></form>
    </td>
  </tr>
</table>
</body>
</html>
