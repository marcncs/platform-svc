<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
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
PaymentLogDetail();
	}
	
	function addNew(){
	window.open("../finance/toAddPaymentLogAction.do","","height=550,width=950,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdPaymentLogAction.do?ID="+checkid,"","height=550,width=950,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function PaymentLogDetail(){
		if(checkid!=""){
			document.all.submsg.src="../finance/paymentLogDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			window.open("../finance/toReferPaymentLogAction.do?PLID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
			if(window.confirm("您确认要删除编号为:"+checkid+"的记录吗？如果删除将永远不能恢复!")){
			window.open("../finance/delPaymentLogAction.do?PLID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}

</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">付款记录</td>
        </tr>
      </table>
       <form name="search" method="post" action="listPaymentLogByPayableObjectAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="11%"  align="right"><input name="POID" type="hidden" id="POID" value="${poid}">
            制单日期：</td>
            <td width="26%">
              <input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}" onFocus="javascript:selectDate(this)" readonly>
              - 
            <input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}" onFocus="javascript:selectDate(this)" readonly></td>
            <td width="8%" align="right">关键字：</td>
            <td width="16%"><input type="text" name="KeyWord" value="${KeyWord}"></td>
            <td width="13%">&nbsp;</td>
            <td width="13%">&nbsp;</td>
            <td width="13%" align="right"><input name="Submit2" type="image" id="Submit" src="../images/CN/search.gif" border="0"></td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:Del()"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a> </td>
          <td style="text-align: right;"><pages:pager action="../finance/listPaymentLogByPayableObjectAction.do"/></td>
        </tr>
      </table>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        
          <tr align="center" class="title-top"> 
            <td width="10%" >编号</td>
            <td width="33%">领款人</td>
            <td width="14%">付款方式</td>
            <td width="7%">付款金额</td>
            <td width="10%">制单人</td>
            <td width="10%">制单日期</td>
          </tr>
          <logic:iterate id="s" name="arls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');"> 
            <td >${s.id}</td>
            <td>${s.payee}</td>
            <td>${s.paymodename}</td>
			 <td align="right"><windrp:format value='${s.paysum}' p='###,##0.00'/></td>
            <td>${s.makeidname}</td>
            <td>${s.makedate}</td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
	<br> 
    <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:PaymentLogDetail();"><span>付款记录详情</span></a></li>
            </ul>
         </div>
         <div>
         <IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setParentIframeHeight(this,'submsg','listdiv');setIframeHeight(this)"></IFRAME>
         </div>
     </div>               
     </td>
  </tr>
</table>

</body>
</html>
