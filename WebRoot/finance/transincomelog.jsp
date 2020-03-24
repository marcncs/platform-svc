<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script language="javascript">
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
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";		
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"rid\" type=\"text\" id=\"rid\" size=\"12\" readonly>";
		c.innerHTML="<input name=\"paymentmode\" type=\"hidden\" id=\"paymentmode\"><input name=\"paymentmodename\" type=\"text\" id=\"paymentmodename\" size=\"12\" readonly>";
		d.innerHTML="<input name=\"billno\" type=\"text\" id=\"billno\" size=\"12\" readonly>";
        e.innerHTML="<input name=\"receivablesum\" type=\"text\" id=\"receivablesum\" size=\"8\" readonly>";
        f.innerHTML="<input name=\"thisreceivablesum\" type=\"text\" id=\"thisreceivablesum\" value=\"0\" size=\"10\" maxlength=\"10\" onchange=\"TotalSum();\" onFocus=\"TotalSum();\">";       
		i++;
    chebox=document.all("che");  //計算total值

 	//SubTotal(dbtable.rows);
	//TotalSum();
}
	

function SupperSelect(rowx){
	var roid=document.validateProvide.roid.value;
	if(roid==null||roid=="")
	{
		alert("请选择应收款对象！");
		return;
	}
	var p =showModalDialog("toSelectReceivableAction.do?roid="+roid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");

	var arrid=p.rid;
	var arrpaymentmode=p.paymentmode;
	var arrpaymentmodename = p.paymentmodename;
	var arrbillno=p.billno;
	var arrreceivablesum=p.receivablesum;
	
	if((dbtable.rows.length-1) <1){
		for(var i=0;i<arrid.length;i++){
				addRow();
				if(i==0){//第一次加时还不是数组
					document.validateProvide.item("rid").value =arrid[0];
					document.validateProvide.item("paymentmode").value =arrpaymentmode[0];
					document.validateProvide.item("paymentmodename").value=arrpaymentmodename[0];
					document.validateProvide.item("billno").value =arrbillno[0];
					document.validateProvide.item("receivablesum").value =arrreceivablesum[0];
					document.validateProvide.item("thisreceivablesum").value=arrreceivablesum[0];
					}else{
						document.validateProvide.item("rid")[i].value =arrid[i];
						document.validateProvide.item("paymentmode")[i].value =arrpaymentmode[i];
					document.validateProvide.item("paymentmodename")[i].value=arrpaymentmodename[i];
					document.validateProvide.item("billno")[i].value =arrbillno[i];
					document.validateProvide.item("receivablesum")[i].value =arrreceivablesum[i];
					document.validateProvide.item("thisreceivablesum")[i].value=arrreceivablesum[i];
					}
			}
			
			//SubTotal(rowx);
			TotalSum();
	}else{
		for(var i=0;i<arrid.length;i++){
			addRow();
			document.validateProvide.item("rid")[rowx-1+i].value =arrid[i];//加i是从已经有的row行起算从0行开始
			document.validateProvide.item("paymentmode")[rowx-1+i].value =arrpaymentmode[i];
					document.validateProvide.item("paymentmodename")[rowx-1+i].value=arrpaymentmodename[i];
					document.validateProvide.item("billno")[rowx-1+i].value =arrbillno[i];
					document.validateProvide.item("receivablesum")[rowx-1+i].value =arrreceivablesum[i];
					document.validateProvide.item("thisreceivablesum")[rowx-1+i].value=arrreceivablesum[i];
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
		totalsum=totalsum+parseFloat(document.validateProvide.item("thisreceivablesum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.validateProvide.item("thisreceivablesum")(i).value);
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
	function CountBalance(){
		var dealsum = document.addform.dealsum.value;
		var incomesum = document.addform.incomesum.value;
		var owebalance = 0.00;
		owebalance = parseFloat(dealsum)-parseFloat(incomesum);
		document.addform.owebalance.value=owebalance;
	}
	
	function SelectReceivableObject(){
		showModalDialog("toSelectReceivableObjectAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
		document.validateProvide.roid.value=getCookie("roid");
		document.validateProvide.drawee.value=getCookie("objname");
		document.validateProvide.draweeid.value=getCookie("objname");
	}
	
	function ChkValue(){
		var roid = document.validateProvide.roid;
		var fundattach = document.validateProvide.fundattach;
		if(roid.value==""){
		alert("收款对象不能为空！");
		return false;
		}
		if(fundattach.value==""){
		alert("收款去向不能为空！");
		return false;
		}
		if ( isMaxSum() ){
			alert("本次收款金额不能超过应收款金额!");
			return false;
		}
		if ( !Validator.Validate(document.validateProvide,2) ){
			return false;
		}
		showloading();
		return true;
	}
	
function isMaxSum(){
	var payablesum=document.all("receivablesum");
	var thispayablesum = document.all("thisreceivablesum");
	if (payablesum.length){
		for(var i=0;i<=payablesum.length;i++){
		  if ( parseFloat(payablesum[i].value) < parseFloat(thispayablesum[i].value) ){
			thispayablesum[i].select();
			return true;
			break;
		  }
		}
	}else{
		 if ( parseFloat(payablesum.value) <parseFloat(thispayablesum.value) ){
			 thispayablesum.select();
			return true;
		  }
	}
	return false;
}
</script>
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
          <td width="772"> 转收款</td>
        </tr>
      </table>
	   <form name="validateProvide" method="post" action="../finance/transIncomeLogAction.do"  onSubmit="return ChkValue();">
	  <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <input type="hidden" name="id" value="${ilf.id}">
		  <tr> 
            <td >
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right"><input name="roid" type="hidden" id="roid" value="${roid}">
应收款对象：</td>
          <td width="29%"><input name="draweeid" type="text" size="35" value="${drawee}" readonly>
            </td>
          <td width="9%" align="right">付款人：</td>
          <td width="22%"><input name="drawee" type="text" id="drawee" size="35" value="${drawee}" readonly></td>
	      <td width="11%" align="right">收款去向：</td>
	      <td width="19%"><select name="fundattach" id="fundattach">
          <logic:iterate id="u" name="cblist">
            <option value="${u.id}">${u.cbname}</option>
          </logic:iterate>
        </select></td>
	  </tr>
	  <tr>
	    <td  align="right">票据号：</td>
	    <td><input name="billnum" type="text" id="billnum" maxlength="32"></td>
	    <td align="right">结算方式：</td>
	    <td><windrp:paymentmode name="hpaymentmode"/></td>
	    <td align="right">凭证号：</td>
	    <td><input name="voucher" type="text" id="voucher" maxlength="32"></td>
	    </tr>
	  </table>
	</fieldset>
	
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr > 
                 
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<!--<td width="3%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>-->
                <td width="22%">结算单号</td>
                <td width="22%" > 结算方式</td>
                <td width="24%">相关单据号</td>
                <td width="12%"> 应收款金额</td>
                <td width="17%">本次收款金额</td>				
                </tr>
				<c:set var="count" value="2"/>
            <logic:iterate id="p" name="als" > 
            <tr class="table-back">
				<!--<td><input type="checkbox" value="${count}" name="che"></td>-->
                <td><input name="rid" type="text" id="rid" value="${p.id}" size="12"></td>
                <td ><input name="paymentmode" type="hidden" value="${p.paymentmode}" ><input name="paymentmodename" type="text" value="${p.paymentmodename}" id="paymentmodename" size="12" readonly></td>
				<td><input name="billno" type="text" value="${p.billno}" id="billno" size="15" readonly></td>
                <td><input name="receivablesum" type="text" value="${p.receivablesum}" id="receivablesum" size="8" readonly></td>               
                <td><input name="thisreceivablesum" type="text" value="${p.alreadysum}" onChange="TotalSum();" id="thisreceivablesum" size="10" maxlength="10" onKeyPress="KeyPress(this)"></td>               
                </tr>
				<c:set var="count" value="${count+1}"/>
			</logic:iterate> 
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="3%" ><!--<a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a>--></td>
                <td width="11%">&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="64%" align="right"><input type="button" name="button" value="收款金额" onClick="TotalSum();"></td>
                <td width="15%"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10" value="${ilf.incomesum}"></td>
              </tr>
            </table></td>
          </tr>
         <tr>
            <td  align="center" ><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr >
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false"></textarea></td>
              </tr>
            </table></td>
          </tr>
          <tr> 
            <td width="33%"> <div align="center">
              <input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
              <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();">
            </div></td>
          </tr>
    </table>
    </form>
    </td>
  </tr>
</table>
</body>
</html>
