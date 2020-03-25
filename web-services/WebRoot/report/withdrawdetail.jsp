<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/showbill.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<script type="text/javascript" src="../js/sorttable.js"> </script>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectCustomer(){
		var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( c == undefined ){
			return;
		}
		document.searchform.CID.value=c.cid;
		document.searchform.CName.value=c.cname;
	}
function SelectOrgan(){
		var p=showModalDialog("../common/selectVisitOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.searchform.MakeOrganID.value=p.id;
			document.searchform.oname.value=p.organname;
					clearUser("MakeID","uname");
	}
function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.searchform.EquipOrganID.value=p.id;
			document.searchform.oname2.value=p.organname;
	}
function excput(){
	searchform.target="_blank";
	searchform.action="../report/excPutWithdrawDetailAction.do";
	searchform.submit();
	searchform.target="";
	searchform.action="../report/withdrawDetailAction.do";
}
function print(){
	searchform.target="_blank";
	searchform.action="../report/printWithdrawDetailAction.do";
	searchform.submit();
	searchform.target="";
	searchform.action="../report/withdrawDetailAction.do";
	
}

function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.searchform.ProductID.value=p.id;
	document.searchform.ProductName.value=p.productname;
	}


this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
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
          <td width="772">报表分析>>零售>>零售退货明细</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="withdrawDetailAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
           <td width="10%"  align="right">制单机构：</td>
            <td width="25%">
            <input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
			<input name="oname" type="text" id="oname" size="30" value="${oname}"
				readonly><a href="javascript:SelectOrgan();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a>
            </td>
            <td width="10%" align="right">制单人：</td>
            <td width="25%">
	         <input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
			<input type="text" name="uname" id="uname" onClick="selectDUW(this,'MakeID',$F('MakeOrganID'),'ou')" value="${uname}" readonly>
            </td>
            <td  align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" onFocus="selectDate(this);" 
             size="10" readonly="readonly">
				-
  			<input name="EndDate" type="text" id="EndDate" value="${EndDate}" onFocus="selectDate(this);" 
  			 size="10" readonly="readonly"></td>
           
          </tr>
          <tr class="table-back">
            <td  align="right">会员名称：</td>
            <td><input name="CID" type="hidden" id="CID" value="${CID}">
              <input id="CName" name="CName" value="${CName}"><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" align="absmiddle" height="18" border="0"></a></td>
            <td align="right">产品：</td>
            <td><input name="ProductID" type="hidden" id="ProductID" value="${ProductID}">
              <input id="ProductName" name="ProductName" value="${ProductName}"><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" width="18" align="absmiddle" height="18" border="0"></a>
            </td>
            <td align="right">&nbsp;</td>
            <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
          <tr class="title-func-back"> 
		  	<td width="50">
			<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
		    </td>
		    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		    <td width="50">
			<a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a>
		    </td>
		    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
            <td class="SeparatePage">
			<pages:pager action="../report/withdrawDetailAction.do"/>	
            </td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable"> 
        <tr class="title-top-lock" align="center">    
          <td>会员编号</td>     
          <td>会员名称</td>
		  <td>单据号</td>
		  <td>制单时间</td>
		  <td>制单机构</td>
		  <td>产品名称</td>
          <td >规格</td>
          <td width="7%">单位</td>
          <td width="7%">单价</td>
          <td width="7%">数量</td>
          <td width="7%">金额</td>
        </tr>
        <c:set var="totalcount" value="0"/>
		<c:forEach items="${list}" var="p">
	        <tr class="table-back-colorbar">   
	         <td  >${p.cid}</td>        
			  <td  >${p.cname}</td>
			  <td ><a href="javascript:ToBill('${p.id}');">${p.id}</a></td>
			  <td><windrp:dateformat value="${p.makedate}" p="yyyy-MM-dd hh:mm:ss"/></td>
			  <td  ><windrp:getname key="organ" p="d" value="${p.makeorganid}"/></td>
	          <td >${p.productname}</td>
	          <td >${p.specmode}</td>
	          <td ><windrp:getname key="CountUnit" p="d" value="${p.unitid}"/></td>
	          <td align="right">￥<windrp:format p="###,##0.00" value='${p.unitprice}' /></td>
	          <td align="right"><windrp:format p="###,##0.00" value='${p.quantity}' /></td>
	          <td align="right">￥<windrp:format p="###,##0.00" value='${p.subsum}' /></td>
	        </tr>
	        <c:set var="totalcount" value="${totalcount+p.subsum}"/>
		</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr align="center" class="back-gray-light">
          <td align="right" width="12%"> 本页合计：</td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${totalcount}' /></td>
        </tr>
		<tr class="back-gray-light">
          <td align="right">总合计：</td>
          <td align="right">${totalsum}</td>
        </tr>
      </table>
      </div>
    </td>
  </tr>
</table>

<form  name="excputform" method="post" action="excPutWithdrawDetailAction.do">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="ProductID" id ="ProductID" value="${ProductID}">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName}">
<input type="hidden" name="CID" id ="CID" value="${CID}">
<input type="hidden" name="CName" id ="CName" value="${CName}">
</form>
</body>
</html>
