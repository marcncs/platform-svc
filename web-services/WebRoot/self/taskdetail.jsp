<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT LANGUAGE=javascript src="../js/function.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
			function isOverStatus(){
				popWin2("../self/overTaskAction.do?ID="+${tp.id});
			}
		</script>
	</head>

	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td>
								任务详情
							</td>
							<td align="right" style="padding-right: 20px;">
								<c:if test="${isover ==0}">
									<a href="javascript:isOverStatus();">完成</a>
								</c:if>
								<c:if test="${isover ==1}">
									<a href="javascript:isOverStatus();">取消完成</a>
								</c:if>
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>

						</tr>
					</table>
					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0" >
								<tr>
									<td>
										基本信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
							<tr>
								<td width="100" align="right" style="width: 10%;">
									任务计划标题:
								</td>
								<td width="198">
									${tp.tptitle}
								</td>
								<td align="right" width="75">
									制单日期:
								</td>
								<td width="113">
									<windrp:dateformat value='${tp.makedate}' p='yyyy-MM-dd' />
								</td>
								<td  align="right">
									结束时间:
								</td>
								<td width="131">
									<windrp:dateformat value='${tp.conclusiondate}' p='yyyy-MM-dd' />
									${tp.endtime}
								</td>
							</tr>
							<!--<tr>
								<td align="right">
									对象类型:
								</td>
								<td>
									<windrp:getname key="ObjSort" value="${tp.objsort}" p="f" />
								</td>
								<td align="right">
									对象名称:
								</td>
								<td>
									${tp.cname}
								</td>
								<td align="right">
									个人是否完成：
								</td>
								<td>
									<windrp:getname key="IsOver" value="${isover}" p="f" />
								</td>
							</tr>-->
                            <tr>
								<td align="right">个人是否完成:</td>
								<td><windrp:getname key="IsOver" value="${isover}" p="f" /></td>
								<td align="right">&nbsp;</td>
								<td>&nbsp;</td>
								<td align="right">&nbsp;</td>
								<td>
									
								</td>
							</tr>-
							<tr>
								<td style="width: 100px;" align="right">
									优先级:
								</td>
								<td>
									<windrp:getname key='Priority' value='${tp.priority}' p='d' />
								</td>

								<td align="right">
									类型:
								</td>
								<td>
									<windrp:getname key='TaskSort' value='${tp.tasksort}' p='d' />
								</td>
								<td align="right">
									状态:
								</td>
								<td>
									<windrp:getname key='TaskPlanStatus' value='${tp.status}' p='f' />
								</td>
							</tr>

							<tr>
								<td style="width: 100px;" align="right">
									制单机构:
								</td>
								<td>
									<windrp:getname key='organ' value='${tp.makeorganid}' p='d' />
								</td>
								<td align="right">
									制单部门:
								</td>
								<td>
									<windrp:getname key='dept' value='${tp.makedeptid}' p='d' />
								</td>
								<td align="right">
									制单人:
								</td>
								<td>
									<windrp:getname key='users' value='${tp.makeid}' p='d' />
								</td>
							</tr>
							<tr>

								<td style="width: 100px;" align="right">
									是否分配:
								</td>
								<td>
									<windrp:getname key='IsAllot' value='${tp.isallot}' p='f' />
								</td>
								<td align="right">
									完成状况:
								</td>
								<td>
									<windrp:getname key='OverStatus' value='${tp.overstatus}' p='d' />
								</td>
								<td align="right">
									完成日期:
								</td>
								<td>
									<windrp:dateformat value="${tp.overdate}" p="yyyy-MM-dd" />
								</td>
							</tr>
							<tr>
								<td  style="width: 100px;" align="right">
									任务计划内容:
								</td>
								<td colspan="5">
										${content }
								</td>
							</tr>
						</table>


					</fieldset>

				</td>
			</tr>
		</table>
	</body>
</html>
