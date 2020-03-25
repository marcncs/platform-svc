<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>暗码上传质量报告</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
		var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	}
	
	function Del(){
		if(checkid!=""){
			if(window.confirm("您确认要删除编号为："+checkid+" 的日志吗？如果删除将永远不能恢复!")){
				popWin("../machin/delRecordAction.do?ID="+checkid,500,250);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function OutPut(){
		search.action="excputCovertErrorLogAction.do";
		search.submit();
		search.action="../sap/listCovertErrorLogAction.do";
	}
	
	function Reset(){
		if(checkid!=""){
			if(window.confirm("您确认要重置编号为："+checkid+" 的日志吗？")){
				popWin("../machin/resetRecordAction.do?ID="+checkid,500,250);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}	
function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
	}


	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
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
						<form name="search" method="post"
								action="../sap/listCovertErrorLogAction.do">
						<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
						<input name="id" type="hidden" id="id" value="${id}">
						<input name="isDetail" type="hidden" id="isDetail" value="${isDetail}">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td align="right">
									二维码：
								</td>
								<td>
									<input name="tdCode" type="text" id="tdCode" value="${tdCode}">
								</td>
								<td align="right">
									暗码：
								</td>
								<td>
									<input name="covertCode" type="text" id="covertCode" value="${covertCode}">
								</td>
								
								<td align="right">
									错误类型：
								</td>
								<td>
									<windrp:select key="CovertErrorType" name="errorType" p="y|f" value="${errorType}"/>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr class="table-back">
								<td align="right">
									上传人：
								</td>
								<td>
									<input type="hidden" name="uploadUser" id="uploadUser" value="${uploadUser}">
									<input type="text" name="uname" id="uname" value="${uname}"
										onClick="selectDUW(this,'uploadUser','CovertErrorLog','ddu','uploadUser')" readonly>
								</td>
								<td align="right">
									上传日期：
								</td>
								<td>
								<input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
								<c:choose>
									<c:when test="${isDetail eq 1}">
										<td></td>
										<td></td>
									</c:when>
									<c:otherwise>
										<td align="right">
											上传日志编号：
										</td>
										<td>
											<input type="text" name="uploadPrId" maxlength="60" value="${uploadPrId}">
										</td>
									</c:otherwise>
								</c:choose>
								
								<td></td>
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit"
										src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
							
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/sap/excputCovertErrorLogAction.do">
									<td width="50">
										<a href="#" onClick="javascript:OutPut();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
								</ws:hasAuth>
								<%-- 
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:Reset();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;重置</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								--%>
								<td class="SeparatePage">
									<pages:pager action="../sap/listCovertErrorLogAction.do" />
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
							<td>编号</td>
							<td>二维码</td>
							<td>暗码</td>
							<td>错误类型</td>
							<td>生产线</td>
							<td>上传人</td>
							<td>上传时间</td>
							<td>上传日志编号</td>
							</tr>
							<logic:iterate id="p" name="logs">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${p.id}');">
									<td>${p.id}</td>
									<td>${p.tdCode}</td>
									<td>${p.covertCode}</td>
									<td><windrp:getname key='CovertErrorType' value='${p.errorType}' p='f'/></td>
									<td>${p.productionLine}</td>
									<td><windrp:getname key='users' value='${p.uploadUser}' p='d'/></td>
									<td><windrp:dateformat value="${p.uploadDate}" p="yyyy-MM-dd HH:mm:ss"/></td>
									<td>${p.covertUploadId}</td>
									
<%--									<td><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>--%>
<%--									<td><windrp:getname key='DealState' value='${p.isdeal}' p='f'/></td>--%>
								</tr>
							</logic:iterate>
						</table>
					</div>
				</td>
			</tr>
		</table>

	</body>
</html>
