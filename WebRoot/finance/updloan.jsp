<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectLoanObject(){
		showModalDialog("toSelectLoanObjectAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		document.addform.uid.value=getCookie("uid");
		document.addform.uidname.value=getCookie("uname");
	}
	
	function Chk(){
		var uid = document.addform.uid;
		
		if(uid.value==""||uid.value==null){
			alert("对象不能为空！");
			return false;
		}
		
		if ( !Validator.Validate(document.addform,2) ){
		return false;
		}
		
		showloading();
		return true;
		
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
        <td width="772"> 修改个人借款</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="addform" method="post" action="updLoanAction.do" onSubmit="return Chk();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="11%"  align="right"><input name="id" type="hidden" id="id" value="${lf.id}">
	  	  借款对象：</td>
          <td width="24%"><input name="uid" type="hidden" id="uid" value="${lf.uid}">
            <input name="uidname" type="text" id="uidname" value="${lf.uidname}"><a href="javascript:SelectLoanObject();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
          <td width="10%" align="right">借款日期：</td>
          <td width="22%"><input name="loandate" type="text" id="loandate" onFocus="javascript:selectDate(this)" value="${lf.loandate}" readonly></td>
	      <td width="10%" align="right">借款金额：</td>
	      <td width="23%"><input name="loansum" type="text" id="loansum" value="${lf.loansum}" size="15" dataType="Double" msg="金额只能是数字!" require="false" onKeyPress="KeyPress(this)"></td>
	  </tr>
	    <tr>
	  	<td width="11%"  align="right">付款来源：</td>
          <td width="24%"><select name="fundsrc">
		   <logic:iterate id="d" name="cblist">
              <option value="${d.id}" ${d.id==lf.fundsrc?"selected":""}>${d.cbname}</option>
            </logic:iterate>
		  </select></td>
          <td width="10%" align="right">&nbsp;</td>
          <td width="22%">&nbsp;</td>
          <td width="10%" align="right">&nbsp;</td>
	      <td width="23%">&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">借款用途：</td>
	    <td colspan="5"><textarea name="purpose" cols="120" rows="3" id="purpose" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false">${lf.purpose}</textarea></td>
	    </tr>
	  <tr>
	    <td  align="right">单位意见：</td>
	    <td colspan="5"><textarea name="companyidea" cols="120" rows="3" id="companyidea" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false">${lf.companyidea}</textarea></td>
	    </tr>
	  <tr>
	    <td  align="right">核算中心意见：</td>
	    <td colspan="5"><textarea name="hubidea" cols="120" rows="3" id="hubidea" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false">${lf.hubidea}</textarea></td>
	    </tr>
	  </table>
	</fieldset>
	
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center">
          <td><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
              <input type="reset" name="cancel" onClick="javascript:window.close()" value="取消"></td>
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
