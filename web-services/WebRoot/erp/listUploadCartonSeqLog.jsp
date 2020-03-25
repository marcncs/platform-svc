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

function download(obj){
	document.location="../common/downloadfile.jsp?filename=upload\fail\\"+obj;
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
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="search" method="post"
							action="../erp/listUploadCartonSeqLogAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td width="10%" align="right">
										文件名：
									</td>
									<td width="25%">
										<input type="text" name="KeyWord" value="${KeyWord}"
											maxlength="256" />
									</td>
									<td width="9%" align="right">
										处理状态：
									</td>
									<td width="20%">
										<windrp:select key="SapUploadLogStatus" name="isDeal" p="y|f"
											value="${isDeal}" />
									</td>
									<td align="right">
										创建日期：
									</td>
									<td>
										<input name="BeginDate" type="text" id="BeginDate" size="10"
											value="${BeginDate}" onFocus="javascript:selectDate(this)"
											readonly>
										-
										<input name="EndDate" type="text" id="EndDate" size="10"
											value="${EndDate}" onFocus="javascript:selectDate(this)"
											readonly>
									</td>
									<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0"
											title="查询">
									</td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td class="SeparatePage">
									<pages:pager action="../erp/listUploadCartonSeqLogAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<FORM METHOD="POST" id="listform" name="listform">
							<table class="sortable" width="100%" cellpadding="0"
								cellspacing="1" bordercolor="#dddddd">

								<thead>
									<tr align="center" class="title-top">
										<td>
											编号
										</td>
										<td>
											生产计划编号
										</td>
										<td>
											文件名
										</td>
										<td>
											原始文件
										</td>
										<td>
											错误信息文件
										</td>
										<td>
											总数量
										</td>
										<td>
											错误数量
										</td>
										<td>
											创建日期
										</td>
										<td>
											创建人
										</td>
										<td>
											处理状态
										</td>
									</tr>
								</thead>
								<logic:iterate id="u" name="logList">
									<tr align="center" class="table-back-colorbar"
										onClick="CheckedObj(this,${u.id});">
										<td>
											${u.id}
										</td>
										<td>
											${u.plno}
										</td>
										<td>
											${u.filename}
										</td>
										<td title="${u.filepath}">
											<a
												href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${u.filepath}' />">[下载]</a>
										</td>
										<td title="${u.logfilepath}">
											<c:choose>
												<c:when test="${u.isdeal == 3 or u.errorcount > 0}">
													<a
														href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${u.logfilepath}' />">[下载]</a>
												</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
										</td>
										<td>
											${u.totalcount}
										</td>
										<td>
											${u.errorcount}
										</td>
										<td>
											${u.makedate}
										</td>
										<td>
											<windrp:getname key='users' value='${u.makeid}' p='d' />
										</td>
										<td>
											<windrp:getname key='SapUploadLogStatus' value='${u.isdeal}' p='f' />
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
