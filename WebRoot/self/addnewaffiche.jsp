<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>添加新公告</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/selectduw.js"></script>
		<script language="javascript" src="../js/jquery-1.4.2.min.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<script language="javascript">
	function Chk(){
		var affichetitle = document.affiche.affichetitle;
		var affichecontent = document.affiche.affichecontent;
		if(affichetitle.value==""||affichetitle.value.trim()==""){
			alert("公告标题不能为空!");
			affichetitle.focus();
			return false;
		}
		if(affichecontent.value==""||affichecontent.value.trim()==""){
			alert("公告内容不能为空!");
			affichecontent.focus();
			return false;
		}
		if(affichecontent.value.length>256){
			alert("公告内容不能超过256个字符!");
			affichecontent.select();
			return false;
		}
		if($("#affichetype").val() == 3 && $("#endDate").val() == ""){
			alert("请选择截至日期");
			return false;
		}
		return true;
	}
</script>

	<body style="overflow-y: auto">
		<form name="affiche" method="post" action="addAfficheAction.do"
			onSubmit="return Chk();">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bordercolor="#BFC0C1">
				<tr>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									添加新公告
								</td>
							</tr>
						</table>
						<fieldset align="center">
							<legend>
								基本信息
							</legend>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">

								<tr>
									<td align="right">
										公告标题：
									</td>
									<td>
										<input name="affichetitle" type="text" size="30"
											maxlength="100">
											<span class="STYLE1">*</span>
									</td>
									<td align="right">
										是否发布：									
									</td>
									<td >
										<windrp:select key="YesOrNo" name="isPublish"
											value="1" p="n|f" />
									</td>
									<td align="right">
										公告类型：
									</td>
									<td >
										<windrp:select key="AfficheType1" name="affichetype" p="n|f"/>
									</td>
									<td align="right">
									截至日期(系统公告用)：
									</td>
									<td>
									<input name="endDate" type="text" id="endDate" size="10" value="${endDate}"
										onFocus="selectDate(this);" readonly="readonly">
									</td>
									<%--<td style="width: 80px;" align="right">
										重要性：
									</td>
									<td style="width: 80px;">
										<windrp:select key="Ponderance" name="ponderance" p="n|f"/>
									</td>
								--%></tr>
								<tr>
									<td style="width: 80px;" align="right">
										公告内容：
									</td>
									<td colspan="7">
										<textarea name="affichecontent" style="width: 100%;"
											cols="100" rows="8"></textarea><br><span class="td-blankout">(公告内容不能超过256个字符!)</span>
									</td>
								</tr>
							</table>
						</fieldset>
						<table width="100%">
							<tr>
								<td align="center">
									<input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
									<input type="button" name="cancel" value="取消"
										onClick="javascript:window.close();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
