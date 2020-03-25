<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation"%>
<%@ include file="../common/tag.jsp"%>
<%@ taglib prefix="ws" uri="ws"%>
<html>
	<head>
		<title>采集器列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<script src="../js/prototype.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery.js"></SCRIPT>
		<script language="JavaScript">
	var checkid = 0;
	var checkcname = "";
	var pdmenu = 0;
	function CheckedObj(obj, objid, objcname) {

		for (i = 0; i < obj.parentNode.childNodes.length; i++) {
			obj.parentNode.childNodes[i].className = "table-back-colorbar";
		}

		obj.className = "event";
		checkid = objid;
		checkcname = objcname;
	}

	function addNew() {
		window
				.open(
						"../scanner/toAddScannerAction.do",
						"",
						"height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update() {
		if (checkid != "") {
			window
					.open(
							"../scanner/toUpdateScannerAction.do?ID=" + checkid,
							"",
							"height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		} else {
			alert("请选择你要操作的记录!");
		}
	}

	function Del() {
		if (checkid != "") {
			if (confirm("你确认要删除编号为:" + checkid + "的产品吗?")) {
				var ln = checkid;
				var url = '../scanner/ajaxDelScannerAction.do';
				var pars = 'ln=' + ln;
				var myAjax = new Ajax.Request(url, {
					method : 'get',
					parameters : pars,
					onComplete : showorgan
				});
			}
		} else {
			alert("请选择你要操作的记录!");
		}
	}

	function showorgan(originalRequest) {
		var data = eval('(' + originalRequest.responseText + ')');
		var lk = data.sw;
		if (lk == undefined) {
			window.open("../scanner/delScannerAction.do?ID=" + checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		} else {
			alert("编号为"+checkid + "的采集器已被其他仓库看配置，请先除去关联再删除！");
		}
	}

	function OutPut() {
		excputform.action = "excPutScannerAction.do";
		excputform.submit();
	}

	function scannerUpdate() {

		if (checkid != "") {
			document.all.submsg.src = "productProviderCompareAction.do?PDID="
					+ checkid;
		} else {
			alert("请选择你要操作的记录!");
		}
	}

	function Import() {
		window
				.open(
						"../scanner/toImportScannerAction.do",
						"",
						"height=300,width=500,top=190,left=190,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");

	}

	function ProductHistory() {
		setCookie("PdCookie", "2");
		if (checkid != "") {
			document.all.submsg.src = "listProductHistoryAction.do?productid="
					+ checkid;
		} else {
			alert("请选择你要操作的记录!");
		}
	}

	this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
	}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<SCRIPT language=javascript>
	
</SCRIPT>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									${menusTrace }
									<input type="hidden" name="ID" value="${id}">
								</td>
							</tr>
						</table>
						<form name="search" method="post"
							action="../sys/toListScannerAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td width="10%" align="right">
										编号:
									</td>
									<td width="10%">
										<input type="text" name="idSearch" value="${idSearch}"
											maxlength="16" size="30">
									</td>
									<td width="10%" align="right">
										型号:
									</td>
									<td width="10%">
										<input type="text" name="modelSearch" value="${modelSearch}"
											maxlength="16" size="30">
									</td>
									<td width="10%" align="right">
										系统版本:
									</td>
									<td width="10%">
										<input type="text" name="osVersionSearch"
											value="${osVersionSearch}" maxlength="16" size="30">
									</td>
								</tr>
								<tr class="table-back">
									<td width="10%" align="right">
										采集器编号:
									</td>
									<td width="10%">
										<input type="text" name="scannerImeiNSearch"
											value="${scannerImeiNSearch}" maxlength="16" size="30">
									</td>
									<td width="10%" align="right">
										状态是否可用:
									</td>
									<td width="10%">
										<%-- 
            	<input type="text" name="statusSearch"  value="${statusSearch}" maxlength="16" size="30">
            	--%>

										<windrp:select key="YesOrNo" name="statusSearch"
											value="${statusSearch}" p="y|f" />

										<%-- 
            	<select id="statusAble" name="statusAble">
            		<option value="请选择">请选择</option>
            		<option value="可用">可用</option>
            		<option value="不可用">不可用</option>
            	</select>
            	--%>
									</td>
									<td width="10%" align="right">
										安装日期:
									</td>
									<td width="10%">
										<input type="text" name="installDateSearch"
											value="${installDateSearch}" maxlength="16" size="30"
											onFocus="javascript:selectDate(this)" readonly>
									</td>
								</tr>
								<tr class="table-back">
									<td width="10%" align="right">
										软件版本:
									</td>
									<td width="10%">
										<input type="text" name="appVersionSearch"
											value="${appVersionSearch}" maxlength="16" size="30">
									</td>
									<td width="10%" align="right">
										更新日期:
									</td>
									<td width="10%">
										<input type="text" name="appVerUpDateSearch"
											value="${appVerUpDateSearch}" maxlength="16" size="30"
											onFocus="javascript:selectDate(this)" readonly>
									</td>
									<%-- 
            <td width="10%" align="right">仓库代码:</td>
          	<td width="10%"><input type="text" name="wHCodeSearch"  value="${wHCodeSearch }" maxlength="16" size="30"></td>
          --%>
									<td></td>
									<td></td>
								</tr>
								<tr class="table-back">
									<td width="5%" align="right">
										名字:
									</td>
									<td width="10%">
										<input type="text" name="scannerNameSearch"
											value="${scannerNameSearch }" maxlength="16" size="30">
									</td>
									<td width="10%" align="right">
										最后更新日期:
									</td>
									<td width="10%">
										<input type="text" name="lastUpDateSearch"
											value="${lastUpDateSearch}" maxlength="16" size="30"
											onFocus="javascript:selectDate(this)" readonly>
									</td>
									<td></td>
									<td width="5%" class="SeparatePage" align="right">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/scanner/toAddScannerAction.do">
									<td width="50">
										<a href="javascript:addNew();"><img
												src="../images/CN/addnew.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;新增</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/scanner/toUpdateScannerAction.do">
									<td width="50">
										<a href="javascript:Update();"><img
												src="../images/CN/update.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;修改</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/scanner/delScannerAction.do">
									<td width="50">
										<a href="javascript:Del();"><img
												src="../images/CN/delete.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;删除</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/scanner/toImportScannerAction.do">
									<td width="50">
										<a href="javascript:Import();"><img
												src="../images/CN/import.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;导入</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/sys/excPutScannerAction.do">
									<td width="50">
										<a href="javascript:OutPut();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;导出</a>
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../sys/toListScannerAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">
								<tr align="center" class="title-top">
									<td width="7%">
										编号
									</td>
									<td width="10%">
										型号
									</td>
									<td width="8%">
										系统版本
									</td>
									<td width="11%">
										采集器编号
									</td>
									<td width="5%">
										状态
									</td>
									<td width="12%">
										安装日期
									</td>
									<td width="7%">
										软件版本
									</td>
									<td width="12%">
										更新日期
									</td>
									<%-- 
            <td width="10%" >仓库代码</td>
            --%>
									<td width="5%">
										名字
									</td>
									<td width="13%">
										最后更新日期
									</td>
								</tr>
								<%-- 输出Scanner表中的数据 --%>
								<logic:iterate id="p" name="scanner">
									<tr class="table-back-colorbar"
										onClick="CheckedObj(this,'${p.id}','${p.scannerImeiN}');">
										<td>
											${p.id}
										</td>
										<td>
											${p.model}
										</td>
										<td>
											${p.osVersion}
										</td>
										<td>
											${p.scannerImeiN}
										</td>
										<td>
											<windrp:getname key='YesOrNo' value='${p.status}' p='f' />
										</td>
										<%--
            <td>${p.installDate}</td>
             --%>
										<td>
											<windrp:dateformat value='${p.installDate}' p='yyyy-MM-dd' />
										</td>
										<td>
											${p.appVersion}
										</td>
										<%-- 
            <td>${p.appVerUpDate}</td>
           --%>
										<td>
											<windrp:dateformat value='${p.appVerUpDate}' p='yyyy-MM-dd' />
										</td>
										<%-- 
            <td>${p.wHCode}</td>
           --%>
										<td>
											${p.scannerName}
										</td>
										<%-- 
            <td>${p.lastUpDate}</td>
			--%>
										<td>
											<windrp:dateformat value='${p.lastUpDate}' p='yyyy-MM-dd' />
										</td>
									</tr>
								</logic:iterate>
							</table>
						</form>
					</div>
		</table>
		<br>
		<form name="excputform" method="post" action="excPutScannerAction.do">
			<input type="hidden" name="idSearch" id="idSearch"
				value="${idSearch}">
			<input type="hidden" name="modelSearch" id="modelSearch"
				value="${modelSearch}" />
			<input type="hidden" name="osVersionSearch" id="osVersionSearch"
				value="${osVersionSearch}" />
			<input type="hidden" name="statusSearch" id="statusSearch"
				value="${statusSearch}" />
			<input type="hidden" name="installDateSearch" id="installDateSearch"
				value="${installDateSearch}" />
			<input type="hidden" name="appVersionSearch" id="appVersionSearch"
				value="${appVersionSearch}" />
			<input type="hidden" name="appVerUpDateSearch"
				id="appVerUpDateSearch" value="${appVerUpDateSearch}" />
			<%-- 
<input type="hidden" name="wHCodeSearch" id ="wHCodeSearch" value="${wHCodeSearch}"/>
--%>
			<input type="hidden" name="scannerNameSearch" id="scannerNameSearch"
				value="${scannerNameSearch}" />
			<input type="hidden" name="lastUpDateSearch" id="lastUpDateSearch"
				value="${lastUpDateSearch}" />
			<input type="hidden" name="scannerImeiNSearch"
				id="scannerImeiNSearch" value="${scannerImeiNSearch}" />

		</form>
	</body>
</html>
