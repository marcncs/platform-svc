<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<script language="JavaScript">  
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
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
function addNew(){
	popWin("../warehouse/toAddTransferLogAction.do",500,300);
	//window.open("../warehouse/toAddTransferLogAction.do","","height=200,width=500,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
}
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">

	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		bordercolor="#BFC0C1">
		<tr>
			<td>
				<div id="bodydiv">
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10"><img src="../images/CN/spc.gif" width="10"
								height="1"></td>
							<td>${menusTrace }</td>
						</tr>
					</table>
					<form name="search" method="post"
						action="../warehouse/listTransferLogAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">

							<tr class="table-back">
								<td width="9%" align="right">文件类型：</td>
								<td width="25%"><windrp:select key="TransferFileType" name="fileType" p="y|f"
										value="${fileType}" /></td>
								<td width="8%" align="right">传输状态：</td>
								<td><windrp:select key="TransferFileStatus" name="status" p="y|f"
										value="${status}" /></td>
								<td align="right">关键字：</td>
								<td width="19%"><input name="KeyWord" type="text"
									id="KeyWord" value="${KeyWord}"></td>
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit"
										src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
						</table>
					</form>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">
							<ws:hasAuth operationName="/warehouse/toAddTransferLogAction.do">
								<td width="50">
									<a href="javascript:addNew();"><img src="../images/CN/addnew.gif"
											width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a>
								</td>
							</ws:hasAuth>
							<td class="SeparatePage"><pages:pager
									action="../warehouse/listTransferLogAction.do" /></td>

						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div id="listdiv" style="overflow-y: auto; height: 600px;">
					<FORM METHOD="POST" name="listform" ACTION="">
						<table class="sortable" width="100%" cellpadding="0"
							cellspacing="1" bordercolor="#dddddd">

							<tr align="center" class="title-top-lock">
								<td>编号</td>
								<td>文件名称</td>
								<td>文件类型</td>
								<td>生成日期</td>
								<td>文件</td>
								<td>传输状态</td>
							</tr>
							<logic:iterate id="u" name="alpi">
								<tr align="center" class="table-back-colorbar"
									onClick="CheckedObj(this,${u.id});">
									<td>${u.id}</td>
									<td>${u.fileName}</td>
									<td><windrp:getname key="TransferFileType"
											value="${u.fileType}" p='f' /></td>
									<td><windrp:dateformat value='${u.makeDate}' /></td>
									<td><a
										href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${u.filePath}' />">下载</a></td>
									<td><windrp:getname key="TransferFileStatus"
											value="${u.status}" p='f' /></td>
								</tr>
							</logic:iterate>

						</table>
					</form>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>
