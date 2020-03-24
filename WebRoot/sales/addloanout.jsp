<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>

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
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"20\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"40\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"4\" readonly>";
		f.innerHTML="<input name=\"warehouseid\" type=\"hidden\" id=\"warehouseid\"><input name=\"warehousename\" type=\"text\" id=\"warehousename\" size=\"10\" readonly><a href=\"#\" onclick=\"SelectWarehourse(this);\"><img src=\"../images/CN/find.gif\" width=\"16\" height=\"16\" border=\"0\"></a>";
		g.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" size=\"10\" readonly>";
		h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"0\" size=\"6\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        i.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"10\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        j.innerHTML="<input name=\"cost\" type=\"text\" id=\"cost\" size=\"4\" readonly>";
		k.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\">";
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
	rowx = getRowNo(rowobj);
	if ( dbtable.rows.length <= 2 ){
	 	pid= document.validateProvide.item("productid").value;
	}else{
		pid = document.validateProvide.item("productid")[rowx-2].value
	}
	var wbc=showModalDialog("toSelectProductStockpileAction.do?pid="+pid,null,"dialogWidth:15.5cm;dialogHeight:7.5cm;center:yes;status:no;scrolling:no;");	
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
/*	var cid=document.validateProvide.cid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
*/
	var p=showModalDialog("toSelectLoanOutProductStockpileAction.do",null,"dialogWidth:20cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	
	var arrid=p.productid;
	var arrpordocutname=p.productname;
	var arrspecmode = p.specmode;
	var unitid=p.unitid;
	var arrcountunit=p.countunit;
	
	if((dbtable.rows.length-1) <1){
		for(var i=0;i<arrid.length;i++){
				addRow();
				if(i==0){//第一次加时还不是数组
					document.validateProvide.item("productid").value =arrid[0];
					document.validateProvide.item("productname").value =arrpordocutname[0];
					document.validateProvide.item("specmode").value=arrspecmode[0];
					document.validateProvide.item("unitid").value =unitid[0];
					document.validateProvide.item("unit").value =arrcountunit[0];
					document.validateProvide.item("quantity").value=0;
					document.validateProvide.item("unitprice").value =0;
					}else{
						document.validateProvide.item("productid")[i].value =arrid[i];
						document.validateProvide.item("productname")[i].value =arrpordocutname[i];
						document.validateProvide.item("specmode")[i].value=arrspecmode[i];
						document.validateProvide.item("unitid")[i].value =unitid[i];
						document.validateProvide.item("unit")[i].value =arrcountunit[i];
						document.validateProvide.item("quantity")[i].value=0;
						document.validateProvide.item("unitprice")[i].value =0;						
					}
			}
			
			SubTotal(rowx);
			TotalSum();
	}else{
		for(var i=0;i<arrid.length;i++){
			addRow();
			document.validateProvide.item("productid")[rowx-1+i].value =arrid[i];//加i是从已经有的row行起算从0行开始
			document.validateProvide.item("productname")[rowx-1+i].value =arrpordocutname[i];
			document.validateProvide.item("specmode")[rowx-1+i].value=arrspecmode[i];
			document.validateProvide.item("unitid")[rowx-1+i].value =unitid[i];
			document.validateProvide.item("unit")[rowx-1+i].value =arrcountunit[i];
			document.validateProvide.item("quantity")[rowx-1+i].value=0;
			document.validateProvide.item("unitprice")[rowx-1+i].value =0;			
			}
	}
	
}

function formatStr(obj){
	if ( obj == 'undefined'){
		return "";
	}
	return obj;
}

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


function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	//alert("dbtable.rows.length===="+dbtable.rows.length+"    dbtable.rows.==="+dbtable.rows);
	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value);
		document.validateProvide.item("subsum").value=sum;
	}else{
		for(var m=0;m<rowslength;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
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
	showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.validateProvide.uid.value=getCookie("cid");
	document.validateProvide.uname.value=getCookie("cname");
}

function SelectLinkman(){
	var cid=document.validateProvide.uid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	showModalDialog("toSelectLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	//document.validateProvide.cid.value=getCookie("lid");
	document.validateProvide.receiveman.value=getCookie("lname");
	document.validateProvide.tel.value=getCookie("ltel");
	document.validateProvide.transportaddr.value=getCookie("ltransportaddr");	
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
        var cname = document.validateProvide.uid;
		var receiveman = document.validateProvide.receiveman;
		var consignmentdate = document.validateProvide.consignmentdate;
		
		if(cname.value==null||cname.value==""){
			alert("用户不能为空!");
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

		validateProvide.submit();
		//return true;
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
	
		var rpid = document.validateProvide.RPID.value;
		//alert(rpid);
		if(event.keyCode==13){
		//alert("aa");
		document.getElementById('RPID').select();
		getProductByRPID(rpid);
		}
		//alert("bb");
	}


	function getProductByRPID(rpid){
	//alert("cc");
	   var url = "../sales/getProductByRPIDAjax.do?RPID="+rpid;
	   //document.getElementById("require").style.display="";
	   //document.getElementById("require").innerHTML="正在读取数据...";
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse}
                    );
	}

	function showResponse(originalRequest){
	var product = originalRequest.responseXML.getElementsByTagName("product");
	if(product.length>=1){
	var rm = product[0];
		if((dbtable.rows.length-1) <1){
			addRow();
			//alert("-11-"+product);
			document.validateProvide.item("productid").value =rm.getElementsByTagName("productid")[0].firstChild.data;
			document.validateProvide.item("productname").value =rm.getElementsByTagName("productname")[0].firstChild.data;
			document.validateProvide.item("specmode").value=rm.getElementsByTagName("specmode")[0].firstChild.data;
			document.validateProvide.item("unitid").value =rm.getElementsByTagName("unitid")[0].firstChild.data;
			document.validateProvide.item("unit").value =rm.getElementsByTagName("unitidname")[0].firstChild.data;
			document.validateProvide.item("quantity").value=0;
			document.validateProvide.item("unitprice").value =rm.getElementsByTagName("unitprice")[0].firstChild.data;
		}else{
		var rowx = dbtable.rows.length;
		  var i=1;
			addRow();
			//alert("-22-");
			document.validateProvide.item("productid")[rowx-1].value =rm.getElementsByTagName("productid")[0].firstChild.data;
			document.validateProvide.item("productname")[rowx-1].value =rm.getElementsByTagName("productname")[0].firstChild.data;
			document.validateProvide.item("specmode")[rowx-1].value=rm.getElementsByTagName("specmode")[0].firstChild.data;
			document.validateProvide.item("unitid")[rowx-1].value =rm.getElementsByTagName("unitid")[0].firstChild.data;
			document.validateProvide.item("unit")[rowx-1].value =rm.getElementsByTagName("unitidname")[0].firstChild.data;
			document.validateProvide.item("quantity")[rowx-1].value=0;
			document.validateProvide.item("unitprice")[rowx-1].value =rm.getElementsByTagName("unitprice")[0].firstChild.data;
		}
		
	}else{
		alert("该编号不存在");
	}

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
	
	function ShowSQ(tbr) {   // yy xx
//alert("db"+dbtable.rows.length);
//alert("abc="+(tbr-2));
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
			var qt = rm.getElementsByTagName("qt")[0].firstChild.data;
			var bc = rm.getElementsByTagName("bc")[0].firstChild.data;
			//var murl =rm.getElementsByTagName("murl")[0].firstChild.data;
			//alert(ispd);
			requireHTML  += "<tr><td width='40%'>"+wh+"</td><td width='30%'>"+bc+" </td><td width='30%' align='right'>"+qt+" </td></tr>";
			//addRows(productname,productcount,stockcount);
		}

		$("stock").innerHTML = requireHTML + "</table>";

		//document.getElementById("result").style.display="none";
	}

</script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<style type="text/css">
<!--
#hd {
	position:absolute;
	left:0px;
	top:0px;
	width:200px;
	height:auto;
	z-index:1;
	visibility:hidden;
}

#sq {
	position:absolute;
	left:0px;
	top:0px;
	width:200px;
	height:auto;
	z-index:1;
	visibility:hidden;
}
-->
</style>
</head>
<div id="hd">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
      <td width="50%" height="32" class="title-back">历史成交价</td>
      <td width="50%" class="title-back">成交价日期</td>
    </tr>
    <tr>
      <td colspan="2">
       <div id="historyprice">	
        	
      </div> 	
	  </td>
    </tr>
  </table>
</div>

<div id="sq">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
      <td width='40%' height="32" class="title-back"> 仓库</td>
	  <td width='25%' class="title-back">批次</td>
      <td width='35%' class="title-back">可用数量</td>
    </tr>
    <tr>
      <td colspan="3">
       <div id="stock">	
        	
      </div> 	
	  </td>
    </tr>
  </table>
</div>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增借出单</td>
        </tr>
      </table>
     <form name="validateProvide" method="post" action="addLoanOutAction.do" onSubmit="return ChkValue();">
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
	  <tr>
	  	<td width="11%"  align="right">借货人：</td>
          <td width="23%"><select name="uid" id="uid">
          <logic:iterate id="d" name="als">
            <option value="${d.userid}">${d.realname}</option>
          </logic:iterate>
        </select></td>
          <td width="10%" align="right">收货人：</td>
          <td width="22%"><select name="receiveman" id="receiveman">
          <logic:iterate id="d" name="als">
            <option value="${d.realname}">${d.realname}</option>
          </logic:iterate>
        </select></td>
	      <td width="9%" align="right">联系电话：</td>
	      <td width="25%"><input name="tel" type="text" id="tel"></td>
	  </tr>
	  <tr>
	    <td  align="right">借出部门：</td>
	    <td><select name="saledept" id="saledept">
          <logic:iterate id="d" name="aldept">
            <option value="${d.id}">${d.deptname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">借出人员：</td>
	    <td><select name="saleid" id="saleid">
          <logic:iterate id="d" name="als">
            <option value="${d.userid}">${d.realname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">发运方式：</td>
	    <td>${transportmodeselect}</td>
	    <td align="right">货运部：</td>
	    <td>${transitselect}</td>
	    <td align="right">交货日期：</td>
	    <td><input name="consignmentdate" type="text" id="consignmentdate" readonly onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>">
          <span class="STYLE1">*</span></td>
	    </tr>
	  <tr>
	    <td align="right">收货地址：</td>
	    <td ><input name="transportaddr" type="text" id="transportaddr" size="50"></td>
	    <td  align="right">&nbsp;</td>
	    <td  colspan="3">&nbsp;</td>
	    </tr>
	  </table>
</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr > 
                  <td width="100%" > <table  border="0" cellpadding="0" cellspacing="1">
                      <tr align="center" class="back-blue-light2">
                   <!--     <td width="60"><a href="javascript:SetCode('r')"><img src="../images/CN/record.gif" width="19"  border="0" title="手工录入产品编号"></a><a href="javascript:SetCode('e')"><img src="../images/CN/elect.gif" width="19"  border="0" title="选择"></a><a href="javascript:SetCode('b')"><img src="../images/CN/bar.gif" width="19"  border="0" title="条码扫描"></a></td> 
                            <td id="record" style="display:none"><input name="RPID" type="text" id="RPID" size="15" title="录入编号后，按回车键继续录其它编号" onKeyUp="RPIDToSearch();" onClick="this.select();" tabindex=1></td>-->
                            <td id="elect" >
				<img src="../images/CN/selectp.gif"  border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                            <td id="bar" style="display:none"><input name="SBAR" type="text" id="SBAR" size="30"></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<td width="3%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="7%">产品编号</td>
                <td width="14%" > 产品名称</td>
                <td width="15%">规格型号</td>
                <td width="3%"> 单位</td>
                <td width="13%">出货仓库</td>
				<td width="7%">批次</td>				
				<td width="7%"> 数量</td>
                <td width="10%"> 单价</td>                
				<td width="3%"> 成本</td>
				<td width="10%"> 金额</td>
               
                </tr>
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="20" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="11%" align="center">&nbsp;</td>
                <td width="7%" align="center">&nbsp;</td>
                <td width="69%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();">：</td>
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
