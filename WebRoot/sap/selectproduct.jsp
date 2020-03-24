<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<base target="_self">
	<head>
		<title>机构列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectunit.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/producttype.js"> </SCRIPT>
		<script type="text/javascript">

var checkid="";
var product={id:"",mCode:"",productname:"",countUnit:"",specmode:"",countUnitName:""};
function CheckedObj(obj,objid,objmcode,objproductname,objspecmode,objcountUnitName, objcountUnit){
 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
 product.id=objid;
 product.mCode=objmcode;
 product.productname=objproductname;
 product.specmode=objspecmode;
 product.countUnitName=objcountUnitName;
 product. countUnit=objcountUnit;
}

function Affirm(){
		if(checkid!=""){
		window.returnValue=product;
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
	/*
	
	//根据得到的行对象得到所在的行数 
	function getRowNo(obj){ 
	var trObj = getRowObj(obj); 
	var trArr = trObj.parentNode.children; 
	for(var trNo= 0; trNo < trArr.length; trNo++){ 
	if(trObj == trObj.parentNode.children[trNo]){ 
		var browser = navigator.appName;
		var b_version = navigator.appVersion;
		
		var version = b_version.split(";");
		var trim_Version = version[1].replace(/[ ]/g,"");
		alert(trim_Version);
		if(browser=="Microsoft Internet Explorer"&&trim_Version=="MSIE8.0"){
			return trNo+1;
		}else if(browser=="Microsoft Internet Explorer"&&trim_Version=="MSIE9.0"){
			return trNo;
		}
		return trNo+1;
	} 
	} 
	}  */
	//根据得到的行对象得到所在的行数 
	//兼容IE9,获取行号
	function getRowNo(obj){ 
	var trObj = getRowObj(obj); 
	var trArr = trObj.parentNode.parentNode.rows; 
	for(var trNo= 0; trNo < trArr.length; trNo++){ 
	if(trObj == trArr[trNo]){ 
	return trNo; 
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

	//改变单位
	function changeSelectValue(obj){
		//selectUnit(tt,obj,v_cid,v_oid,objtype);
		//var pid = document.all("pid");
		var pid = document.getElementsByName("pid");
		var rowx = getRowNo(obj);	
		var pobj = document.all("pobj");
		if ( ptable.rows.length <= 2 ){	
				p=eval('(' +pobj.value+ ')'); 
		}else{
				p=eval('(' +pobj[rowx-1].value+ ')'); 
		}	
		if(pid[rowx-1].checked){
			delObjValue(p);
			changeValue(pid[rowx-1]);
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									选择产品
								</td>

							</tr>
						</table>
						<form name="search" method="post"
							action="../sap/selectProductAction.do?OID=${OID }">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
								 <td width="11%" height="25" align="right">关键字：</td>
            <td width="30%" height="25"><input type="text" name="KeyWord" value="${KeyWord}"></td>

									</td>
										<td width="13%" align="right">产品类别：</td>
            <td width="14%">
            <input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">	
          <input type="text" name="psname" id="psname" onFocus="javascript:selectptype(this, 'KeyWordLeft')" value="${psname}" readonly>
          
			</td>
									
									<td>
										&nbsp;

									</td>
									<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>

							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="50">
									<a href="#" onClick="javascript:Affirm();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;确定</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="#" onClick="javascript:window.close();"><img
											src="../images/CN/cancelx.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;取消</a>
								</td>
								<td class="SeparatePage">
									<pages:pager action="../sap/selectProductAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 400px;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">

								<tr align="center" class="title-top-lock">
									<td>
										产品编号
									</td>
									<td>
										产品名称
									</td>
									<td>
										物料号
									</td>
									<td>
										规格
									</td>
									<td>
										计量单位
									</td>
								</tr>
								<logic:iterate id="p" name="sls">
									<tr align="center" class="table-back-colorbar"
									 onClick="CheckedObj(this,'${p.id}','${p.mCode}','${p.productname}','${p.specmode}','${p.countUnitName}','${p.countunit}');"
										onDblClick="Affirm();">
										<td>
											${p.id}
										</td>
										<td>
											<input type="hidden" name="productname"
												value="${p.productname}">
											${p.productname}
										</td>
										<td>
											<input type="hidden" name="mCode" value="${p.mCode}">
											${p.mCode}
										</td>
										<td>
											${p.specmode}
										</td>
										<td>
											${p.countUnitName}
										</td>
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

