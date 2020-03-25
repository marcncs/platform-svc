<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
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
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+p.productid+"' size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" value='"+p.productname+"' size=\"35\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" value='"+p.specmode+"' size=\"35\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" value='"+p.unitid+"' size=\"8\"><input name=\"unit\" type=\"text\" id=\"unit\" value='"+p.unitidname+"' size=\"8\" readonly>";
		f.innerHTML="<input name=\"dwarehousebit\" type=\"text\" id=\"dwarehousebit\" value='"+p.warehousebit+"' readonly>";
		g.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" value='"+p.batch+"' readonly>";
        h.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value='"+formatCurrency(p.cost)+"' size=\"10\" maxlength=\"10\" onKeyPress=\"KeyPress(this)\" dataType=\"Double\" msg=\"单价只能是数字!\" require=\"false\">";
        i.innerHTML="<input name=\"reckonquantity\" type=\"text\" id=\"reckonquantity\" value='"+p.quantity+"' size=\"8\" maxlength=\"8\" readonly>";
        j.innerHTML="<input name=\"checkquantity\" type=\"text\" id=\"checkquantity\" value=\"0\" size=\"10\" maxlength=\"8\" onKeyPress=\"KeyPress(this)\" dataType=\"Double\" msg=\"盘点数量只能是数字!\" require=\"false\">";

 	//SubTotal(dbtable.rows);
	//TotalSum();
}
	

function SupperSelect(rowx){
	var wid=document.validateProvide.warehouseid.value;
	var wbit=document.validateProvide.warehousebit.value;
	if(wid==null||wid=="")
	{
		alert("请选择盘点仓库！");
		return;
	}
	var p=showModalDialog("toSelectCheckProductAction.do?wid="+wid+"&wbit="+wbit+"&IsIDCode=${scf.isbar}",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	for(var i=0;i<p.length;i++){	
		if ( isready('productid', p[i].productid)&&isready('warehousebit', p[i].warehousebit) &&isready('batch', p[i].batch)){
			continue;
		}
		addRow(p[i]);				
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
	var pid = document.all("che");
	var checkall = document.all("checkall");
	if (pid.length){
		for(i=0;i<pid.length;i++){
				pid[i].checked=checkall.checked;
		}
	}else{
		pid.checked=checkall.checked;
	}
}

function ChkValue(){
	var productid=document.validateProvide.productid;
	if ( !Validator.Validate(document.validateProvide,2) ){
		return false;
	}
	if ( productid==undefined){
		alert("请选择产品!");
		return false;
	}
	showloading();
	return true;
}
	

</script>
</head>

<body style="overflow:auto">

<form name="validateProvide" method="post" action="updStockCheckAction.do" onSubmit="return ChkValue();">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
<input name="ID" type="hidden" id="ID" value="${scf.id}">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">修改库存盘点单</td>
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
	  	<td width="9%"  align="right">盘点仓库：</td>
          <td width="24%"><input type="hidden" name="warehouseid" value="${scf.warehouseid}">
          <windrp:getname key='warehouse' value='${scf.warehouseid}' p='d'/></td>
          <td width="10%" align="right">仓位：</td>
          <td width="23%"><input type="hidden" name="warehousebit" id="warehousebit" value="${scf.warehousebit}">${scf.warehousebit}</td>
	      <td width="9%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td><input name="memo" type="text" id="memo" size="40" maxlength="120" value="${scf.memo}"></td>
	    <td align="right">&nbsp;</td>
	    <td colspan="3">&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	  
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
          <tr> 
            <td width="100%" ><table  border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="back-blue-light2">
                <td id="elect"><img src="../images/CN/selectp.gif" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
              </tr>
            </table></td>
          </tr>
        </table> 
      <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="3%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
          <td width="9%">产品编号</td>
          <td width="20%" > 产品名称 </td>
          <td width="17%">规格</td>
          <td width="7%"> 单位</td>
          <td width="9%">仓位</td>
          <td width="10%">批次</td>
          <td width="9%"> 单价</td>
          <td width="8%"> 账面数量</td>
          <td width="8%"> 盘点数量 </td>
        </tr>
        <c:set var="count" value="2"/>
        <logic:iterate id="p" name="als" >
          <tr class="table-back">
            <td><input type="checkbox" value="${count}" name="che"></td>
            <td><input name="productid" type="text" value="${p.productid}" id="productid" size="12" readonly></td>
            <td ><input name="productname" type="text" value="${p.productname}" id="productname" size="35" readonly></td>
            <td><input name="specmode" type="text" id="specmode" value="${p.specmode}" size="35" readonly></td>
            <td><input name="unitid" type="hidden" value="${p.unitid}"><input name="unit" type="text" value="<windrp:getname key='CountUnit' value='${p.unitid}' p='d'/>" id="unit" size="8" readonly></td>
             <td><input name="dwarehousebit" type="text" id="dwarehousebit" value="${p.warehousebit}" readonly></td>
            <td><input name="batch" type="text" id="batch" value="${p.batch}" readonly></td>
            <td><input name="unitprice" type="text" value="${p.unitprice}" id="unitprice" size="10" maxlength="10" onKeyPress="KeyPress(this)" dataType="Double" msg="单价只能是数字!" require="false"></td>
            <td><input name="reckonquantity" type="text" value="${p.reckonquantity}" id="reckonquantity" size="8" maxlength="8" readonly></td>
            <td><input name="checkquantity" type="text" value="${p.checkquantity}" id="checkquantity" size="10" maxlength="10" onKeyPress="KeyPress(this)" dataType="Double" msg="盘点数量只能是数字!" require="false"></td>
          </tr>
          <c:set var="count" value="${count+1}"/>
        </logic:iterate>
      </table>
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
        <tr align="center" class="table-back">
          <td width="2%" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
          <td width="11%">&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="64%" align="right">&nbsp;</td>
          <td width="15%">&nbsp;</td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center"><input type="submit" name="Submit"  value="提交">&nbsp;&nbsp;
          <input type="button" name="button" value="取消" onClick="window.close();"></td>
        </tr>
      </table></td>
  </tr>

</table>
</form>
</body>
</html>
