<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html style="">
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery-1.4.2.min.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery.jPrintArea.js"> </SCRIPT>
		<link href="../css/pss.css" rel="stylesheet" type="text/css">
<!--		<style media=print> -->
<!--		.Noprint{display:none;} -->
<!--		</style> -->

		</head>
		
		<body style="overflow: auto;">
		<center class="Noprint" > 
		<div class="printstyle">
		<img src="../images/print.gif" onClick="javascript:print();" id="biuuu_button"></img>
		</div><hr align="center" width="100%" size="1" noshade> 
		</center> 
		<div id="myPrintArea" style="width: 90mm; height:45mm; float: left;">
		<c:if test="${type==3}">
			<div style="width: 90mm;height:45mm;">
				
				<div align="center" style="font-size: 20px;" style="width: 90mm;">
					装箱单				
				</div>
				<br/>
				<table cellspacing="0" cellpadding="0" style="width: 90mm;" >
					<tr>
						<td style="font-size: 16px;">MARUBI</td>
						<td style="font-size: 16px;">丸美</td>
					</tr>
					<tr>
						<td style="font-size: 16px;">客户：</td>
						<td style="font-size: 16px;">${tb.oname}</td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" border="1px" style="border-style: solid;width:90mm;" >
					<tr style="">
						<td align="center" style="width:22.5mm;">单号</td>
						<td align="center" style="width:67.5mm;" colspan="2">
							&nbsp;
							<c:forEach items="${siids}" var="siid">
								<span>${siid.siid}</span><br/>
							</c:forEach>
						</td>
						<td align="center" style="width:22.5mm;">NO:<fmt:formatNumber value="${index}" pattern="0000"/>-${count}</td>
					</tr>
					<tr align="center">
						<td>商品编码</td>
						<td>商品全名</td>
						<td>数量</td>
						<td>备注</td>
					</tr>
					
					<c:forEach items="${alpi}" var="s">
						<tr align="center" style="">
							<td>${s.productid}</td>
							<td>${s.productname}</td>
							<td>${s.quantity}</td>
							<td>&nbsp;</td>				
						</tr>
					</c:forEach>
				</table>
				
				<div style=" width: 90mm;" >
					<br/>
					<span style="float: right; margin-right: 80px;">验货员：</span>
				</div>
			</div>
		
		</c:if>
		<c:if test="${type==2}">
		<c:forEach begin="1" end="${count}" step="1" varStatus="status">
			<div style="width: 90mm;height:45mm;">
				
				<div align="center" style="font-size: 9pt;" style="width: 90mm;">
					装箱单				
				</div>
				<br/>
				<table cellspacing="0" cellpadding="0" style="width: 90mm;"  >
					<tr>
						<td style="font-size: 9pt;">MARUBI</td>
						<td style="font-size: 9pt;">丸美</td>
					</tr>
					<tr>
						<td style="font-size: 9pt;">客户：</td>
						<td style="font-size: 9pt;">${tb.oname}</td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" style="width: 90mm;"  border="1px" style="border-style: solid;" >
					<tr style="">
						<td align="center" style="width:22.5mm;" >单号</td>
						<td align="center" width="width:67.5mm;" colspan="2">
							<c:forEach items="${siids}" var="siid">
								<span>${siid.siid}</span><br/>&nbsp;
							</c:forEach>
							&nbsp;
						</td>
						<td align="center" style="width:22.5mm;">NO:<fmt:formatNumber value="${status.index}" pattern="0000"/>-${count}</td>
					</tr>
					<tr align="center">
						<td>商品编码</td>
						<td>商品全名</td>
						<td>数量</td>
						<td>备注</td>
					</tr>
					
					<c:forEach items="${alpi}" var="s">
						<tr align="center" style="">
							<td>${s.productid}</td>
							<td>${s.productname}</td>
							<td>${s.quantity}</td>
							<td>&nbsp;</td>				
						</tr>
					</c:forEach>
				</table>
				
				<div style=" width: 90mm ;" >
					<br/>
					<span style="float: right; margin-right: 50mm;">验货员：</span>
				</div>
			</div>
			<br/>
			<br/>
			<br/>
			</c:forEach>
		</c:if>
		</div>
	</body>
	
<script type="text/javascript">
	function print(){
		$("#myPrintArea").printArea({mode: "popup",popHt:"90mm",popWd:"50mm"}); 
	}
</script>
</html>
