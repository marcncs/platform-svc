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
              <td width="60" align="center"><c:choose>
                  <c:when test="${rf.isaudit==0}"><a href="javascript:Audit('${rf.id}');">复核</a></c:when>
                  <c:otherwise>&nbsp;</c:otherwise>
              </c:choose></td>
			   
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
	  	<td width="15%"  align="right">制单机构：</td>
          <td width="21%">${rf.makeorganidname}</td>
          <td width="10%" align="right">制单部门：</td>
          <td width="21%">${rf.makedeptidname}</td>
          <td width="10%" align="right">制单人：</td>
	      <td width="23%">${rf.makeidname}</td>
	  </tr>
	  <tr>
	    <td  align="right">制单日期：</td>
	    <td>${rf.makedate}</td>
	    <td align="right">是否复核：</td>
	    <td>${rf.isauditname}</td>
	    <td align="right">复核人：</td>
	    <td>${rf.auditidname}</td>
	  </tr>
	  <tr>
	    <td  align="right">复核日期：</td>
	    <td>${rf.auditdate}</td>
	    <td align="right">备注：</td>
	    <td colspan="3">${rf.memo}</td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="60" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>相关产品</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
		<td width="15%">产品编号</td>
		<td width="28%" > 产品名称</td>
		<td width="20%">规格型号</td>   
		<td width="22%">数量</td>  
		<td width="15%">序号</td>  
        </tr>
        <c:forEach var="p" items="${rplist}" > 
        <tr class="table-back">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>   
		  <td >${p.specmode}</td>  
		  <td >${p.quantity}</td>   
		  <td ><a href="javascript:toaddidcode(${p.id},'${rf.id}')"><img src="../images/CN/record.gif" width="19"  border="0" title="录入序号"></a></td>           
        </tr>
        </c:forEach> 
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
