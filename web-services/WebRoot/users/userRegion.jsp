<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>

	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
		<script language="javascript">

function Del(){
	var cks = [];
	$('input[name="id"]:checked').each(function(i){
		cks.push($(this).val());
	});
	if (cks.length > 0) {
		if(confirm("你确定要删除机构权限吗？")){
			popWin("../users/userRegionPageAction.do?op=delete&id="+cks.join(),500,250);
		}
	} else {
		alert("请选择你要操作的记录!");
	}
}
function toSet(){
	var userId = $('input[name="userId"]').val();
	popWin("../users/userRegionPageAction.do?op=addPage&userId="+userId,800,500);
}
//全选框点击事件
function check(){
	if ($('#checkAll').prop('checked')) {
		$(':checkbox').prop('checked', true);
	} else {
		$(':checkbox').prop('checked', false);
	}
}

</script>
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<table width="100%" height="40" border="0" cellpadding="0"
									cellspacing="0" class="title-back">
									<tr>
										<td width="10">
											<img src="../images/CN/spc.gif" width="10" height="1">
										</td>
										<td width="772">
											用户关联区域
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>


								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr class="title-func-back">
										<!-- yzj 20100407 start -->
										<td width="70" align="center">
											<a href="javascript:toSet();"><img
													src="../images/CN/addnew.gif" width="16" height="16"
													border="0" align="absmiddle">&nbsp;增加区域</a>
													<input type="hidden" name="userId" value="${requestScope.userId}">
										</td>
										<td width="1">
											<img src="../images/CN/hline.gif" width="2" height="14">
										</td>
										<td width="50" align="center">
											<a href="javascript:Del();"><img
													src="../images/CN/delete.gif" width="16" height="16"
													border="0" align="absmiddle">&nbsp;删除</a>
										</td>
										<td width="1">
											<img src="../images/CN/hline.gif" width="2" height="14">
										</td>
										
										<%--<td width="50" align="center">
											<a href="javascript:importFile()"><img
													src="../images/CN/import.gif" width="16" height="16"
													border="0" align="absmiddle">&nbsp;导入</a>
										</td>
										<td width="1">
											<img src="../images/CN/hline.gif" width="2" height="14">
										</td>
										--%><td width="50" align="center">
											<a href="userRegionPageAction.do?op=exportXls&userId=${requestScope.userId}"><img
													src="../images/CN/outputExcel.gif" width="16" height="16"
													border="0" align="absmiddle">&nbsp;导出</a>
										</td>

										<td class="SeparatePage">
											<%--<pages:pager action="../users/listOrganVisitAction.do" />--%>
										</td>


									</tr>
								</table>

								<table class="sortable" width="100%" border="0" cellpadding="0"
									cellspacing="1">
									<tr class="title-top">
										<td width="2%" class="sorttable_nosort">
											<input type="checkbox" id="checkAll" name="checkall"
												onClick="check();">
										</td>
										<td width="15%" align="center">
											用户名
										</td>
										<td width="15%" align="center">
											真实姓名
										</td>
										<td width="15%" align="center">
											区域编号
										</td>
										<td width="55%" align="center">
											区域名称
										</td>
									</tr>
									<c:forEach var="item" items="${list}">
										<tr class="table-back-colorbar">
											<td>
												<input type="checkbox" name="id" value="${item.id}" />
											</td>
											<td align="center">
												${item.loginName}
											</td>
											<td align="center">
												${item.realName}
											</td>
											<td align="center">
												${item.areaId}
											</td>
											<td align="center">
												${item.areaName}
											</td>
										</tr>
									</c:forEach>
								</table>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
