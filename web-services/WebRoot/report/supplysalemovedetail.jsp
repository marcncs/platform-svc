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

	function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.searchform.ProductID.value=p.id;
	document.searchform.ProductName.value=p.productname;
	}
	function SelectOrgan(){
		var p=showModalDialog("../common/selectVisitOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.searchform.MakeOrganID.value=p.id;
			document.searchform.oname.value=p.organname;
	}
function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.searchform.InOrganID.value=p.id;
			document.searchform.InOrganIDName.value=p.organname;
	}
	
	function SelectOrgan3(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.searchform.SupplyOrganID.value=p.id;
			document.searchform.SupplyOrganIDName.value=p.organname;
	}
function excput(){
	searchform.target="";
	searchform.action="../report/excPutSupplySaleMoveDetailAction.do";
	searchform.submit();
}
function oncheck(){
	searchform.target="";
	searchform.action="../report/supplySaleMoveDetailAction.do";
	searchform.submit();
}
function print(){
	searchform.target="_blank";
	searchform.action="../report/printSupplySaleMoveDetailAction.do";
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
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="0"></td>
          <td >报表分析>>渠道>>代销明细</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="supplySaleMoveDetailAction.do" onSubmit="return oncheck();">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="10%"  align="right">制单机构：</td>
            <td width="20%">
            <input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
			<input name="oname" type="text" id="oname" value="${oname}" size="30"
				readonly><a href="javascript:SelectOrgan();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a>
            </td>
            <td width="9%" align="right">申请机构：</td>
            <td width="24%"><input name="SupplyOrganID" type="hidden" id="SupplyOrganID" value="${SupplyOrganID}">
              <input name="SupplyOrganIDName" type="text" id="SupplyOrganIDName" value="${SupplyOrganIDName}" size="30" 
				readonly><a href="javascript:SelectOrgan3();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle"></a></td>
             <td width="8%"  align="right">调入机构：</td>
            <td width="25%">
            <input name="InOrganID" type="hidden" id="InOrganID" value="${InOrganID}">
			<input name="InOrganIDName" type="text" id="InOrganIDName" value="${InOrganIDName}" size="30" 
				readonly><a href="javascript:SelectOrgan2();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a>
			</td>
			
          </tr>
          <tr class="table-back">
          <td  align="right">订购日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate"
             onFocus="selectDate(this);" value="${BeginDate}" size="10" readonly="readonly">
-
  <input name="EndDate" type="text" id="EndDate" 
  			onFocus="selectDate(this);" value="${EndDate}" size="10" readonly="readonly"></td>
            <td align="right">产品：</td>
            <td><input name="ProductID" type="hidden" id="ProductID" value="${ProductID}">
              <input id="ProductName" name="ProductName" value="${ProductName}"><a 
              href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" 
              width="18" height="18" align="absmiddle" border="0"></a></td>
  			 
           
            <td align="right"></td>
           
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
            <td class="SeparatePage">
			<pages:pager action="../report/supplySaleMoveDetailAction.do"/>	
            </td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">    
          <td width="9%">调入机构</td>
		  <td width="9%">制单机构</td>
		  <td width="7%">制单时间</td>
		  <td width="9%">申请机构</td>     
		  <td width="7%">单据号</td>
		  <td width="7%">产品编号</td>
		  <td width="9%">产品名称</td>
          <td width="8%">规格</td>
          <td width="4%">单位</td>
          <td width="5%">订购单价</td>
          <td width="4%">销售单价</td>
          <td width="4%">数量</td>
          <td width="6%">订购金额</td>
          <td width="6%">销售金额</td>
        </tr>
        <c:set var="totalqt" value="0"/>
        <c:set var="totalpsum" value="0"/>
        <c:set var="totalssum" value="0"/>
	<c:forEach items="${alsod}" var="d">
        <tr class="table-back-colorbar">           
		  <td  ><windrp:getname key='organ' value='${d.rname}' p='d'/></td>
		  <td  ><windrp:getname key='organ' value='${d.oname}' p='d'/></td>
		  <td><windrp:dateformat value="${d.makedate}" p="yyyy-MM-dd"/></td>
		  <td ><windrp:getname key='organ' value='${d.sname}' p='d'/></td>
		  <td ><a href="javascript:ToBill('${d.billid}');">${d.billid}</a></td>
		  <td  >${d.productid}</td>
          <td >${d.productname}</td>
          <td >${d.specmode}</td>
          <td >${d.unitidname}</td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${d.punitprice}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${d.sunitprice}' /></td>
          <td align="right"><windrp:format p="###,##0.00" value='${d.quantity}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${d.psubsum}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${d.ssubsum}' /></td>
        </tr>
        <c:set var="totalqt" value="${totalqt+d.quantity}"></c:set>
        <c:set var="totalpsum" value="${totalpsum+d.psubsum}"></c:set>
        <c:set var="totalssum" value="${totalssum+d.ssubsum}"></c:set>
	</c:forEach> 
	
	</table>
	<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="10%">本页合计：</td>
          <td width="78%" align="right"><windrp:format p="###,##0.00" value='${totalqt}'/></td>
          <td width="6%" align="right">￥<windrp:format p="###,##0.00" value='${totalpsum}'/></td>
          <td width="6%" align="right">￥<windrp:format p="###,##0.00" value='${totalssum}'/></td>
        </tr>
        <c:forEach items="${allsum}" var="a">
		<tr class="back-gray-light">
          <td align="right">总合计：</td>
          <td align="right"><windrp:format p="###,##0.00" value='${a.qt}'/></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${a.pss}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${a.sss}' /></td>
        </tr>
        </c:forEach>
      </table>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
