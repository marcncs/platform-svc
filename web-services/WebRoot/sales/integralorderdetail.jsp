<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
	function Audit(soid){
			window.open("../sales/auditIntegralOrderAction.do?SOID="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");		
	}
	
	function CancelAudit(soid){
			window.open("../sales/cancelAuditIntegralOrderAction.do?SOID="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function Endcase(soid){
			window.open("../sales/endcaseSaleOrderAction.do?id="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelEndcase(soid){
			window.open("../sales/cancelEndcaseSaleOrderAction.do?id="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="958"> 积分换购单详情 </td>
          <td width="275" align="right"><table width="180"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <!--<td width="60" align="center"><a href="javascript:SaleOrder('${sof.id}');">打印</a></td>-->
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right"> 会员编号： 
	  	  <input name="cid" type="hidden" id="cid" value="${sof.cid}"></td>
          <td width="26%">${sof.cid}</td>
          <td width="10%" align="right">会员名称：</td>
          <td width="25%">${sof.cname}</td>
	      <td width="9%" align="right">订货人：</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">	  	  收货人：</td>
          <td width="26%">${sof.receiveman}</td>
          <td width="10%" align="right">收货人手机：</td>
          <td width="25%">${sof.receivemobile}</td>
	      <td width="9%" align="right">收货人电话：</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">	  	  交货日期：</td>
          <td width="26%">${sof.consignmentdate}</td>
          <td width="10%" align="right">发运方式：</td>
          <td width="25%"><windrp:getname key='TransportMode' value='${sof.transportmode}' p='d'/></td>
	      <td width="9%" align="right">总积分：</td>
	      <td width="21%">${sof.integralsum}</td>
	  </tr>
	  <tr>
	    <td  align="right">送货机构：</td>
	    <td><windrp:getname key='organ' value='${sof.equiporganid}' p='d'/></td>
	    <td align="right">制单机构：</td>
	    <td><windrp:getname key='organ' value='${sof.makeorganid}' p='d'/></td>
		<td align="right">客户来源：</td>
	    <td><windrp:getname key='Source' value='${sof.source}' p='d'/></td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${sof.remark}</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">制单部门：</td>
          <td width="26%"><windrp:getname key='dept' value='${sof.makedeptid}' p='d'/></td>
          <td width="10%" align="right">制单人：</td>
          <td width="25%"><windrp:getname key='users' value='${sof.makeid}' p='d'/></td>
	      <td width="9%" align="right">制单日期：</td>
	      <td width="21%">${sof.makedate}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否复核：</td>
	    <td><windrp:getname key='YesOrNo' value='${sof.isaudit}' p='f'/></td>
	    <td align="right">复核人：</td>
	    <td><windrp:getname key='users' value='${sof.auditid}' p='d'/></td>
	    <td align="right">复核日期：</td>
	    <td>${sof.auditdate}</td>
	    </tr>
	  <tr>
	    <td  align="right">是否结案：</td>
	    <td><windrp:getname key='YesOrNo' value='${sof.isendcase}' p='f'/></td>
	    <td align="right">结案人：</td>
	    <td><windrp:getname key='users' value='${sof.endcaseid}' p='d'/></td>
	    <td align="right">结案日期：</td>
	    <td>${sof.endcasedate}</td>
	    </tr>
	  <tr>
	    <td  align="right">是否作废：</td>
	    <td><windrp:getname key='YesOrNo' value='${sof.isblankout}' p='f'/></td>
	    <td align="right">作废人：</td>
	    <td><windrp:getname key='users' value='${sof.blankoutid}' p='d'/></td>
	    <td align="right">作废日期：</td>
	    <td>${sof.blankoutdate}</td>
	    </tr>
	  <tr>
	    <td  align="right">作废原因：</td>
	    <td colspan="5">${sof.blankoutreason}</td>
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
	<table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="13%">产品编号</td> 
          <td width="17%" >产品名称</td>
          <td width="20%">规格</td>
          <td width="9%">单位</td>
          <td width="10%">出货仓库</td>
		  <td width="8%">积分</td>
          <td width="7%">数量</td>
		  <td width="8%">检货数量</td>
          <td width="8%">总积分</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
          <td><windrp:getname key='warehouse' value='${p.warehouseid}' p='d'/></td>
		  <td align="right"><fmt:formatNumber value="${p.integralprice}" pattern="0.00"/></td>
          <td align="center"><fmt:formatNumber value="${p.quantity}" pattern="0"/></td>
		  <td align="center"><fmt:formatNumber value="${p.takequantity}" pattern="0"/></td>
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
