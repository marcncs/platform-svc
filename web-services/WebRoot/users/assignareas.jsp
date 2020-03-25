<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
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

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td> 
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
                <td width="772"> 分配区域</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
     <form method="post" action="assignAreasAction.do">
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
             
			  <logic:iterate id="ca" name="list">
			  <c:if test="${ca.parentid==0}">
                <tr class="table-back"> 
                  <td width="12%"  align="right">
                    <input type="checkbox" name="checkbox" value="${ca.id}" 
                    <logic:iterate id="ua" name="ulist"><c:if test="${ca.id==ua.areaid}">checked</c:if></logic:iterate>>
                  </td>
                  <td width="33%">${ca.areaname}</td>
                  <td width="200" align="right">&nbsp;</td>
                  <td width="200">&nbsp;</td>
                </tr>
				</c:if>
				
				<logic:iterate id="cal" name="list">
				<c:if test="${cal.parentid==ca.id&&cal.rank==1}">
                <tr class="table-back">
                  <td  align="right"> </td>
                  <td><input type="checkbox" name="checkbox" value="${cal.id}" <logic:iterate id="ua" name="ulist"><c:if test="${cal.id==ua.areaid}">checked</c:if></logic:iterate>>${cal.areaname}</td>
                  <td width="200" align="right">&nbsp;</td>
                  <td width="200">&nbsp;</td>
                </tr>
				
				
				<logic:iterate id="call" name="list">
				<c:if test="${call.parentid==cal.id&&call.rank==2}">
                <tr class="table-back">
                  <td  align="right"></td>
                  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="checkbox" value="${call.id}" <logic:iterate id="ua" name="ulist"><c:if test="${call.id==ua.areaid}">checked</c:if></logic:iterate>>${call.areaname}</td>
                  <td width="200" align="right"></td>
                  <td width="200">&nbsp;</td>
                </tr>
				</c:if>
				
				</logic:iterate>
				</c:if>
				</logic:iterate>
				</logic:iterate>
               
                <tr class="table-back"> 
                  <td  align="right"><input name="uid" type="hidden" id="uid" value="${uid}"></td>
                  <td><input type="submit" name="Submit" value="确定" onClick="javascript:history.back()">
                    <input type="reset" name="Submit2" value="重设"> </td>
                  <td width="200">&nbsp;</td>
                  <td width="200">&nbsp;</td>
                </tr>
             
            </table>
             </form>
	</td>
  </tr>
</table>	
    </td>
  </tr>
</table>

</body>
</html>
