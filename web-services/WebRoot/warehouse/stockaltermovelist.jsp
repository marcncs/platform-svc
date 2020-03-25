<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
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
	StockMoveDetail();
	}
	<%--
		function ToInput(){
		popWin("../warehouse/selectAlterMoveApplyAction.do",1000,650);
	}
	--%>
	
	function ToInput(){
		popWin("../warehouse/toStockAlterMoveImportAction.do",700,250);
	}
	
	function addNew(){
		popWin("../warehouse/toAddStockAlterMoveAction.do",1000,650);
	}

	function Update(){
		if(checkid!=""){
		popWin("../warehouse/toUpdStockAlterMoveAction.do?ID="+checkid,1000,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function StockMoveDetail(){
		if(checkid!=""){
			document.all.submsg.src="stockAlterMoveDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	

	function excput(){
		search.target="";
		search.action="../warehouse/excPutStockAlterMoveAction.do";
		search.submit();
		search.action="listStockAlterMoveAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printListStockAlterMoveAction.do";
		search.submit();
		search.target="";
		search.action="listStockAlterMoveAction.do";
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为：" +checkid+" 的订购单吗？如果删除将永远不能恢复!")){
				popWin2("../warehouse/delStockAlterMoveAction.do?ID="+checkid);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
	}
	
	function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=vw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.ReceiveOrganID.value=p.id;
			document.search.oname2.value=p.organname;
			document.search.inwarehouseid.value="";
			document.search.wname.value="";
	}
	function SelectOutOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.outOrganId.value=p.id;
			document.search.outOrganName.value=p.organname;
			document.search.outwarehouseid.value="";
			document.search.owname.value="";
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
						<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listStockAlterMoveAction.do">
							<input type="hidden" name="MakeDeptID" id="MakeDeptID"
								value="${MakeDeptID}">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td align="right">
										企业内部单号：
									</td>
									<td>
										<input type="text" name="NCcode" value="${NCcode}" maxlength="60">
									</td>
									<td align="right">
										调出机构：
									</td>
									<td>
										<input name="outOrganId" type="hidden" id="outOrganId"
											value="${outOrganId}">
										<input name="outOrganName" type="text" id="outOrganName" size="30"
											value="${outOrganName}" readonly>
										<a href="javascript:SelectOutOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td width="10%" align="right">
										调出仓库：
									</td>
									<td width="20%">
										<input type="hidden" name="outwarehouseid" id="outwarehouseid"
											value="${outwarehouseid }">
										<input type="text" name="owname" id="owname"
											onClick="selectDUW(this,'outwarehouseid',$F('outOrganId'),'w','')"
											value="${owname }" readonly>
									</td>
									<td></td> 
								</tr>
								<tr class="table-back">
									<td align="right">
										产品名称：
									</td>
									<td>
										<input type="hidden" name="pid" id="pid" value="${pid}">
										<input type="text" name="ProductName" id="ProductName"
											onClick="selectDUW(this,'pid','','pn')" value="${ProductName}"
											readonly>
									</td>
									<td align="right">
										调入机构：
									</td>
									<td>
										<input name="ReceiveOrganID" type="hidden" id="ReceiveOrganID"
											value="${ReceiveOrganID}">
										<input name="oname2" type="text" id="oname2" size="30"
											value="${oname2}" readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td align="right">
										调入仓库：
									</td>
									<td>
										<input type="hidden" name="inwarehouseid" value="${inwarehouseid}"
											id="inwarehouseid">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'inwarehouseid',$F('ReceiveOrganID'),'w','')"
											value="${wname}" readonly>
									</td>
									<td></td>
								</tr>
								<tr class="table-back">
									<td align="right">
										是否无单：
									</td>
									<td>
										<windrp:select key="YesOrNo" name="isNoBill" p="y|f"
											value="${isNoBill}" />
									</td>
									<td align="right">
										是否复核：
									</td>
									<td>
										<windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" />
									</td>
									<td align="right">
										是否发货：
									</td>
									<td>
										<windrp:select key="YesOrNo" name="IsShipment" p="y|f"
											value="${IsShipment}" />
									</td>
									<td></td>
								</tr>
								<tr class="table-back">
									<td width="9%" align="right">
										是否签收：
									</td>
									<td width="23%">
										<windrp:select key="YesOrNo" name="IsComplete" p="y|f"
											value="${IsComplete}" />
									</td>
									<td align="right">
										单据日期：
									</td>
									<td>
										<input name="BeginDate" type="text" id="BeginDate" size="10"
											value="${BeginDate}" onFocus="javascript:selectDate(this)" readonly>
										-
										<input name="EndDate" type="text" id="EndDate" size="10"
											value="${EndDate}" onFocus="javascript:selectDate(this)" readonly>
									</td>
									<td align="right">
										制单机构：
									</td>
									<td>
										<input name="MakeOrganID" type="hidden" id="MakeOrganID"
											value="${MakeOrganID}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td></td>

								</tr>
								<tr class="table-back">
									<td width="9%" align="right">
										制单人：
									</td>
									<td width="23%">

										<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
										<input type="text" name="uname" id="uname"
											onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
											value="${uname}" readonly>
									</td>
									<td align="right">
										单据类型：
									</td>
									<td >
										<windrp:select key="DeliveryType" name="Bsort" p="y|f"
											value="${Bsort}" />
									</td>
									<td width="9%" align="right">
										关键字：
									</td>
									<td width="24%">
										<input type="text" name="KeyWord" value="${KeyWord}">
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

								<ws:hasAuth operationName="/warehouse/toStockAlterMoveImportAction.do">
									<td width="50" align="center">
										<a href="javascript:ToInput();"><img src="../images/CN/import.gif"
												width="16" height="16" border="0" align="absmiddle"> 导入</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/warehouse/toAddStockAlterMoveAction.do">
									<td width="50">
										<a href="javascript:addNew();"><img src="../images/CN/addnew.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/warehouse/toUpdStockAlterMoveAction.do">
									<td width="50">
										<a href="javascript:Update();"><img src="../images/CN/update.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/warehouse/delStockAlterMoveAction.do">
									<td width="50">
										<a href="javascript:Del()"><img src="../images/CN/delete.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/warehouse/excPutStockAlterMoveAction.do">
									<td width="50">
										<a href="javascript:excput();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<%--
							<ws:hasAuth operationName="/warehouse/printListStockAlterMoveAction.do">
							<td width="50">
								<a href="javascript:print();"><img	src="../images/CN/print.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;打印</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							</ws:hasAuth>
							--%>
								<td class="SeparatePage">
									<pages:pager action="../warehouse/listStockAlterMoveAction.do" />
								</td>
							</tr>
						</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table width="100%" border="0" cellpadding="0" cellspacing="1"
								class="sortable">
								<tr align="center" class="title-top">
									<td>
										编号
									</td>
									<td>
										调出机构
									</td>
									<td>
										调出仓库
									</td>
									<td>
										调入机构
									</td>
									<td>
										调入仓库
									</td>
									<td>
										单据日期
									</td>
									<td>
										制单机构
									</td>
									<td>
										制单人
									</td>
									<td>
										单据类型
									</td>
									<td>
										是否
										<br>
										复核
									</td>
									<td>
										是否
										<br>
										发货
									</td>
									<td>
										是否
										<br>
										签收
									</td>
								</tr>
								<logic:iterate id="sa" name="als">
									<tr class="table-back-colorbar" onClick="CheckedObj(this,'${sa.id}');">
										<td class="${sa.isblankout==1?'td-blankout':''}">
											${sa.id}
										</td>
										<td class="${sa.isblankout==1?'td-blankout':''}">
											${sa.outOrganName}
										</td>
										<td class="${sa.isblankout==1?'td-blankout':''}">
											<windrp:getname key='warehouse' value='${sa.outwarehouseid}' p='d' />
										</td>
										<td class="${sa.isblankout==1?'td-blankout':''}">
											${sa.receiveorganidname}
										</td>
										<td class="${sa.isblankout==1?'td-blankout':''}">
											<windrp:getname key='warehouse' value='${sa.inwarehouseid}' p='d' />
										</td>
										<td class="${sa.isblankout==1?'td-blankout':''}">
											<windrp:dateformat value='${sa.movedate}' p='yyyy-MM-dd' />
										</td>
										<td class="${sa.isblankout==1?'td-blankout':''}">
											${sa.makeorganidname}
										</td>
										<td class="${sa.isblankout==1?'td-blankout':''}">
											<windrp:getname key='users' value='${sa.makeid}' p='d' />
										</td>
										<td class="${sa.isblankout==1?'td-blankout':''}">
											<windrp:getname key='DeliveryType' value='${sa.bsort}' p='f' />
										</td>
										<td class="${sa.isblankout==1?'td-blankout':''}">
											<windrp:getname key='YesOrNo' value='${sa.isaudit}' p='f' />
										</td>
										<td class="${sa.isblankout==1?'td-blankout':''}">
											<windrp:getname key='YesOrNo' value='${sa.isshipment}' p='f' />
										</td>
										<td class="${sa.isblankout==1?'td-blankout':''}">
											<windrp:getname key='YesOrNo' value='${sa.iscomplete}' p='f' />
										</td>
									</tr>
								</logic:iterate>

							</table>
						</form>
						<br>
						<div style="width: 100%">
							<div id="tabs1">
								<ul>
									<li>
										<a href="javascript:StockMoveDetail();"><span>${menu }详情</span> </a>
									</li>
								</ul>
							</div>
							<div>
								<IFRAME id="submsg"
									style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
									name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"
									onload="setIframeHeight(this);"></IFRAME>
							</div>
						</div>

					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
