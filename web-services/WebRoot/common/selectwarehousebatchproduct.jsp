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
<SCRIPT language="javascript" src="../js/producttype.js"> </SCRIPT>
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
		if ( p.batch == obj.batch && p.productid == obj.productid){
			return;
		}
	}
	parent.parray.push(obj);	
}

function delObjValue(obj){
	for(g=0; g<parent.parray.length; g++){
		var p=parent.parray[g];
		if ( p.productid == obj.productid && p.batch==obj.batch ){
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
          <td width="772"> 选择产品</td>	    
        </tr>
      </table>
      <form name="search" method="post" action="selectWarehouseBatchProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
		  <td width="12%" align="right">产品类别：</td>
            <td width="19%">
             <input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">	
          <input type="text" name="psname" id="psname" onFocus="javascript:selectptype(this, 'KeyWordLeft')" value="${psname}" readonly>
          <!--  
            <input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">			
			<windrp:pstree id="KeyWordLeft" name="productstruts"/>
			-->
			</td>
            <td width="8%" align="right">批号：</td>
            <td width="19%"><input type="text" name="Batch" value="${Batch}"></td>
			 <td width="15%"  align="right">
			 <input name="wid" type="hidden" id="wid" value="${wid}">
				名称关键字：</td>
            <td width="18%" ><input type="text" name="KeyWord" value="${KeyWord}"></td>
            <td width="9%" align="right"><span class="SeparatePage">
              <input name="Submit2" type="image" id="Submit" 
					src="../images/CN/search.gif"  title="查询">
            </span></td>
          </tr>
      
      </table>
        </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td class="SeparatePage"><pages:pager action="../common/selectWarehouseBatchProductAction.do"/></td>
        </tr>
      </table>
       </div>
       </td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		 <FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1" id="ptable" >
       
          <tr align="center" class="title-top">
            <td width="3%" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="15%" >产品编号</td>
            <td width="18%">产品名称</td>
            <td width="15%">规格</td>
            <td width="13%">批号</td>
            <td width="12%">单位</td>           
            <!--<td width="10%">价格</td>-->
            <!--<td width="14%">数量</td>-->
          </tr>
          <c:set var="count" value="2"/>
          <logic:iterate id="p" name="sls" >
            <tr align="center" class="table-back-colorbar" >
              <td><input type="checkbox" name="pid" value="${p.productid}"  onClick="changeValue(this)"></td>
              <td >${p.productid}</td>
              <td>${p.productname}</td>
              <td>${p.specmode}</td>
              <td>${p.batch}</td> 
              <td><input type="text" name="unitidname" size="6" value="${p.unitidname}" onFocus="selectUnit(this,'${p.productid}','','','')">
              <!--<input type="text" name="unitidname" size="6" value="${p.unitidname}" onFocus="selectUnit(this,'${p.productid}','','${oid}','3')">-->
               <input type="hidden" name="stockpile" id="stockpile${count}" value="${p.stockpile}">
                <input type="hidden" name="pobj" id="pobj${count}" value="{productid:'${p.productid}',productname:'${p.productname}',specmode:'${p.specmode}',cost:'${p.cost }',unitid:'${p.unitid }',unitidname:'${p.unitidname }',batch:'${p.batch }',unitprice:'${p.price}',quantity:'${p.squantity }'}">
              </td>                
              <!--<td align="right"><span id="spanprice${count}"><windrp:format value="${p.price}"/></span></td>-->
              <!--<td><span id="spanstockpile${count}">${p.squantity}</span>
               
                </td>-->
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
