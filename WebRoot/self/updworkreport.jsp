<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>修改工作报告</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT LANGUAGE=javascript src="../js/function.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
function Chk(){
		reportcontent = document.ValidateWorkReport.reportcontent;
		
		if(reportcontent.value==null||reportcontent.value=="" ||reportcontent.value.trim()==""){
			alert("请输入内容");
			reportcontent.focus();
			return false;
		}
		
		if(reportcontent.value.length>512){
			alert("报告内容不能超过512个字符");
			reportcontent.select();
			return false;
		}
		showloading();
		return true;
	}
	</script>
	</head>

	<body style="overflow-y: auto;">
		<form name="ValidateWorkReport" method="post"
			action="updWorkReportExecuteAction.do" enctype="multipart/form-data"
			onSubmit="return Chk();">
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
									修改工作报告
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
									<td width="12%" align="right">
										<input name="id" type="hidden" id="id" value="${id}">
										报告分类：
									</td>
									<td width="88%">
										<windrp:select key="ReportSort" name="reportsort" p="n|f"
											value="" />
									</td>
								</tr>
								<tr>
									<td align="right" valign="top">
										内容：
									</td>
									<td>
										<textarea name="reportcontent" style="width: 100%;" cols="120" rows="15"
											id="content">${reportcontent}</textarea><br><span class="td-blankout">(内容不能超过512个字符!)</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										<input name="oldaffix" type="hidden" id="oldaffix"
											value="${affix}">
										附件：
									</td>
									<td>
										<input name="affix" type="file" id="affix" size="50">
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
