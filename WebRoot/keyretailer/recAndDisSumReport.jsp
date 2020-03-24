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
	function SelectOrgan(id,name){
		var p=showModalDialog("../keyretailer/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		document.getElementById(id).value=p.id;
		document.getElementById(name).value=p.organname;
	}
function SelectSingleProductName(){
			var p=showModalDialog("../common/selectSingleProductNameAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
			if(p==undefined){return;}
			document.searchform.ProductName.value=p.productname;
			document.searchform.spec.value="";
			document.searchform.packsizename.value="";
			}
function formChk(){
	showloading();
	return true;
}
this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
}

function OutPut(type) {
	searchform.action = "../keyretailer/exportRecAndDisSumReportAction.do?type="+type;
	searchform.submit();
	searchform.action = "../keyretailer/listRecAndDisSumReportAction.do";
}

function selectArea(obj) {
	var regionId = document.getElementById("regionId").value;
	if(regionId=="") {
		alert("请先选择区域");
		return;
	}
	selectDUW(obj,'areaId',regionId,'area');
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
							action="../keyretailer/listRecAndDisSumReportAction.do"
							onSubmit="return formChk();">
							<input type="hidden" name="queryFlag" id="queryFlag"
								value="${queryFlag }" />
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td align="right">
										发货机构：
									</td>
									<td>
										<input name="outOrganId" type="hidden" id="outOrganId" value="${outOrganId}">
										<input name="outOrganName" type="text" id="outOrganName" size="30"
											value="${outOrganName}" readonly>
										<a href="javascript:SelectOrgan('outOrganId','outOrganName');"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td align="right">
										收货机构：
									</td>
									<td>
										<input name="inOrganId" type="hidden" id="inOrganId" value="${inOrganId}">
										<input name="inOrganName" type="text" id="inOrganName" size="30"
											value="${inOrganName}" readonly>
										<a href="javascript:SelectOrgan('inOrganId','inOrganName');"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td align="right"><%--
										报表类型：
									--%></td>
									<td>
										<%--<select name="reportType">
											<c:choose>
											<c:when test="${reportType==1}">
												<option value="1" selected="selected">
											</c:when>
											<c:otherwise>
												<option value="1">
											</c:otherwise>
											</c:choose>
												收货
											</option>
											<c:choose>
											<c:when test="${reportType==2}">
												<option value="2" selected="selected">
											</c:when>
											<c:otherwise>
												<option value="2">
											</c:otherwise>
											</c:choose>
												发货
											</option>
										</select>
									--%></td>
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
										<input type="hidden" name="spec" id="spec"
											value="${packSizeName}">
										<input type="text" name="packsizename" id="packsizename"
											value="${packsizename}"
											onClick="selectDUW(this,'spec',$F('ProductName'),'psn','')"
											readonly>
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

									<td>
									</td>
								</tr>
								<tr class="table-back">
									<td align="right">
										区域：
									</td>
									<td>
									   <input type="hidden" name="regionId" id="regionId" value="${regionId}" >			
									   <windrp:satree id="regionId" name="regionName" value="${regionName}"/>
									</td>
									<td align="right">
										县：
									</td>
									<td>
										<input type="hidden" name="areaId" id="areaId"
											value="${areaId}">
										<input type="text" name="uname" id="uname" value="${uname}"
											onClick="selectArea(this)"
											readonly>
									</td>
									<td colspan="2">
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
							<c:if test="${prompt=='view'}">
								<ws:hasAuth operationName="/keyretailer/exportRecAndDisSumReportAction.do">
									<td width="50">
										<a href="javascript:OutPut(1);"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/keyretailer/exportRecAndDisSumReportAction.do">
									<td width="80">
										<a href="javascript:OutPut(2);"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出明细</a>
									</td>
								</ws:hasAuth>
								</c:if>
								<td class="SeparatePage">
									<pages:pager action="../keyretailer/listRecAndDisSumReportAction.do"
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
									上级机构名称
								</td>
								<td>
									上级机构所属大区
								</td>
								<td>
									上级机构所属地区
								</td>
								<td>
									下级机构名称
								</td>
								<td>
									下级机构所属地区
								</td>
								<td>
									下级机构所属小区
								</td>
								<td>
									产品
								</td>
								<td>
									规格
								</td>
								<td>
									汇总数量
								</td>
								<td>
									单位
								</td>
								<td>
									积分数量
								</td>
							</tr>
							<c:forEach items="${list}" var="d">
								<tr class="table-back-colorbar" onClick="CheckedObj(this);">
									<td>
										${d.outOrganName }
									</td>
									<td>
										${d.bigRegion }
									</td>
									<td>
										${d.outMiddleRegion }
									</td>
									<td>
										${d.inOrganName }
									</td>
									<td>
										${d.inMiddleRegion }
									</td>
									<td>
										${d.smallRegion }
									</td>
									<td>
										${d.productName }
									</td>
									<td>
										${d.spec }
									</td>
									<td>
										<fmt:formatNumber value="${d.amount }" pattern="###,##0.00"></fmt:formatNumber>
									</td>
									<td>
										${d.unitName }
									</td>
									<td>
										<fmt:formatNumber value="${d.bonusPoint }" pattern="###,###"></fmt:formatNumber>
									</td>
									<%--<td>
										<fmt:formatNumber value="${d.finalPoint }" pattern="###,###"></fmt:formatNumber>
									</td>
									<td>
										${d.confirmDate }
									</td>
								--%></tr>
							</c:forEach>

						</table>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
