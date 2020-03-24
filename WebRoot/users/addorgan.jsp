<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

<title>新增机构</title>
<script language="javascript" src="../js/prototype.js"></script>
<script language="javascript" src="../js/function.js"></script>
<script language="javascript" src="../js/selectDate.js"></script>
<script type="text/javascript" src="../js/validator.js"></script>
<script language="javascript" src="../js/jquery-1.11.1.min.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
//jQuery解除与其它js库的冲突
var $j = jQuery.noConflict(true);
var cobj ="";
function getResult(getobj,toobj){
	if(getobj=='province'){
		buildSelect("",document.getElementById("areas"));
	}
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

function tabit(id,cid) {
<%--	tab5d.className="tabon";--%>
<%--	tabcv.className="tabon";--%>
<%--	id.className="tabon";--%>
<%--	c5d.style.display="none";--%>
<%--	ccv.style.display="none";--%>
	c5d.style.display="block";
	if(${organType} == 2) {
		ccv.style.display="block";
		} else {
			ccv.style.display="none";
			}
	
}

function addtr(tableid){	 	
	 TR = document.getElementById(tableid).insertRow(-1);
	 TD1 = TR.insertCell(0);//加列	
	 TD1.innerHTML = "<fieldset align='center'><table width='100%'  border='0' cellpadding='0' cellspacing='1'><tr><input name='linkid' type='hidden' value=''><td width='10%' align='right'>联系人姓名：</td><td width='21%'><input name='name' type='text' maxlength='30'></td><td width='10%' align='right'>性别：</td><td width='23%'><select name='sex'><option value='1'>女</option><option value='2'>男</option></select></td><td align='right'>职务：</td><td><input type='text' name='duty' maxlength='30'></td><!--</tr><tr><td align='right'>生日：</td><td><input type='text' name='birthday' onFocus='javascript:selectDate(this)' readonly></td><td align='right'>MSN：</td><td><input type='text' name='msn' maxlength='20'></td><td align='right'>身份证号码：</td><td><input name='idcard' type='text' maxlength='24'></td></tr>--><tr><td align='right'>是否主联系人：</td><td><select name='ismain'><option value='0'>否</option><option value='1'>是</option></select></td><td width='9%' height='20' align='right'>办公电话：</td><td width='27%'><input name='linkofficetel' type='text' maxlength='20'></td><td width='10%' align='right'>手机：</td><td width='21%'><input type='text' name='linkmobile' maxlength='20'></td></tr><tr><td height='20' align='right'>Emai：</td><td><input type='text' name='linkemail' maxlength='20'></td><td align='right'>QQ：</td><td><input name='qq' type='text' id='qq' maxlength='20'></td><!--<td width='10%' align='right'>家庭电话：</td><td width='23%'><input name='hometel' type='text' maxlength='20'></td>--><td>&nbsp</td><td>&nbsp</td></tr><tr><td height='20' align='right'>地址：</td><td><input type='text' name='lkaddr' size='40' maxlength='40'></td><td align='right'></td><td></td></tr></table></fieldset>";
}

function deletetr(tableid){
	var celltable = document.getElementById(tableid);
	if ( celltable.rows.length > 1 ){
		document.getElementById(tableid).deleteRow(-1)
	}
}

function selectSaleMan(){
	var pid=new Array();
	var pname=new Array();
	var saleManId = document.getElementById('saleManId').value;
	var pArray=showModalDialog("../common/selectSaleManAction.do?saleManId="+saleManId,null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
    var p;
    if(pArray){
			for(var i=0;i<pArray.length;i++){
		         p=pArray[i];
		          if ( p==undefined){return;}
		          pid.push(p.uid);
		          pname.push(p.uname);
				}
	  }			
	  document.addRoleForm.saleManId.value=pid;
	  document.addRoleForm.saleManName.value=pname;
}

function selectRegionArea(){
	var saleManId = document.getElementById('saleManId').value;
	var RegionId=document.getElementById('RegionId').value;
	var p=showModalDialog("../common/selectRegionAreaAction.do?saleManId="+saleManId+"&RegionId="+RegionId,null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
    if ( p==undefined){return;}
 	document.addRoleForm.saleManId.value=p.uid;
 	document.addRoleForm.saleManName.value=p.uname;
 	document.addRoleForm.RegionId.value=p.uregion
}

function check(){
	// 基本信息验证
	if($F('organname').trim()==""){
		alert("机构名称不能为空!");
		return false;
	}
	if($F('oecode').trim()==""){
		alert("企业内部代码不能为空!");
		return false;
	}
	//验证机构类型
	if($j("#organType").val() == 1 && $j("#organModel1 #organModel option:selected").val() == ""){
		alert("请选择工厂类型");
		return false;
	}
	//验证机构类型
	if($j("#organType").val() == 2 && $j("#organModel2 #organModel option:selected").val() == ""){
		alert("请选择经销商类型");
		return false;
	}
	/*if ( $F('omobile')!=""){
		if ( !$F('omobile').isMobile() ){
			alert("手机格式不正确!");
			return false;
		}
	} else {
		alert("手机号不能为空!");
		return false;
	}*/
	if($F('otel')!= "" && !$F('otel').isTel1()){
		alert("电话格式不正确!");
		return false;
	}
	if($F('ofax')!= "" && !$F('ofax').isTel1() ){
		alert("传真格式不正确!");
		return false;
	}
	if($F('oemail')!= "" && !isEamil($F('oemail'))){
		alert("EMail格式不正确!");
		return false;
	}
	if($F('oaddr')== "" || $F('oaddr') == null){
		alert("机构地址不能为空!");
		return false;
	}
	if($F('province')== "" || $F('province') == null){
		alert("机构区域中省份不能为空!");
		return false;
	}
	if($F('city')== "" || $F('city') == null){
		alert("机构区域中市不能为空!");
		return false;
	}
	
	if($F('areas')== "" || $F('areas') == null){
		alert("机构区/县不能为空!");
		return false;
	}
	// 联系人验证
	var name = document.getElementsByName("name");
	//var idcard = document.getElementsByName("idcard");
	//var linkofficetel = document.getElementsByName("linkofficetel");
	var linkmobile = document.getElementsByName("linkmobile"); 
	//var hometel = document.getElementsByName("hometel");
	//var linkemail = document.getElementsByName("linkemail");
	
	if($j("#organType").val() == 2 ){
	for(var i = 0; i < name.length; i++){
		if(name[i].value.trim()==""){
			alert("联系人名称不能为空!");
			name[i].select();
			return false;
		}
	}
	for(var i = 0; i < linkmobile.length; i++){
		
		if(linkmobile[i].value.trim()==""  ){
			alert("联系人手机不能为空!");
			linkmobile[i].select();
			return false;
		}

		if(linkmobile[i].value.trim()!="" && !linkmobile[i].value.isMobile()){
			alert("联系人手机格式不正确!");
			linkmobile[i].select();
			return false;
		}
	}
	
	}
	
	/*for(var i = 0; i < idcard.length; i++){
		if(idcard[i].value.trim()!="" && !isIdCard(idcard[i].value)){
			alert("身份证号码格式不正确!");
			idcard[i].select();
			return false;
		}
	}
	for(var i = 0; i < linkofficetel.length; i++){
		if(linkofficetel[i].value.trim()!="" && !linkofficetel[i].value.isMobileOrTel()){
			alert("办公电话格式不正确!");
			linkofficetel[i].select();
			return false;
		}
	}
	
	for(var i = 0; i < hometel.length; i++){
		if(hometel[i].value.trim()!="" && !hometel[i].value.isMobileOrTel()){
			alert("家庭电话格式不正确!");
			hometel[i].select();
			return false;
		}
	}
	for(var i = 0; i < linkemail.length; i++){
		if(linkemail[i].value.trim()!="" && !isEamil(linkemail[i].value)){
			alert("Email格式不正确!");
			linkemail[i].select();
			return false;
		}
	}*/
	showloading();
	return true;
}
function SelectOrgan(){
	var c=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( c == undefined ){
		return;
	}	
	document.addRoleForm.parentid.value=c.id;
	document.addRoleForm.pname.value=c.organname;
}
// 机构类型change事件,根据机构类型显示不同的明细
function changeOrganType(){
	var organTypeVal = $j("#organType").val();
	if(organTypeVal == ""){
		// 请选择
		$j("#organModelTag").hide();
		$j("#organModel1").hide();
		$j("#organModel2").hide();
		$j("#tabcv").hide();
		$j(".baseProperty").each(function(i,v){$j(v).hide()});
		$j(".extProperty").each(function(i,v){$j(v).hide()});
		$j(".bkProperty").each(function(i,v){$j(v).hide()});
	}else if(organTypeVal == 1){
		// 工厂
		$j("#organModelTag").show();
		$j("#organModel1").show();
		$j("#organModel1").removeAttr("disabled");
		$j("#organModel2").hide();
		$j("#organModel2").attr("disabled","disabled");
		$j("#ccv").hide();
		$j(".extProperty").each(function(i,v){$j(v).hide()});
		$j(".baseProperty").each(function(i,v){$j(v).show()});
		$j("#areaAttr").each(function(i,v){$j(v).show()});
	}else if(organTypeVal == 2){
		// 经销商
		$j("#organModelTag").show();
		$j("#organModel1").hide();
		$j("#organModel1").attr("disabled","disabled");
		$j("#organModel2").show();
		$j("#organModel2").removeAttr("disabled");
		$j("#ccv").show();
		$j(".extProperty").each(function(i,v){$j(v).show()});
		$j(".baseProperty").each(function(i,v){$j(v).show()});
	}else{
		// 
		$j("#organModelTag").hide();
		$j("#organModel1").hide();
		$j("#organModel2").hide();
		$j("#ccv").hide();
		$j(".extProperty").each(function(i,v){$j(v).show()});
		$j(".baseProperty").each(function(i,v){$j(v).show()});
	}
}

function changeDealerType(){
	var organTypeVal = $j("#organType").val(); 
	$j("#organModel").val($j("#organModel2 #organModel option:selected").val());
	if(organTypeVal == 2){ 
		var dealerTypeVal = $j("#organModel2 #organModel option:selected").val();
		if(dealerTypeVal == 1) {
			$j("#license").val("");
			$j("#customerlevel").val("");
			$j(".bkProperty").each(function(i,v){$j(v).hide()});
		} else {
			if(${ users.makeorganid == '00000001' }){
				$j(".bkProperty").each(function(i,v){$j(v).show()});
				
			}else{
				$j(".bkProperty").each(function(i,v){$j(v).hide()});
			}
		}

		if (dealerTypeVal == 1 || dealerTypeVal =="") {
			$j('#keyR1,#keyR2').hide();
			$j('select[name="isKeyRetailer"]').prop('disabled', true);
		} else {
			$j('#keyR1,#keyR2').show();
			$j('select[name="isKeyRetailer"]').prop('disabled', false);
		}
	}
	
}
//加载事件
$j(function(){
	$j("#organType").change(changeOrganType);
	$j("#organModel2").change(changeDealerType);
	changeOrganType();
	changeDealerType();
	//根据机构级别设置机构类型
	$j("#organType option").each(function(i,v){
		var opVal = $j(v).val();
		if( opVal != "" && opVal< ${organType}){
			$j(v).remove();
		}
	});
	//根据经销商级别来设置 
	$j("#organModel2 #organModel option").each(function(i,v){
		var opVal = $j(v).val();
		if( opVal != "" && opVal <= ${organModel }){
			$j(v).remove();
		}
	});
});
</script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}

.taboff {
	border: 1px solid #E4EAD9;
	background-image: url(../images/CN/back-bntgray2.gif);
}

.tabon {
	BACKGROUND: #FFFFCC;
	BORDER-BOTTOM: #fff 1px solid
}
-->
</style>
	</head>
	<body onLoad="tabit(tab5d,c5d)" style="overflow-y: auto">


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
								新增机构
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<form name="addRoleForm" method="post" action="addOrganAction.do"
						enctype="multipart/form-data" onSubmit="return check();">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr align="center">
								<td class="taboff" id="tab5d" style="cursor: hand"
									onClick="tabit(tab5d,c5d)" width="10%">
<%--									基本资料--%>
								</td>
								<td class="taboff" id="tabcv" style="cursor: hand"
									onClick="tabit(tabcv,ccv)" width="10%">
<%--									联系人--%>
								</td>
								<td width="70%"></td>
							</tr>
							<tr id="c5d" style="display: none">
								<td colspan="4" class="tdbody">

									<fieldset align="center">
										<legend>
											<table width="50" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														基本信息
													</td>
												</tr>
											</table>
										</legend>
										<table width="100%" border="0" cellpadding="0" cellspacing="1">
											<tr >
												<td width="13%" align="right">
													机构类型：
												</td>
												<td >
													<windrp:select key="OrganType" name="organType" p="y|f"
														value="${organType}" /><span class="style1">*</span>
												</td>
												<td id="organModelTag" style="display:none;" align="right">
													类别：
												</td >
												<td id="organModel1" style="display:none;">
													<windrp:select key="PlantType" name="organModel"  p="y|f"
														value="${organModel}" /><span class="style1">*</span>
												</td>
												<td id="organModel2" style="display:none;">
													<windrp:select key="DealerType" name="organModel"  p="y|f"
														value="${organModel}" /><span class="style1">*</span>
												</td>
											</tr>
											<tr class="baseProperty">
												<td width="12%" align="right">
													机构编号：
												</td>
												<td width="19%">
													<input name="id" type="text" id="id" value="${oid}"
														${isread} maxlength="30" />
													<span class="style1">*</span>
												</td>
												<td width="11%" align="right">
													机构名称：
												</td>
												<td width="30%">
													<input name="organname" type="text" id="organname"
														size="35" maxlength="100" />
													<span class="style1">*</span>
												</td>
												<td width="12%" align="right">
													企业内部编码：
												</td>
												<td width="18%">
													<input name="oecode" type="text" id="oecode" maxlength="30" >
													<span class="style1">*</span>
												</td>
											</tr>
											<tr class="extProperty">
												<td align="right">
													上级机构：
												</td>
												<td>
													<input type="hidden" id="parentid" name="parentid"
														value="${parentid}">
													<input name="pname" type="text" id="oname" maxlength="128"
														value="<windrp:getname key='organ' value='${parentid}' p='d'/>"
														readonly><a href="javascript:SelectOrgan();"><img
															src="../images/CN/find.gif" width="17" height="18"
															border="0" align="absmiddle"> </a>
												</td>
												<td align="right">
													机构简称：
												</td>
												<td>
													<input name="shortname" type="text" id="shortname"
														maxlength="16">
												</td>
												<td align="right">
													机构内转仓是否需要审批：
												</td>
												<td>
												<windrp:select key="YesOrNo" name="isNeedApprove" p="n|f"/>
													<%--<input name="omobile" type="text" id="omobile"
														maxlength="30">
													<span class="style1">*</span>
												--%></td>
											</tr>
											<tr class="extProperty">
												<td align="right">
													电话：
												</td>
												<td>
													<input name="otel" type="text" id="otel" maxlength="30">
												</td>
												<td align="right">
													传真：
												</td>
												<td>
													<input name="ofax" type="text" id="ofax" maxlength="30">
												</td>
												<td align="right">
													EMail：
												</td>
												<td>
													<input name="oemail" type="text" id="oemail" maxlength="30">
												</td>
											</tr>
											<tr class="bkProperty">
												<td align="right">
													营业执照：
												</td>
												<td>
													<input name="license" type="text" id="license" maxlength="30">
												</td>
												<td align="right">
													客户级别：
												</td>
												<td>
													<select name="customerlevel">
														<option value="">
															-请选择-
														</option>
														<logic:iterate id="lv" name="level">
														<option value="${lv.tagsubkey}">${lv.tagsubvalue}</option>
														</logic:iterate>
													</select>
												</td>
												<td align="right" >是否审核：</td>
	    										<td><windrp:select key="YesOrNo" name="validatestatus" p="n|f" value="0"/></td>
											</tr>
											<tr class="extProperty">
												<td align="right">
													Logo：
												</td>
												<td colspan="3">
													<input name="logo" type="file" id="logo" size="40">
												</td>
												<ws:hasAuth operationName="/keyretailer/setKeyRetaikerFlagAction.do">
												<td align="right" id="keyR1" style="display:none">
													是否银河会员：
												</td>
												<td id="keyR2" style="display:none">
													<select name="isKeyRetailer" disabled="disabled">
														<option value="">-选择-</option>
														<option value="0">否</option>
														<option value="1">是</option>
													</select>
												</td>
												</ws:hasAuth>
											</tr>
											<tr id="areaAttr" class="extProperty">
												<td align="right">
													区域：
												</td>
												<td colspan="5">
													<select name="province"
														id="province" onChange="getResult('province','city');"
														onkeydown="if(event.keyCode==13)event.keyCode=9 ">
														<option value="">
															-选择-
														</option>
														<logic:iterate id="c" name="cals">
															<option value="${c.id}">
																${c.areaname}
															</option>
														</logic:iterate>
													</select>
													<span class="style1">*</span>
													-
													<select name="city" id="city" onChange="getResult('city','areas');"
														onkeydown="if(event.keyCode==13)event.keyCode=9 ">
														<option value="">
															-选择-
														</option>
													</select>
													<span class="style1">*</span>
													-
													<select name="areas" id="areas"
														onkeydown="if(event.keyCode==13)event.keyCode=9 ">
														<option value="">
															-选择-
														</option>
													</select>
													<span class="style1">*</span>
												</td>
											</tr>
											<tr class="baseProperty">
												<td align="right">
													地址：
												</td>
												<td colspan="5">
													<input name="oaddr" type="text" id="oaddr" size="80"
														  maxlength="200" onClick="setAddr(this)">
													<span class="style1">*</span>
												</td>
											</tr>
											</div>
										</table>
									</fieldset>
								</td>
							</tr>
							<tr id="ccv" style="display: none">
								<td colspan="4">
									<%--<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td align="left">
												<a href="javascript:addtr('linkmantable');"><img
														src="../images/nolines_plus.gif" width="16" height="18"
														border="0" title="新增"> </a>
												<a href="javascript:deletetr('linkmantable');"><img
														src="../images/nolines_minus.gif" width="16" height="18"
														border="0" title="删除"> </a>
											</td>
										</tr>
									</table>
									--%><table width="100%" border="0" cellpadding="0" cellspacing="1"
										id="linkmantable">
										<tr>
											<td>
												<fieldset align="center">
													<table width="100%" border="0" cellpadding="0"
														cellspacing="1">
														<tr>
															<input name="linkid" type="hidden" value="">
															<td width="10%" align="right">
																联系人姓名：
															</td>
															<td width="21%">
																<input id="linkmanname" name="name" type="text" maxlength="30">
																<span class="style1">*</span>
															</td>
															<td width="10%" align="right">
																性别：
															</td>
															<td width="23%">
																<windrp:select key="Sex" name="sex" p="n|f" />
															</td>
															<td align="right">
																职务：
															</td>
															<td>
																<input type="text" name="duty" maxlength="30">
															</td>
														</tr>
														<tr>
															<td align="right">
																是否主联系人：
															</td>
															<td>
																<windrp:select key="YesOrNo" name="ismain" p="n|f" />
															</td>
															<td width="9%" align="right">
																办公电话：
															</td>
															<td width="27%">
																<input name="linkofficetel" type="text" maxlength="20">
															</td>
															<td width="10%" align="right">
																手机：
															</td>
															<td width="21%">
																<input type="text" id="linkmobile" name="linkmobile" maxlength="20">
																<span class="style1">*</span>
															</td>
														</tr>
														<tr>

															<td align="right">
																Emai：
															</td>
															<td>
																<input type="text" name="linkemail" maxlength="20">
															</td>
															<td align="right">
																QQ：
															</td>
															<td>
																<input name="qq" type="text" id="qq" maxlength="20">
															</td>
															<td align="right">
																&nbsp;
															</td>
															<td>
																&nbsp;
															</td>
														</tr>
														<tr>

															<td align="right">
																地址：
															</td>
															<td>
																<input type="text" name="lkaddr" size="40"
																	maxlength="200">
															</td>
															<td align="right">
																&nbsp;
															</td>
															<td>
																&nbsp;
															</td>
														</tr>
													</table>
												</fieldset>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

						<table width="100%" border="0" cellpadding="0" cellspacing="1">

							<tr class="baseProperty" align="center">
								<td>
									<input type="submit" name="Submit" value="提交">
									&nbsp;&nbsp;
									<input type="reset" name="Submit2" value="取消"
										onClick="window.close()">
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>

		<form name="excputform" method="post" action="ToUpdOrganAction.do">
			<input type="hidden" name="saleManId" id="saleManId"
				value="${saleManId}">
		</form>
	</body>
</html>
