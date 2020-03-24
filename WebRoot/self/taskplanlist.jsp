<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,java.net.*,java.net.URLEncoder,java.net.URLDecoder"%>
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

	function AllotTask(){
		if(checkid>0){
window.open("../self/toAllotTaskAction.do?TaskID="+checkid,"newwindow","height=250,width=470,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	function Update(){
		if(checkid>0){
		location.href("../self/toUpdTaskPlanAction.do?ID="+checkid);
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	function keypre(){
	return !(window.event && window.event.keyCode == 13);	
	}

</script>
<script language=javascript>
Event.observe(window, "load", function() {   
    new CAPXOUS.AutoComplete("KeyWord", function() {   
         return "../sys/autoCompleteAction.do?tblname=TaskPlan&keyvalue="+$F('KeyWord');   
     });   
 }); 

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772"> 任务与计划</td>
  </tr>
</table>
 <form name="search" method="post" action="listTaskPlanAction.do">
<table width="100%"   border="0" cellpadding="0" cellspacing="0">
 
    <tr class="table-back"> 
      <td width="8%"  align="right">类别：</td>
      <td width="32%">${sortselect}</td>
      <td width="11%" align="right">关键字：</td>
      <td width="49%"><input type="text" name="KeyWord" value="${keyword}" onKeyPress="return keypre();"><input type="submit" name="Submit" value="查询"></td>
    </tr>
  
</table>
</form>
<FORM METHOD="POST" name="listform" ACTION="">
<table width="100%" border="0" cellpadding="0" cellspacing="1">

  <tr align="center" class="title-top">
    <td width="35">编号</td>
    <td >标题</td>
	<td>开始日期</td>
    <td>结束日期</td>
    <td>状态</td>
    <td>创建人</td>
    <td>类别</td>
    <td>是否审阅</td>
    <td>是否分配</td>
  </tr>
  <logic:iterate id="tp" name="tpfls" >
  <tr align="center" class="table-back" onClick="CheckedObj(this,${tp.id});">
    <td>${tp.id}</td>
    <td  title="点击查看详情"><a href="../self/listTaskPlanDetialAction.do?ID=${tp.id}">${tp.tptitle}</a></td>
    <td>${tp.begindate}</td>
	<td>${tp.enddate}</td>
    <td>${tp.status}</td>
    <td>${tp.username}</td>
    <td>${tp.sort}</td>
    <td>${tp.isapprove}</td>
    <td>${tp.isallot}</td>
  </tr>
  </logic:iterate>
  
</table>
</form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
<tr align="center"> 
                <td width="60"><a href="../self/toaddNewTaskPlanAction.do">新增</a> 
                </td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60">删除</td>
                <td width="60"><a href="javascript:AllotTask();">分配</a></td>
        </tr>
      </table></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sys/listTaskPlanAction.do"/>
			
            </td>
          </tr>
      
      </table></td>
  </tr>
</table>

</td>
  </tr>
</table>
</body>
</html>
