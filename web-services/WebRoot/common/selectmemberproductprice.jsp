<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script type="text/javascript" src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectunit.js"> </SCRIPT>
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
				p=eval('(' +pobj[rowx-1].value+ ')'); 
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
			if ( p.productid == obj.productid ){
				return;
			}
		}
		parent.parray.push(obj);	
	}
	
	function delObjValue(obj){
		for(g=0; g<parent.parray.length; g++){
			var p=parent.parray[g];
			if ( p.productid == obj.productid ){
				parent.parray.remove(g);
				break;
			}
		}
	}

		

	function defaultCheck(){

		var pid =  document.all("pid");
		if ( pid == undefined || pid == null){
			return false;
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
	<div id="bodydiv" >
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td>选择产品</td>
        </tr>
      </table>
      <form name="search" method="post" action="../common/selectMemberProductPriceAction.do?cid=${cid}">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
		  	<td width="13%" align="right">产品类别：</td>
            <td width="14%">
              
            <input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}"/>			
			<windrp:pstree id="KeyWordLeft" name="productstruts" value="${productstruts}" />
			
			</td>
            <td width="11%" height="25" align="right">关键字：</td>
            <td width="30%" height="25"><input type="text" name="KeyWord" value="${KeyWord}"></td>
            <td class="SeparatePage">
            <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"/></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td class="SeparatePage"><pages:pager action="../common/selectMemberProductPriceAction.do"/></td>
        </tr>
      </table>
	  </div>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1" id="ptable">
          <tr align="center" class="title-top-lock"> 
            <td width="3%" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td>产品编号</td>
            <td>产品名称</td>
            <td>规格</td>
            <td>单位</td>
            <td>单价</td>
          </tr>
          <c:set var="count" value="1"/>
         <logic:iterate id="p" name="list" > 
		  <tr align="center" class="table-back-colorbar" > 
		  	<td> <input type="checkbox" name="pid" id="pid" value="${p.productid}" onClick="changeValue(this)"></td>
            <td>${p.productid}</td>
            <td>${p.productname}</td>
            <td>${p.specmode}</td>
            <td><input type="text" name="unitidname" size="6" value="${p.unitidname}" onFocus="selectUnit(this,'${p.productid}','${cid}','${oid}','5')"/></td>
            <td><span id="spanprice${count}">${p.price}</span></td>
            <input type="hidden" name="pobj" id="pobj${count}" value="{productid:'${p.productid}',productname:'${p.productname}',specmode:'${p.specmode}',unitid:'${p.unitid }',unitidname:'${p.unitidname}',unitprice:'${p.price}'}">
		  </tr>
          <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
      </table>
	  </div>
    </td>
  </tr>
</table>
</body>
</html>
