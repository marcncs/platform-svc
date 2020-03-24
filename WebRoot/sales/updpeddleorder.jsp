<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/common.js"> </SCRIPT>
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
		//var l=x.insertCell(11);	
		

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
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"16\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"45\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"6\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"4\" readonly>";
		f.innerHTML="<input name=\"warehouseid\" type=\"hidden\" id=\"warehouseid\"><input name=\"wise\" type=\"hidden\" id=\"wise\"><input name=\"warehousename\" type=\"text\" id=\"warehousename\" size=\"10\" readonly>";
		//g.innerHTML="<input name=\"cost\" type=\"hidden\" id=\"cost\"><input name=\"batch\" type=\"text\" id=\"batch\" size=\"12\" readonly>";
        g.innerHTML="<input name=\"cost\" type=\"hidden\" id=\"cost\"><input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"8\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"0\" size=\"8\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        i.innerHTML="<input name=\"discount\" type=\"text\" id=\"discount\" size=\"4\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">%";
		j.innerHTML="<input name=\"taxrate\" type=\"text\" id=\"taxrate\" size=\"4\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">%";
		k.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"8\" maxlength=\"10\">";
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

function SelectWarehourse(rowobj){
	var pid="";
	var wise=0;
	rowx = getRowNo(rowobj);
	if ( dbtable.rows.length <= 2 ){
	 	pid= document.validateProvide.item("productid").value;
		wise= document.validateProvide.item("wise").value;
	}else{
		pid = document.validateProvide.item("productid")[rowx-2].value
		wise = document.validateProvide.item("wise")[rowx-2].value
	}
	if ( wise ==1 || wise ==2 ){
		alert("产品是费用或劳务,不需要选择仓库!");
		return;
	}
	var wbc=showModalDialog("toSelectProductStockpileAction.do?pid="+pid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	
	if(dbtable.rows.length <=2){
		document.validateProvide.item("warehouseid").value =wbc.warehouseid;
		document.validateProvide.item("warehousename").value =wbc.warehousename;
		document.validateProvide.item("batch").value =wbc.batch;
		document.validateProvide.item("cost").value =wbc.cost;
	}else{
		document.validateProvide.item("warehouseid")[rowx-2].value =wbc.warehouseid;
		document.validateProvide.item("warehousename")[rowx-2].value =wbc.warehousename;
		document.validateProvide.item("batch")[rowx-2].value =wbc.batch;
		document.validateProvide.item("cost")[rowx-2].value =wbc.cost;
	}
}	

	

function SupperSelect(rowx){
var cid=document.validateProvide.cid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}

	window.open("selectSaleOrderProductAction.do?cid="+cid,"","height=600,width=800,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");

}

//--------------------------------start -----------------------	
function addItemValue(obj){
	addRow();
	var l = dbtable.rows.length;
	if ( l < 3){

		document.validateProvide.item("productid").value =obj.productid;
		document.validateProvide.item("productname").value =obj.productname;
		document.validateProvide.item("specmode").value=obj.specmode;
		document.validateProvide.item("unitid").value =obj.unitid;
		document.validateProvide.item("unit").value =obj.countunit;
		document.validateProvide.item("quantity").value=0;
		document.validateProvide.item("unitprice").value =obj.price;
		document.validateProvide.item("warehouseid").value =obj.warehouseid;
		document.validateProvide.item("warehousename").value =obj.warehousename;
		document.validateProvide.item("batch").value =obj.batch;
		document.validateProvide.item("kickback").value =0.00;
		document.validateProvide.item("discount").value =obj.discount;
		document.validateProvide.item("taxrate").value =obj.taxrate;
		document.validateProvide.item("wise").value =obj.wise;
	}else{
		document.validateProvide.item("productid")[l-2].value =obj.productid;
		document.validateProvide.item("productname")[l-2].value =obj.productname;
		document.validateProvide.item("specmode")[l-2].value=obj.specmode;
		document.validateProvide.item("unitid")[l-2].value =obj.unitid;
		document.validateProvide.item("unit")[l-2].value =obj.countunit;
		document.validateProvide.item("quantity")[l-2].value=0;
		document.validateProvide.item("unitprice")[l-2].value =obj.price;
		document.validateProvide.item("warehouseid")[l-2].value =obj.warehouseid;
		document.validateProvide.item("warehousename")[l-2].value =obj.warehousename;
		document.validateProvide.item("batch")[l-2].value =obj.batch;
		document.validateProvide.item("kickback")[l-2].value =0;
		document.validateProvide.item("discount")[l-2].value =obj.discount;
		document.validateProvide.item("taxrate")[l-2].value =obj.taxrate;
		document.validateProvide.item("wise")[l-2].value =obj.wise;
	}
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


function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	if((dbtable.rows.length-1) <=1){
		var total=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value);
		sum=total*document.forms[0].item("discount").value/100*(1+document.forms[0].item("taxrate").value/100);
		sum=sum+parseFloat(document.forms[0].item("kickback").value);
		document.validateProvide.item("subsum").value=sum;
	}else{
		for(var m=0;m<rowslength;m++){
			var total=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			sum=total*document.forms[0].item("discount")(m).value/100*(1+document.forms[0].item("taxrate")(m).value/100);
			sum=sum+parseFloat(document.forms[0].item("kickback")(m).value);
			document.validateProvide.item("subsum")(m).value=sum;	
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
	document.validateProvide.totalsum.value=totalsum;
}

function SelectCustomer(){
	var c=showModalDialog("toSelectSaleOrderCustomerAction.do",null,"dialogWidth:18cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	document.validateProvide.cid.value=c.cid;
	document.validateProvide.cname.value=c.cname;
	setSelectValue('saledept',c.dept);
	setSelectValue('saleid',c.saleid);
	setSelectValue('paymentmode',c.paymentmode);
	setSelectValue('transportmode',c.transportmode);
	getLinkmanBycid(c.cid);
}

function SelectLinkman(){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	var lk=showModalDialog("toSelectLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.validateProvide.receiveman.value=lk.lname;
	document.validateProvide.tel.value=lk.ltel;
	document.validateProvide.transportaddr.value=lk.ltransportaddr;	
	setSelectValue('transit',lk.transit);
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
			setDefaultValue(c.cid, c.cname, '', c.mobile, c.officetel);	
			getLinkmanBycid(c.cid);
			getReceivemanBycid(c.cid);
			return;
		}		
		if ( cur == undefined ){
			setDefaultValue('', '', '', '', '');	
		}else{
			setDefaultValue(cur.cid, cur.cname, lk.name, cur.mobile, cur.officetel);			
		}
	}
	function setDefaultValue(cid, cname, decideman, mobile, officetel,addr){
		document.validateProvide.cid.value=cid;
		document.validateProvide.cname.value=cname;
		document.validateProvide.decideman.value=decideman;
		document.validateProvide.cmobile.value=mobile;
		document.validateProvide.decidemantel.value=officetel;

	}


	
	function SetCode(c){
		if(c=="r"){
			record.style.display = "";
			elect.style.display = "none";
			bar.style.display = "none";
		}
		if(c=="e"){
			record.style.display = "none";
			elect.style.display = "";
			bar.style.display = "none";
		}
		if(c=="b"){
			record.style.display = "none";
			elect.style.display = "none";
			bar.style.display = "";
		}
		
	}
	
	function RPIDToSearch(){
		var cid=document.validateProvide.cid.value;
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
	addRow();
	var l = dbtable.rows.length;
	//if(pd.length>=1){
	if ( l < 3){
		document.validateProvide.item("productid").value =obj.productid;
		document.validateProvide.item("productname").value =obj.psproductname;
		document.validateProvide.item("specmode").value=obj.psspecmode;
		document.validateProvide.item("unitid").value =obj.countunit;
		document.validateProvide.item("unit").value =obj.countunitname;
		document.validateProvide.item("quantity").value=1;
		document.validateProvide.item("unitprice").value =obj.price;
		document.validateProvide.item("warehouseid").value =obj.warehouseid;
		document.validateProvide.item("warehousename").value =obj.warehouseidname;
		document.validateProvide.item("discount").value =obj.discount;
		document.validateProvide.item("taxrate").value =obj.taxrate;
		document.validateProvide.item("wise").value =obj.wise;
		}else{
		document.validateProvide.item("productid")[l-2].value =obj.productid;
		document.validateProvide.item("productname")[l-2].value =obj.psproductname;
		document.validateProvide.item("specmode")[l-2].value=obj.psspecmode;
		document.validateProvide.item("unitid")[l-2].value =obj.countunit;
		document.validateProvide.item("unit")[l-2].value =obj.countunitname;
		document.validateProvide.item("quantity")[l-2].value=1;
		document.validateProvide.item("unitprice")[l-2].value =obj.price;
		document.validateProvide.item("warehouseid")[l-2].value =obj.warehouseid;
		document.validateProvide.item("warehousename")[l-2].value =obj.warehouseidname;
		document.validateProvide.item("discount")[l-2].value =obj.discount;
		document.validateProvide.item("taxrate")[l-2].value =obj.taxrate;
		document.validateProvide.item("wise")[l-2].value =obj.wise;
		}
		
	//}else{
		//alert("该编号不存在");
	//}

	}
	

</script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>

</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改零售单</td>
        </tr>
      </table>
       <form name="validateProvide" method="post" action="updPeddleOrderAction.do" onSubmit="return ChkValue();">
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr> <input name="id" type="hidden" id="id" value="${sof.id}">
	  	<td width="12%"  align="right">会员编号：</td>
          <td width="22%"><input name="cid" type="text" id="cid" value="${sof.cid}" onKeyUp="getCustomerByWhere(this.value,1)"></td>
          <td width="8%" align="right">客户名称：</td>
          <td width="20%"><input name="cname" type="text" id="cname" value="${sof.cname}" onKeyUp="getCustomerByWhere(this.value,2)" >
            <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
	      <td width="8%" align="right">收货人：</td>
	      <td width="30%"><input name="decideman" type="text" id="decideman" value="${sof.receiveman}" readonly>
            <a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
	  </tr>
	  <tr>
	    <td align="right">会员手机：</td>
	    <td ><input name="cmobile" type="text" id="cmobile"  maxlength="11" onKeyUp="getCustomerByWhere(this.value,3)" value="${sof.cmobile}"></td>
	    <td align="right">会员电话：</td>
	    <td><input name="decidemantel" type="text" id="decidemantel" onKeyUp="getCustomerByWhere(this.value,4)" value="${sof.receivetel}"></td>
	    <td align="right">开票信息：</td>
	    <td><select name="invmsg">
          <logic:iterate id="c" name="icls">
            <option value="${c.id}">${c.ivname}</option>
          </logic:iterate>
        </select></td>
	    </tr>
	  <tr>
	    <td align="right">出货仓库：</td>
	    <td >&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr > 
                  <td width="100%" ><table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                      <td width="60"><a href="javascript:SetCode('r')"><img src="../images/CN/record.gif" width="19"  border="0" title="手工录入存货编号"></a><a href="javascript:SetCode('e')"><img src="../images/CN/elect.gif" width="19"  border="0" title="选择"></a><a href="javascript:SetCode('b')"><img src="../images/CN/bar.gif" width="19"  border="0" title="条码扫描"></a></td>
                      <td id="record" style="display:none"><input name="RPID" type="text" id="RPID" size="15" title="录入编号后，按回车键继续录其它编号" onKeyUp="RPIDToSearch();" onClick="this.select();" tabindex=1></td>
                      <td id="elect" style="display:none">选择存货：<img src="../images/CN/find.gif" width="18" height="18" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                      <td id="bar"><input name="SBAR" type="text" id="SBAR" size="30"></td>
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
                <td width="7%">产品编号</td>
                <td width="15%" > 产品名称</td>
                <td width="16%">规格型号</td>
                <td width="3%"> 单位</td>
                <td width="14%">出货仓库</td>
				<td width="7%"> 数量</td>
				 <td width="6%"> 单价</td>
				 <td width="7%"> 折扣率</td>
				<td width="8%"> 税率</td>
                <td width="9%"> 金额</td>
                </tr>
				<c:set var="count" value="2"/>
            <logic:iterate id="p" name="als" > 
            <tr class="table-back">
				<td><input type="checkbox" value="${count}" name="che"></td>
                <td><input name="productid" type="text" id="productid" value="${p.productid}" size="16" readonly></td>
                <td ><input name="productname" type="text" value="${p.productname}" id="productname" size="45" readonly></td>
				<td><input name="specmode" type="text" value="${p.specmode}" id="specmode" size="6" readonly></td>
				<td><input name="unitid" type="hidden" value="${p.unitid}"><input name="unit" type="text" value="${p.unitidname}" id="unit" size="4" readonly></td>
                <td><input name="warehouseid" type="hidden" value="${p.warehouseid}" id="warehouseid"><input name="warehousename" type="text" value="${p.warehouseidname}" id="warehousename" size="10" readonly><a href="#" onClick="SelectWarehourse(this);"></a></td>
				<td><input name="quantity" type="text" value="${p.quantity}" onChange="SubTotal(${count});TotalSum();" id="quantity" size="8" maxlength="8">
                  <input name="cost" type="hidden" value="${p.cost}" id="cost"></td>
				<td><input name="unitprice" type="text" value="<fmt:formatNumber value='${p.unitprice}' pattern='0.00'/>" onChange="SubTotal(${count});TotalSum();" id="unitprice" size="8" maxlength="10"></td>
                
				<td><input name="discount" type="text" value="<fmt:formatNumber value='${p.discount}' pattern='0.00'/>" id="discount" size="4" onChange="SubTotal(${count});TotalSum();">
				%</td>
				<td><input name="taxrate" type="text" value="<fmt:formatNumber value='${p.taxrate}' pattern='0.00'/>" id="taxrate" size="4" onChange="SubTotal(${count});TotalSum();">
				%</td>
                <td><input name="subsum" type="text" value="<fmt:formatNumber value='${p.subsum}' pattern='0.00'/>" id="subsum" size="8" maxlength="10"></td>
                </tr>
				<c:set var="count" value="${count+1}"/>
			</logic:iterate> 
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="20" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="11%" align="center">&nbsp;</td>
                <td width="7%" align="center">&nbsp;</td>
                <td width="69%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();">：</td>
                <td width="10%" align="center"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10" value="${sof.totalsum}"></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center" ><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr >
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark">${sof.remark}</textarea></td>
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
