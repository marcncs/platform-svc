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
	
	}

	function CompleteIncome(){
		if(checkid>0){
			window.open("completeIncomeAction.do?ID="+checkid,"newwindow","height=250,width=470,top=250,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
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
          <td width="772"> 收款</td>
        </tr>
      </table>
       <form name="search" method="post" action="listShouldIncomeLogAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="13%"  align="right">是否完成：</td>
            <td width="33%">${paystatusselect} </td>
            <td width="12%" align="right">收款日期：</td>
            <td width="42%"> 
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
            <td width="7%" >收款单编号</td>
            <td width="7%">销售单编号</td>
            <td width="26%">客户名称</td>
            <td width="8%">金额</td>
            <td width="8%">收款方式</td>
            <td width="16%">收款日期</td>
            <td width="8%">是否完成</td>
          </tr>
          <logic:iterate id="s" name="alpl" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,${s.id});"> 
            <td >${s.id}</td>
            <td>${s.slid}</td>
            <td>${s.customername}</td>
            <td>${s.incomesum}</td>
            <td>${s.incomemodename}</td>
            <td>${s.incomedate}</td>
            <td>${s.incomestatusname}</td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="36%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:CompleteIncome();">完成收款</a></td>
                <td width="60">&nbsp;</td>
                <td width="60">&nbsp;</td>
              </tr>
            </table> </td>
          <td width="64%" align="right"> <presentation:pagination target="/finance/listShouldPaymentLogAction.do"/> 
          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
