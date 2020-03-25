<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="com.winsafe.hbm.util.Internation"%>
<html>

<script src="../js/prototype.js"></script>
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
  
  function SelectDitch(){
	showModalDialog("toSelectDitchAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.validateCustomer.ditchid.value=getCookie("did");
	document.validateCustomer.ditchidname.value=getCookie("dname");
}

var allccode=0;
	function ajaxChkCcode(){
	var cid = $F('cid');
	//alert(vID);
        var url = 'ajaxValidateCustomerCcodeAction.do?Cid='+cid;
        var pars = '';

       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse1}
                    );
					//alert(myAjax);
	
    }

   function showResponse1(originalRequest)
    {
		var cc = originalRequest.responseTEXT; 
		
		//alert("---"+cc);
		setTimeout(ChkWrite(cc),1000);
	}

	function ChkWrite(str){
		if(str>=1){
		alert("该编号已存在！");
		allccode=1;
		}else{
		allccode=0;
		//alert("22222");
		}
	}
	

 function formChk(){
	var cname = document.validateCustomer.cname;
	var areas = document.validateCustomer.areas;
	
	if(cname.value==""){
		alert("客户名不能为空！");
		return false;
	}	
	if(areas.value==""){
		alert("地区不能为空！");
		return false;
	}
	
	if ( !checklinkman() ){
		return false;
	}
	if ( !checklinkofficetel() ){
		return false;
	}
	if ( !checkMobile() ){
		return false;
	}
	if ( !checkAddr() ){
		return false;
	}
	
	return true;
 }
 
 function yulan()
{
var fileext=document.validateProduct.picture.value.substring(document.validateProduct.picture.value.lastIndexOf("."),document.validateProduct.picture.value.length)
        fileext=fileext.toLowerCase()
    
        if ((fileext!='.jpg')&&(fileext!='.gif')&&(fileext!='.jpeg')&&(fileext!='.png')&&(fileext!='.bmp'))
        {
            alert("对不起，系统仅支持标准格式的照片，请您调整格式后重新上传，谢谢 ！");
             document.validateProduct.picture.focus();
        }
        else
        {
        //alert(''+document.validateProduct.UpFile.value)//把这里改成预览图片的语句
  			document.getElementById("preview").innerHTML="<img src='"+document.validateProduct.picture.value+"' align=center width=150 style='border:6px double #ccc'>"
        }


}

function Clear(){
	document.validateCustomer.ditchid.value="";
	document.validateCustomer.ditchidname.value="";
}
function tabit(id,cid) {
tab5d.className="taboff";
tabcv.className="taboff";
tabbi.className="taboff";
id.className="tabon";
c5d.style.display="none";
ccv.style.display="none";
cbi.style.display="none";
cid.style.display="block";
}


function showall() {
tab5d.className="tabon";tabcv.className="tabon";tabbi.className="tabon";
c5d.style.display="block";ccv.style.display="block";cbi.style.display="block";
}

function addtr(tableid){	 	
	 TR = document.getElementById(tableid).insertRow(-1);
	 TD1 = TR.insertCell(0);//加列	
	 if ( tableid == "linkmantable"){
	 TD1.innerHTML = "<fieldset align='center'><table width='100%'  border='0' cellpadding='0' cellspacing='1'><tr><td width='10%' align='right'>联系人姓名：</td><td width='21%'><input name='name' type='text' ></td><td width='10%' align='right'>性别：</td><td width='23%'><select name='sex'><option value='1'>女</option><option value='2'>男</option></select></td><td align='right'>身份证号码：</td><td><input name='idcard' type='text' ></td></tr><tr><td align='right'>生日：</td><td><input type='text' name='birthday' onFocus='javascript:selectDate(this)'></td><td align='right'>部门：</td><td><input name='department' type='text'></td><td align='right'>职务：</td><td><input type='text' name='duty' ></td></tr><tr><td align='right'>是否主联系人：</td><td><select name='ismain'><option value='0'>否</option><option value='1'>是</option></select></td><td width='9%' height='20' align='right'>办公电话：</td><td width='27%'><input name='linkofficetel' type='text' ></td><td width='10%' align='right'>手机：</td><td width='21%'><input type='text' name='linkmobile' ></td></tr><tr><td width='10%' align='right'>家庭电话：</td><td width='23%'><input name='hometel' type='text'></td><td height='20' align='right'>Emai：</td><td><input type='text' name='linkemail' ></td><td align='right'>QQ：</td><td><input name='qq' type='text' id='qq' ></td></tr><tr><td align='right'>MSN：</td><td><input type='text' name='msn'></td><td height='20' align='right'>地址：</td><td><input type='text' name='addr' size='40'></td><td align='right'>货运部：</td><td><select name='transit'><option value='0'>EMS</option><option value='1'>UPS</option></select></td></tr></table></fieldset>";
	 }else if ( tableid == "banktable"){
	 	TD1.innerHTML = "<fieldset align='center'><table width='100%'  border='0' cellpadding='0' cellspacing='1'><tr><td width='10%' align='right'>开户银行：</td><td width='21%'><input name='bankname' type='text' ></td><td width='10%' align='right'>户名：</td><td width='23%'><input name='doorname' type='text' ></td><td align='right'>账号：</td><td><input name='bankaccount' type='text' ></td></tr></table>";
	 }
}

function deletetr(tableid){
	var celltable = document.getElementById(tableid);
	if ( celltable.rows.length > 1 ){
		document.getElementById(tableid).deleteRow(-1)
	}
}
function checklinkman(){
	var lkname = document.validateCustomer.name;
	if ( lkname.length > 1){
		for ( i=0;i<lkname.length;i++){
			if ( lkname[i].value == ""){
				alert("联系人姓名不能为空!");
				return false;
			}
		}
	}else{
		if ( lkname.value == ""){
				alert("联系人姓名不能为空!");
				return false;
			}
	}
	return true;
}

function checklinkofficetel(){
	var lkname = document.validateCustomer.linkofficetel;
	if ( lkname.length > 1){
		for ( i=0;i<lkname.length;i++){
			if ( lkname[i].value.length < 7){
				alert("联系人办公电话格式不正确!");
				return false;
			}
		}
	}else{
		if ( lkname.value.length <7){
				alert("联系人办公电话格式不正确!");
				return false;
			}
	}
	return true;
}

function checkMobile(){
	var lkname = document.validateCustomer.linkmobile;
	if ( lkname.length > 1){
		for ( i=0;i<lkname.length;i++){
			if ( lkname[i].value == ""){
				alert("联系人手机不能为空!");
				return false;
			}
		}
	}else{
		if ( lkname.value == ""){
				alert("联系人手机不能为空!");
				return false;
			}
	}
	return true;
}

function checkAddr(){
	var lkname = document.validateCustomer.addr;
	if ( lkname.length > 1){
		for ( i=0;i<lkname.length;i++){
			if ( lkname[i].value == ""){
				alert("联系人地址不能为空!");
				return false;
			}
		}
	}else{
		if ( lkname.value == ""){
				alert("联系人地址不能为空!");
				return false;
			}
	}
	return true;
}



</script>


<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
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

<body onLoad="tabit(tab5d,c5d)">

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 添加客户资料</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="validateCustomer" method="post" action="addCustomerAction.do" enctype="multipart/form-data" onSubmit="return formChk();">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr align="center"> 
		<td  class="taboff" id="tab5d" style="cursor:hand" onClick="tabit(tab5d,c5d)" width="10%">基本资料</td>
		<td class="taboff" id="tabcv" style="cursor:hand" onClick="tabit(tabcv,ccv)" width="10%">联系人</td>
		<td class="taboff" id="tabbi" style="cursor:hand" onClick="tabit(tabbi,cbi)" width="10%">银行帐号</td>
		<td width="70%"></td>
	  </tr>
	  <tr id="c5d" style="display:none"> 
		<td colspan="4" class="tdbody">
		
		<fieldset align="center"> 		
			 <table width="100%"  border="0" cellpadding="0" cellspacing="1">
				<tr >
				  <td width="9%"  align="right">客户名称：</td>
				  <td width="27%" ><input name="cname" type="text" ><span class="STYLE1"> *</span></td>
				  <td width="10%" align="right">&nbsp;</td>
				  <td width="20%">&nbsp;</td>
				  <td width="34%" rowspan="8" align="right"><table width="100%" border="0" cellpadding="0" cellspacing="1">
					  <tr>
						<td height="150"><div id="preview"></div></td>
					  </tr>
					  <tr>
						<td><table width="100%"  border="0" cellpadding="0" cellspacing="1">
							<tr>
							  <td width="29%" align="right">客户照片：</td>
							  <td width="71%"><input name="picture" type="file" id="picture" onChange="yulan();"></td>
							</tr>
						</table></td>
					  </tr>
				  </table></td>
				  </tr>
				<tr >
				  <td  align="right">分类：</td>
				  <td ><select name="sort">
                      <c:forEach var="sort" items="${sortlist}">
                        <option value="${sort.id}">${sort.sortname}</option>
                      </c:forEach>
                    </select></td>
				  <td  align="right">价格类别：</td>
				  <td >				    ${ratename}</td>
				  </tr>	
				<tr >
				  <td  align="right">信用额度：</td>
				  <td ><input name="creditlock" type="text" ></td>
				  <td  align="right">折扣率：</td>
				  <td ><input name="discount" type="text" value="100">%</td>
				</tr>	
				<tr >
				  <td  align="right">税率：</td>
				  <td ><input name="taxrate" type="text" value="0">
				  %</td>
				  <td  align="right">付款期限：</td>
				  <td ><input name="prompt" type="text" ></td>
				  </tr>	
				<tr >
				  <td  align="right">客户行业：</td>
				  <td >${vocationname}</td>
				  <td  align="right">ABC类别：</td>
				  <td >${customertypename}</td>
				  </tr>
				<tr >
				  <td  align="right">客户状态：</td>
				  <td >${customerstatusname}</td>
				  <td  align="right">活跃率：</td>
				  <td >${yauldname}</td>
				</tr>
				<tr >
				  <td  align="right">客户来源：</td>
				  <td >${sourcename}</td>
				  <td  align="right">渠道：</td>
				  <td ><input name="ditchid" type="hidden" id="ditchid">
					<input name="ditchidname" type="text" id="ditchidname">
					<a href="javascript:SelectDitch();"><img src="../images/CN/find.gif" width="18" height="18" border="0" title="查找"></a><a href="javascript:Clear();"><img src="../images/CN/clear.gif" width="16" height="14" border="0" title="清除"></a></td>
				</tr>
				<tr >
				  <td  align="right">签约日期：</td>
				  <td ><input name="signdate" type="text" id="signdate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"></td>
				  <td  align="right">邮编：</td>
				  <td ><input name="postcode" type="text" id="postcode" maxlength="6"></td>
				</tr>
				<tr >
				  <td  align="right">大区：</td>
				  <td ><select name="region"  onChange="getResult('region','province');">
                    <option value="">-大区-</option>
                    <logic:iterate id="c" name="cals">
                      <option value="${c.id}">${c.areaname}</option>
                    </logic:iterate>
                  </select></td>
				  <td  align="right">省份：</td>
				  <td ><select name="province"  id="province" onChange="getResult('province','city');">
                    <option value="">-省份-</option>
                    </select></td>
				</tr>
				<tr >
				  <td  align="right">城市：</td>
				  <td ><select name="city"  onChange="getResult('city','areas');">
                    <option value="">-城市-</option>
                  </select></td>
				  <td  align="right">地区：</td>
				  <td ><select name="areas" id="areas" >
                    <option value="">-地区-</option>
				  </select></td>
				</tr>
				<tr >
				  <td  align="right">专管人员：</td>
				  <td ><select name="specializeid">
                    <logic:iterate id="w" name="als">
                      <option value="${w.userid}">${w.realname}</option>
                    </logic:iterate>
                  </select></td>
				  <td  align="right">付款方式：</td>
				  <td >${paymentmodeselect}</td>
				</tr>
				<tr >
				  <td  align="right">发运方式：</td>
				  <td >${transportmode}</td>
				  <td  align="right">税号：</td>
				  <td ><input name="taxcode" type="text" id="taxcode"></td>
				</tr>
				<tr >
				  <td  align="right">通讯地址：</td>
				  <td  colspan="3"><input name="commaddr" type="text" id="commaddr" size="80"></td>
				  </tr>
				<tr >
				  <td  align="right">详细地址：</td>
				  <td  colspan="3"><input name="detailaddr" type="text" id="detailaddr" size="80"></td>
				  </tr>
			  </table>
		
			  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
			  <tr>
				<td width="9%"  align="right">办公电话：</td>
				  <td width="26%"><input name="officetel" type="text" id="officetel" maxlength="26"></td>
				  <td width="9%" align="right">手机：</td>
				  <td width="22%"><input name="mobile" type="text" id="mobile" maxlength="26"></td>
				  <td width="9%" align="right">传真：</td>
				  <td width="25%"><input name="fax" type="text" id="fax" maxlength="26"></td>
			  </tr>
			  <tr>
				<td  align="right">网址：</td>
				<td><input name="homepage" type="text" id="homepage" size="40"></td>
				<td align="right">邮箱：</td>
				<td><input name="email" type="text" id="email" maxlength="64"></td>
				<td align="right">备注：</td>
				<td><input name="remark" type="text" value="" size="30"></td>
				</tr>
			  </table>	
		</fieldset>	  
			  
		</td>
  	  </tr>
	  <tr id="ccv" style="display:none">
    	<td colspan="4">
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
			  <tr><td align="left" >
			  	<a href="javascript:addtr('linkmantable');"><img src="../images/nolines_plus.gif" width="16" height="18" border="0" title="添加"></a>
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
							  <td width="21%"><input name="name" type="text" ><span class="STYLE1"> *</span></td>
							  <td width="10%" align="right">性别：</td>
							  <td width="23%"><%=Internation.getSelectTagByKeyAll("Sex",request,"sex",false,null )%></td>
							  <td align="right">身份证号码：</td>
							  <td><input name="idcard" type="text" ></td>
						  </tr>
						  <tr>						
							<td align="right">生日：</td>
							<td><input type="text" name="birthday" onFocus="javascript:selectDate(this)"></td>
							<td align="right">部门：</td>
							<td><input name="department" type="text" ></td>
							<td align="right">职务：</td>
							<td><input type="text" name="duty" ></td>
							</tr>
						  <tr>						
							<td align="right">是否主联系人：</td>
							<td><%=Internation.getSelectTagByKeyAll("YesOrNo",request,"ismain",false,null )%></td>
							<td width="9%"  align="right">办公电话：</td>
							  <td width="27%"><input name="linkofficetel" type="text" ><span class="STYLE1"> *</span></td>
							  <td width="10%" align="right">手机：</td>
							  <td width="21%"><input type="text" name="linkmobile" ><span class="STYLE1"> *</span></td>
							</tr>			 
							  <tr>
								
								  <td width="10%" align="right">家庭电话：</td>
								  <td width="23%"><input name="hometel" type="text"></td>
								  <td  align="right">Emai：</td>
								<td><input type="text" name="linkemail" ></td>
								<td align="right">QQ：</td>
								<td><input name="qq" type="text" id="qq" ></td>
							  </tr>
							  <tr>								
								<td align="right">MSN：</td>
								<td><input type="text" name="msn"></td>
								<td  align="right">地址：</td>
								<td><input type="text" name="addr" size="40"><span class="STYLE1"> *</span></td>
								<td align="right">货运部：</td>
								<td><%=Internation.getSelectTagByKeyAllDB("Transit","transit",false)%></td>
								</tr>				
							  </table>
						</fieldset> 	
				</td></tr>
			</table>
			
	   </td>
	  </tr>
	  <tr id="cbi" style="display:none"> 
		<td colspan="4">
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
			  <tr><td align="left" >
			  	<a href="javascript:addtr('banktable');"><img src="../images/nolines_plus.gif" width="16" height="18" border="0" title="添加"></a>
				<a href="javascript:deletetr('banktable');"><img src="../images/nolines_minus.gif" width="16" height="18" border="0" title="删除">				</a>
				</td>
			  </tr>
			</table>
			<table width="100%"  border="0" cellpadding="0" cellspacing="1" id="banktable">
				<tr><td>
						<fieldset align="center"> 	
						 <table width="100%"  border="0" cellpadding="0" cellspacing="1">
						  <tr>				
							  <td width="10%" align="right">开户银行：</td>
							  <td width="21%"><input name="bankname" type="text" ></td>
							  <td width="10%" align="right">户名：</td>
							  <td width="23%"><input name="doorname" type="text" ></td>
							  <td align="right">账号：</td>
							  <td><input name="bankaccount" type="text" ></td>
						  </tr>
						 </table>
						</fieldset>
				</td></tr>
			</table>
		</td>
	  </tr>
	</table>
	  
	  




<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><div align="center">
      <input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
      <input type="reset" name="cancel" onClick="javascript:history.back()" value="取消">
    </div></td>
  </tr>
</table>

</form>
	
	</td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
