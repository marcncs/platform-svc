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
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/showSQ.js"> </SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
		for(i=0; i<obj.parentNode.childNodes.length; i++)
		{
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
		}
	 
	 obj.className="event";
	 checkid=objid;
	}
	
	
	function ShowHD(productid) {   // yy xx
	var cid = $F('cid');
	$("hd").style.visibility = "visible" ;
	$("hd").style.top = event.clientY;;
	$("hd").style.left = event.clientX;
	if(cid!=""){
	getHistoryPrice(cid,productid);
	}
	}
	
	function HiddenHD(){
	hd.style.visibility = "hidden";
	$("historyprice").removeChild($("historyprice").getElementsByTagName("table")[0]);
	}

	function getHistoryPrice(cid,productid){
	   var url = "../sales/getHistoryChenAjax.do?cid="+cid+"&productid="+productid;
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showHistory}
                    );
	}

	function showHistory(originalRequest){
	
	var product = originalRequest.responseXML.getElementsByTagName("product");
	var requireHTML = '<table id="historyprice" width="100%"  border="0" cellpadding="3" cellspacing="0">';

		for(var i=0;i<product.length;i++){
			var rm = product[i];
			var hprice = rm.getElementsByTagName("hprice")[0].firstChild.data;
			var hdate =rm.getElementsByTagName("hdate")[0].firstChild.data;
			requireHTML  += "<tr><td width='50%'>"+formatCurrency(hprice)+"</td><td width='50%'>"+hdate+" </td></tr>";
		}

		$("historyprice").innerHTML = requireHTML + "</table>";
	}


	
	
	var allorgprice=0;
	function ShowTS(productid,orgprice) {   // yy xx

	$("ts").style.visibility = "visible" ;
	$("ts").style.top = event.clientY;;
	$("ts").style.left = event.clientX;
	//$("require").removeChild($("require").getElementsByTagName("table")[0]);
	allorgprice = orgprice; 
	getTSPrice(productid);
	}
	
	function HiddenTS(){
	ts.style.visibility = "hidden";

	//$('tsprice').removeChild($('tsprice').getElementsByTagName("table")[0]);
	}

	function getTSPrice(productid){
	   var url = "../sales/ajaxGetInvMsgAction.do?invmsg=2";
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: OperatorPrice}
                    );
	}

	function OperatorPrice(originalRequest){
	var ivrate = originalRequest.responseTEXT; 
	
	var requireHTML = '<table width="100%"  border="0" cellpadding="3" cellspacing="0">';
	
	var tprice= formatCurrency(parseInt(allorgprice) + parseInt(allorgprice) * ivrate /100); 
			requireHTML  += "<tr><td>"+tprice+" </td></tr>";
		$("tsprice").innerHTML = requireHTML + "</table>";
	}
	
			
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		document.search.equiporganid.value=p.id;
		document.search.oname.value=p.organname;
	}

	this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}
	
	function excput(){
		search.target="";
		search.action="../sales/excPutFindPriceAction.do";
		search.submit();
		search.action="../sales/findPriceAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../sales/printFindPriceAction.do";
		search.submit();
		search.target="";
		search.action="../sales/findPriceAction.do";
	}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<style type="text/css">
<!--
#hd {
	position:absolute;
	left:0px;
	top:0px;
	width:200px;
	height:auto;
	z-index:1;
	visibility:hidden;
}
#ts {
	position:absolute;
	left:0px;
	top:0px;
	width:100px;
	height:auto;
	z-index:1;
	visibility:hidden;
}
-->
</style>

<div id="hd">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
      <td width="50%" height="32" class="title-back">历史成交价</td>
      <td width="50%" class="title-back">成交价日期</td>
    </tr>
    <tr>
      <td colspan="2">
       <div id="historyprice">	
        	
      </div> 	
	  </td>
    </tr>
  </table>
</div>


<div id="ts">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
      <td class="title-back">特殊票价</td>
    </tr>
    <tr>
      <td>
       <div id="tsprice">	
        	 
      </div> 	
	  </td>
    </tr>
  </table>
</div>

<body>


<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772"> 零售管理>>查价 </td>
  </tr>
</table>
 <form name="search" method="post" action="findPriceAction.do">
    <table width="100%" height="40"  border="0" cellpadding="0" cellspacing="0">
     
        <tr class="table-back">
          <td width="13%" align="right">产品类别：</td>
          <td width="20%"><input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">			
			<windrp:pstree id="KeyWordLeft" name="productstruts" value="${productstruts}"/></td>
          <td width="12%"  align="right">价格范围：</td>
          <td width="20%"><input name="BeginPrice" type="text" id="BeginPrice" size="8" value="${BeginPrice}">
            -
            <input name="EndPrice" type="text" id="EndPrice" size="8" value="${EndPrice}"></td>
          <td width="11%" align="right">送货机构：</td>
          <td width="21%"><input name="equiporganid" type="hidden" id="equiporganid" value="${equiporganid}">
            <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"> </a></td>
          <td width="3%">&nbsp;</td>
        </tr>
        <tr class="table-back">
          <td align="right">关键字：</td>
          <td><input type="text" name="KeyWord" value="${KeyWord}"></td>
          <td  align="right">&nbsp;</td>
          <td>&nbsp;</td>
          <td align="right">&nbsp;</td>
          <td>&nbsp;</td>
          <td class="SeparatePage">
            <input name="Submit2" type="image" id="Submit" 
					src="../images/CN/search.gif"  title="查询">
          </td>
        </tr>
    
    </table>
      </form>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr class="title-func-back">
      <td width="50"><a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>
	    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		<td width="51"><a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a></td>
		<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
        <td class="SeparatePage"><pages:pager action="../sales/findPriceAction.do" />
        </td>
      </tr>
    </table>
	</div>

<div id="listdiv" style="overflow-y: auto; height: 600px;">
    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">

    <tr align="center" class="title-top">
      <td width="12%" >产品编号</td>
      <td>产品名称</td>
	  <td width="4%">相关</td>
	  <td >规格</td>
      <td width="4%">单位</td>
      <td width="14%">非会员价</td>
      <td width="14%">会员价</td>
      </tr>

      <logic:iterate id="p" name="sls" >
        <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}');">
          <td >${p.id}</td>
          <td>${p.productname}</td>
		  <td><a href="#" onMouseOver="ShowSQ(this,'${p.id}');" onMouseOut="HiddenSQ();"><img src="../images/CN/stock.gif" width="16"  border="0"></a><a href="#" onMouseOver="ShowHD('${p.id}');" onMouseOut="HiddenHD();"></a></td>
		  <td>${p.specmode}</td>
          <td>${p.countunitname}</td>
          <td align="right"><fmt:formatNumber value="${p.pricei}" pattern="0.00"/>&nbsp;&nbsp;<a href="#" onMouseOver="ShowTS('${p.id}',${p.pricei});" onMouseOut="HiddenTS();">特殊票价</a></td>
          <td align="right"><fmt:formatNumber value="${p.priceii}" pattern="0.00"/>&nbsp;&nbsp;<a href="#" onMouseOver="ShowTS('${p.id}',${p.priceii});" onMouseOut="HiddenTS();">特殊票价</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
          </tr>
      </logic:iterate>
</table>
</div>
    </td>
  </tr>
</table>

</body>
</html>
