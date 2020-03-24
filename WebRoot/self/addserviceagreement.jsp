<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@page import="java.util.*,java.text.*"%>
<%@page import="com.winsafe.hbm.util.DateUtil"%>
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
		<title>添加服务预约</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="../js/Cookie.js"> </SCRIPT>
		<script type="text/javascript">
		
		function ChkValue(){
			var cname = document.referform.cname;
			var linkman = document.referform.linkman;
			var tel = document.referform.tel;
			var question = document.referform.question;
			if(cname.value==""|| cname.value.trim()==""){
					alert("请选择客户!");
					return false;
			}
			if(linkman.value==""|| linkman.value.trim()==""){
					alert("请选择联系人!");
					return false;
			}
			if(tel.value==""|| tel.value.trim()==""){
				alert("请填写联系电话!");
				tel.focus();
				return false;
			}
			if(!tel.value.isMobileOrTel()){
				alert("请填写正确电话号码");
				tel.select();
				return false;
			}	
			if( question.value.trim()!="" && question.value.length > 256){
				alert("问题描述不能超过256个字符!");
				question.select();
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
		
		
		function getCustomerLinkmanBycid(objcid){
	        var url = '../sales/ajaxCustomerLinkmanAction.do?cid='+objcid;
	        var pars = '';
	       var myAjax = new Ajax.Request(
	                    url,
	                    {method: 'get', parameters: pars, onComplete: showLinkman}
	                    );	
	    }
    
	    function getOrganLinkmanBycid(objcid){
	        var url = '../sales/ajaxOrganLinkmanAction.do?cid='+objcid;
	        var pars = '';
	        
	       var myAjax = new Ajax.Request(
	                    url,
	                    {method: 'get', parameters: pars, onComplete: showLinkman}
	                    );	
	    }
	    
	    function showLinkman(originalRequest)
	    {
			var data = eval('(' + originalRequest.responseText + ')');
			var lk = data.linkman;
			if ( lk == undefined ){
				document.referform.linkman.value='';
			}else{
				document.referform.linkman.value=lk.name;
				document.referform.tel.value=lk.mobile;
				if(document.referform.tel.value == ""){
					document.referform.tel.value=lk.officetel;
				}
			}
		}
		
		function SelectCustomer(){
			var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			document.referform.cid.value=c.cid;
			document.referform.cname.value=c.cname;
			getCustomerLinkmanBycid(c.cid);
			
		}
		function SelectOrgan(){
			var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if(o==undefined){return;}
			document.referform.cid.value=o.id;
			document.referform.cname.value=o.organname;
			getOrganLinkmanBycid(o.id);
		} 
	
		function SelectLinkman(){
			var cid=document.referform.cid.value;
			if(cid==null||cid=="")
			{
				alert("请选择客户！");
				return;
			}
			var lk;
			var objsort = document.referform.objsort;
			if(objsort.value ==1){
				 lk=showModalDialog("../common/selectMemberLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			}else{
				 lk=showModalDialog("../common/selectOrganLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			}
			
			document.referform.linkman.value=lk.lname;
			document.referform.tel.value=lk.mobile;
		}
		
		
		
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<body style="overflow-y: auto;">
		<form name="referform" method="post"
			action="addServiceAgreementAction.do" onSubmit="return ChkValue();">
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
											<td>
												添加服务预约
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>

									<fieldset align="center">
										<legend>
											<table width="60" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														基本信息
													</td>
												</tr>
											</table>
										</legend>
										<table width="100%" border="0" cellpadding="0" cellspacing="1">
											<tr>
												<td align="right" width="10%">
													对象类型：
												</td>
												<td width="20%">
													<windrp:select key="ObjSort" name="objsort" p="n|f"
														value="1" />
												</td>
												<td align="right" width="14%">
													约定服务时间：
												</td>
												<td colspan="3">
													<input name="sdate" type="text" id="sdate"
														onFocus="javascript:selectDate(this)" size="10"
														value="<%=sdf.format(d)%>" readonly="readonly">
												</td>
											</tr>
											<tr>
												<td align="right" width="10%">
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
												<td align="right" width="14%">
													联系人：
												</td>
												<td width="23%">
													<input name="linkman" type="text" id="linkman"
													readonly="readonly"><a href="javascript:SelectLinkman();"><img
															src="../images/CN/find.gif" width="18" height="18"
															align="absmiddle" border="0"> </a>
															<span class="STYLE1">*</span>
												</td>
												<td align="right" width="10%">
													联系电话：
												</td>
												<td >
													<input name="tel" type="text" id="tel">
													<span class="STYLE1">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													服务种类：
												</td>
												<td>
													<windrp:select key="SContent" name="scontent" p="n|d" />
												</td>
												<td align="right">
													服务状态：
												</td>
												<td>
													<windrp:select key="SStatus" name="sstatus" p="n|d" />
												</td>
												<td align="right">
													等级：
												</td>
												<td>
													<windrp:select key="Rank" name="rank" p="n|d" />
												</td>
											</tr>
											<tr>
												
											</tr>
											<tr>
												<td align="right">
													问题描述：
												</td>
												<td colspan="5">
												<textarea name="question" style="width: 100%" rows="4" id="question" ></textarea><br>
												<span class="td-blankout">(公告内容不能超过256个字符!)</span>
												</td>
											</tr>
										</table>
									</fieldset>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
												<div align="center">
													<input type="submit" name="Submit" value="提交">
													&nbsp;&nbsp;
													<input type="reset" name="cancel"
														onClick="javascript:window.close()" value="取消">
												</div>
											</td>
										</tr>
									</table>


								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
