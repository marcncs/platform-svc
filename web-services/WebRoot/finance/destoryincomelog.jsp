<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
 //var cid = $F("cid");
 var iteration=0;
 var i=1;
 var chebox=null;
 var checkid=0;
 var incomesum=0;
	function CheckedObj(obj,objid,objincomesum){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 incomesum=objincomesum;
	}

function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	//alert("dbtable.rows.length===="+dbtable.rows.length+"    dbtable.rows.==="+dbtable.rows);
	if((dbtable.rows.length-1) <=1){
		var total=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value);
		sum=total-total*(document.forms[0].item("discount").value)+total*(document.forms[0].item("taxrate").value);
		document.validateProvide.item("subsum").value=sum;
	}else{
		for(var m=0;m<rowslength;m++){
			var total=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			sum=total-total*(document.forms[0].item("discount")(m).value)+total*(document.forms[0].item("taxrate")(m).value);
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
		
		if(checkid == ""){
			alert("请选择收款!");
			return false;
		}
		var che=document.validateProvide.che;
		var ischeck=false;
		if ( che.length>1){
			for (var i=0; i<che.length; i++){
				if ( che[i].checked){
					ischeck = true;
					break;
				}
			}
		}else{
			if ( che.checked){
					ischeck = true;
				}
		}
		if ( !ischeck ){
			alert("请选择明细资料!");
			return false;
		}
		var thisincomesum = getThisIncomeSum();
		if ( incomesum < thisincomesum){
			alert("预收金额不够!");
			return false;
		}
		document.validateProvide.ilid.value=checkid;
		showloading();
		validateProvide.submit();
		//return true;
	}
	
	function getThisIncomeSum(){
		var totalsum=0;
		var che=document.validateProvide.che;
		if ( che.length>1){
			for (var i=0; i<che.length; i++){
				if ( che[i].checked){
					totalsum=totalsum+document.validateProvide.thisreceivablesum[i].value;
				}
			}
		}else{
			if ( che.checked){
					totalsum=document.validateProvide.thisreceivablesum.value;
				}
		}
		return totalsum;
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
-->
</style>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert('本页拒绝使用右键!');}}document.onmousedown=click
</SCRIPT>



<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">核销</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="../finance/destoryIncomeLogAction.do" onSubmit="return ChkValue();">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <input type="hidden" name="ilid">
		  <tr> 
            <td >
			
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>收款资料</td>
        </tr>
      </table>
	  </legend>
	  	 <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="10%" >编号</td>
            <td width="18%">付款人</td>
            <td width="8%">收款去向</td>
            <td width="9%">收款金额</td>
			<td width="9%">已核销金额</td>
			<td width="7%">票据号</td>
			<td width="15%">制单时间</td>
          </tr>
          <logic:iterate id="s" name="loglist" > 
          <tr class="table-back" onClick="CheckedObj(this,'${s.id}',${s.incomesum-s.alreadyspend});"> 
			<td >${s.id}</td>
            <td>${s.drawee}</td>
            <td>${s.fundattachname}</td>
            <td>${s.incomesum}</td>
			<td>${s.alreadyspend}</td>
			<td>${s.billnum}</td>
			<td>${s.makedate}</td>			
          </tr>
          </logic:iterate> 
      </table>
</fieldset>
			
			</td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<td width="2%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="15%">结算单号</td>
				<td width="6%">结算方式</td>
                <td width="13%" > 相关单据号</td>
                <td width="15%">应收款金额</td>    
				<td width="15%">本次收款金额</td>               
                </tr>
			<c:set var="count" value="0"/>
            <logic:iterate id="p" name="als" > 
            <tr class="table-back">
				<td><input type="checkbox" value="${count}" name="che"></td>
                 <td><input name="rid" type="text" value="${p.id}" id="rid" size="14" readonly></td>
			
                <td ><input name="paymentmode" type="hidden" value="${p.paymentmode}" id="paymentmode"><input name="paymentmodename" type="text" value="${p.paymentmodename}" id="paymentmodename" size="8" readonly></td>
				<td><input name="billno" type="text" value="${p.billno}" id="billno" size="40" readonly></td>
                <td><input name="receivablesum" type="text" value="${p.receivablesum}" readonly></td>		
				<td><input name="thisreceivablesum" type="text" value="${p.alreadysum}" onKeyPress="KeyPress(this)" maxlength="10"></td>				
                </tr>
				<c:set var="count" value="${count+1}"/>
			</logic:iterate> 
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="3%" ><!--<a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a>--></td>
                <td width="11%">&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="64%" align="right"><!--<input type="button" name="button" value="金额小计" onClick="TotalSum();">--></td>
                <td width="15%"><!--<input name="totalsum" type="text" id="totalsum" size="10" maxlength="10">--></td>
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
