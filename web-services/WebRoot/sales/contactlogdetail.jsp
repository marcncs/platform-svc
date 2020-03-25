<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>

<html>

	<script type="text/javascript" src="../js/pub_Calendar.js"></script>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
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
											<img src="../images/CN/spc.gif" width="10" height="8">
										</td>
										<td width="772">
											商务联系详情
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset align="center">
									<legend>
										<table width="50" border="0" cellpadding="0" cellspacing="0"
											class="table-detail">
											<tr>
												<td>
													基本信息
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
										<tr>
											<td width="15%" align="right">
												联系日期：
											</td>
											<td width="20%">
												<windrp:dateformat value='${clf.contactdate}' p='yyyy-MM-dd'/>
											</td>
											<td width="10%" align="right">
												联系方式：
											</td>
											<td width="20%">
												<windrp:getname key='ContactMode' value="${clf.contactmode}"
													p='f' />
											</td>
											<td width="15%" align="right">
												联系性质：
											</td>
											<td width="20%">
												<windrp:getname key='ContactProperty'
													value="${clf.contactproperty}" p='f' />
											</td>
											 
										</tr>
										<tr>
											<td align="right">
												对象名称：
											</td>
											<td>
												${clf.cname}
											</td>
											<td align="right">
												联系人：
											</td>
											<td>
												${clf.linkman}
											</td>
											<td align="right">
												下次联系时间：
											</td>
											<td>
												<windrp:dateformat value='${clf.nextcontact}' p='yyyy-MM-dd'/>
											</td>
										</tr>
										<tr>
											<td align="right">
												下次联系目标：
											</td>
											<td colspan="5">
												${clf.nextgoal}
											</td>
										</tr>
										<tr>
											<td align="right">
												联系内容：
											</td>
											<td colspan="5">
												${clf.contactcontent}
											</td>
										</tr>
										<tr>
											<td align="right">
												客户反馈：
											</td>
											<td colspan="5">
												${clf.feedback}
											</td>
										</tr>
									</table>
								</fieldset>
								
								<fieldset align="center">
									<legend>
										<table width="50" border="0" cellpadding="0" cellspacing="0"
											class="table-detail">
											<tr>
												<td>
													状态信息
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-detail">
										<tr>
											<td align="right" width="15%">制单机构：</td>
											<td width="20%"><windrp:getname key='organ' value='${clf.makeorganid}' p='d'/></td>
											<td align="right" width="10%">制单部门：</td>
											<td><windrp:getname key='dept' value='${clf.makedeptid}' p='d'/> </td>
                                            <td align="right">制单人：</td>
											<td ><windrp:getname key='users' value='${clf.makeid}' p='d'/> </td>
										</tr>
										<tr>
											<td align="right">制单时间：</td>
											<td><windrp:dateformat value='${clf.makedate}' p='yyyy-MM-dd'/>
                                            <td></td>
                                            <td></td>
											 
										</tr>
										
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>
