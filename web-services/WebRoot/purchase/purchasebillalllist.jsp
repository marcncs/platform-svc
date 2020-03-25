<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
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
	PurchaseBillDetail();
	}
	
	function addNew(){
		popWin("toAddPurchaseBillAction.do",1000,650);
		
	}
	
	function ToInput(){
		popWin("../purchase/selectPurchasePlanAction.do",1000,650);
	}

	function Update(){
		if(checkid!=""){
			popWin("../purchase/toUpdPurchaseBillAction.do?ID="+checkid,1000,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function PurchaseBillDetail(){
		if(checkid!=""){
			document.all.submsg.src="purchaseBillDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.search.PID.value=p.pid;
	document.search.ProvideName.value=p.pname;
	}
	
	function excput(){
		search.target="";
		search.action="../purchase/excPutPurchaseBillAction.do";
		search.submit();
		search.target="";
		search.action="../purchase/listPurchaseBillAllAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../purchase/printPurchaseBillAction.do";
		search.submit();
		search.target="";
		search.action="../purchase/listPurchaseBillAllAction.do";
		
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为："+checkid+"的采购订单吗？如果删除将永远不能恢复!")){
			popWin2("../purchase/delPurchaseBillAction.do?ID="+checkid);
		}
		}else{
			alert("请选择你要操作的记录!");
		}
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

	<body>

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
									产品采购>>采购订单
								</td>
							</tr>
						</table>
						<form name="search" method="post"
								action="listPurchaseBillAllAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="12%" align="right">
									供应商：
								</td>
								<td width="27%">
									<input name="PID" type="hidden" id="PID" value="${PID}">
									<input name="ProvideName" type="text" id="ProvideName" readonly size="35" value="${ProvideName}"><a href="javascript:SelectProvide();"><img
											src="../images/CN/find.gif" width="18" height="18" border="0"
											align="absmiddle"> </a>
								</td>
								<td width="11%" align="right">
									是否复核：
								</td>
								<td width="26%">
									<windrp:select key="YesOrNo" name="IsAudit" value="${IsAudit}" p="y|f" />
								</td>
								<td width="8%" align="right">
									是否批准：
								</td>
								<td width="16%">
									<windrp:select key="YesOrNo" name="IsRatify" value="${IsRatify}" p="y|f" />
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
									<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">

									<input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F(MakeDeptID),'du')"
										value="${uname}" readonly>
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									预计到货日期：
								</td>
								<td>
									<input name="BeginDate" type="text" id="BeginDate"
										value="${BeginDate}" size="10"
										onFocus="javascript:selectDate(this)" readonly="readonly">
									-
									<input name="EndDate" type="text" id="EndDate"
										value="${EndDate}" size="10"
										onFocus="javascript:selectDate(this)" readonly="readonly">
								</td>
								<td align="right">
									关键字：
								</td>
								<td>
									<input type="text" name="KeyWord" value="${KeyWord}">
									
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
								<td width="120">
									<a href="javascript:ToInput();"> <img
											src="../images/CN/import.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;采购计划导入(${wic})</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:addNew();"><img
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
									<a href="javascript:Del();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
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
									<pages:pager action="../purchase/listPurchaseBillAllAction.do" />
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
								<td width="120">
									编号
								</td>
								<td>
									供应商
								</td>
								<td>
									金额
								</td>
								<td>
									预计到货日期
								</td>
								<td width="70">
									是否复核
								</td>
								<td width="70">
									是否批准
								</td>
								<td>
									结算方式
								</td>
								<td>
									制单机构
								</td>
								<td>
									制单部门
								</td>
								<td>
									制单人
								</td>
							</tr>
							<logic:iterate id="p" name="alpb">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${p.id}');">
									<td>
										${p.id}
									</td>
									<td>
										${p.pname}
									</td>
									<td align="right">
										<windrp:format value="${p.totalsum}" p="###,##0.00"/>
									</td>
									<td>
										<windrp:dateformat value='${p.receivedate}' p='yyyy-MM-dd' />
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${p.isaudit}' p='f' />
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${p.isratify}' p='f' />
									</td>
									<td>
										<windrp:getname key='PayMode' value='${p.paymode}' p='f' />
									</td>
									<td>
										<windrp:getname key='organ' value='${p.makeorganid}' p='d' />
									</td>
									<td>
										<windrp:getname key='dept' value='${p.makedeptid}' p='d'/> 
									</td>
									<td>
										<windrp:getname key='users' value='${p.makeid}' p='d'/>
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br>
                         <div style="width:100%">
                        	<div id="tabs1">
                              <ul>
                                <li><a href="javascript:PurchaseBillDetail();"><span>采购单详情</span></a></li>
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
<input type="hidden" name="IsRatify" id ="PID" value="${IsRatify}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
	</body>
</html>
