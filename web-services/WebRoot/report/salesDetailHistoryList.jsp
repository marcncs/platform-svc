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
function SelectSingleProductName(){
			var p=showModalDialog("../common/selectSingleProductNameAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
			if(p==undefined){return;}
			document.searchform.ProductName.value=p.productname;
			document.searchform.packSizeName.value="";
			document.searchform.packsizename.value="";
			}
function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
		document.searchform.organId.value=p.id;
		document.searchform.organName.value=p.organname;
		
		document.searchform.warehouseId.value="";
		document.searchform.wname.value="";
	}
function formChk(){
	showloading();
	return true;
}
this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
}

function OutPut() {
	searchform.action = "../report/excputSalesDetailHistoryAction.do";
	searchform.submit();
	searchform.action = "../report/listSalesDetailHistoryAction.do";
}
function ToUpd(){
	popWin("../report/toUpdSalesDetailHistoryAction.do",700,250);
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
							action="../report/listSalesDetailHistoryAction.do" onSubmit="return formChk();">
							<input type="hidden" name="queryFlag" id="queryFlag" value="${queryFlag }"/>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td align="right">
										区域：
									</td>
									<td>
										<select name="region">
										<option value="">
											-请选择-
										</option>
										<logic:iterate id="r" name="regions">
											<option value="${r.regioncode}" ${r.regioncode==region?"selected":""}>${r.sortname}</option>
										</logic:iterate>
										</select>
									</td>
									<td  align="right">
										机构：
									</td>
									<td >
										<input name="organId" type="hidden" id="organId"
											value="${organId}">
										<input name="organName" type="text" id="organName" size="30" value="${organName}"
											readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td  align="right">
										仓库：
									</td>
									<td>
										<input type="hidden" name="warehouseId" id="warehouseId"
											value="${warehouseId}">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'warehouseId',$F('organId'),'rw')"
											value="${wname}" readonly>
									</td>
									<td></td>
								</tr>
								<tr class="table-back">
									<td align="right">
										产品名称：
									</td>
									<td>
										<input id="ProductName" name="ProductName"
											value="${ProductName}" readonly>
										<a href="javascript:SelectSingleProductName();"><img
												src="../images/CN/find.gif" align="absmiddle" border="0">
										</a>
									</td>
									<td align="right">
										规格：
									</td>
									<td>
										<input type="hidden" name="packSizeName" id="packSizeName"
											value="${packSizeName}">
										<input type="text" name="packsizename" id="packsizename" value="${packsizename}"
											onClick="selectDUW(this,'packSizeName',$F('ProductName'),'psn','')" readonly>
									</td>
									<td  align="right">
										日期：
									</td>
									<td>
									<input name="beginDate" type="text" id="beginDate" size="10" value="${beginDate}"
										onFocus="javascript:selectDate(this)" readonly>
									-
									<input name="endDate" type="text" id="endDate" size="10" value="${endDate}"
									onFocus="javascript:selectDate(this)" readonly>
									</td>
									<td>
									</td>
								</tr>
								<tr class="table-back">
									<%--									
									<td align="right">
										机构类型：
									</td>
									<td>
										<windrp:select key="OrganType" name="organType" p="y|f"
											value="${organType}" />
									</td>--%>
									<td align="right">
										单位：
									</td>
									<td>
										<input type="radio" name="countByUnit" value="" <c:if test="${countByUnit eq '' or countByUnit eq null}">checked</c:if>>件</input>
										<input type="radio" name="countByUnit" value="true" <c:if test="${countByUnit eq 'true'}">checked</c:if>>升,千克</input>
									</td>
									<td colspan="4">
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
								<ws:hasAuth operationName="/report/excPutSalesDetailReportAction.do">
									<td width="50">
										<a href="javascript:OutPut();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
								</ws:hasAuth>
								<%--								
								<ws:hasAuth operationName="/report/toUpdSalesDetailHistoryAction.do">
									<td width="50">
										<a href="javascript:ToUpd();"><img
												src="../images/CN/update.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;更新</a>
									</td>
								</ws:hasAuth>--%>
								</c:if>
								<td class="SeparatePage">
									<pages:pager action="../report/listSalesDetailHistoryAction.do" onsubmit="formChk()"/>
								</td>
							</tr>
						</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top-lock">
								<td>
									月份
								</td>
								<td>
									大区
								</td>
								<td>
									省份
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
								<td>
									仓库名称
								</td>
								<td >
									产品类别
								</td>
								<td >
									产品名称
								</td>
								<td >
									产品英文
								</td>
								<td>
									物料号
								</td>
								<td >
									物料中文
								</td>
								<td >
									物料英文
								</td>
								<td >
									规格
								</td>
								<td >
									规格英文
								</td>
								<td >
									批号
								</td>
								<td >
									单位
								</td>
								<td >
									销售数量
								</td>
								<td >
									单据内部编号
								</td>
								<td >
									单据编号
								</td>
								<td >
									销售日期
								</td>
								<td >
									生产日期
								</td>
								<td >
									过期日期
								</td>
							</tr>
							<c:forEach items="${list}" var="d">
								<tr class="table-back-colorbar" onClick="CheckedObj(this);">
									<td>
										${d.yearMonth }
									</td>
									<td>
										${d.regionName }
									</td>
									<td>
										${d.province }
									</td>
									<td>
										${d.organId }
									</td>
									<td>
										${d.oecode }
									</td>
									<td>
										${d.organName }
									</td>
									<td>
										${d.warehouseName }
									</td>
									<td>
										${d.psName }
									</td>
									<td>
										${d.productName }
									</td>
									<td>
										${d.productNameen }
									</td>
									<td>
										${d.mCode }
									</td>
									<td>
										${d.matericalChDes }
									</td>
									<td>
										${d.matericalEnDes }
									</td>
									<td>
										${d.packSizeName }
									</td>
									<td>
										${d.packSizeNameEn }
									</td>
									<td>
										${d.batch }
									</td>
									<td>
										${d.unitName }
									</td>
									<td>
										<fmt:formatNumber value="${d.salesQuantity }" pattern="#0.###"></fmt:formatNumber>
									</td>
									<td>
										${d.nccode }
									</td>
									<td>
										${d.billNo }
									</td>
									<td>
									    <windrp:dateformat value="${d.makeDate }" p="yyyy-MM-dd" />
									</td>
									<td>
										 <windrp:dateformat value="${d.produceDate }" p="yyyy-MM-dd" />
									</td>
									<td>
										 <windrp:dateformat value="${d.expiryDate }" p="yyyy-MM-dd" />
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
