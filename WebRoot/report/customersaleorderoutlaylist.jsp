<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}
	
	function SelectCustomer(){
	showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	//alert(getCookie("id"));
	document.search.CID.value=getCookie("cid");
	document.search.CName.value=getCookie("cname");
	}
	
	function ChkForm(){
		var productid = document.search.ProductName;

		if(productid.value==null||productid.value==""){
			alert("请选择产品");
			return false;
		}
		return true;
	}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="2" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">报表分析>>会员>>客户调货情况</td>
        </tr>
      </table> 
      <form name="search" method="post" action="../report/customerSaleOrderOutlayAction.do" onSubmit="return ChkForm();">
      
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	   
          <tr class="table-back">
            <td width="13%"  align="right">客户：</td>
            <td width="37%"><input name="CID" type="hidden" id="CID" value="${CID}">
              <input id="CName" size="12" name="CName">
  <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
            <td width="16%" align="right">日期：</td>
            <td width="34%"><input id="BeginDate" onFocus="javascript:selectDate(this)" size="12" name="BeginDate">
-
  <input id="EndDate" onFocus="javascript:selectDate(this)" size="12" name="EndDate">
  <input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">

          <tr class="title-top-lock">
            <td width="8%">客户姓名</td> 
            <td width="9%" >调货时间</td>
            <td width="7%" >调货额</td>
            <td width="12%" >费用情况</td>
          </tr>
          <logic:iterate id="s" name="als" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,'${s.cid}');">
            <td>${s.cidname}</td> 
            <td >${s.makedate}</td>
            <td>${s.subsum}</td>
            <td>${s.totaloutlay}</td>
            </tr>
          </logic:iterate> 
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="48%">&nbsp;</td>
          <td width="52%" align="right"></td>
        </tr>
      </table>      </td>
  </tr>
</table>
</body>
</html>
