<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type=text/javascript src="../js/function.js"></SCRIPT>
		<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language=javascript>
		
	function checknum(obj){
		obj.value = obj.value.replace(/[^\d]/g,"");
	}
	
	function checkNumber(obj){
		if(isNaN(obj.value)) {
			obj.value = obj.value.replace(/[^\d.]/g,"");
			obj.value = obj.value.replace(/^\./g,"");
			obj.value = obj.value.replace(/\.{2,}/g,".");
			obj.value = obj.value.replace(".","$#$").replace(/\./g,"","").replace("$#$",".");
		}
	}
	
	function ChkValue(){
		if ( !Validator.Validate(document.validateProvide,2) ){
			return false;
			}		
		showloading();
   	   	return true;
	}

	//加载事件
	$(function(){
		$("#configType").change(changeType);
	});


	function changeType(){
		var configTypeVal = $("#configType").val(); 
		if(configTypeVal != 3){ 
			$("#ratio").hide();
			$("#value").removeAttr("dataType");
			$("#dateRange").show();
			$("#startDate").attr("dataType","Require");
			$("#endDate").attr("dataType","Require");
		} else {
			$("#ratio").show();
			$("#value").attr("dataType","Require");
			$("#dateRange").hide();
			$("#startDate").removeAttr("dataType");
			$("#endDate").removeAttr("dataType");
		}
	}
	
</script>

	</head>
	<body style="overflow: auto">
		<table width="100%" border="1" cellpadding="0" cellspacing="1"
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
								新增积分设置
							</td>
						</tr>
					</table>
					<form name="validateProvide" method="post" id="validateProvide"
						action="../keyretailer/addSBonusConfigAction.do"
						onSubmit="return ChkValue();">
						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td>

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
													年度：
												</td>
												<td width="23%">
													<input type="text" name="year" id="year" maxlength="4" onkeyup="checknum(this)"
														dataType="Require" msg="请输入年份!"><span class="style1">*</span>
												</td>
												<td width="9%" align="right">
													参数类型
												</td>
												<td width="23%">
													<windrp:select key="BonusConfigType" name="configType" p="y|f"
														value="${configType}" /><span class="style1">*</span>
												</td>
											</tr>
											<tr id="dateRange" style="display: none;">
												<td align="right">
													日期：
												</td>
												<td>
													<input name="startDate" type="text" id="startDate" size="10" 
														onFocus="javascript:selectDate(this)" dataType="Require" msg="请输入开始日期!">
													-
													<input name="endDate" type="text" id="endDate" size="10" 
														onFocus="javascript:selectDate(this)" dataType="Require" msg="请输入截止日期!">
													</td>
												</td>
											</tr>
											<tr id="ratio" style="display: none;">
												<td align="right">
													达标系数：
												</td>
												<td>
													<input type="text" name="value" id="value" onkeyup="checkNumber(this)"
														dataType="Require" msg="请输入达标系数!"><span class="style1">*</span>
												</td>
											</tr>
										</table>
									</fieldset>
								</td>
							</tr>
							<tr>
								<td align="right">
									<table width="100%" id="dbtable" border="0" cellpadding="0"
										cellspacing="1">
										<tr id="ccv">
											<td colspan="4">
											</td>
										</tr>
										<tr>
											<td align="center">
												<input type="Submit" name="Submit" value="提交">
												&nbsp;&nbsp;
												<input type="button" name="Submit2" value="取消"
													onClick="window.close();">
											</td>
										</tr>

									</table>
									</form>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
