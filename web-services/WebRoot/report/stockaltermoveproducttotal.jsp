<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
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
			document.searchform.ReceiveOrganID.value="";
			document.searchform.ReceiveOrganIDName.value="";
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
	excputform.action="../report/printStockAlterMoveProductTotalAction.do";
	excputform.submit();
	excputform.target="";
	excputform.action="../report/excPutStockAlterMoveProductTotalAction.do";
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
          <td>报表分析>>渠道>>订购按产品汇总</td>
        </tr>
      </table>
       <form name="searchform" method="post" action="stockAlterMoveProductTotalAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="10%"  align="right">制单机构：</td>
            <td width="25%">
            <input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
			<input name="oname" type="text" id="oname" value="${oname}" size="30"
				readonly><a href="javascript:SelectOrgan();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a>
            </td>
            <td width="10%" align="right">订购机构：</td>
            <td width="25%">
            <input name="ReceiveOrganID" type="hidden" id="ReceiveOrganID" value="${ReceiveOrganID}">
			<input name="oname2" type="text" id="oname2" value="${oname2}" size="30" 
				readonly><a href="javascript:SelectOrgan2();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a>
            </td>
            <td  align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate"  onFocus="selectDate(this);" 
            value="${BeginDate}" size="10" readonly="readonly">-
  			<input name="EndDate" type="text" id="EndDate" value="${EndDate}"  onFocus="selectDate(this);" 
  			 size="10" readonly="readonly"></td>
  			<td align="right">&nbsp;</td>
          </tr>
          <tr class="table-back">
            <td align="right">产品编号：</td>
            <td><input name="ProductID" type="text" id="ProductID" value="${ProductID}"></td>
            <td align="right">产品名称：</td>
            <td><input name="ProductName" id="ProductName" value="${ProductName}"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="SeparatePage"> <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
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
		<td class="SeparatePage"><pages:pager action="../report/stockAlterMoveProductTotalAction.do"/></td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">
          <td width="10%">制单机构</td>
		<td width="10%">产品编号</td>
		<td width="17%">产品名称</td>
		<td width="17%">规格</td>
		<td width="8%">散装单位</td>
<%--		<td width="10%">单价</td>--%>
		<td width="14%">箱数量</td>
<%--		<td width="14%">金额</td>--%>
         <td width="14%">散装数量</td>
        </tr>
		<c:set var="totalqt" value="0"/>
		<c:set var="totalsum" value="0"/>
		<c:set var="totalcost" value="0"/>
		<c:set var="totalgain" value="0"/>
		<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar">
          <td><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>
        <td>${p.productid }</td>
		<td>${p.productname }</td>
		<td>${p.specmode }</td>
		<td><windrp:getname key="CountUnit" p="d" value="${p.unitid }"/></td>
<%--		<td align="right">￥<windrp:format p="###,##0.00" value='${p.unitprice}' /></td>--%>
		<td align="right"><windrp:format p="###,##0" value="${p.boxnum}" /></td>
		<td align="right"><windrp:format p="###,##0" value='${p.scatternum}' /></td>
        </tr>
        <c:set var="totalqt" value="${totalqt+p.boxnum}"/>
		<c:set var="totalsum" value="${totalsum+p.scatternum}"/>
		</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="12%"> 本页合计：</td>
          <td align="right" ><windrp:format p="###,##0" value='${totalqt}' />&nbsp;箱&nbsp;</td>  
		  <td width="14%" align="right"><windrp:format p="###,##0" value='${totalsum}' />&nbsp;散&nbsp;</td>      
        </tr>
		<tr class="back-gray-light">
          <td  align="right">总合计：</td>
          <td align="right"><windrp:format p="###,##0" value='${totalboxnum}' />&nbsp;箱&nbsp;</td>  
		  <td align="right"><windrp:format p="###,##0" value='${totalscatternum}' />&nbsp;散&nbsp;</td>
        </tr>
      </table>
      </div>
    </td>
  </tr>
</table>
<form  name="excputform" method="post" action="excPutStockAlterMoveProductTotalAction.do">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="ReceiveOrganID" id ="ReceiveOrganID" value="${ReceiveOrganID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="ProductID" id ="ProductID" value="${ProductID}">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
</body>
</html>
