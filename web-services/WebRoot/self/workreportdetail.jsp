<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/function.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
	</head>
	<script type="text/javascript">
	function isApprove(status){
		if(status ==1)
			popWin("../self/toApproveWorkReportAction.do?reportid="+${tpid},1000,600);
		else
			popWin2("../self/approveWorkReportAction.do?reportid="+${tpid}+"&status="+status);
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
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td>
								工作报告详情
							</td>
							<td align="right" style="padding-right: 20px;">
								
								<c:if test="${wr.makeid != userid}">
								<c:choose>
									<c:when test="${isApprove==0}">
										<a href="javascript:isApprove(1);">审阅</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:isApprove(2);">取消审阅</a>
									</c:otherwise>
								</c:choose>
								</c:if>
							</td>
						</tr>
					</table>

					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0" class="table-detail">
								<tr>
									<td>
										基本信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
							<tr>
								<td style="width: 100px;"  align="right">
									报告分类：
								</td>
								<td>
									<windrp:getname key='ReportSort' value='${wr.reportsort}' p='f' />
								</td>
								<td  align="right">
									报告创建者：
								</td>
								<td>
									<windrp:getname key='users' value='${wr.makeid}' p='d' />
								</td>
								<td  align="right">
									创建日期：
								</td>
								<td>
									<windrp:dateformat value='${wr.makedate}' p='yyyy-MM-dd' />
								</td>
							</tr>
							<tr>
								<td style="width: 100px;" align="right">
									附件：
								</td>
								<td colspan="3"><img src="../images/CN/beizheng.gif" border="0">
									<a href="../common/downloadfile.jsp?filename=${wr.affix}">${wr.affix}</a>
								</td>
								<td align="right">
									是否审阅：
								</td>
								<td>
									<windrp:getname key='ApproveStatus' value='${wr.approvestatus}'
										p='f' />
								</td>
							</tr>
							<tr>
								<td style="width: 100px;" align="right">
									报告内容：
								</td>
								<td  colspan="5" style="word-break:break-all"> 
										${content}
								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0" class="table-detail">
								<tr>
									<td>
										审阅信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr align="center" class="title-top">
								<td width="8%">
									是否审阅
								</td>
								<td width="14%">
									审阅者
								</td>
								<td width="78%">
									审阅内容
								</td>
							</tr>
							<logic:iterate id="wre" name="alls">
								<tr align="center" class="table-back-colorbar">
									<td>
										${wre.approve}
									</td>
									<td>
										${wre.approveuser}
									</td>
									<td>
										<div title="${wre.approvecontent}">
											${wre.approvecontent}
										</div>

									</td>
								</tr>
							</logic:iterate>
						</table>
					</fieldset>

				</td>
			</tr>
		</table>
	</body>
</html>
