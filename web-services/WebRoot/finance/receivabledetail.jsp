<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="1034"> 应收款结算详情 </td>
    <td width="199" align="right">&nbsp;</td>
  </tr>
</table>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="12%"  align="right">应收款结算金额：</td>
          <td width="19%"><windrp:format value='${rf.receivablesum}'/></td>
          <td width="10%" align="right">单据号：</td>
          <td width="21%">${rf.billno}</td>
          <td width="8%" align="right">结算方式：</td>
          <td width="30%"><windrp:getname key='paymentmode' value='${rf.paymentmode}' p='d'/></td>
	  </tr>
	   <tr>
	  	<td width="12%"  align="right">已收款金额：</td>
          <td width="19%"><windrp:format value='${rf.alreadysum}'/></td>
          <td width="10%" align="right">描述：</td>
          <td colspan="3">${rf.receivabledescribe}</td>
        </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>状态信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	    <td  align="right">制单机构：</td>
	    <td><windrp:getname key='organ' value='${rf.makeorganid}' p='d'/></td>
	    <td align="right">制单部门：</td>
	    <td><windrp:getname key='dept' value='${rf.makedeptid}' p='d'/></td>
	    <td align="right">制单人：</td>
	    <td><windrp:getname key='users' value='${rf.makeid}' p='d'/> </td>
	    </tr>
	  <tr>
	  	<td width="12%"  align="right">制单日期：</td>
          <td width="19%">${rf.makedate}</td>
          <td width="10%" align="right">&nbsp;</td>
          <td width="21%">&nbsp;</td>
	      <td width="8%" align="right">&nbsp;</td>
	      <td width="30%">&nbsp;</td>
	  </tr>
	  </table>
	</fieldset>

</td>
  </tr>
</table>
</body>
</html>
