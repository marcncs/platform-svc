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
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	Detail();
	}

	//function SaleShipmentDetail(){
	//if(checkid!=""){
	//	window.open("../warehouse/shipmentBillDetailAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	//	}else{
	//	alert("请选择你要操作的记录!");
	//	}
	//}
	
	function Detail(){
		if(checkid!=""){
		document.all.submsg.src="../warehouse/shipmentBillDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
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
          <td width="772"> 送货清单 </td>
  </tr>
</table>
 <form name="search" method="post" action="listShipmentBillAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="12%"  align="right">是否复核：</td>
            <td width="37%">${isauditselect}</td>
            <td width="11%" align="right">需求日期：</td>
            <td width="40%"><input name="BeginDate" type="text" onFocus="javascript:selectDate(this)" size="12">
-
  <input name="EndDate" type="text" onFocus="javascript:selectDate(this)" size="12">
  <input type="submit" name="Submit" value="查询"></td>
          </tr>
       
      </table>
       </form>
  <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
      
          <tr align="center" class="title-top"> 
            <td width="6%" >编号</td>
            <td width="28%">客户名称</td>
            <td width="12%">收货人</td>
            <td width="16%">联系电话</td>
            <td width="15%">发运方式</td>
            <td width="13%">需求日期</td>
            <td width="10%">是否复核</td>
          </tr>
		  <c:set var="count" value="0"/>
	<logic:iterate id="s" name="alsb" > 
          <tr class="table-back" onClick="CheckedObj(this,'${s.id}');"> 
            <td >${s.id}</td>
            <td>${s.receiveidname}</td>
            <td>${s.linkman}</td>
            <td>${s.tel}</td>
            <td>${s.transportmodename}</td>
            <td>${s.requiredate}</td>
            <td>${s.isauditname}</td>
            </tr>
			<c:set var="count" value="${count+1}"/>
		  </logic:iterate>
       
      </table>
       </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%">&nbsp;</td>
    <td width="52%" align="right"> 
            <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/warehouse/listShipmentBillAction.do"/>	
            </td>
          </tr>
       
      </table>
          </td>
  </tr>
</table>

    <table width="80"  border="0" cellpadding="0" cellspacing="1">
      <tr align="center" class="back-bntgray2">
        <td width="80"><c:if test="${count>0}">
            <a href="javascript:Detail();">
            
              零售出库详情</a></c:if></td>
      </tr>
    </table></td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
