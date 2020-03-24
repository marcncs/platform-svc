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
	TaskDetail();
	}


	function AllotTask(tpid){
	window.open("../self/allotTaskPlanAction.do?ID="+tpid,"newwindow","height=250,width=470,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function TaskDetail(){
		if(checkid>0){
		document.all.submsg.src="../self/listTaskDetialAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}


</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 收到任务</td>
  </tr>
</table>
<form name="search" method="post" action="receiptTaskAction.do">
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="8%"  align="right">关键字：</td>
            <td width="92%">
              <input type="text" name="KeyWord" value="${keyword}"> 
              <input type="submit" name="Submit" value="查询"></td>
          </tr>
       
      </table> </form>
 <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="35">编号</td>
            <td >标题</td>
            <td>开始日期</td>
            <td>结束日期</td>
            <td>状态</td>
            <td>创建人</td>
          </tr>
          <logic:iterate id="rt" name="alrt" > 
          <tr class="table-back" onClick="CheckedObj(this,${rt.id});"> 
            <td>${rt.id}<c:if test="${rt.isaffirm==0}"><font color="#FF0000" size="-4">新</font></c:if></td>
            <td  >${rt.tptitle}</td>
            <td>${rt.begindate}</td>
            <td>${rt.enddate}</td>
            <td>${rt.statusname}</td>
            <td>${rt.username}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
<tr align="center"> 
                <td width="60">&nbsp;</td>
                <td width="60">&nbsp;</td>
                <td width="60">&nbsp;</td>
                <td width="60">&nbsp;</td>
        </tr>
      </table></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/self/receiptTaskAction.do"/>
			
            </td>
          </tr>
      
      </table></td>
  </tr>
</table>

<table width="62" border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="back-bntgray2">
    <td width="60" ><a href="javascript:TaskDetail();">任务详情</a></td>
  </tr>
</table></td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
