<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/sorttable.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
		<script type="text/javascript">
		function CheckedObj(obj){
	
			 for(i=0; i<obj.parentNode.childNodes.length; i++)
			 {
				   obj.parentNode.childNodes[i].className="table-back-colorbar";
			 }
			 
			 obj.className="event";
			}
		</script>
	<body >
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td>
								零售预测详情
							</td>
						</tr>
					</table>

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
								<td width="100" align="right">
									<input name="pid" type="hidden" id="pid" value="${pbf.pid}">
									客户类型：
								</td>
								<td>
									<windrp:getname key="ObjSort" p="f"
										value="${saleForecast.objsort}" />
								</td>
								<td align="right">
									客户名称：
								</td>
								<td>
									${saleForecast.cname}
								</td>
								<td>

								</td>
								<td>

								</td>
							</tr>
							<tr>
								<td align="right">
									预测开始时间：
								</td>
								<td>
									<windrp:dateformat value='${saleForecast.forestartdate}'
										p='yyyy-MM-dd' />
								</td>
								<td align="right">
									预测结束时间：
								</td>
								<td>
									<windrp:dateformat value='${saleForecast.foreenddate}'
										p='yyyy-MM-dd' />
								</td>
								<td align="right">
									总金额：
								</td>
								<td>
									<windrp:format value='${saleForecast.totalsum}' />

								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0"
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
								<td width="100" align="right">
									制单机构：
								</td>
								<td>
									<windrp:getname key='organ' value='${saleForecast.makeorganid}'
										p='d' />

								</td>
								<td align="right">
									制单部门：
								</td>
								<td>
									<windrp:getname key='dept' value='${saleForecast.makedeptid}'
										p='d' />
								</td>
							</tr>
							<tr>
								<td width="100" align="right">
									制单人：
								</td>
								<td>
									<windrp:getname key='users' value='${saleForecast.makeid}' p='d' />
								</td>
								<td align="right">
									制单日期：
								</td>
								<td>
									<windrp:dateformat value='${saleForecast.makedate}' />
								</td>

							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="90" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										采购单产品列表
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
							<tr align="center" class="title-top">
								<td width="12%">
									产品编号
								</td>
								<td width="18%">
									产品名称
								</td>
								<td width="29%">
									规格
								</td>
                                <td width="8%">
									单位
								</td>
								<td width="11%">
									数量
								</td>
                                <td width="11%">
									单价
								</td>
								<td width="11%">
									金额
								</td>
							</tr>
							<logic:iterate id="p" name="list">
								<tr class="table-back-colorbar" onClick="CheckedObj(this);">
									<td>
										${p.productid}
									</td>
									<td>
										${p.productname}
									</td>
									<td>
										${p.specmode}
									</td>
                                    <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/>
									</td>
									<td>
										${p.quantity}
									</td>
                                    <td><windrp:format value='${p.unitprice}'/>
										
									</td>
									<td>
										<fmt:formatNumber value='${p.subsum}' pattern='0.00' />
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
