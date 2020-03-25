<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery-1.11.1.min.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/highcharts.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
function CheckedObj(obj){

	for(i=0; i<obj.parentNode.childNodes.length; i++)
	{
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
	}
 
	obj.className="event";
}
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<form name="searchform" method="post"
							action="../report/productValidateReportAction.do" >
					</form>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td style="font-weight: bold;align:"left";>
									查询清单
								</td>
								<td class="SeparatePage">
									<pages:pager action="../report/productValidateDetailAction.do" />
								</td>
							</tr>
						</table>
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top-lock">
								<td>
									查询码
								</td>
								<td>
									查询时间
								</td>
								<td>
									查询电话/IP
								</td>
								<td>
									区域
								</td>
								<td >
									防伪码属性
								</td>
							</tr>
							<c:forEach items="${list}" var="d">
								<tr class="table-back-colorbar" onClick="CheckedObj(this);">
									<td>
										${d.idcode }
									</td>
									<td>
										${d.queryDate }
									</td>
									<td>
										${d.queryAddr }
									</td>
									<td>
									</td>
									<td>
										<windrp:getname key='TrueOrFalse' value='${d.chkTrue }' p='f' />
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
