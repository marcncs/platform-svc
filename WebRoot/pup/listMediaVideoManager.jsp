<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="../common/tag.jsp"%>
<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>WINDRP-分销系统</title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/function.js">
	
</SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js">
	
</SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js">
	
</SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/function.js">
	
</SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js">
	
</SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js">
	
</SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xmlextras.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xloadtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/treeselect.js"></SCRIPT>
<script language="JavaScript">
	function addNew() {
		popWin("../yun/toAddMediaVideoAction.do", 600, 300);
	}

	function editMV(pid) {
		if (pid) {
			popWin("../yun/toEditMediaVideoAction.do?MVID=" + pid, 1280, 720);
		} else {
			alert("请选择数据!");
		}
	}
	function editCover(pid) {
		if (pid) {
			//popWin("../mfr_admin/change_cover.jsp?MVID=" + pid, 1280, 720);
			popWin("../qr/api/product/videos/updcover/" + pid, 1280, 720);
			
		} else {
			alert("请选择数据!");
		}
	}

	function deleteMV(pid) {
		if (pid) {
			if (confirm("你确认要删除编号为:" + pid + "的视频吗?")) {
				var url = '../yun/deleteMediaVideoAction.do';
				var pars = "MVID=" + pid;
				var myAjax = new Ajax.Request(url, {
					method : 'get',
					parameters : pars,
					onComplete : function(data) {
						location.reload(true);
					}
				});
			}
		} else {
			alert("请选择数据!");
		}
	}

	function playMV(pid) {
		popWin("../yun/toPlayMediaVideoAction.do?MVID=" + pid, 1280, 720);
	}

	function publishMV(pid, status) {
		if (pid) {
			if (status == true) {
				if (confirm("你确认要发布编号为:" + pid + "的视频吗?")) {
					var url = '../yun/publishMediaVideoAction.do';
					var pars = "MVID=" + pid + "&STATUS=" + status;
					var myAjax = new Ajax.Request(url, {
						method : 'get',
						parameters : pars,
						onComplete : function(data) {
							document.forms[0].submit();
							alert("操作成功");
						}
					});
				}
			} else {
				if (confirm("你确认要取消发布编号为:" + pid + "的视频吗?")) {
					var url = '../yun/publishMediaVideoAction.do';
					var pars = "MVID=" + pid + "&STATUS=" + status;
					var myAjax = new Ajax.Request(url, {
						method : 'get',
						parameters : pars,
						onComplete : function(data) {
							document.forms[0].submit()
							alert("操作成功");
						}
					});
				}
			}
		} else {
			alert("请选择数据!");
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
							<td width="772">云单页>>视频管理
						</tr>
					</table>
					<form name="search" method="post"
						action="../yun/listMediaVideoManagerAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">

							<tr class="table-back">
								<td width="8%" align="right">关键字：</td>
								<td width="16%"><input type="text" name="KeyWord"
									value="${KeyWord}" maxlength="30"></td>
								<td width="9%" align="right"></td>
								<td width="11%"></td>
								<td width="9%" align="right"></td>
								<td width="11%"></td>
								<td width="4%" class="SeparatePage"><input name="Submit"
									type="image" id="Submit" src="../images/CN/search.gif"
									border="0" title="查询"></td>
							</tr>

						</table>
					</form>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">
							<td width="100"><a href="javascript:addNew();"><img
									src="../images/CN/addnew.gif" width="16" height="16" border="0"
									align="absmiddle">&nbsp;新增视频</a></td>
							<td width="1"><img src="../images/CN/hline.gif" width="2"
								height="14"></td>
							<td class="SeparatePage"><pages:pager
									action="../yun/listMediaVideoManagerAction.do" /></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td>


				<table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						bordercolor="#BFC0C1">
						<tr>
							<td>
								<div id="listdiv" style="overflow-y: auto; height: 600px;">
									<table class="sortable" width="100%" border="0" cellpadding="0"
										cellspacing="1">
										<tr align="center" class="title-top">
											<!-- <td width="16px" class="sorttable_nosort"><input
												type="checkbox" id="checkAll" onclick="checkAll()"></td> -->
											<td width="3%">编号</td>
											<td width="20%">视频标题</td>
											<td >视频简介</td>
											<td width="5%">视频大小</td>
											<!-- <td style="width: auto;">评论状态</td> -->
											<td width="5%">是否发布</td>
											<td width="10%">上传日期</td>
											<td width="15%">操作</td>
										</tr>
										<logic:iterate id="p" name="mediaVideos">
											<%-- <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}','${p.name}');"> --%>
											<tr class="table-back-colorbar">
												<%-- <td width="16px"><input type="checkbox" value="${p.id}"
													name="pid"></td> --%>
												<td>${p.id}</td>
												<td>${p.title }</td>
												<td width="800px">${p.digest }</td>
												<td>${p.size}</td>
												<%-- <td>${p.auditStatusName}</td> --%>
												<td><c:if test="${p.isPublish == 0}">未发布</c:if> <c:if
														test="${p.isPublish == 1}">已发布</c:if></td>
												<td>${p.createTime}</td>
												<td><ws:hasAuth
														operationName="/yun/toEditMediaVideoAction.do">
														<a width="100" href="javascript:editMV('${p.id}');">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp; 
									</ws:hasAuth> <ws:hasAuth operationName="/yun/publishMediaVideoAction.do">
														<c:if test="${p.isPublish == 0}">
															<a width="100"
																href="javascript:publishMV('${p.id}' , '1');">发布</a>&nbsp;&nbsp;&nbsp;&nbsp;
										</c:if>
														<c:if test="${p.isPublish == 1}">
															<a width="100"
																href="javascript:publishMV('${p.id}' , '0');">取消发布</a>&nbsp;&nbsp;&nbsp;&nbsp;
										</c:if>
													</ws:hasAuth> <ws:hasAuth operationName="/yun/deleteMediaVideoAction.do">
														<a width="100" href="javascript:deleteMV('${p.id}');">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
									</ws:hasAuth> <ws:hasAuth operationName="/yun/toPlayMediaVideoAction.do">
														<a width="100" href="javascript:playMV('${p.id}');">播放视频</a>
													</ws:hasAuth>
													
													<ws:hasAuth operationName="/yun/toPlayMediaVideoAction.do">
														<a width="100" href="javascript:editCover('${p.id}');">修改封面图</a>
													</ws:hasAuth>
													
													</td>
											</tr>
										</logic:iterate>
									</table>
									<br>
								</div>
							</td>
						</tr>
					</table>
				</table>
</body>
</html>