<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"></SCRIPT>
		<script type="text/javascript" src="../js/validator.js"></script>
		<script language="JavaScript">
	var checkid = "";
	function CheckedObj(obj, objid) {

		for (i = 1; i < obj.parentNode.childNodes.length; i++) {
			obj.parentNode.childNodes[i].className = "table-back";
		}

		obj.className = "event";
		checkid = objid;
	}

	function CheckValue() {
		var keywork = document.getElementById("KeyWord");

		if (keywork.value.trim() == "") {
			alert("请输入防伪码!");
			return false;
		}
	}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<style>
html,body {
	margin: 0;
	font-family: Verdana, Geneva, sans-serif, "黑体-简";
}

input {
	float: left;
	width: 150px;
	height: 14px;
	background: #fff;
	border: 1px #ccc solid;
	margin: 0 3px;
}

.t1 {
	margin-top: 30px;
	font-size: 13px;
	background: #ccc;
}

.t1 td {
	background: #fff;
}

.t2 {
	text-align: left;
	padding-left: 10px;
	color: #5382b8;
	font-weight: bold;
}

.t3 {
	text-align: center;
	padding-left: 10px;
	color: #5382b8;
	font-weight: bold;
}

.red {
	color: #ff0000;
}

.m1 {
	float: left;
	margin-left: 110px;
	padding-top: 2px;
}
</style>


	</head>
	<body>
		<form name="search" method="post" action="${url}"
			onSubmit="return CheckValue();">
			<table width="460" border="0" align="center" cellpadding="0" cellspacing="1"
				class="t1">
				<tr>
					<td>
						<img src="../images/titlebg.gif" width="460" height="35" />
					</td>
				</tr>
				<tr>
					<td height="320" align="center" valign="middle">
						<table width="400" border="0" cellspacing="0" cellpadding="0">
							<c:if test="${prompt=='r'}">
								<tr>
									<td width="120" height="25" align="right">
										防伪码:
									</td>
									<td width="280" class="t2">
										${p.primaryCode}
									</td>
								</tr>
								<%--<tr>
									<td height="25" align="right">
										箱码:
									</td>
									<td class="t2">
										${p.cartonCode }
									</td>
								</tr>
								<tr>
									<td height="25" align="right">
										生成日期:
									</td>
									<td class="t2">
										${p.createDate}
									</td>
								</tr>
								--%><tr>
									<td height="25" align="right">
										产品名称:
									</td>
									<td class="t2">
										${pj.materialName}&nbsp;${pd.specmode}
									</td>
								</tr>
								<%--<tr>
									<td height="25" align="right">
										规格:
									</td>
									<td class="t2">
										${pd.specmode}
									</td>
								</tr>
								<tr>
									<td height="25" align="right">
										生产日期:
									</td>
									<td class="t2">
										<windrp:dateformat value="${pj.productionDate}" p='yyyy-MM-dd' />
									</td>
								</tr>
								<tr>
									<td height="25" align="right">
										包装日期:
									</td>
									<td class="t2">
										<windrp:dateformat value="${pj.packagingDate}" p='yyyy-MM-dd' />
									</td>
								</tr>
								--%><tr>
									<td height="25" align="right">
										批号:
									</td>
									<td class="t2">
										${pj.batchNumber}
									</td>
								</tr>
								<tr>
									<td height="25" align="right">
										查询次数:
									</td>
									<td class="t2">
										${q.queryNum}
									</td>
								</tr>
								<%--<tr>
									<td height="25" align="right">
										第一次查询时间:
									</td>
									<td class="t2">
										<windrp:dateformat value="${firstTime}" />
									</td>
								</tr>
								<tr>
									<td height="25" align="right">
										是否使用:
									</td>
									<td class="t2">
										<windrp:getname key='YesOrNo' value='${p.isUsed}' p='f' />
									</td>
								</tr>
								--%>
							</c:if>
							<c:if test="${prompt=='fn'}">
								<tr>
									<td height="320" align="center" valign="middle">
										<table width="400" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td height="210" align="center" valign="top">
													<img src="../images/cry.png" width="200" height="200" />
												</td>
											</tr>
											<tr>
												<td height="25" class="t3" align="center">
													暂未获取到查询码：
													<span class="red">（${primaryCode}）</span> 相关的产品信息！
												</td>
											</tr>
											<tr>
												<td height="25" class="t3" align="center">
													如有疑问，请拨打咨询电话 400 82 365 82
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</c:if>
						</table>
					</td>
				</tr>
				<tr>
					<td height="45" valign="middle">
						<span class="m1">继续查询</span>
						<input type="text" name="FWIdcode" value="${FWIdcode}" id="FWIdcode"
							size="35" maxlength="53" dataType="Require" msg="请输入验证码">
						<img src="../images/btn.gif" width="19" height="18" onClick="submit();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

