<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
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
	//PurchaseBillDetail();
	}

	function Affirm(){
	if(checkid!=""){
		location.href("../warehouse/toTransPurchanseIncomeAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function PurchaseBillDetail(){
		if(checkid!=""){
			document.all.submsg.src="purchaseBillDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Hidden(){
		if(checkid!=""){
			window.open("../purchase/hiddenPurchaseBillAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}

</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert('本页拒绝使用右键!');}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择采购订单</td>
        </tr>
      </table>
      <form name="search" method="post" action="selectPurchaseBillAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="11%"  align="right">预计到货日期：</td>
            <td width="89%" >
              <input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
              - 
              <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)"> 
<input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="8%" >编号</td>
            <td width="27%">供应商</td>
            <td width="24%">预计到货日期</td>
            <td width="16%">结算方式</td>
			<td width="13%">制单人</td>
		  </tr>
          <logic:iterate id="p" name="alpa" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,'${p.id}');"> 
            <td >${p.id}</td>
            <td>${p.providename}</td>
            <td>${p.receivedate}</td>
            <td>${p.paymodename}</td>
			<td>${p.makeidname}</td>
			</tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="33%">
			<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <!--<td width="60"><a href="javascript:history.back();">返回</a></td>-->
                <td width="81"><a href="javascript:Affirm();">转为采购入库</a></td>
                <td width="69"><a href="javascript:Hidden();">隐藏</a></td>
              </tr>
            </table></td>
          <td width="67%" align="right"> <presentation:pagination target="/purchase/selectPurchaseBillAction.do"/> 
          </td>
        </tr>
      </table>
      
     <!-- <table width="87"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="85"><a href="javascript:PurchaseBillDetail();">采购订单详情</a></td>
        </tr>
      </table> --></td>
  </tr>
</table><!--
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME> -->
</body>
</html>
