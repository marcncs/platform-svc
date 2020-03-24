<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
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
	
	function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.search.PID.value=p.pid;
	document.search.pname.value=p.pname;
	}
	
	function addNew(){
	window.open("../purchase/toAddPaymentApplyAction.do","","height=550,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid>0){
			window.open("../purchase/toUpdPaymentApplyAction.do?ID="+checkid,"","height=550,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../purchase/delPaymentApplyAction.do?PAID="+checkid,"newwindow",		"height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="paymentApplyDetailAction.do?ID="+checkid;
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								采购管理>>付款申请
							</td>
						</tr>
					</table>
					<form name="search" method="post"
							action="listPaymentApplyAction.do">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td width="9%" align="right">
								供应商：
							</td>
							<td width="30%">
								<input name="PID" type="hidden" id="PID">
								<input name="pname" type="text" id="pname" size="40">
								<a href="javascript:SelectProvide();"><img
										src="../images/CN/find.gif" width="18" height="18" border="0">
								</a>
							</td>
							<td width="11%" align="right">
								制单日期：
							</td>
							<td width="26%">
								<input name="BeginDate" type="text" id="BeginDate"
									value="${begindate}" size="12"
									onFocus="javascript:selectDate(this)">
								-
								<input name="EndDate" type="text" id="EndDate"
									value="${enddate}" size="12"
									onFocus="javascript:selectDate(this)">
								<input type="submit" name="Submit" value="查询">
							</td>
						</tr>
					
					</table>
						</form>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">

							<td width="50">
								<a href="#" onClick="javascript:addNew();"><img
										src="../images/CN/addnew.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;新增</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							<td width="50">
								<a href="javascript:Update();"><img
										src="../images/CN/update.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;修改</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							<td width="50">
								<a href="javascript:Del()"><img
										src="../images/CN/delete.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;删除</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>

							<td style="text-align: right;">
								<pages:pager action="../purchase/listPaymentApplyAction.do" />
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
						<tr align="center" class="title-top">
							<td width="8%">
								编号
							</td>
							<td width="31%">
								供应商
							</td>
							<td width="13%">
								联系人
							</td>
							<td width="15%">
								联系电话
							</td>
							<td width="10%">
								金额
							</td>
							<td width="11%">
								制单人
							</td>
							<td width="12%">
								制单日期
							</td>
						</tr>
						<logic:iterate id="pa" name="alpl">
							<tr align="center" class="table-back-colorbar"
								onClick="CheckedObj(this,${pa.id});">
								<td>
									${pa.id}
								</td>
								<td>
									${pa.pname}
								</td>
								<td>
									${pa.plinkman}
								</td>
								<td>
									${pa.tel}
								</td>
								<td>
									${pa.totalsum}
								</td>
								<td>
									${pa.makeidname}
								</td>
								<td>
									${pa.makedate}
								</td>
							</tr>
						</logic:iterate>
					</table>
					<br>
					<table width="87" border="0" cellpadding="0" cellspacing="1">
						<tr align="center" class="back-bntgray2">
							<td width="85">
								<a href="javascript:Detail();">付款申请详情</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<IFRAME id="submsg"
			style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
			name="submsg" src="../sys/remind.htm" frameBorder="0"
			scrolling="no"></IFRAME>
	</body>
</html>
