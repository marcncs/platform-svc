<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
 function addRow(p){
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
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+p.productid+"' size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" value='"+p.productname+"' size=\"45\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" value='"+p.specmode+"' size=\"10\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" value='"+p.unitid+"' size=\"6\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" value='"+p.unitidname+"' readonly>";
        f.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value='"+formatCurrency(p.price)+"' size=\"10\" maxlength=\"10\" readonly>";
		g.innerHTML="<input name=\"taxunitprice\" type=\"text\" id=\"taxunitprice\" value='"+formatCurrency(p.taxprice)+"' size=\"8\" maxlength=\"10\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" onKeyPress=\"KeyPress(this)\" dataType=\"Double\" msg=\"金额只能是数字!\" require=\"false\">";
        h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"8\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\"  onKeyPress=\"KeyPress(this)\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\">";		
		i.innerHTML="<input name=\"discount\" type=\"text\" id=\"discount\" value='"+p.discount+"' size=\"8\" maxlength=\"8\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" readonly>%";
		j.innerHTML="<input name=\"taxrate\" type=\"text\" id=\"taxrate\" value='"+p.taxrate+"' size=\"8\" maxlength=\"8\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" readonly>%";
        k.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\" readonly>";

 	SubTotal();
	TotalSum();
}
	

function SupperSelect(){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	popWin4("../common/selectWithdrawProductAction.do?cid="+cid,800,600,"new");
}

//--------------------------------start -----------------------	
function addItemValue(obj){
	var vproductid = document.validateProvide.productid;	
	if ( isready('productid', obj.productid) ){
		return;
	}
	addRow(obj);
}
//--------------------------------end -----------------------	

function SubTotal(){
	var sum=0.00;
	var unitprice=document.validateProvide.taxunitprice;
	var discount=document.validateProvide.discount;
	var quantity=document.validateProvide.quantity;
	var objsubsum=document.validateProvide.subsum;
	if ( unitprice.length){
		for (var m=0; m<unitprice.length; m++){
			objsubsum[m].value=formatCurrency(parseFloat(unitprice[m].value*quantity[m].value*discount[m].value/100));
		}
	}else{
		objsubsum.value=formatCurrency(parseFloat(unitprice.value*quantity.value*discount.value/100));
	}
}

function TotalSum(){
	var totalsum=0.00;
	var objsubsum=document.validateProvide.subsum;
	if ( objsubsum.length){
		for (var m=0; m<objsubsum.length; m++){
			totalsum=totalsum+parseFloat(objsubsum[m].value);
		}
	}else{
		totalsum=parseFloat(objsubsum.value);
	}
	document.validateProvide.totalsum.value=formatCurrency(totalsum);
}

function getCustomerByWhere(objvalue, temp){
		if (event.keyCode != 13) { 			
			return;
		} 
		var url ="";
		if ( temp == 1 ){
			 url = '../sales/ajaxCustomerAction.do?temp=1&cid='+objvalue;
		}else if ( temp == 2 ){
			 url = '../sales/ajaxCustomerAction.do?temp=2&cname='+objvalue;
		}else if ( temp == 3 ){
			 url = '../sales/ajaxCustomerAction.do?temp=3&mobile='+objvalue;
		}else if ( temp == 4 ){
			 url = '../sales/ajaxCustomerAction.do?temp=4&officetel='+objvalue;
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
			setDefaultValue(cur.cid,cur.cname,lk.name,cur.mobile,cur.officetel);			
		}
	}
	function setDefaultValue(cid, cname, decideman, mobile, officetel){
		document.validateProvide.cid.value=cid;
		document.validateProvide.cname.value=cname;
		document.validateProvide.decideman.value=decideman;
		document.validateProvide.cmobile.value=mobile;
		document.validateProvide.decidemantel.value=officetel;

	}

function SelectCustomer(){
	var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrollbars=yes;");
	document.validateProvide.cid.value=c.cid;
	document.validateProvide.cname.value=c.cname;
	document.validateProvide.cmobile.value=c.mobile;
	document.validateProvide.decidemantel.value=c.officetel;

	getLinkmanBycid(c.cid);
}


function SelectLinkman(){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid==""){
		alert("请选择客户！");
		return;
	}
	var lk=showModalDialog("../common/toSelectLinkmanAction.do?cid="+cid,null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	document.validateProvide.decideman.value=lk.lname;
}

function Check(){
	var pid = document.all("che");
	var checkall = document.all("checkall");
	if (pid==undefined){return;}
	if (pid.length){
		for(i=0;i<pid.length;i++){
				pid[i].checked=checkall.checked;
		}
	}else{
		pid.checked=checkall.checked;
	}		
}

	function ChkValue(){
        var cname = document.validateProvide.cname;
		var productid = document.validateProvide.productid;
		
		if(cname.value==null||cname.value==""){
			alert("会员名称不能为空!");
			return false;
		}
		
		if(productid==undefined){
			alert("请选择产品！");
			return false;
		}
		
		if ( !Validator.Validate(document.validateProvide,2) ){
		return false;
		}

		showloading();
		validateProvide.submit();
	}
	
function SelectSalebill(){
		var cid=document.validateProvide.cid.value;
		if(cid==null||cid=="")
		{
			alert("请选择客户！");
			return;
		} 
		window.open("../aftersale/listViewSaleBillAction.do?cid="+cid,null,"height=550,width=750,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");
	
	}

</script>
</head>

<body style="overflow:auto">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改零售退货</td>
        </tr>
      </table>
       <form name="validateProvide" method="post" action="../aftersale/updWithdrawAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
		<input name="id" type="hidden" id="id" value="${sof.id}">
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
          <input name="id" type="hidden" id="id" value="${sof.id}">
          <td  align="right">会员编号：</td>
	      <td><input name="cid" type="text" id="cid" value="${sof.cid}" onKeyUp="getCustomerByWhere(this.value,1)"></td>
	      <td align="right">客户名称：</td>
	      <td><input name="cname" type="text" id="cname" value="${sof.cname}" onKeyUp="getCustomerByWhere(this.value,2)" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
          <span class="STYLE1" >*</span>
          </td>
	      <td align="right">收货人：</td>
	      <td><input name="decideman" type="text" id="decideman" value="${sof.clinkman}" readonly><a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
	      </tr>
	    <tr>
          <td align="right">会员手机：</td>
	      <td ><input name="cmobile" type="text" id="cmobile" maxlength="11" onKeyUp="getCustomerByWhere(this.value,3)" value="${sof.cmobile}"></td>
	      <td align="right">会员电话：</td>
	      <td><input name="decidemantel" type="text" id="decidemantel" onKeyUp="getCustomerByWhere(this.value,4)" value="${sof.tel}"></td>
	      <td align="right">结算方式：</td>
	      <td><windrp:paymentmode name="paymentmode" value="${sof.paymentmode}"/></td>
	    </tr>
	  
	  <tr>
	    
	    <td width="9%" align="right">入货仓库：</td>
	    <td width="21%"><windrp:warehouse name="warehouseid" value="${sof.warehouseid}"/></td>
		<td width="13%"  align="right">退货类型：</td>
	    <td width="23%"><windrp:select key="WithdrawSort" name="withdrawsort" p="n|d" value="${sof.withdrawsort}"/></td>
	    <td width="9%" align="right">&nbsp;</td>
	    <td width="25%">&nbsp;</td>
	    </tr>
		 <tr>
	    
	    <td align="right">相关单据号：</td>
	    <td  colspan="5"><input name="billno" type="text" id="billno"  value="${sof.billno}" size="60" maxlength="128">
	    <!--<a href="javascript:SelectSalebill();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>--></td>
		 </tr>
		 <tr>
	    
	    <td align="right">退货原因：</td>
	    <td  colspan="5"><input name="withdrawcause" type="text" id="withdrawcause" value="${sof.withdrawcause}" size="60" maxlength="128"></td>
		</tr>
	  </table>
</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr > 
                  <td width="100%" ><table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                      <td id="elect" ><img src="../images/CN/selectp.gif" width="72" height="21" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>

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
                <td width="16%" > 产品名称</td>
                <td width="14%">规格型号</td>
                <td width="7%"> 单位</td>
                <td width="6%"> 单价</td>
				 <td width="13%">税后单价</td>
                <td width="8%"> 数量</td>
				<td width="10%"> 折扣率</td>
				<td width="10%"> 税率</td>
                <td width="11%"> 金额</td>
                </tr>
				<c:set var="count" value="2"/>
            <logic:iterate id="p" name="als" > 
            <tr class="table-back">
				<td><input type="checkbox" value="${count}" name="che"></td>
                <td><input name="productid" type="text" id="productid" value="${p.productid}" size="12"></td>
                <td ><input name="productname" type="text" value="${p.productname}" id="productname" size="45" readonly></td>
				<td><input name="specmode" type="text" value="${p.specmode}" id="specmode" size="10" readonly></td>
                <td><input name="unitid" type="hidden" value="${p.unitid}"><input name="unit" type="text" value="${p.unitidname}" id="unit" size="8" readonly></td>
				 <td><input name="unitprice" type="text" value="<windrp:format value='${p.unitprice}'/>" onChange="SubTotal();TotalSum();" id="unitprice" size="10" maxlength="10" dataType="Double" msg="单价只能是数字!" require="false" readonly></td>
				<td><input name="taxunitprice" type="text" value="<windrp:format value='${p.taxunitprice}'/>"  id="taxunitprice" size="8" onKeyPress="KeyPress(this)" dataType="Double" msg="税后单价只能是数字!" require="false"></td>               
                <td><input name="quantity" type="text" value="<windrp:format value='${p.quantity}'/>" onChange="SubTotal();TotalSum();" id="quantity" size="8" maxlength="8" onKeyPress="KeyPress(this)" dataType="Double" msg="数量只能是数字!" require="false"></td>
				 <td><input name="discount" type="text" value="${p.discount}" onChange="SubTotal(${count});TotalSum();" id="discount" size="8" maxlength="8" readonly>%</td>
				  <td><input name="taxrate" type="text" value="${p.taxrate}" onChange="SubTotal(${count});TotalSum();" id="taxrate" size="8" maxlength="8" readonly>%</td>
                <td><input name="subsum" type="text" value="${p.subsum}" id="subsum" size="10" maxlength="10" readonly></td>
                </tr>
				<c:set var="count" value="${count+1}"/>
			</logic:iterate> 
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="2%" ><a href="javascript:deleteR('che','dbtable')"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="11%">&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="64%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();">：</td>
                <td width="15%"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10" value="${sof.totalsum}"></td>
              </tr>
            </table></td>
          </tr>
       <!--   <tr>
            <td  align="center" ><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr >
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark"></textarea></td>
              </tr>
            </table></td>
          </tr>-->
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
