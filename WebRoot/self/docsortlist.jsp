<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>文档类型</title>
		<script language="JavaScript">
	function newDocSort() {
		popWin("../self/toAddDocSortAction.do", 500, 250);
	}

	function updDocSort() {
		if(checkid == 0){
			alert("请选择你要修改的文件类型!");
		}else{
			popWin("../self/listDocSortDetailAction.do?ID="+checkid,500,250);
		}
		//popWin("../self/listUpdDocSortAction.do", 470, 250);
	}
	
	var pname="";
	var checkid =0;
	function detailDocSort(id,name){
		pname=name;
		checkid=id;
		var doc = document.getElementById("doc");
		doc.src="../self/listDocAction.do?PbsID="+id;
	
	}
	
	function Visit(){
		if(checkid!=""){
			popWin("toVisitDocSortAction.do?ID="+checkid,700,400);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid == 0){
			alert("请选择你要删除的文件类型!");
		}else{
			if(window.confirm("您确认要删除 "+pname+" 文件类型吗?")){
				popWin2("../self/delDocSortAction.do?ID="+checkid);
				checkid=0;
			}
		}
	}
	this.onload = function abc() {
		document.getElementById("div1").style.height = (document.body.offsetHeight)
				+ "px";
	}
</script>
	</head>
	<body>
		<div id="div1">
			<table width="100%" height="100%" border="0" cellpadding=""
				cellspacing="0" bordercolor="#6893CF">
				<tr>
					<td height="27" colspan="3">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									我的办公桌>>文档管理
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="25%" valign="top" style="border-right: 1px solid #D2E6FF;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="title-back">
							<tr>
								<td style="text-align: left;">
									&nbsp;&nbsp;&nbsp;文件类型
								</td>
								<td>&nbsp;
									
								</td>
							</tr>
						</table>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td colspan="2">
									<table width="100%" border="0" cellspacing="0" cellpadding="0"
										class="title-func-back">
										<tr>
											<td width="50">
												<a href="javascript:newDocSort();"><img
														src="../images/CN/addnew.gif" width="16" height="16"
														border="0" align="absmiddle">&nbsp;新增</a>
											</td>
											<td width="1">
												<img src="../images/CN/hline.gif" width="2" height="14">
											</td>
											<td width="50">
												<a href="javascript:updDocSort();"><img
														src="../images/CN/update.gif" width="16" height="16"
														border="0" align="absmiddle">&nbsp;修改</a>
											</td>
											<td width="1">
												<img src="../images/CN/hline.gif" width="2" height="14">
											</td>
											<td width="50">
												<a href="javascript:Del();"><img
														src="../images/CN/delete.gif" width="16" height="16"
														border="0" align="absmiddle">&nbsp;删除</a>
											</td>
											<td width="1">
												<img src="../images/CN/hline.gif" width="2" height="14">
											</td>
														<td width="50" align="center">
			<a href="javascript:Visit();"><img src="../images/CN/xuke.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;许可</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
											<td>&nbsp;
												
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<logic:iterate id="p" name="pbs">
								<tr>
									<td width="11%" height="16">
										<img src="../images/CN/t.gif" width="16" height="16">
									</td>
									<td width="89%" class="CC" title="点击查看详情">
										<a href="javascript:detailDocSort(${p.id},'${p.sortname}');"
											class="BB"> ${p.sortname}</a>
									</td>
								</tr>
							</logic:iterate>
						</table>
					</td>

					<td width="80%">
						<IFRAME id="doc" style="WIDTH: 100%; HEIGHT: 100%" name="doc"
							src="../self/listDocAction.do" frameBorder=0 scrolling=no></IFRAME>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
