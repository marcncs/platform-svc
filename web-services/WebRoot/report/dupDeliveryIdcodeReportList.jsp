<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%> 
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/producttype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
		//jQuery解除与其它js库的冲突
		var $j = jQuery.noConflict(true);
function CheckedObj(obj){

	for(i=0; i<obj.parentNode.childNodes.length; i++)
	{
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
	}
 
	obj.className="event";
}
function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.searchform.productId.value=p.id;
	document.searchform.productName.value=p.productname;
	}
function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
		document.searchform.organId.value=p.id;
		document.searchform.organName.value=p.organname;
		
		document.searchform.warehouseId.value="";
		document.searchform.wname.value="";
	}
function SelectSingleProductName(){
			var p=showModalDialog("../common/selectSingleProductNameAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
			if(p==undefined){return;}
			document.searchform.ProductName.value=p.productname;
			document.searchform.packSizeName.value="";
			document.searchform.packsizename.value="";
			}
function formChk(){
	showloading();
	return true;
}
this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
}

function OutPut() {
	excputform.action = "../report/excPutDupDeliveryIdcodeReportAction.do";
	excputform.submit();
}
</script>
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
									<img src="../images/CN/spc.gif" width="10" height="0">
								</td>
								<td>
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="searchform" method="post"
							action="../report/dupDeliveryIdcodeReportAction.do"
							onSubmit="return formChk();">
							<input type="hidden" name="queryFlag" id="queryFlag"
								value="${queryFlag }" />
							<input type="hidden" name="type" id="type"
								value="${type }" />
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td align="right">
										箱码：
									</td>
									<td>
										<input name="idcode" type="text" id="idcode" size="30"
											value="${idcode}">
									</td>
									<td align="right">
										发货机构：
									</td>
									<td>
										<input name="outoid" type="hidden" id="organId" value="${outoid}">
										<input name="organName" type="text" id="organName" size="30"
											value="${organName}" readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td align="right">
										发货仓库：
									</td>
									<td>
										<input type="hidden" name="outwid" id="warehouseId"
											value="${outwid}">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'warehouseId',$F('organId'),'rw')"
											value="${wname}" readonly>
									</td>
									<td></td>
								</tr>
								<tr class="table-back">
									<td align="right">
										发货日期：
									</td>
									<td>
										<input name="beginDate" type="text" id="beginDate" size="10"
											value="${beginDate}" onFocus="javascript:selectDate(this)" readonly>
										-
										<input name="endDate" type="text" id="endDate" size="10"
											value="${endDate}" onFocus="javascript:selectDate(this)" readonly>
									</td>
									<td align="right">
									</td>
									<td>
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
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/report/excPutDupDeliveryIdcodeReportAction.do">
									<td width="50">
										<a href="javascript:OutPut();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../report/dupDeliveryIdcodeReportAction.do"
										onsubmit="formChk()" />
								</td>
							</tr>
						</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top-lock">
								<td>
									箱码
								</td>
								<td>
									物料号
								</td>
								<td>
									产品名称
								</td>
								<td>
									规格
								</td>
								<td>
									批号
								</td>
								<td>
									发货单号
								</td>
								<td>
									SAP单号
								</td>
								<td>
									发货机构
								</td>
								<td>
									发货仓库
								</td>
								<td>
									发货日期
								</td>
								<td>
									收货机构
								</td>
								<td>
									收货仓库
								</td>
								<td>
									收货日期
								</td>
								
							</tr>
							<c:forEach items="${list}" var="d">
								<tr class="table-back-colorbar" onClick="CheckedObj(this);">
									<td>
										${d.idcode }
									</td>
									<td>
										${d.mCode }
									</td>
									<td>
										${d.productName }
									</td>
									<td>
										${d.specMode }
									</td>
									<td>
										${d.batch }
									</td>
									<td>
										${d.billNo }
									</td>
									<td>
										${d.ncCode }
									</td>
									<td>
										${d.outOName }
									</td>
									<td>
										${d.outWName }
									</td>
									<td>
										${d.outDate }
									</td>
									<td>
										${d.inOName }
									</td>
									<td>
										${d.inWName }
									</td>
									<td>
										${d.receiveDate }
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</td>
			</tr>
		</table>
<form  name="excputform" method="post" action="excPutSalesConsumeReportAction.do">
<input type="hidden" name="organId" id ="organId" value="${organId}">
<input type="hidden" name="organName" id ="organName" value="${organName}">
<input type="hidden" name="warehouseId" id ="warehouseId" value="${warehouseId }">
<input type="hidden" name="wname" id ="wname" value="${wname }">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName }">
<input type="hidden" name="packSizeName" id ="packSizeName" value="${packSizeName }">
<input type="hidden" name="packsizename" id ="packsizename" value="${packsizename }">
<input type="hidden" name="beginDate" id ="beginDate" value="${beginDate }">
<input type="hidden" name="endDate" id ="endDate" value="${endDate }">
<input type="hidden" name="region" id ="region" value="${region}">
<input type="hidden" name="countByUnit" id ="countByUnit" value="${countByUnit}">
<input type="hidden" name="queryFlag" id ="queryFlag" value="${queryFlag}">
</form>
	</body>
</html>
