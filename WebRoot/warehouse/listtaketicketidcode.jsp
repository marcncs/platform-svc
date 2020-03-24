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
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
var checkid="";
var idcode="";
function CheckedObj(obj,objid,objidcode){
 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
 idcode=objidcode;
}

function Update(){
		if(checkid!=""){
		window.open("../warehouse/toUpdPurchaseIncomeAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}


function Del(){
	var flag=false;
	var rid=document.all("che");
	var rids="";
	if ( rid.length >1){
		for(var i=0; i<rid.length; i++){
			if (rid[i].checked){
				flag=true;
				rids=rid[i].value+","+rids;
			}
		}
	}else{
		if(rid.checked){
			flag=true;
			rids=rid.value+",";
		}
	}
	if(flag){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			showloading();			
			popWin4("../warehouse/delTakeTicketIdcodeAction.do?ids="+rids,500,250, "del");
		}
	}else{
		alert("请选择你要操作的记录!");
	}
}

function Check(){
	var pid = document.all("che");
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

function addIdcodeParent() {
	parent.formcheck();
}
function Import(billid,prid) {
		window
				.open(
						"../warehouse/toImportTakeBillAction.do?billid="+billid+"&prid="+prid,
						"",
						"height=300,width=500,top=190,left=190,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");

	}

function Import1() {
	window
			.open(
					"../warehouse/toImportIdcodeAction.do",
					"",
					"height=300,width=500,top=190,left=190,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");

}
function Import2(billid,prid) {
		window
				.open(
						"../warehouse/toImportLInTakeBillAction.do?billid="+billid+"&prid="+prid,
						"",
						"height=300,width=500,top=190,left=190,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");

	}

</script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<form name="search" method="post" action="../warehouse/listTakeTicketIdcode.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr class="table-back">
           <ws:hasAuth operationName="/warehouse/toImportTakeBillAction.do">
			<td width="70">
			<input type="hidden" id="prid" name="prid" value="${prid}"/>
			<a href="javascript:Import('${billid}','${prid}');"><img src="../images/CN/import.gif"
			width="16" height="16" border="0" align="absmiddle">&nbsp;条码导入</a>
			</td>
			<td width="1">
			<img src="../images/CN/hline.gif" width="2" height="14">
			</td>
		  </ws:hasAuth>
		  
		  <ws:hasAuth operationName="/warehouse/toImportIdcodeAction.do">
			<td width="130">
			<a href="javascript:Import1();"><img src="../images/CN/import.gif"
			width="16" height="16" border="0" align="absmiddle">&nbsp;条码不在系统中导入</a>
			</td>
			<td width="1">
			<img src="../images/CN/hline.gif" width="2" height="14">
			</td>
		  </ws:hasAuth>

	      <td width="300">
	      &nbsp;
	      <ws:hasAuth operationName="/warehouse/addTakeTicketIdcodeiiAction.do">
	      	<input type="button" value="新增" onClick="addIdcodeParent()" ${isaudit==1?"disabled":""}>
	      </ws:hasAuth>
	      &nbsp;
	      <input type="button" name="cancel" value="取消" onClick="parent.window.close();">
	      &nbsp; 关键字： &nbsp; 
	      <input type="text" name="KeyWord" maxlength="40">
	      &nbsp;
<!--	      <input style="padding-top: 2px;" name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">-->
	      </td>
<!--		  
            <td width="11%" align="right">关键字：</td>
            <td width="19%"><input type="text" name="KeyWord" maxlength="40"></td>
            <td width="9%" height="25" align="right">&nbsp;</td>
            	  	 -->
            <td><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>

	  	  </tr>
        
      </table>
      </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
		 <!-- <td width="50" >
			<a href="#" onClick="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>-->
			<td width="50" >
			<ws:hasAuth operationName="/warehouse/delTakeTicketIdcodeAction.do">
			<c:choose>
			  <c:when test="${ ( empty isAudit) || isAudit != 1 }">
			  	<a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a>
			  </c:when>
			  <c:otherwise>
			  	<a href="#" disabled><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a>
			  </c:otherwise>
          	</c:choose>	
          	</ws:hasAuth>		
			</td>	
		  <td class="SeparatePage"><pages:pager action="../warehouse/listTakeTicketIdcode.do"/></td>						
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
           <td width="3%" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td>
		 	<td>条码</td>
			<td>单位</td>
			<td>数量</td>
            <td>仓位</td>
			<td>批号</td>
			<td>生产日期</td>
			<td>包装数量</td>
			<td>扫码日期</td>
          </tr>
		  <c:set var="count" value="2"/>
          <logic:iterate id="p" name="vulist" > 
		  <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}','${p.idcode}');"> 			
          <td> <input type="checkbox" name="che" id="che" value="${p.id}"></td>
          <td>${p.idcode}</td> 
		  	<td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
		  	<td>${p.quantity}</td> 
		  	<%-- 
			<td>${p.startno}</td> 
			<td>${p.endno}</td> 
            --%>
            <td height="20">${p.warehousebit}</td>
			<td>${p.batch}</td>     
			<td><windrp:dateformat  p='yyyy-MM-dd' value='${p.producedate}'  /> </td>  
			<td>${p.packquantity}</td> 
			<td>${p.makedate}</td> 
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
