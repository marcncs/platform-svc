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
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.searchform.MakeOrganID.value=p.id;
			document.searchform.oname.value=p.organname;
	}
	function SelectOrgan2(){
			var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.searchform.POrganID.value=p.id;
				document.searchform.oname2.value=p.organname;
		}
function excput(){
	searchform.target="_blank";
	searchform.action="../report/excPutOrganTradesBillTotalAction.do";
	searchform.submit();
	searchform.target="";
	searchform.action="../report/organTradesBillTotalAction.do";
}
function print(){
	searchform.target="_blank";
	searchform.action="../report/printOrganTradesBillTotalAction.do";
	searchform.submit();
	searchform.target="";
	searchform.action="../report/organTradesBillTotalAction.do";
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
          <td width="772">报表分析>>渠道>>渠道换货按单据汇总</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="organTradesBillTotalAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td  align="right">换货机构：</td>
            <td >
            <input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
			<input name="oname" type="text" id="oname" size="30" value="${oname}"
				readonly><a href="javascript:SelectOrgan();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a>
            </td>
    <td align="right">供货机构：</td>
            <td><input name="POrganID" type="hidden" id="POrganID" value="${PorganID}">
			<input name="oname2" type="text" id="oname2" size="30"  value="${oname2}"
				readonly><a href="javascript:SelectOrgan2();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a></td>
            <td align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}"  onFocus="selectDate(this);"  size="10">
				-
				  <input name="EndDate" type="text" id="EndDate" value="${EndDate}"  onFocus="selectDate(this);"  size="10"></td>
   			
          </tr>
          <tr class="table-back">
        
			<td align="right">单据号：</td>
            <td ><input type="text" name="OTID" value="${OTID}"></td>
            <td align="right">&nbsp;</td>
            <td >&nbsp;</td>
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
		    <td width="50">
			<a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a>
		    </td>
		    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
            <td class="SeparatePage">
			<pages:pager action="../report/organTradesBillTotalAction.do"/>	
            </td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">     
		  <td width="14%">单据号</td>
		  <td width="10%">供货机构编号</td>
          <td width="20%">供货机构名称</td>
          <td width="10%">换货机构编号</td>
          <td width="20%">换货机构名称</td>
		  <td width="18%">制单时间</td>
          <td width="8%">数量</td>
        </tr>
         <c:set var="totalqt" value="0"/>
	<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar"> 
       	   <td><a href="javascript:ToBill('${p.id}');">${p.id}</a></td>        
        	<td>${p.porganid}</td>    
           <td><windrp:getname key="organ" p="d" value="${p.porganid}"/></td>	
           <td>${p.makeorganid}</td>  
		   <td><windrp:getname key="organ" p="d" value="${p.makeorganid}"/></td>
		   <td><windrp:dateformat value="${p.makedate}" p="yyyy-MM-dd hh:mm:ss"/>  </td>
          <td align="right"><windrp:format p="###,##0.00" value="${p.quantity}" /></td>
        </tr>
        <c:set var="totalqt" value="${totalqt+p.quantity}"></c:set>
	</c:forEach> 
		</table>
	<table width="100%" cellspacing="1" class="totalsumTable">
        <tr  align="center" class="back-gray-light">
          <td align="right" width="14%"> 本页合计：</td>
          <td align="right"><windrp:format p="###,##0.00" value='${totalqt}' /></td>
        </tr>
		<tr  align="center" class="back-gray-light">
          <td align="right">总合计：</td>
          <td align="right"><windrp:format p="###,##0.00" value='${allqt}' /></td>
        </tr>
      </table>
      
      </div>
    </td>
  </tr>
</table>

<form  name="excputform" method="post" action="excPutOrganTradesBillTotalAction.do">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="POrganID" id ="POrganID" value="${POrganID}">
<input type="hidden" name="OTID" id ="ID" value="${OTID}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
</body>
</html>
