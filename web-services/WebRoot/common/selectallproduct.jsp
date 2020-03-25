<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="tag.jsp"%>
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectunit.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
	function Affirm(){
		if ( parent.parray.length){
			window.returnValue=parent.parray;
			window.close();
		}else{
			alert("请选择你要操作的记录!");
		}		
	}
	
	function Check(){
		if(document.all("checkall").checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		var pid =document.all("pid"); 
		if(pid==undefined){return;}
		if(pid.length){
			for(i=0;i<pid.length;i++){
				pid[i].checked=true;
				changeValue(pid[i]);
			}
		}else{
			pid.checked=true;
			changeValue(pid);
		}		
	}

	function uncheckAll(){
		var pid = document.all("pid");
		if(pid==undefined){return;}
		if(pid.length){
			for(i=0;i<pid.length;i++){
				pid[i].checked=false;
				delObjValue(pid[i].value);
			}
		}else{
			pid.checked=false;
			delObjValue(pid.value);
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
			p=eval('(' +document.listform.pobj.value+ ')'); 
		}else{
			p=eval('(' +document.listform.pobj[rowx-1].value+ ')'); 
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

function delObjValue(pid){
	for(g=0; g<parent.parray.length; g++){
		var p=parent.parray[g];
		if ( p.productid == pid ){
			parent.parray.remove(g);
			break;
		}
	}
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
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}

</script>
</head>

<body onLoad="defaultCheck()">
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
      <form name="search" method="post" action="../common/selectAllProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
		  	<td width="13%" align="right">产品类别：</td>
            <td width="14%">
            <input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">			
			<windrp:pstree id="KeyWordLeft" name="productstruts" value="${productstruts}"/></td>
			 <!--<td width="11%" height="25" align="right">品牌：</td>
            <td width="15%" height="25"><windrp:select key="Brand" name="Brand" p="y|d"/></td>-->
            <td width="11%" height="25" align="right">关键字：</td>
            <td width="30%" height="25"><input type="text" name="KeyWord" value="${KeyWord}">
            </td>
            <td class="SeparatePage">
            <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
            
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" > 
        <tr class="title-func-back">
          <td width="50"><a href="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td class="SeparatePage"><pages:pager action="../common/selectAllProductAction.do"/></td>
        </tr>
      </table>
</div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" id="ptable" class="sortable">
        
          <tr align="center" class="title-top"> 
	  	    <td width="3%" height="25" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="15%" height="25">产品编码</td>
            <td width="22%">产品名称</td>
            <td width="30%">规格</td>
			<td width="12%">品牌</td>
            <td width="6%">单位</td>
            <td width="12%">&nbsp;</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
		  <tr class="table-back-colorbar" > 
		  	<td> <input type="checkbox" name="pid" value="${p.id}" onClick="changeValue(this)"></td>
            <td height="20">${p.id}</td>
            <td><input type="hidden" name="productname" value="${p.productname}">${p.productname}</td>
            <td><input type="hidden" name="specmode" value="${p.specmode}">${p.specmode}</td>
			<td><input type="hidden" name="brandname" value="<windrp:getname key='Brand' value='${p.brand}' p='d'/>"><windrp:getname key='Brand' value='${p.brand}' p='d'/></td>
            <td><input type="hidden" name="unitid" value="${p.countunit}">
              <input type="hidden" name="unitidname" value="${p.countunitname}"><input type="hidden" name="cost" value="${p.cost}">
              ${p.countunitname}<input type="hidden" name="pobj" id="pobj" value="{productid:'${p.id}',productname:'${p.productname}',specmode:'${p.specmode}',unitid:'${p.countunit}',unitidname:'${p.countunitname}',cost:'${p.cost}'}"></td>
            <td></td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
	</div>
    </td>
  </tr>
</table>
</body>
</html>
