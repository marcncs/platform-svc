<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<link href="../css/pss.css" rel="stylesheet" type="text/css">
		<style media=print>
			.Noprint {
				display: none;
			}
		</style>
	</head>
	<body style="overflow: auto;">
		<center class="Noprint">
			<div class="printstyle">
				<img src="../images/print.gif" onClick="javascript:window.print();"></img>
			</div>
			<hr align="center" width="100%" size="1" noshade>
		</center>
		<div id="abc">
			<div align="center" class="print_title_style">
				积分对象
			</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr > 
            <td align="right">期间：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">对象类型：</td>
            <td ><windrp:getname key="ObjectSort" p="f" value="${ObjectSort}"/></td>
            <td align="right">选择对象：</td>
            <td colspan="2">${cname}</td>
          </tr>
          <tr >
            <td  align="right">所属机构：</td>
            <td>${oname}</td>
            <td align="right">关键字：</td>
            <td>${KeyWord}</td>
            <td align="right">&nbsp;</td>
            <td >&nbsp;</td>
            <td align="right"></td>
          </tr>
				<tr>
					<td width="13%" align="right">
						打印机构：
					</td>
					<td width="21%">
						${porganname}
					</td>
					<td width="10%" align="right">
						打印人：
					</td>
					<td width="23%">
						${pusername}
					</td>
					<td width="14%" align="right">
						打印时间：
					</td>
					<td width="19%">
						${ptime}
					</td>
				</tr>
			</table>
			<FORM METHOD="POST" name="listform" ACTION="">
			<table width="100%" border="0" cellpadding="0" cellspacing="1"
				class="sortable">
				
				<tr align="center" class="title-top">
					<td width="8%">
						对象类型
					</td>
					<td width="9%">
						编号
					</td>
					<td width="18%">
						对象名称
					</td>
					<td width="11%">
						手机
					</td>
					<td width="11%">
						应收
					</td>
					<td width="8%">
						已收
					</td>
					<td width="8%">
						应付
					</td>
					<td width="8%">
						已付
					</td>
					<td width="8%">
						结余
					</td>
					<td width="10%">
						所属机构
					</td>
				</tr>
				<c:set var="count" value="0" />
				<logic:iterate id="s" name="alpl">
					<tr class="table-back-colorbar"
						onClick="CheckedObj(this,'${s.oid}','${s.osort}');">
						<td>
							<windrp:getname key='ObjectSort' value='${s.osort}' p='f' />
						</td>
						<td>
							${s.oid}
						</td>
						<td>
							${s.oname}
						</td>
						<td>
							${s.omobile}
						</td>
						<td>
							<fmt:formatNumber value="${s.rvincome}" pattern="0.00" />
						</td>
						<td>
							<fmt:formatNumber value="${s.alincome}" pattern="0.00" />
						</td>
						<td>
							<fmt:formatNumber value="${s.rvout}" pattern="0.00" />
						</td>
						<td>
							<fmt:formatNumber value="${s.alout}" pattern="0.00" />
						</td>
						<td>
							<fmt:formatNumber value="${s.balance}" pattern="0.00" />
						</td>
						<td>
							<windrp:getname key='organ' value='${s.organid}' p='d' />
						</td>
					</tr>
					<c:set var="count" value="${count+1}" />
				</logic:iterate>
				
			</table>
			</form>
		</div>

	</body>
</html>
