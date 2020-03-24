<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
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
function addNew(type){
	popWin("../machin/toUploadProduceReportAction.do?type="+type,600,250);
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
								action="../machin/listRecordAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td align="right">
									制单机构：
								</td>
								<td>
									<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
									<input name="oname" type="text" id="oname" size="30"  value="${oname}"
									readonly><a href="javascript:SelectOrgan();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>

								</td>
								<td align="right">
									制单人：
								</td>
								<td>
									<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
									<input type="text" name="uname" id="uname" value="${uname}"
										onClick="selectDUW(this,'MakeID',$F('MakeOrganID'),'ou')" readonly>
								</td>
								<td align="right">
									处理状态：
								</td>
								<td>
									<windrp:select key="DealState" name="IsDeal" p="y|f" value="${IsDeal}"/>
								</td>
								<td></td>
							</tr>
							<tr class="table-back">
								<td align="right">
									文件名称：
								</td>
								<td>
									<input type="text" name="FileName" maxlength="60" value="${filename}">
								</td>
								<td align="right">
									关键字：
								</td>
								<td>
									<input type="text" name="KeyWord" maxlength="60" value="${KeyWord}">
								</td>
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
							<ws:hasAuth operationName="/machin/toUploadProduceReportAction.do">
								<td width="80"><a href="javascript:addNew(1);"><img	src="../images/CN/upload.gif" width="16" height="16"	border="0" align="absmiddle">&nbsp;暗码上传</a>
								</td>
								
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
							<ws:hasAuth operationName="/machin/delRecordAction.do">
								<td width="50">
									<a href="javascript:Del();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<%-- <td width="50">
									<a href="javascript:Reset();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;重置</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								--%>
								<td class="SeparatePage">
									<pages:pager action="../machin/listRecordAction.do" />
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
							<td width="6%">编号</td>
							<td>文件名称</td>
							<td width="8%">原始文件下载</td>
							<td width="12%">制单机构</td>
							<td width="9%">制单人</td>
							<td width="130px;">制单日期</td>
							<td width="7%">日志文件</td>
							<td width="50px;">总记录数</td>
							<td width="50px;">错误条数</td>
							<td width="60px;">处理状态</td>
							</tr>
							<logic:iterate id="p" name="list">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${p.id}');">
									<td>${p.id}</td>
									<td>${p.filename}</td>
									<td align="center" title="点击下载">
<!--										<img src="../images/CN/beizheng.gif" border="0">-->
										<a href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${p.filepath}' />">[下载]</a>
									</td>
									<td><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>
									<td><windrp:getname key='users' value='${p.makeid}' p='d'/></td>
									<td><windrp:dateformat value="${p.makedate}" p="yyyy-MM-dd HH:mm:ss"/></td>
									<td align="center" title="点击下载">
										<c:if test="${p.isdeal == -1 or p.errorCount > 0}">
											<a href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${p.logFilePath}' />">[下载]</a>
										</c:if>
									</td>
									<td>${p.totalCount}</td>
									<td>${p.errorCount==null?0:p.errorCount}</td>
									<td><windrp:getname key='DealState' value='${p.isdeal}' p='f'/></td>
								</tr>
							</logic:iterate>
						</table>
					</div>
				</td>
			</tr>
		</table>

	</body>
</html>
