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
          <td colspan="4" height="40" align="center" style="font-size:18px">会员积分汇总</td>
        </tr>
		<tr class="table-back">
          <td width="31%"  align="center">所在机构：${makeorganid}</td>
          <td width="29%" align="center">日期：${begindate}--${enddate}</td>
          <td width="2%" align="center">&nbsp;</td>
          <td width="38%" align="center">积分段：${beginprice}--${endprice}</td>          
        </tr>
	   </table>	
      <table width="100%" border="1" cellpadding="0" cellspacing="1">
        <tr style="background-color: #66CCFF">
          <td  width="8%" align="center" >会员编号</td>
          <td width="13%" align="center" >会员名称</td>
          <td width="11%" align="center" >手机</td>
          <td width="19%" align="center" >所在机构</td>
          <td width="10%" align="center" >应得积分</td>
          <td width="7%" align="center" >已得积分</td>
		  <td width="10%" align="center" >已兑换积分</td>
		  <td width="9%" align="center" >积分调整</td>
		  <td width="13%" align="center" >结余积分</td>
        </tr>
		<c:set var="totalcount" value="0"/>
<c:forEach items="${str}" var="d">
        <tr class="table-back">
          <td  align="center">${d.cid}</td>
          <td align="center">${d.cname}</td>
          <td align="center">${d.mobile}</td>
          <td align="center">${d.makeorganidname}</td>
          <td align="right"><fmt:formatNumber value="${d.dealintegral}" pattern="0.00"/></td>
          <td align="right"><fmt:formatNumber value="${d.completeintegral}" pattern="0.00"/></td>
		  <td align="right"><fmt:formatNumber value="${d.duihuan}" pattern="0.00"/></td>
		  <td align="right"><fmt:formatNumber value="${d.tiaozheng}" pattern="0.00"/></td>
		  <td align="right"><fmt:formatNumber value="${d.jieyu}" pattern="0.00"/></td>
        </tr>
</c:forEach> 
      </table>
	  
	  
	  
    </td>
  </tr>
</table>
</body>
</html>
