<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>

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
								<table width="100%" border="0" cellpadding="0" cellspacing="0"
									class="title-back">
									<tr>
										<td width="10">
											<img src="../images/CN/spc.gif" width="10" height="8">
										</td>
										<td>
											服务预约详情
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
									<table width="100%" border="0" cellpadding="0" cellspacing="1"
										class="table-detail">
										<tr>
											<td width="10%" align="right">
												服务种类：
											</td>
											<td width="24%">
												${pf.scontentname}
											</td>
											<td width="15%" align="right">
												服务状态：
											</td>
											<td width="20%">
												${pf.sstatusname}
											</td>
											<td width="9%" align="right">
												等级：
											</td>
											<td width="25%">
												${pf.rankname}
											</td>
										</tr>
										<tr>
											<td align="right">
												服务费用：
											</td>
											<td>
												${pf.sfee}
											</td>
											<td align="right">
												约定服务时间：
											</td>
											<td>
												<windrp:dateformat value='${pf.sdate}'
													p='yyyy-MM-dd HH:mm:ss' />
											</td>
											<td align="right">&nbsp;
												

											</td>
											<td>&nbsp;
												

											</td>
										</tr>
										<tr>
											<td align="right">
												客户名称：
											</td>
											<td>
												${pf.cname}
											</td>
											<td align="right">
												联系人：
											</td>
											<td>
												${pf.linkman}
											</td>
											<td align="right">
												联系电话：
											</td>
											<td>
												${pf.tel}
											</td>
										</tr>
										<tr>
											<td align="right">
												问题描述：
											</td>
											<td colspan="5">
												${question}
											</td>
										</tr>
										
									</table>
								</fieldset>

								<fieldset align="center">
									<legend>其它信息</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
										<tr>
											<td width="10%" align="right">
												制单机构：
											</td>
											<td width="24%">
												<windrp:getname key="organ" p="d" value="${pf.makeorganid}" />
											</td>
											<td width="15%" align="right">
												制单部门：
											</td>
											<td width="20%">
												<windrp:getname key="dept" p="d" value="${pf.makedeptid}" />
											</td>
											<td align="right">

											</td>
											<td>

											</td>
										</tr>
										<tr>
											<td width="10%" align="right">
												制单人：
											</td>
											<td width="24%">
												<windrp:getname key="users" p="d" value="${pf.makeid}" />
											</td>
											<td width="15%" align="right">
												制单日期：
											</td>
											<td width="20%">
												<windrp:dateformat value='${pf.makedate}' p='yyyy-MM-dd' />
											</td>
											<td width="10%" align="right">
												是否分配：
											</td>
											<td>
												${pf.isallotname}
											</td>
										</tr>
									</table>
								</fieldset>

								<fieldset align="center">
									<legend>
										执行者信息
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
										<tr align="center" class="title-top">
											<td width="11%">
												是否查收
											</td>
											<td width="89%">
												执行者
											</td>
										</tr>
										<logic:iterate id="r" name="rvls">
											<tr class="table-back-colorbar">
												<td>
													${r.isaffirmname}
												</td>
												<td>
													${r.useridname}
												</td>
											</tr>
										</logic:iterate>
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<IFRAME id="submsg"
			style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
			name="submsg" src="" frameBorder="0" scrolling="no"></IFRAME>

	</body>
</html>
