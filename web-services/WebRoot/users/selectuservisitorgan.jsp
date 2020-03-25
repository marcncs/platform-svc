<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>	
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
function Check(){
		if(document.getElementById("checkall").checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		var pid = document.listform.pid;		
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
		var pid = document.listform.pid;
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
		addObjValue(obj.value);
	}else{
		delObjValue(obj.value);
	}
}

function addObjValue(obj){
	for(g=0; g<parent.parray.length; g++){
		if ( parent.parray[g] == obj ){
			return;
		}
	}
	parent.parray.push(obj);	
}

function delObjValue(pid){
	for(g=0; g<parent.parray.length; g++){
		if ( parent.parray[g] == pid ){
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
				if ( parent.parray[j] == pid[i].value ){
					pid[i].checked = true;
				}				
			}
		}		
	}else{
		for (j=0; j<parent.parray.length; j++){
			if ( parent.parray[j] == pid.value ){
				pid.checked = true;
			}		
		}
	}
}
	
function formcheck(){
	parent.KeyWord=search.KeyWord.value;
}
</script>
</head>

<body onLoad="defaultCheck()">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	 <form name="search" method="post" action="../users/selectUserVisitOrganAction.do" onSubmit="return formcheck();">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
          	<input type="hidden" name="uid" value="${uid }">
            <td width="16%" align="right">关键字：</td>
            <td width="16%"><input type="text" name="KeyWord" value="${KeyWord }"></td>         
            <td width="26%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
	  	  </tr>
       
      </table>
       </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
		  <td style="text-align:right"><pages:pager action="../users/selectUserVisitOrganAction.do"/></td>						
		</tr>
	</table>	
	 </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 300px;" >
		 <FORM METHOD="POST" name="listform" ACTION=""  >    
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">  
	   
          <tr align="center" class="title-top-lock"> 
	  	    <td width="4%" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="20%" height="25">机构编号</td>
            <td width="30%">机构名称</td>
          </tr>
		  <c:set var="count" value="2"/>
          <logic:iterate id="p" name="vulist" > 
		  <tr align="center" class="table-back-colorbar" > 
		  	<td> <input type="checkbox" name="pid" value="${p.organid}" onClick="changeValue(this)"></td>
            <td height="20">${p.organid}</td>
            <td><input type="hidden" name="organname" value="${p.organname}">${p.organname}</td>      
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
