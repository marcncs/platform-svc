<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>

		<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	Detail();
	}
	
	function addNew(){
		popWin("toAddPurchaseInquireAction.do",900,600);
		
	}
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="purchaseInquireDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
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
	
	function excput(){
		search.target="";
		search.action="../purchase/excPutPurchaseInquireAction.do";
		search.submit();
		search.target="";
		search.action="../purchase/listPurchaseInquireAllAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../purchase/printListPurchaseInquireAction.do";
		search.submit();
		search.target="";
		search.action="../purchase/listPurchaseInquireAllAction.do";
		
	}
	function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.search.PID.value=p.pid;
	document.search.ProvideName.value=p.pname;
	}
	
	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
	
	function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
		document.search.MakeOrganID.value=p.id;
		document.search.oname.value=p.organname;
		clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
}
	
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body >
		
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="div1">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									产品采购>>询价记录
								</td>
							</tr>
						</table>
						<form name="search" method="post"
								action="listPurchaseInquireAllAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="9%" align="right">
									供应商：
								</td>
								<td width="25%">
									<input name="PID" type="hidden" id="PID" value="${PID}">
									<input name="ProvideName" type="text" 
									readonly id="ProvideName" value="${ProvideName}"><a href="javascript:SelectProvide();"><img
											src="../images/CN/find.gif" width="18" height="18" border="0"
											align="absmiddle"> </a>
								</td>
								<td width="9%" align="right">
									询价日期：
								</td>
								<td width="23%">
									<input name="BeginDate" type="text" id="BeginDate" size="10"
										onFocus="javascript:selectDate(this)" value="${BeginDate}" readonly="readonly">
									-
									<input name="EndDate" type="text" id="EndDate" size="10"
										onFocus="javascript:selectDate(this)" value="${EndDate}" readonly="readonly">
								</td>
								<td width="10%" align="right">
									是否复核：
								</td>
								<td width="24%">
									<windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}"/>
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									制单机构：
								</td>
								<td>
									<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
									<input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>

								</td>
								<td align="right">
									制单部门：
								</td>
								<td>
									<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
									<input type="text" name="deptname" id="deptname"
										onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')"
										value="${deptname}" readonly>
								</td>
								<td align="right">
									制单人：
								</td>
								<td>
									<div style="float: left;">
										<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
										<input type="text" name="uname" id="uname"
											onClick="selectDUW(this,'MakeID',$F(MakeDeptID),'du')"
											value="${uname}" readonly>
									</div>
									<div style="float: right; width: 50px;" class="SeparatePage">
										<input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
									</div>
								</td>
							</tr>
							
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="60">
									<a href="javascript:addNew();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;新增</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
								<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
							    </td>
							    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
								
								<td width="51">
									<a href="javascript:print();"><img
											src="../images/CN/print.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;打印</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td class="SeparatePage">
									<pages:pager
										action="../purchase/listPurchaseInquireAllAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top">
								<td width="60">
									编号
								</td>
								<td>
									询价标题
								</td>
								<td>
									供应商
								</td>
								<td>
									供应商联系人
								</td>
								<td>
									有效天数
								</td>
								<td>
									是否复核
								</td>
								<td>
									制单机构
								</td>
								<td>
									制单人
								</td>
								<td>
									制单日期
								</td>
							</tr>
							<logic:iterate id="p" name="alpi">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,${p.id});">
									<td>
										<a href="purchaseInquireDetailAction.do?ID=${p.id}">${p.id}</a>
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
									<td>
										${p.validdate}
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${p.isaudit}' p='f' />
									</td>
									<td>
										<windrp:getname key='organ' value='${p.makeorganid}' p='d' />
									</td>
									<td>
										<windrp:getname key='users' value='${p.makeid}' p='d' />
									</td>
									<td>
										<windrp:dateformat value='${p.makedate}' p='yyyy-MM-dd' />
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br>
                        <div style="width:100%">
                        	<div id="tabs1">
                              <ul>
                                <li><a href="javascript:Detail();"><span>询价记录详情</span></a></li>
                              </ul>
                            </div>
                            <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
                        </div>		
					</div>
				</td>
			</tr>
		</table>
		<form  name="excputform" method="post" action="">
<input type="hidden" name="IsAudit" id ="IsAudit" value="${IsAudit}">
<input type="hidden" name="PID" id ="PID" value="${PID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="MakeDeptID" id ="MakeID" value="${MakeDeptID}">
</form>
	</body>
</html>
