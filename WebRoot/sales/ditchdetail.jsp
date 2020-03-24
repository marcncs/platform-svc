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
        <td width="772"> 渠道详情</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>

	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本资料</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	    <tr >
          <td width="9%"  align="right">            渠道名称：</td>
	      <td width="26%" >${d.dname}</td>
	      <td width="9%" align="right">渠道等级：</td>
	      <td width="22%">${d.ditchrankname}</td>
	      <td width="9%" align="right">信誉度：</td>
	      <td width="25%">${d.prestigename}</td>
	    </tr>
	    <tr >
          <td  align="right">规模：</td>
	      <td >${d.scale}</td>
	      <td align="right">所属行业：</td>
	      <td>${d.vocationname}</td>
	      <td align="right">省份：</td>
	      <td>${d.provincename}</td>
	    </tr>
	    <tr >
          <td  align="right">城市：</td>
	      <td >${d.cityname}</td>
	      <td align="right">地区：</td>
	      <td>${d.areasname}</td>
	      <td align="right">邮编：</td>
	      <td>${d.postcode}</td>
	    </tr>
	    <tr >
          <td  align="right">详细地址：</td>
	      <td  colspan="5">${d.detailaddr}</td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>联系资料</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">电话(1)：</td>
          <td width="26%">${d.telone}</td>
          <td width="9%" align="right">电话(2)：</td>
          <td width="22%">${d.teltwo}</td>
          <td width="9%" align="right">传真：</td>
	      <td width="25%">${d.fax}</td>
	  </tr>
	  <tr>
	    <td  align="right">网址：</td>
	    <td>${d.homepage}</td>
	    <td align="right">邮箱：</td>
	    <td>${d.email}</td>
	    <td align="right">备注：</td>
	    <td>${d.memo}</td>
	  </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>账务信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right"> 开户银行： </td>
          <td width="26%">${d.bankname}</td>
          <td width="9%" align="right">户名：</td>
          <td width="22%">${d.doorname}</td>
          <td width="9%" align="right">账号：</td>
	      <td width="25%">${d.bankaccount}</td>
	  </tr>
	  <tr>
	    <td  align="right">税号：</td>
	    <td>${d.taxcode}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
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
	  	<td width="9%"  align="right"> 所属用户： </td>
          <td width="26%">${d.useridname}</td>
          <td width="9%" align="right">录入人员：</td>
          <td width="22%">${d.makeidname}</td>
          <td width="9%" align="right">录入日期：</td>
	      <td width="25%">${d.makedate}</td>
	  </tr>
	  </table>
</fieldset>
	
	</td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
