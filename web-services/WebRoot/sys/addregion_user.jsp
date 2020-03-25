<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/function.js"></script>
		<script language="JavaScript">
			function chkForm(){
				var sortname = document.addform.sortname;
				if(sortname.value ==null || sortname.value==""||sortname.value.trim()==""){
					alert("区域名称不能为空!");
					sortname.focus();
					return false;
				}
				showloading();
				return true;
			}
			
	   function SelectUser(){
	   
	   var uid=document.getElementById("userid").value;
	   var acode=document.getElementById("acode").value;
	   var p=showModalDialog("../common/selectRegionUserAction.do?uid="+uid+"&acode="+acode,null,"dialogWidth:25cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.addform.userid.value=p.uid;
			document.addform.username.value=p.uname;
	}
		</script>
	</head>

	<body style="overflow: auto;">
		<form name="addform" method="post" action="../sys/relateUserAction.do"
			onSubmit="return chkForm();">
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
									关联用户
								</td>
							</tr>
						</table>

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
									<td colspan="5"><input type="hidden" name="acode" value="${ps.regioncode}">	区域名称：
								    <input name="sortname" type="text" id="sortname" value="${ps.sortname}"  readonly   maxlength="60"><span class="STYLE1">*</span></td>
								
								
							   <td width="20%" width="20%" ></td>
						          <td >
						          选择用户：
								  <input name="userid" type="hidden" id="userid"  value="${ps.userid}">
								  <input name="username" type="text" id="username" size="18"  value="${username}" readonly><a href="javascript:SelectUser();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
								  <span class="text-red3">*</span>
								  </td>
							</tr>
							</table>
						</fieldset>
						<table width="100%">
							<tr>
								<td align="center">
									<input type="submit" name="Submit" value="确定">
									&nbsp;&nbsp;
									<input type="button" name="cancel" value="取消"
										onClick="javascript:window.close();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
