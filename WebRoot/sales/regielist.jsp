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
	Ploy();
	}

	function Update(){
		if(checkid>0){
			location.href("toUpdRegieAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Ploy(){
	if(checkid>0){
		document.all.submsg.src="listRegiePloyAction.do?RPID="+checkid;
		}else{
		alert("请选择记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delRegieAction.do?RID="+checkid,"newwindow",		"height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
    <td width="772"> 专卖店 </td>
  </tr>
</table>
 <form name="search" method="post" action="listRegieAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
    <tr class="table-back"> 
      <td width="12%" height="32" align="right">是否可用：</td>
      <td width="31%">${isuseselect}</td>
      <td width="15%" align="right">关键字：</td>
      <td width="42%"><input type="text" name="KeyWord" value="">
        <input type="submit" name="Submit" value="查询"></td>
    </tr>
 
</table>
 </form>
<FORM METHOD="POST" name="listform" ACTION="">
<table width="100%" border="0" cellpadding="0" cellspacing="1">

  <tr align="center" class="title-top">
    <td >编号</td>
    <td>专卖店名称</td>
    <td>地址</td>
    <td>店内承包人数</td>
    <td>店长</td>
	<td>是否可用</td>
	</tr>
	<c:set var="count" value="0"/>
  <logic:iterate id="r" name="rList" >
  <tr align="center" class="table-back" onClick="CheckedObj(this,${r.id});">
    <td ><a href="regieDetailAction.do?ID=${r.id}" target="_self">${r.id}</a></td>
    <td>${r.regiename}</td>
    <td>${r.regieaddr}</td>
    <td>${r.mancount}</td>
    <td>${r.regienob}</td>
    <td>${r.isusename}</td>
	</tr>
	<c:set var="count" value="${count+1}"/>
  </logic:iterate>

</table>
  </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td width="60"><a href="../sales/toAddRegieAction.do" >新增</a></td>
                <td width="60"><a href="javascript:Update();" >修改</a></td>
                <!--<td width="60"><a href="javascript:Del();">删除</a></td>-->
              </tr>
            </table></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listRegieAction.do"/>	
            </td>
          </tr>
       
      </table></td>
  </tr>
</table>

	<table  border="0" cellpadding="0" cellspacing="1">
      <tr align="center" class="back-bntgray2">
        <td width="60"><c:if test="${count>0}">
            <a href="javascript:Ploy();">
             
              专卖店活动</a> </c:if></td>
        </tr>
    </table></td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
