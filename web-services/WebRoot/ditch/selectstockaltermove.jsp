<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
		var pid = document.all("pid");
		var checkall = document.all("checkall");
		if (pid==undefined){return;}
		if (pid.length){
			for(i=0;i<pid.length;i++){
					pid[i].checked=checkall.checked;
					changeValue(pid[i]);
			}
		}else{
			pid.checked=checkall.checked;
			changeValue(pid);
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
	var pobj = document.all("pobj");
	if ( ptable.rows.length <= 2 ){	
		p=eval('(' +pobj.value+ ')'); 
	}else{
		p=eval('(' +pobj[rowx-2].value+ ')'); 
	}			
	if ( obj.checked ){	
		addObjValue(p);
	}else{
		delObjValue(p);
	}
}

function addObjValue(obj){
	for(g=0; g<parent.parray.length; g++){
		var p=parent.parray[g];
		if ( p.poid == obj.poid ){
			return;
		}
	}
	parent.parray.push(obj);	
}

function delObjValue(pid){
	for(g=0; g<parent.parray.length; g++){
		var p=parent.parray[g];
		if ( p.poid == pid.poid ){
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
				if ( p.poid == pid[i].value ){
					pid[i].checked = true;
				}				
			}
		}		
	}else{
		for (j=0; j<parent.parray.length; j++){
			var p=parent.parray[j];
			if ( p.poid == pid.value ){
				pid.checked = true;
			}		
		}
	}
}
this.onload =function onLoadDivHeight(){
defaultCheck();
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
          <td width="772"> 选择订购单</td>
	    
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td class="SeparatePage"><pages:pager action="../ditch/selectStockAlterMoveAction.do"/></td>
        </tr>
      </table>
	  </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		<FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1" id="ptable">
        
          <tr align="center" class="title-top-lock"> 
            <td width="3%" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td>单据编号</td>
            <td>日期</td>
            <td>金额</td>
          </tr>
         <logic:iterate id="p" name="list" > 
		  <tr align="center" class="table-back-colorbar" > 
		  	<td> <input type="checkbox" name="pid" id="pid" value="${p.id}" onClick="changeValue(this)"></td>
            <td height="20">${p.id}</td>
            <td><windrp:dateformat value='${p.makedate}' p='yyyy-MM-dd'/>
			<input type="hidden" name="pobj" id="pobj" value="{poid:'${p.id}',subsum:'${p.totalsum}',movedate:'${p.makedate}',type:'${p.type}'}">
              </td>
            <td align="right"><windrp:format value="${p.totalsum}"/></td>
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
