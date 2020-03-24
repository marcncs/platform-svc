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
function SelectCustomer(){
	showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	//alert(getCookie("id"));
	document.searchform.CID.value=getCookie("cid");
	document.searchform.CName.value=getCookie("cname");
	}
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">网站订单总表</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="webIndentTotalAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back">
            <td width="8%"  align="right">客户：</td>
            <td width="29%"><input name="CID" type="hidden" id="CID">
                <input id="CName" name="CName">
              <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
            <td width="9%" align="right">配送机构：</td>
            <td width="21%"><select name="EquipOrganID" id="MakeOrganID">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="alos">
                <option value="${o.id}" ${o.id==equiporganid?"selected":""}>
                  <c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${o.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="10%" align="right">开票信息：</td>
            <td width="23%"><select name="InvMsg">
              <option value="">所有</option>
              <logic:iterate id="c" name="icls">
                <option value="${c.id}">${c.ivname}</option>
              </logic:iterate>
            </select></td>
          </tr>
          <tr class="table-back">
            <td  align="right">发运方式：</td>
            <td>${transportmodeselect}</td>
            <td align="right">日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" onFocus="selectDate(this);" value="${begindate}" size="10">
-
  <input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);" value="${enddate}" size="10"></td>
            <td align="right">关键字：</td>
            <td><input name="KeyWord" type="text" id="KeyWord">
              <input type="submit" name="Submit" value="查询"></td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr class="title-top-lock">
          <td width="7%"  align="center" >客户名称</td>
          <td width="12%" align="center" >产品编号</td>
          <td width="22%" align="center" >产品名称</td>
          <td width="20%" align="center" >规格</td>
          <td width="14%" align="center" >单位</td>
          <td width="8%" align="center" >单价</td>
          <td width="8%" align="center" >数量</td>
          <td width="9%" align="center" >金额</td>
		  <!--<td width="9%" align="center" >成本</td>-->
        </tr>
		<c:set var="totalcount" value="0"/>
		<logic:iterate id="r" name="str" > 
        <tr>
          <td  colspan="9">${r.cname}[${r.makedate}]</td>
          </tr>
<c:forEach items="${r.sodls}" var="d">
        <tr class="table-back">
          <td  align="center"></td>
          <td >${d.productid}</td>
          <td >${d.productname}</td>
          <td >${d.specmode}</td>
          <td >${d.unitidname}</td>
          <td align="right">${d.unitprice}</td>
          <td align="right"><windrp:format p="###,##0.00" value="${d.quantity}" /></td>
          <td align="right">${d.subsum}</td>
        </tr>
</c:forEach> 
        <tr class="back-gray-light">
          <td  align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
		  <!-- <td align="center">&nbsp;</td>-->
          <td align="center">小计</td>
          <td align="right">${r.strsubsum}</td>
        </tr>
		
		</logic:iterate> 
      </table>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
          <tr> 
		  	<td width="50%">&nbsp;</td>
            <td width="50%" align="right">
			<presentation:pagination target="/report/webIndentTotalAction.do"/>	
            </td>
          </tr>
       
      </table>
	  
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11%" class="text-orange-heavy">&nbsp;</td>
          <td width="11%" class="text-orange-heavy">&nbsp;</td>
          <td width="11%" class="text-orange-heavy">&nbsp;</td>
          <td width="9%" class="text-orange-heavy">&nbsp;</td>
          <td width="15%" class="text-orange-heavy">当前页合计：</td>
          <td width="18%" class="text-orange-heavy">${totalsum}</td>
          <td width="10%" align="center" class="text-orange-heavy">总合计：</td>
          <td width="15%" align="right" class="text-orange-heavy">${allsum}</td>
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
