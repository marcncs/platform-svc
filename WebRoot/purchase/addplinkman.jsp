<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>


<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script type="text/javascript" src="../js/validator.js"></script>
<script language="javascript">

function SelectProvide(){
	var p=showModalDialog("selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.referForm.pid.value=p.pid;
	document.referForm.providename.value=p.pname;
}

function ChkValue(){
	if ( !Validator.Validate(document.referForm,2) ){
		return false;
	}
	showloading();
	return true;
	
}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>
<html:errors/>
<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
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
		
		<form name="referForm" method="post" action="../purchase/addPlinkmanAction.do" onSubmit="return ChkValue();">
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="11%"  align="right">供应商名称：</td>
          <td width="25%"><input name="pid" type="hidden" id="pid" value="${pid}">
            <input name="providename" type="text" id="providename" value="${pname}" 
            readonly dataType="Require" msg="必须录入供应商名称!"><!--<a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" 
            width="18" height="18" border="0" align="absmiddle"></a>--><span class="STYLE1">*</span></td>
          <td width="10%" align="right">联系人姓名：</td>
          <td width="21%"><input name="name" type="text" dataType="Require" msg="必须录入联系人姓名!"><span class="STYLE1">*</span></td>
	      <td width="10%" align="right">联系人性别：</td>
	      <td width="23%"><windrp:select key="Sex" name="sex" p="n|f"/></td>
	  </tr>
	  <tr>
	    <td  align="right">身份证号码：</td>
	    <td><input name="idcard" type="text" require="false" dataType="IdCard" msg="必须录入正确的身份证号码!"></td>
	    <td align="right">联系人生日：</td>
	    <td><input type="text" name="birthday" onFocus="javascript:selectDate(this)" readonly="readonly"></td>
	    <td align="right">部门：</td>
	    <td><input name="department" type="text" ></td>
	    </tr>
	  <tr>
	    <td  align="right">职务：</td>
	    <td><input type="text" name="duty" ></td>
	    <td align="right">是否主联系人：</td>
	    <td><windrp:select key="YesOrNo" name="ismain" p="n|f"/></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>联系方式</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="11%"  align="right">办公电话：</td>
          <td width="25%"><input name="officetel" type="text" dataType="PhoneOrMobile" msg="必须录入正确的办公电话!" require="false"></td>
          <td width="10%" align="right">手机：</td>
          <td width="21%"><input type="text" name="mobile" require="true"  maxlength="11" dataType="Mobile" msg="必须录入正确的手机!"><span class="STYLE1">*</span></td>
	      <td width="10%" align="right">家庭电话：</td>
	      <td width="23%"><input name="hometel" type="text" require="false" dataType="Phone" msg="必须录入正确的家庭电话!"></td>
	  </tr>
	  <tr>
	    <td  align="right">Emai：</td>
	    <td><input type="text" name="email" require="false" dataType="Email" msg="必须录入正确的Email!"></td>
	    <td align="right">QQ：</td>
	    <td><input name="qq" type="text" id="qq" require="false" dataType="QQ" msg="必须录入正确的QQ!"></td>
	    <td align="right">MSN：</td>
	    <td><input type="text" name="msn"></td>
	    </tr>
	  <tr>
	    <td  align="right">地址：</td>
	    <td colspan="3"><input type="text" name="addr"  maxlength="100" size="100"></td>
	    
	    </tr>
	  </table>
</fieldset>
		
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td><div align="center">
                  <input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                  <input type="reset" name="cancel" onClick="javascript:window.close()" value="取消">
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
