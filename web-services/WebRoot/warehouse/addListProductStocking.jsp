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
		<SCRIPT LANGUAGE="javascript" src="../js/showSQ.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language=javascript>

function Check(){
	var pid = document.all("che");
	var checkall = document.all("checkall");
	if (pid==undefined){return;}
	if (pid.length){
		for(i=0;i<pid.length;i++){
				pid[i].checked=checkall.checked;
		}
	}else{
		pid.checked=checkall.checked;
	}		
}	

	function ChkValue(){
        var productid = document.validateProvide.productid;
		var warehouseid = document.validateProvide.warehouseid;
		if(warehouseid.value==null||warehouseid.value==""){
			alert("仓库不能为空");
			return false;
		}
		if ( !Validator.Validate(document.validateProvide,2) ){
		return false;
		}
		showloading();
		return true;
	}

	function SelectOrgan(){
		var from = document.validateProvide;
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		from.organid.value=p.id;
		from.orgname.value=p.organname;
		from.owname.value=p.wname;
		from.warehouseid.value=p.wid;
			
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
								新增
							</td>
						</tr>
					</table>
					<form name="validateProvide" method="post"
						action="../warehouse/addAmountInventoryAction.do"
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
												<td width="10%" align="right">
													机构：
												</td>
												<td>
													<input name="organid" type="hidden" id="organid" value="${organid}">
													<input name="orgname" type="text" id="orgname" size="30" value="${orgname}"
														dataType="Require" msg="必须录入出货机构!" readonly>
													<a href="javascript:SelectOrgan();"><img
															src="../images/CN/find.gif" width="18" height="18"
															border="0" align="absmiddle">
													</a>
													<span class="style1">*</span>
												</td>
												<td width="10%" align="right">
													仓库：
												</td>
												<td width="24%">
													<input type="hidden" name="warehouseid" id="warehouseid" value="${warehouseid}">
													<input type="text" name="owname" id="owname"  dataType="Require" msg="仓库不能为空!"
														onClick="selectDUW(this,'warehouseid',$F('organid'),'rw','')"
														value="${owname}" readonly>
													<span class="STYLE1">*</span>
												</td>
											</tr>
										</table>
									</fieldset>
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td align="center">
												<input type="Submit" name="Submit" value="提交">
												&nbsp;&nbsp;
												<input type="button" name="Submit2" value="取消"
													onClick="window.close();">
											</td>
										</tr>
									</table>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
