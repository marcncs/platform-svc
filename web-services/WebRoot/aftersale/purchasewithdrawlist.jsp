<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
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
var checkid="";
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
		popWin("toAddPurchaseWithdrawAction.do",1000,650);
	}

	function Update(){
		if(checkid!=""){
		popWin("toUpdPurchaseWithdrawAction.do?ID="+checkid,1000,650);
		}else{
		alert("请选择你要操作的记录!");
		}
	}


	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为："+checkid+"的采购退货单吗？如果删除将永远不能恢复!")){
			popWin2("../aftersale/delPurchaseWithdrawAction.do?id="+checkid);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid!=""){
		document.all.submsg.src="../aftersale/purchaseWithdrawDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function excput(){
		search.target="";
		search.action="../aftersale/excPutPurchaseWithdrawAction.do";
		search.submit();
		search.target="";
		search.action="../aftersale/listPurchaseWithdrawAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../aftersale/printPurchaseWithdrawAction.do";
		search.submit();
		search.target="";
		search.action="../aftersale/listPurchaseWithdrawAction.do";
		
	}
	
	function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.search.PID.value=p.pid;
	document.search.pname.value=p.pname;
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
									产品采购>>采购退货
								</td>
							</tr>
						</table>
						<form name="search" method="post"
								action="../aftersale/listPurchaseWithdrawAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td align="right">
									制单机构：
								</td>
								<td>
									<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
									<input name="oname" type="text" id="oname" value="${oname}" size="30" 
									readonly><a href="javascript:SelectOrgan();"><img
											src="../images/CN/find.gif" width="18" height="18" border="0"
											align="absmiddle">
									</a>

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
								<td width="11%" align="right">
									是否复核：
								</td>
								<td width="25%">
									<windrp:select key="YesOrNo" name="IsAudit" value="${IsAudit}" p="y|f" />
								</td>
								<td width="8%" align="right">
									是否结案：
								</td>
								<td width="24%">
									<windrp:select key="YesOrNo" name="IsEndcase" value="${IsEndcase}" p="y|f" />
								</td>
								<td align="right">
									是否作废：
								</td>
								<td>
									<windrp:select key="YesOrNo" name="IsBlankOut" value="${IsBlankOut}" p="y|f" />
								</td>

							</tr>
							<tr class="table-back">
								<td width="9%" align="right">
									供应商：
								</td>
								<td width="23%">
									<input name="PID" type="hidden" id="PID" value="${PID}">
									<input name="pname" type="text" id="pname" 
									readonly="readonly" value="${pname}"><a href="javascript:SelectProvide();"><img
											align="absmiddle" src="../images/CN/find.gif" width="18"
											height="18" border="0"> </a>
								</td>
								<td align="right">
									制单日期：
								</td>
								<td>
									<input name="BeginDate" type="text"
										onFocus="javascript:selectDate(this)" size="10"
										readonly="readonly" value="${BeginDate}">
									-
									<input name="EndDate" type="text"
										onFocus="javascript:selectDate(this)" size="10"
										readonly="readonly" value="${EndDate}">
								</td>

								<td>&nbsp;
									
								</td>
								<td class="SeparatePage">
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
								<td width="50">
									<a href="javascript:print();"><img
											src="../images/CN/print.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;打印</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td class="SeparatePage">
									<pages:pager
										action="../aftersale/listPurchaseWithdrawAction.do" />
								</td>
							</tr>
						</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top">
								<td width="14%">
									编号
								</td>
								<td width="17%">
									供应商名称
								</td>
								<td width="11%">
									联系人
								</td>
								<td width="7%">
									总金额
								</td>								
								<td width="10%">
									制单机构
								</td>
								<td width="11%">
									制单人
								</td>
								<td width="9%">
									制单日期
								</td>
                                <td width="7%">
									是否复核
								</td>
								<td width="7%">
									是否结案
								</td>
								<td width="7%">
									是否作废
								</td>
							</tr>
							<logic:iterate id="s" name="also">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${s.id}');">
									<td>
										${s.id}
									</td>
									<td>
										${s.pname}
										<a href="../purchase/listProviderAction.do?PID=${s.pid}"><img
												src="../images/CN/go.gif" width="10" height="10" border="0">
										</a>
									</td>
									<td>
										${s.plinkman}
									</td>
									<td align="right"><windrp:format value='${s.totalsum}' p="###,##0.00"/>
										
									</td>									
									<td>
										<windrp:getname key='organ' value='${s.makeorganid}' p='d' />

									</td>
									<td>
										<windrp:getname key='users' value='${s.makeid}' p='d' />

									</td>
									<td>
										<windrp:dateformat value="${s.makedate}" p="yyyy-MM-dd" />
									</td>
                                    <td>
										<windrp:getname key='YesOrNo' value='${s.isaudit}' p='f' />
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${s.isendcase}' p='f' />
									</td>
									<td>

										<windrp:getname key='YesOrNo' value='${s.isblankout}' p='f' />
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br />
						<div style="width:100%">
                        	<div id="tabs1">
                              <ul>
                                <li><a href="javascript:Detail();"><span>退货详情</span></a></li>
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
<input type="hidden" name="IsEndcase" id ="PID" value="${IsEndcase}">
<input type="hidden" name="IsBlankout" id ="IsBlankout" value="${IsBlankout}">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="MakeDeptID" id ="MakeID" value="${MakeDeptID}">
</form>
	</body>
</html>
