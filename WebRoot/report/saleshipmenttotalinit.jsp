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
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectCustomer(){
	showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	//alert(getCookie("id"));
	document.searchform.ReceiveID.value=getCookie("cid");
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
          <td width="772">报表分析>>零售>>零售出库总</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="saleShipmentTotalAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back">
            <td width="12%"  align="right">会员名称：</td>
            <td width="41%"><input name="ReceiveID" type="hidden" id="ReceiveID">
                <input id="CName" size="12" name="CName">
              <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td> 
            <td width="10%" align="right">日期：</td>
            <td width="37%"><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" onFocus="selectDate(this);" >
-
  <input name="EndDate" type="text" id="EndDate" value="${EndDate}" onFocus="selectDate(this);" >
  <input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr class="title-top-lock">
          <td width="7%"  align="center" >会员名称名称</td>
          <td width="9%" align="center" >产品编号</td>
          <td width="24%" align="center" >产品名称</td>
          <td width="23%" align="center" >规格</td>
          <td width="14%" align="center" >单位</td>
          <td width="6%" align="center">单价</td>
          <td width="8%" align="center" >数量</td>
          <td width="9%" align="center">金额</td>
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
