<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<HTML>
	<HEAD>
		<TITLE>修改日程</TITLE>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</HEAD>
	<script type="text/javascript" src="../js/function.js"></script>
	<SCRIPT type=text/javascript src="../js/selectDateTime.js"></SCRIPT>
		<script type="text/javascript" src="../js/validator.js"></script>
<script type="text/javascript">
	function ChkValue(){
		
		if ( !Validator.Validate(document.addusers,2) ){
		return false;
		}
		var AwakeContent = document.awake.AwakeContent;
		if(AwakeContent.value.trim()==""){
			alert("请输入计划内容");
			return false;
		}
		showloading();
		return true;
		
	}
	</script>
	<body style="overflow: auto;">
	<form name="awake" method="post" onSubmit="return ChkValue();"
				action="updCalendarExecuteAction.do">
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
							<td>
								修改日程
							</td>
						</tr>
					</table>
				</td>
			</tr>
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
						<table width=100% border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="80" align="right">
									计划日期：
								</td>
								<td>
									<input type="hidden" name="ID" value="${ID}">
									<input name="AwakeDate" type="text" id="AwakeDate" dataType="Require" msg="必须录入计划日期!"
										value="${awakeDate}" onFocus="selectDate(this);" readonly>
									&nbsp;&nbsp; 时间：&nbsp;
									<input name="AwakeTime" type="text" id="AwakeTime" dataType="Require" msg="必须录入时间!"
										value="${awakeTime}" onFocus="selectTime(this);" readonly>
								</td>
							</tr>
							<tr>
								<td align="right">
									内容：
								</td>
								<td>
								<textarea name="AwakeContent"  rows="8"  style="width: 100%;" 
									id="AwakeContent" dataType="Limit" max="256"  msg="内容必须在256个字之内" require="false"  >${awakeContent}</textarea><br>
									<span class="td-blankout">(内容长度不能超过256字符)</span>
								</td>
							</tr>
						</table>
					</fieldset>
					<table width="100%">
						<tr>
							<td align="center">
								<input type="submit" name="Submit" value="确定">
								&nbsp;
								<input type="button" name="cancel" value="取消"
									onClick="javascript:window.close();">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
		</table>
		</form>
	</BODY>
</HTML>