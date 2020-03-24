<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="application/vnd.ms-excel;charset=UTF-8" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta Content-Disposition="attachment; filename=a.xls">   
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	   <table width="100%" border="0" cellpadding="0" cellspacing="1">
	  	<tr> 
          <td colspan="4" height="40" align="center" style="font-size:18px">会员已兑换积分明细</td>
        </tr>
		<tr class="table-back">
          <td width="31%"  align="center">机构：${organid}</td>
          <td width="29%" align="center">日期：${begindate}--${enddate}</td>
          <td width="2%" align="center">&nbsp;</td>
          <td width="38%" align="center">积分段：${beginprice}--${endprice}</td>          
        </tr>
	   </table>	
      <table width="100%" border="1" cellpadding="0" cellspacing="1">
        <tr style="background-color: #66CCFF">
           <td  width="13%" align="center" >相关单据</td>
		  <td width="15%" align="center" >机构</td>
          <td width="18%" align="center" >会员名称</td>
          <td width="14%" align="center" >手机</td>          
		  <td width="17%" align="center" >兑换积分</td>
		  <td width="23%" align="center" >制单日期</td>
        </tr>
		<c:set var="totalcount" value="0"/>
<c:forEach items="${hList}" var="d">
        <tr class="table-back">
           <td  align="center">${d.billno}</td>
          <td align="center">${d.organid}</td>
          <td align="center">${d.cname}</td>
          <td align="center">${d.mobile}</td>
		  <td align="right"><fmt:formatNumber value="${d.dealintegral}" pattern="0.00"/></td>
		  <td align="right">${d.strmakedate}</td>
        </tr>
</c:forEach> 
      </table>
	  
	  
	  
    </td>
  </tr>
</table>
</body>
</html>
