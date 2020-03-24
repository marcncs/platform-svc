<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>


<html>

	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<script language="javascript">
	function ChkValue(){
		var ldate = document.referform.ldate;
		var lfee=document.referform.lfee;
		var cname = document.referform.cname;
		var largessname = document.referform.largessname;
		var memo = document.referform.memo;
		var largessdescribe = document.referform.largessdescribe;
		if(cname.value.trim()==""){
		alert("客户不能为空！");
		return false;
		}
		if(largessname.value.trim()==""){
			alert("赠品名称不能为空！");
			return false;
		}
		if(ldate.value.trim()==""){
			alert("赠送日期不能为空！");
			return false;
		}
		if(lfee.value.trim()==""){
			alert("赠送费用不能为空！");
			return false;
		}
		if(largessdescribe.value.length>256){
			alert("赠品描述不能超过256个字符！");
			largessdescribe.select();
			return false;
		
		}
		if(memo.value.length>500){
			alert("备注不能超过500个字符！");
			memo.select();
			return false;
		
		}
		showloading();
		return true;
	}
	
	function SelectName(){
		var objsort = document.referform.objsort;
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
		document.referform.cid.value=c.cid;
		document.referform.cname.value=c.cname;
	}

	function SelectOrgan(){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if(o==undefined){return;}
		document.referform.cid.value=o.id;
		document.referform.cname.value=o.organname;
	} 
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body style="overflow-y: auto;">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0"
									class="title-back">
									<tr>
										<td width="10">
											<img src="../images/CN/spc.gif" width="10" height="8">
										</td>
										<td>
											修改赠品
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<form name="referform" method="post"
									action="updLargessAction.do" onSubmit="return ChkValue();">
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
												<td style="text-align: right;">
													客户类型：
												</td>
												<td>
													<input type="hidden" id="objsort" name="objsort" value="${lf.objsort}">
													<windrp:getname key="ObjSort" p="f" value="${lf.objsort}"/>
												</td>
												<td width="10%" height="20" align="right">
													<input name="id" type="hidden" id="id" value="${lf.id}">
													<input name="cid" type="hidden" id="cid" value="${lf.cid}">
													客户名称：
												</td>
												<td colspan="3">
													<input name="cname" type="text" id="cname"
														value="${lf.cname}" readonly><a href="javascript:SelectName();"><img
															src="../images/CN/find.gif" width="18" height="18"
															border="0" align="absmiddle">
													</a>
													<span class="STYLE1">*</span>
												</td>
											</tr>
											<tr>
												
												<td style="text-align: right;">赠品名称：</td>
												<td>
													<input type="text" id="largessname" name="largessname" value="${lf.largessname }">
													<span class="STYLE1">*</span>
												</td>
												<td width="10%" align="right">
													赠品费用：
												</td>
												<td width="20%">
													<input name="lfee" type="text" id="lfee" value="<windrp:format value='${lf.lfee}'/>" onKeyPress="KeyPress(this)" maxlength="8">
													<span class="STYLE1">*</span>
												</td>
												<td width="11%" align="right">
													赠送时间：
												</td>
												<td width="25%">
													<input name="ldate" type="text" id="ldate"
														onFocus="javascript:selectDate(this)"  size="10"
														value="<windrp:dateformat value="${lf.ldate}" p="yyyy-MM-dd"/>" readonly="readonly">
														<span class="STYLE1">*</span>
														
												</td>
											</tr>
											<tr>
												<td height="20" align="right">
													赠品描述：
												</td>
												<td colspan="5">
													<textarea name="largessdescribe" cols="100" style="width: 100%;" rows="3"
														id="largessdescribe">${lf.largessdescribe}</textarea><br>
							<span class="td-blankout">(赠品描述长度不能超过256字符)</span>
												</td>
											</tr>
											<tr>
												<td height="20" align="right">
													备注：
												</td>
												<td colspan="5">
													<textarea name="memo" style="width: 100%;" cols="100" rows="5" id="memo">${lf.memo}</textarea><br>
							<span class="td-blankout">(备注长度不能超过256字符)</span>
												</td>
											</tr>
										</table>
									</fieldset>

									<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
