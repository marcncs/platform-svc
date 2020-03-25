<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<script language="javascript">
	function ToSet(){
		window.open("../purchase/toSetProductPriceAction.do","","height=650,width=500,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function ToApp(){
		window.open("../purchase/toAppLctProductPriceAction.do","","height=600,width=700,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 
	}
	
	//全选框点击事件
	function checkPidAll(){
		if($("#checkAll").attr('checked') == true){
			$("input:checkbox").attr('checked','checked');
		}else{
			$("input:checkbox").removeAttr('checked');
		}
	}
	
	//选择产品
	function SelectSingleProduct(){
		var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
		if(p==undefined){return;}
		document.search.ProductID.value=p.id;
		document.search.ProductName.value=p.productname;
	}
	
	//新增产品历史价格
	function addNew(psid){
		popWin("../purchase/toAddProductPriceHistoryAction.do",900,450);
	}
	
	//修改产品历史价格
	function Update(){
		if(checkid!=""){
			popWin("../purchase/toUpdProductPriceHistoryAction.do?pphid="+checkid,900,450);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	//删除产品历史价格
	function Del(){
		if(checkid!=""){
			if ( confirm("你确认要删除产品价格信息吗?") ){
				popWin2("../purchase/delProductPriceHistoryAction.do?pphid="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	//删除多条记录
	function Del_Many() {
		var flag = false;
		var rids = "";
		var rid = document.getElementsByName("pid");
		if (rid.length > 0) {
			for ( var i = 0; i < rid.length; i++) {
				if (rid[i].checked) {
					flag = true;
					rids = rid[i].value + "," + rids;
				}
			}
		}
		if (flag) {
			if (confirm("确认要解除关联吗?")) {
				popWin2("../purchase/delProductPriceHistoryAction.do?pphid=" + rids);
			}
		} else {
			alert("请选择你要操作的记录!");
		}
	}
	
	//导出
	function OutPut(){
		excputform.action="excPutProductPriceHistoryAction.do";
		excputform.submit();
	}
	
	//导入产品
 	/*
 	function ImportSimpleProduct(){		
		popWin("../purchase/toImportSimpleProductAction.do",500,300);
	}
	*/
	 	
	//导入产品价格
	function ImportSimpleProductHistory(){		
		popWin("../purchase/toImportSimpleProductHistoryAction.do",500,300);
	}
</script>
</head>

<body>
<form name="search" method="post" action="listProductPriceHistoryAction.do">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
		<div id="bodydiv">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">系统设置>>产品价格记录
            <input type="hidden" name="ID" value="${id}"></td>
        </tr>
      </table>
	  <table width="100%"   border="0" cellpadding="0" cellspacing="0">
  
  <tr class="table-back"> 
		<td width="10%" align="right">产品：</td>
        <td width="10%"><input name="productId" type="hidden" id="ProductID" value="${ProductID}">
           <input id="ProductName" name="productName" value="${ProductName}" readonly><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a></td>
  		<td width="15%"  align="right">日期：</td>
        <td width="23%"><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" size="15" onFocus="javascript:selectDate(this)" readonly></td>
        <td width="8%"  align="right">关键字：</td>
        <td width="16%"><input type="text" name="KeyWord" value="${KeyWord}" maxlength="30"></td>
	    <td width="4%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
    </tr>
 
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="title-func-back"> 
	<td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
	 <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	<td width="50"><a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	<td width="50"><a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>

	<td width="50"><a href="javascript:OutPut();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>
	
	<!-- 
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	<td width="70"><a href="javascript:ImportSimpleProduct()"><img src="../images/CN/import.gif" width="16" height="16"border="0" align="absmiddle">&nbsp;导入产品</a></td>
	 -->
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	<td width="100"><a href="javascript:ImportSimpleProductHistory()"><img src="../images/CN/import.gif" width="16" height="16"border="0" align="absmiddle">&nbsp;导入产品价格</a></td>
	
	<td class="SeparatePage"> <pages:pager action="../purchase/listProductPriceHistoryAction.do"/> 
	</td>
	</tr>
</table>
</table>
</div>
        <form action="listProductPriceHistoryAction.do" method="post" name="validateProduct" onSubmit="return formChk();">
		  <table width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr align="center" class="title-top">
              <td width="16px" class="sorttable_nosort"><input type="checkbox" id="checkAll" onclick="checkPidAll();"></td>
              <td width="15%" >产品ID</td>
              <td width="26%" >产品名称</td>
              <td width="8%">单位</td>
              <td width="13%">价格</td>
              <td width="8%">开始日期</td>
              <td width="8%">结束日期</td>
			  <td width="20%">备注</td>
            </tr>
            <logic:iterate id="p" name="alpl" >
             <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}');"> 
                <td width="16px"><input type="checkbox" value="${p.id}" name="pid" onclick="changeCheckAll();"></td>
                <td>${p.productId}</td>
                <td>${p.productName}</td>
                <td>${p.unitName}</td>
                <td>${p.unitPrice}</td>
                <td align="left"><c:if test="${p.startTime!=null}"><windrp:dateformat value='${p.startTime}' p='yyyy-MM-dd'/></c:if></td>
                <td align="left"><c:if test="${p.endTime!=null}"><windrp:dateformat value='${p.endTime}' p='yyyy-MM-dd'/></c:if></td>
				<td>${p.memo}</td>
              </tr>
            </logic:iterate>
          </table>
          
        </form>

    </td>
  </tr>
</table>
</td>
</tr>
</table>
</form>
<form  name="excputform" method="post" action="">
<input type="hidden" name="productId"  value="${ProductID}">
<input type="hidden" name="productName"  value="${ProductName}">
<input type="hidden" name="KeyWord"  value="${KeyWord }">
<input type="hidden" name="BeginDate" value="${BeginDate }">
<input type="hidden" name="EndDate" value="${EndDate }">
</form>
</body>
</html>
