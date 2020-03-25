<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="com.winsafe.hbm.util.Internation"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script language="javascript">
function SelectLoanObject(){
		showModalDialog("toSelectLoanObjectAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		document.addform.uid.value=getCookie("uid");
		document.addform.uidname.value=getCookie("uname");
	}
	
	function Audit(lid){
			window.open("../finance/auditLoanAction.do?LID="+lid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(lid){
			window.open("../finance/cancelAuditLoanAction.do?LID="+lid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
</script>

</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="15"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="942"> 个人借款详情</td>       
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
	  	<td width="11%"  align="right">借款对象：</td>
          <td width="24%">${lf.uidname}</td>
          <td width="10%" align="right">借款日期：</td>
          <td width="22%">${lf.loandate}</td>
          <td width="10%" align="right">借款金额：</td>
	      <td width="23%">${lf.loansum}</td>
	  </tr>
	  <tr>
	    <td  align="right">借款用途：</td>
	    <td colspan="5">${lf.purpose}</td>
	  </tr>
	  <tr>
	    <td  align="right">单位意见：</td>
	    <td colspan="5">${lf.companyidea}</td>
	  </tr>
	  <tr>
	    <td  align="right">核算中心意见：</td>
	    <td colspan="5">${lf.hubidea}</td>
	  </tr>
	  <tr>
	    <td  align="right">制单人：</td>
	    <td>${lf.makeidname}</td>
	    <td align="right">制单日期：</td>
	    <td>${lf.makedate}</td>
	    <td align="right">是否复核：</td>
	    <td>${lf.isauditname}</td>
	  </tr>
	  <tr>
	    <td  align="right">复核人：</td>
	    <td>${lf.auditidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${lf.auditdate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>审核信息</td>
        </tr>
      </table>
	  </legend>
<form name="addform" method="post" action="approveLoanAction.do">
      <table width="100%" height="200"  border="0" cellpadding="0" cellspacing="1">
        
			<input name="logid" type="hidden" id="logid" value="${logid}">
          <tr class="table-back"> 
            <td  align="right">
            审核状态：</td>
            <td>${approvestatus}</td>
          </tr>
          <tr class="table-back">
            <td  align="right">
            动作：</td>
            <td>${stractid}</td>
          </tr>
          <tr class="table-back"> 
            <td width="13%"  align="right" valign="top"> 审核内容：</td>
            <td width="87%"><textarea name="approvecontent" cols="80" rows="10" id="approvecontent"></textarea></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right" valign="top">&nbsp;</td>
            <td><input type="submit" name="Submit2" value="确定"> &nbsp;&nbsp; 
              <input type="button" name="Submit2" value="取消" onClick="javascript:history.back();"></td>
          </tr>
        
      </table>
      </form>
      </fieldset>
	
	
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center">
          <td>&nbsp;</td>
        </tr>

    </table>
	 </td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
