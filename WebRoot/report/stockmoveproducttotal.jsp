<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
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
			document.searchform.OutWarehouseID.value="";
			document.searchform.mname.value="";
	}
	function SelectOrgan2(){
			var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.searchform.InOrganID.value=p.id;
				document.searchform.oname2.value=p.organname;
				document.searchform.InWarehouseID.value="";
				document.searchform.wname.value="";
		}
function excput(){
	excputform.submit();
}
function print(){
	excputform.target="_blank";
	excputform.action="../report/printStockMoveProductTotalAction.do";
	excputform.submit();
	excputform.target="";
	excputform.action="../report/excPutStockMoveProductTotalAction.do";
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
          <td width="772">报表分析>>渠道>>转仓按产品汇总</td>
        </tr>
      </table>
       <form name="searchform" method="post" action="stockMoveProductTotalAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="8%"  align="right">转出机构：</td>
            <td width="23%">
            <input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
			<input name="oname" type="text" id="oname" size="30" value="${oname}"
				readonly><a href="javascript:SelectOrgan();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a>
            </td>
            <td  align="right">转出仓库：</td>
            <td><input type="hidden" name="OutWarehouseID" id="OutWarehouseID" value="${OutWarehouseID}">
				<input type="text" name="mname" id="mname" 
				onClick="selectDUW(this,'OutWarehouseID',$F('MakeOrganID'),'w')" 
				value="${mname}" readonly>
            </td>
            <td  align="right">制单日期：</td>
            <td ><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}"  onFocus="selectDate(this);"  size="10">
-
  <input name="EndDate" type="text" id="EndDate" value="${EndDate}"  onFocus="selectDate(this);"  size="10"></td>
   <td class="SeparatePage"></td>
          </tr>
          <tr class="table-back">
            <td align="right">转入机构：</td>
            <td><input name="InOrganID" type="hidden" id="InOrganID">
			<input name="oname2" type="text" id="oname2" size="30" value="${oname2}"
				readonly><a href="javascript:SelectOrgan2();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a></td>
            <td align="right">转入仓库：</td>
            <td><input type="hidden" name="InWarehouseID" id="InWarehouseID" value="${InWarehouseID}">
				<input type="text" name="wname" id="wname" 
				onClick="selectDUW(this,'InWarehouseID',$F('InOrganID'),'w')" 
				value="${wname}" readonly>
            </td>
            <td align="right">产品名称：</td>
            <td><input name="ProductID" type="hidden" id="ProductID" value="${ProductID}">
              <input id="ProductName" name="ProductName" value="${ProductName}"><a 
              href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" width="18" height="18" align="absmiddle" border="0"></a>
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
            <td class="SeparatePage">
			<pages:pager action="../report/stockMoveProductTotalAction.do"/>	
            </td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">
          <td width="15%" >转出机构</td>
          <td width="17%" >产品编号</td>
          <td width="27%" >产品名称</td>
          <td width="20%" >规格</td>
          <td width="7%">单位</td>
          <td width="10%" >箱数</td>
          <td width="10%" >散数</td>
        </tr>
		  <c:set var="totalboxnum" value="0"/>
		<c:set var="totalscatternum" value="0"/>
		<c:forEach items="${list}" var="d">
        <tr class="table-back-colorbar">
          <td ><windrp:getname key='organ' value='${d.makeorganid}' p='d'/></td>
          <td >${d.productid}</td>
          <td>${d.productname}</td>
          <td>${d.specmode}</td>
          <td><windrp:getname key="CountUnit" p="d" value="${d.unitid}"/></td>
          <td align="right"><windrp:format p="###,##0" value="${d.boxnum}" /></td>
          <td align="right"><windrp:format p="###,##0" value="${d.scatternum}" /></td>
        </tr>
        <c:set var="totalboxnum" value="${totalboxnum+d.boxnum}"></c:set>
        <c:set var="totalscatternum" value="${totalscatternum+d.scatternum}"></c:set>
		</c:forEach> 
	</table>
	<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="12%">本页合计：</td>
          <td align="right"><windrp:format p="###,##0" value='${totalboxnum}' />&nbsp;箱&nbsp; <windrp:format p="###,##0" value='${totalscatternum}' />&nbsp;散&nbsp; </td>
        </tr>
		<tr class="back-gray-light">
          <td align="right">总合计：</td>
          <td align="right"><windrp:format p="###,##0" value='${alltotalboxnum}' />&nbsp;箱&nbsp; <windrp:format p="###,##0" value='${alltotalscatternum}' />&nbsp;散&nbsp; </td>
        </tr>
      </table>
	  </div>
	 
    </td>
  </tr>
</table>

<form  name="excputform" method="post" action="excPutStockMoveProductTotalAction.do">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="OutWarehouseID" id ="OutWarehouseID" value="${OutWarehouseID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="InOrganID" id ="MakeID" value="${InOrganID}">
<input type="hidden" name="InWarehouseID" id ="OutWarehouseID" value="${InWarehouseID}">
</form>
</body>
</html>
