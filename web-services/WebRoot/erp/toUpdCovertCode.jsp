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
	//  __tag_14$4_

	function checknum(obj) {
		obj.value = obj.value.replace(/[^\d]/g, "");
	}
	function checkNumber(obj) {
		if (isNaN(obj.value)) {
			obj.value = obj.value.replace(/[^\d.]/g, "");
			obj.value = obj.value.replace(/^\./g, "");
			obj.value = obj.value.replace(/\.{2,}/g, ".");
			obj.value = obj.value.replace(".", "$#$").replace(/\./g, "", "")
					.replace("$#$", ".");
		}
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
		//	var productPlanId = document.getElementById("productPlanId").value;
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
	function closePlan() {
		var lastnum = document.addform.lastnum.value;
		if (lastnum == '') {
			alert("请输入实际生产数量!");
			return;
		}
		var pnum = document.addform.pnum.value;
		var rnum = document.addform.rnum.value;
		if (parseInt(lastnum, 10) > parseInt(pnum, 10)) {
			alert("实际生产数量不能大于计划数量!");
			return;
		}
		var pid = document.addform.pid.value;
		if (rnum != '' && rnum != '0') {
			if (parseInt(pnum, 10) == parseInt(rnum, 10)
					+ parseInt(lastnum, 10)) {
				if (confirm("请确认:计划生产" + pnum + "箱,释放" + rnum + "箱,实际生产"
						+ lastnum + "箱")) {
					popWin2("../erp/closeProductPlanAction.do?type=reProcess&ID="
							+ pid);
					var bb = setTimeout("winClose(1);", 500);
				}
			} else if (parseInt(rnum, 10) < parseInt(pnum, 10)
					- parseInt(lastnum, 10)) {
				alert("计划生产"
						+ pnum
						+ "箱,实际生产"
						+ lastnum
						+ "箱,您还要释放"
						+ (parseInt(pnum, 10) - parseInt(lastnum, 10) - parseInt(
								rnum, 10)) + "箱条码!");
				return;
			} else if (parseInt(rnum, 10) > parseInt(pnum, 10)
					- parseInt(lastnum, 10)) {
				alert("计划生产"
						+ pnum
						+ "箱,实际生产"
						+ lastnum
						+ "箱,请取消释放"
						+ (parseInt(lastnum, 10) + parseInt(rnum, 10) - parseInt(
								pnum, 10)) + "箱条码!");
				return;
			}
		} else {
			if (parseInt(pnum, 10) == parseInt(lastnum, 10)) {
				if (confirm("请确认:计划生产" + pnum + "箱,实际生产" + lastnum + "箱")) {
					popWin2("../erp/closeProductPlanAction.do?type=reProcess&ID="
							+ pid);
					var bb = setTimeout("winClose(1);", 500);
				}
			} else {
				alert("计划生产" + pnum + "箱,实际生产" + lastnum + "箱,您还要释放"
						+ (parseInt(pnum, 10) - parseInt(lastnum, 10)) + "箱条码!");
				return;
			}
		}

	}
	function winClose(isRefrash) {
		window.returnValue = isRefrash;
		if (window.opener.document.forms[0]) {
			window.opener.document.forms[0].submit();
			//window.opener.location.src=window.opener.location.src;
		}
		window.close();
	}
	function downCovertCode(){
		popWin2("../erp/downloadCovertCodeAction.do?type=download&ID=${ID}");
	}	
	function updCovertCode(){
	    document.updform.submit();
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
	LEFT: expression(Math.abs ( Math.round (( document.body.clientWidth -  
		result.offsetWidth)/ 2 ) ) );
	TOP: expression(Math.abs ( Math.round (( document.body.clientHeight)/ 2
		+ document.body.scrollTop-180 ) ) )
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
								暗码编辑
							</td>
						</tr>
					</table>
					<form name="addform" method="post" action="">
						
						<table width="100%" border="0" cellpadding="0" cellspacing="1">

							<tr>
								<td width="10%" align="right">
									PO单号：
								</td>
								<td width="19%">
									<input type="hidden" name="pid" value="${plan.id}">
									${plan.PONO}
								</td>
								<td width="10%" align="right">
									计划生产(箱)：
								</td>
								<td width="12%">
									<input type="hidden" name="pnum" value="${plan.boxnum}">
									${plan.boxnum}
								</td>
								<td width="10%" align="right">
									已释放箱数：
								</td>
								<td width="12%">
									<input type="hidden" name="rnum" value="${realnum}">
									${realnum}
								</td>


							</tr>
							<tr>
								<td align="right">
									工厂名称：
								</td>
								<td>
									<windrp:getname key='organ' value='${plan.organId}' p='d' />
								</td>
								<td align="right">
									物料号：
								</td>
								<td width="19%">
									${product.mCode}
								</td>
								<td align="right">
									物料名称：
								</td>
								<td width="19%">
									${product.productname}
								</td>

							</tr>
							<tr>
								<td align="right">
									物料批次：
								</td>
								<td width="19%">
									${plan.mbatch}
								</td>
								<td align="right">
									生成打印任务：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${printflag}' p='f' />
									<!--	      <windrp:getname key='YesOrNo' value='${plan.approvalFlag}' p='f'/>-->
								</td>
								<td align="right">
									是否结束：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${plan.closeFlag}' p='f' />
								</td>

							</tr>
						</table>

					</form>
					<br>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						bordercolor="#BFC0C1">
						<tr>
							<td>
								<div id="bodydiv">
									<form name="search" method="post"
										action="../erp/toUpdCovertCodeAction.do?close=1">
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr class="table-back">

												<td width="50%">
													&nbsp; 条码(关键字)： &nbsp;
													<input type="text" name="KeyWord" value="${KeyWord}"
														maxlength="40" size="60" width="100%">
													<input type="hidden" name="ID"
														id="ID" value="${plan.id}">
												</td>

												<td align="right">
													是否释放：
												</td>
												<td>
													<windrp:select key="YesOrNo" name="isRelease"
														value="${isRelease}" p="y|f" />
												</td>

												<td>
													<input name="Submit" type="image" id="Submit"
														src="../images/CN/search.gif" border="0" title="查询">
												</td>
											</tr>

										</table>
									</form>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr class="title-func-back">
											<c:if test="${needCovertCode == 1}">
											<ws:hasAuth operationName="/erp/downloadCovertCodeAction.do">
												<td width="1">
													<img src="../images/CN/hline.gif" width="2" height="14">
												</td>
												<td width="70" align="center">
													<a href="javascript:downCovertCode();"> <img
															src="../images/CN/outputExcel.gif" width="16" height="16"
															border="0" align="absmiddle">&nbsp;下载</a>
												</td>
											</ws:hasAuth>
											<ws:hasAuth operationName="/erp/updCovertCodeAction.do">
												<td width="1">
													<img src="../images/CN/hline.gif" width="2" height="14">
												</td>
												<td width="70" align="center">
													<a href="javascript:updCovertCode();"> <img
															src="../images/CN/setmima.gif" width="16" height="16"
															border="0" align="absmiddle">&nbsp;保存</a>
												</td>
											</ws:hasAuth>
											</c:if>
											<td class="SeparatePage">
												<pages:pager
													action="../erp/toUpdCovertCodeAction.do?close=1" />
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div id="listdiv" style="overflow-y: auto; height: 480px;">
									<FORM METHOD="POST" name="updform" ACTION="../erp/updCovertCodeAction.do">
										<table class="sortable" width="100%" border="0"
											cellpadding="0" cellspacing="1">
											<input type="hidden" name="ID"
														id="ID" value="${plan.id}">
											<tr align="center" class="title-top-lock">
												<td>
													条码
												</td>
												<td>
													单位
												</td>
												<td>
													对应暗码
												</td>
												<td width="16%">
													对应托码
												</td>
												<td width="16%">
													释放状态
												</td>
											</tr>
											<logic:iterate id="p" name="codelist">
												<tr align="center" class="table-back-colorbar">
													<td align="left">
														${p.code}
													</td>
													<td align="left">
														箱
													</td>
													<td align="left">
													<c:if test="${needCovertCode == 1 and (plan.closeFlag == 0 or plan.closeFlag == null)}">
														<input type="text" name="newCode" value="${p.covert_code}"
															maxlength="11" size="20" width="100%">
														<input type="hidden" name="oldCode"
															value="${p.covert_code}">
														<input type="hidden" name="cartonCode"
															value="${p.code}">
													</c:if>
													<c:if test="${needCovertCode == 1 and plan.closeFlag == 1}">
														${p.covert_code}
													</c:if>
													</td>
													<td align="left">
														${p.tcode}
													</td>
													<td align="left">
														<c:if test="${empty p.isrelease or p.isrelease == 0 }"></c:if>
														<c:if test="${p.isrelease == 1}">已释放</c:if>
													</td>
												</tr>
												<c:set var="count" value="${count+1}" />
											</logic:iterate>

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
