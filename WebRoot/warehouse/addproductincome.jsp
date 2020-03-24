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
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" value='"+p.productid+"' readonly>";
        c.innerHTML="<input name=\"nccode\" type=\"text\" id=\"nccode\" size=\"12\" value='"+p.nccode+"' readonly>";
		d.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"35\" value='"+p.productname+"' readonly>";
		e.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"35\" value='"+p.specmode+"' readonly>";
		f.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" >";
        g.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"8\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\"  value='"+p.unitidname+"' readonly>";
        h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"8\" onblur=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\" onKeyPress=\"KeyPress(this)\">";
		i.innerHTML="<input name=\"costprice\" type=\"text\" id=\"costprice\" value=\"0\" size=\"8\" maxlength=\"8\" onblur=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" dataType=\"Double\" msg=\"金额只能是数字!\" require=\"false\" onKeyPress=\"KeyPress(this)\">";
		j.innerHTML="<input name=\"costsum\" type=\"text\" id=\"costsum\" value=\"0\" size=\"8\" maxlength=\"8\" readonly>";

 	SubTotal();
	TotalSum();
}

  function copeRow(v_chebox,v_dbtable){
	  var flag = true;
	  chebox=document.all(v_chebox);
		if(chebox!=null){	
			if (chebox.length >=1){
				  var productid = document.all.item("productid");
				  var nccode = document.all.item("nccode");
				  var productname = document.all.item("productname");
				  var specmode = document.all.item("specmode");
				  var unitid = document.all.item("unitid");
				  var unit = document.all.item("unit");
				for(var i=0;i<chebox.length;i++){
				  if (chebox[i].checked) {
					  flag = false;
					  if(chebox.length==1){
							  var pval =document.getElementById("productid").value;
							  var nval = document.getElementById("nccode").value;
							  var pnval = document.getElementById("productname").value;
							  var sval = document.getElementById("specmode").value;
							  var uval = document.getElementById("unitid").value;
							  var unval = document.getElementById("unit").value;
						  }else{
							  var pval =productid[i].value;
							  var nval = nccode[i].value;
							  var pnval = productname[i].value;
							  var sval = specmode[i].value;
							  var uval = unitid[i].value;
							  var unval = unit[i].value;
						  }
					  
 
					  InsertRowFun(pval,nval,pnval,sval,uval,unval);
				  }
				}
				if(flag){
					alert("请选择要复制的产品");
			}
	 	 }else{
				if (chebox.checked ){
					  var pval =document.getElementById("productid").value;
					  var nval = document.getElementById("nccode").value;
					  var pnval = document.getElementById("productname").value;
					  var sval = document.getElementById("specmode").value;
					  var uval = document.getElementById("unitid").value;
					  var unval = document.getElementById("unit").value;

					  InsertRowFun(pval,nval,pnval,sval,uval,unval);
				}else{
					alert("请选择要复制的产品");
				}
			}
	}
  }

  function InsertRowFun(pval,nval,pnval,sval,uval,unval){
	  var x = document.all("dbtable").insertRow(dbtable.rows.length);
	  
       var a=x.insertCell(0);
       var b=x.insertCell(1);
       var c=x.insertCell(2);
        var d=x.insertCell(3);
       var e=x.insertCell(4);
		var f=x.insertCell(5);
		var g=x.insertCell(6);
		var h=x.insertCell(7);
		var iC=x.insertCell(8);
		var j=x.insertCell(9);
      
      a.className = "table-back";
      b.className = "table-back";
      c.className = "table-back";
      d.className = "table-back";
      e.className = "table-back";
      f.className = "table-back";
	  g.className = "table-back";
	  h.className = "table-back";
	  iC.className = "table-back";
	  j.className = "table-back";
		
      a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
      b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" value='"+pval+"' readonly>";
      c.innerHTML="<input name=\"nccode\" type=\"text\" id=\"nccode\" size=\"12\" value='"+nval+"' readonly>";
		d.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"35\" value='"+pnval+"' readonly>";
		e.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"35\" value='"+sval+"' readonly>";
		f.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" >";
      g.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"8\" value='"+uval+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\"  value='"+unval+"' readonly>";
      h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"8\" onblur=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\" onKeyPress=\"KeyPress(this)\">";
      iC.innerHTML="<input name=\"costprice\" type=\"text\" id=\"costprice\" value=\"0\" size=\"8\" maxlength=\"8\" onblur=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" dataType=\"Double\" msg=\"金额只能是数字!\" require=\"false\" onKeyPress=\"KeyPress(this)\">";
	   j.innerHTML="<input name=\"costsum\" type=\"text\" id=\"costsum\" value=\"0\" size=\"8\" maxlength=\"8\" readonly>";

	SubTotal();
	TotalSum();  
  }

function SupperSelect(rowx){
	var p = showModalDialog("../common/toSelectOrganProductAction.do",null,"dialogWidth:21cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
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
	var unitprice=document.validateProvide.costprice;
	var quantity=document.validateProvide.quantity;
	var objsubsum=document.validateProvide.costsum;
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
	var objsubsum=document.validateProvide.costsum;
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
		var incomedate = document.validateProvide.incomedate;
		var productid=document.validateProvide.productid;
		var batchs = document.all.item("batch");

		if(warehouseid.value==null||warehouseid.value==""){
			alert("仓库不能为空");
			//totalsum.focus();
			return false;
		}

		if(incomedate.value==null||incomedate.value==""){
			alert("入库日期不能为空");
			//totalsum.focus();
			return false;
		}
		if ( productid==undefined){
			alert("请选择产品！");
			return false;
		}

		if(batchs.length>=1){
			for(var i = 0; i<batchs.length ; i++){
				if(batchs[i].value==null||batchs[i].value==""){
					alert("请为每种产品分配批次！");
					return false;
				 }else if(batchs[i].value.length<12){
					 alert("批次必须为12位格式");
					 return false;
				 }
		  }
	    }else if(document.getElementById("batch").value==null||document.getElementById("batch").value==""){
		    	alert("请为产品分配批次！");
				return false;
	    }else if(document.getElementById("batch").value.length<12){
	    	alert("批次必须为12位格式");
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增产成品入库单</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="../warehouse/addProductIncomeAction.do">
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
	  	<td width="9%"  align="right">入货仓库：</td>
          <td width="21%"><windrp:warehouse name="warehouseid" wHType="3"/></td>
          <td width="13%" align="right">手工单号：</td>
          <td width="23%"><input name="handwordcode" type="text" id="handwordcode"></td>
	      <td width="9%" align="right">入库日期：</td>
	      <td width="25%"><input name="incomedate" type="text" id="incomedate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"></td>
	  </tr>
	  <tr>
	    <td  align="right">入库类别：</td>
	    <td><windrp:select key="ProductIncomeSort" name="productincomesort" p="n|f"/></td>
	    <!--  
	    <td align="right">批次：</td>
	    <td><input name="batch" type="text" id="batch"></td>
	    -->
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr> 
                  <td width="80%"><table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                      <td id="elect"><img src="../images/CN/selectp.gif" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                      <td width="20%">&nbsp;</td>
                      <td width="80%">&nbsp;<span class="STYLE1">注：批次格式如 20120101H001</span></td>
                      </tr>
                  </table>
                  </td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="right"><table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
                <td width="2%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="9%">产品编号</td>
                <td width="9%">产品内部编号</td>
                <td width="21%" > 产品名称 </td>
                <td width="15%">规格</td>
                <td width="15%">批次</td>
                <td width="5%"> 单位</td>
                <td width="10%"> 数量</td>
                <td width="8%">成本单价</td>
                <td width="8%">成本金额</td>
              </tr>
            </table>
              <table width="100%"   border="0" cellpadding="0" cellspacing="0">
                <tr align="center" class="table-back">
                  <td width="2%" ><a href="javascript:deleteR('che','dbtable');"><img src="../images/CN/del.gif"   border="0"></a></td>
                  <td width="2%"><a href="javascript:copeRow('che','dbtable');"><img src="../images/CN/copygif.gif"   border="0"></a></td>
                  <td width="19%">&nbsp;</td>
                  <td width="64%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();">
                    ：</td>
                  <td width="15%"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10" readonly></td>
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
