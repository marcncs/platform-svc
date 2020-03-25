<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/showbill.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/producttype.js"> </SCRIPT>
		<script language="JavaScript">
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}


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
	document.search.ProductID.value=p.id;
	document.search.ProductName.value=p.productname;
	}
	
	function ChkForm(){
		var productid = document.search.ProductName;

		if(productid.value==null||productid.value==""){
			alert("请选择产品");
			return false;
		}
		return true;
	}
	
	function excput(){
		search.target="";
		search.action="../warehouse/excPutStockWasteBookAction.do";
		search.submit();
		search.action="../warehouse/listStockWasteBookAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printStockWasteBookAction.do";
		search.submit();
		
	}
	
	function oncheck(){
	/*	var wid = document.getElementById("WarehouseID");
		
		if(wid.value==""){
			alert("请选择仓库!");
			return false;
		}*/
	
		search.target="";
		search.action="../warehouse/listStockWasteBookInitAction.do";
		search.submit();
	}

	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		document.search.MakeOrganID.value=p.id;
		document.search.oname.value=p.organname;
		
		document.search.WarehouseID.value="";
		document.search.wname.value="";
		}

	function SelectOrderColumn(){
		//popWin("../sys/toSelectOrderColumnAction.do?param="+$F('ordercolumn'),800,500);
		popWin("../sys/toSelectOrderColumnAction.do",650,450);
	}

	function setOrderColumn(sql,name){
		document.search.orderbySql.value=sql;
		document.search.orderbySqlShowName.value=name;
	}
	
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="search" method="post"
							action="listStockWasteBookInitAction.do" onSubmit="return oncheck();">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">

									<td width="5%" align="right">
										机构：
									</td>
									<td width="10%">
										<input name="MakeOrganID" type="hidden" id="MakeOrganID"
											value="${MakeOrganID}">
										<input name="oname" type="text" id="oname" size="30"
											value="${oname}" readonly><a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle">
										</a>
									</td>
									<td width="5%" align="right">
										仓库：
									</td>
									<td width="10%">
										<input type="hidden" name="WarehouseID" id="WarehouseID"
											value="${WarehouseID}">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'WarehouseID',$F('MakeOrganID'),'rw')"
											value="${wname}" readonly>
									</td>

									<%--<td width="6%"  align="right">仓库：</td>
          <td width="21%"><windrp:warehouse name="WarehouseID" value="${WarehouseID}" p="n"/></td>
		  --%>

									<%--<td width="8%" align="right">仓位：</td>
            <td width="21%"><input type="hidden" name="WarehouseBit" id="WarehouseBit" value="${WarehouseBit}">
              <input type="text" name="bitname" id="bitname" onClick="selectDUW(this,'WarehouseBit',$F('WarehouseID'),'b')" value="${bitname}" readonly></td>
         --%>

									<td width="5%" align="right">
										产品类别：
									</td>
									<td width="10%">
										<input type="hidden" name="KeyWordLeft" id="KeyWordLeft"
											value="${KeyWordLeft}">
										<input type="text" name="psname" id="psname"
											onFocus="javascript:selectptype(this, 'KeyWordLeft')"
											value="${psname}" readonly>
									</td>
									<td width="5%" align="right">
										产品：
									</td>
									<td width="10%">
										<input name="ProductID" type="hidden" id="ProductID"
											value="${ProductID}">
										<input id="ProductName" name="ProductName"
											value="${ProductName}" readonly>
										<a href="javascript:SelectSingleProduct();"><img
												src="../images/CN/find.gif" align="absmiddle" border="0">
										</a>
									</td>
								</tr>
								<tr class="table-back">
									<td width="5%" align="right">
										日期：
									</td>
									<td width="10%">
										<input id="BeginDate" onFocus="javascript:selectDate(this)"
											size="12" name="BeginDate" value="${BeginDate}" readonly>
										-
										<input id="EndDate" onFocus="javascript:selectDate(this)"
											size="12" name="EndDate" value="${EndDate}" readonly>
									</td>

									<td width="5%" align="right">
										批号：
									</td>
									<td width="10%">
										<input name="Batch" type="text" id="Batch" value="${Batch}"
											maxlength="32">
									</td>
									<td colspan="4"></td>
									</tr>
								<tr class="table-back">
									<td class="SeparatePage" colspan="8">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>

							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="50">
									<a href="javascript:excput();"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;导出</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td class="SeparatePage">
									<pages:pager action="../warehouse/listStockWasteBookInitAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv"
						style="overflow: auto; height: 600px; width: 100%;">
						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							<tr align="center" class="title-top-lock">
								<td width="5%">
									仓库编号
								</td>
								<td width="8%">
									仓库名称
								</td>
								<td width="5%">
									产品类别
								</td>
								<td width="6%">
									物料号
								</td>
								<td width="8%">
									产品名称
								</td>
								<td width="5%">
									产品规格
								</td>
								<td width="5%">
									规格明细
								</td>
								<td width="7%">
									批号
								</td>
								<td width="3%">
									单位
								</td>
								<td width="6%">
									日期
								</td>
								<td width="10%">
									单据号
								</td>
								<td width="5%">
									摘要
								</td>
								<td width="6%">
									期初数量
								</td>
								<td width="6%">
									收入数量
								</td>
								<td width="6%">
									发出数量
								</td>
								<td width="6%">
									结存数量
								</td>
							</tr>
							<c:set var="cyccount" value="0" />
							<c:set var="incount" value="0" />
							<c:set var="outcount" value="0" />
							<c:set var="count" value="0" />
							<c:set var="boxquantity" value="0" />
							<c:set var="scatterQuntity" value="0" />
							<logic:iterate id="s" name="als">
								<tr class="table-back-colorbar" onclick="CheckedObj(this);">
									<td>
										${s.warehouseid}
									</td>
									<td>
										${s.warehouseidname}
									</td>
									<td>
										${s.sortname}
									</td>
									<td>
										${s.nccode}
									</td>
									<td>
										${s.productname}
									</td>
									<td>
										${s.specmode}
									</td>
									<td>
										${s.packSizeName}
									</td>
									<td>
										${s.batch}
									</td>
									<td>
										<windrp:getname key='CountUnit' value='${s.countunit}' p='d'/>
									</td>
									<td>
										${s.recorddate}
									</td>
									<td>
										<a href="javascript:ToBill('${s.billcode}');">${s.billcode}</a>
									</td>
									<td>
										${s.memo}
									</td>
									<td>
										<windrp:format value='${s.cyclefirstquantity}' p="##0.000"/>
									</td>
									<td>
										<windrp:format value='${s.cycleinquantity}' p="##0.000"/>
									</td>
									<td>
										<windrp:format value='${s.cycleoutquantity}' p="##0.000"/>
									</td>
									<td>
										<windrp:format value='${s.cyclebalancequantity}' p="##0.000"/>
									</td>
								</tr>
								<c:set var="cyccount" value="${cyccount+s.cyclefirstquantity}" />
								<c:set var="incount" value="${incount+s.cycleinquantity}" />
								<c:set var="outcount" value="${outcount+s.cycleoutquantity}" />
								<c:set var="count" value="${count+s.cyclebalancequantity}" />
							</logic:iterate>
						</table>
						<table width="100%" cellspacing="1" class="totalsumTable">
							<tr class="back-gray-light">
								<td align="right" width="5%">
									合计：
								</td>
								<td width="63%"></td>
								<td width="6%">
									<windrp:format value='${cyccount}' />
								</td>
								<td width="6%">
									<windrp:format value='${incount}' />
								</td>
								<td width="6%">
									<windrp:format value='${outcount}' />
								</td>
								<td width="6%">
									<windrp:format value='${count}' />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<form name="excputform" method="post"
			action="printStockWasteBookAction.do">
			<input type="hidden" name="BeginDate" id="BeginDate"
				value="${BeginDate}">
			<input type="hidden" name="WarehouseBit" id="WarehouseBit"
				value="${WarehouseBit}">
			<input type="hidden" name="BeginDate" id="BeginDate"
				value="${BeginDate}">
			<input type="hidden" name="EndDate" id="EndDate" value="${EndDate}">
			<input type="hidden" name="ProductID" id="ProductID"
				value="${ProductID}">
			<input type="hidden" name="ProductName" id="ProductName"
				value="${ProductName}">
		</form>

	</body>
</html>
