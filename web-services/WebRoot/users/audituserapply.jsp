<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/validator.js"></script>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script language="javascript" src="../js/jquery-1.4.2.min.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
function SelectOrgan(){ 
	var c=showModalDialog("../common/selectOrganAction.do?userType=${ua.userType}",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( c == undefined ){
		return; 
	}	
	document.addusers.organId.value=c.id;
	document.addusers.oname.value=c.organname;
}

function approveUser(isApprove){
	if (isApprove==1 && !Validator.Validate(document.addusers,2) ){
		return false;
	}
	$("#isApprove").val(isApprove);
	$.ajax({  
    	type : "POST",
		url : "../users/approveUserApplyAction.do",
		data : $("#addusers").serializeArray(),
		dataType: "json",
		async: false,
        success: function (data) {  
            alert(data.returnMsg);
            if(data.returnCode == 0) {
            	if (window.opener.document.forms[0]){
            		window.opener.document.forms[0].submit();
            	}
            	window.close(); 
		    }
        },  
        error: function (data) {  
        	alert("审核失败");  
        }  
    }); 
}

function checkLoginName(){
	var mobile = $('#loginName').val().trim();
	var userId = $('#userId').val().trim();
	if ( mobile == "" ){	
		return;
	}
	$.ajax({  
    	type : "POST",
		url : "../users/ajaxCheckUsersAction.do?type=1",
		data : "mobile="+mobile+"&userId="+userId,
		dataType: "json",
		async: false,
        success: function (data) {  
            if(data.returnCode != 0) {
            	 alert("该登陆名已存在");
		    } 
        },  
        error: function (data) {  
        	alert("网络异常");  
        }
    });  
}

</script>
	</head>

	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<table width="100%" height="40" border="0" cellpadding="0"
									cellspacing="0" class="title-back">
									<tr>
										<td width="10">
											<img src="../images/CN/spc.gif" width="10" height="1">
										</td>
										<td width="772">
											审核用户
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<form name="addusers" id="addusers" method="post" action="addUsersAction.do">
									<input name="userId" type="hidden" id="userId" value="${ua.id}">
									<input name="isApprove" type="hidden" id="isApprove" value="${isApprove}">
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
											<c:if test="${ua.userType == 1 or ua.userType == 2}">
											<tr>
												<td width="13%" align="right">
													登录名：
												</td>
												<td width="22%">
													<input name="loginName" type="text" id="loginName" value="${ua.mobile}" dataType="Require" msg="必须输入登陆名!" maxlength="60" onblur="checkLoginName();"><span class="STYLE1">*</span>
												</td>
												<td width="11%" align="right">
												</td>
												<td width="23%">
												</td>
												<td width="9%" align="right">
												</td>
												<td width="22%">
												</td>
											</tr>
											</c:if>
											<tr>
												<td width="13%" align="right">
													手机号：
												</td>
												<td width="22%">
													${ua.mobile}
												</td>
												<td width="11%" align="right">
													姓名：
												</td>
												<td width="23%">
													${ua.name}
												</td>
												<td width="9%" align="right">
													机构名称：
												</td>
												<td width="22%">
													${ua.organName}
												</td>
											</tr>
											<tr>
												<td align="right">
													所属机构：
												</td>
												<td>
													<input type="hidden" id="organId" name="organId"
														value="${organId}" dataType="Require" msg="必须选择所属机构!">
													<input name="oname" type="text" id="oname" maxlength="128"
														value="" readonly>
													<a href="javascript:SelectOrgan();"><img
															src="../images/CN/find.gif" width="17" height="18"
															border="0" align="absmiddle"> </a>
													<span class="STYLE1">*</span>
												</td>
												<td align="right">
													地区：
												</td>
												<td>
													${ua.provinceName} - ${ua.cityName} - ${ua.areasName}
												</td>
												<td align="right">
													用户类型：
												</td>
												<td>
													<windrp:getname key='ApplyUserType' value='${ua.userType}'
														p='f' />
												</td>
											</tr>
											<tr>
												<td align="right">
													备注：
												</td>
												<td colspan="4">
													<textarea name="remark" cols="100" style="width: 100%"
														rows="3" id="remark" dataType="Limit" max="256"
														msg="备注必须在256个字之内" require="false"></textarea>
													<br>
													<span class="td-blankout">(备注不能超过256个字符!)</span>

												</td>
											</tr>
										</table>
									</fieldset>

									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr align="center">
											<td width="33%">
												<input type="button" name="approve" value="通过" onclick="approveUser('1');">
												&nbsp;&nbsp;
												<input type="button" name="disapprove" value="不通过" onclick="approveUser('2');">
											</td>
										</tr>

									</table>
								</form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>
