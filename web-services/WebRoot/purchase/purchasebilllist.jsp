<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
	<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 	for(i=0; i<obj.parentNode.childNodes.length; i++)
	 	{
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 	}
	 
	 	obj.className="event";
	 	checkid=objid;
		PurchaseBillDetail();
	}

	function Update(){
		if(checkid!=""){
			location.href("../purchase/toUpdPurchaseBillAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function PurchaseBillDetail(){
		if(checkid!=""){
			document.all.submsg2.src="purchaseBillDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
		
	function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:10.5cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.search.PID.value=p.pid;
	document.search.ProvideName.value=p.pname;
	}
	
function excput(){
		excputform.target="";
		excputform.action="../purchase/excPutPurchaseBillAction.do";
		excputform.submit();
	}
	function print(){
		excputform.target="_blank";
		excputform.action="../purchase/printPurchaseBillAction.do";
		excputform.submit();
		
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的产品吗？如果删除将永远不能恢复!")){
		popWin2("../purchase/delPurchaseBillAction.do?ID="+checkid);
		}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	


</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>
			<table width="100%" height="40" border="0" cellpadding="0"
				cellspacing="0" class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td width="772">
						采购订单
					</td>
				</tr>
			</table>
			<form name="search" method="post"
					action="listPurchaseBillAction.do">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">

				<tr class="table-back">
					<td width="12%" align="right">
						供应商：
					</td>
					<td width="35%">
						<input name="PID" type="hidden" id="PID" value="${PID}">
						<input name="ProvideName" type="text" id="ProvideName" value="${ProvideName}" readonly
							size="35"><a href="javascript:SelectProvide();"><img
								src="../images/CN/find.gif" width="18" height="18" border="0"
								align="absmiddle"> </a>
					</td>
					<td width="11%" align="right">
						是否复核：
					</td>
					<td width="19%">
						<windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}"/>
					</td>
					<td width="9%" align="right">
						是否批准：
					</td>
					<td width="14%">
						<windrp:select key="YesOrNo" name="IsRatify" p="y|f" value="${IsRatify}"/>
					</td>
				</tr>
				<tr class="table-back">
					<td align="right">
						预计到货日期：
					</td>
					<td>
						<input name="BeginDate" type="text" id="BeginDate"
							value="${BeginDate}" size="10" readonly
							onFocus="javascript:selectDate(this)">
						-
						<input name="EndDate" type="text" id="EndDate"
							value="${EndDate}" size="10" readonly
							onFocus="javascript:selectDate(this)">
						
					</td>
					<td align="right">&nbsp;
						
					</td>
					<td>&nbsp;
						
					</td>
					<td align="right">&nbsp;
						
					</td>
					<td class="SeparatePage">
						<input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
					</td>
				</tr>
				
			</table>
			</form>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr class="title-func-back">
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
						<pages:pager action="../purchase/listPurchaseBillAction.do" />
					</td>
				</tr>
			</table>
		
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
				<tr align="center" class="title-top">
					<td width="15%">
						编号
					</td>
					<td width="40%">
						供应商
					</td>
					<td width="10%">
						金额
					</td>
					<td width="15%">
						预计到货日期
					</td>
					<td width="11%">
						是否复核
					</td>
					<td width="12%">
						是否批准
					</td>
				</tr>
				<logic:iterate id="p" name="alpb">
					<tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}');">
						<td>
							${p.id}
						</td>
						<td>
							${p.pname}
						</td>
						<td align="right">
							${p.totalsum}
						</td>
						<td><windrp:dateformat value='${p.receivedate}' p='yyyy-MM-dd'/></td>
						<td>
							<windrp:getname key="YesOrNo" p="f" value="${p.isaudit}" />
						</td>
						<td>
							<windrp:getname key="YesOrNo" p="f" value="${p.isratify}" />
						</td>
					</tr>
				</logic:iterate>
			</table>
			<br>
			
			<div style="width:100%;">
                  	<div id="tabs1">
                        <ul>
                          <li><a href="javascript:PurchaseBillDetail();"><span>采购订单详情</span></a></li>
                        </ul>
                      </div>
                      <div>
                            <IFRAME id="submsg2"
							style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
							name="submsg2" src="../sys/remind.htm" frameborder="0" scrolling="no"
							onload="setParentIframeHeight(this,'submsg','listdiv'),setIframeHeight(this)"></IFRAME>
					</div>  
            </div>	
<form  name="excputform" method="post" action="">
<input type="hidden" name="IsAudit" id ="IsAudit" value="${IsAudit}">
<input type="hidden" name="PID" id ="PID" value="${PID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="MakeDeptID" id ="MakeID" value="${MakeDeptID}">
<input type="hidden" name="IsRatify" id ="IsRatify" value="${IsRatify}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>		
	</body>
</html>
