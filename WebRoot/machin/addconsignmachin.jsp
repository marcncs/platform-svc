<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/Currency.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
 var iteration=0;
 var i=1;
 var chebox=null;
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

		var quantity = p.quantity;
		if(quantity==undefined){
			quantity=1;
		}
		
		var specmode = p.specmode;
		if(specmode==undefined){
			specmode="";
		}

        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" value='"+p.productid+"'>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"32\" value='"+p.productname+"' readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"32\" value='"+specmode+"' readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"6\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly value='"+p.unitidname+"'>";
        f.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value='0.00' size=\"10\" maxlength=\"10\" dataType=\"Double\" msg=\"单价只能是数字!\" require=\"false\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        g.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" size=\"8\" value='"+quantity+"' maxlength=\"8\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";		
        h.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" onFocus=\"SubTotal("+dbtable.rows.length+")\" value=\"0.00\" size=\"10\" readonly maxlength=\"10\">";
		i++;
    chebox=document.all("che");  //計算total值

 	SubTotal(dbtable.rows);
	TotalSum();
}
	

function SupperSelect(rowx){
	var p=showModalDialog("../common/toSelectAllProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	for(var i=0;i<p.length;i++){			
		if ( isready('productid', p[i].productid) ){
			continue;
		}
		addRow(p[i]);		
	}
}


	function DelAll(){
		checkAll("che");
		deleteR();
	}
	
	function deleteR(){
	chebox=document.all("che");
	if(chebox!=null){	
		if (chebox.length >1){
			for(var i=1;i<=chebox.length;i++){
			  if (chebox[i-1].checked) {
				document.all('dbtable').deleteRow ( i);
				i=i-1;
			  }
			}
		}else{
			document.all('dbtable').deleteRow(1)
		}
 	 }
}

function Check(){
	if(document.all("checkall").checked){
		checkAll("che");
	}else{
		uncheckAll("che");
	}
}

function checkAll(obj){
	var pid = document.all(obj);
	if ( pid == null ){
		return;
	}
	if(pid.length){
		for(i=0;i<pid.length;i++){
			pid[i].checked=true;
		}
	}else{
		pid.checked=true;
	}		
}

function uncheckAll(obj){
	var pid = document.all(obj);
	if ( pid == null ){
		return;
	}
	if(pid.length){
		for(i=0;i<pid.length;i++){
			pid[i].checked=false;
		}
	}else{
		pid.checked=false;
	}		
}


function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	//alert("dbtable.rows.length===="+dbtable.rows.length+"    dbtable.rows.==="+dbtable.rows);
	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value);
		document.validateProvide.item("subsum").value=formatCurrency(sum);
	}else{
		for(var m=0;m<rowslength;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			document.validateProvide.item("subsum")(m).value=formatCurrency(sum);	
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
	document.validateProvide.totalsum.value=formatCurrency(totalsum);
}

function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.validateProvide.pid.value=p.pid;
	document.validateProvide.providename.value=p.pname;
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
		document.validateProvide.plinkman.value=lk.name;
		document.validateProvide.tel.value=lk.mobile;
	}
}

function SelectLinkman(){
	var pid=document.validateProvide.pid.value;
	if(pid==null||pid=="")
	{
		alert("请选择供应商！");
		return;
	}
	var lk=showModalDialog("../common/selectPlinkmanAction.do?pid="+pid,null,"dialogWidth:21cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	document.validateProvide.plinkman.value=lk.lname;
	document.validateProvide.tel.value=lk.lmobile;
}


	function SelectAssembleProduct(){
	
	var p  = showModalDialog("../machin/toSelectAssembleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
		if (p==undefined){return;}
	document.validateProvide.id.value=p.id;
	document.validateProvide.cproductid.value=p.productid;
	document.validateProvide.cproductname.value=p.productname;
	
	document.validateProvide.cspecmode.value=p.specmode;
	document.validateProvide.cunitid.value=p.unitid;
	document.validateProvide.cunitidname.value=p.unitidname;
	DelAll();
	document.validateProvide.cquantity.value=1;
	document.validateProvide.oquantity.value=1;
	addAjaxProduct(p.id);
	}
	
	function addAjaxProduct(objid){
	
        var url = '../machin/ajaxGetAssembleProductAction.do?id='+objid;
        var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showexrate}
                    );	
    }

   function showexrate(originalRequest)
    {
		var data = eval('(' + originalRequest.responseText + ')');	
		var cr = data.dlist;
			for(var n=0; n<cr.length; n++){
				addRow(cr[n]);
			}
			//SubTotal(1);
			//TotalSum();
	}

	function ChangeQuantity(){
		var q = document.validateProvide.cquantity.value;
		var oq = document.validateProvide.oquantity.value;
		var qu = document.validateProvide.quantity;
		if ( qu.length ){
			for (var j=0; j<qu.length; j++){
				qu[j].value = q /oq * qu[j].value ;
			}
		}else{
			qu.value = q /oq * qu.value ;
		}
		document.validateProvide.oquantity.value = q;
	}
	
	function ComputeCtotal(){
		var q = document.validateProvide.cquantity.value;
		var cp = document.validateProvide.cunitprice.value;
		var ctotal = document.validateProvide.ctotalsum;
		ctotal.value =formatCurrency(q * cp);
	}
	
	function ChkValue(){
		var pid = document.validateProvide.pid;
		var cproductid = document.validateProvide.cproductid;

		if(pid.value==null||pid.value==""){
			alert("供应商不能为空");
			//totalsum.focus();
			return false;
		}

		if(cproductid.value==null||cproductid.value==""){
			alert("加工产品不能为空！");
			return false;
		}
		
		if ( !Validator.Validate(document.validateProduct,2) ){
		return false;
		}
	
		showloading();
		return true;
		
		//validateProvide.submit();
	}

</script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>

</head>

<body style="overflow: auto;">

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">新增委外加工单</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="../machin/addConsignMachinAction.do" onSubmit="return ChkValue();">
        
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
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
	    <td  align="right">供应商：</td>
	    <td><input name="pid" type="hidden" id="pid">
          <input name="providename" type="text" id="providename" size="35" readonly><a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
	    <td align="right">联系人：</td>
	    <td><input name="plinkman" type="text" id="plinkman" readonly><a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
	    <td align="right">联系电话：</td>
	    <td><input name="tel" type="text" id="tel"></td>
	    </tr>
	  <tr>
	    <td width="9%"  align="right">加工产品编码：</td>
	    <td width="23%">
		<input name="cproductid" type="text" id="cproductid" readonly><a 
		href="javascript:SelectAssembleProduct();"><img src="../images/CN/find.gif" 
		width="18" height="18" border="0" align="absmiddle"></a></td>
	    <td width="13%" align="right">加工产品名称：</td>
	    <td width="20%"><input name="cproductname" type="text" id="cproductname" readonly></td>
	    <td width="15%" align="right">规格：</td>
	    <td width="20%"><input name="cspecmode" type="text" id="cspecmode" readonly></td>
	  </tr>
	  <tr>
	    <td height="19" align="right">计量单位：</td>
	    <td><input name="cunitid" type="hidden" id="cunitid">
	      <input name="cunitidname" type="text" id="cunitidname" size="6" readonly></td>
	    <td align="right">数量：</td>
	    <td><input name="cquantity" type="text" id="cquantity" value="1" size="4" dataType="Double" msg="数量只能是数字!" require="false" onChange="ChangeQuantity();ComputeCtotal();">
	      <input name="oquantity" type="hidden" id="oquantity" value="1"></td>
	    <td align="right">加工费单价：</td>
	    <td><input name="cunitprice" type="text" id="cunitprice" onChange="ComputeCtotal();" value="0" size="12" dataType="Double" msg="加工费单价只能是数字!" require="false"></td>
	  </tr>
	  <tr>
	    <td height="19" align="right">加工费总金额：</td>
	    <td><input name="ctotalsum" type="text" id="ctotalsum" value="0.00" size="12" dataType="Double" msg="加工费总金额只能是数字!" require="false" readonly></td>
	    <td align="right">结算方式：</td>
	    <td>${paymodename}</td>
	    <td align="right">预计完工日期：</td>
	    <td><input name="completeintenddate" type="text" id="completeintenddate" onFocus="javascript:selectDate(this)" readonly value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" size="12"></td>
	    </tr>
	  </table>
</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr > 
                  <td width="100%" > <table  border="0" cellpadding="0" cellspacing="1">
                      <tr align="center" class="back-blue-light2">
                         
                            <td id="elect" ><img src="../images/CN/selectp.gif" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                            
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
                <td width="8%">产品编码</td>
                <td width="19%">产品名称</td>
                <td width="12%">规格型号</td>
                <td width="10%">单位</td>
                <td width="8%">单价</td>
                <td width="8%"> 数量</td>				
                <td width="9%"> 金额</td>
                </tr>
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="20" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="11%" align="center">&nbsp;</td>
                <td width="7%" align="center">&nbsp;</td>
                <td width="69%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();" ></td>
                <td width="10%" align="center"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10" readonly> </td>
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
            <td  align="center"><input type="Submit" name="Submit" value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
