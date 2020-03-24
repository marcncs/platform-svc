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
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<script language="javascript">
	function ChkValue(){
		var contactdate = document.validateContactLog.contactdate;
		var nextcontact = document.validateContactLog.nextcontact;
		
		if(contactdate.value==""){
		alert("联系日期不能为空！");
		return false;
		}
		if(nextcontact.value==""){
		alert("下次联系日期不能为空！");
		return false;
		}
	
		return true;
	}
	
	function SelectContact(){
		document.validateContactLog.contactcontent.value=document.validateContactLog.select.value;
	}
	
	
	function SelectDitch(){
	showModalDialog("toSelectDitchAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.validateContactLog.did.value=getCookie("did");
	document.validateContactLog.dname.value=getCookie("dname");
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
              <td width="772"> 添加联系记录</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<form name="validateContactLog" method="post" action="addContactDitchAction.do" onSubmit="return ChkValue();">
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  
	    <tr >
	      <td  align="right"><input name="did" type="hidden" id="did" value="${did}">
	        渠道名称：</td>
	      <td><input name="dname" type="text" id="dname" value="${dname}">
            <a href="javascript:SelectDitch();"><img src="../images/CN/find.gif" width="18" height="18" border="0" title="查找"></a></td>
	      <td align="right" >&nbsp;</td>
	      <td >&nbsp;</td>
	      </tr>
	    <tr >
          <td width="10%"  align="right">联系日期：</td>
	      <td width="37%"><input name="contactdate" type="text" value="<%=sdf.format(d)%>" onFocus="javascript:selectDate(this)"></td>
	      <td width="11%" align="right" >联系方式：</td>
	      <td width="42%" >${contactmodeselect}</td>
	      </tr>
	    <tr >
          <td  align="right">联系性质：</td>
	      <td>${contactpropertyselect}</td>
	      <td align="right">联系人：</td>
	      <td><input name="linkman" type="text" id="linkman">
	        <a href="javascript:SelectLinkman();"></a></td>
	      </tr>
	    <tr >
          <td  align="right" valign="top">联系内容：</td>
	      <td><select name="select" onChange="SelectContact(this)">
		  	<option value="">选择</option>
		  	<logic:iterate id="c" name="als">
            <option value="${c.tagsubvalue}">${c.tagsubvalue}</option>
          	</logic:iterate>
	        </select><br>
	      <textarea name="contactcontent" cols="60" rows="5"></textarea></td>
	      <td align="right" valign="top">客户反馈：</td>
	      <td><textarea name="feedback" cols="60" rows="6" id="feedback"></textarea></td>
	      </tr>
	    <tr >
          <td  align="right">下次联系时间：</td>
	      <td><input name="nextcontact" type="text" id="nextcontact" onFocus="javascript:selectDate(this)" value="<%=sdf.format(d)%>"></td>
	      <td align="right">下次联系目标：</td>
	      <td><input name="nextgoal" type="text" id="nextgoal" size="60"></td>
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
