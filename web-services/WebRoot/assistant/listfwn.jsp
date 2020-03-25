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
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									${menusTrace}
								</td>
							</tr>
						</table>
						<form name="search" method="post"
							action="../assistant/listFWNInitAction.do" onSubmit="return CheckValue();">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td width="8%" align="right">
										验证码：
									</td>
									<td width="220px">
										<input type="text" name="KeyWord" value="${KeyWord}" id="KeyWord"
											size="35" maxlength="53" dataType="Require" msg="请输入验证码">
									</td>
									<td class="SeparatePage1">
										<input name="Submit" type="image" id="Submit" 
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<c:if test="${prompt == 'fn'}">
				<tr>
					<td>
						暂未获取到查询码：（${KeyWord}）相关的产品信息！
					</td>
				</tr>
			</c:if>
			<c:if test="${prompt == 'r'}">
				<tr>
					<td>
						<div id="listdiv" style="overflow-y: auto; height: 600px;">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">
								<tr align="center" class="title-top-lock">
									<td>
										防伪码
									</td>
									<td>
										箱码
									</td><!--
									<td>
										二维码
									</td>
									--><td>
										生成日期
									</td>
									<td>
										产品名称
									</td>
									<td>
										规格
									</td>
									<td>
										生产日期
									</td>
									<td>
										包装日期
									</td>
									<td>
										批号
									</td>
									<td>
										查询次数
									</td>
									<td>
										第一次查询时间
									</td>
									<td>
										是否使用
									</td>
								</tr>
								<tr class="table-back-colorbar">
									<td>
										${p.primaryCode}
									</td>
									<td>
										${p.cartonCode }
									</td>
									<!--<td>
										${cc.outPinCode }
									</td>
									--><td>
										${p.createDate}
									</td>
									<td>
									<c:if test="${pj.printJobId != -1}"> 
										${pj.materialName}
									</c:if>
									</td>
									<td>
									<c:if test="${pj.printJobId != -1}"> 
										${pd.specmode}
									</c:if>
									</td>
									<td>
									<c:if test="${pj.printJobId != -1}"> 
										<windrp:dateformat value="${pj.productionDate}" p='yyyy-MM-dd'/>
									</c:if>
									</td>
									<td>
									<c:if test="${pj.printJobId != -1}"> 
										<windrp:dateformat value="${pj.packagingDate}" p='yyyy-MM-dd'/>
									</c:if>
									</td>
									<td>
									<c:if test="${pj.printJobId != -1}"> 
										${pj.batchNumber}
									</c:if>
									</td>
									<td>
										${q.queryNum}
									</td>
									<td>
										<windrp:dateformat value="${firstTime}" />
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${p.isUsed}' p='f' />
									</td>
								</tr>
							</table>
							<br>
						</div>
					</td>
				</tr>
			</c:if>
		</table>
	</body>
</html>
