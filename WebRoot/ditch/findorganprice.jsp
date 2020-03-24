<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script language="javascript" src="../js/sorttable.js"></script>
<SCRIPT language="javascript" src="../js/showSQ.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/producttype.js"> </SCRIPT>

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
	function CheckedObj(obj){
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	}
			
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		document.search.OrganID.value=p.id;
		document.search.oname.value=p.organname;
	}
	function excput(){
		search.target="";
		search.action="../ditch/excPutFindOrganPriceAction.do";
		search.submit();
		search.action="../ditch/findOrganPriceAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../ditch/printFindOrganPriceAction.do";
		search.submit();
		search.target="";
		search.action="../ditch/findOrganPriceAction.do";
	}

	this.onload = function abc(){
		document.getElementById("listdiv").style.height = (document.body.offsetHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
	}
	
</script>

</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td>渠道管理>>查价 </td>
  </tr>
</table>
<form name="search" method="post" action="findOrganPriceAction.do">
    <table width="100%" height="40"  border="0" cellpadding="0" cellspacing="0">
      
        <tr class="table-back">
          <td width="13%" align="right">产品类别：</td>
          <td width="20%"><input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">	
          <input type="text" name="psname" id="psname" onFocus="javascript:selectptype(this, 'KeyWordLeft')" value="${psname}" readonly></td>
          <td width="12%"  align="right">价格范围：</td>
          <td width="20%"><input name="BeginPrice" type="text" id="BeginPrice" size="8" value="${BeginPrice}">
            -
            <input name="EndPrice" type="text" id="EndPrice" size="8" value="${EndPrice}"></td>
          <td width="11%" align="right">机构：</td>
          <td width="21%"><input name="OrganID" type="hidden" id="OrganID" value="${OrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}"
									readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"> </a></td>
          <td width="3%">&nbsp;</td>
        </tr>
        <tr class="table-back">
          <td align="right">关键字：</td>
          <td><input type="text" name="KeyWord" value="${KeyWord}"></td>
          <td  align="right">&nbsp;</td>
          <td>&nbsp;</td>
          <td align="right">&nbsp;</td>
          <td>&nbsp;</td>
          <td><span class="SeparatePage">
            <input name="Submit2" type="image" id="Submit" 
					src="../images/CN/search.gif"  title="查询">
          </span></td>
        </tr>
     
    </table>
     </form>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr class="title-func-back">
		<td width="50"><a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>
	    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		<td width="51"><a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a></td>
		<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
        <td style="text-align: right;"><pages:pager action="../ditch/findOrganPriceAction.do" /></td>
      </tr>
    </table>
	</div>
	<div id="listdiv" style="overflow-y: auto; height: 600px;">
    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
    <tr align="center" class="title-top-lock">
      <td>产品编号</td>
      <td>产品名称</td>
      <td>机构</td>
	  <td>相关</td>
	  <td>单位</td>
      <td>规格</td>
      <td>价格</td>
      </tr>
      <logic:iterate id="p" name="list" >
        <tr class="table-back-colorbar" onClick="CheckedObj(this)">
	        <td >${p.id}</td>
	        <td>${p.productname}</td>
	        <td><windrp:getname key="organ" p="d" value="${p.organid }"/>   </td>
			<td><a href="#" onMouseOver="ShowSQ(this,'${p.id}');" onMouseOut="HiddenSQ();"><img src="../images/CN/stock.gif" width="16"  border="0"></a></td>
			<td><windrp:getname key="CountUnit" value="${p.unitid}" p="d"/></td>
	        <td>${p.specmode}</td>
	        <td align="right"><fmt:formatNumber value="${p.unitprice}" pattern="0.00"/></td>
        </tr>
      </logic:iterate>
	</table>
	</div>
    </td>
  </tr>
</table>
</body>
</html>
