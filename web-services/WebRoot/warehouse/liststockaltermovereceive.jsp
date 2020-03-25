<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>Untitled Document</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
		<script language="JavaScript">
		//jQuery解除与其它js库的冲突
		var $j = jQuery.noConflict(true);
		
	var checkid=0;
	var completeFlag=0;
	function CheckedObj(obj,objid,isComplete){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 completeFlag = isComplete;
	 StockMoveReceiveDetail();
	}
	
	function StockMoveReceiveDetail(){
		if(checkid!=""){
			document.all.submsg.src="stockAlterMoveReceiveDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function transfer() {
		if(checkid!=""){
			if(completeFlag == 1) {
				alert(" 该单据已签收，不能再转单!");
				return;
			}
			popWin("../warehouse/toTransferStockAlterMoveReceiveAction.do?samid="+checkid,1000,800);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function excput(){
		search.target="";
		search.action="../warehouse/excPutStockAlterMoveReceiveAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listStockAlterMoveReceiveAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printListStockAlterMoveReceiveAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listStockAlterMoveReceiveAction.do";
	}
	
function SelectOrgan(){
var p=showModalDialog("../common/selectOrganAction.do?type=pvw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
if ( p==undefined){return;}
document.search.outOrganId.value=p.id;
document.search.oname.value=p.organname;
	clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
}

function SelectReceiveOrgan(){
var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
if ( p==undefined){return;}
document.search.ReceiveOrganID.value=p.id;
document.search.rname.value=p.organname;
}

function DownTxt(){
	excputform.target="";
	excputform.action="txtPutStockAlterMoveAction.do";
	excputform.submit();
}

function UploadIdcode(){
	popWin("../common/toUploadIdcodeAction.do?billsort=4", 500, 250);
}
	
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}


function completeAll(){
	$j.post("../warehouse/ajaxQuantityCompleteAllStockAlterMoveAction.do", function(result){
	   	var data = eval('(' + result + ')');
		if(data.state=="1"){
			if(confirm("单据"+data.msg+"的条码数量与签收数量不符，是否签收？")){
				receiveAll();
			}
		}else{
			if(confirm("是否全部签收？")){
				receiveAll();
			}
		}
  	}); 
}

function receiveAll(){
	showloading();
	window.open("../warehouse/completeAllStockAlterMoveReceiveAction.do","newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
						<form name="search" method="post"
							action="listStockAlterMoveReceiveAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
									<td align="right">
										企业内部单号：
									</td>
									<td>
										<input type="text" name="NCcode" value="${NCcode}" maxlength="60">
									</td>
									<td  align="right">
										调出机构：
									</td>
									<td >
										<input name="outOrganId" type="hidden" id="outOrganId"
											value="${outOrganId}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
									</td>

									<td width="9%" align="right">

										是否签收：
									</td>
									<td >
										<windrp:select key="YesOrNo" name="IsComplete" p="y|f"
											value="${IsComplete}" />
									</td>
									<td></td>
									</tr>
								<tr class="table-back">
									<td align="right">
										调入机构：
									</td>
									<td>
										<input name="ReceiveOrganID" type="hidden" id="ReceiveOrganID"
											value="${ReceiveOrganID}">
										<input name="rname" type="text" id="rname" size="30" value="${rname}"
											readonly>
										<a href="javascript:SelectReceiveOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
									</td>

									<td  align="right">
										调入仓库：
									</td>
									<td >
										<input type="hidden" name="InWarehouseID" id="InWarehouseID"
											value="${InWarehouseID}">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'InWarehouseID',$F('ReceiveOrganID'),'rw')"
											value="${wname}" readonly>
									</td>
									<td align="right">
										物料号：
									</td>
									<td>
										<input type="text" name="mcode" value="${mcode}" maxlength="60">
									</td>
									<td></td>
								</tr>
								<tr class="table-back">
								
									<td width="9%" align="right">

										制单人：
									</td>
									<td >
										<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
										<input type="text" name="uname" id="uname"
											onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
											value="${uname}" readonly>
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
									<td  align="right">
										关键字：
									</td>
									<td >
										<input type="text" name="KeyWord" value="${KeyWord}" maxlength="60">
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
								<%--
   			<td width="50"><a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>
            <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
            --%>
								<ws:hasAuth
									operationName="/warehouse/excPutStockAlterMoveReceiveAction.do">
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
									operationName="/warehouse/printListStockAlterMoveReceiveAction.do">
									<td width="50">
										<a href="javascript:print();"><img src="../images/CN/print.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth
									operationName="/warehouse/toTransferStockAlterMoveReceiveAction.do">
									<td width="100">
										<a href="javascript:transfer();"><img
												src="../images/CN/update.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;整单销售</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<%--
			<td width="80"><a href="javascript:completeAll();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;全部签收</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>																
		  	--%>
								<td class="SeparatePage">
									<pages:pager action="../warehouse/listStockAlterMoveReceiveAction.do" />
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
									<td width="10%">
										编号
									</td>
									<td width="8%">
										单据日期
									</td>
									<td width="15%">
										调出机构
									</td>
									<td width="15%">
										调出仓库
									</td>
									<td width="15%">
										调入机构
									</td>
									<td width="15%">
										调入仓库
									</td>
									<td width="5%">
										制单人
									</td>
									<td width="5%">
										是否签收
									</td>
								</tr>
								<logic:iterate id="sa" name="als">
									<tr class="table-back-colorbar"
										onClick="CheckedObj(this,'${sa.id}','${sa.iscomplete}');">
										<td>
											${sa.id}
										</td>
										<td>
											<windrp:dateformat value='${sa.movedate}' p='yyyy-MM-dd' />
										</td>
										<td>
											${sa.outorganname}
										</td>
										<td>
											<windrp:getname key='warehouse' value='${sa.outwarehouseid}' p='d' />
										</td>
										<td>
											${sa.receiveorganidname}
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
			<input type="hidden" name="outOrganId" id="outOrganId"
				value="${outorganid}">
			<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
			<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
			<input type="hidden" name="ReceiveOrganID" id="ReceiveOrganID"
				value="${ReceiveOrganID}">
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
