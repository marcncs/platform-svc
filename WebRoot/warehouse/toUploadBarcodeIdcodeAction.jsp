<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>条码导入</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script language="JavaScript">
		function formcheck() {
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
			action="../warehouse/uploadBarcodeIdcodeAction.do"
			onSubmit="return formcheck()">

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						条码导入
					</td>
				</tr>
			</table>
			<fieldset style="text-align: center;">
				<legend>
					基本信息
				</legend>
				<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td align="right">
							单据编号：
						</td>
						<td>
							<input name="billNo" type="text" id="billNo" size="30"
								dataType="Require" msg="必须输入单据编号!" value="">
							<span class="style1">*</span>
						</td>
						<td align="right">
						</td>
						<td>
						</td>
					</tr>
				</table>
				<table class="table-detail">
					<tr>
						<td width="9%" align="right">
							<input type="hidden" id="prid" name="prid" value="${prid }" />
						</td>
						<td width="21%">
							选择条码资料文件：
							<input name="idcodefile" type="file" id="idcodefile" size="40"
								dataType="Filter" accept="xls" msg="只能上传Excel文件!">
						</td>
						<td width="23%">
							<a href="../common/downloadfile.jsp?filename=/templates/<windrp:encode key='条码盘点条码导入模板.xls' />"
								title="模板下载">模板下载</a>
						</td>
					</tr>
				</table>
			</fieldset>
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
				<tr align="center">
					<td>
						<input type="submit" name="Submit" value="确定">
						&nbsp;&nbsp;
						<input type="button" name="cancel" onClick="window.close()" value="取消">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
