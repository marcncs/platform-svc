<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>	
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/jquery-1.4.2.min.js"> </SCRIPT>
		<style type="text/css"></style>
	</head>
	<script language="javascript">
function formcheck(){
	var checkUserId ="";
	$("input[name='che']").each(function() {
		 if ($(this).is(':checked')) {
			 checkUserId += $(this).val()+",";
		}
	});
	if(checkUserId==""){
		alert("请选择需要添加的记录!");
		return false;
	}else{
		$("#userIds").val(checkUserId);
	}
	showloading();
	return true;
}

function submitCheckAll(){
	$("input[name='che']").each(function() {
		$(this).attr("checked",true);
	});
	$("#addAll").val("true");
	showloading();
	$("#addForm").submit();
}

function Check() {
	var pid = document.all("che");
	var checkall = document.all("checkall");
	if (pid == undefined) {
		return;
	}
	if (pid.length) {
		for (i = 0; i < pid.length; i++) {
			pid[i].checked = checkall.checked;
		}
	} else {
		pid.checked = checkall.checked;
	}
}

</script>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								选择用户
							</td>
						</tr>
					</table>

					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						bordercolor="#BFC0C1">
						<tr>
							<td>
								<div id="bodydiv">
									<form name="search" method="post"
										action="../keyretailer/toAddSUserAreaAction.do">
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<input type="hidden" name="pid" value="${pid}">
											<tr class="table-back">
												<td align="right">
													关键字：
												</td>
												<td>
													<input type="text" name="KeyWord" value="${KeyWord}">
												</td>
												<td align="right">
													用户类别：
												</td>
												<td>
													<windrp:select key="UserType1" name="userType" p="y|f"
														value="${organType}" />
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
											<td style="text-align: right">
												<pages:pager action="../keyretailer/toAddSUserAreaAction.do" />
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div id="listdiv" style="overflow-y: auto; height: 300px;">
									<FORM METHOD="POST" name="listform" ACTION="">
										<table class="sortable" width="100%" border="0"
											cellpadding="0" cellspacing="1">

											<tr align="center" class="title-top-lock">
												<td width="4%" class="sorttable_nosort">
													<input type="checkbox" name="checkall" onClick="Check();">
												</td>
												<td>
													用户编号
												</td>
												<td>
													用户名
												</td>
												<td>
													姓名
												</td>
												<td>
													用户类别
												</td>
											</tr>
											<logic:iterate id="p" name="userList">
												<tr align="center" class="table-back-colorbar">
													<td>
														<input type="checkbox" name="che" id="che" value="${p.userid}"
															onClick="changeValue(this)">
													</td>
													<td height="20">
														${p.userid}
													</td>
													<td>
														${p.loginname}
													</td>
													<td>
														${p.realname}
													</td>
													<td>
														<windrp:getname key='UserType1' value='${p.userType}'
															p='f' />
													</td>
												</tr>
											</logic:iterate>
										</table>
									</form>
								</div>
							</td>
						</tr>
					</table>

					<form action="../keyretailer/addSUserAreaAction.do"
						method="post" id="addForm" name="addForm"
						onSubmit="return formcheck();">
						<input type="hidden" id="userIds" name="userIds">
						<input type="hidden" name="pid" value="${pid}">
						<input type="hidden" id="addAll" name="addAll">
						<table width="100%" border="0" cellpadding="0" cellspacing="1">

							<tr>
								<td>
									<div align="center">
										<input type="submit" name="Submit" value="确定">
										&nbsp;&nbsp;
										<input type="button" name="cancel" value="取消"
											onClick="window.close();">
										<input type="hidden" name="speedstr">
										&nbsp;&nbsp;
										<input type="button" name="button1" value="全部选中"
											onclick="submitCheckAll()">
										&nbsp;&nbsp;
									</div>
								</td>
							</tr>

						</table>
					</form>
				</td>
			</tr>
		</table>

	</body>
</html>
