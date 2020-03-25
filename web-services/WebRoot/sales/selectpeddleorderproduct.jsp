<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
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
	
	function setvalue(obj){
		var ri = getRowNo(obj);
		//alert(ri);
 
		var pl= document.listform.pid;
		if ( !pl.length ){
			var pid=document.listform.item("pid").value;
			var productname=document.listform.item("productname").value; 
			var specmode=document.listform.item("specmode").value; 
			var salesort = document.listform.item("salesort").value;
			var unitid=document.listform.item("unitid").value; 
			var countunit=document.listform.item("countunit").value; 
			var warehouseid = document.listform.item("warehouseid").value; 
			var warehousename = document.listform.item("warehousename").value; 
			var batch = document.listform.item("batch").value;
			var price=document.listform.item("price").value; 
			var taxprice=document.listform.item("taxprice").value;
			var discount = document.listform.item("discount").value; 
			var taxrate = document.listform.item("taxrate").value; 
			var wise = document.listform.item("wise").value; 
			var p ={productid:pid.slice(0),productname:productname.slice(0),specmode:specmode.slice(0),salesort:salesort.slice(0),unitid:unitid.slice(0),countunit:countunit.slice(0),warehouseid:warehouseid.slice(0),warehousename:warehousename.slice(0),batch:batch.slice(0),price:price.slice(0),taxprice:taxprice.slice(0),discount:discount.slice(0),taxrate:taxrate.slice(0),wise:wise.slice(0)};	
			opener.window.addItemValue(p);
		}else{
		var pid=document.listform.item("pid")[ri-2].value;
		var productname=document.listform.item("productname")[ri-2].value; 
		var specmode=document.listform.item("specmode")[ri-2].value; 
		var salesort=document.listform.item("salesort")[ri-2].value;
		var unitid=document.listform.item("unitid")[ri-2].value; 
		var countunit=document.listform.item("countunit")[ri-2].value; 
		var warehouseid = document.listform.item("warehouseid")[ri-2].value; 
		var warehousename = document.listform.item("warehousename")[ri-2].value; 
		var batch = document.listform.item("batch")[ri-2].value;
		var price=document.listform.item("price")[ri-2].value; 
		var taxprice=document.listform.item("taxprice")[ri-2].value;
		var discount = document.listform.item("discount")[ri-2].value; 
		var taxrate = document.listform.item("taxrate")[ri-2].value; 
		var wise = document.listform.item("wise")[ri-2].value;
		var p ={productid:pid.slice(0),productname:productname.slice(0),specmode:specmode.slice(0),salesort:salesort.slice(0),unitid:unitid.slice(0),countunit:countunit.slice(0),warehouseid:warehouseid.slice(0),warehousename:warehousename.slice(0),batch:batch.slice(0),price:price.slice(0),taxprice:taxprice.slice(0),discount:discount.slice(0),taxrate:taxrate.slice(0),wise:wise.slice(0)};	
		opener.window.addItemValue(p);
		}
	}
//--------------------------------end -----------------------	

</script>
</head>

<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择产品</td>
	    
        </tr>
      </table>
       <form name="search" method="post" action="selectPeddleOrderProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
		  	<td width="9%" align="right">产品类别：</td>
            <td width="11%"><select name="KeyWordLeft" id="KeyWordLeft">
                <logic:iterate id="ps" name="uls" > 
				<option value="${ps.structcode}">
				<c:forEach var="i" begin="1" end="${fn:length(ps.structcode)/2}" step="1"><c:out value="--"/></c:forEach>${ps.sortname}</option> 
            </logic:iterate> </select></td>
			<td width="7%" align="right">仓库：</td>
            <td width="14%"><select name="warehouseid" id="warehouseid">
                <logic:iterate id="ps" name="whls" > 
				<option value="${ps.id}">
				${ps.warehousename}</option> 
            </logic:iterate> </select></td>
			<!--<td width="8%"  align="right">品牌：</td>
			<td width="13%">${brandselect}</td> -->
            <td width="10%"  align="right">关键字：</td>
            <td width="28%" ><input type="text" name="KeyWord">
            <input type="submit" name="Submit" value="查询"></td>
            
          </tr>
       
      </table>
       </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="8%" >产品编号</td>
            <td width="22%">产品名称</td>
            <td width="15%">规格</td>
            <td width="6%">单位</td>
			<td width="12%">仓库</td>
            <td width="8%">数量</td>
            <td width="10%">价格</td>
            <td width="8%">订购</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
		  <tr class="table-back" > 
		  	<td ><input type="hidden" name="pid" value="${p.productid}">${p.productid}
		  	  <input type="hidden" name="wise" value="${p.wise}">
		  	  <input type="hidden" name="discount" value='<fmt:formatNumber value="${p.discount}" pattern="0.00"/>'>
		  	  <input type="hidden" name="taxrate" value='<fmt:formatNumber value="${p.taxrate}" pattern="0.0"/>'></td>
            <td><input type="hidden" name="productname" value="${p.psproductname}">
              ${p.psproductname}</td>
            <td><input type="hidden" name="specmode" value="${p.psspecmode}">
              ${p.psspecmode}<input name="salesort" type="hidden" id="salesort" value="0"></td>			
            <td><input type="hidden" name="unitid" value="${p.countunit}">
              <input type="hidden" name="countunit" value="${p.countunitname}">
              ${p.countunitname}</td>
			<td><input name="warehouseid" type="hidden" id="warehouseid" value="${p.warehouseid}">
			  <input type="hidden" name="warehousename" value="${p.warehouseidname}">
			  ${p.warehouseidname}</td>
            <td>&nbsp;</td>
            <td align="right"><input type="hidden" name="batch" value="${p.batch}"><input type="hidden" name="price" value='<fmt:formatNumber value="${p.price}" pattern="0.00"/>'>
              <input type="hidden" name="taxprice" value="${p.taxprice}">
              <fmt:formatNumber value="${p.price}" pattern="0.00"/></td>
            <td><a href="#" onClick="setvalue(this)"><img src="../images/CN/buy.gif" width="44"  border="0"></a></td>
		  </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">&nbsp;</td>
          <td width="70%" align="right"> <presentation:pagination target="/sales/selectPeddleOrderProductAction.do"/></td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
