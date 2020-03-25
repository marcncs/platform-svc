<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>导入区域</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">
		function formcheck() {
			if (!Validator.Validate(document.refer, 2)) {
				return false;
			}
			showloading("文件处理中...");
			return true;
		}

		function valid() {
			var fileName = $('input:file').val();
			var regex = /\.xls(x)?$/;
			if (regex.test(fileName)) {
				showloading("文件已上传，请等待处理结果");
				return true;
			}
			alert('只能上传Excel文件!');
			return false;
		}
	</script>

	</head>

	<body>
		<form name="refer" method="post" enctype="multipart/form-data"
			action="../sys/importRegionFileAction.do?op=importXls"
			onSubmit="return valid();">

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						导入区域
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
						<td width="61%">
							选择文件：
							<input name="file" type="file" 
								size="40" dataType="Filter" accept="xls" msg="只能上传Excel文件!">
						</td>
						<td title="点击下载" width="30%" >
								<a href="../common/downloadfile.jsp?filename=templates/<windrp:encode key='销售区域导入模板.xls' />"><img src="../images/CN/beizheng.gif" border="0">模板下载</a>
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
