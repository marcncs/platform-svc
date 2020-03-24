<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>

<html>

	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<script language="javascript">
	function ChkValue(){
		var cid = document.referform.cid;
		var suitstatus = document.referform.suitstatus;
		var suitdate = document.referform.suitdate;
		var memo = document.referform.memo;
		var tel = document.referform.suittools;
		if(cid.value.trim()==""){
			alert("对象名称不能为空!");
			return false;
		}
		if(suitdate.value.trim()==""){
			alert("投诉时间不能为空!");
			return false;
		}
		
		if(tel.value.trim()!="" && !tel.value.trim().isMobileOrTel()){
			alert("请填写正确的联系方式!");
			tel.select();
			return false;
		}
		if(suitstatus.value.length > 256){
			alert("投诉内容请不要超过256!");
			suitstatus.select();
			return false;
		}
		
		if(memo.value.length > 256){
			alert("备注请不要超过256!");
			memo.select();
			return false;
		}
		

	
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
											修改抱怨投诉
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<form name="referform" method="post" action="updSuitAction.do"
									onSubmit="return ChkValue();">
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
													客户类型：
												</td>
												<td>
													<input name="objsort" type="hidden" id="objsort"
														value="${pf.objsort }">
													<windrp:getname key="ObjSort" p="f" value="${pf.objsort}" />
												</td>
												<td align="right">
													
													对象名称：
												</td>
												<td>
													<input name="id" type="hidden" id="id" value="${pf.id}">
													<input name="cid" type="hidden" id="cid" value="${pf.cid}">
													<input name="cname" type="text" id="cname"
														value="${pf.cidname}" readonly><a href="javascript:SelectName();"><img src="../images/CN/find.gif" width="18" height="18" align="absmiddle" border="0"></a><span class="STYLE1">*</span>
													
												</td>
												<td align="right">&nbsp;
													
												</td>
												<td>&nbsp;
													
												</td>
											</tr>
											<tr>
												<td width="11%" align="right">
													投诉种类：
												</td>
												<td width="24%">
													<windrp:select key="SuitContent" name="suitcontent"
														value="${pf.suitcontent}" p="n|d" />
												</td>
												<td width="11%" align="right">
													投诉方式：
												</td>
												<td width="20%">
													<windrp:select key="SuitWay" name="suitway"
														value="${pf.suitway}" p="n|d" />
												</td>
												<td width="11%" align="right">
													投诉时间：
												</td>
												<td width="23%">
													<input name="suitdate" type="text" id="suitdate" size="10"
														onFocus="javascript:selectDate(this)"
														value="${pf.suitdate}" readonly>
														<span class="STYLE1">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													联系方式：
												</td>
												<td colspan="5">
													 <input type="text" id="suittools" name="suittools" value="${pf.suittools}" maxlength="128" style="width: 230px;"> 
												</td>
											</tr>
											<tr>
												<td align="right">
													投诉内容：
												</td>
												<td colspan="5">
													<textarea name="suitstatus" cols="100"  style="width: 100%;" rows="5"
														id="suitstatus">${pf.suitstatus}</textarea><br>
													<span class="td-blankout">(投诉内容长度不能超过256字符)</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													备注：
												</td>
												<td colspan="5">
													<textarea name="memo" cols="100"  style="width: 100%;" rows="5" id="memo">${pf.memo}</textarea><br>
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
