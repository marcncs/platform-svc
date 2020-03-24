<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ include file="../common/tag.jsp"%>
<html> 
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/validator.js"></script>
		<script language="javascript" src="../js/jquery-1.4.2.min.js"></script>
		<SCRIPT language="javascript" src="../js/passwordcheck.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectProvince.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">

	</head>
	<script language="javascript">

function ChkValue(){

	if ( !Validator.Validate(document.addform,2) ){
		return false;
	}
	return true;
}

</script>
	<body>
		<div id="over"></div>
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
							</td>
						</tr>
					</table>
					<form id="addform" name="addform" method="post"
						action="../assistant/trace.do">
						<fieldset align="center">
							<legend>
								<table width="50" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
										</td>
									</tr>
								</table>
							</legend>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<br />
								<tr>
									<td width="9%" align="right">
										条码：
									</td>
									<td width="9%">
										<input id="code" name="code" type="text" maxlength="64"
											value="${code}" dataType="Require" msg="请录入要查询的条码!">
										<span class="STYLE1">*</span>
										<input type="submit" id="submitbtn" name="submitbtn"
											value="查询" onclick="ChkValue();">
									</td>
								</tr>
								<tr>
									<td colspan="2">
										&nbsp;
									</td>
								</tr>
								<c:choose>
									<c:when test="${result !=null}">
										<tr>
											<td colspan="2">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td width="9%" align="right">
												产品名称：
											</td>
											<td width="9%">
												${result.PRODUCTNAME}
											</td>
										</tr>
										<tr>
											<td colspan="2">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td width="9%" align="right">
												登记证持有人名称：
											</td>
											<td width="9%">
												${result.REGCERTUSER}
											</td>
										</tr>
										<tr>
											<td colspan="2">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td width="9%" align="right">
												产品批次：
											</td>
											<td width="9%">
												${result.BATCH}
											</td>
										</tr>
										<tr>
											<td colspan="2">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td align="right">
												检验时间：
											</td>
											<td>
												${result.INSPECTDATE}
											</td>
										</tr>
										<tr>
											<td colspan="2">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td align="right">
												是否合格：
											</td>
											<td>
												<windrp:getname key='YesOrNo' value='${result.ISQUALIFIED}'
													p='f' />
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="2">
												未查到相关信息!
											</td>
										</tr>
									</c:otherwise>
								</c:choose>

							</table>
						</fieldset>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
