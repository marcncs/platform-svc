<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>导入机构</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<script language="JavaScript">
		function formcheck() {
			if (!Validator.Validate(document.refer, 2)) {
				return false;
			}
			showloading("文件已上传，请等待处理结果");
			return true;
		}
	</script>

	</head>

	<body>
		<form name="refer" method="post" enctype="multipart/form-data"
			action="../users/importOrganAction.do"
			onSubmit="return formcheck()">

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						导入机构
					</td>
				</tr>
			</table>
			<fieldset style="text-align: center;">
				<legend>
					基本信息
				</legend>
				<table class="table-detail">
					<tr>
						<td width="35%" align="right">上传类型</td>
						<td width="40%">
							<select id="type" name="type" dataType="Require" msg="必须选择上传类型!">
								<option value="">请选择</option>
								<option value="1">新增</option>
								<option value="2">更新</option>
							</select><span class="style1">*</span>
						</td>
						<td title="点击下载" width="25%" >
						</td>

					</tr>
					<tr>
						<td align="right">选择机构资料文件：</td>
						<td>
							<input name="idcodefile" type="file" id="idcodefile"
								size="40" dataType="Filter" accept="xls,xlsx" msg="只能上传Excel文件!">
						</td>
						<td title="点击下载">
						</td>

					</tr>
					<tr>
						<td align="right"></td>
						<td>
							<a href="../common/downloadfile.jsp?filename=templates/<windrp:encode key='机构导入模板.xlsx' />"><img src="../images/CN/beizheng.gif" border="0">导入模板下载</a>
							<a href="../common/downloadfile.jsp?filename=templates/<windrp:encode key='机构更新模板.xlsx' />"><img src="../images/CN/beizheng.gif" border="0">更新模板下载</a>
						</td>
						<td title="点击下载">
								
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
