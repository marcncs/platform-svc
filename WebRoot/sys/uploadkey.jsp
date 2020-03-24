<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>请选择有效期文件</title>

		<style type="text/css">
.line {
	border: 1px solid #999999;
}

td {
	font-size: 12px;
}

.INPUT {
	FONT-SIZE: 13px;
	FONT-FAMILY: tahoma, sans-serif;
	width: 100px;
	height: 22px;
	border: 1px solid #000066
}

.INPUTCHECKNUM {
	FONT-SIZE: 13px;
	FONT-FAMILY: tahoma, sans-serif;
	width: 37px;
	height: 20px;
	border: 1px solid #000066
}

.middlePosition {
	vertical-align: top;
}

.labelText {
	COLOR: #000066;
}

.error {
	color: #F00;
}
</style>
	</head>

	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"
		style="border: 0px;">
		<div>
			<img src="../images/loginBack.bmp" width="100%" height="35%"></img>
		</div>
		<div align="center">
			<form enctype="multipart/form-data" action="updateUserValAction.do" name="update" method="post">
				<input type="hidden" name="userid" id="userid" value="${userId}" />
				<table>

					<tr>
						<td colspan="2">
							您的密钥已经失效
							<BR>
							请选择新的密钥文件进行激活
						</td>
					</tr>
					<tr>
						<td>
							<input type="file" id="keyfile" name="keyfile">
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="submit" value="确定">
							<input type="button" onclick="window.location='index.jsp'"
								value="返回" />
						</td>
					</tr>
					<tr>
						<td id="resultTd" class="error" align="left" colspan="3">
							${result}
						</td>
					</tr>
					<tr>

					</tr>
				</table>
			</form>
		</div>
		<div align="right">

		</div>
	</body>

</html>