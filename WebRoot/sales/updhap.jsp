<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>

	<head>
		<title>修改零售机会</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<script language="javascript">
		function ChkValue(){
			var memo = document.referform.memo;
	
			var cname = document.referform.cname;
			
			var haptitle = document.referform.haptitle;
			var intend = document.referform.intend；
			if(cname.value==""){
				alert("请选择客户！");
				return false;
			}
			if(haptitle.value==""||haptitle.value.trim()==""){
				alert("请输入机会主题！");
				return false;
			}
			if(memo.value.length>256){
				alert("备注不能超过256个字数！");
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

	<body>
		<table width="100%" border="1" cellpadding="0" cellspacing="1"
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
											修改销售机会
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<form name="referform" method="post" action="updHapAction.do"
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
												<td width="11%" align="right">
													对象类型：
												</td>
												<td width="22%">
													<input type="hidden" id="objsort" name="objsort" value="${hf.objsort}">
													<windrp:getname key='ObjSort' value='${hf.objsort}' p='f'/>
												</td>
												<td width="13%" align="right">
													<input name="id" type="hidden" id="id" value="${hf.id}">
													<input name="cid" type="hidden" id="cid" value="${hf.cid}" readonly="readonly">
													对象名称：
												</td>
												<td width="20%">
													<input name="cname" type="text" id="cname"
														value="${hf.cname}" readonly><a href="javascript:SelectName();"><img
															src="../images/CN/find.gif" width="18" height="18"
															align="absmiddle" border="0">
													</a>
													<span class="STYLE1">*</span>
												</td>
												<td width="12%" align="right">
													机会来源：
												</td>
												<td width="22%">
													<windrp:select key="HapSrc" name="hapsrc" p="n|d"/>
												</td>
											</tr>
											<tr>
												<td align="right">
													机会主题：
												</td>
												<td colspan="3">
													<input name="haptitle" type="text" id="haptitle" value="${hf.haptitle}" size="80" maxlength="100">
													<span class="STYLE1">*</span>
												</td>
												
												<td align="right">
													机会种类：
												</td>
												<td>
													<windrp:select key="HapContent" name="hapcontent" p="n|d" value="${hf.hapcontent}"/>
												</td>
											</tr>
											<tr>
												<td align="right">
													机会性质：
												</td>
												<td>
													<windrp:select key="HapKind" name="hapkind" p="n|d" value="${hf.hapkind}"/>
												</td>
												<td align="right">
													机会状态：
												</td>
												<td>
													<windrp:select key="HapStatus" name="hapstatus" p="n|d" value="${hf.hapstatus}"/>
												</td>
												<td align="right">
													预计金额：
												</td>
												<td>
													<input name="intend" type="text" id="intend" size="11" onKeyPress="KeyPress(this)" value="<windrp:format value='${hf.intend}'/>" maxlength="10">
												</td>
											</tr>
											<tr>
											<td align="right">
													下次签约日期：
												</td>
												<td>
													<input name="intenddate" type="text" id="intenddate" size="10"
														onFocus="javascript:selectDate(this)"
														value="<windrp:dateformat value="${hf.intenddate}" p="yyyy-MM-dd"/>" readonly="readonly">
												</td>
												
												<td align="right">
													下次约定日期：
												</td>
												<td>
													<input name="hapbegin" type="text" id="hapbegin" size="10"
														onFocus="javascript:selectDate(this)"
														value="<windrp:dateformat value="${hf.hapbegin}" p="yyyy-MM-dd"/>" readonly="readonly">
												</td>
												<td align="right">
													机会消失日期：
												</td>
												<td>
													<input name="hapend" type="text" id="hapend" size="10"
														onFocus="javascript:selectDate(this)" value="<windrp:dateformat value="${hf.hapend}" p="yyyy-MM-dd"/>" readonly="readonly">
												</td>
											</tr>
											<tr>
												<td align="right">
													备注：
												</td>
												<td colspan="5">
													<textarea name="memo" style="width: 100%;" rows="5" id="memo">${hf.memo}</textarea><br>
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
