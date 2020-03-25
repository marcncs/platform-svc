<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../js/prototype.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
	function Audit(soid){
			window.open("../sales/auditVocationOrderAction.do?SOID="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(soid){
			window.open("../sales/cancelAuditVocationOrderAction.do?SOID="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function Endcase(soid){
			window.open("../sales/endcaseVocationOrderAction.do?id="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelEndcase(soid){
			window.open("../sales/cancelEndcaseVocationOrderAction.do?id="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function Audittwo(soid){
			window.open("../sales/audittwoSaleOrderAction.do?SOID="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudittwo(soid){
			window.open("../sales/cancelAudittwoSaleOrderAction.do?SOID="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function SaleOrder(soid){
			window.open("../sales/saleOrderAction.do?ID="+soid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function blankout(soid){
		if(window.confirm("您确认要作废该记录吗？如果作废将不能恢复!")){
			window.open("../sales/blankoutSaleOrderAction.do?id="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
	}
	
	
	function ShowHD(productid) {   // yy xx
var cid = $F('cid');
	$("hd").style.visibility = "visible" ;
	$("hd").style.top = event.clientY;;
	$("hd").style.left = event.clientX;
	//$("require").removeChild($("require").getElementsByTagName("table")[0]);
	if(cid!=""){
	getHistoryPrice(cid,productid);
	}
}
function HiddenHD(){
	hd.style.visibility = "hidden";
	/*
	for(var b=0;b<require.rows.length;b++){
		 	document.getElementById('require').deleteRow(b);
		 	b=b-1;
		 	}
	*/ 
	$("historyprice").removeChild($("historyprice").getElementsByTagName("table")[0]);
}

function getHistoryPrice(cid,productid){
	   var url = "../sales/getHistoryChenAjax.do?cid="+cid+"&productid="+productid;
	   //document.getElementById("require").style.display="";
	   //document.getElementById("require").innerHTML="姝ｅ湪璇诲彇鏁版嵁...";
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showHistory}
                    );
}

function showHistory(originalRequest){
	
	var product = originalRequest.responseXML.getElementsByTagName("product");
	//var x=document.all("require");//.insertRow(desk.rows.length);
	//var strcontent="";
	//alert(proot.length);
	var requireHTML = '<table id="historyprice" width="100%"  border="0" cellpadding="3" cellspacing="0">';

		for(var i=0;i<product.length;i++){
			var rm = product[i];
			var hprice = rm.getElementsByTagName("hprice")[0].firstChild.data;
			var hdate =rm.getElementsByTagName("hdate")[0].firstChild.data;
			//var murl =rm.getElementsByTagName("murl")[0].firstChild.data;
			//alert(ispd);
			requireHTML  += "<tr><td width='50%'>"+hprice+"</td><td width='50%'>"+hdate+" </td></tr>";
			//addRows(productname,productcount,stockcount);
		}

		$("historyprice").innerHTML = requireHTML + "</table>";

		//document.getElementById("result").style.display="none";
	}
	
	function ShowSQ(productid) {   // yy xx

	$("sq").style.visibility = "visible" ;
	$("sq").style.top = event.clientY;;
	$("sq").style.left = event.clientX;
	//$("require").removeChild($("require").getElementsByTagName("table")[0]);
	getStockQuantity(productid);
}
function HiddenSQ(){
	sq.style.visibility = "hidden";
	/*
	for(var b=0;b<require.rows.length;b++){
		 	document.getElementById('require').deleteRow(b);
		 	b=b-1;
		 	}
	*/ 
	$("stock").removeChild($("stock").getElementsByTagName("table")[0]);
}

function getStockQuantity(productid){
	   var url = "../warehouse/getStockQuantityAjax.do?productid="+productid;
	   //document.getElementById("require").style.display="";
	   //document.getElementById("require").innerHTML="姝ｅ湪璇诲彇鏁版嵁...";
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showStock}
                    );
}

function showStock(originalRequest){
	
	var product = originalRequest.responseXML.getElementsByTagName("product");
	var requireHTML = '<table id="stock" width="100%"  border="0" cellpadding="3" cellspacing="0">';

		for(var i=0;i<product.length;i++){
			var rm = product[i];
			var wh = rm.getElementsByTagName("wh")[0].firstChild.data;
			var qt =rm.getElementsByTagName("qt")[0].firstChild.data;
			var bc = rm.getElementsByTagName("bc")[0].firstChild.data;
			//var murl =rm.getElementsByTagName("murl")[0].firstChild.data;
			//alert(ispd);
			requireHTML  += "<tr><td width='40%'>"+wh+"</td><td width='30%'>"+bc+" </td><td width='30%' align='right'>"+qt+" </td></tr>";
			//addRows(productname,productcount,stockcount);
		}

		$("stock").innerHTML = requireHTML + "</table>";

		//document.getElementById("result").style.display="none";
	}
	
	function Prints(id){
			window.open("printVocationOrderAction.do?ID="+id,"","height=650,width=1000,top=150,left=150,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
</script>
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
#sq {
	position:absolute;
	left:0px;
	top:0px;
	width:200px;
	height:auto;
	z-index:1;
	visibility:hidden;
}
-->
</style>
</head>
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

<div id="sq">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
      <td width='40%' height="32" class="title-back"> 仓库</td>
	  <td width='25%' class="title-back">批次</td>
      <td width='35%' class="title-back">可用数量</td>
    </tr>
    <tr>
      <td colspan="3">
       <div id="stock">	
        	
      </div> 	
	  </td>
    </tr>
  </table>
</div>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="958"> 行业零售单详情 </td>
          <td width="275" align="right"><table width="240"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:Prints('${sof.id}');">打印</a></td>
              <td width="60" align="center"><c:choose><c:when test="${sof.isaudit==0}"><a href="javascript:Audit('${sof.id}');">复核</a></c:when><c:otherwise>
<a href="javascript:CancelAudit('${sof.id}')">取消复核</a></c:otherwise>
</c:choose></td>
			 <td width="60" align="center"><c:choose><c:when test="${sof.isendcase==0}">结案</c:when><c:otherwise>
取消结案</c:otherwise>
</c:choose></td>
              <td width="60" align="center"><c:choose><c:when test="${sof.isblankout==0}">作废</c:when><c:otherwise>
<font color="#FF0000">已作废</font></c:otherwise>
</c:choose></td>
            </tr>
          </table></td>
  </tr>
</table>
<fieldset align="center"> <legend>
      <table width="70" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>订货人信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right"> 会员编号： 
	  	  <input name="cid" type="hidden" id="cid" value="${sof.cid}"></td>
          <td width="28%">${sof.cid}</td>
          <td width="12%" align="right">会员名称：</td>
          <td width="20%">${sof.cname}</td>
	      <td width="10%" align="right">订货人：</td>
	      <td width="21%">${sof.decideman}</td>
	  </tr>
	  <tr>
	    <td  align="right">会员手机：</td>
	    <td>${sof.cmobile}</td>
	    <td align="right">会员电话：</td>
	    <td>${sof.decidemantel}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
</fieldset>


<fieldset align="center"> <legend>
      <table width="60" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>收货人信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">	  	  收货人：</td>
          <td width="28%">${sof.receiveman}</td>
          <td width="12%" align="right">收货人手机：</td>
          <td width="20%">${sof.receivemobile}</td>
	      <td width="10%" align="right">收货人电话：</td>
	      <td width="21%">${sof.receivetel}</td>
	  </tr>
	  <tr>
	    <td  align="right">收货地址：</td>
	    <td colspan="5">${sof.transportaddr}</td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>其它信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">	  	  交货日期：</td>
          <td width="28%">${sof.consignmentdate}</td>
          <td width="12%" align="right">发运方式：</td>
          <td width="21%">${sof.transportmodename}</td>
	      <td width="9%" align="right">付款方式：</td>
	      <td width="21%">${sof.paymentmodename}</td>
	  </tr>
	  <tr>
	    <td  align="right">开票信息：</td>
	    <td><c:if test="${sof.invmsg>0}"><font color="red"></c:if>${sof.invmsgname}</td>
	    <td align="right">发票抬头：</td>
	    <td>${sof.tickettitle}</td>
	    <td align="right">客户方单据编号：</td>
	    <td>${sof.customerbillid}</td>
	    </tr>
	  <tr>
	    <td  align="right">送货机构：</td>
	    <td>${sof.equiporganidname}</td>
	    <td align="right">制单机构：</td>
	    <td>${sof.makeorganidname}</td>
		<td align="right">客户来源：</td>
	    <td>${sof.sourcename}</td>
	    </tr>
	  <tr>
	    <td  align="right">零售部门：</td>
	    <td>${sof.saledeptname}</td>
	    <td align="right">零售人员：</td>
	    <td>${sof.saleidname}</td>
	    <td align="right">总金额：</td>
	    <td>${sof.totalsum}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">   ${sof.remark}</td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>状态信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">制单人：</td>
          <td width="29%">${sof.makeidname}</td>
          <td width="11%" align="right">制单日期：</td>
          <td width="21%">${sof.makedate}</td>
	      <td width="9%" align="right">修改人：</td>
	      <td width="21%">${sof.updateidname}</td>
	  </tr>
	  <tr>
	    <td  align="right">修改日期：</td>
	    <td>${sof.lastupdate}</td>
	    <td align="right">是否复核：</td>
	    <td>${sof.isauditname}</td>
	    <td align="right">复核日期：</td>
	    <td>${sof.auditdate}</td>
	    </tr>
	  <tr>
	    <td  align="right">复核人：</td>
	    <td>${sof.auditidname}</td>
	    <td align="right">是否结案：</td>
	    <td>${sof.isendcasename}</td>
	    <td align="right">结案日期：</td>
	    <td>${sof.endcasedate}</td>
	    </tr>
	  <tr>
	    <td  align="right">结案人：</td>
	    <td>${sof.endcaseidname}</td>
	    <td align="right">是否作废：</td>
	    <td>${sof.isblankoutname}</td>
	    <td align="right">作废日期：</td>
	    <td>${sof.blankoutdate}</td>
	    </tr>
	  <tr>
	    <td  align="right">作废人：</td>
	    <td>${sof.blankoutidname}</td>
	    <td align="right">作废原因：</td>
	    <td colspan="3">${sof.blankoutreason}</td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="100" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>零售单产品列表</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="8%">产品编号</td> 
          <td width="15%" >产品名称</td>
          <td width="18%">规格</td>
          <td width="7%">单位</td>
          <td width="12%">出货仓库</td>
		<!--  <td width="8%">批次</td>-->
		  <td width="3%">单价</td>
          <td width="5%">税后单价</td>
          <td width="5%">数量</td>
		  <td width="6%">检货数量</td>
		  <td width="4%">折扣率</td>
		  <td width="4%">税率</td>
          <td width="6%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.unitidname}</td>
          <td>${p.warehouseidname}</td>
		  <!-- <td></td>-->
		  <td align="right"><fmt:formatNumber value="${p.unitprice}" pattern="0.00"/></td>
          <td align="center"><c:if test="${p.unitprice!=p.taxunitprice}"><font color="red"></c:if><fmt:formatNumber value="${p.taxunitprice}" pattern="0.00"/></td>
          <td align="center"><fmt:formatNumber value="${p.quantity}" pattern="0"/></td>
		  <td align="center"><fmt:formatNumber value="${p.takequantity}" pattern="0"/></td>
		  <td align="right"><fmt:formatNumber value="${p.discount}" pattern="0.00"/>%</td>
		  <td align="right"><fmt:formatNumber value="${p.taxrate}" pattern="0.0"/>%</td>
          <td align="right"><fmt:formatNumber value="${p.subsum}" pattern="0.00"/></td>
         </tr>
        </logic:iterate> 
      </table>
</fieldset>

</td>
  </tr>
</table>
</body>
</html>
