<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>打印历史</title>
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
										<td width="8%">
											打印日期
										</td>
										<td width="8%">
											打印用户
										</td>
										<td width="10%">
											打印内容
										</td>
										<td width="10%">
											重打原因
										</td>
										<td width="10%">
											是否重打
										</td>
										<td width="10%">
											打印语言
										</td>
									</tr>
								</thead>

								<tbody>
									<logic:iterate id="pl" name="printLogs">
										<tr align="center" class="table-back-colorbar"
											>
											<td>
												<windrp:dateformat value="${pl.printDate}" p="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td>
												<windrp:getname key='users' value='${pl.printUser}' p='d'/>
											</td>
											<td>
												${pl.printContent}
											</td>
											<td>
												${pl.printReason}
											</td>
											<td>
												<windrp:getname key='YesOrNo' value='${pl.printFlag}' p='f'/>
											</td>
											<td>
												${pl.printlang}
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
