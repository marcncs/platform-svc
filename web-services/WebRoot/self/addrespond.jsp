<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增常见问题回复</title>
		<SCRIPT type=text/javascript src="../js/selectDateTime.js"></SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<script type="text/javascript" src="../js/selectduw.js"></script>
		<script type="text/javascript" src="../js/function.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">

		<script type="text/javascript">
		function Chk(){
			var TPContent = document.search.content;
			if(TPContent.value.trim()==""||TPContent.value==""){
				alert("内容不能为空!");
				return false;
			}
			if(TPContent.value.length>512){
				alert("内容不能超过512个字符!");
				TPContent.select();
				return false;
			}
			
			return true;
		}
			
		</script>


	</head>

	<body style="overflow: auto;">
		<form name="search" method="post" action="addRespondAction.do"
			onSubmit="return Chk();">
			<table width="100%" height="40" border="0" cellpadding="0"
				cellspacing="0" class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td width="772">
						新增常见问题回复
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
							问题标题：
						</td>
						<td>
							${title }
						</td>
					</tr>
					<tr>
						<td style="width: 100px;" align="right">
							回复内容：
						</td>
						<td colspan="5">
							<textarea name="content" style="width: 100%;" id="content"
								cols="80" rows="5"></textarea><br>
							<span class="td-blankout">(回复内容长度不能超过512字符)</span>
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
