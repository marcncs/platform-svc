<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script language="JavaScript">
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.offsetHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	}

	function excput(){
	search.action="../users/exportMemberAction.do";
	search.submit();
	search.action="../users/listMemberAction.do";
	}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listMemberAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td width="8%" align="right">
										注册日期：
									</td>
									<td width="26%">
										<input type="text" name="BeginDate" value="${BeginDate}"
											onFocus="javascript:selectDate(this)" size="12"
											readonly="readonly">
										-
										<input type="text" name="EndDate" value="${EndDate}"
											onFocus="javascript:selectDate(this)" size="12"
											readonly="readonly">
									</td>
									<td width="8%" align="right">
										关键字：
									</td>
									<td width="25%">
										<input type="text" name="KeyWord" value="${KeyWord}">
									</td>

									<%--<td width="8%" align="right">用户性质：</td>
        <td width="12%"><windrp:select key="UserType" name="UserType" p="y|f" value="${UserType}"/></td> --%>

									<td colspan="3" class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>

							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/users/exportMemberAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="50">
										<a href="javascript:excput()"><img
												src="../images/CN/outputExcel.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;导出 </a>
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../users/listMemberAction.do" />
								</td>

							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">

								<tr align="center" class="title-top">
									<td>
										编号
									</td>
									<td>
										注册手机
									</td>
									<td>
										注册时间
									</td>
									<td>
										上次登录时间
									</td>
									<td>
										登陆次数
									</td>
								</tr>
								<logic:iterate id="u" name="usList">
									<tr align="center" class="table-back-colorbar"
										onClick="CheckedObj(this,${u.id});">
										<td align="left">
											${u.id}
										</td>
										<td align="left">
											${u.loginname}
										</td>
										<td align="left">
											<windrp:dateformat value='${u.createdate}'
												p='yyyy-MM-dd HH:mm:ss' />
										</td>
										<td align="left">
											<windrp:dateformat value='${u.lastlogin}'
												p='yyyy-MM-dd HH:mm:ss' />
										</td>
										<td align="left">
											${u.logintimes}
										</td>
									</tr>
								</logic:iterate>

							</table>
						</form>
						<br>
					</div>
				</td>
			</tr>
		</table>


	</body>
</html>
