<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/showbill.js"></SCRIPT>
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectCustomer(){
		var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( c == undefined ){
			return;
		}
		document.searchform.CID.value=c.cid;
		document.searchform.CName.value=c.cname;
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
			document.searchform.EquipOrganID.value=p.id;
			document.searchform.oname2.value=p.organname;
	}
function excput(){
	excputform.submit();
}
function print(){
	excputform.target="_blank";
	excputform.action="../report/printCostProductDetailAction.do";
	excputform.submit();
	excputform.target="";
	excputform.action="../report/excPutCostProductDetailAction.do";
}

function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.searchform.ProductID.value=p.id;
	document.searchform.ProductName.value=p.productname;
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
          <td width="772">报表分析>>成本>>营业成本明细</td>
        </tr>
      </table>
        <form name="searchform" method="post" action="costProductDetailAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
      
          <tr class="table-back">
            <td width="10%"  align="right">制单机构：</td>
            <td width="25%">
            <input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
			<input name="oname" type="text" id="oname" size="30" value="${oname}"
				readonly><a href="javascript:SelectOrgan();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a>
            </td>
            <td width="10%" align="right">产品：</td>
            <td width="25%"><input name="ProductID" type="hidden" id="ProductID" value="${ProductID}">
              <input id="ProductName" name="ProductName" value="${ProductName}"><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" align="absmiddle" width="18" height="18" border="0"></a></td>
            <td  align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" onFocus="selectDate(this);" 
            value="${BeginDate}" size="10" readonly="readonly">
				-
  			<input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);" 
  			value="${EndDate}" size="10" readonly="readonly"></td>
            <td>&nbsp;</td>
           
          </tr>
          <tr class="table-back">
            <td align="right">单据类型：</td>
            <td><windrp:select key="BSort" name="BSort" p="y|f" value="${BSort}"/></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
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
		    <td width="50">
			<a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a>
		    </td>
		    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
            <td class="SeparatePage">
			<pages:pager action="../report/costProductDetailAction.do"/>	
            </td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">  
        <td width="5%">对象编号</td>        
          <td width="10%">对象名称</td>
		  <td width="10%">单据号</td>
		  <td width="7%">制单日期</td>
		  <td width="11%">制单机构</td>
		  <td width="10%">产品名称</td>
          <td width="7%">规格</td>
          <td width="7%">批次</td>
          <td width="3%">单位</td>
          <td width="4%">数量</td>
          <td width="6%">单价</td>
          <td width="7%">金额小计</td>
          <td width="7%">成本小计</td>
          <td width="6%">毛利小计</td>
        </tr>
        <c:set var="slcount" value="0"/>
        <c:set var="jecount" value="0"/>
        <c:set var="cbcount" value="0"/>
        <c:set var="mlcount" value="0"/>
		<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar">    
        <td >${p.oid}</td>      
		  <td >${p.oname}</td>
		  <td ><a href="javascript:ToBill('${p.billno}');">${p.billno}</a></td>
		  <td ><windrp:dateformat value='${p.makedate}' p='yyyy-MM-dd'/></td>
		  <td ><windrp:getname key="organ" p="d" value="${p.makeorganid}"/></td>
		  <td >${p.productname}</td>
          <td >${p.specmode}</td>
          <td >${p.batch}</td>
          <td ><windrp:getname key="CountUnit" p="d" value="${p.unitid}"/></td>
          <td align="right"><windrp:format p="###,##0.00" value='${p.quantity}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${p.unitprice}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${p.unitprice*p.quantity}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${p.cost*p.quantity}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${(p.unitprice*p.quantity)-(p.cost*p.quantity)}' /></td>
        </tr>
        <c:set var="slcount" value="${slcount+p.quantity}"/>
        <c:set var="jecount" value="${jecount+(p.unitprice*p.quantity)}"/>
        <c:set var="cbcount" value="${cbcount+(p.cost*p.quantity)}"/>
        <c:set var="mlcount" value="${jecount-cbcount}"/>
	</c:forEach> 
	</table>
	<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td width="9%" align="right">本页合计：</td>
          <td width="65%" align="right"><windrp:format p="###,##0.00" value='${slcount}' /></td>
          <td width="6%" align="right"></td>
          <td width="7%" align="right">￥<windrp:format p="###,##0.00" value='${jecount}' /></td>
          <td width="7%" align="right">￥<windrp:format p="###,##0.00" value='${cbcount}' /></td>
          <td width="6%" align="right">￥<windrp:format p="###,##0.00" value='${mlcount}' /></td>
        </tr>
        <c:forEach items="${sumobj}" var="s">
		<tr class="back-gray-light">
          <td  align="right">总合计：</td>
          <td align="right"><windrp:format p="###,##0.00" value='${s.allquantity}' /></td>
          <td align="right">&nbsp;</td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${s.allsubsum}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${s.allcost}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${s.allsubsum-s.allcost}' /></td>
        </tr>
        </c:forEach>
      </table>
      </div>
    </td>
  </tr>
</table>

<form  name="excputform" method="post" action="excPutCostProductDetailAction.do">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="EquipOrganID" id ="EquipOrganID" value="${EquipOrganID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="ProductID" id ="ProductID" value="${ProductID}">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName}">
<input type="hidden" name="BSort" id ="BSort" value="${BSort}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
</body>
</html>
