<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>打印任务列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/showPE.js"> </SCRIPT>
		<script type="text/javascript">

var checkid="";
var checkStatus="";
var materialCode="";
var primaryCodeStatus="";
var cartonSeqStatus="";
function CheckedObj(obj,objid,pstatus, mcode, pcodeStatus, csStatus){
	for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	obj.className="event";
 	checkid=objid;
 	checkStatus=pstatus;
 	materialCode=mcode;
 	primaryCodeStatus=pcodeStatus;
 	cartonSeqStatus=csStatus; 
 	pdmenu = getCookie("oCookie");
	 switch(pdmenu){
	 	case "1":
		 	if(document.getElementById("DetailUrl")){
		 		Detail(); break;
			}
		case "2":
			if(document.getElementById("PrintHistiryUrl")){
				PrintHistory(); break;
			}
		case "3":
			if(document.getElementById("CommonCodeUrl")){
				CommonCode(); break;
			}
		case "4":
			if(document.getElementById("CartonCodeUrl")){
				CartonCode(); break;
			}
		case "5":
			if(document.getElementById("CovertCodeUrl")){
				CovertCode(); break;
			}
		default:Detail();
	 } 
}
function Delete(){
		if(checkid!=""){
			if ( confirm("你确认要删除编号为:"+checkid+"的记录吗?如果删除将不能恢复！") ){
			popWin("../sap/delPrintJobAction.do?printJobId="+checkid,500,250);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
function SelectOrgan(){
		var p=showModalDialog("selectPlantAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.plantCode.value=p.oecode;
			document.search.plantName.value=p.organname;
	}
function SelectProduct(){
			var p=showModalDialog("../sap/selectProductAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
			document.search.materialCode.value=p.mCode;
			document.search.materialName.value=p.productname;
		}
function Detail(){
	setCookie("oCookie","1");
		if(checkid!=""){
			document.all.basic.src="listPrintJobDetailAction.do?actionType=1&ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
function PrintHistory(){
	setCookie("oCookie","2");
		if(checkid!=""){
			document.all.basic.src="listPrintJobDetailAction.do?actionType=2&ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
function genCommonCode() {
	if(checkid!=""){
		if(primaryCodeStatus == -1) {
			alert("该任务无需生成小包装码!");
			return;
		} else if(primaryCodeStatus == 0) {
			alert("该任务小包装码还未生成!");
			return;
		}
		popWin("toGenerateCommonCode.do?ID="+checkid+"&mCode="+ materialCode,500,250);
	}else{
		alert("请选择你要操作的记录!");
	}
}
function CommonCode() {
	setCookie("oCookie","3");
	if(checkid!=""){
		document.all.basic.src="listCommonCodeLogAction.do?ID="+checkid;
	}else{
		alert("请选择你要操作的记录!");
	}
}
function CartonCode() {
	setCookie("oCookie","4");
	if(checkid!=""){
		document.all.basic.src="listPrintJobDetailAction.do?actionType=3&ID="+checkid;
	}else{
		alert("请选择你要操作的记录!");
	}
}
function excput(){
	if(checkid!=""){
		exportExcel.target="";
		exportExcel.action="excPutPrintJobAction.do?printJobId="+checkid;
		exportExcel.submit();
	}else{
		alert("请选择你要操作的记录!");
	}
}

function CovertCode() {
	setCookie("oCookie","5");
	if(checkid!=""){
		document.all.basic.src="listPrintJobDetailAction.do?actionType=4&ID="+checkid;
	}else{
		alert("请选择你要操作的记录!");
	}
}

this.onload = function abc(){
	//document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
}

function selectCartonSeq() {
	if(checkid>0){
		if(cartonSeqStatus ==1){
		  alert("该打印任务已关联过!");
		  return;
		}
		if(cartonSeqStatus == -1 || cartonSeqStatus == ""){
			alert("该打印任务无需关联!");
		    return;
		}
		popWin4("../sap/toSelectCartonSeqAction.do?ID="+checkid,1000,650,"code");

	}else{
		alert("请选择你要操作的记录!");
	}
}
function Import1() {
	window
			.open(
					"../warehouse/toImportIdcodeAction.do",
					"",
					"height=300,width=500,top=190,left=190,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");

}
</script>
  </head>
  
  <body>
    <table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
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
						<form name="search" method="post" action="listPrintJobAction.do"
								onsubmit="return oncheck();">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="10%" align="right">
									任务编号：
								</td>
								<td width="25%">
									<input type="text" name="printJobId" value=""
										maxlength="30" />
								</td>
								<td align="right">
									工厂：
								</td>
								<td>
									<input name="plantCode" type="hidden" id="plantCode" value="${plantCode}">
									<input name="plantName" type="text" id="plantName" size="30"  value="${plantName}"
									readonly><a href="javascript:SelectOrgan();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>

								</td>
								<td width="10%" align="right">
									生产订单号：
								</td>
								<td width="26%">
									<input name="processOrderNumber" type="text" id="processOrderNumber" value="${processOrderNumber}" maxlength="30" />
								</td>
								<td></td>
							</tr>
							<tr class="table-back">
								<td align="right">
									订单创建日期：
								</td>
								<td>
								<input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
								<td align="right">
									产品：
								</td>
								<td>
									<input name="materialCode" type="hidden" id="materialCode" value="${materialCode}">
									<input name="materialName" type="text" id="materialName" size="30"  value="${materialName}"
									readonly><a href="javascript:SelectProduct();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>
								</td>
								<td width="9%" align="right">
									打印批号： 
								</td>
								<td width="20%">
									<input type="text" name="batchNumber" value="${batchNumber}"
										maxlength="30" />
								</td>
								<td class="SeparatePage">
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									打印状态：
								</td>
								<td>
									<windrp:select key="PrintStatus" name="printingStatus" p="y|f" value="${PrintStatus}" />
								</td>
								<td width="9%" align="right">
								</td>
								<td width="20%">
								</td>
								<td align="right">
								</td>
								<td>
								</td>
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit"
										src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
						</table>
						</form>
						<form name="exportExcel" method="post" action="excPutPrintJobAction.do">
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody><tr class="title-func-back">
								<td width="50">
									<ws:hasAuth operationName="/sap/delPrintJobAction.do">
									<a href="javascript:Delete()"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">删除 </a>
									</ws:hasAuth>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								<td width="90">
								<ws:hasAuth operationName="/sap/generateCommonCode.do">
									<a href="javascript:genCommonCode()"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">生成通用码</a>
									</ws:hasAuth>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								<td width="50">
								<ws:hasAuth operationName="/sap/excPutPrintJobAction.do">
									<a href="javascript:excput()"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">导出</a>
									</ws:hasAuth>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								<%-- <ws:hasAuth operationName="/sap/toSelectCartonSeqAction.do">
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
									<td width="70" align="center">
										<a href="javascript:selectCartonSeq();">
										<img src="../images/CN/setmima.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;箱码关联</a></td>
								</ws:hasAuth> --%>
								<ws:hasAuth operationName="/warehouse/toImportIdcodeAction.do">
									<td width="130">
									<a href="javascript:Import1();"><img src="../images/CN/import.gif"
									width="16" height="16" border="0" align="absmiddle">&nbsp;进口产品码导入</a>
									</td>
									<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../sap/listPrintJobAction.do" />
								</td>
							</tr>
							</tbody>
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
								<td >
									任务编号
								</td>
								<td >
									工厂
								</td>
								<td >
									生产订单号
								</td>
								<td >
									订单创建日期
								</td>
								<td >
									产品
								</td>
								<td >
									包装规格
								</td>
								<td >
									批号
								</td>
								<td >
									箱数
								</td>
								<td >
									标签总数
								</td>
								<td >
									打印状态
								</td>
								<td >
									小包装标签
								</td>
								<td >
									关联箱序号
								</td>
								<td >
									日志编号
								</td>
								<td >
									同步状态
								</td>
							</tr>
							<logic:iterate id="pj" name="printJobs">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${pj.printJobId}','${pj.printingStatus}','${pj.materialCode}','${pj.primaryCodeStatus}','${pj.cartonSeqStatus}');">
									<td>
										${pj.printJobId}
									</td>
									
									<td>
										${pj.plantName}
									</td>
									<td>
										${pj.processOrderNumber}
									</td>
									<td>
										<windrp:dateformat value="${pj.transOrderDate}" p="yyyy-MM-dd" />
									</td>
									<td>
										${pj.materialName}
									</td>
									<td>
										${pj.packSize}
									</td>
									<td>
										${pj.batchNumber}
									</td>
									<td>
										${pj.numberOfCases}
									</td>
									<td>
										${pj.totalNumber}
									</td>
									<td>
										${pj.printingStatusDisplay}
									</td>
									<td >
										<c:choose>
				   							<c:when test="${pj.primaryCodeStatus == 1 and pj.codeFilePath ne null}"><a href="../common/downloadSapfile.jsp?filename=${pj.codeFilePath}">[下载] </a></c:when>
				  							<c:when test="${pj.primaryCodeStatus == 2}"><a href="#" onMouseOver="ShowPE(this,'${pj.codeErrorMsg}');" onMouseOut="HiddenPE();">[生成出错] </a></c:when>
				  							 <c:otherwise><windrp:getname key='PrimaryCodeStatus' value='${pj.primaryCodeStatus}' p='f'/></c:otherwise>
										</c:choose>		
									</td>
									<td>
										<windrp:getname key='SapCartonSeqStatus' value='${pj.cartonSeqStatus}' p='f'/>
									</td>
									<td>
										${pj.uploadId}
									</td>
									<td><windrp:getname key='SyncStatus' value='${pj.syncStatus}' p='f'/></td>
								</tr>
							</logic:iterate>
						</table>
						</form>
						<br>
						<div id="tabs1">
								<ul>
									<li><a id="DetailUrl" href="javascript:Detail();"><span>详细信息</span></a></li>
									<li><a id="PrintHistiryUrl" href="javascript:PrintHistory();"><span>打印历史</span></a></li>
									<li><a id="CommonCodeUrl" href="javascript:CommonCode();"><span>通用码</span></a></li>
									<li><a id="CartonCodeUrl" href="javascript:CartonCode();"><span>箱码</span></a></li>
									<li><a id="CovertCodeUrl" href="javascript:CovertCode();"><span>暗码</span></a></li>
								</ul>
							</div>
						<IFRAME id="phonebook" style="WIDTH: 100%; HEIGHT: 50%; Z-INDEX: 2;"
									name="basic" src="../sys/remind.htm" frameBorder=0 scrolling=no
									onload="setIframeHeight(this)"></IFRAME>
					</div>

				</td>
			</tr>
	</table>
  </body>
</html>
