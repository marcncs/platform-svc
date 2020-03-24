<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>	
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript" src="../js/prototype.js"></script>
		<script src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/jquery-1.4.2.min.js"> </SCRIPT>
		<style type="text/css"></style>
	</head>
	<script language="javascript">
	//jQuery解除与其它js库的冲突
	var $j = jQuery.noConflict(true);
function formcheck(){
	var checkOrganId ="";
	$j("input[name='che']").each(function() {
		 if ($j(this).is(':checked')) {
			 checkOrganId += $j(this).val()+",";
		}
	});
	if(checkOrganId==""){
		alert("请选择需要添加的记录!");
		return false;
	}else{
		$j("#areaIds").val(checkOrganId);
	}
	showloading();
	return true;
}

function submitCheckAll(){
	$j("input[name='che']").each(function() {
		$j(this).attr("checked",true);
	});
	$j("#addAll").val("true");
	showloading();
	$j("#addForm").submit();
}

function Check() {
	var pid = document.all("che");
	var checkall = document.all("checkall");
	if (pid == undefined) {
		return;
	}
	if (pid.length) {
		for (i = 0; i < pid.length; i++) {
			pid[i].checked = checkall.checked;
		}
	} else {
		pid.checked = checkall.checked;
	}
}

var cobj ="";
function getResult(getobj,toobj){
	//if(getobj=='province'){
	//	buildSelect("",document.getElementById("areas"));
	//}
	cobj = toobj;
	var areaID = $F(getobj);
	var url = '../sales/listAreasAction.do';
	var pars = 'parentid=' + areaID;
   var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showResponse}
				);
}

function showResponse(originalRequest){
	var city = originalRequest.responseXML.getElementsByTagName("area"); 
	var strid = new Array();
	var str = new Array();
	for(var i=0;i<city.length;i++){
		var e = city[i];
		str[i] =new Array(e.getElementsByTagName("areaid")[0].firstChild.data , e.getElementsByTagName("areaname")[0].firstChild.data);
	}
	buildSelect(str,document.getElementById(cobj));//赋值给city选择框
}

function buildSelect(str,sel) {//赋值给选择框
	sel.options.length=0;
	sel.options[0]=new Option("-选择-","")
	for(var i=0;i<str.length;i++) {
		sel.options[sel.options.length]=new Option(str[i][1],str[i][0])
	}
}

</script>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								选择机构
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						bordercolor="#BFC0C1">
						<tr>
							<td>
								<div id="bodydiv">
									<form name="search" method="post"
										action="../keyretailer/toAddSalesAreaCountryAction.do">
										<input type="hidden" name="pid" value="${pid}">
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr class="table-back">
												<td align="right">
												省份：
												</td>
												<td>
													<select name="province"
														id="province" onChange="getResult('province','city');">
														<option value="">
															-省份-
														</option>
														<logic:iterate id="c" name="cals">
														<option value="${c.id}" ${c.id==province?"selected":""}>${c.areaname}</option>
														</logic:iterate>
													</select>
												</td>
												<td align="right">
													城市：
												</td>
												<td>
													<select id="city" name="city">
														<option value="">
															-选择-
														</option>
														<logic:iterate id="c" name="citys">
														<option value="${c.id}" ${c.id==city?"selected":""}>${c.areaname}</option>
														</logic:iterate>
													</select>
												</td>
<%--												
												<td>
													地区：
												</td>
												<td>
													<select id="areas" name="Areas" id="areas">
														<option value="">
															-选择-
														</option>
														<logic:iterate id="c" name="Areass">
														<option value="${c.id}" ${c.id==Areas?"selected":""}>${c.areaname}</option>
														</logic:iterate>
													</select>
												</td>--%>
												<td align="right">
													关键字：
												</td>
												<td>
													<input type="text" name="KeyWord" value="${KeyWord}">
												</td>
												<td class="SeparatePage">
													<input name="Submit" type="image" id="Submit"
														src="../images/CN/search.gif" border="0" title="查询">
												</td>
											</tr>
										</table>
									</form>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr class="title-func-back">
											<td style="text-align: right">
												<pages:pager action="../keyretailer/toAddSalesAreaCountryAction.do" />
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div id="listdiv" style="overflow-y: auto; height: 300px;">
									<FORM METHOD="POST" name="listform" ACTION="">
										<table class="sortable" width="100%" border="0"
											cellpadding="0" cellspacing="1">

											<tr align="center" class="title-top-lock">
												<td width="4%" class="sorttable_nosort">
													<input type="checkbox" name="checkall" onClick="Check();">
												</td>
												<td>
													省
												</td>
												<td>
													市
												</td>
												<td>
													区/县
												</td>
											</tr>
											<logic:iterate id="p" name="areaList">
												<tr align="center" class="table-back-colorbar">
													<td>
														<input type="checkbox" name="che" id="che" value="${p.id}"
															onClick="changeValue(this)">
													</td>
													<td>
														${p.province}
													</td>
													<td>
														${p.city}
													</td>
													<td>
														${p.area}
													</td>
												</tr>
											</logic:iterate>
										</table>
									</form>
								</div>
							</td>
						</tr>
					</table>

					<form action="../keyretailer/addSalesAreaCountryAction.do"
						method="post" id="addForm" name="addForm"
						onSubmit="return formcheck();">
						<input type="hidden" id="areaIds" name="areaIds">
						<input type="hidden" name="pid" value="${pid}">
						<input type="hidden" name="KeyWord" value="${KeyWord}">
						<input type="hidden" name="province" value="${province}">
						<input type="hidden" name="city" value="${city}">
						<input type="hidden" id="addAll" name="addAll">
						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td>
									<div align="center">
										<input type="submit" name="Submit" value="确定">
										&nbsp;&nbsp;
										<input type="button" name="cancel" value="取消"
											onClick="window.close();">
										<input type="hidden" name="speedstr">
										&nbsp;&nbsp;
										<input type="button" name="button1" value="全部选中"
											onclick="submitCheckAll()">
										&nbsp;&nbsp;
									</div>
								</td>
							</tr>

						</table>
					</form>
				</td>
			</tr>
		</table>

	</body>
</html>
