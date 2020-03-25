<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
	function Repeal(siid){
			popWin2("../users/repealOrganAction.do?ID="+siid);
	}
	
	function CancelRepeal(siid){
			popWin2("../users/cancelRepealOrganAction.do?ID="+siid);
	}

</script>

<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
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
									流向查询&gt;&gt;物流码全链查询&gt;&gt;产品追踪
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
								<td width="17%">
									发货清单号
								</td>
								<td width="17%">
									发货日期
								</td>
								<td width="17%">
									仓库名字
								</td>
								<td width="11%">
									机构出货代码
								</td>
								<td width="11%">
									机构运货代码
								</td>
								<td width="11%">
									机构名字
								</td>
								<td width="16%">
									产品名
								</td>
							</tr>
							<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${pc.cartonCode}');">
							</tr>
						</table>
					</form>
				<br>
			</div>
		</td>
	</tr>
</table>
</body>
</html>
