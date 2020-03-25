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
<SCRIPT LANGUAGE="javascript" src="../js/prototype.js"></SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	CustomerAreas();
	}

	
	function SelectSingleProduct(){
	showModalDialog("toSelectSingleProductAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:auto;");
	//alert(getCookie("id"));
	document.search.ProductID.value=getCookie("id");
	document.search.ProductName.value=getCookie("productname");
	}
	
	function CustomerAreas(){
	var b=$F("BeginDate");
	var e=$F("EndDate");
		if(checkid!=""){
		document.all.submsg.src="customerAreasAction.do?pid="+checkid+"&b="+b+"&="+e;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function ChkForm(){
		var BeginDate = document.search.BeginDate;

		if(BeginDate.value==null||BeginDate.value==""){
			alert("请选择开始日期");
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
          <td width="772">零售区域分析</td>
        </tr>
      </table>
      <form name="search" method="post" action="saleAreasReportAction.do" onSubmit="return ChkForm();">
      
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	    
          <tr class="table-back">
            <td width="13%"  align="right">产品名称：</td>
            <td width="37%"><input name="ProductID" type="hidden" id="ProductID" value="${p}">
              <input id="ProductName" size="12" name="ProductName">
              <a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
            <td width="16%" align="right">日期：</td>
            <td width="34%"><input name="BeginDate" id="BeginDate" onFocus="javascript:selectDate(this)" value="${BeginDate}" size="12">
-
  <input name="EndDate" id="EndDate" onFocus="javascript:selectDate(this)" size="12">
  <input type="submit" name="Submit" value="查询"></td>
          </tr>
      
      </table>
        </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">

          <tr align="center" class="title-top-lock">
            <td width="8%">产品编号</td> 
            <td width="9%" >产品名称</td>
            <td width="12%" >单位</td>
            <td width="12%" >零售数量</td>
          </tr>
          <logic:iterate id="s" name="also" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,'${s.productid}');">
            <td>${s.productid}</td> 
            <td >${s.productname}</td>
            <td>${s.unitidname}</td>
            <td><windrp:format p="###,##0.00" value='${s.quantity}' /></td>
            </tr>
          </logic:iterate> 
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="37%">&nbsp;</td>
          <td width="63%" align="right"><presentation:pagination target="/report/saleAreasReportAction.do"/></td>
        </tr>
      </table>      
      <table  border="0" cellpadding="0" cellspacing="1">
        <tr class="back-bntgray2">
          <td width="90" align="center"><a href="javascript:CustomerAreas();">客户区域分析</a></td>
        </tr>
      </table></td>
  </tr>
</table>
<IFRAME id="chartmsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 400" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
