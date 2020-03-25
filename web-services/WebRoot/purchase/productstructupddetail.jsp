<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
</head>
<html:errors/>
<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772"> 修改产品类型</td>
  </tr>
</table>
<form name="validateProductStruct" method="post" action="updProductStructExecuteAction.do" onSubmit="return validateValidateProductStruct(this);">
<table width="100%"  border="0" cellpadding="0" cellspacing="1">

  <tr class="table-back">
    <td width="17%" align="right"> 编号 ：</td>
    <td width="29%"><input name="id" type="hidden" value="${psf.id}">
    ${psf.id}</td>
    <td width="19%" align="right"> 类别名称 ：</td>
    <td width="35%"><input name="sortname" type="text" value="${psf.sortname}"></td>
  </tr>
  <tr class="table-back">
    <td align="right"> 父类型 ：</td>
    <td><select name="parentid" id="parentid">
          <option value="0" selected>根</option>
          <logic:iterate id="ps" name="uls" > 
          <option value="${ps.id}" <c:if test="${ps.id==psf.parentsort}"><c:out value="selected"/></c:if>>${ps.sortname}</option>
          </logic:iterate> </select></td>
    <td align="right"> 是否可用 ：</td>
    <td>${psf.useflag}</td>
  </tr>
  <tr class="table-back">
    <td align="right"> 备注 ： </td>
    <td><textarea name="remark" cols="30" rows="5">${psf.remark}</textarea></td>
    <td align="right">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" class="table-back">
    <td colspan="4"><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
    <input type="button" name="cancel" value="取消" onClick="javascript:history.back();"></td>
  </tr>
 <html:javascript formName="validateProductStruct"/>
</table>
 </form>
</td>
  </tr>
</table>
</body>
</html>
