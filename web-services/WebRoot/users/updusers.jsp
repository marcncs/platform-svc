<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/validator.js"></script>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<script language="javascript" src="../js/jquery-1.4.2.min.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript"> 
$(document).ready(function(){
    var r = '${ucids}';
    var result = r.split(",");
    for(var i=0;i<result.length;i++){
      $("input[value='"+result[i]+"']").attr("checked","checked");
  }
}); 
function SelectOrgan(){
	var c=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( c == undefined ){
		return;
	}	
	document.updusers.makeorganid.value=c.id;
	document.updusers.oname.value=c.organname;
}   

function check(){

   /*var UserType=document.getElementsByName("UserType")[0];
   if(UserType.value==""){
      alert("用户性质不能为空!")
      return false;
   }*/
   
   if ( mobile_hasdouble ){
		alert($("#mobile").val()+"此手机号已经注册，请重新录入！");		
		return false;
	}
	
   if( !Validator.Validate(document.addusers,2) ){
		return false;
	}
	showloading();
	return true;
}

function cleardept(){
	document.getElementById("makedeptid").value="";
	document.getElementById("deptname").value="";
}

//验证手机号
var mobile_hasdouble=false;


function checkMobile(){
	var oldmoblie = $('#oldmoblie').val().trim();
	var mobile = $('#mobile').val().trim();
	if ( mobile == "" ){	
		alert("手机号不能为空！");		
		return;
	}
	if(oldmoblie==mobile){
		mobile_hasdouble = false;
		return;
	}
	$.ajax({  
    	type : "POST",
		url : "../users/ajaxCheckUsersAction.do?type=1",
		data : "mobile="+mobile,
		dataType: "json",
		async: false,
        success: function (data) {  
            if(data.returnCode != 0) {
            	mobile_hasdouble = true;
		    } else {
		    	mobile_hasdouble = false;
			}
        },  
        error: function (data) {  
        	alert("网络异常");  
        }
    });  
}

</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td> 
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
                <td width="772"> 修改用户</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="updusers" method="post" action="updUsersAction.do" onSubmit="return check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right"><input name="userid" type="hidden" id="userid" value="${us.userid}">
登录名：</td>
          <td width="21%"><input name="loginname" type="text" id="loginname" value="${us.loginname}" readonly></td>
          <td width="13%" align="right">真实姓名：</td>
          <td width="23%"><input name="realname" type="text" id="realname" value="${us.realname}" dataType="Require" msg="必须录入真实姓名!"><span class="STYLE1">*</span></td>
	      <td width="11%" align="right">英文名：</td>
	      <td width="23%"><input name="nameen" type="text" id="nameen" value="${us.nameen}"></td>
	  </tr>
<%--	 
	  <tr>
	    <td  align="right">性别：</td>
	    <td>${us.sexname} </td>
	    <td align="right">生日：</td>
	    <td><input name="birthday" type="text" id="birthday" onFocus="javascript:selectDate(this)" value="${us.birthday}" readonly></td>
	    <td align="right">身份证号：</td>
	    <td><input name="idcard" type="text" id="idcard" value="${us.idcard}" dataType="IdCard" msg="必须录入正确的身份证号码!" require="false"></td>
	    </tr>
	     --%> 
	  <tr>
	    <td  align="right">所属机构：</td>
	    <td><input type="hidden" id="makeorganid" name="makeorganid" value="${us.makeorganid}">
	    <input name="oname" type="text" id="oname" maxlength="128"
	     value="<windrp:getname key='organ' value='${us.makeorganid}' p='d'/>" 
	     readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif"  
	     border="0" align="absmiddle"> </a>
	      <script type="text/javascript">
			document.getElementById('makeorganid').attachEvent('onpropertychange',function(o){
				if(o.propertyName!='value')return;
				cleardept();
			});
		    </script>       
	      </td>
	    <td align="right">所属部门：</td>
	    <td><input type="hidden" name="makedeptid" id="makedeptid" value="${us.makedeptid}">
			<input type="text" name="deptname" id="deptname" 
			onClick="selectDUW(this,'makedeptid',$F('makeorganid'),'d')" 
			value="<windrp:getname key='dept' value='${us.makedeptid}' p='d'/> " 
			readonly>
			</td>		
	    <td align="right">是否可用：</td>
	    <td>${us.statusname}</td>
	    </tr>
		 <tr>
<%--		 
	    <td align="right">是否可用呼叫中心：</td>
	    <td>${us.iscallname}</td>--%>
	    <td align="right">有效日期：</td>
	    <td><input name="validate" type="text" id="validate" value="${us.validate}" onFocus="javascript:selectDate(this)" readonly></td>
	    <td align="right">
			用户类型：
		</td>
		<td>
			<select name="userType"> 
				<option value="">
					-请选择-
				</option>
				<logic:iterate id="lv" name="salesUserType">
				<c:choose>
				<c:when test="${us.userType == lv.tagsubkey}">
				<option value="${lv.tagsubkey}" selected="selected">${lv.tagsubvalue}</option>
				</c:when>
				<c:otherwise>
				<option value="${lv.tagsubkey}">${lv.tagsubvalue}</option>
				</c:otherwise>
				</c:choose>
				</logic:iterate>
			</select>
		</td>
		<td align="right">是否CWID用户：</td>
	    <td>${us.isCwid}</td>
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
	  	<td width="9%"  align="right">办公电话：</td>
          <td width="21%"><input name="officetel" type="text" id="officetel" value="${us.officetel}" require="false" dataType="Phone" msg="必须录入正确的办公电话号码!"></td>
          <td width="13%" align="right">手机号：</td>
          <td width="23%">
          <input name="mobile" type="text" id="mobile"  value="${us.mobile}" maxlength="11" >
          <input name="oldmoblie"  id="oldmoblie" type="hidden" value="${us.mobile}"/>
          </td>
          <td width="9%" align="right"></td>
	      <td width="25%"></td> 
	  	  
<%--         
	      <td width="9%" align="right">家庭电话：</td>
	      <td width="25%"><input name="hometel" type="text" id="hometel" value="${us.hometel}" require="false" dataType="Phone" msg="必须录入正确的家庭电话号码!"></td>
	  	  --%>
	  </tr>
	  <tr>
	    <td  align="right">Email：</td>
	    <td><input name="email" type="text" id="email" value="${us.email}" size="35" require="false" dataType="Email" msg="必须录入正确的Email!"></td>
<%--	   
	    <td align="right">QQ：</td>
	    <td><input name="qq" type="text" id="qq" value="${us.qq}" require="false" dataType="QQ" msg="必须录入正确的QQ!"></td>
	    <td align="right">MSN：</td>
	    <td><input name="msn" type="text" id="msn" value="${us.msn}"></td>
	     --%>
	    </tr>
<%--
	  <tr>
	    <td  align="right">区域：</td>
	    <td colspan="5"><select name="province"  id="province" onChange="getResult('province','city');">
	      <option value="">-省份-</option>
	      <logic:iterate id="ua" name="cals"> <option value="${ua.id}" 
	        <c:if test="${ua.id==us.province}">
	          <c:out value="selected"/>
	          </c:if>
	        >${ua.areaname}
	        </option>
	        </logic:iterate>
	      </select>
          <select id="city" name="city" onChange="getResult('city','areas');">
				<option value="">
					-选择-
				</option>
				<logic:iterate id="c" name="Citys">
				<option value="${c.id}" ${c.id == us.city?"selected":""}>${c.areaname}</option>
				</logic:iterate>
		  </select>
       	  <select id="areas" name="areas" id="areas">
				<option value="">
					-选择-
				</option>
				<logic:iterate id="c" name="Areass">
				<option value="${c.id}" ${c.id == us.areas?"selected":""}>${c.areaname}</option>
				</logic:iterate>
			</select>
          </td>
	    </tr>
	  <tr>
	    <td  align="right">地址：</td>
	    <td colspan="5"><input name="addr" type="text" id="addr" value="${us.addr}" size="50" onClick="setAddr(this)"></td>
	    </tr>
	    
	    <tr>
	    <td width="8%" align="right">用户性质：</td>
        <td width="12%"><windrp:select key="UserType" name="UserType" p="y|f" value="${UserType}" /><span class="STYLE1">*</span></td>
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
	  </tr>
	  </table>
	</fieldset>
	
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              
                <tr align="center"> 
                  <td width="33%"><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                    <input type="button" name="Submit2" value="取消" onClick="window.close();"> </td>
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
