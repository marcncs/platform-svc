<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/showbill.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:10.5cm;center:yes;status:no;scrolling:no;");
	document.searchform.ProvideID.value=p.pid;
	document.searchform.ProvideName.value=p.pname;
}
function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	document.searchform.ProductID.value=p.id;
	document.searchform.ProductName.value=p.productname;
	}
function SelectOrgan(){
	var p=showModalDialog("../common/selectVisitOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
		document.searchform.MakeOrganID.value=p.id;
		document.searchform.oname.value=p.organname;
		document.searchform.WarehouseID.value="";
		document.searchform.wname.value="";
		clearUser("MakeID","uname");
}
function excput(){
	searchform.target="";
	searchform.action="../report/excPutPurchaseIncomeDetailAction.do";
	searchform.submit();
}
function oncheck(){
	searchform.target="";
	searchform.action="../report/purchaseIncomeDetailAction.do";
	searchform.submit();
}
function print(){
	searchform.target="_blank";
	searchform.action="../report/printPurchaseIncomeDetailAction.do";
	searchform.submit();
	
}


this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
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
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">报表分析>>入库>>采购入库明细</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="purchaseIncomeDetailAction.do" onSubmit="return oncheck();">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
    		<tr class="table-back"> 
            <td align="right">制单机构：</td>
            <td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
			<input name="oname" type="text" id="oname" size="30" value="${oname}"
				readonly><a href="javascript:SelectOrgan();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a></td>
            <td align="right">制单人：</td>
            <td><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
				<input type="text" name="uname" id="uname" 
				onClick="selectDUW(this,'MakeID',$F('MakeOrganID'),'ou')" 
				value="${uname}" readonly>
            </td>
            <td align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" 
            onFocus="selectDate(this);"  size="10" readonly="readonly">
				-
				  <input name="EndDate" type="text" id="EndDate" value="${EndDate}" 
				  onFocus="selectDate(this);"  size="10" readonly="readonly"></td>
				  <td align="right">&nbsp;</td>
          </tr>
          <tr class="table-back">
            <td  align="right">供应商：</td>
            <td><input name="ProvideID" type="hidden" id="ProvideID" value="${ProvideID}">
              <input id="ProvideName" name="ProvideName"   value="${ProvideName}"
              readonly><a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" align="absmiddle" width="18" height="18" border="0"></a></td>
            <td align="right">入货仓库：</td>
            <td>
            <input type="hidden" name="WarehouseID" id="WarehouseID" value="${WarehouseID}">

			<input type="text" name="wname" id="wname" 

			onClick="selectDUW(this,'WarehouseID',$F('MakeOrganID'),'w')" 

			value="${wname}" readonly>
              </td>
            <td align="right">产品：</td>
            <td><input name="ProductID" type="hidden" id="ProductID" value="${ProductID}">
              <input id="ProductName" name="ProductName" value="${ProductName}"><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" align="absmiddle" width="18" height="18" border="0"></a>
              </td>
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
		<td width="50">
			<a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a>
		</td>
		<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		<td class="SeparatePage"><pages:pager action="../report/purchaseIncomeDetailAction.do"/></td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr class="title-top-lock" align="center">         
          <td>供应商</td>
		  <td>入货仓库</td>
		  <td>单据号</td>
		  <td>制单时间</td>
		  <td>产品编号</td>
		  <td>产品名称</td>
          <td>规格</td>
          <td>单位</td>
          <td>单价</td>
          <td width="8%" >数量</td>
          <td width="10%" >金额</td>
        </tr>
        
	<c:forEach items="${alsod}" var="d">
        <tr class="table-back-colorbar">           
		  <td>${d.oname}</td>
		  <td>${d.soname}</td>
		  <td><a href="javascript:ToBill('${d.billid}');">${d.billid}</a></td>
		    <td><windrp:dateformat value="${d.makedate}" p="yyyy-MM-dd hh:mm:ss"/></td>
		  <td>${d.productid}</td>
          <td>${d.productname}</td>
          <td>${d.specmode}</td>
          <td>${d.unitidname}</td>
          <td align="right">￥${d.unitprice}</td>
          <td align="right"> <windrp:format p="###,##0.00" value="${d.quantity}" /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value="${d.subsum}" /></td>
        </tr>
	</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="12%">本页合计：</td>
          <td align="right">${totalsum}</td>
        </tr>
		<tr class="back-gray-light">
          <td align="right">总合计：</td>
          <td align="right">${allsum}</td>
        </tr>
      </table>
	 </div>
    </td>
  </tr>
</table>

<form  name="excputform" method="post" action="excPutPurchaseIncomeDetailAction.do" >
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="ProvideID" id ="ProvideID" value="${ProvideID}">
<input type="hidden" name="ProductID" id ="ProductID" value="${ProductID}">
<input type="hidden" name="WarehouseID" id ="WarehouseID" value="${WarehouseID}">
</form>
</body>
</html>
