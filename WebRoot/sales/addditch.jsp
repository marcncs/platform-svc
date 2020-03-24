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
  


	

 function formChk(){
	var dname = document.validateDitch.dname;
	
	if(dname.value==""){
		alert("渠道名称不能为空！");
		return false;
	}
	
	return true;
 }
 

</script>


<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
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
        <td width="772"> 添加渠道</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="validateDitch" method="post" action="addDitchAction.do" onSubmit="return formChk();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本资料</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	    <tr >
          <td width="9%"  align="right">渠道名称：</td>
	      <td width="26%" ><input name="dname" type="text" id="dname" >
            <span class="STYLE1"> *</span></td>
	      <td width="9%" align="right">渠道等级：</td>
	      <td width="22%">${ditchrankname}</td>
	      <td width="9%" align="right">信誉度：</td>
	      <td width="25%">${prestigename}</td>
	    </tr>
	    <tr >
          <td  align="right">规模：</td>
	      <td ><input name="scale" type="text" id="scale" maxlength="64"></td>
	      <td align="right">所属行业：</td>
	      <td>${vocationname}</td>
	      <td align="right">省份：</td>
	      <td><select name="province"  id="province" onChange="getResult('province','city');">
            <option value="">-省份-</option>
            <logic:iterate id="c" name="cals">
              <option value="${c.id}">${c.areaname}</option>
            </logic:iterate>
          </select></td>
	    </tr>
	    <tr >
          <td  align="right">城市：</td>
	      <td ><select name="city"  onChange="getResult('city','areas');">
            <option value="">-城市-</option>
          </select></td>
	      <td align="right">地区：</td>
	      <td><select name="areas" id="areas" >
            <option value="">-地区-</option>
          </select></td>
	      <td align="right">邮编：</td>
	      <td><input name="postcode" type="text" id="postcode" maxlength="6"></td>
	    </tr>
	    <tr >
          <td  align="right">详细地址：</td>
	      <td  colspan="5"><input name="detailaddr" type="text" id="detailaddr" size="50"></td>
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
	  	<td width="9%"  align="right">电话(1)：</td>
          <td width="26%"><input name="telone" type="text" id="telone" maxlength="26"></td>
          <td width="9%" align="right">电话(2)：</td>
          <td width="22%"><input name="teltwo" type="text" id="teltwo" maxlength="26"></td>
	      <td width="9%" align="right">传真：</td>
	      <td width="25%"><input name="fax" type="text" id="fax" maxlength="26"></td>
	  </tr>
	  <tr>
	    <td  align="right">网址：</td>
	    <td><input name="homepage" type="text" id="homepage" size="40"></td>
	    <td align="right">邮箱：</td>
	    <td><input name="email" type="text" id="email" maxlength="64"></td>
	    <td align="right">备注：</td>
	    <td><input name="memo" type="text" id="memo" value="" size="30"></td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right"> 开户银行： </td>
          <td width="26%"><input name="bankname" type="text" id="bankname" size="30"></td>
          <td width="9%" align="right">户名：</td>
          <td width="22%"><input name="doorname" type="text" id="doorname"></td>
	      <td width="9%" align="right">账号：</td>
	      <td width="25%"><input name="bankaccount" type="text" id="bankaccount" size="30"></td>
	  </tr>
	  <tr>
	    <td  align="right">税号：</td>
	    <td><input name="taxcode" type="text" id="taxcode" size="30"></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
</fieldset>
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
