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
<SCRIPT language="javascript" src="../js/producttype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/ShowWB.js"> </SCRIPT>

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

function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.searchform.ProductID.value=p.id;
	document.searchform.ProductName.value=p.productname;
}

function updateRemark(){
	if(checkid!=""){
		popWin("../report/toUpdateProtectDateByRemarkAction.do?id="+checkid,600,300);
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
          <td >仓库管理>>库存保质期预警报表</td>
        </tr>
      </table>
       <form name="searchform" method="post" action="protectDateByProductStockpileAllAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
         
          <tr class="table-back">
			<td width="10%" align="right">发货机构：</td>
            <td ><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>	
			</td>
            <td  align="right">发货仓库：</td>
            <td >
            <input type="hidden" name="WarehouseID" id="WarehouseID" value="${WarehouseID}">
				<input type="text" name="wname" id="wname" onClick="selectDUW(this,'WarehouseID',$F('MakeOrganID'),'vw')" value="${wname}" readonly>	
			</td>
			<td align="right">到期日期：</td>
      		<td><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" size="10" onFocus="javascript:selectDate(this)" readonly> - <input name="EndDate" type="text" id="EndDate" value="${EndDate}" size="10" onFocus="javascript:selectDate(this)" readonly>
      		</td>
      		<td></td>
          </tr>
          
          
          <tr class="table-back">
            <td align="right">产品类别：</td>
            <td >
	            <input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">	
	          	<input type="text" name="psname" id="psname" onFocus="javascript:selectptype(this, 'KeyWordLeft')" value="${psname}" readonly>
			</td>
			<td align="right">产品：</td>
            <td ><input name="ProductID" type="hidden" id="ProductID" value="${ProductID}">
              <input id="ProductName" name="ProductName" value="${ProductName}" readonly><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a>
            </td>
            <td align="right">显示辅助单位：</td>
            <td ><input name="isFZfunitid" type="checkbox" id="isFZfunitid" value="1" <c:if test="${isFZfunitid!=null}">checked="checked"</c:if> >
            </td>
			<td></td>
          </tr>
          
          
          
          
          <tr class="table-back">
          		  	<td align="right">库存状态：</td>
                <td>
            	<select name="sstruts" id="sstruts">
            		<c:if test="${sstruts == 0}">
            			<option value="0" selected>安全</option>
            			<option value="-1">-请选择-</option>
	            		<option value="1">预警</option>
	            		<option value="2">过期</option>
            		</c:if>
            		<c:if test="${sstruts == 1}">
            			<option value="1" selected>预警</option>
            			<option value="-1">-请选择-</option>
	            		<option value="0">安全</option>
	            		<option value="2">过期</option>
            		</c:if>
            		<c:if test="${sstruts == 2}">
            			<option value="2" selected>过期</option>
            			<option value="-1">-请选择-</option>
	            		<option value="0">安全</option>
	            		<option value="1">预警</option>
            		</c:if>
            		<c:if test="${sstruts == -1 || sstruts == null}">
            			<option value="-1" selected>-请选择-</option>
	            		<option value="0">安全</option>
	            		<option value="1">预警</option>
	            		<option value="2">过期</option>
            		</c:if>
            	</select>
			</td>

			<td align="right">自定义排序：</td>
            <td ><input name="orderbySql" type="hidden" id="orderbySql" value="${orderbySql}">
              <input id="orderbySqlShowName" name="orderbySqlShowName" value="${orderbySqlShowName}" readonly><a href="javascript:SelectOrderColumn();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a></td>
			<td align="right">关键字：</td>
            <td ><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}" maxlength="60"></td>
            <td></td>
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
		    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		    <td width="80">
			<a href="javascript:updateRemark();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;添加备注</a>
		    </td>
            <td class="SeparatePage">
			<pages:pager action="../report/protectDateByProductStockpileAllAction.do"/>	
            </td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow: auto; height: 600px; width: 100%;">
      <table width="150%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">    
		  <td width="5%">仓库编号</td>
		  <td width="10%">仓库名称</td>
		  <td width="10%">产品类别</td>
		  <td width="7%">产品内部编号</td>
		  <td width="10%">产品名称</td>
		  <td width="5%">产品规格</td>
		  <td width="6%">生产日期</td>
		  <td width="4%">仓位</td>
          <td width="5%">基本单位</td>
          <td width="5%">实际库存</td>
          <c:if test="${isFZfunitid == '1'}">
        	<td width="5%">辅助单位(箱)</td>
        	<td width="5%">辅助单位(EA)</td>
       	  </c:if>
          <td width="5%">保质期(天)</td>
          <td width="5%">到期日期</td>
          <td width="5%">过期天数</td>
          <td width="6%">库龄状态</td>
          <td width="10%">备注</td>
          </tr>
	<c:forEach items="${wpsaList}" var="d">
        <tr class="table-back-colorbar" onClick="CheckedObj(this,'${d.psid}');">
       		<c:if test="${d.stockpilestruts == 0}">
        	  <td >${d.warehouseId}</td>
	          <td >${d.warehouseName}</td>
	          <td >${d.productStruct}</td>
	          <td >${d.productNccode}</td>
	          <td >${d.productName}</td>
	          <td >${d.productSpecmode}</td>
	          <td >${d.produceDate}</td>
	          <td align="center"><a href="#" onMouseOver="ShowWB(this,'${d.productId}','${d.warehouseId}','${d.stockpile}');"><img
												src="../images/CN/cang.gif" width="16" border="0"> </a></td>
	          <td>${d.unitidName}</td>
	          <td>${d.stockpile}</td>
	          <c:if test="${isFZfunitid == '1'}">
		          <td>${d.fuzhuBoxStockpile}</td>
		          <td>${d.fuzhuEAStockpile}</td>
	          </c:if>
	          <td>${d.productProtectDate}</td>
	          <td>${d.daoqiDate}</td>
	          <td>${d.daoqiDateByDay}</td>
	          <td>安全</td>
	          <td>${d.remark}</td>
        	</c:if>
        	<c:if test="${d.stockpilestruts == 1}">
        	  <td ><span style="color:#2F5E96;"><b>${d.warehouseId}</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>${d.warehouseName}</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>${d.productStruct}</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>${d.productNccode}</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>${d.productName}</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>${d.productSpecmode}</b></span></td>
	          <td ><span style="color:#2F5E96;"><b>${d.produceDate}</b></span></td>
	          <td align="center"><a href="#" onMouseOver="ShowWB(this,'${d.productId}','${d.warehouseId}','${d.stockpile}');"><img
												src="../images/CN/cang.gif" width="16" border="0"> </a></td>
	          <td><span style="color:#2F5E96;"><b>${d.unitidName}</b></span></td>
	          <td><span style="color:#2F5E96;"><b>${d.stockpile}</b></span></td>
	          
	          <c:if test="${isFZfunitid == '1'}">
		          <td><span style="color:#2F5E96;"><b>${d.fuzhuBoxStockpile}</b></span></td>
		          <td><span style="color:#2F5E96;"><b>${d.fuzhuEAStockpile}</b></span></td>
	          </c:if>
	          
	          <td><span style="color:#2F5E96;"><b>${d.productProtectDate}</b></span></td>
	          <td><span style="color:#2F5E96;"><b>${d.daoqiDate}</b></span></td>
	          <td><span style="color:#2F5E96;"><b>${d.daoqiDateByDay}</b></span></td>
	          <td><span style="color:#2F5E96;"><b>预警</b></span></td>
	          <td><span style="color:#2F5E96;"><b>${d.remark}</b></span></td>
        	</c:if>
        	<c:if test="${d.stockpilestruts == 2}">
        	  <td ><span style="color:#C00A0A;"><b>${d.warehouseId}</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>${d.warehouseName}</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>${d.productStruct}</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>${d.productNccode}</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>${d.productName}</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>${d.productSpecmode}</b></span></td>
	          <td ><span style="color:#C00A0A;"><b>${d.produceDate}</b></span></td>
	          <td align="center"><a href="#" onMouseOver="ShowWB(this,'${d.productId}','${d.warehouseId}','${d.stockpile}');"><img
												src="../images/CN/cang.gif" width="16" border="0"> </a></td>
	          <td><span style="color:#C00A0A;"><b>${d.unitidName}</b></span></td>
	          <td><span style="color:#C00A0A;"><b>${d.stockpile}</b></span></td>
	          
	          <c:if test="${isFZfunitid == '1'}">
		          <td><span style="color:#C00A0A;"><b>${d.fuzhuBoxStockpile}</b></span></td>
		          <td><span style="color:#C00A0A;"><b>${d.fuzhuEAStockpile}</b></span></td>
	          </c:if>
	          <td><span style="color:#C00A0A;"><b>${d.productProtectDate}</b></span></td>
	          <td><span style="color:#C00A0A;"><b>${d.daoqiDate}</b></span></td>
	          <td><span style="color:#C00A0A;"><b>${d.daoqiDateByDay}</b></span></td>
	          <td><span style="color:#C00A0A;"><b>过期</b></span></td>
	          <td><span style="color:#C00A0A;"><b>${d.remark}</b></span></td>
        	</c:if>
       </tr>
	</c:forEach> 
	</table>
      </div>
    </td>
  </tr>
</table>
<form  name="excputform" method="post" action="exportProtectDateByProductStockpileAllAction.do">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="ProductID" id ="ProductID" value="${ProductID}">
<input type="hidden" name="KeyWordLeft" id ="KeyWordLeft" value="${KeyWordLeft}">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName}">
<input type="hidden" name="WarehouseID" id ="WarehouseID" value="${WarehouseID}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
<input type="hidden" name="sstruts" id ="sstruts" value="${sstruts}">
<input type="hidden" name="isFZfunitid" id ="isFZfunitid" value="${isFZfunitid}">
</form>
</body>
</html>
