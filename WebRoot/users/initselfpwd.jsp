<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<script language="javascript" src="../js/jquery-1.4.2.min.js"></script>
		<SCRIPT language="javascript" src="../js/passwordcheck.js">
	
</SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">

	</head>
	<script language="javascript">
	var wait = 60;
	get_code_time = function(o) {
		if (wait == 0) {
			o.removeAttribute("disabled");
			o.value = "获取验证码";
			wait = 60;
		} else {
			o.setAttribute("disabled", true);
			o.value = "(" + wait + ")秒后重新获取";
			wait--;
			setTimeout(function() {
				get_code_time(o)
			}, 1000)
		}
	}

	$(function() {
		if ('${msg}' != "") {
			alert('${msg}');
		}

		$('#getid').click(function() {
			if ($('#userName').val() == "") {
				alert("用户名不能为空");
				return;
			}
			var o = this;
			$.ajax( {
				type : "POST",
				url : "../common/ajaxGetIdentifyCodeAction.do",
				data : $("#addform").serializeArray(),
				dataType : "json",
				async : false,
				success : function(data) {
					alert(data.returnMsg);
					if (data.returnCode == 1) {
						get_code_time(o);
					}
				},
				error : function(data) {
					alert("短信验证码发送失败");
				}
			});
		});

		$('#submitbtn').click(function() {
			if ($('#userName').val() == "") {
				alert("用户名不能为空");
				return;
			}
			if (!ChkValue()) {
				return;
			}
			$.ajax( {
				type : "POST",
				url : "../users/initSelfPwdAction.do",
				data : $("#addform").serializeArray(),
				dataType : "json",
				async : false,
				success : function(data) {
					alert(data.returnMsg);
					if (data.returnCode == 1) {
						window.location.href = "../sys/index.jsp";
					}
				},
				error : function(data) {
					alert("密码设置失败");
				}
			});
		});

	});

	function ChkValue() {
		var password = addform.password.value;
		var agapassword = addform.agapassword.value;
		if (password.trim().length <= 0) {
			alert("新密码不能为空！");
			return false;
		}
		if (password.trim().length < 5) {
			alert("您的密码过于简单，请输入8位以上数字或字母组合的密码!");
			return false;
		}
		if (password != agapassword) {
			alert("两次输入密码不符！");
			return false;
		}
		if (!checkPwd($('#password').val(), $("#userName").val())) {
			return false;
		}
		return true;
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
								找回密码
							</td>
						</tr>
					</table>
					<form id="addform" name="addform" method="post"
						action="../users/initSelfPwdAction.do">
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
										用户名/手机号：
									</td>
									<td width="9%">
										<input id="userName" name="userName" type="text"
											value="${userName}">
									</td>
								</tr>
								<tr>
									<td width="9%" align="right">
										验证码：
									</td>
									<td width="9%">
										<input id="idCode" name="idCode" type="text">
										&nbsp;
										<input id="getid" name="getid" type="button" value="获取验证码">
									</td>
								</tr>
								<tr>
									<td align="right">
										新密码：
									</td>
									<td>
										<input id="password" name="password" type="password">
									</td>
								</tr>
								<tr>
									<td align="right">
										重输新密码：
									</td>
									<td>
										<input name="agapassword" type="password">
									</td>
								</tr>
								<tr>
									<td colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right" valign="top">
										密码复杂度要求：
									</td>
									<td align="left">

										1.至少8位
										<br />
										2.大写英文字母，小写英文字母，数字和特殊字符，4种类型中至少包含3种。
										<br />
										3.不能与用户名相同。
										<br />
										4.不能与最后10次的密码相同。
										<br />
									</td>
								</tr>
							</table>
						</fieldset>

						<table width="100%" border="0" cellpadding="0" cellspacing="1">

							<tr align="center">
								<td width="54%">
									<input type="button" id="submitbtn" name="submitbtn" value="确定">
									&nbsp;&nbsp;
									<!-- 
			<input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();"></td>
			 -->
							</tr>

						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
