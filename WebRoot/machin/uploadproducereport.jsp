<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>上传暗码</title>
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
		<form name="refer" method="post" enctype="multipart/form-data"
			action="../machin/uploadProduceReportAction.do"
			onSubmit="return formcheck()">
			
			<input type="hidden" name="type" value="${type }"> 

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						上传暗码
					</td>
				</tr>
			</table>
			<fieldset style="text-align: center;">
				<legend>
					基本信息
				</legend>
				<table class="table-detail">
					<tr>
					<!--  
					<td align="left" width="30%">
						请选择仓库：
						<select id="warehouses" name="warehouses">
						<option value="none" selected="selected"></option>
						<c:forEach var="wh"  items="${warehouses}">
						<option value="${wh.id}">${wh.warehousename}</option>
						</c:forEach>
						</select>
						<span class="style1">*</span>
						</td>
						-->
						<td   width="60%">
							选择暗码数据文件：
							<input name="idcodefile" type="file" id="idcodefile" size="40"
								dataType="Filter" accept="txt" msg="只能上传txt格式的文件!">
						</td>
						<td></td>
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
