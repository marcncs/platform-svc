<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>导入积分设置信息</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<script language="JavaScript">
		function formcheck() {
			var year = document.getElementById("year").value;
			if(year=="") {
				alert("请选择年度");
				return false;
			}
			showloading("处理中");
			return true;
		}
	</script>

	</head>

	<body>
		<form name="refer" method="post"
			action="../keyretailer/confirmSBonusAction.do"
			onSubmit="return formcheck()">

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						积分返利
					</td>
				</tr>
			</table>
			<fieldset style="text-align: center;">
				<legend>
					年度
				</legend>
				<table class="table-detail">
					<tr>
						<td width="9%" align="right">
							年度:
							<logic:iterate id="d" name="list">
								</logic:iterate>
						</td>
						<td width="61%">
							<select name="year" id="year">
								<option value="">
									-请选择-
								</option>
								<logic:iterate id="d" name="list">
								<option value="${d.year}">${d.year}</option>
								</logic:iterate>
							</select>
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
