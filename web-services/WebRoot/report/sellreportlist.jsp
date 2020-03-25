<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/showbill.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/producttype.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
var checkid=0;
function CheckedObj(obj,objid){

	for(i=0; i<obj.parentNode.childNodes.length; i++)
	{
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
	}
 
	obj.className="event";
	checkid=objid;
}


function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.searchform.ProductID.value=p.id;
	document.searchform.ProductName.value=p.productname;
	}
function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.searchform.MakeOrganID.value=p.id;
	document.searchform.oname.value=p.organname;
	
	document.searchform.WarehouseID.value="";
	document.searchform.wname.value="";
	}
	function SelectOrgan2(){
			var oid = document.searchform.MakeOrganID.value;
	if(oid==""){
		alert("请选择制单机构!");
		return;
	}
		var p=showModalDialog("../common/selectChildOrganAction.do?OID="+oid,null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.searchform.ReceiveOrganID.value=p.id;
				document.searchform.oname2.value=p.organname;
		}
function excput(){
	excputform.submit();
}
function print(){
	excputform.target="_blank";
	excputform.action="../report/printStockAlterMoveDetailAction.do";
	excputform.submit();
	excputform.target="";
	excputform.action="../report/excPutStockAlterMoveDetailAction.do";
}

var win=null;
function doRemark(){
	if(checkid!=""){
		popWin("../warehouse/toRemarkTakeTicketAction.do?id="+checkid,600,300);
		//win=window.open("../warehouse/toRemarkTakeTicketAction.do?id="+checkid,"","height=300,width=600,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}else{
		alert("请选择你要操作的记录!");
	}
}

function updateRemark(){
	if(checkid!=""){
		popWin("../report/toUpdateAlarmByRemarkAction.do?id="+checkid,600,300);
	}else{
		alert("请选择你要操作的记录!");
	}
}
function SelectSingleProductName(){
	var p=showModalDialog("../common/selectSingleProductNameAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.searchform.ProductName.value=p.productname;
	document.searchform.packSizeName.value="";
	document.searchform.packsizename.value="";
	}

this.onload =function onLoadDivHeight(){
	document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px";
	}
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%"
			bordercolor="#BFC0C1">
			<tr>
				<td valign="top">
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
						<form name="searchform" method="post" onSubmit="showloading();"
							action="sellReportAction.do">
							<input type="hidden" name="dataPage" value="${dataPage}" />
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td align="right">
										省份：
									</td>
									<td>
										<select name="province" id="province" onChange="getResult('province','city');"
											onkeydown="if(event.keyCode==13)event.keyCode=9 ">
											<option value="">
												-选择-
											</option>
											<logic:iterate id="c" name="cals">
												<option value="${c.id}" ${c.id==province?"selected":""}>
													${c.areaname}
												</option>
											</logic:iterate>
										</select>
									</td>
									<td align="right">
										机构：
									</td>
									<td>
										<input name="MakeOrganID" type="hidden" id="MakeOrganID"
											value="${MakeOrganID}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td align="right">
										仓库：
									</td>
									<td>
										<input type="hidden" name="WarehouseID" id="WarehouseID"
											value="${WarehouseID}">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'WarehouseID',$F('MakeOrganID'),'rw')"
											value="${wname}" readonly>
									</td>
								</tr>
								<tr class="table-back">
									<td align="right">
										产品：
									</td>
									<td>
										<input name="ProductID" type="hidden" id="ProductID"
											value="${ProductID}">
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
										日期：
									</td>
									<td>
										<input name="beginDate" type="text" id="beginDate" size="10"
											value="${beginDate}" onFocus="javascript:selectDate(this)" readonly>
										-
										<input name="endDate" type="text" id="endDate" size="10"
											value="${endDate}" onFocus="javascript:selectDate(this)" readonly>
									</td>
								</tr>
								<tr class="table-back">
									<td align="right">
										单位：
									</td>
									<td>
										<input type="radio" name="countByUnit" value="" <c:if test="${countByUnit eq '' or countByUnit eq null}">checked</c:if>>件</input>
										<input type="radio" name="countByUnit" value="true" <c:if test="${countByUnit eq 'true'}">checked</c:if>>升,千克</input>
									</td>
									<td colspan="3"></td>
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
							<ws:hasAuth operationName="/report/excputSellReportAction.do">
								<td width="50">
									<a href="javascript:excput();"><img
											src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;导出</a>
								</td>
								</ws:hasAuth>
								</c:if>
								<%--<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="80">
									<a href="javascript:updateRemark();"><img
											src="../images/CN/update.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;添加备注</a>
								</td>
								
								--%><td class="SeparatePage">
									<c:if test="${prompt=='view'}">
										<pages:pager action="../report/sellReportAction.do"
											onsubmit="showloading()" />
									</c:if>
								</td>
							</tr>
						</table>
					</div>
					<c:if test="${prompt=='view'}">
						<div id="abc" style="overflow: auto; height: 81%; width: 99.9%;">
							<table width="100%" border="0" cellpadding="0" cellspacing="1"
								class="sortable">
								<tr align="center" class="title-top-lock">
									<%--<td>
										大区
									</td>
									--%><td>
										省份
									</td>
									<td>
										机构代码
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
										产品名称
									</td>
									<td>
										产品英文
									</td>
									<td>
										物料号
									</td>
									<td>
										物料中文名
									</td>
									<td>
										物料英文名
									</td>
									<td>
										规格
									</td>
									<td>
										规格英文
									</td>
									<td>
										报告日期
									</td>
									<td>
										销售数量
									</td>
									<td>
										单位
									</td>
									<td>
										单据编号
									</td>
									<td>
										单据日期
									</td>
								</tr>
								<c:forEach items="${alp}" var="d">
									<tr class="table-back-colorbar"
										onClick="CheckedObj(this,'${d.organid}');">
										<%--<td>
											${d.area }
										</td>
										--%><td>
											${d.province }
										</td>
										<td>
											${d.organid }
										</td>
										<td>
											${d.organname }
										</td>
										<td>
											<windrp:getname key="warehouse" value="${d.warehouseid }" p="d" />
										</td>
										<td>
											${d.productsort }
										</td>
										<td>
											${d.productname }
										</td>
										<td>
											${d.productNameen }
										</td>
										<td>
											${d.mcode }
										</td>
										<td>
											${d.mcodechinesename }
										</td>
										<td>
											${d.mcodeenglishname }
										</td>
										<td>
											${d.specmode }
										</td>
										<td>
											${d.packSizeNameEn }
										</td>
										<td>
											<windrp:dateformat value="${d.reportdate }" p='yyyy-MM-dd' />
										</td>
										<td>
											${d.inventorypile }
										</td>
										<td>
											<windrp:getname key='CountUnit' value='${d.countunit}' p='d' />
										</td>
										<td>
											${d.billno }
										</td>
										<td>
											<windrp:dateformat value="${d.billdate }" p='yyyy-MM-dd' />
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</c:if>
				</td>
			</tr>
		</table>
<form  name="excputform" method="post" action="excputSellReportAction.do">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="oname" id ="oname" value="${oname}">
<input type="hidden" name="WarehouseID" id ="WarehouseID" value="${WarehouseID }">
<input type="hidden" name="wname" id ="wname" value="${wname }">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName }">
<input type="hidden" name="packSizeName" id ="packSizeName" value="${packSizeName }">
<input type="hidden" name="beginDate" id ="beginDate" value="${beginDate }">
<input type="hidden" name="endDate" id ="endDate" value="${endDate }">
<input type="hidden" name="province" id ="province" value="${province}">
<input type="hidden" name="countByUnit" id ="countByUnit" value="${countByUnit}">
<input type="hidden" name="dataPage" id ="dataPage" value="${dataPage}">
</form>
	</body>
</html>
