<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<base target="_self">
	<head> 
		<title>机构列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script src="../js/prototype.js"></script>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script type="text/javascript">
var checkid="";
var role={id:"",rolename:""};
function CheckedObj(obj,objid,objrolename){
 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 obj.className="event";
 checkid=objid;
 role.id=objid;
 role.rolename=objrolename;
}

function Affirm(){
		if(checkid!=""){
			window.returnValue=role;
			window.close();
		}else{
			alert("请选择你要操作的记录!");
		}
	}

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									选择角色
								</td>

							</tr>
						</table>
						<form name="search" method="post"
							action="../common/selectRoleAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td align="right">
										关键字：
									</td>
									<td>
										<input type="text" name="KeyWord" value="${KeyWord}">
									</td>
									<td align="right">
										&nbsp;

									</td>
									<td>
										&nbsp;

									</td>
									<td align="right">
										&nbsp;

									</td>
									<td>
										&nbsp;

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
								<td width="50">
									<a href="#" onClick="javascript:Affirm();"><img
											src="../images/CN/addnew.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;确定</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="#" onClick="javascript:window.close();"><img
											src="../images/CN/cancelx.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;取消</a>
								</td>
								<td class="SeparatePage">
									<pages:pager action="../common/selectRoleAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 400px;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">

								<tr align="center" class="title-top-lock">
									<td>
										编号
									</td>
									<td>
										角色名称
									</td>
									<td>
										描述
									</td>
								</tr>
								<logic:iterate id="d" name="roleList">
									<tr align="center" class="table-back-colorbar"
										onClick="CheckedObj(this,'${d.id}','${d.rolename}');"
										onDblClick="Affirm();">
										<td>
											${d.id}
										</td>
										<td>
											${d.rolename}
										</td>
										<td>
											${d.describes}
										</td>
									</tr>
								</logic:iterate>

							</table>
						</form>

					</div>
				</td>
			</tr>
		</table>

	</body>
</html>

