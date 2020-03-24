<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>箱码列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
		function showDetail(id){
			showModalDialog("listCodeDetailAction.do?printJobId="+id,null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		}
		</script>
	</head>

	<body> 
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto;">
						<div style="width: 100%">
							<div>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<tr class="title-func-back">

											<td width="50">
												<strong>列表</strong>
											</td>
											<td class="SeparatePage">
												<pages:pager action="listPrintJobDetailAction.do" />
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">
								<thead>
									<tr align="center" class="title-top">
										<td>
											箱码
										</td>
										<td>
											小包装码
										</td>
										<td>
											暗码
										</td>
									</tr>
								</thead>

								<tbody>
									<logic:iterate id="cc" name="primaryCodes">
										<tr align="center" class="table-back-colorbar"
											>
											<td>
												${cc.cartonCode}
											</td>
											<td>
												${cc.primaryCode}
											</td>
											<td>
												${cc.covertCode}
											</td>
										</tr>
									</logic:iterate>
								</tbody>
								<tfoot></tfoot>
							</table>
							
						</div>
					</div>

				</td>
			</tr>
		</table>
	</body>
</html>
