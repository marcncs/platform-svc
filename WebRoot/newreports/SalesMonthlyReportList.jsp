<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>销售消耗库存月数量报表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript">

		var checkid=0;
		function CheckedObj(obj,objid){
		
		 for(i=0; i<obj.parentNode.childNodes.length; i++)
		 {
			   obj.parentNode.childNodes[i].className="table-back-colorbar";
		 }
		 
		 obj.className="event";
		 checkid=objid;
		//StockMoveDetail();
		}
		function checknum(obj){
			obj.value = obj.value.replace(/[^\d]/g,"");
		}
		function print(){
			search.target="_blank";
			search.action="../warehouse/printListStockAlterMoveAction.do";
			search.submit();
			search.target="";
			search.action="listStockAlterMoveAction.do";
		}
		
		function Del(){
			if(checkid!=""){
			if(window.confirm("您确认要删除编号为：" +checkid+" 的订购单吗？如果删除将永远不能恢复!")){
					popWin2("../warehouse/delStockAlterMoveAction.do?ID="+checkid);
				}
			}else{
			alert("请选择你要操作的记录!");
			}
		}
		
		function SelectOrgan(){
			var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.search.MakeOrganID.value=p.id;
				document.search.oname.value=p.organname;
				clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
		}
		
		function SelectOrgan2(){
			var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.search.organId.value=p.id;
				document.search.oname2.value=p.organname;
				document.search.warehouseId.value="";
				document.search.wname.value="";
		}
		function SelectOutOrgan(){
			var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.search.outOrganId.value=p.id;
				document.search.outOrganName.value=p.organname;
				document.search.outwarehouseid.value="";
				document.search.owname.value="";
		}
		function SelectSingleProduct(){
			var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
			if(p==undefined){return;}
			document.search.productId.value=p.id;
			document.search.ProductName.value=p.productname;
			}
		function formChk(){
		
			//验证日期
			var beginDate = document.search.beginDate.value;
			var endDate = document.search.endDate.value;
			if(beginDate == ""){
				alert("请选择开始日期");
				return false;
			}
			if(endDate == ""){
				alert("请选择结束日期");
				return false;
			}
			if(beginDate >= endDate){
				alert("开始年月必须小于结束年月");
				return false;
			}
		
			showloading();
			return true;
		}
		function SelectSingleProductName(){
			var p=showModalDialog("../common/selectSingleProductNameAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
			if(p==undefined){return;}
			document.search.ProductName.value=p.productname;
			document.search.packSizeName.value="";
			document.search.packsizename.value="";
			}
		function OutPut() {
			excputform.action = "../report/exportSalesInventoryMonthSummaryReport.do";
			excputform.submit();
		}
		
		function checknum(obj){
			obj.value = obj.value.replace(/[^-\d]/g,"");
		}
		this.onload = function abc(){
			document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
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
						<form name="search" method="post"
							action="salesInventoryMonthSummaryReport.do" onsubmit="return formChk();">
							<input type="hidden" name="queryFlag" id="queryFlag" value="${queryFlag }"/>
							<input name="makeOrganID" type="hidden" id="makeOrganID"
								value="${MakeOrganID}">
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
									<td align="right">
										机构：
									</td>
									<td>
										<input name="organId" type="hidden" id="organId"
											value="${organId}">
										<input name="oname2" type="text" id="oname2" size="30"
											value="${oname2}" readonly><a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle"> </a>
									</td>
									<td align="right">
										仓库：
									</td>
									<td>
										<input type="hidden" name="warehouseId"
											value="${warehouseId}" id="warehouseId">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'warehouseId',$F('organId'),'rw','')"
											value="${wname}" readonly>
									</td>
									<td></td>
								</tr>
								<tr class="table-back">
									<td align="right">
										产品名称：
									</td>
									<td>
										<input id="ProductName" name="ProductName" value="${ProductName}" readonly><a href="javascript:SelectSingleProductName();"><img src="../images/CN/find.gif" align="absmiddle" border="0"></a>
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
									<td align="right">
										日期：
									</td>
									<td>
										<input name="beginDate" type="text" id="beginDate" size="10" value="${beginDate}"
										onFocus="javascript:selectDate(this)" readonly>
										-
										<input name="endDate" type="text" id="endDate" size="10" value="${endDate}"
										onFocus="javascript:selectDate(this)" readonly>
									</td>
									<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody>
								<tr class="title-func-back">
								<c:if test="${prompt=='view'}">
								<ws:hasAuth operationName="/report/exportSalesInventoryMonthSummaryReport.do">
									<td width="50">
										<a href="javascript:OutPut();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
								</ws:hasAuth>
								</c:if>
									<td class="SeparatePage">
										<pages:pager action="../report/salesInventoryMonthSummaryReport.do" onsubmit="formChk()"/>
									</td>
								</tr>
							</tbody>
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
										<td rowspan="2">
											区<br>Region
										</td>
										<td rowspan="2">
										  	省份<br>Province
										</td>
										<td rowspan="2">
										  	机构代码<br>SAP Code
										</td>
										<td rowspan="2">
										  	机构名称<br>PD Name
										</td>
										<td rowspan="2">
										  	仓库名称<br>Warehouse Name
										</td>
										<td rowspan="2">
										 	物料号<br>Material Code
										</td>
										<td rowspan="2">
											物料中文<br>Material Description CN
										</td>
										<td rowspan="2">
										 	物料英文<br>Material Description EN
										</td>
										<td rowspan="2">
										 	产品名称中文<br>Product Name CN
										</td>
										<td rowspan="2">
											产品名称英文<br>Product Name EN
										</td>
										<td rowspan="2">
											规格英文<br>Pack Size EN
										</td>
										<td rowspan="2">
											数据类型<br>Data Type
										</td>
										<%--动态添加 START --%>
										<logic:iterate id="rs" name="titleList">
										<td colspan="2">${rs}</td>
										</logic:iterate>
										<%--动态添加 END --%>
									</tr>
									<tr align="center" class="title-top">
										<%--动态添加 START --%>
										<logic:iterate id="rs" name="titleList">
										<td>数量<br/></>Volume (KG/L)</td>
										<td>金额<br/></>Value (RMB)</td>
										</logic:iterate>
										<%--动态添加 END --%>
									</tr>
								<logic:iterate id="rs" name="list">
									<tr align="left" class="table-back-colorbar">
										<TD rowspan="3" align="center">
											${rs.regionName}
										</TD>
										<TD rowspan="3" align="center">
											${rs.province}
										</TD>
										<TD rowspan="3" align="center">
											${rs.oecode}
										</TD>
										<TD rowspan="3" align="center">
											${rs.organName}
										</TD>
										<TD rowspan="3" align="center">
											${rs.warehouseName}
										</TD>
										<TD rowspan="3" align="center">
											${rs.mCode}
										</TD>
										<TD rowspan="3" align="center">
											${rs.matericalChDes}
										</TD>
										<TD rowspan="3" align="center">
											${rs.matericalEnDes}
										</TD>
										<TD rowspan="3" align="center">
											${rs.productName}
										</TD>
										<TD rowspan="3" align="center">
											${rs.productNameen}
										</TD>
										<TD rowspan="3" align="center">
											${rs.packSizeNameEn}
										</TD>
										<TD align="center">
											销售<br>Sales
										</TD>
										<%--
										<logic:iterate id="sci" name="rs.salesConsumptionInventoryBeginList">
										<td>${sci.monthEndInventory}</td>
										<td>${sci.lastYearMonthEndInventory}</td>
										</logic:iterate>
										--%>
										<c:forEach var="sci" items="${rs.monthlyDataList}">
										
										<td><fmt:formatNumber value="${sci.salesVolume}" pattern="#0.###"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${sci.salesValue}" pattern="#0.###"></fmt:formatNumber></td>
										</c:forEach>
										<%--
										<logic:iterate id="sci" name="rs.salesConsumptionInventoryList">
										<td>${sci.salesVolume}</td>
										<td>${sci.comsumptionVolume}</td>
										<td>${sci.monthEndInventory}</td>
										<td>${sci.lastYearSalesVolume}</td>
										<td>${sci.lastYearComsumptionVolume}</td>
										<td>${sci.lastYearMonthEndInventory}</td>
										</logic:iterate>
										
										<c:forEach var="sci" items="${rs.salesConsumptionInventoryList}">
										<td>${sci.salesVolume}</td>
										<td>${sci.comsumptionVolume}</td>
										<td>${sci.monthEndInventory}</td>
										<td>${sci.lastYearSalesVolume}</td>
										<td>${sci.lastYearComsumptionVolume}</td>
										<td>${sci.lastYearMonthEndInventory}</td>
										</c:forEach>
										--%>
									</tr>
									<tr align="left" class="table-back-colorbar">
										<TD align="center">
											消耗<br>Consumption
										</TD>
										<c:forEach var="sci" items="${rs.monthlyDataList}">
										<td><fmt:formatNumber value="${sci.consumptionVolume}" pattern="#0.###"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${sci.consumptionValue}" pattern="#0.###"></fmt:formatNumber></td>
										</c:forEach>
									</tr>
									<tr align="left" class="table-back-colorbar">
										<TD align="center">
											期末库存<br>End Inventory
										</TD>
										<c:forEach var="sci" items="${rs.monthlyDataList}">
										<td><fmt:formatNumber value="${sci.endInventoryVolume}" pattern="#0.###"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${sci.endInventoryValue}" pattern="#0.###"></fmt:formatNumber></td>
										</c:forEach>
									</tr>
								</logic:iterate>
							</table>
						</form>
						<br>
					</div>
				</td>
			</tr>
		</table>
<form  name="excputform" method="post" action="exportSalesConsumptionInventoryMonthlyVolumeReportAction.do">
<input type="hidden" name="organId" id ="organId" value="${organId}">
<input type="hidden" name="oname2" id ="oname2" value="${oname2}">
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
