<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/showbill.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
var checkid=0;
function CheckedObj(obj,objid){

	for(i=0; i<obj.parentNode.childNodes.length; i++)
	{
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
	}
 
	obj.className="event";
	checkid=objid;
}

function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.searchform.ProductID.value=p.id;
	document.searchform.ProductName.value=p.productname;
	}
function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do?type=1",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.searchform.MakeOrganID.value=p.id;
	document.searchform.oname.value=p.organname;
	
	document.searchform.WarehouseID.value="";
	document.searchform.wname.value="";
	}
	function SelectOrgan2(){
			var oid = document.searchform.MakeOrganID.value;
	if(oid==""){
		alert("请选择制单机构!");
		return;
	}
		var p=showModalDialog("../common/selectChildOrganAction.do?OID="+oid,null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.searchform.ReceiveOrganID.value=p.id;
				document.searchform.oname2.value=p.organname;
		}
function excput(){
	excputform.submit();
}
function print(){
	excputform.target="_blank";
	excputform.action="../report/printStockAlterMoveDetailAction.do";
	excputform.submit();
	excputform.target="";
	excputform.action="../report/excPutStockAlterMoveDetailAction.do";
}
this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}

var win=null;
function doRemark(){
	if(checkid!=""){
		popWin("../warehouse/toRemarkTakeTicketAction.do?id="+checkid,600,300);
		//win=window.open("../warehouse/toRemarkTakeTicketAction.do?id="+checkid,"","height=300,width=600,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}else{
		alert("请选择你要操作的记录!");
	}
}

function updateRemark(){
	if(checkid!=""){
		popWin("../report/toUpdateAlarmByRemarkAction.do?id="+checkid,600,300);
	}else{
		alert("请选择你要操作的记录!");
	}
}
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
        <div id="div1">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="0"></td>
          <td >仓库管理>>库存预警报表</td>
        </tr>
      </table>
       <form name="searchform" method="post" action="alarmByProductStockpileAllAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
			<td width="8%"  align="right">发货机构：</td>
            <td width="15%"><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>	</td>
            <td width="12%" align="right">发货仓库：</td>
            <td width="15%">
            <input type="hidden" name="WarehouseID" id="WarehouseID" value="${WarehouseID}">
				<input type="text" name="wname" id="wname" onClick="selectDUW(this,'WarehouseID',$F('MakeOrganID'),'vw')" value="${wname}" readonly>	
			</td>
			<td width="8%" align="right">库存状态：</td>
            <td width="12%">
            	<select name="sstruts" id="sstruts">
            		<c:if test="${sstruts == 0}">
            			<option value="0" selected>安全</option>
            			<option value="-1">-请选择-</option>
	            		<option value="1">库存不足</option>
	            		<option value="2">库存超出</option>
            		</c:if>
            		<c:if test="${sstruts == 1}">
            			<option value="1" selected>库存不足</option>
            			<option value="-1">-请选择-</option>
	            		<option value="0">安全</option>
	            		<option value="2">库存超出</option>
            		</c:if>
            		<c:if test="${sstruts == 2}">
            			<option value="2" selected>库存超出</option>
            			<option value="-1">-请选择-</option>
	            		<option value="0">安全</option>
	            		<option value="1">库存不足</option>
            		</c:if>
            		<c:if test="${sstruts == -1 || sstruts == null}">
            			<option value="-1" selected>-请选择-</option>
	            		<option value="0">安全</option>
	            		<option value="1">库存不足</option>
	            		<option value="2">库存超出</option>
            		</c:if>
            	</select>
			</td>
			</tr>
			 <tr class="table-back">
             <td width="8%" align="right">关键字：</td>
            <td width="15%"><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}" maxlength="60"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
           <td>&nbsp;</td>
            <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
      </table>
       </form>
       <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
          <tr class="title-func-back"> 
		  	<td width="50">
			<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
		    </td>
		    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		    <td width="80">
			<a href="javascript:updateRemark();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;添加备注</a>
		    </td>
            <td class="SeparatePage">
			<pages:pager action="../report/alarmByProductStockpileAllAction.do"/>	
            </td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">    
		  <td width="10%">仓库编号</td>
		  <td width="15%">仓库名称</td>
          <td width="7%">基本单位</td>
          <td width="15%">实际库存</td>
          <td width="10%">安全库存(下限)</td>
          <td width="10%">安全库存(上限)</td>
          <td width="10%">库存状态</td>
          <td width="8%">备注</td>
          </tr>
        <c:set var="totalfrate" value="0"/>
	<c:forEach items="${warehouseList}" var="d">
        <tr class="table-back-colorbar" onClick="CheckedObj(this,'${d.id}');">
        	<c:if test="${d.stockpilestruts == 0}">
        	  <td >${d.id}</td>
	          <td >${d.warehousename}</td>
	          <td >KG</td>
	          <td >${d.stockpile}</td>
	          <td >${d.belowNumber}</td>
	          <td >${d.highNumber}</td>
	          <td >安全</td>
	          <td >${d.remark}</td>
        	</c:if>
        	<c:if test="${d.stockpilestruts == 1}">
        	  <td ><span style="color:#2F5E96;"><b>${d.id}</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>${d.warehousename}</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>KG</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>${d.stockpile}</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>${d.belowNumber}</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>${d.highNumber}</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>库存不足</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>${d.remark}</b></span></td>
        	</c:if>
        	<c:if test="${d.stockpilestruts == 2}">
        	  <td ><span style="color:#C00A0A;"><b>${d.id}</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>${d.warehousename}</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>KG</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>${d.stockpile}</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>${d.belowNumber}</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>${d.highNumber}</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>库存超出</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>${d.remark}</b></span></td>
        	</c:if>
       </tr>
        <c:set var="totalfrate" value=""/>
	</c:forEach> 
	
	</table>
      </div>
    </td>
  </tr>
</table>
<form  name="excputform" method="post" action="exportAlarmByProductStockpileAllAction.do">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="ProductID" id ="ProductID" value="${ProductID}">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName}">
<input type="hidden" name="WarehouseID" id ="WarehouseID" value="${WarehouseID}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
<input type="hidden" name="sstruts" id ="sstruts" value="${sstruts}">
</form>
</body>
</html>
