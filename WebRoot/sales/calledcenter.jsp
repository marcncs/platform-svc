<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html>

<head>
<title>来电信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT LANGUAGE=javascript src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">

function DealCalled(){
	document.all.submsg.src="../sales/dealCallCenterAction.do?cid=${customer.cid}";
}
function Suit(){
	document.all.submsg.src="../sales/listSuitAllAction.do?CID=${customer.cid}";
}
function Repository(){
	document.all.submsg.src="../assistant/listRepositoryTypeAction.do";
}
function CallCenterEvent(){
	document.all.submsg.src="../sales/listCallCenterEventAction.do?CID=${customer.cid}";
}
function SaleOrder(){
	document.all.submsg.src="../sales/listSaleOrderAction.do?CID=${customer.cid}";
}
function CIntegral(){
	document.all.submsg.src="../sales/listObjIntegralAction.do?ID=${customer.cid}&OSort=1";
}


this.onload =function onLoadDivHeight(){
			document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
		}
</script>
</head>
<html:errors/>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="bodydiv">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
              <td width="772"> 呼叫中心</td>
            </tr>
        </table>
        <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>来电信息</td>
        </tr>
      </table>
	  </legend>
	  <form name="cusinfo" method="post">
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  
	  <tr>
	  	<td width="13%"  align="right">来电客户编号：</td>
          <td width="17%"><input type="hidden" name="cid" value="${customer.cid}">${customer.cid}</td>
          <td width="13%" align="right">来电客户：</td>
          <td width="20%">${customer.cname}</td>
	      <td width="12%" align="right">来电号码：</td>
	      <td width="25%"><input type="hidden" name="tel" value="${tel}">${tel}</td>
	  </tr>
	  <tr>
	  	<td width="13%"  align="right">Email：</td>
          <td width="17%">${customer.email}</td>
          <td width="13%" align="right">积分：</td>
          <td width="20%">${integral}</td>
	      <td width="12%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
	  </tr>
	 
	  </table>
	   </form>
</fieldset>
	<br>
    <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:DealCalled();"><span>业务处理</span></a></li>
            <li><a href="javascript:Suit();"><span>投诉与建议</span></a></li>
            <li><a href="javascript:CallCenterEvent();"><span>通话记录</span></a></li>
            <li><a href="javascript:SaleOrder();"><span>历史订单</span></a></li>
            <li><a href="javascript:CIntegral();"><span>个人积分</span></a></li>
          </ul>
        </div>        
      </div>	
      
      </div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 500px;">
                     <IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sales/dealCallCenterAction.do?cid=${customer.cid}" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME> 
                    </div>
		
		</td>
      </tr>
    </table>

</body>
</html>
