<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function checkForm(){
	var organid = searchform.MakeOrganID.value;
	var makeid = searchform.MakeID.value;
	
	if ( organid == ""){
		alert("请选择制单机构！");
		return false;
	}
	if ( makeid == ""){
		alert("请选择制单人！");
		return false;
	}
	
	return true;
}
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">零售对帐</td>
        </tr>
      </table>
       <form name="searchform" method="post" action="toCheckPeddleOrderAction.do" onSubmit="return checkForm();">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr class="table-back">
            <td width="11%"  align="right">制单日期：</td>
            <td width="22%"><input name="BeginDate" type="text" id="BeginDate" onFocus="selectDate(this);"  size="10" value="${currentdate}">
-
  <input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);" size="10" value="${currentdate}"></td>
            <td width="17%" align="right">制单机构：</td>
            <td width="23%"><select name="MakeOrganID" id="MakeOrganID">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="ols">
                <option value="${o.id}" ${o.id==equiporganid?"selected":""}>
                <c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${o.organname}</option>
              </logic:iterate>
            </select></td>
			<td width="8%" align="right">制单人：</td>
			<td width="19%"><select name="MakeID" id="MakeID">
              <option value="">请选择...</option>
              <logic:iterate id="u" name="uls">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select></td>
          </tr>
          <tr class="table-back">
            <td  align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td><input type="submit" name="Submit" value="查询" ></td>
  		<td></td>
			<td></td>
          </tr>
        
      </table>
     </form>
    </td>
  </tr>
</table>
</body>
</html>
