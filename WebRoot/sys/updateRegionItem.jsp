<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/function.js"></script>
		<script language="JavaScript">
			function valid() {
				var name = $('input[name="name"]').val();
				if ($.trim(name) == '') {
					alert("区域名称不能为空!");
					return false;
				}
				showloading();
				return true;
			}
			
		</script>
	</head>

	<body style="overflow: auto;">
		<form name="addform" method="post" action="../sys/updateRegionItemAction.do?op=update"
			onSubmit="return valid();">
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
									修改区域
								</td>
							</tr>
						</table>

						<fieldset align="center">
							<legend>
								<table width="50" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											基本信息
										</td>
									</tr>
								</table>
							</legend>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">

								<tr>
								<td width="10%"></td>
									<td  width="30%" align="right">
										<input type="hidden" name="id" value="${info.id}">
										区域名称：
										<input name="name" type="text" id="sortname"
											value="${info.name}" maxlength="15">
										<span class="STYLE1">*</span>
									</td>
									<td  width="25%"></td>
									<td> 区域类型:<span class="STYLE1"> ${rankName}</span></td>
									<td >
										<input type="hidden" name="pid" value="${info.pId}">
										<input type="hidden" name="rank" value="${info.rank}">
									</td>
								</tr>
							</table>
						</fieldset>
						<table width="100%">
							<tr>
								<td align="center">
									<input type="submit" name="Submit" value="确定">
									&nbsp;&nbsp;
									<input type="button" name="cancel" value="取消"
										onClick="javascript:window.close();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
