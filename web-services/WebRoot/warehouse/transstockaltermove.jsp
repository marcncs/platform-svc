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
		if(listform.outwarehouseid.value==null || listform.outwarehouseid.value==""){
			alert("请选择调出仓库!")
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
						return false;
					}		
				}
			}
		}else{
				if(document.listform.pid.checked){
					flag=true;//只要选中一个就设为true
					if ( parseFloat(listform.operatorquantity.value) > parseFloat(listform.maxquantity.value)) {
						alert("本次完成数量不能大于 数量－完成数量!");
						listform.operatorquantity.select();
						return false;
					}
				}
		}
		var linkman = document.listform.olinkman.value;
		var tel =  document.listform.otel.value;
		var transportaddr  =   document.listform.transportaddr.value;
		if(linkman.trim()==""){
			alert("联系人不能为空!");
			return false;
		}	
		if(tel.trim()==""){
			alert("联系电话不能为空!");
			return false;
		}	
		if(transportaddr.trim()==""){
			alert("发货地址不能为空!");
			return false;
		}
		
		if(flag){
			listform.action="transStockAlterMoveAction.do";
			
		}else{
			alert("请选择产品并设定好数量!");
			return false;
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
function SelectLinkman(){
		var inorganid=document.listform.receiveorganid.value;
		if(inorganid==null||inorganid=="")
		{
			alert("请选择调入机构！");
			return;
		}
	
			var lk=showModalDialog("../common/selectOrganLinkmanAction.do?cid="+inorganid,null,"dialogWidth:18cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			document.listform.olinkman.value=lk.lname;
			var tel = lk.mobile;
			if(tel==""){
				tel=lk.tel;
			}
			document.listform.otel.value=tel;
		
	}
	
</script>
</head>

<body>

<form name="listform" method="post" action="transStockAlterMoveAction.do" onSubmit="return Affirm();">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 生成订购单</td>
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
          <td width="9%"  align="right"><input name="aaid" type="hidden" id="aaid" value="${smf.id}">
          	  需求日期：</td>
          <td width="25%"><input name="movedate" type="hidden" id="movedate" onFocus="javascript:selectDate(this)" value="${smf.movedate}">
          <windrp:dateformat value="${smf.movedate}" p="yyyy-MM-dd"/></td>
          <td width="9%" align="right"> 调出机构：</td>
          <td width="24%"><input name="outorganid" type="hidden" id="outorganid" value="${smf.outorganid}">
           <windrp:getname key='organ' value='${smf.outorganid}' p='d'/> </td>
          <td width="10%" align="right">调出仓库：</td>
          <td width="23%">
          <windrp:warehouse name="outwarehouseid"/>
          </td>
        </tr>
        <tr>
          <td  align="right">调入机构：</td>
          <td><input name="receiveorganid" type="hidden" id="receiveorganid" value="${smf.makeorganid}">
             <windrp:getname key='organ' value='${smf.makeorganid}' p='d'/></td>
           	<td  align="right">调入仓库：</td>
          <td><input name="inwarehouseid" type="hidden" id="inwarehouseid" value="${smf.inwarehouseid}">
             <windrp:getname key='warehouse' value='${smf.inwarehouseid}' p='d'/></td>
          <td align="right">付款方式：</td>
          <td>
          <input name="paymentmode" type="hidden" id="paymentmode" value="${smf.paymentmode}">
          <windrp:getname key='PaymentMode' value='${smf.paymentmode}' p='f'/></td>
          
        </tr>
        <tr>
        <td align="right">开票信息：</td>
          <td>
          <windrp:select key="InvoiceType" name="invmsg" p="n|f" value="${smf.invmsg}"/>
          </td>
          <td  align="right">发票抬头：</td>
          <td><input name="tickettitle" type="text" id="tickettitle" value="${smf.tickettitle}"> </td>
          <td align="right">发运方式：</td>
         
          <td> <windrp:select key="TransportMode" name="transportmode" p="n|d"  value="${smf.transportmode}"/></td>
        </tr>
         <tr>
        	<td  align="right">联系人：</td>
			<td>
				<input type="text" id="olinkman" name="olinkman" 
				dataType="Require" msg="必须录入联系人!"><a href="javascript:SelectLinkman();"><img
						src="../images/CN/find.gif" width="19" height="18" 
						align="absmiddle" border="0"> </a>
				<span class="STYLE1">*</span>
			</td>
			<td  align="right">联系电话：</td>
			<td>
				<input type="text" id="otel" name="otel" dataType="PhoneOrMobile" msg="必须录入正确联系电话!" >
				<span class="STYLE1">*</span>
			</td>
          <td align="right"></td>
         
          <td> </td>
        </tr>
        <tr>
          <td  align="right">收货地址：</td>
          <td colspan="5">
          <input name="transportaddr" type="text" id="transportaddr" value="${smf.transportaddr}" size="80"></td>
        </tr>
        <tr>
          <td  align="right">原因：</td>
          <td colspan="5"><input name="movecause" type="hidden" id="movecause" value="${smf.movecause}" >${smf.movecause}</td>
        </tr>
        <tr>
          <td  align="right">备注：</td>
          <td colspan="5"><input name="remark" type="hidden" id="remark" value="${smf.remark}" >${smf.remark}</td>
        </tr>
      </table>
	  </fieldset>
	  <fieldset align="center">
		<legend>
			<table width="50" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						产品信息
					</td>
				</tr>
			</table>
		</legend>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" id="dbtable">
          <tr align="center" class="title-top">
            <td width="2%"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="8%">产品编号</td>
            <td width="19%" >产品名称</td>
            <td width="19%">规格</td>
            <td width="7%">单位</td>
            <td width="4%">单价</td>
            <td width="6%">数量</td>
			<td width="8%">完成数量</td>
			<td width="8%">本次完成数量</td>
			<td width="6%">金额</td>
          </tr>
          <c:set var="count" value="2"/>
		  <logic:iterate id="a" name="als" > 
          <tr align="center" class="table-back">
            <td><input type="checkbox" name="pid" value="${count-2}" onClick="onlycheck();"></td>
            <td><input name="detailid" type="hidden" value="${a.id}" ><input name="productid" type="hidden" id="productid" value="${a.productid}" size="12" >${a.productid}</td>
            <td ><input name="productname" type="hidden" id="productname" value="${a.productname}">${a.productname}</td>
            <td><input name="specmode" type="hidden" id="specmode" value="${a.specmode}" >${a.specmode}</td>			 
            <td><input name="unitid" type="hidden" value="${a.unitid}" size="12">${a.unitidname}</td>
			<td><input name="unitprice" type="hidden" id="unitprice" size="8" value="${a.unitprice}"> ${a.unitprice}</td>
			<td>${a.canquantity}<input type="hidden" name="quantity" value="${a.canquantity}"></td>
			<td>${a.alreadyquantity}<input type="hidden" name="maxquantity" value="${a.canquantity - a.alreadyquantity}"></td>
			<td><input name="operatorquantity" type="text" id="operatorquantity" size="8" value="${a.canquantity - a.alreadyquantity}"   onchange="SubTotal(${count});TotalSum();" onFocus="this.select();SubTotal(${count});TotalSum();" disabled></td>
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
          <td width="64%" align="right"><input type="button" value="金额小计" onClick="TotalSum()">：</td>
          <td width="15%"><input name="totalsum" type="text" id="totalsum" value="${smf.totalsum}" size="12" maxlength="10" readonly></td>
        </tr>
      </table>
      </fieldset>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
		<tr align="center">
			<td width="33%">
				<input type="submit" name="Submit" value="确定">
				&nbsp;&nbsp;
				<input type="button" name="Submit2" value="取消"
					onClick="window.close();">
			</td>
		</tr>
	</table>
    </td>
  </tr>
  
</table>
</form>
</body>
</html>
