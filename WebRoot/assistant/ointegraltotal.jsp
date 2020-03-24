<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectSingleProduct(){
	var p=showModalDialog("../warehouse/toSelectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	//alert(getCookie("id"));
	document.searchform.productid.value=p.id;
	document.searchform.productname.value=p.productname;
	}
function excput(){
	searchform.action="../assistant/excPutOIntegralTotalAction.do";
	searchform.submit();
	searchform.action="../assistant/oIntegralTotalAction.do";
}
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">机构积分汇总表</td>
        </tr>
      </table>
       <form name="searchform" method="post" action="../assistant/oIntegralTotalAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr class="table-back">
            <td width="8%"  align="right">机构：</td>
            <td width="23%"><select name="oid" id="oid">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="alos">
                <option value="${o.id}" ${o.id==oid?"selected":""}>
                <c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${o.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="7%" align="right">日期：</td>
            <td width="25%"><input name="BeginDate" type="text" id="BeginDate" onFocus="selectDate(this);" value="${begindate}" size="10">
-
  <input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);" value="${enddate}" size="10"></td>
            <td width="9%" align="right">关键字：</td>
            <td width="28%"><input type="text" name="KeyWord" value="${KeyWord}">
            <input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr class="title-top">
          <td  width="15%" align="center" >机构编号</td>
          <td width="18%" align="center" >机构名称</td>
          <td width="17%" align="center" >应得积分</td>
          <td width="14%" align="center" >已得积分</td>
		  <td width="16%" align="center" >积分调整</td>
		  <td width="20%" align="center" >本时间段累计积分</td>
        </tr>
		<c:set var="totalcount" value="0"/>
<c:forEach items="${str}" var="d">
        <tr class="table-back">
          <td  align="center">${d.oid}</td>
          <td align="center">${d.oidname}</td>
          <td align="right"><fmt:formatNumber value="${d.dealintegral}" pattern="0.00"/></td>
          <td align="right"><fmt:formatNumber value="${d.completeintegral}" pattern="0.00"/></td>
		  <td align="right"><fmt:formatNumber value="${d.tiaozheng}" pattern="0.00"/></td>
		   <td align="right"><fmt:formatNumber value="${d.leiji}" pattern="0.00"/></td>
        </tr>
</c:forEach> 
     <!--   <tr class="back-gray-light">
          <td  align="right">本页合计：</td>
		  <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
		   <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="right">&nbsp;</td>
          <td align="right">&nbsp;</td>
          <td align="right">&nbsp;</td>
          <td align="right">&nbsp;</td>
        </tr>
		<tr class="back-gray-light">
          <td  align="right">总合计：</td>
		  <td align="center">&nbsp;</td>
		   <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="right"></td>
          <td align="right"></td>
          <td align="right"></td>
          <td align="right"></td>
          <td align="right"></td>
        </tr> -->
      </table>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
          <tr> 
		  	<td width="50%"><table width="60" border="0">
                <tr>
                  <td align="center"><a href="javascript:excput();">导出</a></td>
                </tr>
              </table></td>
            <td width="50%" align="right">
			<presentation:pagination target="/assistant/oIntegralTotalAction.do"/>	
            </td>
          </tr>
       
      </table>
	  
	  
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="57%" >&nbsp;</td>
          <td width="43%">&nbsp;</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
