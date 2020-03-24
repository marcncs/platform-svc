<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

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
	var checkcname="";
	var orgid="";
	var submenu=0;
	function CheckedObj(obj,objid,objcname,objorgid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;
	 orgid= objorgid;
	 submenu = getCookie("ReceivableMenu");
	 switch(submenu){
	 	//case 0: Detail() break
		case "1":Receivable(); break;
		case "2":IncomeLog(); break;
		default:Receivable();
	 }
	}
	
	function addNew(){
	window.open("../finance/toAddReceivableObjectAction.do","","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}

	function IncomeLog(){
	setCookie("ReceivableMenu","2");
		if(checkid!=""){
			document.all.submsg.src="../finance/listIncomeLogByReceivableObjectAction.do?ROID="+checkid+"&ORGID="+orgid;
		}else{
			alert("请选择你要操作的记录");
		}
	}
	
	function Receivable(){
	setCookie("ReceivableMenu","1");
		if(checkid!=""){
			document.all.submsg.src="../finance/listReceivableAction.do?ROID="+checkid+"&ORGID="+orgid;
		}else{
			alert("请选择你要操作的记录");
		}
	}
	
	function Update(){
		if(checkid!=""){
		window.open("toUpdReceivableObjectAction.do?ID="+checkid,"","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SetAwake(){
		if(checkid!=""){
		window.open("../finance/toSetReceiveAwakeAction.do?ID="+checkid,"","height=250,width=300,top=150,left=150,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function CancelAwake(){
		if(checkid!=""){
		window.open("../finance/cancelReceiveAwakeAction.do?ID="+checkid,"","height=250,width=300,top=150,left=150,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的产品吗？如果删除将永远不能恢复!")){
		window.open("../finance/delReceivableObjectAction.do?ID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function SelectCustomer(){
		var os = document.search.ObjectSort.value;
		if(os==0){
		showModalDialog("toSelectUsersAction.do",null,"dialogWidth:16cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		document.search.ID.value=getCookie("uid");
		document.search.cname.value=getCookie("uname");
		}
		if(os==1){
		showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		document.search.ID.value=getCookie("cid");
		document.search.cname.value=getCookie("cname");
		}
		if(os==2){
		showModalDialog("toSelectProvideAction.do",null,"dialogWidth:16cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		document.search.ID.value=getCookie("pid");
		document.search.cname.value=getCookie("pname");
		}

	}
function sc2(){
document.getElementById("di44").style.top=(document.body.scrollTop)+"px";
document.getElementById("di44").style.left=(document.body.scrollLeft)+"px";
}
window.onscroll=sc2;
window.onresize=sc2;
window.onload=sc2;
</script>
</head>

<body >
<div id="di44" style="background:red;width: 100px; height:100px">我是DIV</div>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 收款对象列表</td>
        </tr>
      </table>
      <form name="search" method="post" action="listReceivableObjectAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="10%" height="22" align="right">结算期间：</td>
            <td width="22%">
              <input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)" value="${BeginDate}">
              - 
            <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)" value="${EndDate}"></td>
            <td width="10%" align="right">对象类型：</td>
            <td width="22%"><windrp:select key="ObjectSort" name="ObjectSort" p="y|f" value="${ObjectSort}"/>            </td>
            <td width="8%" align="right">所属机构：</td>
            <td width="28%"><select name="MakeOrganID" id="MakeOrganID">
			 <option value="">所有机构</option>
              <logic:iterate id="ol" name="ols" >
                <option value="${ol.id}">
                <c:forEach var="i" begin="1" end="${fn:length(ol.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${ol.organname}</option>
              </logic:iterate>
            </select></td>
          </tr>
          <tr class="table-back">
            <td height="22" align="right">制单人：</td>
            <td><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select></td>
            <td align="right">关键字：</td>
            <td><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}">
            <input type="submit" name="Submit" value="查询"></td>
            <td align="right"></td>
            <td>&nbsp;</td>
          </tr>
       
      </table>
       </form>
        <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="4%" height="25">编号</td>
            <td width="9%">对象类型</td>
			<td width="18%">付款方名称</td>
			<td width="11%">期初应收金额</td>
            <td width="8%">本期应收金额</td>
            <td width="8%">本期已收金额 </td>
			<td width="8%">期末应收金额 </td>
            <td width="8%">提醒日期</td>
            <td width="10%">所属机构</td>
            <td width="8%">制单人</td>
            <td width="8%">制单日期</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="alpl" > 
          <tr class="table-back" onClick="CheckedObj(this,'${s.oid}','${s.objectsortname}','${s.makeorganid}');"> 
            <td height="20">${s.oid}</td>
            <td>${s.objectsortname}</td>
			<td>${s.payer}</td>
			<td><fmt:formatNumber value="${s.previoussum}" pattern="0.00"/></td>
            <td><fmt:formatNumber value="${s.currentsum}" pattern="0.00"/></td>
			 <td><fmt:formatNumber value="${s.currentalreadysum}" pattern="0.00"/></td>
            <td><fmt:formatNumber value="${s.waitreceivablesum}" pattern="0.00"/></td>
            <td>${s.promisedate}</td>
            <td>${s.makeorganidname}</td>
            <td>${s.makeidname}</td>
            <td>${s.makedate}</td>
            </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
       
      </table>
       </form>
      <table width="100%" height="27" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="36%"><table height="25" border="0" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td width="60"><a href="javascript:addNew();">新增</a></td> 
               <!-- <td width="60"><a href="javascript:Update();">修改</a></td>-->
				<!--<td width="60"><a href="javascript:SetAwake();">设置提醒</a></td>-->
				<!--<td width="60"><a href="javascript:CancelAwake();">取消提醒</a></td>-->
               <!-- <td width="60"><a href="javascript:Del();">删除</a></td>-->
              </tr>
            </table> </td>
          <td width="64%" align="right"> <presentation:pagination target="/finance/listReceivableObjectAction.do"/> 
          </td>
        </tr>
      </table>
      
      <table height="25" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="60"><c:if test="${count>0}">
              <a href="javascript:Receivable();">
                应收款结算</a></c:if></td>
				<td width="60"><c:if test="${count>0}">
              <a href="javascript:IncomeLog();">
                收款</a></c:if></td>
        </tr>
      </table></td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" ></IFRAME>
</body>
</html>
