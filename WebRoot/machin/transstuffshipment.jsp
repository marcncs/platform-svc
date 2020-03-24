<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function Affirm(){

		var flag=false;
		var k=0;
		if(document.listform.pid.length>1){
			for(var i=0;i<document.listform.pid.length;i++){
				if(document.listform.pid[i].checked){
					k++;
					flag=true;//只要选中一个就设为true
				}
			}
		}else{
				if(document.listform.pid.checked){
					//k++;
					flag=true;//只要选中一个就设为true
				}
		}
		
		if(flag){
			listform.action="assembleTransStuffShipmentAction.do";
			listform.submit();
		}else{
			alert("请选择产品并设定好数量!");
		}
	}


	function Check(){
		if(document.listform.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
	var pidleng=document.listform.pid.length;
		if(pidleng>1){
			for(i=0;i<pidleng;i++){
	
				if (!document.listform.pid[i].checked)
					if(listform.elements[i].name != "checkall"){
					document.listform.pid[i].checked=true;
					document.listform.operatorquantity[i].disabled=false;
					}
			}
		}else{
				if (!document.listform.pid.checked)
					if(listform.elements.name != "checkall"){
					document.listform.pid.checked=true;
					document.listform.operatorquantity.disabled=false;
					}
		}
	}

	function uncheckAll(){
	var pidleng=document.listform.pid.length;
		if(pidleng>1){
			for(i=0;i<pidleng;i++){
				if (document.listform.pid[i].checked)
					if(listform.elements[i].name != "checkall"){
					document.listform.pid[i].checked=false;
					document.listform.operatorquantity[i].disabled=true;
					}
			}
		}else{
				if (document.listform.pid.checked)
					if(listform.elements.name != "checkall"){
					document.listform.pid.checked=false;
					document.listform.operatorquantity.disabled=true;
					}
		}
	}
	
	function onlycheck(){
	var pidleng=document.listform.pid.length;
		if(pidleng>1){
			for(var i=0;i<pidleng;i++){
				if(document.listform.pid[i].checked){
					//document.listform.unitprice[i].disabled=false;
					document.listform.operatorquantity[i].disabled=false;
				}
				if(!document.listform.pid[i].checked){
					//document.listform.unitprice[i].disabled=true;
					document.listform.operatorquantity[i].disabled=true;
				}
			}
		}else{
				if(document.listform.pid.checked){
					//document.listform.unitprice.disabled=false;
					document.listform.operatorquantity.disabled=false;
				}
				if(!document.listform.pid.checked){
					//document.listform.unitprice.disabled=true;
					document.listform.operatorquantity.disabled=true;
				}
		}
	}
	
	function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	//alert("dbtable.rows.length===="+dbtable.rows.length+"    dbtable.rows.==="+dbtable.rows);
	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("operatorquantity").value);
		document.listform.item("subsum").value=sum;
	}else{
		for(var m=0;m<rowslength;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("operatorquantity")(m).value);
			document.listform.item("subsum")(m).value=sum;	
		}
	}
}


function TotalSum(){
	var totalsum=0.00;
	//alert(dbtable.rows.length);
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.listform.item("subsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.listform.item("subsum")(i).value);
		}
	}
	document.listform.totalsum.value=totalsum;
}


function ShowSQ(productid) {   // yy xx

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

	var qtsum=0;
		for(var i=0;i<product.length;i++){
			var rm = product[i];
			var wh = rm.getElementsByTagName("wh")[0].firstChild.data;			
			var bt = rm.getElementsByTagName("bt")[0].firstChild.data;
			var qt =rm.getElementsByTagName("qt")[0].firstChild.data;
			qtsum +=parseInt(qt);
			requireHTML  += "<tr><td width='45%'>"+wh+"</td><td width='20%'>"+bt+"</td><td width='35%' align='center'>"+parseInt(qt)+" </td></tr>";
		}
		requireHTML =requireHTML + "<tr><td width='45%'></td><td width='20%' align='right'>合计：</td><td width='35%' align='center'>"+qtsum+" </td></tr>"
		$("stock").innerHTML = requireHTML + "</table>";
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
<form name="listform" method="post">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 组装单转材料出库</td>
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
	  	<td width="9%"  align="right">出货仓库：</td>
          <td width="24%"><select name="warehouseid" onChange="delAllProduct();">
            <logic:iterate id="w" name="alw" >
              <option value="${w.id}">${w.warehousename}</option>
            </logic:iterate>
          </select></td>
          <td width="10%" align="right">出库类别：</td>
          <td width="23%">${shipmentsortselect}</td>
	      <td width="9%" align="right">需求部门：</td>
	      <td width="25%"><select name="shipmentdept" id="shipmentdept">
            <logic:iterate id="d" name="aldept">
              <option value="${d.id}">${d.deptname}</option>
            </logic:iterate>
          </select></td>
	  </tr>
	  <tr>
	    <td  align="right">需求日期：</td>
	    <td><input name="requiredate" type="text" id="requiredate" onFocus="javascript:selectDate(this)" readonly value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"></td>
	    <td align="right">相关单据：</td>
	    <td><input name="billno" type="text" id="billno"></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>

      <table width="100%" border="0" cellpadding="0" cellspacing="1" id="dbtable">
          <tr align="center" class="title-top">
            <td width="2%"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="8%">产品编码</td>
            <td width="20%" >产品名称</td>
            <td width="20%">规格</td>
            <td width="7%">单位</td>
            <td width="4%">单价</td>
            <td width="6%">数量</td>
			<td width="5%">相关</td>
			<td width="14%">完成数量</td>
			<td width="8%">本次完成数量</td>
			<td width="6%">金额</td>
          </tr>
          <c:set var="count" value="2"/>
		  <logic:iterate id="a" name="vls" > 
          <tr align="center" class="table-back">
            <td><input type="checkbox" name="pid" value="${count-2}" onClick="onlycheck();"></td>
            <td><input name="detailid" type="hidden" value="${a.id}" ><input name="productid" type="hidden" id="productid" value="${a.productid}"><input name="productcode" type="text" id="productcode" value="${a.productcode}" size="12" readonly></td>
            <td ><input name="productname" type="text" id="productname" value="${a.productname}" size="35" readonly></td>
            <td><input name="specmode" type="text" id="specmode" value="${a.specmode}" size="35" readonly></td>			 
            <td><input name="unitid" type="hidden" value="${a.unitid}" size="12">
              <input name="unitidname" type="text" id="unitidname" value="${a.unitidname}" size="12" readonly></td>
			<td><input name="unitprice" type="text" id="unitprice" size="8" value="0"></td>
			<td>${a.quantity}<input type="hidden" name="quantity" value="${a.quantity}"></td>
			<td><a href="#" onMouseOver="ShowSQ('${a.productid}');" onMouseOut="HiddenSQ();"><img src="../images/CN/stock.gif" width="16"  border="0"></a></td>
			<td>${a.alreadyquantity}</td>
			<td><input name="operatorquantity" type="text" id="operatorquantity" size="8" value="${a.quantity - a.alreadyquantity}"   onChange="SubTotal(${count});TotalSum();" onFocus="this.select();SubTotal(${count});TotalSum();" disabled></td>
			<td><input name="subsum" type="text" id="subsum" size="8" value="0" readonly></td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
      </table>
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
        <tr align="center" class="table-back">
          <td width="7%" >&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="64%" align="right">&nbsp;</td>
          <td width="15%"><input name="totalsum" type="text" id="totalsum" value="${totalsum}" size="12" maxlength="10" readonly></td>
        </tr>
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table width="148"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="80"><input name="提交" type="button" value="生成材料出库" onClick="Affirm();">
                </td>
                <td width="80">&nbsp;</td>
              </tr>
            </table></td>
          <td width="70%" align="right">
          </td>
        </tr>
      </table>
      
    </td>
  </tr>
  
</table>
</form>
</body>
</html>
