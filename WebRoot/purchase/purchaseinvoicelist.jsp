<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>采购发票</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}

	function Update(){
		if(checkid>0){
			location.href("../purchase/toUpdPurchaseInvoiceAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function excput(){
		search.target="";
		search.action="../purchase/excPutPurchaseInvoiceAction.do?ProvideID=${pid}";
		search.submit();
		search.action="listPurchaseInvoiceAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../purchase/printPurchaseInvoiceAction.do?ProvideID=${pid}";
		search.submit();
		search.target="";
		search.action="listPurchaseInvoiceAction.do";
	}
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
	}
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td>采购发票 </td>
        </tr>
      </table>
      <form name="search" method="post"
				action="listPurchaseInvoiceAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
			
			<tr class="table-back">
				<td align="right">制单机构：				</td>
				<td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
                  <input name="oname" type="text" id="oname" value="${oname}" 
					size="30" readonly><a href="javascript:SelectOrgan();"><img
							src="../images/CN/find.gif" width="18" height="18" border="0"
							align="absmiddle"> </a> </td>
				<td align="right">制单部门：</td>
				<td><input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
                  <input type="text" name="deptname" id="deptname"
						onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')"
						value="${deptname}" readonly></td>
				<td align="right">制单人：</td>
				<td ><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
                  <input type="text" name="uname" id="uname"
						onClick="selectDUW(this,'MakeID',$F(MakeDeptID),'du')"
						value="${uname}" readonly></td>
			</tr>
			<tr class="table-back">
				<td align="right">发票编号：</td>
				<td><input name="InvoiceCode" type="text" id="InvoiceCode" value="${InvoiceCode}"></td>
				<td align="right">发票类型：</td>
				<td><windrp:select key="InvoiceType" name="InvoiceType" value="${InvoiceType}" p="y|f" />				</td>
				<td align="right">开票日期：</td>
				<td>
					<input name="BeginDate" type="text" readonly="readonly"
						onFocus="javascript:selectDate(this)" value="${BeginDate}" size="10">
					-
					<input name="EndDate" type="text" readonly="readonly"
						onFocus="javascript:selectDate(this)" value="${EndDate}" size="10"></td>
			</tr>
			<tr class="table-back">
				<td align="right">
					是否复核：				</td>
				<td>
					<windrp:select key="YesOrNo" name="IsAudit" value="${IsAudit}" p="y|f" />				</td>
				<td align="right">&nbsp;</td>
				<td>				</td>
				<td align="right">&nbsp;				</td>
				<td class="SeparatePage">
					<input name="Submit" type="image" id="Submit"
						src="../images/CN/search.gif" border="0" title="查询">				</td>
			</tr>
			
		</table>
		</form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back"> 
          <td width="50">
			<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" 
				width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
	      </td>
		    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
			<td width="51">
				<a href="javascript:print();"><img
						src="../images/CN/print.gif" width="16" height="16"
						border="0" align="absmiddle">&nbsp;打印</a>
			</td>
			<td width="1">
				<img src="../images/CN/hline.gif" width="2" height="14">
			</td>
         <td  class="SeparatePage"> <pages:pager action="../purchase/listPurchaseInvoiceAction.do"/></td>
        </tr>
      </table></td>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
          <tr align="center" class="title-top"> 
            <td width="12%">发票编号</td>
            <td width="29%">供应商</td>
            <td width="14%" >发票类型</td>
            <td width="12%">制票日期</td>
            <td width="11%">开票日期</td>
            <td width="13%">制单人</td>
            <td width="9%">是否复核</td>
          </tr>
          <logic:iterate id="pl" name="alpl" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,${pl.id});"> 
            <td title="点击查看详情"><a href="../purchase/purchaseInvoiceDetailAction.do?ID=${pl.id}">${pl.invoicecode}</a></td>
            <td>${pl.provideidname}</td>
            <td >${pl.invoicetypename}</td>
            <td><windrp:dateformat value="${pl.makeinvoicedate}" p="yyyy-MM-dd"/></td>
            <td><windrp:dateformat value="${pl.invoicedate}" p="yyyy-MM-dd"/></td>
            <td>${pl.makeidname}</td>
            <td>${pl.isauditname}</td>
          </tr>
          </logic:iterate> 
      </table>
      
  </tr>
</table>
</body>
</html>
