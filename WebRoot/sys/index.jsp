<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ page import="com.winsafe.drp.util.*" %>
<%String loginMode = JProperties.loadProperties("system.properties",JProperties.BY_CLASSLOADER).getProperty("deployEnv");%>
<html>
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<title>欢迎登陆RTCI系统</title>

		<style type="text/css">
<!--
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
.middlePosition{
	vertical-align:top;
}

.labelText{
	COLOR: #000066;
}
.error {
	color: #F00;
}
-->
</style>
<script type="text/javascript" src="../js/function.js"></script>
		<script language="javascript">
	function ChangeLanguage(obj){
		location.href="internationalAction.do?language="+obj;
	}
	function getCheckNum(){
		window.location.href = window.location.herf;
	}
	function getPwd(){
		window.location.href = "../users/initselfpwd.jsp";
	}
	function regist(){
		window.location.href = "../users/registration.jsp";
	}
	function checkPsdLevel(){
		var psdFlag = "<%=request.getAttribute("psdWarn")%>";
		//密码过于简单的情况
		if(psdFlag == 'false'){
			var msg = "系统检测到您的密码过于简单,请您立即修改密码！";
  			if(window.confirm(msg)){
  				var top = (window.screen.height -700)/2;
				var left = (window.screen.width -400)/2;
			  	var newwin = window.open("../users/toUpdSelfPwdAction.do","PopWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,top="+top+",left="+left+",width="+700+",height="+400);
			 	newwin.focus();
  			}
		}
	}
	
	function sso() {
		showloading();
		login.action="../secure/aad";
		login.submit();
	}
	 
</script> 
	</head>

	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"
		style="border: 0px;" onload="checkPsdLevel();">
		<div>
			<img src="../images/loginBack.bmp" width="100%" height="35%"></img>
		</div>
		<div align="center" >
			<form id="form1" name="login" method="post" action="../sys/userLoginAction.do">
				<table border="1px" bordercolor="#567FCD">
					<tr>
						<td>
						<table width="520px" align="center" border="0" height="180px" cellspacing="0" cellpadding="0">
					
					<tr height="29px">
						<td width="100%" background="../images/CN/top-back1.gif" >
							<span style="color: #363382;font-size: medium">
								<font face="Arial">&nbsp;RTCI<sup style="font-size:x-small; height: 5px">TM</sup> - Real-Time Customer Information  V1.4.0</font>
							</span>
						</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
									<td width="100px">&nbsp;</td>
									<td width="100px"><img src="../images/logo.png" align="left"></img></td>
									<td width="260px"> <!-- 增加宽度，适应其他浏览器 -->
										<table align="left">
											<tr>
												<c:choose>
													<c:when test="${error_description!=null}">
														<td id="resultTd" class="error" align="left" colspan="3">${error_description}</td>
													</c:when>
													<c:otherwise>
														<td id="resultTd" class="error" align="left" colspan="3">${result}</td>
													</c:otherwise>
												</c:choose>
											</tr>
											<tr>
												
												<td align="right" height="20px" >
													<label class="labelText">用户名&nbsp;</label>
												</td>
												<td align="left" height="20px">
													<input id="username" tabindex="1" name="UserName" type="text" class="INPUT" value="${username}">
												</td>
												<td>
												<% if(Constants.ENV_EXTERNAL.equals(loginMode)){%>
												<a href="#" onclick="regist();">注册</a>
												<%} %>
												</td>
												<td></td>
											</tr>
											<tr>
												<td align="right" height="20px">
													<label class="labelText">密　码&nbsp;</label><!-- 全角的空格 -->
												</td>
												<td align="left" height="20px" colspan="1">
													<input id="password" tabindex="2" name="Password" type="password" class="INPUT" >
												</td>
												<td><input type="image" name="imageField" src="../images/CN/enter.png"></td>
												<td>
												<% if(Constants.ENV_EXTERNAL.equals(loginMode)){%>
												<a href="#" onclick="getPwd();">忘记密码?</a>
												<%} %>
												</td>
											</tr>
											<% if(Constants.ENV_EXTERNAL.equals(loginMode)){%>
											<tr>
												<td align="right" height="22px">
													<label class="labelText">
														验证码&nbsp;
													</label>
												</td>
												<td align="left" height="22px" valign="middle" class="oneline">
													<input id="checkNum"  tabindex="3" name="CheckNum" border="0" type="text" class="INPUTCHECKNUM">
													 
													<img id="checkNumImg" src="../checkNumber.do" class="middlePosition">
												</td>
												<td valign="bottom" align="center">
													<a href="#" onclick="getCheckNum();">看不清?</a>		
												</td>
											</tr>
											<%} %>
										 	<input type="button" onclick="sso();" value="单点登录"/>
										</table>
									</td>
									 
								</tr>
								
							</table>
							
						</td>
						 
					</tr>
					
				</table>
						</td>
					</tr>
				</table>
				
					<!-- tommy 15-12-25
					delete bordercolor 
					 <table width="520px"  bordercolor="#6495ED" cellspacing="0" cellpadding="0">
					 -->
					  <table width="520px"   cellspacing="0" cellpadding="0">
					<tr height="20px">
						<td width="10px">&nbsp;</td>
						<td align="right">
						 
      						<span style="vertical-align:middle;font-family:Arial; color: #363382;font-size:12px;">
								技术支持:
									<img src="../images/CN/tel.gif"/>
									400-920-6200
									<img src="../images/CN/mail.gif"/>
									<a href="mailto:support@winsafe.cn">support@winsafe.cn</a>
									<img src="../images/CN/e.gif"/>
									<a href="http://www.winsafe.cn" target="_blank">www.winsafe.cn</a>
							</span>
							</td>
					</tr>
				</table>
			</form>
		</div>
	</body>

</html>