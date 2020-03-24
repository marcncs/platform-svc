<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>发货单导入</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
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
			action="../warehouse/importSapIdcodeAction.do"
			onSubmit="return formcheck()">
			<input type="hidden" id="billid" name="billid" value="${billid}" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						发货单条码导入......
					</td>
				</tr>
			</table>
			<fieldset style="text-align: center;">
				<legend>
					基本信息
				</legend>
				<table class="table-detail">
					<tr>
						<td width="9%" align="right"><input type="hidden" id="prid" name="prid" value="${prid }" /></td>
						<td width="21%">
							选择发货单条码资料文件：
							<input name="idcodefile" type="file" id="idcodefile"
								size="40" dataType="Filter" accept="xlsx,XLSX" msg="只能上传xlsx文件!">
						</td>
						<!-- 
						<td width="23%">
							<a
								href="../common/downloadfile.jsp?filename=/templates/sap发货单码信息导入模板.txt"
								title="模板下载">模板下载</a>
						</td>
						 -->
					</tr>
				</table>
			</fieldset>
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr align="center">
                <td ><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                    <input type="button" name="cancel" onClick="window.close()" value="取消"></td>
                </tr>
        </table> 
		</form>
	</body>
</html>
