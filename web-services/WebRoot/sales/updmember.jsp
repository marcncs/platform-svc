<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp"%>
<html>
<head>
<title>修改会员资料</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script src="../js/prototype.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script language="JavaScript">
	var cobj ="";
    function getResult(getobj,toobj)
    {
		//alert(getobj);
		cobj = toobj;
        var areaID = $F(getobj);
        //var y = $F('lstYears');
        var url = 'listAreasAction.do';
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


var allccode=0;
	function ajaxChkMobile(){
	var cid = $F('mobile');
	if ( cid == ${cf.mobile}){
		return;
	}
	//alert(vID);
        var url = 'ajaxCustomerMobileAction.do?Mobile='+cid;
        var pars = '';

       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showMobile}
                    );
					//alert(myAjax);
	
    }

   function showMobile(originalRequest)
    {
		var cc = originalRequest.responseTEXT; 
		
		//alert("---"+cc);
		setTimeout(ChkWrite(cc),1000);
	}

	function ChkWrite(str){
		if(str>=1){
		alert("该手机已存在,不能重复注册!");
		allccode=1;
		}else{
		allccode=0;
		//alert("22222");
		}
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
	  TD1.innerHTML = "<fieldset align='center'><table width='100%'  border='0' cellpadding='0' cellspacing='1'>"+
		 "<tr><td width='10%' align='right'>联系人姓名：</td>"+
		 "<td width='26%'><input name='name' type='text' ></td>"+
		 "<td width='10%' align='right'>性别：</td>"+
		 "<td width='24%'><select name='sex'><option value='1'>女</option><option value='2'>男</option></select></td>"+
		 "<td width='10%' align='right'>生日：</td>"+
		 "<td><input type='text' name='birthday' onFocus='javascript:selectDate(this)' readonly></td></tr>"+
		 "<tr><td align='right'>主联系人：</td>"+
		 "<td><select name='ismain'><option value='0'>否</option><option value='1'>是</option></select></td>"+
		 "<td  height='20' align='right'>办公电话：</td>"+
		 "<td ><input name='linkofficetel' type='text' ></td>"+
		 "<td align='right'>手机：</td>"+
		 "<td ><input type='text' name='linkmobile' maxlength=111'></td></tr>"+ 
		 "<tr><td  align='right'>家庭电话：</td>"+
		 "<td ><input name='hometel' type='text'></td>"+
		 "<td height='20' align='right'>Emai：</td>"+
		 "<td><input type='text' name='linkemail' ></td>"+
		 "<td align='right'>QQ：</td>"+
		 "<td><input name='qq' type='text' id='qq' maxlength='15' onkeydown='onlyNumber(event)'></td></tr>"+
		 "<tr><td align='right'>MSN：</td>"+
		 "<td><input type='text' name='msn' maxlength='15' onkeydown='onlyNumber(event)'></td>"+
		 "<td height='20' align='right'>地址：</td><td><input type='text' name='lkaddr' size='40' maxlength='100'></td>"+
		 "<td align='right'>部门：</td><td><input name='department' type='text'></td></tr>"+
		 "<tr><td align='right'>职务：</td>"+
		 "<td><input type='text' name='duty' ></td><td></td><td></td><td></td><td></td></tr></table></fieldset>";
}

function deletetr(tableid){
	var celltable = document.getElementById(tableid);
	if ( celltable.rows.length > 1 ){
		document.getElementById(tableid).deleteRow(-1)
	}
}

	function SelectCustomer(){
		var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:18cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		document.validateCustomer.parentid.value=c.cid;
		document.validateCustomer.parentidname.value=c.cname;
	}

function formChk(){
		var cname = $F('cname');
		var mobile = $F('mobile');
		var officetel = document.getElementById("officetel");
		var email = document.getElementById("email");
		var remark = document.getElementById("remark");
		var memberidcard = document.getElementById("memberidcard");
		if(cname==""||cname.trim()==""){
			alert("会员名称不能为空!");
			return false;
		}	
		if(mobile==""||mobile.trim()==""){
			alert("会员手机不能为空!");
			return false;
		}else if(!mobile.isMobile()){
			alert("手机格式不正确!");
			return false;
		}else{
			ajaxChkMobile();
			if(allccode==1){
			return false;
			}
		}
		if(officetel.value !="" && !officetel.value.isMobileOrTel()){
			alert("电话格式不正确!");
			officetel.select();
			return false;
		}
		if(email.value!=""&&!isEamil(email.value)){
			alert("Email格式不正确!");
			email.select();
			return false;
		}
		if(memberidcard.value!=""&&!isIdCard(memberidcard.value)){
			alert("身份证号码格式不正确!");
			memberidcard.select();
			return false;
		}
		
		if(remark.value.length >= 256){
			alert("备注不能超过256个字符！");
			return false;
		}
		
		
		var name = document.getElementsByName("name");
	var idcard = document.getElementsByName("idcard");
	var linkofficetel = document.getElementsByName("linkofficetel");
	var linkmobile = document.getElementsByName("linkmobile"); 
	var hometel = document.getElementsByName("hometel");
	var linkemail = document.getElementsByName("linkemail");
	
	for(var i = 0; i < name.length; i++){
		if(name[i].value!=""&& name[i].value.trim()==""){
			alert("联系人名称不能为空!");
			name[i].select();
			return false;
			
		}
	}
	
	for(var i = 0; i < idcard.length; i++){
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
	for(var i = 0; i < linkmobile.length; i++){
		if(linkmobile[i].value.trim()!="" && !linkmobile[i].value.isMobile()){
			alert("联系人手机格式不正确!");
			linkmobile[i].select();
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
	}
		showloading();
		return true;
	 }

function setAddr(obj){
	var tv= "";
	if ( $F('province') != ""){
		tv+=$('province').options[$('province').selectedIndex].text;
	}
	if ( $F('city') != ""){
		tv+=" "+$('city').options[$('city').selectedIndex].text;
	}
	if ( $F('areas') != ""){
		tv+=" "+$('areas').options[$('areas').selectedIndex].text;
	}
	if ( obj.value.indexOf(tv) == -1 ){
		obj.value=tv;
		obj.select();
	}
}
</script>




<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
.taboff {
	border: 1px solid ##E4EAD9;
	background-image: url(../images/CN/back-bntgray2.gif);
}
.tabon{
	BACKGROUND: #FFFFCC; BORDER-BOTTOM: #fff 1px solid
}
-->
</style>
</head>

<body onLoad="tabit(tab5d,c5d)" style="overflow: auto;">

	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 修改会员</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="validateCustomer" method="post" action="updMemberAction.do"  onSubmit="return formChk();">
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">会员名称：</td>
		<input id="cid" name="cid" value="${cf.cid}"  type="hidden">
          <td width="24%"><input name="cname" type="text" id="cname"  value="${cf.cname}" maxlength="50"> 
            <span class="STYLE1">*</span></td>
          <td width="10%" align="right">性别：</td>
          <td width="24%"><windrp:select key="Sex" name="membersex" p="n|f" value="${cf.membersex}"/></td>
	      <td width="10%" align="right">手机：</td>
	      <td width="23%">
          <input name="mobile" type="text" id="mobile" maxlength="11" onKeyDown="if(event.keyCode==13)event.keyCode=9" onBlur="return ajaxChkMobile();" value="${cf.mobile}"></td>
	  </tr>
	  <tr>
	    <td  align="right">电话：</td>
	    <td><input name="officetel" type="text" id="officetel" value="${cf.officetel}" maxlength="26" ></td>
	    <td align="right">出生日期：</td>
	    <td><input name="memberbirthday" type="text" value="${cf.memberbirthdayname}" id="memberbirthday" onFocus="javascript:selectDate(this)"  readonly="readonly"></td>
	    <td align="right">证件号码：</td>
	    <td><input name="memberidcard" type="text" id="memberidcard" value="${cf.memberidcard}" maxlength="18" ></td>
	    </tr>
	  <tr>
	    <td  align="right">卡号：</td>
	    <td><input name="cardno" type="text" id="cardno" value="${cf.cardno}"  maxlength="32"></td>
	    <td align="right">EMail：</td>
	    <td><input name="email" type="text" id="email" value="${cf.email}"  maxlength="50"></td>
	    <td align="right">会员密码：</td>
	    <td><input name="pwd" type="password" id="pwd" value="${cf.pwd}" maxlength="32"></td>
	  </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5"><textarea name="remark" style="width: 100%;" cols="80" id="remark">${cf.remark}</textarea><br><span class="td-blankout">(备注不能超过256字符)</span></td>
	    </tr>
	  </table>
</fieldset>


 <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>辅助信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">单位：</td>
          <td width="24%"><input name="membercompany" type="text" id="membercompany" value="${cf.membercompany}" ></td>
          <td width="10%" align="right">发票抬头：</td>
          <td width="27%"><input name="tickettitle" value="${cf.tickettitle}" type="text" id="tickettitle" size="35" ></td>
          <td width="9%" align="right">职业：</td>
	      <td width="21%"><input name="memberduty" value="${cf.memberduty}" type="text" id="memberduty" ></td>
	  </tr>
	  <tr>
	    <td  align="right">会员级别：</td>
	    <td>
	  
			<logic:iterate id="m" name="amgls">
			<c:if test="${m.id==cf.rate}">	
					${m.gradename}
				</c:if>	
			</logic:iterate>

		</td>
	    <td align="right">信用额度：</td>
	    <td><input name="creditlock" type="text" value="${cf.creditlock}" maxlength="6" onKeyDown="onlyNumber(event)"></td>
	    <td align="right">帐期：</td>
	    <td><input name="prompt" type="text" value="${cf.prompt}" maxlength="4" onKeyDown="onlyNumber(event)"></td>
	  </tr>
	  <tr>
	    <td  align="right">折扣率：</td>
	    <td><input name="discount" value="${cf.discount}" type="text" maxlength="6"  onkeypress="KeyPress(this)">
%</td>
	    <td align="right">税率：</td>
	    <td><input name="taxrate" type="text"  value="${cf.taxrate}" maxlength="6" onKeyPress="KeyPress(this)">%</td>
	    <td align="right">会员来源：</td>
	    <td><windrp:select key="Source" name="source" p="n|d"  value="${cf.source}"/></td>
	    </tr>
	  <tr>
	    <td  align="right">推荐人：</td>
	    <td><input name="parentid" type="hidden" value="${cf.parentid}" id="parentid">
          <input name="parentidname" type="text" id="parentidname" value="${cf.parentidname}" ><a 
          href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="19" height="18" align="absmiddle" border="0"></a>

          </td>
	    <td align="right">是否接收短信：</td>
	    <td>${cf.isreceivemsgname}</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	  </table>
	</fieldset>


	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>区域资料</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">区域：</td>
          <td width="52%"><select name="province"  id="province" onChange="getResult('province','city');" >
            <option value="">-省份-</option>
            <logic:iterate id="c" name="cals">
              <option value="${c.id}" ${c.id==cf.province?"selected":""}>${c.areaname}</option>
            </logic:iterate>
          </select>-<select name="city"  onChange="getResult('city','areas');" >
              <option value="${cf.city}">${cf.cityname}</option>
			  
            </select>-<select name="areas" id="areas" >
              <option value="${cf.areas}">${cf.areasname}</option>

            </select></td>
          <td width="17%" align="right">&nbsp;</td>
	      <td width="22%">&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">通讯地址：</td>
	    <td><input name="commaddr" value="${cf.commaddr}" type="text" id="commaddr" size="80" maxlength="100" onClick="setAddr(this)"></td>
	    <td align="right">通讯邮编：</td>
	    <td><input name="postcode" type="text" value="${cf.postcode}" id="postcode" size="8" maxlength="6"  onKeyDown="onlyNumber(event)"></td>
	  </tr>
	  <tr>
	    <td  align="right">送货地址：</td>
	    <td><input name="detailaddr" type="text" value="${cf.detailaddr}" id="detailaddr" size="80" maxlength="100" onClick="setAddr(this)"></td>
	    <td align="right">送货邮编：</td>
	    <td><input name="sendpostcode" type="text"  value="${cf.sendpostcode}" id="sendpostcode" size="8" maxlength="6" onKeyDown="onlyNumber(event);"></td>
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
                <c:if test="${lmfls==null}">
                <fieldset align="center">
                <table width="100%" border="0" cellpadding="0"
                    cellspacing="1">
                    <tr>
                        <td width="9%" align="right">
                            联系人姓名：
                        </td>
                        <td width="26%">
                            <input name="name" type="text" maxlength="50">
                        </td>
                        <td width="9%" align="right">
                            性别：
                        </td>
                        <td width="24%">
                            <windrp:select key="Sex" name="sex" p="n|f" />
                        </td>
                        <td align="right">
                            生日：
                        </td>
                        <td>
                            <input type="text" name="birthday"
                                onFocus="javascript:selectDate(this)" readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            主联系人：
                        </td>
                        <td>
                            <windrp:select key="YesOrNo" name="ismain" p="n|f" />
                        </td>
                        <td width="9%" align="right">
                            办公电话：
                        </td>
                        <td width="24%">
                            <input name="linkofficetel" type="text" maxlength="26">
                        </td>
                        <td width="8%" align="right">
                            手机：
                        </td>
                        <td width="24%">
                            <input type="text" name="linkmobile" maxlength="26">
                        </td>
                    </tr>
                    <tr>

                        <td width="9%" align="right">
                            家庭电话：
                        </td>
                        <td width="26%">
                            <input name="hometel" type="text" maxlength="26">
                        </td>
                        <td align="right">
                            Emai：
                        </td>
                        <td>
                            <input type="text" name="linkemail" maxlength="50">
                        </td>
                        <td align="right">
                            QQ：
                        </td>
                        <td>
                            <input name="qq" type="text" id="qq" maxlength="15" onKeyDown="onlyNumber(event)">
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            MSN：
                        </td>
                        <td>
                            <input type="text" name="msn" maxlength="15" onKeyDown="onlyNumber(event)">
                        </td>
                        <td align="right">
                            地址：
                        </td>
                        <td>
                            <input type="text" name="lkaddr" size="40" maxlength="100">
                        </td>
                        <td align="right">
                            职务：
                        </td>
                        <td>
                            <input type="text" name="duty" maxlength="50">
                        </td>
                    </tr>
                </table>
            </fieldset>
                </c:if>
                <c:if test="${lmfls!=null}">
				<logic:iterate id="lmf" name="lmfls">
						<fieldset align="center"> 	
						 <table width="100%"  border="0" cellpadding="0" cellspacing="1">
						  <tr>				
							  <td width="10%" align="right">联系人姓名：</td>
							  <td width="26%"><input name="name" type="text" value="${lmf.name}" maxlength="50"></td>
							  <td width="10%" align="right">性别：</td>
							  <td width="24%"><windrp:select key="Sex" name="sex" p="n|f" value="${lmf.sex}"/></td>
							  <td width="10" align="right">生日：</td>
							  <td><input type="text" name="birthday" value="${lmf.birthday}" onFocus="javascript:selectDate(this)" readonly="readonly"></td>
						  </tr>
						  <tr>						
							<td align="right">主联系人：</td>
							<td>${lmf.ismainname}</td>
							<td align="right">办公电话：</td>
							  <td ><input name="linkofficetel" value="${lmf.officetel}" type="text" maxlength="26"></td>
							  <td align="right">手机：</td>
							  <td><input type="text" name="linkmobile" value="${lmf.mobile}" maxlength="26"></td>
							</tr>			 
							  <tr>
								
								  <td align="right">家庭电话：</td>
							    <td ><input name="hometel" type="text" value="${lmf.hometel}"></td>
								  <td  align="right">Emai：</td>
								<td><input type="text" name="linkemail" value="${lmf.email}" maxlength="50" ></td>
								<td align="right">QQ：</td>
								<td><input name="qq" type="text" id="qq" value="${lmf.qq}" maxlength="15" onKeyDown="onlyNumber(event)"></td>
							  </tr>
							  <tr>								
								<td align="right">MSN：</td>
								<td><input type="text" name="msn" value="${lmf.msn}" maxlength="15" onKeyDown="onlyNumber(event)"></td>
								<td  align="right">地址：</td>
								<td><input type="text" name="lkaddr" size="40" value="${lmf.addr}" maxlength="100"></td>
								<td align='right'>部门：</td><td><input name='department' type='text' value="${department }"></td>
								  </tr>		
								  <tr><td align="right">职务：</td>
									<td><input type="text" name="duty" value="${duty }" ></td>
									<td align="right"></td>
									<td></td>
									<td align="right"></td>
									<td></td>
									</tr>		
						  </table>
						</fieldset> 	
						 </logic:iterate>
                         </c:if>
				</td></tr>
			</table>
			
	   </td>
	  </tr>
	  </table>
	

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><div align="center">
      <input type="Submit" name="Submit" value="确定">&nbsp;&nbsp;
      <input type="button" name="cancel" value="取消" onClick="javascript:window.close();">
    </div></td>
  </tr>
</table>

</form>
	
	</td>
  </tr>
</table>

</body>
</html>
