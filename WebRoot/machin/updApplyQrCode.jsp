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
	var organid = $('#organid').val();
		if(organid == undefined || organid.trim() ==""){
			alert("<bean:message key='msg.selectplant'/>");
			return ;
		}
		
	var p=showModalDialog("../common/selectTollerProductAction.do?unit=1&oid="+organid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");

	if(p==undefined){
		return;
	}else{
		$("#ProductName").val(p.productname);
		$("#ProductID").val(p.id);
		$("#specMode").val(p.specmode);
		$("#packingRatio").val(p.packingratio);
		$("#regCertCode").val(p.regcertcode);
		$("#specCode").val(p.speccode);
		
		
	}
}
  function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=toller&i18nFlag=true",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		var oldOrgan = document.referForm.organid.value;
			document.referForm.organid.value=p.id;
			document.referForm.orgname.value=p.organname;
	}
	
	function checkSubmit(){
		if ( !Validator.Validate(document.validateProvide,2) ){
			return false;
			}	
		if($("#needCovertCode").val()=="") {
			alert("<bean:message key='msg.tolling.applycode.covertcode'/>");
			return false;
		}
		showloading();
	}
	
</script>

	</head>
	<body style="overflow: auto;">
		<form id="referForm" name="referForm" method="post"
			action="updApplyQrCodeAction.do" onSubmit="return checkSubmit();">
			<input name="ID" type="hidden" id="ID"  value="${aqc.id}">
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
									<bean:message key='tolling.applycode.update.title'/>
								</td>
							</tr>
						</table>

						<fieldset align="center">
							<legend>
								<table width="50" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<bean:message key='system.baseinfo'/>
										</td>
									</tr>
								</table>
							</legend>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr>

									<td align="right">
										<bean:message key='tolling.applycode.plant'/>：
									</td>
									<td>
										<input name="organid" type="hidden" id="organid"  value="${aqc.organid}">
										<input name="orgname" type="text" id="orgname" size="30"
											dataType="Require" msg="<bean:message key='msg.selectplant'/>!"  value="${aqc.organname}" readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
										<span class="style1">*</span>
									</td>
          						 <td  align="right">
          						 		<bean:message key='product'/>：
          						 </td>
					            <td>
						            <input name="ProductID" type="hidden" id="ProductID" value="${aqc.productid}">
						             <input id="ProductName" type="text"  name="ProductName" value="${aqc.productname}" readonly 
						             dataType="Require" msg="<bean:message key='msg.selectproduct'/>!" >
						            <a href="javascript:SupperSelect();">
						            <img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
						            <span class="style1">*</span>
					            </td>
								</tr>
								<tr>
								   <td align="right">
								   		<bean:message key='product.specification'/>：
								   </td>
				   					<td>
				   					 	 <input type="text" name="specMode" id="specMode" value="${aqc.specmode}" readonly="readonly"/>
				   					</td>
								   <td align="right">
								   		<bean:message key='product.packingratio'/>：
								   </td>
				   					<td>
				   					   <input type="text" name="packingRatio" id="packingRatio" value="${aqc.packingratio}" readonly="readonly"/>
				   					   
				   					</td>				   					
								</tr>
								<tr>
								   <td align="right">
								   		<bean:message key='tolling.applycode.cartoncount'/>：
								   </td>
				   					<td>
										<input type="text" name="quantity" id="quantity" value="${aqc.quantity}"
				   					   maxlength="8" onkeyup="checknum(this)" onblur="checknum(this)"
				   					    dataType="Require" msg="<bean:message key='msg.inputcartoncount'/>!">
				   					    <span class="style1">*</span>
									</td>
									<td align="right">
										<bean:message key='tolling.applycode.add.redundancy'/>：
									</td>
									<td>
										<input type="text" name="redundancy" id="redundancy"  value="${aqc.redundance}"
				   					   maxlength="8" onkeyup="checknum(this)" onblur="checknum(this)"
				   					    dataType="Require" msg="<bean:message key='msg.inputredundancy'/>!">%
				   					    <span class="style1">*</span>
									</td>
				   				<td>
								</tr>
								<tr>
								   <td align="right">
								   		<bean:message key='product.registrynumber'/>：
								   </td>
				   					<td>
										 <input type="text" name="regCertCode" id="regCertCode" value="${aqc.regcertcode}" dataType="Require" msg="<bean:message key='msg.mantianregistybumber'/>!" readonly="readonly"/>
									</td>
									<td align="right" id="codeSeqLabel">
										<bean:message key='product.specificationcode'/>：
									</td>
									<td>
										 <input type="text" name="specCode" id="specCode" value="${aqc.speccode}" dataType="Require" msg="<bean:message key='msg.mantianspecificationcode'/>!" readonly="readonly"/>	
									</td>
				   				<td>
								</tr>
								<tr>
								   <td align="right">
								   		<bean:message key='tolling.applycode.po'/>：
								   </td>
				   					<td>
										 <input type="text" name="pono" id="pono" value="${aqc.pono}" maxlength="32" dataType="Require" msg="<bean:message key='msg.inputprocessorder'/>!"/><span class="style1">*</span>
									</td>
									<td align="right">
										<bean:message key='tolling.applycode.covertcode'/>：
									</td>
									<td>
										<windrp:select key="YesOrNo" name="needCovertCode" value="${aqc.needcovertcode}" p="n|f" i18n="true"/>
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
