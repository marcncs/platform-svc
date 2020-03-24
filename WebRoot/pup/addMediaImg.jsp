<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="../css/xtree.css" type="text/css">
<script src="../js/prototype.js"></script>
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xmlextras.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xloadtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/treeselect.js"></SCRIPT>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
		<tr>
			<td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
					<tr>
						<td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
						<td width="772">新增图片</td>
					</tr>
				</table>
				<form action="addMediaImgAction.do" method="post" name="addMediaImg" enctype="multipart/form-data">
					<fieldset align="center">
						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td height="20" align="right">视频标题：</td>
								<td><input name="title" type="text" id="title" maxlength="21" dataType="Require" msg="视频标题不能为空!">
								<span class="style1">* </span></td>
							</tr>
							<tr>
								<td width="12%" align="right">视频简介：</td>
								<td width="19%">
									<textarea name="digest" type="text" id="digest" maxlength="200" dataType="Require" msg="视频简介不能为空!"></textarea>
									<span class="style1">*</span>
								</td>
							</tr>
							<tr>
								<td align="right">上传图片：</td>
								<td><input type="file" name="video" id="video" class="easyui-validatebox" ></td>
							</tr>
						</table>
					</fieldset>

					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<td><div align="center">
									<input type="submit" name="Submit" value="确定">&nbsp;&nbsp;<input type="button" name="cancel" value="取消"
										onClick="window.close()">
								</div></td>
						</tr>
					</table>
				</form></td>
		</tr>
	</table>
</body>
</html>