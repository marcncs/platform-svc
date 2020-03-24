<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}
	
	function addNew(){
	popWin("toAddPurchaseInquireAction.do",900,600);
	}
/*
	function Update(){
		if(checkid>0){
			location.href("../purchase/toUpdPurchaseInquireAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function Refer(){
		if(checkid>0){
			window.open("../purchase/toReferPurchaseBillAction.do?BillID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function PaymentLog(){
		if(checkid>0){
			location.href("../purchase/listPaymentLogAction.do?OtherKey="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	*/
	function SelectProvide(){
	var p=showModalDialog("toSelectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:10.5cm;center:yes;status:no;scrolling:no;");
	document.search.PID.value=p.pid;
	document.search.ProvideName.value=p.pname;
	}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
		<SCRIPT language=javascript>
function click() {if (event.button==2) {alert('本页拒绝使用右键!');}}document.onmousedown=click
</SCRIPT>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td>
								询价记录
							</td>
						</tr>
					</table>
					<form name="search" method="post"
							action="listPurchaseInquireForPlanAction.do">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td width="9%" align="right">
								供应商：
							</td>
							<td width="20%">
								<input name="PID" type="hidden" id="PID">
								<input name="ProvideName" type="text"
								 id="ProvideName" readonly="readonly"><a href="javascript:SelectProvide();"><img
										src="../images/CN/find.gif" width="18" height="18" border="0"
										align="absmiddle">
								</a>
							</td>
							<td width="10%" align="right">
								询价日期：
							</td>
							<td width="26%">
								<input name="BeginDate" type="text" id="BeginDate" size="10"
									onFocus="javascript:selectDate(this)">
								-
								<input name="EndDate" type="text" id="EndDate" size="10"
									onFocus="javascript:selectDate(this)">
							</td>
							<td width="9%" align="right">
								是否复核：
							</td>
							<td width="22%">
								${isauditselect}
							<td width="4%" class="SeparatePage">
								<input name="Submit" type="image" id="Submit"
									src="../images/CN/search.gif" border="0" title="查询">
							</td>
						</tr>
						
					</table>
					</form>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">
							<td width="50">
								<a href="javascript:addNew();"><img
										src="../images/CN/addnew.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;新增</a>
							</td>
							<td class="SeparatePage">
								<pages:pager
									action="../purchase/listPurchaseInquireForPlanAction.do" />
							</td>
						</tr>
					</table>
					<table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr align="center" class="title-top">
							<td width="12%">
								编号
							</td>
							<td width="22%">
								询价标题
							</td>
							<td width="20%">
								供应商
							</td>
							<td width="10%">
								供应商联系人
							</td>
							<td width="10%">
								制单日期
							</td>
							<td width="8%">
								有效天数
							</td>
							<td width="10%">
								制单人
							</td>
							<td width="8%">
								是否复核
							</td>
						</tr>
						<logic:iterate id="p" name="alpi">
							<tr class="table-back-colorbar" onClick="CheckedObj(this,${p.id});">
								<td>
									<a href="javascript:addNew();">${p.id}</a>
								</td>
								<td>
									${p.inquiretitle}
								</td>
								<td>
									${p.providename}
								</td>
								<td>
									${p.plinkman}
								</td>
								<td><windrp:dateformat value='${p.makedate}' p='yyyy-MM-dd'/>
								</td>
								<td>
									${p.validdate}
								</td>
								<td>
									<windrp:getname key="users" p="d" value="${p.makeid}"/>
								</td>
								<td>
									<windrp:getname key="YesOrNo" p="f" value="${p.isaudit}"/>
								</td>
							</tr>
						</logic:iterate>
					</table>
					
				</td>
			</tr>
		</table>
	</body>
</html>
