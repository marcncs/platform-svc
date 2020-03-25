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
<SCRIPT language="javascript" src="../js/jquery-1.4.2.min.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
function check(){
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
			changeValue(pid[i]);
		}
	}else{
		pid.checked=false;
		changeValue(pid[i]);
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
	addObjValue(obj);

}

function addObjValue(obj){
	for(g=0; g<parent.parray.length; g++){
		if ( parent.parray[g][0] == obj.value ){
			parent.parray[g][1]=obj.checked;
			return;
		}
	}
	var a=new Array(2);
	a[0]=obj.value;
	a[1]=obj.checked;
	parent.parray.push(a);	
}

function defaultCheck(formName,checkboxName){
	var objs=$("[name="+formName+"] [name="+checkboxName+"]");

	if ( objs.length > 0 ){
		var isCheckAll=true;
		objs.each(function(i){
			var obj = this;
			if(parent.parray.length>0){
				for (j=0; j<parent.parray.length; j++){
					if ( parent.parray[j][0] == obj.value ){
						obj.checked = parent.parray[j][1];
						break;
					}					
				}
			}
			if(!obj.checked){
				isCheckAll=false;
			}
		});
		document.getElementById("checkall").checked=isCheckAll;
	}

}

function formcheck(){
	parent.KeyWord=search.KeyWord.value;
	parent.sysid=search.sysid.value;
	parent.bigRegionName=search.bigRegionName.value;
	parent.officeName=search.officeName.value;
}

</script>
</head>

<body onLoad="defaultCheck('listform','pid')">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	 <form name="search" method="post" action="../sys/toVisitUsersWarehouseAction.do?type=list"  onSubmit="return formcheck();">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
               <tr  class="table-back">
        <td  width="20%" align="right"> 许可用户： </td>
        <td width="20%" align="left"> ${user.realname } </td>
         <td width="20%">&nbsp;</td>
         <td width="20%">&nbsp;</td>
         <td >&nbsp;</td>
        </tr>
         <tr class="table-back">
            <td align="right">系统编号： </td>
            <td align="left"> <input id="sysid" name="sysid" type="text" value="${sysid}"></td>
            <td width="16%" align="right">关键字：</td>
            <td width="58%" align="left"><input type="text" name="KeyWord" value="${KeyWord }"></td>         
            <td >&nbsp;</td>
	  	  </tr>
          <tr class="table-back">
            <td align="right">大区： </td>
            <td align="left"> <input id="bigRegionName" name="bigRegionName" type="text" value="${bigRegionName}"></td>
            <td width="16%" align="right">办事处：</td>
            <td width="58%" align="left"><input type="text" name="officeName" value="${officeName }"></td>         
            <td width="26%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
	  	  </tr>
       
      </table>
       </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
		  <td style="text-align:right"><pages:pager action="../sys/toVisitUsersWarehouseAction.do?type=list"/></td>						
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
	  	    <td width="4%" class="sorttable_nosort"><input type="checkbox" id="checkall" name="checkall" onClick="check();" ></td>
            <td width="10%" height="25">仓库编号</td>
            <td width="15%">仓库名称</td>
            <td width="10%" height="25">机构编号</td>
            <td width="18%">内部编号</td>
            <td width="15%">机构名称</td>
            <td width="12%">系统编号</td>
            <td width="10%">大区</td>
            <td width="8%">办事处</td>
            
          </tr>
		  <c:set var="count" value="2"/>
          <logic:iterate id="r" name="wvlist" > 
			  <tr align="center" class="table-back-colorbar" > 
			  	<td><input type="checkbox" id="chk_${r.id}" name="pid" value="${r.wid}" onclick="changeValue(this)" ${r.id!=null?"checked":""}>
			  	</td>
	            <td height="20" title="${r.wid}">${r.wid}</td>
	            <td title="${r.warehouseName}"><ws:subString value="${r.warehouseName}" length="20"/></td>
	            <td title="${r.organId}"><ws:subString value="${r.organId}" length="20"/></td>
	            <td title="${r.oecode}"><ws:subString value="${r.oecode}" length="20"/></td> 
	            <td title="${r.organName}"><ws:subString value="${r.organName}" length="20"/></td>    
	            <td title="${r.sysId}"><ws:subString value="${r.sysId}" length="20"/></td>  
	            <td title="${r.bigRegionName}"><ws:subString value="${r.bigRegionName}" length="20"/></td>  
	            <td title="${r.officeName}"><ws:subString value="${r.officeName}" length="20"/></td>  
	              
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

