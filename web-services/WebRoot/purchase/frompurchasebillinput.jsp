<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>  
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
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
	
	this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}

</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择采购订单</td>
        </tr>
      </table>
      <form name="search" method="post" action="selectPurchaseBillAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="11%"  align="right">预计到货日期：</td>
            <td width="81%" >
              <input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)" readonly>
              - 
              <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)" readonly></td>
            <td width="8%" class="SeparatePage" >
              <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="120"><a href="javascript:Affirm();"><img src="../images/CN/import.gif" width="16" height="16" border="0" align="absmiddle"> 转为采购入库</a></td>
		  <td width="2"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="60"><a href="javascript:Hidden();"><img src="../images/CN/import.gif" width="16" height="16" border="0" align="absmiddle"> 隐藏</a></td>
          <td width="1087" align="right"><pages:pager action="../purchase/selectPurchaseBillAction.do"/></td>
        </tr>
      </table>
	  </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        
          <tr align="center" class="title-top"> 
            <td width="10%" >编号</td>
            <td width="27%">供应商</td>
            <td width="24%">预计到货日期</td>
            <td width="16%">结算方式</td>
			<td width="13%">制单人</td>
		  </tr>
          <logic:iterate id="p" name="alpa" > 
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}');"> 
            <td>${p.id}</td>
            <td>${p.pname}</td>
            <td>${p.receivedate}</td>
            <td><windrp:getname key='paymentmode' value='${p.paymode}' p='d'/></td>
			<td><windrp:getname key='users' value='${p.makeid}' p='d'/></td>
			</tr>
          </logic:iterate> 
       
      </table>
       </form>
	   </div>
      </td>
  </tr>
</table>
</body>
</html>
