<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td> 
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 用户详细信息</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0" >
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">登录名：</td>
          <td width="21%">${uf.loginname}</td>
          <td width="13%" align="right">真实姓名：</td>
          <td width="23%">${uf.realname}</td>
	      <td width="9%" align="right">英文名：</td>
	      <td width="25%">${uf.nameen}</td>
	  </tr>
	  <!-- 
	  <tr>
	    <td  align="right">性别：</td>
	    <td><windrp:getname key='Sex' value='${uf.sex}' p='f'/></td>
	    <td align="right">生日：</td>
	    <td><windrp:dateformat value='${uf.birthday}' p='yyyy-MM-dd'/>
</td>
	    <td align="right">身份证号：</td>
	    <td>${uf.idcard}</td>
	    </tr>
	     -->
	  <tr>
	    <td  align="right">所属机构：</td>
	    <td><windrp:getname key='organ' value='${uf.makeorganid}' p='d'/></td>
	    <td align="right"></td>
	    <td></td>
	    <td align="right">有效日期：</td>
	    <td><c:if test="${uf.validate!=null}"><windrp:dateformat value='${uf.validate}' p='yyyy-MM-dd'/></c:if>
	    <c:if test="${uf.validate==null}">永久</c:if>
</td>
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
	  	<td width="9%"  align="right">办公电话：</td>
          <td width="21%">${uf.officetel}</td>
          <td width="13%" align="right">手机号：</td>
          <td width="23%">${uf.mobile}</td>
          <td width="9%" align="right">&nbsp</td>
	      <td width="25%">&nbsp</td>
          <!-- 
	      <td width="9%" align="right">家庭电话：</td>
	      <td width="25%">${uf.hometel}</td>
	       -->
	  </tr>
	  <tr>
	    <td  align="right">Email：</td>
	    <td>${uf.email}</td>
	   <%--  <td align="right">QQ：</td>
	    <td>${uf.qq}</td> 
	     <td width="9%" align="right">&nbsp</td>
	      <td width="25%">&nbsp</td>
	      
	    <td align="right">Msn：</td>
	    <td>${uf.msn} </td>
	     --%>
	    </tr>
	  <%-- <tr>
	    <td  align="right">区域：</td>
	    <td colspan="5"><windrp:getname key='countryarea' value='${uf.province}' p='d'/>-<windrp:getname key='countryarea' value='${uf.city}' p='d'/>-<windrp:getname key='countryarea' value='${uf.areas}' p='d'/></td>
	    </tr>
	  <tr>
	    <td  align="right">地址：</td>
	    <td colspan="5">${uf.addr}</td>
	    </tr>
	    
	    <tr>
			    <td  align="right">用户性质：</td>
			    <td colspan="5">${UserName}</td>
	    </tr>
	     --%>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>其它信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">创建日期：</td>
          <td width="19%">${uf.createdate}</td>
          <td width="15%" align="right">最后登录日期：</td>
          <td width="23%">${uf.lastlogin}</td>
	      <td width="9%" align="right">登录次数：</td>
	      <td width="25%">${uf.logintimes} </td>
	  </tr>
	  <tr>
	    <td  align="right">状态：</td>
	    <td><windrp:getname key='UseSign' value='${uf.status}' p='f'/></td>
		<%--	    
	    <td align="right">是否可用呼叫中心：</td>
	    <td><windrp:getname key='YesOrNo' value='${uf.iscall}' p='f'/></td>--%>
	    <td align="right">用户类型：</td>
	    <td colspan="3">${ucategory}</td>
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
