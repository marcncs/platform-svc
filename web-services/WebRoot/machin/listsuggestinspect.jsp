<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/jquery-1.4.2.min.js"> </SCRIPT>
<script language="JavaScript">
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}

var checkid=0;

	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 Detail();
	}
	
	function download(obj){
		document.location="../common/downloadfile.jsp?filename=upload\fail\\"+obj;
	}
	
	function SelectOrgan(){

		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");

	}
	
	function UploadIdcode(){
		window.open("../machin/toUploadSuggestInspectAction.do?billsort=1","newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}

 function Check(){
		if(document.listform.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.listform.length;i++){
			if (!document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.listform.length;i++){
			if (document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=false;
				}
		}
	} 
	
	function Delete(){
		var ids = "";
		$("#listform input[type=checkbox]").not($("#checkAll")).each(function(i){
			if(this.checked == true){
				ids = "," + this.value + ids;
			}
		}); 
		if(ids.length > 0){
			ids = ids.substring(1,ids.length);
		}
		if(ids.length <= 0){
			alert("请选择你要操作的记录!");
		}else{
			popWin("../machin/deleteSuggestInspectAction.do?ids="+ids,500,250);
		}
	}
	function Merge(){
		var ids = "";
		$("#listform input[type=checkbox]").not($("#checkAll")).each(function(i){
			if(this.checked == true){
				ids = "," + this.value + ids;
			}
		}); 
		if(ids.length > 0){
			ids = ids.substring(1,ids.length);
		}
		if(ids.length <= 0){
			alert("请选择你要操作的记录!");
		}else{
			popWin("../machin/mergeSuggestInspectAction.do?type=1&ids="+ids,500,250);
		}
	}
	function MergeAll(){
		if(confirm("确认全部合并吗?")){
			popWin("../machin/mergeSuggestInspectAction.do?type=0",500,250);
		}
	}
	function Remove(){
		var ids = "";
		$("#listform input[type=checkbox]").not($("#checkAll")).each(function(i){
			if(this.checked == true){
				ids = "," + this.value + ids;
			}
		}); 
		if(ids.length > 0){
			ids = ids.substring(1,ids.length);
		}
		if(ids.length <= 0){
			alert("请选择你要操作的记录!");
		}else{
			popWin("../machin/removeSuggestInspectAction.do?ids="+ids,500,250);
		}
	}
	
	
	function Detail(){
		setCookie("stockpile","1");
		if(checkid!=""){
			document.all.submsg.src="../machin/listSuggestInspectDetailAction.do?id="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Out(){
		var ids = "";
		$("#listform input[type=checkbox]").not($("#checkAll")).each(function(i){
			if(this.checked == true){
				ids = "," + this.value + ids;
			}
		}); 
		if(ids.length > 0){
			ids = ids.substring(1,ids.length);
		}
		if(ids.length <= 0){
			alert("请选择你要操作的记录!");
		}else{
			popWin("../machin/produceStockAlterMoveAction.do?ids="+ids,500,250);
		}
	}
	
	function excput(){
		search.target="";
		search.action="../machin/excPutSuggestInspectAction.do";
		search.submit();
		search.target="";
		search.action="../machin/listSuggestInspectAction.do";
	}
	
	function print(){
		search.target="_blank";
		search.action="../machin/printSuggestInspectAction.do";
		search.submit();
		search.target="";
		search.action="../machin/listSuggestInspectAction.do";
	}
	
	function Post(){
		var ids = "";
		$("#listform input[type=checkbox]").not($("#checkAll")).each(function(i){
			if(this.checked == true){
				ids = "," + this.value + ids;
			}
		}); 
		if(ids.length > 0){
			ids = ids.substring(1,ids.length);
		}
		if(ids.length <= 0){
			alert("请选择你要操作的记录!");
		}else{
		
			popWin("../machin/postSuggestInspectAction.do?ids="+ids,500,250);
		}
	}


</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td>出入库管理>>拣货建议单</td>
        </tr>
      </table>
       <form name="search" method="post" action="../machin/listSuggestInspectAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
         <tr class="table-back"> 
            
            <td align="right">是否排除：</td>
			<td><windrp:select key="IsRemove" name="isRemove" p="y|f" value="${isRemove}" /></td>
            <td width="8%" align="right">是否合并：</td>
            <td ><windrp:select key="IsMerge" name="isMerge" p="y|f" value="${isMerge}" /></td>
            <td width="9%"  align="right">是否出库：</td>
            <td width="25%"><windrp:select key="IsOut" name="isOut" p="y|f" value="${isOut}" /></td>
			<td width="5%"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">单据类型：</td>
            <td><windrp:select key="typeId" name="typeId" p="y|f" value="${typeId}" /></td>
			<td width="10%" align="right">制单日期：</td>
            <td width="24%"><input id="BeginDate" onFocus="javascript:selectDate(this)" size="12" name="BeginDate" value="${BeginDate}" readonly="readonly" >
-
  <input id="EndDate" onFocus="javascript:selectDate(this)" size="12" name="EndDate" value="${EndDate}" readonly="readonly" ></td>
            <td align="right">关键字：</td>
			<td width="19%" ><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}"></td>
			<td class="SeparatePage"><input name="Submit" type="image" id="Submit" 
								src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
       
      </table>
       </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">	
			  <td width="50">
				<a href="javascript:UploadIdcode();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导入</a></td>	
	          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	          <td width="50">
				<a href="javascript:Merge();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;合并</a></td>	
	          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	           <td width="80">
				<a href="javascript:MergeAll();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;全部合并</a></td>	
	          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	          <td width="50">
				<a href="javascript:Out();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;出库</a></td>	
	          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>		
	          <td width="80">
				<a href="javascript:Post();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;直接过账</a></td>	
	          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	          <td width="50">
				<a href="javascript:Remove();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;排除</a></td>	
	          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	          <td width="50">
				<a href="javascript:Delete();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>	
	          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
	          <td width="50">
				<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>	
	          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	          <td width="50">
				<a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a></td>	
	          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
	           	
		  <td class="SeparatePage"><pages:pager action="../machin/listSuggestInspectAction.do"/></td>
							
		</tr>
	 </table>
	 </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 600px;" >
		<FORM METHOD="POST" name="listform" ACTION="" id="listform">
      <table class="sortable" width="100%"  cellpadding="0" cellspacing="1"  bordercolor="#dddddd">
        
          <tr align="center" class="title-top-lock"> 
          		<td width="2%" class="sorttable_nosort">
					<input type="checkbox" id="checkall" name="checkall" onClick="Check();">
				</td>
            <td width="3%" >编号</td>
            <td width="10%">单据类型</td>
			<td width="8%">单据名称</td>
            <td width="15%">单据编号</td>
			<td width="10%" >往来单位</td>
            <td width="10%" >仓库名称</td>
			<td width="6%" >客户编号</td>
			<td width="15%" >制单日期</td>
			<td width="5%" >是否过账</td>
			<td width="5%" >是否排除</td>
			<td width="5%" >是否合并</td>
			<td width="5%" >是否出库</td>
			
          </tr>
          <logic:iterate id="u" name="alpi" > 
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,${u.id});"> 
           		<td><input type="checkbox" id="chk_" name="uid" value="${u.id}" /></td>
            <td>${u.id}</td>
            <td><windrp:getname key='typeId' value='${u.typeId}' p='f'/></td>
            <td>${u.typeName}</td>
			<td>${u.siid}</td>
			<td>${u.disWareHouseName}</td>
            <td >${u.souWareHouseName}</td>
			<td >${u.customerCode}</td>
			<td><windrp:dateformat value='${u.makeDate}'/></td>
			<td>${u.isPost}</td>
			<td><windrp:getname key='IsRemove' value='${u.isRemove}' p='f'/></td>
			<td><windrp:getname key='IsMerge' value='${u.isMerge}' p='f'/></td>
			<td><windrp:getname key='IsOut' value='${u.isOut}' p='f'/></td>
            </tr>
          </logic:iterate> 
        
      </table>
<br />
		<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>  
      </form> 
	  </div>
	   
    </td>
  </tr>
</table>
</body>
</html>
