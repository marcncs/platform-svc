<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>请选择有效期文件</title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
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
		
		<div align="left">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									系统设置&gt;&gt;更新有效期文件
								</td>

							</tr>
						</table>
			<form enctype="multipart/form-data" action="updateUserValInnerAction.do" name="update" method="post">
				<input type="hidden" name="userid" id="userid" value="${userId}" />
				 <fieldset align="center"> <legend>
				  <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
				 </legend>
				<table width="100%"  border="0" cellpadding="0" cellspacing="1">

					<tr align="center">
						<td>
							您当前所使用的激活文件有效期为：
							
							${valdate}
						</td>
					</tr>
					<tr align="center">
						<td>
							<input type="file" id="keyfile" name="keyfile">
						</td>
					</tr>
					<tr align="center">
						<td align="center">
							<input type="submit" value="确定">
							
						</td>
					</tr>
					<tr align="center">
						<td id="resultTd" class="error"  colspan="3">
							${result}
						</td>
					</tr>
					<tr>

					</tr>
				</table>
				</fieldset>
	  
			</form>
		</div>
		<div align="right">

		</div>
	</body>

</html>