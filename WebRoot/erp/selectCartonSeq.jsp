<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="../css/xtree.css" type="text/css">
		<script src="../js/prototype.js"></script>
		<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/jquery-1.4.2.min.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/xtree.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/xmlextras.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/xloadtree.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/treeselect.js"></SCRIPT>

		<style type="text/css">
<!--
.style1 { 
	color: #FF0000
}
-->
</style>
	</head>
	<script language="javascript"> 
function formChk(){
	if ( !Validator.Validate(document.validateProduct,2) ){
		return false;
	}
	showloading();
	return true;
 }
 
  function addRow(){ 
    var x = document.all("xq").insertRow(xq.rows.length);

	var a=x.insertCell(0);
	var b=x.insertCell(1);
	var c=x.insertCell(2);
	var d=x.insertCell(3);
	

	a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
	b.innerHTML='<input type=\"text\" value=\"\" onkeyup=\"checknum(this)\" name=\"startSeq\">';
	c.innerHTML='~';
	d.innerHTML="<input type=\"text\" value=\"\" onkeyup=\"checknum(this)\" name=\"endSeq\">"; 
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

	<body style="overflow: auto">
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
								关联箱码序号
							</td>
						</tr>
					</table>
					<form action="../erp/selectCartonSeqAction.do" method="post"
						name="validateProduct" onSubmit="return formChk();">
						<input type="hidden" name="range" id="range" value="${range}">
						<input type="hidden" name="planId" id="planId" value="${planId}">
						<fieldset align="center">
							<legend>
								<table width="25" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											箱码序号
										</td>
									</tr>
								</table>
							</legend>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr>
									<td align="right">
										可用箱码序号范围：
									</td>
									<td align="left">
										${range}
									</td>
								</tr>
								<tr>
									<td align="right">
										需关联的箱码数量：
									</td>
									<td align="left">
										${qty}
									</td>
								</tr>
								<tr>
									<td align="right" valign="top">
										箱码序号：
									</td>
									<td colspan="5">
										<a href="javascript:addRow();"><img
												src="../images/nolines_plus.gif" width="16" height="18"
												border="0" title="新增">
										</a>
										<a href="javascript:deleteR();"><img
												src="../images/nolines_minus.gif" width="16" height="18"
												border="0" title="删除">
										</a>

										<table width="271" border="0" id="xq" cellpadding="0"
											cellspacing="0">
											<tr>
												<td width="71">
													<input type="checkbox" name="checkall" value="on"
														onClick="Check();">
												</td>
												<td>
													起始序号
												</td>
												<td></td>
												<td>
													结束序号
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</fieldset>
						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td>
									<div align="center">
										<input type="submit" name="Submit" value="确定">
										&nbsp;&nbsp;
										<input type="button" name="cancel" value="取消"
											onClick="window.close()">
									</div>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
