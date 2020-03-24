<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<html>
	<head>
		<title>详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/showCQ.js"> </SCRIPT>
		<script type="text/javascript">
		function releasecode(planid){
			popWin4("../erp/listProductPlanDetailAction.do?productPlanId="+planid+"&ID="+planid,1000,650,"code");
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
								详情
							</td>
						</tr>
					</table>

					<fieldset align="center">
						<legend>
							<table width="100" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										生产计划详情
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td width="6%" align="right">
									工厂：
								</td>
								<td width="14%">
								 <windrp:getname key='organ' value='${productPlanDetail.organId}' p='d' />
								</td>
								<td width="6%" align="right">
									产品：
								</td>
								<td width="14%">
									${product.productname}
								</td>
								<td width="6%" align="right">
									规格：
								</td>
								<td width="14%">
									${product.specmode}
								</td>
								<td width="6%" align="right">
									份数：
								</td>
								<td width="14%">
									${product.copys}
								</td>
							</tr>
							<tr>
								<td align="right" width="6%">
									托数：
								</td>
								<td  width="14%">
									${tnum}
								</td>
								<td align="right" width="6%">
									箱数：
								</td>
								<td  width="14%">
									${productPlanDetail.boxnum}
								</td>
								<td align="right" width="6%">
									标签总数：
								</td>
								<td  width="14%">
									${totalnum}
								</td>
								<td align="right" width="6%">
									总量：
								</td>
								<td>
									${zongliang}
								</td>
								<td align="right">
									
								</td>
								<td>
									
								</td>
							</tr>
							<tr>
								<td align="right" width="6%">
									条码：
								</td>
								<td><a href="javascript:releasecode('${productPlanDetail.id}')">
								<span><img src="../images/CN/code.gif"  border="0" title="释放条码"></span></a></td>
								<td align="right" width="10%">
									已释放箱数：
								</td>
								<td>
									${realnum}
								</td>
								<td align="right" width="10%">
									实际箱数：
								</td>
								<td>
									${sjnum}
								</td>
							
							</tr>
							<tr>
								<td align="right" width="6%">
									暗码起始号：
								</td>
								<td>
									${productPlanDetail.codeFrom }
								</td>
								<td align="right" width="10%">
									暗码截至号：
								</td>
								<td>
									${productPlanDetail.codeTo }
								</td>
								<td align="right" width="10%">
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
								<td width="6%" align="right">
									产品ID：
								</td>
								<td width="14%">
									${product.id}
								</td>							
								<td width="6%" align="right">
									物料号：
								</td>
								<td width="14%">
									${product.mCode}
								</td>
								<td width="6%" align="right">
									物料批次：
								</td>
								<td width="14%">
									${productPlanDetail.mbatch}
								</td>
								<td width="6%" align="right">
									产品批次：
								</td>
								<td width="14%">
									${productPlanDetail.pbatch}
								</td>


							</tr>
							<tr>
								<td width="6%" align="right">
									PO编号：
								</td>
								<td width="14%">
									${productPlanDetail.PONO}
								</td>
								<td width="6%" align="right">
									生产日期：
								</td>
								<td width="14%">
									<windrp:dateformat value="${productPlanDetail.proDate}" p="yyyy-MM-dd" />
								</td>
								<td width="6%" align="right">
									分装日期：
								</td>
								<td width="14%">
									<windrp:dateformat value="${productPlanDetail.packDate}" p="yyyy-MM-dd" />
								</td>
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
										打印任务状态
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td width="8%" align="right">
									生成状态：
								</td>
								<td width="24%">
									${productPlanDetail.temp}
								</td>							
							</tr>
							<tr>
								<td width="8%" align="right">
									提示信息：
								</td>
								<td width="90%">
									${productPlanDetail.msg}
								</td>

								<td align="right">
									
								</td>
								<td>
								</td>

							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</body>
</html>
