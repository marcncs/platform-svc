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
		if(checkid!=""){
			location.href("../finance/toUpdIncomeLogAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function Refer(){
		if(checkid!=""){
			window.open("../finance/toReferIncomeLogAction.do?ILID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Complete(){
		if(checkid!=""){
			window.open("../finance/completeIncomeAction.do?ILID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function SelectProvide(){
	showModalDialog("toSelectProvideAction.do",null,"dialogWidth:15.5cm;dialogHeight:7.5cm;center:yes;status:no;scrolling:no;");
	document.search.PID.value=getCookie("pid");
	document.search.ProvideName.value=getCookie("pname");
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
          <td width="772"> 收款
          </td>
        </tr>
      </table>
       <form name="search" method="post" action="listToCompletePaymentLogAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="13%"  align="right">制单日期：</td>
            <td width="37%"><input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
              -
            <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)"></td>
            <td width="17%" align="right">是否付款：</td>
            <td width="33%">${incomestatusselect}
            <input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="listToCompleteIncomeLogAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
		  <tr class="title-top"> 
            <td width="4%"  align="center">编号 </td>
            <td width="22%"  align="center">款项来源</td>
            <td width="20%"  align="center">收款去向</td>
            <td width="17%"  align="center">收款金额</td>
            <td width="7%"  align="center">收款时间</td>
            <td width="8%"  align="center">票据号</td>
            <td width="11%"  align="center">是否完成 </td>
          </tr>
          <logic:iterate id="p" name="arls" > 
          <tr class="table-back" onClick="CheckedObj(this,'${p.id}');"> 
            <td  align="center"><a href="incomeLogDetailAction.do?ID=${p.id}">${p.id}</a></td>
            <td align="center">${p.fundsrcname}</td>
            <td align="center">${p.fundattachname}</td>
            <td align="center">${p.incomesum}</td>
            <td align="center">${p.incomedate}</td>
            <td align="center">${p.billnum}</td>
            <td align="center">${p.incomestatusname}</td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="40%">
<table  border="0" cellpadding="0" cellspacing="0">
            <tr align="center">
              <td width="60"><a href="javascript:Complete();">完成</a></td>
            </tr>
          </table> 
          </td>
          <td width="60%" align="right"> <presentation:pagination target="../finance/listToCompleteIncomeLogAction.do"/> 
          </td>
        </tr>
      </table>      </td>
  </tr>
</table>
</body>
</html>
