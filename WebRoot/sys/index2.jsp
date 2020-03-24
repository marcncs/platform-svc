<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<title>登录分销系统</title>

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
		<script language="javascript">
	function ChangeLanguage(obj){
		location.href="internationalAction.do?language="+obj;
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
	 
</script>
	</head>

	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"
		style="border: 0px;padding-top: " onload="checkPsdLevel();">
		<div>
			<img src="../images/loginBack.bmp" width="100%" height="35%"></img>
		</div>
		<div align="center">
			<form id="form1" name="login" method="post" action="userLoginAction.do">
				<table width="620px" bordercolor="#6495ED" align="center" border="1" height="180px" cellspacing="0" cellpadding="0">
					<tr height="29px" >
						<td width="100%" background="../images/CN/top-back6.gif" >
							<span style="color: #363382;font-size: medium">
								<font face="Arial">&nbsp;SINO-AGRI LEADING BIOSCIENCES CO.,LTD.</font>
							</span>
						</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
									<td width="20px">&nbsp;</td>
									<td><img src="../images/logo.gif" align="right"></img></td>
									<td>
										<table align="left">
											<tr>
												<td id="resultTd" class="error" align="right" colspan="3">${result }</td>
											</tr>
											<tr>
												
												<td align="right" height="22px" >
													<label class="labelText">用户名&nbsp;</label>
												</td>
												<td align="left" height="22px" colspan="2">
													<input id="username" name="UserName" type="text" class="INPUT" value="${username}">
												</td>
											</tr>
											<tr>
												<td align="right" height="22px">
													<label class="labelText">密&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;</label>
												</td>
												<td align="left" height="22px" colspan="2">
													<input id="password" name="Password" type="password" class="INPUT" >
												</td>
											</tr>
											<tr>
												<td align="right" height="22px">
													<label class="labelText">
														验证码&nbsp;
													</label>
												</td>
												<td align="left" colspan="2" height="22px" valign="middle" class="oneline">
													<input id="checkNum" name="CheckNum" border="0" type="text" class="INPUTCHECKNUM">
													 
													<img src="../checkNumber.do" class="middlePosition">
												</td>
												<td valign="bottom"><input type="image" name="imageField" src="../images/CN/enter.gif" onclick="return checkInitPassword()">		
												</td>
											</tr>
										 
										</table>
									</td>
									 
								</tr>
								
							</table>
							
						</td>
						 
					</tr>
					
				</table>
					<table width="620px"  bordercolor="#6495ED" cellspacing="0" cellpadding="0">
					<tr height="20px">
						<td width="320px">&nbsp;</td>
						<td>
						<a href="http://www.winsafe.cn/lxwm/Lxwm.asp" target="_blank">
      						<img src="../images/winsafelogo.jpg" width="40" height="16" border="0" align="bottom"></img>
      					</a>
      						<span style="vertical-align:text-top; font-weight:bold;color: #363382;font-size: x-small;">
								技术支持:+86 021 68879030 www.winsafe.cn
							</span>
							</td>
					</tr>
				</table>
			</form>
		</div>
	</body>

</html>