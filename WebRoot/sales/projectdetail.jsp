<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.*,java.text.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html>

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
              <td width="772"> 项目详情</td>
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
	  	<td width="11%"  align="right">项目种类：</td>
          <td width="23%">${pf.pcontentname}</td>
          <td width="11%" align="right">项目状态：</td>
          <td width="21%">${pf.pstatusname}</td>
	      <td width="9%" align="right">项目金额：</td>
	      <td width="25%">${pf.amount}</td>
	  </tr>
	  <tr>
	    <td  align="right">项目开始时间：</td>
	    <td>${pf.pbegin}</td>
	    <td align="right">项目结束时间：</td>
	    <td>${pf.pend}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">客户名称：</td>
	    <td colspan="5">${pf.cidname}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${pf.memo}</td>
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
	  	<td width="11%"  align="right">制单人：</td>
          <td width="23%">${pf.makeidname}</td>
          <td width="9%" align="right">制单日期：</td>
          <td width="23%">${pf.makedate}</td>
	      <td width="9%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
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
