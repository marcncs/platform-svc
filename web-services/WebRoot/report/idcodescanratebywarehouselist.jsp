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
	var p=showModalDialog("../common/selectWarehouseOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
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
function SelectOrderColumn(){
	popWin("../sys/toSelectOrderColumnAction.do",650,450);
}

function setOrderColumn(sql, name){
	document.searchform.orderbySql.value=sql;
	document.searchform.orderbySqlShowName.value=name;
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
          <td >报表分析>>出库>>扫描率按仓库汇总</td>
        </tr>
      </table>
       <form name="searchform" method="post" action="idcodeScanRateByWarehouseAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
			<td width="12%"  align="right">发货机构：</td>
            <td width="20%"><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>	</td>
            <td width="12%" align="right">发货仓库：</td>
            <td width="20%">
            <input type="hidden" name="WarehouseID" id="WarehouseID" value="${WarehouseID}">
				<input type="text" name="wname" id="wname" onClick="selectDUW(this,'WarehouseID',$F('MakeOrganID'),'vw')" value="${wname}" readonly>	
			</td>
             <td width="12%" align="right">产品：</td>
            <td width="20%"><input name="ProductID" type="hidden" id="ProductID" value="${ProductID}">
              <input id="ProductName" name="ProductName" value="${ProductName}" readonly><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a></td>

          </tr>
          <tr class="table-back">
          <td  align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" 
             onFocus="selectDate(this);"  size="10" readonly="readonly">
-
  <input name="EndDate" type="text" id="EndDate" value="${EndDate}"  
  			onFocus="selectDate(this);"  size="10" readonly="readonly"></td>
  			<td align="right">自定义排序：</td>
            <td ><input name="orderbySql" type="hidden" id="orderbySql" value="${orderbySql}">
              <input id="orderbySqlShowName" name="orderbySqlShowName" value="${orderbySqlShowName}" readonly><a href="javascript:SelectOrderColumn();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a></td>
           <td align="right">关键字：</td>
            <td><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}" maxlength="60"></td>
          <!--    <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          -->

          </tr>
         <!-- 控制表格内容不自动换行 by shoshana -->
          <tr class="table-back">
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>                              
          </tr>
       
      </table>
       </form>
       <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
          <tr class="title-func-back"> 
		  	<td width="50">
			<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
		    </td>
            <td class="SeparatePage">
			<pages:pager action="../report/idcodeScanRateByWarehouseAction.do"/>	
            </td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">    
		  <td width="8%">发货机构</td>
		  <td width="15%">发货仓库</td>
          <td width="5%">出库数量(箱)</td>
          <td width="5%">出库数量(EA)</td>
          <td width="5%">扫描数量(箱)</td>
          <td width="5%">扫描数量(EA)</td>
          <td width="5%">扫描率</td>
          </tr>
        <c:set var="totalfrate" value="0"/>
	<c:forEach items="${ttlist}" var="d">
        <tr class="table-back-colorbar" onClick="CheckedObj(this,'${d.ttdid}');">           
		   <td >${d.outorganname}</td>
          <td >${d.warehouseid}</td>
          <td >${d.boxnum}</td>
          <td ><windrp:format p="###,##0" value='${d.scatternum}' /></td>
          <td >${d.idcodeboxnum}</td>
          <td >${d.idcodescatternum}</td>
          <td >${d.rate}</td>
          </tr>
        <c:set var="totalfrate" value=""/>
	</c:forEach> 
	
	</table>
      </div>
    </td>
  </tr>
</table>
<form  name="excputform" method="post" action="exportIdcodeScanRateByWarehouseAction.do">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="ProductID" id ="ProductID" value="${ProductID}">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName}">
<input type="hidden" name="WarehouseID" id ="WarehouseID" value="${WarehouseID}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
</body>
</html>
