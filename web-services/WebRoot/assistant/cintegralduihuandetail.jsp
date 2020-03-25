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
	searchform.action="../assistant/excPutCIntegralDuihuanDetailAction.do";
	searchform.submit();
	searchform.action="../assistant/cIntegralDuihuanDetailAction.do";
}
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">会员已兑换积分明细</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="../assistant/cIntegralDuihuanDetailAction.do">
        
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr class="table-back">
            <td width="8%"  align="right">机构：</td>
            <td width="27%"><select name="organid" id="organid">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="ols">
                <option value="${o.id}" ${o.id==organid?"selected":""}>
                <c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${o.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="9%" align="right">日期：</td>
            <td width="23%"><input name="BeginDate" type="text" id="BeginDate" onFocus="selectDate(this);" value="${begindate}" size="10">
-
  <input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);" value="${enddate}" size="10"></td>
            <td width="9%" align="right">积分：</td>
            <td width="24%"><input name="BeginPrice" type="text" id="BeginPrice" value="${beginprice}" size="10">
-
  <input name="EndPrice" type="text" id="EndPrice" value="${endprice}" size="10"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value="${KeyWord}"><input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
      
      </table>
        </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr class="title-top">
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
			<presentation:pagination target="/assistant/cIntegralDuihuanDetailAction.do"/>	
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
