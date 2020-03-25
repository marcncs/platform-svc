<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
        <SCRIPT type=text/javascript src="../js/function.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<script language="javascript">
	function CheckInput(){
		var doc = document.validateDoc.doc;
		
		if(doc.value==""||doc.value.trim()==""){
			alert("请选择上传文件！");
			return false;
		}
		showloading();
		return true;
	}
</script>

	<body style="overflow: auto;">
		<form name="validateDoc" method="post" action="addDocAction.do"
			enctype="multipart/form-data" onSubmit="return CheckInput();">
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
									新增文件
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
										选择文件：
									</td>
									<td width="30%">
										<input name="doc" type="file" id="doc">
										<font color="#FF0000">*</font>
									</td>
									<td width="14%" align="right">
										文件类型：
									</td>
									<td width="47%">
										<select name="sortid" id="sortid">
											<logic:iterate id="s" name="sort">
												<option value="${s.id}">
													${s.sortname}
												</option>
											</logic:iterate>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right">
										描述：
									</td>
									<td colspan="3">
										<input name="describe" type="text" id="describe" maxlength="50"
											size="50">
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
