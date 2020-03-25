<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>条码上传错误日志</title>
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
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.uploadOrganId.value=p.id;
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
								action="../warehouse/listIdcodeUploadLogAction.do">
						<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td align="right">
									单号：
								</td>
								<td>
									<input name="billNo" type="text" id="billNo" value="${billNo}">
								</td>
								<td align="right">
									单据类型：
								</td>
								<td>
									<windrp:select key="BillSort" name="bsort" p="y|f" value="${bsort}" />
								</td>
								
								<td align="right">
									上传人：
								</td>
								<td>
									<input type="hidden" name="uploadUser" id="uploadUser" value="${uploadUser}">
									<input type="text" name="uname" id="uname" value="${uname}"
										onClick="selectDUW(this,'uploadUser',$F('MakeOrganID'),'ou')" readonly>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr class="table-back">
								<td align="right">
									上传机构：
								</td>
								<td>
									<input name="uploadOrganId" type="hidden" id="uploadOrganId" value="${uploadOrganId}">
									<input name="oname" type="text" id="oname" size="30"  value="${oname}"
									readonly><a href="javascript:SelectOrgan();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>
								</td>
								<td align="right">
									错误类型：
								</td>
								<td>
									<windrp:select key="IdcodeErrorType" name="errCode" p="y|f" value="${errCode}" />
								</td>
								
								<td align="right">
									上传条码：
								</td>
								<td>
									<input name="idcode" type="text" id="idcode" value="${idcode}">
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr class="table-back">
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
								<td align="right">
									上传日志编号：
								</td>
								<td>
									<input type="text" name="idcodeUploadId" maxlength="60" value="${idcodeUploadId}">
								</td>
								<td align="right">关键字：</td>
								<td width="19%" ><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}"></td>
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
								<%-- <td width="50">
									<a href="javascript:Del();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
								</td>
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
									<pages:pager action="../warehouse/listIdcodeUploadLogAction.do" />
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
							<td>单号</td>
							<td>单据类型</td>
							<td>上传条码</td>
							<td>上传人</td>
							<td>上传机构</td>
							<td>上传仓库</td>
							<td>上传时间</td>
							<td>错误类型</td>
							<td>错误信息</td>
							<td>上传日志编号</td>
							</tr>
							<logic:iterate id="p" name="logs">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${p.id}');">
									<td>${p.id}</td>
									<td>${p.billNo}</td>
									<td><windrp:getname key='BillSort' value='${p.bsort}' p='f'/></td>
									<td>${p.idcode}</td>
									<td><windrp:getname key='users' value='${p.uploadUser}' p='d'/></td>
									<td><windrp:getname key='organ' value='${p.uploadOrganId}' p='d'/></td>
									<td><windrp:getname key='warehouse' value='${p.uploadWarehouseId}' p='d' /></td>
									<td><windrp:dateformat value="${p.uploadDate}" p="yyyy-MM-dd"/></td>
									<td><windrp:getname key='IdcodeErrorType' value='${p.errCodeInt}' p='f'/></td>
									<td>${p.errMsg}</td>
									<td>${p.idcodeUploadId}</td>
									
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
