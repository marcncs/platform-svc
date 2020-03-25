<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@page import="java.util.*,java.text.*"%>
<%@include file="../common/tag.jsp"%>

<html>

	<script type="text/javascript" src="../js/pub_Calendar.js"></script>
	<%
        java.util.Date d = null;
        Calendar cd=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        d=cd.getTime();
%>
	<head>
		<title>添加商务联系</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
        <SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<script language="javascript">
	function ChkValue(){
		var contactdate = document.validateContactLog.contactdate;
		var nextcontact = document.validateContactLog.nextcontact;
		var cid = document.validateContactLog.cid;
		var feedback = document.validateContactLog.feedback;
		var contactcontent = document.validateContactLog.contactcontent;
		var linkman = document.validateContactLog.linkman;
		if(cid.value==""){
			alert("请选择客户!");
			return false;
		}
		if(linkman.value=="" || linkman.value.trim()==""){
			alert("联系人不能为空!");
			return false;
		}
		if(contactdate.value==""){
			alert("联系日期不能为空！");
		return false;
		}
		if(nextcontact.value==""){
			alert("下次联系日期不能为空！");
			return false;
		}
		if(contactcontent.value=="" || contactcontent.value.trim()==""){
			alert("联系内容不能为空!");
			return false;
		}
		if(contactcontent.value.length>512){
			alert("联系内容不能超过512个字符！");
			contactcontent.select();
			return false;
		}
		if(feedback.value.length>512){
			alert("客户反馈不能超过512个字符！");
			feedback.select();
			return false;
		}
		showloading();
		return true;
	}
	
	function SelectContact(){
		document.validateContactLog.contactcontent.value=document.validateContactLog.select.value;
	}
	
	function SelectName(){
		var objsort = document.validateContactLog.objsort;
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
		document.validateContactLog.cid.value=c.cid;
		document.validateContactLog.cname.value=c.cname;
		getLinkman(c.cid);
	}

	function SelectLinkman(){
		var cid=document.validateContactLog.cid.value;
		if(cid==null||cid=="")
		{
			alert("请选择客户！");
			return;
		}
		var objsort = document.validateContactLog.objsort;
		if(objsort.value ==0){
			var lk=showModalDialog("../common/selectOrganLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			document.validateContactLog.linkman.value=lk.lname;
		}else{
			var lk=showModalDialog("../common/selectMemberLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			document.validateContactLog.linkman.value=lk.lname;
		}
		
	}
	
	function SelectOrgan(){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if(o==undefined){return;}
		document.validateContactLog.cid.value=o.id;
		document.validateContactLog.cname.value=o.organname;
		getLinkman(o.id);
	} 
	
	function getLinkman(v_cid){
		var url ="";
		var objsort = document.validateContactLog.objsort;
		if(objsort.value ==0){
			url = "../sales/ajaxOrganLinkmanAction.do?cid="+v_cid;
		}else{
			url = "../sales/ajaxCustomerLinkmanAction.do?cid="+v_cid;
		}		
		var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: '', onComplete: showLman}
				);	
	}
	function showLman(originalRequest){
		var data = eval('(' + originalRequest.responseText + ')');
		var lk = data.linkman;
		if ( lk != undefined ){			
			document.validateContactLog.linkman.value=lk.name;
		}
	}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<html:errors />
	<body style="overflow: auto;">

					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<table width="100%" height="40" border="0" cellpadding="0"
									cellspacing="0" class="title-back">
									<tr>
										<td width="10">
											<img src="../images/CN/spc.gif" width="10" height="8">
										</td>
										<td >
											添加商务联系
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<form name="validateContactLog" method="post"
									action="addContactAction.do" onSubmit="return ChkValue();">
									<fieldset align="center">
										<legend>
											<table border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														基本信息
													</td>
												</tr>
											</table>
										</legend>
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td align="right" width="15%">
													对象类型：
												</td>
												<td width="10%">
													<input type="hidden" id="objsort" name="objsort"
														value="${objsort}">
													<windrp:getname key='ObjSort' value='${objsort}' p='f' />
												</td >
												<td align="right" width="15%">
													<input name="cid" type="hidden" id="cid">
													对象名称：
												</td>
												<td width="23%">
													<input name="cname" type="text" id="cname"
														readonly="readonly"><a href="javascript:SelectName();"><img
															src="../images/CN/find.gif" width="18" height="18"
															align="absmiddle" border="0"> </a>
															<span class="STYLE1">*</span>
												</td>
												<td align="right" width="10%">
													联系人：
												</td>
												<td>
													<input name="linkman" type="text" id="linkman"><a href="javascript:SelectLinkman();"><img
															src="../images/CN/find.gif" width="19" height="18"
															align="absmiddle" border="0"> </a>
															<span class="STYLE1">*</span>
												</td>
												
											</tr>
											<tr>
											<td align="right" width="">
													联系方式：
												</td>
												<td>
													<windrp:select key="ContactMode" name="contactmode" p="n|f"
														value="1" />
												</td>
												<td align="right">
													联系性质：
												</td>
												<td>
													<windrp:select key="ContactProperty" name="contactproperty"
														p="n|f" value="1" />
												</td>
												<td align="right">
													联系日期：
												</td>
												<td >
													<input name="contactdate" type="text"
														value="<%=sdf.format(d)%>" size="10"
														onFocus="javascript:selectDate(this)" readonly="readonly">

												</td>
											</tr>
											<tr>
												<td align="right">
													联系内容：
												</td>
												<td colspan="5">
													<select name="select" onChange="SelectContact(this)">
														<option value="">
															选择
														</option>
														<logic:iterate id="c" name="als">
															<option value="${c.tagsubvalue}">
																${c.tagsubvalue}
															</option>
														</logic:iterate>
													</select>
													<br>
													<textarea name="contactcontent" style="width: 100%;"
														cols="60" rows="5"></textarea><br>
														<span class="td-blankout">(联系内容长度不能超过512字符)</span>
												</td>
											</tr>
											<tr>
												<td align="right" >
													客户反馈：
												</td>
												<td colspan="5">
													<textarea name="feedback" cols="60" style="width: 100%;"
														rows="6" id="feedback"></textarea><br>
														<span class="td-blankout">(客户反馈长度不能超过512字符)</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													下次联系时间：
												</td>
												<td colspan="5">
													<input name="nextcontact" type="text" id="nextcontact"
														onFocus="javascript:selectDate(this)" size="12"
														value="<%=sdf.format(d)%>" readonly="readonly">
												</td>
											</tr>
											<tr>
												<td align="right">
													下次联系目标：
												</td>
												<td colspan="5">
													<input name="nextgoal" type="text" id="nextgoal" size="60">
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
			

	</body>
</html>
