<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectunit.js"> </SCRIPT>
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
 
		var pl= document.listform.pid;
		if ( !pl.length ){
			var pid=document.listform.item("pid").value;
			var productname=document.listform.item("productname").value; 
			var specmode=document.listform.item("specmode").value;
			var unitid=document.listform.item("unitid").value; 
			var countunit=document.listform.item("countunit").value; 
			//var price=document.listform.item("price").value; 
			
			var p ={productid:pid.slice(0),productname:productname.slice(0),specmode:specmode.slice(0),unitid:unitid.slice(0),countunit:countunit.slice(0)};	
			opener.window.addItemValue(p);
		}else{
		var pid=document.listform.item("pid")[ri-2].value;
		var productname=document.listform.item("productname")[ri-2].value; 
		var specmode=document.listform.item("specmode")[ri-2].value;
		var unitid=document.listform.item("unitid")[ri-2].value; 
		var countunit=document.listform.item("countunit")[ri-2].value; 
		//var price=document.listform.item("price")[ri-2].value; 

		var p ={productid:pid.slice(0),productname:productname.slice(0),specmode:specmode.slice(0),unitid:unitid.slice(0),countunit:countunit.slice(0)};	

		opener.window.addItemValue(p);
		}
	}
//--------------------------------end -----------------------	

</script>
</head>

<body style="overflow:scroll">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择产品</td>
	    
        </tr>
      </table>
      <form name="search" method="post" action="selectLockProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
		  	<td width="12%" align="right">产品类别：</td>
            <td width="29%"><input type="hidden" name="KeyWordLeft" id="KeyWordLeft">			
			<windrp:pstree id="KeyWordLeft" name="productstruts"/></td>
			<td width="7%" align="right">关键字：</td>
            <td width="52%"><input type="text" name="KeyWord">
            <input type="submit" name="Submit" value="查询"></td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td style="text-align: right;"><pages:pager action="../purchase/selectLockProductAction.do"/></td>
        </tr>
      </table>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        
          <tr align="center" class="title-top"> 
            <td width="8%" >产品编号</td>
            <td width="22%">产品名称</td>
            <td width="15%">规格</td>
            <td width="6%">单位</td>
			<td width="8%">订购</td>
          </tr>
		  <c:set var="count" value="2"/>
          <logic:iterate id="p" name="sls" > 
		  <tr class="table-back-colorbar" > 
		  	<td ><input type="hidden" name="pid" value="${p.id}">${p.id}		  	  </td>
            <td><input type="hidden" name="productname" value="${p.productname}">
              ${p.productname}</td>
            <td><input type="hidden" name="specmode" value="${p.specmode}">
              ${p.specmode}              </td>			
            <td>
			  <input type="hidden" name="unitid" id="unitid${count}"  value="${p.countunit}">
                   <input type="text" name="countunit" size="6" value="${p.countunitname}" onFocus="selectUnit(this,'${p.id}','')">
			  </td>
			<td><a href="#" onClick="setvalue(this)"><img src="../images/CN/buy.gif" width="44"  border="0"></a></td>
		  </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
