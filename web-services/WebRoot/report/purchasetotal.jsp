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
function showreport(){
	var showtype = searchform.showtype.value;
	var url = "";
	if ( showtype == 1 ){
		url = "../report/purchaseProviderTotalAction.do";
	}else if ( showtype == 2 ){
		url = "../report/purchaseProductTotalAction.do";
	}else if ( showtype == 3 ){
		url = "../report/purchaseBillTotalAction.do";
	}
	searchform.action = url;
	searchform.submit();
}
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">报表分析>>采购>>采购汇总</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back">
            <td width="11%"  align="right">制单机构：</td>
            <td width="22%"><select name="MakeOrganID" id="MakeOrganID">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="alos">
                <option value="${o.id}" ${o.id==makeorganid?"selected":""}>
                <c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${o.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="12%" align="right">采购部门：</td>
            <td width="21%"><select name="PurchaseDept" id="PurchaseDept">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="deptlist">
                <option value="${o.id}">               
                  ${o.deptname}</option>
              </logic:iterate>
            </select></td>
			<td width="6%" align="right">日期：</td>
			<td width="28%"><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" onFocus="selectDate(this);"  size="10">
-
  <input name="EndDate" type="text" id="EndDate" value="${EndDate}" onFocus="selectDate(this);" size="10"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">汇总方式：</td>
            <td><select name="showtype" id="showtype">
              <option value="1">供应商</option>
              <option value="2">产品</option>
              <option value="3">单据</option>
            </select>
              <input type="button" name="Submit" value="查询" onClick="showreport()"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
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
