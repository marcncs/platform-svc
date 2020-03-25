<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>


	<head> 
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script language="JavaScript">
function formcheck(){ 
	if ( !Validator.Validate(document.refer,2) ){
		return false;
	}
	showloading();
	return true;
}
 function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		var oldOrgan = document.refer.organid.value;
			document.refer.organid.value=p.id;
			document.refer.orgname.value=p.organname;
	}
</script>
	</head>
	<html:errors />
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<table width="100%" height="40" border="0" cellpadding="0"
									cellspacing="0" class="title-back">
									<tr>
										<td width="10">
											<img src="../images/CN/spc.gif" width="10" height="1">
										</td>
										<td width="772">
											导入
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<form name="refer" method="post"
									action="../report/updInventoryDetailHistoryAction.do"
									onSubmit="return formcheck()">
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
												<td width="20%" align="right">
													机构：
												</td>
												<td width="30%">
													<input name="organid" type="hidden" id="organid"
														value="${organid}">
													<input name="orgname" type="text" id="orgname" size="30"
														value="${oname}"
														readonly>
													<a href="javascript:SelectOrgan2();"><img
															src="../images/CN/find.gif" width="18" height="18"
															border="0" align="absmiddle"> </a>
												</td>
												<td align="right">
													日期:
												</td>
												<td>
													<input name="startDate" type="text" id="startDate" dataType="Require" msg="日期不能为空!"
														value="${startDate}" onFocus="javascript:selectDate(this)"
														readonly><span class="style1">*</span>
												</td>
											</tr>
										</table>
									</fieldset>

									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr align="center">
											<td>
												<input type="submit" name="Submit" value="重新计算">
												&nbsp;&nbsp;
												<input type="button" name="cancel" onClick="window.close()"
													value="取消">
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>
