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
	var p=showModalDialog("../keyretailer/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
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

function OutPut(type) {
	searchform.action = "../keyretailer/exportSBonusReportAction.do?type="+type;
	searchform.submit();
	searchform.action = "../keyretailer/listSBonusReportAction.do";
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
							action="../keyretailer/listSBonusReportAction.do"
							onSubmit="return formChk();">
							<input type="hidden" name="queryFlag" id="queryFlag"
								value="${queryFlag }" />
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
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
									<td></td>
								</tr>
								<tr class="table-back">
									<td align="right">
										年：
									</td>
									<td>
										<input type="text" name="year" id="year"
											value="${year}">
									</td>
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
											value="${spec}">
										<input type="text" name="packsizename" id="packsizename"
											value="${packsizename}"
											onClick="selectDUW(this,'spec',$F('ProductName'),'psn','')"
											readonly>
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
								<ws:hasAuth operationName="/keyretailer/exportSBonusReportAction.do">
									<td width="50">
										<a href="javascript:OutPut(1);"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/keyretailer/exportSBonusReportAction.do">
									<td width="80">
										<a href="javascript:OutPut(2);"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出明细</a>
									</td>
								</ws:hasAuth>
								</c:if>
								<td class="SeparatePage">
									<pages:pager action="../keyretailer/listSBonusReportAction.do"
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
									年度
								</td>
								<td>
									机构名称
								</td>
								<td>
									所属大区
								</td>
								<td>
									所属地区
								</td>
								<td>
									所属小区
								</td>
								<td>
									所属县
								</td>
								<td>
									目标积分
								</td>
								<td>
									YTD积分
								</td>
								<td>
									积分完成进度%
								</td>
								<td>
									是否达标
								</td>
								<td>
									支持度评价
								</td>
								<td>
									最终积分
								</td>
								<td>
									返利确认时间
								</td>
							</tr>
							<c:forEach items="${list}" var="d">
								<tr class="table-back-colorbar" onClick="CheckedObj(this);">
									<td>
										${d.year }
									</td>
									<td>
										${d.organName }
									</td>
									<td>
										${d.bigRegion }
									</td>
									<td>
										${d.middleRegion }
									</td>
									<td>
										${d.smallRegion }
									</td>
									<td>
										${d.areas }
									</td>
									<td>
										<fmt:formatNumber value="${d.targetPoint }" pattern="###,###"></fmt:formatNumber>
									</td>
									<td>
										<fmt:formatNumber value="${d.curPoint }" pattern="###,###"></fmt:formatNumber>
									</td>
									<td>
										${d.completeRate }
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${d.isReached}' p='f' />
									</td>
									<td>
										<c:if test="${d.isSupported != 1 and d.isSupported != 2}">
										未设置
										</c:if>
										<c:if test="${d.isSupported == 1}">
										支持
										</c:if>
										<c:if test="${d.isSupported == 2}">
										不支持
										</c:if>
									</td>
									<td>
										<fmt:formatNumber value="${d.finalPoint }" pattern="###,###"></fmt:formatNumber>
									</td>
									<td>
										${d.confirmDate}
									</td>
								</tr>
							</c:forEach>

						</table>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
