<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/prototype.js"></script>
<script type="text/javascript" src="../js/validator.js"></script>
<script type="text/javascript" src="../js/showSQ.js"></script>
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
		//var i=x.insertCell(8);
		//var j=x.insertCell(9);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";
		//i.className = "table-back";
		//j.className = "table-back";
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" value='"+p.productid+"' readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"35\" value='"+p.productname+"' readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"35\" value='"+p.specmode+"' readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"8\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" value='"+p.unitidname+"' size=\"8\" readonly>";
		f.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" value='"+p.batch+"' size=\"10\" readonly >";
		g.innerHTML="<a href=\"#\" onMouseOver=\"ShowSQ(this,'"+p.productid+"');\" onMouseOut=\"HiddenSQ();\"><img src=\"../images/CN/stock.gif\" width=\"16\" border=\"0\"></a>";
        //g.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"10\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"8\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\" onKeyUp=\"clearNoNum(this)\">";
        //i.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\" readonly>";

}
	

function SupperSelect(rowx){
	var wid=document.validateProvide.outwarehouseid.value;
	 
	if(wid==null||wid=="")
	{
		alert("请选择调出仓库！");
		return;
	}
	var p=showModalDialog("../common/toSelectWarehouseBatchProductAction.do?wid="+wid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return ;}
	for(var i=0;i<p.length;i++){	
		if ( isready('productid', p[i].productid) && isready('batch', p[i].batch)){
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
		totalsum=totalsum+parseFloat(document.validateProvide.item("subsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.validateProvide.item("subsum")(i).value);
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

	function ChkValue(){
		var movedate = document.validateProvide.movedate;
		var outwarehouseid = document.validateProvide.outwarehouseid;
		var inwarehouseid = document.validateProvide.inwarehouseid;
		var productid = document.validateProvide.productid;
		
		if(movedate.value==null||movedate.value==""){
			alert("请输入转换日期");
			return false;
		}
		if(outwarehouseid.value==null ||outwarehouseid.value==""){
			alert("请选择转出仓库");
			return false;
		}
		if(inwarehouseid.value==null ||inwarehouseid.value==""){
			alert("请选择转入仓库");
			return false;
		}
		if(outwarehouseid.value==inwarehouseid.value){
			alert("请选择不同的仓库");
			//totalsum.focus();
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
</script>
</head>
<body style="overflow:auto">
<form name="validateProvide" method="post" action="addProductInterconvertAction.do">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增产品互转单</td>
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
	  	<td width="9%"  align="right">转换日期：</td>
          <td width="25%"><input name="movedate" type="text" id="movedate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"></td>
          <td width="9%" align="right">转出仓库：</td>
          <td width="25%"><windrp:warehouse name="outwarehouseid"/></td>
	      <td width="8%" align="right">转入仓库：</td>
	      <td width="24%"><select name="inwarehouseid" id="inwarehouseid">
            <logic:iterate id="iw" name="aliw" >
              <option value="${iw.id}" >${iw.warehousename}</option>
            </logic:iterate>
          </select></td>
	  </tr>
	  <tr>
	    <td  align="right">原因：</td>
	    <td><input name="movecause" type="text" id="movecause" value="" size="40"></td>
	    <td align="right">备注：</td>
	    <td><input name="remark" type="text" id="remark" value="" size="40"></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr > 
            <td width="100%" ><table  border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="back-blue-light2">
                 
                <td id="elect" ><img src="../images/CN/selectp.gif" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length);"></td>
                
              </tr>
            </table></td>
          </tr>
        </table> 
      <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="2%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
          <td width="11%">产品编号</td>
          <td width="21%">产品名称 </td>
          <td width="21%">规格</td>
          <td width="7%"> 单位</td>
          <td width="11%">批次</td>
          <td width="11%">库存</td>
          <!--<td width="8%"> 单价</td>-->
          <td width="9%"> 数量</td>
          <!--<td width="10%"> 金额</td>-->
        </tr>
      </table>
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
        <tr align="center" class="table-back">
          <td width="3%" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif" border="0"></a></td>
          <td width="11%">&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="69%" align="right"></td>
          <td width="10%"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center"><input type="button" name="Submit" onClick="ChkValue();" value="提交">            &nbsp;&nbsp;
          <input type="button" name="button" value="取消" onClick="window.close();">          </td>
        </tr>
      </table></td>
  </tr>

</table>
</form>
</body>
</html>
