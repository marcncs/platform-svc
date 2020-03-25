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
	var c=showModalDialog("../sales/toSelectSaleOrderCustomerAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrollbars=yes;");
	document.searchform.CID.value=c.cid;
	document.searchform.CName.value=c.cname;
	}
function SelectSingleProduct(){
	var p=showModalDialog("../warehouse/toSelectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	//alert(getCookie("id"));
	document.searchform.ProductID.value=p.id;
	document.searchform.ProductName.value=p.productname;
	}
function excput(){
	searchform.action="../report/excPutPeddleOrderDayDealAction.do";
	searchform.submit();
	searchform.action="../report/peddleOrderDayDealAction.do";
}
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">零售单日结表</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="peddleOrderDayDealAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back">
            <td width="8%"  align="right">制单机构：</td>
            <td width="24%"><select name="MakeOrganID" id="MakeOrganID">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="alos">
                <option value="${o.id}" ${o.id==MakeOrganID?"selected":""}>
                <c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${o.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="10%" align="right"> 制单人：</td>
            <td width="24%"><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}" ${u.userid==MakeID?"selected":""}>${u.realname}</option>
              </logic:iterate>
            </select></td>
            <td width="7%" align="right">日期：</td>
            <td width="27%"><input name="BeginDate" type="text" id="BeginDate" onFocus="selectDate(this);" value="${begindate}" size="10">
-
  <input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);" value="${enddate}" size="10"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">客户：</td>
            <td><input name="CID" type="hidden" id="CID">
              <input id="CName" name="CName" value="${CName}">
              <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
            <td align="right">结算方式：</td>
            <td><select id="paymode" name="paymode">
				<option value="">所有</option>
				<option value="0" ${paymode==0?"selected":""}>现金</option>
				<option value="1" ${paymode==1?"selected":""}>挂帐</option>
				<option value="2" ${paymode==2?"selected":""}>支票</option>
			</select>
            <a href="javascript:SelectSingleProduct();"></a><a href="javascript:SelectProvide();">
            <input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr>
			<td><table width="100%" border="0" cellpadding="0" cellspacing="1">
				<tr class="title-top-lock">    
				  <td width="11%" align="center" >单据号</td>       
				  <td width="9%"    align="center" >客户编号</td>		 
				  <td width="11%"align="center" >客户名称</td>
				  <td width="19%" align="center" >客户手机</td>
				  <td width="15%" align="center" >收款人</td>
				  <td width="8%" align="center" >金额</td>
				</tr>	
				<c:forEach items="${newlist}" var="d">
				<tr class="table-back">  
				  <td >${d.id}</td>      
				  <td  >${d.cid}</td>		 
				  <td  >${d.cname}</td>
				  <td >${d.cmobile}</td>
				  <td >${d.makeidname}</td>
				  <td >${d.totalsum}</td>
				</tr>
				</c:forEach> 
					<tr class="back-gray-light">
					  <td align="right" width="12%">本页合计：</td>
					  <td align="center">&nbsp;</td>
					  <td align="center">&nbsp;</td>		 
					  <td align="center">&nbsp;</td>
					  <td align="center">&nbsp;</td>
					  <td align="right">${xjtotalsum}</td>
					</tr>		
					<tr class="back-gray-light">
					  <td align="right">总合计：</td>
					  <td align="center">&nbsp;</td>
					  <td align="center">&nbsp;</td>		 
					  <td align="center">&nbsp;</td>
					  <td align="center">&nbsp;</td>
					  <td align="right">${allsum}</td>
					</tr>					
				</table>
			</td>
		</tr>	
   
      </table>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
          <tr> 
		  	<td width="50%"><table width="60" border="0">
                <tr>
                  <td align="center"><a href="javascript:excput();">导出</a></td>
                </tr>
              </table></td>
            <td width="50%" align="right">
			<presentation:pagination target="/report/peddleOrderDayDealAction.do"/>	
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
