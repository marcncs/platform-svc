<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="../common/tag.jsp"%>
<html>
	<head>


		<title>导入对象目标</title>
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
		
		function dowloadTemp(){
			var TargetType = document.getElementsByName("TargetType")[0];
			//主管
			if(TargetType.value=="0" || TargetType.value=="1"){
				window.location.href="../common/downloadfile.jsp?filename=/templates/主管指标导入模板.xls"
			}
			//机构
			else if(TargetType.value=="2" || TargetType.value=="3"){
				window.location.href="../common/downloadfile.jsp?filename=/templates/机构指标导入模板.xls"
			}
			//区域
			else if(TargetType.value=="4" || TargetType.value=="5"){
				window.location.href="../common/downloadfile.jsp?filename=/templates/区域指标导入模板.xls"
			}
		}
	</script>

	</head>

	<body>
		<form name="refer" method="post" enctype="multipart/form-data"
			action="../users/importUserTargetAction.do"
			onSubmit="return formcheck()">

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						导入对象目标
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
				          <td width="21%">选择文件类型：
				              <select name="TargetType" dataType="Require" msg="必须选择文件类型!">
								    <logic:iterate id="type" name="typelist">
					          			<option value="${type.tagsubkey }">${type.tagsubvalue }</option>
					          		</logic:iterate>
								</select>
				            </td>
				           <td width="13%" align="left">
				           <a
								href="javascript:dowloadTemp();" 
								title="模板下载">模板下载</a>
				           </td>
				          <td width="23%">&nbsp;</td>
				          </tr>
					<tr>
						<td width="9%" align="right"></td>
						<td width="21%">
							选择对象目标资料文件：
							<input name="usrsfile" type="file" id="usrsfile"
								size="40" dataType="Filter" accept="xls" msg="只能上传Excel文件!">
						</td>
						<td width="13%" align="right">
							
						</td>
						<td width="23%">
							&nbsp;
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
