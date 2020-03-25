<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function Affirm(){
	if(listform.incomedate.value==null || listform.incomedate.value==""){
		alert("请输入交货日期!")
		//document.listform.receivedate.focus();
		return;
	}
		
	var flag=false;
	var k=0;
	if(document.listform.pid.length>1){
		for(var i=0;i<document.listform.pid.length;i++){
			if(document.listform.pid[i].checked){
				flag=true;//只要选中一个就设为true
				if ( parseFloat(listform.operatorquantity[i].value) > parseFloat(listform.maxquantity[i].value)) {
					alert("本次完成数量不能大于 数量－完成数量!");
					listform.operatorquantity[i].select();
					return;
				}	
			}
		}
	}else{
			if(document.listform.pid.checked){
				//k++;
				flag=true;//只要选中一个就设为true
				if ( parseFloat(listform.operatorquantity.value) > parseFloat(listform.maxquantity.value)) {
					alert("本次完成数量不能大于 数量－完成数量!");
					listform.operatorquantity.select();
					return;
				}
			}
	}
	
	if(flag){
		showloading();
		listform.action="transPurchanseIncomeAction.do";
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

function SupperSelect(obj,opid){
var wid=document.listform.warehouseid.value;
if(wid==null||wid=="")
{
	alert("请输入仓库！");
	return;
}
var p =showModalDialog("toSelectBatchProductAction.do?wid="+wid+"&opid="+opid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;resizable=yes;status:no;scrolling:no;");
rowx = getRowNo(obj);
if(dbtable.rows.length <=2){
	document.listform.item("batch").value =p.batch;
	document.listform.item("cost").value =p.cost;
}else{
	document.listform.item("batch")[rowx-2].value =p.batch;
	document.listform.item("cost")[rowx-2].value =p.cost;
}
}


</script>
</head>

<body>
<form name="listform" method="post">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">

<tr>
<td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
	<tr> 
	  <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
	  <td width="772"> 采购订单产品列表</td>
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
  <tr><input type="hidden" name="pbid" value="${pbf.id}">
	<td width="10%"  align="right">仓库：</td>
	  <td width="23%"><windrp:warehouse name="warehouseid"/></td>
	  <td width="9%" align="right">供应商：</td>
	  <td width="25%"><input name="pvid" type="hidden" id="pvid" value="${pbf.pid}">
		<input name="providename" type="text" id="providename" size="35" value="${pbf.pname}" readonly></td>
	  <td width="9%" align="right">质检单号：</td>
	  <td width="24%"><input name="agid" type="text" id="agid"></td>
  </tr>
  <tr>
	<td  align="right">供应商联系人：</td>
	<td><input name="plinkman" type="text" id="plinkman" value="${pbf.plinkman}" readonly></td>
	<td align="right">联系电话：</td>
	<td><input name="tel" type="text" id="tel" value="${pbf.tel}" readonly></td>
	<td align="right">结算方式：</td>
	<td><windrp:paymentmode name="paymode" value="${pbf.paymode}"/></td>
	</tr>
  <tr>
	<td  align="right">预计入库日期：</td>
	<td><input name="incomedate" type="text" id="incomedate" onFocus="javascript:selectDate(this)" readonly value="<windrp:dateformat value='${pbf.receivedate}' p='yyyy-MM-dd'/>
"></td>
	<td align="right">&nbsp;</td>
	<td>&nbsp;</td>
	<td align="right">&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
  </table>
</fieldset>

  <table width="100%" border="0" cellpadding="0" cellspacing="1" id="dbtable">
	  <tr align="center" class="title-top">
		<td width="2%"><input type="checkbox" name="checkall" onClick="Check();" ></td>
		<td width="8%">产品编号</td>
		<td width="21%" >产品名称</td>
		<td width="13%">规格</td>
		<td width="10%">单位</td>
		<td width="6%">单价</td>
		<td width="9%">需求日期</td>
		<td width="7%">数量</td>
		<td width="10%">完成数量</td>
		<td width="8%">本次完成数量</td>
		<td width="6%">金额</td>
	  </tr>
	  <c:set var="count" value="2"/>
	  <logic:iterate id="a" name="als" > 
	  <tr align="center" class="table-back">
		<td><input type="checkbox" name="pid" value="${count-2}" onClick="onlycheck();"></td>
		<td><input name="detailid" type="hidden" value="${a.id}" ><input name="productid" type="text" id="productid" value="${a.productid}" size="12" readonly></td>
		<td ><input name="productname" type="text" id="productname" value="${a.productname}" size="30" readonly></td>
		<td><input name="specmode" type="text" id="specmode" value="${a.specmode}" size="30" readonly></td>			 
		<td><input name="unitid" type="hidden" value="${a.unitid}" size="12">
		  <input name="unitidname" type="text" id="unitidname" value="${a.unitname}" size="12" readonly></td>
		<td><input name="unitprice" type="text" id="unitprice" size="8" value="${a.unitprice}" readonly></td>
		<td><windrp:dateformat value='${a.requiredate}' p='yyyy-MM-dd'/></td>
		<td>${a.quantity}<input type="hidden" name="quantity" value="${a.quantity}"></td>
		<td>${a.incomequantity}<input type="hidden" name="maxquantity" value="${a.quantity - a.incomequantity}"></td>
		<td><input name="operatorquantity" type="text" id="operatorquantity" size="8" value="${a.quantity - a.incomequantity}"   onchange="SubTotal(${count});TotalSum();" onFocus="this.select();SubTotal(${count});TotalSum();" disabled></td>
		<td><input name="subsum" type="text" id="subsum" size="8" value="${a.subsum}" readonly></td>
	  </tr>
	  <c:set var="count" value="${count+1}"/>
	  </logic:iterate> 
  </table>
  <table width="100%"   border="0" cellpadding="0" cellspacing="0">
	<tr align="center" class="table-back">
	  <td width="7%" >&nbsp;</td>
	  <td width="7%">&nbsp;</td>
	  <td width="7%">&nbsp;</td>
	  <td width="64%" align="right">总金额：</td>
	  <td width="15%">￥<input name="totalsum" type="text" id="totalsum" value="${totalsum}" size="12" maxlength="10" readonly></td>
	</tr>
  </table>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
	<tr> 
	  <td width="30%">
		<table width="148"  border="0" cellpadding="0" cellspacing="0">
		  <tr align="center"> 
			<td width="80"><input name="提交" type="button" value="生成采购入库" onClick="Affirm();">
			</td>
			<td width="80"><input name="返回" type="button" onClick="history.back();" value="返回"></td>
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
