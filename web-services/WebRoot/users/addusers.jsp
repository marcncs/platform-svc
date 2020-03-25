<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/validator.js"></script>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/passwordcheck.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectOrgan(){
	var c=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( c == undefined ){
		return; 
	}	
	document.addusers.makeorganid.value=c.id;
	document.addusers.oname.value=c.organname;
}
//验证用户名
var organid_hasdouble=false;
function checkLoginName(){
	var ln = $F('loginname').trim();
	if ( ln == "" ){
		alert("登陆名不能为空！");		
		return;
	}
	organid_hasdouble = false;
	var url = '../users/ajaxCheckUsersAction.do';
	var pars = 'ln=' + ln;
   	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showorgan}
				);
}
function showorgan(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.users;
	if ( lk == undefined ){
	}else{	
		organid_hasdouble=true;	
		alert($F("loginname")+"此登陆名已经存在，请重新录入！");		
	}
}

//验证手机号
var mobile_hasdouble=false;
function checkMobile(){
	var ln = $F('mobile').trim();
	if ( ln == "" ){
		alert("手机号不能为空！");		
		return;
	}
	mobile_hasdouble = false;
	var url = '../users/ajaxCheckUsersAction.do';
	var pars = 'ln=' + ln + '&type=checkmobile';
   	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showorgan2}
				);
}
function showorgan2(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.users;
	if ( lk == undefined ){
	}else{	
		mobile_hasdouble=true;	
		alert($F("mobile")+"此手机号已经注册，请重新录入！");		
	}
}

function check(){

	if(!checkPwd($F('password'),$F("loginname"))) {
		return false;
	}
	
	if ( escape($F("loginname")).indexOf("%u")!=-1 ){
		alert("登录名不能为中文!");
		return false;
	}
	
	if ( organid_hasdouble ){
		alert($F("loginname")+"此登陆名已经存在，请重新录入！");		
		return false;
	}
	
	if ( mobile_hasdouble ){
		alert($F("mobile")+"此手机号已经注册，请重新录入！");		
		return false;
	}
	
	if ( !Validator.Validate(document.addusers,2) ){
		return false;
	}
	
	
	if($F('password').trim()!=$F('agapassword').trim()){
		alert("两次输入的密码不一致!");
		return false;
	}
	
  /* var UserType=document.getElementsByName("UserType")[0];
   if(UserType.value==""){
      alert("用户性质不能为空!")
      return false;
   }*/
	showloading();
	return true;
}

var cobj ="";
    function getResult(getobj,toobj)
    {
    	if(getobj=='province'){
    		buildSelect("",document.getElementById("areas"));
    	}
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

function cleardept(){
	document.getElementById("makedeptid").value="";
	document.getElementById("deptname").value="";
}
</script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td> 
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
                <td width="772"> 新增用户</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="addusers" method="post" action="addUsersAction.do" onSubmit="return check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="13%"  align="right">登录名：</td>
          <td width="22%"><input name="loginname" type="text" id="loginname" dataType="Require" msg="必须录入登录名!" onBlur="checkLoginName()" maxlength="30"><span class="STYLE1">*</span></td>
          <td width="11%" align="right">密码：</td>
          <td width="23%"><input name="password" type="password" id="password" dataType="SafeString" msg="密码位数至少6位，且必须包含字母、数字!" maxlength="30"><span class="STYLE1">*</span></td>
	      <td width="9%" align="right">重输密码：</td>
	      <td width="22%"><input name="agapassword" type="password" id="agapassword" dataType="Require" msg="必须录入重输密码!" maxlength="30"><span class="STYLE1">*</span></td>
	  </tr>
	  <tr>
	    <td  align="right">真实姓名：</td>
	    <td><input name="realname" type="text" id="realname" dataType="Require" msg="必须录入真实姓名!" maxlength="60"><span class="STYLE1">*</span></td>
	    <td align="right">英文名：</td>
	    <td><input name="nameen" type="text" id="nameen" maxlength="60"></td>
<%--	    
	    <td align="right">性别：</td>
	    <td>${sexselect} </td>--%>
	    </tr>
	  <tr>
<%--	 
	    <td  align="right">生日：</td>
	    <td><input name="birthday" type="text" id="birthday" onFocus="javascript:selectDate(this)" readonly></td>
	    <td align="right">身份证号：</td>
	    <td><input name="idcard" type="text" id="idcard" require="false" dataType="IdCard" msg="必须录入正确的身份证号码!" maxlength="24"></td>
	     --%>
	    <td align="right">所属机构：</td>
	    <td><input type="hidden" id="makeorganid" name="makeorganid" value="${makeorganid}"><input name="oname" type="text" id="oname" maxlength="128" value="<windrp:getname key='organ' value='${makeorganid}' p='d'/>" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="17" height="18" border="0" align="absmiddle"> </a></td>
	    <script type="text/javascript">
			document.getElementById('makeorganid').attachEvent('onpropertychange',function(o){
				if(o.propertyName!='value')return;
				cleardept();
			});
		    </script>    
	    </tr>
	  <tr>
	    <td  align="right">所属部门：</td>
	    <td><input type="hidden" name="makedeptid" id="makedeptid">
<input type="text" name="deptname" id="deptname" onClick="selectDUW(this,'makedeptid',$F('makeorganid'),'d')" value="请选择" readonly></td>
	    <td align="right">是否可用：</td>
	    <td>${status}</td>
	    <td align="right">
			用户类型：
		</td>
		<td>
			<select name="userType"> 
				<option value="">
					-请选择-
				</option>
				<logic:iterate id="lv" name="salesUserType">
				<option value="${lv.tagsubkey}">${lv.tagsubvalue}</option>
				</logic:iterate>
			</select>
		</td>
	    </tr>
		<tr>
<%--		
	    <td  align="right">是否可用呼叫中心：</td>
	    <td>${iscall}</td>--%>
	    <td align="right">有效日期：</td>
	    <td><input name="validate" type="text" id="validate" onFocus="javascript:selectDate(this)" readonly></td>
	    <td align="right">是否CWID用户：</td>
	    <td>${isCwid}</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="13%"  align="right">办公电话：</td>
          <td width="22%"><input name="officetel" type="text" id="officetel" require="false" dataType="Phone" msg="必须录入正确的办公电话号码!" maxlength="26"></td>
          <td width="11%" align="right">手机号：</td>
          <td width="23%"><input name="mobile" type="text" id="mobile" maxlength="11" ></td>
		  <td width="9%" align="right"></td>
	      <td width="22%"></td>
	      
<%--	      
	      <td width="9%" align="right">家庭电话：</td>
	      <td width="22%"><input name="hometel" type="text" id="hometel" require="false" dataType="Phone" msg="必须录入正确的家庭电话号码!" maxlength="26"></td>
	      --%>
	  </tr>
	  <tr>
	    <td  align="right">Email：</td>
	    <td><input name="email" type="text" id="email" size="35" require="false" dataType="Email" msg="必须录入正确的Email!" maxlength="35"></td>
<%--	   
	    <td align="right">QQ：</td>
	    <td><input name="qq" type="text" id="qq" require="false" dataType="QQ" msg="必须录入正确的QQ!" maxlength="24"></td>
	    <td align="right">MSN：</td>
	    <td><input name="msn" type="text" id="msn" maxlength="35"></td>
	     --%>
	    </tr>
<%--
	  <tr>
	    <td  align="right">区域：</td>
	    <td colspan="5"><select name="province"  id="province" onChange="getResult('province','city');">
	      <option value="">-省份-</option>
	      <logic:iterate id="c" name="cals">
	        <option value="${c.id}">${c.areaname}</option>
	        </logic:iterate>
	      </select>
	      -
	      <select name="city"  onChange="getResult('city','areas');">
	        <option value="">-城市-</option>
	        </select>
	      -
	      <select name="areas" id="areas" >
	        <option value="">-地区-</option>
	        </select></td>
	    </tr>
	  <tr>
	    <td  align="right">地址：</td>
	    <td colspan="5"><input name="addr" type="text" id="addr" size="50" maxlength="200" onClick="setAddr(this)"></td>
	    </tr>
	    
	   <tr>
	    <td width="8%" align="right">用户性质：</td>
        <td width="12%"><windrp:select key="UserType" name="UserType" p="y|f" value="${UserType}"/><span class="STYLE1">*</span></td>
      </tr>
      --%>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>用户分类</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="13%"  align="right">类型：</td>
        <td width="87%">
	        <logic:iterate id="uc" name="ucategory" >
	        	<input name="ucategary" type="checkbox" value="${uc.value}">${uc.name}
	        </logic:iterate>
        </td>
	  </tr>
	  </table>
	</fieldset>
	
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr align="center"> 
                  <td width="33%"><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                    <input type="button" name="Submit2" value="取消"  onClick="window.close();"> 
                    </td>
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
