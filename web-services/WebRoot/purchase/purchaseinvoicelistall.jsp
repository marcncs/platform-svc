<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
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
	Detail();
	}
	
	function addNew(){
		popWin("toAddPurchaseInvoiceAction.do",1000,650);
	}

	function Update(){
		if(checkid>0){
			popWin("../purchase/toUpdPurchaseInvoiceAction.do?ID="+checkid,1000,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="../purchase/purchaseInvoiceDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.search.ProvideID.value=p.pid;
	document.search.ProvideName.value=p.pname;
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为："+checkid+"的采购发票吗？如果删除将永远不能恢复!")){
			popWin2("../purchase/delPurchaseInvoiceAction.do?ID="+checkid);
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
	function excput(){
		search.target="";
		search.action="../purchase/excPutPurchaseInvoiceAction.do?ProvideID=${pid}";
		search.submit();
		search.action="listPurchaseInvoiceAllAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../purchase/printPurchaseInvoiceAction.do?ProvideID=${pid}";
		search.submit();
		search.target="";
		search.action="listPurchaseInvoiceAllAction.do";
	}
</script>

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
									产品采购>>采购发票
								</td>
							</tr>
						</table>
						<form name="search" method="post"
								action="listPurchaseInvoiceAllAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="8%" align="right">
									供应商：
								</td>
								<td width="27%">
									<input name="ProvideID" type="hidden" id="ProvideID" value="${ProvideID}">
									<input name="ProvideName" type="text" id="ProvideName"
										readonly="readonly" value="${ProvideName}"><a href="javascript:SelectProvide();"><img
											align="absmiddle" src="../images/CN/find.gif" width="18"
											height="18" border="0"> </a>
								</td>
								<td width="8%" align="right">
									发票编号：
								</td>
								<td width="32%">
									<input name="InvoiceCode" type="text" id="InvoiceCode" value="${InvoiceCode}">
								</td>
								<td width="8%" align="right">
									发票类型：
								</td>
								<td width="17%">
									<windrp:select key="InvoiceType" name="InvoiceType" value="${InvoiceType}" p="y|f" />
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									制单机构：
								</td>
								<td>
									<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
									<input name="oname" type="text" id="oname" value="${oname}" 
									size="30" readonly><a href="javascript:SelectOrgan();"><img
											src="../images/CN/find.gif" width="18" height="18" border="0"
											align="absmiddle"> </a>
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
									是否复核：
								</td>
								<td>
									<windrp:select key="YesOrNo" name="IsAudit" value="${IsAudit}" p="y|f" />
								</td>
								<td align="right">
									开票日期：
								</td>
								<td>
									<input name="BeginDate" type="text" readonly="readonly"
										onFocus="javascript:selectDate(this)" value="${BeginDate}" size="10">
									-
									<input name="EndDate" type="text" readonly="readonly"
										onFocus="javascript:selectDate(this)" value="${EndDate}" size="10">

								</td>
								<td align="right">&nbsp;
									
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
									<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" 
										width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
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
									<pages:pager action="../purchase/listPurchaseInvoiceAction.do" />
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
								<td>
									编号
								</td>
                                <td>
									发票编号
								</td>
								<td>
									供应商
								</td>
								<td>
									发票类型
								</td>
								<td>
									制票日期
								</td>
								<td>
									开票日期
								</td>
								<td width="70">
									是否复核
								</td>
								<td>
									制单人
								</td>
								<td>
									制单机构
								</td>

							</tr>
							<logic:iterate id="pl" name="alpl">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,${pl.id});">
                                    <td>
										${pl.id}
									</td>
									<td>
										${pl.invoicecode}
									</td>
									<td>
										${pl.provideidname}
									</td>
									<td>

										<windrp:getname key='InvoiceType' value='${pl.invoicetype}'
											p='f' />
									</td>
									<td>
										<windrp:dateformat value="${pl.makeinvoicedate}" p="yyyy-MM-dd" />

									</td>
									<td>
										<windrp:dateformat value="${pl.invoicedate}" p="yyyy-MM-dd" />

									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${pl.isaudit}' p='f' />
									</td>
									<td>
										<windrp:getname key='users' value='${pl.makeid}' p='d' />
									</td>
									<td>
										<windrp:getname key='organ' value='${pl.makeorganid}' p='d' />
									</td>

								</tr>
							</logic:iterate>
						</table>
						<br />
                        <div style="width:100%">
                        	<div id="tabs1">
                              <ul>
                                <li><a href="javascript:Detail();"><span>发票详情</span></a></li>
                              </ul>
                            </div>
                            <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
                        </div>		
					</div>
				</td>
			</tr>
		</table>

	</body>
</html>
