<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/producttype.js"> </SCRIPT>
<script language="JavaScript">
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}


	function CheckedObj(obj){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 	}
	
	function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.search.ProductID.value=p.id;
	document.search.ProductName.value=p.productname;
	}
	
	function ChkForm(){
		var productid = document.search.ProductName;

		if(productid.value==null||productid.value==""){
			alert("请选择产品");
			return false;
		}
		return true;
	}
	
	function ToBill(billcode){
		var bc=billcode.substring(0,2);
	 switch(bc){
		case "TT":popWin("../warehouse/takeTicketDetailAction.do?ID="+billcode,900,600); break;
		case "SO":popWin("../sales/saleOrderDetailAction.do?ID="+billcode,900,600); break;
		case "PD":popWin("../sales/peddleOrderDetailAction.do?ID="+billcode,900,600); break;
		case "OM":popWin("../warehouse/stockAlterMoveDetailAction.do?ID="+billcode,900,600); break;	
		case "PC":popWin("../warehouse/productInterconvertDetailAction.do?ID="+billcode,900,600); break;
		case "RT":popWin("../sales/retailDetailAction.do?ID="+billcode,900,600); break;
		case "SR":popWin("../sales/withdrawDetailAction.do?ID="+billcode,900,600); break;
		case "WD":popWin("../aftersale/withdrawDetailAction.do?ID="+billcode,900,600); break;
		case "CM":popWin("../sales/saleTradesDetailAction.do?ID="+billcode,900,600);break;
		case "ST":popWin("../aftersale/saleTradesDetailAction.do?ID="+billcode,900,600);break;
		case "PR":popWin("../purchase/purchaseWithdrawDetailAction.do?ID="+billcode,900,600);break;
		case "PM":popWin("../purchase/purchaseTradesDetailAction.do?ID="+billcode,900,600);break;
		case "PT":popWin("../aftersale/purchaseTradesDetailAction.do?ID="+billcode,900,600);break;
		case "SL":popWin("../warehouse/shipmentBillDetailAction.do?ID="+billcode,900,600);break;
		case "ML":popWin("../warehouse/stuffShipmentBillDetailAction.do?ID="+billcode,900,600); break;
		case "OL":popWin("../warehouse/otherShipmentBillDetailAction.do?ID="+billcode,900,600);break;
		case "PI":popWin("../warehouse/purchaseIncomeDetailAction.do?ID="+billcode,900,600); break;
		case "PE":popWin("../warehouse/productIncomeDetailAction.do?ID="+billcode,900,600); break;
		case "OI":popWin("../warehouse/otherIncomeDetailAction.do?ID="+billcode,900,600); break;
		case "TM":popWin("../warehouse/stockMoveDetailAction.do?ID="+billcode,900,600); break;
		case "PY":popWin("../warehouse/otherIncomeDetailAction.do?ID="+billcode,900,600); break;
		case "PK":popWin("../warehouse/otherShipmentBillDetailAction.do?ID="+billcode,900,600); break;
		case "DM":popWin("../ditch/detailSupplySaleMoveAction.do?ID="+billcode,900,600); break;
		case "SM":popWin("../warehouse/stockMoveDetailAction.do?ID="+billcode,900,600); break;
		case "OW":popWin("../ditch/detailOrganWithdrawAction.do?ID="+billcode,900,600); break;
		case "OT":popWin("../ditch/detailOrganTradesAction.do?ID="+billcode,900,600); break;
		default:popWin("../sales/retailDetailAction.do?ID="+billcode,900,600);
	 }
	}
	function excput(){
		//excputform.target="_blank";
		search.action="../warehouse/excPutSalesDailyAverageReportAction.do";
		search.submit();
		search.action="../warehouse/listSalesDailyAverageReportAction.do";
	}
	function print(){
		excputform.target="_blank";
		excputform.action="../warehouse/printStockWasteBookTotalAction.do";
		excputform.submit();
	}
	
	function SelectOrgan(){
	var p=showModalDialog("../common/selectWarehouseOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.MakeOrganID.value=p.id;
	document.search.oname.value=p.organname;
		document.search.WarehouseID.value="";
	document.search.wname.value="";
	}

	function SelectOrderColumn(){
		//popWin("../sys/toSelectOrderColumnAction.do?param="+$F('ordercolumn'),800,500);
		popWin("../sys/toSelectOrderColumnAction.do",650,450);
	}

	function setOrderColumn(sql,name){
		document.search.orderbySql.value=sql;
		document.search.orderbySqlShowName.value=name;
	}
	
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td>仓库管理>>日均销售报表</td>
        </tr>
      </table>
      <form name="search" method="post" action="listSalesDailyAverageReportAction.do" >
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	   
        <tr class="table-back">
        <td align="right">机构：</td>
            <td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>	</td>
           <td  align="right">仓库：</td>
            <td >
            <input type="hidden" name="WarehouseID" id="WarehouseID" value="${WarehouseID}">
<input type="text" name="wname" id="wname" onClick="selectDUW(this,'WarehouseID',$F('MakeOrganID'),'vw')" value="${wname}" readonly>	
			</td>
			
			
		   <td   align="right">产品类别：</td>
	          <td ><input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">	<input type="text" name="psname" id="psname" onFocus="javascript:selectptype(this, 'KeyWordLeft')" value="${psname}" readonly>	</td>
          <td colspan="8"></td>
		 </tr>
		 <tr class="table-back">
		  <td   align="right">自定义排序：</td>
            <td  ><input name="orderbySql" type="hidden" id="orderbySql" value="${orderbySql}">
              <input id="orderbySqlShowName" name="orderbySqlShowName" value="${orderbySqlShowName}" readonly><a href="javascript:SelectOrderColumn();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a></td>
<%--		  <td   align="right">产品类别：</td>--%>
<%--            <td ><input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">	<input type="text" name="psname" id="psname" onFocus="javascript:selectptype(this, 'KeyWordLeft')" value="${psname}" readonly>	</td>--%>
		 <td  align="right">产品：</td>
          <td ><input name="ProductID" type="hidden" id="ProductID" value="${ProductID}">
            <input id="ProductName" name="ProductName" value="${ProductName}" readonly><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a></td>
          <td  align="right">日期：</td>
          <td ><input id="BeginDate" value="${BeginDate}" onFocus="javascript:selectDate(this)" size="12" name="BeginDate" readonly>
-
  <input id="EndDate" onFocus="javascript:selectDate(this)" size="12" value="${EndDate}" name="EndDate" readonly></td>
   <td class="SeparatePage" colspan="4"><input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif" border="0" title="查询"></td>
  		<td></td>
        </tr>
         <tr class="table-back">
          <%--<td align="right">显示批次：</td>
            <td>
            <input name="isShowbatch" type="checkbox"  value="1"  <c:if test="${isShowbatch!=null}">checked="checked"</c:if> > 
            </td>--%>
			 
  		    
             </tr>
        
      </table>
      </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">	
		<td width="50">
								<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
							    </td>
							    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		<%--<td width="50"><a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16"border="0" align="absmiddle">&nbsp;打印</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>			
		  --%><td class="SeparatePage"><pages:pager action="../warehouse/listSalesDailyAverageReportAction.do"/></td>							
		</tr>
	   </table>
      </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 600px;" >
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top-lock">
            <td>仓库编号</td> 
            <td >仓库名称</td>
            <td>产品内部编码</td>
            <td>产品类别</td>
			<td>产品名称</td>
			
            <td >规格</td>
            <c:if test="${isshowBatch!=null}">
            <td >批次</td>
            </c:if>
			 <td>单位</td>
            <td width="6%" >出库数量</td>
            <td>日均销售量</td>
		  </tr>
		  
          <logic:iterate id="s" name="als" > 
          <tr class="table-back-colorbar"  onclick="CheckedObj(this);">
            <td >${s.warehouseid}</td> 
            <td >${s.warehourseidname}</td>
            <td >${s.barcode}</td>
            <td >${s.sortName }</td>
			<td >${s.psproductname}</td>
			
            <td >${s.psspecmode}</td>
             <c:if test="${isshowBatch!=null}">
            <td >${s.batch}</td>
            </c:if>
			<td >
			<windrp:getname key='CountUnit' value='${s.countunit}' p='d'/>
			</td>
            <td ><windrp:format value='${s.stockpile}'/></td>
            <td><windrp:format value='${s.salesAvg }'/></td>
		    </tr>
			
			</logic:iterate>           
      </table>
      <table width="100%" cellspacing="1"  class="totalsumTable">
        <tr class="back-gray-light">
         
          <td width="8%"  align="right">日均总销售量：</td>
		 	<td></td>
          <td width="6%"><windrp:format value='${avg}'/></td>
        </tr>
      </table>
	   </div> 
      </td>
  </tr>
</table>
<form  name="excputform" method="post" action="printStockWasteBookAction.do">
<input type="hidden" name="WarehouseID" id ="WarehouseID" value="${WarehouseID}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="ProductID" id ="ProductID" value="${ProductID}">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName}">
<input type="hidden" name="oname" id ="oname" value="${oname}">
</form>

</body>
</html>
