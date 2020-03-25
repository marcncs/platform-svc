<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type=text/javascript src="../js/function.js"></SCRIPT>
		<script type=text/javascript src="../js/prototype.js"></script>
		<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/showSQ.js"></SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language=javascript>
		
		//jQuery解除与其它js库的冲突
		var $j = jQuery.noConflict(true);
  function addRow(p){
    var x = document.all("dbtable").insertRow(dbtable.rows.length);
 
        var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);
        var d=x.insertCell(3);
        var e=x.insertCell(4);
		var f=x.insertCell(5);
		var g=x.insertCell(6);
		var h=x.insertCell(7);
		var i=x.insertCell(8);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
		f.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";
		i.className = "table-back";i.align="right";
		
        a.innerHTML="<a onclick='delRow(this);'><img src='../images/CN/delete.gif' width='16' border='0'></a>";
        b.innerHTML="<input name='productid' type='text' id='productid'  size='10' value='"+p.productid+"'>";
        c.innerHTML="<input name='nccode' type='text' id='nccode' size='10' readonly value='"+p.nccode+"'>";
		d.innerHTML="<input name='productname' type='text' id='productname' size='60' readonly value='"+p.productname+"'>";
		e.innerHTML="<input name='specmode' type='text' id='specmode' size='15' readonly value='"+p.specmode+"'>";
        f.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" value='' dataType=\"Require\"  msg=\"请输入批号!\"  maxlength=\"10\">";

		var num ="1";
		g.innerHTML="<input name='quantity' type='text' id='quantity' dataType='Integer' msg='必须录入整数' onkeyup=\"checknum(this);changeUnit(this.value,'" + p.unitid + "','" + p.countUnitName + "','" + p.unitList + "',this,true);\"  value='"+num+"' size='8' maxlength='8'>";
		h.innerHTML="<input name='unitid' type='hidden' id='unitid'  value='"+p.unitid+"'>" + p.unitidname;
		i.innerHTML= p.countUnitName;
		//显示计量单位
		changeUnit(1,p.unitid,p.countUnitName,p.unitList,$j(g).children("input"),false);
}
// 用于动态显示计量单位
function changeUnit(count,unitId,countUnitName,unitList,obj,flag){
	//当前单位
	var currUnitId = unitId;
	// 当前数量
	var quantity = count;
	unitList = unitList.replace(/=/g,":");
  	var unitListObjs = eval("(" + unitList + ")");
  	// 将数据转化为Map
  	var unitMap = {};
  	for(var i=0 ; i<unitListObjs.length ; i++){
		var unitObj = unitListObjs[i];
		unitMap[unitObj.unitId] = unitObj.xQuantity;
 	}
 	// 转化为计量单位 (最小包装数量  * 计量单位转化率) 
 	// 0 为最小单位转化率
 	quantity = quantity * unitMap[currUnitId] * unitMap[0];
    //设置箱数
    $j(obj).parent().next().next().html(quantity.toFixed(2) + "&nbsp;" + countUnitName);
}


function checknum(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");
}

function SupperSelect(rowx){
	var oid=document.validateProvide.organid.value;
	var outwid=document.validateProvide.warehouseid.value;
	if(!oid){
		alert("请选择机构！");
		return;
	}
	if(!outwid){
		alert("请选择仓库！");
		return;
	}
	
	
	var p=showModalDialog("../common/toSelectOrganProductPriceAction.do?OID="+oid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	for(var i=0;i<p.length;i++){	
		if ( isready('productid', p[i].productid) ){
			continue;
		}
		addRow(p[i]);	
	}
	
}

function delRow(obj){
	var rowNo = getTableRowNo(obj);
	document.getElementById('dbtable').deleteRow (rowNo);
} 
function getTableRowNo(obj){
	var trSeq = $j(obj).parent().parent().parent().find("tr").index($j(obj).parent().parent()[0]);
	return trSeq;
}

function Check(){
	var pid = document.all("che");
	var checkall = document.all("checkall");
	if (pid==undefined){return;}
	if (pid.length){
		for(i=0;i<pid.length;i++){
				pid[i].checked=checkall.checked;
		}
	}else{
		pid.checked=checkall.checked;
	}		
}	

	function ChkValue(){
		if ( !Validator.Validate(document.validateProvide,2) ){
			return false;
			}		
		showloading();
		/*
        var productid = document.validateProvide.productid;
		var warehouseid = document.validateProvide.warehouseid;
		if(warehouseid.value==null||warehouseid.value==""){
			alert("仓库不能为空");
			return false;
		}
		if ( !Validator.Validate(document.validateProvide,2) ){
		return false;
		}
		if ( productid==undefined){
			alert("请选择产品!");	
			return false;
		}
		//showloading();
		//return true;
		//检查库存
   	   	return checkStock();
	}

	function checkStock(){
		var flag = false;
		// ajax同步检查库存 
   		$j.ajax({
			type:"POST",
			url:"../warehouse/ajaxCheckStockpile.do",
			data:$j("#validateProvide").serialize(),
			dataType:"json",
			async: false,			
			success:function(msg){
				var popMsg = msg.returnMsg + "\r\n是否继续保存?";
				var code = msg.returnCode;
				if(code == 0){
					flag = true;
					showloading();
				}else{
					if(confirm(popMsg)){
						flag = true;
						showloading();
					}else{
						flag = false;
					}
				}
			}
   	   	});
   	   	return flag;*/
   	   	return true;
	}

	function SelectOrgan(){
		var oldOrgan = document.validateProvide.organid.value;
		var from = document.validateProvide;
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		from.organid.value=p.id;
		from.orgname.value=p.organname;
		from.owname.value=p.wname;
		from.warehouseid.value=p.wid;
		if(oldOrgan != p.id) {
			clearProductList()
		}
	}	

	function clearProductList() {
		document.getElementById("outProductId").value="";
		document.getElementById("outProductName").value="";
		document.getElementById("inProductId").value="";
		document.getElementById("inProductName").value="";
		document.getElementById("outMCode").value="";
		document.getElementById("outBatch").value="";
		document.getElementById("outQuantity").value="";
		document.getElementById("inMCode").value="";
		document.getElementById("inBatch").value="";
		document.getElementById("inQuantity").value="";
		document.getElementById("wasteQuantity").value="";
	}

	function SelectOutSingleProduct(pid,pname,pmcode,outUnitId,outUnit,wasteUnit){
		var oldProId = document.getElementById("outProductId").value;
		var wid = document.getElementById("warehouseid").value;
		var p=showModalDialog("../common/selectSingleProductAction.do?wid="+wid+"&isseparate=1",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
		if(p==undefined){return;}
		document.getElementById(pid).value=p.id;
		document.getElementById(pname).value=p.packsizename;
		document.getElementById(pmcode).value=p.nccode;
		document.getElementById(outUnit).innerHTML=p.unitidname;
		document.getElementById(wasteUnit).innerHTML=p.unitidname;
		document.getElementById(outUnitId).value=p.sunit;
		document.getElementById('stockpile').value=p.stockpile;
		document.getElementById('stockpileUnitName').innerHTML=p.unitidname;
		document.getElementById('stockpileSpan').innerHTML=p.stockpile;
		//alert(document.getElementById('boxQuantity').value);
		if(oldProId != p.id) {
			clearInProduct();
		}
	}

	function clearInProduct() {
		document.getElementById("inProductId").value="";
		document.getElementById("inProductName").value="";
		document.getElementById("inMCode").value="";
		document.getElementById("inBatch").value="";
		document.getElementById("inQuantity").value="";
		document.getElementById("wasteQuantity").value="";
		document.getElementById("inUnitName").innerHTML="";

		document.getElementById('defaultUnitQty').value="";
		document.getElementById('defaultUnitQtySpan').innerHTML="";
		document.getElementById('inCountUnitName').innerHTML="";
	}

	function SelectInSingleProduct(pid,pname,pmcode,pidin,pnameout,inUnitId,inUnitName){
		var oldProId = document.getElementById("inProductId").value;
		var proId = document.getElementById(pidin).value;
		var wid = document.getElementById("warehouseid").value;
		if(proId == "" || proId==undefined) {
			alert("请选择分包前产品");
			return;
		}
		var proName = document.getElementById(pnameout).value;
		var p=showModalDialog("../common/selectSingleProductAction.do?proId="+proId+"&wid="+wid+"&isseparate=0&isin=1",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
		if(p==undefined){return;}
		document.getElementById(pid).value=p.id;
		document.getElementById(pname).value=p.packsizename;
		document.getElementById(pmcode).value=p.nccode;
		document.getElementById(inUnitName).innerHTML=p.duname;
		document.getElementById(inUnitId).value=p.du;
		document.getElementById('boxQuantity').value=p.boxquantity;

		document.getElementById('defaultUnitQty').value=p.duqty;
		document.getElementById('defaultUnitQtySpan').innerHTML=p.duqty;
		document.getElementById('inCountUnitName').innerHTML=p.unitidname;
		if(oldProId != p.id) {
			clearProductQuantity();
		}
	}

	function clearProductQuantity() {
		document.getElementById("inQuantity").value="";
		document.getElementById("inBatch").value="";
		document.getElementById("wasteQuantity").value="";
	}

	function checkNumber(obj){
		if(isNaN(obj.value)) {
			obj.value = obj.value.replace(/[^\d.]/g,"");
			obj.value = obj.value.replace(/^\./g,"");
			obj.value = obj.value.replace(/\.{2,}/g,".");
			obj.value = obj.value.replace(".","$#$").replace(/\./g,"","").replace("$#$",".");
		}
	}

	function checkInt(obj){
		obj.value = obj.value.replace(/[^\d]/g,"");
	}

	function checkOutValue(obj) {
		var outValue = obj.value;
		var stockpile = Number(document.getElementById('stockpile').value);
		if(outValue > stockpile) {
			alert("分包数量不能大于库存数量");
			obj.value="";
			return;
		}
		var inValue = document.getElementById('inQuantity').value;
		var boxValue = document.getElementById('boxQuantity').value
		checkValuse(outValue, inValue, boxValue);
	}

	function checkInValue(obj) {
		var inValue = obj.value;
		var outValue = document.getElementById('outQuantity').value;
		var boxValue = document.getElementById('defaultUnitQty').value;
		checkValuse(outValue, inValue, boxValue);
	}

	function checkValuse(outValue, inValue, boxValue) {
		if(outValue!="" && outValue!=undefined && inValue!="" && inValue!=undefined) {
			//alert(outValue + "-" + boxValue +"*" + inValue);
			var wastage = (outValue * 1000 - (boxValue * 1000) * inValue)/1000;
			if( wastage < 0) {
				alert("分包后的数量大于分包前的数量");
				document.getElementById('inQuantity').value = "";
				document.getElementById('wasteQuantity').value = "";
			} else {
				document.getElementById('wasteQuantity').value=wastage;
			}
		}
	}
	
</script>

	</head>
	<body style="overflow: auto">
		<table width="100%" border="1" cellpadding="0" cellspacing="1"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								新增分包管理
							</td>
						</tr>
					</table>
					<form name="validateProvide" method="post" id="validateProvide"
						action="../warehouse/addPackSeparateAction.do"
						onSubmit="return ChkValue();">
						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<input name="isAccount" type="hidden" id="isAccount"
								value="${isAccount}">
							<tr>
								<td>

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
										<table width="100%" border="0" cellpadding="0" cellspacing="1">
											<tr>
												<td width="10%" align="right">
													机构：
												</td>
												<td>
													<input name="organid" type="hidden" id="organid"
														value="${MakeOrganID}">
													<input name="orgname" type="text" id="orgname" size="30"
														dataType="Require" msg="必须录入分包机构!" value="${oname}"
														readonly>
													<a href="javascript:SelectOrgan();"><img
															src="../images/CN/find.gif" width="18" height="18"
															border="0" align="absmiddle"> </a>
													<span class="style1">*</span>
												</td>
												<td width="10%" align="right">
													仓库：
												</td>
												<td width="24%">
													<input type="hidden" name="warehouseid" id="warehouseid"
														value="${WarehouseID}">
													<input type="text" name="owname" id="owname"
														dataType="Require" msg="必须录入分包仓库!"
														onClick="selectDUW(this,'warehouseid',$F('organid'),'rw','')"
														value="${wname}" readonly>
													<span class="STYLE1">*</span>
												</td>
												<td width="9%" align="right">
													分包日期：
												</td>
												<td width="23%">
													<input name="billDate" id="billDate"
														value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"
														onFocus="javascript:selectDate(this)" readonly>
												</td>
											</tr>
											<tr>
												<%--<td width="9%" align="right">相关单据：</td>
	      <td width="25%"><input type="text" name="billno" maxlength="32"></td>
	    --%>
												<td align="right">
													<%--													<input type="checkbox" name="isaccount" checked="checked" />--%>
													&nbsp;
												</td>
												<td>
													<%--													是否记账--%>
												</td>
												<td align="right">
													<%--													出库类别：--%>
												</td>
												<td>
													<%--													<windrp:select key="OtherShipment" name="shipmentsort" p="n|f" />--%>
												</td>
											</tr>
										</table>
									</fieldset>
									<%--
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td width="100%">
												<table border="0" cellpadding="0" cellspacing="1">
													<tr align="center" class="back-blue-light2">
														<td>
															<img src="../images/CN/selectp.gif" border="0"
																style="cursor: pointer" onClick="SupperSelect()">
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>--%>
								</td>
							</tr>
							<tr>
								<td align="right">
									<table width="100%" id="dbtable" border="0" cellpadding="0"
										cellspacing="1">
										<tr id="ccv">
											<td colspan="4">
												<%--											
												<table width="100%" border="0" cellpadding="0"
													cellspacing="1">
													<tr>
														<td align="left">
															<a href="javascript:addtr('linkmantable');"><img
																	src="../images/nolines_plus.gif" width="16" height="18"
																	border="0" title="新增"> </a>
															<a href="javascript:deletetr('linkmantable');"><img
																	src="../images/nolines_minus.gif" width="16"
																	height="18" border="0" title="删除"> </a>
														</td>
													</tr>
												</table>
												--%>
												<table width="100%" border="0" cellpadding="0"
													cellspacing="1" id="linkmantable">
													<tr>
														<td>
															<fieldset align="center">
																<table width="100%" border="0" cellpadding="0"
																	cellspacing="1">
																	<input name="boxQuantity" id="boxQuantity"
																		type="hidden" value="">
																	<input name="outUnitId" id="outUnitId"
																		type="hidden" value="">
																	<input name="inUnitId" id="inUnitId"
																		type="hidden" value="">
																	<tr>
																		<td align="right">
																			分包前产品：
																		</td>
																		<td>
																			<input name="outProductId" type="hidden"
																				id="outProductId" value="${outProductId}">
																			<input name="outProductName" type="text" size="20"
																				id="outProductName" value="${productName}"
																				dataType="Require" msg="必须录入分包前产品!"
																				readonly>
																			<a
																				href="javascript:SelectOutSingleProduct('outProductId', 'outProductName', 'outMCode', 'outUnitId','outUnitName','wasteUnitName');"><img
																					src="../images/CN/find.gif" width="18" height="18"
																					border="0" align="absmiddle"> </a><span class="style1">*</span>
																		</td>
																		<td align="right">
																			分包前物料号：
																		</td>
																		<td width="10%">
<%--																			<span id="outMCode"></span>--%>
																			<input type="text" id="outMCode" name="outMCode"
																				maxlength="30" size="8" readonly/>
																			<%--																			<input name="outMCode" type="text" id="outMCode" size="30" value="${productName}" readonly>--%>
																		</td>
																		<td align="right">
																			批号:
																		</td>
																		<td>
																			<input type="text" id="outBatch" name="outBatch" size="10"
																				maxlength="30" />
																		</td>
																		<td align="right">
																			可用库存:
																		</td>
																		<td>
																			<input type="hidden" id="stockpile" name="stockpile"
																				maxlength="30" />
																			<span id="stockpileSpan"></span><span id="stockpileUnitName"></span>
																		</td>
																		<td align="right">
																			分包数量:
																		</td>
																		<td>
																			<input type="text" id="outQuantity"
																				name="outQuantity" maxlength="30"
																				onkeyup="checkNumber(this)"
																				onblur="checkOutValue(this)"
																				dataType="Require" msg="必须录入分包数量!">
																			<span id="outUnitName"></span><span class="style1">*</span>
																		</td>
																	</tr>
																	<tr>
																		<td align="right">
																			分包后产品：
																		</td>
																		<td>
																			<input name="inProductId" type="hidden"
																				id="inProductId" value="${productId}">
																			<input name="inProductName" type="text" size="20"
																				dataType="Require" msg="必须录入分包后产品!"
																				id="inProductName" value="${productName}"
																				readonly>
																			<a
																				href="javascript:SelectInSingleProduct('inProductId', 'inProductName', 'inMCode','outProductId','outProductName','inUnitId','inUnitName');"><img
																					src="../images/CN/find.gif" width="18" height="18"
																					border="0" align="absmiddle"> </a><span class="style1">*</span>
																		</td>
																		<td align="right">
																			分包后物料号：
																		</td>
																		<td>
<%--																			<span id="inMCode"></span>--%>
																			<input type="text" id="inMCode" name="inMCode"
																				maxlength="30" size="8" readonly/>
																			<%--																			<input name="inMCode" type="text" id="inMCode" size="30" value="${productName}" readonly>--%>
																		</td>
																		<td align="right">
																			批号:
																		</td>
																		<td>
																			<input type="text" id="inBatch" name="inBatch" size="10"
																				maxlength="30" />
																		</td>
																		<td align="right">
																			每件数量:
																		</td>
																		<td>
																			<input type="hidden" id="defaultUnitQty" name="defaultUnitQty" 
																				maxlength="30" />
																			<span id="defaultUnitQtySpan"></span><span id="inCountUnitName"></span>
																		</td>
																		<td align="right">
																			分包后数量:
																		</td>
																		<td>
																			<input type="text" id="inQuantity" name="inQuantity"
																				onkemaxlength="20" onkeyup="checkInt(this)"
																				onblur="checkInValue(this)"
																				dataType="Require" msg="必须录入分包后数量!">
																			<span id="inUnitName"></span><span class="style1">*</span>
																		</td>
																	</tr>
																	<tr>

																		<td align="right">
																		</td>
																		<td>
																		</td>
																		<td align="right">
																		</td>
																		<td>
																		</td>
																		<td align="right">
																		</td>
																		<td>
																		</td>
																		<td>
																		</td>
																		<td>
																		</td>
																		<td align="right">
																			损耗:
																		</td>
																		<td>
																			<input type="text" id="wasteQuantity"
																				name="wasteQuantity" maxlength="20" readonly>
																			<span id="wasteUnitName"></span>
																		</td>
																	</tr>
																</table>
															</fieldset>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<%--										
										<tr align="center" class="title-top">
											<td width="2%"></td>
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
												批号
											</td>
											<td>
												数量
											</td>
											<td>
												单位
											</td>
											<td>
												计量
											</td>
										</tr>
									</table>
								</td>
							</tr>--%>
										<tr>
											<td align="center">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="1">
													<tr>
														<td width="6%" height="77" align="right">
															备注：
														</td>
														<td width="94%">
															<textarea name="remark" cols="180" rows="4" id="remark"
																dataType="Limit" max="256" msg="备注必须在256个字之内"
																require="false"></textarea>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td align="center">
												<input type="Submit" name="Submit" value="提交">
												&nbsp;&nbsp;
												<input type="button" name="Submit2" value="取消"
													onClick="window.close();">
											</td>
										</tr>

									</table>
									</form>
								</td>
							</tr>
						</table>
						</form>
						</td>
						</tr>
						</table>
	</body>
</html>
