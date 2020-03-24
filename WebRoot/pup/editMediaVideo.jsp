<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>

<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>
<body>
<form action="editMediaVideoAction.do" method="post" name="editmv" >
		<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr>
				<td height="20" align="right">视频标题：</td>
				<td>
				<input type="hidden" name = "id"  value="${MV.id}"/>
				<input name="title" type="text" id="title" maxlength="21" dataType="Require" msg="视频标题不能为空!" value="${MV.title}"/>
				<span class="style1">*</span>
				</td>
			</tr>
			<tr>
				<td width="12%" align="right">视频简介：</td>
				<td width="19%"><textarea name="digest" type="text" id="digest" maxlength="200" dataType="Require" msg="视频简介不能为空!">${MV.digest}</textarea>
				<span class="style1">*</span></td>
			</tr>
		</table>

		<table width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td><div align="center">
  				<input type="submit" name="Submit" value="确定">&nbsp;&nbsp;<input type="button" name="cancel" value="取消" onClick="window.close()">
              </div></td>
            </tr>
    </table>
</form>
</body>
</html>