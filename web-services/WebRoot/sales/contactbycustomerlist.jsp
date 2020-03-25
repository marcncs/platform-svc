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

function addNew(){
	window.open("../sales/toAddContactAction.do","","height=550,width=1050,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid>0){
			window.open("toUpdContactLogAction.do?id="+checkid,"","height=550,width=1050,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	//function Detail(id){
	//window.open("../sales/contactLogDetailAction.do?id="+id,"","height=550,width=1050,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	//}
	
	function Detail(){
		if(checkid!=""){
		document.all.submsg.src="../sales/contactLogDetailAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delContactLogAction.do?CLID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
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
    <td width="772"> 联系记录 </td>
  </tr>
</table>
 <form name="search" method="post" action="listContactLogByCustomerAction.do">
<table width="100%"   border="0" cellpadding="0" cellspacing="0">
 
  <tr class="table-back"> 
		<td width="12%"  align="right">内容关键字：</td>
      <td width="25%"><input type="text" name="KeyWord" value=""></td>
	   <td width="14%" align="right">联系日期：</td>
      <td width="49%">
		<input type="text" name="BeginDate" value="${begindate}" onFocus="javascript:selectDate(this)">
              -
              <input type="text" name="EndDate" value="${enddate}" onFocus="javascript:selectDate(this)">
              <input type="submit" name="Submit" value="查询"></td>
    </tr>
  
</table>
</form>
<FORM METHOD="POST" name="listform" ACTION="">
<table width="100%" border="0" cellpadding="0" cellspacing="1">

  <tr align="center" class="title-top">
    <td width="4%">编号</td>
    <td width="30%" >客户名称</td>
    <td width="7%">联系人</td>
    <td width="10%">联系日期</td>
    <td width="13%">联系方式</td>
    <td width="15%">联系性质</td>
	<td width="12%">下次联系时间</td>
	<td width="9%">所属用户</td>
  </tr>
  <logic:present name="usList">
  <c:set var="count" value="0"/>
	<logic:iterate id="c" name="usList" >
  <tr class="table-back" onClick="CheckedObj(this,${c.id});">
    <td>${c.id}</td>
    <td >${c.cidname}</td>
    <td>${c.linkman}</td>
    <td>${c.contactdate}</td>
    <td>${c.contactmodename}</td>
    <td>${c.contactpropertyname}</td>
    <td>${c.nextcontact}</td>
	<td>${c.useridname}</td>
  </tr>
  <c:set var="count" value="${count+1}"/>
  </logic:iterate>
  </logic:present>  
  
</table>
</form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td width="60"><a href="javascript:addNew();">新增</a>                </td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
              </tr>
            </table></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listContactLogByCustomerAction.do"/>	
            </td>
          </tr>
       
      </table></td>
  </tr>
</table>

	<table width="80"  border="0" cellpadding="0" cellspacing="1">
      <tr align="center" class="back-bntgray2">
        <td width="80"><c:if test="${count>0}">
            <a href="javascript:Detail();">
            
              联系记录详情</a></c:if></td>
      </tr>
    </table></td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
