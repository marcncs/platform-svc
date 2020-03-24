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
<script type="text/javascript" src="../javascripts/prototype.js"></script>  
<script type="text/javascript" src="../javascripts/capxous.js"></script>  
<link rel="stylesheet" type="text/css" href="../styles/capxous.css" /> 
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
	
function SelectProvide(){
	var p=showModalDialog("../purchase/selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.search.ProvideID.value=p.pid;
	document.search.pname.value=p.pname;
}

function ExcPut(){
	window.open("../assistant/excputidcode.jsp","","height=450,width=800,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772">序号导出</td>
  </tr>
</table>
 <form name="search" method="post" action="listIdcodeAction.do">
        
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
         <tr class="table-back"> 
            <td width="8%"  align="right">制单机构：</td>
            <td width="24%"><select name="MakeOrganID" id="MakeOrganID">
			 <option value="">所有机构</option>
              <logic:iterate id="ol" name="ols" >
                <option value="${ol.id}">
                <c:forEach var="i" begin="1" end="${fn:length(ol.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${ol.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="8%" align="right">序号：</td>
            <td width="24%"><input type="text" name="IDCode"></td>
            <td width="11%" align="right">相关单据号：</td>
            <td width="25%"><input type="text" name="BillID"></td>
          </tr>
		   <tr class="table-back"> 
            <td width="8%" align="right">供应商：</td>
            <td width="24%"><input name="ProvideID" type="hidden" id="ProvideID" value="">
              <input name="pname" type="text" id="pname" value="" readonly>
            <a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0"> </a></td>
            <td width="8%" align="right">入库日期：</td>
            <td width="24%"><input name="BeginDate" type="text" onFocus="javascript:selectDate(this)" size="12">
-
<input name="EndDate" type="text" onFocus="javascript:selectDate(this)" size="12"></td>
			<td width="11%" align="right">仓库：</td>
            <td width="25%"><select name="WarehouseID" id="WarehouseID">
              <option value="" selected>所有仓库</option>
              <logic:iterate id="w" name="wls" >
                <option value="${w.id}">${w.warehousename}</option>
              </logic:iterate>
            </select></td>
          </tr>
		   <tr class="table-back"> 
            <td width="8%" align="right">关键字：</td>
            <td width="24%"><input name="KeyWord" type="text" id="KeyWord" >
              <input name="submit" type="submit" value="查询"></td>
            <td width="8%" align="right">&nbsp;</td>
            <td width="24%">&nbsp;</td>
            <td width="11%" align="right">&nbsp;</td>
            <td width="25%">&nbsp;</td>
		   </tr>
        
      </table>
      </form>
 <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="15%" >序号</td>
			<td width="10%">产品编号</td>
            <td width="10%">产品名称</td>
            <td width="10%">制单机构</td>
			<td width="10%">入库单号</td>
			<td width="10%">仓库</td>
            <td width="8%">供应商</td>
            <td width="12%">入库日期</td>
          </tr>
		  <logic:iterate id="ms" name="list" >
		 
          <tr align="center" class="table-back" onClick="CheckedObj(this,${ms.id});"> 
			<td >${ms.idcode}</td>
            <td>${ms.productid}</td>
            <td>${ms.productname}</td>
            <td>${ms.makeorganidname}</td>
			<td>${ms.billid}</td>
			<td>${ms.warehouseidname}</td>
            <td>${ms.providename}</td>
            <td>${ms.makedate}</td>
          </tr>
          </logic:iterate>
       
      </table>
       </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><!--<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="../sales/toAddCustomerAction.do">新增</a> 
                </td>
                <td width="60"><a href="javascript:Update(${c.cid});">修改</a></td>
                <td width="60">移交</td>
                <td width="60">共享</td>
              </tr>
            </table>--></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >
          <tr> 		  
            <td width="50%" align="right">
				<presentation:pagination target="/assistant/listIdcodeAction.do"/>	
            </td>
          </tr>       
      </table></td>
  </tr>
</table>
	<table width="62" border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="back-bntgray2">
    <td width="60" ><a href="javascript:ExcPut();">导出</a></td>
    </tr>
</table>
</td>
</tr>
</table>

<form name="xlsform" method="post" >
<input type="hidden" name="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="IDCode" value="${IDCode}">
<input type="hidden" name="BillID" value="${BillID}">
<input type="hidden" name="ProvideID" value="${ProvideID}">
<input type="hidden" name="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" value="${EndDate}">
<input type="hidden" name="WarehouseID" value="${WarehouseID}">
<input type="hidden" name="KeyWord" value="${KeyWord}">
</form>
</html>
