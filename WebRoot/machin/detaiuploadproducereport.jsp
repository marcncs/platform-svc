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
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td>
								产品报表详情
							</td>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td style="float: right; padding-right: 20px;">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

					<fieldset align="center">
						<legend>
							基本信息
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td width="11%" align="right">
									产品类别：
								</td>
								<td width="22%">
									${ps.sortname}
								</td>
								<td width="10%" align="right">
									生产日期：
								</td>
								<td width="20%">
									${dtl.proDate}
								</td>
								<td width="18%" align="right">
									产品编号：
								</td>
								<td width="28%">
									${pro.id}
								</td>
							</tr>
							<tr>
								<td align="right">产品名称：
								</td>
								<td>${pro.productname}</td>
								<td align="right">批号：
								</td>
								<td>
									${dtl.batchNo}
								</td>
								<td align="right">规格：
								</td>
								<td>
									${pro.specmode}
								</td>
							</tr>
							<tr>
								<td align="right">产线编号：
								</td>
								<td>
								${productionLine}
								</td>
								<td align="right">小包装码：
								</td>
								<td>
									${dtl.primaryCode}
								</td>
								 <td align="right">外箱条码：
								</td>
								<td>${dtl.cartonCode}</td>
								
							</tr>
							<tr>
							  <td align="right">暗码：
								</td>
								<td>
									${dtl.covertCode}
								</td>
								<td align="right">生产时间：
								</td>
								<td>
									${printDate}
								</td>
								
								<td align="right">
								</td>
								<td></td>
							</tr>
						</table>
					</fieldset>


				</td>
			</tr>
		</table>
	</body>
</html>
