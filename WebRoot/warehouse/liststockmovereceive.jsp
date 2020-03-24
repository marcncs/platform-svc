<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	StockMoveReceiveDetail();
	}
	
	
	
	function StockMoveReceiveDetail(){
		if(checkid!=""){
			document.all.submsg.src="stockMoveReceiveDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

function SelectOrgan(){

		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");

	}

	function SelectReceiveOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		document.search.inorganid.value=p.id;
		document.search.inorganname.value=p.organname;
	}

	function SelectOutOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		document.search.outorganid.value=p.id;
		document.search.outorganname.value=p.organname;
	}

	function DownTxt(){
		excputform.action="txtPutStockMoveAction.do";
		excputform.submit();
	}
	function excput(){
		search.target="";
		search.action="../warehouse/excPutStockMoveReceiveAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listStockMoveReceiveAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printListStockMoveReceiveAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listStockMoveReceiveAction.do";
	}
	
	function UploadIdcode(){
		popWin("../common/toUploadIdcodeAction.do?billsort=5", 500, 250);
	}
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
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
						<form name="search" method="post" action="listStockMoveReceiveAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td align="right">
										转出机构：
									</td>
									<td>
										<input name="outorganid" type="hidden" id="outorganid"
											value="${outorganid}">
										<input name="outorganname" type="text" id="outorganname" size="30"
											value="${outorganname}" readonly>
										<a href="javascript:SelectOutOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
									</td>
									<td width="12%" align="right">
										制单机构：
									</td>
									<td width="23%">
										<input name="MakeOrganID" type="hidden" id="MakeOrganID"
											value="${MakeOrganID}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
									</td>
									<td width="9%" align="right">
										制单人：
									</td>
									<td width="24%">
										<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
										<input type="text" name="uname" id="uname"
											onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
											value="${uname}" readonly>
									</td>
								</tr>
								<tr class="table-back">
								<td align="right">
										转入机构：
									</td>
									<td>
										<input name="inorganid" type="hidden" id="inorganid"
											value="${inorganid}">
										<input name="inorganname" type="text" id="inorganname" size="30"
											value="${inorganname}" readonly>
										<a href="javascript:SelectReceiveOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
									</td>
									<td align="right">
										转入仓库：
									</td>
									<td>
										<input type="hidden" name="InWarehouseID" id="InWarehouseID"
											value="${InWarehouseID}">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'InWarehouseID',$F('inorganid'),'rw')"
											value="${wname}" readonly>
									</td>
									<td colspan="2"></td>
								</tr>
								<tr class="table-back">
									<td width="9%" align="right">
										是否签收：
									</td>
									<td width="23%">
										<windrp:select key="YesOrNo" name="IsComplete" p="y|f"
											value="${IsComplete}" />
									</td>
									<td width="9%" align="right">
										关键字：
									</td>
									<td width="24%">
										<input type="text" name="KeyWord" value="${KeyWord}" maxlength="60">
									</td>
									<td colspan="1"></td>
									<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/warehouse/excPutStockMoveReceiveAction.do">
									<td width="50">
										<a href="javascript:excput();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth
									operationName="/warehouse/printListStockMoveReceiveAction.do">
									<td width="50">
										<a href="javascript:print();"><img src="../images/CN/print.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../warehouse/listStockMoveReceiveAction.do" />
								</td>

							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">

								<tr align="center" class="title-top">
									<td width="140px">
										编号
									</td>
									<td width="100px">
										转仓日期
									</td>
									<td width="120px">
										转出机构
									</td>
									<td style="width: auto;">
										转出仓库
									</td>
									<td width="120px">
										转入机构
									</td>
									<td style="width: auto;">
										转入仓库
									</td>
									<td width="90px">
										制单人
									</td>
									<td width="30px">
										是否
										<br>
										签收
									</td>
								</tr>
								<logic:iterate id="sa" name="als">
									<tr class="table-back-colorbar" onClick="CheckedObj(this,'${sa.id}');">
										<td>
											${sa.id}
										</td>
										<td>
											<windrp:dateformat value='${sa.movedate}' p='yyyy-MM-dd' />
										</td>
										<td>
											<windrp:getname key='organ' value='${sa.outorganid}' p='d' />
										</td>
										<td>
											<windrp:getname key='warehouse' value='${sa.outwarehouseid}' p='d' />
										</td>
										<td>
											<windrp:getname key='organ' value='${sa.inorganid}' p='d' />
										</td>
										<td>
											<windrp:getname key='warehouse' value='${sa.inwarehouseid}' p='d' />
										</td>
										<td>
											<windrp:getname key='users' value='${sa.makeid}' p='d' />
										</td>
										<td>
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
										<a href="javascript:StockMoveReceiveDetail();"><span>详情</span>
										</a>
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
		<form name="excputform" method="post" action="">
			<input type="hidden" name="MakeOrganID" id="MakeOrganID"
				value="${MakeOrganID}">
			<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
			<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
			<input type="hidden" name="inorganid" id="inorganid" value="${inorganid}">
			<input type="hidden" name="IsComplete" id="IsComplete" value="${IsComplete}">
			<input type="hidden" name="KeyWord" id="KeyWord" value="${KeyWord}">
			<input type="hidden" name="IsAudit" id="IsAudit" value="${IsAudit}">
			<input type="hidden" name="IsBlankOut" id="IsBlankOut" value="${IsBlankOut}">
			<input type="hidden" name="BeginDate" id="BeginDate" value="${BeginDate}">
			<input type="hidden" name="EndDate" id="EndDate" value="${EndDate}">
			<input type="hidden" name="KeyWord" id="KeyWord" value="${KeyWord}">
		</form>
	</body>
</html>
