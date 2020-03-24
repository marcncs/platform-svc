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
<script src="../js/prototype.js"></script>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	}


	function toPurchasePlan(){
		if(checkid>0){
		location.href("controlSaleLogDetailAction.do?ID="+checkid);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function toShipmentPlan(){
		if(checkid>0){
			window.open("flowToShipmentPlanAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function completeControl(){
		if(checkid>0){
			window.open("completeControlAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}

</script>

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
#ws {
	position:absolute;
	left:0px;
	top:0px;
	width:300px;
	height:auto;
	z-index:1;
	visibility:hidden;
}
-->
</style>
</head>
<script language="javascript">
function ShowWS(slid) {   // yy xx
	$("ws").style.visibility = "visible" ;
	$("ws").style.top = event.clientY;;
	$("ws").style.left = event.clientX;
	//$("require").removeChild($("require").getElementsByTagName("table")[0]);
	getCurrentRequire(slid);
}
function HiddenWS(){
	ws.style.visibility = "hidden";
	/*
	for(var b=0;b<require.rows.length;b++){
		 	document.getElementById('require').deleteRow(b);
		 	b=b-1;
		 	}
	*/ 
	$("require").removeChild($("require").getElementsByTagName("table")[0]);
}

function getCurrentRequire(slid){
	   var url = "../self/getCurrentRequireAjax.do?slid="+slid;
	   //document.getElementById("require").style.display="";
	   //document.getElementById("require").innerHTML="正在读取数据...";
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse}
                    );
}

function showResponse(originalRequest){
	
	var product = originalRequest.responseXML.getElementsByTagName("product");
	//var x=document.all("require");//.insertRow(desk.rows.length);
	//var strcontent="";
	//alert(proot.length);
	var requireHTML = '<table id="require" width="100%"  border="0" cellpadding="3" cellspacing="0">';

		
		
		for(var i=0;i<product.length;i++){
			var rm = product[i];
			var productname = rm.getElementsByTagName("productname")[0].firstChild.data;
			var productcount =rm.getElementsByTagName("productcount")[0].firstChild.data;
			var stockcount = rm.getElementsByTagName("stockcount")[0].firstChild.data;
			//var murl =rm.getElementsByTagName("murl")[0].firstChild.data;
			//alert(ispd);
			requireHTML  += "<tr><td width='80%'>"+productname+"</td><td width='20%'>"+productcount+"/"+stockcount+(productcount>stockcount?" <img src='../images/CN/bell.gif' style='vertical-align:middle'>":"")+"</td></tr>";
			//addRows(productname,productcount,stockcount);
		}

		$("require").innerHTML = requireHTML + "</table>";

		//document.getElementById("result").style.display="none";
	}
	//(productcount>stockcount?"<img src='../images/CN/bell.gif'>":"")+
function addRows(pname,pcount,scount){
	
	//alert("aaa");
		
		
		
		var x;
		x=document.all("require").insertRow(require.rows.length);

		var a=x.insertCell(0);
    	var b=x.insertCell(1);
   	//	var c=x.insertCell(2);
		a.innerHTML=pname; 
		a.width="80%"
		b.width="20%";
		//a.bgColor=b.bgColor="#ffffcc";
		b.innerHTML="&nbsp;"+pcount+" / "+scount+ (pcount>scount?" <img src='../images/CN/bell.gif'>":"");
		//if(pcount>scount){
		//c.innerHTML="<img src='../images/CN/bell.gif'>";
		//}
		//c.innerHTML=roleid;
	}
</script>

<body>

<div id="ws">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
      <td width="62%" height="32" class="title-back"> 产品名称：</td>
      <td width="38%" class="title-back">需求量/现有库存</td>
    </tr>
    <tr>
      <td colspan="2">
       <div id="require">	
        	
      </div> 	
	  <!--<table id="require" width="100%"  border="0" cellpadding="3" cellspacing="0">

      </table>	  --></td>
    </tr>
  </table>
</div>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772">产品计划</td>
  </tr>
</table>
<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="59" >订单编号</td>
            <td width="347">客户名</td>
            <td width="143">客户方订单号</td>
			<td width="113">库存状态</td>
            <td width="139">单据创建日期</td>
            <td width="174">交货日期</td>
            <td width="260">是否审阅</td>
          </tr>
		  <logic:iterate id="sl" name="alsl" >
		 
          <tr align="center" class="table-back" onClick="CheckedObj(this,${sl.id});"> 
            <td ><a href="../sales/saleLogDetailAction.do?ID=${sl.id}">${sl.id}</a></td>
            <td>${sl.cname}</td>
            <td>${sl.customerbillid}</td>
			<td><a href="#" onMouseOver="ShowWS(${sl.id});" onMouseOut="HiddenWS();">查看</a></td>
            <td>${sl.createdate}</td>
            <td>${sl.consignmentdate}</td>
            <td>${sl.approvename}</td>
            </tr>
          </logic:iterate>
       
      </table>
       </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table width="309"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="80"><a href="javascript:toShipmentPlan();">生成出库计划</a></td>
                <td width="80"><a href="javascript:toPurchasePlan();">生成采购计划</a></td>
                <td width="80"><a href="javascript:completeControl();">隐藏</a></td>
                <!--<td width="60">移交</td>
                <td width="60">共享</td>-->
              </tr>
            </table></td>
    <td width="52%" align="right"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/self/toFlowControlAction.do"/>	
            </td>
          </tr>
       
      </table></td>
  </tr>
</table>
</td>
</tr>
</table>
</body>	
</html>
