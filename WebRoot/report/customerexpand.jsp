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
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/prototype.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
function ShowReport(p){
var b=$F("BeginDate");
var e=$F("EndDate");
document.all.chartmsg.src="customerReportAction.do?rp="+p+"&b="+b+"&e="+e;
}
</script>
</head>

<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">报表分析>>会员>>客户发展趋势报表</td>
        </tr>
      </table>
        <form name="search" method="post" action="toCustomerReportAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
      

          <tr class="table-back">
            <td width="14%"  align="right">时间段：</td>
            <td width="34%" ><select name="select">
              <option value="0" selected>3个月内</option>
              <option value="1">6个月内</option>
              <option value="2">9个月内</option>
              <option value="3">12个月内</option>
            </select>
            </td>
            <td width="11%"  align="right">所属用户：</td>
            <td width="41%" ><select name="UserID">
			<option value="">所有用户</option>
			<logic:iterate id="u" name="uls">
			<option value="${u.userid}" <c:if test="${u.userid==uid}"><c:out value="selected"/></c:if>>${u.realname}</option>
			</logic:iterate> 
            </select>
            <input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
    </td>
  </tr>
</table>
<IFRAME id="chartmsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../report/customerExpandAction.do" frameBorder="0" scrolling="no" onload="setIframeHeight(this)"></IFRAME>
</body>
</html>
