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
			excputform.action = "../report/excputFIFOAlertReportAction.do";
			excputform.submit();
		}
		this.onload = function abc(){
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
						<form name="search" method="post"
							action="fifoAlertReportAction.do" onsubmit="return formChk();">
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
											value="${oname2}" readonly>
										<a href="javascript:SelectOrgan2();"><img
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
										批次：
									</td>
									<td>
									<input name="batch" type="text" id="batch" value="${batch}">
									</td>
									<td>
									</td>
								</tr>
								<tr class="table-back">
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
										单位：
									</td>
									<td>
										<input type="radio" name="countByUnit" value="" <c:if test="${countByUnit eq '' or countByUnit eq null}">checked</c:if>>件</input>
										<input type="radio" name="countByUnit" value="true" <c:if test="${countByUnit eq 'true'}">checked</c:if>>升,千克</input>
									</td>
									<td align="right">
										<%--有效期(天)： --%>
									</td>
									<td>
										<%--<input name="expiryDate" type="text" id="expiryDate" onkeyup="checknum(this);" value="${expiryDate}"> --%>
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
								<ws:hasAuth operationName="/report/excputFIFOAlertReportAction.do">
									<td width="50">
										<a href="javascript:OutPut();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
								</ws:hasAuth>
								</c:if>
									<td class="SeparatePage">
										<pages:pager action="../report/fifoAlertReportAction.do" onsubmit="formChk()"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; overflow-x: auto; height: 600px;width:100%;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table class="sortable" width="200%" border="0" cellpadding="0"
								cellspacing="1">

								<tr align="center" class="title-top">
										<td>
										 区
										</td>
										<td>
										  省份
										</td>
										<td>
										  机构代码
										</td>
										<td>
										  机构名称
										</td>
										<td>
										  仓库
										</td>
										<td>
										  发货单号
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
										  产品名称中文
										</td>
										<td>
										  产品名称英文
										</td>
										<td>规格英文 </td>
										<td>单位</td>
										<td>发货数量 </td>
										<td>发货最早批次 </td>
										<td>生产日期 </td>
										<td>过期日期 </td>
										<td>库存旧货数量 </td>
										<td>库存最早批次 </td>
										<td>生产日期 </td>
										<td>过期日期 </td>
										<td>差异天数 </td>
									</tr>
								<logic:iterate id="rs" name="rls">
									<tr align="left" class="table-back-colorbar">
										<TD align="center">
											${rs.regionName}
										</TD>
										<TD align="center">
											${rs.province}
										</TD>
										<TD align="center">
											${rs.oecode}
										</TD>
										<TD align="center">
											${rs.organName}
										</TD>
										<TD align="center">
											${rs.warehouseName}
										</TD>
										<TD align="center">
											${rs.billNo}
										</TD>
										<TD align="center">
											${rs.mCode}
										</TD>
										<TD align="center">
											${rs.matericalChDes}
										</TD>
										<TD align="center">
											${rs.matericalEnDes}
										</TD>
										<TD align="center">
											${rs.productName}
										</TD>
										<TD align="center">
											${rs.productNameen}
										</TD>
										<TD align="center">
											${rs.packSizeNameEn}
										</TD>
										<td>
											<windrp:getname key='CountUnit' value='${rs.unitId}' p='d' />
										</td>
										<TD align="center">
											${rs.shipQuantity}
										</TD>
										<TD align="center">
											${rs.shipBatch}
										</TD>
										<TD align="center">
											<windrp:dateformat value="${rs.shipProductionDate}" p="yyyy-MM-dd" />
										</TD>
										<TD align="center">
											<windrp:dateformat value="${rs.shipExpiryDate}" p="yyyy-MM-dd" />
										</TD>
										<TD align="center">
											${rs.stockPile}
										</TD>
										<TD align="center">
											${rs.stockBatch}
										</TD>
										<TD align="center">
											<windrp:dateformat value="${rs.stockProductionDate}" p="yyyy-MM-dd" />
										</TD>
										<TD align="center">
											<windrp:dateformat value="${rs.stockExpiryDate}" p="yyyy-MM-dd" />
										</TD>
										<TD align="center">
											${rs.differentDays}
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
<form  name="excputform" method="post" action="excputFIFOAlertReportAction.do">
<input type="hidden" name="organId" id ="organId" value="${organId}">
<input type="hidden" name="oname2" id ="oname2" value="${oname2}">
<input type="hidden" name="warehouseId" id ="warehouseId" value="${warehouseId }">
<input type="hidden" name="wname" id ="wname" value="${wname }">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName }">
<input type="hidden" name="packSizeName" id ="packSizeName" value="${packSizeName }">
<input type="hidden" name="beginDate" id ="beginDate" value="${beginDate }">
<input type="hidden" name="endDate" id ="endDate" value="${endDate }">
<input type="hidden" name="region" id ="region" value="${region}">
<input type="hidden" name="countByUnit" id ="countByUnit" value="${countByUnit}">
<%-- <input type="hidden" name="expiryDate" id ="expiryDate" value="${expiryDate}">--%>
<input type="hidden" name="batch" id ="batch" value="${batch}">
<input type="hidden" name="queryFlag" id ="queryFlag" value="${queryFlag}">
</form>
	</body>
</html>
