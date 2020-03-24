<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>增加Scanner</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<script src="../js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<script language="JavaScript">
function formcheck(){
		var model1 = document.addScanner.modelSearch.value;
		var model2 = document.addScanner.modelSearch.value.trim();
		var osVersionSearch1 = document.addScanner.osVersionSearch.value;
		var osVersionSearch2 = document.addScanner.osVersionSearch.value.trim();
		var statusSearch1 = document.addScanner.statusSearch.value;
		var statusSearch2 = document.addScanner.statusSearch.value.trim();
		var installDateSearch1 = document.addScanner.installDateSearch.value;
		var installDateSearch2 = document.addScanner.installDateSearch.value.trim();
		//var appVersionSearch1 = document.addScanner.appVersionSearch.value;
		//var appVersionSearch2 = document.addScanner.appVersionSearch.value.trim();
		//var appVerUpDateSearch1 = document.addScanner.appVerUpDateSearch.value;
		//var appVerUpDateSearch2 = document.addScanner.appVerUpDateSearch.value.trim();
		//var wHCodeSearch1 = document.addScanner.wHCodeSearch.value;
		//var wHCodeSearch2 = document.addScanner.wHCodeSearch.value.trim();
		var scannerNameSearch1 = document.addScanner.scannerNameSearch.value;
		var scannerNameSearch2 = document.addScanner.scannerNameSearch.value.trim();
		//var lastUpDateSearch1 = document.addScanner.lastUpDateSearch.value;
		//var lastUpDateSearch2 = document.addScanner.lastUpDateSearch.value.trim();
		var scannerImeiNSearch1 = document.addScanner.scannerImeiNSearch.value;
		var scannerImeiNSearch2 = document.addScanner.scannerImeiNSearch.value.trim();
		
		//采集器型号判断
		if(model1.value=="" || model2 ==""){
		alert("请输入采集器型号！");
		return false;
		}
		
		//采集器系统版本判断 
		if(osVersionSearch1=="" || osVersionSearch2==""){
		alert("请输入采集器系统版本！");
		return false;
		}
		
		//采集器编号判断
		if(scannerImeiNSearch1=="" || scannerImeiNSearch2==""){
		alert("请输入采集器编号！");
		return false;
		}
		
		//采集器状态判断 
		if(statusSearch1=="" || statusSearch2==""){
		alert("请输入采集器状态！");
		return false;
		}
		
		if(isNaN(statusSearch1))
		{
		alert("采集器状态只允许输入数字！");
		document.addScanner.statusSearch.value="";
		return false;
		}
		 
		//采集器安装日期判断 
		if(installDateSearch1=="" || installDateSearch2==""){
		alert("请输入采集器安装日期！");
		return false;
		}
		
		/*
		//采集器软件版本判断 
		if(appVersionSearch1=="" || appVersionSearch2==""){
		alert("请输入采集器软件版本 ！");
		return false;
		}
		
		//采集器更新日期判断
		if(appVerUpDateSearch1=="" || appVerUpDateSearch2==""){
		alert("请输入采集器更新日期！");
		return false;
		}
		
		
		//采集器仓库代码判断 
		if(isNaN(wHCodeSearch1))
		{
		alert("采集器仓库代码只允许输入数字！");
		document.addScanner.wHCodeSearch.value="";
		return false;
		}
		
		if(wHCodeSearch1=="" || wHCodeSearch2==""){
		alert("请输入采集器仓库代码！");
		return false;
		}
		
		//采集器最后更新日期判断 
		if(lastUpDateSearch1=="" || lastUpDateSearch2==""){
		alert("请输入采集器最后更新日期！ ");
		return false;
		}
		
		
		*/
		
		
		//采集器名字判断 
		if(scannerNameSearch1=="" || scannerNameSearch2==""){
		alert("请输入采集器名字 ！");
		return false;
		}
		
		if ( organid_hasdouble ){
		alert($F("scannerImeiNSearch")+"此采集器编码已经存在，请重新录入！");		
		return false;
	}
		
		showloading();
		return true; 
}
var organid_hasdouble=false;	

function checkexist(){
	var ln = $F("scannerImeiNSearch").trim();
	var url = '../scanner/ajaxCheckScannerAction.do';
	var pars = 'ln=' + ln;
	organid_hasdouble=false;
   	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showorgan}
				);
}

function showorgan(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.s;
	if ( lk == undefined ){
	}else{	
		organid_hasdouble=true;	
		alert($F("scannerImeiNSearch")+"此采集器已经存在，请重新录入！");		
	}
}

</script>
	</head>
	<body style="overflow: auto">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								${operateName }
							</td>
						</tr>
					</table>
					<form name="addScanner" method="post"
						action="../sys/addScannerAction.do" onSubmit="return formcheck();">
						<fieldset align="center">
							<legend>
								<table width="50" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											基本信息
										</td>
									</tr>
								</table>
							</legend>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr>
									<%-- 
		  	<td width="6%" align="right">编号:</td>
		  	<td width="14%">
		  		<input id="idSearch" type="text" name="idSearch"  value="${idSearch}" >
		  		<span class="STYLE1">*</span>
		  	</td>
		  	--%>
									<td width="10%" align="right">
										型号:
									</td>
									<td width="10%">
										<input id="modelSearch" type="text" name="modelSearch"
											value="${modelSearch}">
									</td>
									<td width="10%" align="right">
										系统版本:
									</td>
									<td width="10%">
										<input id="osVersionSearch" type="text" name="osVersionSearch"
											value="${osVersionSearch}">
									</td>
									<td width="10%" align="right">
										采集器编号:
									</td>
									<td width="10%">
										<input id="scannerImeiNSearch" type="text"
											name="scannerImeiNSearch" value="${scannerImeiNSearch}"
											onBlur="checkexist();">
									</td>
									<td width="10%" align="right">
										状态:
									</td>
									<td width="10%">
										<windrp:select key="YesOrNo" name="statusSearch"
											value="${statusSearch}" p="y|f" />
									</td>
								</tr>
								<tr>
									<td width="10%" align="right">
										安装日期:
									</td>
									<td width="10%">
										<input id="installDateSearch" type="text"
											name="installDateSearch" value="${installDateSearch}"
											onFocus="javascript:selectDate(this)" readonly>
									</td>
									<td width="5%" align="right">
										名字:
									</td>
									<td width="10%">
										<input id="scannerNameSearch" type="text"
											name="scannerNameSearch" value="${scannerNameSearch }">
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<%-- 
          	<td width="10%" align="right">仓库代码:</td>
          	<td width="10%"><input id="wHCodeSearch" type="text" name="wHCodeSearch"  value="${wHCodeSearch }" ></td>
          	
          										<td width="10%" align="right">
										最后更新日期:
									</td>
									<td width="10%">
										<input id="lastUpDateSearch" type="text"
											name="lastUpDateSearch" value="${lastUpDateSearch}"
											onFocus="javascript:selectDate(this)" readonly>
									</td>
									<td width="10%" align="right">
										软件版本:
									</td>
									<td width="10%">
										<input id="appVersionSearch" type="text"
											name="appVersionSearch" value="${appVersionSearch}">
									</td>
									<td width="10%" align="right">
										更新日期:
									</td>
									<td width="10%">
										<input id="appVerUpDateSearch" type="text"
											name="appVerUpDateSearch" value="${appVerUpDateSearch}"
											onFocus="javascript:selectDate(this)" readonly>
									</td>
									
           --%>
								</tr>
							</table>
						</fieldset>
						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td align="center">
									<input type="submit" name="Submit" value="确定">
									&nbsp;&nbsp;
									<input type="reset" name="cancel"
										onClick="javascript:window.close()" value="取消">
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
