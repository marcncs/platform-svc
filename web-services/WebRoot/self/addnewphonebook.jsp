<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>新增通讯记录</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<script language="javascript">
	function CheckInput(){
	
		var phone = document.validatePhoneBook.phone;
		var name = document.validatePhoneBook.name;
		var mobile = document.validatePhoneBook.mobile;
		if(name.value==null || name.value==""||name.value.trim()==""){
			alert("姓名不能为空！");
			return false;
		}
		
		if((phone.value != "" )&& !phone.value.isMobileOrTel()){
			alert("请输入正确的电话号码!");
			phone.select();
			return false;
		}
		if((mobile.value != "" )&& !mobile.value.isMobileOrTel()){
			alert("请输入正确的手机号码!");
			mobile.select();
			return false;
		}
		return true;
	}
</script>

	<body style="overflow-y: auto;">
		<form name="validatePhoneBook" method="post"
			action="addPhoneBookAction.do" onSubmit="return CheckInput();">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bordercolor="#BFC0C1">
				<tr>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									新增通讯记录
								</td>
							</tr>
						</table>

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
										姓名：
									</td>
									<td width="26%">
										<input name="name" type="text" id="name">
										<font color="#FF0000">*</font>
									</td>
									<td width="9%" align="right">
										性别：
									</td>
									<td width="22%">
										${sex}
									</td>
									<td width="9%" align="right">
										电话：
									</td>
									<td width="25%">
										<input name="phone" type="text" id="phone">
									</td>
								</tr>
								<tr>
									<td align="right">
										手机：
									</td>
									<td>
										<input name="mobile" type="text" id="mobile">
									</td>
									<td align="right">
										Email：
									</td>
									<td>
										<input name="email" type="text" id="email">
									</td>
									<td align="right">
										QQ：
									</td>
									<td>
										<input name="qq" type="text" id="qq">
									</td>
								</tr>
								<tr>
									<td align="right">
										MSN：
									</td>
									<td>
										<input name="msn" type="text" id="msn">
									</td>
									<td align="right">
										生日：
									</td>
									<td>
										<input name="birthday" type="text" id="birthday"
											onFocus="selectDate(this);" readonly="readonly">
									</td>
									<td align="right">
										地址：
									</td>
									<td>
										<input name="addr" type="text" id="addr">
									</td>
								</tr>
								<tr>
									<td align="right">
										分类：
									</td>
									<td>
										<select name="sortid" id="sortid">
											<logic:iterate id="s" name="sort">
												<option value="${s.id}">
													${s.sortname}
												</option>
											</logic:iterate>
										</select>
									</td>
									<td align="right">
										备注：
									</td>
									<td colspan="3">
										<input name="remark" type="text" id="remark" value=""
											size="50">
									</td>
								</tr>

							</table>
						</fieldset>
						<table width="100%">
							<tr>
								<td align="center">
									<input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
									<input type="button" name="cancel" value="取消"
										onClick="javascript:window.close();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
