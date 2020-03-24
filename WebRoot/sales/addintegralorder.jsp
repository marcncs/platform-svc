<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDateTime.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/common.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectctitle.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
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
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+p.productid+"' size=\"16\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" value='"+p.productname+"' size=\"45\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" value='"+p.specmode+"' size=\"6\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"4\" readonly value='"+p.countunit+"'>";
		f.innerHTML="<input name=\"warehouseid\" type=\"hidden\" id=\"warehouseid\" value='"+p.warehouseid+"'><input name=\"warehousename\" type=\"text\" id=\"warehousename\" value='"+p.warehousename+"' size=\"10\" readonly>";
        g.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value='"+p.price+"' readonly size=\"8\" maxlength=\"10\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" >";
		h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"10\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\"   onKeyPress=\"KeyPress(this)\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\">";
		i.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"8\" maxlength=\"10\" readonly>";
 	SubTotal();
	TotalSum();
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


function SupperSelectPresent(){
var cid=document.validateProvide.cid.value;
var organid=document.validateProvide.equiporganid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	if(organid==null||organid=="")
	{
		alert("请选择送货机构！");
		return;
	}

	window.open("selectIntegralOrderProductAction.do?cid="+cid+"&organid="+organid,"","height=600,width=800,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");

}



//--------------------------------start -----------------------	
function addItemValue(obj){
	var vproductid = document.validateProvide.productid;
	var vwarehouseid = document.validateProvide.warehouseid;
	if ( vproductid != undefined ){
		if ( vproductid.length ){
			for ( k=0; k<vproductid.length; k++){
				if ( vproductid[k].value == obj.productid && vwarehouseid[k].value == obj.warehouseid){
					var vquantity = parseInt(document.validateProvide.item("quantity")[k].value);
					document.validateProvide.item("quantity")[k].value = vquantity + 1;
					SubTotal();
					TotalSum();
					return;
				}
			}
		}else{
			if ( vproductid.value == obj.productid && vwarehouseid.value == obj.warehouseid){
					var vquantity = parseInt(document.validateProvide.item("quantity").value);
					document.validateProvide.item("quantity").value = vquantity + 1;
					SubTotal();
					TotalSum();
					return;
			}
		}		
	}	
	addRow(obj);
}
//--------------------------------end -----------------------	


function RemoveProduct(){
	checkAll();
	deleteR();
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

function SelectCustomer(){
	var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrollbars=yes;");
	if(c==undefined){return;}
	document.validateProvide.cid.value=c.cid;
	document.validateProvide.cname.value=c.cname;
	document.validateProvide.cmobile.value=c.mobile;
	document.validateProvide.decidemantel.value=c.officetel;
	document.getElementById("cintegral").innerHTML=c.integral;
	//document.validateProvide.tickettitle.value =c.tickettitle;
	//setSelectValue('saledept',c.dept);
	//setSelectValue('saleid',c.saleid);
	setSelectValue('transportmode',c.transportmode);	
	getLinkmanBycid(c.cid);
	getReceivemanBycid(c.cid);
}



function SelectLinkman(){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid==""){
		alert("请选择客户！");
		return;
	}
	var lk=showModalDialog("../common/selectMemberLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	if(lk==undefined){return;}
	document.validateProvide.decideman.value=lk.lname;
	document.validateProvide.tel.value=lk.ltel;
	//document.validateProvide.transportaddr.value=lk.ltransportaddr;	
	//setSelectValue('transit',lk.transit);
}

function SelectReceiveman(){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid==""){
		alert("请选择客户！");
		return;
	}
	var lk=showModalDialog("../common/selectMemberLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	if(lk==undefined){return;}
	document.validateProvide.receiveman.value=lk.lname;
	document.validateProvide.receivemobile.value=lk.mobile;
	document.validateProvide.receivetel.value=lk.ltel;
	document.validateProvide.transportaddr.value=lk.addr;	
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
	
	function getCustomerByWhere(objvalue, temp){
		if (event.keyCode != 13) { 			
			return;
		} 
		var url ="";
		if ( temp == 1 ){
			 url = 'ajaxCustomerAction.do?temp=1&cid='+objvalue;
		}else if ( temp == 2 ){
			 url = 'ajaxCustomerAction.do?temp=2&cname='+objvalue;
		}else if ( temp == 3 ){
			 url = 'ajaxCustomerAction.do?temp=3&mobile='+objvalue;
		}else if ( temp == 4 ){
			 url = 'ajaxCustomerAction.do?temp=4&officetel='+objvalue;
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
		//alert(sizes);	
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
			//alert(c.tickettitle);
			setDefaultValue(c.cid,c.cname,'',c.mobile,c.officetel,c.ratename,c.integral,c.tickettitle);	
			getLinkmanBycid(c.cid);
			getReceivemanBycid(c.cid);
			return;
		}		
		if ( cur == undefined ){
			setDefaultValue('', '', '', '', '','','');	
		}else{
			setDefaultValue(cur.cid, cur.cname, lk.name, cur.mobile, cur.officetel,cur.ratename,cur.integral,cur.tickettitle);
		}
	}
	function setDefaultValue(cid, cname, decideman, mobile, officetel,rate,integral,tickettitle){
		document.validateProvide.cid.value=cid;
		document.validateProvide.cname.value=cname;
		document.validateProvide.decideman.value=decideman;
		document.validateProvide.cmobile.value=mobile;
		document.validateProvide.decidemantel.value=officetel;
		document.validateProvide.receiveman.value=decideman;
		document.validateProvide.receivemobile.value=mobile;
		document.validateProvide.receivetel.value = officetel;
		document.getElementById("rate").innerHTML=rate;
		document.getElementById("cintegral").innerHTML=integral;
		//document.validateProvide.tickettitle.value = tickettitle;
	}
	
	function ChkValue(){
        var cname = document.validateProvide.cname;
		var receiveman = document.validateProvide.receiveman;
		var consignmentdate = document.validateProvide.consignmentdate;
		var organid=document.validateProvide.equiporganid.value;
		var integral=parseFloat(document.getElementById("cintegral").innerHTML);
		var totalsum=document.validateProvide.totalsum.value;
		var productid = document.validateProvide.productid;
		
		if(cname.value==null||cname.value==""){
			alert("客户不能为空!");
			return false;
		}
		
		if(receiveman.value==null||receiveman.value==""){
			alert("收货人不能为空!");
			return false;
		}

		if(consignmentdate.value==null||consignmentdate.value==""){
			alert("交货日期不能为空");
			//totalsum.focus();
			return false;
		}	
		if(organid==null||organid=="")
		{
			alert("请选择送货机构！");
			return;
		}
		if ( integral < totalsum ){
			alert("该会员的积分小于所选产品的总积分！");
			return;
		}
		
		if ( !Validator.Validate(document.validateProvide,2) ){
		return false;
		}
		
		if(productid==undefined){
			alert("请选择产品！");
			return false;
		}

		showloading();
		validateProvide.submit();
	}

function addNewMember(){
	window.open("../sales/toAddMemberAction.do","","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}


function ToAddLinkman(){
	var cid = $F('cid');
	if(cid==null||cid==""){
		alert("请选择客户！");
		return;
	}
	window.open("../sales/toAddLinkManAction.do?cid="+cid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
}

function SelectOrgan(){
var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
if ( p==undefined){return;}
document.validateProvide.equiporganid.value=p.id;
document.validateProvide.ename.value=p.organname;
}

</script>
</head>
<body style="overflow:auto">

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增积分换购单</td>
          <td width="772" valign="bottom">&nbsp;</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="addIntegralOrderAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr> 
            <td >
			
	<fieldset align="center"> <legend>
      <table width="62" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="62">订货人信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right">会员编码：</td>
          <td width="24%"><input name="cid" type="text" id="cid" onKeyUp="getCustomerByWhere(this.value,1)" >
            <span class="STYLE1">*</span></td>
          <td width="10%" align="right">会员名称：</td>
          <td width="22%"><input name="cname" type="text" id="cname" onKeyUp="getCustomerByWhere(this.value,2)" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle" title="查找会员"></a>
          <a href="#" onClick="javascript:addNewMember();"><img src="../images/CN/add.gif" width="19" height="18" border="0" align="absmiddle" title="新增会员"><span class="STYLE1">*</span></a></td>
	      <td width="11%" align="right">订货人：</td>
	      <td width="23%"><input name="decideman" type="text" id="decideman" readonly><a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
	  </tr>
	  <tr>
	    <td  align="right">会员手机：</td>
	    <td><input name="cmobile" type="text" id="cmobile"  maxlength="11" onKeyUp="getCustomerByWhere(this.value,3)">
	      <span class="STYLE1">*</span>(输入手机号回车)</td>
	    <td align="right">会员电话：</td>
	    <td><input name="decidemantel" type="text" id="decidemantel" onKeyUp="getCustomerByWhere(this.value,4)"></td>
	    <td align="right">会员级别：</td>
	    <td><span id="rate"> </span></td>
	  </tr>
	  <tr>
	    <td  align="right">积分：</td>
	    <td><span id="cintegral"> </span></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
</fieldset>



<fieldset align="center"> <legend>
      <table width="63" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="63">收货人信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right">收货人：</td>
          <td width="24%"><input name="receiveman" type="text" id="receiveman" readonly><a href="javascript:SelectReceiveman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
          <a href="javascript:ToAddLinkman();"><img src="../images/CN/add.gif" width="19" height="18" border="0" align="absmiddle" title="新增收货人"><span class="STYLE1">*</span></a></td>
          <td width="10%" align="right">收货人手机：</td>
          <td width="22%"><input name="receivemobile" type="text" id="receivemobile" ></td>
          <td width="11%" align="right">收货人电话：</td>
	      <td width="23%"><input name="receivetel" type="text" id="receivetel"></td>
	  </tr>
	  <tr>
	    <td  align="right">收货地址：</td>
	    <td colspan="5"><input name="transportaddr" type="text" id="transportaddr" size="60" onClick="selectCTitle(this, $F('cid'),'1')">
	    </td>
	    </tr>
	  </table>
	</fieldset>
	
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>其它信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right">交货日期：</td>
          <td width="24%"><input name="consignmentdate" type="text" id="consignmentdate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" size="8" readonly>
            <span class="STYLE1">
            <input name="consignmenttime" type="text" id="consignmenttime" onFocus="javascript:selectTime(this)" value="18:00:00"  size="8" readonly>
            *</span></td>
          <td width="10%" align="right">发运方式：</td>
          <td width="22%"><windrp:select key="TransportMode" name="transportmode" p="n|d"/></td>
          <td width="11%" align="right">客户来源：</td>
	      <td width="23%"><windrp:select key="Source" name="source" p="n|d"/></td>
	  </tr>
	  <tr>
	    <td  align="right">送货机构：</td>
	    <td><input name="equiporganid" type="hidden" id="equiporganid" value="${makeorganid}">
          <input name="ename" type="text" id="ename" value="<windrp:getname key='organ' value='${makeorganid}' p='d'/>" size="30" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a><span class="STYLE1">*</span></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr > 
                  <td width="100%" > <table width="73"  border="0" cellpadding="0" cellspacing="1">
                      <tr align="center" class="back-blue-light2">
                            <td width="73"><img src="../images/CN/selectl.gif" border="0" style="cursor:pointer" onClick="SupperSelectPresent()" ></td>
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
                <td width="14%">产品编号</td>
                <td width="14%" > 产品名称</td>
                <td width="17%">规格型号</td>
                <td width="6%"> 单位</td>
                <td width="14%">出货仓库</td>
				<!--<td width="6%">批次</td>	-->			
				<td width="11%">积分</td>
                <td width="10%"> 数量</td>                
				<td width="12%"> 总积分</td>
                </tr>
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="2%" ><a href="javascript:deleteR('che','dbtable')"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="10%" align="center">&nbsp;</td>
                <td width="18%" align="center">&nbsp;</td>
                <td width="61%" align="right"><input type="button" name="button" value="积分小计" onClick="SubTotal();TotalSum();">：</td>
                <td width="9%" align="center"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10" readonly></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center" ><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr >
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false"></textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span></td>
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
