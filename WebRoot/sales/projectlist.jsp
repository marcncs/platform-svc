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
	window.open("../sales/toAddProjectAction.do","","height=550,width=1050,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid>0){
			window.open("toUpdProjectAction.do?id="+checkid,"","height=550,width=1050,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	//function Detail(id){
	//window.open("../sales/projectDetailAction.do?id="+id,"","height=550,width=1050,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	//}
	
	function Detail(){
		if(checkid>0){
		document.all.submsg.src="../sales/projectDetailAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delProjectAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
    <td width="772"> 项目</td>
  </tr>
</table>
 <form name="search" method="post" action="listProjectAction.do">
<table width="100%"   border="0" cellpadding="0" cellspacing="0">
 
  <tr class="table-back"> 
		<td width="11%"  align="right">项目种类：</td>
      <td width="23%">${pcontentselect}</td>
	   <td width="10%" align="right">项目状态：</td>
	   <td width="20%">${pstatusselect}
	     <input type="submit" name="Submit" value="查询"></td>
	   <td width="10%" align="right">&nbsp;</td>
      <td width="26%">&nbsp;</td>
    </tr>

</table>
  </form>
<table width="100%" border="0" cellpadding="0" cellspacing="1">

  <tr align="center" class="title-top">
    <td width="4%">编号</td>
    <td width="33%">客户名称</td>
    <td width="12%" >项目种类</td>
    <td width="11%">项目状态</td>
    <td width="10%">项目金额</td>
    <td width="10%">项目开始时间</td>
    <td width="10%">项目结束时间</td>
	<td width="10%">录入用户</td>
  </tr>
<c:set var="count" value="0"/>
	<logic:iterate id="p" name="hList" >
  <tr class="table-back" onClick="CheckedObj(this,${p.id});">
    <td>${p.id}</td>
    <td>${p.cidname}</td>
    <td >${p.pcontentname}</td>
    <td>${p.pstatusname}</td>
    <td>${p.amount}</td>
    <td>${p.pbegin}</td>
    <td>${p.pend}</td>
    <td>${p.makeidname}</td>
  </tr>
  <c:set var="count" value="${count+1}"/>
  </logic:iterate>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td width="60"><a href="#" onClick="javascript:addNew();">新增</a>                </td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
              </tr>
            </table></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listProjectAction.do"/>	
            </td>
          </tr>
       
      </table></td>
  </tr>
</table>

	<table width="60"  border="0" cellpadding="0" cellspacing="1">
      <tr align="center" class="back-bntgray2">
        <td width="60"><c:if test="${count>0}">
            <a href="javascript:Detail();">
           
              项目详情</a> </c:if></td>
      </tr>
    </table></td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
