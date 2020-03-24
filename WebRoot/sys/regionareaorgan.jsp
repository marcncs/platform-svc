<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
	var checkid = 0;
	var pdmenu = 0;
	function CheckedObj(obj, objid) {

		for (i = 0; i < obj.parentNode.childNodes.length; i++) {
			obj.parentNode.childNodes[i].className = "table-back-colorbar";
		}
		obj.className = "event";
		checkid = objid;
	}
	//		var checkid=0;
	//	var checkcname="";
	//	var pdmenu=0;
	//	function CheckedObj(obj,objid,objcname){

	//	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	//	 {
	//		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	//	 }

	//	 obj.className="event";
	//	 checkid=objid;
	//	 checkcname=objcname;
	//	}

	function addNew(psid) {
		if (psid.length == 3) {
			alert("大区不能关联用户!只有办事处可以关联用户")
			return ;
		}
		if(psid.length == 0){
		alert("请选择办事处")
			return ;
		}
		popWin("../sys/toAddRegionAreaAction.do?PSID=" + psid, 900, 650);
	}
	//删除多条记录
	function Del_Many() {
		var flag = false;
		var rids = "";
		var rid = document.getElementsByName("pid");
		if (rid.length > 0) {
			for ( var i = 0; i < rid.length; i++) {
				if (rid[i].checked) {
					flag = true;
					rids = rid[i].value + "," + rids;
				}
			}
		}
		if (flag) {
			if (confirm("确认要解除关联吗?")) {
				popWin2("../sys/delRegionAreaAction.do?ID=" + rids);
			}
		} else {
			alert("请选择你要操作的记录!");
		}
	}
	//删除一条记录
	function Del() {
		if (checkid != "") {
			if (confirm("确认要解除关联吗?")) {
				popWin2("../sys/delRegionAreaAction.do?ID=" + checkid);
			}
		} else {
			alert("请选择你要操作的记录!");
		}
	}
	function Import() {
		popWin("../sys/toImportRegionUserAction.do", 500, 300);

	}
	function OutPut() {
		excputform.action = "excPutProductAction.do";
		excputform.submit();
	}

	this.onload = function onLoadDivHeight() {
		document.getElementById("listdiv").style.height = (document.body.offsetHeight - document
				.getElementById("bodydiv").offsetHeight)
				+ "px";
	}


	//全选框点击事件
	function allCheck() {
		if ($("#checkAll").attr('checked') == true) {
			$("input:checkbox").attr('checked', 'checked');
		} else {
			$("input:checkbox").removeAttr('checked');
		}
	}
	function changeCheckAll() {
		var allCheckFlag = true;
		$("#form1").find("input:checkbox").each(function(i) {
			if (this.name == "pid" && this.checked == false) {
				allCheckFlag = false;
			}
		});
		if (allCheckFlag == true) {
			$("#checkAll").attr('checked', 'checked');
		} else {
			$("#checkAll").removeAttr('checked');
		}
	}

	function Check() {
		var pid = document.all("pid");
		var checkall = document.all("checkall");
		if (pid == undefined) {
			return;
		}
		if (pid.length) {
			for (i = 0; i < pid.length; i++) {
				pid[i].checked = checkall.checked;
			}
		} else {
			pid.checked = checkall.checked;
		}
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
									关联用户
									<input type="hidden" name="ID" value="${id}">
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listRegionAreaAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
									<td width="8%" align="right">
										关键字：
									</td>
									<td width="50%">
										<input type="text" name="KeyWord" value="${KeyWord}"
											maxlength="30">
									</td>
									<td width="4%" class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
									<input type="hidden" name="OtherKey" value="${OtherKey}" />
								</tr>

							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="100">
									<a href="javascript:addNew('${pid}');"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;关联用户</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="80">
									<a href="javascript:Del_Many();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;解除关联</a>
								</td>
								
								
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="70">
									<a href="javascript:Import()"><img
											src="../images/CN/import.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;导入用户 </a>
								</td>
								
								<td class="SeparatePage">
									<pages:pager action="../sys/listRegionAreaAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<form id="form1" action="">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">
								<tr align="center" class="title-top">
									<td width="2px" class="sorttable_nosort">
										<input type="checkbox" id="checkAll" onclick=allCheck();>
									</td>
									<td width="30%">
										编号
									</td>
										<td width="30%">
										用户编号
									</td>
									<td width="40%">
										用户姓名
									</td>
								</tr>
								<logic:iterate id="p" name="alapls">
									<tr class="table-back-colorbar"
										onClick="CheckedObj(this,'${p.id}');">
										<td width="16px">
											<input type="checkbox" id="chk_${p.id}" value="${p.id}"
												name="pid" onclick="changeCheckAll()">
										</td>
										<td>
											${p.userid}
										</td>
										<td>
											${p.userlogin}
										</td>
										<td>
											${p.username}
										</td>
									</tr>
								</logic:iterate>
							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>
		<form name="excputform" method="post" action="excPutProductAction.do">
			<input type="hidden" name="OtherKey" id="OtherKey"
				value="${OtherKey}">
			<input type="hidden" name="KeyWord" id="KeyWord" value="${KeyWord }">
		</form>
	</body>
</html>
