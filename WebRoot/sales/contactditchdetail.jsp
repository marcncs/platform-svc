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
              <td width="772"> 联系记录详情</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">联系日期：</td>
          <td width="25%">${clf.contactdate}</td>
          <td width="9%" align="right">联系方式：</td>
          <td width="23%">${clf.contactmodename}</td>
	      <td width="9%" align="right">联系性质：</td>
	      <td width="25%">${clf.contactpropertyname}</td>
	  </tr>
	  <tr>
	    <td  align="right">渠道名称：</td>
	    <td>${clf.didname}</td>
	    <td align="right">联系人：</td>
	    <td>${clf.linkman}</td>
	    <td align="right">下次联系时间：</td>
	    <td>${clf.nextcontact}</td>
	    </tr>
	  <tr>
	    <td  align="right">下次联系目标：</td>
	    <td colspan="5">${clf.nextgoal}</td>
	    </tr>
	  <tr>
	    <td  align="right">联系内容：</td>
	    <td colspan="5">${clf.contactcontent}</td>
	    </tr>
	  <tr>
	    <td  align="right">客户反馈：</td>
	    <td colspan="5">${clf.feedback}</td>
	    </tr>
	  </table>
	</fieldset>
		
		</td>
      </tr>
    </table></td>
  </tr>
</table>

</body>
</html>
