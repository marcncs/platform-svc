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
<script language="javascript">
function formChk() {
		var reply = document.reply.value;

		if (reply) {
			alert("回复不可为空！");
			return false;
		}
		showloading();
		return true;
	}
</script>
</head>
<body>
<form action="addReplyProductFeedBackAction.do" method="post" name="validateProduct" onSubmit="return formChk();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" >
		<tr>
			<td align="right">回复用户评论：</td>
			<input type="hidden" name="PPID" id="PPID" value="${PPID}"/>
			<br/>
			<td colspan="4"><textarea name="reply" cols="100" style="width: 100%" rows="3" id="reply" dataType="Limit" max="500" msg="评论必须在500个字之内"
				require="true">${reply}</textarea> <br> <span class="td-blankout">(评论不能超过500个字符!)</span></td>
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