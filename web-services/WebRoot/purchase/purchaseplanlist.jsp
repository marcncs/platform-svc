<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 PurchasePlanDetail();
	}
	
	function addNew(){
		popWin("toAddPurchasePlanAction.do",900,600);
	}

	function Update(){
		if(checkid!=""){
			popWin("../purchase/toUpdPurchasePlanAction.do?ID="+checkid,900,600);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Inquire(){
		if(checkid!=""){
				location.href("../purchase/purchasePlanDetailAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function PurchasePlanDetail(){
		if(checkid!=""){
			document.all.submsg.src="purchasePlanDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			popWin("../purchase/toReferPurchasePlanAction.do?PPID="+checkid,500,250);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function PurchaseInquire(){
		if(checkid!=""){
			document.all.submsg.src="listPurchaseInquireForPlanAction.do?ppid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
			if(window.confirm("您确认要删除编号为："+checkid+" 的采购计划吗？如果删除将永远不能恢复!")){
				popWin2("../purchase/delPurchasePlanAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	

	
	function excput(){		
		search.target="_blank";
		search.action="../purchase/excPutPurchasePlanAction.do";
		search.submit();
		search.target="";
		search.action="../purchase/listPurchasePlanAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../purchase/printPurchasePlanAction.do";
		search.submit();
		search.target="";
		search.action="../purchase/listPurchasePlanAction.do";
	}
	
	function ToInput(){
		popWin("../purchase/selectSaleIndentToPurchansePlanAction.do",1000,650);
	}
	
	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearUser("MakeID","uname");
			clearUser("PlanID","uname2");
	}
</script>
		
	</head>

		<body >
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="div1">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									产品采购>>采购计划
								</td>
							</tr>
						</table>
						<form name="search" method="post"
								action="listPurchasePlanAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="12%" align="right">
									是否复核：
								</td>
								<td width="22%">
									<windrp:select key="YesOrNo" name="IsAudit" value="${IsAudit}" p="y|f" />
								</td>
								<td width="8%" align="right">
									是否批准：
								</td>
								<td width="19%">
									<windrp:select key="YesOrNo" name="IsRatify" value="${IsRatify}" p="y|f" />
								</td>
								<td width="9%" align="right">
									计划日期：
								</td>
								<td width="30%">
									<input name="BeginDate" type="text" id="BeginDate" size="10"
										onFocus="javascript:selectDate(this)" value="${BeginDate}" readonly>
									-
									<input name="EndDate" type="text" id="EndDate" size="10"
										onFocus="javascript:selectDate(this)" value="${EndDate}" readonly>
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									制单机构：
								</td>
								<td>
									<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
									<input name="oname" type="text" id="oname" value="${oname}" size="30" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>

								</td>
								<td align="right">
									制单人：
								</td>
								<td>
									<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
									<input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F('MakeOrganID'),'ou')"
										value="${uname}" readonly>
								</td>
								<td align="right">
									计划人：
								</td>
								<td>
									<div style="float: left;">
										<input type="hidden" name="PlanID" id="PlanID" value="${PlanID}">
										<input type="text" name="uname2" id="uname2"
											onClick="selectDUW(this,'PlanID',$F('MakeOrganID'),'ou')"
											value="${uname2}" readonly>
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
									<a href="javascript:Del()"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<!--  
								<td width="50">
									<a href="javascript:Inquire();"><img
											src="../images/CN/inquire.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;询价</a>
								</td>
							
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								-->
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
									<pages:pager action="../purchase/listPurchasePlanAction.do" />
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
								<td width="13%">
									编号
								</td>
								<td width="10%">
									采购类型
								</td>
								<td width="9%">
									计划日期
								</td>
								<td width="11%">
									计划机构
								</td>
								<td width="8%">
									计划人
								</td>								
								<td width="15%">
									制单机构
								</td>								
								<td width="11%">
									制单人
								</td>
								<td width="9%">
									制单时间
								</td>
                                <td width="7%">
									是否复核
								</td>
								<td width="7%">
									是否批准
								</td>
							</tr>
							<logic:iterate id="p" name="alpa">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${p.id}');">
									<td>
										${p.id}
									</td>
									<td>
										<windrp:getname key='PurchaseSort' value='${p.purchasesort}'
											p='d' />
									</td>
									<td>
										<windrp:dateformat value='${p.plandate}' p='yyyy-MM-dd' />
									</td>
									<td>
										<windrp:getname key='organ' value='${p.makeorganid}' p='d' />
									</td>
									<td>
										<windrp:getname key='users' value='${p.planid}' p='d' />
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
                                    <td>
										<windrp:getname key='YesOrNo' value='${p.isaudit}' p='f' />
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${p.isratify}' p='f' />
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br />
                        <div style="width:100%">
                        	<div id="tabs1">
                              <ul>
                                <li><a href="javascript:PurchasePlanDetail();"><span>采购计划详情</span></a></li>
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
<input type="hidden" name="IsRatify" id ="IsRatify" value="${IsRatify}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="PlanID" id ="MakeID" value="${PlanID}">
</form>
	</body>
</html>
