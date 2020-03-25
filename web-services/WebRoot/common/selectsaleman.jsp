<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<base target="_self">
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
	var checkid=0;
	var checkname="";
	function CheckedObj(obj,objid,objname){

	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	}
	function Affirm(){
	if(checkid>0){
		var user={uid:checkid, uname:checkname};
		window.returnValue=user;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}
	}
 function AffirmComfin(){
  var user;
  var userArray =new Array();
	var che = document.getElementsByName("che");
		if(checkid>0){
			  for(var i=0;i<che.length;i++){
			    if(che[i].checked){
			   user = {uid : document.getElementsByName("userid")[i].value ,uname :document.getElementsByName("realname")[i].value};
			   userArray.push(user);
		      }
  	      }
			  window.returnValue=userArray;
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
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									选择用户
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="selectSaleManAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
									<td width="16%" align="right">
										名称关键字：
									</td>
									<td width="84%">
										<input type="text" name="KeyWord" value="${KeyWord}">
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
									<a href="#" onClick="javascript:AffirmComfin();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;确定</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="#" onClick="javascript:window.close();"><img
											src="../images/CN/cancelx.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;取消</a>
								</td>
								<td class="SeparatePage">
									<pages:pager action="../common/selectSaleManAction.do" />
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

								<tr align="center" class="title-top">
									<td>
										&nbsp;
									</td>
									<td width="13%">
										用户编号
									</td>
										<td width="13%">
										用户code
									</td>
									<td width="58%">
										用户名称
									</td>
									<td width="21%">
										性别
									</td>
								</tr>
								<logic:iterate id="p" name="sls">
									<tr align="center" class="table-back-colorbar"
										onClick="CheckedObj(this,${p.userid},'${p.realname}');"
										onDblClick="Affirm();">
										<td align="center">
											<input type="checkbox" name="che" value="${p.userid}"
												${p.isChecked==1? "checked":"" }    />
											<input type="hidden" name="userid" value="${p.userid}">
											<input type="hidden" name="realname" value="${p.realname}">
										</td>
										<td>
											${p.userid}
										</td>
										<td>
											${p.loginname}
										</td>
										<td>
											${p.realname}
										</td>
										<td>
											${p.sexname}
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
