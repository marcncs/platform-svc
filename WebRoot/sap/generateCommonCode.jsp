<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>生成通用码</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
		<script language="JavaScript">
	function formcheck() {
	var warehouse=$("#warehouses").val();
	if("none"==warehouse){
	alert("请选择仓库");
	return false;
	}
		if (!Validator.Validate(document.refer, 2)) {
			return false;
		}
		showloading();
		return true;
	}
</script>

	</head>

	<body>
		<form name="refer" method="post" action="generateCommonCode.do"
			onSubmit="return formcheck()">

			<input type="hidden" name="printJobId" value="<%=request.getParameter("ID") %>">
			<input type="hidden" name="mCode" value="${mCode}">
			
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						生成通用码
					</td>
				</tr>
			</table>
			<fieldset style="text-align: center;">
				<legend>
					基本信息
				</legend>
				<table class="table-detail">
					<tr>
						<td width="15%" align="right">
							任务编号：
						</td>
						<td width="25%">
							<%=request.getParameter("ID") %>
						</td>
					</tr>
					<tr>
						<td width="15%" align="right">
							通用箱码：
						</td>
						<td width="25%">
						${commonCode}
						</td>
					</tr>
					<tr>
						<td width="15%" align="right">
							小包装码生成数量：
						</td>
						<td width="25%">
							<input name="codeCount" type="text" id="noOfCode" style="width:150"
								value="">
						</td>
					</tr>
				</table>
			</fieldset>
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
				<tr align="center">
					<td>
						<input type="submit" name="Submit" value="确定">
						&nbsp;&nbsp;
						<input type="button" name="cancel" onClick="window.close()"
							value="取消">
					</td>
				</tr>
			</table>

		</form>
	</body>
</html>
