<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@page import="java.util.*,java.text.*"%>
<%@ include file="../common/tag.jsp"%>

<html>

	<script type="text/javascript" src="../js/pub_Calendar.js"></script>
	<%
        java.util.Date d = null;
        Calendar cd=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        d=cd.getTime();
%>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
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
		if(c==undefined){return;}
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
											添加销售机会
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<form name="referform" method="post" action="addHapAction.do"
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
												<td width="12%" align="right">
													对象类型：
												</td>
												<td width="15%">
													<input type="hidden" id="objsort" name="objsort"
														value="${objsort}">
													<windrp:getname key='ObjSort' value='${objsort}' p='f' />
												</td>
												<td width="17%" align="right">
													<input name="cid" type="hidden" id="cid">
													对象名称：
												</td>
												<td width="27%">
													<input name="cname" type="text" id="cname"
														readonly="readonly"><a href="javascript:SelectName();"><img
															src="../images/CN/find.gif" width="18" height="18"
															align="absmiddle" border="0"> </a>
															<span class="STYLE1">*</span>
												</td>
												<td width="10%" align="right">
													机会来源：
												</td>
												<td width="19%">
													<windrp:select key="HapSrc" name="hapsrc" p="n|d" />
												</td>
											</tr>

											<tr>
												<td align="right">
													机会主题：
												</td>
												<td colspan="3">
													<input name="haptitle" type="text" id="haptitle"
														size="80" maxlength="100">
														<span class="STYLE1">*</span>
												</td>

												<td align="right">
													机会种类：
												</td>
												<td>
													<windrp:select key="HapContent" name="hapcontent" p="n|d" />
												</td>
											</tr>
											<tr>
												<td align="right">
													机会性质：
												</td>
												<td>
													<windrp:select key="HapKind" name="hapkind" p="n|d" />
												</td>
												<td align="right">
													机会状态：
												</td>
												<td>
													<windrp:select key="HapStatus" name="hapstatus" p="n|d" />
												</td>
												<td align="right">
													预计金额：
												</td>
												<td>
													<input name="intend" size="11" type="text" id="intend"
														value="0" onChange="clearNoNum(this);" maxlength="12">
												</td>

											</tr>
											<tr>
												<td align="right">
													预计签约日期：
												</td>
												<td>
													<input name="intenddate" type="text" id="intenddate"
														size="10" onFocus="javascript:selectDate(this)"
														value="<%=sdf.format(d)%>" readonly="readonly">
												</td>
												<td align="right">
													下次约定日期：
												</td>
												<td>
													<input name="hapbegin" type="text" id="hapbegin" size="10"
														onFocus="javascript:selectDate(this)"
														value="<%=sdf.format(d)%>" readonly="readonly">
												</td>
												<td align="right">
													机会消失日期：
												</td>
												<td>
													<input name="hapend" type="text" id="hapend" size="10"
														onFocus="javascript:selectDate(this)"
														value="<%=sdf.format(d)%>" readonly="readonly">
												</td>
											</tr>
											<tr>
												<td align="right">
													备注：
												</td>
												<td colspan="5">
													<textarea name="memo" style="width: 100%;" rows="5"
														id="memo"></textarea><br>
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
