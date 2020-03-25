<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectunit.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectduw.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">

	//--------------------------------start -----------------------	
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
	
	function setvalue(obj, pm){
		var ri = getRowNo(obj);
 		var p="";
		var pobj= document.listform.pobj;
		if ( !pobj.length ){
			p=eval('(' +pobj.value+ ')'); 			
		}else{
			p=eval('(' +pobj[ri-2].value+ ')'); 			
		}
		if (pm=='peddle'){
			p.price=p.peddleprice;
			p.taxprice=p.peddleprice;
		}
		opener.window.addItemValue(p);
	}
//--------------------------------end -----------------------	
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}
</script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="bodydiv">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择产品</td>
	    
        </tr>
      </table>
        <form name="search" method="post" action="../common/selectSaleOrderProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
      
          <tr class="table-back"> 
		  	<td width="9%" align="right">产品类别：</td>
            <td width="22%">
            <input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">
            <windrp:pstree id="KeyWordLeft" name="productstruts" value="${productstruts}"/>		
			</td>
			<td width="7%" align="right">仓库：</td>
            <td width="20%"><select name="warehouseid" id="warehouseid">
                <logic:iterate id="ps" name="whls" > 
				<option value="${ps.id}" ${ps.id==warehouseid?"selected":""}>
				${ps.warehousename}</option> 
            </logic:iterate> </select></td>
            <td width="12%"  align="right">价格范围：</td>
            <td width="30%" ><input name="BeginPrice" type="text" id="BeginPrice" size="8" value="${BeginPrice}"> -
            <input name="EndPrice" type="text" id="EndPrice" size="8" value="${EndPrice}"></td>
			<td></td>
          </tr>
		  <tr class="table-back"> 
		  	<td width="9%" align="right">关键字：</td>
            <td width="22%"><input type="text" name="KeyWord" value="${KeyWord}"></td>
            <td width="7%" align="right">&nbsp;</td>
            <td width="20%">&nbsp;</td>
            <td width="12%"  align="right">&nbsp;</td>
            <td width="30%" ></td>
			<td class="SeparatePage"><input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
       
      </table>
       </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">				
		  <td class="SeparatePage"><pages:pager action="../common/selectSaleOrderProductAction.do"/></td>
							
		</tr>
	</table>
    </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		 <FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="8%" >产品编号</td>
            <td width="22%">产品名称</td>
            <td width="15%">规格</td>
            <td width="6%">单位</td>
			<td width="12%">仓库</td>
            <td width="8%">数量</td>
			<td width="10%">零售价格</td>
            <td width="10%">会员价格</td>
          </tr>
		  <c:set var="count" value="2"/>
          <logic:iterate id="p" name="sls" > 
		  <tr class="table-back-colorbar" > 
		  	<td >${p.productid}</td>
            <td>${p.psproductname}</td>
            <td>${p.psspecmode}</td>			
            <td><input type="text" name="countunit" size="6" value="${p.countunitname}" onFocus="selectUnit(this,'${p.productid}','${cid}','${organid}','1')"></td>
			<td>${p.warehouseidname}</td>
            <td><span id="spanstockpile${count}">${p.squantity}</span></td>
			<td align="right"><img src="../images/CN/qiang.gif" width="16" height="9" align="absmiddle"/><a href="#" onClick="setvalue(this, 'peddle')"><span id="spanpeddleprice${count}"><windrp:format value="${p.peddleprice}"/></span></a></td>
            <td align="right"><img src="../images/CN/qiang.gif" width="16" height="9"/><a href="#" onClick="setvalue(this, '')"><span id="spanprice${count}"><windrp:format value="${p.price}"/></span></a></td>
           <input type="hidden" name="stockpile" id="stockpile${count}" value="${p.stockpile}">
           <input type="hidden" name="pobj" id="pobj${count}" value="{productid:'${p.productid}',productname:'${p.psproductname}',specmode:'${p.psspecmode}',salesort:'0',unitid:'${p.countunit}',unitidname:'${p.countunitname}',warehouseid:'${p.warehouseid}',warehousename:'${p.warehouseidname}',batch:'${p.batch}',price:'${p.price}',taxprice:'${p.taxprice}',peddleprice:'${p.peddleprice}',discount:'${p.discount}',taxrate:'${p.taxrate}',isidcode:'${p.isidcode}',wise:'0'}">
		  </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
       
      </table>
       </form>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
