<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectCustomer(){
		var os = document.search.objectsort.value;
		if(os==0){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if ( o==undefined){return;}
		document.search.POID.value=o.id;
		document.search.payername.value=o.organname;
		}
		if(os==1){
		var m=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if ( m==undefined){return;}
		document.search.POID.value=m.cid;
		document.search.payername.value=m.cname;
		}
		if(os==2){
		var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		document.search.POID.value=p.pid;
		document.search.payername.value=p.pname;
		}
	}
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectVisitOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
	}
	

function excput(){
	search.target="";
	search.action="../report/excPutPayoutWasteAction.do";
	search.submit();
}
function oncheck(){
	search.target="";
	search.action="../report/payoutWasteAction.do";
	search.submit();
}
function print(){
	search.target="_blank";
	search.action="../report/printPayoutWasteAction.do";
	search.submit();
	
}

this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
	
	function ToBill(billcode){
		var bc=billcode.substring(0,2);
	 	switch(bc){
		case "PL":popWin("../finance/paymentLogDetailAction.do?ID="+billcode,900,600); break;
		case "20":popWin("../finance/payableDetailAction.do?ID="+billcode,900,600); break;
		}
	}
</script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="div1">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">报表分析>>账务>>支出明细</td>
        </tr>
      </table>
      <form name="search" method="post" action="payoutWasteAction.do" onSubmit="return oncheck();">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
		 <tr class="table-back">
            <td width="10%"  align="right">对象类型：</td>
            <td width="26%"><select name="objectsort" >
            <option value="1" ${1==objectsort?"selected":""}>客户</option>
            <option value="2" ${2==objectsort?"selected":""}>供应商</option>
            <option value="0" ${0==objectsort?"selected":""}>机构</option>
          	</select></td>
            <td width="6%" align="right">领款方：</td>
            <td width="27%"><input name="POID" type="hidden" id="POID" value="${POID}">
                <input name="payername" type="text" size="35" readonly value="${payername}"><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td width="7%" align="right">所属机构：</td>
            <td width="20%"><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
			<input name="oname" type="text" id="oname" size="30" value="${oname}"
				readonly><a href="javascript:SelectOrgan();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle"></a></td>
            <td width="4%">&nbsp;</td>
          </tr>
          <tr class="table-back">
            <td width="10%"  align="right">制单日期：</td>
            <td width="26%"><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" size="10" onFocus="javascript:selectDate(this)" readonly>
-  
  <input name="EndDate" type="text" id="EndDate" value="${EndDate}" size="10" onFocus="javascript:selectDate(this)" readonly></td>
            <td width="6%" align="right">&nbsp;</td>
            <td width="27%">&nbsp;</td>
            <td width="7%" align="right">&nbsp;</td>
            <td width="20%">&nbsp;</td>
            <td width="4%" align="right"><span class="SeparatePage">
              <input name="Submit2" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
            </span></td>
          </tr>
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
        <tr class="title-func-back">
          <td width="50"><a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td class="SeparatePage"><pages:pager action="../report/payoutWasteAction.do"/></td>
        </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
       <FORM METHOD="POST" name="listform" ACTION="">
           <tr align="center" class="title-top-lock"> 
           	<td width="11%"  >单据编号</td>
           	<td width="8%">收款方编号</td>
			<td width="14%">收款方</td>  
			<td width="13%">所属机构</td>                     
            <td width="7%">制单日期</td>
            <td width="9%">结算方式</td>
            <td width="17%">摘要</td>
            <td width="9%">应付款结算金额</td>           
			<td width="12%">付款金额</td> 
          </tr>
           <c:set var="payout" value="0"/>
		  <c:set var="pay" value="0"/>
		   <logic:iterate id="r" name="rls" > 
           <tr class="table-back-colorbar">
             <td  align="left"><a href="javascript:ToBill('${r.id}');">${r.id}</a></td>
             <td align="left">${r.poid}</td>
             <td align="left">${r.poidname}</td>
			 <td align="left"><windrp:getname key='organ' value='${r.makeorganid}' p='d'/></td>
             <td align="left"><windrp:dateformat value='${r.makedate}' p='yyyy-MM-dd hh:mm:ss'/></td>
             <td align="left">${r.paymodename}</td>
             <td align="left">${r.memo}</td>
             <td align="right">￥<windrp:format p="###,##0.00" value='${r.payablesum}'/></td>
             <td align="right">￥<windrp:format p="###,##0.00" value='${r.paysum}'/></td>
           </tr>
		   <c:set var="payout" value="${payout+r.payablesum}"/>
		   <c:set var="pay" value="${pay+r.paysum}"/>
          </logic:iterate> 
           </table>
		<table width="100%" cellspacing="1" class="totalsumTable">
         <tr class="back-gray-light">
          <td align="right" width="12%">本页合计：</td>
          <td align="right" >${totalpayablesum}</td>
          <td align="right" width="12%">${totalpaysum}</td>
        </tr>
		 <tr class="back-gray-light">
          <td align="right">总合计：</td>
          <td align="right">${allpayablesum}</td>
          <td align="right">${allpaysum}</td>
        </tr>

     </table>
     </div>
    </td>
  </tr>
</table>
</body>
</html>
