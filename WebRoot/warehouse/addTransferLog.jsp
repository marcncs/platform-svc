<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
	function ChkValue() {
		var fileType = document.validateProvide.fileType;
		if (fileType.value == null || fileType.value == "") {
			alert("文件类型不能为空");
			return false;
		} 
		showloading();
		return true;
	}
</script>

</head>
<body style="overflow: auto">
	<table width="100%" border="1" cellpadding="0" cellspacing="1"
		bordercolor="#BFC0C1">
		<tr>
			<td>
				<table width="100%" height="40" border="0" cellpadding="0"
					cellspacing="0" class="title-back">
					<tr>
						<td width="10"><img src="../images/CN/spc.gif" width="10"
							height="1"></td>
						<td width="772">新增</td>
					</tr>
				</table>
				<form name="validateProvide" method="post"
					action="../warehouse/addTransferLogAction.do"
					onSubmit="return ChkValue();">
					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<td>
								<fieldset align="center">
									<legend>
										<table width="50" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td>基本信息</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td align="right">文件类型：</td>
											<td><windrp:select key="TransferFileType"
													name="fileType" p="y|f" value="${fileType}" /> <span
												class="style1">*</span></td>
										</tr>
									</table>
								</fieldset> <br />
							<br />
								<table width="100%" border="0" cellpadding="0" cellspacing="1">
									<tr>
										<td align="center"><input type="Submit" name="Submit"
											value="提交"> &nbsp;&nbsp; <input type="button"
											name="Submit2" value="取消" onClick="window.close();">
										</td>
									</tr>
								</table>
					</table>
				</form>
			</td>
		</tr>
	</table>
</body>
</html>
