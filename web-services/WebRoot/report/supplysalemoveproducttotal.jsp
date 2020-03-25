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
	searchform.action="../report/excPutSupplySaleMoveProductTotalAction.do";
	searchform.submit();
}
function oncheck(){
	searchform.target="";
	searchform.action="../report/supplySaleMoveProductTotalAction.do";
	searchform.submit();
}
function print(){
	searchform.target="_blank";
	searchform.action="../report/printSupplySaleMoveProductTotalAction.do";
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
          <td>报表分析>>渠道>>代销按产品汇总</td>
        </tr>
      </table>
       <form name="searchform" method="post" action="supplySaleMoveProductTotalAction.do" onSubmit="return oncheck();">
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
            <td width="10%" align="right">申请机构：</td>
            <td width="22%"><input name="SupplyOrganID" type="hidden" id="SupplyOrganID" value="${SupplyOrganID}">
              <input name="SupplyOrganIDName" type="text" id="SupplyOrganIDName" value="${SupplyOrganIDName}" size="30" 
				readonly><a href="javascript:SelectOrgan3();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle"></a></td>
            <td width="7%"  align="right">调入机构：</td>
            <td width="23%"><input name="InOrganID" type="hidden" id="InOrganID" value="${InOrganID}">
              <input name="InOrganIDName" type="text" id="InOrganIDName" value="${InOrganIDName}" size="30" 
				readonly><a href="javascript:SelectOrgan2();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle"></a></td>
  			
          </tr>
          <tr class="table-back">
            <td align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" onFocus="selectDate(this);" 
            value="${BeginDate}" size="10" readonly="readonly">
              -
              <input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);" 
  			value="${EndDate}" size="10" readonly="readonly"></td>
            <td align="right"></td>
            <td></td>
            <td align="right">&nbsp;</td>
         
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
		<td class="SeparatePage"><pages:pager action="../report/supplySaleMoveProductTotalAction.do"/></td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">
          <td width="9%">制单机构</td>
		<td width="9%">产品编号</td>
		<td width="15%">产品名称</td>
		<td width="15%">规格</td>
		<td width="7%">单位</td>
		<td width="9%">数量</td>
		<td width="14%">订购金额小计</td>
		<td width="13%">销售金额小计</td>
        </tr>
		<c:set var="totalqt" value="0"/>
		<c:set var="totalpsum" value="0"/>
		<c:set var="totalssum" value="0"/>
		<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar">
        <td><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>
        <td>${p.productid }</td>
		<td>${p.productname }</td>
		<td>${p.specmode }</td>
		<td><windrp:getname key="CountUnit" p="d" value="${p.unitid }"/></td>
		<td align="right"><windrp:format p="###,##0.00" value="${p.quantity}" /></td>
		<td align="right">￥<windrp:format p="###,##0.00" value='${p.psubsum}' /></td>
		<td align="right">￥<windrp:format p="###,##0.00" value='${p.ssubsum}' /></td>
        </tr>
        <c:set var="totalqt" value="${totalqt+p.quantity}"/>
		<c:set var="totalpsum" value="${totalpsum+p.psubsum}"/>
        <c:set var="totalssum" value="${totalssum+p.ssubsum}"/>
		</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="10%"> 本页合计：</td>
          <td width="61%" align="right" ><windrp:format p="###,##0.00" value='${totalqt}' /></td>
          <td width="15%" align="right">￥<windrp:format p="###,##0.00" value='${totalpsum}' /></td>  
		  <td width="14%" align="right">￥<windrp:format p="###,##0.00" value='${totalssum}' /></td>      
        </tr>
        
        <c:forEach items="${allsum}" var="s">
		<tr class="back-gray-light">
          <td  align="right">总合计：</td>
          <td align="right"><windrp:format p="###,##0.00" value='${s.quantity}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${s.psubsum}' /></td>  
		  <td align="right">￥<windrp:format p="###,##0.00" value='${s.ssubsum}' /></td>
        </tr>
        </c:forEach>
      </table>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
