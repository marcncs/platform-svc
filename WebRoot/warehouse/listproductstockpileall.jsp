
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
		<SCRIPT LANGUAGE="javascript" src="../js/jquery-1.4.2.min.js"></SCRIPT>
		<script language="JavaScript">

var $j = jQuery.noConflict();
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

	var checkid=0;
	var v_wid="";
	var v_bit="";
	var v_productid="";
	var v_batch="";
	function CheckedObj(obj,objid,objwid,objbit,objproductid,objbatch){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++){
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 v_wid=objwid;
	 v_bit=objbit;
	 v_productid=objproductid;
	 v_batch=objbatch;
	  pdmenu = getCookie("stockpile");
	 switch(pdmenu){
	 	case "1":WarehouseBit(); break;
	 	case "2":Detail(); break;
		default:WarehouseBit();
	 }
	}
	
	function WarehouseBit(){
		setCookie("stockpile","1");
		if(checkid!=""){
			document.all.phonebook.src="stockStatAction.do?WarehouseID="+v_wid+"&ProductID="+v_productid+"&Batch="+v_batch;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		setCookie("stockpile","2");
		if(checkid!=""){
			document.all.phonebook.src="listProductStockpileIdcodeAction.do?WarehouseID="+v_wid+"&ProductID="+v_productid+"&Batch="+v_batch;
			//document.all.phonebook.src="listProductStockpileIdcodeAction.do";
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function Adjust(){
		if(checkid>0){
		window.open("toAdjustStockAction.do?PID="+checkid,"newwindow","height=250,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
		
	}
	
	function ClearPrepare(){
	if(window.confirm("您确认清除吗？该动作执行后不能还原!")){
			window.open("../warehouse/coerceClearPrepareAction.do","newwindow",		"height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
	}
	
	function UpdPrepareout(){
		if(checkid>0){
		window.open("toUpdProductStockpileAction.do?ID="+checkid,"newwindow","height=250,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function viewproduct(id){
		window.open("viewProductAction.do?ID="+id,"newwindow","height=250,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");		
	}
	
	function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.search.ProductID.value=p.id;
	document.search.ProductName.value=p.productname;
	}
	
	function excput(){
		search.target="";
		search.action="../warehouse/excPutProductStockpileAllAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listProductStockpileAllAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printProductStockpileAllAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listProductStockpileAllAction.do";
	}
	
	function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.MakeOrganID.value=p.id;
	document.search.oname.value=p.organname;
	document.search.WarehouseID.value="";
	document.search.wname.value="";
	}
	
	function ExcelInput(){
		popWin("../warehouse/toProductStockPileImportAction.do",500,250);
	}
	function Verify(){
		var ids = "";
		$j("#listform input[type=checkbox]").not($j("#checkAll")).each(function(i){
			if(this.checked == true){
				ids = "," + this.value + ids;
			}
		}); 
		if(ids.length > 0){
			ids = ids.substring(1,ids.length);
		}
		if(ids.length <= 0){
			alert("请选择你要操作的记录!");
		}else{
			popWin("../warehouse/toProductStockPileVerifyAction.do?ids="+ids,500,250);
		}
	}

	function SelectOrderColumn(){
		//popWin("../sys/toSelectOrderColumnAction.do?param="+$F('ordercolumn'),800,500);
		popWin("../sys/toSelectOrderColumnAction.do",650,450);
	}

	function setOrderColumn(sql,name){
		document.search.orderbySql.value=sql;
		document.search.orderbySqlShowName.value=name;
	}

	function checkboxAll(){ 
		if($j("#checkAll").attr('checked') == true){
			$j("#listform input[type=checkbox]").attr('checked','checked');
		}else{
			$j("#listform input[type=checkbox]").removeAttr('checked');
		}
		 
	}
	
	function changeCheckAll(){
		var allCheckFlag = true;
		$j("#listform").find("input[type=checkbox]").each(function(i){
			if(this.name == "pid" && this.checked == false){
				  allCheckFlag = false;
			}
		}); 
		if(allCheckFlag == true){
			$j("#checkAll").attr('checked','checked');
		}else{
			$j("#checkAll").removeAttr('checked');
		}
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
								<td width="772">
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="search" method="post"
							action="listProductStockpileAllAction.do">
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
										批号：
									</td>
									<td width="10%">
										<input name="Batch" type="text" id="Batch" value="${Batch}"
											maxlength="32">
									</td>
									<td align="right">
										关键字：
									</td>
									<td>
										<input name="KeyWord" type="text" id="KeyWord"
											value="${KeyWord}" maxlength="60">
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
								<%--
								<td width="50" align="center">
									<a href="javascript:ExcelInput();"><img
											src="../images/CN/import.gif" width="16" height="16"
											border="0" align="absmiddle"> 导入</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50" align="center">
									<a href="javascript:Verify();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle"> 检验</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>--%>
								<td class="SeparatePage">
									<pages:pager
										action="../warehouse/listProductStockpileAllAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<FORM METHOD="POST" name="listform" ACTION="" id="listform">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">

								<tr align="center" class="title-top">
									<%--<td width="3px" class="sorttable_nosort">
										<input type="checkbox" id="checkAll" onclick="checkboxAll()" />
									</td>
									--%><td width="6%">
										仓库编号
									</td>
									<td width="15%">
										仓库名称
									</td>
									<td width="5%">
										产品类别
									</td>
									<td width="6%">
										物料号
									</td>
									<td width="15%">
										产品名称
									</td>
									<td width="6%">
										规格
									</td>
									<td width="6%">
										规格明细
									</td>
									<td width="8%">
										批号
									</td>
									<td width="3%">
										单位
									</td>
									<td width="8%">
										实际库存
									</td>
									<td width="8%">
										生产日期
									</td>
									<td width="8%">
										过期日期
									</td>
									<td width="25%">
										备注
									</td>
								</tr>
								<logic:iterate id="p" name="alp">
									<tr class="table-back-colorbar"
										onClick="CheckedObj(this,${p.id},'${p.warehouseid}','${p.warehousebit}','${p.productid}','${p.batch}');"
										style="color: ${p.tagColor};">
										<%--<td width="3px">
											<input type="checkbox" value="${p.id}" name="pid"
												onclick="changeCheckAll();">
										</td>
										--%><td>
											${p.warehouseid}
										</td>
										<td>
											${p.warehourseidname}
										</td>
										<td>
											${p.sortName}
										</td>
										<td>
											${p.nccode}
										</td>
										<td>
											${p.psproductname}
										</td>
										<td>
											${p.psspecmode}
										</td>
										<td>
											${p.packSizeName}
										</td>
										<td>
											${p.batch}
										</td>
										<td>
											<windrp:getname key='CountUnit' value='${p.countunit}' p='d' />
										</td>
										<td>
											<windrp:format value='${p.allstockpile}' p="##0.000"/>
										</td>
										<td>
											<windrp:dateformat  p='yyyy-MM-dd' value='${p.productionDate}'  /> 
										</td>
										<td>
											<windrp:dateformat  p='yyyy-MM-dd' value='${p.expiryDate}'  /> 
										</td>
										<td>
											${p.remark}
										</td>
									</tr>
								</logic:iterate>
							</table>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr class="back-gray-light">
									<td width="6%" align="right">
										全部合计：
									</td>
									<td width="59%">
										&nbsp;
									</td>
									<td width="20%">
										<windrp:format p="###,##0.00" value="${totalQuanity}" />
									</td>
									<td width="8%">
										&nbsp;
									</td>
								</tr>
							</table>

							<br>
							<div style="width: 100%">
								<div id="tabs1">
									<ul>
										<li>
											<a href="javascript:WarehouseBit();"><span>仓位</span>
											</a>
										</li>
										<li>
											<a href="javascript:Detail();"><span>条码</span>
											</a>
										</li>
									</ul>
								</div>
								<div>
									<IFRAME id="phonebook"
										style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
										name="submsg" src="../sys/remind.htm" frameBorder="0"
										scrolling="no" onload="setIframeHeight(this);"></IFRAME>
								</div>
							</div>
						</form>
					</div>

				</td>
			</tr>
		</table>

		<form name="excputform" method="post"
			action="printProductStockpileAllAction.do">
			<input type="hidden" name="KeyWordLeft" id="KeyWordLeft"
				value="${KeyWordLeft}">
			<input type="hidden" name="KeyWord" id="KeyWord" value="${KeyWord}">
			<input type="hidden" name="WarehouseID" id="WarehouseID"
				value="${WarehouseID}">
			<input type="hidden" name="Batch" id="Batch" value="${Batch}">
			<input type="hidden" name="ProductID" id="ProductID"
				value="${ProductID}">
			<input type="hidden" name="ProductName" id="ProductName"
				value="${ProductName}">
			<input type="hidden" name="MakeOrganID" id="MakeOrganID"
				value="${MakeOrganID}">
		</form>
	</body>
</html>
