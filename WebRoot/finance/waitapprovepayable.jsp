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
<script language="JavaScript">
var checkid=0;
var actid=0;
	function CheckedObj(obj,objid,oactid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 actid = oactid;
	}


	function Approve(){
		if(checkid!=""){	
			location.href("../finance/toApprovePayableAction.do?ID="+checkid+"&actid="+actid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function CancelApprove(){
		if(checkid!=""){
			window.open("../finance/cancelApprovePayableAction.do?PAID="+checkid+"&actid="+actid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 应付款单</td>
        </tr>
      </table>
       <form name="search" method="post" action="../finance/waitPayableAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="14%"  align="right">是否审阅 ：</td>
            <td width="34%">${approveselect}</td>
            <td width="11%" align="right">制单日期：</td>
            <td width="41%"><input name="BeginDate" type="text" id="BeginDate" value="${begindate}" size="12" onFocus="javascript:selectDate(this)">
              - 
              <input name="EndDate" type="text" id="EndDate" value="${enddate}" size="12" onFocus="javascript:selectDate(this)"> 
            <input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="10%" >编号</td>
            <td width="12%">应付款金额</td>
            <td width="8%">是否复核</td>
            <td width="16%">制单人</td>
            <td width="24%">制单日期</td>
            <td width="18%">审阅动作</td>
            <td width="12%">是否审阅</td>
          </tr>
          <logic:iterate id="wd" name="alsb" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,'${wd.id}','${wd.actid}');"> 
            <td ><a href="../finance/payableDetailAction.do?ID=${wd.id}">${wd.id}</a></td>
            <td>${wd.payablesum}</td>
            <td>${wd.isauditname}</td>
            <td>${wd.makeidname}</td>
            <td>${wd.makedate}</td>
            <td>${wd.actidname}</td>
            <td>${wd.approvestatusname}</td>
          </tr>
          </logic:iterate> 
      
      </table>
        </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="41%"> 
<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Approve();">审阅</a></td>
                <td width="60"><a href="javascript:CancelApprove();">取消审阅</a></td>
              </tr>
            </table> </td>
          <td width="59%" align="right"> <presentation:pagination target="/finance/waitPayableAction.do"/></td>
        </tr>
      </table>      </td>
  </tr>
</table>
</body>
</html>
