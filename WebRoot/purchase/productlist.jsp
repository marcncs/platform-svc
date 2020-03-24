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
<script language="JavaScript">
	var checkid=0;
	var checkcname="";
	var pdmenu=0;
	function CheckedObj(obj,objid,objcname){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;
	 pdmenu = getCookie("PdCookie");
	 switch(pdmenu){
	 	case "1":ProductPrice(); break;
		case "2":OrganPrice(); break;
		case "3":ProvideCompare(); break;
		case "4":ProductIntegral(); break;
		case "5":ProductPicture();break;
		case "6":IntegralExchange();break;
		case "7":ProductPriceHistory();break;
		case "8":OrganPriceHistory();break;
		case "9":ProductPriceii();break;
		case "10":ProductICode();break;
		default:Detail();
	 }
	 
	}

	function addNew(psid){
		if(psid){
			popWin("../purchase/toAddProductAction.do?PSID="+psid,900,800);
		}else{
			alert("请选择产品类别!");
		}
	}

	function Update(){
		if(checkid!=""){
			popWin("../purchase/toUpdProductAction.do?ID="+checkid,900,800);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){ 
		if(checkid!=""){
			if ( confirm("你确认要删除编号为:"+checkid+"的产品吗?") ){
				popWin2("../purchase/delProductAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	//产品上架
	function SelectOrgan(){
		var parray=new Array(); 
		$("input:checkbox").each(function(i){
			if(this.name == "pid" && this.checked == true){
	 			parray.push(this.value);
	 		}
		});  
		if(parray!=""){
		    openwindow("../purchase/selectOrganListAction.do?IDS="+parray,"选择机构",600,400);
		}else{
			alert("请勾选你要操作的记录!");
		}
	}
	
	function RemoveOrgan(){
		if(checkid!=""){ 
			popWin2("../purchase/removeOrganListAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
	setCookie("PdCookie","0");
		if(checkid!=""){
			document.all.submsg.src="productDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ProductPrice(){
	setCookie("PdCookie","1");
		if(checkid!=""){
			document.all.submsg.src="listProductPriceAction.do?PID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function IntegralExchange(){
	setCookie("PdCookie","6");
		if(checkid!=""){
			document.all.submsg.src="listIntegralExchangeAction.do?PID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function OrganPrice(){
	setCookie("PdCookie","2");
		if(checkid!=""){
			document.all.submsg.src="listOrganPriceAction.do?PID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ProductPriceii(){
	setCookie("PdCookie","9");
		if(checkid!=""){
			document.all.submsg.src="listProductPriceiiAction.do?PID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function ProductIntegral(){
	setCookie("PdCookie","4");
		if(checkid!=""){
			document.all.submsg.src="productIntegralAction.do?PID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ProvideCompare(){
	setCookie("PdCookie","3");
		if(checkid!=""){
			document.all.submsg.src="productProviderCompareAction.do?PDID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function ProductPicture(){
	setCookie("PdCookie","5");
		if(checkid!=""){
			document.all.submsg.src="listProductPictureAction.do?PID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ProductPriceHistory(){
	setCookie("PdCookie","7");
		if(checkid!=""){
			document.all.submsg.src="listProductPriceHistoryAction.do?PID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function OrganPriceHistory(){
	setCookie("PdCookie","8");
		if(checkid!=""){
			document.all.submsg.src="listOrganPriceHistoryAction.do?PID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function ProductICode(){
	setCookie("PdCookie","10");
		if(checkid!=""){
			document.all.submsg.src="../sys/listICodeAction.do?PID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function Import(){		
		popWin("../purchase/toImportProductAction.do",500,300);
		
	}
	function OutPut(){
		excputform.action="excPutProductAction.do";
		excputform.submit();
	}
	
	function DownTxt(){
		excputform.action="txtPutProductAction.do";
		excputform.submit();
	}
	
	function PrintProduct(){
		if(checkid!=""){
			window.open("../purchase/printProductAction.do?ID="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}	
 //全选框点击事件
	function checkAll(){ 
		if($("#checkAll").attr('checked') == true){
			$("input:checkbox").attr('checked','checked');
		}else{
			$("input:checkbox").removeAttr('checked');
		}
		 
	}
 	 
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
		<div id="bodydiv">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">产品 
            <input type="hidden" name="ID" value="${id}"></td>
        </tr>
      </table>
      <form name="search" method="post" action="listProductAction.do">
	  <table width="100%"   border="0" cellpadding="0" cellspacing="0">
  
  <tr class="table-back"> 
      <td width="9%" align="right">是否可用：</td>
       <td width="11%"><windrp:select key="YesOrNo" name="UseFlag" value="${UseFlag}" p="y|f"/>
  </td>
  <td width="9%" align="right">产品类型：</td>
       <td width="11%"><windrp:select key="ProductType" name="productType" value="${productType}" p="y|f"/>
  </td>
      <td width="8%"  align="right">关键字：</td>
      <td width="16%"><input type="text" name="KeyWord" value="${KeyWord}" maxlength="30"></td>
	<td width="4%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
    </tr>
 
</table> 
</form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="title-func-back"> 
	<ws:hasAuth operationName="/purchase/toAddProductAction.do">
		<td width="50"><a href="javascript:addNew('${pid}');"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
	 <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	</ws:hasAuth>
	<ws:hasAuth operationName="/purchase/toUpdProductAction.do">
	<td width="50"><a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	</ws:hasAuth>
	<ws:hasAuth operationName="/purchase/delProductAction.do">
	<td width="50"><a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	</ws:hasAuth>
	<ws:hasAuth operationName="/purchase/selectOrganListAction.do">
	<td width="50"><a href="javascript:SelectOrgan();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;上架</a></td>
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	</ws:hasAuth>
	<!-- 
	<td width="50"><a href="javascript:RemoveOrgan();"><img src="../images/CN/download.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下架</a></td>
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	 -->
	<ws:hasAuth operationName="/purchase/toImportProductAction.do">
	<td width="50"><a href="javascript:Import()"><img src="../images/CN/import.gif" width="16" height="16"border="0" align="absmiddle">&nbsp;导入</a></td>
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	</ws:hasAuth>
	<ws:hasAuth operationName="/purchase/excPutProductAction.do">
	<td width="50"><a href="javascript:OutPut();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>
	</ws:hasAuth>
	<!--<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
    <td width="50"><a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>-->
	<td class="SeparatePage"> <pages:pager action="../purchase/listProductAction.do"/> 
	</td>
	</tr>
</table>
	  </div>
	</td>
</tr>
<tr>
	<td>
	<div id="listdiv" style="overflow-y: auto; height: 600px;" >
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
          	<td width="16px" class="sorttable_nosort"><input type="checkbox" id="checkAll" onclick="checkAll()"></td>
            <td width="140px">编号</td>
            <td width="140px">物料号</td>
            <td width="140px">物料中文名</td>
            <td style="width: auto;" >产品名称</td>
            <td width="100px">规格</td>
            <td width="100px">规格明细</td>
            <td width="30px">单位</td>
			<td width="60px">是否可用</td>
			<td>产品类型</td> 
          </tr>
          <logic:iterate id="p" name="alapls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}','${p.productname}');"> 
            <td width="16px"><input type="checkbox" value="${p.id}" name="pid" onclick="changeCheckAll();"></td>
            <td>${p.id}</td>
            <td>${p.mCode }</td>
            <td>${p.matericalChDes }</td>
            <td>${p.productname}</td>
            <td>${p.specmode}</td>
            <td>${p.packSizeName}</td>
            <td><windrp:getname key='CountUnit' value='${p.countunit}' p='d'/></td>
			<td><windrp:getname key='YesOrNo' value='${p.useflag}' p='f'/></td>
			<td><windrp:getname key='ProductType' value='${p.productType}' p='f'/></td>
          </tr>
          </logic:iterate> 
      </table>
      <br>
      <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:Detail();"><span>产品详情</span></a></li>
            <ws:hasAuth operationName="/purchase/listProductPictureAction.do">
            <li><a href="javascript:ProductPicture();"><span>产品图片</span></a></li>
            </ws:hasAuth>
            <%--<li><a href="javascript:ProductIntegral();"><span>积分设定</span></a></li>
            <li><a href="javascript:IntegralExchange();"><span>积分兑换</span></a></li>
            <li><a href="javascript:ProductPrice();"><span>零售价格设定</span></a></li>
            <li><a href="javascript:OrganPrice();"><span>机构零售定价</span></a></li>
            <li><a href="javascript:ProductPriceii();"><span>机构价格设定</span></a></li>
            <li><a href="javascript:ProvideCompare();"><span>供应商比较</span></a></li>
            <li><a href="javascript:ProductICode();"><span>物流码前缀</span></a></li>
             --%>
          </ul>
        </div>
        <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>			
	  </div>
	  </td>
  </tr>
</table> 
<form  name="excputform" method="post" action="excPutProductAction.do">
<input type="hidden" name="Wise" id ="Wise" value="${Wise}">
<input type="hidden" name="OtherKey" id ="OtherKey" value="${OtherKey}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord }">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate }">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate }">
<input type="hidden" name="UseFlag" id ="UseFlag" value="${UseFlag }">
</form>
</body>
</html>
