<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function Check(){
	var adjustsum = document.addform.adjustsum.value;
	
	if(adjustsum==""){
		alert("调整金额不能为空！");
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
        <td width="772"> 新增现金银行调整</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	
	<form name="addform" method="post" action="addCashBankAdjustAction.do" onSubmit="return Check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="11%"  align="right">现金银行名称：</td>
          <td width="23%"><select name="cbid" id="cbid">
		   <logic:iterate id="d" name="cblist">
              <option value="${d.id}">${d.cbname}</option>
            </logic:iterate>
		  </select></td>
          <td width="9%" align="right">调整金额：</td>
          <td width="20%"><input name="adjustsum" type="text" id="adjustsum" value="0" maxlength="10" dataType="Double" msg="金额只能是数字!" require="false" ></td>
	      <td width="14%" align="right">备注：</td>
	      <td width="23%"><input name="memo" type="text" id="memo" maxlength="256" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false"></td>
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
</table>
	</td>
  </tr>
</table>

</body>
</html>
