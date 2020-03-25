<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
<head>
		<title>添加相关合同</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
	<script language="javascript">
	function Chk(){
		var pactcode = document.refer.pactcode;
		var signdate = document.refer.signdate;
		var cname = document.refer.cname;
		var cdeputy = document.refer.cdeputy;
		var pdeputy = document.refer.pdeputy;
		
		if(pactcode.value==null||pactcode.value==""|| pactcode.value.trim()==""){
			alert("合同编号不能为空!");
			return false;
		}
		if(cname.value==null||cname.value==""){
			alert("客户名称不能为空!请选择!");
			return false;
		}
		
	
		if(cdeputy.value==null||cdeputy.value==""|| cdeputy.value.trim()==""){
			alert("客户方代表不能为空!");
			return false;
		}
		if(pdeputy.value==null||pdeputy.value==""|| pdeputy.value.trim()==""){
			alert("供方代表不能为空!");
			return false;
		}
		
		if(signdate.value==null||signdate.value==""){
			alert("签订日期不能为空!");
			return false;
		}
		
		var pactscopy = document.refer.pactscopy;
		if(pactscopy.value.length>256){
				alert("合同范围不能超过256个字数！");
				pactscopy.select();
				return false;
			}
		showloading();
		return true;
	}
	
	function SelectName(){
		var objsort = document.refer.objsort;
		if(objsort.value ==1){
			SelectCustomer();
		}else{
			SelectOrgan();
		}
	}
	
	function SelectCustomer(){
		var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( c == undefined ){
			return;
		}
		document.refer.cid.value=c.cid;
		document.refer.cname.value=c.cname;
	}
	
	function SelectOrgan(){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if(o==undefined){return;}
		document.refer.cid.value=o.id;
		document.refer.cname.value=o.organname;
	} 
</script>

	
	</head>
	<html:errors />
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<table width="100%" height="40" border="0" cellpadding="0"
									cellspacing="0" class="title-back">
									<tr>
										<td width="10">
											<img src="../images/CN/spc.gif" width="10" height="8">
										</td>
										<td width="772">
											添加相关合同
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<form action="addPactAction.do" method="post"
									enctype="multipart/form-data" name="refer"
									onSubmit="return Chk();">
									<fieldset style="text-align:center ">
										<legend>
											<table width="50" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														基本资料
													</td>
												</tr>
											</table>
										</legend>
										<table width="100%" border="0" cellpadding="0" cellspacing="1">
											<tr>
												<td align="right" width="10%">
													客户类型：
												</td>
												<td colspan="6">
													<input type="hidden" id="objsort" name="objsort"
														value="${objsort }">
													<windrp:getname key="ObjSort" p="f" value="${objsort}" />
												</td>
											</tr>
											<tr>
												<td width="10%" align="right">
													合同编号：
												</td>
												<td width="20%">
													<input name="pactcode" type="text" id="pactcode">
													<span class="STYLE1">*</span>
												</td>
												<td width="10%" align="right">
													<input name="cid" type="hidden" id="cid">
													客户名称：
												</td>
												<td width="23%">
													<input name="cname" type="text" 
													id="cname" readonly="readonly"><a href="javascript:SelectName();"><img
															src="../images/CN/find.gif" width="18" height="18"
															 align="absmiddle" border="0"> </a>
															 <span class="STYLE1">*</span>
												</td>
												<td width="12%" align="right">
													客户方代表：
												</td>
												<td width="20%">
													<input name="cdeputy" type="text" id="cdeputy">
													<span class="STYLE1">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													合同类型：
												</td>
												<td>
													<windrp:select key="PactType" name="pacttype" p="n|d" />
												</td>
												<td align="right">
													供方：
												</td>
												<td>
													<input name="provide" type="text" id="provide">
												</td>
												<td align="right">
													供方代表：
												</td>
												<td>
													<input name="pdeputy" type="text" id="pdeputy">
													<span class="STYLE1">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													签订日期：
												</td>
												<td>
													<input type="text" name="signdate"
														onFocus="javascript:selectDate(this)" readonly>
														<span class="STYLE1">*</span>
												</td>
												<td align="right">
													签订地址：
												</td>
												<td colspan="3">
													<input name="signaddr" type="text" id="signaddr" size="60">
													
												</td>
											</tr>
											<tr>
												<td align="right" >
													合同范围：
												</td>
												<td colspan="5">
													<textarea name="pactscopy" style="width: 100%;" cols="120" rows="3"
														id="pactscopy"></textarea><br>
													<span class="td-blankout">(合同范围长度不能超过256字符)</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													附件：
												</td>
												<td colspan="3">
													<input name="affix" type="file" id="affix">
												</td>
												<td align="right">
													&nbsp;
												</td>
												<td>
													&nbsp;
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
													<input type="reset" name="cancel"
														onClick="javascript:window.close()" value="取消">
												</div>
											</td>
										</tr>

									</table>
								</form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>
