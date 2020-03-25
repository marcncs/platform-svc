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
	var checkcname="";
	var submenu=0;
	function CheckedObj(obj,objid,objcname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;
	 submenu = getCookie("ReceivableMenu");
	 switch(submenu){
	 	//case 0: Detail() break
		case "1":Receivable(); break;
		case "2":IncomeLog(); break;
		default:Receivable();
	 }
	}
	
	function addNew(){
	window.open("../finance/toAddReceivableObjectAction.do","","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function IncomeLog(){
	setCookie("ReceivableMenu","2");
		if(checkid!=""){
			document.all.submsg.src="../finance/listIncomeLogByReceivableObjectAction.do?ROID="+checkid;
		}else{
			alert("请选择你要操作的记录");
		}
	}
	
	function Receivable(){
	setCookie("ReceivableMenu","1");
		if(checkid!=""){
			document.all.submsg.src="../finance/listReceivableAction.do?ROID="+checkid;
		}else{
			alert("请选择你要操作的记录");
		}
	}
	
	function Update(){
		if(checkid!=""){
		window.open("toUpdReceivableObjectAction.do?ID="+checkid,"","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的产品吗？如果删除将永远不能恢复!")){
		window.open("../finance/delReceivableObjectAction.do?ID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	
	
	function SelectReceivableObject(){
		showModalDialog("toSelectReceivableObjectAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		document.search.roid.value=getCookie("roid");
		//document.search.drawee.value=getCookie("objname");
		document.search.payername.value=getCookie("objname");
		//document.search.dealsum.value=getCookie("shouldreceivable");
	}
	function checkSelectPayer(){
		var payername = document.search.payername.value;
		if ( payername == ""){
			alert("请选择付款方");
			return false;
		}
		
		return true;
	}

</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 收款汇总表</td>
        </tr>
      </table>
       <form name="search" method="post" action="incomeLogTotalAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="10%"  align="right">制单日期：</td>
            <td width="25%">
              <input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
              - 
            <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)"></td>
            <td width="11%" align="right"></td>
            <td width="16%"></td>
            <td width="10%" align="right">付款方：</td>
            <td width="28%"><input name="ROID" type="hidden" id="ROID" value="${ROID}">
			<input name="payername" type="text" size="35" value="${payername}" readonly>
            <a href="javascript:SelectReceivableObject();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
          </tr>
          <tr class="table-back">
            <td  align="right">制单人：</td>
            <td><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select>
            <input type="submit" name="Submit" value="查询" onClick="return checkSelectPayer();"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
           <tr align="center" class="title-top-lock"> 
            <td width="9%" >编号</td>
			<td width="14%">付收方</td>                       
            <td width="15%">制单人</td>
            <td width="16%">制单日期</td>           
			<td width="14%">应收款金额</td> 
          </tr>
		 
      
      </table>
      
        </form>
  </td>
  </tr>
</table>

</html>
