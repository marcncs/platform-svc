<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type=text/javascript src="../js/function.js"></SCRIPT>
		<script type=text/javascript src="../js/prototype.js"></script>
		<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/showSQ.js"></SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language=javascript>
		
		//jQuery解除与其它js库的冲突
		var $j = jQuery.noConflict(true);

		function checknum(obj){
			obj.value = obj.value.replace(/[^\d]/g,"");
		}
		function SelectSBonusAccount(){
			var p=showModalDialog("../keyretailer/selectSBonusAccountAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
			if(p==undefined){return;}
			$j("#accountId").val(p.accountId);
			$j("#mobile").html(p.mobile);
			$j("#companyName").val(p.companyName);
			$j("#name").val(p.name);
		}
	
		function checkNumber(obj){
			if(isNaN(obj.value)) {
				obj.value = obj.value.replace(/[^\d.]/g,"");
				obj.value = obj.value.replace(/^\./g,"");
				obj.value = obj.value.replace(/\.{2,}/g,".");
				obj.value = obj.value.replace(".","$#$").replace(/\./g,"","").replace("$#$",".");
			}
		}
	
		function ChkValue(){
			if ( !Validator.Validate(document.validateProvide,2) ){
				return false;
				}		
			showloading();
	   	   	return true;
		}
	
</script>

	</head>
	<body style="overflow: auto">
		<table width="100%" border="1" cellpadding="0" cellspacing="1"
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
								调整积分
							</td>
						</tr>
					</table>
					<form name="validateProvide" method="post" id="validateProvide"
						action="../keyretailer/updSBonusAppraiseAction.do"
						onSubmit="return ChkValue();">
						<input type="hidden" name="id" id="id" value="${id}">
						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td>
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
												<td align="right">
													积分账号：
												</td>
												<td>
													<span id="name">${sba.accountid}</span>
													<input name="accountId"  value="${sba.accountid}" type="hidden" readonly="readonly">
												</td>
												<td width="9%" align="right">
													手机号码：
												</td>
												<td width="23%">
													${sba.mobile}
												</td>
												<td align="right">
													姓名：
												</td>
												<td>
													<span id="name">${sba.name}</span>
												</td>
											</tr>
											<tr>
												<td width="9%" align="right">
													单位名称：
												</td>
												<td width="23%">
													${sba.companyname}
												</td>
												<td align="right">
													单位地址：
												</td>
												<td>
													${sba.address}
												</td>
												<td align="right">
													积分值:
												</td>
												<td>
													<span id="bonuspoint">${sba.bonuspoint}</span>
												</td>
											</tr>
											<tr>
												<td width="9%" align="right">
													积分评价系数：
												</td>
												<td width="23%">
													<input type="text" name="appraise" id="appraise" value="<windrp:format value='${sba.appraise}'/>" onkeyup="checkNumber(this)" dataType="Require" msg="请输入积分值!">
													<span class="style1">*</span>
												</td>
												<td align="right">
												</td>
												<td>
												</td>
												<td align="right">
												</td>
												<td>
												</td>
											</tr>
										</table>
									</fieldset>
								</td>
							</tr>
							<tr>
								<td align="right">
									<table width="100%" id="dbtable" border="0" cellpadding="0"
										cellspacing="1">
										<tr id="ccv">
											<td colspan="4">
											</td>
										</tr>
										<tr>
											<td align="center">
												<input type="Submit" name="Submit" value="提交">
												&nbsp;&nbsp;
												<input type="button" name="Submit2" value="取消"
													onClick="window.close();">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
