<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/prototype.js"></script>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	function Affirm(){
		if ( !Validator.Validate(document.listform,2) ){
			return;
		}
			
		var flag=false;
		var k=0;
		if(document.listform.pid.length>1){
		for(var i=0;i<document.listform.pid.length;i++){
			if(document.listform.pid[i].checked){
				flag=true;//只要选中一个就设为true
				if ( parseFloat(listform.purchasequantity[i].value) > parseFloat(listform.maxquantity[i].value)) {
					alert("本次完成数量不能大于 数量－完成数量!");
					listform.purchasequantity[i].select();
					return;
				}	
			}
		}
	}else{
			if(document.listform.pid.checked){
				//k++;
				flag=true;//只要选中一个就设为true
				if ( parseFloat(listform.purchasequantity.value) > parseFloat(listform.maxquantity.value)) {
					alert("本次完成数量不能大于 数量－完成数量!");
					listform.purchasequantity.select();
					return;
				}
			}
	}
	
		
		if(flag){
			showloading();
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
					//document.listform.unitprice[i].disabled=false;
					document.listform.purchasequantity[i].disabled=false;
					document.listform.unitid[i].disabled=false;
					}
			}
		}else{
				if (!document.listform.pid.checked)
					if(listform.elements.name != "checkall"){
					document.listform.pid.checked=true;
					//document.listform.unitprice.disabled=false;
					document.listform.purchasequantity.disabled=false;
					document.listform.unitid.disabled=false;
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
					//document.listform.unitprice[i].disabled=true;
					document.listform.purchasequantity[i].disabled=true;
					document.listform.unitid[i].disabled=true;
					}
			}
		}else{
				if (document.listform.pid.checked)
					if(listform.elements.name != "checkall"){
					document.listform.pid.checked=false;
					//document.listform.unitprice.disabled=true;
					document.listform.purchasequantity.disabled=true;
					document.listform.unitid.disabled=true;
					}
		}
	}
	
	function onlycheck(){
	var pidleng=document.listform.pid.length;

		if(pidleng>1){
			for(var i=0;i<pidleng;i++){
				if(document.listform.pid[i].checked){
					//document.listform.unitprice[i].disabled=false;
					document.listform.purchasequantity[i].disabled=false;
					document.listform.unitid[i].disabled=false;
				}
				if(!document.listform.pid[i].checked){
					//document.listform.unitprice[i].disabled=true;
					document.listform.purchasequantity[i].disabled=true;
					document.listform.unitid[i].disabled=true;
				}
			}
		}else{
				if(document.listform.pid.checked){
					//document.listform.unitprice.disabled=false;
					document.listform.purchasequantity.disabled=false;
					document.listform.unitid.disabled=false;
				}
				if(!document.listform.pid.checked){
					//document.listform.unitprice.disabled=true;
					document.listform.purchasequantity.disabled=true;
					document.listform.unitid.disabled=true;
				}
		}
	}

function SubTotal(){
	var sum=0.00;
	var unitprice=document.listform.unitprice;
	var quantity=document.listform.purchasequantity;
	var objsubsum=document.listform.subsum;
	if ( unitprice.length){
		for (var m=0; m<unitprice.length; m++){
			objsubsum[m].value=formatCurrency(parseFloat(unitprice[m].value*quantity[m].value));
		}
	}else{
		objsubsum.value=formatCurrency(parseFloat(unitprice.value*quantity.value));
	}
}
	
function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.listform.providerid.value=p.pid;
	document.listform.pname.value=p.pname;
	document.listform.receiveaddr.value=p.addr;
	getLinkman(p.pid);
}

function getLinkman(v_cid){
	var url = "../sales/ajaxProviderLinkmanAction.do?cid="+v_cid;
	var myAjax = new Ajax.Request(
			url,
			{method: 'get', parameters: '', onComplete: showLman}
			);	
}
function showLman(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.linkman;
	if ( lk != undefined ){			
		document.listform.plinkman.value=lk.name;
		document.listform.tel.value=lk.mobile;
	}
}

function SelectLinkman(){
	var pid=document.listform.providerid.value;
	if(pid==null||pid=="")
	{
		alert("请选择供应商！");
		return;
	}
	var l=showModalDialog("../common/selectPlinkmanAction.do?pid="+pid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	if ( l == undefined ){
		return;
	}
	document.listform.plinkman.value=l.lname;
	document.listform.tel.value=l.lmobile;
}

function showproduct(){
	var pid=document.listform.providerid.value;
	if(pid==null||pid=="")
	{
		alert("请选择供应商！");
		return;
	}
	popWin("../common/selectProviderInProductAction.do?pvid="+pid,800,600);	
}
	

</script>
</head>

<body style="overflow: auto;">
<form name="listform" method="post" action="makePurchaseBillAction.do">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 采购计划产品列表
            <input name="ppid" type="hidden" id="ppid" value="${ppid}"></td>
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
	  	<td width="10%"  align="right">供应商：</td>
          <td width="26%"><input name="providerid" type="hidden" id="providerid">
            <input name="pname" type="text" id="pname" size="35" readonly dataType="Require" msg="必须录入供应商!"><a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>&nbsp;<a href="javascript:showproduct()"><img src="../images/CN/add.gif" width="18" height="18" border="0" align="absmiddle"></a><span class="STYLE1">*</span></td>
          <td width="9%" align="right">联系人：</td>
          <td width="26%"><input name="plinkman" type="text" id="plinkman" dataType="Require" msg="必须录入联系人!" readonly><a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a><span class="STYLE1">*</span></td>
	      <td width="9%" align="right">联系电话：</td>
	      <td width="20%"><input name="tel" type="text" id="tel" dataType="PhoneOrMobile" msg="必须录入正确联系电话!"><span class="STYLE1">*</span></td>
	  </tr>
	  <tr>
	    <td  align="right">采购部门：</td>
	    <td><input type="hidden" name="purchasedept" value="${pp.makedeptid}"><input type="text" name="pdeptname" size="15" value="<windrp:getname key='dept' value='${pp.makedeptid}' p='d'/>" readonly>
        
       </td>
	    <td align="right">采购人员：</td>
	    <td><input type="hidden" name="purchaseid" value="${pp.makeid}"><input type="text" size="15" name="planidname" value="<windrp:getname key='users' value='${pp.makeid}' p='d'/> " readonly></td>
	    <td align="right">结算方式：</td>
	    <td><windrp:paymentmode name="paymode"/></td>
	  </tr>
	  <tr>
	    <td  align="right">预计到货日期：</td>
	    <td><input name="receivedate" type="text" id="receivedate" onFocus="selectDate(this);" readonly value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"></td>
	    <td align="right">收货地址：</td>
	    <td><input name="receiveaddr" type="text" id="receiveaddr" size="35" maxlength="256"></td>
	    <td align="right">发票信息：</td>
	    <td><select name="invmsg">
          <logic:iterate id="c" name="icls">
            <option value="${c.id}">${c.ivname}</option>
          </logic:iterate>
        </select></td>
	    </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top">
            <td width="2%"><input type="checkbox" name="checkall" onClick="Check();" ></td> 
            <td width="27%" >产品</td>
            <td width="12%">规格</td>
            <td width="8%">单位</td>
			<td width="10%">单价</td>
			<td width="6%">数量</td>
            <td width="9%">完成数量</td>
            <td width="9%">本次完成数量</td>
            <td width="9%">金额</td>
          </tr>
          <logic:iterate id="a" name="als" > 
          <tr align="center" class="table-back">
            <td><input type="checkbox" name="pid" value="${a.productid}" onClick="onlycheck();"></td> 
            <td ><input name="productname" type="hidden" id="productname" value="${a.productname}">
              ${a.productname}</td>
            <td><input name="specmode" type="hidden" id="specmode" value="${a.specmode}">
              ${a.specmode}</td>
            <td><input name="unitid" type="hidden" value="${a.unitid}" disabled><windrp:getname key='CountUnit' value='${a.unitid}' p='d'/></td>
			<td><input name="unitprice" type="hidden" id="unitprice" value="${a.unitprice}">${a.unitprice}</td>
			<td>${a.quantity}<input type="hidden" name="quantity" value="${a.quantity}"></td>
            <td>${a.changequantity}<input type="hidden" name="maxquantity" value="${a.quantity - a.changequantity}"></td>
            <td><input name="purchasequantity" type="text" id="purchasequantity" size="8" value="${a.quantity - a.changequantity}" onKeyDown="onlyNumber(event)"  onchange="SubTotal()" onFocus="this.select();SubTotal();" disabled></td>
            <td><input type="text" name="subsum" id="subsum" size="8" value="" readonly></td>
          </tr>
          </logic:iterate> 
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table width="148"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="80"><input name="提交" type="button" value="生成采购订单" onClick="Affirm();"></td>
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
