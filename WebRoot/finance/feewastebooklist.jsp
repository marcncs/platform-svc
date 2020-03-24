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
	Detail();
	}
	
	function addNew(){
	window.open("../finance/toAddLoanAction.do","","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdLoanAction.do?ID="+checkid,"","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	
	function SelectCustomer(){
		var os = document.search.ObjectSort.value;
		if(os==0){
		showModalDialog("toSelectUsersAction.do",null,"dialogWidth:16cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		document.search.CID.value=getCookie("uid");
		document.search.cname.value=getCookie("uname");
		}
		if(os==1){
		var c=showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		document.search.CID.value=c.cid;
		document.search.cname.value=c.cname;
		}
		if(os==2){
		showModalDialog("toSelectProvideAction.do",null,"dialogWidth:16cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		document.search.CID.value=getCookie("pid");
		document.search.cname.value=getCookie("pname");
		}

	}

	
	function Del(){
		if(checkid!=""){
			window.open("../finance/delLoanAction.do?LID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="772">费用台帐列表</td>
        </tr>
      </table>
       <form name="search" method="post" action="listFeeWasteBookAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="9%"  align="right">
            对象类型：</td>
            <td width="28%"><windrp:select key="ObjectSort" name="ObjectSort" p="y|f" value="${ObjectSort}"/>
           </td>
            <td width="11%" align="right">客户：</td>
            <td width="23%"><input name="CID" type="hidden" id="CID">
              <input name="cname" type="text" id="cname" size="30">
            <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a> <input type="submit" name="Submit" value="查询"></td>
            <td width="5%" align="right">&nbsp;</td>
            <td width="24%">&nbsp;</td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">

          <tr align="center" class="title-top"> 
            <td width="5%" >编号</td>
            <td width="14%">客户名称</td>
            <td width="8%">部门名称</td>
            <td width="8%">职员名称</td>
            <td width="13%">项目</td>
			<td width="9%">单据号</td>
			<td width="18%">摘要</td>
            <td width="8%">本期收入</td>
			<td width="8%">本期支出</td>			
			<td width="9%">记录日期</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="arls" > 
          <tr class="table-back" > 
            <td >${s.id}</td>
            <td>${s.cname}</td>
            <td>${s.feedeptname}</td>
            <td>${s.feeidname}</td>
            <td>${s.porject}</td>
			<td>${s.billno}</td>
			<td>${s.memo}</td>
            <td>${s.cycleinsum}</td>
			<td>${s.cycleoutsum}</td>
			
			<td>${s.recorddate}</td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="36%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
             <!--   <td width="60"><a href="javascript:addNew();">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td> -->
              </tr>
            </table> </td>
          <td width="64%" align="right"> <presentation:pagination target="/finance/listFeeWasteBookAction.do"/> 
          </td>
        </tr>
      </table>
      
      </td>
  </tr>
</table>

</body>
</html>
