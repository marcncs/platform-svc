<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
	</head>

	<body>
		<form name="addform" method="post" action="approveWorkReportAction.do">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bordercolor="#BFC0C1">
				<tr>
					<td>
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="8">
								</td>
								<td>
									工作报告详情
								</td>
							</tr>
						</table>
						<fieldset align="center">
							<legend>
								<table width="50" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											报告信息
										</td>
									</tr>
								</table>
							</legend>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr>
									<td width="13%" align="right">
										报告创建者：
									</td>
									<td width="87%">
										<windrp:getname key='users' value='${makeid}' p='d' />
									</td>
								</tr>
								<tr>
									<td align="right">
										报告分类：
									</td>
									<td>
										<windrp:getname key='ReportSort' value='${reportsort}' p='f' />
									</td>
								</tr>
								<tr>
									<td align="right">
										报告内容：
									</td>
									<td>
										${reportcontent}
									</td>
								</tr>
							</table>
						</fieldset>
						<fieldset align="center">
							<legend>
								<table width="50" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											审阅信息
										</td>
									</tr>
								</table>
							</legend>
							<table width="100%">

								<tr>
									<td width="13%" align="right">
										<input name="reportid" type="hidden" value="${reportid}">
										审阅内容：
									</td>
									<td width="87%">
										<textarea name="approvecontent" style="width:100%;"
											cols="80" rows="10" id="approvecontent"></textarea>
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
	</body>
</html>
