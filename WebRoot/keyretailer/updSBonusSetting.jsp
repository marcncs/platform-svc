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
	function SelectSingleProductName(){
		var p=showModalDialog("../common/selectSingleProductNameAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
		if(p==undefined){return;}
		$j("#productName").val(p.productname);
		$j("#unitName").html(p.cunitname);
		$j("#countUnit").val(p.countunit);
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
								更新积分设置
							</td>
						</tr>
					</table>
					<form name="validateProvide" method="post" id="validateProvide"
						action="../keyretailer/updSBonusSettingAction.do"
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
												<td width="9%" align="right">
													截止年月：
												</td>
												<td width="23%">
													<input name="endDate" id="endDate"
														value="${endDate}"
														onFocus="javascript:selectDate(this)" readonly>
												</td>
												<td align="right">
													产品名称：
												</td>
												<td>
													<input id="productName" name="productName"
														value="${bnous.productName}" readonly dataType="Require" msg="请选择产品名称!">
													<input type="hidden" name="countUnit" id="countUnit"
														value="${bnous.countUnit}">
													<a href="javascript:SelectSingleProductName();"><img
															src="../images/CN/find.gif" align="absmiddle" border="0">
													</a><span class="style1">*</span>
												</td>
												<td align="right">
													规格：
												</td>
												<td>
													<input type="hidden" name="spec" id="spec"
														value="${bnous.spec}">
													<input type="text" name="packsizename" id="packsizename"
														value="${bnous.spec}"
														onClick="selectDUW(this,'spec',$F('productName'),'psn','')"
														readonly dataType="Require" msg="请选择产品规格!"><span class="style1">*</span>
												</td>
											</tr>
											<tr>
												<td width="9%" align="right">
													积分：
												</td>
												<td width="23%">
													<input type="text" name="bonusPoint" id="bonusPoint"
														value="${bnous.bonusPoint}" onkeyup="checkNumber(this)" dataType="Require" msg="请输入积分数量!"><span class="style1">*</span>
												</td>
												<td align="right">
													计量单位数量：
												</td>
												<td>
													<input type="text" name="amount" id="amount"
														value="${bnous.amount}" onkeyup="checkNumber(this)" dataType="Require" msg="请输入计量单位数量!">
													<span id="unitName">${unitName}</span><span class="style1">*</span>
												</td>
												<td align="right">
													账户类型：
												</td>
												<td>
													<windrp:select key="AccountType" name="accountType" value="${bnous.accountType}" p="n|f" />
												</td>
											</tr>
											<tr>
												<td width="9%" align="right">
													是否激活：
												</td>
												<td width="23%">
													<windrp:select key="YesOrNo" name="activeFlag" value="${bnous.activeFlag}" p="n|f" />
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
									</form>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
