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
		<SCRIPT language="javascript" src="../js/passwordcheck.js"> </SCRIPT>
		<script type="text/javascript" src="../js/validator.js"></script>
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
$(function(){
	if('${msg}'!="") {
		alert('${msg}'); 
	}

	$('#getid').click(function() {
		if ($('#mobile').val() == "") {
			alert("手机号不能为空");
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
});  
	function ChkValue(){
		if ( !Validator.Validate(document.addform,2) ){
			return false;
		}
		
		return true;
	}
	function checkLoginName(){
		var mobile = $('#mobile').val().trim();
		if ( mobile == "" ){	
			return;
		}
		$.ajax({  
	    	type : "POST",
			url : "../users/ajaxCheckUsersAction.do?type=1",
			data : "mobile="+mobile,
			dataType: "json",
			async: false,
	        success: function (data) {  
	            if(data.returnCode != 0) {
	            	 alert(data.returnMsg);
			    }
	        },  
	        error: function (data) {  
	        	alert("网络异常");  
	        }
	    });  
	}
	function updMobile(){
		if(!ChkValue()) {
			return;
		}
	    $.ajax({  
	    	type : "POST",
			url : "../users/updMobileAction.do",
			data : $("#addform").serializeArray(),
			dataType: "json",
			async: false,
	        success: function (data) {  
				alert(data.returnMsg);
				if(data.returnCode == 0) {
	            	window.location.href="../users/toUpdMobileAction.do"; 
			    }
	        },  
	        error: function (data) {  
	        	alert("修改失败");  
	        }  
	    });
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
								${menusTrace }
							</td>
						</tr>
					</table>
					<form id="addform" name="addform" method="post"
						action="../users/updMobileAction.do"
						onSubmit="return ChkValue();">
						<input type="hidden" name="userName" value="${userName}"/>
						<input type="hidden" name="type" value="1"/>
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
										新手机号：
									</td>
									<td width="9%">
										<input id="mobile" name="mobile" type="text" value="${mobile}"
											dataType="Require" msg="必须录入手机号!" onBlur="checkLoginName()">
										<span class="STYLE1">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										验证码：
									</td>
									<td>
										<input id="idCode" name="idCode" type="text" dataType="Require" msg="必须录入验证码!" >
										<span class="STYLE1">*</span>
										<input id="getid" name="getid" type="button" value="获取验证码">
									</td>
								</tr>
							</table>
						</fieldset>

						<table width="100%" border="0" cellpadding="0" cellspacing="1">

							<tr align="center">
								<td width="54%">
									<input type="button" onclick="updMobile();" name="Submit" value="确定">
									&nbsp;&nbsp;
							</tr>

						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
