<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="com.winsafe.hbm.util.Internation"%>
<html>

<script src="../js/prototype.js"></script>
<script language="JavaScript">
	function toaddidcode(pidid, piid){
			var batch='';
			window.open("../assistant/toAddIdcodeResetIdcodeAction.do?pidid="+pidid+"&batch="+batch+"&piid="+piid,"newwindow","height=400,width=600,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	function Audit(piid){
			window.open("../assistant/auditIdcodeResetAction.do?PIID="+piid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}

</script>


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
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 详情</td>
		<td width="308" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
            <tr>             
              <td width="60" align="center">
                  </td>
			   
            </tr>
          </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="validateCustomer" method="post" action="../sales/addMemberAction.do" >
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="15%"  align="right">序号：</td>
          <td width="21%">${idf.idcode}</td>
          <td width="10%" align="right">产品编号：</td>
          <td width="21%">${idf.productid}</td>
          <td width="10%" align="right">产品名称：</td>
	      <td width="23%">${idf.productname}</td>
	  </tr>
	  <tr>
	    <td  align="right">规格：</td>
	    <td>${idf.specmode}</td>
	    <td align="right">单据类型：</td>
	    <td>${idf.idbilltypename}</td>
	    <td align="right">相关单据：</td>
	    <td>${idf.billid}</td>
	  </tr>	  
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>其它信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="15%"  align="right">制单机构：</td>
          <td width="21%">${idf.makeorganidname}</td>
          <td width="10%" align="right">制单人：</td>
          <td width="21%">${idf.makeuser}</td>
          <td width="10%" align="right">制单日期：</td>
	      <td width="23%">${idf.makedate}</td>
	  </tr>
	  <tr>
	    <td  align="right">配送机构：</td>
	    <td>${idf.equiporganidname}</td>
	    <td align="right">仓库：</td>
	    <td>${idf.warehouseidname}</td>
	    <td align="right">客户名称：</td>
	    <td>${idf.cname}</td>
	  </tr>
	  <tr>
	    <td  align="right">客户手机：</td>
	    <td>${idf.cmobile}</td>
	    <td align="right">&nbsp;</td>
	    <td colspan="3">&nbsp;</td>
	    </tr>
	  </table>
</fieldset>

    </form>
	
	</td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
