<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<%@ taglib prefix="ws" uri="ws" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>修改采集器</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script language="JavaScript">
	var checkid=0;
	var checkcname="";
	var pdmenu=0;
	function CheckedObj(obj,objid,objcname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;
	 pdmenu = getCookie("PdCookie");
	 switch(pdmenu){
		case "1":ProvideCompare(); break;
		case "2":ProductHistory(); break;
		default:ProvideCompare();
	 }
	}

	function addNew(){
		window.open("../scanner/toAddScannerAction.do","","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
			window.open("../scanner/toUpdateScannerAction.do?ID="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function scannerUpdate(){

		if(checkid!=""){
			document.all.submsg.src="productProviderCompareAction.do?PDID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ProductHistory(){
	setCookie("PdCookie","2");
		if(checkid!=""){
			document.all.submsg.src="listProductHistoryAction.do?productid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function formcheck(){
		var model1 = document.updateScanner.modelSearch.value;
		var osVersionSearch1 = document.updateScanner.osVersionSearch.value;
		var statusSearch1 = document.updateScanner.statusSearch.value;
		var installDateSearch1 = document.updateScanner.installDateSearch.value;
		//var appVersionSearch1 = document.updateScanner.appVersionSearch.value;
		//var appVerUpDateSearch1 = document.updateScanner.appVerUpDateSearch.value;
		//var wHCodeSearch1 = document.updateScanner.wHCodeSearch.value;
		var scannerNameSearch1 = document.updateScanner.scannerNameSearch.value;
		//var lastUpDateSearch1 = document.updateScanner.lastUpDateSearch.value;

		if(model1.value=="" ){
		alert("请输入采集器型号！");
		return false;
		}
		
		if(osVersionSearch1=="" ){
		alert("请输入采集器系统版本！");
		return false;
		}
		
		if(isNaN(statusSearch1))
		{
		alert("采集器状态只允许输入数字！");
		document.updateScanner.statusSearch.value="";
		return false;
		}
				
		if(statusSearch1=="" ){
		alert("请输入采集器状态！");
		return false;
		}
		
		if(installDateSearch1=="" ){
		alert("请输入采集器安装日期！");
		return false;
		}
		
		/*
		if(appVersionSearch1=="" ){
		alert("请输入采集器软件版本 ！");
		return false;
		}
		
		if(appVerUpDateSearch1=="" ){
		alert("请输入采集器更新日期！");
		return false;
		}
		
		if(isNaN(wHCodeSearch1))
		{
		alert("采集器仓库代码只允许输入数字！");
		document.updateScanner.wHCodeSearch.value="";
		return false;
		}
		
		if(wHCodeSearch1=="" ){
		alert("请输入采集器仓库代码！");
		return false;
		}
		*/
		
		if(scannerNameSearch1=="" ){
		alert("请输入采集器名字 ！");
		return false;
		}
		/*
		if(lastUpDateSearch1=="" ){
		alert("请输入采集器最后更新日期！ ");
		return false;
		}
		*/
		showloading();
		return true; 
}
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body style="overflow:auto">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" >
 <tr>
  <td>
  	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
   		<tr>
    		<td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
     		<td width="772"> ${operateName }</td>
      	</tr>
  	</table>
    <form name="updateScanner" method="post" action="../sys/updScannerAction.do"  onSubmit="return formcheck();">
         <fieldset align="center">  <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>基本信息</td>
            </tr>
          </table>
		  </legend>
      <table  width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr > 
		  	<!--<td width="6%" align="right">编号:</td>
		  	<td width="14%">
		  		<span class="STYLE1"></span>
		  	</td>
		  	-->
            <td width="10%" align="right" >型号:</td>
            <td width="10%">
            <input id="idSearch" type="hidden" name="idSearch"  value="${p.id}"  readonly>
            <input id="modelSearch" type="text" name="modelSearch"  value="${p.model}" >
            </td>
            <td width="10%" align="right">系统版本:</td>
            <td width="10%"><input id="osVersionSearch" type="text" name="osVersionSearch"  value="${p.osVersion}" ></td>
            <td width="10%" align="right">采集器编号:</td>
            <td width="10%"><input id="scannerImeiNSearch" type="text" name="scannerImeiNSearch"  value="${p.scannerImeiN}" ></td>
          	<td width="10%" align="right">状态:</td>
            <td width="10%">
            	<windrp:select key='YesOrNo' name="statusSearch" value='${p.status}' p="y|f"/>
            </td>
          </tr>
          <tr >
          	
          	<td width="6%" align="right">安装日期:</td>
            <td width="16%"><input id="installDateSearch" type="text" name="installDateSearch"  value="<windrp:dateformat value='${p.installDate}' p='yyyy-MM-dd'/>"  onFocus="javascript:selectDate(this)" readonly></td>
          	<!--<td width="10%" align="right">软件版本:</td>
            <td width="10%"><input id="appVersionSearch" type="text" name="appVersionSearch"  value="${p.appVersion}" ></td>
            <td width="10%"  align="right">更新日期:</td>
            <td width="10%"><input id="appVerUpDateSearch" type="text" name="appVerUpDateSearch"  value="${p.appVerUpDate}"  onFocus="javascript:selectDate(this)" readonly></td>-->
            <td width="5%"  align="right">名字:</td>
            <td width="10%"><input id="scannerNameSearch" type="text" name="scannerNameSearch"  value="${p.scannerName }" ></td>
            <td></td>
            <td></td>
          </tr>
          <tr >
          <%-- 
          	<td width="10%" align="right">仓库代码:</td>
          	<td width="10%"><input id="wHCodeSearch" type="text" name="wHCodeSearch"  value="${p.wHCode }" ></td>
            --%>
          	<!--<td width="10%"  align="right">最后更新日期:</td>
          	<td width="10%"><input id="lastUpDateSearch" type="text" name="lastUpDateSearch"  value="${p.lastUpDate}"  onFocus="javascript:selectDate(this)" readonly></td>
          --></tr>
      </table>
      </fieldset>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr>
				<td align="center">											
					<input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
					<input type="reset" name="cancel" onClick="javascript:window.close()" value="取消">																						</td>
			</tr>
	  </table>
     </form>
</body>
</html>
