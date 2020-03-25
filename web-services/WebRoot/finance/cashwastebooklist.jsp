<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
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
	
	this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

function excput(){
		search.target="";
		search.action="../finance/excPutListCashWasteBookAction.do";
		search.submit();
		search.action="listCashWasteBookAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../finance/printListCashWasteBookAction.do";
		search.submit();
		search.target="";
		search.action="listCashWasteBookAction.do";
	}

</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">账务管理>>资金台帐</td>
        </tr>
      </table>
      <form name="search" method="post" action="listCashWasteBookAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="9%"  align="right">
            现金银行：</td>
            <td width="24%"><select name="CBID"><option value="">选择</option>
				<c:forEach var="cb" items="${cblist}">
				<option value="${cb.id}" ${cb.id==CBID?"selected":""}>${cb.cbname}</option>
				</c:forEach>
			</select></td>
            <td width="9%" align="right">记录日期：</td>
            <td width="28%"><input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}" onFocus="javascript:selectDate(this)" readonly>
-
  <input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}" onFocus="javascript:selectDate(this)" readonly></td>
            <td width="6%" align="right">&nbsp;</td>
            <td class="SeparatePage">
              <input name="Submit2" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
            </td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
        <td width="50">
		<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" 
		width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
	    </td>
	    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		<td width="51">
			<a href="javascript:print();"><img
					src="../images/CN/print.gif" width="16" height="16"
					border="0" align="absmiddle">&nbsp;打印</a>
		</td>
		<td width="1">
			<img src="../images/CN/hline.gif" width="2" height="14">
		</td>
          <td class="SeparatePage"><pages:pager action="../finance/listCashWasteBookAction.do"/></td>
        </tr>
      </table>
	  </div>
					</td>
				</tr>
				<tr>
					<td>
					<div id="listdiv" style="overflow-y: auto; height: 650px;" >
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">

          <tr align="center" class="title-top"> 
            <td width="11%" >编号</td>
            <td width="13%">现金银行</td>
            <td width="13%">单据号</td>
            <td width="16%">摘要</td>
            <td width="9%">期初金额</td>
            <td width="8%">本期收入</td>
			<td width="9%">本期支出</td>
			<td width="11%">本期结存金额</td>
			<td width="10%">记录日期</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="arls" > 
          <tr class="table-back-colorbar"  onClick="CheckedObj(this,'${s.id}');"> 
            <td >${s.id}</td>
            <td>${s.cbidname}</td>
            <td>${s.billno}</td>
            <td>${s.memo}</td>
			<td align="right"><windrp:format value='${s.cyclefirstsum}'  p="###,##0.00"/></td>     
			<td align="right"><windrp:format value='${s.cycleinsum}'  p="###,##0.00"/></td>
			<td align="right"><windrp:format value='${s.cycleoutsum}'  p="###,##0.00"/></td>
			<td align="right"><windrp:format value='${s.cyclebalancesum}'  p="###,##0.00"/></td>
			<td>${s.recorddate}</td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
      </table>
	  </div>
    </td>
  </tr>
</table>

</body>
</html>
