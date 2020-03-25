<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>
		<title>新增机构转仓申请单</title>
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
    b.innerHTML="<input name='productid' type='text' id='productid'  size='10' value='"+p.productid+"'>";
    c.innerHTML="<input name='nccode' type='text' id='nccode' size='10' readonly value='"+p.nccode+"'>";
	d.innerHTML="<input name='productname' type='text' id='productname' size='60' readonly value='"+p.productname+"'>";
	e.innerHTML="<input name='specmode' type='text' id='specmode' size='15' readonly value='"+p.specmode+"'>";
    f.innerHTML="<a href='#' onMouseOver=ShowSQ(this,'"+p.productid+"'); ><img src='../images/CN/stock.gif' width='16' border='0'></a>";

	var num ="1";
	g.innerHTML="<input name='quantity' type='text' id='quantity' dataType='PositiveInteger' msg='必须录入正整数' onkeyup=\"checknum(this);changeUnit(this.value,'" + p.unitid + "','" + p.countUnitName + "','" + p.unitList + "',this,true);\"  value='"+num+"' size='8' maxlength='8'>";
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
    $(obj).parent().next().next().html(quantity.toFixed(2) + "&nbsp;" + countUnitName);
}
function delRow(obj){
	var rowNo = getTableRowNo(obj);
	document.getElementById('dbtable').deleteRow (rowNo);
} 
function getTableRowNo(obj){
	var trSeq = $(obj).parent().parent().parent().find("tr").index($(obj).parent().parent()[0]);
	return trSeq;
}
function checknum(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");
}

function SupperSelect(rowx){
	var oid=document.referForm.MakeOrganID.value;
	var outwid=document.referForm.outwarehouseid.value;
	if(oid==null||oid=="")
	{
		alert("请选择调出机构！");
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



function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value);
		document.referForm.item("subsum").value=sum;
	}else{
		for(var m=0;m<rowslength;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			document.referForm.item("subsum")(m).value=sum;	
		}
	}
}


function TotalSum(){
	var totalsum=0.00;
	//alert(dbtable.rows.length);
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.referForm.item("subsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.referForm.item("subsum")(i).value);
		}
	}
	document.referForm.totalsum.value=totalsum;
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
		var p=showModalDialog("../common/selectMoveOrganAction.do?rank="+'${organRank}'+"&organType="+'${organType}'+"&OID="+'${MakeOrganID}',null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.referForm.receiveorganid.value=p.id;
			document.referForm.ioname.value=p.organname;
			//document.referForm.transportaddr.value=p.oaddr;
			document.referForm.iwname.value=p.wname;
			document.referForm.inwarehouseid.value=p.wid;
			document.referForm.transportaddr.value=p.waddr;
			getOrganLinkmanBycid(p.id);
	}
	
	function SelectLinkman(){
		var inorganid=document.referForm.receiveorganid.value;
		if(inorganid==null||inorganid=="")
		{
			alert("请选择调入机构！");
			return;
		}
	
			var lk=showModalDialog("../common/selectOrganLinkmanAction.do?cid="+inorganid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			document.referForm.olinkman.value=lk.lname;
			var tel = lk.mobile;
			if(tel==""){
				tel=lk.ltel;
			}
			document.referForm.otel.value=tel;
		
	}
	
	function ChkValue(){
		if ( !Validator.Validate(document.referForm,2) ){
			return false;
		}
		var productid = document.referForm.productid;
		if(productid==undefined){
			alert("请选择产品");
			return false;
		}
		var inwarehouseid = document.getElementById("inwarehouseid").value;
		var outwarehouseid = document.getElementById("outwarehouseid").value;
		if(outwarehouseid == inwarehouseid){
			alert("所选出库和入库仓库一致，请重新选择");
			return false;
		}
		
		var moveType = document.getElementById("moveType").value;
		var outorganid = document.getElementById("outorganid").value;
		var receiveorganid = document.getElementById("receiveorganid").value;
		var organids="${moveApplyOrganId}";
		if(outorganid == receiveorganid && organids.indexOf(outorganid)==-1 ){//机构内转仓 ，转仓类型不需要选择
			if(moveType != "") {
				alert("机构内转仓，不需要选择转仓类型");
				return false;
			}
		}else{
			if(moveType == "") {
				alert("请选择转仓类型");
				return false;
			}
		}

		
		//检查库存
   	   	 var flag = checkStock();
   	   	 if(flag == true){
   	   	 	showloading();
   	   	 }
   	 	return flag;
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
				}else{
					if(confirm(popMsg)){
						flag = true;
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
		selectDUW(dom,'inwarehouseid',id,'w','transportaddr')
	}
	function clearProductList() {
		var rowNum = document.getElementById('dbtable').rows.length;
		for(var i =0 ; i< rowNum -1 ;i++) {
			document.getElementById('dbtable').deleteRow(1);
		}
	}
</script>
	</head>
	<div id="sq">
		<table width="100%" height="80" border="0" cellpadding="0"
			cellspacing="0" class="GG">
			<tr>
				<td width='40%' height="32" class="title-back">
					仓库
				</td>
				<td width='35%' class="title-back">
					可用数量
				</td>
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
		<form id="referForm" name="referForm" method="post" action="addMoveApplyAction.do"
			onSubmit="return ChkValue();">
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
								<td>
									新增机构转仓申请单
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
									<td width="10%" align="right">
										需求日期：
									</td>
									<td width="19%">
										<input name="MakeOrganID" type="hidden" id="MakeOrganID"
											value="${MakeOrganID }">
										<input name="movedate" type="text" id="movedate"
											readonly="readonly" onFocus="javascript:selectDate(this)"
											value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"
											dataType="Require" msg="必须录入机构转仓需求日期!">
										<span class="STYLE1">*</span>
									</td>
									<td width="9%" align="right">
										调出机构：
									</td>
									<td width="17%">
										<input name="outorganid" type="hidden" id="outorganid"
											value="${MakeOrganID}">
										<input name="oname" type="text" id="oname" size="30"
											value="${MakeOrganName}" dataType="Require" msg="必须录入调出机构!"
											readonly>
										<span class="STYLE1">*</span>
									</td>
									<td width="9%" align="right">
										调出仓库：
									</td>
									<td width="17%">
										<input type="hidden" name="outwarehouseid" id="outwarehouseid" value="${outwarehouseid}">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'outwarehouseid','${MakeOrganID}','rw')"
											value="<windrp:getname key='warehouse' p='d' value='${outwarehouseid}'/>"
											readonly>
										<span class="STYLE1">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										调入机构：
									</td>
									<td>
										<input name="receiveorganid" type="hidden" id="receiveorganid">
										<input name="ioname" type="text" id="ioname" size="30"
											dataType="Require" msg="必须录入调入机构!" readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle">
										</a>
										<span class="style1">*</span>
									</td>
									<td align="right">
										调入仓库：
									</td>
									<td>
										<input type="hidden" name="inwarehouseid" id="inwarehouseid">
										<input type="text" name="iwname" id="iwname"
											onClick="selectWIn(this)" value="" readonly>
										<span class="style1">*</span>
									</td>
									<td align="right">
										联系人：
									</td>
									<td>
										<input type="text" id="olinkman" name="olinkman"
											dataType="Require" msg="必须录入联系人!">
										<a href="javascript:SelectLinkman();"><img
												src="../images/CN/find.gif" width="19" height="18"
												align="absmiddle" border="0"> </a>
										<span class="STYLE1">*</span>
									</td>

								</tr>
								<tr>
									<td align="right">
										联系电话：
									</td>
									<td>
										<input type="text" id="otel" name="otel"
											dataType="PhoneOrMobile" msg="必须录入正确联系电话!">
										<span class="STYLE1">*</span>
									</td>
									<td align="right">
										收货地址：
									</td>
									<td colspan="5">
										<input name="transportaddr" type="text" id="transportaddr"
											dataType="Require" msg="必须录入收货地址 !" size="80">
										<span class="STYLE1">*</span>
									</td>

								</tr>
								<tr>
									<td align="right">
										转仓类型：
									</td>
									<td>
										<windrp:select key="MoveType" name="moveType"
													p="y|f"  /><span class="STYLE1">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										机构转仓原因：
									</td>
									<td colspan="5">
										<textarea name="movecause" cols="100" style="width: 100%"
											rows="3" id="movecause" max="256" dataType="Require" msg="必须录入转仓原因 !"></textarea><span class="STYLE1">*</span>
										<br>
										<span class="td-blankout">(机构转仓原因不能超过256个字符!)</span>
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
												<img src="../images/CN/selectp.gif" width="72" height="21"
													border="0" style="cursor: pointer"
													onClick="SupperSelect(dbtable.rows.length)">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<fieldset align="center">
							<legend>
								产品信息
							</legend>
							<table width="100%" id="dbtable" border="0" cellpadding="0"
								cellspacing="1">
								<tr align="center" class="title-top">
								  <td width="2%"></td>
						          <td>产品编号</td>
						          <td>产品内部编号</td>
						          <td>产品名称 </td>
						          <td>规格</td>
						          <td>库存</td>
						          <td>数量</td>
							      <td>单位</td>
							      <td>计量</td>
								</tr>
							</table>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr align="center" class="table-back">
									<td width="3%">
									</td>
									<td width="11%">
										&nbsp;
									</td>
									<td width="7%">
										&nbsp;
									</td>
									<td width="69%" align="right">
									</td>
									<td width="10%"></td>
								</tr>
							</table>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr>
									<td align="right" width="9%">
										备注：
									</td>
									<td>
										<textarea name="remark" cols="100" style="width: 100%"
											rows="4" id="remark" dataType="Limit" max="256"
											msg="备注必须在256个字之内" require="false"></textarea>
										<br>
										<span class="td-blankout">(备注不能超过256个字符!)</span>
									</td>
								</tr>
							</table>
						</fieldset>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="center">
									<input type="submit" name="Submit" value="确定">
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
		<form name="referForm2" method="post" action="addMoveApplyAction.do">
			<input type="hidden" name="outwarehouseid" id="outwarehouseid2" value="${outwarehouseid}">
		</form>
	</body>
</html>
