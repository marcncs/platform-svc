<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/validator.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>

<style type="text/css">
<!--
.style1 {
	color: #FF0000
}
-->
</style>
</head>
<script language="javascript">
	function SupperSelect(){
		
		var p=showModalDialog("../common/selectYunProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");

		if(p==undefined){
			return;
		}else{
			$("#ProductID").val(p.id);
			$("#ProductName").val(p.productname);
			$("#component").val(p.standardname);
			$("#certification").val(p.regcertcode);
			$("#sku").val(p.specmode);
		}
	}
	function checkSubmit(){
		if ( !Validator.Validate(document.validateProvide,2) ){
			return false;
			}	
		showloading();
	}
</script>

<body style="overflow: auto">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
		<tr>
			<td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
					<tr>
						<td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
						<td width="772">新增热门产品</td>
					</tr>
				</table>
				<form action="addPopularProductAction.do" method="post" name="validateProduct" onSubmit="return checkSubmit();">
					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>基本信息</td>
								</tr>
							</table>
						</legend>

						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td height="20" align="right">产品：</td>
								<td>
						            <input name="ProductID" type="hidden" id="ProductID">
						             <input id="ProductName" type="text"  name="ProductName" value="${ProductName}" readonly 
						             dataType="Require" msg="<bean:message key='msg.selectproduct'/>!" >
						            <a href="javascript:SupperSelect();">
						            <img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
						            <span class="style1">*</span>
					       		 </td>
							</tr>
							<%-- <tr>
								<td height="20" align="right">产品名称：</td>
								<td><input name="name" type="text" id="name" maxlength="120" dataType="Require" msg="产品名称不能为空!"> <span
									class="style1">* </span></td>
							</tr>
							<tr>
								<td width="12%" align="right">产品类别：</td>
								<td width="19%"><input type="hidden" name="psid" id="psid" value="${psid}">
									<windrp:pstree id="psid" name="productstruts" value="${psidname}" callBack="changePsNameEn()" />
									<span class="style1">*</span>
								</td>
							</tr> --%>
							<tr>
								<td height="20" align="right">宣传口号：</td>
								<td><input name="slogan" type="text" id="slogan"></td>
							</tr>
							<tr>
								<td align="right">有效成分：</td>
								<td><input readonly name="component" type="text" id="component" dataType="Require" maxlength="255" msg="有效成分不能为空!"><span class="style1">* </span></td>
							</tr>
							<tr>
								<td height="20" align="right">登记证号：</td>
								<td><input readonly name="certification" type="text" id="certification" dataType="Require" msg="登记证号不能为空!"><span class="style1">* </span></td>
							</tr>
							<tr>
								<td height="20" align="right">产品规格：</td>
								<td><input readonly name="sku" type="text" id="sku" dataType="Require" msg="规格不能为空!"><span class="style1">* </span></td>
							</tr>
							<!-- <tr>
								<td align="right">产品规格：</td>
								<td><input name="skus" type="text" id="skus" id="packSizeNameEn"></td>
							</tr> -->
						</table>
					</fieldset>

					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<td><div align="center">
									<input type="submit" name="Submit" value="确定">&nbsp;&nbsp;<input type="button" name="cancel" value="取消"
										onClick="window.close()">
								</div></td>
						</tr>
					</table>
				</form></td>
		</tr>
	</table>
</body>
</html>
