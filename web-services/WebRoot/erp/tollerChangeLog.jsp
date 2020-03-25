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
function reProcessSapFile(id, fileType) {

	document.getElementById("listform").action = "reProcessSapFileAction.do?uploadId="+id+"&reProFileType="+fileType;
	document.getElementById("listform").submit();
}
	
function download(obj){
	document.location="../common/downloadfile.jsp?filename=upload\fail\\"+obj;
}
	
function SelectOrgan(){

		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");

	}
function doSearch(){
	var category = document.getElementById("category").value
	if(category == "delivery") {
		document.getElementById("search").action="listSapDeliveryLogAction.do";
	}
	return true;
}

</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<input type="hidden" id="category" value="${category}">
	<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">

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
								<td>
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="search" method="post"
							action="../erp/tollerChangeLogAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<input name="makeOrganID" type="hidden" id="makeOrganID" value="${MakeOrganID}">
							<tr class="table-back">
								<td width="10%" align="right">
									编号：
								</td>
								<td width="25%">
									<input type="text" name="id" value="${id}"
										maxlength="30" />
								</td>
								<td width="9%" align="right">
									文件名：
								</td>
								<td width="20%">
									<input type="text" name="KeyWord" value="${KeyWord}"
										maxlength="256" />
								</td>
								<td align="right">
									处理状态：
								</td>
								<td>
									<windrp:select key="SapUploadLogStatus" name="isDeal" p="y|f" value="${isDeal}" />
								</td>
							</tr>
							<tr class="table-back">
								
								<td width="9%" align="right">
									创建人： 
								</td>
								<td width="20%">
									<input type="hidden" name="makeId" id="makeId" value="${makeId}">
									<input type="text" name="uname" id="uname" value="${uname}"
										onClick="selectDUW(this,'makeId','UploadSAPLog','ddu','makeId')" readonly>
								</td>
								<td align="right">
									创建日期：
								</td>
								<td>
								<input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
								
								<td></td>
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit" onclick="doSearch()"
										src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
							<c:choose>
								<c:when test="${category == 'delivery'}">
										<td class="SeparatePage">
											<pages:pager action="../erp/tollerChangeLogAction.do" />
										</td>
									</c:when>
									<c:otherwise>
										<td class="SeparatePage">
											<pages:pager action="../erp/tollerChangeLogAction.do" />
										</td>
									</c:otherwise>
								</c:choose>
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

								<thead>
									<tr align="center" class="title-top">
										<td width="4%">
											编号
										</td>
										<td width="12%">
											文件名
										</td>
										
										<td width="3%">
											原始文件
										</td>
										<td width="5%">
											结果文件
										</td>
										<td width="6%">
											文件类型
										</td>
										<td width="9%">
											创建日期
										</td>
										<td width="4%">
											创建人
										</td>
										<td width="4%">
											处理状态
										</td>
										<td width="5%">
											总条数
										</td>
										
										<td width="5%">
											错误条数
										</td>
								
										</tr>
								</thead>
								<logic:iterate id="u" name="sapUploadLogs">
									<tr align="center" class="table-back-colorbar"
										onClick="CheckedObj(this,${u.id});">
										<td>
											${u.id}
										</td>
										<td>
											${u.fileName}
										</td>
										<td title="${u.filePath}">
											<a
												href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${u.filePath}' />">[下载]</a>
										</td>
										<td title="${u.logFilePath}">
											<c:choose>
												<c:when test="${u.isDeal == 3}">
													<a
														href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${u.logFilePath}' />">[下载]</a>
												</c:when>
												<c:when test="${u.isDeal == 2}">
													<a
														href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${u.logFilePath}' />">[下载]</a>
												</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
										</td>
										
										<td>
											${u.sapFileType}
										</td>
										<td>
											${u.makeDate}
										</td>
										<td>
											<windrp:getname key='users' value='${u.makeId}' p='d'/>
										</td>
										<td>
											${u.status}
										</td>
										<td>
											${u.totalCount}
										</td>
										<td>
											${u.errorCount}
										</td>

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
