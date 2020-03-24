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
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	SettlementDetail();
	}
	
	function SettlementDetail(){
		if(checkid!=""){
			document.all.submsg.src="../finance/settlementDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			window.open("../finance/toReferSettlementAction.do?SID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
			window.open("../finance/delSettlementAction.do?SID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">结算单</td>
        </tr>
      </table>
       <form name="search" method="post" action="listSettlementAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="11%"  align="right">制单日期：</td>
            <td width="89%">
              <input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
              - 
              <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)"> 
<input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="5%" >编号</td>
            <td width="21%">结算日期</td>
            <td width="32%">供应商</td>
            <td width="12%">结算总金额</td>
            <td width="10%">是否复核</td>
            <td width="10%">是否批准</td>
            <td width="10%">是否提交</td>
          </tr>
          <logic:iterate id="s" name="arls" > 
          <tr class="table-back" onClick="CheckedObj(this,${s.id});"> 
            <td ><a href="../finance/settlementDetailAction.do?ID=${s.id}">${s.id}</a></td>
            <td>${s.settlementdate}</td>
            <td>${s.providename}</td>
            <td>${s.settlementsum}</td>
            <td>${s.isauditname}</td>
            <td>${s.isratifyname}</td>
            <td>${s.isrefername}</td>
            </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="36%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="../finance/handworkSettlementAction.do">手工结算</a></td>
                <td width="60"><a href="javascript:Refer();">提交</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
              </tr>
            </table> </td>
          <td width="64%" align="right"> <presentation:pagination target="/finance/listSettlementAction.do"/> 
          </td>
        </tr>
      </table>
      
      <table width="87"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="85"><a href="javascript:SettlementDetail();">结算单详情</a></td>
        </tr>
      </table></td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
