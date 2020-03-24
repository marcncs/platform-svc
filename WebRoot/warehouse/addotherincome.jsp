<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>新增盘盈单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="javascript" src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
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

        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";
		i.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+p.productid+"' size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" value='"+p.productname+"' size=\"35\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" value='"+p.specmode+"' size=\"35\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" value='"+p.unitid+"' size=\"8\"><input name=\"unit\" type=\"text\" id=\"unit\" value='"+p.unitidname+"' size=\"8\" readonly>";
		f.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" size=\"12\" maxlength=\"32\">";
        g.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"10\" maxlength=\"10\" onchange=\"SubTotal();\" onFocus=\"SubTotal();\" dataType=\"Double\" msg=\"单价只能是数字!\" require=\"false\" onKeyPress=\"KeyPress(this)\">";
        h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"8\" onchange=\"SubTotal();\" onFocus=\"SubTotal();\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\" onKeyPress=\"KeyPress(this)\">";
        i.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\">";
 	SubTotal();
}


  function copeRow(v_chebox,v_dbtable){
	  var flag = true;
	  chebox=document.all(v_chebox);
		if(chebox!=null){	
			if (chebox.length >=1){
				  var productid = document.all.item("productid");
				  var productname = document.all.item("productname");
				  var specmode = document.all.item("specmode");
				  var unitid = document.all.item("unitid");
				  var unit = document.all.item("unit");
				for(var i=0;i<chebox.length;i++){
				  if (chebox[i].checked) {
					  flag = false;
						if(chebox.length==1){

							  var pval =document.getElementById("productid").value;
							  var pnval = document.getElementById("productname").value;
							  var sval = document.getElementById("specmode").value;
							  var uval = document.getElementById("unitid").value;
							  var unval = document.getElementById("unit").value;
						  }else{
							  var pval =productid[i].value;
							  var pnval = productname[i].value;
							  var sval = specmode[i].value;
							  var uval = unitid[i].value;
							  var unval = unit[i].value;
						  }
					  
 
					  InsertRowFun(pval,pnval,sval,uval,unval);
				  }
				}
				if(flag){
					alert("请选择要复制的产品");
				}
	 		 }else{
				if (chebox.checked ){
					  var pval =document.getElementById("productid").value;
					  var pnval = document.getElementById("productname").value;
					  var sval = document.getElementById("specmode").value;
					  var uval = document.getElementById("unitid").value;
					  var unval = document.getElementById("unit").value;

					  InsertRowFun(pval,pnval,sval,uval,unval);
				}else{
					alert("请选择要复制的产品");
				}
			}
		}else{
			alert("请选择要复制的产品");
		}
  }

  function InsertRowFun(productid,productname,specmode,unitid,unitname){

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

       a.className = "table-back";
       b.className = "table-back";
       c.className = "table-back";
       d.className = "table-back";
       e.className = "table-back";
       f.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";	
		i.className = "table-back";	

			
       a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
       b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+productid+"' size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" value='"+productname+"' size=\"35\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" value='"+specmode+"' size=\"35\" readonly>";
       e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" value='"+unitid+"' size=\"8\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" value='"+unitname+"' readonly>";
		f.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" value='' size=\"12\" maxlength=\"32\">";
        g.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"10\" maxlength=\"10\" onchange=\"SubTotal();\" onFocus=\"SubTotal();\" dataType=\"Double\" msg=\"单价只能是数字!\" require=\"false\" onKeyPress=\"KeyPress(this)\">";
        h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"8\"  onchange=\"SubTotal();\" onFocus=\"SubTotal();\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\" onKeyPress=\"KeyPress(this)\">";
       i.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\">";
    	SubTotal();
  }
  
	
function SupperSelect(){
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
	var productid = document.validateProvide.productid;
	if(warehouseid.value==null||warehouseid.value==""){
		alert("仓库不能为空");
		return false;
	}	
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
function SelectOrgan(){
	var from = document.validateProvide;
	var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	from.organid.value=p.id;
	from.orgname.value=p.organname;
	from.owname.value="";
		
}	
</script>
</head>
<body style="overflow:auto">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增盘盈单</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="../warehouse/addOtherIncomeAction.do" onSubmit="return ChkValue();">
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
	     <td width="10%" align="right">机构：</td>
         <td>
	      <input name="organid" type="hidden" id="organid">
		  <input name="orgname" type="text" id="orgname" size="30" dataType="Require" msg="必须录入机构!" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
		<span class="style1">*</span>
	    </td>
   	      <td width="10%" align="right">仓库：</td>
		<td width="24%">
			<input type="hidden" name="warehouseid" id="warehouseid">
			<input type="text" name="owname" id="owname" onClick="selectDUW(this,'warehouseid',$F('organid'),'rw','')" value="" readonly>	
			<span class="STYLE1">*</span>
		</td>		  
          <td width="8%" align="right">入库类别：</td>
          <td width="23%"><windrp:select key="IncomeSort" name="incomesort" p="n|f"/></td>
	  </tr>	
	  <tr>
	      <td width="9%" align="right">相关单据号：</td>
	      <td width="25%"><input name="billno" type="text" id="billno"></td>
	    <td align="right"><input type="checkbox" name="isaccount"/>&nbsp;</td>
	    <td>是否记账</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr> 
                  <td width="100%"><table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                        <td id="elect"><img src="../images/CN/selectp.gif"  border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="right"><table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
                <td width="2%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="11%">产品编号</td>
                <td width="20%" > 产品名称</td>
                <td width="17%">规格型号</td>
                <td width="10%">单位</td>
                <td width="10%">批次</td>
                <td width="10%">单价</td>
                <td width="10%">数量</td>
                <td width="10%">金额</td>
              </tr>
            </table>
              <table width="100%"   border="0" cellpadding="0" cellspacing="0">
                <tr align="center" class="table-back">
                  <td width="2%" ><a href="javascript:deleteR('che','dbtable');"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="2%"><a href="javascript:copeRow('che','dbtable');"><img src="../images/CN/copygif.gif"   border="0"></a></td>
                  <td width="12%">&nbsp;</td>
                  <td width="7%">&nbsp;</td>
                  <td width="64%" align="right">&nbsp;</td>
                  <td width="15%">&nbsp;</td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="center"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false"></textarea></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center"><input type="submit" name="Submit" value="提交">              &nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
