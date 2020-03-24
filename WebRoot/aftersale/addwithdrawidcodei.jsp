<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>	
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script src="../js/prototype.js"></script>
<script src="../js/function.js"></script>
<script src="../js/validator.js"></script>
</head>
<script language="javascript">
var chebox=null;
  function addRow(bit){ 
    var x = document.all("dbtable").insertRow(dbtable.rows.length);

        var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);
		var d=x.insertCell(3);
		var e=x.insertCell(4);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
		d.className = "table-back";
		e.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"warehousebit\" type=\"text\" id=\"warehousebit\" size=\"12\" value='"+bit.warehousebit+"' readonly>";
		c.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" onKeyUp=\"clearNoNum(this)\" dataType=\"Double\" msg=\"数量必须是数字\" maxlength=\"10\" size=\"10\" value='"+bit.quantity+"'>";
		d.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" maxlength=\"32\" size=\"20\" value='"+bit.batch+"' ${isbatch==1?'':'readonly'}>";
		e.innerHTML="<input name=\"producedate\" type=\"text\" id=\"producedate\" maxlength=\"32\" value='"+bit.producedate+"' size=\"15\" ${isbatch==1?'':'readonly'}>";
    chebox=document.all("che");  

}

function Check(){
		if(document.validateProvide.checkall.checked){
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

function checkready(obj,objvalue,obj2,obj2value){
	var productid = document.all.item(obj);
	var batch = document.all.item(obj2);
	if ( productid == null){
		return false;
	}
	if ( productid.length){
		for (h=0; h<productid.length; h++){
			if ( productid[h].value == objvalue && batch[h].value==obj2value){				
				return true;
			}
		}
	}else{
		if ( productid.value == objvalue && batch.value==obj2value){				
			return true;
		}
	}
	return false;
}

function addBit(){
var bit={warehousebit:"",quantity:"",batch:"",producedate:""};
bit.warehousebit=addform.addwarehousebit.value.trim();
bit.quantity=addform.addquantity.value.trim();
bit.batch=addform.addbatch.value.trim();
bit.producedate=addform.addproducedate.value.trim();
if ( !Validator.Validate(document.addform,2) ){
	return;
}
if ( checkready("warehousebit",bit.warehousebit,"batch",bit.batch) ){
return;
}
addRow(bit);
addform.addquantity.value="";
addform.addbatch.value="";
addform.addproducedate.value="";
}

function formcheck(){
if ( !Validator.Validate(document.validateProvide,2) ){
	return;
}
var quantity = document.getElementsByName("quantity");
var totalquantity=validateProvide.totalquantity.value;
var qsum=0;
	if ( quantity == null){
		qsum=0;
	}
	if ( quantity.length){		
		for (h=0; h<quantity.length; h++){
			qsum+=parseFloat(quantity[h].value);			
		}		
	}else{
		qsum=parseFloat(quantity.value);
	}
	if ( qsum != totalquantity){				
		alert("仓位的数量之和必须等于产品数量!");
		return;
	}
	showloading();
	validateProvide.submit();
}
 


</script>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772">分配仓位</td>
      </tr>
    </table>
	<form name="addform" method="post" action="">	
	<c:set ></c:set>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
	
	<tr>
	  	<td width="6%"  align="right">产品名称：</td>
          <td width="11%">${bbd.productname}</td>
          <td width="5%" align="right">数量：</td>
          <td width="8%"> ${bbd.quantity}</td>
	      <td width="7%" align="right">仓库：</td>
	      <td width="19%"><windrp:getname key='warehouse' value='${wid}' p='d'/></td>
		  <td></td>
		  <td></td>
		  <td></td>
	  </tr>
	  <tr><td colspan="8"></tr>
	  <tr>
	  	<td width="6%"  align="right">仓位：</td>
          <td width="11%"><select name="addwarehousebit">
            <logic:iterate id="w" name="bitlist">
              <option value="${w.wbid}">${w.wbid}</option>
            </logic:iterate>
          </select></td>
          <td width="5%" align="right">数量：</td>
          <td width="8%"><input name="addquantity" type="text" id="addquantity" size="10" onKeyUp="clearNoNum(this)" dataType="Double" msg="数量必须是数字" maxlength="10"></td>
	      <td width="7%" align="right">批次：</td>
	      <td width="19%"><input name="addbatch" type="text" id="addbatch" ${isbatch==1?"":"readonly"} maxlength="32"></td>
		  <td width="9%"  align="right">生产日期：</td>
          <td width="17%"><input name="addproducedate" type="text" id="addproducedate" ${isbatch==1?"":"readonly"} maxlength="32"></td>
		  <td width="18%"><input type="button" value="新增" onClick="addBit()" ${isaudit==1?"disabled":""}>&nbsp;<input type="button" name="cancel" value="取消" onClick="window.close();"></td>
	  </tr>
		
	  </table>    
	  </form>
	  <br>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
		  <td width="50" >
		  	<c:choose>
			  <c:when test="${isaudit==0}">
			  	<a href="#" onClick="javascript:formcheck();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a>
			  </c:when>
			  <c:otherwise>
			  	<a href="#" disabled><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a>
			  </c:otherwise>
          </c:choose>
			</td>			
		  <td style="text-align:right">&nbsp;</td>						
		</tr>
	</table>	
	<form name="validateProvide" method="post" action="../aftersale/addWithdrawIdcodeiAction.do">
	
	<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
	<input type="hidden" name="billid" value="${billid}">
	<input type="hidden" name="prid" value="${bbd.productid}">
	<input type="hidden" name="unitid" value="${bbd.unitid}">
	<input type="hidden" name="totalquantity" value="${bbd.quantity}">
              <tr align="center" class="title-top">
                <td width="3%" align="left"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="17%">仓位</td>
                <td width="24%" >数量</td>
                <td width="27%">批次</td>
                <td width="29%">生产日期</td>               
              </tr>
              <c:set var="count" value="2"/>
              <logic:iterate id="p" name="idcodelist" >
                <tr class="table-back">
                  <td align="left"><input type="checkbox" value="${count}" name="che"></td>
                  <td><input name="warehousebit" type="text" value="${p.warehousebit}" id="warehousebit" size="12" readonly></td>
                  <td ><input name="quantity" type="text" value="${p.quantity}" id="quantity" size="10" onKeyUp="clearNoNum(this)" dataType="Double" msg="数量必须是数字" maxlength="10"></td>
                  <td><input name="batch" type="text" id="batch" value="${p.batch}" maxlength="32" size="20" ${isbatch==1?"":"readonly"}></td>
                  <td><input name="producedate" type="text" value="${p.producedate}" maxlength="32" id="producedate" size="15" ${isbatch==1?"":"readonly"}></td>

                </tr>
                <c:set var="count" value="${count+1}"/>
              </logic:iterate>
		
      </table>
      </form>
	  <table width="100%"   border="0" cellpadding="0" cellspacing="0">
		<tr class="table-back">
		  <td width="3%" align="left">
		  <c:choose>
			  <c:when test="${isaudit==0}">
			  	<a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a>
			  </c:when>
			  <c:otherwise>
			  	<img src="../images/CN/del.gif"   border="0">
			  </c:otherwise>
          </c:choose>
		  </td>
		  <td colspan="4">&nbsp;</td>
		</tr>
	  </table>   
       
 </td>
</tr>
</table>

</body>
</html>
