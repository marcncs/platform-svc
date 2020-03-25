<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
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
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+p.productid+"'>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"32\" readonly value='"+p.productname+"'>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"32\" readonly value='"+p.specmode+"'>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"6\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly value='"+p.unitidname+"'>";
        f.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value='"+p.cost+"' size=\"10\" onKeyPress=\"KeyPress(this)\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        g.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" onKeyPress=\"KeyPress(this)\" maxlength=\"8\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";		
        h.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" onFocus=\"SubTotal("+dbtable.rows.length+")\" value=\"0.00\" size=\"10\" maxlength=\"10\">";
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


	function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	document.validateProvide.arproductid.value=p.id;
	document.validateProvide.arproductname.value=p.productname;
	document.validateProvide.arspecmode.value=p.specmode;
	document.validateProvide.arunitid.value=p.unitid;
	document.validateProvide.arunitidname.value=p.unitidname;
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
	
	function Chk(){
		var arproductid = document.validateProvide.arproductid;
		var productid = document.validateProvide.productid;
		if(arproductid.value==""||arproductid.value==null){
			alert("组装产品编码不能为空！");
			return false;
		}
		if(productid==undefined){
			alert("请选择产品资料！");
			return false;
		}
		
		if ( !Validator.Validate(document.validateProduct,2) ){
		return false;
		}
		
		showloading();
		return true;
		
	}
	
</script>

</head>

<body style="overflow: auto;">

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改组装关系设置</td>
        </tr>
      </table>
       <form name="validateProvide" method="post" action="../machin/updAssembleRelationAction.do" onSubmit="return Chk();">
       
      <table width="99%" border="0" cellpadding="0" cellspacing="1">
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
          <td width="11%"  align="right"><input name="id" type="hidden" id="id" value="${sof.id}">
            组装产品编码：</td>
	      <td width="26%"><input name="arproductid" type="text" id="arproductid" value="${sof.arproductid}" readonly><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
	      <td width="9%" align="right">组装产品名称：</td>
	      <td width="21%"><input name="arproductname" type="text" id="arproductname" value="${sof.arproductname}" readonly></td>
	      <td width="15%" align="right">规格：</td>
	      <td width="18%"><input name="arspecmode" type="text" id="arspecmode" value="${sof.arspecmode}" readonly></td>
	      </tr>
	    <tr>
          <td align="right">计量单位：</td>
	      <td><input name="arunitid" type="hidden" id="arunitid" value="${sof.arunitid}" readonly>
              <input name="arunitidname" type="text" id="arunitidname" value="${sof.arunitidname}" size="6" readonly></td>
	      <td align="right">数量：</td>
	      <td><input name="arquantity" type="text" id="arquantity" value="${sof.arquantity}" size="4" readonly></td>
	      <td align="right">&nbsp;</td>
	      <td>&nbsp;</td>
	      </tr>
	  </table>
</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                
                <tr> 
                  <td width="100%"><table  border="0" cellpadding="0" cellspacing="1">
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
                <td width="5%">产品编码</td>
                <td width="19%" > 产品名称 </td>
				<td width="20%">规格型号</td>
                <td width="8%"> 单位</td>
                <td width="8%"> 成本单价</td>
                <td width="5%"> 数量</td>				
                <td width="14%"> 金额</td>
                </tr>
			<c:set var="count" value="2"/>
            <logic:iterate id="p" name="als" > 
            <tr class="table-back">
				<td><input type="checkbox" value="${count}" name="che"></td>
                <td><input name="productid" type="text" id="productid" value="${p.productid}"></td>
                <td ><input name="productname" type="text" value="${p.productname}" id="productname" size="32" readonly></td>
				<td><input name="specmode" type="text" value="${p.specmode}" id="specmode" size="32" readonly></td>
                <td><input name="unitid" type="hidden" value="${p.unitid}"><input name="unit" type="text" value="${p.unitidname}" id="unit" size="8" readonly></td>
                <td><input name="unitprice" type="text" value="<fmt:formatNumber value='${p.unitprice}' pattern='0.00'/>" onKeyPress="KeyPress(this)" dataType="Double" msg="单价只能是数字!" require="false" onChange="SubTotal(${count});TotalSum();" id="unitprice" size="10" maxlength="10"></td>
                <td><input name="quantity" type="text" value="${p.quantity}" onChange="SubTotal(${count});TotalSum();" onKeyPress="KeyPress(this)" dataType="Double" msg="数量只能是数字!" require="false" id="quantity" size="8" maxlength="8"></td>
                <td><input name="subsum" type="text" value="<fmt:formatNumber value='${p.subsum}' pattern='0.00'/>" id="subsum" size="10" maxlength="10" readonly></td>
                </tr>
				<c:set var="count" value="${count+1}"/>
			</logic:iterate> 
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="38" ><a href="javascript:deleteR('che','dbtable');"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="137">&nbsp;</td>
                <td width="86">&nbsp;</td>
                <td width="794" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();"></td>
                <td width="186"><input name="totalsum" type="text" id="totalsum" size="10" readonly maxlength="10"></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center" ><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr >
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false">${sof.remark}</textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center" ><input type="Submit" name="Submit" value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
