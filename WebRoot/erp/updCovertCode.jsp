<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script src="../js/prototype.js"></script>
		<script src="../js/function.js"></script>
	</head>
	<script language="javascript">
	function formcheck() {
		if ($F('idcode').trim() == "") {
			alert("条码不能为空!");
			$('idcode').select();
			return; 
		}
		/*else if($F('idcode').trim().length != 20){
			alert("条码格式不正确!");
			$('idcode').select();
			return;
		}
		 */
		addIdcode();
	}

	function showResponse2(originalRequest) {
		var data = eval('(' + originalRequest.responseText + ')');
		if (data != undefined) {
			buildText(data.result);
			document.submsg.document.forms[0].submit();
		}
	}

	function buildText(str) {//赋值给消息提示框
		document.getElementById("result").style.display = "";
		document.getElementById("result").innerHTML = str;
		setTimeout("document.getElementById('result').style.display='none'",
				2000);
	}

	function openpage() {//
		var billid = $F('billid');
		var prid = $F('prid');
		popWin4("multiAddIdcodeAction.do?billid=" + billid + "&prid=" + prid,
				800, 600, "new");

	}

	function Del() {
		var flag = false;
		var rid = document.all("che");
		var rids = "";
		if (rid.length > 1) {
			for ( var i = 0; i < rid.length; i++) {
				if (rid[i].checked) {
					flag = true;
					rids = rid[i].value + "," + rids;
				}
			}
		} else {
			if (rid.checked) {
				flag = true;
				rids = rid.value + ",";
			}
		}
		if (flag) {
			if (window.confirm("您确认要释放所选的记录吗？")) {
				showloading();
				popWin4("../erp/releaseCodeAction.do?ids=" + rids, 500, 250,
						"del");
			}
		} else {
			alert("请选择你要操作的记录!");
		}
	}

	function Del2() {
		var flag = false;
		var rid = document.all("che");
		var rids = "";
		if (rid.length > 1) {
			for ( var i = 0; i < rid.length; i++) {
				if (rid[i].checked) {
					flag = true;
					rids = rid[i].value + "," + rids;
				}
			}
		} else {
			if (rid.checked) {
				flag = true;
				rids = rid.value + ",";
			}
		}
		if (flag) {
			if (window.confirm("您确认要取消释放所选的记录吗？")) {
				showloading();
				popWin4("../erp/releaseCodeAction.do?type=cancel&ids=" + rids,
						500, 250, "del");
			}
		} else {
			alert("请选择你要操作的记录!");
		}
	}
	function Check() {
		var pid = document.all("che");
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

	<body>
		<style>
#result {
	font-weight: bold;
	position: absolute;
	left: 753px;
	top: 20px;
	text-align: center;
	background-color: #ff0000;
	color: #fff;
	LEFT: expression(Math.abs (   Math.round ((   document.body.clientWidth -   
		 result.offsetWidth)/ 2 ) ) );
	TOP: expression(Math.abs (   Math.round ((   document.body.clientHeight)/
		2 +   document.body.scrollTop-180 ) ) )
}

#result h1 {
	font-size: 12px;
	margin: 0px;
	padding: 0px 5px 0px 5px
}
;
</style>
		<div id="result"></div>
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
								更新暗码
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						bordercolor="#BFC0C1">
						<tr>
							<td>
								<div id="bodydiv">
									<form name="search" method="post"
										action="../erp/toUpdCovertCodeAction.do">
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr class="table-back">
												<td align="right">
													关键字：
												</td>
												<td>
													<input type="text" name="KeyWord" value="${KeyWord}"
														maxlength="40" size="20" width="100%">
													<input type="hidden" name="ID" id="ID" value="${ID}">
												</td>

												<td align="right">
													<input name="Submit" type="image" id="Submit"
														src="../images/CN/search.gif" border="0" title="查询">
												</td>
											</tr>
										</table>
									</form>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr class="title-func-back">
											<td class="SeparatePage">
												<pages:pager action="../erp/toUpdCovertCodeAction.do" />
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div id="listdiv" style="overflow-y: auto; height: 480px;">
									<FORM METHOD="POST" name="listform" ACTION="../erp/updCovertCodeAction.do">
										<input type="hidden" name="ID" id="ID" value="${ID}">
										<table class="sortable" width="100%" border="0"
											cellpadding="0" cellspacing="1">
											<tr align="center" class="title-top-lock">
												<td width="30%">
													箱码
												</td>
												<td width="30%">
													暗码
												</td>
												<td width="30%">
													暗码修改为
												</td>
											</tr>
											<logic:iterate id="p" name="covertCodes">
												<tr align="center" class="table-back-colorbar"
													onClick="CheckedObj(this,'${p.code}','${p.code}');">
													<td align="left">
														${p.carton_code}
													</td>
													<td align="left">
														${p.covert_code}
													</td>
													<td align="left">
														<input type="text" name="covertCode" value=""
															maxlength="11" size="20" width="100%">
														<input type="hidden" name="cartonCode"
															value="${p.carton_code}" width="100%">
													</td>
												</tr>
												<c:set var="count" value="${count+1}" />
											</logic:iterate>
										</table>
										<table width="100%" border="0" cellpadding="0" cellspacing="1">
											<tr>
												<td>
													<div align="center">
														<input type="submit" value="提交">
													</div>
												</td>
											</tr>
										</table>
									</form>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
