<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.*,java.text.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html>
<script type="text/javascript" src="../js/pub_Calendar.js"></script>
<%
        java.util.Date d = null;
        Calendar cd=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        d=cd.getTime();
%>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script language="javascript">
	function ChkValue(){
		var dealdate = document.referform.dealdate;
		
		if(dealdate.value==""){
		alert("处理日期不能为空！");
		return false;
		}
	
		return true;
	}
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
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
              <td width="772"> 处理抱怨投诉</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<form name="referform" method="post" action="dealSuitAction.do" onSubmit="return ChkValue();">
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	    <tr >
          <td width="18%"  align="right"><input name="id" type="hidden" id="id" value="${sid}">
            处理方式：</td>
	      <td width="22%">${dealwayselect}</td>
	      <td width="14%" align="right">处理日期：</td>
	      <td width="46%"><input name="dealdate" type="text" id="dealdate" onFocus="javascript:selectDate(this)" value="<%=sdf.format(d)%>"></td>
	      </tr>
	    <tr >
	      <td  align="right">处理内容：</td>
	      <td colspan="3"><textarea name="dealcontent" cols="70" rows="3" id="dealcontent"></textarea></td>
	      </tr>
	    <tr >
          <td  align="right">处理结果：</td>
	      <td colspan="3"><textarea name="dealfinal" cols="70" rows="5" id="dealfinal"></textarea></td>
	      </tr>
	  </table>
</fieldset>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
    </table></td>
  </tr>
</table>

</body>
</html>
