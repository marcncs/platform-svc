<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>可选择合同列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/prototype.js">
	
</SCRIPT>
<SCRIPT language="javascript" src="../js/function.js">
	
</SCRIPT>
<SCRIPT language="javascript" src="../js/selectDate.js">
	
</SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js">
	
</SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js">
	
</SCRIPT>
<script type="text/javascript">
	this.onload = function onLoadDivHeight() {
		document.getElementById("listdiv").style.height = (document.body.offsetHeight - document
				.getElementById("bodydiv").offsetHeight)
				+ "px";
	}
	var checkid = "";
	var bfcwid = "";
	var pisused = "";
	function CheckedObj(obj, objid, fcid, isused) {
		for (i = 0; i < obj.parentNode.childNodes.length; i++) {
			obj.parentNode.childNodes[i].className = "table-back-colorbar";
		}

		obj.className = "event";
		//合同主键
		checkid = objid;
		//标签防伪码
		bfcwid = fcid;
		//合同防伪码字段
		pisused = isused;
	}

	function Affirm(){
			if(checkid!=""){
				if(pisused!="") {
					alert("此合同已被选用，请重新选择!");
				} else {
					$.ajax({
						type :'post',
						url :'../pact/bindpactajaxfatieAction.do', 
						data:{"id":checkid,"fwmcode":bfcwid} ,
						dataType : 'json',
						success : function(result) {
							if(result.success=="true"){
								alert("添加合同成功！");
								 window.close();
							 }else{
								 alert("添加合同失败！");
							 }
						}
					});
				}
			}else{
				alert("请选择你要操作的记录!");
			}
		}
</script>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		bordercolor="#BFC0C1">
		<tr>
			<td>
				<div id="bodydiv">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="title-back">
						<tr>
							<td width="10"><img src="../images/CN/spc.gif" width="10"
								height="1"></td>
							<td width="772">合同列表</td>

						</tr>
					</table>
					<%-- <form name="search" method="post"
						action="../pact/popListpactAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="table-back">
								<td width="10%" align="right">标签编号：</td>
								<td width="15%"><input name="fwmCodeFile" type="text"
									id="fwmCodeFile" value="${fwmCodeFile}"></td>
								<td width="10%" align="right">企业名称：</td>
								<td width="15%"><input name="companyName" type="text"
									id="companyName" value="${companyName}"></td>
								<td width="10%" align="right">合同单号：</td>
								<td width="15%"><input id="contractNo" name="contractNo"
									value="${contractNo}"></input>
							    </td>
								<td width="10%" align="right"><input name="Submit2"
									type="image" id="Submit" src="../images/CN/search.gif"
									border="0" align="right"></td>
							</tr>
						</table>
					</form> --%>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">
							<td width="50">
									<a href="#" onClick="javascript:Affirm();"><img
											src="../images/CN/addnew.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;确定</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="#" onClick="javascript:window.close();"><img
											src="../images/CN/cancelx.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;取消</a>
								</td>
							<td class="SeparatePage"><pages:pager
									action="../pact/popListpactAction.do" /></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div id="listdiv" style="overflow-y: auto; height: 600px;">
					<FORM METHOD="POST" name="listform" ACTION="">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top">
								<td>标签编号</td>
								<td>产品编号</td>
								<td>产品名称</td>
								<td>企业名称</td>
								<td>合同单号</td>
							</tr>
							<logic:iterate id="c" name="list">
								<tr class="table-back-colorbar" onClick="CheckedObj(this,'${c.id}','${fc }','${c.fwmCodeFile}');" ondblclick="Affirm();">
									<td align="center">${c.fwmCodeFile}</td>
									<td align="center">${c.productId}</td>
									<td align="center">${c.productName}</td>
									<td align="center">${c.companyName}</td>
									<td align="center">${c.contractNo}</td>
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

