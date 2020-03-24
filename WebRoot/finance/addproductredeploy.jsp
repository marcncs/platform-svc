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
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
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
		var n=x.insertCell(13);
		var o=x.insertCell(14);
 
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
		n.className = "table-back";
		o.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"18\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"18\" readonly>";
		e.innerHTML="<input name=\"countunitname\" type=\"text\" id=\"countunitname\" size=\"4\" readonly>";
		f.innerHTML="<input name=\"sandardpurchase\" type=\"text\" id=\"sandardpurchase\" size=\"12\" >";
        g.innerHTML="<input name=\"standardsale\" type=\"text\" id=\"standardsale\" size=\"12\" >";
		h.innerHTML="<input name=\"pricei\" type=\"text\" id=\"pricei\" size=\"12\" >";
		i.innerHTML="<input name=\"priceii\" type=\"text\" id=\"priceii\" size=\"12\" >";
        j.innerHTML="<input name=\"priceiii\" type=\"text\" id=\"priceiii\" size=\"12\" >";
        k.innerHTML="<input name=\"pricewholesale\" type=\"text\" id=\"pricewholesale\" size=\"12\" >";
        l.innerHTML="<input name=\"priceivs\" type=\"text\" id=\"priceivs\" size=\"12\" >";
		m.innerHTML="<input name=\"priceuni\" type=\"text\" id=\"priceuni\" size=\"12\" >";
		n.innerHTML="<input name=\"leastsale\" type=\"text\" id=\"leastsale\" size=\"12\" >";
		o.innerHTML="<input name=\"cost\" type=\"text\" id=\"cost\" size=\"12\" >";
		i++;
    chebox=document.all("che");  //計算total值

 	//SubTotal(dbtable.rows);
	//TotalSum();
}
	

function SupperSelect(rowx){
	 
	 
	var p=showModalDialog("toSelectProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	
		var arrid=p.productid;
	var arrpordocutname=p.productname;
	var arrsandardPurchase = p.sandardPurchase;
	var arrstandardSale=p.standardSale;
	var arrpriceI=p.priceI;
	 
	var arrpriceII=p.priceII;
	var arrpriceIII=p.priceIII;
	var arrpriceWholesale=p.priceWholesale;
	var arrpriceIVS=p.priceIVS;
	var arrpriceUNI=p.priceUNI;
	var arrleastSale=p.leastSale;
	var arrcost=p.cost;
	var arrspecmode=p.specmode;
	var arrcountunitname=p.countunitname;
	
 
	if((dbtable.rows.length-1) <1){
		for(var i=0;i<arrid.length;i++){
				 
				addRow();
				if(i==0){//第一次加时还不是数组
					document.validateProvide.item("productid").value =arrid[0];
					document.validateProvide.item("productname").value =arrpordocutname[0];
					document.validateProvide.item("specmode").value =arrspecmode[0];
					document.validateProvide.item("countunitname").value =arrcountunitname[0];
					document.validateProvide.item("sandardpurchase").value =arrsandardPurchase[0];
					 
					document.validateProvide.item("standardsale").value =arrstandardSale[0];
					document.validateProvide.item("pricei").value =arrpriceI[0];
					 
					document.validateProvide.item("priceii").value=arrpriceII[0];
					document.validateProvide.item("priceiii").value =arrpriceIII[0];
					document.validateProvide.item("pricewholesale").value =arrpriceWholesale[0];
					document.validateProvide.item("priceivs").value =arrpriceIVS[0];
					document.validateProvide.item("priceuni").value =arrpriceUNI[0];
					document.validateProvide.item("leastsale").value =arrleastSale[0];
					document.validateProvide.item("cost").value =arrcost[0];
					}else{
					document.validateProvide.item("productid")[i].value =arrid[i];
					document.validateProvide.item("productname")[i].value =arrpordocutname[i];
					document.validateProvide.item("specmode")[i].value =arrspecmode[i];
					document.validateProvide.item("countunitname")[i].value =arrcountunitname[i];
					document.validateProvide.item("sandardpurchase")[i].value=arrsandardPurchase[i]; 
					document.validateProvide.item("standardsale")[i].value =arrstandardSale[i];
					document.validateProvide.item("pricei")[i].value =arrpriceI[i];
					document.validateProvide.item("priceii")[i].value=arrpriceII[i];
					document.validateProvide.item("priceiii")[i].value =arrpriceIII[i];
					document.validateProvide.item("pricewholesale")[i].value =arrpriceWholesale[i];
					document.validateProvide.item("priceivs")[i].value =arrpriceIVS[i];
					document.validateProvide.item("priceuni")[i].value =arrpriceUNI[i];
					document.validateProvide.item("leastsale")[i].value =arrleastSale[i];
					document.validateProvide.item("cost")[i].value =arrcost[i];
					}
			}
			
			//SubTotal(rowx);
			//TotalSum();
	}else{
		 
		for(var i=0;i<arrid.length;i++){
			addRow();
			document.validateProvide.item("productid")[rowx-1+i].value =arrid[i];//加i是从已经有的row行起算从0行开始
			document.validateProvide.item("productname")[rowx-1+i].value =arrpordocutname[i];
			document.validateProvide.item("specmode")[rowx-1+i].value =arrspecmode[i];
			document.validateProvide.item("countunitname")[rowx-1+i].value =arrcountunitname[i];
			document.validateProvide.item("sandardpurchase")[rowx-1+i].value=arrsandardPurchase[i];
			document.validateProvide.item("standardsale")[rowx-1+i].value =arrstandardSale[i];
			document.validateProvide.item("pricei")[rowx-1+i].value =arrpriceI[i]; 
			document.validateProvide.item("priceii")[rowx-1+i].value=arrpriceII[i];
			document.validateProvide.item("priceiii")[rowx-1+i].value =arrpriceIII[i];
			document.validateProvide.item("pricewholesale")[rowx-1+i].value =arrpriceWholesale[i];
			document.validateProvide.item("priceivs")[rowx-1+i].value =arrpriceIVS[i];
			document.validateProvide.item("priceuni")[rowx-1+i].value =arrpriceUNI[i];
			document.validateProvide.item("leastsale")[rowx-1+i].value =arrleastSale[i];
			document.validateProvide.item("cost")[rowx-1+i].value =arrcost[i];
		}
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
		var movedate = document.validateProvide.movedate;
		var outwarehouseid = document.validateProvide.outwarehouseid;
		var inwarehouseid = document.validateProvide.inwarehouseid;
		
		if(movedate.value==null||movedate.value==""){
			alert("请输入调拨日期");
			return false;
		}
		if(outwarehouseid.value==null ||outwarehouseid.value==""){
			alert("请选择调出仓库");
			return false;
		}
		if(inwarehouseid.value==null ||inwarehouseid.value==""){
			alert("请选择调入仓库");
			return false;
		}
		if(outwarehouseid.value==inwarehouseid.value){
			alert("请选择不同的仓库");
			//totalsum.focus();
			return false;
		}

		
		validateProvide.submit();
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
			document.validateProvide.item("sandardpurchase").value=rm.getElementsByTagName("sandardpurchase")[0].firstChild.data;
			document.validateProvide.item("standardsale").value =rm.getElementsByTagName("standardsale")[0].firstChild.data;
			document.validateProvide.item("pricei").value =rm.getElementsByTagName("pricei")[0].firstChild.data;
			document.validateProvide.item("priceii").value =rm.getElementsByTagName("priceii")[0].firstChild.data;
			document.validateProvide.item("priceiii").value =rm.getElementsByTagName("priceiii")[0].firstChild.data;
			document.validateProvide.item("pricewholesale").value =rm.getElementsByTagName("pricewholesale")[0].firstChild.data;
			document.validateProvide.item("priceivs").value =rm.getElementsByTagName("priceivs")[0].firstChild.data;
			document.validateProvide.item("priceuni").value =rm.getElementsByTagName("priceuni")[0].firstChild.data;
			document.validateProvide.item("leastsale").value =rm.getElementsByTagName("leastsale")[0].firstChild.data;
			document.validateProvide.item("cost").value =rm.getElementsByTagName("cost")[0].firstChild.data;
		}else{
		var rowx = dbtable.rows.length;
		  var i=1;
			addRow();
			//alert("-22-");
			document.validateProvide.item("productid")[rowx-1].value =rm.getElementsByTagName("productid")[0].firstChild.data;
			document.validateProvide.item("productname")[rowx-1].value =rm.getElementsByTagName("productname")[0].firstChild.data;
			document.validateProvide.item("sandardpurchase")[rowx-1].value=rm.getElementsByTagName("sandardpurchase")[0].firstChild.data;
			document.validateProvide.item("pricei")[rowx-1].value =rm.getElementsByTagName("pricei")[0].firstChild.data;
			document.validateProvide.item("priceii")[rowx-1].value =rm.getElementsByTagName("priceii")[0].firstChild.data;
			document.validateProvide.item("priceiii")[rowx-1].value =rm.getElementsByTagName("priceiii")[0].firstChild.data;
			document.validateProvide.item("pricewholesale")[rowx-1].value =rm.getElementsByTagName("pricewholesale")[0].firstChild.data;
			document.validateProvide.item("priceivs")[rowx-1].value =rm.getElementsByTagName("priceivs")[0].firstChild.data;
			document.validateProvide.item("priceuni")[rowx-1].value =rm.getElementsByTagName("priceuni")[0].firstChild.data;
			document.validateProvide.item("leastsale")[rowx-1].value =rm.getElementsByTagName("leastsale")[0].firstChild.data;
			document.validateProvide.item("cost")[rowx-1].value =rm.getElementsByTagName("cost")[0].firstChild.data;
			 
		}
		
	}else{
		alert("该编号不存在");
	}

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
			var qt =rm.getElementsByTagName("qt")[0].firstChild.data;
			//var murl =rm.getElementsByTagName("murl")[0].firstChild.data;
			//alert(ispd);
			requireHTML  += "<tr><td width='50%'>"+wh+"</td><td width='50%'>"+qt+" </td></tr>";
			//addRows(productname,productcount,stockcount);
		}

		$("stock").innerHTML = requireHTML + "</table>";

		//document.getElementById("result").style.display="none";
	}
	
	function checkform(){
		var pid =document.validateProvide.item("productid");
		if ( pid == null ){
			alert("请选择产品!");
			return false;
		}
		return true;
	}

</script>
<style type="text/css">
<!--
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
<div id="sq">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
      <td width="50%" height="32" class="title-back"> 仓库</td>
      <td width="50%" class="title-back">可用数量</td>
    </tr>
    <tr>
      <td colspan="2">
       <div id="stock">	
        	
      </div> 	
	  </td>
    </tr>
  </table>
</div>

<body>
<SCRIPT language=javascript>
////
</SCRIPT>
<form name="validateProvide" method="post" action="addProductRedeployAction.do" onSubmit="return checkform();">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增产品调价单</td>
        </tr>
      </table>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	    <td  align="right">调价人：</td>
	    <td><select name="RedeployID" id="RedeployID">
            <logic:iterate id="u" name="als">
              <option value="${u.userid}">${u.realname}</option>
            </logic:iterate>
        </select></td>
	  	<td width="9%" align="right">调价部门：</td>
          <td width="25%"><select name="RedeployDept" id="RedeployDept">
            <logic:iterate id="d" name="dls">
              <option value="${d.id}">${d.deptname}</option>
            </logic:iterate>
        </select></td>
	      <td width="8%" align="right">调价描述：</td>
	      <td width="24%"><input name="RedeployMemo" type="text" id="RedeployMemo" value="" size="40"></td>
	  </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr > 
            <td width="100%" ><table  border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="back-blue-light2">
                <td id="elect" >选择产品： <img src="../images/CN/find.gif" width="18" height="18" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
              </tr>
            </table></td>
          </tr>
        </table> 
      <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="3%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
          <td width="5%">产品编号</td>
          <td width="11%" > 产品名称 </td>
		  <td width="11%"> 规格 </td>
		  <td width="4%"> 单位 </td>
          <td width="10%">标准采购价</td>
          <td width="6%">标准销售价</td>
          <td width="6%">一级价</td>
		  <td width="5%">二级价</td>
          <td width="3%">三级价</td>
          <td width="6%">二级批发商价</td>
          <td width="4%">4S价</td>
		  <td width="3%">加盟价</td>
          <td width="5%">最低销售价</td>
          <td width="18%">产品成本</td>
        </tr>
      </table>
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
        <tr align="center" class="table-back">
          <td width="3%" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
          <td width="11%">&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="69%" align="right">&nbsp;</td>
          <td width="10%">&nbsp;</td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center"><input type="Submit" name="Submit"  value="提交">            &nbsp;&nbsp;
          <input type="button" name="button" value="取消" onClick="history.back();">          </td>
        </tr>
      </table></td>
  </tr>

</table>
</form>
</body>
</html>
