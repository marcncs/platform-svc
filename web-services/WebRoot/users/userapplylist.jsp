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
		<script src="../js/jquery-1.11.1.min.js"></script>
		<script language="JavaScript">
var $j = jQuery.noConflict(true); 
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

	var checkid=0;
	var isApproved=0;
	function CheckedObj(obj,objid,objislocked){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 isApproved = objislocked;
	}
	
	function approveUserApply(){
		if(checkid>0){
			if(isApproved!=0) {
				alert("该记录已审批过!");
			} else {
				popWin("../users/toApproveUserApplyAction.do?ID="+checkid,900,400);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
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
						<form name="search" method="post" action="listUserApplyAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
									<td width="8%" align="right">
										用户类型：
									</td>
									<td width="26%">
										<windrp:select key="ApplyUserType" name="userType" p="y|f"
														value="${userType}" />
									</td>
									<td width="9%" align="right">
										审核状态：
									</td>
									<td width="24%">
										<windrp:select key="ApproveStatus" name="isApproved" p="y|f"
														value="${isApproved}" />
									</td>
									<td align="right">
										关键字：
									</td>
									<td>
										<input type="text" name="KeyWord" value="${KeyWord}">
									</td>
									<td></td>
								</tr>
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
										
									</td>
									<td width="25%">
										
									</td>

									<td width="8%" align="right">
									</td>
									<td width="12%">
									</td>

									<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/users/toApproveUserApplyAction.do">
									<td width="50" align="center">
										<a href="javascript:approveUserApply();"><img
												src="../images/CN/addnew.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;审批</a>
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../users/listUserApplyAction.do" />
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
										公司名称
									</td>
									<td>
										手机号
									</td>
									<td>
										姓名
									</td>
									<td>
										用户类型
									</td>
									<td>
										省
									</td>
									<td>
										市
									</td>
									<td>
										区
									</td>
									<td>
										注册日期
									</td>
									<td>
										审核状态
									</td>
								</tr>
								<logic:iterate id="u" name="usList">
									<tr align="center" class="table-back-colorbar"
										onClick="CheckedObj(this,${u.id},${u.isApproved});">
										<td align="left">
											${u.id}
										</td>
										<td align="left">
											${u.organName}
										</td>
										<td align="left">
											${u.mobile}
										</td>
										<td align="left">
											${u.name}
										</td>
										<td align="left">
											<windrp:getname key='ApplyUserType' value='${u.userType}' p='f' />
										</td>
										<td align="left">
											${u.provinceName}
										</td>
										<td align="left">
											${u.cityName}
										</td>
										<td align="left">
											${u.areasName}
										</td>
										<td align="left">
											<windrp:dateformat value='${u.makeDate}' p='yyyy-MM-dd'/>
										</td>
										<td align="left">
											<windrp:getname key='ApproveStatus' value='${u.isApproved}' p='f' />
										</td>
									</tr>
								</logic:iterate>

							</table>
						</form>
						<br>
						<%--<div style="width: 100%">
							<div id="tabs1">
								<ul>
									<li>
										<a id="DetailUrl" href="javascript:Detail();"><span>详情</span>
										</a>
									</li>
									<ws:hasAuth operationName="${ruleAuthUrl }">
										<li>
											<a id="UserVisitUrl" href="javascript:UserVisit();"><span>管辖权限</span>
											</a>
										</li>
									</ws:hasAuth>
									<ws:hasAuth operationName="${visitAuthUrl }">
										<li>
											<a id="organVisitUrl" href="javascript:organVisit();"><span>业务往来权限</span>
											</a>
										</li>
									</ws:hasAuth>
									<li>
										<a id="regionUrl" href="javascript:regionInfo();"><span>关联区域</span>
										</a>
									</li>
									<ws:hasAuth
										operationName="/keyretailer/listUserCustomerAction.do">
										<li>
											<a id="customerUrl" href="javascript:userCustomer();"><span>关联客户</span>
											</a>
										</li>
									</ws:hasAuth>
									
            <ws:hasAuth operationName="/users/listMoveCanuseOrganAction.do">
            <li><a id="MoveOrganUrl" href="javascript:MoveOrgan();"><span>转仓机构</span></a></li>
            </ws:hasAuth>
								</ul>
							</div>
							<div>
								<IFRAME id="submsg"
									style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
									name="submsg" src="../sys/remind.htm" frameBorder="0"
									scrolling="no" onload="setIframeHeight(this);"></IFRAME>
							</div>
						</div>
					--%></div>
				</td>
			</tr>
		</table>


	</body>
</html>
