<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT type=text/javascript src="../js/validator.js"></SCRIPT>
<script type=text/javascript src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/Currency.js"> </SCRIPT>
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
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";
 
        a.innerHTML="<input type='checkbox' value='' name='che'>";
        b.innerHTML="<input name='productid' type='text' id='productid' readonly value='"+p.productid+"'>";
		c.innerHTML="<input name='productname' type='text' id='productname' size='40' readonly value='"+p.productname+"'>";
		d.innerHTML="<input name='specmode' type='text' id='specmode' size='15' readonly value='"+p.specmode+"'>";
        e.innerHTML="<input name='unitid' type='hidden' id='unitid' size='6' value='"+p.unitid+"'><input name='unit' type='text' id='unit' size='8' readonly value='"+p.unitidname+"'>";
        f.innerHTML="<input name='unitprice' type='text' id='unitprice' readonly value='"+p.unitprice+"' size='10' maxlength='10' onchange='SubTotal("+dbtable.rows.length+");TotalSum();' onFocus='SubTotal("+dbtable.rows.length+");TotalSum();'>";
        g.innerHTML="<input name='quantity' type='text' id='quantity'  dataType='Currency' msg='必须录入数值类型!' onKeyPress='KeyPress(this)' value='1' size='8' maxlength='8' onchange='SubTotal("+dbtable.rows.length+");TotalSum();' onFocus='SubTotal("+dbtable.rows.length+");TotalSum();'>";
        var subsum = formatCurrency(p.unitprice*1);
        h.innerHTML="<input name='subsum' type='text' id='subsum' readonly value='"+subsum+"' size='10' maxlength='10'>";

	TotalSum();
}
	

function SupperSelect(rowx){
	var cid=document.validateProvide.cid.value;
	if(cid==""){
		alert("请选择对象！");
		return;
	}
	
	var p=showModalDialog("../common/toSelectOrganProductPriceAction.do?OID="+'${oid}',null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	for(var i=0;i<p.length;i++){	
		if ( isready('productid', p[i].productid) ){
			continue;
		}
		addRow(p[i]);	
			
	}
}


function SubTotal(rowx){
	var sum=0.00;
	if((dbtable.rows.length-1) <=1){
		sum=(document.getElementById("unitprice").value)*(document.getElementById("quantity").value);
		document.getElementById("subsum").value=formatCurrency(sum);
	}else{
		for(var m=0;m<(dbtable.rows.length -1);m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
		document.validateProvide.item("subsum")(m).value=formatCurrency(sum);
		}
		
	}
	
}

function TotalSum(){
	var totalsum=0.00;
	
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.getElementById("subsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.validateProvide.item("subsum")(i).value);
		}
	}
	document.validateProvide.totalsum.value=totalsum;
}

function SelectName(){
		var objsort = document.validateProvide.objsort;
		if(objsort.value ==1){
			SelectCustomer();
		}else{
			SelectOrgan();
		}
	}
	
	function getCustomerLinkmanBycid(objcid){
        var url = '../sales/ajaxCustomerLinkmanAction.do?cid='+objcid;
        var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showLinkman}
                    );	
    }
    
    function getOrganLinkmanBycid(objcid){
        var url = '../sales/ajaxOrganLinkmanAction.do?cid='+objcid;
        var pars = '';
        
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showLinkman}
                    );	
    }
    
    function showLinkman(originalRequest)
    {
		var data = eval('(' + originalRequest.responseText + ')');
		var lk = data.linkman;
		if ( lk == undefined ){
			document.validateProvide.linkman.value='';
		}else{
			document.validateProvide.linkman.value=lk.name;
			document.validateProvide.tel.value=lk.officetel;
			if(document.validateProvide.tel.value == ""){
				document.validateProvide.tel.value=lk.mobile;
			}
		}
	}
    
	function SelectCustomer(){
		var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if(c==undefined){return;}
		document.validateProvide.cid.value=c.cid;
		document.validateProvide.cname.value=c.cname;
		getCustomerLinkmanBycid(c.cid);
	}
	function SelectOrgan(){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if(o==undefined){return;}
		document.validateProvide.cid.value=o.id;
		document.validateProvide.cname.value=o.organname;
		getOrganLinkmanBycid(o.id);
		document.validateProvide.receiveaddr.value=o.oaddr;

	} 
	function SelectLinkman(){
		var cid=document.validateProvide.cid.value;
		if(cid==null||cid=="")
		{
			alert("请选择客户！");
			return;
		}
		var objsort = document.validateProvide.objsort;
		if(objsort.value ==0){
			var lk=showModalDialog("../common/selectOrganLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			document.validateProvide.linkman.value=lk.lname;
			document.validateProvide.tel.value=lk.ltel;
			if(document.validateProvide.tel.value == ""){
				document.validateProvide.tel.value=lk.mobile;
			}
		}else{
			var lk=showModalDialog("../common/selectMemberLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			document.validateProvide.linkman.value=lk.lname;
			document.validateProvide.tel.value=lk.ltel;
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


	function Check(){

		var checkche  = document.getElementsByName("che");
		var checkall = document.getElementById("checkall");
		for(i=0;i<checkche.length;i++){
			checkche[i].checked = checkall.checked;
		}

	}


</script>
</head>

<body style="overflow: auto;">

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改样品</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="updSampleBillAction.do" onSubmit="return Validator.Validate(this,2)">
        <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr> 
            <td height="25">
			
		<fieldset align="center"> <legend>
      <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本资料</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%" align="right"> 对象类型：</td>
	  	<td><input name="objsort" type="hidden" id="objsort" value="${sbf.objsort}">
	  		<windrp:getname key="ObjSort" p="f" value="${sbf.objsort}"/>
	  	</td>
	  </tr>
	  <tr>
	  	<td width="9%" height="20" align="right"><input name="ID" type="hidden" id="ID" value="${sbf.id}">
          <input name="cid" type="hidden" id="cid" value="${sbf.cid}">
          客户名称：</td>
          <td width="21%"><input name="cname" type="text" id="cname" value="${sbf.cname}" dataType="Require" msg="客户不能为空!" readonly><a href="javascript:SelectName();"><img src="../images/CN/find.gif" width="18" height="18" border="0"  align="absmiddle"></a><span class="STYLE1">*</span></td>
          <td width="10%" align="right">联系人：</td>
          <td width="26%"><input name="linkman" type="text" id="linkman" value="${sbf.linkman}" dataType="Require" msg="联系人不能为空!"><a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a><span class="STYLE1">*</span></td>
          <td width="9%" align="right">联系电话：</td>
	      <td width="25%"><input name="tel" type="text" id="tel" value="${sbf.tel}" dataType="PhoneOrMobile" msg="必须输入正确的联系电话!"><span class="STYLE1">*</span></td>
	  </tr>
	  <tr>
	    <td height="20" align="right">发货日期：</td>
	    <td><input name="shipmentdate" type="text" id="shipmentdate" onFocus="javascript:selectDate(this)" value="<windrp:dateformat value="${sbf.shipmentdate}" p="yyyy-MM-dd"/>" readonly>
          <span class="STYLE1">*</span></td>
	    <td align="right">约定回收日期：</td>
	    <td><input name="estimaterecycle" type="text" id="estimaterecycle" onFocus="javascript:selectDate(this)" value="<windrp:dateformat value="${sbf.estimaterecycle}" p="yyyy-MM-dd"/>" readonly>
	      <span class="STYLE1">*</span></td>
	    <td align="right">样品制作者：</td>
	    <td><input name="makeuser" type="text" id="makeuser" value="${sbf.makeuser}" maxlength="50"></td>
	  </tr>
	  <tr>
	    <td height="20" align="right">邮编：</td>
	    <td><span class="STYLE1">
	      <input name="postcode" type="text" id="postcode" value="${sbf.postcode}" size="8" maxlength="6" onKeyDown="onlyNumber(event);">
          </span></td>
	    <td align="right">送货地址：</td>
	    <td colspan="3"><input name="receiveaddr" type="text" id="receiveaddr" value="${sbf.receiveaddr}" size="50" maxlength="100"></td>
	    
	    </tr>
	  </table>
</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                
                <tr> 
                  <td width="100%"><table height="22" border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                      <td id="elect"><img src="../images/CN/selectp.gif" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                    </tr>
                  </table></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td height="25" align="right">
            <fieldset>
            <legend>产品资料</legend>
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<td width="3%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="6%">产品编码</td>
                <td width="29%" height="25"> 产品名称 </td>
				<td width="31%">规格型号</td>
                <td width="9%"> 单位</td>
                <td width="8%"> 单价</td>
                <td width="6%"> 数量</td>
                <td width="8%"> 金额</td>
                </tr>
			<c:set var="count" value="2"/>
            <logic:iterate id="p" name="als" > 
            <tr class="table-back">
				<td><input type="checkbox" value="${count}" name="che"></td>
                <td><input name="productid" type="text" id="productid" value="${p.productid}" ></td>
                <td height="20"><input name="productname" type="text" value="${p.productname}" id="productname" size="40" readonly></td>
				<td><input name="specmode" type="text" value="${p.specmode}" id="specmode" size="15" readonly></td>
                <td><input name="unitid" type="hidden" value="${p.unitid}"><input name="unit" type="text" value="${p.unitid}" id="unit" size="8" readonly></td>
                <td><input name="unitprice" type="text" value="<fmt:formatNumber value='${p.unitprice}' pattern='0.00'/>" onChange="SubTotal(${count});TotalSum();" id="unitprice" size="10" maxlength="10"></td>
                <td><input name="quantity" type="text" value="${p.quantity}" onChange="SubTotal(${count});TotalSum();" id="quantity" size="8" maxlength="8"></td>
                <td><input name="subsum" type="text" value="<fmt:formatNumber value='${p.subsum}' pattern='0.00'/>" id="subsum" size="10" maxlength="10"></td>
                </tr>
				<c:set var="count" value="${count+1}"/>
			</logic:iterate> 
            </table>
			<table width="100%" height="20"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="3%" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif" width="19" height="20" border="0"></a></td>
                <td  align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();"></td>
                <td width="15%"><input name="totalsum" type="text" id="totalsum" value="${sbf.totalsum}" size="10" readonly maxlength="10"></td>
              </tr>
            </table>
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr >
                <td width="9%" align="right"> 备注： </td>
                <td ><textarea name="remark" style="width: 100%" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false">${sbf.remark}</textarea><br>
							<span class="td-blankout">(备注长度不能超过256字符)</span></td>
              </tr>
            </table>
                  </fieldset>
            </td>
          </tr>
          <tr>
            <td height="20" align="center" ><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
