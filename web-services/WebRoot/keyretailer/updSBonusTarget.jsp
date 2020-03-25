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
		var from = $j("#fromOrganModel").val();
		var to = $j("#toOrganModel").val();	
		var year = $j("#year").val();
		var targetAmount = $j("#targetAmount").val();
		if(from=="2"&&to=="2") {
			alert("发货客户和进货机构都是BKD!");
			return false;
		}
		if(from=="1"&&to=="3") {
			alert("PD发货客户不能操作BKR进货机构!");
			return false;
		}
		if(isNaN(year)) {
			alert("年度必须是数字");
			return false;
		}
		if(isNaN(targetAmount)) {
			alert("目标数量必须是数字");
			return false;
		}
		showloading();
   	   	return true;
	}
		function SelectOrgan1(){
			var p=showModalDialog("../keyretailer/selectOrganAction.do?type=targetFrom",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.validateProvide.fromOrganId.value=p.id;
				document.validateProvide.fromorganname.value=p.organname;
				document.validateProvide.fromOrganModel.value=p.organModel;
		}
	
		function SelectOrgan2(){
			var oid = document.getElementById("fromOrganId").value;
			var p=showModalDialog("../keyretailer/selectOrganAction.do?type=targetTo&OID="+oid,null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.validateProvide.toOrganId.value=p.id;
				document.validateProvide.toorganname.value=p.organname;
				document.validateProvide.toOrganModel.value=p.organModel;
		}
	
		function SelectSingleProductName(){
			var p=showModalDialog("../sap/selectProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
			if(p==undefined){return;}
			$j("#productName").val(p.productname);
			$j("#countUnit").val(p.countUnit);
			$j("#specshow").val(p.specmode);
			$j("#spec").val(p.specmode);
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
								修改积分目标
							</td>
						</tr>
					</table>
					<form name="validateProvide" method="post" id="validateProvide"
						action="../keyretailer/updSBonusTargetAction.do"
						onSubmit="return ChkValue();">
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
									出货客户：
								</td>
								<td>
									<input name="id" type="hidden" id="id" value="${sbs.id}">
									<input name="fromOrganId" type="hidden" id="fromOrganId" value="${sbs.fromOrganId}">
									<input name="fromOrganModel" type="hidden" id="fromOrganModel" >
									<input name="fromorganname" type="text" id="fromorganname" size="30"  value="${sbs.fromOrganName}"
									readonly dataType="Require" msg="请填写出货客户"><a href="javascript:SelectOrgan1();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a><span class="style1">*</span>
								</td>
								<td align="right">
									进货机构：
								</td>
								<td>
									<input name="toOrganId" type="hidden" id="toOrganId" value="${sbs.toOrganId}">
									<input name="toOrganModel" type="hidden" id="toOrganModel" >
									<input name="toorganname" type="text" id="toorganname" size="30"  value="${sbs.toOrganName}"
									readonly dataType="Require" msg="请填写进货机构"><a href="javascript:SelectOrgan2();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a><span class="style1">*</span>
								</td>
								<td width="9%" align="right">
													年度：
												</td>
												<td width="23%">
													<input id="year" name="year"
														value="${sbs.year}"  dataType="Require" msg="请填写年度"><span class="style1">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													产品名称：
												</td>
												<td>
													<input id="productName" name="productName"
														value="${sbs.productName}" readonly dataType="Require" msg="请选择产品名称!">
													<input type="hidden" name="countUnit" id="countUnit"
														value="${sbs.countUnit}">
													<a href="javascript:SelectSingleProductName();"><img
															src="../images/CN/find.gif" align="absmiddle" border="0">
													</a><span class="style1">*</span>
												</td>
												<td align="right">
													规格：
												</td>
												<td>
													<input type="hidden" name="spec" id="spec"
														value="${sbs.spec}">
													<input type="text"  name="specshow" id="specshow"
														value="${sbs.spec}" readonly dataType="Require" msg="请选择产品规格!"><span class="style1">*</span>
												</td>
												<td align="right">目标数量：</td>
												<td><input id="targetAmount" name="targetAmount"
														value="${sbs.targetAmount}"  dataType="Require" msg="请填写目标数量"><span class="style1">*</span>
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
