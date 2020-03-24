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
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectOrgan(){
		var p=showModalDialog("../common/selectVisitOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.searchform.MakeOrganID.value=p.id;
			document.searchform.MakeOrganIDName.value=p.organname;
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
			document.searchform.ReceiveOrganIDName.value=p.organname;
	}
function excput(){
	excputform.submit();
}
function print(){
	excputform.target="_blank";
	excputform.action="../report/printStockAlterMoveBillTotalAction.do";
	excputform.submit();
	excputform.target="";
	excputform.action="../report/excPutStockAlterMoveBillTotalAction.do";
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
          <td>报表分析>>渠道>>订购按单据汇总</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="stockAlterMoveBillTotalAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="10%"  align="right">制单机构：</td>
            <td width="25%">
            <input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
			<input name="MakeOrganIDName" type="text" id="MakeOrganIDName" size="30" value="${MakeOrganIDName}"
				readonly><a href="javascript:SelectOrgan();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a>
            </td>
            <td width="10%" align="right">订购机构：</td>
            <td width="25%">
            <input name="ReceiveOrganID" type="hidden" id="ReceiveOrganID" value="${ReceiveOrganID}">
			<input name="ReceiveOrganIDName" type="text" id="ReceiveOrganIDName" size="30" value="${ReceiveOrganIDName}"
				readonly><a href="javascript:SelectOrgan2();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a>
            </td>
            <td  align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}"  onFocus="selectDate(this);" 
             size="10" readonly="readonly">
				-
  			<input name="EndDate" type="text" id="EndDate" value="${EndDate}"  onFocus="selectDate(this);" 
  			 size="10" readonly="readonly"></td>
            <td><span class="SeparatePage">
              <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
            </span></td>
           
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
		<td class="SeparatePage"><pages:pager action="../report/stockAlterMoveBillTotalAction.do"/></td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">
			<td width="12%">单据编号</td>
			<td width="16%">订购时间 </td>
			<td width="12%">订购机构 </td>
			<td width="22%">制单机构 </td>
			<td width="22%">制单时间 </td>
<%--			<td width="12%">金额 </td>--%>
            <td width="12%">箱数量</td>
            <td width="12%">散数量 </td>
        </tr>
		<c:set var="totalboxnum" value="0"/>
		<c:set var="totalscatternum" value="0"/>
		<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar">
          <td ><a href="javascript:ToBill('${p.id}');">${p.id}</a></td>
          <td ><windrp:dateformat value="${p.movedate}" p="yyyy-MM-dd hh:mm:ss"/></td>
          <td >${p.receiveorganidname}</td>
          <td ><windrp:getname key="organ" p="d" value="${p.makeorganid}"/></td>
          <td ><windrp:dateformat  value="${p.makedate}" p="yyyy-MM-dd hh:mm:ss"/></td>
          <td align="right" ><windrp:format p="###,##0" value='${p.boxnum}' /></td>   
           <td align="right" ><windrp:format p="###,##0" value='${p.scatternum}' /></td>                 
        </tr>
        <c:set var="totalboxnum" value="${totalboxnum+p.boxnum}"></c:set>
        <c:set var="totalscatternum" value="${totalscatternum+p.scatternum}"></c:set>
		</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="12%"> 本页合计：</td>
          <td align="right"><windrp:format p="###,##0" value='${totalboxnum}' />&nbsp;箱&nbsp; <windrp:format p="###,##0" value='${totalscatternum}' />&nbsp;散&nbsp; 
         </td>
        </tr>
		<tr class="back-gray-light">
          <td  align="right">总合计：</td>
          <td align="right"><windrp:format p="###,##0" value='${alltoalboxnum}' />&nbsp;箱&nbsp; <windrp:format p="###,##0" value='${alltotalscatternum}' />&nbsp;散&nbsp; </td>
        </tr>
      </table>
      </div>
    </td>
  </tr>
</table>

<form  name="excputform" method="post" action="excPutStockAlterMoveBillTotalAction.do" >
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="ReceiveOrganID" id ="ReceiveOrganID" value="${ReceiveOrganID}">
<input type="hidden" name="MakeOrganIDName" id ="MakeOrganIDName" value="${MakeOrganIDName}">
<input type="hidden" name="ReceiveOrganIDName" id ="ReceiveOrganIDName" value="${ReceiveOrganIDName}">
<input type="hidden" name="ID" id ="ID" value="${ID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">

</form>
</body>
</html>
