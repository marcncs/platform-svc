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
		<script type="text/javascript" src="../js/validator.js"></script>
		<script language="javascript" src="../js/jquery-1.4.2.min.js"></script>
		<SCRIPT language="javascript" src="../js/passwordcheck.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectProvince.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">

	</head> 
	<script language="javascript">
var wait = 60;  
get_code_time = function (o) {  
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

	//省市区
	ajaxSelectChildren(null, $("#province"));

	/*$('#getid').click(function() {  
		if($('#userName').val() == "") {
			alert("用户名不能为空");  
			return;
		}
	    var o = this;  
	    $.ajax({  
	    	type : "POST",
			url : "../common/ajaxGetIdentifyCodeAction.do",
			data : $("#addform").serializeArray(),
			dataType: "json",
			async: false,
	        success: function (data) {  
	            alert(data.returnMsg);
	            if(data.returnCode == 1) {
	            	get_code_time(o);
			    }
	        },  
	        error: function (data) {  
	        	alert("短信验证码发送失败");  
	        }  
	    });  
	});  */
});  

function addNewUser(){
	if(!ChkValue()) {
		return;
	}
    $.ajax({  
    	type : "POST",
		url : "../users/addUsersApplyAction.do",
		data : $("#addform").serializeArray(),
		dataType: "json",
		async: false,
        success: function (data) {  
            alert(data.returnMsg);
            if(data.returnCode == 1) {
            	window.location.href="../sys/index.jsp"; 
		    }
        },  
        error: function (data) {  
        	alert("注册失败");  
        }  
    });
}


function ChkValue(){

	if ( !Validator.Validate(document.addform,2) ){
		return false;
	}
	
	var password = addform.password.value;
	var agapassword = addform.agapassword.value;
	if(password.trim().length<5){
		alert("您的密码过于简单，请输入8位以上数字或字母组合的密码!");
		return false;
	}
	if(password!=agapassword){
		alert("两次输入密码不符！");
		return false;
	}
	if(!checkPwd($('#password').val(),$("#userName").val())) {
		return false;
	}
	show();
	return true;
}

function onProvinceChange(currentObj, childObj){
	ajaxSelectChildren(currentObj, childObj);
	$('#areas').html('<option value="">-地区-</option>');
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

</script>
	<style type="text/css">
#login {
	display: none;
	border: 0.1em solid #3366FF;
	height: 40%;
	width: 50%;
	position: absolute; /*让节点脱离文档流,我的理解就是,从页面上浮出来,不再按照文档其它内容布局*/
	top: 24%; /*节点脱离了文档流,如果设置位置需要用top和left,right,bottom定位*/
	left: 24%;
	z-index: 2; /*个人理解为层级关系,由于这个节点要在顶部显示,所以这个值比其余节点的都大*/
	background: white;
}

#over {
	width: 100%;
	height: 100%;
	opacity: 0.8; /*设置背景色透明度,1为完全不透明,IE需要使用filter:alpha(opacity=80);*/
	filter: alpha(opacity =   80);
	display: none;
	position: absolute;
	top: 0;
	left: 0;
	z-index: 1;
	background: silver;
}
</style>
	<body>
		<div id="login">
			<div>
				<br/>
				<p class=MsoNormal align=center
					style='margin-top: 0cm; margin-right: 22.0pt; margin-bottom: 15.0pt; margin-left: 22.0pt; text-align: center; line-height: 20.25pt'>
					<b><span
						style='font-size: 16.5pt; font-family: "微软雅黑", "sans-serif"; color: #286EAA'>个人数据处理同意书</span>
					</b>
				</p>

				<p class=MsoNormal
					style='margin-top: 0cm; margin-right: 22.0pt; margin-bottom: 0cm; margin-left: 22.0pt; margin-bottom: .0001pt; line-height: 15.0pt'>
					<span style='font-size: 9.0pt; font-family: 宋体; color: #333333'>您提交的人员姓名和电话号码等个人信息将被系统记录，拜耳收集、处理和使用您提交的个人信息是为了让您能够使用本系统的服务。</span>
				</p>

				<p class=MsoNormal
					style='margin-top: 0cm; margin-right: 22.0pt; margin-bottom: 0cm; margin-left: 22.0pt; margin-bottom: .0001pt; text-align: justify; text-justify: inter-ideograph; line-height: 15.0pt'>
					<span style='font-size: 9.0pt; font-family: 宋体; color: #333333'>上述数据将由拜耳保存在拜耳关联公司或拜耳授权的第三方公司，并遵守拜耳数据隐私保护的规则及其他所在地的相关法律/法规。 <span
						lang=EN>/</span>法规。</span>
				</p>

				<p class=MsoNormal
					style='margin-top: 0cm; margin-right: 22.0pt; margin-bottom: 0cm; margin-left: 22.0pt; margin-bottom: .0001pt; text-align: justify; text-justify: inter-ideograph; line-height: 15.0pt'>
					<span style='font-size: 9.0pt; font-family: 宋体; color: #333333'>如您同意上述个人数据处理申明，请点击按钮《同意》后继续使用本系统，如不同意，可以点击《不同意》按钮后退出。</span>
				</p>

				<p class=MsoNormal
					style='margin-top: 0cm; margin-right: 22.0pt; margin-bottom: 0cm; margin-left: 22.0pt; margin-bottom: .0001pt; text-align: justify; text-justify: inter-ideograph; line-height: 15.0pt'>
					<span style='font-size: 9.0pt; font-family: 宋体; color: #333333'>您有权在未来随时撤销此同意申明，您可以通过致电拜耳热线<span
						lang=EN>4008100360</span>撤销此同意申明并要求删除个人信息数据。</span>
				</p>
				<br/><br/>
				<table width="100%" border="0" cellpadding="0" cellspacing="1">

							<tr align="center">
								<td width="54%">
									<input type="button" id="submitbtn" name="approve" value="同意"
										onclick="addNewUser();">
									&nbsp;&nbsp;
									<input name="cancel" type="button" id="cancel" value="不同意" 
										onClick="javascript:hide();">
								</td>
							</tr>

				</table>
				<p></p>
			</div>
		</div>
		<div id="over"></div>
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
								新用户注册
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
								<br />
								<tr>
									<td width="9%" align="right">
										公司名称： 
									</td>
									<td width="9%">
										<input id="organName" name="organName" type="text" maxlength="64"
											value="${organName}" dataType="Require" msg="必须录入公司名称!">
										<span class="STYLE1">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td width="9%" align="right">
										所在地区：
									</td>
									<td width="9%">
										<select style="width: 120px;" id="province" name="province"
											onchange="onProvinceChange(this, $('#city'));">
											<option value="">
												-省份-
											</option>
										</select>
										<select style="width: 120px;" id="city" name="city"
											onchange="ajaxSelectChildren(this, $('#areas'));">
											<option value="">
												-城市-
											</option>
										</select>
										<select style="width: 120px;" id="areas" name="areas"
											dataType="Require" msg="必须选择所在地区!">
											<option value="">
												-地区-
											</option>
										</select>
										<span class="STYLE1">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td width="9%" align="right">
										用户类型：
									</td>
									<td width="9%">
										<select id="userType" name="userType" dataType="Require"
											msg="必须选择用户类型!">
											<option value="">
												-请选择-
											</option>
											<option value="1">
												TD用户
											</option>
											<option value="2">
												仓库用户
											</option>
										</select>
										<span class="STYLE1">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td width="9%" align="right">
										手机号：
									</td>
									<td width="9%">
										<input id="mobile" name="mobile" type="text" value="${mobile}"
											dataType="Require" msg="必须录入手机号!" onBlur="checkLoginName()">
										<span class="STYLE1">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td width="9%" align="right">
										姓名：
									</td>
									<td width="9%">
										<input id="name" name="name" type="text" value="${name}"
											dataType="Require" msg="必须录入姓名!">
										<span class="STYLE1">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right">
										密码：
									</td>
									<td>
										<input id="password" name="password" type="password"
											dataType="Require" msg="必须录入密码!">
										<span class="STYLE1">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right">
										重输密码：
									</td>
									<td>
										<input name="agapassword" type="password" dataType="Require"
											msg="必须录入密码!">
										<span class="STYLE1">*</span>
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
										
												1.至少8位<br/>
												2.大写英文字母，小写英文字母，数字和特殊字符，4种类型中至少包含3种。<br/>
												3.不能与用户名相同。<br/>
												4.不能与最后10次的密码相同。<br/>
									</td>
								</tr>
							</table>
						</fieldset>

						<table width="100%" border="0" cellpadding="0" cellspacing="1">

							<tr align="center">
								<td width="54%">
									<input type="button" id="submitbtn" name="submitbtn" value="注册"
										onclick="ChkValue();">
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
<script type="text/javascript">  
var login = document.getElementById('login');  
var over = document.getElementById('over');  
    function show()  
    {  
        login.style.display = "block";  
        over.style.display = "block";  
    }  
    function hide()  
    {  
        login.style.display = "none";  
        over.style.display = "none";  
    }  
</script>
