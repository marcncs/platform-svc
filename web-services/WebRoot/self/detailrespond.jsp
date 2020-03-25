<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>问题回复详情</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<table width="100%" height="40" border="0" cellpadding="0"
			cellspacing="0" class="title-back">
			<tr>
				<td width="10">
					<img src="../images/CN/spc.gif" width="10" height="1">
				</td>
				<td>
					问题回复详情
				</td>
			</tr>
		</table>

		<fieldset style="text-align: center;">
			<legend>
				基本信息
			</legend>
			<table width="100%" border="0" cellpadding="0" cellspacing="1" >
				<tr>
					<td align="right" width="10%">
						内容：
					</td>
					<td style="width: 600px; word-wrap: break-word">
						${respond.content}
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset style="text-align: center;">
			<legend>
				制单信息
			</legend>
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
				<tr>
					<td width="10%" align="right">
						制单机构：
					</td>
					<td width="20%">
						<windrp:getname key='organ' value='${respond.makeorganid}' p='d' />
					</td>
					<td width="10%" align="right">
						制单部门：
					</td>
					<td width="20%">
						<windrp:getname key='dept' value='${respond.makedeptid}' p='d' />
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="right">
						制单人：
					</td>
					<td>
						<windrp:getname key='users' value='${respond.makeid}' p='d' />
					</td>
					<td align="right">
						制单时间：
					</td>
					<td>
						<windrp:dateformat value='${respond.makedate}' p='yyyy-MM-dd' />
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>


	</body>
</html>
