<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>新增码申请</title> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/validator.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script language=javascript>

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
	
function SupperSelect(){
	var p=showModalDialog("../common/selectSingleProductAction.do?type=hz",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");

	if(p==undefined){
		return;
	}else{
		$("#ProductName").val(p.productname);
    	$("#specMode").val(p.specmode);
		$("#ProductID").val(p.id);
		$("#packingRate").val(p.packingratio);
		$("#regCertCode").val(p.regcertcode);
		$("#specCode").val(p.speccode);
	}
}
	
	function checkSubmit(){
		if ( !Validator.Validate(document.validateProvide,2) ){
			return false;
			}	
		showloading();
	}
	
</script>

	</head>
	<body style="overflow: auto;">
		<form id="referForm" name="referForm" method="post"
			action="addApplyQrCodeAction.do" onSubmit="return checkSubmit();">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bordercolor="#BFC0C1">

				<tr>
					<td>
						<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									新增码申请 
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
									<td align="right">
										工厂：
									</td>
									<td>
										<input name="organid" type="hidden" id="organid"  value="10001217">
										<input name="orgname" type="text" id="orgname" size="30"
											dataType="Require" msg="<bean:message key='msg.selectplant'/>!"  value="拜耳作物科学杭州工厂 " readonly>
										<%-- <a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a> --%>
										<span class="style1">*</span>
									</td>
          						 <td  align="right">
          						 		产品：
          						 </td>
					            <td>
						            <input name="ProductID" type="hidden" id="ProductID">
						             <input id="ProductName" type="text"  name="ProductName" value="${ProductName}" readonly 
						             dataType="Require" msg="<bean:message key='msg.selectproduct'/>!" >
						            <a href="javascript:SupperSelect();">
						            <img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
						            <span class="style1">*</span>
					            </td>
								</tr>
								<tr>
								   <td align="right">
								   		产品规格：
								   </td>
				   					<td>
				   					 	 <input type="text" name="specMode" id="specMode" readonly="readonly"/>
				   					</td>
								   <td align="right">
								   		包装比例：
								   </td>
				   					<td>
				   					   <input type="text" name="packingRate" id="packingRate" readonly="readonly"/>
				   					   
				   					</td>				   					
								</tr>
								<tr>
								   <td align="right">
								   		生成小包装数：
								   </td>
				   					<td>
										<input type="text" name="quantity" id="quantity" 
				   					   maxlength="8" onkeyup="checknum(this)" onblur="checknum(this)"
				   					    dataType="Require" msg="<bean:message key='msg.inputcartoncount'/>!">
				   					    <span class="style1">*</span>
									</td>
									<td align="right">
										余量：
									</td>
									<td>
										<input type="text" name="redundancy" id="redundancy"  value="10"
				   					   maxlength="8" onkeyup="checknum(this)" onblur="checknum(this)"
				   					    dataType="Require" msg="<bean:message key='msg.inputredundancy'/>!">%
				   					    <span class="style1">*</span>
									</td>
				   				<td>
								</tr>
								<tr>
								   <td align="right">
								   		产品登记证号：
								   </td>
				   					<td>
										 <input type="text" name="regCertCode" id="regCertCode" dataType="Require" msg="<bean:message key='msg.mantianregistybumber'/>!" readonly="readonly"/>
									</td>
									<td align="right" id="codeSeqLabel">
										产品规格码：
									</td>
									<td>
										 <input type="text" name="specCode" id="specCode" dataType="Require" msg="<bean:message key='msg.mantianspecificationcode'/>!" readonly="readonly"/>	
									</td>
				   				<td>
								</tr>
								<tr>
								   <td align="right">
								   		PO编号：
								   </td>
				   					<td>
										 <input type="text" name="pono" id="pono" maxlength="32" dataType="Require" msg="<bean:message key='msg.inputprocessorder'/>!"/><span class="style1">*</span>
									</td> 
									<td align="right">
									</td>
									<td>
										<input name="needCovertCode" type="hidden" value="0"/>
									</td>
				   				<td>
								</tr>
							</table>
						</fieldset>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="center">
									<input type="submit" name="Submit" value="<bean:message key='system.submit'/>">
									&nbsp;&nbsp;
									<input type="button" name="button" value="<bean:message key='system.cancel'/>" onClick="window.close();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
