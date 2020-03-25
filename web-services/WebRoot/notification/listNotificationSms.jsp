<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>短信信息详细</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/showPE.js"> </SCRIPT>
		<script type="text/javascript">


		</script>
  </head>
  
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
								短信详情
							</td>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									
								</table>
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
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td width="90" align="right">
									短信编号：
								</td>
								<td >
									${sms.id}
								</td>
								<td  align="right">
									手机号码：
								</td>
								<td >
									${sms.mobileNo}
								</td>
								<td  align="right">
									发送时间：
								</td>
								<td >
									<windrp:dateformat value="${sms.sendTime}" p="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td  align="right">
									发送状态：
								</td>
								<td >
									<windrp:getname key='SmsSendStatus' value='${sms.sendStatus}' p='f' />
								</td>
							</tr>
							<tr>
								<td align="right">
									发送内容：
								</td>
								<td colspan="7">
									${sms.content }
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</body>
</html>
