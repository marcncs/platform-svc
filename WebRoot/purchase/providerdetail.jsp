<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>


<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 供应商详情</td>
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
	  	<td width="11%"  align="right">供应商编号：</td>
          <td width="21%">${pf.pid}</td>
          <td width="15%" align="right">供应商名：</td>
          <td colspan="3">${pf.pname}</td>
        </tr>
	  <tr>
	    <td  align="right">行业：</td>
	    <td><windrp:getname key='Vocation' value='${pf.vocation}' p='d'/></td>
	    <td align="right">供应商类型： </td>
	    <td width="19%"><windrp:getname key='Genre' value='${pf.genre}' p='d'/></td>
	    <td width="9%" align="right">ABC分类：</td>
	    <td width="25%"><windrp:getname key='AbcSort' value='${pf.abcsort}' p='f'/></td>
	    </tr>
	  <tr>
	    <td  align="right">法人代表：</td>
	    <td>${pf.corporation}</td>
	    <td align="right">税号：</td>
	    <td>${pf.taxcode}</td>
	    <td align="right">账期：</td>
	    <td>${pf.prompt}天</td>
	    </tr>
	  <tr>
	    <td  align="right">税率：</td>
	    <td>${pf.taxrate}</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${pf.remark}</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">电话：</td>
          <td width="21%">${pf.tel}</td>
          <td width="13%" align="right">传真：</td>
          <td width="23%">${pf.fax}</td>
	      <td width="9%" align="right">手机：</td>
	      <td width="25%">${pf.mobile}</td>
	  </tr>
	  <tr>
	    <td  align="right">Email：</td>
	    <td>${pf.email}</td>
	    <td align="right">邮编：</td>
	    <td>${pf.postcode}</td>
	    <td align="right">公司主页：</td>
	    <td>${pf.homepage}</td>
	    </tr>
	  <tr>
	    <td  align="right">区域：</td>
	    <td colspan="2"><windrp:getname key='countryarea' value='${pf.province}' p='d'/>-<windrp:getname key='countryarea' value='${pf.city}' p='d'/>-<windrp:getname key='countryarea' value='${pf.areas}' p='d'/></td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">详细地址：</td>
	    <td colspan="5">${pf.addr}</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">银行帐号：</td>
          <td width="21%">${pf.bankaccount}</td>
          <td width="13%" align="right">开户银行：</td>
          <td width="23%">${pf.bankname}</td>
	      <td width="9%" align="right">付款条件：</td>
	      <td width="25%">${pf.paycondition}</td>
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
	  	<td width="9%"  align="right">登记人：</td>
          <td width="21%"><windrp:getname key='users' value='${pf.makeid}' p='d'/> </td>
          <td width="13%" align="right">登记日期：</td>
          <td width="23%">${pf.makedate}</td>
	      <td width="9%" align="right">变更人：</td>
	      <td width="25%"><windrp:getname key='users' value='${pf.updateid}' p='d'/></td>
	  </tr>
	  <tr>
	    <td  align="right">变更日期：</td>
	    <td>${pf.modifydate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	  
    </td>
  </tr>
</table>
</body>
</html>
