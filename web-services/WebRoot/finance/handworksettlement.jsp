<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	PaymentLog();
	}
	
	function SelectProvide(){
	showModalDialog("toSelectProvideAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	document.search.PID.value=getCookie("pid");
	document.search.ProvideName.value=getCookie("pname");
	}

</script>
</head>

<body>

<script language="javascript">
function getInvoice(){
		var pid = $F("PID");
		var begindate = $F("BeginDate");
		var enddate = $F("EndDate");
		//alert(pid);
	   var url = "../finance/selectPurchaseInvoiceAjax.do?PID="+pid+"&BeginDate="+begindate+"&EndDate="+enddate;
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse}
                    );
}

function showResponse(originalRequest){
	var invoice = originalRequest.responseXML.getElementsByTagName("invoice");

	var requireHTML = "<table width='100%' border='0' cellpadding='0' cellspacing='1'><tr align='center' class='title-top'><td width='8%' height='25'></td><td width='9%'>开票日期</td><td width='7%'>产品编号</td><td width='20%'>产品名称</td><td width='19%'>规格</td><td width='8%'>单位</td><td width='11%'>数量</td><td width='8%'>单价</td><td width='10%'>金额</td></tr>";

		for(var i=0;i<invoice.length;i++){
			var iv = invoice[i];
			var iid = iv.getElementsByTagName("iid")[0].firstChild.data;

			var idate =iv.getElementsByTagName("idate")[0].firstChild.data;
			var iprovideid =iv.getElementsByTagName("iprovideid")[0].firstChild.data;
			var iproductid =iv.getElementsByTagName("iproductid")[0].firstChild.data;
			var iproductname =iv.getElementsByTagName("iproductname")[0].firstChild.data;
			var ispecmode =iv.getElementsByTagName("ispecmode")[0].firstChild.data;
			var iquantity =iv.getElementsByTagName("iquantity")[0].firstChild.data;
			var iunitpirce = iv.getElementsByTagName("iunitpirce")[0].firstChild.data;
			var iunitidname = iv.getElementsByTagName("iunitidname")[0].firstChild.data;
			var isubsum = iv.getElementsByTagName("isubsum")[0].firstChild.data;

			requireHTML  += "<tr align='center' class='table-back'><td width='10%'><input type='checkbox' name='iid' value='"+iid+"' ></td><td width='10%'>"+idate+"</td><td width='10%'>"+iproductid+"</td><td width='10%'>"+iproductname+"</td><td width='10%'>"+ispecmode+"</td><td width='5%'>"+iunitidname+"</td><td width='10%'>"+iquantity+"</td><td width='10%'>"+iunitpirce+"</td><td width='10%'>"+isubsum+"</td></tr>";
			
		}

		$("ivlist").innerHTML = requireHTML +"</table>";

	}
	
	
	function Check(){
		if(document.search.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.search.length;i++){

			if (!document.search.elements[i].checked)
				if(search.elements[i].name != "checkall"){
				document.search.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.search.length;i++){
			if (document.search.elements[i].checked)
				if(search.elements[i].name != "checkall"){
				document.search.elements[i].checked=false;
				}
		}
	}
	
	function BSelectProvide(){
	showModalDialog("toSelectProvideAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	document.search.BPID.value=getCookie("pid");
	document.search.BProvideName.value=getCookie("pname");
	}
	
	function getBill(){
		var bpid = $F("BPID");
		var warehouseid = $F("WarehouseID");
		var obtaincode = $F("ObtainCode");
		var bbegindate = $F("BBeginDate");
		var benddate = $F("BEndDate");
		//alert(warehouseid);
	   var url = "../finance/selectPurchaseIncomeAjax.do?BPID="+bpid+"&WarehouseID="+warehouseid+"&ObtainCode="+obtaincode+"&BBeginDate="+bbegindate+"&BEndDate="+benddate;
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponseB}
                    );
}

function showResponseB(boriginalRequest){
	var bill = boriginalRequest.responseXML.getElementsByTagName("bill");

	var brequireHTML = " <table width='100%' border='0' cellpadding='0' cellspacing='1'><tr align='center' class='title-top'><td width='8%' height='25'></td><td width='10%'>入库日期</td><td width='14%'>仓库</td><td width='11%'>产品编号</td><td width='19%'>产品名称</td><td width='20%'>规格</td><td width='11%'>单位</td><td width='7%'>数量</td></tr>";

		for(var b=0;b<bill.length;b++){
		
			var ib = bill[b];
			var bid = ib.getElementsByTagName("bid")[0].firstChild.data;
			var bincomedate =ib.getElementsByTagName("bincomedate")[0].firstChild.data;
			var bwarehouse =ib.getElementsByTagName("bwarehouse")[0].firstChild.data;
			var bproductid =ib.getElementsByTagName("bproductid")[0].firstChild.data;
			var bproductname =ib.getElementsByTagName("bproductname")[0].firstChild.data;
			var bspecmode =ib.getElementsByTagName("bspecmode")[0].firstChild.data;
			var bunitidname = ib.getElementsByTagName("bunitidname")[0].firstChild.data;
			var bquantity =ib.getElementsByTagName("bquantity")[0].firstChild.data;

			brequireHTML  += "<tr align='center' class='table-back'><td width='10%'><input type='checkbox' name='bid' value='"+bid+"' ></td><td width='10%'>"+bincomedate+"</td><td width='10%'>"+bwarehouse+"</td><td width='10%'>"+bproductid+"</td><td width='10%'>"+bproductname+"</td><td width='10%'>"+bspecmode+"</td><td width='5%'>"+bunitidname+"</td><td width='10%'>"+bquantity+"</td></tr>";

		}

		$("iblist").innerHTML = brequireHTML +"</table>";

	}
	
	function BCheck(){
		if(document.search.bcheckall.checked){
			BcheckAll();
		}else{
			BuncheckAll();
		}
	}
	
	function BcheckAll(){
		for(i=0;i<document.search.length;i++){

			if (!document.search.elements[i].checked)
				if(search.elements[i].name != "bcheckall"){
				document.search.elements[i].checked=true;
				}
		}
	}

	function BuncheckAll(){
		for(i=0;i<document.search.length;i++){
			if (document.search.elements[i].checked)
				if(search.elements[i].name != "bcheckall"){
				document.search.elements[i].checked=false;
				}
		}
	}
	
	function ClearIV(){
		document.search.PID.value="";
		document.search.ProvideName.value="";
	}
	function ClearIB(){
		document.search.BPID.value="";
		document.search.BProvideName.value="";
	}
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
        
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">手工结算</td>
        </tr>
      </table>
	  <form name="search" method="post" action="affirmSettlementAction.do">
      <table width="100%" border="0">
        <tr>
          <td width="20"  class="title-back">&nbsp;</td>
          <td width="98%" class="title-back">选票</td>
        </tr>
      </table>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="0">

          <tr class="table-back"> 
            <td width="12%"  align="right">供应商：</td>
            <td width="38%"><input name="PID" type="hidden" id="PID">
              <input name="ProvideName" type="text" id="ProvideName" size="40">
            <a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a> <a href="javascript:ClearIV();">清空</a></td>
            <td width="11%" align="right">开票日期：</td>
            <td width="39%"><input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
-
  <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)">
  <input type="button" name="button" value="查询" onClick="getInvoice();"></td>
          </tr>

      </table>
	  <div id="ivlist">	
        	
      		</div>

      <table width="100%"  border="0">
        <tr>
          <td width="20"  class="title-back">&nbsp;</td>
          <td width="98%" class="title-back">选单</td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">

          <tr class="table-back">
            <td  align="right">供应商：</td>
            <td height="2"><input name="BPID" type="hidden" id="BPID">
              <input name="BProvideName" type="text" id="BProvideName" size="40">
            <a href="javascript:BSelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a> <a href="javascript:ClearIB();">清空</a></td>
            <td align="right">入货仓库：</td>
            <td><select name="WarehouseID" id="WarehouseID">
<option value="" selected>所有仓库</option>
                <logic:iterate id="w" name="alw" > 
                <option value="${w.id}">${w.warehousename}</option>
                </logic:iterate> 
              </select></td>
          </tr>
          <tr class="table-back">
            <td width="11%"  align="right">到货单号：</td>
            <td width="39%" height="1"><input type="text" name="ObtainCode"></td>
            <td width="11%" align="right">入库日期：</td>
            <td width="39%"><input name="BBeginDate" type="text" id="BBeginDate" size="12" onFocus="javascript:selectDate(this)">
-
  <input name="BEndDate" type="text" id="BEndDate" size="12" onFocus="javascript:selectDate(this)">
  <input type="button" name="button" value="查询" onClick="getBill();"></td>
          </tr>
       
      </table>
<div id="iblist">	
        	
      		</div>
 <!--     <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top">
            <td width="8%" height="35">编号</td>
            <td width="10%">入库日期</td>
            <td width="14%">仓库</td>
            <td width="11%">产品编号</td>
            <td width="19%">产品名称</td>
            <td width="20%">规格</td>
            <td width="11%">单位</td>
            <td width="7%">数量</td>
          </tr>


      </table>-->

      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="36%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><input name="submit" type="submit" value="确定"></td>
              </tr>
            </table> </td>
          <td width="64%" align="right"> 
          </td>
        </tr>
      </table>
       </form>
    </td>
  </tr>
          
</table>

</body>
</html>
