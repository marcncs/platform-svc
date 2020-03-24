<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<script src='../js/prototype.js'></script>
<SCRIPT LANGUAGE="JavaScript">
function checkMobile(){
	var sMobile = document.mobileform.mobile.value
	if(!(/^1[3|5][0-9]\d{4,8}$/.test(sMobile))){
		alert("不是完整的11位手机号或者正确的手机号前七位");
		document.mobileform.mobile.focus();
		return false;
	}
	else {
		searchMobile();
	}
}


function searchMobile(){
	   var url = '../assistant/searchMobileAreaAction.do';
	 //  alert("url========="+$F("mobile"));
       var pars = 'mno='+$F("mobile");
     // alert("abc======"+pars);
	   var myAjax = new Ajax.Request(url,{method: 'get', parameters: pars, onComplete: showResponse}
                    );
		document.getElementById("strmobile").innerHTML=$F("mobile");
	}
	
	  function showResponse(originalRequest)
    {
		//alert("dsfdsfsf");
		var mobile = originalRequest.responseXML.getElementsByTagName("mobile"); 
		//alert("mobile=="+mobile.length);
		var strareas="";
		var strcardtype=""
			for(var i=0;i<mobile.length;i++){
				var a = mobile[i];
				//alert("content=======111========="+a);
				var areas = a.getElementsByTagName("areas")[0].firstChild.data;
				var cardtype =a.getElementsByTagName("cardtype")[0].firstChild.data ;
				
				strareas = areas;
				strcardtype=cardtype;
		    }
			document.getElementById("strareas").innerHTML=strareas;
			document.getElementById("strcardtype").innerHTML=strcardtype;
	
    }

</SCRIPT>
<title></title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body> 
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772">手机号码归属地查询</td>
  </tr>
</table>
<FORM action="" method=post name="mobileform" >
	<TABLE width=349 border=0 align="center" cellPadding=2 cellspacing="1" bordercolordark="#000000" class="table-back" style="BORDER-COLLAPSE: collapse">
			
			<TR bgColor=#eff1f3 class=tdc>
				<TD width=130 align=middle bgcolor="#eff1f3" class="table-back" noswap>手机号码(段)  </TD>
				<TD width=* align=middle bgcolor="#eff1f3" class="table-back"><INPUT class=tdc type="text" name="mobile" maxLength="11">
				<INPUT name="action" type="hidden" value=mobile>&nbsp;<INPUT class=bdtj name=B1 type=button value="查询" onClick="checkMobile();">
			  </TD>
			</TR>
			
	  </TABLE>
	  </FORM>
		<BR>

		<TABLE width=349 height="131" border="0" align="center" cellpadding="2" cellspacing="1" style="border-collapse: collapse">
			<TR>
				<TD colspan=2 class=title-top align=center height=35>手机号码(段)</TD>
			</TR>
			<TR class=tdc bgcolor=#EFF1F3>
				<TD width="130" align="center" bgcolor="#EFF1F3" class="table-back" noswap>您查询的手机号码</TD>
				<TD width=* align="center" bgcolor="#EFF1F3" class=table-back id="strmobile"></TD>
			</TR>
			<TR class=tdc bgcolor=#EFF1F3>
				<TD width="130" align="center" bgcolor="#EFF1F3" class="table-back" noswap>卡号归属地</TD>
				<TD width=* align="center" bgcolor="#EFF1F3" class=table-back id="strareas"></TD>
			</TR>
			<TR class=tdc bgcolor=#EFF1F3>
				<TD width="130" align="center" bgcolor="#EFF1F3" class="table-back" noswap>卡 类 型</TD>
				<TD width=* align="center" bgcolor="#EFF1F3" class=table-back id="strcardtype"></TD>
			</TR>
	  </TABLE>
</td></tr></table>
</body>
</html>



