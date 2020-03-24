<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>新增零售单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDateTime.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/common.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectctitle.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
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
		var l=x.insertCell(11);		

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
		l.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+p.productid+"' size=\"16\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" value='"+p.productname+"' size=\"45\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"6\" value='"+p.specmode+"' readonly>";
        e.innerHTML="<input name=\"salesort\" type=\"hidden\" value='"+p.salesort+"' id=\"salesort\"><input name=\"cost\" type=\"hidden\" id=\"cost\" value='"+p.cost+"'><input name=\"unitid\" type=\"hidden\" id=\"unitid\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"4\" value='"+p.unitidname+"' readonly>";
		f.innerHTML="<input name=\"warehouseid\" type=\"hidden\" id=\"warehouseid\" value='"+p.warehouseid+"'><input name=\"wise\" type=\"hidden\" id=\"wise\" value='"+p.wise+"'><input name=\"warehousename\" type=\"text\" id=\"warehousename\" size=\"10\" value='"+p.warehousename+"' readonly>";
        g.innerHTML="<input name=\"orgunitprice\" type=\"hidden\" id=\"orgunitprice\" value='"+formatCurrency(p.price)+"'><input name=\"unitprice\" type=\"text\" id=\"unitprice\" value='"+formatCurrency(p.price)+"' readonly size=\"8\" maxlength=\"10\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" >";
		h.innerHTML="<input name=\"taxunitprice\" type=\"text\" id=\"taxunitprice\" value='"+formatCurrency(p.taxprice)+"' size=\"8\" maxlength=\"10\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" dataType=\"Double\" msg=\"税后单价只能是数字!\" require=\"false\">";
		i.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"10\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\">";
        j.innerHTML="<input name=\"discount\" type=\"text\" id=\"discount\" value='"+p.discount+"'  size=\"4\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" readonly>%";
		k.innerHTML="<input name=\"taxrate\" type=\"text\" id=\"taxrate\" size=\"4\" value='' onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" readonly>%";
		l.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"8\" maxlength=\"10\" readonly >";

 	SubTotal();
	TotalSum();
}


function SupperSelect(rowx){
var cid=document.validateProvide.cid.value;
var icid=document.validateProvide.invmsg.value;
var organid=document.validateProvide.equiporganid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	if(organid==null||organid=="")
	{
		alert("请选择送货机构！");
		return;
	}

	window.open("../common/selectSaleOrderProductAction.do?cid="+cid+"&organid="+organid+"&icid="+icid,"","height=600,width=800,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");

}

function SupperSelectPresent(rowx){
var cid=document.validateProvide.cid.value;
var icid=document.validateProvide.invmsg.value;
var organid=document.validateProvide.equiporganid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	if(organid==null||organid=="")
	{
		alert("请选择送货机构！");
		return;
	}

	window.open("selectSaleOrderPresentAction.do?cid="+cid+"&organid="+organid+"&icid="+icid,"","height=600,width=800,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");

}

function SupperSelectFee(rowx){
var cid=document.validateProvide.cid.value;
var icid=document.validateProvide.invmsg.value;
var organid=document.validateProvide.equiporganid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	if(organid==null||organid=="")
	{
		alert("请选择送货机构！");
		return;
	}

	window.open("selectSaleOrderFeeAction.do?cid="+cid+"&organid="+organid+"&icid="+icid,"","height=600,width=800,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");

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


function deleteR(){
	chebox=document.all("che");
	if(chebox!=null){	
		if (chebox.length >1){
			for(var i=1;i<=chebox.length;i++){
			  if (chebox[i-1].checked) {
				document.getElementById('dbtable').deleteRow ( i);
				i=i-1;
			  }
			}
		}else{
			document.all('dbtable').deleteRow(1)
		}
 	 }
}
function RemoveProduct(){
	checkAll();
	deleteR();
}


function SubTotal(){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	if((dbtable.rows.length-1) <=1){
		var total=(document.forms[0].item("taxunitprice").value)*(document.forms[0].item("quantity").value);
		sum=total*document.forms[0].item("discount").value/100;
		document.validateProvide.item("subsum").value=formatCurrency(sum);
	}else{
		for(var m=0;m<rowslength;m++){
			var total=(document.forms[0].item("taxunitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			sum=total*document.forms[0].item("discount")(m).value/100;
			document.validateProvide.item("subsum")(m).value=formatCurrency(sum);	
		}
	}
}


function TotalSum(){
	var totalsum=0.00;
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.validateProvide.item("subsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.validateProvide.item("subsum")(i).value);
		}
	}
	document.validateProvide.totalsum.value=formatCurrency(totalsum);
}

function SelectCustomer(){
	var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrollbars=yes;");
	document.validateProvide.cid.value=c.cid;
	document.validateProvide.cname.value=c.cname;
	document.validateProvide.cmobile.value=c.mobile;
	document.validateProvide.decidemantel.value=c.officetel;
	document.validateProvide.tickettitle.value =c.tickettitle;
	document.getElementById("cintegral").innerHTML=c.integral;
	setSelectValue('paymentmode',c.paymentmode);
	setSelectValue('transportmode',c.transportmode);	
	getLinkmanBycid(c.cid);
	getReceivemanBycid(c.cid);
}


function CalledCenterCustomer(){
	var cus = eval(${customer.cid});
	if ( cus == undefined ){
		return;
	}	
	document.validateProvide.cid.value='${customer.cid}';
	document.validateProvide.cname.value='${customer.cname}';
	document.validateProvide.cmobile.value='${customer.mobile}';
	document.validateProvide.decidemantel.value='${customer.officetel}';
	setSelectValue('paymentmode','${customer.paymentmode}');
	setSelectValue('transportmode','${customer.transportmode}');	
	getLinkmanBycid('${customer.cid}');
	getReceivemanBycid('${customer.cid}'); 
	
}
function showss(){
	alert(lk.name);
}

function SelectLinkman(){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid==""){
		alert("请选择客户！");
		return;
	}
	var lk=showModalDialog("../common/toSelectLinkmanAction.do?cid="+cid,null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	document.validateProvide.decideman.value=lk.lname;
	document.validateProvide.tel.value=lk.ltel;
	//document.validateProvide.transportaddr.value=lk.ltransportaddr;	
	//setSelectValue('transit',lk.transit);
}

function SelectReceiveman(){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid==""){
		alert("请选择客户！");
		return;
	}
	var lk=showModalDialog("../common/toSelectLinkmanAction.do?cid="+cid,null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	document.validateProvide.receiveman.value=lk.lname;
	document.validateProvide.receivemobile.value=lk.mobile;
	document.validateProvide.receivetel.value=lk.ltel;
	document.validateProvide.transportaddr.value=lk.ltransportaddr;	
	//setSelectValue('transit',lk.transit);
}


	function Check(){
			//alert("document.activeform.checkall.values");
		if(document.validateProvide.checkall.checked){
			//alert(document.activeform.checkall.values);
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.validateProvide.length;i++){

			if (!document.validateProvide.elements[i].checked)
				if(validateProvide.elements[i].name != "checkall"){
				document.validateProvide.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.validateProvide.length;i++){
			if (document.validateProvide.elements[i].checked)
				if(validateProvide.elements[i].name != "checkall"){
				document.validateProvide.elements[i].checked=false;
				}
		}
	}

	
	
	function getCustomerByWhere(objvalue, temp){
		if (event.keyCode != 13) { 			
			return;
		} 
		var url ="";
		if ( temp == 1 ){
			 url = 'ajaxCustomerAction.do?temp=1&cid='+objvalue;
		}else if ( temp == 2 ){
			 url = 'ajaxCustomerAction.do?temp=2&cname='+objvalue;
		}else if ( temp == 3 ){
			 url = 'ajaxCustomerAction.do?temp=3&mobile='+objvalue;
		}else if ( temp == 4 ){
			 url = 'ajaxCustomerAction.do?temp=4&officetel='+objvalue;
		}else{
			return;
		}
        var pars = '';
       	var myAjax = new Ajax.Request(
                    url,
                    {method: 'post', parameters: pars, onComplete: showCustomer}
                    );	
	}

function showCustomer(originalRequest){
		var data = eval('(' + originalRequest.responseText + ')');
		var cur = data.customer;
		var lk = data.linkman;
		var sizes = parseInt(data.sizes);	
		var temp = parseInt(data.temp);	
		//alert(sizes);	
		if ( sizes > 1 ){
			var url ="";
			if ( temp ==1 ){
				url ="toSelectSaleOrderCustomerAction.do?KeyWord="+$F("cid");				
			}else if ( temp ==2 ){
				url ="toSelectSaleOrderCustomerAction.do?KeyWord="+$F("cname");
			}else if ( temp ==3 ){
				url ="toSelectSaleOrderCustomerAction.do?KeyWord="+$F("cmobile");
			}else if ( temp ==4 ){
				url ="toSelectSaleOrderCustomerAction.do?KeyWord="+$F("decidemantel");
			}else{
				return;
			}
			var c=showModalDialog(url,null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrollbars=yes;");			
			setDefaultValue(c.cid,c.cname,'',c.mobile,c.officetel,c.ratename,c.integral,c.tickettitle);	
			getLinkmanBycid(c.cid);
			getReceivemanBycid(c.cid);
			return;
		}		
		if ( cur == undefined ){
			setDefaultValue('', '', '', '', '','','');	
		}else{
			setDefaultValue(cur.cid, cur.cname, lk.name, cur.mobile, cur.officetel,cur.ratename,cur.integral,cur.tickettitle);
		}
	}
	function setDefaultValue(cid, cname, decideman, mobile, officetel,rate,integral,tickettitle){
		document.validateProvide.cid.value=cid;
		document.validateProvide.cname.value=cname;
		document.validateProvide.decideman.value=decideman;
		document.validateProvide.cmobile.value=mobile;
		document.validateProvide.decidemantel.value=officetel;
		document.validateProvide.receiveman.value=decideman;
		document.validateProvide.receivemobile.value=mobile;
		document.validateProvide.receivetel.value = officetel;
		document.getElementById("rate").innerHTML=rate;
		document.getElementById("cintegral").innerHTML=integral;
		document.validateProvide.tickettitle.value = tickettitle;
	}
	
	function ChkValue(){
        var cname = document.validateProvide.cname;
		var receiveman = document.validateProvide.receiveman;
		var consignmentdate = document.validateProvide.consignmentdate;
		var organid=document.validateProvide.equiporganid.value;
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

		if(organid==null||organid=="")
		{
			alert("请选择送货机构！");
			return;
		}

		if ( !Validator.Validate(document.validateProvide,2) ){
		return false;
		}
		if(productid==undefined){
			alert("请选择产品！");
			return false;
		}	


		showloading();
		validateProvide.submit();
		//return true;
	}

function addNewMember(){
	window.open("../sales/toAddMemberAction.do","","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}


function ChangeInvMsg(){
	var invmsg = $F('invmsg');
	//alert(vID);
        var url = 'ajaxGetInvMsgAction.do?invmsg='+invmsg;
        var pars = '';

       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: OperatorRete}
                    );
					//alert(myAjax);
}


function OperatorRete(originalRequest){
	var ivrate = originalRequest.responseTEXT; 
	//alert(ivrate);
	var taxrate = document.validateProvide.taxrate;
	var unitprice = document.validateProvide.unitprice;
	var taxunitprice = document.validateProvide.taxunitprice;
	var wise = document.validateProvide.wise;

	
	if (taxrate.length){
			for ( r=0; r<taxrate.length; r++){
				taxrate[r].value=ivrate;
				if(ivrate >0){
				if(wise[r].value==0){
				//alert(formatCurrency(parseInt(unitprice[r].value) + parseInt(unitprice[r].value) * ivrate /100),-1);
					taxunitprice[r].value = ForDight(formatCurrency(parseInt(unitprice[r].value) + parseInt(unitprice[r].value) * ivrate /100),-1);
					}
				}else{
					taxunitprice[r].value = formatCurrency(parseInt(unitprice[r].value));
				}
			}
		}else{
				taxrate.value=ivrate;
				if(ivrate>0){
				if(wise.value==0){
					taxunitprice.value = ForDight(formatCurrency(parseInt(unitprice.value) + parseInt(unitprice.value) * ivrate /100),-1);
					}
				}else{
					taxunitprice.value = formatCurrency(parseInt(unitprice.value));
				}
		}

	SubTotal();
	TotalSum();
		
}

function ToAddLinkman(){
	var cid = $F('cid');
	if(cid==null||cid==""){
		alert("请选择客户！");
		return;
	}
	window.open("../sales/toAddLinkManAction.do?cid="+cid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
}



function LookIntegral(){
	var cid = $F('cid');
	if(cid==""){
		alert("请选择会员");
		return;
	}
	window.open("../sales/listObjIntegralAction.do?OID="+cid+"&OSort=1","","height=650,width=1024,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes");
	}
	
	function LookOrder(){
	var cid = $F('cid');
	window.open("../sales/listSaleOrderAction.do?CID="+cid,"","height=650,width=1024,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes");
	}
	
function SelectOrgan(){
var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
if ( p==undefined){return;}
document.validateProvide.equiporganid.value=p.id;
document.validateProvide.ename.value=p.organname;
}
</script>
	</head>


	<body onLoad="CalledCenterCustomer();" style="overflow: auto">

		<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								新增零售单
							</td>

						</tr>
					</table>
					<form name="validateProvide" method="post"
							action="addSaleOrderAction.do" >
					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						
						<tr>
							<td>

								<fieldset align="center">
									<legend>
										<table width="62" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="62">
													订货人信息
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td width="11%" align="right">
												会员编码：											</td>
											<td width="24%">
												<input name="cid" type="text" id="cid"
													onKeyUp="getCustomerByWhere(this.value,1)"><span class="STYLE1">*</span>
										  </td>
											<td width="10%" align="right">
												会员名称：											</td>
										  <td width="21%">
												<input name="cname" type="text" id="cname"
													onKeyUp="getCustomerByWhere(this.value,2)" readonly><a href="javascript:SelectCustomer();"><img
														src="../images/CN/find.gif" width="18" height="18"
														border="0" align="absmiddle" title="查找会员">
												</a><a href="#" onClick="javascript:addNewMember();"><img
														src="../images/CN/add.gif" width="19" height="18"
														border="0" align="absmiddle" title="新增会员">
												</a><span class="STYLE1">*</span>											</td>
											<td width="11%" align="right">
												订货人：
											</td>
											<td width="23%">
												<input name="decideman" type="text" id="decideman" readonly><a href="javascript:SelectLinkman();"><img
														src="../images/CN/find.gif" width="18" height="18"
														border="0" align="absmiddle">
												</a>
											</td>
										</tr>
										<tr>
											<td align="right">
												会员手机：
											</td>
											<td>
												<input name="cmobile" type="text" id="cmobile"  maxlength="11"
													onKeyUp="getCustomerByWhere(this.value,3)">
												<span class="STYLE1">*</span>(输入手机号回车)</td>
											<td align="right">
												会员电话：
											</td>
											<td>
												<input name="decidemantel" type="text" id="decidemantel"
													onKeyUp="getCustomerByWhere(this.value,4)">
											</td>
											<td align="right">
												会员级别：
											</td>
											<td>
												<span id="rate"> </span>
											</td>
										</tr>
										<tr>
											<td align="right">
												积分：
											</td>
											<td>
												<span id="cintegral"> </span>
											</td>
											<td align="right">
												积分明细：
											</td>
											<td>
												<a href="javascript:LookIntegral();">查看</a>
											</td>
											<td align="right">
												历史订单：
											</td>
											<td>
												<a href="javascript:LookOrder();">查看</a>
											</td>
										</tr>
									</table>
								</fieldset>



								<fieldset align="center">
									<legend>
										<table width="63" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="63">
													收货人信息
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td width="11%" align="right">
												收货人：											</td>
										  <td width="23%">
												<input name="receiveman" type="text" id="receiveman" readonly><a href="javascript:SelectReceiveman();"><img
														src="../images/CN/find.gif" width="18" height="18"
														border="0" align="absmiddle">
												</a><a href="javascript:ToAddLinkman();"><img
														src="../images/CN/add.gif" width="19" height="18"
														border="0" align="absmiddle" title="新增收货人">
												</a><span class="STYLE1">*</span>											</td>
											<td width="11%" align="right">
												收货人手机：											</td>
											<td width="21%">
												<input name="receivemobile"  maxlength="11" type="text" id="receivemobile">
										  </td>
											<td width="11%" align="right">
												收货人电话：
											</td>
											<td width="23%">
												<input name="receivetel" type="text" id="receivetel">
											</td>
										</tr>
										<tr>
											<td align="right">
												收货地址：
											</td>
											<td colspan="5">
												<input name="transportaddr" type="text" id="transportaddr"
													size="60" onClick="selectCTitle(this, $F('cid'),'1')">
											</td>
										</tr>
									</table>
								</fieldset>


								<fieldset align="center">
									<legend>
										<table width="50" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td>
													其它信息
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td width="11%" align="right">
												交货日期：											</td>
											<td width="23%">
												<input name="consignmentdate" type="text"
													id="consignmentdate" onFocus="javascript:selectDate(this)"
													value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"
													size="8" readonly>
												<input name="consignmenttime"
														type="text" id="consignmenttime"
														onFocus="javascript:selectTime(this)" value="18:00:00"
														size="8" readonly><span class="STYLE1">*</span>
										  </td>
											<td width="11%" align="right">
												发运方式：											</td>
											<td width="22%">
												<windrp:select key="TransportMode" name="transportmode"
													p="n|d" />
										  </td>
											<td width="10%" align="right">
												付款方式：
											</td>
											<td width="23%">
												<windrp:paymentmode name="paymentmode" />
											</td>
										</tr>
										<tr>
											<td align="right">
												开票信息：
											</td>
										  <td>
												<windrp:select key="InvoiceType" name="invmsg" p="n|f" value=""/></td>
											<td align="right">
												发票抬头：
											</td>
											<td>
												<input name="tickettitle" type="text" id="tickettitle"
													size="35" onClick="selectCTitle(this, $F('cid'))">
												<br />
												<div
													style="height: 100px; overflow-y: yes; width: 195px; overflow: hidden; text-overflow: ellipsis; display: none; border: 1px solid #CCCCCC; font-size: 12px; POSITION: absolute; Z-INDEX: 9999"
													id="selecttitle"></div>
											</td>
											<td align="right">
												客户方单据号：
											</td>
											<td>
												<input name="customerbillid" type="text" id="customerbillid">
											</td>
										</tr>
										<tr>
											<td align="right">
												送货机构：
											</td>
										  <td><input name="equiporganid" type="hidden" id="equiporganid" value="${makeorganid}">
              <input name="ename" type="text" id="ename" value="<windrp:getname key='organ' value='${makeorganid}' p='d'/>" size="30" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a><span class="STYLE1">*</span>											</td>
											<td align="right">
												客户来源：
											</td>
											<td>
												<windrp:select key="Source" name="source" p="n|d" />
											</td>
											<td align="right">&nbsp;
												
											</td>
											<td>&nbsp;
												
											</td>
										</tr>
										<tr>
											<td align="right">
												是否发送短信：
											</td>
											<td>
												<input type="radio" name="sendmsg" value="1">
												是
												<input type="radio" name="sendmsg" value="0" checked>
												否&nbsp;
											</td>
											<td align="right">
												是否自动发送：
											</td>
											<td>
												<input type="radio" name="autosend" value="1">
												是
												<input type="radio" name="autosend" value="0" checked>
												否
											</td>
											<td align="right">&nbsp;
												
											</td>
											<td>
												&nbsp;&nbsp;
											</td>
										</tr>
									</table>
								</fieldset>


								<table width="100%" border="0" cellpadding="0" cellspacing="1">
									<tr>
										<td width="100%">
											<table width="198" border="0" cellpadding="0" cellspacing="1">
												<tr align="center" class="back-blue-light2">
													<td width="73">
														<img src="../images/CN/selectp.gif" border="0"
															style="cursor: pointer"
															onClick="SupperSelect(dbtable.rows.length)">
													</td>
													<td width="72">
													<img src="../images/CN/selectf.gif" border="0"
															style="cursor: pointer"
															onClick="SupperSelectFee()">														
													</td>
													<td width="72">
													<img src="../images/CN/selectz.gif" border="0"
															style="cursor: pointer"
															onClick="SupperSelectPresent()">
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
										<td width="2%">
											<input type="checkbox" name="checkall" value="on"
												onClick="Check();">
										</td>
										<td width="8%">
											产品编号
										</td>
										<td width="11%">
											产品名称
										</td>
										<td width="14%">
											规格型号
										</td>
										<td width="3%">
											单位
										</td>
										<td width="11%">
											出货仓库
										</td>
										<!--<td width="6%">批次</td>	-->
										<td width="8%">
											单价
										</td>
										<td width="7%">
											税后单价
										</td>
										<td width="7%">
											数量
										</td>
										<td width="6%">
											折扣率
										</td>
										<td width="6%">
											税率
										</td>
										<td width="9%">
											金额
										</td>
									</tr>
								</table>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr align="center" class="table-back">
										<td width="20">
											<a href="javascript:deleteR();"><img
													src="../images/CN/del.gif" border="0">
											</a>
										</td>
										<td width="11%" align="center">&nbsp;
											
										</td>
										<td width="7%" align="center">&nbsp;
											
										</td>
										<td width="69%" align="right">
											<input type="button" name="button" value="金额小计"
												onClick="SubTotal();TotalSum();">
											：
										</td>
										<td width="10%" align="center">
											<input name="totalsum" type="text" id="totalsum" size="10"
												maxlength="10">
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
											<textarea name="remark" cols="180" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false"></textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
								<input type="button" name="Submit" onClick="ChkValue();" value="确定">
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
