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
		var hapbegin = document.referform.hapbegin;
		var hapend = document.referform.hapend;
		
		if(hapbegin.value==""){
		alert("零售机会开始日期不能为空！");
		return false;
		}
		if(hapend.value==""){
		alert("零售机会结束日期不能为空！");
		return false;
		}
	
		return true;
	}
	
	function SelectCustomer(){
	var c=showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.referform.cid.value=c.cid;
	document.referform.cname.value=c.cname;
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
              <td width="772"> 添加项目</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<form name="referform" method="post" action="addProjectAction.do" onSubmit="return ChkValue();">
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  
	    <tr >
          <td  align="right"><input name="cid" type="hidden" id="cid" >
            客户名称：</td>
	      <td><input name="cname" type="text" id="cname" >
            <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
	      <td align="right" >&nbsp;</td>
	      <td >&nbsp;</td>
	      <td align="right" >&nbsp;</td>
	      <td >&nbsp;</td>
	      </tr>
	    <tr >
          <td width="10%"  align="right">项目种类：</td>
	      <td width="27%">${pcontentselect}</td>
	      <td width="10%" align="right" >项目状态：</td>
	      <td width="17%" >${pstatusselect}</td>
	      <td width="11%" align="right" >项目金额：</td>
	      <td width="25%" ><input name="amount" type="text" id="amount" value="0"></td>
	    </tr>
	    <tr >
          <td  align="right">项目开始时间：</td>
	      <td><input name="pbegin" type="text" id="pbegin" onFocus="javascript:selectDate(this)" value="<%=sdf.format(d)%>"></td>
	      <td align="right">项目结束时间：</td>
	      <td><input name="pend" type="text" id="pend" onFocus="javascript:selectDate(this)" value="<%=sdf.format(d)%>"></td>
	      <td align="right">&nbsp;</td>
	      <td>&nbsp;</td>
	    </tr>
	    <tr >
          <td  align="right">备注：</td>
	      <td colspan="5"><textarea name="memo" cols="150" rows="5" id="memo"></textarea></td>
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
