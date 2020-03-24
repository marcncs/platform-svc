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
<SCRIPT language="javascript" src="../js/showbill.js"></SCRIPT>
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
	Detail();
	}
	
	function addNew(){
	window.open("../finance/toAddReceivableAction.do","","height=550,width=1100,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdReceivableAction.do?ID="+checkid,"","height=550,width=1100,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
	if(checkid!=""){
			document.all.submsg.src="../finance/receivableDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			window.open("../finance/toReferReceivableAction.do?RID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
			window.open("../finance/delReceivableAction.do?RID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
		function Check(){
		if(document.all("checkall").checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.listform.length;i++){

			if (!document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.listform.length;i++){
			if (document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=false;
				}
		}
	}
	
	function TransIncomeLog(){
		var flag=true;
		var rid=document.listform.rid;
		var rids="";
		if ( rid.length >1){
			for(var i=0; i<rid.length; i++){
				if (rid[i].checked){
					flag=false;
					rids="'"+rid[i].value+"',"+rids;
				}
			}
		}else{
			if(rid.checked){
				flag=false;
				rids="'"+rid.value+"',";
			}
		}
		if (flag){
			alert("请选择你要操作的记录!");
		}else{
			window.open("../finance/toTransIncomeLogAction.do?rids="+rids,"","height=550,width=1100,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");					
		}
	}
	
	function Destory(){
		var flag=true;
		var rid=document.listform.rid;
		var rids="";
		if ( rid.length >1){
			for(var i=0; i<rid.length; i++){
				if (rid[i].checked){
					flag=false;
					rids="'"+rid[i].value+"',"+rids;
				}
			}
		}else{
			if(rid.checked){
				flag=false;
				rids="'"+rid.value+"',";
			}
		}
		if (flag){
			alert("请选择你要操作的记录!");
		}else{
			window.open("../finance/toDestoryIncomeLogAction.do?rids="+rids,"","height=550,width=1100,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");					
		}
	}
	
		function excput(){
		search.target="";
		search.action="../sales/excPutIntegralDetailAction.do";
		search.submit();
		
	}
	function print(){
		search.target="_blank";
		search.action="../sales/printIntegralDetailAction.do";
		search.submit();
	}
	
	function oncheck(){
		search.target="";
		search.action="../sales/listIntegralDetailAction.do";
		search.submit();
		
	}
	


</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">积分明细</td>
        </tr>
      </table>
       <form name="search" method="post" action="listIntegralDetailAction.do" onSubmit="return oncheck();">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="9%"  align="right"><input name="OID" type="hidden" id="OID" value="${OID}">
              <input name="OSort" type="hidden" id="OSort" value="${OSort}">
              <input name="BillNo" type="hidden" id="BillNo" value="${BillNo}">
            类别：</td>
            <td width="28%"><windrp:select key="DSort" name="ISort" p="y|d" value="${ISort}"/></td>
            <td width="9%" align="right">&nbsp;</td>
            <td width="26%">&nbsp;</td>
            <td width="7%" align="right">&nbsp;</td>
            <td width="21%" align="right"><input name="Submit2" type="image" border="0" src="../images/CN/search.gif"></td>
          </tr>
      
      </table>
        </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
		<td width="50"><a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" 
		width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a> </td>
		<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		<td width="51"><a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16"
		border="0" align="absmiddle">&nbsp;打印</a></td>
		<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>			
          <td style="text-align: right;"><pages:pager action="../sales/listIntegralDetailAction.do"/></td>
        </tr>
      </table>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
       
          <tr align="center" class="title-top"> 
		    <td width="10%">编号</td>
            <td width="15%">单据号</td>
            <td width="13%">积分类别</td>
            <td width="14%">积分</td>
            <td width="14%">制单日期</td>
            <td width="18%">配送机构</td>
            <td width="16%">所属机构</td>
          </tr>
          <logic:iterate id="s" name="idls" > 
          <tr class="table-back-colorbar" > 
            <td>${s.id}</td>
            <td><a href="javascript:ToBill('${s.billno}');">${s.billno}</a></td>
            <td><windrp:getname key='DSort' value='${s.isort}' p='d'/></td>
            <td align="right"><windrp:format value="${s.integral}"/></td>
            <td><windrp:dateformat value='${s.makedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='organ' value='${s.equiporganid}' p='d'/></td>
            <td><windrp:getname key='organ' value='${s.organid}' p='d'/></td>
            </tr>
          </logic:iterate> 
        
      </table>
      </form>
	  </td>
  </tr>
</table>
</body>
</html>
