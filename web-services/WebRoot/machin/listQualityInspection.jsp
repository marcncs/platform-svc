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
function reProcessSapFile(id) {
	
	document.getElementById("search").action = "reProcessProduceFileAction.do?uploadId="+id;
	//document.getElementById("listform").submit();
	document.getElementById("search").submit();
	document.getElementById("search").action ="../machin/listProduceUploadAction.do";
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

this.onload =function onLoadDivHeight(){
	//document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	  document.getElementById("listdiv").style.height = (document.body.clientHeight - document.getElementById("bodydiv").offsetHeight)+"px";
}

function addNew(type){
	popWin("../machin/toAddQualityInspectionAction.do",800,300);
}

function Del(){
	if(checkid>0){
		if ( confirm("你确认要删除编号为:"+checkid+"的记录吗?如果删除将不能恢复!") ){
			popWin2("../machin/delQualityInspectionAction.do?ID="+checkid);
		}
	}else{
		alert("请选择你要操作的记录!");
	}
}

function Upd(){
	if(checkid>0){
	popWin("../machin/toUpdQualityInspectionAction.do?id="+checkid,900,600);
	}else{
	alert("请选择你要操作的记录!");
	}
	
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
							action="../machin/listQualityInspectionAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<input name="makeOrganID" type="hidden" id="makeOrganID"
									value="${MakeOrganID}">
								<input type="hidden" id="category" value="${category}">
								<tr class="table-back">
									<td width="10%" align="right">
										关键字：
									</td>
									<td width="25%">
										<input type="text" name="KeyWord" value="${KeyWord}"
											maxlength="256" />
									</td>
									<td width="9%" align="right">
										是否合格：
									</td>
									<td width="20%">
										<windrp:select key="YesOrNo" name="isQualified" p="y|f"
											value="${isQualified}" />
									</td>
									<td align="right">
										检验时间：
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
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth
									operationName="/machin/toAddQualityInspectionAction.do">
									<td width="50">
										<a href="javascript:addNew();"><img
												src="../images/CN/addnew.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;新增</a>
									</td>

									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth
									operationName="/machin/toUpdQualityInspectionAction.do">
									<td width="50">
										<a href="javascript:Upd();"><img
												src="../images/CN/addnew.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;修改</a>
									</td>

									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/machin/delQualityInspectionAction.do">
									<td width="50" align="center">
										<a href="javascript:Del();"><img
												src="../images/CN/delete.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;删除</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../machin/listProduceUploadAction.do" />
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
											物料号
										</td>
										<td>
											批次
										</td>
										<td>
											检验人
										</td>
										<td>
											检验时间
										</td>
										<td>
											是否合格
										</td>
										<td>
											创建人
										</td>
										<td>
											创建日期
										</td>
										<td>
											检验报告
										</td>
									</tr>
								</thead>
								<logic:iterate id="u" name="qiList">
									<tr align="center" class="table-back-colorbar"
										onClick="CheckedObj(this,${u.id});">
										<td>
											${u.id}
										</td>
										<td>
											${u.mCode}
										</td>
										<td>
											${u.batch}
										</td>
										<td>
											${u.inspector}
										</td>
										<td>
											${u.inspectDate}
										</td>
										<td>
											<windrp:getname key='YesOrNo' value='${u.isQualified}' p='f' />
										</td>
										<td>
											<windrp:getname key='users' value='${u.makeId}' p='d' />
										</td>
										<td>
											${u.makeDate}
										</td>
										<td title="${u.filePath}">
											<c:if test="${u.filePath != null}">
												<a
													href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${u.filePath}' />">[下载]</a>
											</c:if>
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
