<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>新增组</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
	function chkForm(){
		var sortname = document.addform.sortname;
		if(sortname.value ==null || sortname.value=="" || sortname.value.trim()==""){
			alert("组名不能为空!");
			sortname.focus();
			return false;
		}
		return true;
	}
</script>
	</head>

	<body style="overflow: auto;">
		<form name="addform" method="post" action="addPhoneBookSortAction.do"
			onSubmit="return chkForm();">
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
									新增组
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
									<td width="9%" align="right">
										组名：
									</td>
									<td colspan="5">
										<input name="sortname" type="text" id="sortname">
										<span class="STYLE1">*</span>
										(如：朋友、同事等)
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
