<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/validator.js"> </SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/showSQ.js"></SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script language=javascript>
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
        b.innerHTML="<input name='productid' type='text' id='productid' readonly value='"+p.productid+"'>";
        c.innerHTML="<input name='nccode' type='text' id='nccode' size='20' readonly value='"+p.nccode+"'>";
		d.innerHTML="<input name='productname' type='text' id='productname' size='70' readonly value='"+p.productname+"'>";
		e.innerHTML="<input name='specmode' type='text' id='specmode' size='15' readonly value='"+p.specmode+"'>";
        f.innerHTML="<a href='#' onMouseOver=ShowSQ(this,'"+p.productid+"'); onMouseOut='HiddenSQ();'><img src='../images/CN/stock.gif' width='16'  border='0'></a>";
		
        var num = 1;
		g.innerHTML="<input name='quantity' type='text' id='quantity' dataType='Integer' msg='必须录入整数' onkeyup=\"checknum(this);changeUnit(this.value,'" + p.unitid + "','" + p.countUnitName + "','" + p.unitList + "',this,true);\"  value='"+num+"' size='8'  maxlength='8'>";
		h.innerHTML="<input name='unitid' type='hidden' id='unitid'  value='"+p.unitid+"'>" + p.unitidname;
		i.innerHTML= p.countUnitName;
		//显示计量单位
		changeUnit(1,p.unitid,p.countUnitName,p.unitList,$(g).children("input"),false);
}


//用于动态显示计量单位
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
     $(obj).parent().next().next().html(formatFloat(quantity,2) + "&nbsp;" + countUnitName);
 }

function checknum(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");
}
	
function SupperSelect(rowx){
	var oid=document.referForm.receiveorganid.value;
	var outwid=document.referForm.outwarehouseid.value;
	
	if(oid==null||oid=="")
	{
		alert("请选择调入机构！");
		return;
	}
	var p=showModalDialog("../ditch/toSelectOrganWithdrawProductAction.do?wid="+outwid,null,"dialogWidth:22cm;dialogHeight:17cm;center:yes;status:no;scrolling:auto;");
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
	var trSeq = $(obj).parent().parent().parent().find("tr").index($(obj).parent().parent()[0]);
	return trSeq;
}

function deleteR(){
	chebox=document.all("che");
	if(chebox!=null){ 
		if (chebox.length >=1){
			for(var i=1;i<=chebox.length;i++){
				if (chebox[i-1].checked) {
					document.getElementById('dbtable').deleteRow (i);
					i=i-1;
			  	}
			}
		}else{
			if (chebox.checked ){
				document.all('dbtable').deleteRow(1);
			}
		}
	}
} 

function Check(){

		var checkche  = document.getElementsByName("che");
		var checkall = document.getElementById("checkall");
		for(i=0;i<checkche.length;i++){
			checkche[i].checked = checkall.checked;
		}

	}

	

	
	function getOrganLinkmanBycid(objcid){
        var url = '../sales/ajaxOrganLinkmanAction.do?cid='+objcid;
        var pars = '';
        
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showLinkman}
                    );	
    }
    
    function showLinkman(originalRequest)
    {
		var data = eval('(' + originalRequest.responseText + ')');
		var lk = data.linkman;
		if ( lk == undefined ){
			document.referForm.olinkman.value='';
			document.referForm.otel.value='';
		}else{
			document.referForm.olinkman.value=lk.name;
			document.referForm.otel.value=lk.mobile;
			if(document.referForm.otel.value == ""){
				document.referForm.otel.value=lk.officetel;
			}
		}
	}
	
	function SelectOrgan(){  
		var p=showModalDialog("../common/selectOrganAction.do?type=vwar",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.referForm.receiveorganid.value=p.id;
			document.referForm.oname.value=p.organname;
			//document.referForm.transportaddr.value=p.oaddr;
			document.referForm.inwarehouseid.value=p.wid;
			document.referForm.wname.value=p.wname;
			document.referForm.transportaddr.value=p.waddr;
			getOrganLinkmanBycid(p.id);
			
	}
	
	  function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			var oldOrgan = document.referForm.organid.value;
			document.referForm.organid.value=p.id;
			document.referForm.ooname.value=p.organname;
			//document.referForm.transportaddr.value=p.oaddr;
			document.referForm.owname.value=p.wname;
			document.referForm.outwarehouseid.value=p.wid;
		//	getOrganLinkmanBycid(p.id);
		if(oldOrgan != p.id) {
			clearProductList()
		}
			
	}

	function SelectLinkman(){
		var receiveorganid=document.referForm.receiveorganid.value;
		if(receiveorganid==null||receiveorganid=="")
		{
			alert("请选择调入机构！");
			return;
		}
	
			var lk=showModalDialog("../common/selectOrganLinkmanAction.do?cid="+receiveorganid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			var tel;
			if(lk)
			{
				document.referForm.olinkman.value=lk.lname;
				tel = lk.ltel;
			}
			
			if(tel==""){
				tel=lk.mobile;
			}
			document.referForm.otel.value=tel;
		
	}


	function ChkValue(){		
		var nums = document.all.item("quantity");
		
		if( $('#outwarehouseid').val() ==""){
			alert("必须录入调出仓库!");
			return false;
		}
		if ( !Validator.Validate(document.referForm,2) ){
		return false;
		}
		if( $('#inwarehouseid').val() ==""){
			alert("必须录入调入仓库!");
			return false;
		}
		var productid = document.referForm.productid;
		if(productid==null){
			alert("请选择产品");
			return false;
		}
		if(nums.length>=1){
			for(var i = 0; i<nums.length ; i++){
				if(nums[i].value == 0){
					alert("产品数量不能为0");
					return false;
				 }else if(nums[i].value==""||nums[i].value==null){
					 alert("产品数量不能为空");
					 return false;
				 }
		  }
	    }else if(document.getElementById("quantity").value==null||document.getElementById("quantity").value==""){
		    	alert("产品数量不能为空");
				return false;
	    }else if(document.getElementById("quantity").value==0){
	    	alert("产品数量不能为0");
			return false;
    	}
		return checkStock();
	}
	
	function checkStock(){
		var flag = false;
		// ajax同步检查库存 
   		$.ajax({
			type:"POST",
			url:"../warehouse/ajaxCheckStockpile.do",
			data:$("#referForm").serialize(),
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
	
	function selectW(dom,type){
		var id = $('#organid').val();
		selectDUW(dom,'outwarehouseid',id,type,'');
	}
	function selectWIn(dom){
		var id = $('#receiveorganid').val();
		selectDUW(dom,'inwarehouseid',id,'rw','transportaddr')
	}
	function clearProductList() {
		var rowNum = document.getElementById('dbtable').rows.length;
		for(var i =0 ; i< rowNum -1 ;i++) {
			document.getElementById('dbtable').deleteRow(1);
		}
	}
	var bsort = $("#bsort").val();
	//加载事件
	$(function(){
		$("#bsort").change(changeBsort);
	});
	
	function changeBsort(){
		var deliveryType = $("#bsort").val(); 
		if(deliveryType == 2 && bsort != deliveryType) {
			clearProductList();
		}
		bsort = deliveryType; 
	}
</script>
</head>
<div id="sq">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
     <td width="45%" height="32" class="title-back"> 仓库</td>
      <td width="35%" class="title-back">可用数量</td>
    </tr>
    <tr>
      <td colspan="3">
       <div id="stock">	
        	
      </div> 	
	  </td>
    </tr>
  </table>
</div>

	<body style="overflow: auto;">
		<form name="referForm" id="referForm" method="post"
			action="updStockAlterMoveAction.do" onSubmit="return ChkValue();">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
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
									${operateName }${menu }
								</td>
							</tr>
						</table>

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
									<td width="9%" align="right">
										<input name="id" type="hidden" id="id" value="${smf.id}">
										调拨日期：
									</td>
									<td width="25%">
										<input name="movedate" type="text" id="movedate"
											onFocus="javascript:selectDate(this)"
											value="<windrp:dateformat value="${smf.movedate}" p="yyyy-MM-dd"/>" readonly>
											
									</td>
									<td width="9%" align="right">
										调出机构：
									</td>
									<td width="24%">
										<input name="organid" type="hidden" id="organid"
											value="${smf.outOrganId}">
										<input name="ooname" type="text" id="ooname" size="30"
											value="<windrp:getname key='organ' p='d' value='${smf.outOrganId}'/>"
											dataType="Require" msg="必须录入调出机构!" readonly><a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle">
										</a><span class="style1">*</span>
									</td>
									<td width="10%" align="right">
										调出仓库：
									</td>
									<td width="23%">
	 								    <input type="hidden" name="outwarehouseid" id="outwarehouseid" value="${smf.outwarehouseid}">
										<input type="text" name="owname" id="owname" 	onClick="selectW(this,'rw')"
											value="<windrp:getname key='warehouse' value='${smf.outwarehouseid}' p='d'/>"
											readonly>
										<span class="style1">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										调入机构：
									</td>
									<td>

										<input name="receiveorganid" type="hidden" id="receiveorganid"
											value="${smf.receiveorganid }">
										<input name="oname" type="text" id="oname" size="30"
											value="<windrp:getname key='organ' p='d' value='${smf.receiveorganid }'/>"
											dataType="Require" msg="必须录入调入机构!" readonly><a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle">
										</a><span class="style1">*</span>
									</td>

									<td align="right">
										调入仓库：
									</td>
									<td>
										<input type="hidden" name="inwarehouseid" id="inwarehouseid" value="${smf.inwarehouseid}">
										<input type="text" name="wname" id="wname" 	onClick="selectWIn(this)"
											value="<windrp:getname key='warehouse' value='${smf.inwarehouseid}' p='d'/>"
											readonly>
										<span class="style1">*</span>
									</td>
									<td align="right">
										联系人：
									</td>
									<td>
										<input type="text" id="olinkman" name="olinkman" dataType="Require" msg="必须录入联系人!"
										value="${smf.olinkman }"><a href="javascript:SelectLinkman();"><img
												src="../images/CN/find.gif" width="19" height="18"
												align="absmiddle" border="0"> </a>
									</td>
								</tr>
								<tr>
									<td align="right">
										联系电话：
									</td>
									<td>
										<input type="text" id="otel" name="otel" value="${smf.otel }"
											dataType="PhoneOrMobile" msg="必须录入正确联系电话!" require="false">
									</td>
									<td align="right">
										收货地址：
									</td>
									<td colspan="3">
										<input name="transportaddr" type="text" id="transportaddr"
											size="50" maxlength="100" dataType="Require" msg="必须录入收货地址!"
											value="${smf.transportaddr }">
										<span class="style1">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										单据类型：
									</td>
									<td colspan="5">
										<windrp:select key="DeliveryType" name="bsort" value="${smf.bsort}" p="n|f"/>		
									</td>
								 <tr>
								<tr>
									<td align="right">
										发货原因：
									</td>
									<td colspan="5">
										<textarea name="movecause" cols="100" style="width: 100%"
											rows="3" id="movecause" dataType="Limit" max="256"
											msg="调拨原因必须在256个字之内" require="false">${smf.movecause }</textarea><br>
											<span class="td-blankout">(发货原因不能超过256个字符!)</span>
									</td>
								</tr>
							</table>
						</fieldset>

						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td width="100%">
									<table border="0" cellpadding="0" cellspacing="1">
										<tr align="center" class="back-blue-light2">
											<td id="elect">
												<img src="../images/CN/selectp.gif" height="18" border="0"
													style="cursor: pointer"
													onClick="SupperSelect(dbtable.rows.length)">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<fieldset align="center">
							<legend>
								<table width="50" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											产品信息
										</td>
									</tr>
								</table>
							</legend>
							<table width="100%" id="dbtable" border="0" cellpadding="0"
								cellspacing="1">
								<tr align="center" class="title-top">
									<td width="3%">
									</td>
									<td width="5%">
										产品编号
									</td>
									<td width="6%">物料号</td>
									<td width="10%">
										产品名称
									</td>
									<td width="10%">
										规格
									</td>
									<td width="7%">
										库存
									</td>
									<td width="8%">
										数量
									</td>
									<td width="5%">
                                                                                                           单位
										</td>
									<td width="10%">计量</td>
									<td></td>
								</tr>
								<c:set var="count" value="2" />
								<logic:iterate id="p" name="als">
									<tr class="table-back">
										<td>
											<a onclick='delRow(this);'><img src='../images/CN/delete.gif' width='16' border='0'></a>
										</td>
										<td>
											<input name="productid" type="text" value="${p.productid}"
												id="productid"  readonly>
										</td>
										<td>
											<input name="nccode" type="text" value="${p.nccode}"
												id="nccode"  readonly>
										</td>
										<td>
											<input name="productname" type="text"
												value="${p.productname}" id="productname" size="70" readonly>
										</td>
										<td>
											<input name="specmode" type="text" id="specmode"
												value="${p.specmode}" size="15" readonly>
										</td>
										<td>
											<a href="#" onMouseOver="ShowSQ(this,'${p.productid}');"
												><img
													src="../images/CN/stock.gif" width="16" border="0">
											</a>
										</td>
										<td>
											<input id="quantity" name="quantity" type="text" value='<windrp:format value="${p.quantity}" p="#" />'	onkeyup="checknum(this);changeUnit(this.value,${p.unitid},'${p.countUnitName}','${p.unitList}',this,true);" 	id="quantity" 
												size="8" maxlength="8">
										</td>
										<td>
											<input name="unitid" type="hidden" value='${p.unitid}' >
											${p.unitidname}
										</td>
										<td align="right">
											<windrp:format value="${p.cUnitQuantity}" p="###,##0.0" />
											<windrp:getname key='CountUnit' value='${p.countunit}' p='d' />
										</td>
									</tr>
									<c:set var="count" value="${count+1}" />
								</logic:iterate>
							</table>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr align="center" class="table-back">
									<td width="11%">&nbsp;
									</td>
									<td width="86%">&nbsp;
									</td>
								</tr>
							</table>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr>
									<td align="right" width="10%">
										备注：
									</td>
									<td>
										<textarea name="remark" cols="100" style="width: 100%"
											rows="3" id="remark" dataType="Limit" max="256"
											msg="备注必须在256个字之内" require="false">${smf.remark }</textarea><br>
					<span class="td-blankout">(备注不能超过256个字符!)</span>
									</td>
								</tr>
							</table>
						</fieldset>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="center">
									<input type="submit" name="Submit" value="提交">
									&nbsp;&nbsp;
									<input type="button" name="button" value="取消"
										onClick="window.close();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
