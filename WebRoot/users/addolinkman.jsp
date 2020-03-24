<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<script type="text/javascript" src="../js/validator.js"></script>
<script language="javascript">

function SelectOrgan(){
	var c=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( c == undefined ){
		return;
	}
	document.validateLinkman.cid.value=c.id;
	document.validateLinkman.cname.value=c.organname;
}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>
<html:errors/>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
              <td width="772"> 添加联系人</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		
		<form name="validateLinkman" method="post" action="../users/addOLinkManAction.do" onsubmit="return Validator.Validate(this,2)" >
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right"><input name="cid" type="hidden" id="cid" value="${sjoid}" >
	  	  机构名称：</td>
          <td width="24%"><input name="cname" type="text" id="cname"
           value="<windrp:getname key='organ' value='${sjoid}' p='d'/>" 
            readonly><%--<a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" 
            align="absmiddle" height="18" border="0"></a> --%></td>
          <td width="10%" align="right">联系人姓名：</td>
          <td width="21%"><input name="name" type="text" dataType="Require" msg="必须录入联系人姓名!">
            <span class="STYLE1">*</span></td>
	      <td width="10%" align="right">手机：</td>
	      <td width="23%"><input type="text" name="mobile" dataType="Mobile" msg="必须录入正确的手机号码!">
            <span class="STYLE1">*</span></td>
	  </tr>
	  
	  <!--<tr>
	    <td  align="right">身份证号码：</td>
	    <td><input name="idcard" type="text" require="false" dataType="IdCard" msg="必须录入正确的身份证号码!"></td>
	    <td align="right">联系人生日：</td>
	    <td><input type="text" name="birthday" onFocus="javascript:selectDate(this)" readonly="readonly"></td>
	    </tr>--><%--
	  <tr>
	    <td  align="right">职务：</td>
	    <td><input type="text" name="duty" ></td>
	    <td align="right">主联系人：</td>
	    <td><windrp:select key="YesOrNo" name="ismain" p="n|f"/></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  --%></table>
</fieldset>

<%--<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>联系方式</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right">办公电话：</td>
          <td width="24%"><input name="officetel" type="text" require="false" dataType="Phone" msg="必须录入正确的办公电话号码!"></td>
          <td width="10%" align="right">手机：</td>
          <td width="21%"><input type="text" name="mobile" dataType="Mobile" msg="必须录入正确的手机号码!">
            <span class="STYLE1">*</span></td>
	      <!--<td width="10%" align="right">家庭电话：</td>
	      <td width="23%"><input name="hometel" type="text" require="false" dataType="Phone" msg="必须录入正确的家庭电话号码!"></td>-->
	 	  <td  align="right">Emai：</td>
	      <td><input type="text" name="email" require="false" dataType="Email" msg="必须录入正确的Email!"></td>
	  </tr>
	  <tr>
	    
	    <td align="right">QQ：</td>
	    <td><input name="qq" type="text" id="qq" require="false" dataType="QQ" msg="必须录入正确的QQ!"></td>
	    <!--<td align="right">MSN：</td>
	    <td><input type="text" name="msn"></td>-->
	    <td  align="right">送货地址：</td>
	    <td colspan="5"><input name="addr" type="text" size="40" ></td>
	    </tr>
	  <tr>
	    
	    </tr>
	  </table>
</fieldset>
		
		--%><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td><div align="center">
                  <input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                  <input type="button" name="cancel" onClick="javascript:window.close()" value="取消">
                </div></td>
              </tr>
        </table>
		 </form>
		 </td>
      </tr>
    </table></td>
  </tr>
</table>

</body>
</html>
