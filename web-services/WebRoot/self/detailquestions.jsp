<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>

		<title>常见问题详情</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<table width="100%" height="40" border="0" cellpadding="0"
			cellspacing="0" class="title-back">
			<tr>
				<td width="10">
					<img src="../images/CN/spc.gif" width="10" height="1">
				</td>
				<td width="772">
					常见问题详情
				</td>
			</tr>
		</table>

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
					<td width="10%" align="right">
						标题：
					</td>
					<td>
						${questions.title}
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">
						内容：
					</td>
					<td style="word-wrap: break-word">
						${content}
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset align="center">
			<legend>
				<table width="50" border="0" cellpadding="0" cellspacing="0"
					>
					<tr>
						<td>
							制单信息
						</td>
					</tr>
				</table>
			</legend>
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
				<tr>
					<td width="10%" align="right">
						制单机构：
					</td>
					<td width="20%">
						<windrp:getname key='organ' value='${questions.makeorganid}' p='d' />
					</td>
					<td width="10%" align="right">
						制单部门：
					</td>
					<td width="20%">
						<windrp:getname key='dept' value='${questions.makedeptid}' p='d' />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">
						制单人：
					</td>
					<td>
						<windrp:getname key='users' value='${questions.makeid}' p='d' />
					</td>
					<td align="right">
						制单时间：
					</td>
					<td>
						<windrp:dateformat value='${questions.makedate}' p='yyyy-MM-dd'/>
					</td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</fieldset>


	</body>
</html>
