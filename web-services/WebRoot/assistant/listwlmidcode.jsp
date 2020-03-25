<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>物流全链查询</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/prototype.js">
	
</SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js">
	
</SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js">
	
</SCRIPT>
		<script type="text/javascript">
	var checkid = "";
	function CheckedObj(obj, objid) {
		for (i = 0; i < obj.parentNode.childNodes.length; i++) {
			obj.parentNode.childNodes[i].className = "table-back-colorbar";
		}

		obj.className = "event";
		checkid = objid;
	}

	function Detail() {
		setCookie("oCookie", "1");
		if (checkid != "") {
			document.all.basic.src = "../assistant/wlmIdcodeDetailAction.do?ID="
					+ checkid;

		} else {
			alert("请选择你要操作的记录!");
		}
	}
	function Track() {
		setCookie("oCookie", "2");
		if (checkid != "") {
			document.all.basic.src = "../assistant/trackDetailAction.do?OID="
					+ checkid;

		} else {
			alert("请选择你要操作的记录!");
		}
	}
	function PostAll(objidcode) {
		window
				.open(
						"../assistant/toAddTrackApplyAction.do?idCode="
								+ objidcode,
						"",
						"height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									${menusTrace }
								</td>

							</tr>
						</table>
						<form name="search" method="post"
							action="../assistant/viewWlmIdcodeAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td align="right" width="80px">
										查询码：
									</td>
									<td width="330px">
										<input type="text" name="cartonCodeSearch" size="58"
											value="${cartonCodeSearch}" maxlength="58" />
									</td>
									<td class="SeparatePage1">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<form method="post" action="" name="postAll">
							<table>
								<tr>
									<td>
										<c:if test="${noRight=='noRight'}">
											<windrp:getname key="PromptInformation" p="d" value="1" />
											<br />
											
										<br />
											<input type="hidden" id="idCode" name="idCode"
												value="${cartonCodeSearch}" />
											<input type="button" id="submitappfr" name="submitappfr" value="提交申请"
												onclick="PostAll('${cartonCodeSearch}');" />
										</c:if>
										<c:if test="${prompt=='nocode'}">
											<br />
										暂未获取到查询码${cartonCodeSearch}相关的产品信息！
										<br />
										</c:if>
										<c:if test="${prompt=='rightcode'}">
											<br />
										请输入正确的查询码！！！
									<br />
										</c:if>
										<c:if test="${prompt=='code'}">
											<br />
										请输入查询码！！！
									<br />
										</c:if>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">
								<c:if test="${prompt=='r'}">
									<tr align="center" class="title-top">
										<td width="15%">
											箱码
										</td>
										<c:if test="${viewPc == 'viewTrue'}">
											<td width="10%">
												小包装码
											</td>

											<td width="10%">
												暗码
											</td>
										</c:if>
										<td width="20%">
											外部pin码
										</td>
										<td width="16%">
											内部pin码
										</td>
									</tr>
									<tr align="left" class="table-back-colorbar">
										<td width="15%">
											${existString}
										</td>
										<c:if test="${viewPc == 'viewTrue'}">
											<td width="10%">
												${pc.primaryCode}
											</td>

											<td width="10%">
												${pc.covertCode}
											</td>
										</c:if>
										<td width="20%">
											${cc.outPinCode}
										</td>
										<td width="16%">
											${cc.innerPinCode}
										</td>

									</tr>

								</c:if>

							</table>
						</form>
						<br>
						<c:if test="${prompt == 'r'}">
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="title-back">
								<tr>
									<td width="10">
										<img src="../images/CN/spc.gif" width="10" height="1">
									</td>
									<td width="772">
										产品基本信息
									</td>
								</tr>
							</table>
							<fieldset align="center">
								<legend>
									<table width="50" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td>
												产品基本信息
											</td>
										</tr>
									</table>
								</legend>
								<table width="100%" border="0" cellpadding="0" cellspacing="1"
									class="table-detail">
									<tr>
										<td width="12%" align="right">
											物料号:
										</td>
										<td width="13%">
										<c:if test="${pg.printJobId != -1}"> 
											${p.mCode}
										</c:if>
										</td>
										<td width="12%" align="right">
											产品名:
										</td>

										<td width="13%">
										<c:if test="${pg.printJobId != -1}"> 
											${p.productname}
										</c:if>
										</td>

										<!--<td width="12%" align="right">
											小包装码：
										</td>
										<td width="13%">
											${pc.primaryCode}
										</td>
										<td width="12%" align="right">
											暗码：
										</td>
										<td width="13%">
											${pc.covertCode}
										</td>
										<td width="12%" align="right">
											箱码：
										</td>
										<td width="16%">
											${pc.cartonCode}
										</td>
										-->
										<!--<td width="12%" align="right">
											包装大小：
										</td>
										<td width="10%">
											${pg.packSize}
										</td>
									-->
										<td width="12%" align="right">
											工厂名称：
										</td>
										<td width="13%">
										<c:if test="${pg.printJobId != -1}"> 
											${pg.plantName}
										</c:if>
											<br>
										</td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td width="12%" align="right">
											产品规格：
										</td>
										<td width="13%">
										<c:if test="${pg.printJobId != -1}"> 
											${p.specmode}
										</c:if>
										</td>
										<td width="12%" align="right">
											批号：
										</td>
										<td width="13%">
										<c:if test="${pg.printJobId != -1}"> 
											${pg.batchNumber}
										</c:if>
										</td>
										<td width="12%" align="right">
											生产日期：
										</td>
										<td width="13%">
										<c:if test="${pg.printJobId != -1}"> 
											<windrp:dateformat value="${pg.productionDate}" p="yyyy-MM-dd" />
										</c:if>
										</td>
										<td width="12%" align="right">
											到期日期：
										</td>
										<td width="13%">
										<c:if test="${pg.printJobId != -1}"> 
											<windrp:dateformat value="${pg.expiryDate}" p="yyyy-MM-dd" />
										</c:if>

										</td>
									</tr>
									<tr>

									</tr>
								</table>
							</fieldset>
							</c:if>
							<c:if test="${wlmessage == 'logistics'}">
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								bordercolor="#BFC0C1">
								<tr>
									<td>
										<div id="bodydiv">
											<table width="100%" border="0" cellpadding="0" cellspacing="0"
												class="title-back">
												<tr>
													<td width="10">
														<img src="../images/CN/spc.gif" width="10" height="1">
													</td>
													<td width="772">
														产品追踪
													</td>

												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div id="listdiv" style="overflow-y: auto; height: 600px;">
											<FORM METHOD="POST" name="listform" ACTION="">
												<table class="sortable" width="100%" border="0" cellpadding="0"
													cellspacing="1">
													<tr align="center" class="title-top">

														<td width="10%">
															发货仓库
														</td>
														<!--<td width="8%">
															发货机构代码
														</td>
														-->
														<td width="8%">
															发货机构
														</td>
														<td width="10%">
															发货日期
														</td>

														<td width="10%">
															收货仓库
														</td>
														<!--<td width="8%">
															收货机构代码
														</td>
														-->
														<td width="9%">
															收货机构
														</td>
														<td width="10%">
															收货日期
														</td>
														<td width="6%">
															单据类型
														</td>
														<td width="10%">
															单据号
														</td>
														<td width="9%">
															SAP发货单号
														</td>
													</tr>
													<logic:iterate id="tt" name="lttall">
														<tr align="left" class="table-back-colorbar"
															onClick="CheckedObj(this,'${tt.id}');">


															<td>
																<windrp:getname key="warehouse" value="${tt.warehouseid}" p="d" />
															</td>
															<!--<td>
																${tt.oid}
															</td>
															-->
															<td>
																${tt.oname}
															</td>
															<td>
																${tt.auditdate}
															</td>

															<td>
																<windrp:getname key="warehouse" value="${tt.inwarehouseid}"
																	p="d" />
															</td>
															<!--<td>
																${tt.inOid}
															</td>
															-->
															<td>
																${tt.inOname}
															</td>
															<td>
																${tt.pickedDate}
															</td>
															<td>
																<windrp:getname key='BSort' value="${tt.bsort }" p='f' />
															</td>
															<td>
																${tt.billno}
															</td>
															<td>
																${tt.nccode}
															</td>
														</tr>
													</logic:iterate>
												</table>
											</form>
											<br>
										</div>
									</td>
								</tr>
							</table>
						</c:if>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>

