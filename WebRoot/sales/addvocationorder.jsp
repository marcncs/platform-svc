<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDateTime.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/Currency.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/common.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectctitle.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
 var iteration=0;
 var i=1;
 var chebox=null;
  function addRow(){
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
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"16\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"45\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"6\" readonly>";
        e.innerHTML="<input name=\"salesort\" type=\"hidden\" id=\"salesort\"><input name=\"cost\" type=\"hidden\" id=\"cost\"><input name=\"unitid\" type=\"hidden\" id=\"unitid\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"4\" readonly>";
		f.innerHTML="<input name=\"warehouseid\" type=\"hidden\" id=\"warehouseid\"><input name=\"wise\" type=\"hidden\" id=\"wise\"><input name=\"warehousename\" type=\"text\" id=\"warehousename\" size=\"10\" readonly>";
		//g.innerHTML="<input name=\"cost\" type=\"hidden\" id=\"cost\"><input name=\"batch\" type=\"text\" id=\"batch\" size=\"12\" readonly>";
        g.innerHTML="<input name=\"orgunitprice\" type=\"hidden\" id=\"orgunitprice\"><input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" readonly size=\"8\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\" >";
		h.innerHTML="<input name=\"taxunitprice\" type=\"text\" id=\"taxunitprice\" value=\"0.00\" size=\"8\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
		i.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        j.innerHTML="<input name=\"discount\" type=\"text\" id=\"discount\" size=\"4\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\" readonly>%";
		k.innerHTML="<input name=\"taxrate\" type=\"text\" id=\"taxrate\" size=\"4\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\" readonly>%";
		l.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"8\" maxlength=\"10\">";
		i++;
    chebox=document.all("che");  //計算total值

 	//SubTotal(dbtable.rows);
	//TotalSum();
}

//得到行对象 
function getRowObj(obj) 
{ 
var i = 0; 
while(obj.tagName.toLowerCase() != "tr"){ 
obj = obj.parentNode; 
if(obj.tagName.toLowerCase() == "table")return null; 
} 
return obj; 
} 

//根据得到的行对象得到所在的行数 
function getRowNo(obj){ 
var trObj = getRowObj(obj); 
var trArr = trObj.parentNode.children; 
for(var trNo= 0; trNo < trArr.length; trNo++){ 
if(trObj == trObj.parentNode.children[trNo]){ 
return trNo+1; 
} 
} 
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

	window.open("selectSaleOrderProductAction.do?cid="+cid+"&organid="+organid+"&icid="+icid,"","height=600,width=800,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");

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

	window.open("selectSaleOrderPresentAction.do?cid="+cid+"&organid="+organid+"&icid="+icid,"","height=600,width=800,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");

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

	window.open("selectSaleOrderFeeAction.do?cid="+cid+"&organid="+organid+"&icid="+icid,"","height=600,width=800,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");

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
					document.validateProvide.item("quantity")[k].value = vquantity + 1;
					SubTotal();
					TotalSum();
					return;
				}
			}
		}else{
			if ( vproductid.value == obj.productid && vwarehouseid.value == obj.warehouseid){
					var vquantity = parseInt(document.validateProvide.item("quantity").value);
					document.validateProvide.item("quantity").value = vquantity + 1;
					SubTotal();
					TotalSum();
					return;
			}
		}		
	}
	
	addRow();
	var l = dbtable.rows.length;
	if ( l < 3){
		document.validateProvide.item("productid").value =obj.productid;
		document.validateProvide.item("productname").value =obj.productname;
		document.validateProvide.item("specmode").value=obj.specmode;
		document.validateProvide.item("salesort").value=obj.salesort;
		document.validateProvide.item("unitid").value =obj.unitid;
		document.validateProvide.item("unit").value =obj.countunit;
		document.validateProvide.item("quantity").value=1;
		document.validateProvide.item("orgunitprice").value =obj.price;
		document.validateProvide.item("unitprice").value =obj.price;
		document.validateProvide.item("taxunitprice").value =obj.taxprice;
		document.validateProvide.item("warehouseid").value =obj.warehouseid;
		document.validateProvide.item("warehousename").value =obj.warehousename;
		//document.validateProvide.item("batch").value =obj.batch;
		document.validateProvide.item("discount").value =obj.discount;
		document.validateProvide.item("taxrate").value =obj.taxrate;
		document.validateProvide.item("wise").value =obj.wise;
		SubTotal();
		TotalSum();
	}else{
		document.validateProvide.item("productid")[l-2].value =obj.productid;
		document.validateProvide.item("productname")[l-2].value =obj.productname;
		document.validateProvide.item("specmode")[l-2].value=obj.specmode;
		document.validateProvide.item("salesort")[l-2].value=obj.salesort;
		document.validateProvide.item("unitid")[l-2].value =obj.unitid;
		document.validateProvide.item("unit")[l-2].value =obj.countunit;
		document.validateProvide.item("quantity")[l-2].value=1;
		document.validateProvide.item("orgunitprice")[l-2].value =obj.price;
		document.validateProvide.item("unitprice")[l-2].value =obj.price;
		document.validateProvide.item("taxunitprice")[l-2].value =obj.taxprice;
		document.validateProvide.item("warehouseid")[l-2].value =obj.warehouseid;
		document.validateProvide.item("warehousename")[l-2].value =obj.warehousename;
		//document.validateProvide.item("batch")[l-2].value =obj.batch;
		document.validateProvide.item("discount")[l-2].value =obj.discount;
		document.validateProvide.item("taxrate")[l-2].value =obj.taxrate;
		document.validateProvide.item("wise")[l-2].value =obj.wise;
		SubTotal();
		TotalSum();
	}
}
//--------------------------------end -----------------------	


function deleteR(){
 // 第一行不能放 alert EX.alert("document.table1.chebox[0].checked="+chebox[0].checked);
 //用if來解決chebox在第一次load時其為undifined而不能delete時所使用的方法

    if(chebox!=null){
      if(document.all('dbtable').rows.length==2){
      if(chebox.checked){
         document.all('dbtable').deleteRow(1);  //Row 行是從0開始
      }
    }else{
        //    for (i=chebox.length;i>0;i--){
      for(var i=1;i<=chebox.length;i++){
                if (chebox[i-1].checked) {
 
          document.getElementById('dbtable').deleteRow ( i);
        i=i-1;
        }
 
      }
        }
    }
}

function RemoveProduct(){
	checkAll();
	deleteR();
}


function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	if((dbtable.rows.length-1) <=1){
		var total=(document.forms[0].item("taxunitprice").value)*(document.forms[0].item("quantity").value);
		sum=total*document.forms[0].item("discount").value/100;//*(1+document.forms[0].item("taxrate").value/100);
		//sum=sum+parseFloat(document.forms[0].item("kickback").value);
		document.validateProvide.item("subsum").value=formatCurrency(sum);
	}else{
		for(var m=0;m<rowslength;m++){
			var total=(document.forms[0].item("taxunitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			sum=total*document.forms[0].item("discount")(m).value/100;//*(1+document.forms[0].item("taxrate")(m).value/100);
			//sum=sum+parseFloat(document.forms[0].item("kickback")(m).value);
			document.validateProvide.item("subsum")(m).value=formatCurrency(sum);	
		}
	}
}


function TotalSum(){
	var totalsum=0.00;
	//alert(dbtable.rows.length);
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
	var c=showModalDialog("selectVocationOrderCustomerAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrollbars=yes;");
	document.validateProvide.cid.value=c.cid;
	document.validateProvide.cname.value=c.cname;
	document.validateProvide.cmobile.value=c.mobile;
	document.validateProvide.decidemantel.value=c.officetel;
	document.validateProvide.tickettitle.value =c.tickettitle;
	//setSelectValue('saledept',c.dept);
	//setSelectValue('saleid',c.saleid);
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

function SelectLinkman(){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid==""){
		alert("请选择客户！");
		return;
	}
	var lk=showModalDialog("toSelectLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
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
	var lk=showModalDialog("toSelectLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
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

	
	function ShowSQ(tbr) {   // yy xx
var productid="";
if(tbr-2<=0){
	if(dbtable.rows.length<=2){
	productid=document.validateProvide.item("productid").value;
	}else{
	productid=document.validateProvide.item("productid")[tbr-2].value;
	}
}else{

productid=document.validateProvide.item("productid")[tbr-2].value;
}
	$("sq").style.visibility = "visible" ;
	$("sq").style.top = event.clientY;;
	$("sq").style.left = event.clientX;
	//$("require").removeChild($("require").getElementsByTagName("table")[0]);
	getStockQuantity(productid);
}
function HiddenSQ(){
	sq.style.visibility = "hidden";
	/*
	for(var b=0;b<require.rows.length;b++){
		 	document.getElementById('require').deleteRow(b);
		 	b=b-1;
		 	}
	*/ 
	$("stock").removeChild($("stock").getElementsByTagName("table")[0]);
}

function getStockQuantity(productid){
	   var url = "../warehouse/getStockQuantityAjax.do?productid="+productid;
	   //document.getElementById("require").style.display="";
	   //document.getElementById("require").innerHTML="姝ｅ湪璇诲彇鏁版嵁...";
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showStock}
                    );
}

function showStock(originalRequest){
	
	var product = originalRequest.responseXML.getElementsByTagName("product");
	var requireHTML = '<table id="stock" width="100%"  border="0" cellpadding="3" cellspacing="0">';

		for(var i=0;i<product.length;i++){
			var rm = product[i];
			var wh = rm.getElementsByTagName("wh")[0].firstChild.data;
			var qt =rm.getElementsByTagName("qt")[0].firstChild.data;
			var bc = rm.getElementsByTagName("bc")[0].firstChild.data;
			//var murl =rm.getElementsByTagName("murl")[0].firstChild.data;
			//alert(ispd);
			requireHTML  += "<tr><td width='40%'>"+wh+"</td><td width='30%'>"+bc+" </td><td width='30%' align='right'>"+qt+" </td></tr>";
			//addRows(productname,productcount,stockcount);
		}

		$("stock").innerHTML = requireHTML + "</table>";

		//document.getElementById("result").style.display="none";
	}
	
	function getCustomerByWhere(objvalue, temp){
		if (event.keyCode != 13) { 			
			return;
		} 
		var url ="";
		if ( temp == 1 ){
			 url = 'ajaxCustomerAction.do?rate=5&temp=1&cid='+objvalue;
		}else if ( temp == 2 ){
			 url = 'ajaxCustomerAction.do?rate=5&temp=2&cname='+objvalue;
		}else if ( temp == 3 ){
			 url = 'ajaxCustomerAction.do?rate=5&temp=3&mobile='+objvalue;
		}else if ( temp == 4 ){
			 url = 'ajaxCustomerAction.do?rate=5&temp=4&officetel='+objvalue;
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
			//alert(c.tickettitle);
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
	window.open("../assistant/listCIntegralDealAllAction.do?ct.cid="+cid,"","height=650,width=1024,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes");
	}
	
	function LookOrder(){
	var cid = $F('cid');
	window.open("../sales/listSaleOrderAction.do?CID="+cid,"","height=650,width=1024,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes");
	}
</script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>

</head>


<body onLoad="CalledCenterCustomer();">

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增行业零售单</td>
          <td width="772" valign="bottom"><table width="120"  border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td align="center" class="back-gray"><a href="../sales/toAddVocationOrderAction.do">新增</a></td>
              <td align="center" class="back-gray"><a href="../sales/listVocationOrderAction.do">查询</a></td>
            </tr>
          </table></td>
        </tr>
      </table>
       <form name="validateProvide" method="post" action="addVocationOrderAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr> 
            <td >
			
	<fieldset align="center"> <legend>
      <table width="62" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="62">订货人信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right">会员编码：</td>
          <td width="24%"><input name="cid" type="text" id="cid" onKeyUp="getCustomerByWhere(this.value,1)" ></td>
          <td width="10%" align="right">会员名称：</td>
          <td width="22%"><input name="cname" type="text" id="cname" onKeyUp="getCustomerByWhere(this.value,2)"><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle" title="查找会员"></a><a href="#" onClick="javascript:addNewMember();"><img src="../images/CN/add.gif" width="17" height="16" border="0" align="absmiddle" title="新增会员"></a></td>
	      <td width="11%" align="right">订货人：</td>
	      <td width="23%"><input name="decideman" type="text" id="decideman" readonly>
            <a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
	  </tr>
	  <tr>
	    <td  align="right">会员手机：</td>
	    <td><input name="cmobile" type="text" id="cmobile"  maxlength="11" onKeyUp="getCustomerByWhere(this.value,3)"></td>
	    <td align="right">会员电话：</td>
	    <td><input name="decidemantel" type="text" id="decidemantel" onKeyUp="getCustomerByWhere(this.value,4)"></td>
	    <td align="right">会员级别：</td>
	    <td><span id="rate"> </span></td>
	  </tr>
	  <tr>
	    <td  align="right">积分：</td>
	    <td><span id="cintegral"> </span></td>
	    <td align="right">积分明细：</td>
	    <td><a href="javascript:LookIntegral();">查看</a></td>
	    <td align="right">历史订单：</td>
	    <td><a href="javascript:LookOrder();">查看</a></td>
	    </tr>
	  </table>
</fieldset>



<fieldset align="center"> <legend>
      <table width="63" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="63">收货人信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right">收货人：</td>
          <td width="24%"><input name="receiveman" type="text" id="receiveman">
            <a href="javascript:SelectReceiveman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a><a href="javascript:ToAddLinkman();"><img src="../images/CN/add.gif" width="17" height="16" border="0" align="absmiddle" title="新增收货人"></a></td>
          <td width="10%" align="right">收货人手机：</td>
          <td width="22%"><input name="receivemobile" type="text" id="receivemobile" ></td>
          <td width="11%" align="right">收货人电话：</td>
	      <td width="23%"><input name="receivetel" type="text" id="receivetel"></td>
	  </tr>
	  <tr>
	    <td  align="right">收货地址：</td>
	    <td colspan="5"><input name="transportaddr" type="text" id="transportaddr" size="60" onClick="selectCTitle(this, $F('cid'),'1')">
	    <br/><div style="height:100px;overflow-y:yes;width:320px;overflow: hidden;text-overflow:ellipsis;display:none;border:1px solid #CCCCCC;font-size:12px;POSITION: absolute; Z-INDEX: 9998 " id="select"></div></td>
	    </tr>
	  </table>
	</fieldset>
	
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>其它信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right">交货日期：</td>
          <td width="24%"><input name="consignmentdate" type="text" id="consignmentdate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" size="8" readonly>
            <span class="STYLE1">
            <input name="consignmenttime" type="text" id="consignmenttime" onFocus="javascript:selectTime(this)" value="18:00:00"  size="8" readonly>
            *</span></td>
          <td width="11%" align="right">发运方式：</td>
          <td width="21%">${transportmodeselect}</td>
          <td width="9%" align="right">付款方式：</td>
	      <td width="23%">${paymentmodeselect}</td>
	  </tr>
	  <tr>
	    <td  align="right">开票信息：</td>
	    <td><select name="invmsg" onChange="ChangeInvMsg();">
                    <logic:iterate id="c" name="icls">
                      <option value="${c.id}">${c.ivname}</option>
                    </logic:iterate>
                  </select>
	    (先选择产品,再选开票)</td>
	    <td align="right">发票抬头：</td>
	    <td><input name="tickettitle" type="text" id="tickettitle" size="35" onClick="selectCTitle(this, $F('cid'))">
		<br/><div style="height:100px;overflow-y:yes;width:195px;overflow: hidden;text-overflow:ellipsis;display:none;border:1px solid #CCCCCC;font-size:12px;POSITION: absolute; Z-INDEX: 9999 " id="selecttitle"></div></td>
	    <td align="right">客户方单据号：</td>
	    <td><input name="customerbillid" type="text" id="customerbillid"></td>
	  </tr>
	  <tr>
	    <td  align="right">送货机构：</td>
	    <td><select name="equiporganid" id="equiporganid" onChange="RemoveProduct();">
           <option value="">请选择...</option>
		  <logic:iterate id="o" name="alos">
            <option value="${o.id}" ${o.id==equiporganid?"selected":""}><c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>${o.organname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">客户来源：</td>
	    <td>${sourcename}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
		<tr>
	    <td  align="right">是否发送短信：</td>
	    <td><input type="radio" name="sendmsg" value="1">
	      是
	        <input type="radio" name="sendmsg" value="0" checked>
	        否&nbsp;</td>
	    <td align="right">是否自动发送：</td>
	    <td><input type="radio" name="autosend" value="1">
	      是
	        <input type="radio" name="autosend" value="0" checked>
	        否</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr > 
                  <td width="100%" > <table width="198"  border="0" cellpadding="0" cellspacing="1">
                      <tr align="center" class="back-blue-light2">
                            <td width="73"><img src="../images/CN/findproduct.gif" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                            <td width="72"><a href="javascript:SupperSelectFee();">选择费用</a></td>
                            <td width="72"><a href="javascript:SupperSelectPresent();">选择赠品</a></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<td width="2%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="8%">产品编号</td>
                <td width="11%" > 产品名称</td>
                <td width="14%">规格型号</td>
                <td width="3%"> 单位</td>
                <td width="11%">出货仓库</td>
				<!--<td width="6%">批次</td>	-->			
				<td width="8%">单价</td>
                <td width="7%">税后单价</td>
                <td width="7%"> 数量</td>                
				<td width="6%"> 折扣率</td>
				<td width="6%"> 税率</td>
				<td width="9%"> 金额</td>
                </tr>
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="20" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="11%" align="center">&nbsp;</td>
                <td width="7%" align="center">&nbsp;</td>
                <td width="69%" align="right"><input type="button" name="button" value="金额小计" onClick="SubTotal();TotalSum();">：</td>
                <td width="10%" align="center"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10"></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center" ><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr >
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark"></textarea></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center"><input type="button" name="Submit" onClick="ChkValue();" value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
