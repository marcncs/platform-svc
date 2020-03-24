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
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
var checkid=0;
var actid=0;
var pbaid=0;
	function CheckedObj(obj,objid,oactid,opbaid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 actid = oactid;
	 pbaid=opbaid;
	}

	function Approve(){
		if(checkid!=""){
			window.open("../finance/toApproveLoanAction.do?ID="+checkid+"&actid="+actid+"&logid="+pbaid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function CancelApprove(){
		if(checkid!=""){
			window.open("../purchase/cancelApprovePurchaseBillAction.do?PBID="+checkid+"&actid="+actid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}

</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 审批个人借支单</td>
        </tr>
      </table>
      <form name="search" method="post" action="../finance/waitLoanAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="10%"  align="right">个人借支单编号：</td>
            <td width="64%" > 
              <input name="KeyWord" type="text" >
              <input type="submit" name="Submit" value="查询">            </td>
          </tr>
        
      </table>
      </form>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="20%" >个人借支单编号</td>
            <td width="36%"> 审批者 </td>
            <td width="22%">审阅动作</td>
            <td width="22%">审阅状态</td>
          </tr>
          <logic:iterate id="pb" name="arpb" > 
          <tr class="table-back" onClick="CheckedObj(this,'${pb.billno}',${pb.actid},${pb.id});"> 
            <td ><a href="../finance/loanDetailAction.do?ID=${pb.billno}">${pb.billno}</a></td>
            <td>${pb.approveidname}</td>
            <td>${pb.actidname}</td>
            <td>${pb.approvename}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="42%">
<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Approve();">审批</a></td>
                <td width="60"><a href="javascript:CancelApprove();">取消审批</a></td>
                <td width="60">&nbsp;</td>
              </tr>
            </table></td>
          <td width="58%" align="right"> <presentation:pagination target="/finance/waitLoanAction.do"/>          </td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
