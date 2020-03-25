<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp" %>
<html>

	<script src="../js/prototype.js"></script>
	<script language="JavaScript">
	var cobj = "";
	function getResult(getobj, toobj) {
		//alert(getobj);
		cobj = toobj;
		var areaID = $F(getobj);
		//var y = $F('lstYears');
		var url = 'listAreasAction.do';
		var pars = 'parentid=' + areaID;
		var myAjax = new Ajax.Request(url, {
			method :'get',
			parameters :pars,
			onComplete :showResponse
		});

	}

	function showResponse(originalRequest) {
		//put returned XML in the textarea
		var city = originalRequest.responseXML.getElementsByTagName("area");
		//alert(city.length);
		var strid = new Array();
		var str = new Array();
		for ( var i = 0; i < city.length; i++) {
			var e = city[i];
			str[i] = new Array(
					e.getElementsByTagName("areaid")[0].firstChild.data,
					e.getElementsByTagName("areaname")[0].firstChild.data);
			//alert(str);
		}
		buildSelect(str, document.getElementById(cobj));//赋值给city选择框
		//$('cc').value = originalRequest.responseXML.getElementsByTagName("area");
	}

	function buildSelect(str, sel) {//赋值给选择框
		//alert(str.length);

		sel.options.length = 0;
		sel.options[0] = new Option("选择", "")
		for ( var i = 0; i < str.length; i++) {
			//alert(str[i]);	
			sel.options[sel.options.length] = new Option(str[i][1], str[i][0])
		}
	}
</script>


	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/Cookie.js">
	
</SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">

		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0"
									class="title-back">
									<tr>
										<td width="10">
											<img src="../images/CN/spc.gif" width="10" height="1">
										</td>
										<td>
											会员详情
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<form name="validateCustomer" method="post"
									action="addMemberAction.do">

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
												<td width="15%" align="right">
													会员名称：
												</td>
												<td width="21%">
													${cf.cname}
												</td>
												<td width="10%" align="right">
													性别：
												</td>
												<td width="21%">
													${cf.membersexname}
												</td>
												<td width="10%" align="right">
													手机：
												</td>
												<td width="23%">
													${cf.mobile}
												</td>
											</tr>
											<tr>
												<td align="right">
													电话：
												</td>
												<td>
													${cf.officetel}
												</td>
												<td align="right">
													出生日期：
												</td>
												<td>
													${cf.memberbirthday}
												</td>
												<td align="right">
													证件号码：
												</td>
												<td>
													${cf.memberidcard}
												</td>
											</tr>
											<tr>
												<td align="right">
													卡号：
												</td>
												<td>
													${cf.cardno}
												</td>
												<td align="right">
													EMail：
												</td>
												<td>
													${cf.email}
												</td>
												<td align="right">&nbsp;</td>
												<td>&nbsp;</td>
											</tr>
										</table>
									</fieldset>


									<fieldset align="center">
										<legend>
											<table width="50" border="0" cellpadding="0" cellspacing="0"
												class="table-detail">
												<tr>
													<td>
														辅助信息
													</td>
												</tr>
											</table>
										</legend>
										<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
											<tr>
												<td width="15%" align="right">
													单位：
												</td>
												<td width="21%">
													${cf.membercompany}
												</td>
												<td width="9%" align="right">
													发票抬头：
												</td>
												<td width="25%">
													${cf.tickettitle}
												</td>
												<td width="9%" align="right">
													职业：
												</td>
												<td width="21%">
													${cf.memberduty}
												</td>
											</tr>
											<tr>
												<td align="right">
													会员级别：
												</td>
												<td>
													${cf.ratename}
												</td>
												<td align="right">
													信用额度：
												</td>
												<td>
													${cf.creditlock}
												</td>
												<td align="right">
													付款期限：
												</td>
												<td>
													${cf.prompt}
												</td>
											</tr>
											<tr>
												<td align="right">
													折扣率：
												</td>
												<td>
													${cf.discount}%
												</td>
												<td align="right">
													税率：
												</td>
												<td>
													${cf.taxrate}%
												</td>
												<td align="right">
													会员来源：
												</td>
												<td>
													${cf.sourcename}
												</td>
											</tr>
											<tr>
												<td align="right">
													推荐人：
												</td>
												<td>
													${cf.parentid}
												</td>
												<td align="right">&nbsp;
													
												</td>
												<td>&nbsp;
													
												</td>
												<td>&nbsp;
													
												</td>
												<td>&nbsp;
													
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
														联系资料
													</td>
												</tr>
											</table>
										</legend>
										<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
											<tr>
												<td width="15%" align="right">
													区域：
												</td>
												<td>
													${cf.provincename}-${cf.cityname}-${cf.areasname}
												</td>
												<td width="25%">&nbsp;
													
												</td>
												<td width="9%" align="right">
													邮编：
												</td>
												<td width="21%">
													${cf.postcode}
												</td>
											</tr>
											<tr>
												<td align="right">
													通讯地址：
												</td>
												<td colspan="4">
													${cf.commaddr}
												</td>
											</tr>
											<tr>
												<td align="right">
													送货地址：
												</td>
												<td colspan="4">
													${cf.detailaddr}
												</td>
											</tr>
											<tr>
												<td align="right">
													备注：
												</td>
												<td colspan="4">
													${cf.remark}
												</td>
											</tr>
										</table>
									</fieldset>

									<fieldset align="center">
										<legend>
											<table width="50" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														状态信息
													</td>
												</tr>
											</table>
										</legend>
										<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
											<tr>
												<td width="15%" align="right">
													制单人：
												</td>
												<td width="21%">
													${cf.makeidname}
												</td>
												<td width="10%" align="right">
													制单日期：
												</td>
												<td width="24%">
													${cf.makedate}
												</td>
												<td width="9%" align="right">
													是否激活：
												</td>
												<td width="21%">
													${cf.isactivatename}
												</td>
											</tr>
											<tr>
												<td align="right">
													激活人：
												</td>
												<td>
													${cf.activateidname}
												</td>
												<td align="right">
													激活日期：
												</td>
												<td>
													${cf.activatedate}
												</td>
												<td align="right">&nbsp;
													
												</td>
												<td>&nbsp;
													
												</td>
											</tr>
										</table>
									</fieldset>
								</form>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>
