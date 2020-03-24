<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script type="text/javascript" src="../js/validator.js"></script>
<script type="text/javascript" src="../js/prototype.js"></script>
<script language="JavaScript">
var cobj ="";
    function getResult(getobj,toobj)
    {
		//alert(getobj);
		cobj = toobj;
        var areaID = $F(getobj);
        //var y = $F('lstYears');
        var url = '../sales/listAreasAction.do';
        var pars = 'parentid=' + areaID;
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse}
                    );

    }

    function showResponse(originalRequest)
    {
        //put returned XML in the textarea
		var city = originalRequest.responseXML.getElementsByTagName("area"); 
						//alert(city.length);
						var strid = new Array();
						var str = new Array();
						for(var i=0;i<city.length;i++){
							var e = city[i];
							str[i] =new Array(e.getElementsByTagName("areaid")[0].firstChild.data , e.getElementsByTagName("areaname")[0].firstChild.data);
							//alert(str);
						}
						buildSelect(str,document.getElementById(cobj));//赋值给city选择框
        //$('cc').value = originalRequest.responseXML.getElementsByTagName("area");
    }

	function buildSelect(str,sel) {//赋值给选择框
	//alert(str.length);

		sel.options.length=0;
		sel.options[0]=new Option("选择","")
		for(var i=0;i<str.length;i++) {
			//alert(str[i]);	
				sel.options[sel.options.length]=new Option(str[i][1],str[i][0])
		}
}



var organid_hasdouble=false;
function checkProviderId(){
	var providerid = $F('pid');
	organid_hasdouble = false;
	var url = '../purchase/ajaxValidateProductIDAction.do';
	var pars = 'PID=' + providerid;
   	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showprovider}
				);
}

function showprovider(providerRequest){
	var data = eval('(' + providerRequest.responseText + ')');
	var lk = data.provider;
	if ( lk == undefined ){
	}else{	
		organid_hasdouble=true;	
		alert($F("pid")+"此供应商编号已经存在，请重新录入！");		
	}
}

	function ChkValue(){
        var pid = document.validateProvide.pid;
		var pname = document.validateProvide.pname;
		var tel = document.validateProvide.tel;
		var fax = document.validateProvide.fax;
		var email = document.validateProvide.email;
		var mobile = document.validateProvide.mobile;;
		var remark = document.validateProvide.remark;
		var prompt = document.validateProvide.prompt;
		var taxrate = document.validateProvide.taxrate;
		if(pid.value.length!=${mc.extent}){
			alert("请输入"+${mc.extent}+"位编号!");
			pid.select();
			return false;
		}
		if ( organid_hasdouble ){
			alert($F("pid")+"此供应商编号已经存在，请重新录入！");
			return false;
		}
		
		if(pname.value.trim() ==""){
			alert("供应商名称不能为空!");
			pname.select();
			return false;
		}
		
		
		if(prompt.value.trim() ==""){
			alert("账期不能为空!");
			prompt.select();
			return false;
		}
		if(taxrate.value.trim() ==""){
			alert("税率不能为空!");
			taxrate.select();
			return false;
		}
		
		if(tel.value.trim() ==""){
			alert("联系资料中电话不能为空!");
			tel.select();
			return false;
		}
		
		if(tel.value.trim() !="" && !tel.value.isMobileOrTel()){
			alert("联系资料中电话格式不正确!");
			tel.select();
			return false;
		}
		if(mobile.value.trim() !="" && !mobile.value.isMobile()){
			alert("联系资料中手机格式不正确!");
			mobile.select();
			return false;
		}
		if(fax.value.trim() !="" && !fax.value.isTel1()){
			alert("联系资料中传真格式不正确!");
			fax.select();
			return false;
		}
		
		if(email.value!=""&&!isEamil(email.value)){
			alert("联系资料中Email格式不正确!");
			email.select();
			return false;
		}

		
		if(remark.value.length >= 256){
			alert("备注不能超过256个字符！");
			return false;
		}


	
		
	var name = document.getElementsByName("name");
	var linkofficetel = document.getElementsByName("linkofficetel");
	var linkmobile = document.getElementsByName("linkmobile"); 
	var hometel = document.getElementsByName("hometel");
	var linkemail = document.getElementsByName("linkemail");
	for(var i = 0; i < name.length; i++){
		if(name[i].value==""|| name[i].value.trim()==""){
			alert("联系人名称不能为空!");
			name[i].select();
			return false;
			
		}
	}

	for(var i = 0; i < linkofficetel.length; i++){
		if(linkofficetel[i].value.trim()!="" && !linkofficetel[i].value.isMobileOrTel()){
			alert("联系人办公电话格式不正确!");
			linkofficetel[i].select();
			return false;
			
		}
	}
	for(var i = 0; i < linkmobile.length; i++){
		if(linkmobile[i].value.trim()=="" || !linkmobile[i].value.isMobile()){
			alert("联系人手机格式不正确!");
			linkmobile[i].select();
			return false;
			
		}
	}
	for(var i = 0; i < hometel.length; i++){
		if(hometel[i].value.trim()!="" && !hometel[i].value.isMobileOrTel()){
			alert("联系人家庭电话格式不正确!");
			hometel[i].select();
			return false;
			
		}
	}
	for(var i = 0; i < linkemail.length; i++){
		if(linkemail[i].value.trim()!="" && !isEamil(linkemail[i].value)){
			alert("联系人Email格式不正确!");
			linkemail[i].select();
			return false;
			
		}
	}
		
		showloading();
		return true;
	}
	
function tabit(id,cid) {
tab5d.className="taboff";
tabcv.className="taboff";
id.className="tabon";
c5d.style.display="none";
ccv.style.display="none";
cid.style.display="block";
}

function addtr(tableid){	 	
	 TR = document.getElementById(tableid).insertRow(-1);
	 TD1 = TR.insertCell(0);//加列	
	 TD1.innerHTML = "<fieldset align='center'><table width='100%'  border='0' cellpadding='0' cellspacing='1'><tr><td width='10%' align='right'>联系人姓名：</td><td width='21%'><input name='name' type='text' ></td><td width='10%' align='right'>性别：</td><td width='23%'><select name='sex'><option value='1'>女</option><option value='2'>男</option></select></td><td align='right'>婚姻状况：</td><td><select name='wedlock'><option value='0'>未知</option><option value='1'>未婚</option><option value='2'>已婚</option><option value='3'>离异</option><option value='4'>丧偶</option></select></td></tr><tr><td align='right'>生日：</td><td><input type='text' name='birthday' onFocus='javascript:selectDate(this)' readonly></td><td align='right'>部门：</td><td><input name='department' type='text'></td><td align='right'>职务：</td><td><input type='text' name='duty' ></td></tr><tr><td align='right'>是否主联系人：</td><td><select name='ismain'><option value='0'>否</option><option value='1'>是</option></select></td><td width='9%' height='20' align='right'>办公电话：</td><td width='27%'><input name='linkofficetel' type='text' ></td><td width='10%' align='right'>手机：</td><td width='21%'><input type='text' name='linkmobile'  maxlength='11' ></td></tr><tr><td width='10%' align='right'>家庭电话：</td><td width='23%'><input name='hometel' type='text' ></td><td height='20' align='right'>Emai：</td><td><input type='text' name='linkemail' ></td><td align='right'>QQ：</td><td><input name='qq' type='text' id='qq' onkeydown='onlyNumber(event)'></td></tr><tr><td align='right'>MSN：</td><td><input type='text' name='msn'></td><td height='20' align='right'>地址：</td><td><input type='text' name='lkaddr' size='40'></td><td align='right'>&nbsp;</td><td>&nbsp;</td></tr></table></fieldset>";
}

function deletetr(tableid){
	var celltable = document.getElementById(tableid);
	if ( celltable.rows.length > 1 ){
		document.getElementById(tableid).deleteRow(-1)
	}
}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
.taboff {
	border: 1px solid #E4EAD9;
	background-image: url(../images/CN/back-bntgray2.gif);
}
.tabon{
	BACKGROUND: #FFFFCC; BORDER-BOTTOM: #fff 1px solid
}
-->
</style>
</head>

<body onLoad="tabit(tab5d,c5d)">

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增供应商</td>
        </tr>
      </table>
	  
	  <form name="validateProvide" method="post" action="addProviderAction.do" onSubmit="return ChkValue();">
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr align="center"> 
		<td  class="taboff" id="tab5d" style="cursor:hand" onClick="tabit(tab5d,c5d)" width="10%">基本资料</td>
		<td class="taboff" id="tabcv" style="cursor:hand" onClick="tabit(tabcv,ccv)" width="10%">联系人</td>
		<td width="70%"></td>
	  </tr>
	  <tr id="c5d" style="display:none"> 
		<td colspan="4" class="tdbody">
		
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="10%"  align="right">供应商编号：</td>
          <td width="26%"><input name="pid" type="text" id="pid" onBlur="checkProviderId();" value="${pid}" ${isread} dataType="Require" msg="必须录入供应商编号!"></td>
          <td width="9%" align="right">供应商名称：</td>
          <td width="25%"><input name="pname" type="text" id="pname" size="35" maxlength="50" dataType="Require" msg="必须录入供应商名称!">
            <font color="#FF0000">*</font></td>
	      <td width="9%" align="right">供应商类型：</td>
	      <td width="21%">${genreselect}</td>
	  </tr>
	  <tr>
	    <td  align="right">行业：</td>
	    <td>${vocationselect}</td>
	    <td align="right">ABC分类：</td>
	    <td>${abcsortselect}</td>
	    <td align="right">法人代表：</td>
	    <td><input name="corporation" type="text" id="corporation" maxlength="32"></td>
	    </tr>
	  <tr>
	    <td  align="right">税号：</td>
	    <td><input name="taxcode" type="text" id="taxcode" maxlength="32"></td>
	    <td align="right">账期：</td>
	    <td><input name="prompt" type="text" id="prompt" size="8" maxlength="5" value="0" onKeyDown="onlyNumber(event);" >天<span class="STYLE1">*</span></td>
	    <td align="right">税率：</td>
	    <td><input name="taxrate" type="text" id="taxrate" size="8" value="0.0"  maxlength="8" onKeyPress="KeyPress(this)">%
	    <span class="STYLE1">*</span></td>
	  </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5"><input name="remark" type="text" id="remark" value="" size="60" maxlength="256"></td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>联系资料</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="10%"  align="right">电话：</td>
          <td width="20%"><input name="tel" type="text" id="tel" maxlength="26" >
            <font color="#FF0000">*</font></td>
          <td width="13%" align="right">传真：</td>
          <td width="25%"><input name="fax" type="text" id="fax" maxlength="26" ></td>
	      <td width="11%" align="right">手机：</td>
	      <td width="21%"><input name="mobile" type="text" id="mobile" maxlength="11" ></td>
	  </tr>
	  <tr>
	    <td  align="right">Email：</td>
	    <td><input name="email" type="text" id="email" size="35" maxlength="35" ></td>
	    <td align="right">公司主页：</td>
	    <td><input name="homepage" type="text" id="homepage" size="35" maxlength="120" ></td>
	    <td align="right">邮编：</td>
	    <td><input name="postcode" type="text" id="postcode" maxlength="6" onKeyDown="onlyNumber(event);"></td>
	    </tr>
	  <tr>
	    <td  align="right">区域：</td>
	    <td colspan="2"><select name="province"  id="province" onChange="getResult('province','city');">
          <option value="">-省份-</option>
          <logic:iterate id="c" name="cals">
            <option value="${c.id}">${c.areaname}</option>
          </logic:iterate>
        </select>-<select name="city"  onChange="getResult('city','areas');">
            <option value="">-城市-</option>
          </select>-<select name="areas" id="areas" >
            <option value="">-地区-</option>
          </select></td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">详细地址：</td>
	    <td colspan="5"><input name="addr" type="text" id="addr" maxlength="200" size="80" onClick="setAddr(this)"></td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>账务信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="10%"  align="right">开户银行：</td>
          <td width="20%"><input name="bankname" type="text" id="bankname" size="35" maxlength="64"></td>
          <td width="13%" align="right">银行帐号：</td>
          <td width="25%"><input name="bankaccount" type="text" id="bankaccount" size="35" maxlength="24"></td>
	      <td width="11%" align="right">付款条件：</td>
	      <td width="21%"><input name="paycondition" type="text" id="paycondition" maxlength="64"></td>
	  </tr>
	  </table>
	</fieldset>
	
	</td>
  	  </tr>
	  <tr id="ccv" style="display:none">
    	<td colspan="4">
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
			  <tr><td align="left" >
			  	<a href="javascript:addtr('linkmantable');"><img src="../images/nolines_plus.gif" width="16" height="18" border="0" title="新增"></a>
				<a href="javascript:deletetr('linkmantable');"><img src="../images/nolines_minus.gif" width="16" height="18" border="0" title="删除"></a>
				</td>
			  </tr>
			</table>
			<table width="100%"  border="0" cellpadding="0" cellspacing="1" id="linkmantable">
				<tr><td>
						<fieldset align="center"> 	
						 <table width="100%"  border="0" cellpadding="0" cellspacing="1">
						  <tr>				
							  <td width="10%" align="right">联系人姓名：</td>
							  <td width="21%"><input name="name" type="text" maxlength="64"><span class="STYLE1"> *</span></td>
							  <td width="10%" align="right">性别：</td>
							  <td width="23%"><windrp:select key="Sex" name="sex" p="n|f"/></td>
							  <td align="right">婚姻状况：</td>
							  <td><select name="wedlock"><option value="0">未知</option><option value="1">未婚</option><option value="2">已婚</option><option value="3">离异</option><option value="4">丧偶</option></select></td>
						  </tr>
						  <tr>						
							<td align="right">生日：</td>
							<td><input type="text" name="birthday" onFocus="javascript:selectDate(this)" readonly="readonly"></td>
							<td align="right">部门：</td>
							<td><input name="department" type="text" maxlength="64"></td>
							<td align="right">职务：</td>
							<td><input type="text" name="duty" maxlength="64"></td>
							</tr>
						  <tr>						
							<td align="right">是否主联系人：</td>
							<td><windrp:select key="YesOrNo" name="ismain" p="n|f"/></td>
							<td width="9%"  align="right">办公电话：</td>
							  <td width="27%"><input name="linkofficetel" type="text" maxlength="26"></td>
							  <td width="10%" align="right">手机：</td>
							  <td width="21%"><input type="text" name="linkmobile" maxlength="26">
							  <font color="#FF0000">*</font></td>
							</tr>			 
							  <tr>
								
								  <td width="10%" align="right">家庭电话：</td>
								  <td width="23%"><input name="hometel" type="text" maxlength="26"></td>
								  <td  align="right">Emai：</td>
								<td><input type="text" name="linkemail" maxlength="64"></td>
								<td align="right">QQ：</td>
								<td><input name="qq" type="text" id="qq" onKeyDown="onlyNumber(event)" maxlength="13"></td>
							  </tr>
							  <tr>								
								<td align="right">MSN：</td>
								<td><input type="text" name="msn" maxlength="64"></td>
								<td  align="right">地址：</td>
								<td><input type="text" name="lkaddr" size="40" maxlength="100"></td>
								<td align="right">&nbsp;</td>
								<td>&nbsp;</td>
							</tr>				
							  </table>
						</fieldset> 	
				</td></tr>
			</table>
			
	   </td>
	  </tr>
	  </table>
	
	
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr> 
            <td > <div align="center">
              <input type="submit" name="Submit" value="确定"> &nbsp;&nbsp;
              <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();">
            </div></td>
          </tr>
      </table>
	  </form>
    </td>
  </tr>
</table>
</body>
</html>
