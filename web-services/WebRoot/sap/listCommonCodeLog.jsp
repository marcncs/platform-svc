<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>打印历史</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/showPE.js"> </SCRIPT>
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
												<pages:pager action="../sap/listCommonCodeListAction.do" />
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
											编号
										</td>
										<td width="8%">
											生成数量
										</td>
										<td width="10%">
											创建日期
										</td>
										<td width="10%">
											创建人
										</td>
										<td width="10%">
											通用码
										</td>
										<td width="10%">
											同步状态
										</td>
									</tr>
								</thead>

								<tbody>
									<logic:iterate id="ccl" name="commonCodeLogs">
										<tr align="center" class="table-back-colorbar"
											>
											<td>
												${ccl.id}
											</td>
											<td>
												${ccl.count}
											</td>
											<td>
												${ccl.makeDate}
											</td>
											<td>
												<windrp:getname key='users' value='${ccl.makeId}' p='d'/>
											</td>
											<td>
												<c:choose>
				   									<c:when test="${ccl.status == 2}"><a href="../common/downloadSapfile.jsp?filename=${ccl.filePath}">[下载] </a></c:when>
				  							 		<c:when test="${ccl.status == 3}"><a href="#" onMouseOver="ShowPE(this,'${ccl.errorMsg}');" onMouseOut="HiddenPE();">[生成出错] </a></c:when>
				  							 		<c:otherwise><windrp:getname key='CommonCodeStatus' value='${ccl.status}' p='f'/></c:otherwise>
												</c:choose>		
											</td>
											<td><windrp:getname key='SyncStatus' value='${ccl.syncStatus}' p='f'/></td>
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
