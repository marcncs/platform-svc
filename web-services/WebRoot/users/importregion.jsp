<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>导入用户</title>
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
			action="../sys/importRegionAction.do"
			onSubmit="return formcheck()">

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						导入用户
					</td>
				</tr>
			</table>
			<fieldset style="text-align: center;">
				<legend>
					基本信息
				</legend>
				<table class="table-detail">
					<tr>
						<td width="9%" align="right"></td>
						<td width="21%">
							选择用户资料文件：
							<input name="usrsfile" type="file" id="usrsfile"
								size="40" dataType="Filter" accept="xls" msg="只能上传Excel文件!">
						</td>
							<td width="23%">
							<a
								href="../common/downloadfile.jsp?filename=/templates/光明渠道导入模板.xls"
								title="模板下载">模板下载</a>
						</td>

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
