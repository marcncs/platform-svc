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
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
  function addRow(p){
    var x = document.all("dbtable").insertRow(dbtable.rows.length);
 
        var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);
        var d=x.insertCell(3);
        var e=x.insertCell(4);
		var g=x.insertCell(5);
		var h=x.insertCell(6);
		var i=x.insertCell(7);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";
		i.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\" align=\"left\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" readonly value='"+p.productid+"'>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"35\" readonly value='"+p.productname+"'>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"35\" readonly value='"+p.specmode+"'>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"8\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly value='"+p.unitname+"'>";
        g.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value='"+p.price+"' size=\"10\" maxlength=\"10\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" onKeyPress=\"KeyPress(this)\" dataType=\"Double\" msg=\"单价只能是数字!\">";
        h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"8\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" onKeyPress=\"KeyPress(this)\" dataType=\"Double\" msg=\"数量只能是数字!\">";
        i.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\" readonly>";

 	SubTotal();
	TotalSum();
}
	

function SupperSelect(rowx){
	var pid=document.validateProvide.pid.value;
	if(pid==null||pid=="")
	{
		alert("请输入供应商！");
		return;
	}
	var p = showModalDialog("../common/toSelectProviderProductAction.do?pid="+pid,null,"dialogWidth:21cm;dialogHeight:14cm;center:yes;status:no;scrolling:no;");
	if (p==undefined){return;}
	for(var i=0;i<p.length;i++){			
		if ( isready('productid', p[i].productid) ){
			continue;
		}
		addRow(p[i]);		
	}
}


function SubTotal(){
	var sum=0.00;
	var unitprice=document.validateProvide.unitprice;
	var quantity=document.validateProvide.quantity;
	var objsubsum=document.validateProvide.subsum;
	if ( unitprice.length){
		for (var m=0; m<unitprice.length; m++){
			objsubsum[m].value=formatCurrency(parseFloat(unitprice[m].value*quantity[m].value));
		}
	}else{
		objsubsum.value=formatCurrency(parseFloat(unitprice.value*quantity.value));
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
		var warehouseid = document.validateProvide.warehouseid;
		var pid = document.validateProvide.pid;
		var incomedate = document.validateProvide.incomedate;
		var productid=document.validateProvide.productid;
		
		if(warehouseid.value.trim()==null||warehouseid.value.trim()==""){
			alert("仓库不能为空");
			//totalsum.focus();
			return false;
		}
		
		if(pid.value.trim()==null||pid.value.trim()==""){
			alert("供应商不能为空");
			//totalsum.focus();
			return false;
		}

		if(incomedate.value.trim()==null||incomedate.value.trim()==""){
			alert("预计入库日期不能为空");
			//totalsum.focus();
			return false;
		}
		if ( productid==undefined){
			alert("请选择产品！");
			return false;
		}
		if ( !Validator.Validate(document.validateProvide,2) ){
			return false;
		}
	
		showloading();
		validateProvide.submit();
	}
	
	
function SelectProvide(){	
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.validateProvide.pid.value=p.pid;
	document.validateProvide.providename.value=p.pname;
	document.validateProvide.prompt.value=p.pprompt;
	getLinkman(p.pid);
}

function SelectLinkman(){
	var pid=document.validateProvide.pid.value;
	if(pid==null||pid=="")
	{
		alert("请选择供应商！");
		return;
	}
	var l=showModalDialog("../common/selectPlinkmanAction.do?pid="+pid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	if(l==undefined){
		return;
	}
	document.validateProvide.plinkman.value=l.lname;
	document.validateProvide.tel.value=l.lmobile;
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
		document.validateProvide.plinkman.value=lk.name;
		document.validateProvide.tel.value=lk.mobile;
	}
}
</script>
</head>

<body style="overflow:auto">

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增采购入库单</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="../warehouse/addPurchaseIncomeAction.do" onSubmit="return ChkValue();">
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
	  	<td width="10%"  align="right">仓库：</td>
          <td width="23%"><windrp:warehouse name="warehouseid"/></td>
          <td width="9%" align="right">供应商：</td>
          <td width="25%"><input name="pid" type="hidden" id="pid">
            <input name="providename" type="text" id="providename" size="30" readonly><a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a><span class="style1">*</span></td>
	      <td width="9%" align="right">采购订单号：</td>
	      <td width="24%"><input name="poid" type="text" id="poid" maxlength="32"></td>
	  </tr>
	  <tr>
	    <td  align="right">供应商联系人：</td>
	    <td><input name="plinkman" type="text" id="plinkman" readonly><a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
	    <td align="right">联系电话：</td>
	    <td><input name="tel" type="text" id="tel" maxlength="26"></td>
	    <td align="right">帐期：</td>
	    <td><input name="prompt" type="text" id="prompt" size="10" maxlength="10" onKeyDown="onlyNumber(event)" dataType="Double" msg="帐期只能是数字!" require="false">/天</td>
	    </tr>
	  <tr>
	    <td  align="right">预计入库日期：</td>
	    <td><input name="incomedate" type="text" id="incomedate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" readonly></td>
	    <td align="right">结算方式：</td>
	    <td><windrp:paymentmode name="paymode"/></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr> 
                  <td width="100%"><table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">

                      <td><img src="../images/CN/selectp.gif" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="right"><table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
                <td width="3%" align="left"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="17%">产品编号</td>
                <td width="22%" > 产品名称 </td>
                <td width="16%">规格</td>
                <td width="10%"> 单位</td>
                <!--<td width="15%">批次</td>-->
                <td width="8%"> 单价</td>
                <td width="11%"> 数量</td>
                <td width="13%"> 金额</td>
              </tr>
            </table>
              <table width="100%"   border="0" cellpadding="0" cellspacing="0">
                <tr class="table-back">
                  <td width="3%" align="left"><a href="javascript:deleteR('che','dbtable');"><img src="../images/CN/del.gif"   border="0"></a></td>
                  <td width="11%" align="center">&nbsp;</td>
                  <td width="7%" align="center">&nbsp;</td>
                  <td width="68%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();">：</td>
                  <td width="11%"><input name="totalsum" type="text" id="totalsum" value="0" size="10" maxlength="10" readonly></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="center"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark" 
dataType="Limit" max="256"  msg="备注在256个字之内" require="false"></textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center"><input type="button" name="Submit" onClick="ChkValue();" value="提交">              &nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
