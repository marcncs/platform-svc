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
   	   	return flag;
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
		var rowNum = document.getElementById('dbtable').rows.length;
		for(var i =0 ; i< rowNum -1 ;i++) {
			document.getElementById('dbtable').deleteRow(1);
		}
	}
</script>

	</head>
	<body style="overflow: auto">
		<table width="100%" border="1" cellpadding="0" cellspacing="1"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
						class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								新增红冲出库单
							</td>
						</tr>
					</table>
					<form name="validateProvide" method="post" id="validateProvide"
						action="../machin/addRedShipmentBillAction.do"
						onSubmit="return ChkValue();">
						<table width="100%" border="0" cellpadding="0" cellspacing="1">

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
													出货机构：
												</td>
												<td>
													<input name="organid" type="hidden" id="organid"
														value="${MakeOrganID}">
													<input name="orgname" type="text" id="orgname" size="30"
														dataType="Require" msg="必须录入出货机构!" value="${oname}" readonly>
													<a href="javascript:SelectOrgan();"><img
															src="../images/CN/find.gif" width="18" height="18" border="0"
															align="absmiddle">
													</a>
													<span class="style1">*</span>
												</td>
												<td width="10%" align="right">
													出货仓库：
												</td>
												<td width="24%">
													<input type="hidden" name="warehouseid" id="warehouseid"
														value="${WarehouseID}">
													<input type="text" name="owname" id="owname"
														onClick="selectDUW(this,'warehouseid',$F('organid'),'rw','')"
														value="${wname}" readonly>
													<span class="STYLE1">*</span>
												</td>
												<td width="9%" align="right">
													出库类别：
												</td>
												<td width="23%">
													<windrp:select key="RedShipment" name="shipmentsort" p="n|f" />
												</td>	
											</tr>
											<tr>
												<%--<td width="9%" align="right">相关单据：</td>
	      <td width="25%"><input type="text" name="billno" maxlength="32"></td>
	    --%>
												<td align="right">
													<input type="checkbox" name="isaccount" checked="checked" />
													&nbsp;
												</td>
												<td>
													是否记账
												</td>
												<%--<td align="right">
													需求日期：
												</td>
												--%><td>
													<input name="requiredate" type="hidden" id="requiredate"
														value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"
														onFocus="javascript:selectDate(this)" readonly>
												</td>
											</tr>
										</table>
									</fieldset>

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
									</table>
								</td>
							</tr>
							<tr>
								<td align="right">
									<table width="100%" id="dbtable" border="0" cellpadding="0"
										cellspacing="1">
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
							</tr>
							<tr>
								<td align="center">
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td width="6%" height="77" align="right">
												备注：
											</td>
											<td width="94%">
												<textarea name="remark" cols="180" rows="4" id="remark"
													dataType="Limit" max="256" msg="备注必须在256个字之内" require="false"></textarea>
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
	</body>
</html>
