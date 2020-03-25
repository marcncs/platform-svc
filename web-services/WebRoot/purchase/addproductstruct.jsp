<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		
		<script language="JavaScript">
			function chkForm(){
				var sortname = document.addform.sortname;
				var sortnameen = document.addform.sortnameen;
				if(sortname.value ==null || sortname.value==""||sortname.value.trim()==""){
					alert("类别名称不能为空!");
					sortname.focus();
					return false;
				}
				
				if(sortnameen.value ==null || sortnameen.value=="" || sortnameen.value.trim()==""){
					alert("类别英文名不能为空!");
					sortnameen.focus();
					return false;
				}
				if(!Validator.Validate(document.addform,2)){
					return false;
				}
				
					
				if ( !Validator.Validate(document.addform,2) ){
					return false;
				}
				showloading();
				return true;
			}
		</script>
	</head>

	<body style="overflow: auto;">
		<form name="addform" method="post" action="../purchase/addProductStructAction.do"
			onSubmit="return chkForm();">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bordercolor="#BFC0C1">
				<tr>
					<td>
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									新增产品类别
								</td>
							</tr>
						</table>

						<fieldset align="center">
							<legend>
								<table width="50" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											基本信息
										</td>
									</tr>
								</table>
							</legend>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">

								<tr>
									<td width="15%" align="right">上级类别名称：</td>
									<td >${ps.sortname}</td>
									<td width="19%" align="right">上级类别编号：</td>
									<td ><input type="hidden" name="parentid" value="${ps.structcode}"> ${ps.structcode}</td>
								</tr>
								<tr>
									<td width="12%" align="right">类别名称：</td>
									<td width="24%" >
										<input name="sortname" type="text" id="sortname" maxlength="60">
										<span class="STYLE1">*</span>
								  	</td>
								  	<td width="12%" align="right">类别英文名：</td>
									<td width="24%" >
										<input name="sortnameen" type="text"  id="sortnameen" maxlength="60" >
										<span class="STYLE1">*</span>
								  	</td>
								</tr>
							</table>
						</fieldset>
						<table width="100%">
							<tr>
								<td align="center">
									<input type="submit" name="Submit" value="确定">
									&nbsp;&nbsp;
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
