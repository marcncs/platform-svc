<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectunit.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/producttype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
		 var parray=new Array();
		 
		 Array.prototype.remove=function(dx) { 
		     if(isNaN(dx)||dx>this.length){
		 		return false;
		 	} 
		     for(var i=0,n=0;i<this.length;i++){ 
		         if(this[i]!=this[dx]){ 
		             this[n++]=this[i];
		         } 
		     } 
		     this.length-=1;
		 } 
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
		if (p.fieldName == obj.fieldName){
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									选择字段
								</td>
							</tr>
						</table>
						<form name="search" method="post"
							action="../ditch/selectOrganWithdrawProductAction.do"><%--
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td width="12%" align="right">
										产品类别：
									</td>
									<td width="19%">
										<input type="hidden" name="KeyWordLeft" id="KeyWordLeft"
											value="${KeyWordLeft}">
										<input type="text" name="psname" id="psname"
											onFocus="javascript:selectptype(this, 'KeyWordLeft')"
											value="${psname}" readonly>
										<!--  
            <input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">			
			<windrp:pstree id="KeyWordLeft" name="productstruts"/>
			-->
									</td>
									<td width="12%" align="right">
										产品名称：
									</td>
									<td>
										<input type="hidden" name="pidd" id="pidd" value="${pidd}">
										<input type="text" name="ProductName" id="ProductName"
											onClick="selectDUW(this,'pidd','','pn')" value="${ProductName}"
											readonly>
									</td>
									<td width="15%" align="right">
										<input name="wid" type="hidden" id="wid" value="${wid}">
										关键字：
									</td>
									<td width="18%">
										<input type="text" name="KeyWord" value="${KeyWord}">
									</td>
									<td width="9%" align="right">
										<span class="SeparatePage"> <input name="Submit2" type="image"
												id="Submit" src="../images/CN/search.gif" title="查询"> </span>
									</td>
								</tr>

							</table>
						--%></form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="50">
									<a href="javascript:Affirm();"><img src="../images/CN/addnew.gif"
											width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="#" onClick="javascript:window.close();"><img
											src="../images/CN/cancelx.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;取消</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td class="SeparatePage">
<%--									<pages:pager action="../ditch/selectOrganWithdrawProductAction.do" />--%>
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
								cellspacing="1" id="ptable">

								<tr align="center" class="title-top">
									<td width="3%" class="sorttable_nosort">
										<input type="checkbox" name="checkall" onClick="Check();">
									</td>
									<td width="10%">
										字段名称
									</td>
								</tr>
								<c:set var="count" value="1" />
								<logic:iterate id="f" name="fields">
									<tr align="center" class="table-back-colorbar">
										<td>
											<input type="checkbox" name="pid" value="${f.fieldName}"
												onClick="changeValue(this)">
										</td>
										<td>
											${f.displayName}<c:if test="${f.required}"><span class="style1">*</span></c:if>
											<input type="hidden" name="pobj" id="pobj${count}"
												value="{fieldName:'${f.fieldName}',displayName:'${f.displayName}'}">
										
										</td>
									</tr>
									<c:set var="count" value="${count+1}" />
								</logic:iterate>
									<tr align="left" class="table-back-colorbar">
										<td colspan="2">
										说明：<br>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;带<span class="style1">*</span>的为必须配置的字段。<br>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计量单位数,件数任意配置一个即可。若都配置，优先选择件数。<br>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单据日期目前支持格式为yyyy-MM-dd(例:2015-01-31),若不配置，默认取导入时间为单据日期。<br>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;若配置了出货机构名称或出货仓库名称，系统会检查文件中名称是否与系统一致<br>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;表头行号,数据起始行号需设置默认值,无需配置对应列名。<br>
										</td>
									</tr>

							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
