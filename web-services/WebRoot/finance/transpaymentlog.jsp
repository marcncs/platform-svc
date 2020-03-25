<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
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
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";		
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"pid\" type=\"text\" id=\"pid\" size=\"12\" readonly>";
		c.innerHTML="<input name=\"paymentmode\" type=\"hidden\" id=\"paymentmode\"><input name=\"paymentmodename\" type=\"text\" id=\"paymentmodename\" size=\"12\" readonly>";
		d.innerHTML="<input name=\"billno\" type=\"text\" id=\"billno\" size=\"12\" readonly>";
        e.innerHTML="<input name=\"payablesum\" type=\"text\" id=\"payablesum\" size=\"8\" readonly>";
        f.innerHTML="<input name=\"thispayablesum\" type=\"text\" id=\"thispayablesum\" value=\"0\" size=\"10\" maxlength=\"10\" onchange=\"TotalSum();\" onFocus=\"TotalSum();\">";       
		i++;
    chebox=document.all("che");  //計算total值

 	//SubTotal(dbtable.rows);
	//TotalSum();
}
	

function SupperSelect(rowx){
	var roid=document.validateProvide.poid.value;
	if(roid==null||roid=="")
	{
		alert("请选择应付款对象！");
		return;
	}
	var p =showModalDialog("toSelectPayableAction.do?poid="+roid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");

	var arpid=p.pid;
	var arrpaymentmode=p.paymentmode;
	var arrpaymentmodename = p.paymentmodename;
	var arrbillno=p.billno;
	var arrpayablesum=p.payablesum;	
	if((dbtable.rows.length-1) <1){
		for(var i=0;i<arpid.length;i++){
				addRow();
				if(i==0){//第一次加时还不是数组
					document.validateProvide.item("pid").value =arpid[0];
					document.validateProvide.item("paymentmode").value =arrpaymentmode[0];
					document.validateProvide.item("paymentmodename").value=arrpaymentmodename[0];
					document.validateProvide.item("billno").value =arrbillno[0];
					document.validateProvide.item("payablesum").value =arrpayablesum[0];
					document.validateProvide.item("thispayablesum").value=arrpayablesum[0];
					}else{
						document.validateProvide.item("pid")[i].value =arpid[i];
						document.validateProvide.item("paymentmode")[i].value =arrpaymentmode[i];
					document.validateProvide.item("paymentmodename")[i].value=arrpaymentmodename[i];
					document.validateProvide.item("billno")[i].value =arrbillno[i];
					document.validateProvide.item("payablesum")[i].value =arrpayablesum[i];
					document.validateProvide.item("thispayablesum")[i].value=arrpayablesum[i];
					}
			}
			
			//SubTotal(rowx);
			TotalSum();
	}else{
		for(var i=0;i<arpid.length;i++){
			addRow();
			document.validateProvide.item("pid")[rowx-1+i].value =arpid[i];//加i是从已经有的row行起算从0行开始
			document.validateProvide.item("paymode")[rowx-1+i].value =arrpaymentmode[i];
					document.validateProvide.item("paymentmodename")[rowx-1+i].value=arrpaymentmodename[i];
					document.validateProvide.item("billno")[rowx-1+i].value =arrbillno[i];
					document.validateProvide.item("payablesum")[rowx-1+i].value =arrpayablesum[i];
					document.validateProvide.item("thispayablesum")[rowx-1+i].value=arrpayablesum[i];
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
		totalsum=totalsum+parseFloat(document.validateProvide.item("thispayablesum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.validateProvide.item("thispayablesum")(i).value);
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
		var paysum = document.addform.paysum.value;
		var owebalance = 0.00;
		owebalance = parseFloat(dealsum)-parseFloat(paysum);
		document.addform.owebalance.value=owebalance;
	}

function SelectPayableObject(){
		showModalDialog("toSelectPayableObjectAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
		document.validateProvide.poid.value=getCookie("poid");
		document.validateProvide.payee.value=getCookie("payeeidname");
		document.validateProvide.payeeid.value=getCookie("payeeidname");
	}
	
function ChkValue(){
		var poid = document.validateProvide.poid;
		var fundsrc = document.validateProvide.fundsrc;
		if(poid.value==""){
			alert("付款对象不能为空！");
			return false;
		}
		if(fundsrc.value==""){
			alert("款项来源不能为空！");
			return false;
		}
		
		if ( !Validator.Validate(document.validateProduct,2) ){
		return false;
		}
		if ( isMaxSum() ){
			alert("本次付款金额不能超过应付款金额!");
			return false;
		}
		
		showloading();
		return true;
	}
	
function isMaxSum(){
	var payablesum=document.all("payablesum");
	var thispayablesum = document.all("thispayablesum");
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

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 转付款</td>
        </tr>
      </table> 
	  <form name="validateProvide" method="post" action="../finance/transPaymentLogAction.do" onSubmit="return ChkValue();">
        
	   <table width="100%" border="0" cellpadding="0" cellspacing="1">
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
	  	<td width="10%"  align="right"><input name="poid" type="hidden" id="poid" value="${poid}">
应付款对象：</td>
          <td width="26%"><input name="payeeid" type="text" id="payeeid" size="35" value="${payee}" readonly></td>
          <td width="10%" align="right">领款人：</td>
          <td width="25%"><input name="payee" type="text" id="payee" value="${payee}" size="35" maxlength="64"></td>
	      <td width="8%" align="right">票据号：</td>
	      <td width="21%"><input name="billnum" type="text" id="billnum" maxlength="32"></td>
	  </tr>
	  <tr>
	    <td  align="right">付款方式：</td>
	    <td><windrp:paymentmode name="hpaymode"/></td>
	    <td align="right">款项来源：</td>
	    <td><select name="fundsrc" id="fundsrc">
          <logic:iterate id="u" name="cblist">
            <option value="${u.id}">${u.cbname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">凭证号：</td>
	    <td><input name="voucher" type="text" id="voucher" maxlength="32"></td>
	    </tr>
	  <tr>
	    <td  align="right">付款用途：</td>
	    <td><input name="paypurpose" type="text" id="paypurpose" size="35" maxlength="256"></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	</td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<!--<td width="3%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>-->
                <td width="22%">结算单号</td>
                <td width="22%" > 结算方式</td>
                <td width="24%">相关单据号</td>
                <td width="12%"> 应付款金额</td>
                <td width="17%">本次付款金额</td>				
                </tr>
				<c:set var="count" value="2"/>
            <logic:iterate id="p" name="als" > 
            <tr class="table-back">
				<!--<td><input type="checkbox" value="${count}" name="che"></td>-->
                <td><input name="pid" type="text" id="pid" value="${p.id}" size="12" readonly></td>
                <td ><input name="paymentmode" type="hidden" value="${p.paymode}" ><input name="paymentmodename" type="text" value="${p.paymodename}" id="paymentmodename" size="12" readonly></td>
				<td><input name="billno" type="text" value="${p.billno}" id="billno" size="15" readonly></td>
                <td><input name="payablesum" type="text" value="${p.payablesum}" id="payablesum" size="8" readonly></td>               
                <td><input name="thispayablesum" type="text" value="${p.alreadysum}" onChange="TotalSum();" id="thispayablesum" size="10" onKeyPress="KeyPress(this)" maxlength="10" dataType="Double" msg="金额只能是数字!" require="false"></td>               
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
                <td width="15%"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10" readonly></td>
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
            <td width="13%"  align="center"><input type="submit" name="Submit2" value="确定">&nbsp;&nbsp;
              <input name="cancel2" type="button" id="cancel2" value="取消" onClick="javascript:window.close();"></td>
          </tr>
	 
    </table>
     </form>
    </td>
  </tr>
</table>
</body>
</html>
