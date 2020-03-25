<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>修改零售订单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
        <SCRIPT language="javascript" src="../js/showSQ.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
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
		var j=x.insertCell(9);
		var k=x.insertCell(10);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";
		i.className = "table-back";
		j.className = "table-back";
		k.className = "table-back";
 
         a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+p.productid+"' size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\"  value='"+p.productname+"' size=\"32\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" value='"+p.specmode+"' size=\"32\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" value='"+p.unitid+"' size=\"6\"><input name=\"unit\" type=\"text\" id=\"unit\" value='"+p.unitidname+"' size=\"8\" readonly>";
		f.innerHTML="<a href=\"#\" onMouseOver=\"ShowHD("+dbtable.rows.length+");\" onMouseOut=\"HiddenHD();\"><img src=\"../images/CN/cheng.gif\" width=\"16\" height=\"16\" border=\"0\"></a> <a href=\"#\" onMouseOver=\"ShowSQ(this,'"+p.productid+"');\" onMouseOut=\"HiddenSQ();\"><img src=\"../images/CN/stock.gif\" width=\"16\" height=\"16\" border=\"0\"></a>";
        g.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value='"+formatCurrency(p.price)+"' size=\"10\" maxlength=\"10\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" dataType=\"Double\" msg=\"单价只能是数字!\" require=\"false\" onKeyPress=\"KeyPress(this)\">";
        h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1.00\" size=\"8\" maxlength=\"8\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\" onKeyPress=\"KeyPress(this)\">";
		i.innerHTML="<input name=\"discount\" type=\"text\" id=\"discount\" value='"+p.discount+"' size=\"8\" maxlength=\"10\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" readonly>%";
		j.innerHTML="<input name=\"taxrate\" type=\"text\" id=\"taxrate\" value='"+p.taxrate+"' size=\"8\" maxlength=\"10\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" readonly>%";
        k.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" onFocus=\"SubTotal()\" value=\"0.00\" size=\"10\" maxlength=\"10\" readonly>";		
	 	SubTotal();
		TotalSum();
}
	

function SupperSelect(rowx){
	var cid=document.validateProvide.cid.value;
	var icid=0;
	 if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
		window.open("../common/selectSaleOrderProductAction.do?cid="+cid+"&icid="+icid,"","height=600,width=800,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");
	
}
 
//--------------------------------start -----------------------	
function addItemValue(obj){
	var vproductid = document.validateProvide.productid;
	var vwarehouseid = document.validateProvide.warehouseid;
	if ( vproductid != undefined ){
		if ( vproductid.length ){
			for ( k=0; k<vproductid.length; k++){
				if ( vproductid[k].value == obj.productid && vwarehouseid[k].value == obj.warehouseid){
					var vquantity = parseInt(document.validateProvide.item("quantity")[k].value);
					document.validateProvide.item("quantity")[k].value = vquantity ;
					SubTotal();
					TotalSum();
					return;
				}
			}
		}else{
			if ( vproductid.value == obj.productid && vwarehouseid.value == obj.warehouseid){
					var vquantity = parseInt(document.validateProvide.item("quantity").value);
					document.validateProvide.item("quantity").value = vquantity ;
					SubTotal();
					TotalSum();
					return;
			}
		}		
	}
	addRow(obj);
}
//--------------------------------end -----------------------	 

function SubTotal(){
	var sum=0.00;
	var unitprice=document.validateProvide.unitprice;
	var quantity=document.validateProvide.quantity;
	var discount=document.validateProvide.discount;
	var taxrate=document.validateProvide.taxrate;
	var objsubsum=document.validateProvide.subsum;
	if ( unitprice.length){
		for (var m=0; m<unitprice.length; m++){
			objsubsum[m].value=formatCurrency(parseFloat(unitprice[m].value*quantity[m].value*discount[m].value/100*(1+taxrate[m].value/100)));
		}
	}else{
		objsubsum.value=formatCurrency(parseFloat(unitprice.value*quantity.value*discount.value/100*(1+taxrate.value/100)));
	}
}		

function TotalSum(){
	var totalsum=0.00;
	var objsubsum=document.validateProvide.subsum;
	if ( objsubsum.length){
		for (var m=0; m<objsubsum.length; m++){
			totalsum=totalsum+parseFloat(objsubsum[m].value);
		}
	}else{
		totalsum=parseFloat(objsubsum.value);
	}
	document.validateProvide.totalsum.value=formatCurrency(totalsum);
}

function SelectCustomer(){
	var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	if(c==undefined){return;}
	document.validateProvide.cid.value=c.cid;
	document.validateProvide.cname.value=c.cname;
	//document.validateProvide.tel.value=c.mobile;
}
		
function SelectLinkman(){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	var c=showModalDialog("../common/toSelectLinkmanAction.do?cid="+cid,null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	if(c==undefined){return;}
	document.validateProvide.receiveman.value=c.lname;
	document.validateProvide.tel.value=c.mobile;
	document.validateProvide.transportaddr.value=c.ltransportaddr;
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
        var cname = document.validateProvide.cname;
		var receiveman = document.validateProvide.receiveman;
		var consignmentdate = document.validateProvide.consignmentdate;
		var productid = document.validateProvide.productid;
		
		if(cname.value==null||cname.value==""){
			alert("客户不能为空!");
			return false;
		}
		
		if(receiveman.value==null||receiveman.value==""){
			alert("收货人不能为空!");
			return false;
		}

		if(consignmentdate.value==null||consignmentdate.value==""){
			alert("交货日期不能为空");
			//totalsum.focus();
			return false;
		}
		
		if(productid==undefined){
					alert("请选择产品！");
					return false;
				}
		
		if ( !Validator.Validate(document.validateProvide,2) ){
					return false;
				}

		showloading();
		validateProvide.submit();
		//return true;
	}

	function ShowHD(tbr) {   // yy xx
//alert("db"+dbtable.rows.length);
//alert("abc="+(tbr-2));
var cid = $F('cid');
var productid="";
if(tbr-2<=0){
	if(dbtable.rows.length<=2){
	productid=document.validateProvide.item("productid").value;
	}else{
	productid=document.validateProvide.item("productid")[tbr-2].value;
	}
}else{
//alert(">>>>>2"+document.validateProvide.item("productid")[0].value);
productid=document.validateProvide.item("productid")[tbr-2].value;
}
	$("hd").style.visibility = "visible" ;
	$("hd").style.top = event.clientY;;
	$("hd").style.left = event.clientX;
	//$("require").removeChild($("require").getElementsByTagName("table")[0]);
	if(cid!=""){
	getHistoryPrice(cid,productid);
	}
}
function HiddenHD(){
	hd.style.visibility = "hidden";
	/*
	for(var b=0;b<require.rows.length;b++){
		 	document.getElementById('require').deleteRow(b);
		 	b=b-1;
		 	}
	*/ 
	$("historyprice").removeChild($("historyprice").getElementsByTagName("table")[0]);
}

function getHistoryPrice(cid,productid){
	   var url = "../sales/getHistoryChenAjax.do?cid="+cid+"&productid="+productid;
	   //document.getElementById("require").style.display="";
	   //document.getElementById("require").innerHTML="姝ｅ湪璇诲彇鏁版嵁...";
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showHistory}
                    );
}

function showHistory(originalRequest){
	
	var product = originalRequest.responseXML.getElementsByTagName("product");
	//var x=document.all("require");//.insertRow(desk.rows.length);
	//var strcontent="";
	//alert(proot.length);
	var requireHTML = '<table id="historyprice" width="100%"  border="0" cellpadding="3" cellspacing="0">';

		for(var i=0;i<product.length;i++){
			var rm = product[i];
			var hprice = rm.getElementsByTagName("hprice")[0].firstChild.data;
			var hdate =rm.getElementsByTagName("hdate")[0].firstChild.data;
			//var murl =rm.getElementsByTagName("murl")[0].firstChild.data;
			//alert(ispd);
			requireHTML  += "<tr><td width='50%'>"+hprice+"</td><td width='50%'>"+hdate+" </td></tr>";
			//addRows(productname,productcount,stockcount);
		}

		$("historyprice").innerHTML = requireHTML + "</table>";

		//document.getElementById("result").style.display="none";
	}
	
</script>
		<style type="text/css">
<!--
#hd {
	position: absolute;
	left: 0px;
	top: 0px;
	width: 200px;
	height: auto;
	z-index: 1;
	visibility: hidden;
}

-->
</style>
	</head>
	<div id="hd">
		<table width="100%" height="80" border="0" cellpadding="0"
			cellspacing="0" class="GG">
			<tr>
				<td width="50%" height="32" class="title-back">
					历史成交价
				</td>
				<td width="50%" class="title-back">
					成交价日期
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="historyprice">

					</div>
				</td>
			</tr>
		</table>
	</div>

	<body style="overflow: auto;">

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
								修改零售预订单
							</td>
						</tr>
					</table>
					<form name="validateProvide" method="post"
							action="updSaleIndentAction.do">
					<table width="99%" border="0" cellpadding="0" cellspacing="1">
						
						<tr>
							<td>

								<fieldset align="center">
									<legend>
										<table width="50" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td>
													基本资料
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td align="right">
												<input name="ID" type="hidden" id="ID" value="${sof.id}">
												<input name="cid" type="hidden" id="cid" value="${sof.cid}">
												客户名称：
											</td>
											<td>
												<input name="cname" type="text" id="cname" value="${sof.cname}" readonly><a href="#"><img
														src="../images/CN/find.gif" width="18" height="18"
														align="absmiddle" border="0"> </a><span class="STYLE1">*</span>
											</td>
											<td align="right">
												收货人：
											</td>
											<td>
												<input name="receiveman" type="text" id="receiveman"
													value="${sof.receiveman}" readonly><a href="javascript:SelectLinkman();"><img
														src="../images/CN/find.gif" width="18" height="18"
														align="absmiddle" border="0"> </a>
											</td>
											<td align="right">
												联系电话：
											</td>
											<td>
												<input name="tel" type="text" id="tel" value="${sof.tel}">
											</td>
										</tr>
										<tr>
											<td align="right">
												发货方式：
											</td>
											<td>
												<windrp:select key="TransportMode" name="transportmode"
													p="n|d" value="${sof.transportmode}" />
											</td>
											<td align="right">
												付款方式：
											</td>
											<td>
												<windrp:paymentmode name="paymentmode"
													value="${sof.paymentmode}" />
											</td>
											<td align="right">
												交货日期：
											</td>
											<td>
												<input name="consignmentdate" type="text"
													id="consignmentdate" onFocus="javascript:selectDate(this)"
													value="${sof.consignmentdate}" readonly>
											</td>
										</tr>
										<tr>
											<td align="right">
												客户方单据编号：
											</td>
											<td>
												<input name="customerbillid" type="text" id="customerbillid"
													value="${sof.customerbillid}">
											</td>
											<td align="right">
												发货地址：
											</td>
											<td colspan="2">
												<input name="transportaddr" type="text" id="transportaddr"
													value="${sof.transportaddr}" size="70">
											</td>
											<td>
											</td>
										</tr>
									</table>
								</fieldset>

								<table width="100%" border="0" cellpadding="0" cellspacing="1">

									<tr>
										<td width="100%">
											<table border="0" cellpadding="0" cellspacing="1">
												<tr align="center" class="back-blue-light2">


													<td id="elect"><img src="../images/CN/selectp.gif" border="0"
															style="cursor: pointer"
															onClick="SupperSelect(dbtable.rows.length)">													</td>
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
										<td width="2%">
											<input type="checkbox" name="checkall" value="on"
												onClick="Check();">
										</td>
										<td width="5%">
											产品编号
										</td>
										<td width="19%">
											产品名称
										</td>
										<td width="20%">
											规格型号
										</td>
										<td width="8%">
											单位
										</td>
										<td width="7%">
											相关
										</td>
										<td width="8%">
											单价
										</td>
										<td width="5%">
											数量
										</td>
										<td width="10%">
											折扣率
										</td>
										<td width="10%">
											税率
										</td>

										<td width="14%">
											金额
										</td>
									</tr>
									<c:set var="count" value="2" />
									<logic:iterate id="p" name="als">
										<tr class="table-back">
											<td>
												<input type="checkbox" value="${count}" name="che">
											</td>
											<td>
												<input name="productid" type="text" id="productid"
													value="${p.productid}" size="12">
											</td>
											<td>
												<input name="productname" type="text"
													value="${p.productname}" id="productname" size="32"
													readonly>
											</td>
											<td>
												<input name="specmode" type="text" value="${p.specmode}"
													id="specmode" size="32" readonly>
											</td>
											<td>
												<input name="unitid" type="hidden" value="${p.unitid}">
												<input name="unit" type="text" value="${p.unitidname}"
													id="unit" size="8" readonly>
											</td>
											<td>
												<a href="#" onMouseOver="ShowHD(${count});"
													onMouseOut="HiddenHD();"><img
														src="../images/CN/cheng.gif" width="16" border="0"></a>
														<a href="#" 
														onMouseOver="ShowSQ(this,'${p.productid}');" onMouseOut="HiddenSQ();"><img
														src="../images/CN/stock.gif" width="16" border="0">
												</a>
											</td>

											<td>
											
												<input name="unitprice" type="text" value="<windrp:format value="${p.unitprice}"/>"
													onChange="SubTotal();TotalSum();" id="unitprice"
													size="10" maxlength="10" onKeyPress="KeyPress(this)">
											</td>
											<td>
												<input name="quantity" type="text" value="<windrp:format value="${p.quantity}"/>"
													onChange="SubTotal();TotalSum();" id="quantity"
													size="8" maxlength="8" onKeyPress="KeyPress(this)">
											</td>
											<td>
												<input name="discount" type="text" value="${p.discount}"
													onChange="SubTotal();TotalSum();" id="discount"
													size="8" maxlength="10" readonly>%
											</td>


											<td>
												<input name="taxrate" type="text" value="${p.taxrate}"
													onChange="SubTotal();TotalSum();" id="taxrate"
													size="8" maxlength="8" readonly>%
											</td>

											<td>
												<input name="subsum" type="text" value="${p.subsum}"
													id="subsum" size="10" maxlength="10" readonly>
											</td>
										</tr>
										<c:set var="count" value="${count+1}" />
									</logic:iterate>
								</table>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr align="center" class="table-back">
										<td width="2%">
											<a href="javascript:deleteR('che','dbtable');"><img
													src="../images/CN/del.gif" border="0"> </a>
										</td>
										<td width="137">&nbsp;
											
										</td>
										<td width="86">&nbsp;
											
										</td>
										<td width="794" align="right">
											<input type="button" name="button" value="金额小计"
												onClick="TotalSum();">
											：
										</td>
										<td width="186">
											<input name="totalsum" type="text" id="totalsum"
												value="${sof.totalsum}" size="10" readonly maxlength="10">
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
											<textarea name="remark" cols="180" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false">${sof.remark}</textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
								<input type="button" name="Submit" onClick="ChkValue();"
									value="提交">
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
