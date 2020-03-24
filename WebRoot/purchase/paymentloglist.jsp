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
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	
	}

	function Update(){
		if(checkid>0){
			location.href("../purchase/toUpdPaymentLogAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function Refer(){
		if(checkid>0){
			window.open("../purchase/toReferPaymentLogAction.do?PLID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
		
	function SelectProvide(){
	var p=showModalDialog("selectProviderAction.do",null,"dialogWidth:15.5cm;dialogHeight:7.5cm;center:yes;status:no;scrolling:no;");
	document.search.PID.value=p.pid;
	document.search.ProvideName.value=p.pname;
	}
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="2" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 付款记录</td>
        </tr>
      </table>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="51" >编号 </td>
            <td width="72">采购单编号</td>
            <td width="273"> 收款方</td>
            <td width="82"> 付款金额</td>
           <!-- <td width="95"> 付款后余款</td>-->
            <td width="60"> 付款方式</td>
            <td width="125"> 开单时间</td>
            <td width="56"> 是否提交 </td>
            <td width="60"> 是否审阅 </td>
            <td width="60">是否完成</td>
          </tr>
          <logic:iterate id="p" name="alpl" >
		  
          <tr align="center" class="table-back" onClick="CheckedObj(this,${p.id});"> 
            <td ><a href="paymentLogDetailAction.do?ID=${p.id}">${p.id}</a></td>
            <td>${p.pbid}</td>
            <td>${p.pname}</td>
            <td><c:if test="${p.approvestatus==3}"><del>${p.paysum}</del></c:if></td>
           <!-- <td>${p.owebalance}</td>-->
            <td>${p.paymodename}</td>
            <td>${p.billdate}</td>
            <td>${p.refer}</td>
            <td>${p.approvename}</td>
            <td>${p.paystatusname}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
            <tr align="center">
              <td width="60"><a href="javascript:history.back();">返回</a></td>
                <td width="60"><a href="toAddPaymentLogAction.do?PBID=${pbid}">新增</a></td>
              <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60"><a href="javascript:Refer();">提交</a></td>
            </tr>
          </table> </td>
          <td width="52%" align="right"> <presentation:pagination target="/purchase/listPaymentLogAction.do"/> 
          </td>
        </tr>
      </table>      </td>
  </tr>
</table>
</body>
</html>
