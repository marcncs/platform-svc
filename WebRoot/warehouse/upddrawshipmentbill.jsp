<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/showSQ.js"> </SCRIPT>
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
		//var h=x.insertCell(7);
		//var i=x.insertCell(8);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
		g.className = "table-back";
		//h.className = "table-back";
		//i.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" value='"+p.productid+"' readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"35\" value='"+p.productname+"' readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"35\" value='"+p.specmode+"' readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"8\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" value='"+p.unitidname+"' readonly>";
		f.innerHTML="<a href=\"#\" onMouseOver=\"ShowSQ(this,'"+p.productid+"');\" ><img src=\"../images/CN/stock.gif\" width=\"16\" height=\"16\" border=\"0\"></a>";
       // g.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"10\" maxlength=\"10\" onchange=\"SubTotal();\" onFocus=\"SubTotal();\" dataType=\"Double\" msg=\"单价只能是数字!\" require=\"false\">";
        g.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"8\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\" onKeyUp=\"clearNoNum(this)\">";
        //i.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\" readonly>";

 	//SubTotal();
	//TotalSum();
}
	

function SupperSelect(rowx){
var wid=document.validateProvide.warehouseid.value;
	if(wid==null||wid=="")
	{
		alert("请选择仓库！");
		return;
	}

	var p=showModalDialog("../common/toSelectWarehouseProductAction.do?wid="+wid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
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
		for(i=0;i<pid.length;i++){
				pid[i].checked=checkall.checked;
		}		
	}

	function ChkValue(){
        var drawdate = document.validateProvide.drawdate;
		var warehouseid = document.validateProvide.warehouseid;
		var productid = document.validateProvide.productid;
		if(warehouseid.value==null||warehouseid.value==""){
			alert("仓库不能为空");
			return false;
		}
		if(drawdate.value==null||drawdate.value==""){
			alert("领用日期不能为空!");
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
		validateProvide.action = "../warehouse/updDrawShipmentBillAction.do?id=" + document.validateProvide.id.value;
		 
		validateProvide.submit();
	}
  function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.validateProvide.organid.value=p.id;
			document.validateProvide.orgname.value=p.organname;
			//document.referForm.transportaddr.value=p.oaddr;
			document.validateProvide.owname.value="";
		//	getOrganLinkmanBycid(p.id);
			
	}

</script>

</head>
<body>


<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改领用出库单</td>
        </tr>
      </table>
              <form name="validateProvide" method="post" action="">
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
	  <td width="9%" align="right">出库机构：</td>
	      <td width="25%">
	    	  <input name="id" type="hidden" id="id" value="${osbf.id }">
			<input name="organid" type="hidden" id="organid" value="${osbf.organId }">
		  	<input name="orgname" type="text" id="orgname" value="${osbf.organName }" size="30" dataType="Require" msg="必须录入调出机构!" readonly><a href="javascript:SelectOrgan2();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
			<span class="style1">*</span>
		  </td>
	  	  <td width="9%"  align="right">出货仓库：</td>
          <td width="25%">
				<input type="hidden" name="warehouseid" id="warehouseid" value="${osbf.warehouseid }">
			<input type="text" name="owname" id="owname" value="${osbf.warehouseidname }" onClick="selectDUW(this,'warehouseid',$F('organid'),'w','')" value="" readonly/>	
			<span class="STYLE1">*</span>
			</td>
          <td width="9%" align="right">领用日期：</td>
          <td width="23%"><input name="drawdate" type="text" id="drawdate" onFocus="javascript:selectDate(this)"  value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" readonly></td>
          
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
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<td width="3%" align="left"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="28%">产品编号</td>
                <td width="28%" > 产品名称 </td>
				<td width="15%">规格型号</td>
                <td width="12%"> 单位</td>
                <td width="16%">相关</td>
                <td width="7%"> 数量</td>
                </tr>
			<c:set var="count" value="2"/>
            <logic:iterate id="p" name="als" > 
            <tr class="table-back">
				<td><input type="checkbox" value="${count}" name="che"></td>
                <td><input name="productid" type="text" value="${p.productid}" id="productid" size="12" readonly></td>
                <td ><input name="productname" type="text" value="${p.productname}" id="productname" size="35" readonly></td>
				<td><input name="specmode" type="text" value="${p.specmode}" id="specmode" size="35" readonly></td>
                <td><input name="unitid" type="hidden" value="${p.unitid}"><input name="unit" type="text" value="${p.unitidname}" id="unit" size="8" readonly></td>
                <td><a href="#" onMouseOver="ShowSQ(this,'${p.productid}');" onMouseOut="HiddenSQ();"><img src="../images/CN/stock.gif" width="16"  border="0"></a></td>
                
                <td><input name="quantity" type="text" value="${p.quantity}" id="quantity" size="8" maxlength="8" dataType="Double" msg="数量只能是数字!" require="false" onKeyUp="clearNoNum(this)"></td>
               
                </tr>
				<c:set var="count" value="${count+1}"/>
			</logic:iterate> 
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="3%" align="left"><a href="javascript:deleteR('che','dbtable');"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="11%">&nbsp;</td>
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
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false">${osbf.remark}</textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span></td>
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
