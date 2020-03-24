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
	excputform.action = "../report/excPutSalesConsumeReportAction.do";
	excputform.submit();
}
function addNew(type){
	popWin("../report/toUploadSalesConsumHistoryAction.do",600,250);
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
							action="../report/listSalesConsumHistoryAction.do"
							onSubmit="return formChk();">
							<input type="hidden" name="queryFlag" id="queryFlag"
								value="${queryFlag }" />
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<%--<td align="right">
										区域：
									</td>
									<td>
									
										<select name="region">
											<option value="">
												-请选择-
											</option>
											<logic:iterate id="r" name="regions">
												<option value="${r.regioncode}" ${r.regioncode==region?"selected":""}>
													${r.sortname}
												</option>
											</logic:iterate>
										</select>
									</td> --%>
									<td align="right">
										机构：
									</td>
									<td>
										<input name="organId" type="hidden" id="organId" value="${organId}">
										<input name="organName" type="text" id="organName" size="30"
											value="${organName}" readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td align="right">
										仓库：
									</td>
									<td>
										<input type="hidden" name="warehouseId" id="warehouseId"
											value="${warehouseId}">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'warehouseId',$F('organId'),'rw')"
											value="${wname}" readonly>
									</td>
									<td align="right">
										日期：
									</td>
									<td>
										<input name="beginDate" type="text" id="beginDate" size="10"
											value="${beginDate}" onFocus="javascript:selectDate(this)" readonly>
										-
										<input name="endDate" type="text" id="endDate" size="10"
											value="${endDate}" onFocus="javascript:selectDate(this)" readonly>
									</td>
									<td></td>
								</tr>
								<tr class="table-back">
									<td align="right">
										产品名称：
									</td>
									<td>
										<input id="ProductName" name="ProductName" value="${ProductName}"
											readonly>
										<a href="javascript:SelectSingleProductName();"><img
												src="../images/CN/find.gif" align="absmiddle" border="0"> </a>
									</td>
									<td align="right">
										规格：
									</td>
									<td>
										<input type="hidden" name="packSizeName" id="packSizeName"
											value="${packSizeName}">
										<input type="text" name="packsizename" id="packsizename"
											value="${packsizename}"
											onClick="selectDUW(this,'packSizeName',$F('ProductName'),'psn','')"
											readonly>
									</td>
									<td align="right">
										单位：
									</td>
									<td>

										<input type="radio" name="countByUnit" value="" <c:if test="${countByUnit eq '' or countByUnit eq null}">checked</c:if>>件</input>
										<input type="radio" name="countByUnit" value="true" <c:if test="${countByUnit eq 'true'}">checked</c:if>>升,千克</input>

									</td>

									<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>
								<%-- <tr class="table-back">
									<td align="right">
									</td>
									<td>
									</td>
									<td colspan="4">
									</td>
									<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>--%>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
							<ws:hasAuth operationName="/report/toUploadSalesConsumHistoryAction.do">
								<td width="80"><a href="javascript:addNew(1);"><img	src="../images/CN/upload.gif" width="16" height="16"	border="0" align="absmiddle">&nbsp;上传</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../report/listSalesConsumHistoryAction.do"
										onsubmit="formChk()" />
								</td>
							</tr>
						</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top-lock">
								<%--
								<td>
									大区
								</td>
								<td>
									省份
								</td> --%>
								<td>
									月份
								</td>
								<td>
									机构代码
								</td>
								<td>
									机构内部编码
								</td>
								<td>
									机构名称
								</td>
								<%--
								<td>
									产品类别
								</td>
								 --%>
								<td>
									产品名称
								</td>
								<td>
									产品英文
								</td>
								<td>
									物料号
								</td>
								<td>
									物料中文
								</td>
								<td>
									物料英文
								</td>
								<td>
									规格
								</td>
								<td>
									规格英文
								</td>
								<td>
									单位
								</td>
								<td>
									销售数量
								</td>
								<td>
									销售金额
								</td>
								<td>
									消耗数量
								</td>
								<td>
									其他消耗数量
								</td>
								<td>
									单价
								</td>
							</tr>
							<c:forEach items="${schList}" var="d">
								<tr class="table-back-colorbar" onClick="CheckedObj(this);">
									<%--								
									<td>
										${d.regionName }
									</td>
									<td>
										${d.province }
									</td>--%>
									<td>
										${d.yearmonth }
									</td>
									<td>
										${d.oid }
									</td>
									<td>
										${d.oecode }
									</td>
									<td>
										${d.oname }
									</td>
									<%-- 
									<td>
										${d.psName }
									</td>--%>
									<td>
										${d.pname }
									</td>
									<td>
										${d.pnameen }
									</td>
									<td>
										${d.mcode}
									</td>
									<td>
										${d.mchname }
									</td>
									<td>
										${d.menname }
									</td>
									<td>
										${d.packsizename }
									</td>
									<td>
										${d.packsizenameen }
									</td>
									<td>
										${d.unit }
									</td>
									<td>
										${d.salesvolume }
									</td>
									<td>
										${d.salesvalue }
									</td>
									<td>
										${d.consumvolume }
									</td>
									<td>
										${d.otherconsum }
									</td>
									<td>
										${d.price }
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
