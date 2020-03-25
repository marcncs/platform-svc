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

	function Affirm(){
	if(checkid!=""){
		location.href("../purchase/purchaseOrderProductListAction.do?PIID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Hidden(){
		if(checkid!=""){
			window.open("../purchase/hiddenPurchaseOrderAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}

function SelectProvide(){
	var p=showModalDialog("selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	document.search.PID.value=p.pid;
	document.search.ProvideName.value=p.pname;
	}
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择采购订单</td>
        </tr>
      </table>
       <form name="search" method="post" action="selectPurchaseOrderAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="9%"  align="right">单据日期：</td>
            <td width="30%" >
              <input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
              - 
              <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)"></td>
            <td width="11%" align="right">供应商：</td>
            <td width="50%"><input name="PID" type="hidden" id="PID">
              <input name="ProvideName" type="text" id="ProvideName" size="35">
              <a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a>
            <input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="13%" >编号</td>
            <td width="20%">供应商</td>
            <td width="11%">联系人</td>
			<td width="18%">采购部门</td>
            <td width="15%">制单人</td>
			<td width="12%">制单日期</td>
			<td width="11%">批次</td>
		  </tr>
          <logic:iterate id="p" name="alpa" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,'${p.id}');"> 
            <td >${p.id}</td>
            <td>${p.providename}</td>            
            <td>${p.plinkman}</td>
			<td>${p.purchasedeptname}</td>
            <td>${p.makeidname}</td>
			<td>${p.makedate}</td>
			<td>${p.batch}</td>
			</tr>
          </logic:iterate> 
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table width="134"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="68"><a href="javascript:Affirm();">转为采购单</a></td>
                <td width="52"><a href="javascript:Hidden();">隐藏</a></td>
              </tr>
            </table></td>
          <td width="70%" align="right"> <presentation:pagination target="/purchase/selectPurchaseOrderAction.do"/> 
          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
