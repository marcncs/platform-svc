<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js">
	
</SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js">
	
</SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js">
	
</SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery.js">
	
</SCRIPT>
		<script language="JavaScript">
	var checkid = 0;
	var checkcname = "";
	var pdmenu = 0;
	var idCode = "";
	function CheckedObj(obj, objid, objidcode) {

		for (i = 0; i < obj.parentNode.childNodes.length; i++) {
			obj.parentNode.childNodes[i].className = "table-back-colorbar";
		}

		obj.className = "event";
		checkid = objid;
		idCode = objidcode;
		Detail();
	}


	function addNew(objidcode) {
		idCode = document.search.idCode.value;
		if (idCode != "") {
			window
					.open(
							"../assistant/toAddTrackApplyAction.do?idCode="
									+ idCode,
							"",
							"height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		} else {
			alert("请在查询码中输入你要新增的物流码!");
		}
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
				window
						.open(
								"../scanner/delScannerAction.do?ID=" + checkid,
								"",
								"height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
			}
		} else {
			alert("请选择你要操作的记录!");
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
	function Detail() {
		if (checkid != "") {
			document.all.submsg2.src = "../assistant/viewTrackApplyDetailAction.do?ID="
					+ idCode;
		} else {
			alert("请选择你要操作的记录!");
		}
	}
	this.onload = function listdiv() {
		document.getElementById("listdiv").style.height = (document.body.clientHeight - document
				.getElementById("bodydiv").offsetHeight)
				+ "px";
	}
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.applyOrgId.value=p.id;
			document.search.oname.value=p.organname;
	}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									${menusTrace}
									<input type="hidden" name="ID" value="${id}">
								</td>
							</tr>
						</table>
						<form name="search" method="post"
							action="../assistant/listViewTrackApplyAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td width="9%" align="right">
										制单机构：
									</td>
									<td width="20%">
										<input name="applyOrgId" type="hidden" id="applyOrgId"
											value="${applyOrgId}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
									</td>
									<td width="10%" align="right">
										日志日期：
									</td>
									<td width="25%">
										<input id="BeginDate" onFocus="javascript:selectDate(this)" size="12"
											name="BeginDate" value="${BeginDate}" readonly="readonly">
										-
										<input id="EndDate" onFocus="javascript:selectDate(this)" size="12"
											name="EndDate" value="${EndDate}" readonly="readonly">
									</td>
									<td width="8%" align="right">
										查询码:
									</td>
									<td width="330px">
										<input type="text" id="idCodeSearch" name="idCode"
											value="${idCode}" maxlength="20" size="58">
									</td>
									<td width="1%"></td>
									<td class="SeparatePage1">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/assistant/toAddTrackApplyAction.do">
									<td width="50">
										<a href="javascript:addNew('${idCodeSearch}');"><img
												src="../images/CN/addnew.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;新增</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../assistant/listViewTrackApplyAction.do" />
								</td>
							</tr>
						</table>
					</div>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">
								<tr align="center" class="title-top">
									<td >
										编号
									</td>
									<td >
										申请机构
									</td>
									<td >
										申请人
									</td>
									<%--
            <td width="5%">码类别</td>
             --%>
									<td >
										申请日期
									</td>
									<td >
										查询码
									</td>
									<!--<td width="4%">
										箱号
									</td>
									<td width="4%">
										发货日期
									</td>
									<td width="4%">
										SAP发货单号
									</td>
									<td width="4%">
										客户名称
									</td>
									-->
									<!--<td width="7%">
										状态
									</td>
								-->
								</tr>
								<logic:iterate id="p" name="TrackApply">
									<tr class="table-back-colorbar"
										onClick="CheckedObj(this,'${p.id}','${p.idCode}');">
										<td>
											${p.id}
										</td>
										<%--
            <td>${p.applyOrgId}</td>
            --%>
										<td>
											<windrp:getname key='organ' value='${p.applyOrgId}' p='d' />
										</td>
										<%--
            <td>${p.applyUserId}</td>
            --%>
										<td>
											<windrp:getname key='users' value='${p.applyUserId}' p='d' />
										</td>
										<%--
            <td>${p.codeType}</td>
             --%>
										<td>
											${p.createDate}
										</td>
										<td>
											${p.idCode}
										</td>
										<!--
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										-->
										<!--<td>
										<windrp:getname key="TaskPlanStatus" value="${p.status}" p="f"/>
										</td>
									-->
									</tr>
								</logic:iterate>
							</table>
						</Form>

						<div style="width: 100%">
							<div id="tabs1">
								<ul>
									<li>
										<a href="javascript:Detail();"><span>查询码详情</span> </a>
									</li>
								</ul>
							</div>
							<div>
								<IFRAME id="submsg2"
									style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
									name="submsg2" src="../sys/remind.htm" frameBorder="0" scrolling="no"
									onload="setIframeHeight(this);"></IFRAME>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
