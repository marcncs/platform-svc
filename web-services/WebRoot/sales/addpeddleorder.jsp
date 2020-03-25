<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
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
		var m=x.insertCell(12);	
		

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
		m.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"16\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"35\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"6\" readonly>";
        e.innerHTML="<input name=\"salesort\" type=\"hidden\" id=\"salesort\"><input name=\"unitid\" type=\"hidden\" id=\"unitid\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"4\" readonly>";
		f.innerHTML="<input name=\"warehouseid\" type=\"hidden\" id=\"warehouseid\"><input name=\"warehousename\" type=\"text\" id=\"warehousename\" size=\"10\" readonly>";
		//g.innerHTML="<input name=\"cost\" type=\"hidden\" id=\"cost\"><input name=\"batch\" type=\"text\" id=\"batch\" size=\"12\" readonly>";
        g.innerHTML="<input name=\"wise\" type=\"hidden\" id=\"wise\"><input name=\"cost\" type=\"hidden\" id=\"cost\"><input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"8\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\" readonly>";
		h.innerHTML="<input name=\"taxunitprice\" type=\"text\" id=\"taxunitprice\" value=\"0.00\" size=\"8\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        i.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"0\" size=\"8\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
		j.innerHTML="<input name=\"isidcode\" type=\"hidden\" id=\"isidcode\"><input name=\"codes\" type=\"hidden\" id=\"codes\"><a href=\"#\" onclick=\"toaddidcode(this)\"><img src=\"../images/CN/record.gif\" width=\"19\" height=\"20\" border=\"0\" title=\"录入序号\"></a>";
        k.innerHTML="<input name=\"discount\" type=\"text\" id=\"discount\" size=\"4\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\" readonly>%";
		l.innerHTML="<input name=\"taxrate\" type=\"text\" id=\"taxrate\" size=\"4\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">%";
		m.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"8\" maxlength=\"10\">";
		i++;
    chebox=document.all("che");  //計算total值
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
//录入序号
function toaddidcode(obj){	
	var rowx=getRowNo(obj);
	var isidcode;
	var productid;
	var quantity;
	var codes;
	if ( dbtable.rows.length <= 2 ){
		isidcode= document.validateProvide.item("isidcode").value;
	 	productid= document.validateProvide.item("productid").value;
		quantity= document.validateProvide.item("quantity").value;
		codes= document.validateProvide.item("codes").value;
	}else{
		isidcode= document.validateProvide.item("isidcode")[rowx-2].value;
		productid = document.validateProvide.item("productid")[rowx-2].value
		quantity = document.validateProvide.item("quantity")[rowx-2].value
		codes = document.validateProvide.item("codes")[rowx-2].value
	}	
	if ( isidcode != 1){
		alert("该产品不需要序号管理！");
		return;
	}
	window.open("../sales/toAddPeddleOrderIdcodeAction.do?pid="+productid+"&qt="+quantity+"&codes="+codes+"&rowx="+rowx,"newwindow","height=400,width=600,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
}



function SupperSelect(rowx){
var cid=document.validateProvide.cid.value;
var icid=document.validateProvide.invmsg.value;
//var organid=document.validateProvide.equiporganid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	//if(organid==null||organid=="")
	//{
	//	alert("请选择送货机构！");
	//	return;
	//}

	window.open("../common/selectSaleOrderProductAction.do?cid="+cid+"&icid="+icid,"","height=600,width=800,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");

}

function SupperSelectPresent(rowx){
var cid=document.validateProvide.cid.value;
var icid=document.validateProvide.invmsg.value;
//var organid=document.validateProvide.equiporganid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	//if(organid==null||organid=="")
	//{
	//	alert("请选择送货机构！");
	//	return;
	//}

	window.open("selectSaleOrderPresentAction.do?cid="+cid+"&icid="+icid,"","height=600,width=800,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");

}

function SupperSelectFee(rowx){
var cid=document.validateProvide.cid.value;
var icid=document.validateProvide.invmsg.value;
//var organid=document.validateProvide.equiporganid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	///if(organid==null||organid=="")
	//{
	//	alert("请选择送货机构！");
	//	return;
	//}

	window.open("selectSaleOrderFeeAction.do?cid="+cid+"&icid="+icid,"","height=600,width=800,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");

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
		document.validateProvide.item("unitid").value =obj.unitid;
		document.validateProvide.item("unit").value =obj.countunit;
		document.validateProvide.item("quantity").value=1;
		document.validateProvide.item("unitprice").value =obj.price;
		document.validateProvide.item("taxunitprice").value =obj.taxprice;
		document.validateProvide.item("warehouseid").value =obj.warehouseid;
		document.validateProvide.item("warehousename").value =obj.warehousename;
		document.validateProvide.item("discount").value =obj.discount;
		document.validateProvide.item("taxrate").value =obj.taxrate;
		document.validateProvide.item("wise").value =obj.wise;
		document.validateProvide.item("isidcode").value =obj.isidcode;
		SubTotal();
		TotalSum();
	}else{
		document.validateProvide.item("productid")[l-2].value =obj.productid;
		document.validateProvide.item("productname")[l-2].value =obj.productname;
		document.validateProvide.item("specmode")[l-2].value=obj.specmode;
		document.validateProvide.item("unitid")[l-2].value =obj.unitid;
		document.validateProvide.item("unit")[l-2].value =obj.countunit;
		document.validateProvide.item("quantity")[l-2].value=1;
		document.validateProvide.item("unitprice")[l-2].value =obj.price;
		document.validateProvide.item("taxunitprice")[l-2].value =obj.taxprice;
		document.validateProvide.item("warehouseid")[l-2].value =obj.warehouseid;
		document.validateProvide.item("warehousename")[l-2].value =obj.warehousename;
		document.validateProvide.item("discount")[l-2].value =obj.discount;
		document.validateProvide.item("taxrate")[l-2].value =obj.taxrate;
		document.validateProvide.item("wise")[l-2].value =obj.wise;
		document.validateProvide.item("isidcode")[l-2].value =obj.isidcode;
		SubTotal();
		TotalSum();
	}
}
//--------------------------------end -----------------------	


function deleteR(){
    if(chebox!=null){
      if(document.all('dbtable').rows.length==2){
      if(chebox.checked){
         document.all('dbtable').deleteRow(1);  //Row 行是從0開始
      }
    }else{
      for(var i=1;i<=chebox.length;i++){
                if (chebox[i-1].checked) {
 
          document.getElementById('dbtable').deleteRow ( i);
        i=i-1;
        }
 
      }
        }
    }
}


function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	if((dbtable.rows.length-1) <=1){
		var total=(document.forms[0].item("taxunitprice").value)*(document.forms[0].item("quantity").value);
		sum=total*document.forms[0].item("discount").value/100*(1+document.forms[0].item("taxrate").value/100);
		//sum=sum+parseFloat(document.forms[0].item("kickback").value);
		document.validateProvide.item("subsum").value=formatCurrency(sum);
	}else{
		for(var m=0;m<rowslength;m++){
			var total=(document.forms[0].item("taxunitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			sum=total*document.forms[0].item("discount")(m).value/100*(1+document.forms[0].item("taxrate")(m).value/100);
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
	document.validateProvide.factsum.value=formatCurrency(totalsum);
}

function SelectCustomer(){
	var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrollbars=yes;");
	document.validateProvide.cid.value=c.cid;
	document.validateProvide.cname.value=c.cname;
	document.validateProvide.cmobile.value=c.mobile;
	document.validateProvide.decidemantel.value=c.officetel;
	document.validateProvide.tickettitle.value =c.tickettitle;
	//setSelectValue('saledept',c.dept);
	//setSelectValue('saleid',c.saleid);
	//setSelectValue('paymentmode',c.paymentmode);
	//setSelectValue('transportmode',c.transportmode);
	getLinkmanBycid(c.cid);
}



function SelectLinkman(){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	var lk=showModalDialog("../common/toSelectLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.validateProvide.decideman.value=lk.lname;
	document.validateProvide.decidemantel.value=lk.officetel;
	//document.validateProvide.transportaddr.value=lk.ltransportaddr;	
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

	function ChkValue(){
        var cname = document.validateProvide.cname;
		var decideman = document.validateProvide.decideman;
		
		if(cname.value==null||cname.value==""){
			alert("客户不能为空!");
			return false;
		}
		
		if(decideman.value==null||decideman.value==""){
			alert("收货人不能为空!");
			return false;
		}
		
		//检测序号
		var isidcode = document.validateProvide.item("isidcode");
		var codes = document.validateProvide.item("codes");
		var productname = document.validateProvide.item("productname");
		var quantity = document.validateProvide.item("quantity");
		if (isidcode==null){
			alert("请选择产品!");
			return false;
		}else{
			if (isidcode.length){
				for(r=0; r<isidcode.length; r++){
					if ( isidcode[r].value==1 ){					
						if ( codes[r].value=='' ){
							alert(productname[r].value+"必须录入序号!");
							return false;
						}else{
							if ( quantity[r].value > codes[r].value.split(",").length){
								alert(productname[r].value+"序号数量不正确!");
								return false;
							}
						}						
					}
				}
			}else{
				if ( isidcode.value==1 ){
					if ( codes.value=='' ){
						alert(productname.value+"必须录入序号!");
						return false;
					}else{
						if ( quantity.value > codes.value.split(",").length){
							alert(productname.value+"序号数量不正确!");
							return false;
						}
					}	
				}
			}
		}
		
		validateProvide.submit();
		//return true;
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
			setDefaultValue(c.cid, c.cname, '', c.mobile, c.officetel,c.ratename,c.integral);	
			getLinkmanBycid(c.cid);
			getReceivemanBycid(c.cid);
			return;
		}		
		if ( cur == undefined ){
			setDefaultValue('', '', '', '', '');	
		}else{
			setDefaultValue(cur.cid,cur.cname,lk.name,cur.mobile,cur.officetel,cur.ratename,cur.integral);			
		}
	}
	function setDefaultValue(cid, cname, decideman, mobile, officetel,rate,integral){
		document.validateProvide.cid.value=cid;
		document.validateProvide.cname.value=cname;
		document.validateProvide.decideman.value=decideman;
		document.validateProvide.cmobile.value=mobile;
		document.validateProvide.decidemantel.value=officetel;
		document.getElementById("rate").innerHTML=rate;
		document.getElementById("cintegral").innerHTML=integral;

	}



	function RPIDToSearch(){
		var cid=document.validateProvide.cid.value;
		if(cid==null||cid==""){
			alert("请选择客户！");
			return;
		}
		var rpid = document.validateProvide.RPID.value;
		var icid = document.validateProvide.invmsg.value;
		//alert(rpid);
		if(event.keyCode==13){
		//alert("aa");
		document.getElementById('RPID').select();
		getProductByRPID(rpid,cid,icid);
		}
		//alert("bb");
	}


	function getProductByRPID(rpid,cid,icid){
	   var url = "../sales/getProductByRPIDAjax.do?RPID="+rpid+"&CID="+cid+"&icid="+icid;
	   //document.getElementById("require").style.display="";
	   //document.getElementById("require").innerHTML="正在读取数据...";
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse}
                    );
	}

	function showResponse(originalRequest){
	var prod = eval('(' + originalRequest.responseText + ')');
	var obj=prod.product;
	addItemValue(obj);
	
	}
	
	
	
	function BarToSearch(){
		var cid=document.validateProvide.cid.value;
		var rpid = document.validateProvide.RPID.value;
		var icid = document.validateProvide.invmsg.value;
		//alert(rpid);
		if(event.keyCode==13){
		//alert("aa");
		document.getElementById('RPID').select();
		getProductByRPID(rpid,cid,icid);
		}
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

	if (taxrate.length){
			for ( r=0; r<taxrate.length; r++){
				taxrate[r].value=ivrate;
				if(ivrate >0){
				//alert(formatCurrency(parseInt(unitprice[r].value) + parseInt(unitprice[r].value) * ivrate /100),-1);
					taxunitprice[r].value = ForDight(formatCurrency(parseInt(unitprice[r].value) + parseInt(unitprice[r].value) * ivrate /100),-1);
					
				}else{
					taxunitprice[r].value = formatCurrency(parseInt(unitprice[r].value));
				}
			}
		}else{
				taxrate.value=ivrate;
				if(ivrate >0){
					taxunitprice.value = ForDight(formatCurrency(parseInt(unitprice.value) + parseInt(unitprice.value) * ivrate /100),-1);
				}else{
					taxunitprice.value = formatCurrency(parseInt(unitprice.value));
				}
		}
	SubTotal();
	TotalSum();
		
}


function FactBack(){
	var totalsum = document.validateProvide.totalsum;
	var factsum = document.validateProvide.factsum;
	var backsum = document.validateProvide.backsum;
	backsum.value = parseFloat(totalsum.value)-parseFloat(factsum.value);
}

function LookIntegral(){
	var cid = $F('cid');
	window.open("../assistant/listCIntegralDealAllAction.do?ct.cid="+cid,"","height=650,width=1024,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes");
	}
	
	function LookPeddleOrder(){
	var cid = $F('cid');
	window.open("../sales/listPeddleOrderAction.do?CID="+cid,"","height=650,width=1024,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes");
	}

</script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>

</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增零售单</td>
          <td width="772" valign="bottom">&nbsp;</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="addPeddleOrderAction.do" onSubmit="return ChkValue();">
        <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr> 
            <td >
			
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本资料</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="11%"  align="right">会员编号：</td>
          <td width="23%"><input name="cid" type="text" id="cid" onKeyUp="getCustomerByWhere(this.value,1)"></td>
          <td width="10%" align="right">会员名称：</td>
          <td width="22%"><input name="cname" type="text" id="cname" onKeyUp="getCustomerByWhere(this.value,2)"><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
		  <a href="#" onClick="javascript:addNewMember();"><img src="../images/CN/add.gif" width="19" height="18" border="0" align="absmiddle" title="新增会员"></a></td>
	      <td width="9%" align="right">收货人：</td>
	      <td width="25%"><input name="decideman" type="text" id="decideman" readonly><a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
	  </tr>
	  <tr>
	    <td align="right">会员手机：</td>
	    <td ><input name="cmobile" type="text" id="cmobile"  maxlength="11" onKeyUp="getCustomerByWhere(this.value,3)"></td>
	    <td  align="right">会员电话：</td>
	    <td><input name="decidemantel" type="text" id="decidemantel" onKeyUp="getCustomerByWhere(this.value,4)"></td>
	    <td align="right">是否挂帐：</td>
	    <td><windrp:select key="YesOrNo" name="IsAccount" p="n|f" value="0"/></td>
	  </tr>
	  <tr>
	    <td align="right">付款方式：</td>
	    <td ><windrp:paymentmode name="paymentmode" /></td>
	    <td  align="right">开票信息：</td>
	    <td><select name="invmsg">
          <logic:iterate id="c" name="icls">
            <option value="${c.id}">${c.ivname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">发票抬头：</td>
	    <td><input name="tickettitle" type="text" id="tickettitle"
													size="35" onClick="selectCTitle(this, $F('cid'))"></td>
	    </tr>
	  <tr>
	    <td align="right">会员级别：</td>
	    <td ><span id="rate"> </span></td>
	    <td  align="right">积分：</td>
	    <td><span id="cintegral"> </span></td>
	    <td align="right">积分明细：</td>
	    <td><a href="javascript:LookIntegral();">查看</a></td>
	    </tr>
	  <tr>
	    <td align="right">历史订单：</td>
	    <td ><a href="javascript:LookPeddleOrder();">查看</a></td>
	    <td  align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr > 
                  <td width="100%" ><table width="198" border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                      <td width="73"><img src="../images/CN/selectp.gif" border="0"
															style="cursor: pointer"
															onClick="SupperSelect(dbtable.rows.length)"> </td>
                      <td width="72"><img src="../images/CN/selectf.gif" border="0"
															style="cursor: pointer"
															onClick="SupperSelectFee()"> </td>
                      <td width="72"><img src="../images/CN/selectz.gif" border="0"
															style="cursor: pointer"
															onClick="SupperSelectPresent()"> </td>
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
                <td width="18%">规格型号</td>
                <td width="6%"> 单位</td>
                <td width="6%">出货仓库</td>
                <!-- <td width="7%">出货仓库</td>	-->
				<!--<td width="6%">批次</td>				-->
				<td width="6%">单价</td>
                <td width="9%">税后单价</td>
                <td width="9%"> 数量</td>  
				<td width="9%"> 序号</td>                
				<td width="6%"> 折扣率</td>
				<td width="9%"> 税率</td>
				<td width="10%"> 金额</td>
                </tr>
            </table>
			<table width="100%" height="60"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="39" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="136" align="center">&nbsp;</td>
                <td width="86" align="center">&nbsp;</td>
                <td width="854" align="right"><input type="button" name="button" value="应收金额" onClick="TotalSum();">：</td>
                <td width="126" align="center"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10"></td>
              </tr>
              <tr align="center" class="table-back">
                <td >&nbsp;</td>
                <td align="center">&nbsp;</td>
                <td align="center">&nbsp;</td>
                <td align="right">实收金额：</td>
                <td align="center"><input name="factsum" type="text" id="factsum" size="10" maxlength="10" onClick="this.select();" onChange="FactBack();" onKeyDown="if(event.keyCode==13)event.keyCode=9 "></td>
              </tr>
              <tr align="center" class="table-back">
                <td >&nbsp;</td>
                <td align="center">&nbsp;</td>
                <td align="center">&nbsp;</td>
                <td align="right">找零：</td>
                <td align="center"><input name="backsum" type="text" id="backsum" size="10" maxlength="10" onFocus="FactBack();"></td>
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
            <td  align="center"><input type="button" name="Submit" onClick="ChkValue();"   value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
        
      </table>
      </form> 
</body>
</html>
