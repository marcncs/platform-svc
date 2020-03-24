<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<%@ page import="com.winsafe.hbm.util.Internation"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
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

	

	
	function Detail(id){
	if(checkid!=""){
		document.all.submsg.src="../sales/serviceAgreementDetailAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	

	
	function SelectCustomer(){
	var c=showModalDialog("../sales/toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.search.CID.value=c.cid;
	document.search.cname.value=c.cname;
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
    <td width="772"> 查收服务预约</td>
  </tr>
</table>
 <form name="search" method="post" action="../self/receiptServiceAction.do">
<table width="100%" height="40"  border="0" cellpadding="0" cellspacing="0">
 
  <tr class="table-back"> 
		<td width="10%"  align="right">客户名称：</td>
      <td width="19%"><input name="CID" type="hidden" id="CID" value="">
        <input name="cname" type="text" id="cname" value="">
        <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"> </a></td>
	   <td width="11%" align="right">服务种类：</td>
	   <td width="31%">${scontentselect}</td>
	   <td width="9%" align="right">服务状态：</td>
	   <td width="20%">${sstatusselect} </td>
  </tr>
  <tr class="table-back">
    <td  align="right">等级：</td>
    <td>${rankselect}</td>
    <td align="right">约定服务时间：</td>
    <td><input type="text" name="BeginDate" size="12" value="${begindate}" readonly onFocus="javascript:selectDate(this)">
-
  <input type="text" name="EndDate" size="12" value="${enddate}" readonly onFocus="javascript:selectDate(this)"></td>
    <td align="right">所属用户：</td>
    <td><select name="MakeID" id="MakeID">
      <option value="">所有用户</option>
      <logic:iterate id="u" name="als">
        <option value="${u.userid}">${u.realname}</option>
      </logic:iterate>
    </select>
      <input type="submit" name="Submit" value="查询"></td>
  </tr>
  
</table>
</form>
<table width="100%" border="0" cellpadding="0" cellspacing="1">

  <tr align="center" class="title-top">
    <td width="2%">&nbsp;</td>
    <td width="44%">客户名称</td>
    <td width="9%" >服务种类</td>
    <td width="8%">服务状态</td>
    <td width="8%">等级</td>
    <td width="8%">服务费用</td>
    <td width="12%">约定服务时间</td>
    <td width="9%">录入用户</td>
  </tr>
  <c:set var="count" value="0"/>
	<logic:iterate id="p" name="alrt" >
  <tr class="table-back" onClick="CheckedObj(this,${p.id});">
    <td><c:if test="${p.isaffirm==0}"><font color="#FF0000" size="-4">新</font></c:if></td>
    <td>${p.cidname} <a href="../sales/listCustomerAction.do?CID=${p.cid}"><img src="../images/CN/go.gif" width="10" height="10" border="0"></a></td>
    <td >${p.scontentname}</td>
    <td>${p.sstatusname}</td>
    <td>${p.rankname}</td>
    <td>${p.sfee}</td>
    <td>${p.sdate}</td>
    <td>${p.makeidname}</td>
  </tr>

    <c:set var="count" value="${count+1}"/>
  </logic:iterate>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%">&nbsp;</td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="../self/receiptServiceAction.do"/>	
            </td>
          </tr>
       
      </table></td>
  </tr>
</table>

	<table  border="0" cellpadding="0" cellspacing="1">
      <tr align="center" class="back-bntgray2">
        <td width="80"><c:if test="${count>0}">
            <a href="javascript:Detail();">服务预约详情</a></c:if></td>
      </tr>
    </table></td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
