<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>

	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
			function Deal(sid) {
				popWin("toDealSuitAction.do?SID=" + sid,600,350);
			}
		
			function CancelDeal(sid) {
				popWin("cancelDealSuitAction.do?SID=" + sid,500,250);
			}
		</script>
	</head>
	<html:errors />
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<table width="100%" height="40" border="0" cellpadding="0"
									cellspacing="0" class="title-back">
									<tr>
										<td width="15">
											<img src="../images/CN/spc.gif" width="10" height="8">
										</td>
										<td>
											抱怨投诉详情
										</td>
										<td width="60" align="center">
											<c:choose>
												<c:when test="${pf.isdeal==0}">
													<a href="javascript:Deal('${pf.id}');">处理</a>
												</c:when>
												<c:otherwise>
													<a href="javascript:CancelDeal('${pf.id}')">取消处理</a>
												</c:otherwise>
											</c:choose>
										</td>

									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset align="center">
									<legend>
										<table width="50" border="0" cellpadding="0" cellspacing="0"
											class="table-detail">
											<tr>
												<td>
													基本信息
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
										<tr>
											<td width="12%"  align="right">
												投诉种类：
											</td>
											<td width="20%">
												<windrp:getname key='SuitContent' value='${pf.suitcontent}'
													p='d' />
											</td>
											<td width="15%" align="right">
												投诉方式：
											</td>
											<td width="15%">
												<windrp:getname key='SuitWay' value='${pf.suitway}' p='d' />
											</td>
											<td width="15%" align="right">
												投诉时间：
											</td>
											<td >
												<windrp:dateformat value='${pf.suitdate}'
													p='yyyy-MM-dd' />
											</td>
										</tr>
										<tr>
											<td align="right">
												客户名称：
											</td>
											<td >
												${pf.cname}
											</td>
											<td align="right">
												投诉人联系方式：
											</td>
											<td colspan="3">
												${pf.suittools}
											</td>
										</tr>
										<tr>
											<td align="right">
												投诉内容：
											</td>
											<td colspan="5" style="width: 600px; word-wrap: break-word">
												${pf.suitstatus}
											</td>
										</tr>
										<tr>
											<td align="right">
												备注：
											</td>
											<td colspan="5" >
												${pf.memo}
											</td>
										</tr>
									</table>
								</fieldset>

								<fieldset align="center">
									<legend>
										<table  border="0" cellpadding="0" cellspacing="0"
											class="table-detail">
											<tr>
												<td>
													状态信息
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
										<tr>
											<td align="right" width="12%">
												制单机构：
											</td>
											<td width="20%">
												<windrp:getname key='organ' value='${pf.makeorganid}' p='d' />
											</td>
											<td align="right" width="15%">
												制单部门：
											</td>
											<td width="15%">
												<windrp:getname key='dept' value='${pf.makedeptid}' p='d' />
										  </td>
											<td width="15%" align="right" >
												制单人：	</td>
											<td >
												<windrp:getname key='users' value='${pf.makeid}' p='d' />
										  </td>
										</tr>
										<tr>
											<td align="right">
												制单时间：
											</td>
											<td>
												<windrp:dateformat value='${pf.makedate}' p='yyyy-MM-dd' />
											</td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>

									</table>
								</fieldset>

								<fieldset align="center">
									<legend>
										<table width="50" border="0" cellpadding="0" cellspacing="0"
											class="table-detail">
											<tr>
												<td>
													处理信息
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
										<tr>

											<td width="12%" align="right">
												是否处理：											</td>
											<td width="20%">
												<windrp:getname key='YesOrNo' value='${pf.isdeal}' p='f' />										  </td>
											<td width="15%" align="right">
												处理方式：											</td>
											<td width="15%">
												<windrp:getname key='DealWay' value='${pf.dealway}' p='d' />										  </td>
											<td width="15%" align="right" >处理人：</td>
											<td  ><windrp:getname key='users' value='${pf.dealuser}' p='d' /></td>
										</tr>
										<tr>
											<td align="right">处理日期：</td>
											<td>
															<windrp:dateformat value='${pf.dealdate}' p='yyyy-MM-dd' />								</td>
											<td align="right">
																							</td>
											<td>
																							</td>
											<td align="right">&nbsp;											</td>
											<td>&nbsp;											</td>
										</tr>
										<tr>
											<td align="right">
												处理内容：											</td>
											<td colspan="5" style="width: 600px; word-wrap: break-word">
												${pf.dealcontent}											</td>
										</tr>
										<tr>
											<td align="right">
												处理结果：											</td>
											<td colspan="5" style="width: 600px; word-wrap: break-word">
												${pf.dealfinal}											</td>
										</tr>
									</table>
								</fieldset>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>
