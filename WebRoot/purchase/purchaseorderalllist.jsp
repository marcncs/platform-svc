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
	PurchaseBillDetail();
	}
	
	function addNew(){
	window.open("toAddPurchaseOrderAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function ToInput(){
	window.open("selectPurchaseInquireAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
			window.open("../purchase/toUpdPurchaseOrderAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function PurchaseBillDetail(){
		if(checkid!=""){
			document.all.submsg.src="purchaseOrderDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			window.open("../purchase/toReferPurchaseOrderAction.do?PBID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function SelectProvide(){
	var p=showModalDialog("selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.search.PID.value=p.pid;
	document.search.ProvideName.value=p.pname;
	}
	
	function PurchaseBill(){
		if(checkid!=""){
			window.open("../purchase/purchaseBillAction.do?ID="+checkid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要作废所选的产品吗？如果作废将永远不能恢复!")){
		window.open("../purchase/blankoutPurchaseOrderAction.do?ID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
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
<table width="100%" border="1" cellpadding="0" cellspacing="2" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 采购订单 </td>
        </tr>
      </table>
      <form name="search" method="post" action="listPurchaseOrderAllAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="10%"  align="right">供应商：</td>
            <td width="31%" ><input name="PID" type="hidden" id="PID">
                <input name="ProvideName" type="text" id="ProvideName" size="40">
            <a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
            <td width="9%"  align="right">是否审核：</td>
            <td width="20%" >${approvestatusselect}</td>
            <td width="11%" align="right">是否作废：</td>
            <td width="19%">${isblankoutselect}</td>
          </tr>
          <tr class="table-back">
            <td  align="right">预计到货日期：</td>
            <td ><input name="BeginDate" type="text" id="BeginDate" value="${begindate}" size="12" onFocus="javascript:selectDate(this)">
-
  <input name="EndDate" type="text" id="EndDate" value="${enddate}" size="12" onFocus="javascript:selectDate(this)">
  <input type="submit" name="Submit" value="查询"></td>
            <td  align="right">&nbsp;</td>
            <td >&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="54" >编号</td>
            <td width="564" >供应商</td>
            <td width="155"> 金额</td>
            <td width="180" >预计到货日期</td>
            <td width="137" >是否审核</td>
			<td width="144" >是否作废</td>
          </tr>
          <logic:iterate id="p" name="alpb" > 
          <tr class="table-back" onClick="CheckedObj(this,'${p.id}');"> 
            <td ><a href="purchaseOrderDetailAction.do?ID=${p.id}">${p.id}</a></td>
            <td>${p.providename}</td>
            <td>${p.totalsum}</td>
            <td>${p.receivedate}</td>
            <td>${p.approvestatusname}</td>
			<td>${p.isblankoutname}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="48%"><table width="299"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td width="121"><a href="javascript:ToInput();">采购询价单导入(${wic})</a></td>
                <td width="60"><a href="javascript:addNew();">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
              <!--  <td width="60"><a href="javascript:Refer();">提交</a></td> -->
                <td width="60"><a href="javascript:Del();">作废</a></td>
              </tr>
            </table> </td>
          <td width="52%" align="right"> <presentation:pagination target="/purchase/listPurchaseOrderAllAction.do"/>          </td>
        </tr>
      </table>
      <table width="87"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="85"><a href="javascript:PurchaseBillDetail();">采购订单详情</a></td>
        </tr>
      </table></td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
