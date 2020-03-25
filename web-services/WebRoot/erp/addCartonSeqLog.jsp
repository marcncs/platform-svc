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
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
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
		
	var p=showModalDialog("../common/selectTollerProductAction.do?type=1&oid="+organid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");

	if(p==undefined){
		return;
	}else{
		<c:choose>
    	<c:when test="${'en' eq localeLanguage}">
    	$("#ProductName").val(p.productnameen);
    	</c:when>
    	<c:otherwise>
    	$("#ProductName").val(p.productname);
    	</c:otherwise>
    	</c:choose>
		$("#ProductID").val(p.id);
		$("#packingRate").val(p.packingratio);
		getAvailableRange(p.id);
	}
}

function getAvailableRange(productId){
    $.ajax({  
    	type : "POST",
		url : "../erp/ajaxGetAvailableRange.do",
		data : "productId="+productId,
		dataType: "json",
		async: false,
        success: function (data) {  
			$("#rangeTd").html(data.range);
			$("#range").val(data.range);
			$("#seqsTd").html(data.seqs);
			
        },  
        error: function (data) {  
        	
        }  
    });
}
  function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=toller&i18nFlag=true",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		var oldOrgan = document.referForm.organid.value;
			document.referForm.organid.value=p.id;
			document.referForm.orgname.value=p.organname;
			document.referForm.inspectionInstitution.value=p.organname;
	}
	
	function checkSubmit(){
		if ( !Validator.Validate(document.validateProvide,2) ){
			return false;
			}	
		showloading();
	}


	  function addRow(){ 
		var ss = "<bean:message key='msg.cartonseqactivate.startseq'/>!";
		var es = "<bean:message key='msg.cartonseqactivate.endseq'/>!";
	    var x = document.all("xq").insertRow(xq.rows.length);

		var a=x.insertCell(0);
		var b=x.insertCell(1);
		var c=x.insertCell(2);
		var d=x.insertCell(3);
		var e=x.insertCell(4);
		var f=x.insertCell(5);
		var g=x.insertCell(6);
		var h=x.insertCell(7);
		

		a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
		b.innerHTML='<input type=\"text\" value=\"\" onkeyup=\"checknum(this)\" name=\"startSeq\" dataType=\"Require\" msg=\"'+ss+'\">';
		c.innerHTML='~';
		d.innerHTML="<input type=\"text\" value=\"\" onkeyup=\"checknum(this)\" name=\"endSeq\"  dataType=\"Require\" msg=\""+es+"\" ><span class='style1'>*</span>"; 
		e.innerHTML="&nbsp;";
		f.innerHTML='<input type=\"text\" value=\"\" onkeyup=\"checknum(this)\" name=\"startPSeq\" dataType=\"Require\" msg=\"'+ss+'\">';
		g.innerHTML='~';
		h.innerHTML="<input type=\"text\" value=\"\" onkeyup=\"checknum(this)\" name=\"endPSeq\"  dataType=\"Require\" msg=\""+es+"\" ><span class='style1'>*</span>"; 
	
	}

	function deleteR(){
		chebox=document.all("che");
		if(chebox!=null){	
			if (chebox.length >=1){
				for(var i=1;i<=chebox.length;i++){
				  if (chebox[i-1].checked) {
					document.getElementById('xq').deleteRow ( i);
					i=i-1;
				  }
				}
			}else{
				if (chebox.checked){
				 document.all('xq').deleteRow(1);
				}
			}
	 	 }
	}

	function Check(){
			if(document.validateProduct.checkall.checked){
				checkAll();
			}else{
				uncheckAll();
			}
		}
		
	function checkAll(){
		var che=document.all("che");
		if(che!=null){	
			if (che.length){
				for(j=0; j<che.length; j++){
					che[j].checked=true;
				}
			}else{
				che.checked=true;
			}
		 }
	}

	function uncheckAll(){
		var che=document.all("che");
		if(che!=null){	
			if (che.length){
				for(j=0; j<che.length; j++){
					che[j].checked=false;
				}
			}else{
				che.checked=false;
			}
		 }
	}

	function checknum(obj){ 
		obj.value = obj.value.replace(/[^\d]/g,"");
	}
</script>

	</head>
	<body style="overflow: auto;">
		<form id="referForm" name="referForm" method="post"
			action="addCartonSeqLogAction.do" onSubmit="return checkSubmit();">
			<input type="hidden" id="range" name="range" value="${range}"> 
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
									<bean:message key='tolling.cartonseqactivate.add.title'/>
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
										<input name="organid" type="hidden" id="organid"  value="${organid}">
										<input name="orgname" type="text" id="orgname" size="30"
											dataType="Require" msg="<bean:message key='msg.selectplant'/>!"  value="${oname}" readonly>
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
										<bean:message key='tolling.cartonseqactivate.batch'/>：
									</td>
									<td>
										<input name="batch" type="text" id="batch" maxlength="32"  value="${batch}" dataType="Require" msg="<bean:message key='msg.cartonseqactivate.batch'/>!"><span class="style1">*</span>
									</td>
									<td align="right">
								   		<bean:message key='product.packingratio'/>：
								   </td>
				   					<td>
				   					   <input type="text" name="packingRate" id="packingRate" readonly="readonly"/>
				   					   
				   					</td>		
								</tr>
								
								<tr>
									<td align="right">
										<bean:message key='tolling.cartonseqactivate.producedate'/>：
									</td>
									<td>
										<input name="productionDate" type="text" id="productionDate" size="10" value="${productionDate}"
											onFocus="javascript:selectDate(this)" readonly>
									</td>
									<td align="right">
								   		<bean:message key='tolling.cartonseqactivate.packingdate'/>：
								   </td>
				   					<td>
				   					   <input name="packingDate" type="text" id="packingDate" size="10" value="${packingDate}"
											onFocus="javascript:selectDate(this)" readonly>
				   					</td>		
								</tr>
								<tr>
									<td align="right">
										<bean:message key='tolling.cartonseqactivate.inspectiondate'/>：
									</td>
									<td>
										<input name="inspectionDate" type="text" id="inspectionDate" size="10" value="${inspectionDate}"
											onFocus="javascript:selectDate(this)" dataType="Require" msg="<bean:message key='msg.cartonseqactivate.inspectiondate'/>!"  readonly>
										<span class="style1">*</span>
									</td>
									<td align="right">
								   		<bean:message key='tolling.cartonseqactivate.inspectionInstitution'/>：
								   </td>
				   					<td>
				   					   <input type="text" name="inspectionInstitution" id="inspectionInstitution" value="${oname}" dataType="Require" msg="<bean:message key='msg.cartonseqactivate.inspectionInstitution'/>!"><span class="style1">*</span>
				   					</td>		
								</tr>
							</table>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr>
									<td align="right">
										<bean:message key='tolling.cartonseqactivate.availablecartonseq'/>：
									</td>
									<td id="rangeTd" align="left">
										${range}
									</td>
								</tr>
								<tr>
									<td align="right">
										<bean:message key='tolling.cartonseqactivate.notfullyactivatedseq'/>：
									</td>
									<td id="seqsTd" align="left">
										${seqs}
									</td>
								</tr>
								<tr>
									<td align="right" valign="top">
										<bean:message key='tolling.cartonseqactivate.cartonseq'/>：
									</td>
									<td>
										<a href="javascript:addRow();"><img
												src="../images/nolines_plus.gif" width="16" height="18"
												border="0" title="新增">
										</a>
										<a href="javascript:deleteR();"><img
												src="../images/nolines_minus.gif" width="16" height="18"
												border="0" title="删除">
										</a>

										<table width="550" border="0" id="xq" cellpadding="0"
											cellspacing="0">
											<tr>
												<td width="71">
													<input type="checkbox" name="checkall" value="on"
														onClick="Check();">
												</td>
												<td>
													<bean:message key='tolling.cartonseqactivate.startseq'/>
												</td>
												<td></td>
												<td>
													<bean:message key='tolling.cartonseqactivate.endseq'/>
												</td>
												<td></td>
												<td>
													<bean:message key='tolling.cartonseqactivate.startpseq'/>
												</td>
												<td></td>
												<td>
													<bean:message key='tolling.cartonseqactivate.endpseq'/>
												</td>
											</tr>
										</table>
									</td>
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
