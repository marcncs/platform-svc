<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>销售消耗库存年月报表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript">

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
			document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
		}
		function OutPut() {
			excputform.action = "../report/exportSalesConsumptionInventoryYearMonthlyReportAction.do";
			excputform.submit();
		}
		</script>
	</head>

	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" style="table-layout:fixed;">
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
						<form name="searchform" method="post"
							action="../report/SalesDetailsReportAction.do"
							onSubmit="return formChk();">
							<input type="hidden" name="queryFlag" id="queryFlag"
								value="${queryFlag }" />
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
												<option value="${r.regioncode}" ${r.regioncode==region?"selected":""}>
													${r.sortname}
												</option>
											</logic:iterate>
										</select>
									</td>
									<td align="right">
										机构：
									</td> 
									<td>
										<input name="organId" type="hidden" id="organId"
											value="${organId}">
										<input name="organName" type="text" id="organName" size="30"
											value="${organName}" readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle"> </a>
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
								<ws:hasAuth operationName="/report/exportSalesConsumptionInventoryYearMonthlyReportAction.do">
									<td width="50">
										<a href="javascript:OutPut();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
								</ws:hasAuth>
								</c:if>
								<td class="SeparatePage">
									<pages:pager action="../report/SalesDetailsReportAction.do"
										onsubmit="formChk()" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto;overflow-x: auto; height: 600px; width:100%;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table width="200%" border="0" cellpadding="0" cellspacing="1">
								<tr align="center" class="title-top">
									<td rowspan="3">
										区
										<br>
										Region
									</td>
									<td rowspan="3">
										省份
										<br>
										Province
									</td>
									<td rowspan="3">
										机构代码
										<br>
										SAP Code
									</td>
									<td rowspan="3">
										机构名称
										<br>
										PD Name
									</td>
									<td rowspan="3">
										仓库
										<br>
										Warehouse
									</td>
									<td rowspan="3">
										物料号
										<br>
										Material Code
									</td>
									<td rowspan="3">
										物料中文
										<br>
										Material Description CN
									</td>
									<td rowspan="3">
										物料英文
										<br>
										Material Description EN
									</td>
									<td rowspan="3">
										产品名称中文
										<br>
										Product Name CN
									</td>
									<td rowspan="3">
										产品名称英文
										<br>
										Product Name EN
									</td>
									<td rowspan="3">
										规格英文
										<br>
										Pack Size EN
									</td>
									<td align="center" colspan="8">
										${year}年/YTD
									</td>
	
									<td align="center" colspan="8">
										${yearMonth}月/MTD
									</td>
								</tr>
								<tr align="center" class="title-top">
									<td colspan="4" align="center">
										数量
										<br>
										Volume(KG/L)
									</td>
									<td align="center" colspan="4">
										金额
										<br>
										Value(RMB)
									</td>
									<td colspan="4" align="center">
									          数量
										<br>
										Volume(KG/L)
									</td>
									<td colspan="4" align="center">
										金额
										<br>
										Value(RMB)
									</td>
							    </tr>
								<tr align="center" class="title-top">
								<td>
									销售
									<br/>
									Sales
								</td>
								<td>
									消耗
									<br/>
									Consumption
								</td>
								<td>
									期初库存
									<br/>
									Begin Inventory
								</td>
								<td>
									期末库存
									<br/>
									End Inventory
								</td>
								<td>
									销售
									<br/>
									Sales
								</td>
								<td>
									消耗
									<br/>
									Consumption
								</td>
								<td>
									期初库存
									<br/>
									Begin Inventory
								</td>
								<td>
									期末库存
									<br/>
									End Inventory
								</td>
								<td>
									销售
									<br/>
									Sales
								</td>
								<td>
									消耗
									<br/>
									Consumption
								</td>
								<td>
									期初库存
									<br/>
									Begin Inventory
								</td>
								<td>
									期末库存
									<br/>
									End Inventory
								</td>
								<td>
									销售
									<br/>
									Sales
								</td>
								<td>
									消耗
									<br/>
									Consumption
								</td>
								<td>
									期初库存
									<br/>
									Begin Inventory
									
								</td>
								<td>
									期末库存
									<br/>
									End Inventory
								</td>
							</tr>
							<c:forEach items="${list}" var="d">
								<tr class="table-back-colorbar">
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
										${d.organName }
									</td>
									<td>
										${d.warehouseName }
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
										${d.productName }
									</td>
									<td>
										${d.productNameen }
									</td>
									<td>
										${d.packSizeNameEn }
									</td>
									<td>
										<fmt:formatNumber value="${d.salesVolume}" pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber value="${d.consumptionVolume}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber
											value="${d.yearBeginInventory}"
											pattern="#0.###"></fmt:formatNumber>

									</td>

									<td>
										<fmt:formatNumber value="${d.yearEndInventory}"
											pattern="#0.###"></fmt:formatNumber>

									</td>

									<td>
										<fmt:formatNumber value="${d.salesValue}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber value="${d.consumptionValue}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber
											value="${d.yearBeginInventoryValue}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber value="${d.yearEndInventoryValue}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber value="${d.monthSalesVolume}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber value="${d.monthConsumptionVolume}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber
											value="${d.monthBeginInventory}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber value="${d.monthEndInventory}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber value="${d.monthSalesValue}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber value="${d.monthConsumptionValue}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber
											value="${d.monthBeginInventoryValue}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
									<td>
										<fmt:formatNumber value="${d.monthEndInventoryValue}"
											pattern="#0.###"></fmt:formatNumber>

									</td>
								</tr>
							</c:forEach>
							</table>
						</form>
						<br>
					</div>
				</td>
			</tr>
		</table>
<form  name="excputform" method="post" action="exportSalesConsumptionInventoryMonthlyVolumeReportAction.do">
<input type="hidden" name="organId" id ="organId" value="${organId}">
<input type="hidden" name="organName" id ="organName" value="${organName}">
<input type="hidden" name="warehouseId" id ="warehouseId" value="${warehouseId }">
<input type="hidden" name="wname" id ="wname" value="${wname }">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName }">
<input type="hidden" name="packSizeName" id ="packSizeName" value="${packSizeName }">
<input type="hidden" name="region" id ="region" value="${region}">
<input type="hidden" name="queryFlag" id ="queryFlag" value="${queryFlag}">
<input type="hidden" name="beginDate" id ="beginDate" value="${beginDate}">
<input type="hidden" name="endDate" id ="endDate" value="${endDate}">
</form>
	</body>
</html>
