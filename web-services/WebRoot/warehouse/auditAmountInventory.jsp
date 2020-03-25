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
		<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/producttype.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/jquery-1.4.2.min.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
var $j = jQuery.noConflict();


	var checkid=0;
	var v_wid="";
	var v_bit="";
	var v_productid="";
	var v_batch="";
	var v_stockpile;
	function CheckedObj(obj,objid,objwid,objbit,objproductid,objbatch,objstockpile){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++){
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 v_wid=objwid;
	 v_bit=objbit;
	 v_productid=objproductid;
	 v_batch=objbatch;
	 v_stockpile = objstockpile;
	  pdmenu = getCookie("stockpile");
	 switch(pdmenu){
	 	//case "1":WarehouseBit(); break;
	 	//case "2":Detail(); break;
		//default:WarehouseBit();
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
	//var p=showModalDialog("../common/selectWarehouseOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.MakeOrganID.value=p.id;
	document.search.oname.value=p.organname;
	if(p.wid == undefined){
		document.search.WarehouseID.value="";
		document.search.wname.value="";
	}else{
	document.search.WarehouseID.value=p.wid;
	document.search.wname.value=p.wname;
	}
	document.search.submit();
	}
	
	function product(){
	var p=showModalDialog("../warehouse/toSelectProductIdAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	
	document.listform.productid[3].value=p.id;
	document.listform.productname[3].value=p.productname;
	
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
	
	function changeValue(){
		var different = 0.00;
		var stockpile = document.listform.stockpile;
		var realstock = document.listform.realstock;
		var resultstock = document.listform.resultstock;
		if (stockpile.length ){
			for (var m=0; m<stockpile.length; m++){
				resultstock[m].value=realstock[m].value-stockpile[m].value;
			}
		}else{
			resultstock.value=realstock.value-stockpile.value;
		}
		if ( !Validator.Validate(document.listform,2) ){
			return false;
		}
	}
	function check(){
	if ( !Validator.Validate(document.listform,2) ){
			return false;
	}
	var warehouseid = document.getElementById("WarehouseID").value;
	if(warehouseid == ""){
			alert("请选择仓库！！！");
			return false;
	}
	var resultstock = document.listform.resultstock;
	var remark = document.listform.remark;
		if(resultstock.length){
			for(var m=0; m<resultstock.length; m++)
			if(resultstock[m].value != 0  && remark[m].value.trim() == ""){
				alert("存在差异数量，必须填写备注");
				return false;
			}
		
		}
	
		if(document.all("pd").rows.length<2){
			alert("无库存数量无法盘库");
			return false;
		}
	
		
		var productIds = document.listform.productid;
		var batches = document.listform.batch;
		var productNames = document.listform.productname;
		var pAndb = "";
		var alertMsg = "存在重复产品与批号:";
		var hasDuplicate = false;
		if(productIds.length) {
			for(var m=0; m<productIds.length; m++) {
				if(pAndb.indexOf(productIds[m].value+'_'+batches[m].value+'_') != -1) {
					hasDuplicate = true;
					alertMsg = alertMsg + '\n 编号:' + productIds[m].value+' 名称:' + productNames[m].value+' 批次:'+batches[m].value 
				} else {
					pAndb = pAndb + ',' + productIds[m].value+'_'+batches[m].value+'_';
				}
			}
		}
		if(hasDuplicate) {
			alert(alertMsg);
			return false;
		}
		
		if(confirm("是否确定盘库")){
			showloading();
			listform.submit();
			return true;
		}
		return false;
	}
	//判断是否经过查询 
	function checkright(){
		if(document.getElementById("MakeOrganID").value != "" && document.getElementById("wname").value != ""){
			flag = true;
			return true;
		}else{
			alert("请先选择要盘点的机构和仓库！！！");
			return false;
		}	
	}
	function addNew() {
		window
				.open(
						"../warehouse/toAddListProductStockingAction.do",
						"",
						"height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
  function addRow(p){
    var x = document.all("pd").insertRow(pd.rows.length);
 
        //var a=x.insertCell(0);
        var b=x.insertCell(0);
        var c=x.insertCell(1);
        var d=x.insertCell(2);
        var e=x.insertCell(3);
        var f=x.insertCell(4);
		var h=x.insertCell(5);
		var i=x.insertCell(6);
		var j=x.insertCell(7);
		var k=x.insertCell(8);
		var l=x.insertCell(9);

        //a.className = "table-back";
        b.className = "table-back left";
        c.className = "table-back left";
        d.className = "table-back left";
        e.className = "table-back left";
        f.className = "table-back left";
		h.className = "table-back left";
		i.className = "table-back left";
		j.className = "table-back left";
		k.className = "table-back left";
		l.className = "table-back left";
 
        //a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input type=\"hidden\" value='"+p.productid+"' name=\"productid\" id=\"productid\"  readonly >  <label >"+p.productid+"</label> ";
        c.innerHTML="<input type=\"hidden\" value='"+p.nccode+"' name=\"nccode\" id=\"nccode\"  readonly >  <label >"+p.nccode+"</label> ";
		d.innerHTML="<input type=\"hidden\" name=\"productname\" id=\"productname\" value='"+p.productname+"'   >  <label >"+p.productname+"</label>";
		e.innerHTML="<input name=\"specmode\" id=\"specmode\" type=\"hidden\" value="+p.specmode+" readonly />  <label >"+p.specmode+"</label>";
        f.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" value='"+p.unitid+"' > <label>"+p.unitidname+"</label>";
		h.innerHTML="<input name=\"batch\" id=\"batch\" type=\"text\" value=\"\" size=\"10\" dataType=\"Require\"  msg=\"请输入批次号\" />";
		i.innerHTML="<input type=\"hidden\" value=\"0\" name=\"stockpile\" id=\"stockpile\" readonly >  <label >0</label>";
        j.innerHTML="<input type=\"text\" value=\"0\" name=\"realstock\" id=\"realstock\" size=\"10\" dataType=\"Double\" msg=\"请输入数字\" onBlur=\"changeValue();\" />";
        k.innerHTML="<input name=\"resultstock\" id=\"resultstock\" type=\"text\" value=\"0\" size=\"10\" readonly />";
		l.innerHTML="<input name=\"remark\" id=\"remark\" type=\"text\" value=\"\" size=\"55\" maxlength=\"100\" />";
}
	
	function keydown(){
		var e = window.event;
		if(e && e.keyCode == 13){
			changeValue();
		}
	}
	
	function SupperSelect(){
	var oid=document.search.MakeOrganID.value;
	var p = showModalDialog("../common/toSelectOrganProductPriceAction.do?OID="+oid,null,"dialogWidth:21cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if (p==undefined){return;}
	for(var i=0;i<p.length;i++){	
		//if ( isready2('productid', p[i].productid, 'batch', '')){
		//	continue;
		//}
		addRow(p[i]);			
	}
}
	function clearProductList() {
		var rowNum = document.getElementById('pd').rows.length;
		for(var i =0 ; i< rowNum -1 ;i++) {
			document.getElementById('pd').deleteRow(1);
		}
	}

this.onload =function onLoadDivHeight(){ 
	document.getElementById("listdiv").style.height = (document.body.clientHeight - document.getElementById("bodydiv").offsetHeight-20)+"px";
}
</script>

	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									复核
								</td>
							</tr>
						</table>

						<form name="search" method="post"
							action="toAuditAmountInventoryAction.do">
							<input type="hidden" name=aiid id="aiid"
								value="${aiid}">
							<fieldset align="center">
								<legend>
									<table width="50" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td>
												基本信息
											</td>
										</tr>
									</table>
								</legend>

								<table width="100%" border="0" cellpadding="0" cellspacing="0">

									<tr>
										<td width="5%" align="right">
											机构：
										</td>
										<td width="10%">
											<input name="MakeOrganID" type="hidden" id="MakeOrganID"
												value="${MakeOrganID}">
											<input name="oname" type="text" id="oname" size="20" value="${oname}"
												readonly>
											<%--<a href="javascript:SelectOrgan();"><img
													src="../images/CN/find.gif" width="18" height="18" border="0"
													align="absmiddle"> </a>
										--%></td>
										<td width="5%" align="right">
											仓库：
										</td>
										<td width="10%">
											<input type="hidden" name="WarehouseID" id="WarehouseID"
												value="${WarehouseID}">
											<input type="text" name="wname" id="wname"
<%--												onClick="selectDUW(this,'WarehouseID',$F('MakeOrganID'),'rw')"--%>
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
											<input id="ProductName" name="ProductName" value="${ProductName}"
												readonly>
											<a href="javascript:SelectSingleProduct();"><img
													src="../images/CN/find.gif" align="absmiddle" border="0"> </a>
										</td>
									</tr>
									<tr>
										<td class="SeparatePage" colspan="8">
											<input name="Submit" type="image" id="Submit"
												src="../images/CN/search.gif" border="0" title="查询"
												onclick="return checkright();">
										</td>
									</tr>
								</table>
							</fieldset>
						</form>

						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td width="100%">
									<table border="0" cellpadding="0" cellspacing="1">
										<tr align="center" class="back-blue-light2">
											<td id="elect">
												<img src="../images/CN/selectp.gif" border="0"
													style="cursor: pointer" onClick="SupperSelect()">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto;">
						<FORM METHOD="POST" name="listform" onkeydown="keydown();"
							ACTION="auditAmountInventoryAction.do" id="listform">
							<input type="hidden" name="MakeOrganID" id="MakeOrganID"
											value="${MakeOrganID}">
							<input type="hidden" name="WarehouseID" id="WarehouseID"
								value="${WarehouseID}">
							<input type="hidden" name=aiid id="aiid"
								value="${aiid}">
							<table class="sortable" width="100%" border="0" cellpadding="0" id="pd"
								cellspacing="1">
								<tr align="center" class="title-top">
									<!--<td width="6%">
											仓库编号
										</td>
										<td width="15%">
											仓库名称
										</td>
										<td width="10%">
											产品类别
										</td>
										-->
									<td>
										产品编号
									</td>
									<td>
										物料号
									</td>
									<td>
										产品名称
									</td>
									<td>
										规格
									</td>
									<td>
										单位
									</td>
									<td>
										批次
									</td>
									<td>
										库存数量
										<br>
									</td>
									<td>
										盘点数量
									</td>
									<td>
										差异数量
									</td>
									<td width="10%">
										备注
									</td>
								</tr>
								<logic:iterate id="p" name="alp">
									<tr class="table-back-colorbar"
										onClick="CheckedObj(this,${p.warehouseid},'${p.warehouseid}','${p.productid}','${p.batch}','${p.allstockpile}');"
										style="color: ${p.tagColor};">
										<!--<td>
												<input name="warehouseid" id="warehouseid" type="text"
													value="${p.warehouseid}" readonly />

											</td>
											<td>
												<windrp:getname key="warehouse" value="${p.warehouseid}" p="d" />
												${p.warehourseidname}
											</td>
											<td>
												${p.id}
											</td>
											-->
										<td>
											<input name="productid" id="productid" type="hidden"
												value="${p.productid}" readonly />
											<label>
												${p.productid}
											</label>
										</td>
										<td>
											<label>
												${p.nccode}
											</label>
										</td>
										<td>
											<input name="productname" id="productname" type="hidden"
												value="${p.psproductname}" style="width: 200px" readonly />
											<label>
												${p.psproductname}
											</label>
										</td>
										<td>
											<input name="specmode" id="specmode" type="hidden"
												value="${p.specmode}" readonly />
											<label>
												${p.specmode}
											</label>
										</td>
										<td>
											<input name="unitid" id="unitid" type="hidden" value="${p.countunit}"
												readonly />
											<windrp:getname key='CountUnit' value='${p.countunit}' p='d' />
										</td>
										<%--
										<td>
											${p.psspecmode}
										</td> --%>
										<td>
											<input id="batch" name="batch" size="10"
												value="${p.batch}"
												dataType="Require" msg="请输入批次号" type="text"
												>
										</td>
										
										
										<td>
											<input name="stockpile" id="stockpile" type="hidden"
												value="<windrp:format value='${p.stockpile}' />" readonly />
											<label>
												<windrp:format value="${p.stockpile}" />
											</label>

										</td>
										<td>
											<input id="realstock" name="realstock" size="10"
												value="<windrp:format value='${p.stockpile}' />"
												dataType="Double" msg="数量必须是数字" type="text"
												onBlur="changeValue();">
										</td>
										<td>
											<input name="resultstock" id="resultstock" type="text" value="0"
												size="10" readonly />
										</td>
										<td>
											<input name="remark" id="remark" type="text" value="" size="55"
												maxlength="100" />
										</td>
									</tr>
								</logic:iterate>
							</table>
						</form>
						</div>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr>
									<td align="center">
										<input type="button" name="button1" value="确定"
											onclick="return check();">
									</td>
								</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
