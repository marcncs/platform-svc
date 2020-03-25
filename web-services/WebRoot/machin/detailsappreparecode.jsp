<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
	</head>

	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>

						<fieldset align="center">
						<legend>
							<table width="110" border="0" cellpadding="0" cellspacing="0">
								<tr>
									
								</tr>
							</table>
						</legend>

						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							<tr align="center" class="title-top">
								<td >
									箱码
								</td>
								<td >
									对应托码
								</td>
								<td >
									释放状态
								</td>
								

							</tr>
							<logic:iterate id="p" name="codes">
								<tr class="table-back-colorbar">
									<td>
										${p.code}
									</td>
									<td>
										${p.tcode}
									</td>
									<td>
						<c:if test="${empty p.isRelease or p.isRelease == 0 }"></c:if>
		         	  	<c:if test="${p.isRelease == 1}">已释放</c:if>
									</td>
								
								</tr>
							</logic:iterate>
						</table>
					</fieldset>


				</td>
			</tr>
		</table>
	</body>
</html>
