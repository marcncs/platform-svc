<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>PD货物签收状态</title>
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
		<%--
			function ToInput(){
			popWin("../warehouse/selectAlterMoveApplyAction.do",1000,650);
		}
		--%>
		
		function ToInput(){
			popWin("../warehouse/toStockAlterMoveImportAction.do",500,250);
		}
		
		function addNew(){
			popWin("../warehouse/toAddStockAlterMoveAction.do",1000,650);
		}

		function Update(){
			if(checkid!=""){
			popWin("../warehouse/toUpdStockAlterMoveAction.do?ID="+checkid,1000,650);
			}else{
				alert("请选择你要操作的记录!");
			}
		}
		
		function StockMoveDetail(){
			if(checkid!=""){
				document.all.submsg.src="stockAlterMoveDetailAction.do?ID="+checkid;
			}else{
				alert("请选择你要操作的记录!");
			}
		}
		

		function excput(){
			search.target="";
			search.action="../warehouse/excPutStockAlterMoveAction.do";
			search.submit();
			search.action="listStockAlterMoveAction.do";
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
				document.search.inOrganId.value=p.id;
				document.search.oname2.value=p.organname;
				document.search.inWarehouseId.value="";
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
			excputform.action = "../report/excputPDProductScanningRatioReportAction.do";
			excputform.submit();
		}

		this.onload =function onLoadDivHeight(){
			document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";

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
						<form name="search" method="post"
							action="pdProductScanningRatioReportAction.do" onsubmit="return formChk();">
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
										出货机构：
									</td>
									<td>
										<input name="inOrganId" type="hidden" id="inOrganId"
											value="${inOrganId}">
										<input name="oname2" type="text" id="oname2" size="30"
											value="${oname2}" readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle"> </a>
									</td>
									<td align="right">
										出货仓库：
									</td>
									<td>
										<input type="hidden" name="inWarehouseId"
											value="${inWarehouseId}" id="inWarehouseId">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'inWarehouseId',$F('inOrganId'),'rw','')"
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
										统计方式：
									</td>
									<td>
									<select name="countMode" id="countMode">
										<option value="0" <c:if test="${countMode eq 0}">selected="selected"</c:if>>按单据</option>
										<option value="1" <c:if test="${countMode eq 1}">selected="selected"</c:if>>按机构</option>
										<option value="2" <c:if test="${countMode eq 2}">selected="selected"</c:if>>按仓库</option>
									</select>
<%--									<windrp:select key="ScanRatioCountMode" name="countMode" p="y|f"--%>
<%--											value="${countMode}" />--%>
									</td>
									<td>
									</td>
								</tr>
								<tr class="table-back">
									<td align="right">
										单据类型：
									</td>
									<td>
										<windrp:select key="BSort" name="BSort" value="${BSort}" p="y|f" />
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
							<tbody>
								<tr class="title-func-back">
								<c:if test="${prompt=='view'}">
								<ws:hasAuth operationName="/report/excputPDProductScanningRatioReportAction.do">
									<td width="50">
										<a href="javascript:OutPut();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
								</ws:hasAuth>
								</c:if>
									<td class="SeparatePage">
										<pages:pager action="../report/pdProductScanningRatioReportAction.do" onsubmit="formChk()"/>
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
										<td>
										  产品类别
										</td>
										<td>
										  物料号
										</td>
										<td>
										  产品
										</td>
										<td>
										  产品英文
										</td>
										<td>规格</td>
										<td>规格英文</td>
										<%--<td>批次</td>--%>
										<td>单据编号</td>
										<td>出/入库数量</td>
										<td>码数量</td>
										<td>扫描率</td>
									</tr>
								<logic:iterate id="rs" name="rls">
									<tr align="left" class="table-back-colorbar">
										<TD align="center">
											${rs.regionName}
										</TD>
										<TD align="center">
											${rs.areaName}
										</TD>
										<TD align="center">
											${rs.organId }
										</TD>
										<TD align="center">
											${rs.oecode }
										</TD>
										<TD align="center">
											${rs.organName}
										</TD>
										<TD align="center">
											${rs.warehouseName}
										</TD>
										<TD align="center">
											${rs.sortName}
										</TD>
										<TD align="center">
											${rs.mCode}
										</TD>
										<TD align="center">
											${rs.productName}
										</TD>
										<TD align="center">
											${rs.productNameen}
										</TD>
										<TD align="center">
											${rs.specMode}
										</TD>
										<TD align="center">
											${rs.packSizeNameEn}
										</TD>
										<%--<TD align="center">
											${rs.batch}
										</TD>--%>
										<TD align="center">
											${rs.billNo}
										</TD>
										<TD align="center">
											${rs.ioQuantity}
										</TD>
										<TD align="center">
											${rs.idcodeQuantity}
										</TD>
										<TD align="center">
											${rs.ratio}
										</TD>
									</tr>
								</logic:iterate>
							</table>
						</form>
						<br>
					</div>
				</td>
			</tr>
		</table>
		<form  name="excputform" method="post" action="excputPDProductScanningRatioReportAction.do">
<input type="hidden" name="inOrganId" id ="inOrganId" value="${inOrganId}">
<input type="hidden" name="oname2" id ="oname2" value="${oname2}">
<input type="hidden" name="inWarehouseId" id ="inWarehouseId" value="${inWarehouseId }">
<input type="hidden" name="wname" id ="wname" value="${wname }">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName }">
<input type="hidden" name="packSizeName" id ="packSizeName" value="${packSizeName }">
<input type="hidden" name="beginDate" id ="beginDate" value="${beginDate }">
<input type="hidden" name="endDate" id ="endDate" value="${endDate }">
<input type="hidden" name="region" id ="region" value="${region}">
<input type="hidden" name="countMode" id ="countMode" value="${countMode}">
<input type="hidden" name="queryFlag" id ="queryFlag" value="${queryFlag}">
<input type="hidden" name="BSort" id ="BSort" value="${BSort}">
</form>
	</body>
</html>
