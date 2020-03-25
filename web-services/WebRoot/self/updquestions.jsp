<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>修改常见问题</title>
		<SCRIPT type=text/javascript src="../js/selectDateTime.js"></SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/selectduw.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">

		<script type="text/javascript">
		function Chk(){
			var TPTitle = document.search.title;
			var TPContent = document.search.content;
			if(TPTitle.value.trim()==""||TPTitle.value==""){
				alert("标题不能为空!");
				return false;
			}
			if(TPContent.value.length>512){
				alert("问题描述不能超过512个字符!");
				TPContent.select();
				return false;
			}
			
			return true;
		}
			
		</script>


	</head>

	<body style="overflow: auto;">
	<form name="search" method="post" action="updQuestionsAction.do"
					onSubmit="return Chk();">
		<table width="100%" height="40" border="0" cellpadding="0"
			cellspacing="0" class="title-back">
			<tr>
				<td width="10">
					<img src="../images/CN/spc.gif" width="10" height="1">
				</td>
				<td >
					修改常见问题
				</td>
			</tr>
		</table>

		<fieldset style="text-align: center;">
			<legend>

				基本信息

			</legend>
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
				
				<tr>
					<td style="width: 100px;" align="right">
						标题：
					</td>
					<td>
						<input type="hidden" id="id" name="id" value="${questions.id }">
						<input name="title" type="text" id="title" maxlength="128" size="80" value="${questions.title }">
						<span class="STYLE1">*</span>
					</td>
				</tr>
				<tr>
					<td style="width: 100px;" align="right">
						问题描述：
					</td>
					<td colspan="5">
						<textarea name="content" id="content"  style="width: 100%; " cols="80" rows="5">${questions.content }</textarea><br>
						<span class="td-blankout">(问题描述长度不能超过512字符)</span>
					</td>
				</tr>
				
				
			</table>
		</fieldset>
		<table width="100%">
				<tr>
					<td align="center">
						<input type="submit" name="Submit" value="确定">
						&nbsp;&nbsp;
						<input type="button" name="cancel" value="取消"
							onClick="javascript:window.close();">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
