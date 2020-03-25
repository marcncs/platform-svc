<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/prototype.js">
	
</SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js">
	
</SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js">
	
</SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
	var checkid = 0;
	function CheckedObj(obj, objid, proDate, batchNo, primaryCode, cartonCode,
			covertCode, productionLine, printDate) {

		for (i = 0; i < obj.parentNode.childNodes.length; i++) {
			obj.parentNode.childNodes[i].className = "table-back-colorbar";
		}
		obj.className = "event";
		checkid = objid;

		Detail(proDate, batchNo, primaryCode, cartonCode, covertCode,
				productionLine, printDate);
	}

	function addNew(type) {
		popWin("../machin/toUploadProduceReportAction.do?type=" + type, 600,
				250);
	}
	function exportIncome() {
		popWin("../machin/produceProductIncomeAction.do", 500, 400);
	}

	this.onload = function abc() {
		document.getElementById("abc").style.height = (document.body.clientHeight - document
				.getElementById("div1").offsetHeight)
				+ "px";
	}
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="div1">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="blank" method="get" action=""></form>
						<form name="search" method="post"
							action="../erp/listDupontPrimaryCode.do">
							<input type="hidden" name="search" id="search" value="true" />
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
									<td width="15%" align="right">
										杜邦小包装码(激光码)：
									</td>
									<td width="25%">
										<input name="primaryCode" type="text" id="primaryCode"
											value="${primaryCode}">
									</td>
									<td width="10%" align="right">
										杜邦箱码：
									</td>
									<td width="25%">
										<input name="dpCartoncode" type="text" id="dpCartoncode"
											value="${dpCartoncode}">
									</td>
									<td align="right">
										拜耳箱码：
									</td>
									<td>
										<input name="bcsCartoncode" type="text" id="bcsCartoncode"
											value="${bcsCartoncode}">
									</td>
									<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">

								<td width="80">
								</td>

								<td width="1">
								</td>
								<td class="SeparatePage">
									<pages:pager action="../erp/listDupontPrimaryCode.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top">
								<td>
									杜邦小包装码(激光码)
								</td>
								<td>
									杜邦箱码
								</td>
								<td>
									拜耳箱码
								</td>
							</tr>
							<logic:iterate id="p" name="codes">
								<tr class="table-back-colorbar">
									<td>
										${p.primaryCode}
									</td>
									<td>
										${p.dpCartoncode}
									</td>
									<td>
										${p.bcsCartoncode}
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br />
						<div style="width: 100%">
						</div>
				</td>
			</tr>
		</table>
	</body>
</html>
