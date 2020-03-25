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
var materialCode=""
function CheckedObj(obj,objid){
	for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	obj.className="event";
 	checkid=objid;
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
			document.search.plantCode.value=p.id;
			document.search.plantName.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
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
		if(checkStatus == 0) {
			alert("该任务还未打印!");
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
function excput(){
	search.target="";
	search.action="../sap/excPutInvoiceAction.do";
	search.submit();
	search.action="listInvoicesAction.do";
}
this.onload = function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
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
						<form name="search" method="post" action="listInvoicesAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="10%" align="right">
									发票编号：
								</td>
								<td width="25%">
									<input type="text" name="invoiceNumber" value="${invoiceNumber}"
										maxlength="30" />
								</td>
								<td width="10%" align="right">
									送货单号：
								</td>
								<td width="26%">
									<input name="deliveryNumber" type="text" id="deliveryNumber" value="${deliveryNumber}" maxlength="30" />
								</td>
								<td align="right">
									发票日期：
								</td>
								<td>
								<input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
								
								
								<td></td>
							</tr>
							<tr class="table-back">
								<td align="right">
									客户编号：
								</td>
								<td>
									<input type="text" name="partnSold" value="${partnSold}"
										maxlength="30" />
<%--									<input name="plantCode" type="hidden" id="plantCode" value="${plantCode}">--%>
<%--									<input name="plantName" type="text" id="plantName" size="30"  value="${plantName}"--%>
<%--									readonly><a href="javascript:SelectOrgan();"><img --%>
<%--									src="../images/CN/find.gif" width="18" height="18" --%>
<%--									border="0" align="absmiddle"></a>--%>

								</td>

								<td align="right">
									客户名称：
								</td>
								<td>
									<input type="text" name="partnName" value="${partnName}"
										maxlength="30" />
								</td>
								<td width="9%" align="right">
									物料号： 
								</td>
								<td width="20%">
									<input type="text" name="materialCode" value="${materialCode}"
										maxlength="30" />
<%--									<input name="materialCode" type="hidden" id="materialCode" value="${materialCode}">--%>
<%--									<input name="materialName" type="text" id="materialName" size="30"  value="${materialName}"--%>
<%--									readonly><a href="javascript:SelectProduct();"><img --%>
<%--									src="../images/CN/find.gif" width="18" height="18" --%>
<%--									border="0" align="absmiddle"></a>--%>
								</td>
								<td >
								</td>
							</tr>
							<tr class="table-back">
								
								<td width="9%" align="right">
								产品名称：
								</td>
								<td width="20%">
								<input type="text" name="productname" value="${productname}"
										maxlength="30" />
								</td>
								<td align="right">
								规格：
								</td>
								<td>
								<input type="text" name="packagesize" value="${packagesize}"
										maxlength="30" />
								</td>
								<td align="right">
									批号：
								</td>
								<td>
									<input type="text" name="batchNumber" value="${batchNumber}"
										maxlength="30" />
								</td>
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit"
										src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody><tr class="title-func-back">
								<td width="50">
								<ws:hasAuth operationName="/sap/excPutInvoiceAction.do">
									<a href="javascript:excput()"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">导出</a>
									</ws:hasAuth>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								<td class="SeparatePage">
									<pages:pager action="../sap/listInvoicesAction.do" />
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
								<td>
									发票编号
								</td>
								<td>
									发票日期
								</td>
								<td>
									客户编号
								</td>
								<td>
									客户名称
								</td>
								<td>
									送货单号
								</td>
								<td>
									发票行号
								</td>
								<td>
									物料号
								</td>
								<td>
									产品
								</td>
								<td>
									规格
								</td>
								<td>
									批号
								</td>
								<td>
									数量
								</td>
								<td>
									金额
								</td>
							</tr>
							<logic:iterate id="inv" name="invoices">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${inv.invoiceNumber}');">
									<td>
										${inv.invoiceNumber}
									</td>
									<td>
										<windrp:dateformat value="${inv.invoiceDate}" p="yyyy-MM-dd"/>
									</td>
									<td>
										${inv.partnSold}
									</td>
									<td>
										${inv.partnName}
									</td>
									<td>
										${inv.deliveryNumber}
									</td>
									<td>
										${inv.invoiceLineItem}
									</td>
									<td>
										${inv.materialCode}
									</td>
									<td>
										${inv.proName}
									</td>
									<td>
										${inv.packSize}
									</td>
									<td>
										${inv.batchNumber}
									</td>
									<td>
										${inv.invoiceQty}
									</td>
									<td>
										${inv.netVal}
									</td>
								</tr>
							</logic:iterate>
						</table>
						</form>
					</div>

				</td>
			</tr>
	</table>
  </body>
</html>
