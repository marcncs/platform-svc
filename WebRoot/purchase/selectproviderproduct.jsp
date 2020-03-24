<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	function Affirm(){
		
		
		var pid = document.listform.pid;
		for(var i = 0 ;  i < pid.length ; i++){
		
			if(pid[i].checked){
				changeValue(pid[i]);
			}
		}	
		window.returnValue=parent.parray;
		window.close();
		
	}
	
	function Check(){
		var pid = document.all("pid");
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
	
//---------------------------test -------------------------------------
//得到行对象 
function getRowObj(obj) { 
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

function changeValue(obj){	
	var p="";	
	var rowx = getRowNo(obj);		
	if ( obj.checked ){	
		if ( ptable.rows.length <= 2 ){
		var xpid =document.listform.pid.value;//k保证只有选中的才放到数组里
		var xproductname=document.listform.productname.value;
		var xspecmode=document.listform.specmode.value;
		var xunitid=document.listform.unitid.value;	
		var xunitname=document.listform.unitname.value
		var xprice=document.listform.price.value;
		p={productid:xpid,productname:xproductname,specmode:xspecmode,unitid:xunitid,unitname:xunitname,price:xprice};
		}else{			
			var xpid =document.listform.pid[rowx-2].value;//k保证只有选中的才放到数组里
			var xproductname=document.listform.productname[rowx-2].value;
			var xspecmode=document.listform.specmode[rowx-2].value;
			var xunitid=document.listform.unitid[rowx-2].value;
			var xunitname=document.listform.unitname[rowx-2].value
			var xprice=document.listform.price[rowx-2].value;
			p={productid:xpid,productname:xproductname,specmode:xspecmode,unitid:xunitid,unitname:xunitname,price:xprice};
		}	
		addObjValue(p);
	}else{
		delObjValue(obj.value);
	}
}

function addObjValue(obj){
	for(g=0; g<parent.parray.length; g++){
		var p=parent.parray[g];
		if ( p.productid == obj.productid ){
			return;
		}
	}
	parent.parray.push(obj);	
}

function defaultCheck(){
	var pid = document.listform.pid;
	if ( pid == undefined ){
		return;
	}		
	if ( pid.length > 1 ){
		for ( i=0;i < pid.length; i++){
			for (j=0; j<parent.parray.length; j++){
				var p=parent.parray[j];
				if ( p.productid == pid[i].value ){
					pid[i].checked = true;
				}				
			}
		}		
	}else{
		for (j=0; j<parent.parray.length; j++){
			var p=parent.parray[j];
			if ( p.productid == pid.value ){
				pid.checked = true;
			}		
		}
	}
}

</script>
</head>

<body onLoad="defaultCheck()">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择产品</td>
	    
        </tr>
      </table>
      <form name="search" method="post" action="selectProviderProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="11%"  align="right">
            <input name="pvid" type="hidden" id="pvid" value="${pvid}">
            产品类别：</td>
            <td width="22%"><input type="hidden" name="KeyWordLeft" id="KeyWordLeft">			
			<windrp:pstree id="KeyWordLeft" name="productstruts"/></td> 
	  	    <td width="13%" align="right">品牌：</td>
	  	    <td width="15%"><windrp:select key="Brand" name="unitid" p="y|d"/></td>
	  	    <td width="12%" align="right">关键字：</td>
	  	    <td width="20%"><input type="text" name="KeyWord">
              </td>
              <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td class="SeparatePage"><pages:pager action="../purchase/selectProviderProductAction.do"/></td>
        </tr>
      </table>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable" id="ptable">
        
          <tr align="center" class="title-top"> 
	  	    <td width="3%"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="9%" >产品编号</td>
            <td width="20%">产品名称</td>
            <td width="14%">规格</td>
            <td width="20%">单位</td>
            <td width="20%">单价</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
		  <tr align="center" class="table-back-colorbar" > 
		  	<td> <input type="checkbox" name="pid" value="${p.productid}" ></td>
            <td >${p.productid}</td>
            <td><input type="hidden" name="productname" value="${p.pvproductname}">
            ${p.pvproductname}</td>
            <td><input type="hidden" name="specmode" value="${p.pvspecmode}">
            ${p.pvspecmode}</td>
            <td><input type="hidden" name="unitid" value="${p.countunit}">
              <input type="hidden" name="unitname" value="${p.unitname}">
              ${p.unitname}</td>
            <td><input type="hidden" name="price" value="${p.price}">
              ${p.price}</td>
		  </tr>
          </logic:iterate> 
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
