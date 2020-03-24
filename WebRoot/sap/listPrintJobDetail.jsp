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
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td>
								打印详情
							</td>
						</tr>
					</table>

					<fieldset align="center">
						<legend>
							<table width="100" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										打印任务详情
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td width="5%" align="right">
									工厂：
								</td>
								<td width="20%">
									${printJobDetail.plantName}
								</td>
								<td width="10%" align="right">
									产品：
								</td>
								<td width="25%">
									${printJobDetail.materialName}
								</td>
								<td width="7%" align="right">
									规格：
								</td>
								<td width="10%">
									${printJobDetail.packSize}
								</td>
								<td width="5%" align="right">
									份数：
								</td>
								<td width="15%">
									${copys}
								</td>
							</tr>
							<tr>
								<td align="right">
									箱数：
								</td>
								<td>
									${printJobDetail.numberOfCases}
								</td>
								<td align="right">
									标签总数：
								</td>
								<td>
									${printJobDetail.totalNumber}
								</td>
								<c:if test="${printJobDetail.labelType!=null}">
									<td align="right">
										标签类型：
									</td>
									<td>
										<windrp:getname key='labelType' value='${printJobDetail.labelType}' p='f'/>
									</td>
								</c:if>	
								<td align="right">
									
								</td>
								<td>
									
								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="100" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										其他详细信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td width="5%" align="right">
									批号：
								</td>
								<td width="20%">
									${printJobDetail.batchNumber}
								</td>
								<td width="10%" align="right">
									打印日期：
								</td>
								<td width="25%">
									<windrp:dateformat value="${printJobDetail.printingDate}" p="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td width="7%" align="right">
									生产日期：
								</td>
								<td width="10%">
									<windrp:dateformat value="${printJobDetail.productionDate}" p="yyyy-MM-dd" />
								</td>
								<td width="5%" align="right">
									物料：
								</td>
								<td width="15%">
									${printJobDetail.materialCode}
								</td>

							</tr>
							<tr>
								<td align="right">
									过期日：
								</td>
								<td>
									${printJobDetail.expiryDate}
								</td>
								<td align="right">
									生产订单号码：
								</td>
								<td>
									${printJobDetail.processOrderNumber}
								</td>
								<td align="right">
									分装日期：
								</td>
								<td>
									<windrp:dateformat value="${printJobDetail.packagingDate}" p="yyyy-MM-dd" />
								</td>
								<td align="right" width="10%">
									 分装厂批次：
								</td>
								<td>
									${printJobDetail.materialBatchNo}
								</td>

							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</body>
</html>
