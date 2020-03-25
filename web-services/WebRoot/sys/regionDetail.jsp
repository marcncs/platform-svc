<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<div style="overflow-y: auto; height:100%">
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr style="height:50%">
				<td>
					<div>
						<IFRAME id="submsg3"
							style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
							name="submsg3" src="../keyretailer/listSalesAreaCountryAction.do?pid=${pid}&rank=${rank}"
							frameBorder="0" scrolling="no"></IFRAME>
					</div>
				</td>
			</tr>
			<tr style="height:1%">
				<td>
				</td>
			</tr>
			<tr style="height:50%">
				<td>
					<div>
						<IFRAME id="submsg1"
							style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
							name="submsg1" src="../keyretailer/listSUserAreaAction.do?pid=${pid}"
							frameBorder="0" scrolling="no"></IFRAME>
					</div>
				</td>
			</tr>
			<%--
			<tr style="height:50%">
				<td>
					<div>
						<IFRAME id="submsg3"
							style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
							name="submsg3" src="../keyretailer/listSBonusAreaAction.do?pid=${pid}"
							frameBorder="0" scrolling="no"></IFRAME>
					</div>
				</td>
			</tr>
			<tr style="height:1%">
				<td>
				</td>
			</tr>
			<tr style="height:50%">
				<td>
					<div>
						<IFRAME id="submsg1"
							style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
							name="submsg1" src="../keyretailer/listSUserAreaAction.do?pid=${pid}"
							frameBorder="0" scrolling="no"></IFRAME>
					</div>
				</td>
			</tr>
			
			<tr style="height:1%">
				<td>
				</td>
			</tr>
			<tr style="height:39%">
				<td>
					<div>
						<IFRAME id="submsg2"
							style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
							name="submsg2" src="../keyretailer/listSCustomerAreaAction.do?pid=${pid}"
							frameBorder="0" scrolling="no"></IFRAME>
					</div>
				</td>
			</tr>--%>
		</table>
		</div>
	</body>
</html>
